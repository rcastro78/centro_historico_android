package sv.gob.sansalvadorhistorico;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import sv.gob.sansalvadorhistorico.adapter.NotificacionAdapter;
import sv.gob.sansalvadorhistorico.db.NotificacionDB;
import sv.gob.sansalvadorhistorico.modelos.Notificacion;

public class NotificacionesActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    SharedPreferences sharedPreferences;
    String email="",userFirebaseID="";
    ImageView imgFotoPerfil;
    TextView txtNombre,lblSaludo,lblHeader;
    ArrayList<Notificacion> notificaciones = new ArrayList<>();
    ListView lstNotificaciones;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificaciones);
        sharedPreferences = getSharedPreferences(this.getString(R.string.PREFS), 0);
        email = sharedPreferences.getString("email","");
        userFirebaseID = sharedPreferences.getString("userFirebaseID","");
//Encabezado
        imgFotoPerfil = findViewById(R.id.imgFotoPerfil);
        txtNombre = findViewById(R.id.lblNombre);
        lblSaludo = findViewById(R.id.lblSaludo);
        lblHeader = findViewById(R.id.lblHeader);
        lstNotificaciones = findViewById(R.id.lstNotificaciones);
        Typeface nexaRegular = Typeface.createFromAsset(getAssets(),"fonts/avenir_next_lt_pro_reg.otf");
        Typeface nexaBold = Typeface.createFromAsset(getAssets(),"fonts/avenir_next_lt_pro_bold.otf");
        lblHeader.setTypeface(nexaBold);
        lblSaludo.setTypeface(nexaRegular);
        txtNombre.setTypeface(nexaBold);

        Query docRef = db.collection("usuarios").whereEqualTo("email",email);
        docRef.get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                QuerySnapshot document = task.getResult();
                String n = document.getDocuments().get(0).get("nombre").toString();
                String picUrl = document.getDocuments().get(0).get("picUrl").toString();
                txtNombre.setText(n);
                Glide.with(imgFotoPerfil.getContext()).load(picUrl)
                        .placeholder(R.drawable.perfil)
                        .error(android.R.drawable.ic_menu_camera)
                        .override(256, 256)
                        .circleCrop()
                        .into(imgFotoPerfil);
            }
        });
        cargarNotificaciones();

    }

    private void cargarNotificaciones(){
        ArrayList<NotificacionDB> dbNotificacion = new ArrayList<>();
        NotificacionAdapter adapter = null;
        List<NotificacionDB> list = new Select().from(NotificacionDB.class).execute();
        dbNotificacion.addAll(list);
        for (NotificacionDB notificacion:
             dbNotificacion) {
            //postId,postTitle,guid,fecha,content
            String postId = notificacion.postId;
            String postTitle = notificacion.post_title;
            String url = notificacion.guid;
            String fecha = notificacion.date_published;
            String content = notificacion.content;
            notificaciones.add(new Notificacion(postId,postTitle,content,url,fecha));
        }
        adapter = new NotificacionAdapter(NotificacionesActivity.this,notificaciones);
        lstNotificaciones.setAdapter(adapter);
    /*
    *  ArrayList<DBCircular> dbCirculares = new ArrayList<>();
        List<DBCircular> list = new Select().from(DBCircular.class).where("idUsuario=? AND favorita=0",idUsuario).execute();
        dbCirculares.addAll(list);
        //llenar el adapter
        for(int i=0; i<dbCirculares.size(); i++){
    * */
    }


}