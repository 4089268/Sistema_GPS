package com.example.salvador.sistema_gps;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

/**
 * Created by Salvador on 18/05/2016.
 */

public class ServiceGPS extends Service {
    MediaPlayer reproductor;
    LocationManager locmanger;
    MyLocationListener loclist;

    @Override
    public void onCreate() {
        Toast.makeText(this, "Servicio creado", Toast.LENGTH_SHORT);

        locmanger = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        loclist = new MyLocationListener();

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
        locmanger.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) loclist);

        reproductor = MediaPlayer.create(this, R.raw.audio);

        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intenc, int flags, int idArranque) {
        Toast.makeText(this, "Servicio Arrancado "+idArranque, Toast.LENGTH_SHORT);

        reproductor.start();

        return START_STICKY;
    }

    @Override
    public void onDestroy(){
        reproductor.stop();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }
}
