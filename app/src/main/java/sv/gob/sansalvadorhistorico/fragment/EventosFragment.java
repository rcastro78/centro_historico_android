package sv.gob.sansalvadorhistorico.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sv.gob.sansalvadorhistorico.R;
import sv.gob.sansalvadorhistorico.adapter.EventoAdapter;
import sv.gob.sansalvadorhistorico.adapter.MenuAdapter;
import sv.gob.sansalvadorhistorico.api.APIClient;
import sv.gob.sansalvadorhistorico.api.ISanSalvadorHistorico;
import sv.gob.sansalvadorhistorico.modelos.Evento;
import sv.gob.sansalvadorhistorico.modelos.MenuPrincipal;

public class EventosFragment extends Fragment {
    ArrayList<Evento> items = new ArrayList<>();
    EventoAdapter eventoAdapter = null;
    ISanSalvadorHistorico iSanSalvadorHistorico;
    ListView lstEventos;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        iSanSalvadorHistorico = APIClient.getSSHistoricoService();
        View v = inflater.inflate(R.layout.fragment_calendario, container, false);
        lstEventos = v.findViewById(R.id.lstEventos);
        getEventos();

        return v;
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
                                   //items.add(new Evento(e.getId(),e.getPost_id(),e.getPost_name(),e.getPost_title(),e.getPost_content()));
                                }
                                eventoAdapter = new EventoAdapter(getActivity(),items);
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
