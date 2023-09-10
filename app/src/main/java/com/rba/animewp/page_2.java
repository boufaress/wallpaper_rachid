package com.rba.animewp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.rba.animewp.ads.AdsController;
import com.rba.animewp.ads.applovinADS;
import com.rba.animewp.mData.constant;

public class page_2 extends AppCompatActivity {
TextView custemtext ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page2);
        custemtext = this.findViewById(R.id.tt);
        custemtext.setText(constant.customtext);
        custemtext.setTextColor(Color.parseColor(constant.customtext_color));
        constant.chekinternet(this);
        FrameLayout nativeads = findViewById(R.id.Nativead );
        applovinADS.createNativeAdlarg(this,nativeads);

        ImageView cpaimag = this.findViewById(R.id.cpaimag);
        ViewGroup rootView = findViewById(R.id.maxad_container );
        AdsController.BannerAdsCreateur(this,rootView,cpaimag);
    }

    public void next1(View view) {

        AdsController.Showinter(this, new AdsController.Adfinished() {
            @Override
            public void onADfinished() {
                Intent intent = new Intent(page_2.this, page_3.class);
                startActivity(intent);
                finish();
            }
        });


    }

    public void Rateus(View view) {
        Uri uri = Uri.parse(constant.AppUrl); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        this.startActivity(intent);
    }
}