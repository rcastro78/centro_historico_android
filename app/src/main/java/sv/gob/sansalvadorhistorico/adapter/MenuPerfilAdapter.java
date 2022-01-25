package sv.gob.sansalvadorhistorico.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import sv.gob.sansalvadorhistorico.R;
import sv.gob.sansalvadorhistorico.modelos.MenuPrincipal;

public class MenuPerfilAdapter extends BaseAdapter {
    protected Activity activity;
    protected ArrayList<MenuPrincipal> items;
    MenuPrincipal menuPrincipal;
    ViewHolder holder=new ViewHolder();
    String TAG="MenuAdapter";
    Typeface tf,tfBold;

    public MenuPerfilAdapter(Activity activity, ArrayList<MenuPrincipal> items) {
        this.activity = activity;
        this.items = items;
        tf = Typeface.createFromAsset(activity.getAssets(),"fonts/avenir_next_lt_pro_reg.otf");
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        menuPrincipal = items.get(position);
        ViewHolder holder = null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_menu_perfil, null);
            holder = new ViewHolder();
            holder.lblMenu = convertView.findViewById(R.id.lblItemPerfil);
            holder.imgMenu = convertView.findViewById(R.id.imgItemPerfil);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.lblMenu.setTypeface(tf);
        holder.lblMenu.setText(menuPrincipal.getNombre());
        holder.imgMenu.setImageResource(menuPrincipal.getIdImagen());
        //holder.rlMenu.setBackground(menuPrincipal.getIdColor());
        return convertView;
    }

    static class ViewHolder {
        TextView lblMenu;
        ImageView imgMenu;
    }

}

