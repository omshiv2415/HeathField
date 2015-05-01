package appointment.booking.com.Doctor.Doctor_Support;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import appointment.booking.com.heathfield.R;

public class Doctor_ConfirmAppointment_ListViewAdapter extends BaseAdapter {


	// Declare Variables
	Context mContext;
	LayoutInflater inflater;
  
	private List<Doctor_ConfirmAppointment_support> DoctorConfirmAppList = null;

	private ArrayList<Doctor_ConfirmAppointment_support> arraylist;

	public Doctor_ConfirmAppointment_ListViewAdapter(Context context,
                                                     List<Doctor_ConfirmAppointment_support> DoctorConfirmAppList) {
		mContext = context;
		this.DoctorConfirmAppList = DoctorConfirmAppList;
		inflater = LayoutInflater.from(mContext);
		this.arraylist = new ArrayList<Doctor_ConfirmAppointment_support>();
		this.arraylist.addAll(DoctorConfirmAppList);
	}

	public class ViewHolder {
		TextView pateint_id;
		TextView patient_name;
		TextView Date_of_birth;
        TextView Appointment_Date;
        TextView DoctorName;
        TextView pemail;
        TextView docorId;
        TextView pPhone;


    }

	@Override
	public int getCount() {
		return DoctorConfirmAppList.size();
	}

	@Override
	public Doctor_ConfirmAppointment_support getItem(int position) {
		return DoctorConfirmAppList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View view, ViewGroup parent) {
		final ViewHolder holder;
		if (view == null) {
			holder = new ViewHolder();
			view = inflater.inflate(R.layout.doctor_confirm_appointemtn_listview, null);
			// Locate the TextViews in listview_item.xml
			holder.pateint_id = (TextView) view.findViewById(R.id.Patient_id);
			holder.patient_name = (TextView) view.findViewById(R.id.Patient_name);
			holder.Date_of_birth = (TextView) view.findViewById(R.id.patiendob);
            holder.Appointment_Date = (TextView) view.findViewById(R.id.Appointment);
            holder.DoctorName = (TextView) view.findViewById(R.id.Doctor_name);
            holder.pPhone = (TextView)view.findViewById(R.id.patientcontact);
            holder.pemail = (TextView)view.findViewById(R.id.emailaddress);
         //   holder.pemail = (TextView)view.findViewById(R.id.Appointment_no);
            holder.docorId = (TextView)view.findViewById(R.id.Docotorid);
			view.setTag(holder);




		} else {
			holder = (ViewHolder) view.getTag();
		}
		// Set the results into TextViews
	    holder.pateint_id.setText(DoctorConfirmAppList.get(position).getPatient_Id());
		holder.patient_name.setText(DoctorConfirmAppList.get(position).getPatient_Name());
		holder.Date_of_birth.setText(DoctorConfirmAppList.get(position).getDate_of_Birth());
        holder.Appointment_Date.setText((DoctorConfirmAppList.get(position).getAppointment_Date()));
        holder.DoctorName.setText((DoctorConfirmAppList.get(position).getDoctorname()));
        holder.pemail.setText((DoctorConfirmAppList.get(position).getPatientEmail()));
        holder.docorId.setText((DoctorConfirmAppList.get(position).getDocId()));
        holder.pPhone.setText((DoctorConfirmAppList.get(position).getPatientContactNo()));



        view.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View arg0) {
                // Send single item click data to Booking_Appointments_View Class
                Intent intent = new Intent(mContext, Appointment_Confirmation_View.class);
                // Pass all data rank
                intent.putExtra("PatientId",
                        (DoctorConfirmAppList.get(position).getPatient_Id()));

                // Pass all data population
                intent.putExtra("DoctorID",
                        (DoctorConfirmAppList.get(position).getDocId()));
                intent.putExtra("AppointmentDate",
                        (DoctorConfirmAppList.get(position).getAppointment_Date()));
                intent.putExtra("DoctorName",
                        (DoctorConfirmAppList.get(position).getDoctorname()));





                // Start Booking_Appointments_View Class
                mContext.startActivity(intent);
                // intent.removeExtra("Cancel");


            }
        });

        return view;



    }



}