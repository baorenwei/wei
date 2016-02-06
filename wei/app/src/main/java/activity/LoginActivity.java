package activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.bao.R;
import com.example.base.base.BaseApplication;
import com.example.base.base.BaseFragmentActivity;
import com.example.base.utils.LogUtils;

/**
 * Created by Administrator on 2016/1/10.
 */
public class LoginActivity extends BaseFragmentActivity {

    private TextView mForgetPassTextView;
    private TextView mRegistTextView;
    public static final int REGIST_CODE = 1;
    public static final int FORGET_PASS_CODE = 2;

    @Override
    protected int initLayout() {
        return R.layout.activity_login_layout;
    }

    @Override
    protected void initView() {
        mForgetPassTextView = (TextView)findViewById(R.id.forgetTextView);
        mRegistTextView = (TextView)findViewById(R.id.registTextView);
    }

    @Override
    protected void initData() {

        mRegistTextView.setOnClickListener(this);
        mForgetPassTextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.forgetTextView:
                Intent intent1 = new Intent(this,RegistActivity.class);
                intent1.setFlags(FORGET_PASS_CODE);
                startActivity(intent1);
                break;
            case R.id.registTextView:
                Intent intent2 = new Intent(this,RegistActivity.class);
                intent2.setFlags(FORGET_PASS_CODE);
                startActivity(intent2);
                break;
        }
    }
    private void login(String userName,String passWord){

    }
}
