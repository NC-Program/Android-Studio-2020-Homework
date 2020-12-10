package com.example.movieappextension;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.MissingFormatArgumentException;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {
    private JSONArray jsonArray;
    private Context context;

    public RecyclerViewAdapter(JSONArray jsonArray, Context context) {
        this.jsonArray = jsonArray;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_row, parent, false);
        context = view.getContext();
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewHolder holder, int position) {

        try {
            if (jsonArray != null) {
                JSONObject jsonObject = jsonArray.getJSONObject(position);
                holder.textView_title.setText(jsonObject.getString("title"));
                holder.textView_popularity.setText(context.getText(R.string.label_popularity) + jsonObject.getString("popularity"));
                holder.textView_genre.setText(context.getText(R.string.label_genre) + new Genre().getGenre(jsonObject.getJSONArray("genre_ids")));
                ImageLoader imageLoader = MySingleton.getInstance(context).getImageLoader();
                String imageURL = context.getString(R.string.poster_url) + jsonObject.getString(context.getString(R.string.json_key_poster));
                imageLoader.get(imageURL, ImageLoader.getImageListener(holder.networkImageView_movie, R.drawable.ic_launcher_background, android.R.drawable
                        .ic_dialog_alert));
                holder.networkImageView_movie.setImageUrl(imageURL, imageLoader);
            } else {
                holder.textView_title.setText(MainActivity.movieWatchList.get(position).getTitle());
                holder.textView_popularity.setText(context.getText(R.string.label_popularity) + String.valueOf(MainActivity.movieWatchList.get(position).getPopularity()));
                holder.textView_genre.setText(context.getText(R.string.label_genre) + MainActivity.movieWatchList.get(position).getGenre());
                ImageLoader imageLoader = MySingleton.getInstance(context).getImageLoader();
                String imageURL = MainActivity.movieWatchList.get(position).getPoster();
                imageLoader.get(imageURL, ImageLoader.getImageListener(holder.networkImageView_movie, R.drawable.ic_launcher_background, android.R.drawable
                        .ic_dialog_alert));
                holder.networkImageView_movie.setImageUrl(imageURL, imageLoader);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        if (jsonArray == null)
            return MainActivity.movieWatchList.size();
        else
            return jsonArray.length();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView textView_title, textView_genre, textView_popularity;
        public NetworkImageView networkImageView_movie;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_title = itemView.findViewById(R.id.textView_title);
            textView_genre = itemView.findViewById(R.id.textView_genre);
            textView_popularity = itemView.findViewById(R.id.textView_popularity);
            networkImageView_movie = itemView.findViewById(R.id.networkImageView_movie);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int pos = getAdapterPosition();
            System.out.println(pos);
            if (jsonArray != null) {
                try {
                    JSONObject jsonObject = jsonArray.getJSONObject(pos);
                    String title = jsonObject.getString("title");
                    String genre = new Genre().getGenre(jsonObject.getJSONArray("genre_ids"));
                    String overview = jsonObject.getString("overview");
                    String poster = context.getString(R.string.poster_url) + jsonObject.getString(context.getString(R.string.json_key_poster));
                    String bigPoster = context.getString(R.string.poster_big_url) + jsonObject.getString(context.getString(R.string.json_key_bigPoster));
                    String popularity = jsonObject.getString("popularity");
                    System.out.println(title);
                    System.out.println(genre);
                    System.out.println(overview);
                    System.out.println(poster);
                    System.out.println(bigPoster);
                    System.out.println(popularity);

                    Movie movie = new Movie(title, genre, overview, poster, bigPoster, Double.parseDouble(popularity));

                    Intent intent = new Intent();
                    intent.putExtra("WATCHMOVIE", movie);
                    intent.setClass(context, MainActivity2.class);
                    ((MainActivity) context).startActivityForResult(intent, 1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
