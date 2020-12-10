package com.example.week10demo;

import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class GridViewAdapter extends BaseAdapter {
    private List<Book> bookList;
    private Context context;

    public GridViewAdapter(List<Book> bookList, Context context) {
        this.bookList = bookList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return bookList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Book book=bookList.get(i);
        if(view==null)
        {
            final LayoutInflater layoutInflater=LayoutInflater.from(context);
            view=layoutInflater.inflate(R.layout.item_view,null);
            MyViewHolder viewHolder=new MyViewHolder(view);
            view.setTag(viewHolder);
        }
        MyViewHolder viewHolder=(MyViewHolder)view.getTag();
        viewHolder.imageView.setImageResource(book.getImage());
        viewHolder.textView_title.setText(book.getTitle());
        viewHolder.textView_genre.setText(book.getGenre());
        return view;
    }

    private class MyViewHolder {
        public TextView textView_title,textView_genre;
        public ImageView imageView;
        public MyViewHolder(View view) {
            textView_title=view.findViewById(R.id.textView_title);
            textView_genre=view.findViewById(R.id.textView_genre);
            imageView=view.findViewById(R.id.imageView);
        }
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }
}
