package com.example.alora_matrimony;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.alora_matrimony.databinding.FragmentReg6QualiOccupationIncomeBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class reg6_qualiOccupationIncome extends Fragment {
    AppCompatButton btnCreate;
    Spinner spQual,spincome,spOccupation;
    String relation,gender,firstName,lastName,mobile,email,password,religion,community,subCommunity,state,city,maritalStatus,height,diet,qualification,income,occupation;
    long dateOfBirth;
    int weight;
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
                relation=b.getString("relation");
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

                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            UserDetails u=new UserDetails(relation,gender,firstName,lastName,mobile,email,password,religion,community,subCommunity,city,state,maritalStatus,height,weight,diet,qualification,occupation,income,dateOfBirth);
                            FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getUid()).setValue(u);
                            Toast.makeText(getActivity(), "Registered Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getActivity(), Dashboard.class));
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
}