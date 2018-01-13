package com.knownow.vyomjain.knownowv2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * Created by Vyom Jain on 11-11-2017.
 */

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ViewHolder> {
    ArrayList<WeatherForecastObject> list;
    Context context;

    @Override
    public ForecastAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_card, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    public ForecastAdapter(Context context, ArrayList<WeatherForecastObject> list){
        this.list = list;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(ForecastAdapter.ViewHolder holder, int position) {
        holder.temp.setText(list.get(position).getTemp() + "\u00B0");

        String dateString = list.get(position).getTime();
        if(dateString != null){
            Date dateObject = getDate(dateString);

            if(dateObject != null){
                //formatting the date...
                String formattedDate = formatDate(dateObject);
                holder.time.setText(formattedDate);
            }
        }

        switch (list.get(position).getIcon()){
            case "01d":
                holder.icon.setImageResource(R.drawable.w_01d);
                break;

            case "01n":
                holder.icon.setImageResource(R.drawable.w_01n);
                break;

            case "02d":
                holder.icon.setImageResource(R.drawable.w_02d);
                break;

            case "02n":
                holder.icon.setImageResource(R.drawable.w_02n);
                break;

            case "03d":
                holder.icon.setImageResource(R.drawable.w_03d);
                break;

            case "03n":
                holder.icon.setImageResource(R.drawable.w_03n);
                break;

            case "04d":
                holder.icon.setImageResource(R.drawable.w_04d);
                break;

            case "04n":
                holder.icon.setImageResource(R.drawable.w_04n);
                break;

            case "09d":
                holder.icon.setImageResource(R.drawable.w_09d);
                break;

            case "09n":
                holder.icon.setImageResource(R.drawable.w_09n);
                break;

            case "10d":
                holder.icon.setImageResource(R.drawable.w_10d);
                break;

            case "10n":
                holder.icon.setImageResource(R.drawable.w_10n);
                break;

            case "11d":
                holder.icon.setImageResource(R.drawable.w_11d);
                break;

            case "11n":
                holder.icon.setImageResource(R.drawable.w_11n);
                break;

            case "13d":
                holder.icon.setImageResource(R.drawable.w_13d);
                break;

            case "13n":
                holder.icon.setImageResource(R.drawable.w_13n);
                break;

            case "50d":
                holder.icon.setImageResource(R.drawable.w_50d);
                break;

            case "50n":
                holder.icon.setImageResource(R.drawable.w_50n);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView temp;
        public ImageView icon;
        public TextView time;

        public ViewHolder(View itemView){
            super(itemView);
            temp = (TextView) itemView.findViewById(R.id.temp);
            time = (TextView) itemView.findViewById(R.id.time);
            icon = (ImageView) itemView.findViewById(R.id.icon);
        }
    }

    private static Date getDate(String dateString){
        try {
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = sdf.parse(dateString);
            return date;
        }catch (Exception e){
            Log.i("news card", "Exception hua hai be");
            e.printStackTrace();
            return null;
        }
    }

    private String formatDate(Date dateObject){
        SimpleDateFormat sdf = new SimpleDateFormat("h a", Locale.getDefault());
        String time = sdf.format(dateObject);
        return time;
    }
}
