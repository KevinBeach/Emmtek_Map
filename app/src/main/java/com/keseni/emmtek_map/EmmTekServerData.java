package com.keseni.emmtek_map;

import com.google.android.gms.maps.model.LatLng;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
 /* Created by Kevin Beach on 01/02/2015.
 */


public class EmmTekServerData {
    private static final int NO_OF_COMMAS = 26 ;

    public enum TriStateStatus {OK,LO,HI};
    public enum DualStateStatus {OFF,ON};
    public enum AlarmStatus {OFF,OK,ALARM};

    private String mDeviceName;
    private String mImei;
    private String mTime;
    private LatLng mLatLng;
    private Integer mSpeed;
    private BigDecimal mVin;
    private TriStateStatus mVinStatus;

    private BigDecimal mAnalogueValues[] = new BigDecimal[3];
    private TriStateStatus mAnalogueStatus[] = new TriStateStatus[3];
    private AlarmStatus mDValue[] = new AlarmStatus[2];
    private DualStateStatus mDStatus[] = new DualStateStatus[2];
    private DualStateStatus mRelay[] = new DualStateStatus[2];
    private DualStateStatus mOut[] = new DualStateStatus[2];
    private AlarmStatus mMoveStatus;

    public EmmTekServerData(String messageReceived) {
        // first check the number of commas in the string to determine a valid server push
        int count = messageReceived.length() - messageReceived.replace(",", "").length();
        if (count == NO_OF_COMMAS) {
            //the message is valid
            //split the String into a string array to allow it to be used with the objects
            List<String> items = Arrays.asList(messageReceived.split("\\s*,\\s*"));
            // assign the list items to the various variables
            mDeviceName = items.get(0);
            mImei = items.get(1);
            mTime = items.get(2);


        }else
        {
            //set the device name to null to indicate the object is invalid
            mDeviceName = null;
        }
    }
}
