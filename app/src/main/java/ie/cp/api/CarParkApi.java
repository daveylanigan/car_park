package ie.cp.api;

import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import ie.cp.activities.Home;
import ie.cp.models.CarPark;

public class CarParkApi {

    private static final String hostURL = "http://coffeemate-nodeserver.herokuapp.com";
    private static final String LocalhostURL = "http://192.168.0.13:3000";
    //private static List<CarPark> result = null;
    private static VolleyListener vListener;

    public static void attachListener(VolleyListener fragment) { vListener = fragment; }
    public static void detachListener() {
        vListener  = null;
    }
    public static int FLAG;

    ///////////////////////////////////////////////////////////////////////////////////////////////
    public static void get(String url) {

        // Request a string response
        JsonObjectRequest gsonRequest = new JsonObjectRequest(Request.Method.GET, hostURL + url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            FLAG = response.getInt("status");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        // Result handling
                        List<CarPark> result = null;
                        Log.v("carpark","CARPARK JSON DATA : " + response);
                        Type collectionType = new TypeToken<List<CarPark>>(){}.getType();

                        try {
                            result = new Gson().fromJson(response.getString("data"), collectionType);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(FLAG == 99)vListener.setList(result); //99 indicates a 'GetAll' on Server
                        vListener.setCarPark(result.get(0));
                        vListener.updateUI((Fragment)vListener);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Error handling
                System.out.println("Something went wrong!");
                error.printStackTrace();
            }
        });

// Add the request to the queue
        Home.app.add(gsonRequest);
    }


    public static void getGooglePhoto(String url,final ImageView googlePhoto) {
        ImageRequest imgRequest = new ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        Home.app.googlePhoto = response;
                        googlePhoto.setImageBitmap(Home.app.googlePhoto);
                    }
                }, 0, 0, ImageView.ScaleType.FIT_XY, Bitmap.Config.ARGB_8888,

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("Something went wrong!");
                        error.printStackTrace();
                    }
                });
        // Add the request to the queue
        Home.app.add(imgRequest);
    }




}