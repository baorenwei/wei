package com.example.base.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import android.preference.DialogPreference;
import android.widget.Toast;

import activity.UpdateActivity;

/**
 * Created by Administrator on 2016/2/6.
 */
public class DialogUtils {

    public static void createDialog(Context context,int iconId,String iconTitle,String message,
                                    String positiveButton,String NegativeButton,DialogInterface.OnClickListener ll){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(iconId);
        builder.setTitle(iconTitle);
        builder.setMessage(message);
        builder.setCancelable(false);

        builder.setPositiveButton(positiveButton,ll);
        builder.setNegativeButton(NegativeButton,ll);


//        builder.setPositiveButton("更新", new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                if (Environment.getExternalStorageState().equals(
//                        Environment.MEDIA_MOUNTED)) {
//                    downFile(mInfo.getUrl());     //在下面的代码段
//                } else {
//                    Toast.makeText(UpdateActivity.this, "SD卡不可用，请插入SD卡",
//                            Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//            }
//
//        });
        builder.create().show();
    }

    public class Dialog implements DialogInterface.OnClickListener{

        @Override
        public void onClick(DialogInterface dialog, int which) {

        }
    }
}
