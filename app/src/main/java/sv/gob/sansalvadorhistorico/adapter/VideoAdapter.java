package sv.gob.sansalvadorhistorico.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.ArrayList;

import sv.gob.sansalvadorhistorico.R;
import sv.gob.sansalvadorhistorico.modelos.Evento;
import sv.gob.sansalvadorhistorico.modelos.Video;

public class VideoAdapter extends BaseAdapter {
    protected Activity activity;
    protected ArrayList<Video> items;
    ViewHolder holder=new ViewHolder();
    String TAG="VideoAdapter";
    Typeface tf,tfBold;
    Video video;
    public VideoAdapter(Activity activity, ArrayList<Video> items) {
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

        video = items.get(position);
        ViewHolder holder = null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_recorrido360, null);
            holder = new ViewHolder();
            holder.lblVideo = convertView.findViewById(R.id.lblVideo);
            holder.imgRegalo = convertView.findViewById(R.id.imgRegalo);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.lblVideo.setTypeface(tf);
        holder.lblVideo.setText(video.getTextoVideo());
        holder.imgRegalo.setImageResource(video.getImagen());
        //holder.imgMenu.setImageResource(menuPrincipal.getIdImagen());
        //holder.rlMenu.setBackground(menuPrincipal.getIdColor());
        return convertView;
    }

    static class ViewHolder {
        TextView lblVideo;
        ImageView imgRegalo;

    }

}



