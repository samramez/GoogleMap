package com.samramez.googlemap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements
        OnMapReadyCallback {


    private GoogleMap googleMap;
    final String TAG = "myLogs";
    //final String ASYNC_TAG = "********************";

//    protected static ArrayList<String> imageArrayList = new ArrayList();
//
//    private static ArrayList<Bitmap> imageObjects = new ArrayList<Bitmap>();

    // Value for the toolbar on the top
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.app_bar_toolbar);
        //setSupportActionBar(toolbar);



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
                Log.e(TAG, "onMapLongClick: " + latLng.latitude + "," + latLng.longitude);

                // Creating double array so I can send the latlong attributes to the next Activity
                double[] latLong = {latLng.latitude, latLng.longitude };

//                new getInstagramAsyncTask().execute(latLng);

                if (latLong != null) {
                    // send the list of image urls along with the intent
                    startPhotoActivity(latLong);
                } else
                    Toast.makeText(getApplicationContext(), "Please choose a different location!",
                            Toast.LENGTH_LONG).show();


            }
        });


        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                Log.e(TAG, "onMapLongClick: " + latLng.latitude + "," + latLng.longitude);

                // Creating double array so I can send the latlong attributes to the next Activity
                double[] latLong = {latLng.latitude, latLng.longitude };

//                new getInstagramAsyncTask().execute(latLng);

                if (latLong != null) {
                    // send the list of image urls along with the intent
                    startPhotoActivity(latLong);
                } else
                    Toast.makeText(getApplicationContext(), "Please choose a different location!",
                            Toast.LENGTH_LONG).show();

            }
        });
    }

//    /**
//     * Start the photo activity and pass the imageArrayList with it.
//     * @param imageList
//     */
//    private void startPhotoActivity(ArrayList<Bitmap> imageList){
//
//        Intent intent = new Intent(this, PhotoActivity.class);
//        intent.put("image_list", imageList);
//        startActivity(intent);
//    }


    /**
     * Start the photo activity and pass the imageArrayList with it.
     */
    private void startPhotoActivity(double[] latLong){

        Intent intent = new Intent(this, PhotoActivity.class);
        intent.putExtra("lat_attribute", latLong[0]);
        intent.putExtra("long_attribute", latLong[1]);
        startActivity(intent);
    }




//    class getInstagramAsyncTask extends AsyncTask<LatLng, Void, String> {
//
//        @Override
//        protected String doInBackground(LatLng... latLng) {
//
//            String urlString = "https://api.instagram.com/v1/media/search?lat=";
//            urlString += latLng[0].latitude + "&lng=" ;
//            urlString += latLng[0].longitude;
//            urlString += "&access_token=" + getString(R.string.access_token);
//
//            //Log.d(ASYNC_TAG, urlString);
//
//            // Getting Json response from url http request
//            String result = getJsonResponse(urlString);
//
//            imageArrayList = getImagesLink(result);
//
//            //String[] imageLinkArray =
//
//            /**
//             * Getting the images
//             */
//
//            for (String link : arrayListToString(imageArrayList)) {
//                try {
//                    //ImageView i = (ImageView)findViewById(R.id.image);
//                    Bitmap bitmap = BitmapFactory.decodeStream(
//                            (InputStream) new URL(link).getContent()
//                    );
//
//                    imageObjects.add(bitmap);
//
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                    //return null;
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    //return null;
//                }
//
//            }
//
//            return null;
//        }
//    }



//    private ArrayList<String> getImagesLink(String result){
//
//        ArrayList<String> imageArray = new ArrayList<String>();
//
//        //ArrayList imageArrayList = new ArrayList();
//        try {
//            JSONObject jObject = new JSONObject(result);
//            JSONArray data = jObject.getJSONArray("data"); // get data object
//
//            for(int i=0 ; i < data.length() ; i++){
//                String imageLink = ((JSONObject) data.get(i))
//                        .getJSONObject("images")
//                        .getJSONObject("low_resolution")
//                        .getString("url");
//
//                imageArray.add(imageLink.replace("\\", ""));
//
//                //Log.d(ASYNC_TAG, imageArrayList.get(0).toString());
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return imageArray;
//    }

//    /**
//     * Gets url attribute and returns the JSON result in a string format
//     */
//    public String getJsonResponse(String urlToRead) {
//        URL url;
//        HttpURLConnection conn;
//        BufferedReader rd;
//        String line;
//        StringBuilder result = new StringBuilder();
//        try {
//            url = new URL(urlToRead);
//            conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod("GET");
//            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//            while ((line = rd.readLine()) != null) {
//                result.append(line);
//            }
//            rd.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return result.toString();
//    }


    @Override
    public void onMapReady(GoogleMap map) {
        // Add a marker in Sydney, Australia, and move the camera.
        LatLng newYork = new LatLng(40.711462, -74.013184);
        map.addMarker(new MarkerOptions().position(newYork).title("Marker in New Brunswick"));
        map.moveCamera(CameraUpdateFactory.newLatLng(newYork));
        map.animateCamera(CameraUpdateFactory.zoomTo(16),200,null);
    }

    /**
     * Method to convert ArrayList<String> to String[]
     */
    private String[] arrayListToString(ArrayList<String> list){
        String[] stringArray = new String[list.size()];
        stringArray = list.toArray(stringArray);
        return stringArray;
    }
}
