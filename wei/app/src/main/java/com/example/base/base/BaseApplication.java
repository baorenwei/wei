package com.example.base.base;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.view.View;

import com.example.base.utils.LogUtils;
import com.videogo.openapi.EZOpenSDK;

import cn.smssdk.SMSSDK;


/**
 * Created by Administrator on 2016/1/7.
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
//        initSMSSDK();
        initYING();
    }

    private void initSMSSDK(){
        SMSSDK.initSDK(this, "6f42ce0f17ac", "475cb159f24926d723017b28809a0ce8");
    }
    private void initYING(){
            EZOpenSDK.initLib(this, "7ed2f4946ca543098661cc360c4083c4", null);
        LogUtils.showLogI("初始化成功");
        //打开授权登录中间页
        EZOpenSDK.getInstance().openLoginPage();
    }
}