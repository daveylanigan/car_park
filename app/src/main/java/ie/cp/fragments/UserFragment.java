package ie.cp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import ie.cp.R;
import ie.cp.activities.Home;
import ie.cp.api.CarParkApi;
import ie.cp.api.VolleyListener;
import ie.cp.main.CarParkApp;
import ie.cp.models.CarPark;

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
        img.setImageBitmap(app.googlePhoto);

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


    /* ************ MultiChoiceModeListener methods (begin) *********** */
  //  @Override
  //  public boolean onCreateActionMode(ActionMode actionMode, Menu menu)
  //  {
  //      MenuInflater inflater = actionMode.getMenuInflater();
  //      inflater.inflate(R.menu.delete_carparkspacelist_context, menu);
  //      return true;
  //  }

  //  @Override
  //  public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
  //      return false;
  //  }

  //  @Override
  //  public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem)
  //  {
      //  switch (menuItem.getItemId())
      //  {
       //     case R.id.menu_item_delete_carparkspace:
       //         deleteCarParkSpace(actionMode);
       //         return true;
       //     default:
       //         return false;
      //  }
   // }

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
    public void setCarPark(CarPark carpark) {
    }

    @Override
    public void updateUI(Fragment fragment) {
        fragment.onResume();
  //      checkSwipeRefresh(v);
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
