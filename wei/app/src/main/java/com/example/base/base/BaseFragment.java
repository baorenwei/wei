package com.example.base.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/1/4.
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener,
        View.OnLongClickListener,AdapterView.OnItemSelectedListener {

    public View mLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mLayout = inflater.inflate(layoutResID(), null);
        initView();
        initData();
        return mLayout;
    }

    protected abstract void initData();

    protected abstract void initView();

    protected abstract int layoutResID();

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

}
