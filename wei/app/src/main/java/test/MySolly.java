package test;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.administrator.bao.R;
import com.example.base.utils.LogUtils;
import com.example.base.utils.LruCacheUtils;
import com.handmark.pulltorefresh.library.PullToRefreshAdapterViewBase;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import light.LightImage;

/**
 * Created by Administrator on 2016/1/16.
 */
public class MySolly extends ScrollView implements View.OnTouchListener{

    LruCacheUtils mUtils;
    //每页要加载的图片数量
    private static final int PAZE_SIZE = 15;
    //是否加载过一次    这里onLayout只需加载一次
    private boolean loadOnce;
    //MyScorlly的高度
    private static  int mScrollyHeight;
    //MyScrolly下的直接子布局
    private static  View scrollyLayout;
    
    private LinearLayout firstColum;
    private LinearLayout secondColum;
    private LinearLayout thridColum;

    /**
     * 当前第一列的高度
     */
    private int firstColumnHeight;

    /**
     * 当前第二列的高度
     */
    private int secondColumnHeight;

    /**
     * 当前第三列的高度
     */
    private int thirdColumnHeight;

    //记录当前加载到第几页
    private int page;

    //每一列的宽度
    private int columnWidth;

    //记录垂直滚动的距离
    private static  int lastScrolly = -1;

    //记录所有下载的 或等待下载的任务
    private static Set<LoadImageTagk> taskCollection;

    //记录所有图片的数量  用于随时可以控制图片的释放
    private List<ImageView> imageViewList = new ArrayList<>();

    //对图片的可见性进行判断   以及加载更多的图片
    private Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            MySolly mySolly = (MySolly)msg.obj;
            int scrollY =  mySolly.getScrollY();
            //如果当前的位置和上次的相同 , 标示已停止滚动
            if (scrollY == lastScrolly){
                //当滚到最底部   ,并且没有正在下载的任务  开始加载下一页的图片
                if (mScrollyHeight + scrollY >= scrollyLayout.getHeight()&& taskCollection.isEmpty()){
                    mySolly.loadMoreImages();
                }
                mySolly.checlVisibility();
            }else{
                lastScrolly = scrollY;
                Message message = new Message();
                message.obj = mySolly;
                mHandler.sendMessageAtTime(message,5); //5毫秒之后在进行检测
            }
        }
    };

    public MySolly(Context context, AttributeSet attrs) {
        super(context, attrs);
        mUtils = new LruCacheUtils();
        taskCollection = new HashSet<>();
        setOnTouchListener(this);

    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed && !loadOnce){
            mScrollyHeight = getHeight();
            LogUtils.showLogI(mScrollyHeight + "");
            scrollyLayout = getChildAt(0);
            firstColum = (LinearLayout)findViewById(R.id.first_column);
            secondColum = (LinearLayout)findViewById(R.id.second_column);
            thridColum = (LinearLayout)findViewById(R.id.third_column);
            loadOnce = true;
            loadMoreImages();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP){
            Message msg = new Message();
            msg.obj = this;
            mHandler.sendMessageDelayed(msg, 5);
        }
        return false;
    }

    //开始加载下一页的图片  每张图片都会开启一个线程
    private void loadMoreImages() {
        if (isHasSDcard()){
            int startIndex = page * PAZE_SIZE;
            int endIndex = page * PAZE_SIZE + PAZE_SIZE;
            if (startIndex < LightImage.imageThumbUrls.length){
                Toast.makeText(getContext(),"正在加载",Toast.LENGTH_SHORT).show();
                if (endIndex > LightImage.imageThumbUrls.length){
                    endIndex = LightImage.imageThumbUrls.length;
                }
                for (int i = startIndex; i < endIndex ; i++){

                    LoadImageTagk task = new LoadImageTagk();
                    taskCollection.add(task);
                    task.execute(LightImage.imageThumbUrls[i]);
                }
                page++;
            }else{
                Toast.makeText(getContext(),"已没有更多图片",Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getContext(),"未发现SD卡",Toast.LENGTH_SHORT).show();
        }
    }

    //遍历imageList 中的每种图片  ，对图片的可见性进行检查  ，对离开屏幕范围的替换冲一张空的图片
    private void checlVisibility(){
        for (int i = 0 ; i < imageViewList.size() ;i ++){
            ImageView imageView = imageViewList.get(i);
            int borderTop = (Integer)imageView.getTag(R.string.border_buttom);
            int borderButtom = (Integer)imageView.getTag(R.string.border_top);
            if (borderButtom > getScrollY() && borderTop < getScrollY() + mScrollyHeight){
                String imageUrl = (String)imageView.getTag(R.string.app_name);
                Bitmap bitmap = mUtils.getBitmapFromMemoryCache(imageUrl);
                if (bitmap != null){
                    imageView.setImageBitmap(bitmap);
                }else{
                    LoadImageTagk task = new LoadImageTagk(imageView);
                    task.execute(imageUrl);
                }
            }else{
                imageView.setImageResource(R.drawable.smssdk_search_icon);
            }
        }
    }

    //判断是否存在SD卡
    private boolean isHasSDcard(){
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    class LoadImageTagk extends AsyncTask<String,Void,Bitmap>{

        //图片的地址
        private String mImageUri;
        //可重复使用的ImageView
        private ImageView mImageView;
        private LoadImageTagk(ImageView imageView){
            this.mImageView = imageView;
        }

        private LoadImageTagk(){
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            mImageUri = params[0];
            Bitmap bitmapUrl = mUtils.getBitmapFromMemoryCache(mImageUri);
            if (bitmapUrl == null){
                bitmapUrl =  loadImage(mImageUri);
            }
            return bitmapUrl;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (bitmap != null){
                double ratio = bitmap.getWidth() / (columnWidth * 1.0);
                int scaledHeight = (int) (bitmap.getHeight() / ratio);
                addImage(bitmap,columnWidth,scaledHeight);
            }
            taskCollection.remove(this);
        }
        private Bitmap loadImage(String imageUrl) {
            File imageFile = new File(getImagePath(imageUrl));
            if (!imageFile.exists()) {
                downloadImage(imageUrl);
            }
            if (imageUrl != null) {
                Bitmap bitmap = mUtils.decodeSampledBitmapFromResource(
                        imageFile.getPath(), columnWidth);
                if (bitmap != null) {
                    mUtils.addBitmapToMemoryCache(imageUrl, bitmap);
                    return bitmap;
                }
            }
            return null;
        }
        /**
         * 将图片下载到SD卡缓存起来。
         *
         * @param imageUrl
         *            图片的URL地址。
         */
        private void downloadImage(String imageUrl) {
            HttpURLConnection con = null;
            FileOutputStream fos = null;
            BufferedOutputStream bos = null;
            BufferedInputStream bis = null;
            File imageFile = null;
            try {
                URL url = new URL(imageUrl);
                con = (HttpURLConnection) url.openConnection();
                con.setConnectTimeout(5 * 1000);
                con.setReadTimeout(15 * 1000);
                con.setDoInput(true);
                con.setDoOutput(true);
                bis = new BufferedInputStream(con.getInputStream());
                imageFile = new File(getImagePath(imageUrl));
                fos = new FileOutputStream(imageFile);
                bos = new BufferedOutputStream(fos);
                byte[] b = new byte[1024];
                int length;
                while ((length = bis.read(b)) != -1) {
                    bos.write(b, 0, length);
                    bos.flush();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bis != null) {
                        bis.close();
                    }
                    if (bos != null) {
                        bos.close();
                    }
                    if (con != null) {
                        con.disconnect();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (imageFile != null) {
                Bitmap bitmap = mUtils.decodeSampledBitmapFromResource(
                        imageFile.getPath(), columnWidth);
                if (bitmap != null) {
                    mUtils.addBitmapToMemoryCache(imageUrl, bitmap);
                }
            }
        }

        //向ImageView添加一张图片
        private void addImage(Bitmap bitmap,int imageWidth, int imageHeight) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(imageWidth,imageHeight);
            if (mImageView != null){
                mImageView.setImageBitmap(bitmap);
            }else{
                ImageView imageView = new ImageView(getContext());
                imageView.setLayoutParams(params);
                imageView.setImageBitmap(bitmap);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setPadding(5, 5, 5, 5);
                imageView.setTag(R.string.app_name, mImageUri);
                findColumnToAdd(mImageView, imageHeight).addView(imageView);
                imageViewList.add(imageView);
            }
        }
        /**
         * 找到此时应该添加图片的一列。原则就是对三列的高度进行判断，当前高度最小的一列就是应该添加的一列。
         *
         * @param imageView
         * @param imageHeight
         * @return 应该添加图片的一列
         */
        private LinearLayout findColumnToAdd(ImageView imageView,
                                             int imageHeight) {
            if (firstColumnHeight <= secondColumnHeight) {
                if (firstColumnHeight <= thirdColumnHeight) {
                    imageView.setTag(R.string.border_top, firstColumnHeight);
                    firstColumnHeight += imageHeight;
                    imageView.setTag(R.string.border_buttom, firstColumnHeight);
                    return firstColum;
                }
                imageView.setTag(R.string.border_top, thirdColumnHeight);
                thirdColumnHeight += imageHeight;
                imageView.setTag(R.string.border_buttom, thirdColumnHeight);
                return thridColum;
            } else {
                if (secondColumnHeight <= thirdColumnHeight) {
                    imageView.setTag(R.string.border_top, secondColumnHeight);
                    secondColumnHeight += imageHeight;
                    imageView
                            .setTag(R.string.border_buttom, secondColumnHeight);
                    return secondColum;
                }
                imageView.setTag(R.string.border_top, thirdColumnHeight);
                thirdColumnHeight += imageHeight;
                imageView.setTag(R.string.border_buttom, thirdColumnHeight);
                return thridColum;
            }
        }
        private String getImagePath(String imageUrl) {
            int lastSlashIndex = imageUrl.lastIndexOf("/");
            String imageName = imageUrl.substring(lastSlashIndex + 1);
            String imageDir = Environment.getExternalStorageDirectory()
                    .getPath() + "/PhotoWallFalls/";
            File file = new File(imageDir);
            if (!file.exists()) {
                file.mkdirs();
            }
            String imagePath = imageDir + imageName;
            return imagePath;
        }
    }
}
