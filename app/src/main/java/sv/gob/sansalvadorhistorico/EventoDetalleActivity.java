package sv.gob.sansalvadorhistorico;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class EventoDetalleActivity extends AppCompatActivity {
ImageView imgLugar;
TextView lblNomLugar,lblDetalle;
String nombre,detalle,guid;
Typeface tf,tfBold;
String email;
String userFirebaseID;
int registrado=0;
FloatingActionButton fabParticipar;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    SharedPreferences sharedPreferences;
    RelativeLayout rlHome,rlCalendario,rlPerfil,rl360;

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evento_detalle);
        sharedPreferences = getSharedPreferences(this.getString(R.string.PREFS), 0);
        email = sharedPreferences.getString("email","");
        registrado = sharedPreferences.getInt("registrado",0);
        userFirebaseID = sharedPreferences.getString("userFirebaseID","");
        tf = Typeface.createFromAsset(getAssets(),"fonts/avenir_next_lt_pro_reg.otf");
        tfBold = Typeface.createFromAsset(getAssets(),"fonts/avenir_next_lt_pro_bold.otf");
        imgLugar = findViewById(R.id.imgFotoLugar);
        lblDetalle = findViewById(R.id.lblDescLugar);
        lblNomLugar = findViewById(R.id.lblNomLugar);
        fabParticipar = findViewById(R.id.fabParticipar);
        nombre = getIntent().getStringExtra("nomEvento");
        detalle = getIntent().getStringExtra("detaEvento");
        guid = getIntent().getStringExtra("guid");
        lblDetalle.setTypeface(tf);
        lblDetalle.setText(Html.fromHtml(detalle));
        lblNomLugar.setTypeface(tfBold);
        lblNomLugar.setText(nombre);

        rlHome = findViewById(R.id.rlHome);
        rl360 = findViewById(R.id.rlRecorrido);
        rlCalendario = findViewById(R.id.rlCal);
        rlPerfil = findViewById(R.id.rlPerfil);


        rlHome.setOnClickListener(v -> {
            Intent intent = new Intent(this,PrincipalActivity2.class);
            startActivity(intent);
            finish();
        });
        rl360.setOnClickListener(v -> {
            Intent intent = new Intent(this,RecorridosActivity.class);
            startActivity(intent);
            finish();
        });
        rlCalendario.setOnClickListener(v -> {
            Intent intent = new Intent(this,CalendarioEventosActivity.class);
            startActivity(intent);
            finish();
        });
        rlPerfil.setOnClickListener(v -> {
            Intent intent = new Intent(this,PerfilActivity.class);
            startActivity(intent);
            finish();
        });


        Glide.with(imgLugar.getContext()).load(guid)
                .placeholder(R.drawable.portal)
                .error(R.drawable.portal)
                .centerCrop()
                //.circleCrop()
                .into(imgLugar);



        fabParticipar.setOnClickListener(v -> {
            if(registrado>0){
                Map<String,Object> participacion = new HashMap<>();
                participacion.put("email",email);
                participacion.put("userFirebaseID",userFirebaseID);
                participacion.put("fechaRegistro",new Date().getTime());
                participacion.put("guid",guid);
                participacion.put("post_id",getIntent().getStringExtra("pid"));
                participacion.put("post_name",nombre);
                participacion.put("post_title",nombre);
                participacion.put("post_content",detalle);
                //Revisar que este post_id no exista en la base para este usuario
                Query eventos = db.collection("eventos")
                        .whereEqualTo("post_id",getIntent().getStringExtra("pid"))
                        .whereEqualTo("userFirebaseID",userFirebaseID);

                eventos.get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot document = task.getResult();

                        if(document.size()<=0){
                            db.collection("eventos")
                                    .add(participacion)
                                    .addOnFailureListener(e -> {
                                        Toast.makeText(getApplicationContext(),this.getString(R.string._participate_no_event),Toast.LENGTH_LONG).show();
                                    })
                                    .addOnSuccessListener(documentReference -> {
                                        Toast.makeText(getApplicationContext(),this.getString(R.string._participate_event),Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(EventoDetalleActivity.this,CalendarioEventosActivity.class);
                                        startActivity(intent);
                                        finish();
                                    });
                        }else{
                            Toast.makeText(getApplicationContext(),this.getString(R.string._participate_already_event),Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(EventoDetalleActivity.this,CalendarioEventosActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });

            }else{
                Toast.makeText(getApplicationContext(), this.getString(R.string._session_warning), Toast.LENGTH_LONG).show();
            }



        });


    }
}