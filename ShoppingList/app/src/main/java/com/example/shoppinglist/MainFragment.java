package com.example.shoppinglist;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment implements RecyclerAdapter.OnListClickListener {
    private List<ShoppingItem> shoppingItemList;
    private onFragmentInteractionListener fragmentListener;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Context context;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public interface onFragmentInteractionListener {
        public void onFragmentListClick(int position);
        public void onFragmentListLongClick(int position);
        public void onFragmentBoughtSwitchClick(int position);
    }

    public MainFragment() {
        // Required empty public constructor
    }

    public MainFragment(List<ShoppingItem> shoppingItemList, Context context) {
        this.shoppingItemList = shoppingItemList;
        this.context = context;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        RecyclerView recyclerView;
        RecyclerAdapter recyclerAdapter;
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        DividerItemDecoration divider = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(divider);

        recyclerAdapter = new RecyclerAdapter(shoppingItemList, context, this);
        recyclerView.setAdapter(recyclerAdapter);
        return view;
    }

    @Override
    public void onListClick(int position) {
        fragmentListener.onFragmentListClick(position);
    }

    @Override
    public void onListtLongClick(int position) {
        fragmentListener.onFragmentListLongClick(position);
    }

    @Override
    public void onBoughtSwitchClick(int position) {
        fragmentListener.onFragmentBoughtSwitchClick(position);
    }

    public interface OnListClickListener {
        void onListClick(int position);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            fragmentListener = (onFragmentInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement onFragmentInteractionListener");
        }
    }
}