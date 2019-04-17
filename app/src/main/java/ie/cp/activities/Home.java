package ie.cp.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.util.Arrays;

import ie.cp.R;
import ie.cp.api.CarParkApi;
import ie.cp.fragments.AddCarParkFragment;
import ie.cp.fragments.AddCarParkSpaceFragment;
import ie.cp.fragments.AddReservationFragment;
import ie.cp.fragments.CarParkFragment;
import ie.cp.fragments.EditCarParkFragment;
import ie.cp.fragments.ReservationFragment;
import ie.cp.fragments.UserFragment;
import ie.cp.main.CarParkApp;
import ie.cp.models.CarPark;
import ie.cp.models.CarParkSpace;
import ie.cp.models.User;
import io.realm.OrderedRealmCollection;
import io.realm.RealmResults;

public class Home extends AppCompatActivity
implements NavigationView.OnNavigationItemSelectedListener,
        EditCarParkFragment.OnFragmentInteractionListener {

    FragmentTransaction ft;
    public static CarParkApp app = CarParkApp.getInstance();
    public AlertDialog loader;
    private ImageView googlePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //SetUp GooglePhoto and Email for Drawer here
        googlePhoto = navigationView.getHeaderView(0).findViewById(R.id.googlephoto);
 //       CarParkApi.getGooglePhoto(app.googlePhotoURL,googlePhoto);
        googlePhoto.setImageBitmap(app.googlePhoto);

        TextView googleName = navigationView.getHeaderView(0).findViewById(R.id.googlename);
        googleName.setText(app.googleName);

        TextView googleMail = navigationView.getHeaderView(0).findViewById(R.id.googlemail);
        googleMail.setText(app.googleMail);

        ft = getSupportFragmentManager().beginTransaction();

        UserFragment fragment = UserFragment.newInstance();
        ft.replace(R.id.homeFrame, fragment);
        ft.commit();

        this.setupCarParks();
        this.setupCarParkSpaces();
        this.setTitle(R.string.recentlyViewedLbl);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment;
        ft = getSupportFragmentManager().beginTransaction();

        if (id == R.id.nav_home) {
            fragment = UserFragment.newInstance();
            ft.replace(R.id.homeFrame, fragment);
            ft.addToBackStack(null);
            ft.commit();

        } else if (id == R.id.nav_logout) {
            this.menuSignOut(null);

        } else if (id == R.id.nav_carpark_add) {
            fragment = AddCarParkFragment.newInstance();
            ft.replace(R.id.homeFrame, fragment);
            ft.addToBackStack(null);
            ft.commit();

        } else if (id == R.id.nav_space_add) {
            fragment = AddCarParkSpaceFragment.newInstance();
            ft.replace(R.id.homeFrame, fragment);
            ft.addToBackStack(null);
            ft.commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setupCarParks(){
      //      RealmResults res;
       //     res = app.dbManager.getCarPark("MultiStory 1");
       //     if (res.size() == 0){
       //         app.dbManager.addCarPark(new CarPark("MultiStory 1", "Newgate Street", "Waterford", "4", "4"));
       //     }
        //    res = app.dbManager.getCarPark("MultiStory 2");
        //    if (res.size() == 0){
         //       app.dbManager.addCarPark(new CarPark("MultiStory 2", "City Square","Waterford","4","4"));
        //    }
        //    res = app.dbManager.getCarPark("MultiStory 3");
        //    if (res.size() == 0){
        //        app.dbManager.addCarPark(new CarPark("MultiStory 3", "The Quay","New Ross","4","4"));
        //    }

    }

    public void setupCarParkSpaces(){
        OrderedRealmCollection<CarPark> carParks = app.dbManager.getAllCarParks();
        RealmResults res;
        // add 4 spaces for each carpark
        for(CarPark cp : carParks) {
            if (cp.carParkName.equalsIgnoreCase("MultiStory 1") ) {
                res = app.dbManager.getCarParkSpace("Space 1");
                if (res.size() == 0) {
                    app.dbManager.addCarParkSpace(new CarParkSpace("Space 1", "Space 1", cp.carParkName, false));
                }
                res = app.dbManager.getCarParkSpace("Space 2");
                if (res.size() == 0) {
                    app.dbManager.addCarParkSpace(new CarParkSpace("Space 2", "Space 2", cp.carParkName, false));
                }
                res = app.dbManager.getCarParkSpace("Space 3");
                if (res.size() == 0) {
                    app.dbManager.addCarParkSpace(new CarParkSpace("Space 3", "Space 3", cp.carParkName, false));
                }
                res = app.dbManager.getCarParkSpace("Space 4");
                if (res.size() == 0) {
                    app.dbManager.addCarParkSpace(new CarParkSpace("Space 4", "Space 4", cp.carParkName, false));
                }
           }
            else if (cp.carParkName.equalsIgnoreCase("MultiStory 2") ) {
                res = app.dbManager.getCarParkSpace("Space 5");
                if (res.size() == 0) {
                    app.dbManager.addCarParkSpace(new CarParkSpace("Space 5", "Space 5", cp.carParkName, false));
                }
                res = app.dbManager.getCarParkSpace("Space 6");
                if (res.size() == 0) {
                    app.dbManager.addCarParkSpace(new CarParkSpace("Space 6", "Space 6", cp.carParkName, false));
                }
                res = app.dbManager.getCarParkSpace("Space 7");
                if (res.size() == 0) {
                    app.dbManager.addCarParkSpace(new CarParkSpace("Space 7", "Space 7", cp.carParkName, false));
                }
                res = app.dbManager.getCarParkSpace("Space 8");
                if (res.size() == 0) {
                    app.dbManager.addCarParkSpace(new CarParkSpace("Space 8", "Space 8", cp.carParkName, false));
                }
            }
            else if (cp.carParkName.equalsIgnoreCase("MultiStory 3") ) {
                res = app.dbManager.getCarParkSpace("Space 9");
                if (res.size() == 0) {
                    app.dbManager.addCarParkSpace(new CarParkSpace("Space 9", "Space 9", cp.carParkName, false));
                }
                res = app.dbManager.getCarParkSpace("Space 10");
                if (res.size() == 0) {
                    app.dbManager.addCarParkSpace(new CarParkSpace("Space 10", "Space 10", cp.carParkName, false));
                }
                res = app.dbManager.getCarParkSpace("Space 11");
                if (res.size() == 0) {
                    app.dbManager.addCarParkSpace(new CarParkSpace("Space 11", "Space 11", cp.carParkName, false));
                }
                res = app.dbManager.getCarParkSpace("Space 12");
                if (res.size() == 0) {
                    app.dbManager.addCarParkSpace(new CarParkSpace("Space 12", "Space 12", cp.carParkName, false));
                }
            }
        } // end for

        OrderedRealmCollection<CarParkSpace> carParkSpaces = app.dbManager.getAllCarParkSpaces();

        Log.d("car park spaces", "arr: " + Arrays.toString(new OrderedRealmCollection[]{carParkSpaces}));


    }


    @Override
    public void toggle(View v) {
        EditCarParkFragment editFrag = (EditCarParkFragment)
                getSupportFragmentManager().findFragmentById(R.id.homeFrame);
        if (editFrag != null) {
            editFrag.toggle(v);
        }
    }

    @Override
    public void saveCarPark(View v) {
        EditCarParkFragment editFrag = (EditCarParkFragment)
                getSupportFragmentManager().findFragmentById(R.id.homeFrame);
        if (editFrag != null) {
            editFrag.saveCarPark(v);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    public void menuHome(MenuItem m) {
        startActivity(new Intent(this, Home.class));
    }

    public void menuInfo(MenuItem m) {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.app_about))
                .setMessage(getString(R.string.app_desc)
                        + "\n\n"
                        + getString(R.string.app_more_info))
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // we could put some code here too
                    }
                })
                .show();
    }

    public void createLoader() {
        AlertDialog.Builder loaderBuilder = new AlertDialog.Builder(this);
        loaderBuilder.setCancelable(true); // 'false' if you want user to wait
        loaderBuilder.setView(R.layout.loading);
        loader = loaderBuilder.create();
        loader.setTitle(R.string.app_name);
        //loader.setMessage("Downloading CarParks...");
        loader.setIcon(R.drawable.favourites_72);
    }

    // [START signOut]
    public void menuSignOut(MenuItem m) {

        //https://stackoverflow.com/questions/38039320/googleapiclient-is-not-connected-yet-on-logout-when-using-firebase-auth-with-g
        app.mGoogleApiClient.connect();
        app.mGoogleApiClient.registerConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
            @Override
            public void onConnected(@Nullable Bundle bundle) {

                //FirebaseAuth.getInstance().signOut();
                if(app.mGoogleApiClient.isConnected()) {
                    Auth.GoogleSignInApi.signOut(app.mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
                        @Override
                        public void onResult(@NonNull Status status) {
                            if (status.isSuccess()) {
                                Intent intent = new Intent(Home.this, Login.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
                }
            }

            @Override
            public void onConnectionSuspended(int i) {
                Toast.makeText(Home.this, "Google API Client Connection Suspended",Toast.LENGTH_SHORT).show();
            }
        });
    }
    // [END signOut]

}
