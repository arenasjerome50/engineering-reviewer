package com.philcst.www.engineeringreviewer.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.philcst.www.engineeringreviewer.R;

import java.io.File;

public class MathView extends WebView {
    private String text;
    private static final String TAG = MathView.class.getSimpleName();
    private volatile boolean pageLoaded;
    private boolean clickable;
    //private int textColor;
    //private int textSize;

    public MathView(Context context) {
        super(context);
        init(context);
    }

    public MathView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        TypedArray mTypeArray = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.MathView,
                0, 0);
        try {
            setViewBackgroundColor(mTypeArray.getInteger(R.styleable.MathView_setViewBackgroundColor, ContextCompat.getColor(context, android.R.color.transparent)));
            setText(mTypeArray.getString(R.styleable.MathView_setText));
            setClickable(mTypeArray.getBoolean(R.styleable.MathView_setClickable, false));
            //pixelSizeConversion(mTypeArray.getDimension(R.styleable.MathView_setTextSize,default_text_size));
            //setTextColor(mTypeArray.getColor(R.styleable.MathView_setTextColor,ContextCompat.getColor(context,android.R.color.black)));
        }
        catch (Exception e) {
            Log.d(TAG, "Exception:"+e.toString());
        }
    }

    public void setViewBackgroundColor(int color) {
        setBackgroundColor(color);
        this.invalidate();
    }

    public void setClickable(boolean is_clickable) {
        this.clickable = is_clickable;
        this.invalidate();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return !clickable;
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void init(Context context) {
        setViewBackgroundColor(Color.TRANSPARENT);
        this.text = "";
        this.pageLoaded = false;

        // enable javascript
        getSettings().setLoadWithOverviewMode(true);
        getSettings().setJavaScriptEnabled(true);

        // disable zoom control
        getSettings().setDisplayZoomControls(false);
        getSettings().setBuiltInZoomControls(false);
        getSettings().setSupportZoom(false);

        // caching
        File dir = context.getCacheDir();
        if (!dir.exists()) {
            Log.d(TAG, "directory does not exist");
            boolean mkdirsStatus = dir.mkdirs();
            if (!mkdirsStatus) {
                Log.e(TAG, "directory creation failed");
            }
        }
        getSettings().setAppCachePath(dir.getPath());
        getSettings().setAppCacheEnabled(true);
        getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        // disable click
        setClickable(false);
        setLongClickable(false);
        getSettings().setUseWideViewPort(true);
        loadUrl("file:///android_asset/content/MathTemplate.html");
        setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                pageLoaded = true;
                loadUrl("javascript:showFormula('" + MathView.this.text + "')");
                super.onPageFinished(view, url);
            }
        });
    }

    public void setText(String text) {
        this.text = text;
        if (pageLoaded) {
            loadUrl("javascript:showFormula('" + MathView.this.text + "')");
        } else {
            Log.e(TAG, "Page is not loaded yet.");
        }
    }

    public String getText() {
        //return text.substring(1, text.length() - 1);
        return text;
    }

}
