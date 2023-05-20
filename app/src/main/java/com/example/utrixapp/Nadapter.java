package com.example.utrixapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Nadapter extends RecyclerView.Adapter<Nadapter.viewHolder> {

    Context ctx;
    ArrayList<News> list;
    public static String urlim;

    public Nadapter(Context ctx, ArrayList<News> list) {
        this.ctx = ctx;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(ctx).inflate(R.layout.show_news,parent,false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        News n=list.get(position);
        holder.data.setText(n.news);
        holder.date.setText(n.Date);

        holder.pics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ctx,clicked_item.class);
                urlim=n.getImageurl();
                intent.putExtra("imurl",n.getImageurl());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(intent);
                ((Activity)ctx).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder{
        TextView data,date,pics;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            pics=itemView.findViewById(R.id.pics);
            data=itemView.findViewById(R.id.textn);
            date=itemView.findViewById(R.id.daten);
        }
    }
}

