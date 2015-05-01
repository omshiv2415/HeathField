package appointment.booking.com.Doctor.Doctor_Support;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import appointment.booking.com.heathfield.R;

public class Prescription_Confirmation_ListViewAdapter extends BaseAdapter {


	// Declare Variables
	Context mContext;
	LayoutInflater inflater;
    protected Button  priscription_order;
	private List<prescription_Confirmation_support> PrescriptionSupportList = null;

	private ArrayList<prescription_Confirmation_support> arraylist;

	public Prescription_Confirmation_ListViewAdapter(Context context,
                                                     List<prescription_Confirmation_support> PrescriptionSupportList) {
		mContext = context;
		this.PrescriptionSupportList = PrescriptionSupportList;
		inflater = LayoutInflater.from(mContext);
		this.arraylist = new ArrayList<prescription_Confirmation_support>();
		this.arraylist.addAll(PrescriptionSupportList);
	}

	public class ViewHolder {
		TextView pateint_id;
		TextView patient_name;
		TextView Date_of_birth;
        TextView Appointment_Date;
        TextView DoctorName;
        TextView Medication;
        TextView MedicineAmount;
        TextView MedicineDispence;
        TextView MedicineRefill;
        TextView DoctorId;

    }

	@Override
	public int getCount() {
		return PrescriptionSupportList.size();
	}

	@Override
	public prescription_Confirmation_support getItem(int position) {
		return PrescriptionSupportList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View view, ViewGroup parent) {
		final ViewHolder holder;
		if (view == null) {
			holder = new ViewHolder();
			view = inflater.inflate(R.layout.prescription, null);
			// Locate the TextViews in listview_item.xml
			holder.pateint_id = (TextView) view.findViewById(R.id.patientID);
			holder.patient_name = (TextView) view.findViewById(R.id.patientName);
			holder.Date_of_birth = (TextView) view.findViewById(R.id.patient_dob);
            holder.Appointment_Date = (TextView) view.findViewById(R.id.appointment_date);
            holder.DoctorName = (TextView) view.findViewById(R.id.doctor_Name);
            holder.DoctorId = (TextView) view.findViewById(R.id.Doctorid);
            holder.Medication = (TextView)view.findViewById(R.id.medicine_Name);
            holder.MedicineAmount = (TextView)view.findViewById(R.id.medicineAmount);
            holder.MedicineDispence = (TextView)view.findViewById(R.id.medicine_Despense);
            holder.MedicineRefill = (TextView)view.findViewById(R.id.medicine_Refill);
			view.setTag(holder);




		} else {
			holder = (ViewHolder) view.getTag();
		}
		// Set the results into TextViews
		holder.pateint_id.setText(PrescriptionSupportList.get(position).getPatient_Id());
		holder.patient_name.setText(PrescriptionSupportList.get(position).getPatient_Name());
		holder.Date_of_birth.setText(PrescriptionSupportList.get(position).getDate_of_Birth());
        holder.Appointment_Date.setText((PrescriptionSupportList.get(position).getAppointment_Date()));
        holder.DoctorName.setText((PrescriptionSupportList.get(position).getDoctorname()));
        holder.DoctorId.setText((PrescriptionSupportList.get(position).getDoctor_Id()));
        holder.Medication.setText((PrescriptionSupportList.get(position).getMedication()));
        holder.MedicineAmount.setText((PrescriptionSupportList.get(position).getMedicineamount()));
        holder.MedicineDispence.setText((PrescriptionSupportList.get(position).getMedicinedispense()));
        holder.MedicineRefill.setText((PrescriptionSupportList.get(position).getMedicinerefill()));

        view.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View arg0) {
                    // Send single item click data to Booking_Appointments_View Class
                    Intent intent = new Intent(mContext, Prescription_Confirmation_View.class);
                    // Pass all data rank
                    intent.putExtra("PatientId",
                            (PrescriptionSupportList.get(position).getPatient_Id()));
                    // Pass all data country
                    intent.putExtra("PatientName",
                            (PrescriptionSupportList.get(position).getPatient_Name()));
                    // Pass all data population
                    intent.putExtra("PatientDob",
                            (PrescriptionSupportList.get(position).getDate_of_Birth()));
                    intent.putExtra("AppointmentDate",
                            (PrescriptionSupportList.get(position).getAppointment_Date()));
                    intent.putExtra("DoctorName",
                            (PrescriptionSupportList.get(position).getDoctorname()));
                    intent.putExtra("DoctorID",
                            (PrescriptionSupportList.get(position).getDoctor_Id()));
                    // Pass all data country
                    intent.putExtra("Medication",
                            (PrescriptionSupportList.get(position).getMedication()));
                    // Pass all data population
                    intent.putExtra("MedicationAmount",
                            (PrescriptionSupportList.get(position).getMedicineamount()));
                    intent.putExtra("MedicineDispense",
                            (PrescriptionSupportList.get(position).getMedicinedispense()));
                    intent.putExtra("MedicineRefill",
                            (PrescriptionSupportList.get(position).getMedicinerefill()));



                    intent.putExtra("Book","BookAppointment");

                    // Start Booking_Appointments_View Class
                    mContext.startActivity(intent);
                    // intent.removeExtra("Cancel");


                }
            });

            return view;


	}



}