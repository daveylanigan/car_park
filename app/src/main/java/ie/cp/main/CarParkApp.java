package ie.cp.main;

import android.app.Application;
import android.util.Log;

import ie.cp.db.DBManager;

public class CarParkApp extends Application {

    public DBManager dbManager;

    @Override
    public void onCreate()
    {
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
}
