package com.example.imagegallery;

import android.os.Parcel;
import android.os.Parcelable;
import android.provider.ContactsContract;

import java.util.ArrayList;

public class Image {
    private String thumbnail;
    private String fullImage;

    public Image(String thumbnail, String fullImage) {
        this.thumbnail = thumbnail;
        this.fullImage = fullImage;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getFullImage() {
        return fullImage;
    }

    public static ArrayList<Image> LoadImage() {
        ArrayList<Image> imageArrayList = new ArrayList<>();
        imageArrayList.add(new Image("https://i.ibb.co/MkyFvNT/japan-thumb.png", "https://i.ibb.co/ggj9swN/japan.png"));//Japan
        imageArrayList.add(new Image("https://i.ibb.co/SvLj5Rb/sarawak-thumb.png", "https://i.ibb.co/RHHzB00/sarawak.png"));//Sarawak
        imageArrayList.add(new Image("https://i.ibb.co/GHyhBzH/singapore-thumb.png", "https://i.ibb.co/yXXKYW4/singapore.png"));//Singapore
        imageArrayList.add(new Image("https://i.ibb.co/dDcR2JL/switzerland-thumb.png", "https://i.ibb.co/vkvTzdv/switzerland.png"));//Switzerland
        imageArrayList.add(new Image("https://i.ibb.co/tKpsxcb/germany-thumb.png", "https://i.ibb.co/hLkN0cB/germany.png"));//Germany
        imageArrayList.add(new Image("https://i.ibb.co/8dX41HM/indonesia-thumb.png", "https://i.ibb.co/8mDv1f8/indonesia.png"));//Indonesia
        return imageArrayList;
    }


}
