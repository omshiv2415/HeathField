package appointment.booking.com.booking;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import appointment.booking.com.heathfield.R;

public class Booking_ListViewAdapter extends BaseAdapter {

	// Declare Variables
	Context mContext;
	LayoutInflater inflater;
	private List<App_Booking_and_cancel_support> HeathfieldAppointment = null;
	private ArrayList<App_Booking_and_cancel_support> arraylist;
	public Booking_ListViewAdapter(Context context,
                                   List<App_Booking_and_cancel_support> HeathfieldAppointment) {
		mContext = context;
		this.HeathfieldAppointment = HeathfieldAppointment;
		inflater = LayoutInflater.from(mContext);
		this.arraylist = new ArrayList<App_Booking_and_cancel_support>();
		this.arraylist.addAll(HeathfieldAppointment);
	}

    public class ViewHolder {
		TextView D_Id;
		TextView D_Name;
		TextView P_Appointment;
        TextView P_Appointment_No;
	}

	@Override
	public int getCount() {
		return HeathfieldAppointment.size();
	}

	@Override
	public App_Booking_and_cancel_support getItem(int position) {
		return HeathfieldAppointment.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	public View getView(final int position, View view, ViewGroup parent) {
		final ViewHolder holder;

		if (view == null) {
			holder = new ViewHolder();
			view = inflater.inflate(R.layout.listview_item, null);
			// Locate the TextViews in listview_item.xml
			holder.D_Id = (TextView) view.findViewById(R.id.Doctor_id);
			holder.D_Name = (TextView) view.findViewById(R.id.Doctor_name);
			holder.P_Appointment = (TextView) view.findViewById(R.id.Appointment);
            holder.P_Appointment_No = (TextView) view.findViewById(R.id.Appointment_no);
			view.setTag(holder);


		} else {
			holder = (ViewHolder) view.getTag();
		}

		// Set the results into TextViews
		holder.D_Id.setText(HeathfieldAppointment.get(position).getDoctor_id());
		holder.D_Name.setText(HeathfieldAppointment.get(position).getDoctor_Name());
		holder.P_Appointment.setText(HeathfieldAppointment.get(position).getAppointment_Date());
        holder.P_Appointment_No.setText((HeathfieldAppointment.get(position).getAppointment_number()));
     //   holder.P_Appointment_No.setText("hello");

		// Listen for ListView Item Click
		view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// Send single item click data to Booking_Appointments_View Class
				Intent intent = new Intent(mContext, Booking_Appointments_View.class);

				// Pass all data D_Id
				intent.putExtra("D_Id",
						(HeathfieldAppointment.get(position).getDoctor_id()));
				// Pass all data D_Name
				intent.putExtra("D_Name",
						(HeathfieldAppointment.get(position).getDoctor_Name()));
				// Pass all data P_Appointment
				intent.putExtra("P_Appointment",
						(HeathfieldAppointment.get(position).getAppointment_Date()));
                intent.putExtra("P_Appointment_No",
                        (HeathfieldAppointment.get(position).getAppointment_number()));

                intent.putExtra("Book","BookAppointment");

				// Start Booking_Appointments_View Class
				mContext.startActivity(intent);
               // intent.removeExtra("Cancel");




			}
		});

		return view;
	}
}