package sv.gob.sansalvadorhistorico;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import sv.gob.sansalvadorhistorico.adapter.VideoAdapter;
import sv.gob.sansalvadorhistorico.modelos.Video;

public class RecorridosActivity extends AppCompatActivity {
    ArrayList<Video> items = new ArrayList<>();
    VideoAdapter videoAdapter = null;
    RelativeLayout rlHome,rlCalendario,rlPerfil,rlMap;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorridos);
        sharedPreferences = getSharedPreferences(this.getString(R.string.PREFS), 0);
        rlHome = findViewById(R.id.rlHome);

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
        ListView lstVideos = findViewById(R.id.lstRecorrido);
        mostrarVideos(lstVideos);
        lstVideos.setOnItemClickListener((parent, view, position, id) -> {
            Video video = (Video)lstVideos.getItemAtPosition(position);
            String videoUrl = video.getVideoUrl();
            Intent intent = new Intent(RecorridosActivity.this, Player360Activity.class);
            intent.putExtra("videoUrl",videoUrl);
            startActivity(intent);
        });



    }



    private void mostrarVideos(ListView lstVideos){
        items.add(new Video(1,"https://my.matterport.com/show/?m=MdURrp2Xx8z","Museo de la Moneda",R.drawable.museo_moneda));
        items.add(new Video(2,"https://my.matterport.com/show/?m=mrWoZNaA5Ro ","Parroquia Ma. Auxiliadora Don Rua",R.drawable.don_rua));
        items.add(new Video(3,"https://my.matterport.com/show/?m=gPo3XRi7Kbj","Plaza Cap. Gral. Gerardo Barrios",R.drawable.gerardo_barrios));
        items.add(new Video(4,"https://my.matterport.com/show/?m=hJNotvJbjwd","Iglesia el Rosario",R.drawable.el_rosario));
        items.add(new Video(5,"https://my.matterport.com/models/i51Y7kmsmDz","Plaza Francisco Morazán",R.drawable.plaza_morazan));
        items.add(new Video(6,"https://my.matterport.com/models/namQBta6kmu","Catedral Metropolitana de San Salvador",R.drawable.catedral));
        items.add(new Video(7,"https://my.matterport.com/models/qhEFCyA2kqf","Cementerio General Los Ilustres",R.drawable.los_ilustres));
        items.add(new Video(8,"https://my.matterport.com/show/?m=NmHVNDuefMX","Plaza Libertad",R.drawable.plaza_libertad));
        items.add(new Video(9,"https://youtu.be/eKovdv2srnY","Caminata Nocturna",R.drawable.caminata_nocturna));
        items.add(new Video(10,"https://youtu.be/x4D_Sg8w3_Y","Parque Cuscatlán",R.drawable.parque_cuscatlan));
        videoAdapter = new VideoAdapter(RecorridosActivity.this,items);
        lstVideos.setAdapter(videoAdapter);
    }
}