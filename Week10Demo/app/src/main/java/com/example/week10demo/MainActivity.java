package com.example.week10demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DatabaseHandler databaseHandler;
    private List<Book> bookList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView gridView=findViewById(R.id.grid_layout);
        databaseHandler=new DatabaseHandler(this,null,null,1);
        populateDB();
        populateBookList();

        final GridViewAdapter gridViewAdapter=new GridViewAdapter(bookList,this);
        gridView.setAdapter(gridViewAdapter);
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                int result=databaseHandler.deleteBook(bookList.get(i));
                System.out.println(result);
                populateBookList();
                gridViewAdapter.setBookList(bookList);
                gridViewAdapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    private void populateBookList() {
    if(bookList!=null)
        bookList.clear();
    bookList=databaseHandler.getAllBook();
    }

    private void populateDB() {
        databaseHandler.addBook(new Book("AAA00","Horror",R.drawable.cover1));
        databaseHandler.addBook(new Book("BBB00","Horror",R.drawable.cover2));
        databaseHandler.addBook(new Book("CCC00","Horror",R.drawable.cover3));
        databaseHandler.addBook(new Book("DDD00","Mystery",R.drawable.cover4));
        databaseHandler.addBook(new Book("EEE00","Mystery",R.drawable.cover5));
        databaseHandler.addBook(new Book("FFF00","Mystery",R.drawable.cover6));

    }
}