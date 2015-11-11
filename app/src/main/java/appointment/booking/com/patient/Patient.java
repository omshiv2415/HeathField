package appointment.booking.com.patient;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TabHost;
import android.widget.Toast;

import com.parse.ParseInstallation;
import com.parse.ParseUser;

import appointment.booking.com.Doctor.Doctor_Content.DoctorLogin;
import appointment.booking.com.Prescription.PrescriptionActivity;
import appointment.booking.com.Request_Appintment.RequestAppointment;
import appointment.booking.com.booking.BookingAppointment;
import appointment.booking.com.cancel.PatientCancelActivity;
import appointment.booking.com.heathfield.R;

/**
 * Created by omshiv on 14/02/2015.
 */
public class Patient extends TabActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_main);
        // create the TabHost that will contain the Tabs
        TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);
        tabHost.getTabWidget().setDividerDrawable(null);

        // getting parse user is patient or Doctor
        final String verify = ParseUser.getCurrentUser().get("Verify").toString();
        //final String verify =  "Doctor";
        final String DcCheck = "Patient";
        // checking user is patient or doctor
        if (verify.equals(DcCheck)) {
            ParseInstallation installation = ParseInstallation.getCurrentInstallation();
            installation.put("username", ParseUser.getCurrentUser());
            installation.saveInBackground();

            TabHost.TabSpec tab1 = tabHost.newTabSpec("App Booking ");
            TabHost.TabSpec tab2 = tabHost.newTabSpec("App Cancel");
            TabHost.TabSpec tab3 = tabHost.newTabSpec("Pre Order");
            TabHost.TabSpec tab4 = tabHost.newTabSpec("Request");

            // Set the Tab name and Activity
            // that will be opened when particular Tab will be selected
            tab1.setIndicator("Appointment Booking", getResources().getDrawable(R.drawable.appointmentnewf));
            tab1.setContent(new Intent(this, BookingAppointment.class));

            tab2.setIndicator("Appointment Cancel", getResources().getDrawable(R.drawable.appointmentmissedc));
            tab2.setContent(new Intent(this, PatientCancelActivity.class));

            tab3.setIndicator("Prescription Order", getResources().getDrawable(R.drawable.prescriptionf));
            tab3.setContent(new Intent(this, PrescriptionActivity.class));

            tab4.setIndicator("Request Appointment", getResources().getDrawable(R.drawable.requestf));
            tab4.setContent(new Intent(this, RequestAppointment.class));
            /** Add the tabs  to the TabHost to display. */
            tabHost.addTab(tab1);
            tabHost.addTab(tab2);
            tabHost.addTab(tab3);
            tabHost.addTab(tab4);

        } else {
            // if doctor trying to login with doctor login and Id
            // Restricting doctor to login in patient screen
            Toast toast = Toast.makeText(Patient.this, " Please try Doctor Login " +
                    ParseUser.getCurrentUser().getUsername(), Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

            Intent takeUserHome = new Intent(Patient.this, DoctorLogin.class);
            startActivity(takeUserHome);
            Patient.this.finish();

        }
    }
}

