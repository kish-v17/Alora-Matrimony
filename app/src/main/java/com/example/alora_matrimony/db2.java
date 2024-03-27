package com.example.alora_matrimony;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

public class db2 extends AppCompatActivity {

    FrameLayout dbContainer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db2);

        dbContainer2=findViewById(R.id.dbContainer2);
        Intent i=getIntent();

        if(getIntent()!=null){
            int btnId=i.getIntExtra("btnId",-1);
            if(btnId==R.id.partner_preferences){
                getSupportFragmentManager().beginTransaction().replace(R.id.dbContainer2,new Partner_Preferences()).commit();
            }
            else if(btnId==R.id.more){
                getSupportFragmentManager().beginTransaction().replace(R.id.dbContainer2,new Single_userProfile_Details()).commit();
            }
        }

    }


}