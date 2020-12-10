package com.example.movieappextension;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
    private int id;
    private String title;
    private String genre;
    private String overview;
    private String poster;
    private String bigPoster;
    private double popularity;

    public Movie() {
    }

    public Movie(String title, String genre, String overview, String poster, String bigPoster, double popularity) {
        this.title = title;
        this.genre = genre;
        this.overview = overview;
        this.poster = poster;
        this.bigPoster = bigPoster;
        this.popularity = popularity;
    }

    public Movie(int id, String title, String genre, String overview, String poster, String bigPoster, double popularity) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.overview = overview;
        this.poster = poster;
        this.bigPoster = bigPoster;
        this.popularity = popularity;
    }

    protected Movie(Parcel in) {
        id = in.readInt();
        title = in.readString();
        genre = in.readString();
        overview = in.readString();
        poster = in.readString();
        bigPoster = in.readString();
        popularity = in.readDouble();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getBigPoster() {
        return bigPoster;
    }

    public void setBigPoster(String bigPoster) {
        this.bigPoster = bigPoster;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(genre);
        parcel.writeString(overview);
        parcel.writeString(poster);
        parcel.writeString(bigPoster);
        parcel.writeDouble(popularity);
    }
}
