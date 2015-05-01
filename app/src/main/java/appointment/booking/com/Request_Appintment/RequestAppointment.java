package appointment.booking.com.Request_Appintment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import appointment.booking.com.heathfield.R;
import appointment.booking.com.patient.Patient;

public class RequestAppointment extends  Activity implements AdapterView.OnItemSelectedListener {
    protected EditText RPatientNamePatient;
    protected EditText RAppointmentDatePatient;
    protected EditText RAppointmentTimePatient;
    protected EditText RpatientPhone;
    Spinner spinnerDoctor;
    ProgressDialog mProgressDialog;
    protected Button RAppointmentRequestbtn;
    private int mYear;
    private int mMonth;
    private int mDay;

    String Doctor;

    private String[] state = {"Dr Hameed", "Dr B R Patel", "Dr Jamal"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_appointment);

        // setting ids from activity_request_appointment to editText and Spinner
        spinnerDoctor = (Spinner) findViewById(R.id.RDoctor);
        RAppointmentDatePatient = (EditText) findViewById(R.id.RAppointmentDate);
        RAppointmentTimePatient = (EditText) findViewById(R.id.RAppointmentTime);
        RPatientNamePatient = (EditText) findViewById(R.id.RPatientName);
        RpatientPhone = (EditText) findViewById(R.id.PatientPhone);
        RAppointmentRequestbtn = (Button) findViewById(R.id.Request);


        // setting up doctor selection through spinner
        ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, state);

        adapter_state
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerDoctor.setAdapter(adapter_state);
        spinnerDoctor.setOnItemSelectedListener(this);
        // setting up prompt to spinner as a select Doctor
        spinnerDoctor.setPrompt("Select Doctor");
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        // getting patient information and setting to editText box
        //RPatientNamePatient.setText(ParseUser.getCurrentUser().getEmail());
        //Rpatientid.setText(ParseUser.getCurrentUser().getObjectId().toUpperCase());
        RpatientPhone.setFocusable(false);
        RpatientPhone.setText((CharSequence) ParseUser.getCurrentUser().get("Contact_Number"));
        RPatientNamePatient.setFocusable(false);
        RPatientNamePatient.setText((CharSequence) ParseUser.getCurrentUser().get("Name"));
        TextView head = (TextView)findViewById(R.id.textView3);
        TextView head1 = (TextView)findViewById(R.id.textView2);
        TextView head2 = (TextView)findViewById(R.id.textView4);
        TextView head3 = (TextView)findViewById(R.id.textView5);
        TextView head4 = (TextView)findViewById(R.id.textView6);
        TextView head5 = (TextView)findViewById(R.id.textView9);

        Typeface Heading = Typeface.createFromAsset(getAssets(), "fonts/Sansation_Regular.ttf");
        head.setTypeface(Heading);
        head.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        head1.setTypeface(Heading);
        head1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        head2.setTypeface(Heading);
        head2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        head3.setTypeface(Heading);
        head3.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        head4.setTypeface(Heading);
        head4.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        head5.setTypeface(Heading);
        head5.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);


       // setting onClick listener on editText to select Appointment Time
        RAppointmentTimePatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar mAppointmentTime = Calendar.getInstance();
                int hour = mAppointmentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mAppointmentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(RequestAppointment.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        RAppointmentTimePatient.setFocusable(false);
                        RAppointmentTimePatient.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Appointment Time");
                mTimePicker.show();
            }
        });
        // setting onClick Listener on editText to select Appointment Date
        RAppointmentDatePatient.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Calendar mAppointmentDate = Calendar.getInstance();
                mYear = mAppointmentDate.get(Calendar.YEAR);
                mMonth = mAppointmentDate.get(Calendar.MONTH);
                mDay = mAppointmentDate.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog mDatePicker;

                mDatePicker = new DatePickerDialog(RequestAppointment.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
                        RAppointmentDatePatient.setFocusable(false);
                        RAppointmentDatePatient.setText(i3 + "-" + (i2+1) + "-"+ i);
                    }
                }, mDay, mMonth, mYear);//Yes 24 hour time
                mDatePicker.updateDate(mYear, mMonth, mDay);
                mDatePicker.setTitle("Select Appointment Date");
                mDatePicker.show();
            }
        });

        // Sending Request to The HeathFiled Surgery Through parse.comm
        RAppointmentRequestbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProgressDialog = new ProgressDialog(RequestAppointment.this);
                // Set progressdialog title
                mProgressDialog.setTitle("Requesting Appointment");
                // Set progressdialog message
                mProgressDialog.setMessage("Please wait...");
                mProgressDialog.setIndeterminate(false);
                // Show progressdialog
                mProgressDialog.show();
                ParseObject PatientAppointment = new ParseObject("PatientAppointmentRequest");
                String patientname = RPatientNamePatient.getText().toString().trim();
                String appointmentTime = RAppointmentTimePatient.getText().toString().trim();
                String appointmentDate = RAppointmentDatePatient.getText().toString().trim();
                String patientPhone = RpatientPhone.getText().toString();
                String Patientemail = ParseUser.getCurrentUser().getEmail();
                String Patientid = ParseUser.getCurrentUser().getObjectId();
                String PatientDob = String.valueOf(ParseUser.getCurrentUser().get("DateofBirth"));
                String DoctorHameedId = "DR4567";
                String DoctorBRPatelId ="DR1234";
                String DoctorJamal = "DR2112";
                //26 May 2015 Tue 10:00

                SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
                SimpleDateFormat format2 = new SimpleDateFormat("dd MMM yyyy E");
                Date date = null;
                try {
                    date = format1.parse(appointmentDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String v = (format2.format(date) +" "+ appointmentTime);




                if (Doctor.equals("Dr B R Patel")){
                    PatientAppointment.put("DoctorID", DoctorBRPatelId);
                    PatientAppointment.put("createdBy", ParseUser.getCurrentUser());
                    PatientAppointment.put("DrName", Doctor);
                    PatientAppointment.put("PatientName", patientname);
                    PatientAppointment.put("RequestedAppointment", v);
                    PatientAppointment.put("RequestedTime", appointmentTime);
                    PatientAppointment.put("PatientPhone", patientPhone);
                    PatientAppointment.put("RequestedEmail", Patientemail);
                    PatientAppointment.put("RequestedId", Patientid);
                    PatientAppointment.put("PatientDob",PatientDob);
                    PatientAppointment.saveEventually();

                } else if (Doctor.equals("Dr Jamal")){
                    PatientAppointment.put("DoctorID", DoctorJamal);
                    PatientAppointment.put("createdBy", ParseUser.getCurrentUser());
                    PatientAppointment.put("DrName", Doctor);
                    PatientAppointment.put("PatientName", patientname);
                    PatientAppointment.put("RequestedAppointment", v);
                    PatientAppointment.put("RequestedTime", appointmentTime);
                    PatientAppointment.put("PatientPhone", patientPhone);
                    PatientAppointment.put("RequestedEmail", Patientemail);
                    PatientAppointment.put("RequestedId", Patientid);
                    PatientAppointment.put("PatientDob",PatientDob);
                    PatientAppointment.saveEventually();

                }else if (Doctor.equals("Dr Hameed")){
                    PatientAppointment.put("DoctorID", DoctorHameedId);
                    PatientAppointment.put("createdBy", ParseUser.getCurrentUser());
                    PatientAppointment.put("DrName", Doctor);
                    PatientAppointment.put("PatientName", patientname);
                    PatientAppointment.put("RequestedAppointment", v);
                    PatientAppointment.put("RequestedTime", appointmentTime);
                    PatientAppointment.put("PatientPhone", patientPhone);
                    PatientAppointment.put("RequestedEmail", Patientemail);
                    PatientAppointment.put("RequestedId", Patientid);
                    PatientAppointment.put("PatientDob",PatientDob);
                    PatientAppointment.saveEventually();
                }

                Toast toast = Toast.makeText(RequestAppointment.this, " Appointment Requested Successfully ", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                Intent takeUserHome = new Intent(RequestAppointment.this, Patient.class);
                startActivity(takeUserHome);
                ParseQuery<ParseInstallation> pQuery = ParseInstallation.getQuery();
                pQuery.whereEqualTo("username", ParseUser.getCurrentUser());
                ParsePush push = new ParsePush();
                push.setQuery(pQuery);
                push.setMessage("Appointment Requested Successfully we will be back withing 48 hours to you");
                push.sendInBackground();
                RequestAppointment.this.finish();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.request_appointment, menu);
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
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        spinnerDoctor.setSelection(i);
        String selState = (String) spinnerDoctor.getSelectedItem();
        Doctor = selState;

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

        Toast.makeText(RequestAppointment.this, "Please Select Doctor", Toast.LENGTH_SHORT).show();

    }
    @Override
    public void onStop() {
        super.onStop();
        this.finish();

    }
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        RequestAppointment.this.finish();


                    }
                })
                .setNegativeButton("No", null)
                .show();


    }
}
