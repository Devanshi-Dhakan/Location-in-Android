package com.example.location;

public class Locationhelper {

    private double Latitude;
    private double Longitude;

    public Locationhelper(double latitude, double longitude) {
        Latitude = latitude;
        Longitude = longitude;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude)
    {
        Latitude = latitude;
    }

    public double getLongitude()
    {
        return Longitude;
    }

    public void setLongitude(double longitude)
    {
        Longitude = longitude;
    }
}


