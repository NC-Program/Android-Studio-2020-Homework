package com.example.tabviewer;

import android.bluetooth.le.ScanSettings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PagerAdapter extends FragmentPagerAdapter {
    private String[] tabTitles={"FragOne","FragTwo"};
    private String[] urls;
    public PagerAdapter(@NonNull FragmentManager fm, int behavior,String urls[]) {
        super(fm, behavior);
        this.urls=urls;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return FragmentOne.newInstance(urls[position],"Cookie Monster");
            case 1:
                return FragmentTwo.newInstance(urls[position],"Elmo");
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
//        return super.getPageTitle(position);
        return tabTitles[position];
    }
}
