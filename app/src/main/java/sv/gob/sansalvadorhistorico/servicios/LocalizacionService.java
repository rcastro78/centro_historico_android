package sv.gob.sansalvadorhistorico.servicios;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sv.gob.sansalvadorhistorico.R;
import sv.gob.sansalvadorhistorico.receiver.AlarmaReceiver;
import sv.gob.sansalvadorhistorico.utilidades.Tracker;

public class LocalizacionService extends Service implements GpsStatus.Listener{
    Context context;
    SharedPreferences sharedPreferences = null;
    boolean gps_enabled = false;
    boolean network_enabled = false;
    private Timer timer = new Timer();
    double lat,lng,latAnt,lngAnt;
    int _velocidad,_altitud;
    private static final String BATT_ACTION="android.intent.action.BATTERY_CHANGED";
    private String[] PERMISSIONS_LOCATION = {
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION
    };
    int grados;
    int totalSatelites=0;
    LocationManager locationManager;
    Tracker tracker;
    Location  net_loc;
    int precision=5;
    AlarmaReceiver alarma = new AlarmaReceiver();


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        AlarmaReceiver alarma = new AlarmaReceiver();
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (Build.VERSION.SDK_INT >= 26) {
            String CHANNEL_ID = "my_channel_01";
            sharedPreferences = this.getSharedPreferences(this.getString(R.string.PREFS), 0);
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);

            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("")
                    .setContentText("").build();

            startForeground(1, notification);
        }
        return START_STICKY;
    }


    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.d("Localizacion","Proceso iniciado...");


        Tracker t = new Tracker(LocalizacionService.this);
        lat = t.getLatitude();
        lng = t.getLongitude();
        if(lat!=0 && lng!=0)
            Log.d("Geolocalizacion",""+lat+","+lng);
    }

    @Override
    public void onDestroy()
    {
        timer.cancel();
        Log.d("Localizacion","Proceso detenido...");

    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        Tracker t = new Tracker(this);
        lat = t.getLatitude();
        lng = t.getLongitude();
        if(lat!=0 && lng!=0)
            Log.d("Geolocalizacion 0",""+lat+","+lng);

        locationManager = (LocationManager) getApplicationContext()
                .getSystemService(Context.LOCATION_SERVICE);




        if (ContextCompat.checkSelfPermission( this,android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED )
        {
            return;
        }

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, precision*1000, 0, listener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, precision*1000, 0, listener);
        locationManager.addGpsStatusListener(this);

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        locationManager.requestSingleUpdate(criteria, listener, null);


        gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (!gps_enabled && network_enabled)
        {
            net_loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Tracker t = new Tracker(LocalizacionService.this);

            }
        }, 0, precision * 1000);



        if (Build.VERSION.SDK_INT >= 26) {
            String CHANNEL_ID = "my_channel_01";
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);

            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("")
                    .setContentText("").build();

            startForeground(1, notification);
        }

    }



    @Override
    public void onGpsStatusChanged(int event) {

    }


    private android.location.LocationListener listener = new android.location.LocationListener() {

        private Location mLastLocation;


        @Override
        public void onLocationChanged(Location pCurrentLocation) {
            lat = pCurrentLocation.getLatitude();
            lng = pCurrentLocation.getLongitude();
            _altitud = (int)pCurrentLocation.getAltitude();
            double speed = 0;
            if (this.mLastLocation != null)
                speed = Math.sqrt(
                        Math.pow(pCurrentLocation.getLongitude() - mLastLocation.getLongitude(), 2)
                                + Math.pow(pCurrentLocation.getLatitude() - mLastLocation.getLatitude(), 2)
                ) / (pCurrentLocation.getTime() - this.mLastLocation.getTime());
            //if there is speed from location
            if (pCurrentLocation.hasSpeed())
                //get location speed
                speed = pCurrentLocation.getSpeed();
            this.mLastLocation = pCurrentLocation;
            Location loc1 = new Location("");
            loc1.setLatitude(mLastLocation.getLatitude());
            loc1.setLongitude(mLastLocation.getLongitude());

            Location loc2 = new Location("");
            loc2.setLatitude(pCurrentLocation.getLatitude());
            loc2.setLongitude(pCurrentLocation.getLongitude());

            float distanceInMeters = loc1.distanceTo(loc2);
            float v = Math.round(3.6*speed);
            //aqu?? se est??n obteniendo cada 5 segundos.
            Log.i("Geolocalizacion lis", "Coordenadas (Changed): "+lat+","+lng+" moviendose a: "+v);



            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("latitud",""+lat);
            editor.putString("longitud",""+lng);
            editor.apply();

            //Enviar a recorrido dets




            //velocidad = (int)(3.6*(distanceInMeters/precision));


            //float v1 = Math.round(3.6*(distanceInMeters/precision));


            _velocidad = (int)v;


        }

        @Override
        public void onProviderDisabled(String provider) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onProviderEnabled(String provider) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            // TODO Auto-generated method stub

        }

    };


}