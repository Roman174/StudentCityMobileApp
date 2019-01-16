package com.susu.studentcity.models.database;


import android.text.TextUtils;

import java.io.Serializable;
import java.util.ArrayList;

public class Hostel implements Serializable {
    public static final String SERIALIZABLE_KEY = "HOSTEL";

    private Coordinates coordinates;
    private String title;
    private String photo;
    private String address;
    private String phone;

    private int numberFloors;
    private int numberStudents;
    private double rating;

    private ArrayList<Stuff> stuffs;
    private ArrayList<Resident> residents;

    public Hostel() {
        coordinates = new Coordinates();
        title = photo = phone = address = null;
        numberFloors = numberStudents = 0;
        rating = 0;

        stuffs = new ArrayList<>();
        residents = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public String getPhoto() {
        return photo;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public int getNumberFloors() {
        return numberFloors;
    }

    public int getNumberStudents() {
        return numberStudents;
    }

    public double getRating() {
        return rating;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public ArrayList<Resident> getResidents() {
        return residents;
    }

    public ArrayList<Stuff> getStuffs() {
        return stuffs;
    }
    
    public Stuff getStuff(String post) {
        if(TextUtils.isEmpty(post))
            return null;

        if(stuffs == null) return null;

        for (Stuff stuff :
                stuffs) {
            if (stuff.getPost().equals(post))
                return stuff;
        }

        return null;
    }
}
