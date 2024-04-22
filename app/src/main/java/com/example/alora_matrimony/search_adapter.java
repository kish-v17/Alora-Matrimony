package com.example.alora_matrimony;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alora_matrimony.R;
import com.example.alora_matrimony.UserDetails;

import org.apache.commons.text.WordUtils;

import java.util.List;

public class search_adapter extends RecyclerView.Adapter<search_adapter.ViewHolder> {

    private List<UserDetails> userList;

    public search_adapter(List<UserDetails> userList) {
        this.userList = userList;
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            usernameTextView = itemView.findViewById(R.id.unm);
            professionTextView = itemView.findViewById(R.id.profession);
        }

        public void bind(UserDetails user) {
            String lnm= WordUtils.capitalizeFully(user.getLastName());
            usernameTextView.setText(WordUtils.capitalizeFully(user.getFirstName())+" "+lnm.charAt(0)+".");
            professionTextView.setText(user.getOccupation());
        }
    }
}
