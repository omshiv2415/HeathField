package appointment.booking.com.support;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;

/**
 * Created by omshiv on 07/03/2015.
 */
public class AlarmReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        // TODO Auto-generated method stub
        // here you can start an activity or service depending on your need
        // for ex you can start an activity to vibrate phone or to ring the phone
       // String phoneNumberReciver = String.valueOf(ParseUser.getCurrentUser().get("Contact_Number"));
       // String message="Hi I will be there later, See You soon";// message to send
      //  SmsManager sms = SmsManager.getDefault();
       // sms.sendTextMessage(phoneNumberReciver, null, message, null, null);
        // Show the toast  like in above screen shot
       // Toast.makeText(context, "Alarm Triggered and SMS Sent", Toast.LENGTH_LONG).show();
        ParseQuery<ParseInstallation> pQuery = ParseInstallation.getQuery();
        pQuery.whereEqualTo("username", ParseUser.getCurrentUser());
        ParsePush push = new ParsePush();
        push.setQuery(pQuery);
        push.setMessage("Tomorrow You Have an Appointment");
        push.sendInBackground();

        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            Intent mServiceIntent = new Intent();
            mServiceIntent.setAction("<appointment.booking.com.support>.BootService");
            ComponentName service = context.startService(mServiceIntent);


            if (null == service) {
                // something really wrong here
            }
        }

    }

}