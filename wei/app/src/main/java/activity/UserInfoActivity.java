package activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.administrator.bao.R;
import com.example.base.base.BaseFragmentActivity;
import com.example.base.utils.BitmapUtils;
import com.example.base.utils.FileUtils;
import com.example.base.utils.LogUtils;
import com.example.base.utils.LruCacheUtils;
import com.example.base.utils.ThreadPollUtils;
import com.example.base.widget.RefreshableView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by Administrator on 2016/1/28.
 */
public class UserInfoActivity extends Activity {

    RefreshableView refreshableView;

    private float mPosX;
    private float mPosY;
    private float mCurrentPosX;
    private float mCurrentPosY;

    /*用来标识请求照相功能的activity*/
    private static final int CAMERA_WITH_DATA = 1001;
    /*用来标识请求gallery的activity*/
    private static final int PHOTO_PICKED_WITH_DATA = 1002;
    //图片地址
    private Uri mCameraAddress = Uri.parse("file:///storage/sdcard1/temp00.jpg"); //图像地址地址

    private Bitmap bitMap;       //用来保存图片
    private boolean hasImage;    //是否已经选择了图片

    private ImageView mUserIconImageView;
    private LinearLayout mUpgrade;

    private RelativeLayout mUserIconRelativeLayout;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LogUtils.showLogI("到了这里");
            toRight();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo_layout);
        initView();


    }

    //    @Override
//    protected int initLayout() {
//        return R.layout.activity_userinfo_layout;
//    }

//    @Override
    protected void initView() {

        mUserIconImageView = (ImageView) findViewById(R.id.userIconImageView);
//        mUpgrade = (LinearLayout) findViewById(R.id.upgrade);

        refreshableView = (RefreshableView) findViewById(R.id.refreshable_view);
//        listView = (ListView)findViewById(R.id.list_view);
//
//        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
//        listView.setAdapter(adapter);
        refreshableView.setOnRefreshListener(new RefreshableView.PullToRefreshListener() {
            @Override
            public void onRefresh() {
//                try {
////                    Thread.sleep(3000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                refreshableView.finishRefreshing();
            }
        }, 0);
    }


    private Runnable runn = new Runnable() {
        @Override
        public void run() {
            toRight();
        }
    };

    private void toLeft(){
        TranslateAnimation animation = new TranslateAnimation
                (10,-10,0,0);
        animation.setDuration(300);
        mUserIconImageView.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
//                animation.setRepeatCount(3);
                fromToRight();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
    private void  fromToLeft(){
        TranslateAnimation animation = new TranslateAnimation
                (10,-10,0,0);
        animation.setDuration(300);
        mUserIconImageView.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                to();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void to(){
        TranslateAnimation animation = new TranslateAnimation
                (-10,0,0,0);
        animation.setDuration(300);
        mUserIconImageView.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
//                animation.setRepeatCount(3);
                mHandler.postDelayed(runn,2000);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void  fromToRight(){
        TranslateAnimation animation = new TranslateAnimation
                (-10,10,0,0);
        animation.setDuration(300);
        mUserIconImageView.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                fromToLeft();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void toRight(){
        TranslateAnimation animation = new TranslateAnimation
                (0,10,0,0);
        animation.setDuration(300);
        mUserIconImageView.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                toLeft();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

//    @Override
//    protected void initData() {
//
//        mUpgrade.setOnClickListener(this);
//        mUserIconImageView.setOnClickListener(this);
//
//        //获取用户头像
//        File file = new File("storage/sdcard1/temp00.jpg");
//        if(file.exists()) {
//            Bitmap mBit = BitmapUtils.getRoundBitmap(BitmapUtils.getBitmap(mCameraAddress, mContext));
//            mUserIconImageView.setImageBitmap(mBit);
//        }
//    }
//
//
    @Override
    protected void onResume() {
        super.onResume();
        //开启用户头像动画
        mHandler.postAtTime(runn, 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(runn);
    }

    //
//    @Override
//    public void onClick(View v) {
//        super.onClick(v);
//
//        switch (v.getId()) {
////            case R.id.upgrade:
////                new UpdateActivity(this);
////                break;
//            case R.id.userIconImageView:
//                showDialog();
//                break;
//        }
//    }
//
//    public void showDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle(mContext.getResources().getString(R.string.please_select_a_photo));
//        builder.setPositiveButton(mContext.getResources().getString(R.string.camera_get), new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface arg0, int arg1) {
//                doTakePhoto();
//            }
//        });
//        builder.setNegativeButton(mContext.getResources().getString(R.string.gallery_get), new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface arg0, int arg1) {
//                doSelectImageFromLoacal();
//            }
//        });
//        builder.show();
//    }
//
//    /**
//     * 拍照获取图片
//     */
//    protected void doTakePhoto() {
//        String state = Environment.getExternalStorageState();
//        if (state.equals(Environment.MEDIA_MOUNTED)) {
//
//            Intent getImageByCamera = new Intent("android.media.action.IMAGE_CAPTURE");
//            getImageByCamera.putExtra(MediaStore.EXTRA_OUTPUT, mCameraAddress);
//            getImageByCamera.putExtra("noFaceDetection", false);
//            startActivityForResult(getImageByCamera, CAMERA_WITH_DATA);
//        } else {
//            Toast.makeText(getApplicationContext(), "请确认已经插入SD卡", Toast.LENGTH_LONG).show();
//        }
//    }
//
//    /**
//     * 从本地手机中选择图片
//     */
//    private void doSelectImageFromLoacal() {
//        Intent localIntent = new Intent(Intent.ACTION_GET_CONTENT, null);
//        localIntent.setType("image/*");
//        localIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCameraAddress);
//        localIntent.putExtra("noFaceDetection", false);
//        Intent localIntent2 = Intent.createChooser(localIntent, mContext.getResources().getString(R.string.select_photo));
//        startActivityForResult(localIntent2, PHOTO_PICKED_WITH_DATA);
//    }
//
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode != RESULT_OK)
//            return;
//        switch (requestCode) {
//            case PHOTO_PICKED_WITH_DATA: //图片
//                if (bitMap != null && !bitMap.isRecycled()) {
//                    bitMap.recycle();
//                }
//                Uri selectedImageUri = data.getData();
//                bitMap = BitmapUtils.getRoundBitmap(BitmapUtils.getBitmap(selectedImageUri, mContext));
//                //获取图片的绝对路径
////                String[] proj = {MediaStore.Images.Media.DATA};
////                Cursor cursor = getContentResolver().query(selectedImageUri, proj, null, null, null);
////                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
////                cursor.moveToFirst();
////                String path = cursor.getString(column_index);
////                Log.i("TAG","------path-------"+path);
////                bitMap = getBitmap(Uri.parse(path));
////                Log.i("TAG","------bitMap-------"+bitMap);
//                mUserIconImageView.setImageBitmap(bitMap);
//                break;
//            case CAMERA_WITH_DATA:  //相机
//                if (bitMap != null && !bitMap.isRecycled()) {
//                    bitMap.recycle();
//                }
//                bitMap = BitmapUtils.getRoundBitmap(BitmapUtils.getBitmap(mCameraAddress,mContext));
//                mUserIconImageView.setImageBitmap(bitMap);
//                hasImage = true;
//                break;
//        }
//    }
}
