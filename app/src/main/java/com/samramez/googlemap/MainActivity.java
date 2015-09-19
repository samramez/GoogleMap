package com.samramez.googlemap;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
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

    // Value for the toolbar on the top
    private Toolbar toolbar;

    private static double lat;
    private static double lon;

    private TextView toolbarTitleTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null){
            lat = getIntent().getExtras().getDouble("lat_attribute");
            lon = getIntent().getExtras().getDouble("lang_attribute");
        } else {
            lat = 40.711462;
            lon = -74.013184;
        }

        setContentView(R.layout.activity_main);

        toolbarTitleTextView = (TextView) findViewById(R.id.toolbar_title);
        Typeface custom_font = Typeface.createFromAsset(this.getApplicationContext().getAssets(), "fonts/localista_font.ttf");
        toolbarTitleTextView.setTypeface(custom_font);

        // Turning on the sliding option
        //overridePendingTransition(R.anim.slide_out , R.anim.slide_in);

        toolbar = (Toolbar) findViewById(R.id.app_bar_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.rectangular4));
        toolbar.setBackgroundColor(getResources().getColor(R.color.toolbar_color));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });



        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        googleMap = mapFragment.getMap();

        onMapLongClicked();

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


    @Override
    public void onMapReady(GoogleMap map) {
        // Add a marker in Sydney, Australia, and move the camera.
        LatLng newYork = new LatLng(lat, lon);
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
