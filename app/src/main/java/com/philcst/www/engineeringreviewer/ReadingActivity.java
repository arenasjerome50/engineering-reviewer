package com.philcst.www.engineeringreviewer;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.philcst.www.engineeringreviewer.data.Topic;

public class ReadingActivity extends AppCompatActivity implements AdjustFontDialogFragment.OnDialogFragmentInteraction{

    private final String TAG = ReadingActivity.class.getSimpleName();
    private int fontSize;

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

        // get prefs
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        fontSize = prefs.getInt("reading_font_size", 18);

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
        settings.setDefaultFontSize(fontSize);

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
        int id = item.getItemId();
        if(id == R.id.resize_text) {
            new AdjustFontDialogFragment().show(getSupportFragmentManager(), this.getClass().getSimpleName());
            return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.reading_option, menu);
        return true;
    }

    @Override
    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.edit().putInt("reading_font_size", fontSize).apply();
        webView.getSettings().setDefaultFontSize(fontSize);
    }

    @Override
    public int getFontSize() {
        return fontSize;
    }
}
