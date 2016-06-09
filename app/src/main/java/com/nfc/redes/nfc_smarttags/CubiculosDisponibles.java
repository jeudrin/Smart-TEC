package com.nfc.redes.nfc_smarttags;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import nfc_adapters.NFCTags;

public class CubiculosDisponibles extends AppCompatActivity
{

    NFCTags nfcTags;

    private WebView mWebView;
    private static String url = "172.24.17.14:8080/#/disponibilidad-cubiculos/consultar-disponibilidad-mobile";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cubiculos_disponibles);

        nfcTags = new NFCTags(this, this.getApplicationContext());

        setTitle(getResources().getString(R.string.activity_cubiculos_disponibles));

        ////////

        mWebView  = new WebView(this);
        mWebView.getSettings().setJavaScriptEnabled(true);
        final Activity activity = this;
        mWebView.setWebViewClient(new WebViewClient()
        {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl)
            {
                Toast.makeText(activity, "Favor revise su conexión", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);
                Toast.makeText(activity, "Favor revise su conexión", Toast.LENGTH_SHORT).show();
            }
        });

        mWebView.loadUrl("http://" + url);
        setContentView(mWebView);
        ////////

    }


    @Override
    protected void onPause()
    {
        super.onPause();
        nfcTags.disableForegroundDispatchSystem();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        nfcTags.enableForegroundDispatchSystem();
    }
}