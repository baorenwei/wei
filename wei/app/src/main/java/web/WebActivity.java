package web;

import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.administrator.bao.R;
import com.example.base.base.BaseFragmentActivity;
import com.example.base.utils.LogUtils;

/**
 * Created by Administrator on 2016/2/9.
 */
public class WebActivity extends BaseFragmentActivity {

    private WebView mWebView;
    private Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    protected int initLayout() {
        return R.layout.activity_web_layout;

    }

    @Override
    protected void initView() {

        mWebView = (WebView)findViewById(R.id.webView);
    }

    @Override
    protected void initData() {
        mWebView.setWebChromeClient(new MyWebChromeClient());
        mWebView.setWebViewClient(new MyWebViewClient());

        WebSettings mWebSetting = mWebView.getSettings();
        mWebSetting.setJavaScriptEnabled(true);

        mWebView.loadUrl("file:///android_asset/index.jsp");

        mWebView.addJavascriptInterface(new DemoJavaScriptInterface(), "bao");

    }

    //JS 调用的方法
    final class DemoJavaScriptInterface{
        DemoJavaScriptInterface(){

        }
        @android.webkit.JavascriptInterface
        public void secondHTML() {
            mHandler.post(new Runnable() {
                public void run() {
                   mWebView.loadUrl("javascript:save()");
                    mWebView.loadUrl("file:///android_asset/second.html");
                }
            });
        }
        @android.webkit.JavascriptInterface
        public void firstHTML() {
            mHandler.post(new Runnable() {
                public void run() {
                    mWebView.loadUrl("file:///android_asset/index.jsp");
                }
            });
        }
    }

    //帮助WebView处理各种通知、请求事件的
    final class MyWebChromeClient extends WebChromeClient{

        //判断页面的加载过程
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }
    }

    //主要处理解析，渲染网页等浏览器做的事情
    class MyWebViewClient extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
            return true;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (event.getAction()) {
            case KeyEvent.KEYCODE_BACK:
                if (mWebView.canGoBack()) {
                    mWebView.goBack();
                    return true;
                } else {

                }
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    public boolean onKey(View v, int keyCode, KeyEvent event) {

        if(event.getAction() == KeyEvent.ACTION_DOWN){
            LogUtils.showLogI("点了");

        }
        return false;
    }
}
