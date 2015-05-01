package appointment.booking.com.Prescription;

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

public class Prescription_Booking_ListViewAdapter extends BaseAdapter {


	// Declare Variables
	Context mContext;
	LayoutInflater inflater;
    protected Button  priscription_order;
	private List<prescription_support> PrescriptionList = null;

	private ArrayList<prescription_support> arraylist;

	public Prescription_Booking_ListViewAdapter(Context context,
                                                List<prescription_support> PrescriptionList) {
		mContext = context;
		this.PrescriptionList = PrescriptionList;
		inflater = LayoutInflater.from(mContext);
		this.arraylist = new ArrayList<prescription_support>();
		this.arraylist.addAll(PrescriptionList);
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
		return PrescriptionList.size();
	}

	@Override
	public prescription_support getItem(int position) {
		return PrescriptionList.get(position);
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
		holder.pateint_id.setText(PrescriptionList.get(position).getPatient_Id());
		holder.patient_name.setText(PrescriptionList.get(position).getPatient_Name());
		holder.Date_of_birth.setText(PrescriptionList.get(position).getDate_of_Birth());
        holder.Appointment_Date.setText((PrescriptionList.get(position).getAppointment_Date()));
        holder.DoctorName.setText((PrescriptionList.get(position).getDoctorname()));
        holder.DoctorId.setText((PrescriptionList.get(position).getDoctor_Id()));
        holder.Medication.setText((PrescriptionList.get(position).getMedication()));
        holder.MedicineAmount.setText((PrescriptionList.get(position).getMedicineamount()));
        holder.MedicineDispence.setText((PrescriptionList.get(position).getMedicinedispense()));
        holder.MedicineRefill.setText((PrescriptionList.get(position).getMedicinerefill()));

        view.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View arg0) {
                    // Send single item click data to Booking_Appointments_View Class
                    Intent intent = new Intent(mContext, Prescription_View.class);
                    // Pass all data rank
                    intent.putExtra("PatientId",
                            (PrescriptionList.get(position).getPatient_Id()));
                    // Pass all data country
                    intent.putExtra("PatientName",
                            (PrescriptionList.get(position).getPatient_Name()));
                    // Pass all data population
                    intent.putExtra("PatientDob",
                            (PrescriptionList.get(position).getDate_of_Birth()));
                    intent.putExtra("AppointmentDate",
                            (PrescriptionList.get(position).getAppointment_Date()));
                    intent.putExtra("DoctorName",
                            (PrescriptionList.get(position).getDoctorname()));
                    intent.putExtra("DoctorID",
                            (PrescriptionList.get(position).getDoctor_Id()));
                    // Pass all data country
                    intent.putExtra("Medication",
                            (PrescriptionList.get(position).getMedication()));
                    // Pass all data population
                    intent.putExtra("MedicationAmount",
                            (PrescriptionList.get(position).getMedicineamount()));
                    intent.putExtra("MedicineDispense",
                            (PrescriptionList.get(position).getMedicinedispense()));
                    intent.putExtra("MedicineRefill",
                            (PrescriptionList.get(position).getMedicinerefill()));



                    intent.putExtra("Book","BookAppointment");

                    // Start Booking_Appointments_View Class
                    mContext.startActivity(intent);
                    // intent.removeExtra("Cancel");



                }
            });

            return view;


	}



}