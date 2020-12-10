package com.example.songlist;

import android.content.Context;
import android.content.res.Resources;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class PagerAdapter extends FragmentPagerAdapter {

    private Context context;
    private String[] tabTitles = new String[2];

    public PagerAdapter(@NonNull FragmentManager fm, int behavior, Context context) {
        super(fm, behavior);
        this.context = context;
        tabTitles[0] = context.getResources().getString(R.string.tabOneTitle);
        tabTitles[1] = context.getResources().getString(R.string.tabTwoTitle);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return FragmentPop.newInstance(Song.createPopSongs(context));
            case 1:
                return FragmentOthers.newInstance(Song.createOthersSongs(context));
        }
        return null;
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
