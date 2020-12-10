package com.example.movieappextension;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class PagerAdapter extends FragmentPagerAdapter {
    private Context context;
    private ArrayList<String> tabTitles=new ArrayList<>();
    public PagerAdapter(@NonNull FragmentManager fm, int behavior, Context context) {
        super(fm, behavior);
        this.context=context;
        tabTitles.add(context.getString(R.string.tab_1));
        tabTitles.add(context.getString(R.string.tab_2));
        tabTitles.add(context.getString(R.string.tab_3));
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                System.out.println("TAB 1 CHOSEN");
                return MovieFragment.newInstance("https://api.themoviedb.org/3/movie/now_playing?api_key="+context.getString(R.string.api_key));
            case 1:
                System.out.println("TAB 2 CHOSEN");
                return MovieFragment.newInstance("https://api.themoviedb.org/3/movie/upcoming?api_key="+context.getString(R.string.api_key));
            case 2:
                System.out.println("TAB 3 CHOSEN");
                return MovieFragment.newInstance(null);
        }
        return null;
    }

    @Override
    public int getCount() {
        return tabTitles.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles.get(position);
    }
}
