package com.example.movieappextension;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static List<Movie> movieWatchList;
    private ViewPager viewPager;
    private DatabaseHandler databaseHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHandler = new DatabaseHandler(this, null, null, 1);

        updateMovieWatchList();
        viewPager=findViewById(R.id.viewPager);
        PagerAdapter pagerAdapter=new PagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,this);
        viewPager.setAdapter(pagerAdapter);

    }

    private void updateMovieWatchList() {

        if (movieWatchList != null)
            movieWatchList.clear();
        movieWatchList = databaseHandler.getAllMovie();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (data.getParcelableExtra("SELECTEDMOVIE") != null) {
                Movie movie = data.getParcelableExtra("SELECTEDMOVIE");
                if (requestCode == 1) { // Add item
                    if (resultCode == RESULT_OK) {
                        System.out.println("BEFORE");
                        System.out.println(movieWatchList.size());
                        databaseHandler.addMovie(movie);
                        updateMovieWatchList();
                        System.out.println("After");
                        System.out.println(movieWatchList.size());
                    }
                }
            }
        }
    }
}