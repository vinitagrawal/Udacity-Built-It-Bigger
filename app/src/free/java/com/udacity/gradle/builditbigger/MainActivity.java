package com.udacity.gradle.builditbigger;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.jokedisplay.JokeDisplayActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;


public class MainActivity extends AppCompatActivity {

    boolean isAppPaidVersion;

    InterstitialAd interstitialAd;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        isAppPaidVersion = getResources().getBoolean(R.bool.is_paid_version);

        if (!isAppPaidVersion) {
            interstitialAd = new InterstitialAd(this);
            interstitialAd.setAdUnitId(getString(R.string.banner_ad_unit_id));
            interstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    requestNewInterstitial();
                    fetchAndDisplayJoke();
                }
            });
            requestNewInterstitial();
        }
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        interstitialAd.loadAd(adRequest);
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

    private void fetchAndDisplayJoke() {
        progressBar.setVisibility(View.VISIBLE);

        new JokeAsyncTask() {
            @Override
            protected void onPostExecute(String str) {
                if (str != null) {
                    startActivity(JokeDisplayActivity.startIntent(MainActivity.this, str));
                } else {
                    Toast.makeText(MainActivity.this, "Unable to retrieve joke", Toast.LENGTH_LONG).show();
                }

                progressBar.setVisibility(View.GONE);
            }
        }.execute();
    }

    public void displayJoke(View view){
        if (!isAppPaidVersion && interstitialAd.isLoaded())
            interstitialAd.show();
        else {
            fetchAndDisplayJoke();
        }
    }


}
