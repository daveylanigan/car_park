package ie.cp.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class AddCarParkSpaceFragment extends Fragment implements VolleyListener {

    private String carParkSpaceName, carParkSpaceDecription, carParkSpaceCarPark;
    private EditText name, description;
    private Spinner carpark;
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


    public AddCarParkSpaceFragment() {
        // Required empty public constructor
    }

    public static AddCarParkSpaceFragment newInstance() {
        AddCarParkSpaceFragment fragment = new AddCarParkSpaceFragment();

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
        View v = inflater.inflate(R.layout.fragment_add_carparkspace, container, false);
        getActivity().setTitle(R.string.addCarParkSpaceBtnLbl);

        // create our dropdown list of carparks
    //app.db    RealmResults<CarPark> realmResults = app.dbManager.getAllCarParks();

    //app.db    List<CarPark> carParks = app.dbManager.realmDatabase.copyFromRealm(realmResults);
  //      CarParkApi.getCarParksForCombo("/carpark");

  //      ArrayAdapter<CarPark> adapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_list_item_1, carParks);
   //     adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

   //     Spinner spinner = v.findViewById(R.id.carParkSpaceSpinner);
   //     spinner.setAdapter(adapter);

   //     name = v.findViewById(R.id.addCarParkSpaceNameET);
    //    description =  v.findViewById(R.id.addCarParkSpaceDescriptionET);
    //    carpark =  v.findViewById(R.id.carParkSpaceSpinner);
    //    saveButton = v.findViewById(R.id.addCarParkSpaceBtn);
    //    saveButton.setOnClickListener(new View.OnClickListener() {
    //        @Override
    //        public void onClick(View view) {
    //            addCarParkSpace();
    //        }
    //    });

        return v;
    }

    public void addCarParkSpace() {

        carParkSpaceName = name.getText().toString();
        carParkSpaceDecription = description.getText().toString();
        carParkSpaceCarPark = carpark.getSelectedItem().toString();

        if ((carParkSpaceName.length() > 0) && (carParkSpaceDecription.length() > 0) && (carParkSpaceCarPark.length() > 0)) {
            CarParkSpace c = new CarParkSpace("",carParkSpaceName, carParkSpaceDecription, carParkSpaceCarPark, false);
            // add the car park space
     //db.app       app.dbManager.addCarParkSpace(c);
            CarParkApi.putCarParkSpace("/carparkspace",c);
            // now we need to update the amount of spaces in the related car park
    //db.app        app.dbManager.updateCarParkTotals(carParkSpaceCarPark, true, false);

          //  CarParkSpaceFragment nextFrag = CarParkSpaceFragment.newInstance(carParkSpaceCarPark);
            UserFragment nextFrag = UserFragment.newInstance();
          //  CarParkFragment nextFrag = CarParkFragment.newInstance();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.homeFrame, nextFrag)
                    .addToBackStack(null)
                    .commit();

            Toast.makeText(
                    this.getActivity(),
                    "Car space " + carParkSpaceName + " added successfully",
                    Toast.LENGTH_SHORT).show();

        } else {

            Toast.makeText(
                    this.getActivity(),
                    "You must Enter Something for "
                            + "\'Name\', \'Description\' and \'Car Park\'",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setList(List list) {
        activity.app.carparkList = list;
    }

    @Override
    public void setSpaceList(List list) {
        activity.app.carparkspaceList = list;
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
        View v = fragment.getView();
        ArrayAdapter<CarPark> adapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_list_item_1, activity.app.carparkList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = v.findViewById(R.id.carParkSpaceSpinner);
        spinner.setAdapter(adapter);

        name = v.findViewById(R.id.addCarParkSpaceNameET);
        description =  v.findViewById(R.id.addCarParkSpaceDescriptionET);
        carpark =  v.findViewById(R.id.carParkSpaceSpinner);
        saveButton = v.findViewById(R.id.addCarParkSpaceBtn);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCarParkSpace();
            }
        });

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
}