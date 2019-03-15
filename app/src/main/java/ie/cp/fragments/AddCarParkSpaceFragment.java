package ie.cp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ie.cp.R;
import ie.cp.activities.Home;
import ie.cp.main.CarParkApp;
import ie.cp.models.CarPark;
import ie.cp.models.CarParkSpace;
import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmResults;

public class AddCarParkSpaceFragment extends Fragment {

    private String carParkSpaceName, carParkSpaceDecription, carParkSpaceCarPark;
    private EditText name, description;
    private Spinner carpark;
    private Button saveButton;
    private CarParkApp app;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_carparkspace, container, false);
        getActivity().setTitle(R.string.addCarParkSpaceBtnLbl);
        // create our dropdown list of carparks

     //   adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

      //  Realm realm = Realm.getDefaultInstance();
        RealmResults<CarPark> realmResults = app.dbManager.getAllCarParks();

        List<CarPark> carParks = app.dbManager.realmDatabase.copyFromRealm(realmResults);

        ArrayAdapter<CarPark> adapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_list_item_1, carParks);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = v.findViewById(R.id.carParkSpaceSpinner);
        spinner.setAdapter(adapter);
    //    spinner.setOnItemSelectedListener(this);

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

        return v;
    }

    public void addCarParkSpace() {

        carParkSpaceName = name.getText().toString();
        carParkSpaceDecription = description.getText().toString();
        carParkSpaceCarPark = carpark.getSelectedItem().toString();

        if ((carParkSpaceName.length() > 0) && (carParkSpaceDecription.length() > 0) && (carParkSpaceCarPark.length() > 0)) {
            CarParkSpace c = new CarParkSpace(carParkSpaceName, carParkSpaceDecription, carParkSpaceCarPark);

            app.dbManager.addCarParkSpace(c);
            startActivity(new Intent(this.getActivity(), Home.class));
        } else {

            Toast.makeText(
                    this.getActivity(),
                    "You must Enter Something for "
                            + "\'Name\', \'Description\' and \'Car Park\'",
                    Toast.LENGTH_SHORT).show();
        }
    }
}