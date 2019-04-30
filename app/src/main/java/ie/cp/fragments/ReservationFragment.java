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
import ie.cp.adapters.ReservationListAdapter;
import ie.cp.api.CarParkApi;
import ie.cp.api.VolleyListener;
import ie.cp.main.CarParkApp;
import ie.cp.models.CarPark;
import ie.cp.models.CarParkSpace;
import ie.cp.models.Reservation;
import io.realm.RealmResults;

public class ReservationFragment extends Fragment implements
        AdapterView.OnItemClickListener,
        View.OnClickListener,
        AbsListView.MultiChoiceModeListener,
        VolleyListener
{
    public Home activity;
    public static ReservationListAdapter listAdapter;
    public ListView listView;
    private CarParkApp app;
    public View v;

    public ReservationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
  //      Bundle activityInfo = new Bundle(); // Creates a new Bundle object
  //      activityInfo.putString("reservationId", (String) view.getTag());
//
  //      Fragment fragment = EditReservationFragment.newInstance(activityInfo);
   //     getActivity().setTitle(R.string.editCarParkSpaceLbl);

   //     getActivity().getSupportFragmentManager().beginTransaction()
   //             .replace(R.id.homeFrame, fragment)
   //             .addToBackStack(null)
   //             .commit();
    }


    public static ReservationFragment newInstance() {
        ReservationFragment fragment = new ReservationFragment();
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
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        app = (CarParkApp) getActivity().getApplication();

     //app.db   RealmResults<Reservation> realmResults = activity.app.dbManager.getAllReservations(app.googleMail);

    //app.db    List<Reservation> reservations = activity.app.dbManager.realmDatabase.copyFromRealm(realmResults);
    //app.db    setList(reservations);


           CarParkApi.getReservations("/reservation/" + app.googleMail);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, parent, false);

        // create our dropdown list of carparks
  //app.db      listAdapter = new ReservationListAdapter(activity, this, app.dbManager.getAllReservations(app.googleMail));

        listView = v.findViewById(R.id.homeList);
        updateView();

  //app.db      setListView(v);

  //app.db      getActivity().setTitle(R.string.reservationsLbl);

        return v;
    }

    public void setListView(View view)
    {
        listView.setAdapter (listAdapter);
        listView.setOnItemClickListener(this);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(this);
      //  listView.setEmptyView(view.findViewById(R.id.emptyList));
    }

    @Override
    public void onStart()
    {
        super.onStart();
    }

    @Override
    public void onClick(View view)
    {
        if (view.getTag() instanceof Reservation)
        {
            onReservationDelete ((Reservation) view.getTag());
        }
    }

    public void onReservationDelete(final Reservation reservation)
    {
        String stringName = reservation.carParkSpaceId;
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("Are you sure you want to Delete the \'Reservation\' for " + stringName + "?");
        builder.setCancelable(false);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
              //app.db  app.dbManager.deleteReservation(reservation.reservationId);
                CarParkApi.deleteReservation("/reservation/" + reservation.reservationId);
                // remove from our list
                listAdapter.notifyDataSetChanged(); // refresh adapter
                // update the spacesbooked amount
                int count = Integer.parseInt(app.spacesBooked);
                if (count > 0) count = count - 1;
                app.spacesBooked = Integer.toString(count);
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
        inflater.inflate(R.menu.delete_reservation_context, menu);
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
            case R.id.menu_item_delete_reservations:
                deleteReservation(actionMode);
                return true;
            default:
                return false;
        }
    }

    public void deleteReservation(ActionMode actionMode)
    {
        Reservation c = null;
        for (int i = listAdapter.getCount() - 1; i >= 0; i--)
            if (listView.isItemChecked(i))
                app.dbManager.deleteReservation(listAdapter.getItem(i).reservationId); //delete from DB

        actionMode.finish();

        listAdapter.notifyDataSetChanged();
        // update the spacesbooked amount
        int count = Integer.parseInt(app.spacesBooked);
        if (count > 0) count = count - 1;
        app.spacesBooked = Integer.toString(count);

    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode)
    {}

    @Override
    public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {
    }

    /* ************ MultiChoiceModeListener methods (end) *********** */

    public void updateView() {
        listAdapter = new ReservationListAdapter(activity, this, activity.app.reservationList);

        setListView(v);

        getActivity().setTitle(R.string.reservationsLbl);
        listAdapter.notifyDataSetChanged(); // Update the adapter
    }

    @Override
    public void setList(List list) {
        activity.app.reservationList = list;
    }

    @Override
    public void setSpaceList(List list) {

    }

    @Override
    public void setReservationList(List list) {
        activity.app.reservationList = list;
    }

    @Override
    public void setReservation(Reservation reservation) {
    }

    @Override
    public void setCarPark(CarPark carpark) {
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

    public void onResume() {
        super.onResume();
        CarParkApi.attachListener(this);
        updateView();
    }

}
