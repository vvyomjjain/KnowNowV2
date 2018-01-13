package com.knownow.vyomjain.knownowv2;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * Created by Vyom Jain on 02-09-2017.
 */

public class ArticleAdapter extends ArrayAdapter<Article> {

    private Context context;
    public static final String LOG_TAG = ArticleAdapter.class.getName();

    public ArticleAdapter(Activity context, ArrayList<Article> list){
        super(context, 0, list);
        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        Article currentArticle = getItem(position);

        TextView source = (TextView) listItemView.findViewById(R.id.source);
        source.setText(currentArticle.getSource().replace('-', ' '));

        TextView title = (TextView) listItemView.findViewById(R.id.title);
        title.setText(currentArticle.getTitle());

        String dateString = currentArticle.getPublishedAt();
        if(dateString != null){
            Date dateObject = getDate(dateString);

            //finding the date textView...
            TextView date = (TextView) listItemView.findViewById(R.id.date);
            if(dateObject != null){
                //formatting the date...
                String formattedDate = formatDate(dateObject);
                date.setText(formattedDate);
            }
        }
        ImageView image = (ImageView)listItemView.findViewById(R.id.image);
        Glide
                .with(context)
                .load(currentArticle.getUrlToImage())
                .into(image);

        return listItemView;

    }

    private static Date getDate(String dateString){
        try {
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = sdf.parse(dateString);
            return date;
        }catch (Exception e){
            Log.i(LOG_TAG, "Exception hua hai be");
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
