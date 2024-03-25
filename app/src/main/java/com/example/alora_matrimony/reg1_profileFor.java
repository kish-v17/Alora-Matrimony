package com.example.alora_matrimony;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.alora_matrimony.databinding.FragmentReg1ProfileForBinding;

public class reg1_profileFor extends Fragment {

//    FragmentReg1ProfileForBinding b;
    AppCompatButton btnContinue;
    RadioGroup rel,gen;
    RadioButton relsel,gensel;
    String relation,gender;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_reg1_profile_for, container, false);

        btnContinue= view.findViewById(R.id.btnContinue);
        rel=view.findViewById(R.id.rg_pro_for);
        gen=view.findViewById(R.id.rg_gen);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateFields();
            }
        });
        return view;
    }
    void validateFields() {
        // Check if a profile type is selected
        int checkedRelId=rel.getCheckedRadioButtonId();
        int checkedGenId = gen.getCheckedRadioButtonId();
        if (checkedRelId == -1) {
            Toast.makeText(getContext(), "Please select a profile type", Toast.LENGTH_SHORT).show();
            return;
        }

        if (checkedGenId == -1) {
            Toast.makeText(getContext(), "Please select a gender", Toast.LENGTH_SHORT).show();
            return;
        }
        relsel=getView().findViewById(checkedRelId);
        gensel=getView().findViewById(checkedGenId);
        relation=relsel.getText().toString();
        gender=gensel.getText().toString();

        reg2_nmDob nmDob=new reg2_nmDob();
        Bundle b=new Bundle();
        b.putString("relation",relation);
        b.putString("gender",gender);
        nmDob.setArguments(b);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,nmDob).addToBackStack(null).commit();
    }
}