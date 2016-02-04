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
public class LightAdapter<String> extends MyBaseAdapter implements View.OnClickListener{

    private LayoutInflater mInfalter;
    private Context mContext;
    private Bitmap mBitmap;
    private int mScreentWidth;
    int mFirstButtomWidth;
    int ViewWidth;

    LightAdapter(Context mContext,int mScreentWidth) {
        mInfalter = LayoutInflater.from(mContext);
        this.mContext = mContext;
        this.mScreentWidth = mScreentWidth;
    }
    public  void setBitmap(Bitmap bitmap){
        this.mBitmap = bitmap;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder mHolder = null;
        final int posi = position;
        if (convertView == null) {
            mHolder = new ViewHolder();
            convertView = mInfalter.inflate(R.layout.widget_listview_delete_item_layout, null);
            mHolder.hSView = (HorizontalScrollView) convertView.findViewById(R.id.hsv);

            mHolder.action = convertView.findViewById(R.id.ll_action);
            ViewWidth =  mHolder.action.getWidth();
            mHolder.btOne = (Button) convertView.findViewById(R.id.deleteItemButtom);
            mFirstButtomWidth = mHolder.btOne.getWidth();
            mHolder.btTwo = (Button) convertView.findViewById(R.id.stickItemButtom);
            mHolder.mLightListViewImageView = (ImageView) convertView.findViewById(R.id.lightListViewImageView);
            mHolder.content = convertView.findViewById(R.id.ll_content);
            LayoutParams params =  mHolder.content.getLayoutParams();
            params.width = mScreentWidth;
            convertView.setTag(mHolder);
        }else{
            mHolder = (ViewHolder)convertView.getTag();
        }
        if (mBitmap == null){
            mHolder.mLightListViewImageView.setImageResource(R.drawable.smssdk_search_icon);
        }else{
            mHolder.mLightListViewImageView.setImageBitmap(mBitmap);
        }
        mHolder.btOne.setTag(position);
        mHolder.btTwo.setTag(position);
        convertView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_POINTER_DOWN:
                        if (v != null) {
                            ViewHolder mHolder = (ViewHolder) v.getTag();
                            mHolder.hSView.smoothScrollTo(0, 0);
                        }
                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        ViewHolder mHolder = (ViewHolder) v.getTag();
                        int scrollX = mHolder.hSView.getScrollX();
                        int actionW = mHolder.action.getWidth();
                        if (scrollX < actionW / 2) {
                            mHolder.hSView.smoothScrollTo(0, 0);
                        } else {
                            mHolder.hSView.smoothScrollTo(actionW, 0);
                        }
                        return true;
                }
                return false;
            }
        });
        if (mHolder.hSView.getScrollX() != 0){
            mHolder.hSView.scrollTo(0,0);
        }
        // 设置背景颜色,设置填充内容.
        mHolder.content.setBackgroundResource(R.color.bule);
//        mHolder.mLightListViewImageView
        // 设置监听事件
        mHolder.btOne.setOnClickListener(this);
        mHolder.btTwo.setOnClickListener(this);

        return convertView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.deleteItemButtom:
                LogUtils.showLogI("点了Buttom1");
                break;
            case R.id.stickItemButtom:
                LogUtils.showLogI("点了Buttom2");
                break;
        }
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
