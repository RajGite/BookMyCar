package com.example.sony.bookmycar.fragments;

import android.app.Activity;
import android.content.Intent;
import android.icu.text.DecimalFormat;
import android.icu.text.NumberFormat;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sony.bookmycar.FragmentListener;
import com.example.sony.bookmycar.R;
import com.example.sony.bookmycar.TripModel;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by SONY on 25-10-2017.
 */
public class MapFragment extends Fragment {

    FragmentListener listener;
    TripModel trip;
    Place place1, place;
    double latitude1, longitude1, latitude2, longitude2;
    float distance;
    Location start,end;


    private GoogleMap mMap;
    private int LOCATION_REQUEST_CODE = 123;
    private int PLACE_PICKER_REQUEST_1 = 124;
    private int PLACE_PICKER_REQUEST_2 = 125;

     public static MapFragment newInstance() {
         return new MapFragment();
    }

    public void setListsner( FragmentListener listener){
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map,container,false);

        Bundle bundle = getArguments();
        if (bundle != null) {
            try {
                trip = bundle.getParcelable("trip");
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }

        view.findViewById(R.id.nextButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*fill trip details*/
                start=new Location("start");
                start.setLatitude(latitude1);
                start.setLongitude(longitude1);

                end=new Location("end");
                end.setLatitude(latitude2);
                end.setLongitude(longitude2);

                distance =(start.distanceTo(end));
                distance/=1000;
                int i=((int)distance);
                distance=(float)i;
                System.out.println("Distance"+distance);
                Log.e("Distance",String.valueOf(distance));
                trip.setStartaddress(place1.getAddress().toString());
                trip.setDestinationaddress(place.getAddress().toString());
                trip.setDistance(distance);
                listener.onClick(trip);
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                onFragmentMapReady(googleMap);
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                    startActivityForResult(builder.build(getActivity()), PLACE_PICKER_REQUEST_1);
                } catch (GooglePlayServicesRepairableException e) {
                    Toast.makeText(getActivity(), "Google Play Services error", Toast.LENGTH_LONG).show();
                } catch (GooglePlayServicesNotAvailableException e) {
                    Toast.makeText(getActivity(), "No Google Play Services installed", Toast.LENGTH_LONG).show();
                }
            }
        });

        getActivity().findViewById(R.id.end).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                    startActivityForResult(builder.build(getActivity()), PLACE_PICKER_REQUEST_2);
                } catch (GooglePlayServicesRepairableException e) {
                    Toast.makeText(getActivity(), "Google Play Services error", Toast.LENGTH_LONG).show();
                } catch (GooglePlayServicesNotAvailableException e) {
                    Toast.makeText(getActivity(), "No Google Play Services installed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void addMarker(LatLng location, String str) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 14f));
        LatLng loc1 = new LatLng(location.latitude, location.longitude);
        mMap.addMarker(new MarkerOptions().position(loc1).title(str));
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST_1) {
            if (resultCode == Activity.RESULT_OK) {
                place1 = PlacePicker.getPlace(data, getActivity());
                ((TextView)getActivity().findViewById(R.id.start)).setText(place1.getAddress());
                latitude1 = place1.getLatLng().latitude;
                longitude1 = place1.getLatLng().longitude;
                LatLng myLocation = new LatLng(place1.getLatLng().latitude, place1.getLatLng().longitude);
                addMarker(myLocation, "Pickup");
            }
        } else if (requestCode == PLACE_PICKER_REQUEST_2) {
            if (resultCode == Activity.RESULT_OK) {
                place = PlacePicker.getPlace(data, getActivity());
                ((TextView)getActivity().findViewById(R.id.end)).setText(place.getAddress());
                latitude2 = place.getLatLng().latitude;
                longitude2 = place.getLatLng().longitude;
                LatLng myLocation = new LatLng(place.getLatLng().latitude, place.getLatLng().longitude);
                addMarker(myLocation, "Destination");
            }
        }
    }

    public void onFragmentMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setMapToolbarEnabled(false);
    }

}
