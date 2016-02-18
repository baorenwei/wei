package com.example.base.base;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.RadioGroup;
import android.widget.RemoteViews;

import com.example.administrator.bao.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

import activity.WindowActivity;
import activity.WindowListView;
import cn.smssdk.gui.GroupListView;

/**
 * Created by Administrator on 2016/1/2.
 *
 */
public abstract class BaseFragmentActivity extends BaseActivity implements View.OnClickListener ,
        View.OnLongClickListener,AdapterView.OnItemSelectedListener,AdapterView.OnItemClickListener,Runnable,
        AbsListView.OnScrollListener, PullToRefreshBase.OnRefreshListener,RadioGroup.OnCheckedChangeListener, View.OnKeyListener {

    private SharedPreferences.Editor mEditor;
    private SharedPreferences mSharedPreferences;
    private ProgressDialog mDialog;

    public String LOGIN = "1";
    public String UN_LOGIN = "2";
    public WindowActivity mWindowActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initLayout());

        initView();
        initData();
    }

    protected abstract int initLayout();

    protected abstract void initData();

    protected abstract void initView();

    protected  void initSharedPreferences(String spfName){

        mSharedPreferences = getSharedPreferences(spfName, mContext.MODE_APPEND);
        mEditor = mSharedPreferences.edit();
    };

    public boolean pullDataEditor(String key ,String value){
        mEditor.putString(key, value);
        mEditor.commit();
        return true;
    }
    //
    public String getDataEditor(String key){

        String value = mSharedPreferences.getString(key,null);
        return value;
    }

    public void clearEditorData(){
        mEditor.clear();
        mEditor.commit();
    }
    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void run() {

    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    @Override
    public void onRefresh(PullToRefreshBase refreshView) {


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        return false;
    }


    //设置Notifiction
    public void setmNotifiction(Context context, Class<?> cls,int drawable,String title,int layout,int image,String content,String time){

        NotificationManager mNotificationManager = (NotificationManager)mContext.getSystemService(mContext.NOTIFICATION_SERVICE);
        Intent intent = new Intent(this,cls);
        PendingIntent mPendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        Notification notification = new Notification();
        long[] vir = {0,100,300,100};
        notification.vibrate = vir;
        notification.icon = drawable;
        notification.tickerText = title;

        RemoteViews contentView = new RemoteViews(getPackageName(), layout);
        contentView.setImageViewResource(R.id.noticationImageView, image);
        contentView.setTextViewText(R.id.noticationContentTextView, content);
        contentView.setTextViewText(R.id.noticationTimeTextView, time);
        notification.contentView = contentView;

        notification.contentIntent = mPendingIntent;
        mNotificationManager.notify(3, notification);
    }

    //设置ProgressDialog
    public void showDialog(){
        if (mDialog == null){
            mDialog = new ProgressDialog(this);
        }
        mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mDialog.setMessage(getResources().getString(R.string.in_the_effort));
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
    }
    public void dismissDialog(){
        mDialog.dismiss();
    }

}
