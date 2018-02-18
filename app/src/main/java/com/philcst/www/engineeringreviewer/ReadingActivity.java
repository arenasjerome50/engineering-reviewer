package com.philcst.www.engineeringreviewer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.philcst.www.engineeringreviewer.data.Topic;

public class ReadingActivity extends AppCompatActivity {

    private final String TAG = ReadingActivity.class.getSimpleName();

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Topic topic = getIntent().getParcelableExtra("topic_data");

        setTitle(topic.getName());
        String htmlUrl = "file:///android_asset/content/" + topic.getContent();
        webView = (WebView) findViewById(R.id.web_view);

        // get the settings
        final WebSettings settings = webView.getSettings();
        // for faster loading
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setAppCacheEnabled(false);
        settings.setLoadsImagesAutomatically(true);
        settings.setGeolocationEnabled(false);
        settings.setNeedInitialFocus(false);
        settings.setSaveFormData(false);

        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(htmlUrl);

        // disable copy paste and selection of text
        webView.setClickable(false);
        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        webView.setLongClickable(false);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
