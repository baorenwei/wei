package activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.example.administrator.bao.R;
import com.example.base.base.BaseFragmentActivity;

/**
 * Created by Administrator on 2016/1/29.
 */
public class WindowActivity extends Activity {

    private ImageView mWindowImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_window_layout);
        initView();
        initData();
    }

    private void initView() {

        mWindowImageView = (ImageView)findViewById(R.id.windowImageView);
    }

    private void initData() {

        top();
    }

    public void finshWindow(){
        this.finish();
    }

    public void top() {
        AnimationSet set = new AnimationSet(true);
        TranslateAnimation animation = new TranslateAnimation(0, 0,-110, 30);
        animation.setDuration(800);//设置动画持续时间
        set.addAnimation(animation);
        set.setFillAfter(true);
        mWindowImageView.startAnimation(set);

        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                buttom();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public void buttom() {
        AnimationSet set = new AnimationSet(true);
        TranslateAnimation animation = new TranslateAnimation(0, 0,30, -110);
        animation.setDuration(1500);//设置动画持续时间
        set.addAnimation(animation);
        set.setFillAfter(true);
        mWindowImageView.startAnimation(set);
        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                top();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

}
