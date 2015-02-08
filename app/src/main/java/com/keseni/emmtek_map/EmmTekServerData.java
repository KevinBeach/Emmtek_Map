package com.keseni.emmtek_map;

import com.google.android.gms.maps.model.LatLng;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
 /* Created by Kevin Beach on 01/02/2015.
 */


public class EmmTekServerData {
//    public static final int NO_OF_COMMAS = 26 ;
  // public static final int NO_OF_COMMAS = R.integer.NO_OF_COMMAS ;

    private List<String> items;
    private String mDeviceName;
    private String mImei;
    private String mTime;
    private String mLat;
    private String mLon;
    private String mSpeed;
    private String mVin;
    private String mVinStatus;

    private String mAnalogueValues[] = new String[3];
    private String mAnalogueStatus[] = new String[3];
    private String mDValue[] = new String[2];
    private String mDStatus[] = new String[2];
    private String mRelay[] = new String[2];
    private String mOut[] = new String[2];
    private String mMoveStatus;

    // constructor which takes the SMS string and converts into individual variables
    public EmmTekServerData(String messageReceived) {
        // first check the number of commas in the string to determine a valid server push
        int count = messageReceived.length() - messageReceived.replace(",", "").length();
        if (count == R.integer.NO_OF_COMMAS) {
            //the message is valid
            //split the String into a string array to allow it to be used with the objects

            items = Arrays.asList(messageReceived.split("\\s*,\\s*"));
            // assign the list items to the various variables
            mDeviceName = items.get(0);
            mImei = items.get(1);
            mTime = items.get(2);
            mLat = items.get(3);
            mLon = items.get(4);
            mSpeed = items.get(5);
            mVin = items.get(6);
            mVinStatus = items.get(7);
            for (int loop = 8; loop < 13 ; loop = loop+2 ) {
                mAnalogueValues[(loop - 8) / 2] = items.get(loop);
                mAnalogueStatus[(loop - 8) / 2] = items.get(loop + 1);
            }
            for (int loop = 14;loop < 17; loop = loop + 2){
                mDValue[(loop - 14) / 2] = items.get(loop);
                mDStatus[(loop - 14) /2] = items.get(loop+1);
            }
            mRelay[1] = items.get(18);
            mRelay[2] = items.get(19);
            mOut[1] = items.get(20);
            mOut[2] = items.get(21);
            mMoveStatus = items.get(22);

        }else
        {
            //set the device name to null to indicate the object is invalid
            mDeviceName = null;
        }
    }

    public String getmDeviceName() {
        return mDeviceName;
    }

    public void setmDeviceName(String mDeviceName) {
        this.mDeviceName = mDeviceName;
    }

    public String getmImei() {
        return mImei;
    }

    public void setmImei(String mImei) {
        this.mImei = mImei;
    }

    public String getmTime() {
        return mTime;
    }

    public void setmTime(String mTime) {
        this.mTime = mTime;
    }

    public String getmLat() {
        return mLat;
    }

    public void setmLat(String mLat) {
        this.mLat = mLat;
    }

    public String getmLon() {
        return mLon;
    }

    public void setmLon(String mLon) {
        this.mLon = mLon;
    }

    public String getmSpeed() {
        return mSpeed;
    }

    public void setmSpeed(String mSpeed) {
        this.mSpeed = mSpeed;
    }

    public String getmVin() {
        return mVin;
    }

    public void setmVin(String mVin) {
        this.mVin = mVin;
    }

    public String getmVinStatus() {
        return mVinStatus;
    }

    public void setmVinStatus(String mVinStatus) {
        this.mVinStatus = mVinStatus;
    }

    public String[] getmAnalogueValues() {
        return mAnalogueValues;
    }

    public void setmAnalogueValues(String[] mAnalogueValues) {
        this.mAnalogueValues = mAnalogueValues;
    }

    public String[] getmAnalogueStatus() {
        return mAnalogueStatus;
    }

    public void setmAnalogueStatus(String[] mAnalogueStatus) {
        this.mAnalogueStatus = mAnalogueStatus;
    }

    public String[] getmDValue() {
        return mDValue;
    }

    public void setmDValue(String[] mDValue) {
        this.mDValue = mDValue;
    }

    public String[] getmDStatus() {
        return mDStatus;
    }

    public void setmDStatus(String[] mDStatus) {
        this.mDStatus = mDStatus;
    }

    public String[] getmRelay() {
        return mRelay;
    }

    public void setmRelay(String[] mRelay) {
        this.mRelay = mRelay;
    }

    public String[] getmOut() {
        return mOut;
    }

    public void setmOut(String[] mOut) {
        this.mOut = mOut;
    }

    public String getmMoveStatus() {
        return mMoveStatus;
    }

    public void setmMoveStatus(String mMoveStatus) {
        this.mMoveStatus = mMoveStatus;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }
}
