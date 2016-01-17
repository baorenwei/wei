package com.example.base.base;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.bao.R;
import com.example.base.utils.LogUtils;
import com.example.base.utils.NetUtils;

import java.util.ArrayList;
import java.util.List;

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

    private final static List<Activity> mActivitys = new ArrayList<Activity>();
    public com.example.base.utils.InnUtils<Activity> mListActivity = new com.example.base.utils.InnUtils<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_layout);
        mContext = getApplication();

        mActivitys.add(this);
        setupActionBar(R.layout.action_bar);
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

//    public void setMenu(int menuResid){
//        this.mMenuResid = menuResid;
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        getMenuInflater().inflate(0, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id){
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
    public void onClick(View v) {

    }

    @Override
    public void update(String str) {

    }
}