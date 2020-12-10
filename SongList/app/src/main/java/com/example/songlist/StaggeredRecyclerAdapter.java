package com.example.songlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class StaggeredRecyclerAdapter extends RecyclerView.Adapter<StaggeredRecyclerAdapter.ImageViewHolder> {

    Context context;
    ArrayList<Song> songList;

    public StaggeredRecyclerAdapter(Context context, ArrayList<Song> songList) {
        this.context = context;
        this.songList = songList;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.song_card_layout, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        int resId = context.getResources().getIdentifier(songList.get(position).getImage(), "drawable", context.getPackageName());
        holder.imageView_song.setImageResource(resId);
        holder.textView_title.setText(songList.get(position).getTitle());
        holder.textView_artist.setText("Artist: " + songList.get(position).getArtist());
        holder.textView_releasedDate.setText("Released Date: " + songList.get(position).getReleaseDate());
        if (songList.get(position).getGenre() == null)
            holder.textView_genre.setVisibility(View.GONE);
        else
            holder.textView_genre.setText("Genre: " + songList.get(position).getGenre());

    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView_song;
        TextView textView_title, textView_artist, textView_genre, textView_releasedDate;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView_song = itemView.findViewById(R.id.imageView_song);
            textView_title = itemView.findViewById(R.id.textView_song_title);
            textView_artist = itemView.findViewById(R.id.textView_song_artist);
            textView_genre = itemView.findViewById(R.id.textView_song_genre);
            textView_releasedDate = itemView.findViewById(R.id.textView_song_releasedDate);
            imageView_song.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int pos=getAdapterPosition();
            FragmentWebpage fragmentWebpage=FragmentWebpage.newInstance(songList.get(pos));
            ((FragmentActivity)context).getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_layout,fragmentWebpage)
                    .addToBackStack(null)
                    .commit();
        }
    }


}
