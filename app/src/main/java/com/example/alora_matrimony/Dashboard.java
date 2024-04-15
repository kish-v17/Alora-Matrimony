package com.example.alora_matrimony;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class Dashboard extends AppCompatActivity {

    private final int home=1;
    private final int search=2;
    private final int chat=3;
    private final int profile=4;

    String fTag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        MeowBottomNavigation navbar=findViewById(R.id.navbar);

        //fragment
        Fragment fragmentToRemove = getSupportFragmentManager().findFragmentByTag(fTag);

        //navbar items
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
                switch (model.getId()){
                    case home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.dbContainer,new Home()).commit();
                        break;
                    case search:
                        getSupportFragmentManager().beginTransaction().replace(R.id.dbContainer,new Search()).commit();
                        fTag=Search.class.getName();
                        break;
                    case chat:
                        getSupportFragmentManager().beginTransaction().replace(R.id.dbContainer,new Chat()).commit();
                        fTag=Chat.class.getName();
                        break;
                    case profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.dbContainer,new Profile()).commit();
                        fTag=Profile.class.getName();
                        break;
                }
                //for notification
                navbar.setCount(chat,"3");
                return null;
            }
        });


    }
}