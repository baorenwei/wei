package com.example.base.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.bao.R;

/**
 * Created by tom on 15/5/28.
 */
public class MyProgressDialog  extends AlertDialog{

    private ProgressBar mProgress;
    private TextView mMessageView;

    private CharSequence mMessage;

    protected MyProgressDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setGravity(Gravity.CENTER_HORIZONTAL);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setContentView(R.layout.progress_dialog);
        mProgress = (ProgressBar)findViewById(R.id.progress);
        mMessageView = (TextView)findViewById(R.id.message);
        mMessageView.setText(mMessage);
        if (mMessage != null){
            mMessageView.setVisibility(View.VISIBLE);
        }else {
            mMessageView.setVisibility(View.GONE);
        }
    }

    public static MyProgressDialog show(Context context) {
        return show(context, null);
    }

    public static MyProgressDialog show(Context context, CharSequence title) {
        return show(context, title, null);
    }

    public static MyProgressDialog show(Context context, CharSequence title,
                                      CharSequence message) {
        return show(context, title, message,false);
    }

    public static MyProgressDialog show(Context context, CharSequence title,
                                      CharSequence message, boolean cancelable) {
        return show(context, title, message, cancelable, null);
    }

    public static MyProgressDialog show(Context context, CharSequence title,
                                      CharSequence message, boolean cancelable,
                                        OnCancelListener cancelListener) {
        MyProgressDialog dialog = new MyProgressDialog(context);
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setCancelable(cancelable);
        dialog.setOnCancelListener(cancelListener);
        dialog.show();
        return dialog;
    }

    @Override
    public void setMessage(CharSequence message) {
        if (mMessageView != null) {
            mMessageView.setText(message);
            if (message != null){
                mMessageView.setVisibility(View.VISIBLE);
            }else {
                mMessageView.setVisibility(View.GONE);
            }
        }else {
            this.mMessage = message;
        }

    }
}
