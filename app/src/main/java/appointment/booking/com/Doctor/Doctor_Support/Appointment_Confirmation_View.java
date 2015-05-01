package appointment.booking.com.Doctor.Doctor_Support;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import appointment.booking.com.Doctor.Doctor_Content.Doctor;
import appointment.booking.com.heathfield.R;

public class Appointment_Confirmation_View extends Activity {
	// Declare Variables
	TextView PatientId;
    TextView PatientAppointmentdate;
    TextView DoctorName;
    TextView DoctorID;

	String P_ID;
    String P_APPOINTMENT;
    String P_DOCTOR_NAME;
    String P_DoctorId;

    Button ConfirmAppointment;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.confirm_singleitemview);
		// Retrieve data from booking appointment on item click event
		Intent i = getIntent();

		P_ID = i.getStringExtra("PatientId");
        P_APPOINTMENT = i.getStringExtra("AppointmentDate");
        P_DOCTOR_NAME = i.getStringExtra("DoctorName");
        P_DoctorId = i.getStringExtra("DoctorID");


        // Locate the TextViews in singleitemview.xml

        PatientAppointmentdate = (TextView) findViewById(R.id.Appointment);
        DoctorName = (TextView) findViewById(R.id.Doctor_name);
        DoctorID = (TextView)findViewById(R.id.Doctor_id);
        PatientId = (TextView)findViewById(R.id.Appointment_no);


        ConfirmAppointment = (Button)findViewById(R.id.appointment);

		// Load the results into the TextViews

        PatientAppointmentdate.setText(P_APPOINTMENT);
        DoctorName.setText(P_DOCTOR_NAME);
        DoctorID.setText(P_DoctorId);
        PatientId.setText(P_ID);

        ConfirmAppointment.setOnClickListener(new View.OnClickListener() {

            String pid = PatientId.getText().toString();
            String pappointment = PatientAppointmentdate.getText().toString();
            String pdocotor = DoctorName.getText().toString().trim();
            String pdoctorId = DoctorID.getText().toString().trim();


            @Override
            public void onClick(View view) {


                ParseObject PatientPrescription = new ParseObject("PatientAppointment");


                PatientPrescription.put("createdBy", pid);
                PatientPrescription.put("MyAppointment",pappointment);
                PatientPrescription.put("DoctorName",pdocotor);
                PatientPrescription.put("DrID",pdoctorId);
                PatientPrescription.put("Appointment_No","No Number (Requested)");


                    PatientPrescription.saveInBackground();
                   // sending a notification to user once they booked the appointment
                    String check = new ParseUser().getObjectId();

                    ParseQuery<ParseInstallation> pQuery = ParseInstallation.getQuery();
                    pQuery.whereEqualTo( pid,check );



                    ParsePush push = new ParsePush();
                    push.setQuery(pQuery);
                    push.setMessage("Your Appointment Request has been Approved for "+ pappointment);
                    push.sendInBackground();
                    Toast toast = Toast.makeText(Appointment_Confirmation_View.this, "Appointment Confirmed Successfully ", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    Intent takeUserHome = new Intent(Appointment_Confirmation_View.this, Doctor.class);
                    startActivity(takeUserHome);
                    Appointment_Confirmation_View.this.finish();

            }
        });




}}