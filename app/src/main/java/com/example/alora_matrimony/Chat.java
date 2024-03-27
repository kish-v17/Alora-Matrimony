package com.example.alora_matrimony;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class Chat extends Fragment {

    RecyclerView rv_chatList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_chat, container, false);

        rv_chatList=view.findViewById(R.id.rv_chatList);
        List<chat_itemModel> items=new ArrayList<>();

        for (int i=1;i<=5;i++){
            items.add(new chat_itemModel(R.drawable.deshboard_profile_circle,"Username"));
        }
        rv_chatList.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_chatList.setAdapter(new chat_adapter(getContext(),items));

        return view;
    }
}