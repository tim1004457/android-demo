package com.tencent.timluo.demo.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tencent.timluo.demo.R;
import com.tencent.timluo.demo.SelfApp;
import com.tencent.timluo.demo.uitl.XLog;

/**
 * Created by timluo on 2014/11/1.
 */
public class WebViewActivity extends BaseActivity {
public static final String TAG = "WebView";
    private WebView webView;
    private EditText urlInputEdit;
    private Button btn;
    private Context baseContext;

    private void initView() {
        webView = (WebView) findViewById(R.id.web_view);
        urlInputEdit = (EditText) findViewById(R.id.url);
        btn = (Button) findViewById(R.id.go_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editable url = urlInputEdit.getText();
                if (TextUtils.isEmpty(url.toString()))
                    return;
                webView.loadUrl(url.toString());
            }
        });
        baseContext = getBaseContext();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        initView();
        initDate();
        webView.loadUrl("http://info.3g.qq.com/g/s?sid=ASFvsAzqBtj6nAzMAimR72Bi&&iarea=86&&icfa=home_touch&aid=news_ss&id=news_20141031050895");

    }

    class JsObject {
        public String toString() {
            XLog.d("Tim", "test To String");
            return "injectedObject";
        }
    }

    private void initDate() {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setBuiltInZoomControls(true);
        webView.addJavascriptInterface(new JavaScriptInterface(), "history"); //history.back();
        webView.addJavascriptInterface(new JavaScriptInterface(), "jstest"); //history.back();
        webView.setWebViewClient(new MyWebChromeClient());
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onCloseWindow(WebView window) {
                Toast.makeText(SelfApp.getContext(),"onCloseWindow",Toast.LENGTH_LONG).show();
                finish();
            }
        });
         //        webView.addJavascriptInterface(new JsObject(), "injectedObject");
        //        webView.loadData("", "text/html", null);
        //        webView.loadUrl("javascript:function(window.history.back();)");
    }

    private class MyWebChromeClient extends WebViewClient{
        @Override
        public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
            XLog.d(TAG, "doUpdateVisitedHistory|" + url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            XLog.d(TAG,"onPageFinished|"+url);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description,
                                    String failingUrl) {
            XLog.d(TAG, "onReceivedError|" + failingUrl);
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            XLog.d(TAG,"shouldOverrideUrlLoading|"+url);
            if (TextUtils.isEmpty(url)) {
                return false;
               }
            view.loadUrl(url);
            return true;
        }
    }

    public class JavaScriptInterface {
        JavaScriptInterface() {
        }

        public void back() {
            Toast.makeText(SelfApp.getContext(),"test2",Toast.LENGTH_LONG).show();
            ((Activity) baseContext).finish();
        }
        public void go(){
            Toast.makeText(SelfApp.getContext(),"go",Toast.LENGTH_LONG).show();
        }
        public void test(){
            Toast.makeText(SelfApp.getContext(),"test",Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);
    }
}
