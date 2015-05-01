package appointment.booking.com.patient;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import appointment.booking.com.heathfield.R;


public class PatientHome extends Activity {

    EditText email ;
    EditText mUsername;
    EditText mLastname;
    EditText mName;
    EditText mGender;
    Button Update;
    EditText mDateOfBirth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_home);
        email = (EditText)findViewById(R.id.Email);
        mLastname = (EditText)findViewById(R.id.lastname);
        mUsername = (EditText)findViewById(R.id.Username);
        mName = (EditText)findViewById(R.id.name);
        mGender = (EditText)findViewById(R.id.gender);
        mDateOfBirth =(EditText)findViewById(R.id.dateofbirth);
        Update = (Button)findViewById(R.id.update);

        email.setText(ParseUser.getCurrentUser().getEmail());
        mUsername.setText(ParseUser.getCurrentUser().getUsername());
        mDateOfBirth.setText((CharSequence) ParseUser.getCurrentUser().get("DateofBirth"));
        mGender.setText((CharSequence) ParseUser.getCurrentUser().get("Gender"));
        mName.setText((CharSequence) ParseUser.getCurrentUser().get("Name"));
        mLastname.setText((CharSequence) ParseUser.getCurrentUser().get("LastName"));



        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            final String dateofbirth =    mDateOfBirth.getText().toString();
            final String lastname    =    mLastname.getText().toString();
            final String myname      =    mName.getText().toString();
            final String mUsernam    =    mUsername.getText().toString();
            final String myemail     =    email.getText().toString();



                ParseUser user = ParseUser.getCurrentUser();
                user.setUsername(mUsernam);
                user.setEmail(myemail);
                user.put("LastName", lastname);
                user.put("DateofBirth", dateofbirth);
                user.put("Name", myname);


                user.saveInBackground(new SaveCallback() {

                    @Override
                    public void done(ParseException e) {
                        setProgressBarIndeterminateVisibility(false);

                        if (e == null) {
                            //Success!
                            Toast.makeText(PatientHome.this, " Your Details updated successfully ", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(PatientHome.this, PatientHome.class);
                            startActivity(intent);

                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(PatientHome.this);
                            builder.setMessage(e.getMessage())
                                    .setTitle(R.string.sign_up_error_title)
                                    .setPositiveButton(android.R.string.ok, null);
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    }
                });




            }
        });



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.patient_home, menu);
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
}
