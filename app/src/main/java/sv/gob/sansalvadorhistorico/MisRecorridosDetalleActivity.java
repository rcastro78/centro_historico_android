package sv.gob.sansalvadorhistorico;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.EventLog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import sv.gob.sansalvadorhistorico.adapter.EventoAdapter;
import sv.gob.sansalvadorhistorico.modelos.Evento;

public class MisRecorridosDetalleActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    SharedPreferences sharedPreferences;
    ImageView imgFotoPerfil;
    TextView txtNombre,lblSaludo,lblHeader;
    String email,userFirebaseID;
    int cat=0;
    ArrayList<Evento> items = new ArrayList<>();
    EventoAdapter misRecorridosAdapter = null;
    ListView lstRecorridos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_recorridos_detalle);
        cat = getIntent().getIntExtra("categoria",0);
        sharedPreferences = getSharedPreferences(this.getString(R.string.PREFS), 0);
        email = sharedPreferences.getString("email","");
        userFirebaseID = sharedPreferences.getString("userFirebaseID","");
//Encabezado
        imgFotoPerfil = findViewById(R.id.imgFotoPerfil);
        txtNombre = findViewById(R.id.lblNombre);
        lblSaludo = findViewById(R.id.lblSaludo);
        lblHeader = findViewById(R.id.lblHeader);
        lstRecorridos = findViewById(R.id.lstRecorridos);
        Typeface nexaRegular = Typeface.createFromAsset(getAssets(),"fonts/avenir_next_lt_pro_reg.otf");
        Typeface nexaBold = Typeface.createFromAsset(getAssets(),"fonts/avenir_next_lt_pro_bold.otf");
        lblHeader.setTypeface(nexaBold);
        lblSaludo.setTypeface(nexaRegular);
        txtNombre.setTypeface(nexaBold);
        lblHeader.setText(getIntent().getStringExtra("nombreReco"));
        //Recuperar datos del usuario
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


        Query docRecorridos = db.collection("misRecorridos").whereEqualTo("email",email).whereEqualTo("categoria",cat);
        docRecorridos.get().addOnCompleteListener(task -> {
           if(task.isSuccessful()){

               for(QueryDocumentSnapshot recorridos : task.getResult()){
                   String post_id = recorridos.get("post_id").toString();
                   String nombre = recorridos.get("nombre").toString();
                   String url = recorridos.get("url").toString();
                   items.add(new Evento("0",post_id,nombre,nombre,"",url));
               }

               misRecorridosAdapter = new EventoAdapter(MisRecorridosDetalleActivity.this,items);
               lstRecorridos.setAdapter(misRecorridosAdapter);


           }
        });


        lstRecorridos.setOnItemClickListener((parent, view, position, id) -> {
            Evento evento = (Evento)lstRecorridos.getItemAtPosition(position);
            Intent intent = new Intent(MisRecorridosDetalleActivity.this,LugarDetalleActivity.class);
            intent.putExtra("url",evento.getGuid());
            intent.putExtra("pid",evento.getPost_id());
            intent.putExtra("nomLugar",evento.getPost_name());
            intent.putExtra("descLugar", evento.getPost_content());
            startActivity(intent);
        });


    }
}