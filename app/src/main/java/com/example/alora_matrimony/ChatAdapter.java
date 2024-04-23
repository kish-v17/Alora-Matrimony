package com.example.alora_matrimony;


import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.apache.commons.text.WordUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    List<UserDetails> users;
    Context context;

    public ChatAdapter(List<UserDetails> users, Context context) {
        this.users = users;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chat_list_rv,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserDetails user = users.get(position);
        String uid=user.getUserId();
        String username= WordUtils.capitalizeFully(user.getFirstName())+" "+WordUtils.capitalizeFully(user.getLastName());

        holder.tvSender.setText(username);
        Glide.with(context)
                .load(user.getImage())
                .apply(new RequestOptions().circleCrop())
                .placeholder(R.drawable.deshboard_profile_circle)
                .error(R.drawable.deshboard_profile_circle)
                .into(holder.imageProfile);

        DatabaseReference chatRef = FirebaseDatabase.getInstance().getReference().child("chats");
        Query query = chatRef.orderByChild("recieverUid").equalTo(uid).limitToLast(1);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String recentMessage = snapshot.child("messageText").getValue(String.class);
                    long timestamp = snapshot.child("timestamp").getValue(Long.class);
                    String time = convertTimestampToTime(timestamp);

                    // Set recent message and time
                    holder.tvRecentMsg.setText(recentMessage);
                    holder.tvTime.setText(time);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle failure
            }
        });

        holder.itemView.setOnClickListener(v->{
            Intent intent = new Intent(context, db2.class);
            intent.putExtra("userMsgId", uid);
            intent.putExtra("image", user.getImage());
            context.startActivity(intent);

        });
    }
    private String convertTimestampToTime(long timestamp) {
        long now = System.currentTimeMillis();
        long diff = now - timestamp;
        long seconds = diff / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;

        if (days > 0) {
            return days + " day(s) ago";
        } else if (hours > 0) {
            return hours + " hour(s) ago";
        } else if (minutes > 0) {
            return minutes + " minute(s) ago";
        } else {
            return "Just now";
        }
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{

        ShapeableImageView imageProfile;
        TextView tvSender,tvRecentMsg,tvTime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageProfile=itemView.findViewById(R.id.imgProfile);
            tvSender=itemView.findViewById(R.id.tvSender);
            tvRecentMsg=itemView.findViewById(R.id.tvRecentMsg);
            tvTime=itemView.findViewById(R.id.tvTime);
        }
    }
}
