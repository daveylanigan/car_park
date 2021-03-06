package ie.cp.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import ie.cp.R;
import ie.cp.activities.Home;
import ie.cp.api.CarParkApi;
import ie.cp.api.VolleyListener;
import ie.cp.main.CarParkApp;
import ie.cp.models.CarPark;
import ie.cp.models.CarParkSpace;
import ie.cp.models.Reservation;
import io.realm.RealmResults;

public class AddReservationFragment extends Fragment  implements VolleyListener {

    private CarParkSpace reservationCarParkSpace;
    private CarPark reservationCarPark;
    private boolean carParkSpaceBooked;
    private Spinner carpark;
    private Spinner carParkSpace;
    private CheckBox booked;
    private Button saveButton;
    private CarParkApp app;
    public Home activity;

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

    public AddReservationFragment() {
        // Required empty public constructor
    }

    public static AddReservationFragment newInstance() {
        AddReservationFragment fragment = new AddReservationFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        app = (CarParkApp) getActivity().getApplication();
        CarParkApi.getCarParks("/carpark");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_reservation, container, false);
        getActivity().setTitle(R.string.addReservationBtnLbl);

        return v;
    }

    public void addReservation() {

        reservationCarParkSpace = ((CarParkSpace) carParkSpace.getSelectedItem());
        reservationCarPark = ((CarPark) carpark.getSelectedItem());

        // reserve the space
        Reservation r = new Reservation("",app.googleMail,reservationCarPark.carParkName, reservationCarParkSpace.carParkSpaceName );
        // add the reservation
        CarParkApi.putReservation("/reservation",r);
        // update the spacesbooked amount
        int count = Integer.parseInt(app.spacesBooked);
        count = count + 1;
        app.spacesBooked = Integer.toString(count);

        //startActivity(new Intent(this.getActivity(), Home.class));
        ReservationFragment nextFrag = ReservationFragment.newInstance();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.homeFrame, nextFrag)
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void setList(List list) {
        app.carparkList = list;

    }

    @Override
    public void setSpaceList(List list) {
        app.carparkspaceList = list;
    }

    @Override
    public void setReservationList(List list) {
        app.reservationList = list;

    }

    @Override
    public void setCarPark(CarPark carpark) {

    }

    @Override
    public void setReservation(Reservation reservation) {

    }

    @Override
    public void updateUI(Fragment fragment) {
        View v = fragment.getView();
        // create our dropdown list of carparks
        ArrayAdapter<CarPark> adapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_list_item_1, app.carparkList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final Spinner spinner = v.findViewById(R.id.reservationCarParkSpinner);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selectedCarPark = spinner.getSelectedItem().toString();

   //             //**** need to call carpark api here for selected car park///
                CarParkApi.getCarParkSpaces("/carparkspace/" + selectedCarPark + "/false");

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        carpark =  v.findViewById(R.id.reservationCarParkSpinner);
        carParkSpace =  v.findViewById(R.id.reservationCarParkSpaceSpinner);
        saveButton = v.findViewById(R.id.addReservationBtn);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addReservation();
            }
        });

    }

    @Override
    public void updateCarParkSpaceDropdown(Fragment fragment) {
        View v = fragment.getView();
        final Spinner spinner2 = v.findViewById(R.id.reservationCarParkSpaceSpinner);

        //**** need to call carpark api here for selected car park///
        ArrayAdapter<CarParkSpace> adapter2 = new ArrayAdapter<>(v.getContext(), android.R.layout.simple_list_item_1, activity.app.carparkspaceList);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner2.setAdapter(adapter2);

    }

    @Override
    public void setValidStatus(int result) {

    }

    @Override
    public void setCarParkSpace(CarParkSpace carParkSpace) {

    }

}