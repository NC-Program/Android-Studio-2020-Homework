package com.example.songlist;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentOthers#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentOthers extends Fragment {
    private RecyclerView recyclerView_tab_others;
    private StaggeredRecyclerAdapter staggeredRecyclerAdapter;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private Context context;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private ArrayList<Song> songList;

    public FragmentOthers() {
        // Required empty public constructor
    }


    public static FragmentOthers newInstance(ArrayList<Song> songList) {
        FragmentOthers fragment = new FragmentOthers();
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
        View view = inflater.inflate(R.layout.fragment_others, container, false);
        context = container.getContext();
        recyclerView_tab_others = view.findViewById(R.id.recyclerView_tab_others);
        staggeredGridLayoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView_tab_others.setLayoutManager(staggeredGridLayoutManager);
        ArrayList<Song> songList = Song.createOthersSongs(context);

        staggeredRecyclerAdapter = new StaggeredRecyclerAdapter(context, songList);
        recyclerView_tab_others.setAdapter(staggeredRecyclerAdapter);
        return view;
    }
}