package ie.cp.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import ie.cp.R;
import ie.cp.fragments.CarParkFragment;
import ie.cp.models.CarPark;
import ie.cp.models.CarParkSpace;
import ie.cp.models.User;

public class Home extends Base
        implements NavigationView.OnNavigationItemSelectedListener {

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

        CarParkFragment fragment = CarParkFragment.newInstance();
        ft.replace(R.id.homeFrame, fragment);
        ft.commit();

        this.setupCarParks();
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

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setupCarParks(){
        app.dbManager.addCarPark(new CarPark("MultiStory 1", "Newgate Street","Waterford",20,20));
        app.dbManager.addCarPark(new CarPark("MultiStory 2", "City Square","Waterford",20,20));
        app.dbManager.addCarPark(new CarPark("MultiStory 3", "The Quay","New Ross",20,20));

    }
    public void setupUsers(){
        app.dbManager.addUser(new User("David Lanigan", "daveylanigan@gmail.com","password"));
        app.dbManager.addUser(new User("Conor Lanigan", "clanigan@gmail.com","password"));
    }

    public void setupCarParkSpaces(){
        app.dbManager.addCarParkSpace(new CarParkSpace("Space 1", "Space 1","1"));
        app.dbManager.addCarParkSpace(new CarParkSpace("Space 2", "Space 2","1"));
        app.dbManager.addCarParkSpace(new CarParkSpace("Space 3", "Space 3","1"));
        app.dbManager.addCarParkSpace(new CarParkSpace("Space 4", "Space 4","1"));

    }



}
