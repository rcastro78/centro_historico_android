package sv.gob.sansalvadorhistorico.fragment;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import sv.gob.sansalvadorhistorico.R;
import sv.gob.sansalvadorhistorico.adapter.MenuPerfilAdapter;
import sv.gob.sansalvadorhistorico.modelos.MenuPrincipal;

public class PerfilFragment extends Fragment {
    ArrayList<MenuPrincipal> items = new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    SharedPreferences sharedPreferences;
    String email="";
    ImageView imgFotoPerfil;
    TextView txtNombre,lblSaludo,lblHeader;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_perfil, container,
                false);
        sharedPreferences = getActivity().getSharedPreferences(this.getString(R.string.PREFS), 0);
        email = sharedPreferences.getString("email","");

        ListView lstOpciones = v.findViewById(R.id.lstOpciones);
        imgFotoPerfil = v.findViewById(R.id.imgFotoPerfil);
        txtNombre = v.findViewById(R.id.lblNombre);
        lblSaludo = v.findViewById(R.id.lblSaludo);
        lblHeader = v.findViewById(R.id.lblHeader);
        Typeface nexaRegular = Typeface.createFromAsset(getActivity().getAssets(),"fonts/avenir_next_lt_pro_reg.otf");
        Typeface nexaBold = Typeface.createFromAsset(getActivity().getAssets(),"fonts/avenir_next_lt_pro_bold.otf");
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




        llenarMenu();
        MenuPerfilAdapter adapter = new MenuPerfilAdapter(getActivity(),items);
        lstOpciones.setAdapter(adapter);





        return v;
    }


    private void llenarMenu(){
        items.clear();
        //int id, String nombre, int idImagen, int idColor
        items.add(new MenuPrincipal(1,this.getString(R.string.profile_info),R.drawable.perfil));
        items.add(new MenuPrincipal(2,this.getString(R.string.profile_notification),R.drawable.campana));
        items.add(new MenuPrincipal(3,this.getString(R.string.profile_trips),R.drawable.marker));
        items.add(new MenuPrincipal(4,this.getString(R.string.profile_friends),R.drawable.amigos));
        items.add(new MenuPrincipal(5,this.getString(R.string.profile_contact),R.drawable.menu_world));
        items.add(new MenuPrincipal(6,this.getString(R.string.profile_terms),R.drawable.terminos));
        items.add(new MenuPrincipal(7,this.getString(R.string.profile_close),R.drawable.terminos));
    }





}
