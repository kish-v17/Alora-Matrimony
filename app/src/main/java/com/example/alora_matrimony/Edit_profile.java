package com.example.alora_matrimony;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.alora_matrimony.databinding.FragmentEditProfileBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.apache.commons.text.WordUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;


public class Edit_profile extends Fragment {

    private FragmentEditProfileBinding binding;
    private DatabaseReference databaseReference;
    private UserDetails userDetails;
    ActivityResultLauncher<Intent> activityResultLauncher;
    String userId,gender,image, firstName, lastName, mobileNo, email, password, religion, community, subCommunity, city, state, maritalStatus, height, diet, qualification, occupation,income;
    long dateOfBirth;
    int weight;
    Uri selectedImageUri;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentEditProfileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        retrieveUserDetails();

        return view;
    }

    private void retrieveUserDetails() {
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        databaseReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    userDetails = snapshot.getValue(UserDetails.class);
                    if (userDetails != null) {
                        binding.fnm.setText(userDetails.getFirstName());
                        binding.lnm.setText(userDetails.getLastName());
                        binding.gen.setText(userDetails.getGender());
                        binding.dob.setText(userDetails.getDateOfBirthFormatted());
                        binding.height.setText(userDetails.getHeight());
                        binding.weight.setText(String.valueOf(userDetails.getWeight()));
                        binding.diet.setText(userDetails.getDiet());
                        binding.maritalStatus.setText(userDetails.getMaritalStatus());
                        binding.email.setText(userDetails.getEmail());
                        binding.cno.setText(userDetails.getMobileNo());
                        binding.quali.setText(userDetails.getQualification());
                        binding.profession.setText(userDetails.getOccupation());
                        binding.income.setText(userDetails.getIncome());
                        binding.reg.setText(userDetails.getReligion());
                        binding.commu.setText(userDetails.getCommunity());
                        binding.subcommu.setText(userDetails.getSubCommunity());
                        binding.state.setText(userDetails.getState());
                        binding.city.setText(userDetails.getCity());

                        if (userDetails.getImage() != null && !userDetails.getImage().isEmpty()) {
                            Glide.with(requireContext())
                                    .load(userDetails.getImage())
                                    .placeholder(R.drawable.rectangle_radius_40)
                                    .centerCrop()
                                    .error(R.drawable.rectangle_radius_40)
                                    .into(binding.profilePhoto);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setClickListeners();
        binding.btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                activityResultLauncher.launch(intent);
            }
        });
        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            selectedImageUri = data.getData();
                            Glide.with(requireContext())
                                    .load(selectedImageUri)
                                    .placeholder(R.drawable.rectangle_radius_40)
                                    .centerCrop()
                                    .error(R.drawable.rectangle_radius_40)
                                    .into(binding.profilePhoto);
                        }
                    }
                });
        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void setClickListeners() {
        binding.fnm.setOnClickListener(view -> openEditTextDialog(binding.fnm, "firstName"));
        binding.lnm.setOnClickListener(view -> openEditTextDialog(binding.lnm, "lastName"));
        binding.dob.setOnClickListener(view -> openDatePickerDialog(binding.dob));
        binding.height.setOnClickListener(view -> openDropdownDialog(binding.height, "height",R.array.height));
        binding.weight.setOnClickListener(view -> openEditTextDialog(binding.weight, "weight"));
        binding.diet.setOnClickListener(view -> openDropdownDialog(binding.diet, "diet",R.array.diet));
        binding.maritalStatus.setOnClickListener(view -> openDropdownDialog(binding.maritalStatus, "maritalStatus",R.array.marital_status));
        binding.cno.setOnClickListener(view -> openEditTextDialog(binding.cno, "mobileNo"));
        binding.quali.setOnClickListener(view -> openDropdownDialog(binding.quali, "qualification",R.array.qaulifications));
        binding.profession.setOnClickListener(view -> openDropdownDialog(binding.profession, "occupation",R.array.professions));
        binding.income.setOnClickListener(view -> openDropdownDialog(binding.income, "income",R.array.income));
        binding.reg.setOnClickListener(view -> openReligionDialog());
        binding.state.setOnClickListener(view -> openStateCityDialog());
    }

    private void openReligionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Spiritual Details");

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_religion_subcommunity, null);
        Spinner religionSpinner = dialogView.findViewById(R.id.religionSpinner);
        Spinner communitySpinner = dialogView.findViewById(R.id.communitySpinner);
        Spinner subCommunitySpinner = dialogView.findViewById(R.id.subCommunitySpinner);

        ArrayAdapter<CharSequence> religionAdapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.religion, android.R.layout.simple_spinner_item);
        religionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        religionSpinner.setAdapter(religionAdapter);

        ArrayAdapter<CharSequence> comAdapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.community, android.R.layout.simple_spinner_item);
        comAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        communitySpinner.setAdapter(comAdapter);

        ArrayAdapter<CharSequence> subComAdapter=ArrayAdapter.createFromResource(requireContext(),R.array.default_subcommunity,android.R.layout.simple_spinner_item);
        subCommunitySpinner.setAdapter(subComAdapter);

        religionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] subCommunities;
                switch (position) {
                    case 0: // Select Religion (Default)
                        subCommunities = getResources().getStringArray(R.array.default_subcommunity);
                        break;
                    case 1: // Hindu
                        subCommunities = getResources().getStringArray(R.array.hindu_communities);
                        break;
                    case 2: // Muslim
                        subCommunities = getResources().getStringArray(R.array.muslim_communities);
                        break;
                    case 3: // Christian
                        subCommunities = getResources().getStringArray(R.array.christian_communities);
                        break;
                    case 4: // Jain
                        subCommunities = getResources().getStringArray(R.array.jain_communities);
                        break;
                    case 5: // Sikh
                        subCommunities = getResources().getStringArray(R.array.sikh_communities);
                        break;
                    case 6: // Buddhist
                        subCommunities = getResources().getStringArray(R.array.buddhist_communities);
                        break;
                    case 7: // Parsi
                        subCommunities = getResources().getStringArray(R.array.parsi_communities);
                        break;
                    case 8: // Jewish
                        subCommunities = getResources().getStringArray(R.array.jewish_communities);
                        break;
                    case 9: // Other
                        subCommunities = getResources().getStringArray(R.array.other_communities);
                        break;
                    case 10: // No Religion
                        subCommunities = getResources().getStringArray(R.array.no_religion_communities);
                        break;
                    case 11: // Spiritual - not religious
                        subCommunities = getResources().getStringArray(R.array.spiritual_not_religious_communities);
                        break;
                    default:
                        subCommunities = getResources().getStringArray(R.array.default_subcommunity);
                        break;
                }

                ArrayAdapter<String> subCommunityAdapter = new ArrayAdapter<>(requireContext(),
                        android.R.layout.simple_spinner_item, subCommunities);
                subCommunityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                subCommunitySpinner.setAdapter(subCommunityAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
        builder.setView(dialogView);

        builder.setPositiveButton("OK", (dialog, which) -> {
            binding.reg.setText(religionSpinner.getSelectedItem().toString());
            binding.commu.setText(communitySpinner.getSelectedItem().toString());
            binding.subcommu.setText(subCommunitySpinner.getSelectedItem().toString());
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void openStateCityDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Select State and City");

        // Inflate the layout containing state and city dropdowns
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_state_city, null);
        Spinner stateSpinner = dialogView.findViewById(R.id.stateSpinner);
        Spinner citySpinner = dialogView.findViewById(R.id.citySpinner);

        // Populate state dropdown
        ArrayAdapter<CharSequence> stateAdapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.state, android.R.layout.simple_spinner_item);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(stateAdapter);

        ArrayAdapter<CharSequence> cityAd=ArrayAdapter.createFromResource(requireContext(),R.array.defaultCity,android.R.layout.simple_spinner_item);
        citySpinner.setAdapter(cityAd);

        // Set listener for state selection
        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] cities;
                switch (position) {
                    case 0: // Select State (Default)
                        cities = new String[0]; // No cities for this option
                        break;
                    case 1: // Andaman and Nicobar Islands
                        cities = getResources().getStringArray(R.array.andaman_and_nicobar_islands);
                        break;
                    case 2: // Andhra Pradesh
                        cities = getResources().getStringArray(R.array.andhra_pradesh);
                        break;
                    case 3: // Arunachal Pradesh
                        cities = getResources().getStringArray(R.array.arunachal_pradesh);
                        break;
                    case 4: // Assam
                        cities = getResources().getStringArray(R.array.assam);
                        break;
                    case 5: // Bihar
                        cities = getResources().getStringArray(R.array.bihar);
                        break;
                    case 6: // Chhattisgarh
                        cities = getResources().getStringArray(R.array.chhattisgarh);
                        break;
                    case 7: // Chandigarh
                        cities = getResources().getStringArray(R.array.chandigarh);
                        break;
                    case 8: // Dadra and Nagar Haveli
                        cities = getResources().getStringArray(R.array.dadra_and_nagar_haveli);
                        break;
                    case 9: // Daman and Diu
                        cities = getResources().getStringArray(R.array.daman_and_diu);
                        break;
                    case 10: // Delhi
                        cities = getResources().getStringArray(R.array.delhi);
                        break;
                    case 11: // Goa
                        cities = getResources().getStringArray(R.array.goa);
                        break;
                    case 12: // Gujarat
                        cities = getResources().getStringArray(R.array.gujarat);
                        break;
                    case 13: // Haryana
                        cities = getResources().getStringArray(R.array.haryana);
                        break;
                    case 14: // Himachal Pradesh
                        cities = getResources().getStringArray(R.array.himachal_pradesh);
                        break;
                    case 15: // Jharkhand
                        cities = getResources().getStringArray(R.array.jharkhand);
                        break;
                    case 16: // Karnataka
                        cities = getResources().getStringArray(R.array.karnataka);
                        break;
                    case 17: // Kerala
                        cities = getResources().getStringArray(R.array.kerala);
                        break;
                    case 18: // Lakshadweep
                        cities = getResources().getStringArray(R.array.lakshadweep);
                        break;
                    case 19: // Madhya Pradesh
                        cities = getResources().getStringArray(R.array.madhya_pradesh);
                        break;
                    case 20: // Maharashtra
                        cities = getResources().getStringArray(R.array.maharashtra);
                        break;
                    case 21: // Manipur
                        cities = getResources().getStringArray(R.array.manipur);
                        break;
                    case 22: // Meghalaya
                        cities = getResources().getStringArray(R.array.meghalaya);
                        break;
                    case 23: // Mizoram
                        cities = getResources().getStringArray(R.array.mizoram);
                        break;
                    case 24: // Nagaland
                        cities = getResources().getStringArray(R.array.nagaland);
                        break;
                    case 25: // Odisha
                        cities = getResources().getStringArray(R.array.odisha);
                        break;
                    case 26: // Puducherry
                        cities = getResources().getStringArray(R.array.pondicherry);
                        break;
                    case 27: // Punjab
                        cities = getResources().getStringArray(R.array.punjab);
                        break;
                    case 28: // Rajasthan
                        cities = getResources().getStringArray(R.array.rajasthan);
                        break;
                    case 29: // Sikkim
                        cities = getResources().getStringArray(R.array.sikkim);
                        break;
                    case 30: // Tamil Nadu
                        cities = getResources().getStringArray(R.array.tamil_nadu);
                        break;
                    case 31: // Telangana
                        cities = getResources().getStringArray(R.array.telangana);
                        break;
                    case 32: // Tripura
                        cities = getResources().getStringArray(R.array.tripura);
                        break;
                    case 33: // Uttar Pradesh
                        cities = getResources().getStringArray(R.array.uttar_pradesh);
                        break;
                    case 34: // Uttarakhand
                        cities = getResources().getStringArray(R.array.uttarakhand);
                        break;
                    case 35: // West Bengal
                        cities = getResources().getStringArray(R.array.west_bengal);
                        break;
                    default:
                        cities = getResources().getStringArray(R.array.defaultCity); // Default to an empty array
                        break;
                }

                ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(requireContext(),
                        android.R.layout.simple_spinner_item, cities);
                cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                citySpinner.setAdapter(cityAdapter);
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        builder.setView(dialogView);

        builder.setPositiveButton("OK", (dialog, which) -> {
            binding.state.setText(stateSpinner.getSelectedItem().toString());
            binding.city.setText(citySpinner.getSelectedItem().toString());
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private void openDatePickerDialog(final TextView textView) {
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select Date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build();

        datePicker.addOnPositiveButtonClickListener(selection -> {
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            calendar.setTimeInMillis(selection);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            String selectedDate = dateFormat.format(calendar.getTime());
            textView.setText(selectedDate);
        });

        datePicker.show(requireActivity().getSupportFragmentManager(), "DATE_PICKER");
    }

    private void openEditTextDialog(final TextView textView, final String fieldName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Update " + fieldName);
        final EditText input = new EditText(requireContext());
        input.setText(textView.getText().toString());
        builder.setView(input);
        builder.setPositiveButton("OK", (dialog, which) -> {
            String updatedText = input.getText().toString();
            textView.setText(updatedText);
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    private void openDropdownDialog(final TextView textView, final String fieldName, final int optionsArrayId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Choose " + fieldName);
        final String[] options = getResources().getStringArray(optionsArrayId);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, options);
        builder.setAdapter(adapter, (dialog, which) -> {
            String selectedItem = options[which];
            textView.setText(selectedItem);
        });
        builder.show();
    }
}
