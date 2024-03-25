package com.example.alora_matrimony;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment f=new reg6_qualiOccupationIncome();
        getSupportFragmentManager().beginTransaction().add(R.id.shortcutCon,f).commit();


    }
}