package activity;

import android.app.LocalActivityManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;

import com.example.administrator.bao.R;
import com.example.base.base.BaseFragmentActivity;
import com.example.base.utils.LogUtils;
import com.example.base.widget.MyLinearLayout;
import com.videogo.constant.Constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapter.MainFragmentViewPagerAdapter;
import camera.CameraBroadcats;
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

    private MyLinearLayout mMainFragmentMessage;
    private MyLinearLayout mMainFragmentGroup;
    private MyLinearLayout mMainFragmentMy;

    private CameraBroadcats mCameraBroadcats;   //萤石广播

    @Override
    protected int initLayout() {
        return R.layout.fragment_main_layout;
    }

    @Override
    protected void initView() {
        mMainFragmentMessage = (MyLinearLayout) findViewById(R.id.mainFragmentMessage);
        mMainFragmentGroup = (MyLinearLayout) findViewById(R.id.mainFragmentGroup);
        mMainFragmentMy = (MyLinearLayout) findViewById(R.id.mainFragmentMy);
        mMainFragmentViewPager = (ViewPager)findViewById(R.id.mainFragmentViewPager);

        mMainFragmentGroup.setImageView(R.drawable.ic_yuan);
        mMainFragmentGroup.setTextView("聊天");

        mMainFragmentMy.setImageView(R.drawable.ic_yuan);
        mMainFragmentMy.setTextView("摄像头");

        mMainFragmentMessage.setImageView(R.drawable.ic_yuan);
        mMainFragmentMessage.setTextView("我的");
    }

    @Override
    protected void initData() {

        setLeftTextView("左边");
        setRightTextView("右边");
        setTitleTextView("小包");


        mMainFragmentGroup.setOnClickListener(this);
        mMainFragmentMy.setOnClickListener(this);
        mMainFragmentMessage.setOnClickListener(this);
        initPagerAdapter();

//        startActivity(new Intent(this, WindowActivity.class));
//
//        new  Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                WindowActivity activity = null;
//                if (activity == null){
//                    activity = new WindowActivity();
//                }
//                activity.finshWindow();
//            }
//        },3000);
//        sendYINGbroad();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(mCameraBroadcats);
    }

    //发送萤石广播
    private void sendYINGbroad(){
        Intent intent = new Intent();
        mCameraBroadcats = new CameraBroadcats();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.OAUTH_SUCCESS_ACTION);
        registerReceiver(mCameraBroadcats,filter);
        mCameraBroadcats.onReceive(this, intent);
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
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.mainFragmentMessage:
                mMainFragmentViewPager.setCurrentItem(0);
                break;
            case R.id.mainFragmentGroup:
                mMainFragmentViewPager.setCurrentItem(1);
                break;
            case R.id.mainFragmentMy:
                mMainFragmentViewPager.setCurrentItem(2);
                break;
            case R.id.rightTextView:
                if (mWindowActivity == null){
                    mWindowActivity = new WindowActivity();
                }
                break;
        }
    }
}