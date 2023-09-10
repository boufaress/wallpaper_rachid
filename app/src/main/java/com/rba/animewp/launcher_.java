package com.rba.animewp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import com.rba.animewp.ads.AdsController;
import com.rba.animewp.mData.settings;

public class launcher_ extends AppCompatActivity {

    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher_);
        progressBar = findViewById(R.id.prog);

        launch__activity();

        progressBar.setIndeterminate(true);

    }
    private void launch__activity() {
        Handler hndler = new Handler();
        hndler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (settings.statue==0){
                    hndler.postDelayed(this,500);

                } else if (settings.statue==1) {
                    Intent intent = new Intent(launcher_.this, page_1.class);
                    AdsController.initialiseads (launcher_.this);
                    hndler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            startActivity(intent);

                            finish();

                        }
                    },3500);


                }

            }
        },1000);

        }





    @Override
    public void onBackPressed() {

        finish();
        System.exit(0);
    }









}