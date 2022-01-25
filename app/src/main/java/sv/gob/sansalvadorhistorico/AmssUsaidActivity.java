package sv.gob.sansalvadorhistorico;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class AmssUsaidActivity extends AppCompatActivity {
ImageView imgAMSS,imgUSAID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amss_usaid);
        imgAMSS = findViewById(R.id.imgAMSS);
        imgUSAID = findViewById(R.id.imgUSAID);

        imgAMSS.setOnClickListener(v->{
            String url = "http://www.sansalvador.gob.sv";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        });

        imgUSAID.setOnClickListener(v->{
            String url = "http://www.usaid.gov";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        });

        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                    Intent mainIntent = new Intent().setClass(AmssUsaidActivity.this, EmpezarActivity.class);
                    startActivity(mainIntent);



                //Validar que el email registrado exista


                finish();//Destruimos esta activity para prevenir que el usuario retorne aqui presionando el boton Atras.
            }
        };


        Timer timer = new Timer();
        timer.schedule(task, 3000);


    }
}