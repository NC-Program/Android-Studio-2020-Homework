package com.example.cafebookingapp;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;



public class CafeRestaurants implements Parcelable {
    private String name;
    private String address;
    private String openingHours;
    private String cuisineText;
    private Integer likes;
    private Integer maxDiners;
    private Integer icon;
    private Integer image;

    public CafeRestaurants(String name, String address, String openingHours, String cuisineText, Integer likes, Integer maxDiners, Integer icon, Integer image) {
        this.name = name;
        this.address = address;
        this.openingHours = openingHours;
        this.cuisineText = cuisineText;
        this.likes = likes;
        this.maxDiners = maxDiners;
        this.icon = icon;
        this.image = image;
    }

    protected CafeRestaurants(Parcel in) {
        name = in.readString();
        address = in.readString();
        openingHours = in.readString();
        cuisineText = in.readString();
        if (in.readByte() == 0) {
            likes = null;
        } else {
            likes = in.readInt();
        }
        if (in.readByte() == 0) {
            maxDiners = null;
        } else {
            maxDiners = in.readInt();
        }
        if (in.readByte() == 0) {
            icon = null;
        } else {
            icon = in.readInt();
        }
        if (in.readByte() == 0) {
            image = null;
        } else {
            image = in.readInt();
        }

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(address);
        dest.writeString(openingHours);
        dest.writeString(cuisineText);
        if (likes == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(likes);
        }
        if (maxDiners == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(maxDiners);
        }
        if (icon == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(icon);
        }
        if (image == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(image);
        }

    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CafeRestaurants> CREATOR = new Creator<CafeRestaurants>() {
        @Override
        public CafeRestaurants createFromParcel(Parcel in) {
            return new CafeRestaurants(in);
        }

        @Override
        public CafeRestaurants[] newArray(int size) {
            return new CafeRestaurants[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public String getCuisineText() {
        return cuisineText;
    }

    public Integer getLikes() {
        return likes;
    }

    public Integer getMaxDiners() {
        return maxDiners;
    }

    public Integer getIcon() {
        return icon;
    }

    public Integer getImage() {
        return image;
    }



    public static ArrayList<CafeRestaurants> createCafeRestaurantsList()
    {

        ArrayList<CafeRestaurants>cafeRestaurantsList=new ArrayList<>();

        return cafeRestaurantsList;
    }


}
