package com.example.alora_matrimony;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

public class Home extends Fragment {

    ImageButton more,partner_preferences;

    RecyclerView db_rv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_home, container, false);

        db_rv=view.findViewById(R.id.db_rv);

        partner_preferences=view.findViewById(R.id.partner_preferences);

        List<rv_itemmodel> iteamlist=new ArrayList<>();

        for (int i=1;i<=5;i++){
            iteamlist.add(new rv_itemmodel(R.drawable.deshboard_profile_circle,R.drawable.deshboard_profile,R.drawable.img_likebtn,R.drawable.img_morebtn));
        }

        db_rv.setLayoutManager(new LinearLayoutManager(getContext()));
        db_rv.setAdapter(new rv_adapter(getContext(),iteamlist));
        partner_preferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchdb2(R.id.partner_preferences);
            }
        });


        // Inflate the layout for this fragment
        return view;
    }

    //method to load fragment based on btn click
    public void launchdb2(int btnId){
        Intent i=new Intent(getContext(), db2.class);
        Bundle b=new Bundle();
        b.putInt("btnId",btnId);
        i.putExtras(b);
        startActivity(i);
    }
}