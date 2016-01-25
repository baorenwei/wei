package light;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.administrator.bao.R;
import com.example.base.base.MyBaseAdapter;
import com.example.base.utils.HttpUtils;
import com.example.base.utils.LogUtils;
import com.example.base.utils.LruCacheUtils;

import java.io.FileNotFoundException;

/**
 * Created by Administrator on 2016/1/14.
 */
public class LightAdapter<String> extends MyBaseAdapter {

    private LayoutInflater mInfalter;
    private Context mContext;
    private Bitmap mBitmap;

    LightAdapter(Context mContext) {
        mInfalter = LayoutInflater.from(mContext);
        this.mContext = mContext;
    }
    public  void setBitmap(Bitmap bitmap){
        this.mBitmap = bitmap;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder mHolder = null;
        if (convertView == null) {
            mHolder = new ViewHolder();
            convertView = mInfalter.inflate(R.layout.widget_light_adapter_layout, null);
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
        if (mBitmap == null) {
            mHolder.mInageView.setImageResource(R.drawable.smssdk_search_icon);
        } else {
            mHolder.mInageView.setImageBitmap(mBitmap);
        }
        return convertView;
    }

    class ViewHolder {
        ImageView mInageView;
    }
}
