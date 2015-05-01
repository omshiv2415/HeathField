package appointment.booking.com.booking.History;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import appointment.booking.com.booking.App_Booking_and_cancel_support;
import appointment.booking.com.heathfield.R;

public class Booking_History_ListViewAdapter extends BaseAdapter {

	// Declare Variables
	Context mContext;
	LayoutInflater inflater;
	private List<App_Booking_and_cancel_support> HeathfieldAppointment = null;
	private ArrayList<App_Booking_and_cancel_support> arraylist;

	public Booking_History_ListViewAdapter(Context context,
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
		TextView population;
        TextView P_Appointment_No;
        TextView P_Appointment_Created;
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
			view = inflater.inflate(R.layout.listview_item_book_and_cancel_history, null);
			// Locate the TextViews in listview_item.xml
			holder.D_Id = (TextView) view.findViewById(R.id.Doctor_id);
			holder.D_Name = (TextView) view.findViewById(R.id.Doctor_name);
			holder.population = (TextView) view.findViewById(R.id.Appointment);
            holder.P_Appointment_No = (TextView) view.findViewById(R.id.Appointment_no);
            holder.P_Appointment_Created = (TextView)view.findViewById(R.id.CreatedAt);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		// Set the results into TextViews
		holder.D_Id.setText(HeathfieldAppointment.get(position).getDoctor_id());
		holder.D_Name.setText(HeathfieldAppointment.get(position).getDoctor_Name());
		holder.population.setText(HeathfieldAppointment.get(position).getAppointment_Date());
        holder.P_Appointment_No.setText((HeathfieldAppointment.get(position).getAppointment_number()));
        holder.P_Appointment_Created.setText((HeathfieldAppointment.get(position).getAppointment_Created()));


		return view;
	}
}