package com.example.alora_matrimony;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

public class reg4_religionCommunity extends Fragment {
    androidx.appcompat.widget.AppCompatButton btnContinue;
    AutoCompleteTextView regCity;
    Spinner regReligion;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_reg4_religion_community, container, false);

        btnContinue= view.findViewById(R.id.btnContinue);
        regCity= view.findViewById(R.id.regCity);
        regReligion= view.findViewById(R.id.regReligion);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reg5_maritalHeightWeightDiet merital=new reg5_maritalHeightWeightDiet();
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container,merital).addToBackStack(null).commit();
            }
        });

        String[] cs= getResources().getStringArray(R.array.cs);
        String[] rel= getResources().getStringArray(R.array.religion);

        ArrayAdapter<String> cities=new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,cs);
        regCity.setAdapter(cities);
        regCity.setThreshold(1);

        ArrayAdapter<String> religion=new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,rel);
        regReligion.setAdapter(religion);








        return view;
    }
}