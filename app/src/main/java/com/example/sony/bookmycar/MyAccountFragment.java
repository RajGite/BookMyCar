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

    private FirebaseAuth firebaseAuth;
    private DatabaseReference dataBaseReference;
    private FirebaseDatabase firebaseDatabase;

    private TextView name,address,mobno,email;
    View myView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /*Error
         java.lang.NullPointerException: Attempt to invoke
         virtual method 'android.view.View android.view.View.findViewById(int)' on a null object reference*/
        myView=inflater.inflate(R.layout.my_account_layout,container, false);

        name=(TextView)myView.findViewById(R.id.name);
        address=(TextView)myView.findViewById(R.id.address);
        mobno=(TextView)myView.findViewById(R.id.mobno);
        email=(TextView)myView.findViewById(R.id.email);

        firebaseAuth= FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        dataBaseReference = firebaseDatabase.getReference();

        if(firebaseAuth.getCurrentUser()!=null)
        {
            dataBaseReference.child(firebaseAuth.getCurrentUser().getUid().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {

                    try{
                        name.setText((String)snapshot.child("name").getValue());
                        address.setText((String)snapshot.child("address").getValue());
                        mobno.setText((String)snapshot.child("contact_no").getValue());
                        email.setText((String)snapshot.child("email").getValue());
                    } catch (Throwable e) {
                        System.out.println("Some Problem");
                        e.printStackTrace();
                    }
                }
                @Override public void onCancelled(DatabaseError error) { }
            });

        }
        return myView;
    }
}
