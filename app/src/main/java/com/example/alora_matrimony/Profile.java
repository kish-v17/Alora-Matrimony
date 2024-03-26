package com.example.alora_matrimony;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.alora_matrimony.databinding.FragmentProfileBinding;
import com.google.firebase.auth.FirebaseAuth;

public class Profile extends Fragment {
    FragmentProfileBinding b;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        b = FragmentProfileBinding.inflate(inflater, container, false);
        View view = b.getRoot();
        b.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(requireContext(), Login.class));
                requireActivity().finish();
                Toast.makeText(requireContext(), "Logged out successfully", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}