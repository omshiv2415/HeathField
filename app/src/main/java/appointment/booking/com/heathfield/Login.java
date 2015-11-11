package appointment.booking.com.heathfield;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.parse.ParseAnalytics;

import appointment.booking.com.Doctor.Doctor_Content.DoctorLogin;
import appointment.booking.com.patient.PatientLogin;

//login Activity
public class Login extends Activity {

    protected ImageButton Doctors;
    protected ImageButton Patients;
    protected Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ParseAnalytics.trackAppOpenedInBackground(getIntent());

        // image button initializing
        Doctors = (ImageButton)findViewById(R.id.Doctor);
        Patients = (ImageButton)findViewById(R.id.patient);


        // setting up click listner
        Doctors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent takeDoctorLogin = new Intent(Login.this, DoctorLogin.class);
                startActivity(takeDoctorLogin);


            }
        });

        Patients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePatientLogin = new Intent(Login.this, PatientLogin.class);
                startActivity(takePatientLogin);

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }


}
