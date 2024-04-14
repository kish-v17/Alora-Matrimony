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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_SENDER = 1;
    private static final int VIEW_TYPE_RECEIVER = 2;

    private List<ChatMessage> messages;
    private String currentUserId;

    public MessageAdapter(List<ChatMessage> messages, String currentUserId) {
        this.messages = messages;
        this.currentUserId = currentUserId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        if (viewType == VIEW_TYPE_SENDER) {
            // Inflate sender message layout
            view = inflater.inflate(R.layout.item_container_sent_message, parent, false);
            return new SenderMessageViewHolder(view);
        } else {
            // Inflate receiver message layout
            view = inflater.inflate(R.layout.item_container_recieved_message, parent, false);
            return new ReceiverMessageViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatMessage message = messages.get(position);
        Context context = holder.itemView.getContext();
        if (holder.getItemViewType() == VIEW_TYPE_SENDER) {
            ((SenderMessageViewHolder) holder).bind(message);
        } else {
            ((ReceiverMessageViewHolder) holder).bind(message,context);
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public int getItemViewType(int position) {
        ChatMessage message = messages.get(position);
        if (message.getSenderUid().equals(currentUserId)) {
            return VIEW_TYPE_SENDER;
        } else {
            return VIEW_TYPE_RECEIVER;
        }
    }

    // ViewHolder for sender message
    public static class SenderMessageViewHolder extends RecyclerView.ViewHolder {
        TextView messageTextView;
        TextView timestampTextView;

        public SenderMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            messageTextView = itemView.findViewById(R.id.sentmsg);
            timestampTextView = itemView.findViewById(R.id.sentTime);
        }

        public void bind(ChatMessage message) {
            messageTextView.setText(message.getMessageText());
            timestampTextView.setText(formatTimestamp(message.getTimestamp()));
        }
    }

    // ViewHolder for receiver message
    public static class ReceiverMessageViewHolder extends RecyclerView.ViewHolder {
        TextView messageTextView;
        TextView timestampTextView;
        ImageView profileImageView;

        public ReceiverMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            messageTextView = itemView.findViewById(R.id.recievedmsg);
            timestampTextView = itemView.findViewById(R.id.recievedTime);
            profileImageView = itemView.findViewById(R.id.recieverImg);
        }

        public void bind(ChatMessage message, Context context) {
            messageTextView.setText(message.getMessageText());
            timestampTextView.setText(formatTimestamp(message.getTimestamp()));

            // Load receiver's profile image using Glide
            String imageUrl = message.getSenderProfileImageUrl();
            if (imageUrl != null && !imageUrl.isEmpty()) {
                Glide.with(context)
                        .load(imageUrl)
                        .placeholder(R.drawable.db_profile_icon) // Placeholder image
                        .error(R.drawable.db_profile_icon) // Error image if loading fails
                        .circleCrop()
                        .into(profileImageView);
            } else {
                // If no image URL is available, set a default placeholder
                profileImageView.setImageResource(R.drawable.db_profile_icon);
            }
        }
    }


    private static String formatTimestamp(long timestamp) {
        // Implement your timestamp formatting logic
        // For example, convert timestamp to readable date/time format
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date(timestamp));
    }
}

