package com.example.alora_matrimony;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.alora_matrimony.databinding.FragmentReg1ProfileForBinding;

public class reg1_profileFor extends Fragment {

//    FragmentReg1ProfileForBinding b;
androidx.appcompat.widget.AppCompatButton btnContinue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_reg1_profile_for, container, false);
//        b=FragmentReg1ProfileForBinding.inflate(getLayoutInflater());
        btnContinue= view.findViewById(R.id.btnContinue);

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reg2_nmDob nmDob=new reg2_nmDob();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.for_proFor,nmDob).commit();
            }
        });
        return view;

    }
}