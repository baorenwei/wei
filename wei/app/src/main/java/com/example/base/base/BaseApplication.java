package com.example.base.base;

import android.app.Application;

import cn.smssdk.SMSSDK;


/**
 * Created by Administrator on 2016/1/7.
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SMSSDK.initSDK(this, "6f42ce0f17ac", "475cb159f24926d723017b28809a0ce8");
    }


}
