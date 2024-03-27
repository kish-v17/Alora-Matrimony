package com.example.alora_matrimony;

import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class chat_myViewHolder extends RecyclerView.ViewHolder {
    ImageView profileImg;
    TextView usrnm;

    public chat_myViewHolder(@NonNull View itemView) {
        super(itemView);
        profileImg=itemView.findViewById(R.id.pfp_otheruser);
        usrnm=itemView.findViewById(R.id.usrnm_otheruser);
    }
}
