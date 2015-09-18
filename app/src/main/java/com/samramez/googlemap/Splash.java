package com.samramez.googlemap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Splash extends Activity {


    // Duration of wait
    private final int SPLASH_DISPLAY_LENGTH = 3000;

    private Button tryAgainButton;
    private ImageView loadingImage;
    private TextView notConnectedTextView;
    private TextView localistTextView;

    private static Context mContext;



    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_splash);

        // Setting font for app logo
        localistTextView = (TextView) findViewById(R.id.localistaTextView);
        Typeface custom_font = Typeface.createFromAsset(this.getApplicationContext().getAssets(), "fonts/localista_font.ttf");
        localistTextView.setTypeface(custom_font);

        // Initiating elements
        tryAgainButton = (Button) findViewById(R.id.tryButton);
        loadingImage = (ImageView) findViewById(R.id.loadingImageView);
        notConnectedTextView = (TextView) findViewById(R.id.notConnectedTextView);

        if(isInternetAvailable())
            openMainActivityDelayed();
        else
            tryAgainModeDelayed();

    }

    /**
     * @return Current application Context
     */
    public static Context getContext() {
        return mContext;
    }

    private void tryAgainModeDelayed() {
        // New Handler to cause delay in running the code
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tryAgainMode();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    private void openMainActivityDelayed() {
        // New Handler to cause delay in running the code
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                openMainActivity();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    // http://developer.android.com/training/monitoring-device-state/connectivity-monitoring.html
    // Method to determine internet is conneced or not.
    public boolean isInternetAvailable() {

        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    public void tryAgainClicked(View view) {

        loadingMode();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(isInternetAvailable())
                    openMainActivity();
                else{
                    tryAgainMode();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    private void openMainActivity(){
        /* Create an Intent that will start the Menu-Activity. */
        Intent mainIntent = new Intent(Splash.this, MainActivity.class);
        Splash.this.startActivity(mainIntent);
        Splash.this.finish();
    }

    // make loading image visible and hides the Button and Text
    private void loadingMode(){
        loadingImage.setVisibility(View.VISIBLE);
        tryAgainButton.setVisibility(View.GONE);
        notConnectedTextView.setVisibility(View.GONE);
    }

    // Make loading image gone and make Button and text visible
    private void tryAgainMode(){
        loadingImage.setVisibility(View.GONE);
        tryAgainButton.setVisibility(View.VISIBLE);
        notConnectedTextView.setVisibility(View.VISIBLE);
    }
}