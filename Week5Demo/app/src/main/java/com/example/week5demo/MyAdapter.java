package com.example.week5demo;

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
    private ArrayList<Contact> contactList;
    private Context context;

    public MyAdapter(ArrayList<Contact> contactList) {
        this.contactList = contactList;
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
        Contact contact=contactList.get(position);
        holder.textView_name.setText(contact.getName());
        holder.textView_type.setText(contact.getType());
        if(contact.getGender().equals("female"))
            holder.imageView.setImageResource(R.drawable.female);
        else
            holder.imageView.setImageResource(R.drawable.male);
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView textView_name,textView_type;
        public ImageView imageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_name=itemView.findViewById(R.id.textView_name);
            textView_type=itemView.findViewById(R.id.textView_type);
            imageView=itemView.findViewById(R.id.imageView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int pos=getAdapterPosition();
            Bundle bundle = new Bundle();
            bundle.putSerializable("contact",contactList.get(pos));
            Intent intent=new Intent();
            intent.putExtras(bundle);
            intent.setClass(context,Main2Activity.class);
            context.startActivity(intent);
        }
    }
}
