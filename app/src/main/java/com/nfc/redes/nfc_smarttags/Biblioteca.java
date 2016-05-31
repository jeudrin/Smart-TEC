package com.nfc.redes.nfc_smarttags;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Biblioteca extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biblioteca);

        setTitle(getResources().getString(R.string.activity_biblioteca));
    }

    //Starts CubiculosDisponibles activity
    public void muestraCubiculosDisponibles(View view)
    {
        Intent intent = new Intent(this, CubiculosDisponibles.class);
        startActivity(intent);
    }

    //Starts Registrarse activity
    public void registrarse(View view)
    {
        Intent intent = new Intent(this, Registrarse.class);
        startActivity(intent);
    }
}