package appointment.booking.com.booking;

public class App_Booking_and_cancel_support {
	private String doctor_id;
	private String Doctor_Name;
	private String Appointment_Date;
    private String Appointment_number;
    private String Appointment_Created;


    public String getAppointment_number(){

        return Appointment_number;
    }

    public void setAppointment_number(String object){

        this.Appointment_number = object;
    }
    public String getAppointment_Created(){

        return Appointment_Created;
    }

    public void setAppointment_Created(String created){

        this.Appointment_Created = created;
    }
	public String getDoctor_id() {
		return doctor_id;
	}


	public void setDoctor_id(String doctor_id) {
		this.doctor_id = doctor_id;
	}

	public String getDoctor_Name() {
		return Doctor_Name;
	}

	public void setDoctor_Name(String doctor_Name) {
		this.Doctor_Name = doctor_Name;
	}

	public String getAppointment_Date() {
		return Appointment_Date;
	}

	public void setAppointment_Date(String appointment_Date) {
		this.Appointment_Date = appointment_Date;
	}

}