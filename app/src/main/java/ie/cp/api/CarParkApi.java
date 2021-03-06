package ie.cp.api;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ie.cp.activities.Home;
import ie.cp.activities.Login;
import ie.cp.fragments.AddReservationFragment;
import ie.cp.models.CarPark;
import ie.cp.models.CarParkSpace;
import ie.cp.models.Reservation;

public class CarParkApi {

    //private static final String LocalhostURL = "http://10.0.2.2:8080/api";
    private static final String LocalhostURL = "http://carparkserver.herokuapp.com/api";
    private static VolleyListener vListener;
    private static AlertDialog loader;

    public static void attachListener(VolleyListener fragment) { vListener = fragment; }
    public static void detachListener() {
        vListener  = null;
    }
    public static void attachDialog(AlertDialog aloader) {

        loader = aloader;
    }

    public static void getCarParks(String url) {
            showLoader("Downloading Data...");

        // Request a string response
        Log.v("carparkApplication","GET REQUEST : " + LocalhostURL + url);

        //        JsonObjectRequest gsonRequest = new JsonObjectRequest(Request.Method.GET, LocalhostURL + url,null,
        JsonArrayRequest gsonRequest = new JsonArrayRequest(Request.Method.GET, LocalhostURL + url,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        // Result handling
                        List<CarPark> result = new ArrayList<CarPark>();

                        try {

                            for (int j = 0; j < response.length(); j++){
                                JSONObject obj = response.getJSONObject(j);

                                result.add(new CarPark(obj.getString("_id"),obj.getString("carParkName"),obj.getString("address"),obj.getString("location"),obj.getString("spacesAvailable"),obj.getString("totalSpaces"),obj.getDouble("latitude"),obj.getDouble("longitude")));

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        vListener.setList(result);

                        if (result.size() > 0) {
                            vListener.setCarPark(result.get(0));

                        }
                        hideLoader();
                        vListener.updateUI((Fragment) vListener);
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

    public static void isValidUser(String url) {
      //  showLoader("checking status...");

        // Request a string response
        Log.v("carparkApplication","GET REQUEST : " + LocalhostURL + url);

        JsonObjectRequest gsonRequest = new JsonObjectRequest(Request.Method.GET, LocalhostURL + url,null,
     //   JsonArrayRequest gsonRequest = new JsonArrayRequest(Request.Method.GET, LocalhostURL + url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        // Result handling
                        int result = 0;

                        try {
                            result = response.getInt("result");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Login.app.validStatus =result;
               //         hideLoader();
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
        Login.app.add(gsonRequest);
    }

    public static void getCarParkSpaces(String url) {
            showLoader("Downloading Data...");

        // Request a string response
        Log.v("carparkApplication","GET REQUEST : " + LocalhostURL + url);

        JsonArrayRequest gsonRequest = new JsonArrayRequest(Request.Method.GET, LocalhostURL + url,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Result handling
                        List<CarParkSpace> result = new ArrayList<CarParkSpace>();

                        try {

                            for (int j = 0; j < response.length(); j++){
                                JSONObject obj = response.getJSONObject(j);

                                result.add(new CarParkSpace(obj.getString("_id"),obj.getString("carParkSpaceName"),obj.getString("carParkSpaceDescription"),obj.getString("carParkId"),obj.getBoolean("booked")));

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        vListener.setSpaceList(result);

                        if (result.size() > 0) {
                            vListener.setCarParkSpace(result.get(0));
                        }
                        hideLoader();
                        if(vListener instanceof AddReservationFragment){
                            vListener.updateCarParkSpaceDropdown((Fragment) vListener);
                        } else {
                            vListener.updateUI((Fragment) vListener);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Error handling
                System.out.println("Something went wrong getting car spaces!");
                error.printStackTrace();
            }
        });

        // Add the request to the queue
        Home.app.add(gsonRequest);
    }

    public static void delete(String url) {
        showLoader("Deleting Data...");
        Log.v("carparkApplication", "DELETEing from " + url);

        // Request a string response
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, LocalhostURL + url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Result handling
                        Log.v("carparkApplication", "DELETE success " + response);
                        getCarParks("/carpark"); // Forcing a refresh of the updated list on the Server
                        hideLoader();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Error handling
                Log.v("carparkApplication","Something went wrong with DELETE of car park!");
                error.printStackTrace();
            }
        });

        // Add the request to the queue
        Home.app.add(stringRequest);
    }

    public static void deleteSpace(String url, final String carParkId) {
           showLoader("Deleting Data...");
        Log.v("carparkApplication", "DELETEing from " + url);

        // Request a string response
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, LocalhostURL + url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Result handling
                        Log.v("carparkApplication", "DELETE success " + response);
                        getCarParkSpaces("/carparkspace/" + carParkId + "/true"); // Forcing a refresh of the updated list on the Server
                        hideLoader();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Error handling
                Log.v("carparkApplication","Something went wrong with DELETE of car park space!");
                error.printStackTrace();
            }
        });

        // Add the request to the queue
        Home.app.add(stringRequest);
    }

    public static void deleteReservation(String url) {
           showLoader("Deleting Data...");
        Log.v("carparkApplication", "DELETEing from " + url);

        // Request a string response
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, LocalhostURL + url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Result handling
                        Log.v("carparkApplication", "DELETE success " + response);
                        getReservations("/reservation/" + Home.app.googleMail); // Forcing a refresh of the updated list on the Server
                        hideLoader();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Error handling
                Log.v("carparkApplication","Something went wrong with DELETE of reservation!");
                error.printStackTrace();
            }
        });

        // Add the request to the queue
        Home.app.add(stringRequest);
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
                        System.out.println("Something went wrong getting google photo!");
                        error.printStackTrace();
                    }
                });
        // Add the request to the queue
        Home.app.add(imgRequest);
    }


    public static void putCarPark(final String url, CarPark aCarPark) {

        Log.v("carparkApplication", "PUTing to : " + url);
        Type objType = new TypeToken<CarPark>(){}.getType();
        String json = new Gson().toJson(aCarPark, objType);

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest gsonRequest = new JsonObjectRequest( Request.Method.PUT, LocalhostURL + url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Result handling
                        List<CarPark> result = null;
                        Type objType = new TypeToken<List<CarPark>>(){}.getType();

                        try {
                            result = new Gson().fromJson(response.getString("data"), objType);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        getCarParks("/carpark"); // Forcing a refresh of the updated list on the Server
                        Log.v("carparkApplication", "Updating a CarPark successful with : " + response + result);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //   Handle Error
                        Log.v("carparkApplication", "Unable to update CarPark with error : " + error.getMessage());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        // Add the request to the queue
        Home.app.add(gsonRequest);
    }

    public static void putCarParkSpace(final String url, CarParkSpace aCarParkSpace) {

        Log.v("carparkApplication", "PUTing to : " + url);
        Type objType = new TypeToken<CarParkSpace>(){}.getType();
        String json = new Gson().toJson(aCarParkSpace, objType);

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest gsonRequest = new JsonObjectRequest( Request.Method.PUT, LocalhostURL + url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Result handling
                        List<CarParkSpace> result = null;
                        Type objType = new TypeToken<List<CarParkSpace>>(){}.getType();

                        try {
                            result = new Gson().fromJson(response.getString("data"), objType);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        getCarParks("/carparkspace"); // Forcing a refresh of the updated list on the Server
                        Log.v("carparkApplication", "Updating a CarParkSpace successful with : " + response + result);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //   Handle Error
                        Log.v("carparkApplication", "Unable to update CarParkSpace with error : " + error.getMessage());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        // Add the request to the queue
        Home.app.add(gsonRequest);
    }

    public static void getReservations(String url) {
        // Request a string response
        Log.v("carparkApplication","GET REQUEST : " + LocalhostURL + url);

        JsonArrayRequest gsonRequest = new JsonArrayRequest(Request.Method.GET, LocalhostURL + url,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Result handling
                        List<Reservation> result = new ArrayList<Reservation>();

                        try {
                           for (int j = 0; j < response.length(); j++){
                                JSONObject obj = response.getJSONObject(j);

                                result.add(new Reservation(obj.getString("_id"),obj.getString("userId"),obj.getString("carParkId"),obj.getString("carParkSpaceId")));

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (vListener !=null) {
                            vListener.setReservationList(result);
                        }
                        if (result.size() > 0) {
                            vListener.setReservation(result.get(0));
                        }
                        if (vListener !=null) {
                            vListener.updateUI((Fragment) vListener);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Error handling
                System.out.println("Something went wrong getting reservations!");
                error.printStackTrace();
            }
        });

        // Add the request to the queue
        Home.app.add(gsonRequest);
    }


    public static void putReservation(final String url, Reservation aReservation) {

        Log.v("carparkApplication", "PUTing to : " + url);
        Type objType = new TypeToken<Reservation>(){}.getType();
        String json = new Gson().toJson(aReservation, objType);

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest gsonRequest = new JsonObjectRequest( Request.Method.PUT, LocalhostURL + url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Result handling
                        List<Reservation> result = null;
                        Type objType = new TypeToken<List<Reservation>>(){}.getType();

                        try {
                            result = new Gson().fromJson(response.getString("data"), objType);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        getReservations("/reservation/" + Home.app.googleMail); // Forcing a refresh of the updated list on the Server
                        Log.v("carparkApplication", "making a reservation successful with : " + response + result);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //   Handle Error
                        Log.v("carparkApplication", "Unable to make reservation with error : " + error.getMessage());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        // Add the request to the queue
        Home.app.add(gsonRequest);
    }

    private static void showLoader(String message) {
        if (!loader.isShowing()) {
            if(message != null)loader.setTitle(message);
            loader.show();
        }
    }

    private static void hideLoader() {
        if (loader.isShowing())
            loader.dismiss();
    }


}