package appointment.booking.com.booking.History;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
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

import appointment.booking.com.booking.App_Booking_and_cancel_support;
import appointment.booking.com.cancel.History.Cancel_History_ListViewAdapter;
import appointment.booking.com.heathfield.R;
import appointment.booking.com.patient.Patient;

public class Appointment_Booking_History extends Activity {

    // Declare Variables
    ListView listview;
    List<ParseObject> ob;
    ProgressDialog mProgressDialog;
    Cancel_History_ListViewAdapter adapter;
    private List<App_Booking_and_cancel_support> AppBookAndCancelList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment__booking_history);

        // patientname.setText((CharSequence) ParseUser.getCurrentUser().get("Name"));
        new RemoteDataTask().execute();
        TextView Head = (TextView)findViewById(R.id.textView);
        Typeface Heading = Typeface.createFromAsset(getAssets(), "fonts/Sansation_Regular.ttf");
        Head.setTypeface(Heading);

    }
    // RemoteDataTask AsyncTask


    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(Appointment_Booking_History.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Please wait for Appointments");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            // Create the array
            AppBookAndCancelList = new ArrayList<App_Booking_and_cancel_support>();
            try {
                // Locate the class table named "Country" in Parse.com
                ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                        "PatientBookingHistory");
                // Locate the column named "ranknum" in Parse.com and order list
                // by ascending
                query.orderByAscending("Doctor_Name");
                query.whereEqualTo("createdBy", ParseUser.getCurrentUser());
                ob = query.find();
                for (ParseObject country : ob) {
                    App_Booking_and_cancel_support map = new App_Booking_and_cancel_support();
                    map.setDoctor_id((String) country.get("DrID"));
                    map.setDoctor_Name((String) country.get("DoctorName"));
                    map.setAppointment_Date(country.get("MyAppointment").toString());
                    map.setAppointment_number((String) country.get("Appointment_No"));
                    map.setAppointment_Created(String.valueOf(country.getCreatedAt()));
                    AppBookAndCancelList.add(map);
                }
            } catch (ParseException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            TextView noApp = (TextView)findViewById(R.id.empty_history);


            Typeface Heading = Typeface.createFromAsset(getAssets(), "fonts/Sansation_Regular.ttf");
            noApp.setTypeface(Heading);
            noApp.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

            // Locate the listview in activity_booking_appointmenting_appointment.xml
            listview = (ListView) findViewById(R.id.AppointmentListViewHistory);
            // Pass the results into Booking_ListViewAdapter.java
            adapter = new Cancel_History_ListViewAdapter(Appointment_Booking_History.this,
                    AppBookAndCancelList);
            // Binds the Adapter to the ListView
            if(adapter.getCount()!=0){
                noApp.setVisibility(View.INVISIBLE);
                listview.setAdapter(adapter);

            }else{
                Toast.makeText(Appointment_Booking_History.this, "No Appointments Booking History", Toast.LENGTH_SHORT).show();
                noApp.setVisibility(View.VISIBLE);

            }
            // Close the progressdialog
            mProgressDialog.dismiss();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.patint_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {

        Intent takeUserHome = new Intent(Appointment_Booking_History.this, Patient.class);
        startActivity(takeUserHome);
        Appointment_Booking_History.this.finish();


    }
}
