package test;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.widget.Button;

import com.example.administrator.bao.R;
import com.example.base.base.BaseFragmentActivity;
import com.example.base.utils.FileUtils;
import com.example.base.utils.HttpUtils;
import com.example.base.utils.LogUtils;
import com.example.base.widget.ZoomImageView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Interface.Watcher;

/**
 * Created by Administrator on 2016/1/10.
 */
public class Test extends BaseFragmentActivity{

    private List<Watcher> list = new ArrayList<>();
    private Button mButton;
    private ZoomImageView mZoomImageView;

    @Override
    protected int initLayout() {
        return R.layout.activity_login_layout;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
//        mZoomImageView.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources(),R.drawable.ic));
        HttpUtils mUtils = new HttpUtils();
        try {
            Bitmap bitmap = mUtils.downLoadImage("http://img.my.csdn.net/uploads/201309/01/1378037095_7515.jpg", mContext);
//            mZoomImageView.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static boolean hasSdcard() {
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void run() {
        super.run();
        LogUtils.showLogI("aaa");

    }
}
