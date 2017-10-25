package com.example.sony.bookmycar.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sony.bookmycar.FragmentListener;
import com.example.sony.bookmycar.R;
import com.example.sony.bookmycar.TripModel;

/**
 * Created by SONY on 25-10-2017.
 */
public class MapFragment extends Fragment {

    FragmentListener listener;
    TripModel trip;

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
                listener.onClick(trip);
            }
        });

        return view;
    }

}
