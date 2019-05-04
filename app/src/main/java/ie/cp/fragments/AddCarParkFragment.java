package ie.cp.fragments;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import ie.cp.R;
import ie.cp.activities.Home;
import ie.cp.api.CarParkApi;
import ie.cp.api.VolleyListener;
import ie.cp.main.CarParkApp;
import ie.cp.models.CarPark;
import ie.cp.models.CarParkSpace;
import ie.cp.models.Reservation;

public class AddCarParkFragment extends Fragment implements
        VolleyListener, OnMapReadyCallback {

    private String carParkName, carParkAddress, carParkLocation;
    private EditText name, address, location;
    private Button saveButton;
    private CarParkApp app;
    public Home activity;
    private GoogleMap mMap;

    public AddCarParkFragment() {
        // Required empty public constructor
    }

    public static AddCarParkFragment newInstance() {
        AddCarParkFragment fragment = new AddCarParkFragment();

        return fragment;
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.activity = (Home) context;
        CarParkApi.attachListener(this);
        CarParkApi.attachDialog(activity.loader);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        CarParkApi.detachListener();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        app = (CarParkApp) getActivity().getApplication();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_carpark, container, false);
        getActivity().setTitle(R.string.addCarParkBtnLbl);
        name = v.findViewById(R.id.addCarParkNameET);
     //   address =  v.findViewById(R.id.addCarParkAddressET);
    //    location =  v.findViewById(R.id.addCarParkLocationET);
        saveButton = v.findViewById(R.id.addCarParkBtn);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCarPark();
            }
        });

        return v;
    }


    public void addCarPark() {

        carParkName = name.getText().toString();
        //carParkAddress = address.getText().toString();
        carParkLocation = "";
        carParkAddress = getAddressFromLocation(app.mCurrentLocation);

        if ((carParkName.length() > 0) && (carParkAddress.length() > 0)) {
            //car park id will be added by mongo
            CarPark c = new CarPark("",carParkName, carParkAddress, carParkLocation, "0", "0",app.mCurrentLocation.getLatitude(),app.mCurrentLocation.getLongitude());

        //app.db    app.dbManager.addCarPark(c);
            CarParkApi.putCarPark("/carpark",c);

            CarParkFragment nextFrag = CarParkFragment.newInstance();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.homeFrame, nextFrag)
                    .addToBackStack(null)
                    .commit();

        } else {

            Toast.makeText(
                    this.getActivity(),
                    "You must Enter Something for "
                            + "\'Name\'",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        CarParkApi.getCarParks("/carpark");

    }

    public void addCarParks(List<CarPark> list)
    {
        for(CarPark c : list)
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(c.latitude, c.longitude))
                    .title(c.carParkName + " " + c.address)
                    .snippet(c.spacesAvailable + ": " + "space(s) available")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.mapmarker)));

    }

    @Override
    public void setList(List list) {
        app.carparkList = list;
        mMap.clear();
        addCarParks(app.carparkList);

    }

    @Override
    public void setSpaceList(List list) {

    }

    @Override
    public void setReservationList(List list) {

    }

    @Override
    public void setCarPark(CarPark carpark) {

    }

    @Override
    public void setReservation(Reservation reservation) {

    }

    @Override
    public void updateUI(Fragment fragment) {

    }

    private void resetFields() {
        name.setText("");
        address.setText("");
     //   location.setText("");
        name.requestFocus();
        name.setFocusable(true);
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

    private String getAddressFromLocation( Location location ) {
        Geocoder geocoder = new Geocoder( getActivity() );

        String strAddress = "";
        Address address;
        try {
            address = geocoder
                    .getFromLocation( location.getLatitude(), location.getLongitude(), 1 )
                    .get( 0 );
            strAddress = address.getAddressLine(0) +
                    " " + address.getAddressLine(1) +
                    " " + address.getAddressLine(2);
        }
        catch (IOException e ) {
        }

        return strAddress;
    }


}

