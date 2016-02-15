package activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.administrator.bao.R;
import com.example.base.utils.LogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/1/29.
 */
public class WindowListView extends Activity implements OnItemClickListener{

    private ListView mActionBarListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_window_listview_layout);
        initView();
        initData();
    }

    private void initView() {

//        mActionBarListView = (ListView)findViewById(R.id.actbarListView);
    }

    private void initData() {

        addListViewItem();
    }

    private void addListViewItem() {
        String[] titles = {"title1", "title2", "title3", "title4", "title5", "title6"};
        int[] drawableIds = {R.drawable.ic_yuan, R.drawable.ic_yuan, R.drawable.ic_yuan,
                R.drawable.ic_yuan, R.drawable.ic_yuan, R.drawable.ic_yuan};

        setListViewItem(drawableIds, titles);
    }

    //    设置ListView Item的数据
    public void setListViewItem(int[] drawable, String[] titles) {
        List<Map<String, Object>> mListViewList = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < titles.length; i++) {
            map.put("title", titles[i]);
            map.put("drawable", drawable[i]);
            mListViewList.add(map);
        }
        SimpleAdapter adapter = new SimpleAdapter(this, mListViewList, R.layout.widget_action_bar_listview_item,
                new String[]{"title", "drawable"},
                new int[]{R.id.actionBarTextView, R.id.actionBarImageView});
        mActionBarListView.setAdapter(adapter);
        mActionBarListView.setOnItemClickListener(WindowListView.this);
    }

    //显示ListView
    public void visibleListView() {
        AnimationSet set = new AnimationSet(true);
        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 0.1f, 0, 0.1f, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0);
        scaleAnimation.setDuration(10);
        set.addAnimation(scaleAnimation);
        mActionBarListView.startAnimation(set);
    }

    //隐藏ListView
    public void gomeListView() {
        AnimationSet set = new AnimationSet(true);
        ScaleAnimation scaleAnimation = new ScaleAnimation(1f, 0, 1f, 0, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0);
        scaleAnimation.setDuration(500);
        set.addAnimation(scaleAnimation);
        set.setFillAfter(true);
        mActionBarListView.startAnimation(set);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        LogUtils.showLogI("点了"+position);
    }
}
