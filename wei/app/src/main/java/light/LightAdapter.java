package light;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.bao.R;
import com.example.base.base.MyBaseAdapter;
import com.example.base.utils.HttpUtils;
import com.example.base.utils.LogUtils;
import com.example.base.utils.LruCacheUtils;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * Created by Administrator on 2016/1/14.
 */
public class LightAdapter<String> extends MyBaseAdapter {

    LayoutInflater mInfalter;
    Context mContext;

    LightAdapter(Context mContext) {
        mInfalter = LayoutInflater.from(mContext);
        this.mContext = mContext;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder mHolder = null;
        if (convertView == null) {
            mHolder = new ViewHolder();
            convertView = mInfalter.inflate(R.layout.activity_resigt_layout, null);
            mHolder.mInageView = (ImageView) convertView.findViewById(R.id.imageView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        try {
            HttpUtils.downLoadImage(position, mContext);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Bitmap bit = LruCacheUtils.getInstance().getBitmapFromMemoryCache(LightImage.imageThumbUrls[position]);
        LogUtils.showLogI(bit + "");
        if (bit == null) {
            mHolder.mInageView.setImageResource(R.drawable.smssdk_search_icon);
        } else {
            mHolder.mInageView.setImageBitmap(bit);
        }
        notifyDataSetChanged();
        return convertView;
    }

    class ViewHolder {
        ImageView mInageView;
    }
}
