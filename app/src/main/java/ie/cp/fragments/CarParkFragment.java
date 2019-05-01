package ie.cp.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import ie.cp.R;
import ie.cp.activities.Home;
import ie.cp.adapters.CarParkListAdapter;
import ie.cp.api.CarParkApi;
import ie.cp.api.VolleyListener;
import ie.cp.models.CarPark;
import ie.cp.models.CarParkSpace;
import ie.cp.models.Reservation;
import io.realm.RealmResults;

public class CarParkFragment   extends Fragment implements
        AdapterView.OnItemClickListener,
        View.OnClickListener,
        AbsListView.MultiChoiceModeListener,
        VolleyListener
{
    public Home activity;
    public static CarParkListAdapter listAdapter;
    public ListView listView;
    public CarParkFilter carParkFilter;
    public View v;

    public CarParkFragment() {
        // Required empty public constructor
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Bundle activityInfo = new Bundle(); // Creates a new Bundle object
        activityInfo.putString("carParkId", (String) view.getTag());

        CarPark c = (CarPark) adapterView.getAdapter().getItem(i);
        activityInfo.putString("carParkName", c.carParkName);

        Fragment fragment = EditCarParkFragment.newInstance(activityInfo);
        getActivity().setTitle(R.string.editCarParkLbl);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.homeFrame, fragment)
                .addToBackStack(null)
                .commit();
    }


    public static CarParkFragment newInstance() {
        CarParkFragment fragment = new CarParkFragment();
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
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        CarParkApi.getCarParks("/carpark");

        // create our dropdown list of carparks
    //    RealmResults<CarPark> realmResults = activity.app.dbManager.getAllCarParks();

    //    List<CarPark> carParks = activity.app.dbManager.realmDatabase.copyFromRealm(realmResults);
     //   setList(carParks);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, parent, false);

    //    listAdapter = new CarParkListAdapter(activity, this, activity.app.dbManager.getAllCarParks());
    //    listAdapter = new CarParkListAdapter(activity, this, activity.app.carparkList);

        listView = v.findViewById(R.id.homeList);
        updateView();

  //dbmanager      setListView(v);

    //    getActivity().setTitle(R.string.carParksLbl);

        return v;
    }

    public void setListView(View view)
    {
        listView.setAdapter (listAdapter);
        listView.setOnItemClickListener(this);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(this);
    //    listView.setEmptyView(view.findViewById(R.id.emptyList));
    }
    @Override
    public void setList(List list) {
        activity.app.carparkList = list;
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

    @Override
    public void onStart()
    {
        super.onStart();
    }

    @Override
    public void onClick(View view)
    {
        if (view.getTag() instanceof CarPark)
        {
            if (view.getId() == R.id.rowDeleteImg) {
                onCarParkDelete((CarPark) view.getTag());
            }
            if (view.getId() == R.id.rowSpaceImg) {
                onCarParkSpace((CarPark) view.getTag());
            }
        }
    }

    public void onResume() {
        super.onResume();
        CarParkApi.attachListener(this);
        updateView();
    }
    public void onCarParkDelete(final CarPark carPark)
    {
        String stringName = carPark.carParkName;
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("Are you sure you want to Delete the \'Car Park\' " + stringName + "?");
        builder.setCancelable(false);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
              //app.db  activity.app.dbManager.deleteCarPark(carPark.carParkId); // remove from our list
                   CarParkApi.delete("/carpark/" + carPark.carParkId);
                // remove from our list
                listAdapter.notifyDataSetChanged(); // refresh adapter

            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void onCarParkSpace(final CarPark carPark)
    {
      //  String stringName = carPark.carParkName;
        CarParkSpaceFragment nextFrag = CarParkSpaceFragment.newInstance(carPark.carParkName);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.homeFrame, nextFrag)
                .addToBackStack(null)
                .commit();
    }

    /* ************ MultiChoiceModeListener methods (begin) *********** */
    @Override
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu)
    {
        MenuInflater inflater = actionMode.getMenuInflater();
        inflater.inflate(R.menu.delete_list_context, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem)
    {
        switch (menuItem.getItemId())
        {
            case R.id.menu_item_delete_carpark:
                deleteCarParks(actionMode);
                return true;
            default:
                return false;
        }
    }

    public void deleteCarParks(ActionMode actionMode)
    {
        CarPark c = null;
        for (int i = listAdapter.getCount() - 1; i >= 0; i--)
            if (listView.isItemChecked(i))
                activity.app.dbManager.deleteCarPark(listAdapter.getItem(i).carParkId); //delete from DB

        actionMode.finish();

        listAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode)
    {}

    @Override
    public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {
    }

    /* ************ MultiChoiceModeListener methods (end) *********** */

    public void updateView() {
        listAdapter = new CarParkListAdapter(activity, this, activity.app.carparkList);

        setListView(v);

        getActivity().setTitle(R.string.carParksLbl);

        listAdapter.notifyDataSetChanged(); // Update the adapter
    }

}
