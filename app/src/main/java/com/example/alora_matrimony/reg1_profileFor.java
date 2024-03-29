package com.example.alora_matrimony;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.example.alora_matrimony.databinding.FragmentReg1ProfileForBinding;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ktx.Firebase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.List;

public class reg1_profileFor extends Fragment {

    FragmentReg1ProfileForBinding b;
    String fnm,lnm,image;
    private Uri selectedImageUri;
    ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        b=FragmentReg1ProfileForBinding.inflate(inflater,container,false);


        View view =b.getRoot();

        b.btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateFields();
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

        b.btnUpload.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            activityResultLauncher.launch(intent);
        });
        return view;
    }

    public void uploadImg(){
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

    void validateFields() {
        fnm = b.etFirstName.getText().toString().trim();
        lnm = b.etLastName.getText().toString().trim();


        if (TextUtils.isEmpty(fnm)) {
            b.etFirstName.setError("Enter first name");
            b.etFirstName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(lnm)) {
            b.etLastName.setError("Enter last name");
            b.etLastName.requestFocus();
            return;
        }
        uploadImg();
        reg2_nmDob genDob = new reg2_nmDob();
        Bundle b=new Bundle();
        b.putString("firstName",fnm);
        b.putString("lastName",lnm);
        b.putString("image",image);

        genDob.setArguments(b);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, genDob).addToBackStack(null).commit();

    }
}