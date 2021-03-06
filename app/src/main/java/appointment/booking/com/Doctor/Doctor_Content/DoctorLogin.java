package appointment.booking.com.Doctor.Doctor_Content;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

public class DoctorLogin extends Activity {

    Boolean isInternetPresent = true;
    ConnectionDetector cd;
    protected EditText mUsername;
    protected EditText mPassword;
    protected Button mLoginBtn;
    protected Button mCreateAccountBtn;
    protected Toast toast;
    protected Button PassReset;
    int TotalSecond = 60000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_login);
        cd = new ConnectionDetector(getApplicationContext());

        //initialize
        mUsername = (EditText) findViewById(R.id.usernameLoginTextBox);
        mPassword = (EditText) findViewById(R.id.passwordLoginTextBox);
        mLoginBtn = (Button) findViewById(R.id.loginBtn);
        mCreateAccountBtn = (Button) findViewById(R.id.createAccountbtnLogin);
        PassReset = (Button) findViewById(R.id.reset);


        PassReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takeUserReset = new Intent(DoctorLogin.this, PasswordReset.class);
                startActivity(takeUserReset);
                DoctorLogin.this.finish();
            }
        });





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

                    // Intent takeUserHome = new Intent(LoginActivity.this, MainActivity.class);
                    mUsername.getText().clear();
                    mPassword.getText().clear();
                    // startActivity(takeUserHome);



                    ParseUser.logInInBackground(username, password, new LogInCallback() {
                        @Override
                        public void done(ParseUser parseUser, ParseException e) {

                            if (e == null) {
                                // success !


                                // Take user to homepage
                                Intent takeUserHome = new Intent(DoctorLogin.this, Doctor.class);
                                mUsername.getText().clear();
                                mPassword.getText().clear();
                                startActivity(takeUserHome);
                                DoctorLogin.this.finish();
                            } else {
                                // sorry there is a login problem
                                AlertDialog.Builder builder = new AlertDialog.Builder(DoctorLogin.this);
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


                }else {
                    // Internet connection is not present
                    // Ask user to connect to Internet
                    showAlertDialog(DoctorLogin.this, "No Internet Connection",
                            "Please check your internet connection.", false);

                }

            }

    });}


    @Override
    public void onStop() {
        super.onStop();
        //this.finish();

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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.doctor_login, menu);
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
