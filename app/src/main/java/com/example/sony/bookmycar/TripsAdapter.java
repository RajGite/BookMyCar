package com.example.sony.bookmycar;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by SONY on 26-10-2017.
 */
public class TripsAdapter extends RecyclerView.Adapter<TripsAdapter.ViewHolder> {

    Context context;
    ArrayList<TripModel> tripModels;

    TripsAdapter(Context context, ArrayList<TripModel> tripModels){
        this.context = context;
        this.tripModels = tripModels;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView addressTextView1,addressTextView2, daysTextView, dateTextView,fareTextView;
        ViewHolder(View view){
            super(view);
            addressTextView1 = (TextView)view.findViewById(R.id.startaddressTextView);
            addressTextView2 = (TextView)view.findViewById(R.id.destinationaddressTextView);
            daysTextView = (TextView)view.findViewById(R.id.daysTextView);
            dateTextView = (TextView)view.findViewById(R.id.dateTextView);
            fareTextView=(TextView)view.findViewById(R.id.fareTextView);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item_trip,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TripModel trip = tripModels.get(position);
        holder.daysTextView.setText(String.valueOf(trip.getNumOfDays()));
        holder.addressTextView1.setText(trip.getStartaddress());
        holder.addressTextView2.setText(trip.getDestinationaddress());
        holder.fareTextView.setText(trip.getFare());
        holder.dateTextView.setText(trip.getStartDate());
        //driver
    }

    @Override
    public int getItemCount() {
        return tripModels.size();
    }
}
