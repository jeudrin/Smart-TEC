package com.nfc.redes.nfc_smarttags;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Toast;

public class Registrarse extends AppCompatActivity
{
    private WebView mWebView;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        mWebView  = new WebView(this);
        mWebView.getSettings().setJavaScriptEnabled(true);
        final Activity activity = this;
        mWebView.setWebViewClient(new WebViewClient()
        {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl)
            {
                Toast.makeText(activity, description, Toast.LENGTH_SHORT).show();
            }
        });

        editText = (EditText) findViewById(R.id.editText);

        setTitle(getResources().getString(R.string.activity_registrarse));

    }

    public void loadView(View view)
    {
        mWebView.loadUrl("http://" + editText.getText().toString());
        setContentView(mWebView);
    }
}