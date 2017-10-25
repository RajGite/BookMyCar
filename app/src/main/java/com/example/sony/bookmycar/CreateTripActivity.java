package com.example.sony.bookmycar;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.widget.Toast;

import com.example.sony.bookmycar.fragments.ConfirmTripFragment;
import com.example.sony.bookmycar.fragments.MapFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * Created by SONY on 25-10-2017.
 */
public class CreateTripActivity extends AppCompatActivity{

   MapFragment fragment1;
    ConfirmTripFragment fragment2;
    FragmentManager fm;
    Bundle bundle;
    TripModel trip;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trip);

        fm = getSupportFragmentManager();

        trip = new TripModel();

        fragment1 = MapFragment.newInstance();
        fragment2 = ConfirmTripFragment.newInstance();

        fragment1.setListsner(new FragmentListener() {
            @Override
            public void onClick(TripModel trip) {
                bundle = new Bundle();
                bundle.putParcelable("trip",trip);
                fragment2.setArguments(bundle);
               fm.beginTransaction()
                       .replace(R.id.container,fragment2)
                       .commit();
            }
        });

        fragment2.setListsner(new FragmentListener() {
            @Override
            public void onClick(TripModel trip) {
                uploadTrip(trip);
            }
        });

        if(savedInstanceState == null){
            bundle = new Bundle();
            bundle.putParcelable("trip",trip);
            fragment1.setArguments(bundle);
            fm.beginTransaction()
                    .add(R.id.container,fragment1)
                    .commit();
        }

    }

    private void uploadTrip(TripModel trip){
        DatabaseReference root = FirebaseDatabase.getInstance().getReference();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        String key = root.child(user.getUid()).child("TRIPS").push().getKey();

        root.child(user.getUid()).child("TRIPS").child(key).updateChildren(trip.getTripMap()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(CreateTripActivity.this,"Success",Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
