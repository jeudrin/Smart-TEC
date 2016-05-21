package com.nfc.redes.nfc_smarttags;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class CubiculosDisponibles extends AppCompatActivity
{
    ArrayList<String> cubiculosList = new ArrayList<>();
    ListView cubiculosListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cubiculos_disponibles);

        setTitle(getResources().getString(R.string.activity_cubiculos_disponibles));

        cubiculosListView = (ListView) findViewById(R.id.listViewCubiculos);

        cubiculosList.add("Cubículo 1");
        cubiculosList.add("Cubículo 3");
        cubiculosList.add("Cubículo 5");
        cubiculosList.add("Cubículo 10");

        setUpListViewCubiculos();
    }

    /**
     * This method sets up the listView products
     */
    public void setUpListViewCubiculos()
    {
        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cubiculosList);
        cubiculosListView.setAdapter(itemsAdapter);

    }
}
