package com.rba.animewp;

import static com.rba.animewp.mData.ConstantFile.DBSqliteWallpaper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.applovin.mediation.nativeAds.adPlacer.MaxAdPlacerSettings;
import com.applovin.mediation.nativeAds.adPlacer.MaxRecyclerAdapter;
import com.rba.animewp.adapters.GameAdapter;
import com.rba.animewp.ads.AdsController;
import com.rba.animewp.mData.ConstantFile;
import com.rba.animewp.mData.SQLiteHelper;
import com.rba.animewp.mData.constant;
import com.rba.animewp.mData.settings;

public class wallpaperdetail extends AppCompatActivity {
    public static SQLiteHelper sqlhelper;
    static String x_array;
    Toolbar toolbar;
    RecyclerView myListView;
    ProgressBar myProgressBar;
    GameAdapter gameAdapter;
    MaxRecyclerAdapter adAdapter;
int P;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaperdetail);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        SharedPreferences sharedpre = getSharedPreferences(ConstantFile.shared_pref_file_name, MODE_PRIVATE);

        x_array = sharedpre.getString("g_title", "default");


        P = getIntent().getIntExtra("position",0) ;

        constant.chekinternet(this);
        toolbar = this.findViewById(R.id.mtoolbar);
        setSupportActionBar(toolbar);
        //setTitle("Pubg Wallpapers");

        sqlhelper = new SQLiteHelper(this, DBSqliteWallpaper, null, 1);
        sqlhelper.queryData("CREATE TABLE IF NOT EXISTS FOOD(Id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, price VARCHAR, image BLOB)");

        myListView = this.findViewById(R.id.myListView);
        myProgressBar = this.findViewById(R.id.myProgressBar);
        Showcategory();
        getMaxAdapter();

        ViewGroup rootView = findViewById(R.id.maxad_container );
        ImageView cpaimag = this.findViewById(R.id.cpaimag);
        AdsController.BannerAdsCreateur(this,rootView,cpaimag);

    }

    private void getMaxAdapter() {
        MaxAdPlacerSettings setting = new MaxAdPlacerSettings(settings.Nativeapplovine);
        setting.addFixedPosition( 0);
        setting.setRepeatingInterval( constant.nombre_nativedetail);
        adAdapter = new MaxRecyclerAdapter(setting, gameAdapter, this);
        adAdapter.loadAds();


        myListView.setAdapter(adAdapter);
        adAdapter.getAdPlacer().setAdSize( 160, 210 );
        adAdapter.getAdPlacer();



    }

    private void Showcategory() {



        myListView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
         gameAdapter=new GameAdapter(this,settings.arrayL);

    }

    @Override
    public void onBackPressed() {
        settings.arrayL.clear();
        settings.statue1=0;
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.m_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.favori) {

            startActivity(new Intent(getApplicationContext(), wallpaperFavorites.class));
            return true;
        }

        if (id == R.id.reloader) {

            finish();
            startActivity(getIntent());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy()
    {
        adAdapter.destroy();
        super.onDestroy();
    }

    }

