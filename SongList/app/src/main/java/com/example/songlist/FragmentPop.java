package com.example.songlist;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentPop#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentPop extends Fragment {
    private ViewPager viewPager;
    private RecyclerView recyclerView_tab_pop;
    private StaggeredRecyclerAdapter staggeredRecyclerAdapter;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private Context context;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private ArrayList<Song> songList;




    public FragmentPop() {
        // Required empty public constructor
    }


    public static FragmentPop newInstance(ArrayList<Song> songList) {
        FragmentPop fragment = new FragmentPop();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1, songList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            songList = getArguments().getParcelableArrayList(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pop, container, false);
        context = container.getContext();
        recyclerView_tab_pop = view.findViewById(R.id.recyclerView_tab_pop);
        staggeredGridLayoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView_tab_pop.setLayoutManager(staggeredGridLayoutManager);
        staggeredRecyclerAdapter = new StaggeredRecyclerAdapter(context, songList);
        recyclerView_tab_pop.setAdapter(staggeredRecyclerAdapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }


}