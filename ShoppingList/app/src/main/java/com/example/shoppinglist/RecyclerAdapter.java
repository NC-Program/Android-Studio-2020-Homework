package com.example.shoppinglist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {
    private List<ShoppingItem> shoppingItemArrayList;
    private Context context;
    private OnListClickListener mOnListClickListener;

    public RecyclerAdapter(List<ShoppingItem> shoppingItemArrayList, Context context, OnListClickListener onListClickListener) {
        this.shoppingItemArrayList = shoppingItemArrayList;
        this.context = context;
        this.mOnListClickListener = onListClickListener;
    }

    @NonNull
    @Override
    public RecyclerAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new RecyclerViewHolder(view, mOnListClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.RecyclerViewHolder holder, int position) {
        holder.textView_item_name.setText(context.getText(R.string.item_name) + shoppingItemArrayList.get(position).getName());
        holder.textView_size.setText(context.getText(R.string.item_size) + shoppingItemArrayList.get(position).getSize());
        holder.textView_quantity.setText(context.getText(R.string.item_quantity) + String.valueOf(shoppingItemArrayList.get(position).getQty()));
        if (shoppingItemArrayList.get(position).isBought()) {
            holder.imageView_icon_condition.setImageResource(R.drawable.completed);
            holder.textView_date_bought.setText(context.getText(R.string.item_date_bought) + shoppingItemArrayList.get(position).getBoughtDate());
            holder.switch_bought.setVisibility(Switch.INVISIBLE);
        } else if (shoppingItemArrayList.get(position).isUrgent()) {
            holder.imageView_icon_condition.setImageResource(R.drawable.urgent);
            holder.textView_date_bought.setVisibility(TextView.INVISIBLE);
        } else {
            holder.imageView_icon_condition.setImageResource(R.drawable.buy);
            holder.textView_date_bought.setVisibility(TextView.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return shoppingItemArrayList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public TextView textView_item_name, textView_quantity, textView_size, textView_date_bought;
        public Switch switch_bought;
        public ImageView imageView_icon_condition;
        OnListClickListener onListClickListener;

        public RecyclerViewHolder(@NonNull View itemView, OnListClickListener onListClickListener) {
            super(itemView);
            textView_item_name = itemView.findViewById(R.id.textView_item_name);
            textView_quantity = itemView.findViewById(R.id.textView_quantity);
            textView_size = itemView.findViewById(R.id.textView_size);
            textView_date_bought = itemView.findViewById(R.id.textView_date_bought);
            switch_bought = itemView.findViewById(R.id.switch_bought);
            imageView_icon_condition = itemView.findViewById(R.id.imageView_icon_condition);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            switch_bought.setOnClickListener(this);
            this.onListClickListener = onListClickListener;



        }

        @Override
        public void onClick(View view) {

            if (view.getId() == R.id.switch_bought) {
                onListClickListener.onBoughtSwitchClick(getAdapterPosition());
            }
            else
                onListClickListener.onListClick(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            onListClickListener.onListtLongClick(getAdapterPosition());
            return true;
        }
    }

    public interface OnListClickListener {
        void onListClick(int position);

        void onListtLongClick(int position);

        void onBoughtSwitchClick(int position);
    }
}
