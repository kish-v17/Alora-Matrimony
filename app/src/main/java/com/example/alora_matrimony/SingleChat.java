package com.example.alora_matrimony;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.alora_matrimony.databinding.FragmentSingleChatBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.apache.commons.text.WordUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SingleChat extends Fragment {

    FragmentSingleChatBinding b;
    DatabaseReference dbr;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        b = FragmentSingleChatBinding.inflate(inflater, container, false);
        View view = b.getRoot();

        dbr = FirebaseDatabase.getInstance().getReference();
        readData();

        b.sendbtn.setOnClickListener(v -> {
            String messageText = b.msg.getText().toString().trim();
            b.msg.setText("");
            if (messageText.isEmpty()) {
                Toast.makeText(getContext(), "Message can't be empty!", Toast.LENGTH_SHORT).show();
            } else {
                Bundle bundle = getArguments();
                if (bundle != null) {
                    String receiverUid = bundle.getString("userMsgId");
                    String image=bundle.getString("image");
                    sendMessage(messageText, receiverUid,image);
                }
            }
        });

        // Setup message listener
        Bundle bundle = getArguments();
        if (bundle != null) {
            String receiverUid = bundle.getString("userMsgId");
            setupMessageListener(receiverUid);
        }

        return view;
    }

    private void setupMessageListener(String receiverUid) {
        DatabaseReference chatRef = dbr.child("chats").child(receiverUid);
        chatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<ChatMessage> messageList = new ArrayList<>();
                for (DataSnapshot messageSnapshot : snapshot.getChildren()) {
                    ChatMessage message = messageSnapshot.getValue(ChatMessage.class);
                    if (message != null) {
                        messageList.add(message);
                    }
                }

                // Pass messageList to the ChatAdapter
                MessageAdapter adapter = new MessageAdapter(messageList, FirebaseAuth.getInstance().getCurrentUser().getUid());
                b.singleChatrv.setAdapter(adapter);
                b.singleChatrv.scrollToPosition(messageList.size() - 1); // Scroll to bottom
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle cancelled event
            }
        });
    }


    private void sendMessage(String messageText, String receiverUid,String image) {
        ChatMessage message = new ChatMessage();
        message.setMessageText(messageText);
        message.setSenderUid(FirebaseAuth.getInstance().getCurrentUser().getUid());
        message.setTimestamp(System.currentTimeMillis());
        message.setSenderProfileImageUrl(image);

        DatabaseReference chatRef = dbr.child("chats").child(receiverUid).push();
        chatRef.setValue(message)
                .addOnSuccessListener(aVoid -> {
                    // Message sent successfully
                    Toast.makeText(getContext(), "Message Sent", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    // Error sending message
                    Toast.makeText(getContext(), "Failed to send message", Toast.LENGTH_SHORT).show();
                });
    }

    private void readData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            String uid = bundle.getString("userMsgId");
            DatabaseReference uRef = dbr.child("users").child(uid);
            uRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists() && isAdded()) {
                        UserDetails user = snapshot.getValue(UserDetails.class);
                        if (user != null) {
                            // Set user's name
                            String userName = WordUtils.capitalizeFully(user.getFirstName());
                            b.userName.setText(userName);

                            // Set user's image using Glide
                            String imageUrl = user.getImage();
                            if (imageUrl != null && !imageUrl.isEmpty()) {
                                Glide.with(requireContext())
                                        .load(imageUrl)
                                        .placeholder(R.drawable.deshboard_profile_circle)
                                        .error(R.drawable.deshboard_profile_circle)
                                        .circleCrop()
                                        .into(b.imageProfile);
                            } else {
                                // If no image URL is available, set a default placeholder
                                b.imageProfile.setImageResource(R.drawable.deshboard_profile_circle);
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle cancelled event
                }
            });
        }
    }

    private String formatTimestamp(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        return sdf.format(new Date(timestamp));
    }
}
