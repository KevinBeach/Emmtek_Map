package com.keseni.emmtek_map;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;


public class SettingsDialog extends DialogFragment {

    CheckBox gpsNotification;
    CheckBox alarmNotification;
    CheckBox vibrate;
    Button ok, cancel;

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.settings_dialog,null);
        final SharedPreferences sharedPreferences = getActivity().getSharedPreferences("LocateData", Context.MODE_PRIVATE);
        getDialog().setTitle("Settings");
        gpsNotification = (CheckBox) view.findViewById(R.id.checkGPSNotification);
        alarmNotification = (CheckBox) view.findViewById(R.id.checkAlarmNotification);
        vibrate = (CheckBox) view.findViewById(R.id.checkVibrate);
        ok = (Button) view.findViewById(R.id.setting_dialog_OK);
        cancel = (Button) view.findViewById(R.id.setting_dialog_Cancel);
        gpsNotification.setChecked(sharedPreferences.getBoolean("GpsNotification",true));
        alarmNotification.setChecked(sharedPreferences.getBoolean("AlarmNotification",true));
        vibrate.setChecked(sharedPreferences.getBoolean("VibrateNotification",true));

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("GpsNotification", gpsNotification.isChecked());
                    editor.putBoolean("AlarmNotification",alarmNotification.isChecked());
                    editor.putBoolean("VibrateNotification",vibrate.isChecked());
                editor.commit();
                dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        return view;
    }


}
