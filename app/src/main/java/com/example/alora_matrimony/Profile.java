package com.example.alora_matrimony;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.alora_matrimony.databinding.FragmentProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.apache.commons.text.WordUtils;

public class Profile extends Fragment {
    FragmentProfileBinding b;
    DatabaseReference dbr;
    String uid;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        b = FragmentProfileBinding.inflate(inflater, container, false);
        View view = b.getRoot();
        uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        dbr=FirebaseDatabase.getInstance().getReference();
        readData();
        b.ppref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(R.id.ppref);
            }
        });

        b.editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(R.id.editProfile);
            }
        });
        b.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(requireContext(), Login.class));
                requireActivity().finish();
                Toast.makeText(requireContext(), "Logged out successfully", Toast.LENGTH_SHORT).show();
            }
        });
        b.abtus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(R.id.abtus);
            }
        });
        b.delacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(R.id.delacc);
            }
        });
        return view;
    }
    void changeFragment(int btnId){
        Intent i=new Intent(getContext(), db2.class);
        Bundle b=new Bundle();
        b.putInt("btnId",btnId);
        i.putExtras(b);
        startActivity(i);
    }

    private void readData(){
        DatabaseReference uRef=dbr.child("users").child(uid);
        uRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists() && isAdded()){
                    UserDetails user = snapshot.getValue(UserDetails.class);
                    if(user != null){
                        // Set user's name
                        String userName = WordUtils.capitalizeFully(user.getFirstName()) + " " + WordUtils.capitalizeFully(user.getLastName());
                        b.txtUserName.setText(userName);

                        // Set user's image using Glide
                        String imageUrl = user.getImage();
                        if (imageUrl != null && !imageUrl.isEmpty()) {
                            Glide.with(requireContext())
                                    .load(imageUrl)
                                    .placeholder(R.drawable.deshboard_profile_circle)
                                    .error(R.drawable.deshboard_profile_circle)
                                    .circleCrop()
                                    .into(b.UserProfile);
                        } else {
                            // If no image URL is available, set a default placeholder
                            b.UserProfile.setImageResource(R.drawable.deshboard_profile_circle);
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