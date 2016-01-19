package com.example.base.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.administrator.bao.R;
import com.example.base.utils.LogUtils;
import com.example.base.utils.NetUtils;
import com.google.zxing.client.android.ScanQRActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Interface.Watcher;

/**
 *
 *
 * */
public class BaseActivity extends FragmentActivity implements View.OnClickListener , Watcher {

    private TextView mLeftTextView;
    private TextView mTitltTextView;
    private TextView mRightLeftTextView;
    private TextView mRightTextView;

    public Context mContext;
    public boolean isConneted;
    private int mMenuResid;

    private static final int REQUEST_CODE = 765;

    private final static List<Activity> mActivitys = new ArrayList<Activity>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        init();
    }
    private void init(){
        mContext = getApplication();
        mActivitys.add(this);
        setupActionBar(R.layout.action_bar);
        setMenu(R.menu.menu_main);
    }

    private void setupActionBar(int layoutId){
        android.app.ActionBar actionBar = getActionBar();
        if (null != actionBar) {
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayShowCustomEnabled(true);
            LayoutInflater inflator = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflator.inflate(layoutId, null);
            android.app.ActionBar.LayoutParams layout = new android.app.ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            actionBar.setCustomView(v, layout);
            mLeftTextView = (TextView) actionBar.getCustomView().findViewById(R.id.leftTextView);
            mTitltTextView = (TextView) actionBar.getCustomView().findViewById(R.id.titleTextView);
            mRightLeftTextView = (TextView) actionBar.getCustomView().findViewById(R.id.rightLeftTextView);
            mRightTextView = (TextView) actionBar.getCustomView().findViewById(R.id.rightTextView);

            mLeftTextView.setOnClickListener(this);
            mTitltTextView.setOnClickListener(this);
            mRightLeftTextView.setOnClickListener(this);
            mRightTextView.setOnClickListener(this);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        isConneted = NetUtils.isNetworkConnected(mContext);
        if (isConneted == false){
            LogUtils.showToast(mContext,"网络已断开");
        }
    }

    public void setLeftTextView(String str){

        mLeftTextView.setText(str);
    }
    public void setLeftTextView(int resit){

        mLeftTextView.setText(resit);
    }
    public void setLeftTextViewBackgroup(int backgroup){

        mLeftTextView.setBackgroundResource(backgroup);
    }


    public void setTitleTextView(String str){

        mTitltTextView.setText(str);
    }
    public void setTitleTextView(int resit){

        mTitltTextView.setText(resit);
    }
    public void setTitleBackgroup(int backgroup){

        mTitltTextView.setBackgroundResource(backgroup);
    }


    public void setRightLeftTextView(String str){

        mRightLeftTextView.setText(str);
    }
    public void setRightLeftTextView(int resit){

        mRightLeftTextView.setText(resit);
    }
    public void setRightLeftTextViewBackgroup(int backgroup){

        mRightLeftTextView.setBackgroundResource(backgroup);
    }


    public void setRightTextView(String str){

        mRightTextView.setText(str);
    }
    public void setRightTextView(int resit){

        mRightTextView.setText(resit);
    }
    public void setRightTextViewBackgroup(int backgroup){

        mRightTextView.setBackgroundResource(backgroup);
    }

   //监听返回键
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.abc_slide_in_left, R.anim.abc_slide_out_right);
    }

    //获取手机屏幕属性
    public int getDisplayMetrics(){
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;
        return width;
    }

    @Override
    public void finish() {
        super.finish();
        mActivitys.remove(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mActivitys.remove(this);
    }


    public void clearTask(){
        List<Activity> activities = new ArrayList<Activity>(mActivitys);
        int size = activities.size();
        if (size > 1){
            for (int i = 0;i < size - 1;i++ ){
                Activity activity = activities.get(i);
                activity.finish();
            }
        }
    }

    public void stopApp(){
        List<Activity> activities = new ArrayList<Activity>(mActivitys);
        for (Activity activity : activities){
            activity.finish();
        }
        mActivitys.clear();
    }

    public void removeTopActivity(){
        if (mActivitys.size() > 1){
            Activity activity = mActivitys.get(mActivitys.size() -1);
            activity.finish();
        }
    }

    public void backToRootActivity(){
        List<Activity> activities = new ArrayList<Activity>(mActivitys);
        int size = activities.size();
        if (size > 1){
            for (int i = 1;i < size;i++ ){
                Activity activity = activities.get(i);
                activity.finish();
            }
        }
    }

    public Activity getTopActivity(){
        com.example.base.utils.LogUtils.showLogI(mActivitys.size() + "");
        if (mActivitys.size() > 0){
            return mActivitys.get(mActivitys.size() - 1);
        }
        return null;
    }

    public Activity getPreviousActivity(){
        com.example.base.utils.LogUtils.showLogI(mActivitys.size() + "");
        if (mActivitys.size() > 1){
            return mActivitys.get(mActivitys.size() - 2);
        }
        return null;
    }

    public void setMenu(int menuResid){
        this.mMenuResid = menuResid;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(mMenuResid, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id){
            case R.id.action_scan:
                Intent intent = new Intent(this, ScanQRActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.action_settings:
                break;
            case R.id.action_quit:
                stopApp();
                clearTask();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE ){

            String result = data.getStringExtra(ScanQRActivity.EXTRA_RESULT);
            LogUtils.showLogI(result);
        }
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void update(String str) {

    }
}
