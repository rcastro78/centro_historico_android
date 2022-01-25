package sv.gob.sansalvadorhistorico;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import sv.gob.sansalvadorhistorico.adapter.MenuPerfilAdapter;
import sv.gob.sansalvadorhistorico.modelos.MenuPrincipal;

public class PerfilActivity extends AppCompatActivity {
    ArrayList<MenuPrincipal> items = new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    SharedPreferences sharedPreferences;
    String email="";
    ImageView imgFotoPerfil;
    int registrado=0;
    TextView txtNombre,lblSaludo,lblHeader;
    GoogleSignInClient mGoogleSignInClient;
    GoogleSignInOptions gso;
    RelativeLayout rlHome,rl360,rlCalendario,rlPerfil,rlMap;
    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        sharedPreferences = getSharedPreferences(this.getString(R.string.PREFS), 0);
        registrado = sharedPreferences.getInt("registrado",0);
        rl360 = findViewById(R.id.rlRecorrido);
        rlCalendario = findViewById(R.id.rlCal);
        rlHome = findViewById(R.id.rlHome);
        rlMap = findViewById(R.id.rlMarker);

        rlMap.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("filtrado",false);

            Intent intent = new Intent(PerfilActivity.this,MapsActivity.class);
            startActivity(intent);
            finish();
        });

        rl360.setOnClickListener(v -> {
            Intent intent = new Intent(PerfilActivity.this,RecorridosActivity.class);
            startActivity(intent);
            finish();
        });
        rlCalendario.setOnClickListener(v -> {
            Intent intent = new Intent(PerfilActivity.this,CalendarioEventosActivity.class);
            startActivity(intent);
            finish();
        });
        rlHome.setOnClickListener(v -> {
            Intent intent = new Intent(PerfilActivity.this,PrincipalActivity2.class);
            startActivity(intent);
            finish();
        });


        email = sharedPreferences.getString("email","");
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        ListView lstOpciones = findViewById(R.id.lstOpciones);
        imgFotoPerfil = findViewById(R.id.imgFotoPerfil);
        txtNombre = findViewById(R.id.lblNombre);
        lblSaludo = findViewById(R.id.lblSaludo);
        lblHeader = findViewById(R.id.lblHeader);
        Typeface nexaRegular = Typeface.createFromAsset(getAssets(),"fonts/avenir_next_lt_pro_reg.otf");
        Typeface nexaBold = Typeface.createFromAsset(getAssets(),"fonts/avenir_next_lt_pro_bold.otf");
        lblHeader.setTypeface(nexaBold);
        lblSaludo.setTypeface(nexaRegular);
        txtNombre.setTypeface(nexaBold);
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




        llenarMenu();
        MenuPerfilAdapter adapter = new MenuPerfilAdapter(this,items);
        lstOpciones.setAdapter(adapter);

        lstOpciones.setOnItemClickListener((parent, view, position, id) -> {
            MenuPrincipal m = (MenuPrincipal)lstOpciones.getItemAtPosition(position);
            int idMenu = m.getId();

            if(idMenu==1)
            {
                if(registrado>0) {
                    Intent intent = new Intent(PerfilActivity.this, DatosPersonalesActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), this.getString(R.string._session_warning), Toast.LENGTH_LONG).show();
                }
            }
            if(idMenu==2)
            {
                if(registrado>0) {
                    Intent intent = new Intent(PerfilActivity.this, NotificacionesActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), this.getString(R.string._session_warning), Toast.LENGTH_LONG).show();
                }
            }

            if(idMenu==3)
            {
                if(registrado>0) {
                    Intent intent = new Intent(PerfilActivity.this, MisRecorridosActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), this.getString(R.string._session_warning), Toast.LENGTH_LONG).show();
                }
            }

            if(idMenu==4)
            {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Centro Histórico");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, this.getString(R.string._share)+"https://play.google.com/store/apps/details?id=sv.gob.sansalvadorhistorico");
                    shareIntent.setPackage("com.whatsapp");
                    startActivity(shareIntent);
                } catch(Exception e) {
                    Toast.makeText(getApplicationContext(),this.getString(R.string._whatsapp),Toast.LENGTH_LONG).show();
                }
            }

            if(idMenu==6)
            {
                Intent intent = new Intent(PerfilActivity.this,TermServActivity.class);
                startActivity(intent);
            }


            if(idMenu==7) {

                if (registrado > 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Centro Histórico");
                    builder.setMessage(this.getString(R.string._close_session));

                    builder.setPositiveButton(this.getString(R.string._accept), (dialog, which) -> {

                        //Cerrar Sesión en Facebook
                        LoginManager.getInstance().logOut();
                        //Cerrar Sesión en Google
                        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
                        if (account == null) {
                            mGoogleSignInClient.signOut();
                        }


                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("email", "");
                        editor.putString("nombre", "");
                        editor.putBoolean("viaGoogle", false);
                        editor.putBoolean("viaFacebook", false);
                        editor.putInt("valida", 0);
                        editor.putInt("registrado", 0);
                        editor.apply();

                        Intent intent = new Intent(PerfilActivity.this, IniciarSesionActivity.class);
                        startActivity(intent);
                        finish();
                    });
                    builder.setNegativeButton(this.getString(R.string._cancel), (dialog, which) -> {
                        dialog.dismiss();
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();

                }
            }else{
                Toast.makeText(getApplicationContext(), this.getString(R.string._session_warning), Toast.LENGTH_LONG).show();
            }

        });


    }


    private void llenarMenu(){
        items.clear();
        //int id, String nombre, int idImagen, int idColor
        items.add(new MenuPrincipal(1,this.getString(R.string.profile_info),R.drawable.perfil));
        items.add(new MenuPrincipal(2,this.getString(R.string.profile_notification),R.drawable.campana));
        items.add(new MenuPrincipal(3,this.getString(R.string.profile_trips),R.drawable.marker));
        items.add(new MenuPrincipal(4,this.getString(R.string.profile_friends),R.drawable.amigos));
        items.add(new MenuPrincipal(5,this.getString(R.string.profile_contact),R.drawable.menu_world));
        items.add(new MenuPrincipal(6,this.getString(R.string.profile_terms),R.drawable.terminos));
        items.add(new MenuPrincipal(7,this.getString(R.string.profile_close), android.R.drawable.ic_menu_close_clear_cancel));
    }


}