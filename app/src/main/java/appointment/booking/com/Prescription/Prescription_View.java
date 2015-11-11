package appointment.booking.com.Prescription;

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

import appointment.booking.com.heathfield.R;
import appointment.booking.com.patient.Patient;

public class Prescription_View extends Activity {
	// Declare Variables
	TextView PatientId;
	TextView PatientName;
	TextView PatientDob;
    TextView PatientAppointmentdate;
    TextView DoctorName;
    TextView DoctorID;
    TextView Medication;
    TextView MedicineAmount;
    TextView MedicineDispese;
    TextView MedicineRefill;

	String P_ID;
	String P_NAME;
	String P_DOB;
    String P_APPOINTMENT;
    String P_DOCTOR_NAME;
    String P_MEDICATION;
    String P_MEDICINEAMOUNT;
    String P_MEDICINE_DISPENSE;
    String P_MEDICINE_REFILL;
    String P_DoctorId;
    Button ORDER_PRISCRIPTION;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.prescription_view);
		// Retrieve data from booking appointment on item click event
		Intent i = getIntent();

		P_ID = i.getStringExtra("PatientId");
		P_NAME = i.getStringExtra("PatientName");
		P_DOB = i.getStringExtra("PatientDob");
        P_APPOINTMENT = i.getStringExtra("AppointmentDate");
        P_DOCTOR_NAME = i.getStringExtra("DoctorName");
        P_MEDICATION = i.getStringExtra("Medication");
        P_MEDICINEAMOUNT = i.getStringExtra("MedicationAmount");
        P_MEDICINE_DISPENSE = i.getStringExtra("MedicineDispense");
        P_MEDICINE_REFILL = i.getStringExtra("MedicineRefill");
        P_DoctorId = i.getStringExtra("DoctorID");


        // Locate the TextViews in singleitemview.xml
		PatientId = (TextView) findViewById(R.id.patientID);
		PatientName = (TextView) findViewById(R.id.patientName);
		PatientDob = (TextView) findViewById(R.id.patient_dob);
        PatientAppointmentdate = (TextView) findViewById(R.id.appointment_date);
        DoctorName = (TextView) findViewById(R.id.doctor_Name);
        DoctorID = (TextView)findViewById(R.id.doctorid);
        Medication  = (TextView) findViewById(R.id.medicine_Name);
        MedicineAmount = (TextView) findViewById(R.id.medicineAmount);
        MedicineDispese = (TextView) findViewById(R.id.medicine_Despense);
        MedicineRefill = (TextView) findViewById(R.id.medicine_Refill);

        ORDER_PRISCRIPTION = (Button)findViewById(R.id.prescriptionorder);

		// Load the results into the TextViews
		PatientId.setText(P_ID);
		PatientName.setText(P_NAME);
		PatientDob.setText(P_DOB);
        PatientAppointmentdate.setText(P_APPOINTMENT);
        DoctorName.setText(P_DOCTOR_NAME);
        DoctorID.setText(P_DoctorId);
        Medication.setText(P_MEDICATION);
        MedicineAmount.setText(P_MEDICINEAMOUNT);
        MedicineDispese.setText(P_MEDICINE_DISPENSE);
        MedicineRefill.setText(P_MEDICINE_REFILL);



        ORDER_PRISCRIPTION.setOnClickListener(new View.OnClickListener() {
            String pid = PatientId.getText().toString().trim();
            String pname = PatientName.getText().toString().trim();
            String pdateofbirth= PatientDob.getText().toString().trim();
            String pappointment = PatientAppointmentdate.getText().toString();
            String pdocotor = DoctorName.getText().toString().trim();
            String pdoctorId = DoctorID.getText().toString().trim();
            String pmedication = Medication.getText().toString().trim();
            String pmedicationamount= MedicineAmount.getText().toString().trim();
            String pmedicationdispense = MedicineDispese.getText().toString();
            String pmedicatonrefill = MedicineRefill.getText().toString();

            @Override
            public void onClick(View view) {
                Integer prescription = (Integer) ParseUser.getCurrentUser().get("Order_Prescription");
                if(pmedicatonrefill.equals("no")||pmedicatonrefill.equals("NO")) {
                    Toast toast = Toast.makeText(Prescription_View.this, " Sorry you can't order prescription", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                }else if(prescription >= 5){
                    Toast toast = Toast.makeText(Prescription_View.this, " Sorry you can't order more than 5 prescription", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                else{

                ParseObject PatientPrescription = new ParseObject("Ordered_Prescription");

                PatientPrescription.put("createdBy", ParseUser.getCurrentUser());
                PatientPrescription.put("Patient_ID",pid);
                PatientPrescription.put("Patient_Name",pname);
                PatientPrescription.put("Patient_Date_of_Birth",pdateofbirth);
                PatientPrescription.put("AppointmentDate",pappointment);
                PatientPrescription.put("Doctor_Name",pdocotor);
                PatientPrescription.put("Medication",pmedication);
                PatientPrescription.put("Medicine_Amount",pmedicationamount);
                PatientPrescription.put("Medicine_Dispense",pmedicationdispense);
                PatientPrescription.put("Medicine_Refill",pmedicatonrefill);
                PatientPrescription.put("Doctor_id",pdoctorId);
                ParseUser user = ParseUser.getCurrentUser();
                user.increment("Order_Prescription");
                PatientPrescription.saveInBackground();
                // sending a notification to user once they booked the appointment
                    ParseQuery<ParseInstallation> pQuery = ParseInstallation.getQuery();
                    pQuery.whereEqualTo("username", ParseUser.getCurrentUser());

                    ParsePush push = new ParsePush();
                    push.setQuery(pQuery);
                    push.setMessage("Confirmation of prescription order");
                    push.sendInBackground();
                    Toast toast = Toast.makeText(Prescription_View.this, "Prescription Order Successfully ", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                    Intent takeUserHome = new Intent(Prescription_View.this, Patient.class);
                    startActivity(takeUserHome);
                    Prescription_View.this.finish();

            }
        }});

    }



    @Override
    public void onBackPressed() {

        this.finish();
        Intent takeUserHome = new Intent(Prescription_View.this, Patient.class);
        startActivity(takeUserHome);


    }


}