package com.example.cafebookingapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private ArrayList<CafeRestaurants>cafeRestaurantsList;
    private Context context;
    private int REQUEST_CODE=0;

    public MyAdapter(ArrayList<CafeRestaurants> cafeRestaurantsList) {
        this.cafeRestaurantsList = cafeRestaurantsList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout,parent,false);
        context=parent.getContext();
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CafeRestaurants cafeRestaurants=cafeRestaurantsList.get(position);
        holder.textView_name.setText(cafeRestaurants.getName());
        holder.textView_address.setText(cafeRestaurants.getAddress());
        holder.textView_openingHours.setText(cafeRestaurants.getOpeningHours());
        holder.textView_likes.setText(cafeRestaurants.getLikes().toString());
        holder.imageView_cafe_restaurant_icon.setImageResource(cafeRestaurants.getIcon());
        holder.imageView_location_icon.setImageResource(R.drawable.location_32);
        holder.imageView_like_icon.setImageResource(R.drawable.like_icon);
    }

    @Override
    public int getItemCount() {
        return cafeRestaurantsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView textView_name,textView_address,textView_openingHours,textView_likes;
        public ImageView imageView_cafe_restaurant_icon,imageView_location_icon,imageView_like_icon;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_name=itemView.findViewById(R.id.textView_name);
            textView_address=itemView.findViewById(R.id.textView_address);
            textView_openingHours=itemView.findViewById(R.id.textView_openingHours);
            textView_likes=itemView.findViewById(R.id.textView_likes);
            imageView_cafe_restaurant_icon=itemView.findViewById(R.id.imageView_cafe_restaurant_icon);
            imageView_location_icon=itemView.findViewById(R.id.imageView_location_icon);
            imageView_like_icon=itemView.findViewById(R.id.imageView_like_icon);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int pos=getAdapterPosition();
            Bundle bundle=new Bundle();
            bundle.putParcelable("cafeRestaurant",cafeRestaurantsList.get(pos));
            Intent intent=new Intent();
            intent.putExtras(bundle);
            intent.setClass(context,MainActivity2.class);
            ((Activity)context).startActivityForResult(intent,REQUEST_CODE);
        }
    }
}
