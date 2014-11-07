package com.keseni.emmtek_map;

import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Kevin Beach on 06/11/2014.
 */
public class LocationDataDialog extends DialogFragment{
    TextView tLat;
    TextView tLon;
    TextView tSpeed;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.location_data_dialog,null);
        getDialog().setTitle("Location Data");
        tLat = (TextView) view.findViewById(R.id.dialog_lat);
        tLon = (TextView) view.findViewById(R.id.dialog_lon);
        tSpeed = (TextView) view.findViewById(R.id.dialog_speed);

        // open up the preferences file
        final SharedPreferences sharedPreferences = getActivity().getSharedPreferences("LocateData", Context.MODE_PRIVATE);
        // Get Latitude, Longitude and speed
        String mLat = sharedPreferences.getString("lat","N/A");
        String mLon = sharedPreferences.getString("lon","N/A");
        String mSpeed = sharedPreferences.getString("speed","N/A");
        // Set these parameters
        tLat.setText(mLat);
        tLon.setText(mLon);
        tSpeed.setText(mSpeed);
        
        return view;
    }
}
