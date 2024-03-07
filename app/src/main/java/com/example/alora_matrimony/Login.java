package com.example.alora_matrimony;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.alora_matrimony.databinding.ActivityLoginBinding;

public class Login extends AppCompatActivity {
    ActivityLoginBinding b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        b=ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());


        b.txtsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Registration.class));
            }
        });

        b.txtLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getApplicationContext(), Dashboard.class));
            }
        });
    }
}