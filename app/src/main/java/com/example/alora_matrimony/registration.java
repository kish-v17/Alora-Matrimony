package com.example.alora_matrimony;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

public class registration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Fragment f=new reg1_profileFor();
        getSupportFragmentManager().beginTransaction().add(R.id.container,f).commit();
    }
}