package com.example.alora_matrimony;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.alora_matrimony.databinding.FragmentSearchBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.ktx.Firebase;

import java.util.ArrayList;
import java.util.List;

public class Search extends Fragment {

    FragmentSearchBinding b;
    FirebaseFirestore db;
    search_adapter adapter;
    List<UserDetails> userList;
    Context context;
    DatabaseReference dbr;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        b = FragmentSearchBinding.inflate(inflater, container, false);
        View view = b.getRoot();

        db = FirebaseFirestore.getInstance();
        userList = new ArrayList<>();
        adapter = new search_adapter(userList, context);
        b.searchrv.setAdapter(adapter);
        b.searchrv.setLayoutManager(new LinearLayoutManager(getContext()));

        dbr = FirebaseDatabase.getInstance().getReference("users");


        b.search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Filter the user list based on the search query
                filter();
                return true;
            }
        });
        // Fetch user data from Firebase Realtime Database
        fetchUserData();

        // Inflate the layout for this fragment
        return view;
    }

    private void fetchUserData() {

        Query query = FirebaseDatabase.getInstance().getReference().child("users"); // Example: Order by user's first name

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    UserDetails user = dataSnapshot.getValue(UserDetails.class);
                    if (user != null) {
                        userList.add(user);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });
    }

    private void filter() {
        String searchText = b.search.getQuery().toString().toLowerCase();
        List<UserDetails> filteredList = new ArrayList<>();

        for (UserDetails user : userList) {
            if (user.getFirstName().toLowerCase().contains(searchText) ||
                    user.getLastName().toLowerCase().contains(searchText) ||
                    user.getCity().toLowerCase().contains(searchText) ||
                    user.getState().toLowerCase().contains(searchText) ||
                    user.getQualification().toLowerCase().contains(searchText) ||
                    user.getReligion().toLowerCase().contains(searchText) ||
                    user.getCommunity().toLowerCase().contains(searchText) ||
                    user.getSubCommunity().toLowerCase().contains(searchText) ||
                    user.getOccupation().toLowerCase().contains(searchText)) {
                // Add the user to the filtered list if it matches the search criteria
                filteredList.add(user);
            }
        }

        // Update the RecyclerView with the filtered list
        adapter.filterList(filteredList);
    }

}