package appointment.booking.com.Doctor.Doctor_Support;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import appointment.booking.com.heathfield.R;

public class Doctor_ListViewAdapter extends BaseAdapter {


	// Declare Variables
	Context mContext;
	LayoutInflater inflater;
  
	private List<Doctor_support> DoctorViewList = null;

	private ArrayList<Doctor_support> arraylist;

	public Doctor_ListViewAdapter(Context context,
                                  List<Doctor_support> DoctorViewList) {
		mContext = context;
		this.DoctorViewList = DoctorViewList;
		inflater = LayoutInflater.from(mContext);
		this.arraylist = new ArrayList<Doctor_support>();
		this.arraylist.addAll(DoctorViewList);
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

    }

	@Override
	public int getCount() {
		return DoctorViewList.size();
	}

	@Override
	public Doctor_support getItem(int position) {
		return DoctorViewList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View view, ViewGroup parent) {
		final ViewHolder holder;
		if (view == null) {
			holder = new ViewHolder();
			view = inflater.inflate(R.layout.doctor_listview, null);
			// Locate the TextViews in listview_item.xml
			holder.pateint_id = (TextView) view.findViewById(R.id.Patient_id);
			holder.patient_name = (TextView) view.findViewById(R.id.Patient_name);
			holder.Date_of_birth = (TextView) view.findViewById(R.id.patiendob);
            holder.Appointment_Date = (TextView) view.findViewById(R.id.Appointment);
            holder.DoctorName = (TextView) view.findViewById(R.id.Doctor_name);
            holder.Medication = (TextView)view.findViewById(R.id.patientcontact);
            holder.MedicineAmount = (TextView)view.findViewById(R.id.emailaddress);
            holder.MedicineDispence = (TextView)view.findViewById(R.id.Appointment_no);
            holder.MedicineRefill = (TextView)view.findViewById(R.id.Docotorid);
			view.setTag(holder);




		} else {
			holder = (ViewHolder) view.getTag();
		}
		// Set the results into TextViews
	    holder.pateint_id.setText(DoctorViewList.get(position).getPatient_Id());
		holder.patient_name.setText(DoctorViewList.get(position).getPatient_Name());
		holder.Date_of_birth.setText(DoctorViewList.get(position).getDate_of_Birth());
        holder.Appointment_Date.setText((DoctorViewList.get(position).getAppointment_Date()));
        holder.DoctorName.setText((DoctorViewList.get(position).getDoctorname()));
        holder.Medication.setText((DoctorViewList.get(position).getMedication()));
        holder.MedicineAmount.setText((DoctorViewList.get(position).getMedicineamount()));
        holder.MedicineDispence.setText((DoctorViewList.get(position).getMedicinedispense()));
        holder.MedicineRefill.setText((DoctorViewList.get(position).getMedicinerefill()));


        return view;


	}



}