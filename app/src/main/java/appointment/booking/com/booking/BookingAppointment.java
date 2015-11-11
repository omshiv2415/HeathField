package appointment.booking.com.booking;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.CountCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.RefreshCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import appointment.booking.com.heathfield.R;
import appointment.booking.com.patient.PatientDetails;

public class BookingAppointment extends Activity {
    // Declare Variables
    ListView listview;
    List<ParseObject> ob;
    ProgressDialog mProgressDialog;
    Booking_ListViewAdapter adapter;
    private TextView NoPatient;
    private TextView Malepatient;
    private TextView FemalePatient;
    private TextView PopularDoctor;
    private TextView TotalNoOfAppointment;
    private TextView DisplayPatientName;
    EditText SearchAppointment;

    private List<App_Booking_and_cancel_support> HeathFieldSurgery = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from activity_booking_appointment.xmlappointment.xml
        setContentView(R.layout.activity_booking_appointment);
        // Execute RemoteDataTask AsyncTask
        ParseObject appointment = new ParseObject("Doctor");

        appointment.refreshInBackground(new RefreshCallback() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {

                } else {

                }
            }
        });

        appointment.refreshInBackground(new RefreshCallback() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {

                } else {

                }
            }
        });
        new RemoteDataTask().execute();
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.put("username", ParseUser.getCurrentUser());
        installation.saveInBackground();

        //WebView myWebView = (WebView) findViewById(R.id.webView);
        // myWebView.loadUrl("http://www.telegraph.co.uk/news/health/news/");
        NoPatient = (TextView) findViewById(R.id.TotalPatient);
        Malepatient = (TextView) findViewById(R.id.MalePatient);
        FemalePatient = (TextView) findViewById(R.id.FemalePat);
        PopularDoctor = (TextView) findViewById(R.id.popDoc);
        TotalNoOfAppointment = (TextView) findViewById(R.id.TotalAppointments);
        DisplayPatientName = (TextView) findViewById(R.id.PatientName);
        TextView head = (TextView) findViewById(R.id.textView);
        TextView head1 = (TextView) findViewById(R.id.textView2);
        TextView head2 = (TextView) findViewById(R.id.textView3);
        TextView head3 = (TextView) findViewById(R.id.textView4);
        TextView head4 = (TextView) findViewById(R.id.DocPopular);
        TextView head5 = (TextView) findViewById(R.id.AvailableApp);
        TextView head6 = (TextView) findViewById(R.id.DocAvailable);
        TextView head7 = (TextView) findViewById(R.id.Available);


        Typeface Heading = Typeface.createFromAsset(getAssets(), "fonts/Sansation_Regular.ttf");
        head.setTypeface(Heading);
        head.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        head1.setTypeface(Heading);
        head1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        head2.setTypeface(Heading);
        head2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        head3.setTypeface(Heading);
        head3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        head4.setTypeface(Heading);
        head4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        head5.setTypeface(Heading);
        head5.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        head6.setTypeface(Heading);
        head6.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        head7.setTypeface(Heading);
        head7.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        NoPatient.setTypeface(Heading);
        NoPatient.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        Malepatient.setTypeface(Heading);
        Malepatient.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        FemalePatient.setTypeface(Heading);
        FemalePatient.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        PopularDoctor.setTypeface(Heading);
        PopularDoctor.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        TotalNoOfAppointment.setTypeface(Heading);
        TotalNoOfAppointment.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        DisplayPatientName.setTypeface(Heading);
        DisplayPatientName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        SearchAppointment = (EditText) findViewById(R.id.ApponintmentSearch);
        SearchAppointment.setSelected(false);

        DisplayPatientName.setText((CharSequence) ParseUser.getCurrentUser().get("Name"));

        DisplayPatientName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takeUserHome = new Intent(BookingAppointment.this, PatientDetails.class);
                startActivity(takeUserHome);
            }
        });


        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("Verify", "Patient");
        query.countInBackground(new CountCallback() {
            public void done(int count, ParseException e) {
                if (e == null) {
                    // The count request succeeded. Log the count
                    NoPatient.setText(Integer.toString(count));

                } else {
                    // The request failed
                }
            }
        });


        ParseQuery<ParseUser> male = ParseUser.getQuery();
        male.whereEqualTo("Verify", "Patient");
        male.whereEqualTo("Gender", "Male");
        male.countInBackground(new CountCallback() {
            public void done(int count, ParseException e) {
                if (e == null) {
                    // The count request succeeded. Log the count
                    Malepatient.setText(Integer.toString(count));

                } else {
                    // The request failed
                }
            }
        });

        ParseQuery<ParseUser> female = ParseUser.getQuery();
        female.whereEqualTo("Verify", "Patient");
        female.whereEqualTo("Gender", "Female");
        female.countInBackground(new CountCallback() {
            public void done(int count, ParseException e) {
                if (e == null) {
                    // The count request succeeded. Log the count
                    FemalePatient.setText(Integer.toString(count));

                } else {
                    // The request failed
                }
            }
        });

        ParseQuery<ParseObject> popular = ParseQuery.getQuery("Doctor");
        popular.countInBackground(new CountCallback() {
            public void done(int count, ParseException e) {
                if (e == null) {
                    // The count request succeeded. Log the count
                    TotalNoOfAppointment.setText(Integer.toString(count));




                } else {
                    // The request failed
                }
            }
        });


        // getting popular doctor from Heath Field Parse Database
        ParseQuery<ParseObject> FidPopDoc = ParseQuery.getQuery("PopularDoctor");
        // setting up the AppointmentRequested in DescendingOrder so i can get max count
        // of appointmentRequested.
        FidPopDoc.addDescendingOrder("AppointmentRequested");
        // finding first value of AppointmentRequested to find popular Doctor
        FidPopDoc.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (object == null) {

                } else {
                    // Setting up popular doctor name to popular doctor textview
                    PopularDoctor.setText((CharSequence) object.get("DoctorName"));
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ParseUser.logOut();
                        BookingAppointment.this.finish();

                    }
                })
                .setNegativeButton("No", null)
                .show();


    }

    @Override
    public void onStop() {
        super.onStop();
        //this.finish();

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

    // RemoteDataTask AsyncTask
    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(BookingAppointment.this);
            // Set progressdialog title
            mProgressDialog.setTitle("HeathField Appointment Booking");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            // Create the array
            HeathFieldSurgery = new ArrayList<App_Booking_and_cancel_support>();
            try {
                // Locate the class table named "Doctor" in Parse.com
                ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                        "Doctor");
                // Locate the column named "Appointment_Number" in Parse.com and order list
                // by ascending
                query.orderByAscending("Appointment_Number");
                ob = query.find();
                for (ParseObject Appointment : ob) {
                    App_Booking_and_cancel_support map = new App_Booking_and_cancel_support();
                    map.setDoctor_id((String) Appointment.get("Doctor_ID"));
                    map.setDoctor_Name((String) Appointment.get("Doctor_Name"));
                    map.setAppointment_Date((String) Appointment.get("Appointment_Slot").toString());
                    map.setAppointment_number((String) Appointment.get("Appointment_Number"));
                    HeathFieldSurgery.add(map);
                }
            } catch (ParseException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            // Locate the listview in activity_booking_appointment.xmlappointment.xml
            listview = (ListView) findViewById(R.id.listview);

            // Pass the results into Booking_ListViewAdapter.java
            adapter = new Booking_ListViewAdapter(BookingAppointment.this,
                    HeathFieldSurgery);
            // Binds the Adapter to the ListView
            listview.setAdapter(adapter);
            // Close the progressdialog
            mProgressDialog.dismiss();
            SearchAppointment = (EditText) findViewById(R.id.ApponintmentSearch);
            //  SearchAppointment.setSelected(false);
            // Capture Text in EditText
            SearchAppointment.addTextChangedListener(new TextWatcher() {

                @Override
                public void afterTextChanged(Editable arg0) {
                    // TODO Auto-generated method stub
                    String text = SearchAppointment.getText().toString()
                            .toLowerCase(Locale.getDefault());
                    adapter.filter(text);

                }

                @Override
                public void beforeTextChanged(CharSequence arg0, int arg1,
                                              int arg2, int arg3) {
                    // TODO Auto-generated method stub
                }

                @Override
                public void onTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                    // TODO Auto-generated method stub
                }
            });

        }


    }
}