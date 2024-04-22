package com.example.alora_matrimony;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.ktx.Firebase;

import java.util.ArrayList;
import java.util.List;

public class Search extends Fragment {

    FragmentSearchBinding b;
    FirebaseFirestore db;

    private search_adapter sa;
    private List<UserDetails> userList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        b=FragmentSearchBinding.inflate(inflater,container,false);
        View view=b.getRoot();
        db=FirebaseFirestore.getInstance();

        b.search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Perform search when user submits query
                searchUsers(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Perform search as user types
                searchUsers(newText);
                return true;
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    private void searchUsers(String query) {
        // Clear previous search results
        userList.clear();

        // Query Firebase Firestore for users matching the search query
        CollectionReference usersRef = db.collection("users");
        Query searchQuery = usersRef.whereEqualTo("firstName", query);
        searchQuery.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                // Iterate through the query results and add them to the userList
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    UserDetails user = documentSnapshot.toObject(UserDetails.class);
                    userList.add(user);
                }
                // Notify the adapter of dataset change
                sa.notifyDataSetChanged();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Handle failure
                Toast.makeText(getContext(), "Search failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}