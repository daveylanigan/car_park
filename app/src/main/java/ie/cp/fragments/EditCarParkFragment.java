package ie.cp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ie.cp.R;
import ie.cp.api.CarParkApi;
import ie.cp.api.VolleyListener;
import ie.cp.main.CarParkApp;
import ie.cp.models.CarPark;
import ie.cp.models.CarParkSpace;
import ie.cp.models.Reservation;
import io.realm.RealmResults;

public class EditCarParkFragment extends Fragment   implements VolleyListener {

    public boolean isFavourite;
    public CarPark aCarPark;
    private EditText name, address, location;
    public CarParkApp app;
    public View v;

    private OnFragmentInteractionListener mListener;

    public EditCarParkFragment() {
        // Required empty public constructor
    }

    public static EditCarParkFragment newInstance(Bundle carparkBundle) {
        EditCarParkFragment fragment = new EditCarParkFragment();
        fragment.setArguments(carparkBundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        app = (CarParkApp) getActivity().getApplication();
    //app.db    RealmResults<CarPark> res = null;

        if(getArguments() != null)
            //app.db    res = app.dbManager.getCarPark(getArguments().getString("carParkId"));
            //app.db    if (res.size()>0) {
        //app.db        aCarPark = res.first();
        //app.db    }
            CarParkApi.getCarParks("/carparkbyname/" + getArguments().getString("carParkName"));
       // CarParkApi.getCarParks("/carpark");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_carpark_edit, container, false);

        return v;
    }

    public void saveCarPark(View v) {
        if (mListener != null) {
            String carParkName = name.getText().toString();
            String carParkAddress = address.getText().toString();
        //    String carParkLocation = location.getText().toString();
            String carParkLocation = "";
            CarPark c = new CarPark();

            c.carParkId = aCarPark.carParkId;
            c.carParkName = carParkName;
            c.address = carParkAddress;
            c.location = carParkLocation;
            c.spacesAvailable = aCarPark.spacesAvailable;
            c.totalSpaces = aCarPark.totalSpaces;

            if ((carParkName.length() > 0) && (carParkAddress.length() > 0)) {
                CarParkApi.putCarPark("/carpark/" + aCarPark.carParkId, c);

                CarParkFragment nextFrag = CarParkFragment.newInstance();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.homeFrame, nextFrag)
                        .addToBackStack(null)
                        .commit();
            }
        } else
            Toast.makeText(getActivity(), "You must Enter Something for Name and Address", Toast.LENGTH_SHORT).show();
    }

    public void toggle(View v) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        CarParkApi.attachListener(this);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void setList(List list) {
        app.carparkList = list;
    }

    @Override
    public void setSpaceList(List list) {

    }

    @Override
    public void setReservationList(List list) {

    }

    @Override
    public void setCarPark(CarPark carPark) {

        aCarPark = carPark;
    }

    @Override
    public void setReservation(Reservation reservation) {

    }


    @Override
    public void updateUI(Fragment fragment) {
        View v = fragment.getView();
        ((TextView)v.findViewById(R.id.editCarParkTitleTV)).setText(aCarPark.carParkName);

        name = v.findViewById(R.id.editCarParkNameET);
       // address = v.findViewById(R.id.editCarParkAddressET);
       // location = v.findViewById(R.id.editCarParkLocationET);
        address = v.findViewById(R.id.editCarParkAddressET);
      //  location = "";

        name.setText(aCarPark.carParkName);
     //   location.setText(aCarPark.location);
        address.setText(""+aCarPark.address);
        fragment.onResume();
    }

    @Override
    public void setCarParkSpace(CarParkSpace carParkSpace) {

    }

    @Override
    public void updateCarParkSpaceDropdown(Fragment fragment) {

    }

    @Override
    public void setValidStatus(int result) {

    }


    public interface OnFragmentInteractionListener {
        void toggle(View v);
        void saveCarPark(View v);
    }
}
