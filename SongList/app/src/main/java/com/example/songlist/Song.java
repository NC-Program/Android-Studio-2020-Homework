package com.example.songlist;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Song implements Parcelable {
    private String category, image, title, genre, website, releaseDate, artist;

    public Song(String category, String image, String title, String genre, String website, String releaseDate, String artist) {
        this.category = category;
        this.image = image;
        this.title = title;
        this.genre = genre;
        this.website = website;
        this.releaseDate = releaseDate;
        this.artist = artist;
    }

    public String getCategory() {
        return category;
    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public String getWebsite() {
        return website;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getArtist() {
        return artist;
    }

    public static ArrayList<Song> createPopSongs(Context context) {
        ArrayList<Song> popSongList = new ArrayList<>();
        AssetManager assetManager = context.getAssets();
        InputStream inputStream;
        InputStreamReader inputStreamReader;
        BufferedReader bufferedReader;
        String line = null;
        int sizes = 0;
        try {
            inputStream = assetManager.open("song_list.txt");
            inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
            String category, image, title, genre, website, releaseDate, artist;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains("Category:Pop")) {
                    category = "Pop";
                    //Get Image
                    line = bufferedReader.readLine();
                    String[] imageSplit1 = line.split("Image:");
                    String[] imageSplit2 = imageSplit1[1].split("\\.");

                    image = imageSplit2[0];
                    //Get title
                    line = bufferedReader.readLine();
                    String[] titleSplit = line.split("Title:");
                    title = titleSplit[1];
                    //Genre for pop songs is nothing
                    genre=null;
                    //Get website
                    line = bufferedReader.readLine();
                    String[] websiteSplit = line.split("Website:");
                    website = websiteSplit[1];
                    //Get releaseDate
                    line = bufferedReader.readLine();
                    String[] releaseDateSplit = line.split("Release date:");
                    releaseDate = releaseDateSplit[1];
                    //Get releaseDate
                    line = bufferedReader.readLine();
                    String[] artistSplit = line.split("Artist:");
                    artist = artistSplit[1];
                    popSongList.add(new Song(category,image,title, genre, website, releaseDate, artist));
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return popSongList;
    }

    public static ArrayList<Song> createOthersSongs(Context context) {
        ArrayList<Song> othersSongList = new ArrayList<>();
        AssetManager assetManager = context.getAssets();
        InputStream inputStream;
        InputStreamReader inputStreamReader;
        BufferedReader bufferedReader;
        String line = null;
        int sizes = 0;
        try {
            inputStream = assetManager.open("song_list.txt");
            inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
            String category, image, title, genre, website, releaseDate, artist;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains("Category:Others")) {
                    category = "Others";
                    //Get Image
                    line = bufferedReader.readLine();
                    String[] imageSplit1 = line.split("Image:");
                    String[] imageSplit2 = imageSplit1[1].split("\\.");

                    image = imageSplit2[0];
                    //Get title
                    line = bufferedReader.readLine();
                    String[] titleSplit = line.split("Title:");
                    title = titleSplit[1];
                    //Get Genre
                    line = bufferedReader.readLine();
                    String[] genreSplit = line.split("Genre:");
                    genre = genreSplit[1];
                    //Get website
                    line = bufferedReader.readLine();
                    String[] websiteSplit = line.split("Website:");
                    website = websiteSplit[1];
                    //Get releaseDate
                    line = bufferedReader.readLine();
                    String[] releaseDateSplit = line.split("Release date:");
                    releaseDate = releaseDateSplit[1];
                    //Get releaseDate
                    line = bufferedReader.readLine();
                    String[] artistSplit = line.split("Artist:");
                    artist = artistSplit[1];
                    othersSongList.add(new Song(category,image,title, genre, website, releaseDate, artist));
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return othersSongList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
