package ie.cp.api;

import android.support.v4.app.Fragment;

import java.util.List;

import ie.cp.models.CarPark;

public interface VolleyListener {
    void setList(List list);
    void setCarPark(CarPark carpark);
    void updateUI(Fragment fragment);

}