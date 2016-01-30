package activity;

import android.app.LocalActivityManager;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.administrator.bao.R;
import com.example.base.base.BaseFragmentActivity;
import com.example.base.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import adapter.MainFragmentViewPagerAdapter;
import fragment.FirstFragment;
import fragment.SecondFragment;
import fragment.ThredFragment;

/**
 * Created by Administrator on 2016/1/6.
 */
public class MainFragmentActivity extends BaseFragmentActivity {

    private String[] tagList = { "one", "two", "thread"};
    private ViewPager mMainFragmentViewPager;
    private ArrayList<View> mlistview = null;
    private MainFragmentViewPagerAdapter mViewAdapter;


    private RadioGroup mMainFragmentRadioGroup;
    private RadioButton mMainFragmentMessageRadioButton;
    private RadioButton mMainFragmentContactRadioButton;
    private RadioButton mMainFragmentDynamicRadioButton;


    @Override
    protected int initLayout() {
        return R.layout.fragment_main_layout;
    }


    @Override
    protected void initView() {
        mMainFragmentRadioGroup = (RadioGroup) findViewById(R.id.mainFragmentRadioGroup);
        mMainFragmentMessageRadioButton = (RadioButton) findViewById(R.id.mainFragmentMessageRadioButton);
        mMainFragmentContactRadioButton = (RadioButton) findViewById(R.id.mainFragmentContactRadioButton);
        mMainFragmentDynamicRadioButton = (RadioButton) findViewById(R.id.mainFragmentDynamicRadioButton);
        mMainFragmentViewPager = (ViewPager)findViewById(R.id.mainFragmentViewPager);
    }

    @Override
    protected void initData() {
        mMainFragmentRadioGroup.setOnCheckedChangeListener(this);
        setLeftTextView("左边");
        setRightTextView("右边");
        setTitleTextView("小包");
        initPagerAdapter();

    }

    private void initPagerAdapter() {
        // TODO Auto-generated method stub
        mlistview = new ArrayList<View>(); // viewpager中的内容
        tagList = new String[5];
        Intent intent1 = new Intent(mContext, FirstFragment.class);
        View v1 = getView(tagList[0], intent1);
        mlistview.add(v1);

        Intent intent2 = new Intent(mContext, SecondFragment.class);
        View v2 = getView(tagList[1], intent2);
        mlistview.add(v2);

        Intent intent3 = new Intent(mContext, ThredFragment.class);
        View v3 = getView(tagList[2], intent3);
        mlistview.add(v3);
        mViewAdapter = new MainFragmentViewPagerAdapter();
        mViewAdapter.setListData(mlistview);
        mMainFragmentViewPager.setAdapter(mViewAdapter);
        mMainFragmentViewPager.setCurrentItem(0);
    }

    private View getView(String string, Intent intent) {
        Window window = mactivityManager.startActivity(string, intent);
        View view = window.getDecorView();
        return view;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        super.onCheckedChanged(group, checkedId);
        if (checkedId == mMainFragmentMessageRadioButton.getId()) {
            mMainFragmentViewPager.setCurrentItem(0);
        } else if (checkedId == mMainFragmentContactRadioButton.getId()) {
            mMainFragmentViewPager.setCurrentItem(1);
        } else if (checkedId == mMainFragmentDynamicRadioButton.getId()) {
            mMainFragmentViewPager.setCurrentItem(2);
            LogUtils.showLogI("点了");
        }
    }
}