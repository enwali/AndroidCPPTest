package com.example.androidcpptest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidcpptest.utils.web.VideoResourceListener;
import com.example.androidcpptest.utils.web.WebViewHelper;

public class WebViewActivity extends AppCompatActivity {
    private WebViewHelper webViewHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        WebView webView = findViewById(R.id.webView);

        // 初始化WebViewHelper
        webViewHelper = new WebViewHelper(this, webView);
        webViewHelper.setVideoResourceListener(new VideoResourceListener() {
            @Override
            public void onVideoResourceFound(String url, String mimeType) {
                Log.d("VideoSniffer", "发现视频资源: " + url);
//                Toast.makeText(WebViewActivity.this, "发现视频资源: " + url, Toast.LENGTH_LONG).show();
                // 这里可以处理视频资源URL，比如展示给用户或进行下载
            }
        });

        // 加载初始URL
        webViewHelper.loadUrl("https://www.tyys2.com/index.php/vod/play/id/76606/sid/1/nid/1.html");
    }

    @Override
    protected void onResume() {
        super.onResume();
        webViewHelper.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        webViewHelper.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webViewHelper.onDestroy();
    }
}