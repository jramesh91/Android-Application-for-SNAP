package teamj.application.appforsnaptest.view;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import teamj.application.appforsnaptest.R;

/**
 * Created by Rakesh on 09-12-2016.
 */

public class WeatherAdapter extends ArrayAdapter<Weather> {

    Context context;
    int layoutResourceId;
    ArrayList<Weather> data = null;

    public WeatherAdapter(Context context, int layoutResourceId, ArrayList<Weather> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        WeatherHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new WeatherHolder();
            holder.txtTitle_ID = (TextView) row.findViewById(R.id.Request_ID);
            holder.txtTitle_Source = (TextView) row.findViewById(R.id.Request_Source);
            holder.txtTitle_des = (TextView) row.findViewById(R.id.Request_Dest);

            row.setTag(holder);
        }
        else
        {
            holder = (WeatherHolder)row.getTag();
        }

        Weather weather = data.get(position);
            holder.txtTitle_ID.setText(String.valueOf(weather.R_id));
        holder.txtTitle_Source.setText(weather.source);
        holder.txtTitle_des.setText(weather.destination);
        //holder.imgIcon.setImageResource(weather.icon);

        return row;
    }

    static class WeatherHolder
    {

        TextView txtTitle_ID;
        TextView txtTitle_Source;
        TextView txtTitle_des;

    }
}
