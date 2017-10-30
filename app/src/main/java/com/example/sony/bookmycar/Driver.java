package com.example.sony.bookmycar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Driver extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private TextView textViewWelcome;
    private Button buttonSignout;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    ArrayList<TripModel> tripModels;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        textViewWelcome = (TextView) findViewById(R.id.textViewWelcome);
        buttonSignout = (Button) findViewById(R.id.buttonSignout);

        textViewWelcome.setText("Welcome Driver " + user.getEmail());
        buttonSignout.setOnClickListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        new FetchAllDataTask().execute();

    }

    @Override
    public void onClick(View v) {
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }

    private class FetchAllDataTask extends AsyncTask<String, Void, ArrayList<TripModel>> {

        public FetchAllDataTask() {

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(Driver.this);
            progressDialog.show();
        }

        @Override

        protected ArrayList<TripModel> doInBackground(String... params) {
            Log.i("DoINBackground", "Data begining");

            DatabaseReference root = FirebaseDatabase.getInstance().getReference();


            tripModels = new ArrayList<>();

            DatabaseReference ref = root.child("EkgCAeUeVfchhVCET8YVKpLwUd23").child("TRIPS");

            ValueEventListener valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    tripModels.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        /*
                        * private String carType,address,startDate;
                            private double latitude,longitude,fare;
                            private int numOfDays;
                            private UserInformation driver;
                        * */
                        TripModel trip = new TripModel();
                        trip.setStartaddress((String) snapshot.child("start-address").getValue());
                        trip.setDestinationaddress((String) snapshot.child("destination-address").getValue());
                        trip.setNumOfDays(((Long) snapshot.child("days").getValue()).intValue());
                        trip.setStartDate((String)snapshot.child("date").getValue());
                        trip.setCarType((String) snapshot.child("carType").getValue());
                        trip.setFare((String) snapshot.child("fare").getValue());
                        tripModels.add(trip);
                    }
                    //Toast.makeText(getApplicationContext(),"Data Fetched",Toast.LENGTH_SHORT).show();
                    Log.i("DoINBackground", "Data Fetched");
                    updateUI(tripModels);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(getApplicationContext(), "onCancelled " + databaseError, Toast.LENGTH_SHORT).show();
                }
            };

            ref.addValueEventListener(valueEventListener);

            return tripModels;
        }

        private void updateUI(ArrayList<TripModel> trips) {

            progressDialog.cancel();
            recyclerView.setAdapter(new TripsAdapter(Driver.this, trips));

        }
    }
}

