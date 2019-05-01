package ie.cp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ie.cp.R;
import ie.cp.activities.Home;
import ie.cp.api.CarParkApi;
import ie.cp.api.VolleyListener;
import ie.cp.main.CarParkApp;
import ie.cp.models.CarPark;
import ie.cp.models.CarParkSpace;
import ie.cp.models.Reservation;
import io.realm.OrderedRealmCollection;

public class UserFragment extends Fragment implements
        View.OnClickListener,
        VolleyListener
{
    private CarParkApp app;
    private Button carParkBtn;
    private Button makeReservationBtn;
    private Button viewReservationBtn;
    public Home activity;

    public UserFragment() {
        // Required empty public constructor
    }

   public static UserFragment newInstance() {
        UserFragment fragment = new UserFragment();
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
        View v =  inflater.inflate(R.layout.fragment_user, container, false);

        TextView userGoogleName = v.findViewById(R.id.userGoogleName);
        userGoogleName.setText(app.googleName);

        TextView userGoogleMail = v.findViewById(R.id.userGoogleMail);
        userGoogleMail.setText(app.googleMail);

        ImageView img = v.findViewById(R.id.profile);
        //SetUp GooglePhoto
        CarParkApi.getGooglePhoto(app.googlePhotoURL,img);


        CarParkApi.getReservations("/reservation/" + app.googleMail);
    //    OrderedRealmCollection<Reservation> reservations;

    //    reservations = app.dbManager.getReservationsByUser(app.googleMail);
    //    if (reservations.size() > 0) {
     //       app.spacesBooked = Integer.toString(reservations.size());
     //   } else {
     //       app.spacesBooked = Integer.toString(0);
     //   }



        TextView userSpacesBooked = v.findViewById(R.id.userSpacesBooked);

        userSpacesBooked.setText(app.spacesBooked);

        carParkBtn = v.findViewById(R.id.userCarParkButton);
        carParkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CarParkFragment nextFrag = CarParkFragment.newInstance();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.homeFrame, nextFrag)
                        .addToBackStack(null)
                        .commit();
            }
        });

        makeReservationBtn = v.findViewById(R.id.userBookButton);
        makeReservationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AddReservationFragment nextFrag = AddReservationFragment.newInstance();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.homeFrame, nextFrag)
                        .addToBackStack(null)
                        .commit();
            }
        });

        viewReservationBtn = v.findViewById(R.id.userReservationButton);
        viewReservationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ReservationFragment nextFrag = ReservationFragment.newInstance();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.homeFrame, nextFrag)
                        .addToBackStack(null)
                        .commit();
            }
        });

        return v;
    }

    @Override
    public void onStart()
    {
        super.onStart();
    }


    @Override
    public void onClick(View view)
    {
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

 //   public void setListView(View view)
 //   {
   //     listView.setAdapter (listAdapter);
   //     listView.setOnItemClickListener(this);
   //     listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
   //     listView.setMultiChoiceModeListener(this);
   //     listView.setEmptyView(view.findViewById(R.id.emptyList));
 //   }

    @Override
    public void setList(List list) {
        app.carparkList = list;
    }

    @Override
    public void setSpaceList(List list) {

    }

    @Override
    public void setReservationList(List list) {
        app.reservationList = list;
        if (list.size() > 0) {
           app.spacesBooked = Integer.toString(list.size());
        } else {
           app.spacesBooked = Integer.toString(0);
        }
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
        TextView userSpacesBooked = v.findViewById(R.id.userSpacesBooked);
        userSpacesBooked.setText(app.spacesBooked);
        fragment.onResume();

        //      checkSwipeRefresh(v);
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

    public void setSwipeRefresh(View v)
    {
   //     SwipeRefreshLayout swipeRefresh = v.findViewById(R.id.swiperefresh);
    //    swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
   //         @Override
    //        public void onRefresh() {
   //             CarParkApi.get("/coffees/" + app.googleToken);
   //         }
   //     });
    }

    public void checkSwipeRefresh(View v)
    {
   //     SwipeRefreshLayout swipeRefresh = v.findViewById(R.id.swiperefresh);
   //     if (swipeRefresh.isRefreshing()) swipeRefresh.setRefreshing(false);
    }


}
