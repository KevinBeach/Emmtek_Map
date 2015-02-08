package com.keseni.emmtek_map;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import java.util.List;

/**
 * Created by kbeach on 23/09/2014.
 */
public class SmsReceiver extends BroadcastReceiver {
    SharedPreferences sharedPreferences;
    @Override
    public void onReceive(Context context, Intent intent) {
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
            if (mPhoneNumber.length() > 10) {

                if (senderPhoneNumber.length() > 10) {

                    //Use only the last 10 digits to allow both +44 and 0 to register as the same phone number
                    // This will only work in the UK! Looks to see if you can remove the country code??
                    // Add in error traps as well

                    if (mPhoneNumber.substring(mPhoneNumber.length() - 10).equals(senderPhoneNumber.substring(senderPhoneNumber.length() - 10))) {
                        // Check the message to see if it is a server push
                        int count = messageReceived.length() - messageReceived.replace(",", "").length();
                        if (count == R.integer.NO_OF_COMMAS) {
                            // This is the format for a server push
                            // Now create the serverpush object with messageReceived
                            EmmTekServerData emmTekServerData = new EmmTekServerData(messageReceived);
                            Boolean write_done = Write_To_Preferences(emmTekServerData);

                        }
                    }
                }
            }
        }
    }

    private Boolean Write_To_Preferences(EmmTekServerData emmTekServerData) {

        // write to preferences the emmTekServerData information

        String serverPushItems[] = new String[R.array.serverpushpreferenceitems];
        List<String> items;
        SharedPreferences.Editor editor = sharedPreferences.edit();

        items = emmTekServerData.getItems();

        for (int i = 0; i <= items.size() && i <= serverPushItems.length; i++){
            editor.putString(serverPushItems[i],items.get(i));
            editor.commit();
        }
        return true;
    }
}
