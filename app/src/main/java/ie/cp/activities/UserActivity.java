package ie.cp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import ie.cp.R;
import ie.cp.fragments.AddCarParkFragment;
import ie.cp.main.CarParkApp;

public class UserActivity extends AppCompatActivity {

    FragmentTransaction ft;
    public static CarParkApp app = CarParkApp.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imgAddCarPark = (ImageView) findViewById(R.id.img_add_car_park);
        imgAddCarPark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddCarParkScreen();
            }
        });
    }


    private void showAddCarParkScreen() {

  //      ft = getSupportFragmentManager().beginTransaction();
  //      AddCarParkFragment fragment = AddCarParkFragment.newInstance();
  //      ft.replace(R.id.homeFrame, fragment);
  //      ft.addToBackStack(null);
  //      ft.commit();
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);

    }





}
