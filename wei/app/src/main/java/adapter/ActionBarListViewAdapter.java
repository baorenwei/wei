package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.administrator.bao.R;
import com.example.base.base.MyBaseAdapter;
import com.example.base.utils.LogUtils;

import activity.MainFragmentActivity;

/**
 * Created by Administrator on 2016/2/16.
 */
public class ActionBarListViewAdapter extends MyBaseAdapter {

    private Context mContext;

    public ActionBarListViewAdapter( Context mContext){
        this.mContext = mContext;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View layout = LayoutInflater.from(mContext).inflate(R.layout.widget_action_bar_listview_item,null);
        RelativeLayout one = (RelativeLayout)layout.findViewById(R.id.action_one);
        RelativeLayout two = (RelativeLayout)layout.findViewById(R.id.action_two);
        RelativeLayout three = (RelativeLayout)layout.findViewById(R.id.action_three);

        one.setOnClickListener(new MyOnClickListener());
        two.setOnClickListener(new MyOnClickListener());
        three.setOnClickListener(new MyOnClickListener());
        return layout;
    }

    class MyOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.action_one:
                    MainFragmentActivity.getListViewItem(1);
                    break;
                case R.id.action_two:
                    MainFragmentActivity.getListViewItem(2);
                    break;
                case R.id.action_three:
                    MainFragmentActivity.getListViewItem(3);
                    break;
            }
        }
    }
}
