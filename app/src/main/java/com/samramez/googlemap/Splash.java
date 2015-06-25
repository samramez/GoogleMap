package com.samramez.googlemap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.InetAddress;

public class Splash extends Activity {

    /**
     * Duration of wait
     **/
    private final int SPLASH_DISPLAY_LENGTH = 3000;

    private Button tryAgainButton;
    private ImageView loadingImage;
    private TextView notConnectedTextView;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_splash);

        // Initiating elements
        tryAgainButton = (Button) findViewById(R.id.tryButton);
        loadingImage = (ImageView) findViewById(R.id.loadingImageView);
        notConnectedTextView = (TextView) findViewById(R.id.notConnectedTextView);



        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {



                if(isInternetAvailable() )
                    openMainActivity();
                else {
                    // Hiding the loading View and showing Try Again TextView and Button
                    tryAgainMode();
                }

            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com"); //You can replace it with your name

            if (ipAddr.equals("")) {
                return false;
            } else {
                return true;
            }

        } catch (Exception e) {
            return false;
        }

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