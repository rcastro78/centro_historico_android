package sv.gob.sansalvadorhistorico.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import sv.gob.sansalvadorhistorico.R;
import sv.gob.sansalvadorhistorico.modelos.Categorias;


public class SpinnerAdapter extends BaseAdapter {
    protected Activity activity;
    protected ArrayList<Categorias> items;
    Categorias cat;
    ViewHolder holder=new ViewHolder();
    String TAG="SpinnerAdapter";
    Typeface tf,tfBold;

    public SpinnerAdapter(Activity activity, ArrayList<Categorias> items) {
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

        cat = items.get(position);
        ViewHolder holder = null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.spinner_item, null);
            holder = new ViewHolder();
            holder.lblHeader = convertView.findViewById(R.id.listTitle);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.lblHeader.setTypeface(tf);
        holder.lblHeader.setText(cat.getName());

        return convertView;
    }

    static class ViewHolder {
        TextView lblHeader;

    }

}

