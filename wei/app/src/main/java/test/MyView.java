package test;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.example.base.utils.LogUtils;

/**
 * Created by Administrator on 2016/1/20.
 */
public class MyView extends ViewGroup {

    private static final int painding = 20;

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        LogUtils.showLogI("changed:" + changed + "--" + "l:" + l + "--" + "t:" + t + "--" + "r:" + r + "--" + "b:" + b);
        for (int i = 0, size = getChildCount(); i < size; i++) {
            View view = getChildAt(i);
            view.layout(l+10, t+10, l + 100, t + 100);
            l += 100 + painding;
        }
    }
}
