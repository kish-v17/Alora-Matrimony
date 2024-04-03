package com.example.alora_matrimony;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class reg6_qualiOccupationIncome extends Fragment {
    AppCompatButton btnCreate;
    Spinner spQual, spincome, spOccupation;
    String uid, gender, image, firstName, lastName, mobile, email, password, religion, community, subCommunity, state, city, maritalStatus, height, diet, qualification, income, occupation;
    long dateOfBirth;
    int weight;
    Uri selectedImageUri;
    DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_reg6_quali_occupation_income, container, false);
        btnCreate = view.findViewById(R.id.btnContinue);
        spQual = view.findViewById(R.id.spQualification);
        spincome = view.findViewById(R.id.spIncome);
        spOccupation = view.findViewById(R.id.spOccupation);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = getArguments();
                if (b != null) {
                    gender = b.getString("gender");
                    firstName = b.getString("firstName");
                    lastName = b.getString("lastName");
                    dateOfBirth = b.getLong("dateOfBirth");
                    mobile = b.getString("mobile");
                    email = b.getString("email");
                    password = b.getString("password");
                    religion = b.getString("religion");
                    community = b.getString("community");
                    subCommunity = b.getString("subCommunity");
                    state = b.getString("state");
                    city = b.getString("city");
                    maritalStatus = b.getString("maritalStatus");
                    height = b.getString("height");
                    weight = b.getInt("weight");
                    diet = b.getString("diet");
                    qualification = spQual.getSelectedItem().toString();
                    income = spincome.getSelectedItem().toString();
                    occupation = spOccupation.getSelectedItem().toString();
                    selectedImageUri = Uri.parse(b.getString("image"));

                    // Check if image URI is not null
                    if (selectedImageUri != null) {
                        createUserAndUploadData();
                    } else {
                        Toast.makeText(getActivity(), "Please select an Image", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Handle null bundle
                    Toast.makeText(getActivity(), "Error: Missing data", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void createUserAndUploadData() {
        // Create user with Firebase Authentication
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            uid = FirebaseAuth.getInstance().getUid();
                            uploadImage();
                        } else {
                            // Handle authentication failure
                            Toast.makeText(getActivity(), "Failed to create user", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void uploadImage() {
        // Upload image to Firebase Storage
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference imageRef = storageRef.child("Profile/" + System.currentTimeMillis() + ".jpg");

        imageRef.putFile(selectedImageUri)
                .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful()) {
                            // Image uploaded successfully, get download URL
                            imageRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    if (task.isSuccessful()) {
                                        Uri downloadUri = task.getResult();
                                        image = downloadUri.toString();
                                        saveUserDataToDatabase();
                                    } else {
                                        // Handle failure to get image URL
                                        Toast.makeText(getActivity(), "Failed to get URL", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            // Handle failure to upload image
                            Toast.makeText(getActivity(), "Failed to upload image", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void saveUserDataToDatabase() {
        // Save user data to Firebase Realtime Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("users");

        UserDetails userDetails = new UserDetails(uid, gender, image, firstName, lastName, mobile, email, password, religion, community, subCommunity, city, state, maritalStatus, height, weight, diet, qualification, occupation, income, dateOfBirth);
        userDetails.setUserId(uid);
        databaseReference.child(uid).setValue(userDetails)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // Registration successful
                            Toast.makeText(getActivity(), "Registered Successfully", Toast.LENGTH_SHORT).show();
                            // Redirect to Dashboard or another activity
                            startActivity(new Intent(getActivity(), Dashboard.class));
                        } else {
                            // Handle failure to save user data
                            Toast.makeText(getActivity(), "Failed to register user", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
