package com.example.alora_matrimony;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class reg5_maritalHeightWeightDiet extends Fragment {
    androidx.appcompat.widget.AppCompatButton btnContinue;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_reg5_marital_height_weight_diet, container, false);

        btnContinue= view.findViewById(R.id.btnContinue);

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reg6_qualiOccupationIncome quali=new reg6_qualiOccupationIncome();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,quali).commit();
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
}