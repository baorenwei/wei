package light;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.bao.R;
import com.example.base.base.BaseFragmentActivity;
import com.example.base.utils.FileUtils;
import com.example.base.utils.HttpUtils;
import com.example.base.utils.LogUtils;
import com.example.base.utils.StringUtil;
import com.example.base.utils.ThreadPollUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Interface.LightCallBack;
import door.DoorActivity;

/**
 * Created by Administrator on 2016/1/10.
 */
public class LightActivity extends BaseFragmentActivity {
    private TextView mTextView;
    ImageView mInageView;
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

        mAdapter = new LightAdapter<String>(mContext);
        mList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mList.add(i + "");
        }
        mAdapter.setList(mList);
        mListView.setAdapter(mAdapter);
        mListView.setOnRefreshListener(this);
        mListView.setMode(PullToRefreshBase.Mode.BOTH);  //下拉和上拉都会执行onRefresh()中的方法了。
    }

    @Override
    protected void initData() {

        ThreadPollUtils.addCachedThreadPoll(new LightActivity());
        commit.setOnClickListener(this);
        button.setOnClickListener(this);
        mListView.setOnScrollListener(this);
        getMetaData();

    }


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

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        mResolver.unregisterContentObserver(mObserver);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.commit:
//                textView.setText("userName--"+ben.getUserName()+"--sexs--"+ben.getSex()+"--email--"+ben.getEmail()+"--date--"+ben.getData());
                break;
            case R.id.button:
                Intent intent = new Intent(mContext, DoorActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void getJsonData() {
        new LightDao().getJsonData(mContext, new LightCallBack() {
            @Override
            public void complete(LightBen ben) {
//                textView.setText("userName--"+ben.getUserName()+"--sexs--"+ben.getSex()+"--email--"+ben.getEmail()+"--date--"+ben.getData());
            }

            @Override
            public void exception(Exception exception) {

            }
        });
    }

    @Override
    public void run() {
        super.run();
//        getJsonData();
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        super.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);

//        this.bitmap = HttpUtils.downLoadImage(firstVisibleItem,mContext);
    }

    @Override
    public void onRefresh(PullToRefreshBase refreshView) {
        super.onRefresh(refreshView);

        if (!mListView.isRefreshing()) {
            new Handler().postAtTime(new Runnable() {
                @Override
                public void run() {
                    mListView.onRefreshComplete();
                }
            }, 1500);
        }

        if (refreshView.isHeaderShown()) {
            Toast.makeText(mContext, "下拉刷新", Toast.LENGTH_SHORT).show();
            //下拉刷新 业务代码
        } else {
            Toast.makeText(mContext, "上拉加载更多", Toast.LENGTH_SHORT).show();
            //上拉加载更多 业务代码
        }

    }
}
