package activity;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.administrator.bao.R;
import com.example.base.base.BaseFragmentActivity;
import com.example.base.utils.BitmapUtils;
import com.example.base.utils.FileUtils;
import com.example.base.utils.LogUtils;
import com.example.base.utils.LruCacheUtils;

import java.io.FileNotFoundException;

/**
 * Created by Administrator on 2016/1/28.
 */
public class UserInfoActivity extends BaseFragmentActivity {

    /*用来标识请求照相功能的activity*/
    private static final int CAMERA_WITH_DATA = 1001;
    /*用来标识请求gallery的activity*/
    private static final int PHOTO_PICKED_WITH_DATA = 1002;
    //图片地址
    private Uri mCameraAddress = Uri.parse("file:///storage/sdcard1/temp00.jpg"); //照相地址
    private Uri mPictureAddress = Uri.parse("file:///storage/sdcard1/temp.jpg");  //图库地址

    private Bitmap bitMap;       //用来保存图片
    private boolean hasImage;    //是否已经选择了图片

    private ImageView mUserIconImageView;
    private LinearLayout mUpgrade;
    @Override
    protected int initLayout() {
        return R.layout.activity_userinfo_layout;
    }

    @Override
    protected void initView() {

        mUserIconImageView = (ImageView)findViewById(R.id.userIconImageView);
        mUpgrade = (LinearLayout)findViewById(R.id.upgrade);
    }

    @Override
    protected void initData() {

        mUpgrade.setOnClickListener(this);
        mUserIconImageView.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bitmap bitmap = LruCacheUtils.getInstance().getBitmapFromMemoryCache("mCameraAddress");
        LogUtils.showLogI(bitmap+"");
        if (bitmap != null){
            mUserIconImageView.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        switch (v.getId()){
            case R.id.upgrade:
                    new UpdateActivity(this);
                break;
            case R.id.userIconImageView:
                showDialog();
                break;
        }
    }

    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(mContext.getResources().getString(R.string.please_select_a_photo));
        builder.setPositiveButton(mContext.getResources().getString(R.string.camera_get), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                doTakePhoto();
            }
        });
        builder.setNegativeButton(mContext.getResources().getString(R.string.gallery_get), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                doSelectImageFromLoacal();
            }
        });
        builder.show();
    }

    /**
     * 拍照获取图片
     */
    protected void doTakePhoto() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {

            Intent getImageByCamera = new Intent("android.media.action.IMAGE_CAPTURE");
            getImageByCamera.putExtra(MediaStore.EXTRA_OUTPUT, mCameraAddress);
            getImageByCamera.putExtra("noFaceDetection", false);
            startActivityForResult(getImageByCamera, CAMERA_WITH_DATA);
        }
        else {
            Toast.makeText(getApplicationContext(), "请确认已经插入SD卡", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 从本地手机中选择图片
     */
    private void doSelectImageFromLoacal() {
        Intent localIntent = new Intent();
        localIntent.setType("image/*");
        localIntent.setAction("android.intent.action.GET_CONTENT");
        Intent localIntent2 = Intent.createChooser(localIntent, mContext.getResources().getString(R.string.select_photo));
        startActivityForResult(localIntent2, PHOTO_PICKED_WITH_DATA);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK)
            return;
        switch (requestCode) {
            case PHOTO_PICKED_WITH_DATA: //图片
                if (bitMap != null && !bitMap.isRecycled()) {
                    bitMap.recycle();
                }
                Uri selectedImageUri = data.getData();
                bitMap = BitmapUtils.getRoundBitmap(BitmapUtils.getBitmap(selectedImageUri,mContext));
                //获取图片的绝对路径
//                String[] proj = {MediaStore.Images.Media.DATA};
//                Cursor cursor = getContentResolver().query(selectedImageUri, proj, null, null, null);
//                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//                cursor.moveToFirst();
//                String path = cursor.getString(column_index);
//                Log.i("TAG","------path-------"+path);
//                bitMap = getBitmap(Uri.parse(path));
//                Log.i("TAG","------bitMap-------"+bitMap);
                LruCacheUtils.getInstance().addBitmapToMemoryCache("mCameraAddress",bitMap);
                mUserIconImageView.setImageBitmap(bitMap);
                break;
            case CAMERA_WITH_DATA:  //相机
                if (bitMap != null && !bitMap.isRecycled()) {
                    bitMap.recycle();
                }
                bitMap = BitmapUtils.getRoundBitmap(BitmapUtils.getBitmap(mCameraAddress, mContext));
                LruCacheUtils.getInstance().addBitmapToMemoryCache("mCameraAddress",bitMap);
                mUserIconImageView.setImageBitmap(bitMap);
                hasImage = true;
                break;
        }
    }
}
