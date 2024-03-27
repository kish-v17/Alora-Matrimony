package com.example.alora_matrimony;

import android.widget.ImageView;
import android.widget.TextView;

public class chat_itemModel {

    int profileImg;
    String usrnm;

    public chat_itemModel(int profileImg, String  usrnm) {
        this.profileImg = profileImg;
        this.usrnm = usrnm;
    }

    public int getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(int profileImg) {
        this.profileImg = profileImg;
    }

    public String getUsrnm() {
        return usrnm;
    }

    public void setUsrnm(String usrnm) {
        this.usrnm = usrnm;
    }
}
