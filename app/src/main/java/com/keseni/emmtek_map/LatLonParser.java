package com.keseni.emmtek_map;

import android.nfc.FormatException;
import android.widget.Toast;

/**
 * Created by Kevin Beach on 30/11/2014.
 */
public class LatLonParser {


    private String sLat;
    private String sLon;
    private Double dLat;
    private Double dLon;

    // constructors,  use two strings for lat, lon
    LatLonParser(String lat, String lon){
        sLat = lat;
        sLon = lon;
    }


    Double GetdLat (){
        return dLat;
    }

    Double GetdLon (){
        return dLon;
    }
    Boolean ConvToDouble () {

       // first check is to make sure that slat and slon are not null
        if (sLat==null || sLon==null) return false;
        // next check that sLat is 10 characters (including N/S char) and sLon is 11 characters
        // including E/W char
        if(sLat.length() != 10 && sLon.length() != 11) return false;

        // Now convert to a double using the last character to determine sign

        dLat = (Double.parseDouble(sLat.substring(0,2))) + (Double.parseDouble(sLat.substring(2,9)) / 60);
        dLon = (Double.parseDouble(sLon.substring(0,3))) + (Double.parseDouble(sLon.substring(3,10)) / 60);

        if (sLat.substring(9).equals("S")) dLat = dLat * -1;
        if (sLon.substring(10).equals("W")) dLon = dLon * -1;

        return true;
    }
    Boolean CheckForChar (String string){
        return string.matches("-?\\d+(.\\d+)?");
    }
}

