package sv.gob.sansalvadorhistorico.adapter;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.protobuf.Internal;

import java.util.ArrayList;

import sv.gob.sansalvadorhistorico.R;
import sv.gob.sansalvadorhistorico.modelos.Evento;
import sv.gob.sansalvadorhistorico.modelos.MenuPrincipal;


public class EventoAdapter extends BaseAdapter {
    protected Activity activity;
    protected ArrayList<Evento> items,originales;
    ViewHolder holder=new ViewHolder();
    String TAG="EventoAdapter";
    Typeface tf,tfBold;
    Evento evento;
    public EventoAdapter(Activity activity, ArrayList<Evento> items) {
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

        evento = items.get(position);
        ViewHolder holder = null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_calendario, null);
            holder = new ViewHolder();
            holder.lblEvento = convertView.findViewById(R.id.lblEvento);
            holder.imgEvento = convertView.findViewById(R.id.imgEvento);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.lblEvento.setTypeface(tf);
        holder.lblEvento.setText(evento.getPost_title());
        //holder.imgMenu.setImageResource(menuPrincipal.getIdImagen());
        //holder.rlMenu.setBackground(menuPrincipal.getIdColor());

        Glide.with(holder.imgEvento.getContext()).load(evento.getGuid())
                .placeholder(R.drawable.logo_centro_placeholder)
                .error(R.drawable.logo_centro_placeholder)
                .override(128, 128)

                .circleCrop()
                .into(holder.imgEvento);



        return convertView;
    }

    static class ViewHolder {
        TextView lblEvento;
        ImageView imgEvento;
    }



    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<Evento> results = new ArrayList<>();
                if (originales == null)
                    originales = items;
                if (constraint != null) {
                    if (originales != null && originales.size() > 0) {
                        for (final Evento e : originales) {
                            if (e.getPost_title().toLowerCase()
                                    .contains(constraint.toString()))
                                results.add(e);
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                items = (ArrayList<Evento>)results.values;
                notifyDataSetChanged();
            }
        };

    }


    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

}



