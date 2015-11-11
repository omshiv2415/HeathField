package appointment.booking.com.Doctor.Doctor_Support;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.RefreshCallback;

import java.util.List;

import appointment.booking.com.heathfield.R;
import appointment.booking.com.patient.Patient;
import appointment.booking.com.support.ConnectionDetector;

public class Confirm_Appointments_View extends Activity {
    // Declare Variables
    Boolean isInternetPresent = true;
    TextView DrID;
    TextView DrName;
    TextView Appointment;
    TextView AppointmentNO;
    String rank;
    String country;
    String population;
    String cancel;
    String book;
    String Appointment_number;
    Button appButton;
    Button cancelAppointment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirmappointmentview);
        // Retrieve data from booking appointment on item click event
        Intent i = getIntent();
        // Get the results of rank
        rank = i.getStringExtra("D_Id");
        // Get the results of country
        country = i.getStringExtra("D_Name");
        // Get the results of population
        population = i.getStringExtra("P_Appointment");
        // Get the results of Cancel to Display cancel button
        cancel = i.getStringExtra("Cancel");
        // Get the results of Book to Display Booking button
        book = i.getStringExtra("Book");
        // Get the results of AppointmentNO
        Appointment_number = i.getStringExtra("P_Appointment_No");

        // Locate the TextViews in singleitemview.xml
        DrID = (TextView) findViewById(R.id.Doctor_id);
        DrName = (TextView) findViewById(R.id.Doctor_name);
        Appointment = (TextView) findViewById(R.id.Appointment);
        appButton = (Button)findViewById(R.id.appointment);
        AppointmentNO= (TextView)findViewById(R.id.Appointment_no);
        cancelAppointment =(Button)findViewById(R.id.appointment_Cancel);

        // Load the results into the TextViews
        DrID.setText(rank);
        DrName.setText(country);
        Appointment.setText(population);
        AppointmentNO.setText(Appointment_number);

        if(book.equals("BookAppointment")) {
            appButton.setVisibility(View.VISIBLE);
            cancelAppointment.setVisibility(View.GONE);
        }else if(cancel.equals("CancelAppointment")){
            appButton.setVisibility(View.GONE);
            cancelAppointment.setVisibility(View.VISIBLE);

        }

        appButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectionDetector cd;
                // creating connection detector class instance
                cd = new ConnectionDetector(getApplicationContext());
                // get Internet status
                isInternetPresent = cd.isConnectingToInternet();
                // check for Internet status

                if (isInternetPresent) {
                    ParseObject PatientAppointment = new ParseObject("PatientAppointment");
                    String drid = DrID.getText().toString().trim();
                    String drname = DrName.getText().toString().trim();
                    String appointment = Appointment.getText().toString().trim();
                    String AppNo = AppointmentNO.getText().toString();
                    PatientAppointment.put("createdBy", ParseUser.getCurrentUser());
                    PatientAppointment.put("DrID", drid);
                    PatientAppointment.put("DoctorName", drname);
                    PatientAppointment.put("MyAppointment", appointment);
                    PatientAppointment.put("Appointment_No", AppNo);
                    ParseUser user = ParseUser.getCurrentUser();
                    user.increment("Activity");
                    //PatientAppointment.saveInBackground();
                    PatientAppointment.saveEventually();

                    ParseQuery<ParseObject> query = ParseQuery.getQuery("Doctor");
                    query.whereEqualTo("Appointment_Number", AppNo);
                    query.findInBackground(new FindCallback<ParseObject>() {
                        public void done(List<ParseObject> v, ParseException e) {
                            if (e == null) {

                                // ParseObject.deleteAllInBackground(v);
                                for (ParseObject invite : v) {
                                    invite.deleteEventually();
                                    ParseUser.getCurrentUser().refreshInBackground(new RefreshCallback() {
                                        public void done(ParseObject object, ParseException e) {
                                            if (e == null) {
                                                Toast toast = Toast.makeText(Confirm_Appointments_View.this, " Appointment Booked Successfully ", Toast.LENGTH_LONG);
                                                toast.setGravity(Gravity.CENTER, 0, 0);
                                                toast.show();
                                                Intent takeUserHome = new Intent(Confirm_Appointments_View.this, Patient.class);
                                                startActivity(takeUserHome);
                                                // Success!
                                            } else {
                                                // Failure!
                                            }
                                        }
                                    });
                                }
                            } else {

                            }
                        }
                    });

                    // adding appointment to doctor view
                    ParseObject DoctorView = new ParseObject("DoctorView");
                    String patientid = String.valueOf(ParseUser.getCurrentUser().getObjectId());
                    DoctorView.put("PatientID", patientid.toUpperCase());
                    DoctorView.put("DrID", drid);
                    DoctorView.put("DoctorName", drname);
                    DoctorView.put("PatientName",ParseUser.getCurrentUser().get("Name"));
                    DoctorView.put("MyAppointment", appointment);
                    DoctorView.put("Appointment_No", AppNo);
                    DoctorView.put("Patient_DOB", ParseUser.getCurrentUser().get("DateofBirth"));
                    DoctorView.put("Patient_Email", ParseUser.getCurrentUser().get("email"));
                    DoctorView.put("Patient_ContactNo", ParseUser.getCurrentUser().get("Contact_Number"));

                   // DoctorView.saveInBackground();
                    DoctorView.saveEventually();
                    // sending a notification to user once they booked the appointment
                    ParseQuery<ParseInstallation> pQuery = ParseInstallation.getQuery();
                    pQuery.whereEqualTo("username", ParseUser.getCurrentUser());
                    ParsePush push = new ParsePush();
                    push.setQuery(pQuery);
                    push.setMessage("Confirmation of Appointment on " + appointment);
                    push.sendInBackground();

                    Confirm_Appointments_View.this.finish();
                }
                else{

                    showAlertDialog(Confirm_Appointments_View.this, "No Internet Connection",
                            "Please check your internet connection.", false);

                }
            }});



    }

    public void showAlertDialog(Context context, String title, String message, Boolean status) {


        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(message);

        // Setting alert dialog icon
        alertDialog.setIcon((status) ? R.drawable.success : R.drawable.error);

        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        // Showing Alert Message
        alertDialog.show();

    }
}