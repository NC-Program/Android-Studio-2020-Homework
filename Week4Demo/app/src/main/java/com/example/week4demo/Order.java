package com.example.week4demo;

import android.os.Parcel;
import android.os.Parcelable;

public class Order implements Parcelable {
    private String order;
    private int qty;

    public Order(String order, int qty) {
        this.order = order;
        this.qty = qty;
    }

    protected Order(Parcel in) {
        order = in.readString();
        qty = in.readInt();
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

    public String getOrder() {
        return order;
    }

    public int getQty() {
        return qty;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(order);
        parcel.writeInt(qty);
    }

    @Override
    public String toString() {
        return "Order: " + order + ", qty=" + qty;
    }
}
