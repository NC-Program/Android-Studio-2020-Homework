package com.example.imagegallery;

import java.util.ArrayList;

public class RecyclerImage {
    private Image imageRecycler;
    private Integer index;

    public RecyclerImage(Image imageRecycler, Integer index) {
        this.imageRecycler = imageRecycler;
        this.index = index;
    }

    public RecyclerImage() {
    }

    public Image getImageRecycler() {
        return imageRecycler;
    }

    public Integer getIndex() {
        return index;
    }

    public void setImageRecycler(Image imageRecycler) {
        this.imageRecycler = imageRecycler;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }
}
