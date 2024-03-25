package com.example.alora_matrimony;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.example.alora_matrimony.databinding.FragmentReg6QualiOccupationIncomeBinding;

public class reg6_qualiOccupationIncome extends Fragment {
    AppCompatButton btnCreate;
    Spinner spQual,spincome,spWorkAs;
    String relation,gender,firstName,lastName,dateOfBirth,mobile,email,password,religion,community,subCommunity,state,city,maritalStatus,height,weight,diet,qualification,income,workSector,occupation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_reg6_quali_occupation_income, container, false);
        btnCreate=view.findViewById(R.id.btnContinue);
        spQual=view.findViewById(R.id.spQualification);
        spincome=view.findViewById(R.id.spIncome);;
        spWorkAs=view.findViewById(R.id.spWorksAs);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b=getArguments();
                relation=b.getString("relation");
                gender=b.getString("gender");
                firstName=b.getString("firstName");
                lastName=b.getString("lastName");
                //dateOfBirth


                Intent intent=new Intent(getActivity(), Dashboard.class);
                startActivity(intent);
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
}