package light;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.bao.R;
import com.example.base.base.BaseFragmentActivity;
import com.example.base.utils.FileUtils;
import com.example.base.utils.HttpUtils;
import com.example.base.utils.LogUtils;
import com.example.base.utils.LruCacheUtils;
import com.example.base.utils.StringUtil;
import com.example.base.utils.ThreadPollUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import Interface.LightCallBack;
import door.DoorActivity;
import model.FormFile;

/**
 * Created by Administrator on 2016/1/10.
 */
public class LightActivity extends BaseFragmentActivity {
    private  TextView mTextView;
    private ContentResolver mResolver;
    private LigthSQLiteOpenHelper dBlite;
    private Uri mLightUri;
    Button commit;
    Button button;
    RelativeLayout mLinearLayout;
    TextView textView;

    List<String> mList;
    PullToRefreshListView mListView;
    LightAdapter mAdapter;
    Bitmap bitmap;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    mAdapter.notifyDataSetChanged();
            }
        }
    };

    private ContentObserver mObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            com.example.base.utils.LogUtils.showLogI("收到");
        }
    };

    @Override
    protected int initLayout() {

        return R.layout.activity_light_layout;
    }

    @Override
    protected void initView() {
        textView = (TextView) findViewById(R.id.textView);
        commit = (Button) findViewById(R.id.commit);
        button = (Button) findViewById(R.id.button);
        mListView = (PullToRefreshListView) findViewById(R.id.listView);
        mLinearLayout = (RelativeLayout) findViewById(R.id.linearLayout);

    }

    @Override
    protected void initData() {

        ThreadPollUtils.addCachedThreadPoll(new LightActivity());
        commit.setOnClickListener(this);
        button.setOnClickListener(this);
        //初始化ListView数据
        mAdapter = new LightAdapter<String>(mContext,getDisplayMetrics(1));
        mList = new ArrayList<>();
        for (int i = 0; i < LightImage.imageThumbUrls.length; i++) {
            mList.add(i + "");
        }
        mAdapter.setList(mList);
        mListView.getRefreshableView();
        mListView.setAdapter(mAdapter);
        mListView.setOnRefreshListener(this);
        mListView.setMode(PullToRefreshBase.Mode.BOTH);  //下拉和上拉都会执行onRefresh()中的方法了。
        mListView.setOnScrollListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
//        getMetaData();
    }

    @Override
    public void run() {
        super.run();
        getJsonData();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.button:
                break;
        }
    }

    @Override
    public void onRefresh(PullToRefreshBase refreshView) {
        super.onRefresh(refreshView);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mListView.isRefreshing()) {
                    mListView.onRefreshComplete();
                }
            }
        }, 1000);
    }



    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        super.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);

        int visiItemCount = visibleItemCount;
        int index = firstVisibleItem;

        for (int i = 0; i < visiItemCount; i++){
            if (index == LightImage.imageThumbUrls.length){
                break;
            }
            try {
                HttpUtils.downLoadImage(index, mContext);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Bitmap bit = LruCacheUtils.getInstance().getBitmapFromMemoryCache(LightImage.imageThumbUrls[index]);
            mAdapter.setBitmap(bit);
            index ++;
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        super.onScrollStateChanged(view, scrollState);
        int y =  mListView.getHeight();
        int x =  mListView.getScrollX();
    }

    //得到数据库数据
    private void getMetaData() {
        String userName = null;
        String sexs = null;
        String email = null;
        String date = null;
        mResolver = mContext.getContentResolver();
        Cursor c = mResolver.query(LightMeteDate.CONTENT_URI, null, LightMeteDate._ID + "=?", new String[]{"1"}, null);
        if (c.moveToFirst()) {
            userName = c.getString(c.getColumnIndex(LightMeteDate.USERNAME));
            sexs = c.getString(c.getColumnIndex(LightMeteDate.SEX));
            email = c.getString(c.getColumnIndex(LightMeteDate.EMAIL));
            date = c.getString(c.getColumnIndex(LightMeteDate.DATE));
        }
        c.close();
        textView.setText("userName--" + userName + "--sexs--" + sexs + "--email--" + email + "--date--" + date);

    }

    //得到解析后的Json数据
    private void getJsonData() {
        LightDao.getJsonData(mContext, new LightCallBack() {
            @Override
            public void complete(LightBen ben) {
//                textView.setText("userName--" + ben.getUserName() + "--sexs--" + ben.getSex() + "--email--" + ben.getEmail() + "--date--" + ben.getData());
            }

            @Override
            public void exception(Exception exception) {

            }
        });
    }
}
