package ie.cp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ie.cp.R;
import ie.cp.models.Reservation;

public class ReservationItem {
    public View view;

    public ReservationItem(Context context, ViewGroup parent,
                           View.OnClickListener deleteListener, Reservation reservation)
    {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.reservationcard, parent, false);
        view.setTag(reservation.reservationId);

        updateControls(reservation);

        ImageView imgDelete = view.findViewById(R.id.rowDeleteImg);
        imgDelete.setTag(reservation);
        imgDelete.setOnClickListener(deleteListener);
    }

    private void updateControls(Reservation reservation) {
        ((TextView) view.findViewById(R.id.rowReservationUser)).setText(reservation.userId);
        ((TextView) view.findViewById(R.id.rowReservationCarPark)).setText(reservation.carParkId);
        ((TextView) view.findViewById(R.id.rowReservationCarParkSpace)).setText(reservation.carParkSpaceId);
    }
}
