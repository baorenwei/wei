package com.example.base.base;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.View;

import com.baidu.mapapi.SDKInitializer;
import com.example.base.utils.LogUtils;

import java.io.File;
import cn.smssdk.SMSSDK;


/**
 * Created by Administrator on 2016/1/7.
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initSMSSDK();
//        initYING();
//        initBaiDuSDK();
    }

    private void initBaiDuSDK(){
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
    }

    private void initSMSSDK(){
        SMSSDK.initSDK(this, "6f42ce0f17ac", "475cb159f24926d723017b28809a0ce8");
    }
    private void initYING(){
        File file = new File("storage/sdcard1/bao.txt");
        if (!file.exists()){
            file.mkdirs();
        }
//            Boolean isInit = EZOpenSDK.initLib(this, "7ed2f4946ca543098661cc360c4083c4", null);
//            if (isInit){
//                LogUtils.showLogI("初始化成功");
//            }else{
//                LogUtils.showLogI("初始化失败");
            }
        //打开授权登录中间页
//        EZOpenSDK.getInstance().openLoginPage();
//    }
}