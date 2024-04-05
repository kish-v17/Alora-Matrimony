package com.example.alora_matrimony;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.alora_matrimony.databinding.FragmentHomeBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import org.apache.commons.text.WordUtils;

import java.util.ArrayList;
import java.util.List;

public class Home extends Fragment {

    DatabaseReference dbr;
    FragmentHomeBinding b;
    String uid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        b = FragmentHomeBinding.inflate(inflater, container, false);
        View view = b.getRoot();

        dbr = FirebaseDatabase.getInstance().getReference();
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        readData();
        listData();

        // Inflate the layout for this fragment
        return view;
    }

    private void listData(){
        DatabaseReference usersRef=dbr.child("users");
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<HomeUserList> userList=new ArrayList<>();
                for(DataSnapshot userSnapshot:snapshot.getChildren()){
                    HomeUserList homeUserList=userSnapshot.getValue(HomeUserList.class);
                    String uid2=homeUserList.getUserId();
                    if(homeUserList!=null && !uid.equals(uid2)){
                        userList.add(homeUserList);
                    }
                }
                rv_adapter adapter=new rv_adapter(getContext(),userList);
                b.dbRv.setLayoutManager(new LinearLayoutManager(getContext()));
                b.dbRv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
                        String userName = WordUtils.capitalizeFully(user.getFirstName());
                        b.txtUserName.setText(userName);

                        // Set user's image using Glide
                        String imageUrl = user.getImage();
                        if (imageUrl != null && !imageUrl.isEmpty()) {
                            Glide.with(requireContext())
                                    .load(imageUrl)
                                    .placeholder(R.drawable.deshboard_profile_circle)
                                    .error(R.drawable.deshboard_profile_circle)
                                    .circleCrop()
                                    .into(b.UserProfile);
                        } else {
                            // If no image URL is available, set a default placeholder
                            b.UserProfile.setImageResource(R.drawable.deshboard_profile_circle);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        b.partnerPreferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchdb2(R.id.partner_preferences);
            }
        });
    }



    //method to load fragment based on btn click
    public void launchdb2(int btnId) {
        Intent i = new Intent(getContext(), db2.class);
        Bundle b = new Bundle();
        b.putInt("btnId", btnId);
        i.putExtras(b);
        startActivity(i);
    }
}
