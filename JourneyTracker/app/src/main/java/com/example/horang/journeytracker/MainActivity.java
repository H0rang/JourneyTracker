package com.example.horang.journeytracker;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;

public class MainActivity extends AppCompatActivity {
    public static Boolean active;
    public static CircularQueue Q;
    Button button;
    TextView ovTime, avSpeed, curSpeed, gpsActive;
    LocationManager manager;
    LocationListener listener;
    Boolean GPS;
    long startTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Q = new CircularQueue(30);
        manager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        ovTime = (TextView) findViewById(R.id.text4);
        avSpeed = (TextView) findViewById(R.id.text3);
        curSpeed = (TextView) findViewById(R.id.text2);
        gpsActive = (TextView) findViewById(R.id.text);
        GPS = false;
        gpsIsOn(manager, gpsActive);

        ovTime.setText(getString(R.string.time) + " 00:00");
        avSpeed.setText(getString(R.string.avspeed) + " N/A");
        curSpeed.setText(getString(R.string.curspeed) + " N/A");

        button = (Button) findViewById(R.id.button);
        button.setText(R.string.start);
        active = false;

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gpsIsOn(manager, gpsActive);

                if(button.getText() == getString(R.string.start)){
                    button.setText(R.string.stop);
                    active = true;

                    startTime = System.currentTimeMillis();

                    listener = new LocationListener() {
                        public void onLocationChanged(Location location) {
                            if(active){
                                Q.addLocation(location);
                                curSpeed.setText(getString(R.string.curspeed) + " " + Float.toString(Q.getSpeed()) +
                                        " km/h");
                                avSpeed.setText(getString(R.string.avspeed) + " " + Float.toString(Q.getAverageSpeed()) +
                                        " km/h");

                                gpsIsOn(manager, gpsActive);

                                long millis = System.currentTimeMillis() - startTime;
                                int seconds = (int) (millis / 1000);
                                int minutes = seconds / 60;
                                seconds = seconds % 60;

                                ovTime.setText(getString(R.string.time) + " " + minutes + ":" + seconds);
                            }
                        }

                        @Override
                        public void onStatusChanged(String provider, int status, Bundle extras) {

                        }

                        @Override
                        public void onProviderEnabled(String provider) {

                        }

                        @Override
                        public void onProviderDisabled(String provider) {

                        }
                    };

                    try{
                        manager.requestLocationUpdates(manager.GPS_PROVIDER,1000,0,listener);
                    }catch (SecurityException e){
                        Log.e("GPS",e.getMessage());
                    }
                }
                else{
                    active = false;
                    button.setText(R.string.start);
                    ovTime.setText(getString(R.string.time) + " 00:00");
                    avSpeed.setText(getString(R.string.avspeed) + " N/A");
                    curSpeed.setText(getString(R.string.curspeed) + " N/A");
                    Q.deleteQueue();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void gpsIsOn(LocationManager locman, TextView text){
        //doesn't work with mock localisation
        try{
            GPS = locman.isProviderEnabled(LocationManager.GPS_PROVIDER);
        }
        catch (Exception e){}

        if(GPS)
            text.setText("GPS active");
        else
            text.setText("GPS inactive");
    }
}
