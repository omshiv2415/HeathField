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
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        // setting up the parse push Notification
        ParseQuery<ParseInstallation> pQuery = ParseInstallation.getQuery();
        pQuery.whereEqualTo("username", ParseUser.getCurrentUser());
        ParsePush push = new ParsePush();
        push.setQuery(pQuery);
        push.setMessage("Tomorrow You Have an Appointment");
        push.sendInBackground();
        // setting alarm service for notification in the phone if user restart the phone
        // still system will set the alarm by default so patient will get notification
        // 24 hours before the actual appointment date
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