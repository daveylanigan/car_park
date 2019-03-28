package ie.cp.fragments;

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
import ie.cp.main.CarParkApp;
import ie.cp.models.CarPark;
import ie.cp.models.CarParkSpace;
import ie.cp.models.Reservation;
import io.realm.RealmResults;

public class AddReservationFragment extends Fragment {

 //   private String carParkSpaceName, carParkSpaceDecription, carParkSpaceCarPark;
    private CarParkSpace reservationCarParkSpace;
    private CarPark reservationCarPark;
    private boolean carParkSpaceBooked;
 //   private EditText name, description;
    private Spinner carpark;
    private Spinner carParkSpace;
    private CheckBox booked;
    private Button saveButton;
    private CarParkApp app;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_reservation, container, false);
        getActivity().setTitle(R.string.addReservationBtnLbl);
        // create our dropdown list of carparks
        RealmResults<CarPark> realmResults = app.dbManager.getAllCarParks();

        List<CarPark> carParks = app.dbManager.realmDatabase.copyFromRealm(realmResults);

        ArrayAdapter<CarPark> adapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_list_item_1, carParks);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final Spinner spinner = v.findViewById(R.id.reservationCarParkSpinner);
        spinner.setAdapter(adapter);

        final Spinner spinner2 = v.findViewById(R.id.reservationCarParkSpaceSpinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String restaurantname = spinner.getSelectedItem().toString();

                RealmResults<CarParkSpace> realmResults2 = app.dbManager.getCarParkSpaces(restaurantname);

                List<CarParkSpace> carParkSpaces = app.dbManager.realmDatabase.copyFromRealm(realmResults2);

                ArrayAdapter<CarParkSpace> adapter2 = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1, carParkSpaces);
                adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spinner2.setAdapter(adapter2);

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

        return v;
    }

    public void addReservation() {

        reservationCarParkSpace = ((CarParkSpace) carParkSpace.getSelectedItem());
        reservationCarPark = ((CarPark) carpark.getSelectedItem());

        // reserve the space
        app.dbManager.ReserveSpace(reservationCarParkSpace);

        Reservation r = new Reservation("David",reservationCarPark.carParkName, reservationCarParkSpace.carParkSpaceName );
        // add the reservation
        app.dbManager.addReservation(r);


        startActivity(new Intent(this.getActivity(), Home.class));
   //     } else {

  //          Toast.makeText(this.getActivity(), "yay ", Toast.LENGTH_SHORT).show();
   //     }
    }
}