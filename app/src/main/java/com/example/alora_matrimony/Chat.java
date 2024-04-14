package com.example.alora_matrimony;

import static java.util.Locale.filter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alora_matrimony.databinding.FragmentChatBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
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

public class Chat extends Fragment implements chat_adapter.OnUserClickListener {
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
                chat_adapter adapter = new chat_adapter(getContext(), userList, Chat.this);
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
    public void onUserClick(String userId) {
        singleChat singleChatFragment = new singleChat();
        Bundle bundle = new Bundle();
        bundle.putString("userMsgId", userId);
        singleChatFragment.setArguments(bundle);
        //alll for you
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.dbContainer2, singleChatFragment);
        transaction.addToBackStack(null); // Optional: add to back stack
        transaction.commit();
    }
}

