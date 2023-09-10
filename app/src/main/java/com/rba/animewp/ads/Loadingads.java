package com.rba.animewp.ads;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;


import com.rba.animewp.R;

public class Loadingads {
    public static    Activity activity;
    public static    AlertDialog Dialog;



    public static void StartDialoge (Activity activity){
        AlertDialog.Builder builder= new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading_ads,null));
        builder.setCancelable(true);
        Dialog = builder.create();
        Dialog.show();

    }
    public static void closeDialoge (Activity activity){

        Dialog.dismiss();
    }

}
