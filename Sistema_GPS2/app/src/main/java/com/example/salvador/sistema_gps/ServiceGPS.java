package com.example.salvador.sistema_gps;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import java.nio.BufferUnderflowException;

/**
 * Created by Salvador on 18/05/2016.
 */

public class ServiceGPS extends Service {
    LocationManager locmanger;
    MyLocationListener loclist;

    ConexionServicioWeb x;

    String latitud, longitud;
    String ip;
    int camion,trans;


    @Override
    public void onCreate() {

        locmanger = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        loclist = new MyLocationListener(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locmanger.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, loclist);


        x= new ConexionServicioWeb("192.168.173.1");

        super.onCreate();
    }



    @Override
    public int onStartCommand(Intent intenc, int flags, int idArranque) {

        return START_STICKY;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    public void mostrarMensaje(String texto){
        Toast.makeText(this,texto, Toast.LENGTH_SHORT).show();

    }

    public void enviarcoordenadas(String latitud, String longitud){
        this.latitud = latitud;
        this.longitud = longitud;

        new asyRes().execute();
    }


    class asyRes extends AsyncTask<String,String,String> {
        @Override
        protected void onPreExecute() {
            //super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

                if(x.AgregarCoordenadas(latitud,longitud,18,18,24,5,2016,3,11)){
                    return "ok";
                }else{
                    return "err";
                }

        }

        @Override
        protected void onPostExecute(String s) {
            //super.onPostExecute(s);
            if(s.equals("ok")){
                mostrarMensaje("CoordenadasOk");

            }else {
                mostrarMensaje("Error mostrar coordenadas");
            }

        }
    }

}
