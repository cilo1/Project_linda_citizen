package com.example.cmigayi.projectlindacitizen;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.io.ByteArrayOutputStream;

/**
 * Created by cmigayi on 20-Oct-15.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {
    FragmentManager fm;
    int numOfTabs;
    String activityName;
    Bitmap bitmap;

    public PagerAdapter(FragmentManager fm, int numOfTabs, String actitivityName) {
        super(fm);
        this.numOfTabs = numOfTabs;
        this.activityName = actitivityName;
    }

    @Override
    public Fragment getItem(int position) {
            switch(position){
                case 0:
                    FlaggedPeople flaggedPeople = new FlaggedPeople();
                    return flaggedPeople;
                case 1:
                    FlaggedPlaces flaggedPlaces = new FlaggedPlaces();
                    return flaggedPlaces;
            }
        return null;
    }


    @Override
    public int getCount() {
        return numOfTabs;
    }
}

