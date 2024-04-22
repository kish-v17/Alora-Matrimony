package com.example.alora_matrimony;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.alora_matrimony.databinding.FragmentDeleteProfileBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Delete_Profile extends Fragment {

    FragmentDeleteProfileBinding b;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        b=FragmentDeleteProfileBinding.inflate(inflater,container,false);
        View view=b.getRoot();

        b.btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDeleteAccount();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
    private void onDeleteAccount() {
        String password = b.etPassword.getText().toString().trim();
        String confirmPassword = b.etConPassword.getText().toString().trim();

        // Check if passwords match
        if (!password.equals(confirmPassword)) {
            Toast.makeText(getContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Authenticate user with email and password
            FirebaseAuth.getInstance().signInWithEmailAndPassword(user.getEmail(), password)
                    .addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (task.isSuccessful()) {
                                // Authentication successful, proceed with account deletion
                                deleteUserAccount(user);
                            } else {
                                // Authentication failed
                                Toast.makeText(getContext(), "Authentication failed, please check your password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            // No user is currently signed in
            Toast.makeText(getContext(), "No user is currently signed in", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteUserAccount(FirebaseUser user) {
        // Delete user account from Firebase Authentication
        user.delete().addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()) {
                    // Account deletion successful, delete user record from Realtime Database
                    deleteUserDataFromDatabase(user.getUid());
                } else {
                    // Account deletion failed
                    Toast.makeText(getContext(), "Failed to delete account", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void deleteUserDataFromDatabase(String userId) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userId);
        databaseReference.removeValue().addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()) {
                    // User data deleted from Realtime Database
                    Toast.makeText(getContext(), "Account deleted successfully", Toast.LENGTH_SHORT).show();
                    // Finish the activity or navigate to a different screen if needed
                } else {
                    // Failed to delete user data from Realtime Database
                    Toast.makeText(getContext(), "Failed to delete user data", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}