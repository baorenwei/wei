package com.example.base.utils;

import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import model.FormFile;

/**
 * Created by Administrator on 2016/2/13.
 */
public class HttpUtil {


    public static String postType(String actionUrl, Map<String, String> params, Map<String, File> files, String method) throws IOException {
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
            LogUtils.showLogI(connection.getResponseCode()+"");
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
    public static String post(String actionUrl, Map<String, String> params, Map<String, File> files,String method) throws IOException {

        String newName = "htys.mp3";

        //要上传的本地文件路径

         String uploadFile = "/data/data/com.xzq/htys.mp3";

        String end = "/r/n";

        String Hyphens = "--";

        String boundary = "*****";

            URL url = new URL(actionUrl);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();

      /* 允许Input、Output，不使用Cache */

            con.setDoInput(true);

            con.setDoOutput(true);

            con.setUseCaches(false);

      /* 设定传送的method=POST */

            con.setRequestMethod("POST");

      /* setRequestProperty */

            con.setRequestProperty("Connection", "Keep-Alive");

            con.setRequestProperty("Charset", "UTF-8");

            con.setRequestProperty("Content-Type",

                    "multipart/form-data;boundary=" + boundary);

      /* 设定DataOutputStream */
            DataOutputStream ds = new DataOutputStream(con.getOutputStream());
            ds.writeBytes(Hyphens + boundary + end);
//            ds.writeBytes("Content-Disposition: form-data; "
//                    + "name=/"file1/";filename=/"" + newName + "/"" + end);
            ds.writeBytes(end);
      /* 取得文件的FileInputStream */
            FileInputStream fStream = new FileInputStream(uploadFile);
      /* 设定每次写入1024bytes */
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];
            int length = -1;
      /* 从文件读取数据到缓冲区 */
            while ((length = fStream.read(buffer)) != -1)
            {
        /* 将数据写入DataOutputStream中 */
                ds.write(buffer, 0, length);
            }
            ds.writeBytes(end);
            ds.writeBytes(Hyphens + boundary + Hyphens + end);
            fStream.close();
            ds.flush();
      /* 取得Response内容 */
            InputStream is = con.getInputStream();
            int ch;
            StringBuffer b = new StringBuffer();
            while ((ch = is.read()) != -1)
            {
                b.append((char) ch);
            }
            System.out.println("上传成功");
            ds.close();
        return null;
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

    private static StringBuffer paseMaps(Map<String, File > file) {
        StringBuffer sb = null;
        if (sb == null) {
            sb = new StringBuffer();
        }
        Iterator it = file.entrySet().iterator();
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
