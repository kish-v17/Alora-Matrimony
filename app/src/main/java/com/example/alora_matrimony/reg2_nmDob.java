package com.example.alora_matrimony;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class reg2_nmDob extends Fragment {

    androidx.appcompat.widget.AppCompatButton btnContinue;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_reg2_nm_dob, container, false);

        btnContinue= view.findViewById(R.id.btnContinue);

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reg3_contactDetails con=new reg3_contactDetails();
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container,con).addToBackStack(null).commit();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}