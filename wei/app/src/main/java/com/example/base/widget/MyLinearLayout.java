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

//    TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.anim);
//    int resourceId = getResourceId(a, context, attrs);
//    if (resourceId != 0) {
//        // 当资源id不等于0时，就去获取该资源的流
//        InputStream is = getResources().openRawResource(resourceId);
//        // 使用Movie类对流进行解码
//        mMovie = Movie.decodeStream(is);
//        if (mMovie != null) {
//            // 如果返回值不等于null，就说明这是一个GIF图片，下面获取是否自动播放的属性
//            isAutoPlay = a.getBoolean(R.styleable.anim_auto_play, false);
//            Bitmap bitmap = BitmapFactory.decodeStream(is);
//            mImageWidth = bitmap.getWidth();
//            mImageHeight = bitmap.getHeight();
//            bitmap.recycle();
//            if (!isAutoPlay) {
//                // 当不允许自动播放的时候，得到开始播放按钮的图片，并注册点击事件
//                mStartButton = BitmapFactory.decodeResource(getResources(),
//                        R.drawable.ic_launcher);
//                setOnClickListener(this);
//            }
//        }

    public MyLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

//        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MyLayout);
//        a.getInteger(R.styleable.ba,0);
//        a.getString(R.styleable.anim_auto_play)

        View layout = LayoutInflater.from(context).inflate(R.layout.widget_linearlayout_layout,null);
        mLinImageView = (ImageView)layout.findViewById(R.id.linImageView);
        mLinTextView = (TextView)layout.findViewById(R.id.linTextView);
    }

    public void setImageView(int resId){
        mLinImageView.setImageResource(resId);
    }
    public void setTextView(String resId){
        mLinTextView.setText(resId);
    }
}
