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

import ie.cp.R;
import ie.cp.activities.Base;
import ie.cp.adapters.CarParkListAdapter;
import ie.cp.models.CarPark;

public class CarParkFragment   extends Fragment implements
        AdapterView.OnItemClickListener,
        View.OnClickListener,
        AbsListView.MultiChoiceModeListener
{
    public Base activity;
    public static CarParkListAdapter listAdapter;
    public ListView listView;
    public CarParkFilter carParkFilter;
 //   public boolean favourites = false;

    public CarParkFragment() {
        // Required empty public constructor
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Bundle activityInfo = new Bundle(); // Creates a new Bundle object
        activityInfo.putString("carParkId", (String) view.getTag());

 //       Fragment fragment = EditFragment.newInstance(activityInfo);
 //       getActivity().setTitle(R.string.editCoffeeLbl);
//
 //       getActivity().getSupportFragmentManager().beginTransaction()
 //               .replace(R.id.homeFrame, fragment)
 //               .addToBackStack(null)
 //               .commit();
    }


    public static CarParkFragment newInstance() {
        CarParkFragment fragment = new CarParkFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.activity = (Base) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, parent, false);

        listAdapter = new CarParkListAdapter(activity, this, activity.app.dbManager.getAllCarParks());
        carParkFilter = new CarParkFilter(activity.app.dbManager, listAdapter);

 //       if (favourites) {
 //           coffeeFilter.setFilter("favourites"); // Set the filter text field from 'all' to 'favourites'
 //           coffeeFilter.filter(null); // Filter the data, but don't use any prefix
 //           listAdapter.notifyDataSetChanged(); // Update the adapter
  //      }

        listView = v.findViewById(R.id.homeList);
        setListView(v);

//        if (!favourites)
            getActivity().setTitle(R.string.recentlyViewedLbl);
//        else
//            getActivity().setTitle(R.string.favouritesCoffeeLbl);

        return v;
    }

    public void setListView(View view)
    {
        listView.setAdapter (listAdapter);
        listView.setOnItemClickListener(this);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(this);
        listView.setEmptyView(view.findViewById(R.id.emptyList));
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
            onCarParkDelete ((CarPark) view.getTag());
        }
    }

    public void onCarParkDelete(final CarPark carPark)
    {
        String stringName = carPark.name;
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("Are you sure you want to Delete the \'Coffee\' " + stringName + "?");
        builder.setCancelable(false);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                activity.app.dbManager.deleteCarPark(carPark.carParkId); // remove from our list
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

 //       if (favourites) {
            //Update the filters data
 //           coffeeFilter = new CoffeeFilter(activity.app.dbManager,listAdapter);
 //           coffeeFilter.setFilter("favourites");
 //           coffeeFilter.filter(null);
 //       }
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode)
    {}

    @Override
    public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {
    }

    /* ************ MultiChoiceModeListener methods (end) *********** */
}
