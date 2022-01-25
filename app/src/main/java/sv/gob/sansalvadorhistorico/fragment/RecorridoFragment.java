package sv.gob.sansalvadorhistorico.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import sv.gob.sansalvadorhistorico.Player360Activity;
import sv.gob.sansalvadorhistorico.R;
import sv.gob.sansalvadorhistorico.adapter.VideoAdapter;
import sv.gob.sansalvadorhistorico.modelos.Video;
public class RecorridoFragment extends Fragment {
    ArrayList<Video> items = new ArrayList<>();
    VideoAdapter videoAdapter = null;

    ListView lstVideos;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recorrido360, container, false);
        lstVideos = v.findViewById(R.id.lstRecorrido);
        mostrarVideos();
        lstVideos.setOnItemClickListener((parent, view, position, id) -> {
            Video video = (Video)lstVideos.getItemAtPosition(position);
            String videoUrl = video.getVideoUrl();
            Intent intent = new Intent(getActivity(), Player360Activity.class);
            intent.putExtra("videoUrl",videoUrl);
            startActivity(intent);
        });

        /*WebView webView = v.findViewById(R.id.webPlayer);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setSupportMultipleWindows(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {

                super.onPageFinished(view, url);
            }
        });
        webView.loadUrl("https://my.matterport.com/show/?m=MdURrp2Xx8z");
        webView.setVisibility(View.VISIBLE);
*/

        return v;
    }
/*
* Museo de la moneda
https://my.matterport.com/show/?m=MdURrp2Xx8z

Parroquia Ma. Auxiliadora Don Rua
https://my.matterport.com/show/?m=mrWoZNaA5Ro

Plaza Cap. Gral. Gerardo Barrios
https://my.matterport.com/show/?m=gPo3XRi7Kbj

Iglesia el Rosario
https://my.matterport.com/show/?m=hJNotvJbjwd

Plaza Francisco Morazán
https://my.matterport.com/models/i51Y7kmsmDz

Catedral Metropolitana de San Salvador
https://my.matterport.com/models/namQBta6kmu

Cementerio General Los Ilustres
https://my.matterport.com/models/qhEFCyA2kqf

Plaza Libertad
https://my.matterport.com/show/?m=NmHVNDuefMX

* */

    private void mostrarVideos(){

    }

}
