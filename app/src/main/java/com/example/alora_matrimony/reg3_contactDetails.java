package com.example.alora_matrimony;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class reg3_contactDetails extends Fragment {

    androidx.appcompat.widget.AppCompatButton btnContinue;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_reg3_contact_details, container, false);

        btnContinue= view.findViewById(R.id.btnContinue);

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reg4_religionCommunity community=new reg4_religionCommunity();
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container,community).addToBackStack(null).commit();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}