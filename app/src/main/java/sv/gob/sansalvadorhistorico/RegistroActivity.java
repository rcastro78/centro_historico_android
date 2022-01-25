package sv.gob.sansalvadorhistorico;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class RegistroActivity extends AppCompatActivity {
ImageView imgFotoPerfil;
TextView lblSaludo,lblNombre,lblNom,lblHeader,txtNombre,lblEmail,txtEmail,
        lblTel,txtTelf,txtFNac,lblFNac;
Button btnRegistro;
    Typeface nexaRegular,nexaBold;
    SharedPreferences sharedPreferences;
    String email,nombre,fotoUrl;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        sharedPreferences = this.getSharedPreferences(this.getString(R.string.PREFS), 0);
        nexaRegular = Typeface.createFromAsset(getAssets(),"fonts/avenir_next_lt_pro_reg.otf");
        nexaBold = Typeface.createFromAsset(getAssets(),"fonts/avenir_next_lt_pro_bold.otf");
        email = sharedPreferences.getString("email","");
        nombre = sharedPreferences.getString("nombre","");
        fotoUrl = sharedPreferences.getString("picPerfil","");
        txtFNac = findViewById(R.id.txtFNac);
        lblFNac = findViewById(R.id.lblFNac);
        imgFotoPerfil = findViewById(R.id.imgFotoPerfil);
        lblSaludo = findViewById(R.id.lblSaludo);
        lblNombre = findViewById(R.id.lblNombre);
        lblNom = findViewById(R.id.lblNom);
        lblHeader = findViewById(R.id.lblHeader);
        txtNombre = findViewById(R.id.txtNombre);
        lblEmail = findViewById(R.id.lblEmail);
        txtEmail = findViewById(R.id.txtEmail);

        lblTel = findViewById(R.id.lblTel);
        txtTelf = findViewById(R.id.txtTelf);
        btnRegistro = findViewById(R.id.btnRegistro);
        lblFNac.setTypeface(nexaRegular);
        lblSaludo.setTypeface(nexaRegular);
        lblNombre.setTypeface(nexaBold);
        lblHeader.setTypeface(nexaBold);
        lblNom.setTypeface(nexaRegular);
        txtNombre.setTypeface(nexaRegular);
        lblEmail.setTypeface(nexaRegular);
        txtEmail.setTypeface(nexaRegular);

        lblTel.setTypeface(nexaRegular);
        txtTelf.setTypeface(nexaRegular);
        btnRegistro.setTypeface(nexaRegular);
        txtFNac.setTypeface(nexaRegular);
        lblNombre.setText(nombre);
        txtNombre.setText(nombre);
        txtEmail.setText(email);

     /*   db.collectionGroup("usuarios").whereEqualTo("email", email).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if(queryDocumentSnapshots.size()>0){
                        //Ya está registrado
                        String n = queryDocumentSnapshots.getDocuments().get(0).get("nombre").toString();
                        Toast.makeText(getApplicationContext(),"Bienvenido de nuevo, "+n,Toast.LENGTH_LONG).show();

                        //Por si los datos se perdieron
                        Intent intent = new Intent(RegistroActivity.this, PrincipalActivity.class);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("email",email);
                        editor.putString("picPerfil",fotoUrl);
                        editor.putString("nombre",nombre);
                        editor.apply();
                        startActivity(intent);

                    }
                        })
                    ;
*/




        Glide.with(imgFotoPerfil.getContext()).load(fotoUrl)
                .placeholder(R.drawable.perfil)
                .error(android.R.drawable.ic_menu_camera)
                .override(256, 256)
                .circleCrop()
                .into(imgFotoPerfil);

//78:47:E3:19:D6:D4:FB:A2:98:AF:00:66:B5:57:F1:7A:D1:47:3A:7A

        btnRegistro.setOnClickListener(v -> {

           Map<String,Object> registro = new HashMap<>();
            registro.put("nombre",nombre);
            registro.put("email",txtEmail.getText().toString());
            registro.put("picUrl",fotoUrl);
            registro.put("fechaNacimiento",txtFNac.getText().toString());
            registro.put("telefono",txtTelf.getText().toString());
            db.collection("usuarios")
                    .add(registro)
                    .addOnFailureListener(e -> {
                        Toast.makeText(getApplicationContext(),this.getString(R.string._register_error)+e.getMessage(),Toast.LENGTH_LONG).show();
                    })
                    .addOnSuccessListener(documentReference -> {
                        Log.d("Registro", "Usuario ID: " + documentReference.getId());
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("valida",1);
                        editor.putString("userFirebaseID",documentReference.getId());
                        editor.commit();
                        Toast.makeText(getApplicationContext(),this.getString(R.string._register_success),Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(RegistroActivity.this,PrincipalActivity2.class);
                        startActivity(intent);
                    })

            ;

        });


    }
}