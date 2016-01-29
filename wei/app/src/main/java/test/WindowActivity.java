package test;

import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.example.administrator.bao.R;
import com.example.base.base.BaseFragmentActivity;

/**
 * Created by Administrator on 2016/1/29.
 */
public class WindowActivity extends BaseFragmentActivity {

    private ImageView mWindowImageView;
    @Override
    protected int initLayout() {
        return R.layout.activity_window_layout;
    }

    @Override
    protected void initView() {

        mWindowImageView = (ImageView)findViewById(R.id.windowImageView);
    }

    @Override
    protected void initData() {

        Log.i("TAG","------------------"+mWindowImageView.getY());
        visibleListView();
    }

    //显示ListView
    public void visibleListView() {
        AnimationSet set = new AnimationSet(true);
        TranslateAnimation animation = new TranslateAnimation(0, 0,-150, 0);
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

                gomeListView();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    //隐藏ListView
    public void gomeListView() {
        AnimationSet set = new AnimationSet(true);
        TranslateAnimation animation = new TranslateAnimation(0, 0,0, -150);
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

                visibleListView();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

}
