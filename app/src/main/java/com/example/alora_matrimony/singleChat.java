package com.example.alora_matrimony;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.alora_matrimony.databinding.FragmentChatBinding;
import com.example.alora_matrimony.databinding.FragmentSingleChatBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.apache.commons.text.WordUtils;

public class singleChat extends Fragment {

    FragmentSingleChatBinding b;
    DatabaseReference dbr;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        b = FragmentSingleChatBinding.inflate(inflater, container, false);
        View view = b.getRoot();

        dbr= FirebaseDatabase.getInstance().getReference();
        readData();
        b.sendbtn.setOnClickListener(v->{
            String messageText = b.msg.getText().toString();
            b.msg.setText("");
            if(messageText.isEmpty())
            {
                Toast.makeText(getContext(), "Message can't be empty!", Toast.LENGTH_SHORT).show();
            }
            else
            {
                //sendMessage(messageText, receiver);
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    private void readData() {
        Bundle bundle = getArguments();
        String uid = bundle.getString("userMsgId");
        DatabaseReference uRef = dbr.child("users").child(uid);
        uRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && isAdded()) {
                    UserDetails user = snapshot.getValue(UserDetails.class);
                    if (user != null) {
                        // Set user's name
                        String userName = WordUtils.capitalizeFully(user.getFirstName());
                        b.userName.setText(userName);

                        // Set user's image using Glide
                        String imageUrl = user.getImage();
                        if (imageUrl != null && !imageUrl.isEmpty()) {
                            Glide.with(requireContext())
                                    .load(imageUrl)
                                    .placeholder(R.drawable.deshboard_profile_circle)
                                    .error(R.drawable.deshboard_profile_circle)
                                    .circleCrop()
                                    .into(b.imageProfile);
                        } else {
                            // If no image URL is available, set a default placeholder
                            b.imageProfile.setImageResource(R.drawable.deshboard_profile_circle);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}