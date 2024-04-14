package com.example.alora_matrimony;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;

public class db2 extends AppCompatActivity {

    FrameLayout dbContainer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db2);

        dbContainer2=findViewById(R.id.dbContainer2);
        Intent i=getIntent();

        if(i!=null){
            int btnId=i.getIntExtra("btnId",-1);
            String suid=i.getStringExtra("usrEmail");

            if(btnId==R.id.partner_preferences || btnId==R.id.ppref){
                getSupportFragmentManager().beginTransaction().replace(R.id.dbContainer2,new Partner_Preferences()).commit();
            }
            else if(btnId==R.id.more){
                Bundle bundle = new Bundle();
                bundle.putString("usrEmail", suid); // Pass usrEmail to fragment
                Single_userProfile_Details userProfileDetailsFragment = new Single_userProfile_Details();
                userProfileDetailsFragment.setArguments(bundle);// Bundle as argument
                getSupportFragmentManager().beginTransaction().replace(R.id.dbContainer2,userProfileDetailsFragment).commit();
            } else if (btnId==R.id.editProfile) {
                getSupportFragmentManager().beginTransaction().replace(R.id.dbContainer2,new Edit_profile()).commit();
            } else if (btnId==R.id.abtus) {
                getSupportFragmentManager().beginTransaction().replace(R.id.dbContainer2,new AboutUs()).commit();
            }else if(btnId==R.id.delacc) {
                getSupportFragmentManager().beginTransaction().replace(R.id.dbContainer2, new Delete_Profile()).commit();
            }

        }

    }


}