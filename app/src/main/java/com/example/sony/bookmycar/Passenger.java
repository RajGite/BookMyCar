package com.example.sony.bookmycar;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.api.model.StringList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Passenger extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    ArrayList<TripModel> tripModels;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger);
        //android.app.FragmentManager fragmentManager= getFragmentManager();
        //fragmentManager.beginTransaction().replace(R.id.content_frame, new HomeFragment()).commit();

        auth = FirebaseAuth.getInstance();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        new FetchAllDataTask().execute();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Passenger.this,CreateTripActivity.class));
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        TextView headerTextView = (TextView) navigationView.getHeaderView(0).findViewById(R.id.header_textView);
        FirebaseUser user = auth.getCurrentUser();
        if(user != null)
            headerTextView.setText("Signed in as \n"+user.getEmail());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
       android.app.FragmentManager fragmentManager= getFragmentManager();

        if (id == R.id.nav_home_layout) {
            //fragmentManager.beginTransaction().replace(R.id.content_frame, new HomeFragment()).commit();
        } else if (id == R.id.nav_my_account_layout) {
            //fragmentManager.beginTransaction().replace(R.id.content_frame, new MyAccountFragment()).commit();
        } else if (id == R.id.nav_logout_layout) {
            //fragmentManager.beginTransaction().replace(R.id.content_frame, new LogoutFragment()).commit();
            AlertDialog.Builder builder = new AlertDialog.Builder(Passenger.this);
            builder.setMessage("Confirm logout?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            auth.signOut();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private class FetchAllDataTask extends AsyncTask<String,Void,ArrayList<TripModel>> {

        public FetchAllDataTask() {

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(Passenger.this);
            progressDialog.show();
        }

        @Override

        protected ArrayList<TripModel> doInBackground(String... params) {
            Log.i("DoINBackground","Data begining");

            DatabaseReference root = FirebaseDatabase.getInstance().getReference();

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            tripModels = new ArrayList<>();

             DatabaseReference ref = root.child(user.getUid()).child("TRIPS");

            ValueEventListener valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    tripModels.clear();
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){

                        /*
                        * private String carType,address,startDate;
                            private double latitude,longitude,fare;
                            private int numOfDays;
                            private UserInformation driver;
                        * */
                        TripModel trip = new TripModel();
                        trip.setStartaddress((String)snapshot.child("start-address").getValue());
                        trip.setDestinationaddress((String)snapshot.child("destination-address").getValue());
                        trip.setNumOfDays(((Long)snapshot.child("days").getValue()).intValue());
                        trip.setCarType((String)snapshot.child("carType").getValue());
                        trip.setFare((String)snapshot.child("fare").getValue());
                        tripModels.add(trip);
                    }
                    //Toast.makeText(getApplicationContext(),"Data Fetched",Toast.LENGTH_SHORT).show();
                    Log.i("DoINBackground","Data Fetched");
                    updateUI(tripModels);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(getApplicationContext(),"onCancelled "+databaseError, Toast.LENGTH_SHORT).show();
                }
            };

            ref.addValueEventListener(valueEventListener);

            return tripModels;
        }

        @Override
        protected void onPostExecute(ArrayList<TripModel> tripModels) {
            super.onPostExecute(tripModels);
        }
    }

    private void updateUI(ArrayList<TripModel> trips){

        progressDialog.cancel();
        recyclerView.setAdapter(new TripsAdapter(Passenger.this,trips));

    }

}
