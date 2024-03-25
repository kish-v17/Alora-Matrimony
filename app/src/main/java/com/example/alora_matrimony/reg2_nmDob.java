package com.example.alora_matrimony;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class reg2_nmDob extends Fragment {

    AppCompatButton btnContinue;
    EditText etFnm,etLnm;
    TextView tvPickDate;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_reg2_nm_dob, container, false);

        btnContinue= view.findViewById(R.id.btnContinue);
        tvPickDate = view.findViewById(R.id.tvPickDate);
        etFnm=view.findViewById(R.id.etFirstName);
        etLnm=view.findViewById(R.id.etLastName);

        MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Select Date of Birth");

        final MaterialDatePicker<Long> materialDatePicker = builder.build();
        tvPickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                materialDatePicker.show(getActivity().getSupportFragmentManager(), "DATE_PICKER");
                if (materialDatePicker != null && !materialDatePicker.isVisible()) {
                    materialDatePicker.show(getActivity().getSupportFragmentManager(), "DATE_PICKER");
                }
            }
        });

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override
            public void onPositiveButtonClick(Long selectedDate) {
//                Calendar calendar = Calendar.getInstance();
//                calendar.setTimeInMillis(selectedDate);
//                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
//                String selectedDateStr = dateFormat.format(calendar.getTime());
//                tvPickDate.setText(selectedDateStr);
                if (selectedDate != null) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(selectedDate);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
                    String selectedDateStr = dateFormat.format(calendar.getTime());
                    tvPickDate.setText(selectedDateStr);
                } else {
                    Toast.makeText(getContext(), "Date is null", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateFields();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

        void validateFields() {
            String fnm = etFnm.getText().toString().trim();
            String lnm = etLnm.getText().toString().trim();
            String dob = tvPickDate.getText().toString().trim();

            if (fnm.isEmpty()) {
                etFnm.setError("Enter first name");
                etFnm.requestFocus();
                return;
            }

            if (lnm.isEmpty()) {
                etLnm.setError("Enter last name");
                etLnm.requestFocus();
                return;
            }

            if (dob.isEmpty()) {
                Toast.makeText(getContext(), "Select date of birth", Toast.LENGTH_SHORT).show();

                return;
            }
        reg3_contactDetails con = new reg3_contactDetails();
        Bundle b=getArguments();
        b.putString("firstName",fnm);
        b.putString("lastName",lnm);
        b.putString("dateOfBirth",dob);
        con.setArguments(b);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, con).addToBackStack(null).commit();

    }
}