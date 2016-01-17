package com.example.base.base;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/1/6.
 */
public abstract class BasePagerAdapter extends PagerAdapter {

    public ArrayList<View> mList = null;

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mList.size() == 0 ? 0 : mList.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        // TODO Auto-generated method stub
        return arg0 == arg1;
    }

    //
    @Override
    public void destroyItem(ViewGroup v, int position, Object object) {
        // TODO Auto-generated method stub
        v.removeView(mList.get(position % mList.size())); // 移除页面
    }

    @Override
    public abstract Object instantiateItem(ViewGroup container, int position);
}
