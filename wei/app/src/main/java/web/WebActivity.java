package web;

import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.administrator.bao.R;
import com.example.base.base.BaseFragmentActivity;

/**
 * Created by Administrator on 2016/2/9.
 */
public class WebActivity extends BaseFragmentActivity {

    private WebView mWebView;

    @Override
    protected int initLayout() {
        return R.layout.activity_web_layout;

    }
    @Override
    protected void initData() {

        mWebView = (WebView)findViewById(R.id.webview);
    }

    @Override
    protected void initView() {

        WebSettings mWebSetting = mWebView.getSettings();
        mWebSetting.setJavaScriptEnabled(true);
//        mWebView.setWebChromeClient();
    }

}
