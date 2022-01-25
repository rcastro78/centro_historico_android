package sv.gob.sansalvadorhistorico;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sv.gob.sansalvadorhistorico.adapter.EventoAdapter;
import sv.gob.sansalvadorhistorico.api.APIClient;
import sv.gob.sansalvadorhistorico.api.ISanSalvadorHistorico;
import sv.gob.sansalvadorhistorico.modelos.Evento;

public class MisEventosActivity extends AppCompatActivity {
    ArrayList<Evento> items = new ArrayList<>();
    EventoAdapter eventoAdapter = null;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    SharedPreferences sharedPreferences;
    ListView lstEventos;
    ImageView imgFotoPerfil;
    TextView txtNombre,lblSaludo,lblHeader;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_eventos);

        sharedPreferences = getSharedPreferences(this.getString(R.string.PREFS), 0);
        email = sharedPreferences.getString("email","");


        imgFotoPerfil = findViewById(R.id.imgFotoPerfil);
        txtNombre = findViewById(R.id.lblNombre);
        lblSaludo = findViewById(R.id.lblSaludo);
        lblHeader = findViewById(R.id.lblHeader);
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


        lstEventos = findViewById(R.id.lstEventos);
        getEventos(email);

        lstEventos.setOnItemClickListener((parent, view, position, id) -> {
            /*Evento evento = (Evento)lstEventos.getItemAtPosition(position);
            String nomEvento = evento.getPost_name();
            String desc = evento.getPost_content();
            String guid = evento.getGuid();
            Intent intent = new Intent(CalendarioEventosActivity.this,EventoDetalleActivity.class);
            intent.putExtra("nomEvento",nomEvento);
            intent.putExtra("detaEvento",desc);
            intent.putExtra("guid",guid);
            intent.putExtra("pid",evento.getId());
            startActivity(intent);
            finish();*/

        });

    }

    private void getEventos(String email){
        Query qEventos = db.collection("eventos").whereEqualTo("email",email);
        qEventos.get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                for(QueryDocumentSnapshot evento : task.getResult()){
                    String id = evento.getId();
                    String post_id = evento.get("post_id").toString();
                    String post_name = evento.get("post_name").toString();
                    String post_content = evento.get("post_content").toString();
                    String guid = evento.get("guid").toString();

                    items.add(new Evento(id,post_id,post_name,post_name,post_content,guid));

                }
                eventoAdapter = new EventoAdapter(MisEventosActivity.this,items);
                lstEventos.setAdapter(eventoAdapter);

            }
        });
    }
}