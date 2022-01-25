package sv.gob.sansalvadorhistorico;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class DatosPersonalesActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    SharedPreferences sharedPreferences;
    String email="";
    ImageView imgFotoPerfil;
    TextView txtNombre,lblSaludo,lblHeader;
    TextView lblNom,lblName,lblEm,lblEmail,lblTelefono,lblTelef;
    int registrado=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_personales);

        sharedPreferences = getSharedPreferences(this.getString(R.string.PREFS), 0);
        email = sharedPreferences.getString("email","");
        registrado = sharedPreferences.getInt("registrado",0);
        imgFotoPerfil = findViewById(R.id.imgFotoPerfil);
        txtNombre = findViewById(R.id.lblNombre);
        lblSaludo = findViewById(R.id.lblSaludo);
        lblHeader = findViewById(R.id.lblHeader);

        lblName = findViewById(R.id.lblName);
        lblNom = findViewById(R.id.lblNom);
        lblEm = findViewById(R.id.lblEm);
        lblEmail = findViewById(R.id.lblEmail);
        lblTelef = findViewById(R.id.lblTelef);
        lblTelefono = findViewById(R.id.lblTelefono);



        Typeface nexaRegular = Typeface.createFromAsset(getAssets(),"fonts/avenir_next_lt_pro_reg.otf");
        Typeface nexaBold = Typeface.createFromAsset(getAssets(),"fonts/avenir_next_lt_pro_bold.otf");
        lblHeader.setTypeface(nexaBold);
        lblSaludo.setTypeface(nexaRegular);
        txtNombre.setTypeface(nexaBold);
        lblName .setTypeface(nexaRegular);
        lblNom.setTypeface(nexaRegular);
        lblEm.setTypeface(nexaRegular);
        lblEmail.setTypeface(nexaRegular);
        lblTelef.setTypeface(nexaRegular);
        lblTelefono.setTypeface(nexaRegular);

        if(registrado>0){
            Query docRef = db.collection("usuarios").whereEqualTo("email",email);
            docRef.get().addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    QuerySnapshot document = task.getResult();
                    String n = document.getDocuments().get(0).get("nombre").toString();
                    String e = document.getDocuments().get(0).get("email").toString();
                    String t = document.getDocuments().get(0).get("telefono").toString();
                    String picUrl = document.getDocuments().get(0).get("picUrl").toString();
                    txtNombre.setText(n);
                    lblName.setText(n);
                    lblEmail.setText(e);
                    lblTelefono.setText(t);
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
            lblName.setText("");
            lblEmail.setText("");
            lblTelefono.setText("");
        }






    }
}