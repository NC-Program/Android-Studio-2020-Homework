package com.example.cafebookingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class CuisineAdapter extends RecyclerView.Adapter<CuisineAdapter.CuisineViewHolder> {

    private HashMap<String, Integer> cuisineReferenceMap = new HashMap<>();
    private String[] cuisineList;
    private Context context;
    private int selectedCount = 0;
    private ArrayList<String> selectedCuisineList = new ArrayList<>();


    public CuisineAdapter( String cuisineList) {
        this.cuisineList = cuisineList.split(",");
        cuisineReferenceMap.put("Western", R.drawable.western_icon);
        cuisineReferenceMap.put("Italian", R.drawable.italian_icon);
        cuisineReferenceMap.put("Chinese", R.drawable.chinese_icon);
        cuisineReferenceMap.put("Japanese", R.drawable.japanese_icon);
        cuisineReferenceMap.put("Malay", R.drawable.malay_icon);
        cuisineReferenceMap.put("Indian", R.drawable.indian_icon);
    }

    public ArrayList<String> getSelectedCuisineList() {
        return selectedCuisineList;
    }

    public int getSelectedCount() {
        return selectedCount;
    }

    @NonNull
    @Override
    public CuisineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cuisine_layout, parent, false);
        context = parent.getContext();
        return new CuisineAdapter.CuisineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CuisineViewHolder holder, int position) {
        String cuisine=cuisineList[position].trim();
//        String cuisine = cuisineList.get(position);
        holder.imageView_cuisine.setImageResource(cuisineReferenceMap.get(cuisine));
    }

    @Override
    public int getItemCount() {
        return cuisineList.length;
    }

    public class CuisineViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public CheckBox checkBox_cuisine;
        public ImageView imageView_cuisine;

        public CuisineViewHolder(@NonNull View itemView) {
            super(itemView);

            checkBox_cuisine = itemView.findViewById(R.id.checkBox_cuisine);
            imageView_cuisine = itemView.findViewById(R.id.imageView_cuisine);
            checkBox_cuisine.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int pos = getAdapterPosition();
            if (checkBox_cuisine.isChecked()) {
                selectedCuisineList.add(cuisineList[pos]);
                selectedCount += 1;
            } else {
                selectedCuisineList.remove(cuisineList[pos]);
                selectedCount -= 0;
            }
        }
    }


}
