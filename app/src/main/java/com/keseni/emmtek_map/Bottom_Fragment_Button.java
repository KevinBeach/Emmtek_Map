package com.keseni.emmtek_map;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Kevin Beach on 01/11/2014.
 */
public class Bottom_Fragment_Button extends Fragment{
    private Button mBLocate;
    private SharedPreferences sharedPreferences;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_locate_button,container,false);
        mBLocate = (Button) view.findViewById(R.id.bLocate);
        //Open shared Preferences file
        sharedPreferences = getActivity().getSharedPreferences("LocateData", Context.MODE_PRIVATE);
        //Set onclicklistener for the button
        mBLocate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mPhoneNumber;
                mPhoneNumber = sharedPreferences.getString("phone", "Default");
                if (mPhoneNumber.equals("Default")) {
                    //User hasn't set the phone number of the Emmtek unit in the app
                    //Maybe in future this could automatically bring up the dialog box instead
                    Toast.makeText(getActivity(), "Set EmmTek Phone Number", Toast.LENGTH_LONG).show();

                } else {
                    SmsSend smsSend = new SmsSend(mPhoneNumber, getString(R.string.sms_send_message));
                    if (smsSend.Send()) {
                        //note sending isn't the same as succesfully received! maybe
                        //we can add an received message?
                        Toast.makeText(getActivity(), "Your sms has successfully sent!",
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getActivity(), "Your sms has failed...",
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        return view;
    }
}
