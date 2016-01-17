package test;

import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Button;

import com.example.administrator.bao.R;
import com.example.base.base.BaseFragmentActivity;
import com.example.base.utils.ConcreteWatched;
import com.example.base.utils.LogUtils;
import com.example.base.utils.ThreadPollUtils;
import com.example.base.weight.ZoomImageView;

import java.util.ArrayList;
import java.util.List;

import Interface.Watched;
import Interface.Watcher;
import light.LightActivity;

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
        mZoomImageView = (ZoomImageView)findViewById(R.id.zoomImageView);
        mZoomImageView.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources(),R.drawable.ic));
    }

    @Override
    public void run() {
        super.run();
        LogUtils.showLogI("aaa");
    }

    private void add(){
        while(true){

        }
    }
}
