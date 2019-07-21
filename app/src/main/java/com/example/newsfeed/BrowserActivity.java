package com.example.newsfeed;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

public class BrowserActivity extends AppCompatActivity {
    WebView webView;
    ProgressBar showing_progress;
    String url="";
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);

        Intent i = getIntent();
        url= i.getStringExtra("url");
        showing_progress=(ProgressBar) findViewById(R.id.progressBar);
        webView =(WebView) findViewById(R.id.webView);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.loadUrl(url);

        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if (progress == 100) {
                    showing_progress.setVisibility(View.GONE);
                } else {
                    showing_progress.setVisibility(View.VISIBLE);
                }
            }
        });

    }
}
