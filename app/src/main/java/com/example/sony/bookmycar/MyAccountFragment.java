package com.example.sony.bookmycar;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by SONY on 24-10-2017.
 */

public class MyAccountFragment extends Fragment {


    View myView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /*Error
         java.lang.NullPointerException: Attempt to invoke
         virtual method 'android.view.View android.view.View.findViewById(int)' on a null object reference*/
        myView=inflater.inflate(R.layout.my_account_layout,container, false);


        return myView;
    }
}
