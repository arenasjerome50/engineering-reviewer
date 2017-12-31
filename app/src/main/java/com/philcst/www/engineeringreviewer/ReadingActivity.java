package com.philcst.www.engineeringreviewer;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.philcst.www.engineeringreviewer.data.Topic;

public class ReadingActivity extends AppCompatActivity {

    private final String TAG = ReadingActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading);
        ActionBar actionBar = getSupportActionBar();

        Topic topic = getIntent().getParcelableExtra("topic_data");

        setTitle(topic.getTitle());
        String htmlUrl = "file:///android_asset/content/" + topic.getContent();
        WebView webView = (WebView) findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(htmlUrl);
        webView.setWebViewClient(new WebViewClient());

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
