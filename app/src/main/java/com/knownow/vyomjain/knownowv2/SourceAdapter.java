package com.knownow.vyomjain.knownowv2;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Source;

import static java.security.AccessController.getContext;

/**
 * Created by Vyom Jain on 02-09-2017.
 */

public class SourceAdapter extends RecyclerView.Adapter<SourceAdapter.ViewHolder> {

    ArrayList<NewsSource> list;
    Context context;

    @Override
    public SourceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.source_card, parent, false));
    }

    public SourceAdapter(Context context, ArrayList<NewsSource> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onBindViewHolder(SourceAdapter.ViewHolder holder, int position) {

        holder.sname.setText(list.get(position).getSource_name());
        Glide
                .with(context)
                .load(list.get(position).getImageResourceId())
                .into(holder.slogo);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView sname;
        public ImageView slogo;

        public ViewHolder(View itemView){
            super(itemView);
            sname = (TextView)itemView.findViewById(R.id.source_name);
            slogo = (ImageView)itemView.findViewById(R.id.source_image);
        }
    }
}
