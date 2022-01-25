package sv.gob.sansalvadorhistorico.utilidades;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.GlideException;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import sv.gob.sansalvadorhistorico.R;
import sv.gob.sansalvadorhistorico.modelos.Lugar;
import sv.gob.sansalvadorhistorico.modelos.LugaresCategoria;

public class CustomInfoWindowGoogleMap implements GoogleMap.InfoWindowAdapter {

    private Context context;
    private ArrayList<LugaresCategoria> lugares;
    public CustomInfoWindowGoogleMap(Context ctx, ArrayList<LugaresCategoria> lugares){
        context = ctx;
        this.lugares = lugares;
    }

    @Override
    public View getInfoWindow(Marker marker) {

        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View infoWindow = ((Activity)context).getLayoutInflater().inflate(
                R.layout.item_marker, null);
        TextView lblInfo = infoWindow.findViewById(R.id.lblInfo);

        //ImageView imgFoto = infoWindow.findViewById(R.id.imgFoto);
        lblInfo.setText(marker.getTitle());

            /*Glide.with(imgFoto.getContext()).load(lugares.get(Integer.parseInt(marker.getId().replace("m", ""))).getUrl())
                    .placeholder(R.drawable.marker)
                    .error(android.R.drawable.ic_menu_camera)
                    .override(256, 256)

                    //.circleCrop()
                    .into(imgFoto);
*/




        return infoWindow;
    }
}
