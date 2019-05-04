package ie.cp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;

import ie.cp.R;

public class CarParkSearchFragment extends CarParkFragment
        implements AdapterView.OnItemSelectedListener {

    String selected;
    SearchView searchView;

    public CarParkSearchFragment() {
        // Required empty public constructor
    }

    public static CarParkSearchFragment newInstance() {
        CarParkSearchFragment fragment = new CarParkSearchFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        getActivity().setTitle(R.string.searchCarParksLbl);
        View v = inflater.inflate(R.layout.fragment_car_park_search, container, false);
        listView = v.findViewById(R.id.searchList); //Bind to the list on our Search layout
        setListView(v);
      //  ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter
     //           .createFromResource(getActivity(), R.array.carParkTypes,
     //                   android.R.layout.simple_spinner_item);

     //   spinnerAdapter
     ///           .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

     //   Spinner spinner = v.findViewById(R.id.searchSpinner);
     //   spinner.setAdapter(spinnerAdapter);
      //  spinner.setOnItemSelectedListener(this);

        searchView = v.findViewById(R.id.searchView);
        searchView.setQueryHint("Search your Car Parks Here");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
      //          carParkFilter.filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
       //         carParkFilter.filter(newText);
                return false;
            }
        });

        return v;
    }

    @Override
    public void onAttach(Context c) { super.onAttach(c); }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void checkSelected(String selected)
    {
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selected = parent.getItemAtPosition(position).toString();
        checkSelected(selected);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) { }

    @Override
    public void deleteCarParks(ActionMode actionMode) {
        super.deleteCarParks(actionMode);
        checkSelected(selected);
    }

}

