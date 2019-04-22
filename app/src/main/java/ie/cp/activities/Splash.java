package ie.cp.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.ImageDecoder;
import android.graphics.drawable.AnimatedImageDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import ie.cp.R;

public class Splash extends Activity {
    // used to know if the back button was pressed in the splash screen activity
    // and avoid opening the next activity
    private boolean 			mIsBackButtonPressed;
    private static final int 	SPLASH_DURATION = 3000; // 3 seconds

    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        ImageView im = findViewById(R.id.imageView);

        try {
            AnimatedImageDrawable animation;
            animation = (AnimatedImageDrawable) ImageDecoder.decodeDrawable(
                    ImageDecoder.createSource(getResources(), R.drawable.cpark));
            im.setImageDrawable(animation);
            animation.start();
        }
        catch(Exception e)
        {

        }
        Handler handler = new Handler();
        // run a thread after 3 seconds to start the home screen
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                // make sure we close the splash screen so the user
                // won't come back to it if back key pressed
                finish();

                if (!mIsBackButtonPressed) {
                    // start the home screen if the back button wasn't pressed already
                //    Intent intent = new Intent(Splash.this, Home.class);
                    Intent intent = new Intent(Splash.this, Login.class);

                    Splash.this.startActivity(intent);
                }
            }
        }, SPLASH_DURATION); // time in milliseconds to delay call to run()

    }

    @Override
    public void onBackPressed() {
        // set the flag to true so the next activity won't start up
        mIsBackButtonPressed = true;
        super.onBackPressed();
    }


}
