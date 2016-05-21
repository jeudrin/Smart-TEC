package com.nfc.redes.nfc_smarttags;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MenuDia extends AppCompatActivity
{
    ListView productsListView;

    String[] productos;
    Integer[] precios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_dia);

        setTitle(getResources().getString(R.string.activity_menu_dia));

        productsListView = (ListView) findViewById(R.id.listViewProducts);

        setUpListViewProducts();
    }

    /**
     * This method sets up the listView products
     */
    public void setUpListViewProducts()
    {
        productos = new String[5];
        precios = new Integer[5];

        productos[0] = "Arroz";
        productos[1] = "Frijoles";
        productos[2] = "Fresco";
        productos[3] = "Ensalada";
        productos[4] = "Fajitas de Pollo";

        precios[0] = 100;
        precios[1] = 100;
        precios[2] = 150;
        precios[3] = 150;
        precios[4] = 700;

        CustomListView adapter = new CustomListView(this, productos, precios);

        productsListView.setAdapter(adapter);
    }
}