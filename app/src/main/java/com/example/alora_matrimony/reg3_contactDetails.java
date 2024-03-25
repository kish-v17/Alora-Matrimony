package com.example.alora_matrimony;

import static com.example.alora_matrimony.R.drawable.view;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

public class reg3_contactDetails extends Fragment {

    AppCompatButton btnContinue;
    EditText etMobile, etEmail, etPassword, etConPassword;
    boolean isPasswordVisable = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reg3_contact_details, container, false);

        btnContinue = view.findViewById(R.id.btnContinue);
        etMobile = view.findViewById(R.id.etMobile);
        etEmail = view.findViewById(R.id.etEmail);
        etPassword = view.findViewById(R.id.etPassword);
        etConPassword = view.findViewById(R.id.etConPassword);

        etPassword.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (isPasswordVisable) {
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    isPasswordVisable = false;
                } else {
                    etPassword.setTransformationMethod(null);
                    isPasswordVisable = true;
                }
                etPassword.setSelection(etPassword.getText().length());
                return true;
            }
        });
        etConPassword.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (isPasswordVisable) {
                    etConPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    isPasswordVisable = false;
                } else {
                    etConPassword.setTransformationMethod(null);
                    isPasswordVisable = true;
                }
                etConPassword.setSelection(etConPassword.getText().length());
                return true;
            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateAndContinue();
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    private boolean isValidEmail(CharSequence email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void validateAndContinue() {
        String mobile = etMobile.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConPassword.getText().toString().trim();

        if (TextUtils.isEmpty(mobile)) {
            etMobile.setError("Mobile number is required");
            etMobile.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Email is required");
            etEmail.requestFocus();
            return;
        }

        if (!isValidEmail(email)) {
            etEmail.setError("Enter a valid email address");
            etEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Password is required");
            etPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            etPassword.setError("Password must be at least 6 characters");
            etPassword.requestFocus();
            return;
        }

        if (!password.equals(confirmPassword)) {
            etConPassword.setError("Passwords do not match");
            etConPassword.requestFocus();
            return;
        }
        reg4_religionCommunity community = new reg4_religionCommunity();
        Bundle b = getArguments();
        b.putString("mobile", mobile);
        b.putString("email", email);
        b.putString("password", password);
        community.setArguments(b);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, community).addToBackStack(null).commit();
    }
}