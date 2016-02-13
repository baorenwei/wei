package com.example.base.utils;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 2016/2/13.
 */
public class HttpUtil {


    public static String postType(String actionUrl, Map<String, String> params, String[] files, String method) throws IOException {
        if(files == null){
            return httpPost(actionUrl,params,method);
        }else{
            return post(actionUrl,params,files,method);
        }
    }

    /**
     * 普通文本参数的提交
     */
    public static String httpPost(String url, Map<String, String> params, String method) throws IOException {
//
        DataOutputStream ds = null;
        InputStream is = null;
        try {
            StringBuffer sb = paseMap(params);
            URL uri = new URL(url);
            URLConnection conn = uri.openConnection();
            HttpURLConnection connection = (HttpURLConnection) conn;
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setConnectTimeout(5000);
            connection.setUseCaches(false);
            connection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("Charset", "UTF-8");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestMethod(method);
            connection.connect();

            //上传文本
            byte[] bytes = sb.toString().getBytes();
            connection.getOutputStream().write(bytes);

            is = connection.getInputStream();
            return readInput(is);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (ds != null){
                    ds.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 通过拼接的方式构造请求内容，实现参数传输以及文件传输
     *
     * @param actionUrl 访问的服务器URL
     * @param params 普通参数
     * @param files 文件参数
     * @return
     */
    public static String post(String actionUrl, Map<String, String> params, String[] files, String method) throws IOException
    {
        String end = "/r/n";
        String twoHyphens = "--";
        String boundary = "*****";

        URL uri = new URL(actionUrl);
        HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
        conn.setReadTimeout(5 * 1000); // 缓存的最长时间
        conn.setDoInput(true);// 允许输入
        conn.setDoOutput(true);// 允许输出
        conn.setUseCaches(false); // 不允许使用缓存
        conn.setRequestMethod(method);
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.setRequestProperty("Charset", "UTF-8");
        conn.setRequestProperty("Content-Type",
                "multipart/form-data;boundary=" + boundary);

        // 首先组拼文本类型的参数
        StringBuffer sb = paseMap(params);

        DataOutputStream ds = new DataOutputStream(conn.getOutputStream());
        ds.write(sb.toString().getBytes());

        for (int i = 0; i < files.length; i++) {
            String uploadFile = files[i];
            String filename = uploadFile.substring(uploadFile.lastIndexOf("//") + 1);
            ds.writeBytes(twoHyphens + boundary + end);
//            ds.writeBytes("Content-Disposition: form-data; " +
//                            "name=/"file" + i + "/";filename=/"" +
//                    filename + "/"" + end);
                    ds.writeBytes(end);
            FileInputStream fStream = new FileInputStream(uploadFile);
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];
            int length = -1;
            while ((length = fStream.read(buffer)) != -1) {
                ds.write(buffer, 0, length);
            }
            ds.writeBytes(end);
              /* close streams */
            fStream.close();
        }
        ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
        ds.flush();
        String json = readInput(conn.getInputStream());
        return json;
    }

    //拼接文本参数
    private static StringBuffer paseMap(Map<String, String > params) {
        StringBuffer sb = null;
        if (sb == null) {
            sb = new StringBuffer();
        }
        Iterator it = params.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry element = (Map.Entry) it.next();
            sb.append(element.getKey());
            sb.append("=");
            sb.append(element.getValue());
            sb.append("&");
        }
        return sb;
    }

    //讲流转换成Json数据
    private static String readInput(InputStream is) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = is.read(buffer)) != -1) {
            outputStream.write(buffer, 0, len);
        }
        byte[] b = outputStream.toByteArray();
        String json = new String(b);
        outputStream.close();
        is.close();
        return json;
    }
}
