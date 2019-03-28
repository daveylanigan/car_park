package ie.cp.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import ie.cp.R;
import ie.cp.models.Reservation;

public class ReservationListAdapter extends ArrayAdapter<Reservation> {

    private Context context;
    private View.OnClickListener deleteListener;
    public List<Reservation> reservationList;

    public ReservationListAdapter(Context context, View.OnClickListener deleteListener, List<Reservation> reservationList)
    {
        super(context, R.layout.reservationrow, reservationList);

        this.context = context;
        this.deleteListener = deleteListener;
        this.reservationList = reservationList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ReservationItem item = new ReservationItem(context, parent,
                deleteListener, reservationList.get(position));
        return item.view;
    }

    @Override
    public int getCount() {
        return reservationList.size();
    }

    @Override
    public Reservation getItem(int position) {
        return reservationList.get(position);
    }

}
