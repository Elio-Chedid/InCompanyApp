package com.example.utrixapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    Context ctx;
    List<ModalClass> mList;

    public CustomAdapter(Context ctx, List<ModalClass> mList) {
        this.ctx = ctx;
        this.mList = mList;
    }

    @NonNull
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(ctx);
        View view= inflater.inflate(R.layout.multi_images,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, int position) {
        holder.imgname.setText(mList.get(position).getImagename());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView imgname;
        ImageView imageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgname=itemView.findViewById(R.id.imgname);
            imageView=itemView.findViewById(R.id.upload_icon);
        }
    }
}
