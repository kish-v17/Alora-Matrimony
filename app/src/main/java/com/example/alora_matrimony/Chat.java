package com.example.alora_matrimony;

import static java.util.Locale.filter;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.alora_matrimony.databinding.FragmentChatBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Chat extends Fragment implements ChatAdapter.OnUserClickListener {
    FragmentChatBinding b;
    DatabaseReference dbr;
    String uid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        b = FragmentChatBinding.inflate(inflater, container, false);
        View view = b.getRoot();

        dbr = FirebaseDatabase.getInstance().getReference();
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        listData();

        return view;
    }

    private void listData(){
        DatabaseReference usersRef = dbr.child("users");
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<UserDetails> userList = new ArrayList<>();
                for(DataSnapshot userSnapshot : snapshot.getChildren()) {
                    UserDetails userDetails = userSnapshot.getValue(UserDetails.class);
                    String uid2 = userDetails.getUserId();
                    if(userDetails != null && !(uid.equals(uid2))) {
                        userList.add(userDetails);
                    }
                }
                ChatAdapter adapter = new ChatAdapter(getContext(), userList, Chat.this);
                b.chatrv.setLayoutManager(new LinearLayoutManager(getContext()));
                b.chatrv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled event
            }
        });
    }

    @Override
    public void onUserClick(String userId, String image) {
        Intent intent = new Intent(getActivity(), db2.class);
        intent.putExtra("userMsgId", userId);
        intent.putExtra("image", image);
        startActivity(intent);
    }
}

