package com.example.utrixapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Aadapter extends RecyclerView.Adapter<Aadapter.viewHolder> {

    Context ctx;
    ArrayList<Quick> list;

    public Aadapter(Context ctx, ArrayList<Quick> list) {
        this.ctx = ctx;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(ctx).inflate(R.layout.annouceview,parent,false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Quick q=list.get(position);
        holder.data.setText(q.announcement);
        holder.date.setText(q.announce_date);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder{
        TextView data,date;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            data=itemView.findViewById(R.id.textt);
            date=itemView.findViewById(R.id.datet);
        }
    }
}
