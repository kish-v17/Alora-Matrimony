package com.example.alora_matrimony;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.example.alora_matrimony.databinding.FragmentSingleChatBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
    List<ChatMessage> messageList;

    MessageAdapter messageAdapter;
    String receiver;
    String userId,image;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        b = FragmentSingleChatBinding.inflate(inflater, container, false);
        View view = b.getRoot();

        messageList = new ArrayList<>();
        Bundle bundle = getArguments();
        receiver = bundle.getString("userMsgId");
        userId = FirebaseAuth.getInstance().getUid();

        dbr = FirebaseDatabase.getInstance().getReference();
        readData();

        b.sendbtn.setOnClickListener(v -> {
            String messageText = b.msg.getText().toString().trim();
            b.msg.setText("");
            if (messageText.isEmpty()) {
                Toast.makeText(getContext(), "Message can't be empty!", Toast.LENGTH_SHORT).show();
            } else {
                sendMessage(messageText, receiver, image);
            }
        });

        dbr.child("chats").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageList.clear();
                for(DataSnapshot message:snapshot.getChildren()){
                    ChatMessage msg=message.getValue(ChatMessage.class);
                    if(msg.getSenderUid().equals(userId) && msg.getRecieverUid().equals(receiver) || msg.getSenderUid().equals(receiver) && msg.getRecieverUid().equals(userId)){
                        messageList.add(msg);
                    }
                }
                messageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        b.singleChatrv.setLayoutManager(new LinearLayoutManager(getContext()));
        messageAdapter=new MessageAdapter(messageList,userId);
        b.singleChatrv.setAdapter(messageAdapter);


        return view;
    }

private void sendMessage(String messageText, String receiverUid, String image){
    String userId = FirebaseAuth.getInstance().getUid();
    String messageId = dbr.child("chats").push().getKey();
    ChatMessage message = new ChatMessage(messageText, userId, receiverUid, image, messageId);
    dbr.child("chats").child(messageId).setValue(message).addOnCompleteListener(new OnCompleteListener<Void>() {
        @Override
        public void onComplete(@NonNull Task<Void> task) {
            Toast.makeText(getActivity(), "Message sent!", Toast.LENGTH_SHORT).show();
        }
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
                            String userName = WordUtils.capitalizeFully(user.getFirstName());
                            image=user.getImage();
                            b.userName.setText(userName);

                            String imageUrl = user.getImage();
                            if (imageUrl != null && !imageUrl.isEmpty()) {
                                Glide.with(requireContext())
                                        .load(imageUrl)
                                        .placeholder(R.drawable.deshboard_profile_circle)
                                        .error(R.drawable.deshboard_profile_circle)
                                        .circleCrop()
                                        .into(b.imageProfile);
                            } else {
                                b.imageProfile.setImageResource(R.drawable.deshboard_profile_circle);
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}

