package com.example.base.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.example.administrator.bao.R;
import com.google.zxing.common.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2016/1/3.
 */
public class FileUtils {

    public static void save(Context context, String fileName, Bitmap bitmap) {

        if (bitmap == null || "".equals(bitmap)) {
            return;
        }
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            FileOutputStream fos = null;
            File fileSDDir = Environment.getExternalStorageDirectory() ;  //获取SD卡目录
            File fileSave = new File(fileSDDir,fileName);
            try {
                fos = new FileOutputStream(fileSave);
                fos.write(bitmap.toString().getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                if (fos != null){
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return;
        }

        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(fileName, context.MODE_PRIVATE);
            fos.write(bitmap.toString().getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String getData(Context context, String fileName) {
        FileInputStream fis = null;
        ByteArrayOutputStream baos = null;
        try {
            fis = context.openFileInput(fileName);
            baos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int len;
            while ((len = fis.read(b)) != -1) {
                baos.write(b, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    if (fis != null) {
                        fis.close();
                    }
                    if (baos != null) {
                        baos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return baos.toString();
    }
}
