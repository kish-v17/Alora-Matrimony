package com.example.alora_matrimony;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class reg5_maritalHeightWeightDiet extends Fragment {
   AppCompatButton btnContinue;
   AutoCompleteTextView actvHeight;
   Spinner spMarital,spDiet;
   EditText etWeight;
   String maritalStatus,height,weight,diet;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_reg5_marital_height_weight_diet, container, false);

        btnContinue= view.findViewById(R.id.btnContinue);
        actvHeight=view.findViewById(R.id.actvHeight);
        spMarital=view.findViewById(R.id.spMaritalStatus);
        etWeight=view.findViewById(R.id.etWeight);
        spDiet=view.findViewById(R.id.spDiet);

        ArrayAdapter<String> heightAdd=new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_dropdown_item,getResources().getStringArray(R.array.height));
        actvHeight.setAdapter(heightAdd);

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maritalStatus=spMarital.getSelectedItem().toString();
                height=actvHeight.getText().toString().trim();
                weight=etWeight.getText().toString().trim();
                diet=spDiet.getSelectedItem().toString();

                if (maritalStatus.equals("Select Marital Status")) {
                    Toast.makeText(getActivity(), "Please select a marital status", Toast.LENGTH_SHORT).show();
                } else if (height.isEmpty()) {
                    actvHeight.setError("Please enter height");
                } else if (weight.isEmpty()) {
                    etWeight.setError("Please enter weight");
                } else if(diet.equals("Select Diet")) {
                    Toast.makeText(getActivity(), "Please select a diet", Toast.LENGTH_SHORT).show();
                }else{
                    reg6_qualiOccupationIncome qual=new reg6_qualiOccupationIncome();
                    Bundle b=getArguments();
                    b.putString("maritalStatus",maritalStatus);
                    b.putString("height",height);
                    b.putString("weight",weight);
                    b.putString("diet",diet);
                    qual.setArguments(b);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,qual).addToBackStack(null).commit();
                }
            }
        });
        return view;
    }
}