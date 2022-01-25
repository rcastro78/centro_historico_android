package sv.gob.sansalvadorhistorico;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class SplashFondoActivity extends AppCompatActivity {
int activo=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_fondo);
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if(activo==1){
                    Intent mainIntent = new Intent().setClass(SplashFondoActivity.this, AmssUsaidActivity.class);
                    startActivity(mainIntent);
                }
                /*int valida = sharedPreferences.getInt("valida",0);*/
                if(activo==0){
                    Intent mainIntent = new Intent().setClass(SplashFondoActivity.this, AmssUsaidActivity.class);
                    startActivity(mainIntent);
                }


                //Validar que el email registrado exista


                finish();//Destruimos esta activity para prevenir que el usuario retorne aqui presionando el boton Atras.
            }
        };


        Timer timer = new Timer();
        timer.schedule(task, 3000);
    }
}