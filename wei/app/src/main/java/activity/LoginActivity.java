package activity;

import android.content.Intent;
import android.util.Log;

import com.example.administrator.bao.R;
import com.example.base.base.BaseApplication;
import com.example.base.base.BaseFragmentActivity;
import com.example.base.utils.LogUtils;

/**
 * Created by Administrator on 2016/1/10.
 */
public class LoginActivity extends BaseFragmentActivity {

    String token1 = "NsLjBMERXwycb4O9yn7BAeDIPZ5RCX+GBErAV4swq8eegy8wB6rEhXmsK5/XoqFIMlnHQ6TSfAU=";
    String token2 = "UQa4JIIr3E4SCU4r+ZP3lwNfdRjhq7Pfo7eu1jOJfbApmQBy+iibMfr2nNDLJCrIiESQ99sdBxqsoo9oJN5/lw==";
    @Override
    protected int initLayout() {
        return R.layout.activity_login_layout;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
    }

}
