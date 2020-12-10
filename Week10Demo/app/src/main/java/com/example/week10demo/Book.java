package com.example.week10demo;

public class Book {
    private int id;
    private String title;
    private String genre;
    private int image;

    public Book() {
    }

    public Book(String title, String genre, int image) {
        this.title = title;
        this.genre = genre;
        this.image = image;
    }

    public Book(int id, String title, String genre, int image) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.image = image;
    }

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

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
