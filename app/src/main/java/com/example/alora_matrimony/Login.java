package com.example.alora_matrimony;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.alora_matrimony.databinding.ActivityLoginBinding;

public class Login extends AppCompatActivity {
    ActivityLoginBinding b;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        b = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());


        b.txtsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), registration.class));
            }
        });
        b.txtLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Dashboard.class));
            }
        });

        b.regGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

//        b.txtLogIn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                startActivity(new Intent(getApplicationContext(), Deshbord.class));
//                FirebaseAuth.getInstance().createUserWithEmailAndPassword("kish.v07@gmail.com", "kv@1234")
//                        .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task)
//                    {
//                        if(task.isSuccessful())
//                        {
//        UserDetails u = new UserDetails(
//                "Relation",
//                "Gender",
//                "First Name",
//                "Last Name",
//                "Mobile Number",
//                "Email",
//                "Religion",
//                "Community",
//                "Caste",
//                "City",
//                "State",
//                "Country",
//                "Marital Status",
//                "Height",
//                "Weight",
//                "Diet",
//                "Qualification",
//                "Occupation",
//                50000.00, // Income as double
//                System.currentTimeMillis() // Date of birth as milliseconds since epoch
//        );
//        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getUid()).setValue(u);
//                         Toast.makeText(Login.this, "Done", Toast.LENGTH_SHORT).show();
//                        } else
//                        {
//                            Toast.makeText(getApplicationContext(), "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//            }
//        });
    }
}
