package com.keseni.emmtek_map;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by kbeach on 23/09/2014.
 */
public class SmsReceiver extends BroadcastReceiver {
    SharedPreferences sharedPreferences;
    Context context;
    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        Bundle bundle = intent.getExtras();
        SmsMessage[] msgs = null;
        String messageReceived = "";
        if (bundle != null) {
            // retrieve SMS received
            Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];
            for (int i = 0; i < msgs.length; i++) {
                msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                messageReceived += msgs[i].getMessageBody().toString();
                messageReceived += "\n";
            }
            // get the telephone number from preferences

            sharedPreferences = context.getSharedPreferences("LocateData", Context.MODE_PRIVATE);
            String mPhoneNumber;
            mPhoneNumber = sharedPreferences.getString("phone", "Default");
            String senderPhoneNumber = msgs[0].getOriginatingAddress();
            // Test toasts to verify phone numbers
            //Toast.makeText(context, "senderPhoneNumber " + senderPhoneNumber, Toast.LENGTH_SHORT).show();
            //Toast.makeText(context, "mPhoneNumber " + mPhoneNumber, Toast.LENGTH_SHORT).show();
            // Check the phone numbers are greater than 10 digits
            if (mPhoneNumber.length() > 10 || Constants.DEBUG_OVERRIDE) {

                if (senderPhoneNumber.length() > 10 || Constants.DEBUG_OVERRIDE) {

                    //Use only the last 10 digits to allow both +44 and 0 to register as the same phone number
                    // This will only work in the UK! Looks to see if you can remove the country code??
                    // Add in error traps as well
                    boolean phoneNumberEqual;
                    if (Constants.DEBUG_OVERRIDE)
                        phoneNumberEqual = true;
                    else
                        phoneNumberEqual = mPhoneNumber.substring(mPhoneNumber.length() - 10).equals(senderPhoneNumber.substring(senderPhoneNumber.length() - 10));

                    if (phoneNumberEqual) {
                        // Check the message to see if it is a server push
                        int count = messageReceived.length() - messageReceived.replace(",", "").length();
                        if (count == Constants.NO_OF_COMMAS) {
                            // This is the format for a server push
                            // Now create the serverpush object with messageReceived
                            if(Constants.TOASTS_ON)
                                Toast.makeText(context, "Server Push Received on " + senderPhoneNumber.toString(),Toast.LENGTH_SHORT).show();
                            EmmTekServerData emmTekServerData = new EmmTekServerData(messageReceived);
                            Boolean write_done = Write_To_Preferences(emmTekServerData);

                            // Now set up a notification for a server push
                            //Check the setting in shared preferences and application not visible

                            if(sharedPreferences.getBoolean("GpsNotification",true) && !Globals.isVisible) {

                                // First set up pending intent

                                Intent i = new Intent(context, MapActivity.class);
                                PendingIntent pi = PendingIntent.getActivity(context, 0, i, 0);

                                Notification notification = new NotificationCompat.Builder(context)
                                        .setTicker("Emmtek Data Received")
                                        .setSmallIcon(R.drawable.ic_launcher)
                                        .setContentTitle("SMS received")
                                        .setContentIntent(pi)
                                        .setSound(Uri.parse("android.resource://com.keseni.emmtek_map/" + R.raw.alarm))
                                        .setAutoCancel(true)
                                        .build();
                                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                                notificationManager.notify(0, notification);
                            }
                        }
                    }
                }
            }
        }
    }

    private Boolean Write_To_Preferences(EmmTekServerData emmTekServerData) {

        // write to preferences the emmTekServerData information


        String serverPushItems[] = context.getResources().getStringArray(R.array.serverpushpreferenceitems);



        List<String> items;
        SharedPreferences.Editor editor = sharedPreferences.edit();

        items = emmTekServerData.getItems();

        for (int i = 0; i < items.size() && i < serverPushItems.length; i++){
            editor.putString(serverPushItems[i],items.get(i));
            if(Constants.LOGGING_ON)
                Log.d(serverPushItems[i],items.get(i));
            if (Constants.TOASTS_ON)
                Toast.makeText(context, serverPushItems[i] + " : " + items.get(i), Toast.LENGTH_SHORT).show();

        }
        editor.commit();
        return true;
    }
}
