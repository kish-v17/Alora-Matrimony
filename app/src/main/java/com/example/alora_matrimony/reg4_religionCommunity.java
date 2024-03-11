package com.example.alora_matrimony;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;

public class reg4_religionCommunity extends Fragment {
    androidx.appcompat.widget.AppCompatButton btnContinue;
    AutoCompleteTextView cityState;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_reg4_religion_community, container, false);

        btnContinue= view.findViewById(R.id.btnContinue);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reg5_maritalHeightWeightDiet merital=new reg5_maritalHeightWeightDiet();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,merital).commit();
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
}