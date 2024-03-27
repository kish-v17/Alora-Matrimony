package com.example.alora_matrimony;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class chat_adapter extends RecyclerView.Adapter<chat_myViewHolder> {

    Context context;
    List<chat_itemModel> il;

    public chat_adapter(Context context, List<chat_itemModel> il) {
        this.context = context;
        this.il = il;
    }

    @NonNull
    @Override
    public chat_myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new chat_myViewHolder(LayoutInflater.from(context).inflate(R.layout.chat_rv,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull chat_myViewHolder holder, int position) {
        holder.usrnm.setText(il.get(position).getUsrnm());
        holder.profileImg.setImageResource(il.get(position).getProfileImg());

    }

    @Override
    public int getItemCount() {
        return il.size();
    }
}
