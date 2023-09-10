package com.rba.animewp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.rba.animewp.ads.AdsController;
import com.rba.animewp.ads.applovinADS;
import com.rba.animewp.mData.constant;

public class page_3 extends AppCompatActivity {
    TextView custemtext ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page3);
        constant.chekinternet(this);
        custemtext = this.findViewById(R.id.txtx);
        custemtext.setText(constant.customtext1);
        custemtext.setTextColor(Color.parseColor(constant.customtext1_color));

        FrameLayout nativeads = findViewById(R.id.Nativead );
        applovinADS.createNativeAdlarg(this,nativeads);

        ImageView cpaimag = this.findViewById(R.id.cpaimag);
        ViewGroup rootView = findViewById(R.id.maxad_container );
        AdsController.BannerAdsCreateur(this,rootView,cpaimag);

    }


    public void next2(View view) {


        AdsController.Showinter(this, new AdsController.Adfinished() {
            @Override
            public void onADfinished() {
                Intent intent = new Intent(page_3.this, wallpaperCategory.class);
                startActivity(intent);
                finish();
            }
        });


    }
}