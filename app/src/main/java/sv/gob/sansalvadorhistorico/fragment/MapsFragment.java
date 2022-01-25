package sv.gob.sansalvadorhistorico.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sv.gob.sansalvadorhistorico.R;
import sv.gob.sansalvadorhistorico.api.APIClient;
import sv.gob.sansalvadorhistorico.api.ISanSalvadorHistorico;
import sv.gob.sansalvadorhistorico.modelos.Lugar;
import sv.gob.sansalvadorhistorico.modelos.LugaresCategoria;
import sv.gob.sansalvadorhistorico.utilidades.CustomInfoWindowGoogleMap;

public class MapsFragment extends Fragment implements GoogleMap.OnCameraMoveListener {
SharedPreferences sharedPreferences;
    GoogleMap mGoogleMap;
    Marker mCurrLocationMarker;
    ArrayList<Lugar> lugares = new ArrayList<>();
    ArrayList<LugaresCategoria> lugaresCat = new ArrayList<>();
    CustomInfoWindowGoogleMap customInfoWindowGoogleMap = null ;
    ISanSalvadorHistorico iSanSalvadorHistorico;
    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {
            mGoogleMap=googleMap;
            Double lat = Double.parseDouble(sharedPreferences.getString("latitud","0.0"));
            Double lng = Double.parseDouble(sharedPreferences.getString("longitud","0.0"));
            googleMap.getUiSettings().setMapToolbarEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);
            googleMap.getUiSettings().setZoomControlsEnabled(true);
            //LatLng latLng = new LatLng(lat, lng);
            //googleMap.addMarker(new MarkerOptions().position(latLng).title("Estoy aquí"));
            //
            //.animateCamera(CameraUpdateFactory.newLatLngZoom(cameraLatLng, cameraZoom));

            String nombre = sharedPreferences.getString("nombreCat","");
            boolean filtrado = sharedPreferences.getBoolean("filtrado",false);
            if(filtrado){
                Toast.makeText(getActivity(),nombre+"!!",Toast.LENGTH_LONG).show();
                getLugares(nombre);
            }

            //getLugares();

            googleMap.setOnInfoWindowClickListener(marker -> {
                Toast.makeText(getActivity(), marker.getId().replace("m", ""), Toast.LENGTH_LONG).show();
            });





        }
    };






    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        sharedPreferences = getActivity().getSharedPreferences(this.getString(R.string.PREFS), 0);
        iSanSalvadorHistorico = APIClient.getSSHistoricoService();

        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }


    @Override
    public void onCameraMove() {
        mCurrLocationMarker=null;
        mCurrLocationMarker.setPosition(mGoogleMap.getCameraPosition().target);
    }

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
    private void getLugares(String categoria){
        iSanSalvadorHistorico.getLugaresPorCategoria(categoria).enqueue(new Callback<List<LugaresCategoria>>() {
            @Override
            public void onResponse(Call<List<LugaresCategoria>> call, Response<List<LugaresCategoria>> response) {
                if(response.isSuccessful()){
                    List<LugaresCategoria> l = response.body();
                    for (LugaresCategoria lugar:
                            l) {
                        lugaresCat.add(new LugaresCategoria(
                                lugar.getId(),
                                lugar.getPost_title(),
                                lugar.getCategoria(),
                                lugar.getGuid(),
                                lugar.getLatitud(),
                                lugar.getLongitud(),
                                lugar.getUrl(),
                                lugar.getPost_content()));

                        MarkerOptions markerOptions = new MarkerOptions();
                        if(lugar.getLatitud()!="" || lugar.getLongitud()!=""){
                            LatLng p = new LatLng(Double.parseDouble(lugar.getLatitud()), Double.parseDouble(lugar.getLongitud()));
                            markerOptions.position(p);

                            markerOptions.title(lugar.getPost_title());
                            mGoogleMap.addMarker(markerOptions);
                            mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(p,18));
                        }


                    }
                    customInfoWindowGoogleMap = new CustomInfoWindowGoogleMap(getActivity(),lugaresCat);
                    mGoogleMap.setInfoWindowAdapter(customInfoWindowGoogleMap);
                }
            }

            @Override
            public void onFailure(Call<List<LugaresCategoria>> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}