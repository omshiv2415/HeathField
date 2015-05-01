package appointment.booking.com.cancel;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
import appointment.booking.com.booking.History.Appointment_Booking_History;
import appointment.booking.com.cancel.History.Appointment_Cancel_History;
import appointment.booking.com.heathfield.R;


public class PatientCancelActivity extends Activity {

    // Declare Variables
    ListView listview;
    List<ParseObject> ob;
    ProgressDialog mProgressDialog;
    Cancel_ListViewAdapter adapter;
    private List<App_Booking_and_cancel_support> AppBookAndCancelList = null;
    Button AppointmentCancelHistory;
    Button AppointmentBookingHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_cancel);

       // patientname.setText((CharSequence) ParseUser.getCurrentUser().get("Name"));
        new RemoteDataTask().execute();
        AppointmentCancelHistory = (Button)findViewById(R.id.CancelHistory);
        AppointmentBookingHistory = (Button)findViewById(R.id.BookingHistory);

        AppointmentCancelHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent CancelHistory = new Intent(PatientCancelActivity.this, Appointment_Cancel_History.class);
                startActivity(CancelHistory);
            }
        });

        AppointmentBookingHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent BookingHistory = new Intent(PatientCancelActivity.this, Appointment_Booking_History.class);
                startActivity(BookingHistory);
            }
        });
        TextView head = (TextView)findViewById(R.id.textView);
        TextView empty = (TextView)findViewById(R.id.list_empty);
        Typeface Heading = Typeface.createFromAsset(getAssets(), "fonts/Sansation_Regular.ttf");
        head.setTypeface(Heading);
        head.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        empty.setTypeface(Heading);
        empty.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

    }
    // RemoteDataTask AsyncTask

    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(PatientCancelActivity.this);
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
                        "PatientAppointment");
                // Locate the column named "ranknum" in Parse.com and order list
                // by ascending
                query.orderByAscending("Doctor_Name");
                query.whereEqualTo("createdBy", ParseUser.getCurrentUser());
                ob = query.find();
                for (ParseObject country : ob) {
                    App_Booking_and_cancel_support map = new App_Booking_and_cancel_support();
                    map.setDoctor_id((String) country.get("DrID"));
                    map.setDoctor_Name((String) country.get("DoctorName"));
                    map.setAppointment_Date((String) country.get("MyAppointment").toString());
                    map.setAppointment_number((String) country.get("Appointment_No"));
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
            // Locate the listview in activity_booking_appointmenting_appointment.xml
            TextView noApp = (TextView)findViewById(R.id.list_empty);
            listview = (ListView) findViewById(R.id.AppointmentListView);
            // Pass the results into Booking_ListViewAdapter.java
            adapter = new Cancel_ListViewAdapter(PatientCancelActivity.this,
                    AppBookAndCancelList);
            // Binds the Adapter to the ListView
            if(adapter.getCount()!=0){
                listview.setAdapter(adapter);
                noApp.setVisibility(View.GONE);
            }else{
                Toast.makeText(PatientCancelActivity.this, "No Appointments To Cancel", Toast.LENGTH_SHORT).show();
                noApp.setVisibility(View.VISIBLE);
                noApp.setText("No Appointments To Cancel");
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
    public void onStop() {
        super.onStop();
        //this.finish();

    }
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        PatientCancelActivity.this.finish();

                    }
                })
                .setNegativeButton("No", null)
                .show();


    }
}
