package camera;

import android.widget.ListView;

import com.example.administrator.bao.R;
import com.example.base.base.BaseFragmentActivity;
import com.example.base.utils.LogUtils;
import com.videogo.openapi.EZOpenSDK;

/**
 * Created by Administrator on 2016/2/5.
 */
public class CameraListViewActivity extends BaseFragmentActivity{

    private ListView mCameraListView;

    @Override
    protected int initLayout() {
        return R.layout.activity_camera_list_layout;
    }

    @Override
    protected void initView() {
        mCameraListView = (ListView)findViewById(R.id.cameraListView);
    }

    @Override
    protected void initData() {

        LogUtils.showLogI("到了这里");
        //登录
        EZOpenSDK.getInstance().logout();
    }


}
