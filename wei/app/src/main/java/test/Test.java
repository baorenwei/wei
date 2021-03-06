package test;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.administrator.bao.R;
import com.example.base.base.BaseFragmentActivity;
import com.example.base.utils.UpdateInfoServiceUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import activity.WindowActivity;
import model.UpdateInfo;

/**
 * Created by Administrator on 2016/1/10.
 */
public class Test extends BaseFragmentActivity {

    private Button mButton;
    public ListView mActionBarListView;
    public boolean mIsShow = false;

    @Override
    protected int initLayout() {
        return R.layout.activity_login_layout;
    }

    @Override
    protected void initView() {
//        mZoomImageView.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources(),R.drawable.ic));
//        HttpUtils mUtils = new HttpUtils();
//        try {
//            Bitmap bitmap = mUtils.downLoadImage("http://img.my.csdn.net/uploads/201309/01/1378037095_7515.jpg", mContext);
//            mZoomImageView.setImageBitmap(bitmap);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    protected void initData() {
//        setRightTextView("菜单");
//        addListViewItem();
         startActivity(new Intent(mContext, WindowActivity.class));
    }

    private void addListViewItem() {
        String[] titles = {"title1", "title2", "title3", "title4", "title5", "title6"};
        int[] drawableIds = {R.drawable.ic_yuan, R.drawable.ic_yuan, R.drawable.ic_yuan,
                R.drawable.ic_yuan, R.drawable.ic_yuan, R.drawable.ic_yuan};

        setListViewItem(drawableIds, titles);
    }

    //    设置ListView Item的数据
    public void setListViewItem(int[] drawable, String[] titles) {
        List<Map<String, Object>> mListViewList = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < titles.length; i++) {
            map.put("title", titles[i]);
            map.put("drawable", drawable[i]);
            mListViewList.add(map);
        }
//        mActionBarListView = (ListView) findViewById(R.id.actionBarListView);
        SimpleAdapter adapter = new SimpleAdapter(this, mListViewList, R.layout.widget_action_bar_listview_item,
                new String[]{"title", "drawable"},
                new int[]{R.id.actionBarTextView, R.id.actionBarImageView});
        mActionBarListView.setAdapter(adapter);
        mActionBarListView.setOnItemClickListener(this);
    }

    //显示ListView
    public void visibleListView() {
        AnimationSet set = new AnimationSet(true);
        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 0.1f, 0, 0.1f, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0);
        scaleAnimation.setDuration(10);
        set.addAnimation(scaleAnimation);
        mActionBarListView.startAnimation(set);
    }

    //隐藏ListView
    public void gomeListView() {
        AnimationSet set = new AnimationSet(true);
        ScaleAnimation scaleAnimation = new ScaleAnimation(1f, 0, 1f, 0, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0);
        scaleAnimation.setDuration(500);
        set.addAnimation(scaleAnimation);
        set.setFillAfter(true);
        mActionBarListView.startAnimation(set);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.rightTextView:
                if (mIsShow == true) {
                    gomeListView();
                    mIsShow = false;
                } else {
                    mActionBarListView.setVisibility(View.VISIBLE);
                    visibleListView();
                    mIsShow = true;
                }
                break;
        }
    }
}
