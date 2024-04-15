package com.example.alora_matrimony;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.alora_matrimony.databinding.FragmentEditProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.apache.commons.text.WordUtils;

public class Edit_profile extends Fragment {
    DatabaseReference dbr;
    FragmentEditProfileBinding b;
    String uid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        b=FragmentEditProfileBinding.inflate(inflater,container,false);
        View view=b.getRoot();

        dbr= FirebaseDatabase.getInstance().getReference();
        uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        readData();
        Toast.makeText(getContext(), "uid:"+uid, Toast.LENGTH_SHORT).show();

        // Inflate the layout for this fragment
        return view;
    }
    private void readData() {
        DatabaseReference uRef = dbr.child("users").child(uid);
        uRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists() && isAdded()) {
                    UserDetails user = snapshot.getValue(UserDetails.class);
                    if (user != null) {
                        // Set user's name
                        String userName = WordUtils.capitalizeFully(user.getFirstName())+" "+WordUtils.capitalizeFully(user.getLastName());
                        b.nm.setText(userName);
                        b.diet.setText(user.getDiet());
                        b.email.setText(user.getEmail());
                        b.cno.setText(user.getMobileNo());
                        b.city.setText(user.getCity());
                        b.gen.setText(user.getGender());
                        b.income.setText(user.getIncome());
                        b.maritalStatus.setText(user.getMaritalStatus());
                        b.profession.setText(user.getOccupation());
                        b.quali.setText(user.getQualification());
                        b.state.setText(user.getState());
//                        b.weight.setText(user.getWeight());
//                        b.height.setText(user.getHeight());
//                        b.dob.setText((int) user.getDateOfBirth());

                        // Set user's image using Glide
                        String imageUrl = user.getImage();
                        if (imageUrl != null && !imageUrl.isEmpty()) {
                            Glide.with(requireContext())
                                    .load(imageUrl)
                                    .placeholder(R.drawable.rectangle_radius_40)
                                    .error(R.drawable.rectangle_radius_40)
                                    .fitCenter().centerCrop()
                                    .into(b.profilePhoto);
                        } else {
                            // If no image URL is available, set a default placeholder
                            b.profilePhoto.setImageResource(R.drawable.rectangle_radius_40);
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