package sv.gob.sansalvadorhistorico;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.Map;

import sv.gob.sansalvadorhistorico.adapter.MenuAdapter;
import sv.gob.sansalvadorhistorico.modelos.MenuPrincipal;

public class PrincipalActivity2 extends AppCompatActivity {
    ArrayList<MenuPrincipal> items = new ArrayList<>();
    SharedPreferences sharedPreferences;
    RelativeLayout rl360,rlCalendario,rlPerfil,rlMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal2);

        sharedPreferences = getSharedPreferences(this.getString(R.string.PREFS), 0);
        llenarMenu();





        GridView gdvMenu = findViewById(R.id.gdvMenu);
        rl360 = findViewById(R.id.rlRecorrido);
        rlCalendario = findViewById(R.id.rlCal);
        rlPerfil = findViewById(R.id.rlPerfil);
        rlMap = findViewById(R.id.rlMarker);

        rlMap.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("filtrado",false);

            Intent intent = new Intent(PrincipalActivity2.this,MapsActivity.class);
            startActivity(intent);
            finish();
        });

        rl360.setOnClickListener(v -> {
            Intent intent = new Intent(PrincipalActivity2.this,RecorridosActivity.class);
            startActivity(intent);
            finish();
        });
        rlCalendario.setOnClickListener(v -> {
            Intent intent = new Intent(PrincipalActivity2.this,CalendarioEventosActivity.class);
            startActivity(intent);
            finish();
        });
        rlPerfil.setOnClickListener(v -> {
            Intent intent = new Intent(PrincipalActivity2.this,PerfilActivity.class);
            startActivity(intent);
            finish();
        });

        MenuAdapter adapter = new MenuAdapter(this,items);
        gdvMenu.setAdapter(adapter);
        gdvMenu.setOnItemClickListener((parent, view, position, id) -> {
            MenuPrincipal menuPrincipal = (MenuPrincipal)gdvMenu.getItemAtPosition(position);
            String nombre = menuPrincipal.getNombre();
            int idCat = menuPrincipal.getId();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("filtrado",true);
            editor.putString("nombreCat",nombre);
            editor.putInt("idCat",idCat);
            editor.apply();
            Intent intent = new Intent(PrincipalActivity2.this, MapsActivity.class);
            startActivity(intent);

        });


    }


          /*
                        *  private static int Alojamiento=157;
    private static int Bancos=69;
    private static int Bares=197;
    private static int Bibliotecas=468;
    private static int Comida=469;
    private static int Compras=470;
    private static int Instituciones=478;
    private static int Mercados=71;
    private static int Museos=290;
    private static int Otros=474;
    private static int Parada=223;
    private static int Parqueo=442;
    private static int Religion=464;
    private static int Restaurantes=35;
    private static int Parques=302;
    private static int Salud=445;
    private static int Educativos=472;
    private static int Municipales=452;
    private static int Interes=465;
    private static int Emblematicos=183;
    private static int Supermercados=145;
    private static int Tour=451;
                        * */

    private void llenarMenu(){
        items.clear();
        //int id, String nombre, int idImagen, int idColor
        items.add(new MenuPrincipal(197,this.getString(R.string.bar),R.drawable.principal1));
        items.add(new MenuPrincipal(546,this.getString(R.string.entertainment),R.drawable.principal2));
        items.add(new MenuPrincipal(183,this.getString(R.string.culture),R.drawable.principal3));
        items.add(new MenuPrincipal(35,this.getString(R.string.restaurants),R.drawable.principal4));
        items.add(new MenuPrincipal(464,this.getString(R.string.religion),R.drawable.principal5));
        items.add(new MenuPrincipal(302,this.getString(R.string.plazas),R.drawable.principal6));
    }
}