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
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

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
public class pictureActivity extends BaseFragmentActivity {

    /*用来标识请求照相功能的activity*/
    private static final int CAMERA_WITH_DATA = 1001;
    /*用来标识请求gallery的activity*/
    private static final int PHOTO_PICKED_WITH_DATA = 1002;

    private Bitmap bitMap;       //用来保存图片
    private boolean hasImage;    //是否已经选择了图片

    private ImageView mUserImageView;
    @Override
    protected int initLayout() {
        return R.layout.activity_userinfo_layout;
    }

    @Override
    protected void initView() {

        mUserImageView = (ImageView)findViewById(R.id.userImageView);
    }

    @Override
    protected void initData() {

        mUserImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        Bitmap bitmap = LruCacheUtils.getInstance().getBitmapFromMemoryCache("1");
        LogUtils.showLogI(address+"");
    }

    // 创建对话框
    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请选择图片来源");
        builder.setPositiveButton("相机获取", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                doTakePhoto();
            }
        });
        builder.setNegativeButton("图库获取", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // TODO Auto-generated method stub
//                getFromPictureHome();
                doSelectImageFromLoacal();
            }
        });
        builder.show();
    }

    private String address;
    /**
     * 拍照获取图片
     */
    protected void doTakePhoto() {
        try {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, address);
            startActivityForResult(cameraIntent, CAMERA_WITH_DATA);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从本地手机中选择图片
     */
    private void doSelectImageFromLoacal() {
        Intent localIntent = new Intent();
        localIntent.setType("image/*");
        localIntent.setAction("android.intent.action.GET_CONTENT");
        Intent localIntent2 = Intent.createChooser(localIntent, "选择图片");
        startActivityForResult(localIntent2, PHOTO_PICKED_WITH_DATA);
    }

//    然后需要重写onActivityResult()方法，判断是否那种方式获取的图片。
//    Java 代码复制内容到剪贴板


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK)
            return;
//        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, address);
        Log.i("TAG","---------------"+address);
        switch (requestCode) {
            case PHOTO_PICKED_WITH_DATA: //从本地选择图片
                if (bitMap != null && !bitMap.isRecycled()) {
                    bitMap.recycle();
                }
                Uri selectedImageUri = data.getData();
                if (selectedImageUri != null) {
                    try {
                        bitMap = BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImageUri));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    //下面这两句是对图片按照一定的比例缩放，这样就可以完美地显示出来。有关图片的处理将重新写文章来介绍。
//                    int scale = ImageThumbnail.reckonThumbnail(bitMap.getWidth(), bitMap.getHeight(), 500, 600);
//                    bitMap = ImageThumbnail.PicZoom(bitMap, (int) (bitMap.getWidth() / scale), (int) (bitMap.getHeight() / scale));
                    Bitmap bitmap = LruCacheUtils.getInstance().decodeSampledBitmapFromResource(selectedImageUri.toString(), bitMap.getWidth());
                    if (bitmap != null){
                        LruCacheUtils.getInstance().addBitmapToMemoryCache("1",bitmap);
                    }
                    if (bitMap != null){
                        Bitmap bit = BitmapUtils.getRoundBitmap(bitMap);
                        mUserImageView.setImageBitmap(bit);
                    }else{
//                        Bitmap bitmap1 = mContext.getResources().getDrawable(R.drawable.ic);
                        mUserImageView.setImageResource(R.drawable.smssdk_search_icon);
                    }
                    // 将得到的图片设置到SmartImageView
//                    mUserImageView.setImageBitmap(bitMap);
//                    mPictureImageView.setVisibility(View.VISIBLE);
                    hasImage = true;
                }
                break;
            case CAMERA_WITH_DATA:  //拍照
                Bundle bundle = data.getExtras();
                bitMap = (Bitmap) bundle.get("data");
                if (bitMap != null)
                    bitMap.recycle();
                bitMap = (Bitmap) data.getExtras().get("data");
//                int scale = ImageThumbnail.reckonThumbnail(bitMap.getWidth(), bitMap.getHeight(), 500, 600);
//                bitMap = ImageThumbnail.PicZoom(bitMap, (int) (bitMap.getWidth() / scale), (int) (bitMap.getHeight() / scale));
//                mUserImageView.setImageBitmap(bitMap);
//                imageView.setVisibility(View.VISIBLE);
//                Bitmap cameraBitmap = LruCacheUtils.getInstance().decodeSampledBitmapFromResource(selectedImageUri.toString(),bitMap.getWidth());
//                if (cameraBitmap != null){
//                    LruCacheUtils.getInstance().addBitmapToMemoryCache("1",cameraBitmap);
//                }
                hasImage = true;
                break;
        }
    }
}
