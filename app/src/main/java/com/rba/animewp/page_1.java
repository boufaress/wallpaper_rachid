package com.rba.animewp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.rba.animewp.ads.AdsController;
import com.rba.animewp.ads.applovinADS;
import com.rba.animewp.mData.constant;

public class page_1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page1);
        FrameLayout nativeads = findViewById(R.id.Nativead );
        applovinADS.createNativeAdlarg(this,nativeads);

        ImageView cpaimag = this.findViewById(R.id.cpaimag);
        ViewGroup rootView = findViewById(R.id.maxad_container );
        AdsController.BannerAdsCreateur(this,rootView,cpaimag);

        constant.chekinternet(this);


    }

    public void next(View view) {


        AdsController.Showinter(this, new AdsController.Adfinished() {
            @Override
            public void onADfinished() {
                Intent intent = new Intent(page_1.this, wallpaperCategory.class);
                startActivity(intent);
            }
        });




    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {


                        page_1.super.onBackPressed();
                        finish();
                    }
                }).create().show();
    }


}