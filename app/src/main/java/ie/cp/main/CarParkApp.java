package ie.cp.main;

import android.app.Application;
import android.graphics.Bitmap;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

import ie.cp.db.DBManager;
import ie.cp.models.CarPark;

public class CarParkApp extends Application {

    private RequestQueue mRequestQueue;
    public List<CarPark> carparkList = new ArrayList<CarPark>();
    public static final String TAG = CarParkApp.class.getName();

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
        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
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

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    public <T> void add(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancel() {
        mRequestQueue.cancelAll(TAG);
    }


}


