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
import android.widget.Toast;

import ie.cp.R;
import ie.cp.activities.Base;

import ie.cp.adapters.CarParkSpaceListAdapter;
import ie.cp.models.CarParkSpace;

public class CarParkSpaceFragment extends Fragment implements
        AdapterView.OnItemClickListener,
        View.OnClickListener,
        AbsListView.MultiChoiceModeListener
{
    public Base activity;
    public static CarParkSpaceListAdapter listAdapter;
    public ListView listView;

    public CarParkSpaceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Bundle activityInfo = new Bundle(); // Creates a new Bundle object
        activityInfo.putString("carParkSpaceId", (String) view.getTag());

        Fragment fragment = EditCarParkFragment.newInstance(activityInfo);
        getActivity().setTitle(R.string.editCarParkSpaceLbl);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.homeFrame, fragment)
                .addToBackStack(null)
                .commit();
    }


    public static CarParkSpaceFragment newInstance() {
        CarParkSpaceFragment fragment = new CarParkSpaceFragment();
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

        listAdapter = new CarParkSpaceListAdapter(activity, this, activity.app.dbManager.getAllCarParkSpaces());
 ////       carParkFilter = new CarParkFilter(activity.app.dbManager, listAdapter);

        listView = v.findViewById(R.id.homeList);
        setListView(v);

        getActivity().setTitle(R.string.carParkSpacesLbl);

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
        if (view.getTag() instanceof CarParkSpace)
        {
            onCarParkSpaceDelete ((CarParkSpace) view.getTag());
        }
    }

    public void onCarParkSpaceDelete(final CarParkSpace carParkSpace)
    {
        if (carParkSpace.booked) return;
        String stringName = carParkSpace.carParkSpaceName;
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("Are you sure you want to Delete the \'Car Park Space\' " + stringName + "?");
        builder.setCancelable(false);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                activity.app.dbManager.deleteCarParkSpace(carParkSpace.carParkSpaceId);
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

    /* ************ MultiChoiceModeListener methods (begin) *********** */
    @Override
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu)
    {
        MenuInflater inflater = actionMode.getMenuInflater();
        inflater.inflate(R.menu.delete_carparkspacelist_context, menu);
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
            case R.id.menu_item_delete_carparkspace:
                deleteCarParkSpace(actionMode);
                return true;
            default:
                return false;
        }
    }

    public void deleteCarParkSpace(ActionMode actionMode)
    {
        CarParkSpace c = null;
        for (int i = listAdapter.getCount() - 1; i >= 0; i--)
            if (listView.isItemChecked(i))
       //         if (activity.app.dbManager.deleteCarParkSpace(listAdapter.getItem(i).carParkSpaceId)) {
       //             actionMode.finish();
        //            listAdapter.notifyDataSetChanged();
        //        } else{
        //            Toast.makeText(this.getActivity(), "This space is already booked. Plaese cancel the booking first ", Toast.LENGTH_SHORT).show();
        //        }
        activity.app.dbManager.deleteCarParkSpace(listAdapter.getItem(i).carParkSpaceId);
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
}
