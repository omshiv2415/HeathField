package appointment.booking.com.Prescription;

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

import appointment.booking.com.heathfield.R;


public class PrescriptionActivity extends Activity {

    // Declare Variables
    ListView listview;
    List<ParseObject> ob;
    ProgressDialog mProgressDialog;
    Prescription_Booking_ListViewAdapter adapter;
    private List<prescription_support> PrescriptionList = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from activity_booking_appointment.xmlappointment.xml
        setContentView(R.layout.activity_prescription);
        // Execute RemoteDataTask AsyncTask
        new RemoteDataTask().execute();
        TextView head = (TextView)findViewById(R.id.textView);
        TextView nopre = (TextView)findViewById(R.id.noPrescription);


        Typeface Heading = Typeface.createFromAsset(getAssets(), "fonts/Sansation_Regular.ttf");
        head.setTypeface(Heading);
        head.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        nopre.setTypeface(Heading);
        nopre.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

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
        // this.finish();

    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                        PrescriptionActivity.this.finish();

                    }
                })
                .setNegativeButton("No", null)
                .show();


    }

    // RemoteDataTask AsyncTask
    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(PrescriptionActivity.this);
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
            PrescriptionList = new ArrayList<prescription_support>();
            try {
                // Locate the class table named "Prescription" in Parse.com
                ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                        "Prescription");
                query.whereEqualTo("Patient_ID", ParseUser.getCurrentUser().getObjectId());

                ob = query.find();
                for (ParseObject Prescription_Items : ob) {
                    prescription_support map = new prescription_support();
                    map.setPatient_Id((String) Prescription_Items.get("Patient_ID"));
                    map.setPatient_Name((String) Prescription_Items.get("Patient_Name"));
                    map.setDate_of_Birth((String) Prescription_Items.get("Patient_Date_of_Birth"));
                    map.setAppointment_Date((String) Prescription_Items.get("AppointmentDate"));
                    map.setDoctorname((String) Prescription_Items.get("Doctor_Name"));
                    map.setDoctor_Id((String) Prescription_Items.get("Doctor_Id"));
                    map.setMedication((String) Prescription_Items.get("Medication"));
                    map.setMedicineamount((String) Prescription_Items.get("Medicine_Amount"));
                    map.setMedicinedispense((String) Prescription_Items.get("Medicine_Dispense"));
                    map.setMedicinerefill((String) Prescription_Items.get("Medicine_Refill"));
                    PrescriptionList.add(map);
                }
            } catch (ParseException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            TextView noPre = (TextView) findViewById(R.id.noPrescription);
            // Locate the listview in activity_booking_appointmenting_appointment.xml
            listview = (ListView) findViewById(R.id.prescription_listview);
            // Pass the results into Booking_ListViewAdapter.java
            adapter = new Prescription_Booking_ListViewAdapter(PrescriptionActivity.this,
                    PrescriptionList);
            // Binds the Adapter to the ListView
            if (adapter.getCount() != 0) {
                listview.setAdapter(adapter);
                noPre.setVisibility(View.GONE);
            } else {
                Toast.makeText(PrescriptionActivity.this, "No Prescription To Order", Toast.LENGTH_SHORT).show();
                noPre.setVisibility(View.VISIBLE);

            }
            // Close the progressdialog
            mProgressDialog.dismiss();
        }


    }

}
