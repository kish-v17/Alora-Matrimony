package com.example.alora_matrimony;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Splash_Screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Handler h=new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                checkStatus();
            }
        },2500);
    }
    void checkStatus(){
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            startActivity(new Intent(getApplicationContext(), Dashboard.class));
            finish();
        }else{
            startActivity(new Intent(getApplicationContext(), Login.class));
            finish();
        }
    }
}