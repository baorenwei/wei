package com.google.zxing.client.android;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.google.zxing.client.android.camera.CameraManager;
import com.google.zxing.client.android.decoding.CaptureActivityHandler;
import com.google.zxing.client.android.decoding.InactivityTimer;
import com.google.zxing.client.android.widget.ViewfinderView;

import java.io.IOException;
import java.util.Vector;

public class ScanQRActivity extends Activity implements Callback{

    public static final String EXTRA_RESULT = "result";

    private CaptureActivityHandler handler;
    private ViewfinderView viewfinderView;
    private boolean hasSurface;
    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet;
    private InactivityTimer inactivityTimer;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scan_qr);
        CameraManager.init(getApplication());
        viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
        inactivityTimer.shutdown();
		super.onDestroy();
	}
	
    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {
            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(this, decodeFormats,
                    characterSet);
        }
    }
	
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }		
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		hasSurface = false;
	}
	
    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();

    }

    public void handleDecode(final Result obj, Bitmap barcode) {
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();
        if (obj.getText() != null){
            String result = obj.getText();
            Intent intent = new Intent();
            intent.putExtra(EXTRA_RESULT,result);
            setResult(RESULT_OK,intent);
            onBackPressed();
//            if (result.startsWith(DeviceUtil.PROTOCOL_IOT)){
//                Intent intent = getIntent();
//                intent.setClass(this, AddDeviceActivity.class);
//                intent.putExtra("jid", DeviceUtil.getUDID(result)+getString(R.string.xmpp_service));
//                intent.putExtra("name", DeviceUtil.getDeviceName(this, DeviceUtil.getModel(result)));
//                intent.putExtra("model", DeviceUtil.getModel(result));
//                startActivity(intent);
//                finish();
//            }else if (result.startsWith(DeviceUtil.PROTOCOL_HTTP)){
//                Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse(result));
//                startActivity(it);
//                finish();
//            }
        }
    }

	private void playBeepSoundAndVibrate() {
		// TODO Auto-generated method stub
		Vibrator v = (Vibrator) getSystemService(VIBRATOR_SERVICE);
		v.vibrate(100);
		MediaPlayer mp = MediaPlayer.create(this, R.raw.beep);
		mp.start();
	}
	
	public void goBack(View view) {
		onBackPressed();
	}
}
