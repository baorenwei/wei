package com.example.base.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Administrator on 2016/1/4.
 */
public abstract class BaseBroadcastReceiver extends BroadcastReceiver {
    @Override
    public abstract void onReceive(Context context, Intent intent);
}
