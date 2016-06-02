package com.nfc.redes.nfc_smarttags;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import nfc_adapters.NFCTags;

public class SmartTEC extends AppCompatActivity
{
    NFCTags nfcTags;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_tec);

        nfcTags = new NFCTags(this, this.getApplicationContext());
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

    @Override
    protected void onNewIntent(Intent intent)
    {
        super.onNewIntent(intent);

        if(intent.hasExtra(nfcTags.getNfcAdapter().EXTRA_TAG))
        {
            String tagText = nfcTags.readTextFromTag(intent);

            if(tagText.equals("飯廳"))
            {
                showMenu(null);
            }
        }
    }

    //Starts MenuDia activity
    public void showMenu(View view)
    {
        Intent intent = new Intent(this, MenuDia.class);
        startActivity(intent);
    }

    //Starts Biblioteca activity
    public void showLibrary(View view)
    {
        Intent intent = new Intent(this, Biblioteca.class);
        startActivity(intent);
    }
}