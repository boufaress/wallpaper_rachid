package com.rba.animewp.mData;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;

import com.rba.animewp.pb_internet;

public class constant {

    public static String onesignalid = "990ffc47-ecd5-497f-accf-e842db2ceebb";
    public static int CHECK_INTERVAL_MS = 500;



    public static String customtext  ="hi" ;
    public static String customtext_color  ="#B9C2B9" ;
    public static String customtext1_color  ="#B9C2B9" ;
    public static String customtext1  ="hi" ;
    public static String AppUrl  ="" ;


    public static int nombre_nativeCategory  = 2;

    public static int nombre_nativedetail  = 2;





    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static void chekinternet(Activity activity){
        Handler hndler = new Handler();
        hndler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (constant.isNetworkAvailable(activity)) {
                    chekinternet(activity);
                } else {
                    // La connexion Internet n'est pas disponible
                    Intent intent = new Intent(activity, pb_internet.class);
                    activity.startActivity(intent);
                    activity.finish();
                }

            }
        },constant.CHECK_INTERVAL_MS);
    }

}
