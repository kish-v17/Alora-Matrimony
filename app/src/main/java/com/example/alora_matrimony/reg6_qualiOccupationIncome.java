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

import com.example.alora_matrimony.databinding.FragmentReg6QualiOccupationIncomeBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class reg6_qualiOccupationIncome extends Fragment {
    AppCompatButton btnCreate;
    Spinner spQual,spincome,spOccupation;
    String uid,gender,image,firstName,lastName,mobile,email,password,religion,community,subCommunity,state,city,maritalStatus,height,diet,qualification,income,occupation;
    long dateOfBirth;
    int weight;
    Uri selectedImageUri;
    DatabaseReference databaseReference;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_reg6_quali_occupation_income, container, false);
        btnCreate=view.findViewById(R.id.btnContinue);
        spQual=view.findViewById(R.id.spQualification);
        spincome=view.findViewById(R.id.spIncome);
        spOccupation=view.findViewById(R.id.spOccupation);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b=getArguments();
                gender=b.getString("gender");
                firstName=b.getString("firstName");
                lastName=b.getString("lastName");
                dateOfBirth=b.getLong("dateOfBirth");
                mobile=b.getString("mobile");
                email=b.getString("email");
                password=b.getString("password");
                religion=b.getString("religion");
                community=b.getString("community");
                subCommunity=b.getString("subCommunity");
                state=b.getString("state");
                city=b.getString("city");
                maritalStatus=b.getString("maritalStatus");
                height=b.getString("height");
                weight=b.getInt("weight");
                diet=b.getString("diet");
                qualification=spQual.getSelectedItem().toString();
                income= spincome.getSelectedItem().toString();
                occupation=spOccupation.getSelectedItem().toString();
                selectedImageUri=Uri.parse(b.getString("image"));

//                Log.d("ProfileFragment", "Data: " + image +"  " + firstName + " "+ lastName +" "+ dateOfBirth);

                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        databaseReference = database.getReference("users");
                        if(task.isSuccessful()){
                               uid= FirebaseAuth.getInstance().getUid();
                               uploadData();
                        }else{
                            Toast.makeText(getActivity(), "Something went wrong..!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
        // Inflate the layout for this fragment
        return view;
    }


    public void uploadData(){
        if(selectedImageUri != null){
            StorageReference sr= FirebaseStorage.getInstance().getReference();
            StorageReference imgRef=sr.child("Profile/"+ System.currentTimeMillis()+".jpg");

            imgRef.putFile(selectedImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if(task.isSuccessful()){
                        imgRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                if(task.isSuccessful()){
                                    Uri downloadedUri=task.getResult();
                                    image=downloadedUri.toString();
                                    UserDetails u=new UserDetails(gender,image,firstName,lastName,mobile,email,password,religion,community,subCommunity,city,state,maritalStatus,height,weight,diet,qualification,occupation,income,dateOfBirth);
                                    databaseReference.child(uid).setValue(u).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(getActivity(), "Registered Successfully", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getActivity(), Dashboard.class));
                                        }
                                    });

                                }else {
                                    Toast.makeText(getActivity(), "Failed to get URL", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else{
                        Toast.makeText(getActivity(), "Failed to upload image", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else{
            Toast.makeText(getActivity(), "Please select an Image", Toast.LENGTH_SHORT).show();
        }
    }
}