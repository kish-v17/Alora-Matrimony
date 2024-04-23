package com.example.alora_matrimony;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.alora_matrimony.databinding.FragmentSingleUserProfileDetailsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.apache.commons.text.WordUtils;

import java.util.Calendar;

public class Single_userProfile_Details extends Fragment {

    FragmentSingleUserProfileDetailsBinding b;
    DatabaseReference dbr;
    String uid,suid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        b=FragmentSingleUserProfileDetailsBinding.inflate(inflater,container,false);
        View view= b.getRoot();

        if (getArguments() != null) {
            suid = getArguments().getString("usrEmail");
        }

        dbr= FirebaseDatabase.getInstance().getReference().child("users");

        fetchUserData();
        // Inflate the layout for this fragment
        return view;
    }

    private void fetchUserData(){
        dbr.orderByChild("email").equalTo(suid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot snap: snapshot.getChildren()){
                        UserDetails ud=snap.getValue(UserDetails.class);
                        uid=ud.getUserId();
                        Glide.with(requireContext())
                                .load(ud.getImage())
                                .placeholder(R.drawable.rectangle_radius_40)
                                .error(R.drawable.rectangle_radius_40)
                                .fitCenter().centerCrop()
                                .into(b.bigpfp);

                        b.addbtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//                                    onUserClickListener.onUserClick(ud.getUserId(),ud.getImage());
                                SingleChat s=new SingleChat();
                                Bundle bundle=new Bundle();
                                bundle.putString("userMsgId",uid);
                                s.setArguments(bundle);
                                    getParentFragmentManager().beginTransaction().replace(R.id.dbContainer2,s).commit();
                            }
                        });

                        String lnmletter=WordUtils.capitalizeFully(ud.getLastName());
                        b.usrnm.setText(WordUtils.capitalizeFully(ud.getFirstName())+" "+(lnmletter.charAt(0))+".");
                        b.profession1.setText(WordUtils.capitalizeFully(ud.getOccupation()));
//                        b.dob1.setText((int) ud.dateOfBirth);
//                        b.height1.setText(ud.height);
//                        b.weight1.setText(ud.weight+"kg");
                        b.fnm.setText(WordUtils.capitalizeFully(ud.getFirstName())+" "+WordUtils.capitalizeFully(ud.getLastName()));
                        b.profession.setText("Proffession : "+WordUtils.capitalizeFully(ud.getOccupation()));
                        b.maritalStatus.setText(WordUtils.capitalizeFully(ud.getMaritalStatus()));
                        b.area.setText(WordUtils.capitalizeFully(ud.getCity())+","+WordUtils.capitalizeFully(ud.getState()));
                        b.religion.setText(WordUtils.capitalizeFully(ud.getReligion()));
                        b.community.setText(WordUtils.capitalizeFully(ud.getCommunity())+","+WordUtils.capitalizeFully(ud.getSubCommunity()));
                        b.diet.setText(WordUtils.capitalizeFully(ud.getDiet()));
                        b.email.setText(ud.getEmail());
                        b.phnno.setText(ud.getMobileNo());
                        b.hquali.setText("Highest Qualification : "+WordUtils.capitalizeFully(ud.getQualification()));
                        b.annualincome.setText("Annual Income : "+WordUtils.capitalizeFully(ud.getIncome()));



                    }
                }else {
                    // User not found
                    Toast.makeText(getContext(), "User not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}