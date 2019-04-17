package ie.cp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ie.cp.R;
import ie.cp.activities.Home;
import ie.cp.api.CarParkApi;
import ie.cp.main.CarParkApp;
import ie.cp.models.CarPark;

public class AddCarParkFragment extends Fragment {

    private String carParkName, carParkAddress, carParkLocation;
    private EditText name, address, location;
    private Button saveButton;
    private CarParkApp app;

    public AddCarParkFragment() {
        // Required empty public constructor
    }

    public static AddCarParkFragment newInstance() {
        AddCarParkFragment fragment = new AddCarParkFragment();

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
        View v = inflater.inflate(R.layout.fragment_add_carpark, container, false);
        getActivity().setTitle(R.string.addCarParkBtnLbl);
        name = v.findViewById(R.id.addCarParkNameET);
        address =  v.findViewById(R.id.addCarParkAddressET);
        location =  v.findViewById(R.id.addCarParkLocationET);
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
        carParkAddress = address.getText().toString();
        carParkLocation = location.getText().toString();

        if ((carParkName.length() > 0) && (carParkAddress.length() > 0) && (carParkLocation.length() > 0)) {
            //car park id will be added by mongo
            CarPark c = new CarPark("",carParkName, carParkAddress, carParkLocation, "0", "0");

            app.dbManager.addCarPark(c);
        //    CarParkApi.putCarPark("/carpark",c);
         //   startActivity(new Intent(this.getActivity(), Home.class));
            CarParkFragment nextFrag = CarParkFragment.newInstance();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.homeFrame, nextFrag)
                    .addToBackStack(null)
                    .commit();

        } else {

            Toast.makeText(
                    this.getActivity(),
                    "You must Enter Something for "
                            + "\'Name\', \'Address\' and \'Location\'",
                    Toast.LENGTH_SHORT).show();
        }
    }
}