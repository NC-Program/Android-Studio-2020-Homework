package com.example.cafebookingapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Reservation implements Parcelable {
    private String cafeRestaurantName;
    private String date;
    private String time;
    private Integer numberOfDiners;
    private ArrayList<String> selectedCuisineList;
    private Integer image;

    public Reservation(String cafeRestaurantName, String date, String time, Integer numberOfDiners, ArrayList<String> selectedCuisineList, Integer image) {
        this.cafeRestaurantName = cafeRestaurantName;
        this.date = date;
        this.time = time;
        this.numberOfDiners = numberOfDiners;
        this.selectedCuisineList = selectedCuisineList;
        this.image = image;
    }

    protected Reservation(Parcel in) {
        cafeRestaurantName = in.readString();
        date = in.readString();
        time = in.readString();
        if (in.readByte() == 0) {
            numberOfDiners = null;
        } else {
            numberOfDiners = in.readInt();
        }
        selectedCuisineList = in.createStringArrayList();
        if (in.readByte() == 0) {
            image = null;
        } else {
            image = in.readInt();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cafeRestaurantName);
        dest.writeString(date);
        dest.writeString(time);
        if (numberOfDiners == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(numberOfDiners);
        }
        dest.writeStringList(selectedCuisineList);
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

    public static final Creator<Reservation> CREATOR = new Creator<Reservation>() {
        @Override
        public Reservation createFromParcel(Parcel in) {
            return new Reservation(in);
        }

        @Override
        public Reservation[] newArray(int size) {
            return new Reservation[size];
        }
    };

    public String getCafeRestaurantName() {
        return cafeRestaurantName;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public Integer getNumberOfDiners() {
        return numberOfDiners;
    }

    public ArrayList<String> getSelectedCuisineList() {
        return selectedCuisineList;
    }

    public Integer getImage() {
        return image;
    }
}
