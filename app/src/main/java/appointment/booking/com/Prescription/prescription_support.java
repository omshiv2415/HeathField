package appointment.booking.com.Prescription;

public class prescription_support {
	private String Patient_Id;
    private String Doctor_Id;
    private String Patient_Name;
    private String Date_of_Birth;
    private String Appointment_Date;
    private String doctorname;
    private String medication;
    private String medicineamount;
    private String medicinedispense;
    private String medicinerefill;



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

    public String getMedication(){

        return medication;
    }

    public void setMedication(String patient_medication){

        this.medication = patient_medication;
    }

    public String getMedicineamount(){

        return medicineamount;
    }

    public void setMedicineamount(String medicine_amount){

        this.medicineamount = medicine_amount;
    }
    public String getMedicinedispense(){

        return medicinedispense;
    }

    public void setMedicinedispense(String medicine_dispence){

        this.medicinedispense = medicine_dispence;

    }
    public String getMedicinerefill(){

        return medicinerefill;
    }

    public void setMedicinerefill(String medicine_refill){

        this.medicinerefill = medicine_refill;
    }


    public String getDoctor_Id() {
        return Doctor_Id;
    }


    public void setDoctor_Id(String D_ID) {
        this.Doctor_Id = D_ID;
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