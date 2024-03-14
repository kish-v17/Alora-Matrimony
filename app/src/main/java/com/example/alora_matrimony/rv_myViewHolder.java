package com.example.alora_matrimony;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class rv_myViewHolder extends RecyclerView.ViewHolder {

    ImageView pfp,bigpfp,like,more;

    public rv_myViewHolder(@NonNull View itemView) {
        super(itemView);
        pfp=itemView.findViewById(R.id.pfp);
        bigpfp=itemView.findViewById(R.id.bigpfp);
        like=itemView.findViewById(R.id.like);
        more=itemView.findViewById(R.id.more);
    }
}
