package com.example.alora_matrimony;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class Deshbord extends AppCompatActivity {

    private final int home=1;
    private final int search=2;
    private final int chat=3;
    private final int profile=4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deshbord);
        MeowBottomNavigation navbar=findViewById(R.id.navbar);

        navbar.add(new MeowBottomNavigation.Model(home,R.drawable.img_home));
        navbar.add(new MeowBottomNavigation.Model(search,R.drawable.img_search));
        navbar.add(new MeowBottomNavigation.Model(chat,R.drawable.img_chat));
        navbar.add(new MeowBottomNavigation.Model(profile,R.drawable.img_profile));
        //for default cell
        navbar.show(1,true);

        navbar.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                return null;
            }
        });
        navbar.setOnShowListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                String name;
                switch (model.getId()){
                    case home:name="Home";
                        break;
                    case search:name="Search";
                        //break;
                    case chat:name="Chat";
                        break;
                    case profile:name="Profile";
                        break;
                }
                //for notification
                navbar.setCount(chat,"3");
                return null;
            }
        });


        //fragment
        getSupportFragmentManager().beginTransaction().add(R.id.container,new Home()).commit();
    }
}