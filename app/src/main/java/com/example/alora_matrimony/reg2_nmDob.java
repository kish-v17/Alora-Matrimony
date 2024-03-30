package com.example.alora_matrimony;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
    RadioGroup gen;
    RadioButton gensel;
    TextView tvPickDate;
    long dob;
    String gender;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_reg2_nm_dob, container, false);

        btnContinue= view.findViewById(R.id.btnContinue);
        tvPickDate = view.findViewById(R.id.tvPickDate);
        etFnm=view.findViewById(R.id.etFirstName);
        etLnm=view.findViewById(R.id.etLastName);
        gen=view.findViewById(R.id.rg_gen);

        MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Select Date of Birth");

        final MaterialDatePicker<Long> materialDatePicker = builder.build();
        tvPickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (materialDatePicker != null && !materialDatePicker.isVisible()) {
                    materialDatePicker.show(getActivity().getSupportFragmentManager(), "DATE_PICKER");
                }
            }
        });

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override
            public void onPositiveButtonClick(Long selectedDate) {
                if (selectedDate != null) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(selectedDate);
                    dob=selectedDate;
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
        return view;
    }

    void validateFields() {
        String dobStr = tvPickDate.getText().toString().trim();
        int checkedGenId = gen.getCheckedRadioButtonId();

        if (checkedGenId == -1) {
            Toast.makeText(getContext(), "Please select a gender", Toast.LENGTH_SHORT).show();
            return;
        }

        if (dobStr.isEmpty()) {
            Toast.makeText(getContext(), "Select date of birth", Toast.LENGTH_SHORT).show();

            return;
        }
        gensel=getView().findViewById(checkedGenId);
        gender=gensel.getText().toString();
        reg3_contactDetails con = new reg3_contactDetails();
        Bundle b=getArguments();

        b.putLong("dateOfBirth",dob);
        b.putString("gender",gender);

        con.setArguments(b);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, con).addToBackStack(null).commit();

    }
}