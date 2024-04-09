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

public class Chat extends Fragment {
    FragmentChatBinding b;
    private UserAdapter userAdapter;
    private List<UsersChatsHelper> userList;

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
        DatabaseReference usersRef=dbr.child("users");
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<HomeUserList> userList=new ArrayList<>();
                for(DataSnapshot userSnapshot:snapshot.getChildren()){
                    HomeUserList homeUserList=userSnapshot.getValue(HomeUserList.class);
                    String uid2=homeUserList.getUserId();
                    if(homeUserList!=null && !uid.equals(uid2)){
                        userList.add(homeUserList);
                    }
                }
                chat_adapter adapter=new chat_adapter(getContext(),userList);
                b.chatrv.setLayoutManager(new LinearLayoutManager(getContext()));
                b.chatrv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
