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

import appointment.booking.com.Doctor.Doctor_Support.Doctor_ConfirmAppointment_ListViewAdapter;
import appointment.booking.com.Doctor.Doctor_Support.Doctor_ConfirmAppointment_support;
import appointment.booking.com.heathfield.R;

public class Confirm_Appointment extends Activity {
    // Declare Variables
    ListView listview;
    List<ParseObject> ob;
    ProgressDialog mProgressDialog;
    Doctor_ConfirmAppointment_ListViewAdapter adapter;
    private List<Doctor_ConfirmAppointment_support> AppointmentConfirmList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm__appointment);
        new RemoteDataTask().execute();
        TextView head = (TextView) findViewById(R.id.textView);
        TextView empty = (TextView) findViewById(R.id.noAppointments);
        Typeface Heading = Typeface.createFromAsset(getAssets(), "fonts/Sansation_Regular.ttf");
        head.setTypeface(Heading);
        head.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        empty.setTypeface(Heading);
        empty.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

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

                        Confirm_Appointment.this.finish();


                    }
                })
                .setNegativeButton("No", null)
                .show();


    }

    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(Confirm_Appointment.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Prescription");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            // Create the array
            AppointmentConfirmList = new ArrayList<Doctor_ConfirmAppointment_support>();
            try {
                // Locate the class table named "PatientAppointmentRequest" in Parse.com
                ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                        "PatientAppointmentRequest");
                // Locate the column named "DrName" in Parse.com and getting appointment to
                // Particular doctor
                query.whereEqualTo("DrName", ParseUser.getCurrentUser().get("Name"));


                ob = query.find();
                for (ParseObject Prescription_Items : ob) {
                    Doctor_ConfirmAppointment_support map = new Doctor_ConfirmAppointment_support();
                    map.setPatient_Id((String) Prescription_Items.get("RequestedId"));
                    map.setPatient_Name((String) Prescription_Items.get("PatientName"));
                    map.setDate_of_Birth((String) Prescription_Items.get("PatientDob"));
                    map.setAppointment_Date((String) Prescription_Items.get("RequestedAppointment"));
                    map.setDoctorname((String) Prescription_Items.get("DrName"));
                    map.setDocId((String) Prescription_Items.get("DoctorID"));
                    map.setPatientEmail((String) Prescription_Items.get("RequestedEmail"));
                    map.setPatientContactNo((String) Prescription_Items.get("PatientPhone"));
                    AppointmentConfirmList.add(map);
                }
            } catch (ParseException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            TextView noApp = (TextView) findViewById(R.id.noAppointments);
            // Locate the listview in activity_booking_appointmenting_appointment.xml
            listview = (ListView) findViewById(R.id.ConfirmListView);
            // Pass the results into Booking_ListViewAdapter.java
            adapter = new Doctor_ConfirmAppointment_ListViewAdapter(Confirm_Appointment.this,
                    AppointmentConfirmList);
            // Binds the Adapter to the ListView
            if (adapter.getCount() != 0) {
                listview.setAdapter(adapter);
                noApp.setVisibility(View.GONE);
            } else {
                Toast.makeText(Confirm_Appointment.this, "No Appointments Requested", Toast.LENGTH_SHORT).show();
                noApp.setVisibility(View.VISIBLE);

            }
            // Close the progressdialog
            mProgressDialog.dismiss();
        }


    }

}
