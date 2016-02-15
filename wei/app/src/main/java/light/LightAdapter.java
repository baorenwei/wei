package light;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.ViewGroup.LayoutParams;

import com.example.administrator.bao.R;
import com.example.base.base.MyBaseAdapter;
import com.example.base.utils.HttpUtils;
import com.example.base.utils.LogUtils;
import com.example.base.utils.LruCacheUtils;
import com.example.base.widget.LineIndicatier;

import java.io.FileNotFoundException;
import java.util.logging.MemoryHandler;

/**
 * Created by Administrator on 2016/1/14.
 */
public class LightAdapter<String> extends MyBaseAdapter implements View.OnClickListener {

    private LayoutInflater mInfalter;
    private Context mContext;
    private Bitmap mBitmap;
    private int mScreentWidth;
    private int mItemPosition;

    LightAdapter(Context mContext, int mScreentWidth) {
        mInfalter = LayoutInflater.from(mContext);
        this.mContext = mContext;
        this.mScreentWidth = mScreentWidth;
    }

    public void setBitmap(Bitmap bitmap) {
        this.mBitmap = bitmap;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.widget_listview_delete_item_layout,null);
            holder = new ViewHolder();

            holder.hSView = (HorizontalScrollView) convertView.findViewById(R.id.hsv);
            holder.action = convertView.findViewById(R.id.ll_action);
            holder.btOne = (Button) convertView.findViewById(R.id.deleteItemButtom);
            holder.btTwo = (Button) convertView.findViewById(R.id.stickItemButtom);
            holder.mLightListViewImageView = (ImageView)convertView.findViewById(R.id.lightListViewImageView);
            // 设置内容view的大小为屏幕宽度,这样按钮就正好被挤出屏幕外
            holder.content = convertView.findViewById(R.id.ll_content);
            LayoutParams lp = holder.content.getLayoutParams();
            lp.width = mScreentWidth;

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        // 把位置放到view中,这样点击事件就可以知道点击的是哪一条item
        holder.btOne.setTag(position);
        holder.btTwo.setTag(position);

        if(mBitmap != null){
            holder.mLightListViewImageView.setImageBitmap(mBitmap);
        }else{
            holder.mLightListViewImageView.setImageResource(R.drawable.ic_yuan);
        }
        // 设置监听事件
        convertView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (v != null) {
                            ViewHolder viewHolder1 = (ViewHolder)v .getTag();
                            viewHolder1.hSView.smoothScrollTo(0, 0);
                            if(v.getTag() == v.getTag()){
                                viewHolder1.hSView.smoothScrollTo(0, 0);
                            }else{
                                viewHolder1.hSView.smoothScrollTo(0, 0);
                            }
                        }
                    case MotionEvent.ACTION_UP:
                        ViewHolder viewHolder = (ViewHolder) v.getTag();
//                        view = v;
                        int scrollX = viewHolder.hSView.getScrollX();
                        int actionW = viewHolder.action.getWidth();

                        // 注意使用smoothScrollTo,这样效果看起来比较圆滑,不生硬
                        // 如果水平方向的移动值<操作区域的长度的一半,就复原
                        if (scrollX < actionW / 1.5) {
                            viewHolder.hSView.smoothScrollTo(0, 0);
                        } else // 否则的话显示操作区域
                        {
                            viewHolder.hSView.smoothScrollTo(actionW, 0);
                        }
                        return true;
                }
                return false;
            }
        });

        // 这里防止删除一条item后,ListView处于操作状态,直接还原
        if (holder.hSView.getScrollX() != 0) {
            holder.hSView.scrollTo(0, 0);
        }

        // 设置监听事件
        holder.btOne.setOnClickListener(this);
        holder.btTwo.setOnClickListener(this);
        return convertView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.deleteItemButtom:
                delete((int)v.getTag());
                break;
            case R.id.stickItemButtom:
                LogUtils.showLogI("点了Buttom2");
                break;
        }
    }

    private void delete(int position){

            mList.remove(position);
        notifyDataSetChanged();
    }

    class ViewHolder {

        public HorizontalScrollView hSView;
        public View content;
        public ImageView mLightListViewImageView;
        public View action;
        public Button btOne;
        public Button btTwo;
    }

}
