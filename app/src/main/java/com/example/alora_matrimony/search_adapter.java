package com.example.alora_matrimony;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.alora_matrimony.R;
import com.example.alora_matrimony.UserDetails;

import org.apache.commons.text.WordUtils;

import java.util.List;

public class search_adapter extends RecyclerView.Adapter<search_adapter.ViewHolder> {

    private List<UserDetails> userList;
    Context context;

    public search_adapter(List<UserDetails> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_rv, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserDetails user = userList.get(position);
        holder.bind(user);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView usernameTextView;
        TextView professionTextView;
        ImageView image;
        ConstraintLayout more;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            usernameTextView = itemView.findViewById(R.id.unm);
            professionTextView = itemView.findViewById(R.id.profession);
            image =itemView.findViewById(R.id.img);
            more=itemView.findViewById(R.id.more);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        UserDetails user = userList.get(position);
                        String suid = user.getEmail();
                        Intent intent = new Intent(itemView.getContext(), db2.class);
                        Bundle b = new Bundle();
                        b.putInt("btnId", R.id.more);
                        b.putString("usrEmail", suid);
                        intent.putExtras(b);
                        itemView.getContext().startActivity(intent);
                    }
                }
            });
        }

        public void bind(UserDetails user) {
            String lnm= WordUtils.capitalizeFully(user.getLastName());
            usernameTextView.setText(WordUtils.capitalizeFully(user.getFirstName())+" "+lnm.charAt(0)+".");
            professionTextView.setText(user.getOccupation());
            String imageUrl = user.getImage();
            Glide.with(itemView.getContext())
                    .load(imageUrl)
                    .centerCrop()
                    .into(image);
        }
    }

    public void filterList(List<UserDetails> filteredList) {
        userList = filteredList;
        notifyDataSetChanged();
    }
}
