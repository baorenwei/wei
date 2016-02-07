package com.example.base.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Movie;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.bao.R;

import java.io.InputStream;

/**
 * Created by Administrator on 2016/2/5.
 */
public class MyLinearLayout extends LinearLayout {

    private ImageView mLinImageView;
    private TextView mLinTextView;

    public MyLinearLayout(Context context){
        super(context);
//        View layout = LayoutInflater.from(context).inflate(R.layout.widget_linearlayout_layout,null);
//        mLinImageView = (ImageView)layout.findViewById(R.id.linImageView);
//        mLinTextView = (TextView)layout.findViewById(R.id.linTextView);
    }

    public MyLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.MyLayout);
        String text = a.getString(R.styleable.MyLayout_text);
        Drawable resuId = a.getDrawable(R.styleable.MyLayout_backgroup);

        LayoutInflater layout = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layout.inflate(R.layout.widget_linearlayout_layout,this);
        mLinImageView = (ImageView)findViewById(R.id.linImageView);
        mLinTextView = (TextView)findViewById(R.id.linTextView);

        mLinImageView.setImageDrawable(resuId);
        mLinTextView.setText(text);
    }

    public void setImageView(int resId){
        mLinImageView.setImageResource(resId);
    }
    public void setTextView(String resId){
        mLinTextView.setText(resId);
    }
}
