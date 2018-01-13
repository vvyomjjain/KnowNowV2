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
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;


/**
 * Created by Vyom Jain on 31-10-2017.
 */

public class NewsCardAdapter extends RecyclerView.Adapter<NewsCardAdapter.ViewHolder> {
    ArrayList<Article> list;
    Context context;

    @Override
    public NewsCardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_card, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    public NewsCardAdapter(Context context, ArrayList<Article> list){
        this.list = list;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onBindViewHolder(NewsCardAdapter.ViewHolder holder, int position) {
        holder.title.setText(list.get(position).getTitle());
        holder.source.setText(list.get(position).getSource().replace('-',' '));
        holder.time.setText(list.get(position).getPublishedAt());

        String dateString = list.get(position).getPublishedAt();
        if(dateString != null){
            Date dateObject = getDate(dateString);

            if(dateObject != null){
                //formatting the date...
                String formattedDate = formatDate(dateObject);
                holder.time.setText(formattedDate);
            }
        }

        Glide
                .with(context)
                .load(list.get(position).getUrlToImage())
                .into(holder.image);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView title;
        public ImageView image;
        public TextView source;
        public TextView time;

        public ViewHolder(View itemView){
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            source = (TextView) itemView.findViewById(R.id.source);
            time = (TextView) itemView.findViewById(R.id.date);
            image = (ImageView) itemView.findViewById(R.id.image);
        }
    }

    private static Date getDate(String dateString){
        try {
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
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
        long time = dateObject.getTime();
        long currentTime = System.currentTimeMillis();
        long diff = currentTime - time;
        long s= TimeUnit.MILLISECONDS.toSeconds(diff);
        long m=TimeUnit.MILLISECONDS.toMinutes(diff);
        long h=TimeUnit.MILLISECONDS.toHours(diff);
        long d=TimeUnit.MILLISECONDS.toDays(diff);

        if(s<60)
        {
            return s + " seconds ago";
        }
        else if(m<60)
        {
            return m + " min ago";
        }
        else if(h<24)
        {
            return h + " hrs ago";
        }else if(d == 1){
            return "a day ago";
        }
        else
        {
            return d + " days ago";
        }
    }
}
