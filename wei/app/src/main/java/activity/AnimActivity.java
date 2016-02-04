package activity;

import android.content.Intent;
import android.os.Handler;

import com.example.administrator.bao.R;
import com.example.base.base.BaseFragmentActivity;

/**
 * Created by Administrator on 2016/2/3.
 */
public class AnimActivity extends BaseFragmentActivity {
    @Override
    protected int initLayout() {
        return R.layout.anim_activity_layout;
    }

    @Override
    protected void initData() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(AnimActivity.this,LoginActivity.class));
            }
        },2000);
    }

    @Override
    protected void initView() {

    }
}
