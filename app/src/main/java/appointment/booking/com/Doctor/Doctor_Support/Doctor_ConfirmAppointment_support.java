package appointment.booking.com.Doctor.Doctor_Support;

public class Doctor_ConfirmAppointment_support {
	private String Patient_Id;
    private String Patient_Name;
    private String Date_of_Birth;
    private String Appointment_Date;
    private String doctorname;
    private String PatientEmail;
    private String DoctorId;
    private String pContact;




    public String getAppointment_Date(){

        return Appointment_Date;
    }

    public void setAppointment_Date(String App_Date){

        this.Appointment_Date = App_Date;
    }
    public String getDoctorname(){

        return doctorname;
    }

    public void setDoctorname(String doctor_name){

        this.doctorname = doctor_name;
    }

    public String getPatientContactNo(){

        return pContact;
    }

    public void setPatientContactNo(String patient_contact){

        this.pContact = patient_contact;
    }

    public String getPatientEmail(){

        return PatientEmail;
    }

    public void setPatientEmail(String patEmail){

        this.PatientEmail = patEmail;
    }
    public String getDocId(){

        return DoctorId;
    }

    public void setDocId(String medicine_dispence){

        this.DoctorId = medicine_dispence;

    }


    public String getPatient_Id() {
		return Patient_Id;
	}


	public void setPatient_Id(String P_ID) {
		this.Patient_Id = P_ID;
	}

	public String getPatient_Name() {
		return Patient_Name;
	}

	public void setPatient_Name(String Pa_Name) {
		this.Patient_Name = Pa_Name;
	}

	public String getDate_of_Birth() {
		return Date_of_Birth;
	}

	public void setDate_of_Birth(String Pa_DatofBirth) {
		this.Date_of_Birth = Pa_DatofBirth;
	}




}