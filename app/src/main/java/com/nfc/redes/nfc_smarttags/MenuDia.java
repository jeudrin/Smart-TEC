package com.nfc.redes.nfc_smarttags;

import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.sql.ResultSet;
import java.util.ArrayList;

import DBAdapters.SQLAdapter;

public class MenuDia extends AppCompatActivity
{
    ListView productsListView;

    String[] productos;
    String[] precios;

    SQLAdapter sqlAdapter;

    ArrayList<Alimento> listAlimentos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_dia);

        setTitle(getResources().getString(R.string.activity_menu_dia));

        sqlAdapter = new SQLAdapter();

        listAlimentos = getMenu();

        productsListView = (ListView) findViewById(R.id.listViewProducts);

        setUpListViewProducts();
    }

    /**
     * This method sets up the listView products
     */
    public void setUpListViewProducts()
    {
        productos = new String[listAlimentos.size()];
        precios = new String[listAlimentos.size()];

        for(int i = 0; i < listAlimentos.size(); i++)
        {
            productos[i] = listAlimentos.get(i).getNombre();
            precios[i] = listAlimentos.get(i).getPrecio();
        }

        /*
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
        */

        CustomListView adapter = new CustomListView(this, productos, precios);

        productsListView.setAdapter(adapter);
    }

    public ArrayList<Alimento> getMenu()
    {
        ArrayList<Alimento> listaAlimentos = new ArrayList<>();
        try{
            //ResultSet result = sqlAdapter.connect("select top 10 nombre, imagen, precio from alimentos");
            ResultSet result = sqlAdapter.connect("exec getMenu");
            Alimento alimento;
            String nombre;
            String precio;
            String imagen;
            if(result != null){
                while(result.next()){
                    nombre = result.getString("nombre");
                    precio = result.getString("precio");
                    imagen = result.getString("nombre");
                    alimento = new Alimento(nombre, precio,imagen);
                    listaAlimentos.add(alimento);
                }
            }else {
                Log.i("\n\n\n-SQL NULL ------\n\n\n","-----------------------------");
            }

        }catch(Exception e){
            e.printStackTrace();

        }
        return listaAlimentos;
    }
}