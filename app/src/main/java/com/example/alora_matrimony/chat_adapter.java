package com.example.alora_matrimony;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.apache.commons.text.WordUtils;

import java.util.List;

public class chat_adapter extends RecyclerView.Adapter<chat_adapter.chat_viewHolder> {

    Context context;
    List<HomeUserList> itemlist;

    HomeUserList hur;

    public chat_adapter(Context context, List<HomeUserList> itemlist) {
        this.context = context;
        this.itemlist = itemlist;
    }

    @NonNull
    @Override
    public chat_viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.chat_list_rv,parent,false);
        return new chat_adapter.chat_viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull chat_viewHolder holder, int position) {
        //holder.image_profile.setImageResource(R.drawable.deshboard_profile_circle);
        holder.text_recent_message.setText("msg");
        holder.text_time.setText("11:11");

        hur=itemlist.get(position);
        if(hur!=null){
            holder.text_sender_name.setText(WordUtils.capitalizeFully(hur.firstName)+" "+ WordUtils.capitalizeFully(hur.lastName));
            Glide.with(context)
                    .load(hur.getImage())
                    .circleCrop()
                    .error(R.drawable.deshboard_profile_circle)
                    .into(holder.image_profile);
        }
    }

    @Override
    public int getItemCount() {
        return itemlist.size();
    }

    public  static  class chat_viewHolder extends RecyclerView.ViewHolder{
        ImageView image_profile;
        TextView text_sender_name,text_recent_message,text_time;

        public chat_viewHolder(@NonNull View itemView) {
            super(itemView);
            image_profile=itemView.findViewById(R.id.image_profile);
            text_sender_name=itemView.findViewById(R.id.text_sender_name);
            text_recent_message=itemView.findViewById(R.id.text_recent_message);
            text_time=itemView.findViewById(R.id.text_time);
        }
    }
}
