package com.example.alora_matrimony;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.alora_matrimony.databinding.FragmentEditProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.apache.commons.text.WordUtils;

public class Edit_profile extends Fragment {
    DatabaseReference dbr;
    FragmentEditProfileBinding b;
    String uid;

    private static final int DIET_FIELD = 1;
    private static final int MARITAL_STATUS_FIELD = 2;
    private static final int HIGHEST_QUALI=3;
    private static final int PROFESSION=4;
    private static final int INCOME=5;
    private static final int STATE=6;
    private static final int CITY=7;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        b=FragmentEditProfileBinding.inflate(inflater,container,false);
        View view=b.getRoot();

        dbr= FirebaseDatabase.getInstance().getReference();
        uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        readData();

        b.diet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSpinnerDialog(b.diet, DIET_FIELD);
            }
        });
        b.maritalStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSpinnerDialog(b.maritalStatus, MARITAL_STATUS_FIELD);
            }
        });


        // Inflate the layout for this fragment
        return view;
    }

    private void readData() {
        DatabaseReference uRef = dbr.child("users").child(uid);
        uRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists() && isAdded()) {
                    UserDetails user = snapshot.getValue(UserDetails.class);
                    if (user != null) {
                        // Set user's name
                        String userName = WordUtils.capitalizeFully(user.getFirstName())+" "+WordUtils.capitalizeFully(user.getLastName());
                        b.nm.setText(userName);
                        b.diet.setText(user.getDiet());
                        b.email.setText(user.getEmail());
                        b.cno.setText(user.getMobileNo());
                        b.city.setText(user.getCity());
                        b.gen.setText(user.getGender());
                        b.income.setText(user.getIncome());
                        b.maritalStatus.setText(user.getMaritalStatus());
                        b.profession.setText(user.getOccupation());
                        b.quali.setText(user.getQualification());
                        b.state.setText(user.getState());
//                        b.weight.setText(user.getWeight());
//                        b.height.setText(user.getHeight());
//                        b.dob.setText((int) user.getDateOfBirth());

                        // Set user's image using Glide
                        String imageUrl = user.getImage();
                        if (imageUrl != null && !imageUrl.isEmpty()) {
                            Glide.with(requireContext())
                                    .load(imageUrl)
                                    .placeholder(R.drawable.rectangle_radius_40)
                                    .error(R.drawable.rectangle_radius_40)
                                    .fitCenter().centerCrop()
                                    .into(b.profilePhoto);
                        } else {
                            // If no image URL is available, set a default placeholder
                            b.profilePhoto.setImageResource(R.drawable.rectangle_radius_40);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void showSpinnerDialog(final EditText editText, final int fieldIdentifier) {
        // Create AlertDialog with Spinner
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Select an option");


        // Set up the Spinner
        final Spinner spinner = new Spinner(requireContext());

        // Load appropriate string array based on fieldIdentifier
        ArrayAdapter<String> adapter;
        switch (fieldIdentifier) {
            case DIET_FIELD:
                adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.diet));
                break;
            case MARITAL_STATUS_FIELD:
                adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.marital_status));
                break;
            case HIGHEST_QUALI:
                adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.qaulifications));
                break;
            case PROFESSION:
                adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.professions));
                break;
            case INCOME:
                adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.income));
                break;
            // Add cases for other EditText fields here
            default:
                adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, new String[]{});
                break;
        }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Set up the AlertDialog
        builder.setView(spinner)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Get selected item from the Spinner and set it as the hint for the EditText field
                        int selectedIndex = spinner.getSelectedItemPosition();
                        String selectedItem = adapter.getItem(selectedIndex);
                        editText.setHint(selectedItem);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        // Show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}