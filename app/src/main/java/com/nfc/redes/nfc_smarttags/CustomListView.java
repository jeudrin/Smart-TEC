package com.nfc.redes.nfc_smarttags;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomListView extends ArrayAdapter<String>
{
    private final Activity context;
    private final String[] productos;
    private final Integer[] precios;
    private final ArrayList<Bitmap> images;

    public CustomListView(Activity context, String[] productos, Integer[] precios, ArrayList<Bitmap> images)
    {
        super(context, R.layout.custom_list_view, productos);
        this.context = context;
        this.productos = productos;
        this.precios = precios;
        this.images = images;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent)
    {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.custom_list_view, null, true);

        TextView producto = (TextView) rowView.findViewById(R.id.tvProducto);
        TextView precio = (TextView) rowView.findViewById(R.id.tvPrecio);
        ImageView productImage = (ImageView) rowView.findViewById(R.id.imageViewProductImage);

        producto.setText(productos[position]);
        precio.setText("₡ " + precios[position]);
        productImage.setImageBitmap(images.get(position));

        return rowView;
    }
}