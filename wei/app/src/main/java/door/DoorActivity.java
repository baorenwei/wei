package door;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.bao.R;
import com.example.base.base.BaseFragmentActivity;
import com.example.base.utils.LogUtils;

import light.LightBen;
import light.LightDao;
import light.LightMeteDate;
import light.LigthSQLiteOpenHelper;

/**
 * Created by Administrator on 2016/1/10.
 */
public class DoorActivity extends BaseFragmentActivity{
    private TextView mTextView;
    ImageView mInageView;
    private ContentResolver mResolver;
    private LigthSQLiteOpenHelper dBlite;
    private Uri mLightUri;
    Button stop;

    TextView mDoorTextView;

    private ContentObserver mObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            LogUtils.showLogI("收到");
        }
    };

    @Override
    protected int initLayout() {
        return R.layout.activity_door_layout;
    }

    @Override
    protected void initView() {
        mDoorTextView = (TextView)findViewById(R.id.doorTextView);
        stop = (Button)findViewById(R.id.stop);
    }
    @Override
    protected void initData() {

        getJsonData();
        stop.setOnClickListener(this);
    }

    private void getJsonData(){
        String userName = null;
        String sexs = null;
        String email = null;
        String date = null;
        mResolver = mContext.getContentResolver();
        Cursor c = mResolver.query(DoorMeteDate.CONTENT_URI, null, DoorMeteDate._ID + "=?", new String[]{"1"}, null);
        LogUtils.showLogI("到了这里");
        if (c.moveToFirst()){
            userName = c.getString(c.getColumnIndex(DoorMeteDate.USERNAME));
            sexs = c.getString(c.getColumnIndex(DoorMeteDate.SEX));
            email = c.getString(c.getColumnIndex(DoorMeteDate.EMAIL));
            date = c.getString(c.getColumnIndex(DoorMeteDate.DATE));
        }
        c.close();
        mDoorTextView.setText("userName--"+userName+"--sexs--"+sexs+"--email--"+email+"--date--"+date);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mResolver.unregisterContentObserver(mObserver);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch(v.getId()){
            case R.id.stop:
                DoorBen ben = DoorDao.getJsonData(mContext, null, null);
                mDoorTextView.setText("userName--"+ben.getUserName()+"--sexs--"+ben.getSex()+"--email--"+ben.getEmail()+"--date--"+ben.getData());
                break;
        }
    }
}
