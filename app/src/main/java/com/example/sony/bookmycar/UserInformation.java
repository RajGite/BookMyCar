package com.example.sony.bookmycar;

/**
 * Created by SONY on 29-07-2017.
 */
public class UserInformation {
    private String name,email;

    UserInformation()
    {

    }

    UserInformation(String name,String email)
    {
        this.name=name;
        this.email=email;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}
