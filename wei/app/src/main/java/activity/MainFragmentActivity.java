package activity;

import android.app.AlertDialog;
import android.app.LocalActivityManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.administrator.bao.R;
import com.example.base.base.BaseFragmentActivity;
import com.example.base.utils.LogUtils;
import com.example.base.utils.QueueUtils;
import com.example.base.widget.MyLinearLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapter.ActionBarListViewAdapter;
import adapter.MainFragmentViewPagerAdapter;
import camera.CameraBroadcats;
import fragment.FirstFragment;
import fragment.SecondFragment;
import fragment.ThredFragment;

/**
 * Created by Administrator on 2016/1/6.
 */
public class MainFragmentActivity extends BaseFragmentActivity implements View.OnTouchListener{

    private String[] tagList = { "one", "two", "thread"};
    private ViewPager mMainFragmentViewPager;
    private ArrayList<View> mlistview = null;
    private MainFragmentViewPagerAdapter mViewAdapter;

    private MyLinearLayout mMainFragmentMessage;
    private MyLinearLayout mMainFragmentGroup;
    private MyLinearLayout mMainFragmentMy;

    private CameraBroadcats mCameraBroadcats;   //萤石广播
    private ListView mActionBarListView;

    Boolean mIsShow = false;  //ListView是否显示
    private WindowManager.LayoutParams lp;   //窗口变暗

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
        mActionBarListView = (ListView)findViewById(R.id.actbarListView);

    }

    @Override
    protected void initData() {

        setLeftTextView("左边");
        setRightTextView("右边");
        setTitleTextView("小包");

        mMainFragmentGroup.setOnClickListener(this);
        mMainFragmentMy.setOnClickListener(this);
        mMainFragmentMessage.setOnClickListener(this);

        //动态设置ActionBarListView的宽
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(getDisplayMetrics(1)/3, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        params.setMargins(0, 15, 20, 0);
        mActionBarListView.setLayoutParams(params);

        initPagerAdapter();
        addListViewItem();

        QueueUtils.soft();

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
//        filter.addAction(Constant.OAUTH_SUCCESS_ACTION);
        registerReceiver(mCameraBroadcats, filter);
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

                if(mIsShow == false){
                    visibleListView();
                    mIsShow = true;
                }else{
                    gomeListView();
                    mIsShow = false;
                }
                break;
        }
    }

    private void addListViewItem() {

        ActionBarListViewAdapter mAdapter = new ActionBarListViewAdapter(mContext);
        List<Integer> mListViewList = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            mListViewList.add(i);
        }
        mAdapter.setList(mListViewList);
        mActionBarListView.setAdapter(mAdapter);
        mActionBarListView.setOnItemClickListener(this);
    }

    //显示ListView
    public void visibleListView() {
        AnimationSet set = new AnimationSet(true);
        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 0.1f, 0, 0.1f, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0);
        scaleAnimation.setDuration(10);
        set.addAnimation(scaleAnimation);
        mActionBarListView.setVisibility(View.VISIBLE);
        mActionBarListView.startAnimation(set);
    }

    //隐藏ListView
    public  void gomeListView() {
        AnimationSet set = new AnimationSet(true);
        ScaleAnimation scaleAnimation = new ScaleAnimation(1f, 0, 1f, 0, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0);
        scaleAnimation.setDuration(500);
        set.addAnimation(scaleAnimation);
        set.setFillAfter(true);
        mActionBarListView.startAnimation(set);
        mActionBarListView.setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (position == 0){
            gomeListView();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_POINTER_DOWN:
                LogUtils.showLogI("点了");
                break;
        }
        return true;
    }

    public static void getListViewItem(int item){
        switch (item){
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
        }
    }
}