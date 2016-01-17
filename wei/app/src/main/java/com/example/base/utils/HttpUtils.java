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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
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
    public static String httpPost(String url, Map<String, String> params, String str) {

        InputStream is = null;
        try {
            URL uri = new URL(url);
            URLConnection conn =  uri.openConnection();
            HttpURLConnection connection = (HttpURLConnection)conn;
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setConnectTimeout(5000);
            connection.setUseCaches(false);
            connection.setRequestProperty("Content-type", "application/x-java-serialized-object");
            // 设定请求的方法为"POST"，默认是GET
            connection.setRequestMethod(str);
            connection.connect();   //请求连接
            byte[] bytes = params.toString().getBytes();
            connection.getOutputStream().write(bytes);
            is = connection.getInputStream();
            return readInput(is);

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private static String readInput(InputStream is) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        byte[] buffer = new byte[1024];
        int len = 0;
            while((len = is.read(buffer)) != -1){
                outputStream.write(buffer,0,len);
            }
        byte[] b = outputStream.toByteArray();
        String json = new String(b);
        outputStream.close();
        is.close();
        return json;
    }

    public void checkVisibility() {

        int len = LightImage.imageThumbUrls.length;

        for (int i = 0; i < len; i++) {
//            ImageView imageView = LightImage.imageThumbUrls.
//            int borderTop = (Integer) imageView.getTag(R.string.border_top);
//            int borderBottom = (Integer) imageView.getTag(R.string.border_bottom);
//            if (borderBottom > getScrollY() && borderTop < getScrollY() + scrollViewHeight) {
//                String imageUrl = (String) imageView.getTag(R.string.image_url);
//                Bitmap bitmap = imageLoader.getBitmapFromMemoryCache(imageUrl);
//                if (bitmap != null) {
//                    imageView.setImageBitmap(bitmap);
//                } else {
//                    LoadImageTask task = new LoadImageTask(imageView);
//                    task.execute(i);
//                }
//            } else {
//                imageView.setImageResource(R.drawable.empty_photo);
//            }
        }
    }

    //图片下载
    public static Bitmap downLoadImage(int position,Context context){

//        ImageView imageView = new ImageView(HttpUtils.this);
        LoadImageTask task = new LoadImageTask(context);
//        String imahrUrl =  LightImage.imageThumbUrls[position];
        Bitmap bitmap;
//                =  LruCacheUtils.getInstance().getBitmapFromMemCache(imahrUrl);
//        if (bitmap != null){
//        return bitmap;
//        }else {
            task.execute(2);
//        Bitmap bitmap = task.bitmap;
//        }
        return null;
    }
    static class LoadImageTask extends AsyncTask<Integer,Void,Bitmap> {
        private String mImageUrl;
        private int position;
        Bitmap bitmap;
        Context context;
        LruCacheUtils mUtils;
        /**
         * 可重复使用的ImageView
         */
        private ImageView mImageView;

        public LoadImageTask() {
        }

        /**
         * 将可重复使用的ImageView传入
         *
         */
        public LoadImageTask(Context context) {
            this.context = context;
            mUtils = new LruCacheUtils();
        }

        @Override
        protected Bitmap doInBackground(Integer... params) {
            position = params[0];
            mImageUrl = LightImage.imageThumbUrls[position];

            Bitmap imageBitmap = mUtils.getBitmapFromMemoryCache(mImageUrl);
            if (imageBitmap == null) {
//            Bitmap imageBitmap;
                imageBitmap =   loadImage(mImageUrl,context);
//                LogUtils.showLogI(imageBitmap+"");
            }
            return imageBitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
//                double ratio = bitmap.getWidth() / (columnWidth * 1.0);
//                int scaledHeight = (int) (bitmap.getHeight() / ratio);
//                addImage(bitmap, columnWidth, scaledHeight);
                this.bitmap = bitmap;
            }
//            taskCollection.remove(this);
        }

        /**
         * 根据传入的URL，对图片进行加载。如果这张图片已经存在于SD卡中，则直接从SD卡里读取，否则就从网络上下载。
         *
         * @param imageUrl 图片的URL地址
         * @return 加载到内存的图片。
         */
        private Bitmap loadImage(String imageUrl,Context context) {
            File imageFile = new File(getImagePath(imageUrl));
            if (!imageFile.exists()) {
                downloadImage(imageUrl);
            }
            if (imageUrl != null) {
                Bitmap bitmap = mUtils.decodeSampledBitmapFromResource(
                        imageUrl , 100);
                if (bitmap != null) {
                    mUtils.addBitmapToMemoryCache(imageUrl, bitmap);
                    return bitmap;
                }
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
                Bitmap bitmap = mUtils.decodeSampledBitmapFromResource(
                        imageFile.getPath(), 100);
                LogUtils.showLogI(imageFile.getPath()+"");
                LogUtils.showLogI(bitmap+"");
                if (bitmap != null) {
                    mUtils.addBitmapToMemoryCache(imageUrl, bitmap);
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
                    .getPath() + "/aImage/";
            File file = new File(imageDir);
            if (!file.exists()) {
                file.mkdirs();
                LogUtils.showLogI("到了这里");
            }
            String imagePath = imageDir + imageName;
            LogUtils.showLogI(imagePath);
            return imagePath;
        }

    }
}
