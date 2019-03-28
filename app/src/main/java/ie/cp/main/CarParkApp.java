package ie.cp.main;

import android.app.Application;
import android.graphics.Bitmap;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;

import ie.cp.db.DBManager;

public class CarParkApp extends Application {
    /* Client used to interact with Google APIs. */
    public GoogleApiClient mGoogleApiClient;
    public GoogleSignInOptions mGoogleSignInOptions;

    private static CarParkApp mInstance;
    public boolean signedIn = false;
    public String googleToken;
    public String googleName;
    public String googleMail;
    public String googlePhotoURL;
    public Bitmap googlePhoto;
    public DBManager dbManager;

    @Override
    public void onCreate()
    {
        mInstance = this;
        super.onCreate();
        Log.v("CarParkApp", "Car Park App Started");
        dbManager = new DBManager(this);
        dbManager.open();
        Log.v("CarParkApp", "Realm Database Created & Opened");
    }

    @Override
    public void onTerminate()
    {
        super.onTerminate();
        Log.v("CarParkApp", "Realm Database Closed");
        dbManager.close();
    }
    public static synchronized CarParkApp getInstance() {
        return mInstance;
    }

}
