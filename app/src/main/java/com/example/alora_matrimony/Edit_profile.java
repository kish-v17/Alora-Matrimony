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
    String selectedState = null;
    String selectedReligion=null;
    private Uri selectedImageUri;
    ActivityResultLauncher<Intent> activityResultLauncher;
    private static final int DIET_FIELD = 1;
    private static final int MARITAL_STATUS_FIELD = 2;
    private static final int HIGHEST_QUALI = 3;
    private static final int PROFESSION = 4;
    private static final int INCOME = 5;
    private static final int STATE = 6;
    private static final int CITY = 7;
    private static final int RELIGION=8;
    private static final int COMMUNITY=9;
    private static final int SUBCOMMUNITY=10;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        b = FragmentEditProfileBinding.inflate(inflater, container, false);
        View view = b.getRoot();

        dbr = FirebaseDatabase.getInstance().getReference();
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
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
        b.quali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSpinnerDialog(b.quali, HIGHEST_QUALI);
            }
        });
        b.profession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSpinnerDialog(b.profession, PROFESSION);
            }
        });
        b.income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSpinnerDialog(b.income, INCOME);
            }
        });
        b.state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSpinnerDialog(b.state, STATE);
            }
        });
        b.city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSpinnerDialog(b.city, CITY);
            }
        });
        b.reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSpinnerDialog(b.reg, RELIGION);
            }
        });
        b.commu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSpinnerDialog(b.commu, COMMUNITY);
            }
        });
        b.subcommu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSpinnerDialog(b.subcommu, SUBCOMMUNITY);
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
                            b.profilePhoto.setImageURI(selectedImageUri);
                        }
                    }
                });
        b.btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                activityResultLauncher.launch(intent);
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
                        String userName = WordUtils.capitalizeFully(user.getFirstName()) + " " + WordUtils.capitalizeFully(user.getLastName());
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
                        b.reg.setText(user.getReligion());
                        b.commu.setText(user.getCommunity());
                        b.subcommu.setText(user.getSubCommunity());
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

    private void updateDataToFirebase() {
        String name = b.nm.getText().toString().trim();
        String diet = b.diet.getText().toString().trim();
        String email = b.email.getText().toString().trim();
        String mobileNo = b.cno.getText().toString().trim();
        String city = b.city.getText().toString().trim();
        String gender = b.gen.getText().toString().trim();
        String income = b.income.getText().toString().trim();
        String maritalStatus = b.maritalStatus.getText().toString().trim();
        String occupation = b.profession.getText().toString().trim();
        String qualification = b.quali.getText().toString().trim();
        String state = b.state.getText().toString().trim();
        String religion = b.reg.getText().toString().trim();
        String community = b.commu.getText().toString().trim();
        String subCommunity = b.subcommu.getText().toString().trim();
        //needed values
        String height = null;
        long dateOfBirth = 0;
        int weight = 0;
        String fnm=null;
        String gen=null;
        String img= String.valueOf(selectedImageUri);
        // Construct the UserDetails object
        UserDetails userDetails = new UserDetails(uid,img,gen,fnm,height,dateOfBirth,weight,name, diet, email, mobileNo, city, gender, income, maritalStatus, occupation, qualification, state, religion, community, subCommunity);

        // Update data in Firebase Realtime Database
        DatabaseReference userRef = dbr.child("users").child(uid);
        userRef.setValue(userDetails)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(requireContext(), "Data updated successfully!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(requireContext(), "Failed to update data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void showSpinnerDialog(final TextView tv, final int fieldIdentifier) {
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
            case STATE:
                adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.state));
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        selectedState = adapter.getItem(position).toString().toLowerCase().replace(" ", "_");
                        b.city.setText("Select City");
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                break;
            case CITY:
                int stateArrayResource = getResources().getIdentifier(selectedState, "array", requireContext().getPackageName());
                adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(stateArrayResource));
                break;
            case RELIGION:
                adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.religion));
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        selectedReligion = adapter.getItem(position).toString().toLowerCase().replace(" ", "_")+"_communities";
                        b.subcommu.setText("Select Sub Community");
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                break;
            case SUBCOMMUNITY:
                int ReligionArrayResource = getResources().getIdentifier(selectedReligion, "array", requireContext().getPackageName());
                adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(ReligionArrayResource));
                break;
            case COMMUNITY:
                adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.community));
                break;
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
                        // Getting selected item from the Spinner and set it as text for the TextView field
                        int selectedIndex = spinner.getSelectedItemPosition();
                        String selectedItem = adapter.getItem(selectedIndex);
                        if (selectedIndex != 0) {
                            tv.setText(selectedItem);
                        }

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