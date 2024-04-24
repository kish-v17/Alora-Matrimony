package com.example.alora_matrimony;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.alora_matrimony.databinding.FragmentPartnerPreferencesBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.apache.commons.text.WordUtils;

public class Partner_Preferences extends Fragment {
    FragmentPartnerPreferencesBinding b;
    DatabaseReference dbr;
    String uId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        b=FragmentPartnerPreferencesBinding.inflate(inflater,container,false);
        View view=b.getRoot();
        dbr= FirebaseDatabase.getInstance().getReference().child("partnerPreferences");
        uId= FirebaseAuth.getInstance().getUid();
        dbr.orderByChild("prefUId").equalTo(uId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot snap: snapshot.getChildren()){
                        PP_Details pref=snap.getValue(PP_Details.class);

                        b.txtPrefAge.setText("Age : "+pref.getPrefAgeGroup());
                        b.txtPrefHeight.setText("Height : "+pref.getPrefHeight());
                        b.txtPrefWeight.setText("Weight : "+pref.getPrefWeight()+" kg");
                        b.txtprefDiet.setText("Diet : "+pref.getPrefDiet());
                        b.txtPrefMs.setText("Marital Status : "+pref.getPrefMaritalStatus());

                        b.txtPrefCity.setText("City : "+pref.getPrefCity());
                        b.txtPrefState.setText("State : "+pref.getPrefState());

                        b.txtPrefReligion.setText("Religion : "+pref.getPrefReligion());
                        b.txtPrefCom.setText("Community : "+pref.getPrefCommunity());
                        b.txtPrefSubCom.setText("Sub Com. : "+pref.getPrefSubCom());

                        b.txtPrefQual.setText("Qualification : "+pref.getPrefQual());
                        b.txtPrefIncome.setText("Income : "+pref.getPrefIncome());
                        b.txtPrefProf.setText("Proffession : "+pref.getPrefOccupation());

                    }
                }else {
                    // User not found
                    Toast.makeText(getContext(), "Preference not Found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
}