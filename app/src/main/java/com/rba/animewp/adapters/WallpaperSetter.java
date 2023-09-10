package com.rba.animewp.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.rba.animewp.R;

import com.rba.animewp.mData.WallpaperDialog;

public class WallpaperSetter {

    private Context context;
    private FragmentManager fragmentManager;
    private String imageUrl;

    public WallpaperSetter(Context context, FragmentManager fragmentManager, String imageUrl) {
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.imageUrl = imageUrl;
    }

    public void setWallpaperInBackground(View view) {
        Glide.with(context).asBitmap().load(imageUrl)
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        WallpaperDialog wallSelectDialog = new WallpaperDialog(context, resource, view);
                        wallSelectDialog.show(fragmentManager, "wallselectdialog");
                        return false;
                    }
                })
                .apply(new RequestOptions().error(R.drawable.ic_holder))
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        // Set the bitmap as wallpaper here if needed
                        // For example:
                        // WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);
                        // try {
                        //     wallpaperManager.setBitmap(resource);
                        // } catch (IOException e) {
                        //     e.printStackTrace();
                        // }
                    }

                    @Override
                    public void removeCallback(SizeReadyCallback cb) {
                    }

                    @Override
                    public Request getRequest() {
                        return null;
                    }

                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onStop() {
                    }

                    @Override
                    public void onDestroy() {
                    }

                    @Override
                    public void setRequest(Request request) {
                    }
                });
    }
}
