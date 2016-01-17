package com.example.base.base;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;

import cn.smssdk.gui.GroupListView;

/**
 * Created by Administrator on 2016/1/2.
 *
 */
public abstract class BaseFragmentActivity extends com.example.base.base.BaseActivity implements View.OnClickListener ,
        View.OnLongClickListener,AdapterView.OnItemSelectedListener,GroupListView.OnItemClickListener,Runnable,AbsListView.OnScrollListener, PullToRefreshBase.OnRefreshListener  {

    private SharedPreferences.Editor mEditor;
    private SharedPreferences mSharedPreferences;


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
    public void onItemClick(GroupListView parent, View view, int group, int position) {

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
}
