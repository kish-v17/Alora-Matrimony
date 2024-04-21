package com.example.alora_matrimony;

import static android.app.PendingIntent.getActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class db2 extends AppCompatActivity {
    String userId;
    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("partnerPreferences");

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db2);
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Intent intent = getIntent();
        if (intent != null) {
            String userId = intent.getStringExtra("userMsgId");
            String image=intent.getStringExtra("image");
            if (userId != null) {
                loadSingleChatFragment(userId,image);
            } else {
                int btnId = intent.getIntExtra("btnId",-1);
                if (btnId != -1) {
                    if(btnId== R.id.partner_preferences ||btnId== R.id.ppref) {
                        checkPartnerPreferences();


                    }
                    else if(btnId== R.id.more) {
                        String usrEmail = intent.getStringExtra("usrEmail");
                        if (usrEmail != null) {
                            Bundle bundle = new Bundle();
                            bundle.putString("usrEmail", usrEmail);
                            Single_userProfile_Details userProfileDetailsFragment = new Single_userProfile_Details();
                            userProfileDetailsFragment.setArguments(bundle);
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.dbContainer2, userProfileDetailsFragment)
                                    .commit();
                        }
                    }else if (btnId==R.id.cpwd) {
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.dbContainer2, new Change_Password())
                                .commit();

                    }
                    else if(btnId==R.id.editProfile){
                        Edit_profile editProfile=new Edit_profile();
                        Intent i=getIntent();
                        String cuid=i.getStringExtra("cuid");
                        Bundle bundle = new Bundle();
                        bundle.putString("cuid",cuid);
                        editProfile.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.dbContainer2, editProfile)
                                .commit();
                    }
                    else if(btnId==R.id.abtus) {
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.dbContainer2, new AboutUs())
                                .commit();
                    }
                    else if(btnId==R.id.delacc) {
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.dbContainer2, new Delete_Profile())
                                .commit();
                    }
                }
            }
        }
    }

    private void loadSingleChatFragment(String userId,String image) {
        // Load SingleChat fragment with userId
        SingleChat singleChatFragment = new SingleChat();
        Bundle bundle = new Bundle();
        bundle.putString("userMsgId", userId);
        bundle.putString("image", image);
        singleChatFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.dbContainer2, singleChatFragment)
                .commit();
    }
    private void checkPartnerPreferences() {
        Query query = userRef.orderByChild("prefUId").equalTo(userId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.dbContainer2, new Partner_Preferences())
                            .commit();
                } else {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.dbContainer2, new pp_Input())
                            .commit();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error
            }
        });
    }
}
