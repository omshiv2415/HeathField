package appointment.booking.com.Doctor.Doctor_Content;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import appointment.booking.com.Doctor.Doctor_Support.Doctor_ListViewAdapter;
import appointment.booking.com.Doctor.Doctor_Support.Doctor_support;
import appointment.booking.com.heathfield.R;


public class Doctor_Appointments extends Activity {

    ListView listview;
    List<ParseObject> ob;
    ProgressDialog mProgressDialog;
    Doctor_ListViewAdapter adapter;
    private List<Doctor_support> DoctorHeathFieldSurgery = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_home);

        new RemoteDataTask().execute();

        TextView head = (TextView)findViewById(R.id.textView);
        TextView empty = (TextView)findViewById(R.id.NoDocApp);
        Typeface Heading = Typeface.createFromAsset(getAssets(), "fonts/Sansation_Regular.ttf");
        head.setTypeface(Heading);
        head.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        empty.setTypeface(Heading);
        empty.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);


    }
    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(Doctor_Appointments.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Please wait for appointments");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }
        @Override
        protected Void doInBackground(Void... params) {
            // Create the array
            DoctorHeathFieldSurgery = new ArrayList<Doctor_support>();
            try {
                // Locate the class table named "Country" in Parse.com
                ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                        "DoctorView");
                // query.whereEqualTo("createdBy",  ParseUser.getCurrentUser());
                query.whereEqualTo("DoctorName", ParseUser.getCurrentUser().get("Name"));

                ob = query.find();
                for (ParseObject DoctorViewAppointment : ob) {
                    Doctor_support map = new Doctor_support();
                    map.setPatient_Id((String) DoctorViewAppointment.get("PatientID"));
                    map.setPatient_Name((String) DoctorViewAppointment.get("PatientName"));
                    map.setDate_of_Birth((String) DoctorViewAppointment.get("Patient_DOB"));
                    map.setAppointment_Date((String) DoctorViewAppointment.get("MyAppointment"));
                    map.setDoctorname((String) DoctorViewAppointment.get("DoctorName"));
                    map.setPatientContactNo((String) DoctorViewAppointment.get("Patient_ContactNo"));
                    map.setPatientEmail((String) DoctorViewAppointment.get("Patient_Email"));
                    map.setAppointmentNo((String) DoctorViewAppointment.get("Appointment_No"));
                    map.setDoctorId((String) DoctorViewAppointment.get("DrID"));
                    DoctorHeathFieldSurgery.add(map);
                }
            } catch (ParseException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            TextView noApp = (TextView)findViewById(R.id.NoDocApp);
            Typeface Heading = Typeface.createFromAsset(getAssets(), "fonts/Sansation_Regular.ttf");
            noApp.setTypeface(Heading);
            // Locate the listview in activity_booking_appointment.xmlappointment.xml
            listview = (ListView) findViewById(R.id.DoctorListView);
            // Pass the results into Booking_ListViewAdapter.java
            adapter = new Doctor_ListViewAdapter(Doctor_Appointments.this,
                    DoctorHeathFieldSurgery);
            // Binds the Adapter to the ListView
            if(adapter.getCount()!=0){
                listview.setAdapter(adapter);
                noApp.setVisibility(View.GONE);
            }else{
                Toast.makeText(Doctor_Appointments.this, "No Appointments Booked", Toast.LENGTH_SHORT).show();
                noApp.setVisibility(View.VISIBLE);

            }
            // Close the progressdialog
            mProgressDialog.dismiss();
        }


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
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Doctor_Appointments.this.finish();


                    }
                })
                .setNegativeButton("No", null)
                .show();


    }

}
