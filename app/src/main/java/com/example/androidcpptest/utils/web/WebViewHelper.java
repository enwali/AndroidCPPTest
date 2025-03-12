package com.example.androidcpptest.utils.web;
import android.content.Context;
import android.net.Uri;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.Arrays;
import java.util.List;

public class WebViewHelper {
    private final Context context;
    private WebView webView;
    private VideoResourceListener videoListener;
    private final List<String> videoExtensions = Arrays.asList("mp4", "mkv", "m3u8");
    private final List<String> videoMimeTypes = Arrays.asList("video/mp4", "video/x-matroska", "application/x-mpegURL");

    public WebViewHelper(Context context, WebView webView) {
        this.context = context;
        this.webView = webView;
        initWebViewSettings();
    }

    private void initWebViewSettings() {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        settings.setAllowFileAccess(true);
        settings.setAllowContentAccess(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setMediaPlaybackRequiresUserGesture(false);

        webView.setWebViewClient(createWebViewClient());
    }

    private WebViewClient createWebViewClient() {
        return new WebViewClient() {
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                checkVideoResource(request);
                return super.shouldInterceptRequest(view, request);
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                checkVideoResource(url);
                return super.shouldInterceptRequest(view, url);
            }

            // 其他需要重写的方法...
        };
    }

    private void checkVideoResource(WebResourceRequest request) {
        String url = request.getUrl().toString();
        String mimeType = request.getRequestHeaders().get("Content-Type");
        checkVideoResource(url, mimeType);
    }

    private void checkVideoResource(String url) {
        checkVideoResource(url, null);
    }

    private void checkVideoResource(String url, String mimeType) {
        if (isVideoResource(url, mimeType) && videoListener != null) {
            videoListener.onVideoResourceFound(url, mimeType);
        }
    }

    private boolean isVideoResource(String url, String mimeType) {
        // 检查文件扩展名
        String path = Uri.parse(url).getPath();
        if (path != null) {
            String ext = path.substring(path.lastIndexOf('.') + 1);
            if (videoExtensions.contains(ext.toLowerCase())) {
                return true;
            }
        }

        // 检查MIME类型
        if (mimeType != null) {
            if (videoMimeTypes.contains(mimeType.toLowerCase())) {
                return true;
            }
            if (mimeType.startsWith("video/")) {
                return true;
            }
        }

        return false;
    }

    public void setVideoResourceListener(VideoResourceListener listener) {
        this.videoListener = listener;
    }

    // 其他WebView操作方法
    public void loadUrl(String url) {
        webView.loadUrl(url);
    }

    public void onResume() {
        webView.onResume();
    }

    public void onPause() {
        webView.onPause();
    }

    public void onDestroy() {
        webView.destroy();
    }
}