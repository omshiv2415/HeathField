package appointment.booking.com.patient;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import appointment.booking.com.heathfield.R;


public class PatientRegister extends Activity implements AdapterView.OnItemSelectedListener {

    static final int DATE_DIALOG_ID = 1;
    protected EditText Name;
    protected EditText DateOfBirth;
    protected EditText Enail;
    protected EditText CreateId;
    protected EditText Password;
    protected EditText age;
    protected EditText lastname;
    protected EditText p_Phone;
    protected Button RegisterButton;
    protected TextView AllreadyHaveanAccount;
    String Gender;
    Spinner spinnerGender;
    private TextToSpeech speech;
    private int mYear;
    private int mMonth;
    private int mDay;
    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
                    updateDisplay();
                }
            };
    private String[] state = {"Male", "Female"};
    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_register);

        Name = (EditText) findViewById(R.id.Name);
        DateOfBirth = (EditText) findViewById(R.id.DateofBirth);
        Enail = (EditText) findViewById(R.id.EmailAddress);
        CreateId = (EditText) findViewById(R.id.CreateID);
        Password = (EditText) findViewById(R.id.Password);
        lastname = (EditText) findViewById(R.id.LastName);
        p_Phone = (EditText) findViewById(R.id.PatientPhone);
        spinnerGender = (Spinner) findViewById(R.id.gender);


        ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, state);
        adapter_state
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(adapter_state);

        spinnerGender.setOnItemSelectedListener(this);


        DateOfBirth.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        updateDisplay();

        // when user press already have an account it will take user to login page
        AllreadyHaveanAccount = (TextView) findViewById(R.id.AllReadyAccount);
        AllreadyHaveanAccount.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                // Switching to Login Screen/closing register screen
                finish();
            }
        });
        RegisterButton = (Button) findViewById(R.id.SignUpButton);
        // LISTEN TO REGISTER BUTTON CLICK
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get the username, password and email and convert them to string
                String pName = Name.getText().toString().trim();
                String pDateOfBirth = DateOfBirth.getText().toString().trim();
                String pPhone = p_Phone.getText().toString().trim();
                String pEmail = Enail.getText().toString().trim();
                String PCid = CreateId.getText().toString().trim();
                String pPassword = Password.getText().toString().trim();
                String plastName = lastname.getText().toString().trim();
                ParseUser user = new ParseUser();
                //1. Validation For Name
                if (pName.equals("")) {
                    speech = new TextToSpeech(PatientRegister.this, new TextToSpeech.OnInitListener() {
                        @Override
                        public void onInit(int status) {
                            if (status != TextToSpeech.ERROR) {
                                speech.setLanguage(Locale.UK);
                                String toSpeak = ("Sorry Please provide Name");
                                speech.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                                Toast.makeText(PatientRegister.this, toSpeak,
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    //2. Validation For Last Name
                } else if (plastName.equals("")) {


                    speech = new TextToSpeech(PatientRegister.this, new TextToSpeech.OnInitListener() {
                        @Override
                        public void onInit(int status) {
                            if (status != TextToSpeech.ERROR) {
                                speech.setLanguage(Locale.UK);
                                String toSpeak = ("Sorry Please provide Last Name");
                                speech.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                                Toast.makeText(PatientRegister.this, toSpeak,
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    //3. Validation For Date of Birth
                } else if (pDateOfBirth.equals("")) {


                    speech = new TextToSpeech(PatientRegister.this, new TextToSpeech.OnInitListener() {
                        @Override
                        public void onInit(int status) {
                            if (status != TextToSpeech.ERROR) {
                                speech.setLanguage(Locale.UK);
                                String toSpeak = ("Sorry Please provide Date of Birth");
                                speech.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                                Toast.makeText(PatientRegister.this, toSpeak,
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                //4. Validation For Email Address
                else if (!isValidEmail(pEmail)) {
                    speech = new TextToSpeech(PatientRegister.this, new TextToSpeech.OnInitListener() {
                        @Override
                        public void onInit(int status) {
                            if (status != TextToSpeech.ERROR) {
                                speech.setLanguage(Locale.UK);
                                String toSpeak = ("Please provide correct Email");
                                speech.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                                Toast.makeText(PatientRegister.this, toSpeak,
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                //5. Validation For Phone No
                else if (!isValidPhone(pPhone)) {
                    speech = new TextToSpeech(PatientRegister.this, new TextToSpeech.OnInitListener() {
                        @Override
                        public void onInit(int status) {
                            if (status != TextToSpeech.ERROR) {
                                speech.setLanguage(Locale.UK);
                                String toSpeak = ("Sorry Phone Number must be 11 Digit");
                                speech.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                                Toast.makeText(PatientRegister.this, toSpeak,
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                //5. Validation For ID It checks Id must be between 6 and 12
                else if (!isValidPatientID(PCid)) {
                    speech = new TextToSpeech(PatientRegister.this, new TextToSpeech.OnInitListener() {
                        @Override
                        public void onInit(int status) {
                            if (status != TextToSpeech.ERROR) {
                                speech.setLanguage(Locale.UK);
                                String toSpeak = ("Sorry Patient Login ID must be between 6 and 12");
                                speech.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                                Toast.makeText(PatientRegister.this, toSpeak,
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                //6. Validation For Password It checks Password must be between 7 and 21
                else if (!isValidPassword(pPassword)) {
                    speech = new TextToSpeech(PatientRegister.this, new TextToSpeech.OnInitListener() {
                        @Override
                        public void onInit(int status) {
                            if (status != TextToSpeech.ERROR) {
                                speech.setLanguage(Locale.UK);
                                String toSpeak = ("Sorry Password must be between 7 and 21");
                                speech.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                                Toast.makeText(PatientRegister.this, toSpeak,
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {

                    user.setPassword(pPassword);
                    user.setUsername(PCid);
                    user.setEmail(pEmail);
                    user.put("LastName", plastName);
                    user.put("Name", pName);
                    user.put("DateofBirth", pDateOfBirth);
                    user.put("Gender", Gender);
                    user.put("Contact_Number", pPhone);
                    // setting activity as 0 to restrict the patient to book more than two appointment
                    user.put("Activity", 0);
                    // verifying user is patient or doctor
                    user.put("Verify", "Patient");
                    // setting activity as 0 to restrict the patient to book more than five prescription
                    user.put("Order_Prescription", 0);

                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                // user signed up successfully
                                Toast.makeText(PatientRegister.this, "Registered Successfully", Toast.LENGTH_LONG).show();
                                // take user login page
                                Intent takeUserHome = new Intent(PatientRegister.this, PatientLogin.class);
                                startActivity(takeUserHome);
                            } else {
                                //there was an error signing up user.
                                Toast.makeText(PatientRegister.this, "Please Select different User Name or Email Address",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }

            }
        });
    }

    // validating email id
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isValidName(String name) {
        if (name != null && name.length() >= 0 && name.length() <= 32) {
            return true;
        }
        return false;
    }

    private boolean isValidDateofBirth(String dob) {
        if (dob != null && dob.length() >= 0 && dob.length() <= 8) {
            return true;
        }
        return false;

    }

    private boolean isValidPatientID(String pid) {
        if (pid != null && pid.length() >= 6 && pid.length() <= 12) {
            return true;
        }
        return false;

    }

    private boolean isValidLastName(String Lastname) {
        if (Lastname != null && Lastname.length() >= 0 && Lastname.length() <= 32) {
            return true;
        }
        return false;

    }

    // validating password with retype password
    private boolean isValidPassword(String pass) {
        if (pass != null && pass.length() >= 7 && pass.length() <= 21) {
            return true;
        }
        return false;
    }

    private boolean isValidPhone(String pass) {
        if (pass != null && pass.length() >= 11 && pass.length() <= 11) {
            return true;
        }
        return false;
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {

            case DATE_DIALOG_ID:
                return new DatePickerDialog(this,
                        mDateSetListener,
                        mYear, mMonth, mDay);
        }
        return null;
    }

    protected void onPrepareDialog(int id, Dialog dialog) {
        switch (id) {

            case DATE_DIALOG_ID:
                ((DatePickerDialog) dialog).updateDate(mYear, mMonth, mDay);
                break;
        }
    }

    private void updateDisplay() {
        DateOfBirth.setText(
                new StringBuilder()
                        // Month is sunny based so add 1
                        .append(mDay).append("/")
                        .append(mMonth + 1).append("/")
                        .append(mYear).append(" "));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        spinnerGender.setSelection(position);
        String selState = (String) spinnerGender.getSelectedItem();
        Gender = selState;

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {


    }
}
