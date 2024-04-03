package com.example.alora_matrimony;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<UsersChatsHelper> userList;
    private OnUserClickListener listener;

    public UserAdapter(List<UsersChatsHelper> userList, OnUserClickListener listener) {
        this.userList = userList;
        this.listener = listener;
    }

    // ViewHolder class
    public static class UserViewHolder extends RecyclerView.ViewHolder {
        ShapeableImageView profileImage;
        TextView senderName;
        TextView recentMessage;
        TextView time;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.image_profile);
            senderName = itemView.findViewById(R.id.text_sender_name);
            recentMessage = itemView.findViewById(R.id.text_recent_message);
            time = itemView.findViewById(R.id.text_time);
        }
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_list_rv, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        UsersChatsHelper user = userList.get(position);
//        Log.d("Hello",userList.toString());

        // Set data to views
        holder.senderName.setText(user.getName());
        holder.recentMessage.setText(user.getRecentMessage());
        holder.time.setText(user.getTime());

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.deshboard_profile_circle) // Placeholder image while loading
                .error(R.drawable.deshboard_profile_circle) // Image to show if loading fails
                .transforms(new CircleCrop()); // Circular image transformation

        Glide.with(holder.itemView.getContext())
                .load(user.getProfileImageUrl()) // URL of the image
                .apply(requestOptions)
                .transforms(new CircleCrop())// Apply RequestOptions
                .into(holder.profileImage);

        // Set click listener
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onUserClick(user);
            }
        });
    }
    public void updateList(List<UsersChatsHelper> newList) {
        userList.clear();
        userList.addAll(newList);
        notifyDataSetChanged(); // Notify adapter of data change
    }
    @Override
    public int getItemCount() {
        return userList.size();
    }

    // Interface for click events
    public interface OnUserClickListener {
        void onUserClick(UsersChatsHelper user);
    }
}

