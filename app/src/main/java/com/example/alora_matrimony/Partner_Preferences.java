package com.example.alora_matrimony;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alora_matrimony.databinding.FragmentPartnerPreferencesBinding;

public class Partner_Preferences extends Fragment {
    FragmentPartnerPreferencesBinding b;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        b=FragmentPartnerPreferencesBinding.inflate(inflater,container,false);
        View view=b.getRoot();

        return view;
    }
}