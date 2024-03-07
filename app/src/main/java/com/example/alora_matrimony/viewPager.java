package com.example.alora_matrimony;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class viewPager extends FragmentPagerAdapter {
    public viewPager(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position==0)
            return new reg1_profileFor();
        else if(position ==1)
            return new reg2_nmDob();
        else if (position==2)
            return  new reg3_contactDetails();
        else if(position==3)
            return new reg4_religionCommunity();
        else if(position==4)
            return new reg5_maritalHeightWeightDiet();
        else
            return new reg6_qualiOccupationIncome();
    }

    @Override
    public int getCount() {
        return 6;
    }

}
