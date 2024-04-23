package com.example.alora_matrimony;

import static java.util.Locale.filter;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.SearchView;



import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.alora_matrimony.databinding.FragmentChatBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;


import java.util.ArrayList;
import java.util.List;

public class Chat extends Fragment {
    FragmentChatBinding b;
    List<UserDetails> users, searchedUsersList;
    List<String> usersList;
    ChatAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        b = FragmentChatBinding.inflate(inflater, container, false);

        users = new ArrayList<>();
        usersList = new ArrayList<>();
        searchedUsersList = new ArrayList<>();

        adapter = new ChatAdapter(users,getContext());
        b.chatRv.setLayoutManager(new LinearLayoutManager(getContext()));
        b.chatRv.setAdapter(adapter);
        String userId = FirebaseAuth.getInstance().getUid();
        Query query = FirebaseDatabase.getInstance().getReference("chats");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usersList.clear();
                for(DataSnapshot userSnapshot : snapshot.getChildren())
                {
                    ChatMessage msg = userSnapshot.getValue(ChatMessage.class);
                    if(msg.getRecieverUid().equals(userId)){
                        usersList.add(msg.getSenderUid());
                    }
                    if(msg.getSenderUid().equals(userId)){
                        usersList.add(msg.getRecieverUid());
                    }
                }
                if(usersList.isEmpty())
                {
                    b.tvSuggestions.setVisibility(View.VISIBLE);
                    FirebaseDatabase.getInstance().getReference("user").child(userId).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists())
                            {
                                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                                {
                                    usersList.add(dataSnapshot.getKey());
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                if(usersList.isEmpty())
                {
                    b.tvSuggestions.setVisibility(View.GONE);
                }
                setUsers(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        
        return b.getRoot();
    }



    void setUsers(ChatAdapter adapter)
    {
        users.clear();
        for (String id : usersList) {
            FirebaseDatabase.getInstance().getReference("users").child(id)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                UserDetails user = dataSnapshot.getValue(UserDetails.class);
                                boolean exists=false;
                                for(UserDetails userData: users)
                                {
                                    if(userData.getUserId().equals(user.getUserId()))
                                    {
                                        exists=true;
                                        break;
                                    }
                                }
                                if(!exists)
                                {
                                    users.add(user);
                                }
                            }
                            adapter.notifyDataSetChanged();
                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
        }
    }
}

