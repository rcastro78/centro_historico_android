package sv.gob.sansalvadorhistorico;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class IniciarSesionActivity extends AppCompatActivity {
    GoogleSignInClient mGoogleSignInClient;
    GoogleSignInOptions gso;
    SharedPreferences sharedPreferences;
    static int RC_SIGN_IN=999;
    TextView lblEncab01,lblEncab02,lblEncab03;
    Button btnFB,btnGoogle,btnSinRegistro;
    Typeface nexaRegular,nexaBold;
    CallbackManager callbackManager;
    String email,nombre,imagePerfilUrl;
    String apellido = "";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account==null){
             mGoogleSignInClient.signOut();
        }




    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("CENTRO_H", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
                        String msg = "Registro";
                        Log.d("CENTRO_H", token);
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                    }
                });




        FirebaseMessaging.getInstance().subscribeToTopic("centro_historico")
                .addOnCompleteListener(task -> {
                    String msg = "Suscrito";
                    if (!task.isSuccessful()) {
                        msg = "No suscrito";
                    }

                });



        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                //.requestIdToken("22651250-2h4dech3fs4lhk9d66jc655tqibbifto.apps.googleusercontent.com")
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        FacebookSdk.sdkInitialize(this.getApplicationContext());
        sharedPreferences = this.getSharedPreferences(this.getString(R.string.PREFS), 0);
        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d("Success", "Login");
                        final Profile profile = Profile.getCurrentProfile();

                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                (object, response) -> {
                                    Log.v("LoginActivity", object.toString());
                                    try {
                                        email = object.getString("email");
                                        nombre = object.getString("name").split(" ")[0];
                                        String picture = object.getString("picture");
                                        JSONObject jsonObject = new JSONObject(picture);
                                        String picUrl = jsonObject.getString("data");
                                        JSONObject jsonObject1 = new JSONObject(picUrl);
                                        String url1 = jsonObject1.getString("url");
                                        imagePerfilUrl = url1;

                                        try {
                                            apellido = object.getString("name").split(" ")[1];

                                        } catch (Exception e) {

                                        }
                                    } catch (Exception ex) {
                                    }
                                    //Si el email existe en Firebase, mandarlo al menu, si no, registrar

                                    Query docRef = db.collection("usuarios").whereEqualTo("email",email);
                                    docRef.get().addOnCompleteListener(task -> {
                                                if (task.isSuccessful()) {
                                                    QuerySnapshot document = task.getResult();
                                                    if(document.size()>0){
                                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                                        Intent intent = new Intent(IniciarSesionActivity.this, PrincipalActivity2.class);
                                                        editor.putInt("registrado",1);
                                                        editor.putString("userFirebaseID",document.getDocuments().get(0).getId());
                                                        editor.putString("email",email);
                                                        editor.apply();
                                                        startActivity(intent);
                                                    }else{
                                                        //No existe, registrar
                                                        Intent intent = new Intent(IniciarSesionActivity.this, RegistroActivity.class);
                                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                                        editor.putString("email",email);
                                                        editor.putString("picPerfil",imagePerfilUrl);
                                                        editor.putString("nombre",nombre+" "+apellido);
                                                        editor.apply();
                                                        startActivity(intent);
                                                    }
                                                }
                                            });



                                });

                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,gender,birthday,picture.type(large)");
                        request.setParameters(parameters);
                        request.executeAsync();





                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(IniciarSesionActivity.this, "Login Cancel", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(IniciarSesionActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        setContentView(R.layout.activity_iniciar_sesion);

        nexaRegular = Typeface.createFromAsset(getAssets(),"fonts/avenir_next_lt_pro_reg.otf");
        nexaBold = Typeface.createFromAsset(getAssets(),"fonts/avenir_next_lt_pro_bold.otf");
        lblEncab01 = findViewById(R.id.lblEncab01);
        lblEncab02 = findViewById(R.id.lblEncab02);
        lblEncab03 = findViewById(R.id.lblEncab03);
        btnFB = findViewById(R.id.btnLoginFB);
        btnGoogle = findViewById(R.id.btnLoginGoogle);
        btnSinRegistro = findViewById(R.id.btnSinRegistro);
        lblEncab01.setTypeface(nexaBold);
        lblEncab02.setTypeface(nexaRegular);
        lblEncab03.setTypeface(nexaRegular);
        btnFB.setTypeface(nexaBold);
        btnGoogle.setTypeface(nexaBold);
        btnSinRegistro.setTypeface(nexaBold);


        btnSinRegistro.setOnClickListener(v->
        {
            final SharedPreferences.Editor editor = sharedPreferences.edit();
            Intent intent = new Intent(IniciarSesionActivity.this, PrincipalActivity2.class);
            editor.putInt("registrado",0);
            editor.apply();
            startActivity(intent);
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                //.requestIdToken("22651250-2h4dech3fs4lhk9d66jc655tqibbifto.apps.googleusercontent.com")
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        try {
            if (ActivityCompat.checkSelfPermission(IniciarSesionActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(IniciarSesionActivity.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission( IniciarSesionActivity.this,android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {

            } else {
                ActivityCompat.requestPermissions(IniciarSesionActivity.this,
                        new String[]{Manifest.permission.CAMERA,Manifest.permission.CALL_PHONE, Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_CAMERA_PERMISSION);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        btnGoogle.setOnClickListener(v -> {
            signIn();
        });

        btnFB.setOnClickListener(v -> LoginManager.getInstance().logInWithReadPermissions(IniciarSesionActivity.this, Arrays.asList("public_profile", "email")));
    }


    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        //GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(IniciarSesionActivity.this);
        startActivityForResult(signInIntent, RC_SIGN_IN);
        /*email = account.getEmail();
        nombre = account.getDisplayName();
        apellido = account.getFamilyName();
        imagePerfilUrl = account.getPhotoUrl().getPath();
        Intent intent = new Intent(IniciarSesionActivity.this, RegistroActivity.class);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email",email);
        editor.putString("nombre",nombre+" "+apellido);
        editor.putString("picPerfil",imagePerfilUrl);
        editor.apply();
        startActivity(intent);


        ;*/
    }


    private void handleSignInResult(GoogleSignInResult result) {

        //Toast.makeText(getApplicationContext(), ""+result.getStatus().getStatusCode(), Toast.LENGTH_SHORT).show();

        if (result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();

            final SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("email", account.getEmail());

            editor.putString("nombre", account.getGivenName() + " " + account.getFamilyName());
            editor.putBoolean("viaGoogle", true);
            editor.putBoolean("viaFacebook", false);
            editor.putInt("valida", 1);
            String userPic = account.getPhotoUrl().toString();
            Log.d("LoginActivity",userPic);
            editor.putString("picPerfil",userPic);
            editor.apply();

            //si el email ya existe en firebase, extraer el firebaseUserId
            Query docRef = db.collection("usuarios").whereEqualTo("email",account.getEmail());
            docRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    QuerySnapshot document = task.getResult();
                    if(document.size()>0){

                        Intent intent = new Intent(IniciarSesionActivity.this, PrincipalActivity2.class);
                        editor.putInt("registrado",1);
                        editor.putString("userFirebaseID",document.getDocuments().get(0).getId());
                        editor.putString("email",account.getEmail());
                        editor.apply();
                        startActivity(intent);
                    }else{
                        //No existe, registrar
                        Intent intent = new Intent(IniciarSesionActivity.this, RegistroActivity.class);
//78:47:E3:19:D6:D4:FB:A2:98:AF:00:66:B5:57:F1:7A:D1:47:3A:7A
                        //AC:A5:06:7F:D7:4F:45:06:E4:4C:AF:56:F4:43:F8:0B:C5:09:93:11
                        editor.putString("email",email);
                        editor.putString("picPerfil",userPic);
                        editor.putString("nombre",account.getGivenName() + " " + account.getFamilyName());
                        editor.apply();
                        startActivity(intent);
                    }
                }
            });

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            try {
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                handleSignInResult(result);
            }catch (Exception ex){
                Toast.makeText(getApplicationContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
            }
        }else{
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }
}