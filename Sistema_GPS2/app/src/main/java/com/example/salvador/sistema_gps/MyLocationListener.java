package com.example.salvador.sistema_gps;

import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by Salvador on 16/02/2016.
 */
public class MyLocationListener implements android.location.LocationListener {

    private ServiceGPS servicio;

    public MyLocationListener(ServiceGPS servicio) {
        this.servicio = servicio;
    }


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

        String latitud = location.getLatitude()+"";
        String longitude = location.getLongitude()+"";

        servicio.enviarcoordenadas(latitud, longitude);



    }
}
