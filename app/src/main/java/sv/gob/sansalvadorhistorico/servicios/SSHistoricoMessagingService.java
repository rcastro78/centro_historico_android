package sv.gob.sansalvadorhistorico.servicios;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import sv.gob.sansalvadorhistorico.PrincipalActivity2;
import sv.gob.sansalvadorhistorico.R;
import sv.gob.sansalvadorhistorico.db.NotificacionDB;

public class SSHistoricoMessagingService extends FirebaseMessagingService {
    private static final String NOTIFICATION_ID_EXTRA = "notificationId";
    private static final String IMAGE_URL_EXTRA = "imageUrl";
    private static final String ADMIN_CHANNEL_ID ="admin_channel";
    private NotificationManager notificationManager;
    String token,email;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    SharedPreferences sharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
        sharedPreferences = getSharedPreferences(this.getString(R.string.PREFS), 0);
        email = sharedPreferences.getString("email","");
    }

    @Override
    public void onMessageReceived (RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext() ,
                ADMIN_CHANNEL_ID )
                .setSmallIcon(R.drawable. ic_launcher_foreground )
                .setContentTitle(remoteMessage.getData().get("title"))
                .setContentText(remoteMessage.getData().get("mipost"))
                .setAutoCancel(true)
                .setContentIntent(null) ;
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context. NOTIFICATION_SERVICE ) ;
        if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES. O ) {
            int importance = NotificationManager. IMPORTANCE_HIGH ;
            NotificationChannel notificationChannel = new
                    NotificationChannel( ADMIN_CHANNEL_ID , "NOTIFICATION_CHANNEL_NAME" , importance) ;
            mBuilder.setChannelId( ADMIN_CHANNEL_ID ) ;
            assert mNotificationManager != null;
            mNotificationManager.createNotificationChannel(notificationChannel) ;
        }
        assert mNotificationManager != null;
        mNotificationManager.notify(( int ) System. currentTimeMillis () ,
                mBuilder.build()) ;

        //Guardar los resultados de la notificación en la tabla
        /*
        * @Column(name="postId",unique = true)
    public String postId;
    @Column(name="post_title")
    public String post_title;
    @Column(name="guid")
    public String guid;
    @Column(name="date_published")
    public String date_published;
        * */
        String post_id = remoteMessage.getData().get("post_id");
        String post_title = remoteMessage.getData().get("post_title");
        String post_image_url = remoteMessage.getData().get("post_image_url");
        String date_published = remoteMessage.getData().get("date_published");
        String content = remoteMessage.getData().get("content");
        NotificacionDB notificacionDB = new NotificacionDB();
        notificacionDB.postId = post_id;
        notificacionDB.post_title = post_title;
        notificacionDB.guid = post_image_url;
        notificacionDB.date_published = date_published;
        notificacionDB.content = content;
        if(notificacionDB.save()>=1){
            Log.d("SSHISTORICO","Notificacion guardada!");
        }



    }

    private void sendNotification(){
        Uri soundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.calendario)//notification icon
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),
                        R.drawable.calendario))
                .setContentTitle("title")
                .setContentText("body")
                .setAutoCancel(true)
                .setSound(soundUri)
                .setContentIntent(null)
                .setNumber(1)

                .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL);


        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.e("TOKEN", s);
        String versionRelease = Build.VERSION.RELEASE;
        token = s;
        //Registrar este token en la base de datos y en el dispositivo
        Map<String,Object> registroDispositivo = new HashMap<>();
        registroDispositivo.put("token",s);
        registroDispositivo.put("email",email);
        registroDispositivo.put("fecha",new Date().getTime());
        db.collection("dispositivos").add(registroDispositivo)
                .addOnFailureListener(e -> {

                })
                .addOnSuccessListener(documentReference -> {

                });
    }

}
