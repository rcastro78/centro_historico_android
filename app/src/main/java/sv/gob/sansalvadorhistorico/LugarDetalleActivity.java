package sv.gob.sansalvadorhistorico;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sv.gob.sansalvadorhistorico.api.APIClient;
import sv.gob.sansalvadorhistorico.api.ISanSalvadorHistorico;

public class LugarDetalleActivity extends AppCompatActivity {
ImageView imgFotoLugar;
TextView lblNomLugar;
WebView webDescLugar;
String nombre,detalle,guid;
    FloatingActionButton fabGuardarRecorrido;
    String email, url;
    String userFirebaseID;
    int registrado=0;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    SharedPreferences sharedPreferences;
    RelativeLayout rlHome,rl360,rlCalendario,rlPerfil,rlMap;
    ISanSalvadorHistorico iSanSalvadorHistorico;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lugar_detalle);
        iSanSalvadorHistorico = APIClient.getSSHistoricoService();
        sharedPreferences = getSharedPreferences(this.getString(R.string.PREFS), 0);
        email = sharedPreferences.getString("email","");
        registrado = sharedPreferences.getInt("registrado",0);
        userFirebaseID = sharedPreferences.getString("userFirebaseID","");
        lblNomLugar = findViewById(R.id.lblNomLugar);
        webDescLugar = findViewById(R.id.webDescLugar);
        imgFotoLugar = findViewById(R.id.imgFotoLugar);
        rlHome = findViewById(R.id.rlHome);
        rl360 = findViewById(R.id.rlRecorrido);
        rlCalendario = findViewById(R.id.rlCal);
        rlPerfil = findViewById(R.id.rlPerfil);
        rlMap = findViewById(R.id.rlMarker);


        rlMap.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("filtrado",false);

            Intent intent = new Intent(this,MapsActivity.class);
            startActivity(intent);
            finish();
        });

        rl360.setOnClickListener(v -> {
            Intent intent = new Intent(this,RecorridosActivity.class);
            startActivity(intent);
            finish();
        });

        rlHome.setOnClickListener(v -> {
            Intent intent = new Intent(this,PrincipalActivity2.class);
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


        webDescLugar.getSettings().setJavaScriptEnabled(true);
        webDescLugar.setWebViewClient(new WebViewClient() {
            @SuppressWarnings("deprecation")
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(LugarDetalleActivity.this, description, Toast.LENGTH_SHORT).show();
            }
            @TargetApi(android.os.Build.VERSION_CODES.M)
            @Override
            public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
                // Redirect to deprecated method, so you can use it in all SDK versions
                onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
            }
        });
        String pid = getIntent().getStringExtra("pid");
        webDescLugar.loadUrl("https://www.sansalvadorhistorico.com/app/getPostContentTempB.php?pid="+pid);
        getUrlFoto(pid);
        fabGuardarRecorrido = findViewById(R.id.fabGuardarRecorrido);
        Typeface  tf = Typeface.createFromAsset(getAssets(),"fonts/avenir_next_lt_pro_reg.otf");
        Typeface  tfBold = Typeface.createFromAsset(getAssets(),"fonts/avenir_next_lt_pro_bold.otf");

        nombre = getIntent().getStringExtra("nomLugar");
        /*detalle = getIntent().getStringExtra("descLugar");
        detalle = detalle.replace("Horario:","<br>Horario:");
        detalle = detalle.replace("Schedule:","<br>Schedule:");
        detalle = detalle.replace("Entrada:","<br>Entrada:");
        detalle = detalle.replace("Misas:","<br>Misas:");
        detalle = detalle.replace("Visitas Guiadas:","<br>Visitas Guiadas:");
        guid = getIntent().getStringExtra("url");*/
        /*lblDetalle.setTypeface(tf);
        try {
            lblDetalle.setText(Html.fromHtml("<p align='justify'>"+detalle+"</p>"));
        }catch (Exception ex){
            lblDetalle.setText("");
        }*/




        lblNomLugar.setTypeface(tfBold);
        lblNomLugar.setText(nombre);




        fabGuardarRecorrido.setOnClickListener(v -> {
            if(registrado>0) {
                Map<String, Object> participacion = new HashMap<>();
                int idCat = sharedPreferences.getInt("idCat", 0);
                String _url = sharedPreferences.getString("url", "");
                participacion.put("email", email);
                participacion.put("nombre", nombre);
                participacion.put("userFirebaseID", userFirebaseID);
                participacion.put("fechaRegistro", new Date().getTime());
                participacion.put("categoria", idCat);
                participacion.put("url", _url);
                participacion.put("post_id", getIntent().getStringExtra("pid"));

                db.collection("misRecorridos")
                        .add(participacion)
                        .addOnFailureListener(e -> {
                            Toast.makeText(getApplicationContext(), this.getString(R.string._no_route), Toast.LENGTH_LONG).show();
                        })
                        .addOnSuccessListener(documentReference -> {
                            Toast.makeText(getApplicationContext(), this.getString(R.string._route), Toast.LENGTH_LONG).show();
                            //Intent intent = new Intent(EventoDetalleActivity.this,CalendarioEventosActivity.class);
                            //startActivity(intent);
                            finish();
                        });
            }else{
                Toast.makeText(getApplicationContext(), this.getString(R.string._session_warning), Toast.LENGTH_LONG).show();
            }


        });


    }


    String content="";
    private String getUrlFoto(String pid){
        iSanSalvadorHistorico.getLugarFoto(pid)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(response.isSuccessful())
                            content = response.body();
                            content = content.replace("@","/");
                            Log.d("CH_LUGAR",content);
                            SharedPreferences.Editor  e = sharedPreferences.edit();
                            e.putString("url","https://"+content);
                            e.apply();
                        Glide.with(imgFotoLugar.getContext()).load("https://"+content)

                                .placeholder(R.drawable.logo_centro_2)
                                .error(R.drawable.logo_centro_2)

                                .centerCrop()
                                //.circleCrop()
                                .into(imgFotoLugar);


                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        content = "";
                        Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });



        return content;
    }


}
