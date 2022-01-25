package sv.gob.sansalvadorhistorico.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

import sv.gob.sansalvadorhistorico.R;
import sv.gob.sansalvadorhistorico.modelos.Lugar;

public class LugarAdapter extends BaseAdapter implements Filterable {
    protected Activity activity;
    protected ArrayList<Lugar> items;
    Lugar lugar;
    ViewHolder holder=new ViewHolder();
    Typeface tf,tfBold;

    public LugarAdapter(Activity activity, ArrayList<Lugar> items) {
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

        lugar = items.get(position);
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
        holder.lblHeader.setText(lugar.getPost_title());

        return convertView;
    }

    @Override
    public Filter getFilter() {
        Filter filtro = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    filterResults.values = items;
                    filterResults.count = items.size();
                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                if (results != null && (results.count > 0)) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }

            }
        };

        return filtro;
    }

    static class ViewHolder {
        TextView lblHeader;

    }

}

