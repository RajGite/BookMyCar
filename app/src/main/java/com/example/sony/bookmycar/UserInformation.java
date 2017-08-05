package com.example.sony.bookmycar;

/**
 * Created by SONY on 29-07-2017.
 */
public class UserInformation {
    private String name,address,contact_no,user,email;

    UserInformation()
    {

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
}
