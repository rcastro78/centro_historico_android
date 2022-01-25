package sv.gob.sansalvadorhistorico.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import sv.gob.sansalvadorhistorico.R;

import sv.gob.sansalvadorhistorico.modelos.MisRecorridosMenu;

public class MenuRecorridosAdapter extends BaseAdapter {
    protected Activity activity;
    protected ArrayList<MisRecorridosMenu> items;
    MisRecorridosMenu menuPrincipal;
    ViewHolder holder=new ViewHolder();
    String TAG="MenuAdapter";
    Typeface tf,tfBold;

    public MenuRecorridosAdapter(Activity activity, ArrayList<MisRecorridosMenu> items) {
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
            convertView = inflater.inflate(R.layout.item_mis_recorridos, null);
            holder = new ViewHolder();
            holder.lblCantidad = convertView.findViewById(R.id.lblCantLugares);
            holder.lblNomCat = convertView.findViewById(R.id.lblNomCat);
            holder.imgCategoria = convertView.findViewById(R.id.imgCategoria);
            holder.imgFotoCat = convertView.findViewById(R.id.imgFotoCat);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.lblCantidad.setTypeface(tf);
        holder.lblNomCat.setTypeface(tf);
        holder.lblNomCat.setText(menuPrincipal.getNombre());

        String lenguaje = Locale.getDefault().getLanguage();
        if(lenguaje.equalsIgnoreCase("es")) {
            if(Integer.parseInt(menuPrincipal.getCantidad())==1){
                holder.lblCantidad.setText(menuPrincipal.getCantidad()+" lugar");
            }else{
                holder.lblCantidad.setText(menuPrincipal.getCantidad()+" lugares");
            }
        }else{
            if(Integer.parseInt(menuPrincipal.getCantidad())==1){
                holder.lblCantidad.setText(menuPrincipal.getCantidad()+" place");
            }else{
                holder.lblCantidad.setText(menuPrincipal.getCantidad()+" places");
            }
        }



        holder.imgCategoria.setImageResource(menuPrincipal.getIcono());
        holder.imgFotoCat.setImageResource(menuPrincipal.getImagenPrincipal());
        //holder.rlMenu.setBackground(menuPrincipal.getIdColor());
        return convertView;
    }

    static class ViewHolder {
        TextView lblNomCat,lblCantidad;
        ImageView imgFotoCat,imgCategoria;
    }

}

