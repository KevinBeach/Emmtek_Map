package com.keseni.emmtek_map;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RadioButton;

/**
 * Created by Kevin Beach on 13/07/2015.
 */
public class GeoFenceDialog extends DialogFragment {
    public Button ok,cancel;
    public NumberPicker geoFenceNumberPicker;
    public RadioButton alarmArmed;
    OnOkSelectedListener callBack;

    public interface OnOkSelectedListener{
        public void itemsSelected(int geofenceRange, boolean alarmOn);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        //Make sure that the activity container has implemented the callback interface
        //If not throw an exception
        try{
            callBack = (OnOkSelectedListener) activity;
        } catch (ClassCastException e){
            throw new ClassCastException(activity.toString()
                    + " must implement OnOkSelectedListener");
        }
    }

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Integer geoFenceInitial = getArguments().getInt("geofencerange");
        Boolean alarmOnInitial = getArguments().getBoolean("alarmon");
        View view = inflater.inflate(R.layout.geofence_dialog,null);
        ok = (Button) view.findViewById(R.id.geofence_bOK);
        cancel = (Button) view.findViewById(R.id.geofence_bCancel);
        geoFenceNumberPicker = (NumberPicker) view.findViewById(R.id.geofence_nbFenceSize);
        alarmArmed = (RadioButton) view.findViewById(R.id.geofence_rbAlarmArmed);
    // set the number picker max and min
        geoFenceNumberPicker.setMaxValue(99);
        geoFenceNumberPicker.setMinValue(1);

        // set the geoFenceNumberPicker to initial value from MapActivity
        geoFenceNumberPicker.setValue(geoFenceInitial);
        // set the alarm radio button to initial value from Mapactivity
        alarmArmed.setChecked(alarmOnInitial);
        // Should not use activity shared preferences within the fragment!!
     // get the alarm setting in SharedPreferences and set the radiobutton
     //   SharedPreferences sharedPreferences = getActivity().getSharedPreferences("LocateData", Context.MODE_PRIVATE);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.itemsSelected(geoFenceNumberPicker.getValue(),alarmArmed.isChecked());
                dismiss();
            }
        });
        return view;

    }
}
