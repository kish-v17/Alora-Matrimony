package com.example.alora_matrimony;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

public class Home extends Fragment {

    ImageButton more;

    RecyclerView db_rv;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_home, container, false);

        db_rv=view.findViewById(R.id.db_rv);
        more=view.findViewById(R.id.more);
        List<rv_itemmodel> iteamlist=new ArrayList<>();

        for (int i=1;i<=5;i++){
            iteamlist.add(new rv_itemmodel(R.drawable.deshboard_profile_circle,R.drawable.deshboard_profile,R.drawable.img_likebtn,R.drawable.img_morebtn));
        }

        db_rv.setLayoutManager(new LinearLayoutManager(getContext()));
        db_rv.setAdapter(new rv_adapter(getContext(),iteamlist));
        // Inflate the layout for this fragment
        return view;
    }
}