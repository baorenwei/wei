package baiduMap;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.MapView;
import com.example.administrator.bao.R;
import com.example.base.base.BaseFragmentActivity;

/**
 * Created by Administrator on 2016/2/17.
 */
public class MapActivity extends BaseFragmentActivity {

    MapView mMapView = null;
    @Override
    protected int initLayout() {
        return R.layout.activity_baidu_layout;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mMapView = (MapView) findViewById(R.id.bmapView);
    }
}
