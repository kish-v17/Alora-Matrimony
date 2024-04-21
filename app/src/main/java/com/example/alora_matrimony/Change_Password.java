package com.example.alora_matrimony;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.alora_matrimony.databinding.FragmentChangePasswordBinding;
import com.example.alora_matrimony.databinding.FragmentProfileBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Change_Password extends Fragment {

    FragmentChangePasswordBinding b;
    DatabaseReference dbr;
    FirebaseAuth mAuth;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        b=FragmentChangePasswordBinding.inflate(inflater,container,false);
        View view=b.getRoot();

        dbr= FirebaseDatabase.getInstance().getReference("users");
        mAuth = FirebaseAuth.getInstance();

        b.btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void changePassword() {
        String oldPassword = b.etoldPassword.getText().toString().trim();
        String newPassword = b.etPassword.getText().toString().trim();
        String confirmPassword = b.etConPassword.getText().toString().trim();

        // Check if new password and confirm password match
        if (!newPassword.equals(confirmPassword)) {
            // Show error message
            Toast.makeText(getContext(), "New password and confirm password do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get the current user
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // Re-authenticate the user with their old password
            AuthCredential credential = EmailAuthProvider.getCredential(currentUser.getEmail(), oldPassword);
            currentUser.reauthenticate(credential)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Re-authentication successful, update password
                            currentUser.updatePassword(newPassword)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            // Password updated successfully
                                            // Now update the password in the Realtime Database
                                            updateUserPasswordInDatabase(currentUser.getUid(), newPassword);
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            // Failed to update password
                                            Toast.makeText(getContext(), "Failed to update password: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Re-authentication failed
                            Toast.makeText(getContext(), "Failed to authenticate user: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void updateUserPasswordInDatabase(String userId, String newPassword) {
        dbr.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Retrieve user details
                UserDetails userDetails = dataSnapshot.getValue(UserDetails.class);
                if (userDetails != null) {
                    // Update password in the UserDetails object
                    userDetails.setPassword(newPassword);
                    // Update password in the Realtime Database
                    dbr.child(userId).setValue(userDetails)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    // Password updated successfully
                                    Toast.makeText(getContext(), "Password updated successfully", Toast.LENGTH_SHORT).show();

                                    // Log out the user
                                    FirebaseAuth.getInstance().signOut();

                                    // Navigate to the login screen
                                    startActivity(new Intent(requireContext(), Login.class));
                                    requireActivity().finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Failed to update password in the Realtime Database
                                    Toast.makeText(getContext(), "Failed to update password in database: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
            }
        });
    }
}