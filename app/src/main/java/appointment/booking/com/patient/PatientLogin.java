package appointment.booking.com.patient;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import appointment.booking.com.heathfield.PasswordReset;
import appointment.booking.com.heathfield.R;
import appointment.booking.com.support.ConnectionDetector;

public class PatientLogin extends Activity {
    protected EditText mUsername;
    protected EditText mPassword;
    protected Button mLoginBtn;
    protected Button mCreateAccountBtn;
    protected Toast toast;
    protected Button PassReset;
    Boolean isInternetPresent = true;
    ConnectionDetector cd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_login);
        cd = new ConnectionDetector(getApplicationContext());
        //initialize
        mUsername = (EditText) findViewById(R.id.usernameLoginTextBox);
        mPassword = (EditText) findViewById(R.id.passwordLoginTextBox);
        mLoginBtn = (Button) findViewById(R.id.loginBtn);
        mCreateAccountBtn = (Button) findViewById(R.id.createAccountbtnLogin);
        PassReset = (Button) findViewById(R.id.reset);
        //  mUsername.setCompoundDrawables(null, null, getResources().getDrawable(R.drawable.user), null);

        mLoginBtn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                // flurry implementation when user click login button

                ConnectionDetector cd;
                // creating connection detector class instance
                cd = new ConnectionDetector(getApplicationContext());
                // get Internet status
                isInternetPresent = cd.isConnectingToInternet();
                // check for Internet status

                if (isInternetPresent) {
                    // get the user inputs from edit text and convert to string
                    final String username = mUsername.getText().toString().trim();
                    String password = mPassword.getText().toString().trim();
                    mUsername.getText().clear();
                    mPassword.getText().clear();
                    ParseUser.logInInBackground(username, password, new LogInCallback() {
                        @Override
                        public void done(ParseUser parseUser, ParseException e) {

                            if (e == null) {
                                // success ! Take user to homepage
                                Intent takeUserHome = new Intent(PatientLogin.this, Patient.class);
                                mUsername.getText().clear();
                                mPassword.getText().clear();
                                startActivity(takeUserHome);

                            } else {
                                // sorry there is a login problem
                                AlertDialog.Builder builder = new AlertDialog.Builder(PatientLogin.this);
                                builder.setMessage(e.getMessage());
                                builder.setTitle("Sorry");
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                        }
                    });
                } else {
                    // Internet connection is not present
                    // Ask user to connect to Internet
                    showAlertDialog(PatientLogin.this, "No Internet Connection",
                            "Please check your internet connection.", false);
                }
            }
        });

        mCreateAccountBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                ConnectionDetector cd;
                // creating connection detector class instance
                cd = new ConnectionDetector(getApplicationContext());
                // get Internet status
                isInternetPresent = cd.isConnectingToInternet();
                // check for Internet status
                if (isInternetPresent) {
                    Intent takeUserRegister = new Intent(PatientLogin.this, PatientRegister.class);
                    startActivity(takeUserRegister);
                    PatientLogin.this.finish();

                } else {
                    // Internet connection is not present
                    // Ask user to connect to Internet
                    showAlertDialog(PatientLogin.this, "No Internet Connection",
                            "Please check your internet connection.", false);

                }
            }
        });

        PassReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takeUserReset = new Intent(PatientLogin.this, PasswordReset.class);
                startActivity(takeUserReset);
                PatientLogin.this.finish();
            }
        });

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

}
