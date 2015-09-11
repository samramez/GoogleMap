package com.samramez.googlemap;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends FragmentActivity implements
        OnMapReadyCallback {


    private GoogleMap googleMap;
    final String TAG = "myLogs";
    //final String ASYNC_TAG = "********************";

    protected static ArrayList<String> imageArrayList = new ArrayList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        googleMap = mapFragment.getMap();

        onMapLongClicked();



        /*
         Image Links are received at this point
         Now we will put these images in a recycle view
          */

    }


    private void onMapLongClicked() {
        googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {

            @Override
            public void onMapLongClick(LatLng latLng) {
                Log.d(TAG, "onMapLongClick: " + latLng.latitude + "," + latLng.longitude);

                new getInstagramAsyncTask().execute(latLng);

                if (imageArrayList.size() != 0) {
                    // send the list of image urls along with the intent
                    startPhotoActivity(imageArrayList);
                } else
                    Toast.makeText(getApplicationContext(), "Please choose a different location!",
                            Toast.LENGTH_LONG).show();


            }
        });
    }

    /**
     * Start the photo activity and pass the imageArrayList with it.
     * @param imageList
     */
    private void startPhotoActivity(ArrayList<String> imageList){

        Intent intent = new Intent(this, PhotoActivity.class);
        intent.putStringArrayListExtra("image_list", imageList);
        startActivity(intent);
    }


    class getInstagramAsyncTask extends AsyncTask<LatLng, Void, String>{

        @Override
        protected String doInBackground(LatLng... latLng) {

            String urlString = "https://api.instagram.com/v1/media/search?lat=";
            urlString += latLng[0].latitude + "&lng=" ;
            urlString += latLng[0].longitude;
            urlString += "&access_token=" + getString(R.string.access_token);

            //Log.d(ASYNC_TAG, urlString);

            // Getting Json response from url http request
            String result = getJsonResponse(urlString);

            getImagesLink(result);

            return null;
        }

        @Override
        protected void onPostExecute(String response) {

            if (response != null){
                //testTextView.setText(response);
            }
        }
    }

    /*
    Gets url attribute and returns the JSON result in a string format
     */
    public String getJsonResponse(String urlToRead) {
        URL url;
        HttpURLConnection conn;
        BufferedReader rd;
        String line;
        StringBuilder result = new StringBuilder();
        try {
            url = new URL(urlToRead);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }


    private void getImagesLink(String result){

        //ArrayList imageArrayList = new ArrayList();
        try {
            JSONObject jObject = new JSONObject(result);
            JSONArray data = jObject.getJSONArray("data"); // get data object

            for(int i=0 ; i < data.length() ; i++){
                String imageLink = ((JSONObject) data.get(i))
                        .getJSONObject("images")
                        .getJSONObject("low_resolution")
                        .getString("url");

                imageArrayList.add(imageLink.replace("\\", ""));

                //Log.d(ASYNC_TAG, imageArrayList.get(0).toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //return imageArrayList;
    }






    @Override
    public void onMapReady(GoogleMap map) {
        // Add a marker in Sydney, Australia, and move the camera.
        LatLng newYork = new LatLng(40.77560182201292, -73.95034790039062);
        map.addMarker(new MarkerOptions().position(newYork).title("Marker in Sydney"));
        map.moveCamera(CameraUpdateFactory.newLatLng(newYork));
        map.animateCamera(CameraUpdateFactory.zoomTo(15),200,null);
    }
}
