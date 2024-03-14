package com.example.alora_matrimony;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class rv_adapter extends RecyclerView.Adapter<rv_myViewHolder> {

    Context context;
    List<rv_itemmodel> itemlist;

    public rv_adapter(Context context, List<rv_itemmodel> itemlist) {
        this.context = context;
        this.itemlist = itemlist;
    }

    @NonNull
    @Override
    public rv_myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new rv_myViewHolder(LayoutInflater.from(context).inflate(R.layout.deshboard_profile_rv,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull rv_myViewHolder holder, int position) {
        holder.pfp.setImageResource(itemlist.get(position).getPfp());
        holder.bigpfp.setImageResource(itemlist.get(position).getBigpfp());
        holder.like.setImageResource(itemlist.get(position).getLike());
        holder.more.setImageResource(itemlist.get(position).getMore());

        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context.getApplicationContext(), "user acc page"+v, Toast.LENGTH_SHORT).show();
            }
        });

        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context.getApplicationContext(), "added to liked"+v, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemlist.size();
    }
}
