package activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.example.base.utils.UpdateInfoServiceUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import model.UpdateInfo;

/**
 * Created by Administrator on 2016/2/6.
 */
public class UpdateActivity  {

    private UpdateInfo mInfo;
    private ProgressDialog pBar;
    private Context mContext;

    public UpdateActivity(Context context){
        this.mContext = context;
        init();
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(isNeedUpdate()){
                showUpdateDialog();
            }
        }
    };

    public void initData() {
    }

    private void init() {
//        Toast.makeText(mContext, "正在检查版本更新..", Toast.LENGTH_SHORT).show();
        new Thread(new Runnable() {
            @Override
            public void run() {
//                UpdateInfoServiceUtils mUpdateInfoServiceUtils = new UpdateInfoServiceUtils();
//                mInfo = mUpdateInfoServiceUtils.getUdateInfo();
                UpdateInfo info = new UpdateInfo();
                info.setVersion("3.4");
                mInfo = info;
                mHandler.sendEmptyMessage(0);
            }
        }).start();
    }
    private boolean isNeedUpdate(){

        String v = mInfo.getVersion();
        if (v.equals(getVersion())){
            return false;
        }else{
            return true;
        }
    }


    private String getVersion(){
        try {
            PackageManager packageManager =  mContext.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(mContext.getPackageName(),0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "版本号未知";
    }

    private void showUpdateDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setTitle("发现新版本  " + mInfo.getVersion());
        builder.setMessage(mInfo.getDescription());
        builder.setCancelable(false);

        builder.setPositiveButton("更新", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (Environment.getExternalStorageState().equals(
                        Environment.MEDIA_MOUNTED)) {
                    downFile(mInfo.getUrl());     //在下面的代码段
                } else {
                    Toast.makeText(mContext, "SD卡不可用，请插入SD卡",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
            }

        });
        builder.create().show();
    }

    private void downFile(final String url) {
        pBar = new ProgressDialog(mContext);    //进度条，在下载的时候实时更新进度，提高用户友好度
        pBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pBar.setTitle("正在下载");
        pBar.setMessage("请稍候...");
        pBar.setProgress(0);
        pBar.show();
        new Thread() {
            public void run() {
                URL mUrl = null;
                InputStream is = null;
                FileOutputStream fileOutputStream = null;
                try {
                    mUrl = new URL(url);
                    HttpURLConnection conn = (HttpURLConnection)mUrl.openConnection();
                    is = conn.getInputStream();
                    if (is != null) {
                        File file = new File(
                                Environment.getExternalStorageDirectory(),
                                "Test.apk");
                        fileOutputStream = new FileOutputStream(file);
                        byte[] buf = new byte[10];   //这个是缓冲区，即一次读取10个比特，我弄的小了点，因为在本地，所以数值太大一 下就下载完了，看不出progressbar的效果。
                        int ch ;
                        int process = 0;
                        while ((ch = is.read(buf)) != -1) {
                            fileOutputStream.write(buf, 0, ch);
                            process += ch;
                            pBar.setProgress(process);       //这里就是关键的实时更新进度了！
                        }
                    }
                    fileOutputStream.flush();
                    down();
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    try {
                        if (fileOutputStream != null){
                            fileOutputStream.close();
                        }
                        if(is != null){
                            is.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
    void down() {
        mHandler.post(new Runnable() {
            public void run() {
                pBar.cancel();
                update();
            }
        });
    }
    //安装文件，一般固定写法
    void update() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(Environment
                        .getExternalStorageDirectory(), "Test.apk")),
                "application/vnd.android.package-archive");
        mContext.startActivity(intent);
    }

}
