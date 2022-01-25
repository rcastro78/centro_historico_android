package sv.gob.sansalvadorhistorico.fragment;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ShareActionProvider;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import sv.gob.sansalvadorhistorico.PrincipalActivity;
import sv.gob.sansalvadorhistorico.R;
import sv.gob.sansalvadorhistorico.adapter.MenuAdapter;
import sv.gob.sansalvadorhistorico.modelos.MenuPrincipal;

import static sv.gob.sansalvadorhistorico.PrincipalActivity.tabLayout;

public class HomeFragment extends Fragment {
    ArrayList<MenuPrincipal> items = new ArrayList<>();
    SharedPreferences sharedPreferences;
    TextView lblTexto1,lblTexto2;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        sharedPreferences = getActivity().getSharedPreferences(this.getString(R.string.PREFS), 0);
        llenarMenu();
        lblTexto1 = getActivity().findViewById(R.id.lblTexto1);
        lblTexto2 = getActivity().findViewById(R.id.lblTexto2);
        Typeface regular = Typeface.createFromAsset(getActivity().getAssets(),"fonts/avenir_next_lt_pro_reg.otf");
        Typeface bold = Typeface.createFromAsset(getActivity().getAssets(),"fonts/avenir_next_lt_pro_bold.otf");
        lblTexto1.setTypeface(bold);
        lblTexto2.setTypeface(regular);
        GridView gdvMenu = v.findViewById(R.id.gdvMenu);
        MenuAdapter adapter = new MenuAdapter(getActivity(),items);
        gdvMenu.setAdapter(adapter);
        gdvMenu.setOnItemClickListener((parent, view, position, id) -> {
            MenuPrincipal menuPrincipal = (MenuPrincipal)gdvMenu.getItemAtPosition(position);
            String nombre = menuPrincipal.getNombre();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("filtrado",true);
            editor.putString("nombreCat",nombre);
            editor.apply();

            TabLayout.Tab tab = tabLayout.getTabAt(1);
            tab.select();



        });
        return v;
    }

    private void llenarMenu(){
        items.clear();
        //int id, String nombre, int idImagen, int idColor
        items.add(new MenuPrincipal(1,this.getString(R.string.bar),R.drawable.principal1));
        /*items.add(new MenuPrincipal(2,this.getString(R.string.entertainment),R.drawable.principal2));
        items.add(new MenuPrincipal(3,this.getString(R.string.culture),R.drawable.principal3));
        items.add(new MenuPrincipal(4,this.getString(R.string.restaurants),R.drawable.principal4));
        items.add(new MenuPrincipal(5,this.getString(R.string.religion),R.drawable.principal5));
        items.add(new MenuPrincipal(6,this.getString(R.string.plazas),R.drawable.principal6));*/
    }
}
