package com.nfc.redes.nfc_smarttags;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.nio.ByteBuffer;
import java.sql.ResultSet;
import java.util.ArrayList;
import DBAdapters.SQLAdapter;

public class MenuDia extends AppCompatActivity
{
    TextView tvConsumoTotal;
    ListView productsListView;

    SQLAdapter sqlAdapter;
    ArrayList<Alimento> listAlimentos;

    String[] productos;
    Integer[] precios;
    int consumoTotal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_dia);

        setTitle(getResources().getString(R.string.activity_menu_dia));

        sqlAdapter = new SQLAdapter();
        listAlimentos = getMenu();

        tvConsumoTotal = (TextView) findViewById(R.id.tvConsumoTotal);
        productsListView = (ListView) findViewById(R.id.listViewProducts);

        setUpListViewProducts();
    }

    public void setUpListViewProducts()
    {
        productos = new String[listAlimentos.size()];
        precios = new Integer[listAlimentos.size()];
        ArrayList<Bitmap> images = new ArrayList<>();

        String temPrice;

        for(int i = 0; i < listAlimentos.size(); i++)
        {
            productos[i] = listAlimentos.get(i).getNombre();

            temPrice = listAlimentos.get(i).getPrecio();

            if(temPrice.contains(".0000"))
            {
                temPrice = temPrice.replace(".0000","");
            }

            precios[i] = Integer.parseInt(temPrice);

            //Obtaining the image of the product.
            String binaryImage = listAlimentos.get(i).getImagen();

            // Parsing binary String to byte array.
            byte[] tryingHarder = fromBinaryStringToByteArray(binaryImage);

            // Parsing byte array to Bitmap.
            Bitmap decodedByte = BitmapFactory.decodeByteArray(tryingHarder, 0, tryingHarder.length);

            // Adding the new Bitmap image to the images arrayList.
            images.add(decodedByte);
        }

        CustomListView adapter = new CustomListView(this, productos, precios, images);

        productsListView.setAdapter(adapter);

        productsListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                consumoTotal += precios[position];
                tvConsumoTotal.setText("Total: ₡" + consumoTotal);
            }
        });
    }

    /**
     * This method receives a binary String of an image and returns a byte array.
     * @param binary Binary String of an image
     * @return A byte array of the product image.
     */
    public byte[] fromBinaryStringToByteArray(String binary)
    {
        int arrayLength = (binary.length() / 8);
        byte[] byteArray;
        byteArray = new byte[arrayLength];

        int substringRange = 0; //used for obtaining sub strings from the binary string.

        for (int i = 0; i < arrayLength; i++)
        {
            String iteratedByte = binary.substring(substringRange, substringRange + 8);
            substringRange += 8;

            short a = Short.parseShort(iteratedByte, 2);
            ByteBuffer bytes = ByteBuffer.allocate(2).putShort(a);
            byteArray[i] = bytes.get(1);
        }

        return byteArray;
    }

    public ArrayList<Alimento> getMenu()
    {
        ArrayList<Alimento> listaAlimentos = new ArrayList<>();

        try
        {
            //ResultSet result = sqlAdapter.connect("select top 20 nombre, imagen, precio from alimentos");
            //ResultSet result = sqlAdapter.connect("select nombre, imagen, precio from alimentos where idAlimento = 87");
            ResultSet result = sqlAdapter.connect("exec getMenu");

            Alimento alimento;
            String nombre;
            String precio;
            String imagen;

            if(result != null)
            {
                while(result.next())
                {
                    nombre = result.getString("nombre");
                    precio = result.getString("precio");
                    imagen = result.getString("imagen");
                    alimento = new Alimento(nombre, precio,imagen);
                    listaAlimentos.add(alimento);
                }

                if(listaAlimentos.size() == 0)
                    horarioComedor();
            }
            else
            {
                Log.i("\n\n\n-SQL NULL ------\n\n\n","-----------------------------");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return listaAlimentos;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_menu_dia, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId()== R.id.menu_reset)
        {
            tvConsumoTotal.setText(getResources().getString(R.string.consumo_total));
            consumoTotal = 0;
        }
        else if(item.getItemId() == R.id.menu_horario_comedor)
        {
            horarioComedor();
        }

        return super.onOptionsItemSelected(item);
    }

    public void horarioComedor()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.horario_comedor, (ViewGroup) findViewById(R.id.root));

        builder.setTitle("Significado de colores")

                .setView(layout)

                .setPositiveButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                        dialog.cancel();
                        finish();
                    }
                });

        AlertDialog alertDialog = builder.create();

        alertDialog.show();
    }
}