package com.nfc.redes.nfc_smarttags;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

import nfc_adapters.NFCTags;

public class SmartTEC extends AppCompatActivity
{
    NFCTags nfcTags;
    String ip;
    String ipTec1 = "172";
    String ipTec2 = "24";
    String ipActual1;
    String ipActual2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_tec);

        nfcTags = new NFCTags(this, this.getApplicationContext());

        ip = getLocalIpAddress();
        ipActual1 = ip.substring(0,ip.indexOf("."));
        ipActual2 = ip.substring(ip.indexOf(".")+1,ip.indexOf(".", ip.indexOf(".") + 1));
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

        if(intent.hasExtra(NfcAdapter.EXTRA_TAG))
        {
            String tagText = nfcTags.readTextFromTag(intent);

            if(tagText.equals("飯廳")){//Comedor
                showMenu(null);
            }else if(tagText.equals("藏書")){ //Biblioteca
                showLibrary(null);
            }

        }
    }

    //Starts MenuDia activity
    public void showMenu(View view)
    {
        if(inTEC(ipTec1, ipActual1) && inTEC(ipTec2, ipActual2)){
            Intent intent = new Intent(this, MenuDia.class);
            startActivity(intent);
        }else{
            Toast.makeText(SmartTEC.this, "Su red no pertenece al TEC", Toast.LENGTH_LONG).show();
        }
    }

    //Starts Cubiculos activity
    public void showLibrary(View view)
    {
        if(inTEC(ipTec1, ipActual1) && inTEC(ipTec2, ipActual2)){
            Intent intent = new Intent(this, CubiculosDisponibles.class);
            startActivity(intent);
        }else{
            Toast.makeText(SmartTEC.this, "Su red no pertenece al TEC", Toast.LENGTH_LONG).show();
        }
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

    public boolean inTEC(String seg1, String seg2){
        return seg1.compareTo(seg2) == 0;
    }
}