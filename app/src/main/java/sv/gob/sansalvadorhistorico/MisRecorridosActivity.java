package sv.gob.sansalvadorhistorico;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Locale;

import sv.gob.sansalvadorhistorico.adapter.MenuRecorridosAdapter;
import sv.gob.sansalvadorhistorico.modelos.MisRecorridosMenu;

public class MisRecorridosActivity extends AppCompatActivity {
ArrayList<MisRecorridosMenu> menu = new ArrayList<>();
MenuRecorridosAdapter adapter = null;
GridView gdvMenu;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    SharedPreferences sharedPreferences;
    ImageView imgFotoPerfil;
    TextView txtNombre,lblSaludo,lblHeader;
    String email,userFirebaseID;
    RelativeLayout rlHome,rl360,rlCalendario,rlPerfil,rlMap;
    private static int Alojamiento=157;
    int alojamiento;
    private static int Bancos=69;
    int bancos;
    private static int Bares=197;
    int bares;
    private static int Bibliotecas=468;
    int biblio;
    private static int Comida=469;
    int comida;
    private static int Compras=470;
    int compras;
    private static int Instituciones=478;
    int instituc;
    private static int Mercados=71;
    int mercados;
    private static int Museos=290;
    int museos;
    private static int Otros=474;
    int otros;
    private static int Parada=223;
    int paradas;
    private static int Parqueo=442;
    int parqueos;
    private static int Religion=464;
    int religion;
    private static int Restaurantes=35;
    int restaurantes;
    private static int Parques=302;
    int parques;
    private static int Salud=445;
    int salud;
    private static int Educativos=472;
    int educativos;
    private static int Municipales=452;
    int municipales;
    private static int Interes=465;
    int interes;
    private static int Emblematicos=183;
    int emblematicos;
    private static int Supermercados=145;
    int supermercados;
    private static int Tour=451;
    int tour;



    private static int Accommodation=535;

    private static int Banks=532;

    private static int Bars=537;

    private static int Libraries=547;

    private static int Food=548;

    private static int Shopping=549;

    private static int Institutions=552;

    private static int Markets=533;

    private static int Museums=539;

    private static int Others=551;

    private static int BusStations=538;

    private static int Parking=541;

    private static int ReligionEn=545;

    private static int Restaurants=531;

    private static int Parks=540;

    private static int Health=542;

    private static int Educational=550;

    private static int Municipal=544;

    private static int Interest=546;

    private static int Emblematics=536;

    private static int Supermarkets=534;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_recorridos);
        gdvMenu = findViewById(R.id.gdvRecorridos);

        sharedPreferences = getSharedPreferences(this.getString(R.string.PREFS), 0);
        email = sharedPreferences.getString("email","");
        userFirebaseID = sharedPreferences.getString("userFirebaseID","");
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


//Encabezado
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


//fin encabezado

        crearMenu(email);


        gdvMenu.setOnItemClickListener((parent, view, position, id) -> {
            MisRecorridosMenu misRecorridosMenu = (MisRecorridosMenu)gdvMenu.getItemAtPosition(position);
            String nombre = misRecorridosMenu.getNombre();
            int cat = misRecorridosMenu.getIdMenu();
            Intent intent = new Intent(MisRecorridosActivity.this,MisRecorridosDetalleActivity.class);
            intent.putExtra("nombreReco",nombre);
            intent.putExtra("categoria",cat);
            startActivity(intent);

        });



    }


    private void crearMenu(String email){
        Query qRecorridos = db.collection("misRecorridos").whereEqualTo("email",email);
        qRecorridos.get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                for(QueryDocumentSnapshot evento : task.getResult()){
                    int idCat = Integer.parseInt(evento.get("categoria").toString());

                    /* private static int Alojamiento=157;
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
    private static int Tour=451;*/

                    if(idCat==Bares || idCat==Bars){
                        bares++;
                    }
                    if(idCat==Alojamiento || idCat==Accommodation){
                        alojamiento++;
                    }
                    if(idCat==Bancos || idCat==Banks){
                        bancos++;
                    }
                    if(idCat==Bibliotecas  || idCat==Libraries){
                        biblio++;
                    }
                    if(idCat==Comida || idCat==Food){
                        comida++;
                    }
                    if(idCat==Compras || idCat==Shopping){
                        compras++;
                    }
                    if(idCat==Instituciones || idCat==Institutions){
                        instituc++;
                    }
                    if(idCat==Mercados || idCat==Markets){
                        mercados++;
                    }
                    if(idCat==Museos || idCat==Museums){
                        museos++;
                    }
                    if(idCat==Otros || idCat==Others){
                        otros++;
                    }
                    if(idCat==Parada || idCat==BusStations){
                        paradas++;
                    }
                    if(idCat==Parqueo || idCat==Parking){
                        parqueos++;
                    }
                    if(idCat==Religion || idCat==ReligionEn){
                        religion++;
                    }
                    if(idCat==Restaurantes || idCat==Restaurants){
                        restaurantes++;
                    }
                    if(idCat==Parques || idCat==Parks){
                        parques++;
                    }
                    if(idCat==Salud || idCat==Health){
                        salud++;
                    }
                    if(idCat==Educativos || idCat==Educational){
                        educativos++;
                    }
                    if(idCat==Municipales || idCat==Municipal){
                        municipales++;
                    }
                    if(idCat==Interes || idCat==Interest){
                        interes++;
                    }
                    if(idCat==Emblematicos || idCat==Emblematics){
                        emblematicos++;
                    }
                    if(idCat==Supermercados || idCat==Supermarkets){
                        supermercados++;
                    }
                    if(idCat==Tour){
                        tour++;
                    }
                }
                String lenguaje = Locale.getDefault().getLanguage();
                if(lenguaje.equalsIgnoreCase("es")) {
                    if(alojamiento>0){
                        menu.add(new MisRecorridosMenu(Alojamiento,R.drawable.alojamiento_item,R.drawable.alojamiento_red,"Alojamiento",String.valueOf(alojamiento)));
                    }

                    if(bares>0){
                        menu.add(new MisRecorridosMenu(Bares,R.drawable.bares_item,R.drawable.bares_red,"Bares y Cafés",String.valueOf(bares)));
                    }

                    if(bancos>0){
                        menu.add(new MisRecorridosMenu(Bancos,R.drawable.cultura_item,R.drawable.bancos_red,"Bancos",String.valueOf(bancos)));
                    }
                    if(restaurantes>0){
                        menu.add(new MisRecorridosMenu(Restaurantes,R.drawable.restaurantes_item,R.drawable.restaurantes_red,"Restaurantes",String.valueOf(restaurantes)));
                    }
                    if(biblio>0){
                        menu.add(new MisRecorridosMenu(Bibliotecas,R.drawable.biblioteca_item,R.drawable.templos_2_red,"Bibliotecas",String.valueOf(biblio)));
                    }
                    if(comida>0){
                        menu.add(new MisRecorridosMenu(Bibliotecas,R.drawable.subway,R.drawable.comida_rapida_red,"Comida Rápida",String.valueOf(comida)));
                    }

                    if(compras>0){
                        menu.add(new MisRecorridosMenu(Compras,R.drawable.compras_item,R.drawable.centros_com,"Compras",String.valueOf(compras)));
                    }
                    if(instituc>0){
                        menu.add(new MisRecorridosMenu(Instituciones,R.drawable.instituciones_item,R.drawable.monumentos_red,"Instituciones",String.valueOf(instituc)));
                    }

                    if(mercados>0){
                        menu.add(new MisRecorridosMenu(Mercados,R.drawable.mercado_item,R.drawable.tiendas_red,"Mercados",String.valueOf(mercados)));
                    }

                    if(museos>0){
                        menu.add(new MisRecorridosMenu(Museos,R.drawable.museos_item,R.drawable.sitios_red,"Museos",String.valueOf(museos)));
                    }

                    if(otros>0){
                        menu.add(new MisRecorridosMenu(Museos,R.drawable.otros_item,R.drawable.buscar_red,"Otros",String.valueOf(otros)));
                    }

                    if(paradas>0){
                        menu.add(new MisRecorridosMenu(Parada,R.drawable.otros_item,R.drawable.paradas_red,"Paradas",String.valueOf(paradas)));
                    }
                    if(parqueos>0){
                        menu.add(new MisRecorridosMenu(Museos,R.drawable.parques_item,R.drawable.buscar_red,"Parqueos",String.valueOf(parqueos)));
                    }
                    if(religion>0){
                        menu.add(new MisRecorridosMenu(Museos,R.drawable.iglesia_item,R.drawable.religion_red,"Religión",String.valueOf(religion)));
                    }
                    if(parques>0){
                        menu.add(new MisRecorridosMenu(Parques,R.drawable.parques_item,R.drawable.parques_red,"Parques",String.valueOf(parques)));
                    }

                    if(salud>0){
                        menu.add(new MisRecorridosMenu(Salud,R.drawable.salud,R.drawable.templos_red,"Salud",String.valueOf(salud)));
                    }

                    if(educativos>0){
                        menu.add(new MisRecorridosMenu(Educativos,R.drawable.educativos_item,R.drawable.academico_red,"Serv. Educativos",String.valueOf(educativos)));
                    }

                    if(municipales>0){
                        menu.add(new MisRecorridosMenu(Municipales,R.drawable.municipales_item,R.drawable.templos_red,"Serv. Municipales",String.valueOf(municipales)));
                    }

                    if(interes>0){
                        menu.add(new MisRecorridosMenu(Municipales,R.drawable.entretenimiento_item,R.drawable.cines_red,"Sitios de Interés",String.valueOf(interes)));
                    }
                    if(supermercados>0){
                        menu.add(new MisRecorridosMenu(Supermercados,R.drawable.selectos,R.drawable.super_red,"Supermercados",String.valueOf(supermercados)));
                    }
                    if(tour>0){
                        menu.add(new MisRecorridosMenu(Tour,R.drawable.entretenimiento_item,R.drawable.icono_360_red,"Tour 360",String.valueOf(tour)));
                    }
                }else{
                    if(alojamiento>0){
                        menu.add(new MisRecorridosMenu(Alojamiento,R.drawable.alojamiento_item,R.drawable.alojamiento_red,"Accomodation",String.valueOf(alojamiento)));
                    }

                    if(bares>0){
                        menu.add(new MisRecorridosMenu(Bares,R.drawable.bares_item,R.drawable.bares_red,"Bars and Cafes",String.valueOf(bares)));
                    }

                    if(bancos>0){
                        menu.add(new MisRecorridosMenu(Bancos,R.drawable.cultura_item,R.drawable.bancos_red,"Banks",String.valueOf(bancos)));
                    }
                    if(restaurantes>0){
                        menu.add(new MisRecorridosMenu(Restaurantes,R.drawable.restaurantes_item,R.drawable.restaurantes_red,"Restaurants",String.valueOf(restaurantes)));
                    }
                    if(biblio>0){
                        menu.add(new MisRecorridosMenu(Bibliotecas,R.drawable.biblioteca_item,R.drawable.templos_2_red,"Libraries",String.valueOf(biblio)));
                    }
                    if(comida>0){
                        menu.add(new MisRecorridosMenu(Bibliotecas,R.drawable.subway,R.drawable.comida_rapida_red,"Fast Food",String.valueOf(comida)));
                    }

                    if(compras>0){
                        menu.add(new MisRecorridosMenu(Compras,R.drawable.compras_item,R.drawable.centros_com,"Shopping",String.valueOf(compras)));
                    }
                    if(instituc>0){
                        menu.add(new MisRecorridosMenu(Instituciones,R.drawable.instituciones_item,R.drawable.monumentos_red,"Institutions",String.valueOf(instituc)));
                    }

                    if(mercados>0){
                        menu.add(new MisRecorridosMenu(Mercados,R.drawable.mercado_item,R.drawable.tiendas_red,"Markets",String.valueOf(mercados)));
                    }

                    if(museos>0){
                        menu.add(new MisRecorridosMenu(Museos,R.drawable.museos_item,R.drawable.sitios_red,"Museums",String.valueOf(museos)));
                    }

                    if(otros>0){
                        menu.add(new MisRecorridosMenu(Museos,R.drawable.otros_item,R.drawable.buscar_red,"Others",String.valueOf(otros)));
                    }

                    if(paradas>0){
                        menu.add(new MisRecorridosMenu(Parada,R.drawable.otros_item,R.drawable.paradas_red,"Bus Stations",String.valueOf(paradas)));
                    }
                    if(parqueos>0){
                        menu.add(new MisRecorridosMenu(Museos,R.drawable.parques_item,R.drawable.buscar_red,"Parking Lots",String.valueOf(parqueos)));
                    }
                    if(religion>0){
                        menu.add(new MisRecorridosMenu(Museos,R.drawable.iglesia_item,R.drawable.religion_red,"Religion",String.valueOf(religion)));
                    }
                    if(parques>0){
                        menu.add(new MisRecorridosMenu(Parques,R.drawable.parques_item,R.drawable.parques_red,"Parks",String.valueOf(parques)));
                    }

                    if(salud>0){
                        menu.add(new MisRecorridosMenu(Salud,R.drawable.salud,R.drawable.templos_red,"Health",String.valueOf(salud)));
                    }

                    if(educativos>0){
                        menu.add(new MisRecorridosMenu(Educativos,R.drawable.educativos_item,R.drawable.academico_red,"Educational Serv.",String.valueOf(educativos)));
                    }

                    if(municipales>0){
                        menu.add(new MisRecorridosMenu(Municipales,R.drawable.municipales_item,R.drawable.templos_red,"Municipals Serv.",String.valueOf(municipales)));
                    }

                    if(interes>0){
                        menu.add(new MisRecorridosMenu(Municipales,R.drawable.entretenimiento_item,R.drawable.cines_red,"Places of interest",String.valueOf(interes)));
                    }
                    if(supermercados>0){
                        menu.add(new MisRecorridosMenu(Supermercados,R.drawable.selectos,R.drawable.super_red,"Supermarkets",String.valueOf(supermercados)));
                    }
                    if(tour>0){
                        menu.add(new MisRecorridosMenu(Tour,R.drawable.entretenimiento_item,R.drawable.icono_360_red,"Tour",String.valueOf(tour)));
                    }
                }

                adapter = new MenuRecorridosAdapter(MisRecorridosActivity.this,menu);
                gdvMenu.setAdapter(adapter);
            }
        });
    }


}