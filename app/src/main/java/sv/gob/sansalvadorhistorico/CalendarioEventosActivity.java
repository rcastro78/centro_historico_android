package sv.gob.sansalvadorhistorico;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sv.gob.sansalvadorhistorico.adapter.EventoAdapter;
import sv.gob.sansalvadorhistorico.api.APIClient;
import sv.gob.sansalvadorhistorico.api.ISanSalvadorHistorico;
import sv.gob.sansalvadorhistorico.modelos.Evento;

public class CalendarioEventosActivity extends AppCompatActivity {
    ArrayList<Evento> items = new ArrayList<>();
    EventoAdapter eventoAdapter = null;
    ISanSalvadorHistorico iSanSalvadorHistorico;
    ListView lstEventos;
    SearchView searchView;
    private android.widget.SearchView.OnQueryTextListener queryTextListener;
    RelativeLayout rlHome,rlCalendario,rlPerfil,rl360,rlMap;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario_eventos);
        iSanSalvadorHistorico = APIClient.getSSHistoricoService();
        sharedPreferences = getSharedPreferences(this.getString(R.string.PREFS), 0);
        searchView  = findViewById(R.id.searchView);
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






        lstEventos = findViewById(R.id.lstEventos);
        getEventos();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                eventoAdapter.getFilter().filter(newText);
                eventoAdapter.notifyDataSetChanged();
                lstEventos.setAdapter(eventoAdapter);
                return true;
            }
        });




        lstEventos.setOnItemClickListener((parent, view, position, id) -> {
            Evento evento = (Evento)lstEventos.getItemAtPosition(position);
            String nomEvento = evento.getPost_title();
            String desc = evento.getPost_content();
            String guid = evento.getGuid();
            Intent intent = new Intent(CalendarioEventosActivity.this,EventoDetalleActivity.class);
            intent.putExtra("nomEvento",nomEvento);
            intent.putExtra("detaEvento",desc);
            intent.putExtra("guid",guid);
            intent.putExtra("pid",evento.getId());
            startActivity(intent);
            finish();

        });

    }

    @Override
    public void onBackPressed() {

    }

    private void getEventos(){
        iSanSalvadorHistorico.getEventos()
                .enqueue(new Callback<List<Evento>>() {
                    @Override
                    public void onResponse(Call<List<Evento>> call, Response<List<Evento>> response) {
                        if(response.isSuccessful()){
                            if(response.isSuccessful()){

                                List<Evento> eventos = response.body();
                                for (Evento e:
                                        eventos) {
                                    items.add(new Evento(
                                            e.getId(),
                                            e.getPost_id(),
                                            e.getPost_name(),
                                            e.getPost_title(),
                                            e.getPost_content(),
                                            e.getGuid()));


                                }
                                eventoAdapter = new EventoAdapter(CalendarioEventosActivity.this,items);
                                lstEventos.setAdapter(eventoAdapter);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Evento>> call, Throwable t) {

                    }
                });
    }
}