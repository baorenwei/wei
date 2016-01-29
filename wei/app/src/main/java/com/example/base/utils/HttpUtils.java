package com.example.base.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import com.example.administrator.bao.R;

import org.apache.http.params.CoreConnectionPNames;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.Key;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import light.LightImage;

/**
 * Created by Administrator on 2016/1/13.
 */
public class HttpUtils {

    /**
     * 普通文本参数的提交
     *
     * @param url
     * @param params
     * @return
     */
    public static String httpPost(String url, Map<String, Object> params, String str,String[] uploadFiles) {

        String end = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";

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
            connection.setRequestProperty("Content-Type",
            "multipart/form-data;boundary=" + boundary);
            connection.setRequestMethod(str);
            connection.connect();   //请求连接

            //上传文本
            byte[] bytes = sb.toString().getBytes();
            connection.getOutputStream().write(bytes);
            //上传文件
            ds = new DataOutputStream(connection.getOutputStream());
            for (int i = 0; i < uploadFiles.length; i ++){
                String uploadFile = uploadFiles[i];
                String fileName = uploadFile.substring(uploadFile.lastIndexOf("//") + 1);
                ds.writeBytes(twoHyphens + boundary + end);
//                ds.writeBytes();
                ds.writeBytes(end);
                FileInputStream fis = new FileInputStream(uploadFile);
                int len = -1;
                byte[] b = new byte[1024];
                while((len = fis.read(b)) != -1){
                    ds.write(b,0,len);
                }
                ds.writeBytes(end);
                fis.close();
            }
            ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
            ds.flush();

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

    private static StringBuffer paseMap(Map<String, Object> params) {
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

    //图片下载
    public static void downLoadImage(Object object, Context context) throws FileNotFoundException {

        if (object instanceof String) {
            Bitmap bitmap = LruCacheUtils.getInstance().getBitmapFromMemoryCache((String) object);
            if (bitmap == null) {
                LoadUrlImageTask task = null;
                if (task == null) {
                    task = new LoadUrlImageTask(context);
                }
                task.execute(object);
            }
        } else if (object instanceof Integer) {
            Bitmap bitmap = LruCacheUtils.getInstance().getBitmapFromMemoryCache(LightImage.imageThumbUrls[(Integer) object]);
            if (bitmap == null) {
                LoadPositionImageTask task = null;
                if (task == null) {
                    task = new LoadPositionImageTask(context);
                    task.execute(object);
                }
            }
        }
    }

    static class LoadUrlImageTask extends AsyncTask<Object, Void, Bitmap> {

        String mImageUrl;
        Context mContext;

        LoadUrlImageTask(Context context) {
            this.mContext = context;
        }

        @Override
        protected Bitmap doInBackground(Object... params) {
            mImageUrl = (String) params[0];
            Bitmap imageBitmap = LruCacheUtils.getInstance().getBitmapFromMemoryCache(mImageUrl);
            if (imageBitmap == null) {
                imageBitmap = load(mImageUrl);
            }
            return imageBitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (bitmap != null) {
                LruCacheUtils.getInstance().addBitmapToMemoryCache(mImageUrl, bitmap);
            }
        }

        private Bitmap load(String mImageUrl) {
            File fileImage = new File(getImagePath(mImageUrl));
            if (!fileImage.exists()) {
                dowmImage(mImageUrl);
            }
            if (mImageUrl != null) {
                Bitmap bitmap = LruCacheUtils.getInstance().decodeSampledBitmapFromResource(fileImage.getPath(), 100);
                LruCacheUtils.getInstance().addBitmapToMemoryCache(mImageUrl, bitmap);
                return bitmap;
            }
            return null;
        }

        private void dowmImage(String mImageUrl) {

            HttpURLConnection conn = null;
            FileOutputStream fos = null;
            BufferedOutputStream bos = null;
            BufferedInputStream bis = null;
            File imageFile = null;
            try {
                URL url = new URL(mImageUrl);
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setConnectTimeout(5000);
                bis = new BufferedInputStream(conn.getInputStream());
                imageFile = new File(getImagePath(mImageUrl));
                fos = new FileOutputStream(imageFile);
                bos = new BufferedOutputStream(fos);
                int len = 0;
                byte[] b = new byte[1024];
                while ((len = bis.read(b)) != -1) {
                    bos.write(b, 0, len);
                    bos.flush();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bis != null) {
                        bis.close();
                    }
                    if (bos != null) {
                        bos.close();
                    }
                    if (fos != null) {
                        fos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (imageFile != null) {
                Bitmap bitmap = LruCacheUtils.getInstance().decodeSampledBitmapFromResource(
                        imageFile.getPath(), 100);
                if (bitmap != null) {
                    LruCacheUtils.getInstance().addBitmapToMemoryCache(mImageUrl, bitmap);
                }
            }
        }

        private String getImagePath(String imageUrl) {
            int lastSlashIndex = imageUrl.lastIndexOf("/");
            String imageName = imageUrl.substring(lastSlashIndex + 1);
            String imageDir = Environment.getExternalStorageDirectory()
                    .getPath() + "/photoImage/";
            imageDir.trim();
            File file = new File(imageDir);
            boolean is = file.exists();
            if (!file.exists()) {
                file.mkdirs();
            }
            String imagePath = imageDir + imageName;
            return imagePath;
        }
    }


    static class LoadPositionImageTask extends AsyncTask<Object, Void, Bitmap> {
        private String mImageUrl;
        private int position;
        Context context;

        public LoadPositionImageTask(Context context) {
            this.context = context;
        }

        @Override
        protected Bitmap doInBackground(Object... params) {
            position = (Integer) params[0];
            mImageUrl = LightImage.imageThumbUrls[position];
            Bitmap imageBitmap = LruCacheUtils.getInstance().getBitmapFromMemoryCache(mImageUrl);
            if (imageBitmap == null) {
                imageBitmap = loadImage(mImageUrl, context);
            }
            return imageBitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                LruCacheUtils.getInstance().addBitmapToMemoryCache(mImageUrl, bitmap);
            }
        }

        /**
         * 根据传入的URL，对图片进行加载。如果这张图片已经存在于SD卡中，则直接从SD卡里读取，否则就从网络上下载。
         *
         * @param imageUrl 图片的URL地址
         * @return 加载到内存的图片。
         */
        private Bitmap loadImage(String imageUrl, Context context) {
            File imageFile = new File(getImagePath(imageUrl));
            if (!imageFile.exists()) {
                downloadImage(imageUrl);
            }
            if (mImageUrl != null) {
                Bitmap bitmap = LruCacheUtils.getInstance().decodeSampledBitmapFromResource(imageFile.getPath(), 100);
                LruCacheUtils.getInstance().addBitmapToMemoryCache(mImageUrl, bitmap);
                bitmap = LruCacheUtils.getInstance().getBitmapFromMemoryCache(mImageUrl);
                return bitmap;
            }
            return null;
        }

        /**
         * 将图片下载到SD卡缓存起来。
         *
         * @param imageUrl 图片的URL地址。
         */
        private void downloadImage(String imageUrl) {
            HttpURLConnection con = null;
            FileOutputStream fos = null;
            BufferedOutputStream bos = null;
            BufferedInputStream bis = null;
            File imageFile = null;
            try {
                URL url = new URL(imageUrl);
                con = (HttpURLConnection) url.openConnection();
                con.setConnectTimeout(5 * 1000);
                con.setReadTimeout(15 * 1000);
                con.setDoInput(true);
                con.setDoOutput(true);
                bis = new BufferedInputStream(con.getInputStream());
                imageFile = new File(getImagePath(imageUrl));
                fos = new FileOutputStream(imageFile);
                bos = new BufferedOutputStream(fos);
                byte[] b = new byte[1024];
                int length;
                while ((length = bis.read(b)) != -1) {
                    bos.write(b, 0, length);
                    bos.flush();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bis != null) {
                        bis.close();
                    }
                    if (bos != null) {
                        bos.close();
                    }
                    if (con != null) {
                        con.disconnect();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (imageFile != null) {
                Bitmap bitmap = LruCacheUtils.getInstance().decodeSampledBitmapFromResource(
                        imageFile.getPath(), 100);
                if (bitmap != null) {
                    LruCacheUtils.getInstance().addBitmapToMemoryCache(imageUrl, bitmap);
                }
            }
        }

        /**
         * 获取图片的本地存储路径。
         *
         * @param imageUrl 图片的URL地址。
         * @return 图片的本地存储路径。
         */
        private String getImagePath(String imageUrl) {
            int lastSlashIndex = imageUrl.lastIndexOf("/");
            String imageName = imageUrl.substring(lastSlashIndex + 1);
            String imageDir = Environment.getExternalStorageDirectory()
                    .getPath() + "/Photo/";
            File file = new File(imageDir);
            if (!file.exists()) {
                file.mkdirs();
            }
            String imagePath = imageDir + imageName;
            return imagePath;
        }

    }
}
