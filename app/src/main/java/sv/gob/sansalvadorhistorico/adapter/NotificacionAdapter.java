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

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import sv.gob.sansalvadorhistorico.R;
import sv.gob.sansalvadorhistorico.modelos.Notificacion;

public class NotificacionAdapter extends BaseAdapter {
    protected Activity activity;
    protected ArrayList<Notificacion> items;
    ViewHolder holder=new ViewHolder();
    String TAG="EventoAdapter";
    Typeface tf,tfBold;
    Notificacion notificacion;
    public NotificacionAdapter(Activity activity, ArrayList<Notificacion> items) {
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

        notificacion = items.get(position);
        ViewHolder holder = null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_notificacion, null);
            holder = new ViewHolder();
            holder.lblItem = convertView.findViewById(R.id.lblItem);
            holder.lblItemDescrip = convertView.findViewById(R.id.lblItemDescrip);
            holder.lblFecha = convertView.findViewById(R.id.lblFecha);
            holder.imgItem = convertView.findViewById(R.id.imgItem);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.lblItem.setTypeface(tf);
        holder.lblItemDescrip.setTypeface(tf);
        holder.lblItemDescrip.setTypeface(tf);
        holder.lblItem.setText(notificacion.getPostTitle());
        holder.lblFecha.setText(notificacion.getFecha());
        holder.lblItemDescrip.setText(notificacion.getContent());
        holder.lblFecha.setText(notificacion.getFecha());
        //holder.imgMenu.setImageResource(menuPrincipal.getIdImagen());
        //holder.rlMenu.setBackground(menuPrincipal.getIdColor());

        Glide.with(holder.imgItem.getContext()).load(notificacion.getGuid())
                .placeholder(R.drawable.calendario)
                .error(R.drawable.calendario)
                .override(256, 256)
                .circleCrop()
                .into(holder.imgItem);



        return convertView;
    }

    static class ViewHolder {
        TextView lblItem,lblItemDescrip,lblFecha;
        ImageView imgItem;
    }

}



