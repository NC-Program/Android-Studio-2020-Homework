package com.example.tabviewer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private String urls[]={"https://specials-images.forbesimg.com/imageserve/5ea0466e9869020006ff53d7/960x0.jpg?cropX1=0&cropX2=1280&cropY1=0&cropY2=720","https://images.newindianexpress.com/uploads/user/imagelibrary/2020/4/23/w1200X800/Sony_adapting_popular.jpg"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager=findViewById(R.id.view_pager);
        PagerAdapter pagerAdapter=new PagerAdapter(getSupportFragmentManager(),FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,urls);
        viewPager.setAdapter(pagerAdapter);
    }
}