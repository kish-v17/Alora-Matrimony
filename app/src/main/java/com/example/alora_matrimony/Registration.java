package com.example.alora_matrimony;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.helper.widget.Carousel;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;

import com.example.alora_matrimony.databinding.ActivityLoginBinding;
import com.example.alora_matrimony.databinding.ActivityRegistrationBinding;
import com.google.android.material.tabs.TabLayout;

public class Registration extends AppCompatActivity {

    ActivityRegistrationBinding b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b=ActivityRegistrationBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        viewPager ad=new viewPager(getSupportFragmentManager());
        b.vp.setAdapter(ad);
        b.tb.setupWithViewPager(b.vp);

    }
}