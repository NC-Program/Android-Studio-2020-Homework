package com.example.movieappextension;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener {
    private NetworkImageView networkImageView;
    private TextView textView_title_watch, textView_popularity_watch, textView_overview_watch;
    private Button button_add_to_watch_list;
    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initUI();
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            movie = intent.getParcelableExtra("WATCHMOVIE");
            textView_title_watch.setText(movie.getTitle());
            textView_popularity_watch.setText(String.valueOf(movie.getPopularity()));
            textView_overview_watch.setText(String.valueOf(movie.getOverview()));
            ImageLoader imageLoader = MySingleton.getInstance(this).getImageLoader();
            String imageURL = movie.getBigPoster();
            imageLoader.get(imageURL, ImageLoader.getImageListener(networkImageView, R.drawable.ic_launcher_background, android.R.drawable
                    .ic_dialog_alert));
            networkImageView.setImageUrl(imageURL, imageLoader);
            for (Movie a : MainActivity.movieWatchList)
                if (a.getTitle().equals(movie.getTitle()))
                    button_add_to_watch_list.setVisibility(View.INVISIBLE);
        }
    }

    private void initUI() {
        networkImageView = findViewById(R.id.networkImageView_movie_big_poster);
        textView_title_watch = findViewById(R.id.textView_title_watch);
        textView_popularity_watch = findViewById(R.id.textView_popularity_watch);
        textView_overview_watch = findViewById(R.id.textView_overview_watch);
        button_add_to_watch_list = findViewById(R.id.button_add_to_watch_list);
        button_add_to_watch_list.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("SELECTEDMOVIE", movie);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}