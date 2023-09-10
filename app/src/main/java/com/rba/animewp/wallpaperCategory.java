package com.rba.animewp;

import static com.rba.animewp.mData.settings.arrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.applovin.mediation.nativeAds.adPlacer.MaxAdPlacerSettings;
import com.applovin.mediation.nativeAds.adPlacer.MaxRecyclerAdapter;
import com.rba.animewp.adapters.category_Adapter;
import com.rba.animewp.ads.AdsController;
import com.rba.animewp.ads.applovinADS;
import com.rba.animewp.mData.constant;
import com.rba.animewp.mData.settings;

public class wallpaperCategory extends AppCompatActivity {

    category_Adapter category_adapter;
    MaxRecyclerAdapter adAdapter;
    ProgressBar mProgBar;
    RecyclerView mListVe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaper_category);

        constant.chekinternet(this);


        id_list();


        ViewGroup rootView = findViewById(R.id.maxad_container );
        ImageView cpaimag = this.findViewById(R.id.cpaimag);
        AdsController.BannerAdsCreateur(this,rootView,cpaimag);

        Showcategory();
        getMaxAdapter();

    }

    private void id_list() {

        mProgBar = findViewById(R.id.justprogr);
    }
    private void Showcategory() {

        mListVe=this.findViewById(R.id.myListVie);
        mListVe.setHasFixedSize(true);
        mListVe.setLayoutManager(new LinearLayoutManager(this));
        category_adapter=new category_Adapter(this, settings.arrayList);

    }
    @Override
    public void onBackPressed() {


        finish();
    }


    private void getMaxAdapter() {
        MaxAdPlacerSettings setting = new MaxAdPlacerSettings(settings.Nativeapplovine);
        setting.addFixedPosition( 0);
        setting.setRepeatingInterval( constant.nombre_nativeCategory);
        adAdapter = new MaxRecyclerAdapter(setting, category_adapter, wallpaperCategory.this);
        adAdapter.loadAds();


        mListVe.setAdapter(adAdapter);
        adAdapter.getAdPlacer();



    }
    @Override
    public void onDestroy()
    {
        adAdapter.destroy();
        super.onDestroy();
    }

}