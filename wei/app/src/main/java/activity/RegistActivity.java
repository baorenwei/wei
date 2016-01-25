package activity;

import android.widget.TextView;

import com.example.administrator.bao.R;
import com.example.base.base.BaseFragmentActivity;
import com.example.base.utils.LogUtils;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by Administrator on 2016/1/10.
 */
public class RegistActivity extends BaseFragmentActivity {

    private static  final boolean DEBUG = true;
    private TextView mResistCodeTextView;

    private EventHandler mSMSEventHandler = new EventHandler(){
        @Override
        public void afterEvent(int event, int result,final Object data) {
            switch (event){
                case SMSSDK.EVENT_GET_VERIFICATION_CODE:
                    LogUtils.showLogI("到了这里1");
                    break;
                case SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE:
                    LogUtils.showLogI("到了这里2");
                    break;
                case SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES:
                    LogUtils.showLogI("到了这里3");
                    break;
            }
        }
    };

    @Override
    protected int initLayout() {
        return R.layout.widget_light_adapter_layout;
    }

    protected void initView() {

//        mResistCodeTextView = (TextView)findViewById(R.id.resistCodeTextView);
    }

    protected void initData() {
        initSmssdkData();
    }

    private void initSmssdkData() {

        SMSSDK.getVerificationCode("86", "13316584270");
        //短信返回的结果
        SMSSDK.registerEventHandler(mSMSEventHandler);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
    }

}
