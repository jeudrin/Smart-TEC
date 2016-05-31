package com.nfc.redes.nfc_smarttags;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SmartTEC extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_tec);
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