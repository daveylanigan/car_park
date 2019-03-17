package ie.cp.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.Arrays;

import ie.cp.R;
import ie.cp.fragments.AddCarParkFragment;
import ie.cp.fragments.AddCarParkSpaceFragment;
import ie.cp.fragments.CarParkFragment;
import ie.cp.fragments.CarParkSpaceFragment;
import ie.cp.fragments.EditCarParkFragment;
import ie.cp.models.CarPark;
import ie.cp.models.CarParkSpace;
import ie.cp.models.User;
import io.realm.OrderedRealmCollection;

public class Home extends Base
        implements NavigationView.OnNavigationItemSelectedListener,
        EditCarParkFragment.OnFragmentInteractionListener {

    FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Information", Snackbar.LENGTH_LONG)
                        .setAction("More Info...", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        }).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ft = getSupportFragmentManager().beginTransaction();

   //     CarParkFragment fragment = CarParkFragment.newInstance();
   //     ft.replace(R.id.homeFrame, fragment);
   //     ft.commit();
        CarParkSpaceFragment fragment = CarParkSpaceFragment.newInstance();
        ft.replace(R.id.homeFrame, fragment);
        ft.commit();

 //       this.setupCarParks();
 //       this.setupCarParkSpaces();
 //       this.setTitle(R.string.recentlyViewedLbl);
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
            fragment = CarParkFragment.newInstance();
            ft.replace(R.id.homeFrame, fragment);
            ft.addToBackStack(null);
            ft.commit();

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

            //     } else if (id == R.id.nav_camera) {
            // Handle the camera action
   //     } else if (id == R.id.nav_gallery) {

   //     } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setupCarParks(){
            app.dbManager.addCarPark(new CarPark("MultiStory 1", "Newgate Street", "Waterford", "4", "4"));
            app.dbManager.addCarPark(new CarPark("MultiStory 2", "City Square","Waterford","4","4"));
            app.dbManager.addCarPark(new CarPark("MultiStory 3", "The Quay","New Ross","4","4"));

    }
    public void setupUsers(){
        app.dbManager.addUser(new User("David Lanigan", "daveylanigan@gmail.com","password"));
        app.dbManager.addUser(new User("Conor Lanigan", "clanigan@gmail.com","password"));
    }

    public void setupCarParkSpaces(){

        OrderedRealmCollection<CarPark> carParks = app.dbManager.getAllCarParks();
        Log.d("car parks", "arr: " + Arrays.toString(new OrderedRealmCollection[]{carParks}));

        // add 4 spaces for each carpark
        for(CarPark cp : carParks) {
            if (cp.name.equalsIgnoreCase("MultiStory 1") ) {
                app.dbManager.addCarParkSpace(new CarParkSpace("Space 1", "Space 1", cp.carParkId, false));
                app.dbManager.addCarParkSpace(new CarParkSpace("Space 2", "Space 2", cp.carParkId, false));
                app.dbManager.addCarParkSpace(new CarParkSpace("Space 3", "Space 3", cp.carParkId, false));
                app.dbManager.addCarParkSpace(new CarParkSpace("Space 4", "Space 4", cp.carParkId, false));
            }
            if (cp.name.equalsIgnoreCase("MultiStory 2") ) {
                app.dbManager.addCarParkSpace(new CarParkSpace("Space 5", "Space 5", cp.carParkId, false));
                app.dbManager.addCarParkSpace(new CarParkSpace("Space 6", "Space 6", cp.carParkId, false));
                app.dbManager.addCarParkSpace(new CarParkSpace("Space 7", "Space 7", cp.carParkId, false));
                app.dbManager.addCarParkSpace(new CarParkSpace("Space 8", "Space 8", cp.carParkId, false));
            }
            if (cp.name.equalsIgnoreCase("MultiStory 3") ) {
                app.dbManager.addCarParkSpace(new CarParkSpace("Space 9", "Space 9", cp.carParkId, false));
                app.dbManager.addCarParkSpace(new CarParkSpace("Space 10", "Space 10", cp.carParkId, false));
                app.dbManager.addCarParkSpace(new CarParkSpace("Space 11", "Space 11", cp.carParkId, false));
                app.dbManager.addCarParkSpace(new CarParkSpace("Space 12", "Space 12", cp.carParkId, false));
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

}
