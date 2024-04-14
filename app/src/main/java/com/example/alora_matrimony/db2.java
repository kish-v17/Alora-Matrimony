package com.example.alora_matrimony;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

public class db2 extends AppCompatActivity {

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db2);

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
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.dbContainer2, new Partner_Preferences())
                                .commit();
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
                    }
                    else if(btnId==R.id.editProfile){
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.dbContainer2, new Edit_profile())
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
}
