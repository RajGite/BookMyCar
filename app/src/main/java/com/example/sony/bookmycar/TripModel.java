package com.example.sony.bookmycar;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;

/**
 * Created by SONY on 25-10-2017.
 */
public class TripModel implements Parcelable{
    private String carType;
    private String startaddress;
    private String destinationaddress;
    private String startDate;
    private String fare;
    private double latitude,longitude;
    private float distance;
    private int numOfDays;
    private UserInformation driver;

    public TripModel() {
    }

    public HashMap<String,Object> getTripMap(){
        HashMap<String,Object> map = new HashMap<>();
        map.put("carType",carType);
        map.put("fare",fare);
        map.put("start-address",startaddress);
        map.put("destination-address",destinationaddress);
        map.put("date",startDate);
        map.put("days",numOfDays);
        //map.put("driver",driver.getName());
        return map;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getFare() {
        return fare;
    }

    public void setFare(String fare) {
        this.fare = fare;
    }

    public UserInformation getDriver() {
        return driver;
    }

    public void setDriver(UserInformation driver) {
        this.driver = driver;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getNumOfDays() {
        return numOfDays;
    }

    public void setNumOfDays(int numOfDays) {
        this.numOfDays = numOfDays;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.carType);
        dest.writeString(this.startaddress);
        dest.writeString(this.destinationaddress);
        dest.writeString(this.startDate);
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
        dest.writeString(this.fare);
        dest.writeInt(this.numOfDays);
        dest.writeFloat(this.distance);
        dest.writeParcelable(this.driver, flags);
    }

    protected TripModel(Parcel in) {
        this.carType = in.readString();
        this.distance=in.readFloat();
        this.startaddress = in.readString();
        this.destinationaddress = in.readString();
        this.startDate = in.readString();
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
        this.fare = in.readString();
        this.numOfDays = in.readInt();
        this.driver = in.readParcelable(UserInformation.class.getClassLoader());
    }

    public static final Creator<TripModel> CREATOR = new Creator<TripModel>() {
        @Override
        public TripModel createFromParcel(Parcel source) {
            return new TripModel(source);
        }

        @Override
        public TripModel[] newArray(int size) {
            return new TripModel[size];
        }
    };

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public void setStartaddress(String startaddress) {
        this.startaddress = startaddress;
    }
    public String getStartaddress() {
        return startaddress;
    }

    public String getDestinationaddress() {
        return destinationaddress;
    }

    public void setDestinationaddress(String destinationaddress) {
        this.destinationaddress = destinationaddress;
    }
}
