package test.cj.com.myapplication.webView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import test.cj.com.myapplication.R;

/**
 * Created by Administrator on 2019/6/5.
 */

public class WebViewActivity extends AppCompatActivity {
    WebView webView;
    String url = "https://shouji.baidu.com/s?wd=%E6%99%BA%E8%83%BD%E8%AF%AD%E9%9F%B3&bdtype=software&data_type=software&from=pcsuite";
    String apkUrl = "http://gdown.baidu.com/data/wisegame/fd84b7f6746f0b18/baiduyinyue_4802.apk";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DownloadThread(apkUrl).start();
            }
        });


        webView = findViewById(R.id.webView);


        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                Log.e("url--->>", url);
                if (url.endsWith(".apk")) {
                    new DownloadThread(url).start();
                }
            }
        });

    }
}
