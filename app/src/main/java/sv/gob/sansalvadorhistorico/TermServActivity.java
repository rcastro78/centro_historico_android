package sv.gob.sansalvadorhistorico;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class TermServActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    SharedPreferences sharedPreferences;
    ImageView imgFotoPerfil;
    TextView txtNombre,lblSaludo,lblHeader,lblTermServ;
    String email;
    int registrado=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_serv);
        sharedPreferences = getSharedPreferences(this.getString(R.string.PREFS), 0);
        email = sharedPreferences.getString("email","");
        registrado = sharedPreferences.getInt("registrado",0);
        imgFotoPerfil = findViewById(R.id.imgFotoPerfil);
        txtNombre = findViewById(R.id.lblNombre);
        lblSaludo = findViewById(R.id.lblSaludo);
        lblHeader = findViewById(R.id.lblHeader);
        lblTermServ = findViewById(R.id.lblTermServ);
        Typeface nexaRegular = Typeface.createFromAsset(getAssets(),"fonts/avenir_next_lt_pro_reg.otf");
        Typeface nexaBold = Typeface.createFromAsset(getAssets(),"fonts/avenir_next_lt_pro_bold.otf");
        lblHeader.setTypeface(nexaBold);
        lblSaludo.setTypeface(nexaRegular);
        txtNombre.setTypeface(nexaBold);
        lblTermServ.setTypeface(nexaRegular);
        if(registrado>0) {
            Query docRef = db.collection("usuarios").whereEqualTo("email", email);
            docRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
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
        }else{
            txtNombre.setText(this.getString(R.string._user));
        }

    }
}