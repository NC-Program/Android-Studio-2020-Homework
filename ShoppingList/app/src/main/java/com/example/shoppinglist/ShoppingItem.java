package com.example.shoppinglist;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

public class ShoppingItem implements Parcelable {
    private String name;
    private String details;
    private String size;
    private String boughtDate;
    private int id;
    private int qty;
    private int bought;
    private int urgent;

    public ShoppingItem() {

    }


    public ShoppingItem(String name, String details, String size, int qty, boolean urgent) {
        this.name = name;
        this.details = details;
        this.size = size;
        this.qty = qty;
        this.urgent = urgent ? 1 : 0;
        this.bought = 0;
    }

    public ShoppingItem(int id, String name, String details, String size, int qty, int urgent, int bought, String boughtDate) {
        this.name = name;
        this.details = details;
        this.size = size;
        this.boughtDate = boughtDate;
        this.id = id;
        this.qty = qty;
        this.bought = bought;
        this.urgent = urgent;
    }

    protected ShoppingItem(Parcel in) {
        name = in.readString();
        details = in.readString();
        size = in.readString();
        boughtDate = in.readString();
        id = in.readInt();
        qty = in.readInt();
        bought = in.readInt();
        urgent = in.readInt();
    }

    public static final Creator<ShoppingItem> CREATOR = new Creator<ShoppingItem>() {
        @Override
        public ShoppingItem createFromParcel(Parcel in) {
            return new ShoppingItem(in);
        }

        @Override
        public ShoppingItem[] newArray(int size) {
            return new ShoppingItem[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getBoughtDate() {
        return boughtDate;
    }

    public void setBoughtDate(String boughtDate) {
        this.boughtDate = boughtDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getBought() {
        return bought;
    }

    public void setBought(int bought) {
        this.bought = bought;
    }

    public int getUrgent() {
        return urgent;
    }

    public void setUrgent(int urgent) {
        this.urgent = urgent;
    }

    //Self define function for checking the bought in boolean
    public boolean isBought() {
        if (this.bought == 1)
            return true;
        else
            return false;
    }

    //Self define function for checking the urgent in boolean
    public boolean isUrgent() {
        if (this.urgent == 1)
            return true;
        else
            return false;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(details);
        parcel.writeString(size);
        parcel.writeString(boughtDate);
        parcel.writeInt(id);
        parcel.writeInt(qty);
        parcel.writeInt(bought);
        parcel.writeInt(urgent);
    }
}
