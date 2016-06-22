package com.nfc.redes.nfc_smarttags;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class SmartTEC extends AppCompatActivity
{
    String ip;
    //String ipTec1 = "172";
    //String ipTec2 = "24";
    String ipActual1;
    String ipActual2;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_tec);


    }

    @Override
    protected void onPause()
    {
        super.onPause();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }


    //Starts MenuDia activity
    public void showMenu(View view)
    {

        //if(inTEC(ipTec1, ipTec2)){
            Intent intent = new Intent(this, MenuDia.class);
            startActivity(intent);
        //}else{
          //  Toast.makeText(SmartTEC.this, "Su red no pertenece al TEC", Toast.LENGTH_SHORT).show();
        //}
    }

    //Starts Cubiculos activity
    public void showLibrary(View view)
    {

        //if(inTEC(ipTec1, ipTec2)){
            Intent intent = new Intent(this, CubiculosDisponibles.class);
            startActivity(intent);
        //}else{
          //  Toast.makeText(SmartTEC.this, "Su red no pertenece al TEC", Toast.LENGTH_SHORT).show();
        //}
    }



    public String getLocalIpAddress() {
        String ip = "";
        int i = 0;
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();

                    i++;
                    if(i==2){
                        ip+= inetAddress.getHostAddress();
                    }

                }
            }
        } catch (Exception ex) {
            Log.e("IP Address", ex.toString());
        }
        return ip;
    }
/*
    public boolean inTEC(String seg1, String seg2){

        ip = getLocalIpAddress();
        ipActual1 = ip.substring(0,ip.indexOf("."));
        ipActual2 = ip.substring(ip.indexOf(".")+1,ip.indexOf(".", ip.indexOf(".") + 1));

        if((seg1.compareTo(ipActual1) == 0) && (seg2.compareTo(ipActual2) == 0)){
            return true;
        }

        return false;
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_smarttec, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId()== R.id.menu_about) {
            Intent intent = new Intent(this,Acercade.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

}