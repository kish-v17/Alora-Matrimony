package com.example.alora_matrimony;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class rv_adapter extends RecyclerView.Adapter<rv_adapter.rv_myViewHolder> {

    Context context;
    List<UserDetails> itemlist;

    UserDetails hur;

    public rv_adapter(Context context, List<UserDetails> itemlist) {
        this.context = context;
        this.itemlist = itemlist;
    }

    @NonNull
    @Override
    public rv_myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.deshboard_profile_rv,parent,false);
        return new rv_myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull rv_myViewHolder holder, int position) {
        holder.like.setImageResource(R.drawable.img_likebtn);
        holder.more.setImageResource(R.drawable.img_morebtn);

        hur=itemlist.get(position);
            if(hur!=null){
                Glide.with(context)
                        .load(hur.getImage())
                        .fitCenter().centerCrop()
                        .error(R.drawable.deshboard_profile)
                        .into(holder.bigpfp);
                Glide.with(context)
                        .load(hur.getImage())
                        .circleCrop()
                        .error(R.drawable.deshboard_profile_circle)
                        .into(holder.pfp);

                holder.more.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String suid = "";
                        UserDetails clickedUser = itemlist.get(holder.getAdapterPosition());
                        if (clickedUser != null) {
                            suid=clickedUser.getEmail();
                        }
                        Intent i=new Intent(context, db2.class);
                        Bundle b=new Bundle();
                        b.putInt("btnId",R.id.more);
                        b.putString("usrEmail",suid);
                        i.putExtras(b);
                        context.startActivity(i);
                    }
                });
            }

    }

    @Override
    public int getItemCount() {
        return itemlist.size();
    }

    public  static  class rv_myViewHolder extends RecyclerView.ViewHolder{
        ImageView pfp,bigpfp,like,more;

        public rv_myViewHolder(@NonNull View itemView) {
            super(itemView);
            pfp=itemView.findViewById(R.id.pfp);
            bigpfp=itemView.findViewById(R.id.bigpfp);
            like=itemView.findViewById(R.id.like);
            more=itemView.findViewById(R.id.more);
        }
    }

}
