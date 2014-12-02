package com.keseni.emmtek_map;

import android.nfc.FormatException;
import android.widget.Toast;

/**
 * Created by Kevin Beach on 30/11/2014.
 */
public class LatLonParser {

    private static final int SCALING_VALUE = 100; // Scaling value for lat and lon from unit
    private String sLat;
    private String sLon;
    private Double dLat;
    private Double dLon;

    // constructors, can use two strings for lat, lon or just one string if one parameter to parse
    LatLonParser(String lat, String lon){
        sLat = lat;
        sLon = lon;
    }
    LatLonParser(String latlon){
        sLat = sLon = latlon;
    }

    Double GetdLat (){
        return dLat/SCALING_VALUE;
    }

    Double GetdLon (){
        return dLon/SCALING_VALUE;
    }
    Boolean ConvToDouble () {

        //trim the last character if it is a non-numeric number
        if (!CheckForChar(sLat)) {
            sLat = sLat.substring(0,sLat.length()-1);
            if (!CheckForChar(sLat))
                return false;
        }
        if (!CheckForChar(sLon)) {
            sLon = sLon.substring(0,sLon.length()-1);
            if (!CheckForChar(sLon))
                return false;
        }

        // change global dLat dLon to the value in sLat and sLon
        // error catching returns a false.

        try {
            dLat = Double.parseDouble(sLat);
            dLon = Double.parseDouble(sLon);
        } catch (NumberFormatException e) {
            dLat.equals("0");
            dLon.equals("0");
            return false;
        } catch (NullPointerException e) {
            dLat.equals("0");
            dLon.equals("0");
            return false;
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    Boolean CheckForChar (String string){
        return string.matches("-?\\d+(.\\d+)?");
    }
}

