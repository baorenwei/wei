package com.example.base.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.bao.R;

/**
 * Created by Administrator on 2016/2/19.
 */
public class MyDialog extends Dialog {

//    TextView mContentTextView;

    public MyDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.widget_dialog_layout);

//        mContentTextView = (TextView)findViewById(R.id.contentTextView);
    }

    public void setContent(int content){
//        mContentTextView.setText(content);
    }

}
