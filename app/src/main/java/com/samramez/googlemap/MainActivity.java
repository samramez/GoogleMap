package com.samramez.googlemap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
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

    private TextView toolbarTitleTextView;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private String[] mDrawerItems;
    private ActionBarDrawerToggle mDrawerToggle;

    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;

    private double lat;
    private double lon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // Using SharedPreferences to load the most recent clicked locations
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        lat = getDouble(sharedpreferences, "lat", 40.711462);
        lon = getDouble(sharedpreferences, "long", -74.013184);

        // Setting the Font for the title in the Toolbar
        toolbarTitleTextView = (TextView) findViewById(R.id.toolbar_title);
        Typeface custom_font = Typeface.createFromAsset(this.getApplicationContext().getAssets(), "fonts/localista_font.ttf");
        toolbarTitleTextView.setTypeface(custom_font);

        // Turning on the sliding option from other activities
        overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit);

        toolbar = (Toolbar) findViewById(R.id.app_bar_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.rectangular4));
        toolbar.setBackgroundColor(getResources().getColor(R.color.toolbar_color));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


        // Setting up the drawer layout
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        ){

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //This shows the hamburger button
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerToggle.syncState();

        //Building DrawerLayout contents
        mDrawerItems = getResources().getStringArray(R.array.drawer_array);
        DrawerMyAdapter adapter = new DrawerMyAdapter(this, generateData());
        ListView mListView = (ListView) findViewById(R.id.DrawerListView);
        mListView.setAdapter(adapter);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        googleMap = mapFragment.getMap();

        onMapLongClicked();

    }


    /**
     * Method to genrate data for the AdapterView
     * @return
     */
    private ArrayList<DrawerModel> generateData(){
        ArrayList<DrawerModel> models = new ArrayList<DrawerModel>();
        models.add(new DrawerModel(R.drawable.favourites , mDrawerItems[0]));
        models.add(new DrawerModel(R.drawable.history , mDrawerItems[1]));

        return models;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }

//    @Override
//    public boolean onOptionsItemSelected(android.view.MenuItem item) {
//        if (mDrawerToggle.onOptionsItemSelected(item)) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    SharedPreferences.Editor putDouble(final SharedPreferences.Editor edit, final String key, final double value) {
        return edit.putLong(key, Double.doubleToRawLongBits(value));
    }

    double getDouble(final SharedPreferences prefs, final String key, final double defaultValue) {
        if ( !prefs.contains(key))
            return defaultValue;

        return Double.longBitsToDouble(prefs.getLong(key, 0));
    }



    private void onMapLongClicked() {
        googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {

            @Override
            public void onMapLongClick(LatLng latLng) {
                Log.e(TAG, "onMapLongClick: " + latLng.latitude + "," + latLng.longitude);

                // Creating double array so I can send the latlong attributes to the next Activity
                double[] latLong = {latLng.latitude, latLng.longitude };

                // Saving the values in the storage
                SharedPreferences.Editor editor = sharedpreferences.edit();
                putDouble(editor,"lat", latLong[0]);
                putDouble(editor,"long", latLong[1]);
                editor.commit();

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

                // Saving the values in the storage
                SharedPreferences.Editor editor = sharedpreferences.edit();
                putDouble(editor,"lat", latLong[0]);
                putDouble(editor,"long", latLong[1]);
                editor.commit();

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
        //Log.e("**MAIN ACTIVITY**", "SENT LAT IS : " + latLong[0]);
        intent.putExtra("long_attribute", latLong[1]);
        //Log.e("**MAIN ACTIVITY**", "SENT LONG IS : " + latLong[1]);
        startActivity(intent);
    }


    @Override
    public void onMapReady(GoogleMap map) {
        // Add a marker in Sydney, Australia, and move the camera.
        LatLng newYork = new LatLng(lat, lon);
//        Log.e("**MAIN ACTIVITY**", "LAT IS : " + lat);
//        Log.e("**MAIN ACTIVITY**", "LAT IS : " + lon);
        map.addMarker(new MarkerOptions().position(newYork).title("Marker in New Brunswick"));
        map.moveCamera(CameraUpdateFactory.newLatLng(newYork));
        map.animateCamera(CameraUpdateFactory.zoomTo(12),200,null);
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
