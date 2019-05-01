package ie.cp.api;

import android.support.v4.app.Fragment;

import java.util.List;

import ie.cp.models.CarPark;
import ie.cp.models.CarParkSpace;
import ie.cp.models.Reservation;

public interface VolleyListener {
    void setList(List list);
    void setSpaceList(List list);
    void setReservationList(List list);
    void setCarPark(CarPark carpark);
    void setReservation(Reservation reservation);
    void updateUI(Fragment fragment);
    void setCarParkSpace(CarParkSpace carParkSpace);
    void updateCarParkSpaceDropdown(Fragment fragment);
    void setValidStatus(int result);
}