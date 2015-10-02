package appointment.booking.com.support;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseCrashReporting;
import com.parse.PushService;

import appointment.booking.com.heathfield.Login;


public class AppParse extends Application{
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "rpcplJPWVKUYBUl0r7DOd5lG2eSpKwvxHYb3M9Vm", "vt3kreg6ApoD3LvBFelpU6VEGROj5IRpOOe4Q3Cr");
        PushService.setDefaultPushCallback(this,Login.class);
        // Enable Crash Reporting
       // ParseCrashReporting.enable(this);
       // ParseUser.enableAutomaticUser();
       // ParseACL defaultACL = new ParseACL();
        // If you would like all objects to be private by default, remove this line.
        //defaultACL.setPublicReadAccess(true);
        //ParseACL.setDefaultACL(defaultACL, true);


       
    }

}
