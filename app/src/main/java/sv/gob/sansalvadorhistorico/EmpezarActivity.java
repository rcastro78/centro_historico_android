package sv.gob.sansalvadorhistorico;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EmpezarActivity extends AppCompatActivity {
TextView lbl01,lbl02;
    Typeface nexaRegular,nexaBold;
    Button btnEmpezar;
    int registrado=0;
    int valida=0;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empezar);
        sharedPreferences = this.getSharedPreferences(this.getString(R.string.PREFS), 0);
        registrado = sharedPreferences.getInt("registrado",0);
        valida = sharedPreferences.getInt("valida",0);
        nexaRegular = Typeface.createFromAsset(getAssets(),"fonts/avenir_next_lt_pro_reg.otf");
        nexaBold = Typeface.createFromAsset(getAssets(),"fonts/avenir_next_lt_pro_bold.otf");
        lbl01 = findViewById(R.id.lbl01);
        lbl02 = findViewById(R.id.lbl02);
        btnEmpezar = findViewById(R.id.btnEmpezar);
        lbl01.setTypeface(nexaBold);
        lbl02.setTypeface(nexaRegular);
        if(registrado==1){
            Intent intent = new Intent(EmpezarActivity.this,PrincipalActivity2.class);
            startActivity(intent);
            finish();
        }
        btnEmpezar.setTypeface(nexaBold);
        btnEmpezar.setOnClickListener(v -> {
            Intent intent = new Intent(EmpezarActivity.this,IniciarSesionActivity.class);
            startActivity(intent);
            finish();
        });
    }
}