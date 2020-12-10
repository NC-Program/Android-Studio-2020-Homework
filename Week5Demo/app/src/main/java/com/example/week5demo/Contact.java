package com.example.week5demo;

import java.io.Serializable;
import java.util.ArrayList;

public class Contact implements Serializable {
    private String name;
    private String gender;
    private String email;
    private String number;
    private String type;

    public Contact(String name, String gender, String email, String number, String type) {
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.number = number;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public String getNumber() {
        return number;
    }

    public String getType() {
        return type;
    }

    public static ArrayList<Contact> createContactList() {
        ArrayList<Contact> contactList = new ArrayList<Contact>();
        contactList.add(new Contact("AAAAA", "female", "aaa@gmail.com", "111-1111", "Family"));
        contactList.add(new Contact("BBBBB", "male", "bbb@gmail.com", "222-2222", "Friends"));
        contactList.add(new Contact("CCCCC", "female", "ccc@gmail.com", "333-3333", "Colleague"));
        contactList.add(new Contact("DDDDD", "male", "ddd@gmail.com", "444-4444", "Family"));
        contactList.add(new Contact("EEEEE", "male", "eee@gmail.com", "555-5555", "Friends"));
        contactList.add(new Contact("EEEEE", "male", "eee@gmail.com", "555-5555", "Friends"));
        contactList.add(new Contact("EEEEE", "male", "eee@gmail.com", "555-5555", "Friends"));
        contactList.add(new Contact("EEEEE", "male", "eee@gmail.com", "555-5555", "Friends"));
        return contactList;
    }

    @Override
    public String toString() {
        return "Contact Number:" +number+"\n"+"Email:"+email;
    }
}
