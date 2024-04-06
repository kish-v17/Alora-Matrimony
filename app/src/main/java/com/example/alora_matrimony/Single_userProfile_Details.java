package com.example.alora_matrimony;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.alora_matrimony.databinding.FragmentSingleUserProfileDetailsBinding;

public class Single_userProfile_Details extends Fragment {

    FragmentSingleUserProfileDetailsBinding b;
    SharedPreferences sp;
    String uid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        b=FragmentSingleUserProfileDetailsBinding.inflate(inflater,container,false);
        View view= b.getRoot();



        Toast.makeText(getContext(), "mail", Toast.LENGTH_SHORT).show();
        // Inflate the layout for this fragment
        return view;
    }
}