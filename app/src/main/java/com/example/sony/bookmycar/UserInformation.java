package com.example.sony.bookmycar;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;

/**
 * Created by SONY on 29-07-2017.
 */
public class UserInformation implements Parcelable{
    private String name,address,contact_no,user,email;

    UserInformation() {}

    public HashMap<String,Object> getDriverMap(){
        HashMap<String,Object> map = new HashMap<>();
        map.put("name",name);
        map.put("address",address);
        map.put("contact_no",contact_no);

        return map;
    }

    UserInformation(String name,String address,String contact_no,String user,String email)
    {
        this.name=name;
        this.address=address;
        this.contact_no=contact_no;
        this.user=user;
        this.email=email;
    }

    public String getAddress() {
        return address;
    }

    public String getContact_no() {
        return contact_no;
    }

    public String getUser() {
        return user;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.address);
        dest.writeString(this.contact_no);
        dest.writeString(this.user);
        dest.writeString(this.email);
    }

    protected UserInformation(Parcel in) {
        this.name = in.readString();
        this.address = in.readString();
        this.contact_no = in.readString();
        this.user = in.readString();
        this.email = in.readString();
    }

    public static final Creator<UserInformation> CREATOR = new Creator<UserInformation>() {
        @Override
        public UserInformation createFromParcel(Parcel source) {
            return new UserInformation(source);
        }

        @Override
        public UserInformation[] newArray(int size) {
            return new UserInformation[size];
        }
    };
}
