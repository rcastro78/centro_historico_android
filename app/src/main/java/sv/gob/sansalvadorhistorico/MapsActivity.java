package sv.gob.sansalvadorhistorico;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.icu.util.MeasureUnit;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sv.gob.sansalvadorhistorico.adapter.LugarAdapter;
import sv.gob.sansalvadorhistorico.adapter.SpinnerAdapter;
import sv.gob.sansalvadorhistorico.api.APIClient;
import sv.gob.sansalvadorhistorico.api.ISanSalvadorHistorico;
import sv.gob.sansalvadorhistorico.modelos.Categorias;
import sv.gob.sansalvadorhistorico.modelos.Lugar;
import sv.gob.sansalvadorhistorico.modelos.LugaresCategoria;
import sv.gob.sansalvadorhistorico.utilidades.CustomInfoWindowGoogleMap;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    SharedPreferences sharedPreferences;
    GoogleMap mGoogleMap;
    MarkerOptions markerOptions = new MarkerOptions();
    ArrayList<Lugar> lugares = new ArrayList<>();
    ArrayList<Categorias> cats = new ArrayList<>();
    ArrayList<LugaresCategoria> lugaresCat = new ArrayList<>();
    CustomInfoWindowGoogleMap customInfoWindowGoogleMap = null ;
    ISanSalvadorHistorico iSanSalvadorHistorico;
    private GoogleMap mMap;
    TextView txtBuscar;
    Spinner sprCategoria;
    Button btnBuscar;
    String term_id,cat_name;
    RelativeLayout rlHome,rlCalendario,rlPerfil,rl360;
    private static int BARES=1;
    private static int ENTRETENIM=2;
    private static int CULTURA=3;
    private static int RESTAURANTES=4;
    private static int ALOJAMIENTO=5;
    private static int PARQUES=6;

    //Filtro

    private static int Alojamiento=157;
    private static int Accommodation=535;
    private static int Bancos=69;
    private static int Banks=532;
    private static int Bares=197;
    private static int Bars=537;
    private static int Bibliotecas=468;
    private static int Libraries=547;
    private static int Comida=469;
    private static int Food=548;
    private static int Compras=470;
    private static int Shopping=549;
    private static int Instituciones=478;
    private static int Institutions=552;
    private static int Mercados=71;
    private static int Markets=533;
    private static int Museos=290;
    private static int Museums=539;
    private static int Otros=474;
    private static int Others=551;
    private static int Parada=223;
    private static int BusStations=538;
    private static int Parqueo=442;
    private static int Parking=541;
    private static int Religion=464;
    private static int ReligionEn=545;
    private static int Restaurantes=35;
    private static int Restaurants=531;
    private static int Parques=302;
    private static int Parks=540;
    private static int Salud=445;
    private static int Health=542;
    private static int Educativos=472;
    private static int Educational=550;
    private static int Municipales=452;
    private static int Municipal=544;
    private static int Interes=465;
    private static int Interest=546;
    private static int Emblematicos=183;
    private static int Emblematics=536;
    private static int Supermercados=145;
    private static int Supermarkets=534;
    private static int Tour=451;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        sharedPreferences = getSharedPreferences(this.getString(R.string.PREFS), 0);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        iSanSalvadorHistorico = APIClient.getSSHistoricoService();
        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/avenir_next_lt_pro_reg.otf");

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


        txtBuscar = findViewById(R.id.txtBuscar);
        txtBuscar.setTypeface(typeface);
        sprCategoria = findViewById(R.id.sprCategoria);
        btnBuscar = findViewById(R.id.btnBuscar);
        btnBuscar.setTypeface(typeface);
        btnBuscar.setOnClickListener(v->{


            if(!term_id.equalsIgnoreCase("0")){
                mGoogleMap.clear();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("idCat",Integer.parseInt(term_id));
                editor.apply();
                getLugares(cat_name,Integer.parseInt(term_id));
            }


            if(txtBuscar.getText().toString().length()>0) {
                getLugarNombre(txtBuscar.getText().toString());
            }

            if(txtBuscar.getText().toString().length()<=0 && term_id.equalsIgnoreCase("0")){
                Toast.makeText(getApplicationContext(),"No puedes buscar si no escribes algo o si no seleccionas una categoría",Toast.LENGTH_LONG).show();
            }


        });


        String nombre = sharedPreferences.getString("nombreCat","");
        boolean filtrado = sharedPreferences.getBoolean("filtrado",false);
        int idCat = sharedPreferences.getInt("idCat",0);
        if(filtrado){

          getLugares(nombre,idCat);
        }
        String lenguaje = Locale.getDefault().getLanguage();
        if(lenguaje.equalsIgnoreCase("es")) {
            getCategorias();
        }else{
            getCategories();
        }


        sprCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Categorias categoria = (Categorias)sprCategoria.getItemAtPosition(position);
                term_id = categoria.getTerm_id();
                cat_name = categoria.getName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }


    @Override
    public void onBackPressed() {
        
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */


    /*private void getLugares(){
        iSanSalvadorHistorico.getLugaresMapa().enqueue(new Callback<List<Lugar>>() {
            @Override
            public void onResponse(Call<List<Lugar>> call, Response<List<Lugar>> response) {
                if(response.isSuccessful()){
                List<Lugar> l = response.body();
                for (Lugar lugar:
                     l) {
                    lugares.add(new Lugar(lugar.getId(),lugar.getPost_title(),lugar.getUrl(),lugar.getPost_type(),lugar.getThumbnail(),
                            lugar.getLongitud(),lugar.getLongitud(),lugar.getTipo()));
                    //Agregar los markers al mapa

                        MarkerOptions markerOptions = new MarkerOptions();
                        if(lugar.getLatitud()!="" || lugar.getLongitud()!=""){
                            LatLng p = new LatLng(Double.parseDouble(lugar.getLatitud()), Double.parseDouble(lugar.getLongitud()));
                            markerOptions.position(p);
                            markerOptions.title(lugar.getPost_title());
                            mGoogleMap.addMarker(markerOptions);
                            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(p,18));
                        }


                }
                    customInfoWindowGoogleMap = new CustomInfoWindowGoogleMap(getActivity(),lugares);
                    mGoogleMap.setInfoWindowAdapter(customInfoWindowGoogleMap);
                }
            }

            @Override
            public void onFailure(Call<List<Lugar>> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }*/

    private void getCategorias(){
        iSanSalvadorHistorico.getCategorias().enqueue(new Callback<List<Categorias>>() {
            @Override
            public void onResponse(Call<List<Categorias>> call, Response<List<Categorias>> response) {
                if(response.isSuccessful()){

                    List<Categorias> lista = response.body();
                    Categorias cIni = null;
                    String lenguaje = Locale.getDefault().getLanguage();
                    if(lenguaje.equalsIgnoreCase("es")){
                        cIni = new Categorias("0","Selecciona...");
                    }
                    if(lenguaje.equalsIgnoreCase("en")){
                        cIni = new Categorias("0","Select...");
                    }
                    cats.add(cIni);
                    for (int i=0; i<lista.size(); i++){
                       cats.add(new Categorias(lista.get(i).getTerm_id(),lista.get(i).getName()));
                    }
                    SpinnerAdapter adapter = new SpinnerAdapter(MapsActivity.this,cats);
                    sprCategoria.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Categorias>> call, Throwable t) {
                Log.d("CATEGORIAS",t.getMessage());
            }
        });
    }
    private void getCategories(){
        iSanSalvadorHistorico.getCategories().enqueue(new Callback<List<Categorias>>() {
            @Override
            public void onResponse(Call<List<Categorias>> call, Response<List<Categorias>> response) {
                if(response.isSuccessful()){

                    List<Categorias> lista = response.body();
                    Categorias cIni = null;
                    String lenguaje = Locale.getDefault().getLanguage();
                    if(lenguaje.equalsIgnoreCase("es")){
                        cIni = new Categorias("0","Selecciona...");
                    }
                    if(lenguaje.equalsIgnoreCase("en")){
                        cIni = new Categorias("0","Select...");
                    }
                    cats.add(cIni);
                    for (int i=0; i<lista.size(); i++){
                        cats.add(new Categorias(lista.get(i).getTerm_id(),lista.get(i).getName()));
                    }
                    SpinnerAdapter adapter = new SpinnerAdapter(MapsActivity.this,cats);
                    sprCategoria.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Categorias>> call, Throwable t) {
                Log.d("CATEGORIAS",t.getMessage());
            }
        });
    }


    private void getLugarNombre(String q){
        iSanSalvadorHistorico.getLugaresPorNombre(q).enqueue(new Callback<List<Lugar>>() {
            @Override
            public void onResponse(Call<List<Lugar>> call, Response<List<Lugar>> response) {
                List<Lugar> lugares = response.body();
                ArrayList<Lugar> lgrs = new ArrayList<>();
                lgrs.addAll(lugares);
                for(Lugar l : lgrs){
                    if(l.getLatitud()!="" || l.getLongitud()!=""){
                        try {
                            LatLng p = new LatLng(Double.parseDouble(l.getLatitud()), Double.parseDouble(l.getLongitud()));
                            markerOptions.position(p);
                            BitmapDrawable bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.buscar_red);
                            Bitmap b = bitmapdraw.getBitmap();
                            Bitmap icon = Bitmap.createScaledBitmap(b, 64, 64, false);

                            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));
                            markerOptions.title(l.getPost_title());
                            mGoogleMap.addMarker(markerOptions);
                            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(p, 18));
                        }catch (Exception ex){

                        }
                    }

                }




            }

            @Override
            public void onFailure(Call<List<Lugar>> call, Throwable t) {

            }
        });
    }

    private void getLugares(String categoria, int idCat){
        String lenguaje = Locale.getDefault().getLanguage();
        Log.d("LENGUAJE APP",lenguaje);
        iSanSalvadorHistorico.getLugaresPorCategoria(categoria).enqueue(new Callback<List<LugaresCategoria>>() {
            @Override
            public void onResponse(Call<List<LugaresCategoria>> call, Response<List<LugaresCategoria>> response) {
                if(response.isSuccessful()){
                    lugaresCat.clear();
                    mGoogleMap.clear();
                    List<LugaresCategoria> l = response.body();
                    for (LugaresCategoria lugar:
                            l) {
                        //lugares.add(new Lugar(lugar.getId(),lugar.getPost_title(),lugar.getUrl(),lugar.getPost_type(),lugar.getThumbnail(),
                        //       lugar.getLongitud(),lugar.getLongitud(),lugar.getTipo()));
                        //Agregar los markers al mapa
                        if(lugar.getLatitud()!=null)

                            if(lenguaje.equalsIgnoreCase(lugar.getLanguage_code())) {
                                lugaresCat.add(new LugaresCategoria(lugar.getId(),
                                        lugar.getPost_title(),
                                        lugar.getCategoria(),
                                        "",
                                        lugar.getLatitud(),
                                        lugar.getLongitud(),
                                        lugar.getUrl(),
                                        lugar.getPost_content()));


                            }

                        Bitmap icon = null;

                        //Filtro

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

                        if(idCat==Alojamiento || idCat==Accommodation){
                            BitmapDrawable bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.alojamiento_red);
                            Bitmap b = bitmapdraw.getBitmap();
                            icon = Bitmap.createScaledBitmap(b, 64, 64, false);
                        }
                        if(idCat==Bancos || idCat==Banks){
                            BitmapDrawable bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.bancos_red);
                            Bitmap b = bitmapdraw.getBitmap();
                            icon = Bitmap.createScaledBitmap(b, 64, 64, false);
                        }
                        if(idCat==Bibliotecas || idCat==Libraries){
                            BitmapDrawable bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.templos_2_red);
                            Bitmap b = bitmapdraw.getBitmap();
                            icon = Bitmap.createScaledBitmap(b, 64, 64, false);
                        }
                        if(idCat==Comida || idCat==Food){
                            BitmapDrawable bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.comida_rapida_red);
                            Bitmap b = bitmapdraw.getBitmap();
                            icon = Bitmap.createScaledBitmap(b, 64, 64, false);
                        }

                        if(idCat==Compras || idCat==Shopping){
                            BitmapDrawable bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.centros_com);
                            Bitmap b = bitmapdraw.getBitmap();
                            icon = Bitmap.createScaledBitmap(b, 64, 64, false);
                        }
                        if(idCat==Instituciones || idCat==Institutions){
                            BitmapDrawable bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.monumentos_red);
                            Bitmap b = bitmapdraw.getBitmap();
                            icon = Bitmap.createScaledBitmap(b, 64, 64, false);
                        }

                        if(idCat==Museos  || idCat==Museums){
                            BitmapDrawable bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.cultura_red);
                            Bitmap b = bitmapdraw.getBitmap();
                            icon = Bitmap.createScaledBitmap(b, 64, 64, false);
                        }

                        if(idCat==Mercados || idCat==Markets){
                            BitmapDrawable bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.tiendas_red);
                            Bitmap b = bitmapdraw.getBitmap();
                            icon = Bitmap.createScaledBitmap(b, 64, 64, false);
                        }

                        if(idCat==Otros || idCat==Others){
                            BitmapDrawable bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.buscar_red);
                            Bitmap b = bitmapdraw.getBitmap();
                            icon = Bitmap.createScaledBitmap(b, 64, 64, false);
                        }
                        if(idCat==Parada  || idCat==BusStations){
                            BitmapDrawable bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.paradas_red);
                            Bitmap b = bitmapdraw.getBitmap();
                            icon = Bitmap.createScaledBitmap(b, 64, 64, false);
                        }
                        if(idCat==Religion  || idCat==ReligionEn){
                            BitmapDrawable bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.religion_red);
                            Bitmap b = bitmapdraw.getBitmap();
                            icon = Bitmap.createScaledBitmap(b, 64, 64, false);
                        }
                        if(idCat==Salud  || idCat==Health){
                            BitmapDrawable bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.templos_red);
                            Bitmap b = bitmapdraw.getBitmap();
                            icon = Bitmap.createScaledBitmap(b, 64, 64, false);
                        }

                        if(idCat==Educativos || idCat==Educational){
                            BitmapDrawable bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.academico_red);
                            Bitmap b = bitmapdraw.getBitmap();
                            icon = Bitmap.createScaledBitmap(b, 64, 64, false);
                        }

                        if(idCat==Municipales || idCat== Municipal){
                            BitmapDrawable bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.templos_red);
                            Bitmap b = bitmapdraw.getBitmap();
                            icon = Bitmap.createScaledBitmap(b, 64, 64, false);
                        }

                        if(idCat==Interes  || idCat==Interest){
                            BitmapDrawable bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.cines_red);
                            Bitmap b = bitmapdraw.getBitmap();
                            icon = Bitmap.createScaledBitmap(b, 64, 64, false);
                        }

                        if(idCat==Emblematicos || idCat==Emblematics){
                            BitmapDrawable bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.monumentos_red);
                            Bitmap b = bitmapdraw.getBitmap();
                            icon = Bitmap.createScaledBitmap(b, 64, 64, false);
                        }
                        if(idCat==Supermercados  || idCat==Supermarkets){
                            BitmapDrawable bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.super_red);
                            Bitmap b = bitmapdraw.getBitmap();
                            icon = Bitmap.createScaledBitmap(b, 64, 64, false);
                        }

                        if(idCat==BARES || idCat==Bares  || idCat==Bars){
                            BitmapDrawable bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.bares_red);
                            Bitmap b = bitmapdraw.getBitmap();
                            icon = Bitmap.createScaledBitmap(b, 64, 64, false);
                        }
                        if(idCat==ENTRETENIM ){
                            BitmapDrawable bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.cines_red);
                            Bitmap b = bitmapdraw.getBitmap();
                            icon = Bitmap.createScaledBitmap(b, 64, 64, false);
                        }
                        if(idCat==RESTAURANTES || idCat==Restaurantes  || idCat==Restaurants) {
                            BitmapDrawable bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.restaurantes_red);
                            Bitmap b = bitmapdraw.getBitmap();
                            icon = Bitmap.createScaledBitmap(b, 64, 64, false);
                        }

                        if(idCat==ALOJAMIENTO  || idCat==Accommodation){
                            BitmapDrawable bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.alojamiento_circ);
                            Bitmap b = bitmapdraw.getBitmap();
                            icon = Bitmap.createScaledBitmap(b, 64, 64, false);
                        }

                        if(idCat==CULTURA){
                            BitmapDrawable bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.cultura_mk);
                            Bitmap b = bitmapdraw.getBitmap();
                            icon = Bitmap.createScaledBitmap(b, 64, 64, false);
                        }

                        if(idCat==PARQUES || idCat==Parques  || idCat==Parking){
                            BitmapDrawable bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.parques_mk);
                            Bitmap b = bitmapdraw.getBitmap();
                            icon = Bitmap.createScaledBitmap(b, 64, 64, false);
                        }


                        if(lugar.getLatitud()!=null && lugar.getLongitud()!=null){
                            if(lugar.getLatitud()!="" || lugar.getLongitud()!=""){
                                try {
                                    LatLng p = new LatLng(Double.parseDouble(lugar.getLatitud()), Double.parseDouble(lugar.getLongitud()));
                                    markerOptions.position(p);
                                    markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));
                                    markerOptions.title(lugar.getPost_title());
                                    mGoogleMap.addMarker(markerOptions);
                                    mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(p, 18));
                                }catch (Exception ex){

                                }
                            }
                        }



                    }
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("filtrado",false);
                    editor.commit();
                    customInfoWindowGoogleMap = new CustomInfoWindowGoogleMap(MapsActivity.this,lugaresCat);
                    mGoogleMap.setInfoWindowAdapter(customInfoWindowGoogleMap);
                }
            }

            @Override
            public void onFailure(Call<List<LugaresCategoria>> call, Throwable t) {
                Toast.makeText(MapsActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }




    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap=googleMap;

        googleMap.getUiSettings().setMapToolbarEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(13.6995142,-89.1915935), 17f));

        googleMap.setOnInfoWindowClickListener(marker -> {

            LatLng pos = marker.getPosition();
            Double lat = pos.latitude;
            Double lng = pos.longitude;

            getLugaresCoord(String.valueOf(lat),String.valueOf(lng));
        });

    }

    private void getLugaresCoord(String lat, String lng){
        String lenguaje = Locale.getDefault().getLanguage();

        iSanSalvadorHistorico.getLugaresPorCoordenadas(lat,lng,lenguaje)
                .enqueue(new Callback<List<Lugar>>() {
                    @Override
                    public void onResponse(Call<List<Lugar>> call, Response<List<Lugar>> response) {
                        if(response.isSuccessful()){
                            List<Lugar> lugares = response.body();
                            ArrayList<Lugar> l = new ArrayList<>();
                            l.addAll(lugares);
                            for (Lugar lugar : l){
                                    getPost(lugar.getId(), lugar.getUrl(), lugar.getPost_title());
                            }

                         }
                    }

                    @Override
                    public void onFailure(Call<List<Lugar>> call, Throwable t) {
                            Log.d("CH-ERROR",t.getMessage());
                    }
                });
    }




    String content="";
    private String getPost(String pid,String url,String nomLugar){

        iSanSalvadorHistorico.getPostContent(pid)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        try {
                            content = response.body();
                        }catch(Exception ex){content="";}
                        Intent intent = new Intent(MapsActivity.this,LugarDetalleActivity.class);
                        intent.putExtra("url",url);
                        intent.putExtra("pid",pid);
                        intent.putExtra("nomLugar",nomLugar);
                        intent.putExtra("descLugar", content);
                        startActivity(intent);



                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        content = "";
                        Toast.makeText(getApplicationContext(),"Fallo: "+t.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });

        return content;
    }

}
