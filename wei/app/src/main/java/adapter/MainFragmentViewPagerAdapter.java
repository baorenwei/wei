package adapter;

import android.view.View;
import android.view.ViewGroup;

import com.example.base.base.BasePageAdapter;
import com.example.base.base.MyBaseAdapter;

/**
 * Created by Administrator on 2016/1/30.
 */
public class MainFragmentViewPagerAdapter extends BasePageAdapter {
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mList.get(position % mList.size());
        // 在添加到ViewGroup中
        container.addView(view);
        return view;    }
}
