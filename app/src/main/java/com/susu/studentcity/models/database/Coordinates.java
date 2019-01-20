package com.susu.studentcity.models.database;

import java.io.Serializable;

public class Coordinates implements Serializable {
    private double latitude;
    private double longitude;

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
