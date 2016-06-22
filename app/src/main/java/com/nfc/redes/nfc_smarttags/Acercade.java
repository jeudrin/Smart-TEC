package com.nfc.redes.nfc_smarttags;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class Acercade extends AppCompatActivity {

    WebView wv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acercade);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        wv  = new WebView(this);
        wv.getSettings().setJavaScriptEnabled(true);
        final Activity activity = this;
        wv.setWebViewClient(new WebViewClient()
        {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl)
            {
                Toast.makeText(activity, "Favor revise su conexión", Toast.LENGTH_SHORT).show();
                wv.loadUrl("file:///android_res/raw/error.html");
                //finish();
            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                //super.onReceivedHttpError(view, request, errorResponse);
                //Toast.makeText(activity, "Favor revise su conexión", Toast.LENGTH_SHORT).show();
                //finish();
            }
        });

        wv.loadUrl("file:///android_res/raw/about.html");
        setContentView(wv);

    }

}
