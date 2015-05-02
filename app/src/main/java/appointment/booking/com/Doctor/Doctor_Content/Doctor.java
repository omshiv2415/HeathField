package appointment.booking.com.Doctor.Doctor_Content;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TabHost;
import android.widget.Toast;

import com.parse.ParseInstallation;
import com.parse.ParseUser;

import appointment.booking.com.heathfield.R;
import appointment.booking.com.patient.PatientLogin;

/**
 * Created by omshiv on 14/02/2015.
 */
public class Doctor extends TabActivity {


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_main);

        // create the TabHost that will contain the Tabs
        TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);
        tabHost.getTabWidget().setDividerDrawable(null);

        final String verify = ParseUser.getCurrentUser().get("Verify").toString();
        final String DcCheck = "Doctor";

        if (verify.equals(DcCheck)) {
            ParseInstallation installation = ParseInstallation.getCurrentInstallation();
            installation.put("username", ParseUser.getCurrentUser());
            installation.saveInBackground();
            TabHost.TabSpec tab1 = tabHost.newTabSpec("First Tab");
            TabHost.TabSpec tab2 = tabHost.newTabSpec("Second Tab");
            TabHost.TabSpec tab3 = tabHost.newTabSpec("Third Tab");

            // Set the Tab name and Activity
            // that will be opened when particular Tab will be selected
            tab1.setIndicator("Patients Booked Appointments", getResources().getDrawable(R.drawable.ladydoc));
            tab1.setContent(new Intent(this, Doctor_Appointments.class));

            tab2.setIndicator("Confirm Prescription", getResources().getDrawable(R.drawable.res));
            tab2.setContent(new Intent(this, Confirm_Prescription.class));

            tab3.setIndicator("Confirm Appointments", getResources().getDrawable(R.drawable.appointmentreminderf));
            tab3.setContent(new Intent(this, Confirm_Appointment.class));


            /** Add the tabs  to the TabHost to display. */
            tabHost.addTab(tab1);
            tabHost.addTab(tab2);
            tabHost.addTab(tab3);

        } else {
            // if patient trying to login with patient login and Id
            // Restricting patient to login in doctor screen
            Toast toast = Toast.makeText(Doctor.this, " Please try Patient Login " +
                    ParseUser.getCurrentUser().getUsername(), Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            Intent takeUserHome = new Intent(Doctor.this, PatientLogin.class);
            startActivity(takeUserHome);
            Doctor.this.finish();

        }

    }
}


