package com.rba.animewp.mData;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.google.android.material.snackbar.Snackbar;
import com.rba.animewp.R;

import java.io.IOException;

@SuppressLint("ValidFragment")
public class WallpaperDialog extends DialogFragment {

    private final Context context;
    private final Bitmap resource;
    private final View view;
    private Bitmap bitmap;
    private WallpaperManager wallpaperManager;
    private ProgressDialog progressDialog;

    public WallpaperDialog(Context context, Bitmap resource, View view) {
        this.context = context;
        this.resource = resource;
        this.view = view;
    }


    private void setOurWall(int which, final int sbMessage) {

        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(true);
        progressDialog.setMessage("Setting Wallpaper ...");
        progressDialog.show();

        DisplayMetrics metrics = new DisplayMetrics();
        ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE))
            .getDefaultDisplay().getMetrics(metrics);
        int height = metrics.heightPixels;
        int width = metrics.widthPixels;
        bitmap = Bitmap.createScaledBitmap(resource, width, height, true);

        wallpaperManager = WallpaperManager.getInstance(context);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    wallpaperManager.setBitmap(bitmap);
                    //Snackbar.make(view, sbMessage, Snackbar.LENGTH_SHORT).show();
                    Toast.makeText(context, "Wallpaper Set Successfully", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                } catch (IOException e) {
                    //e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        }, 1000);

    }

    private void setOurWall(int sbMessage) {
        if (Build.VERSION.SDK_INT >= 24) {
            try {
//            WallpaperManager.getInstance(context)
//                    .setBitmap(resource);
                WallpaperManager.getInstance(context)
                    .setBitmap(resource, null, true, WallpaperManager.FLAG_SYSTEM);
                WallpaperManager.getInstance(context)
                    .setBitmap(resource, null, true, WallpaperManager.FLAG_LOCK);
                Snackbar.make(view, sbMessage, Snackbar.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        int i;
        if (Build.VERSION.SDK_INT >= 24) i = R.array.set_wallpaper_options;
        else i = R.array.set_wallpaper_options2;

        final AlertDialog.Builder setWall = new AlertDialog.Builder(context);
        setWall.setTitle("Set As Wallpaper")
            .setItems(i, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    switch (i) {
                        case 0: {
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    setOurWall(WallpaperManager.FLAG_SYSTEM, R.string.home_set);
                                }
                            });
                            break;
                        }
                        case 1: {
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    setOurWall(WallpaperManager.FLAG_LOCK, R.string.lock_screen_set);
                                }
                            });
                            break;
                        }
                        case 2: {
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    setOurWall(R.string.both_set);
                                }
                            });
                            break;
                        }
                    }

                }
            });

        return setWall.create();
    }
}
