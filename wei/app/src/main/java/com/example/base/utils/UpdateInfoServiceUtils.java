package com.example.base.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import model.UpdateInfo;

/**
 * Created by Administrator on 2016/1/28.
 * 获取更新信息
 */
public class UpdateInfoServiceUtils {

    public static UpdateInfo getUdateInfo(){
        String path  = GetServerUrl.getServerUrl() + "/update.txt";

        StringBuffer sb = new StringBuffer();
        BufferedReader bufferedReader = null;
        String mLine;
        try {
            URL url = new URL(path);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            while((mLine = bufferedReader.readLine()) != null){
                sb.append(mLine);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
                try {
                    if ( bufferedReader != null) {
                        bufferedReader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
            }
        }
        String info = sb.toString();
        UpdateInfo mInfo = new UpdateInfo();
        mInfo.setVersion(info.split("&")[1]);
        mInfo.setDescription(info.split("&")[2]);
        mInfo.setUrl(info.split("&")[3]);
        return  mInfo;
    }
}
