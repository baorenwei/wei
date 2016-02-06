package camera;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.base.base.BaseBroadcastReceiver;
import com.videogo.openapi.EZOpenSDK;
import com.videogo.openapi.bean.EZAccessToken;

/**
 * Created by Administrator on 2016/2/6.
 */
public class CameraBroadcats extends BaseBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent toIntent = new Intent(context, CameraListViewActivity.class);
        toIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //获取登录token
        // 包含2个字段，一个是token string本身
        //另外一个表示过期时间，单位为秒，获取到的值一般为 604800 秒，即7天。
        //该函数一般在收到登陆成功的广播OAUTH_SUCCESS_ACTION 后调用。
        EZAccessToken token = EZOpenSDK.getInstance().getEZAccessToken();
        //保存token，获取超时时间，在token过期时重新获取
        CameraInterfaceUtils.baoCunTokenAndTime(token);
        context.startActivity(toIntent); //启动camera list

    }
}
