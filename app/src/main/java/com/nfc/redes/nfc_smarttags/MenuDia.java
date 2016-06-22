package com.nfc.redes.nfc_smarttags;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.nfc.NfcAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import android.widget.Toast;

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
    int estrellaEvaluar = 0;

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
        //sqlAdapter = new SQLAdapter();
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
        else if(item.getItemId() == R.id.menu_evaluar_menu)
        {
            evaluarMenu();
        }

        return super.onOptionsItemSelected(item);
    }

    public void horarioComedor()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.horario_comedor, (ViewGroup) findViewById(R.id.root));

        builder.setTitle("Menú no disponible")

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



    public void evaluarMenu()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        alertDialog.setIcon(R.drawable.icono_evaluar);
        alertDialog.setTitle("Evaluar Menú");

        LayoutInflater li = LayoutInflater.from(this);

        final View view = li.inflate(R.layout.evaluar_menu, null);

        final ImageView ivEstrella1 = (ImageView) view.findViewById(R.id.ivEstrella1);
        final ImageView ivEstrella2 = (ImageView) view.findViewById(R.id.ivEstrella2);
        final ImageView ivEstrella3 = (ImageView) view.findViewById(R.id.ivEstrella3);
        final ImageView ivEstrella4 = (ImageView) view.findViewById(R.id.ivEstrella4);
        final ImageView ivEstrella5 = (ImageView) view.findViewById(R.id.ivEstrella5);

        final ArrayList<ImageView> imageViewArrayList = new ArrayList<>();

        imageViewArrayList.add(ivEstrella1);
        imageViewArrayList.add(ivEstrella2);
        imageViewArrayList.add(ivEstrella3);
        imageViewArrayList.add(ivEstrella4);
        imageViewArrayList.add(ivEstrella5);

        ivEstrella1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                estrellaEvaluar = 1;
                pintar(imageViewArrayList);
            }
        });
        ivEstrella2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                estrellaEvaluar = 2;
                pintar(imageViewArrayList);
            }
        });
        ivEstrella3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                estrellaEvaluar = 3;
                pintar(imageViewArrayList);
            }
        });
        ivEstrella4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                estrellaEvaluar = 4;
                pintar(imageViewArrayList);
            }
        });
        ivEstrella5.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                estrellaEvaluar = 5;
                pintar(imageViewArrayList);
            }
        });

        alertDialog.setView(view);

        pintar(imageViewArrayList);

        alertDialog.setPositiveButton("Evaluar", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
                if(estrellaEvaluar > 0) 
                {
                    HTTPRequest httpRequest = new HTTPRequest();
                    try {
                        httpRequest.reviewFoodPostRequest(estrellaEvaluar);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(MenuDia.this, "Gracias por evaluar este menú", Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                }
                else
                    Toast.makeText(MenuDia.this, "Seleccione una calificación", Toast.LENGTH_SHORT).show();
            }
        });
        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.cancel();
            }
        });

        alertDialog.show();
    }

    public void pintar(ArrayList<ImageView> imageViewArrayList)
    {
        for(int i = 0; i < imageViewArrayList.size(); i++)
        {
            imageViewArrayList.get(i).setImageResource(R.drawable.icono_estrella_vacia);
        }

        for(int i = 0; i < estrellaEvaluar; i++)
        {
            imageViewArrayList.get(i).setImageResource(R.drawable.icono_estrella_llena);
        }
    }
}