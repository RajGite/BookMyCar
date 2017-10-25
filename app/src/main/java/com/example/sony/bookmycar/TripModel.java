package com.example.sony.bookmycar;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;

/**
 * Created by SONY on 25-10-2017.
 */
public class TripModel implements Parcelable{
    private String carType,address,startDate;
    private double latitude,longitude,fare;
    private int numOfDays;
    private UserInformation driver;

    public TripModel() {
    }

    public HashMap<String,Object> getTripMap(){
        HashMap<String,Object> map = new HashMap<>();
        map.put("carType",carType);
        map.put("fare",fare);
        //map.put("driver",driver.getName());
        return map;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public double getFare() {
        return fare;
    }

    public void setFare(double fare) {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
        dest.writeString(this.address);
        dest.writeString(this.startDate);
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
        dest.writeDouble(this.fare);
        dest.writeInt(this.numOfDays);
        dest.writeParcelable(this.driver, flags);
    }

    protected TripModel(Parcel in) {
        this.carType = in.readString();
        this.address = in.readString();
        this.startDate = in.readString();
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
        this.fare = in.readDouble();
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
}
