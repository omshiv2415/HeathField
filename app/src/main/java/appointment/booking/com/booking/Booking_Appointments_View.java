package appointment.booking.com.booking;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
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
import com.parse.SaveCallback;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import appointment.booking.com.heathfield.R;
import appointment.booking.com.patient.Patient;
import appointment.booking.com.support.AlarmReceiver;
import appointment.booking.com.support.ConnectionDetector;

public class Booking_Appointments_View extends Activity {
    // Declare Variables
    Boolean isInternetPresent = true;
    TextView DrID;
    TextView DrName;
    TextView Appointment;
    TextView AppointmentNO;
    String DoctorId;
    String DoctorName;
    String Patient_Appointment;
    String cancel;
    String book;
    String Appointment_number;
    Button appButton;
    Button cancelAppointment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.singleitemview);
        // Retrieve data from booking appointment on item click event
        Intent i = getIntent();
        // Get the results of DoctorId
        DoctorId = i.getStringExtra("D_Id");
        // Get the results of DoctorName
        DoctorName = i.getStringExtra("D_Name");
        // Get the results of Patient_Appointmetn
        Patient_Appointment = i.getStringExtra("P_Appointment");
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
        appButton = (Button) findViewById(R.id.appointment);
        AppointmentNO = (TextView) findViewById(R.id.Appointment_no);
        cancelAppointment = (Button) findViewById(R.id.appointment_Cancel);

        // Load the results into the TextViews
        DrID.setText(DoctorId);
        DrName.setText(DoctorName);
        Appointment.setText(Patient_Appointment);
        AppointmentNO.setText(Appointment_number);


        if (book.equals("BookAppointment")) {
            // hiding appointment cancel button
            appButton.setVisibility(View.VISIBLE);
            cancelAppointment.setVisibility(View.GONE);
        } else if (cancel.equals("CancelAppointment")) {
            // hiding appointment booking button
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
                if (isInternetPresent) if (ParseUser.getCurrentUser().get("Activity").equals(2)) {
                    Toast toast = Toast.makeText(Booking_Appointments_View.this, " Sorry You can't book more \n than two appointments", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {
                    // adding appointment to Patient Appointment for doctor and patient
                    ParseObject PatientAppointment = new ParseObject("PatientAppointment");
                    String drid = DrID.getText().toString().trim();
                    String drname = DrName.getText().toString().trim();
                    String appointment = Appointment.getText().toString().trim();
                    String AppNo = AppointmentNO.getText().toString().trim();
                    PatientAppointment.put("createdBy", ParseUser.getCurrentUser());
                    PatientAppointment.put("DrID", drid);
                    PatientAppointment.put("DoctorName", drname);
                    PatientAppointment.put("MyAppointment", appointment);
                    PatientAppointment.put("Appointment_No", AppNo);
                    PatientAppointment.saveEventually();
                    // adding history to booking
                    ParseObject Booking_History_Appointment = new ParseObject("PatientBookingHistory");
                    Booking_History_Appointment.put("createdBy", ParseUser.getCurrentUser());
                    Booking_History_Appointment.put("DrID", drid);
                    Booking_History_Appointment.put("DoctorName", drname);
                    Booking_History_Appointment.put("MyAppointment", appointment);
                    Booking_History_Appointment.put("Appointment_No", AppNo);
                    Booking_History_Appointment.saveEventually();
                    ParseUser user = ParseUser.getCurrentUser();
                    // counting patient appointment to restrict patient
                    // so patient can not book more than two appointment
                    user.increment("Activity");

                    // incrementing Doctor Count for popularity
                    ParseQuery<ParseObject> CountPopDoc = ParseQuery.getQuery("PopularDoctor");
                    CountPopDoc.whereEqualTo("DoctorName", drname);
                    CountPopDoc.findInBackground(new FindCallback<ParseObject>() {
                        public void done(List<ParseObject> v, ParseException e) {
                            if (e == null) {

                                for (ParseObject invite : v) {
                                    // incrementing appointmentRequested no of particular doctor
                                    invite.increment("AppointmentRequested");
                                    invite.saveEventually();
                                }
                            } else {
                                // if doctor count not properly
                                Toast toast = Toast.makeText(Booking_Appointments_View.this, "Doctor Count Fail", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            }
                        }
                    });


                            user.saveEventually(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                }
                            });
                    PatientAppointment.saveEventually(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {

                        }
                    });

                    // string variable loading appointment date and time
                    String input = appointment;
                    Date date = null;
                    try {
                        // converting to parse date format
                        date = new SimpleDateFormat("dd MMM yyyy EEE HH:mm", Locale.ENGLISH).parse(input);
                    } catch (java.text.ParseException e) {
                        e.printStackTrace();
                    }

                    // one day in milliseconds setting variable to send reminder before 24 hours
                    long oneDay = 86400000;
                    long milliseconds = date.getTime();
                    long millisecondsFromNow = milliseconds - (new Date()).getTime();
                    long reminder = millisecondsFromNow - oneDay;

                    Long time = new GregorianCalendar().getTimeInMillis() + reminder;

                    // create an Intent and set the class which will execute when Alarm triggers, here we have
                    // given AlarmReceiver in the Intent, the onReceive() method of this class will execute when
                    // alarm triggers and send notification

                    Intent intentAlarm = new Intent(Booking_Appointments_View.this, AlarmReceiver.class);
                    // create the object
                    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    //set the alarm for particular time
                    alarmManager.set(AlarmManager.RTC_WAKEUP, time, PendingIntent.getBroadcast(Booking_Appointments_View.this, 1,
                            intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));

                    // Deleting Booked appointment from available appointment from Doctor
                    // Class so appointment will not display to other patient.
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("Doctor");
                    query.whereEqualTo("Appointment_Number", AppNo);
                    query.findInBackground(new FindCallback<ParseObject>() {
                        public void done(List<ParseObject> v, ParseException e) {
                            if (e == null) {

                                // ParseObject.deleteAllInBackground(v);
                                for (ParseObject invite : v) {
                                    invite.deleteInBackground();
                                    Toast toast = Toast.makeText(Booking_Appointments_View.this, " Appointment Booked Successfully ", Toast.LENGTH_LONG);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();
                                    // Taking patient to Patient Class where patient will see available appointment
                                    Intent takeUserHome = new Intent(Booking_Appointments_View.this, Patient.class);
                                    startActivity(takeUserHome);
                                }
                            } else {
                                // if appointment not booked Successfully
                                Toast toast = Toast.makeText(Booking_Appointments_View.this, " Please Try Again ", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            }
                        }
                    });

                    // adding appointment to doctor view
                    ParseObject DoctorView = new ParseObject("DoctorView");
                    String patientid = String.valueOf(ParseUser.getCurrentUser().getObjectId());
                    DoctorView.put("PatientID", patientid.toUpperCase());
                    DoctorView.put("DrID", drid);
                    DoctorView.put("DoctorName", drname);
                    DoctorView.put("PatientName", ParseUser.getCurrentUser().get("Name"));
                    DoctorView.put("MyAppointment", appointment);
                    DoctorView.put("Appointment_No", AppNo);
                    DoctorView.put("Patient_DOB", ParseUser.getCurrentUser().get("DateofBirth"));
                    DoctorView.put("Patient_Email", ParseUser.getCurrentUser().get("email"));
                    DoctorView.put("Patient_ContactNo", ParseUser.getCurrentUser().get("Contact_Number"));

                    // saving data to parse
                    DoctorView.saveEventually();
                    // sending a notification to user once they booked the appointment
                    ParseQuery<ParseInstallation> pQuery = ParseInstallation.getQuery();
                    pQuery.whereEqualTo("username", ParseUser.getCurrentUser());
                    ParsePush push = new ParsePush();
                    push.setQuery(pQuery);
                    push.setMessage("Confirmation of Appointment on " + appointment);
                    push.sendInBackground();
                    Booking_Appointments_View.this.finish();
                }

                else {

                    showAlertDialog(Booking_Appointments_View.this, "No Internet Connection",
                            "Please check your internet connection.", false);

                }
            }
        });

        cancelAppointment.setOnClickListener(new View.OnClickListener() {

            ParseObject PatientAppointment = new ParseObject("Doctor");

            @Override
            public void onClick(View view) {

                ConnectionDetector cd;
                // creating connection detector class instance
                cd = new ConnectionDetector(getApplicationContext());
                // get Internet status
                isInternetPresent = cd.isConnectingToInternet();
                // check for Internet status
                if (isInternetPresent) {
                    ParseQuery<ParseObject> Doctorquery = ParseQuery.getQuery("Doctor");
                    String drid = DrID.getText().toString().trim();
                    String drname = DrName.getText().toString().trim();
                    String appointment = Appointment.getText().toString().trim();
                    String AppNo = AppointmentNO.getText().toString();

                    Doctorquery.whereEqualTo("Appointment_Number", AppNo);
                    Doctorquery.findInBackground(new FindCallback<ParseObject>() {
                        public void done(List<ParseObject> v, ParseException e) {
                            if (e == null) {
                                // ParseObject.deleteAllInBackground(v);
                                for (final ParseObject invite : v) {
                                    // deleting appointment from Patient Appointment
                                    invite.deleteInBackground();
                                    Intent takeUserHome = new Intent(Booking_Appointments_View.this, Patient.class);
                                    startActivity(takeUserHome);
                                }
                            } else {
                                // if appointment not cancel Successfully
                                Toast toast = Toast.makeText(Booking_Appointments_View.this, " Please Try Again ", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            }
                        }
                    });

                    // adding appointment to Available appointment to other patient
                    PatientAppointment.put("Doctor_ID", drid);
                    PatientAppointment.put("Doctor_Name", drname);
                    PatientAppointment.put("Appointment_Slot", appointment);
                    PatientAppointment.put("Appointment_Number", AppNo);

                    // creating history of cancel appointments
                    ParseObject PatientCancelHistory = new ParseObject("PatientAppointmentHistory");
                    PatientCancelHistory.put("createdBy", ParseUser.getCurrentUser());
                    PatientCancelHistory.put("DrID", drid);
                    PatientCancelHistory.put("DoctorName", drname);
                    PatientCancelHistory.put("MyAppointment", appointment);
                    PatientCancelHistory.put("Appointment_No", AppNo);
                    PatientCancelHistory.saveEventually();

                    ParseUser user = ParseUser.getCurrentUser();
                    Number decrement = (Number) user.get("Activity");

                    if (decrement.equals(2)) {
                        user.put("Activity", 1);
                        user.saveInBackground();
                        user.saveEventually();

                        // decrementing patient appointment count so patient can book
                        // another appointment
                    } else if (decrement.equals(1)) {

                        user.put("Activity", 0);
                        user.saveInBackground();
                        user.saveEventually();
                    }
                    // saving all the data to parse
                    PatientAppointment.saveEventually();


                    ParseQuery<ParseInstallation> pQuery = ParseInstallation.getQuery();
                    pQuery.whereEqualTo("username", ParseUser.getCurrentUser());
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("PatientAppointment");
                    query.whereEqualTo("Appointment_No", AppNo);
                    query.findInBackground(new FindCallback<ParseObject>() {
                        public void done(List<ParseObject> v, ParseException e) {
                            if (e == null) {
                                // ParseObject.deleteAllInBackground(v);
                                for (ParseObject invite : v) {
                                    invite.deleteInBackground();
                                    Toast toast = Toast.makeText(Booking_Appointments_View.this, " Appointment Cancel Successfully ", Toast.LENGTH_LONG);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();
                                    Intent takeUserHome = new Intent(Booking_Appointments_View.this, Patient.class);
                                    startActivity(takeUserHome);

                                }
                            } else {
                                //Handle condition here
                            }
                        }
                    });
                    ParseQuery<ParseObject> DocQuery = ParseQuery.getQuery("DoctorView");
                    DocQuery.whereEqualTo("Appointment_No", AppNo);
                    DocQuery.findInBackground(new FindCallback<ParseObject>() {
                        public void done(List<ParseObject> v, ParseException e) {
                            if (e == null) {
                                // ParseObject.deleteAllInBackground(v);
                                for (ParseObject invite : v) {
                                    invite.deleteInBackground();

                                }
                            } else {
                                //Handle condition here
                            }
                        }
                    });

                    ParsePush push = new ParsePush();
                    push.setQuery(pQuery);
                    push.setMessage("You have Successfully cancel your appointment on " + appointment);
                    push.sendInBackground();
                    Booking_Appointments_View.this.finish();
                } else {
                    showAlertDialog(Booking_Appointments_View.this, "No Internet Connection",
                            "Please check your internet connection.", false);
                }
            }
        });

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

    @Override
    public void onStop() {
        super.onStop();
        this.finish();

    }
}