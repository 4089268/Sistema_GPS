package com.example.salvador.sistema_gps;

import android.location.Location;
import android.os.Bundle;

/**
 * Created by Salvador on 16/02/2016.
 */
public class MyLocationListener implements android.location.LocationListener {

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        //este metodo se ejecuta cadavez que haiga un cambio en el estado del del proveedor de GPS
    }

    @Override
    public void onProviderEnabled(String provider) {
        //se ejecuta cuando el GPS esta activado

    }

    @Override
    public void onProviderDisabled(String provider) {
        // se ejecuta cuando el GPS esta desactivado

    }

    @Override
    public void onLocationChanged(Location location) {
        //este metodo se ejecuta cada vez que el GPS reciva nuevas coordenadas en el GPS
        location.getLatitude();
        location.getLongitude();

        ConexionServicioWeb x = new ConexionServicioWeb(192,168,173,1);

        String latitud = location.getLatitude()+"";
        String longitude = location.getLongitude()+"";

        x.AgregarCoordenadas(latitud,longitude,2,58,18,5,2016,2,7);

    }
}
