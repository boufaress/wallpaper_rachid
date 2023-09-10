package com.rba.animewp.adapters;

import static android.content.Context.MODE_PRIVATE;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.rba.animewp.R;
import com.rba.animewp.mData.ConstantFile;
import com.rba.animewp.mData.items_wallpaper;
import com.rba.animewp.mData.settings;
import com.rba.animewp.wallpaperPreview;


import java.util.List;

public class detail_adapter extends BaseAdapter {

    // wallpaper adapter

    Activity activity;


    public detail_adapter(Activity activity1, List<items_wallpaper> arrayL) {
        this.activity = activity1;

    }

    @Override
    public int getCount() {
        return  settings.arrayL.size();
    }

    @Override
    public Object getItem(int i) {
        return  settings.arrayL.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(activity).inflate(R.layout.wallpaperfavitem, viewGroup, false);
        }

        ImageView spacecraftImageView = view.findViewById(R.id.imgFood);
        ProgressBar propro = view.findViewById(R.id.progressBar_wall_grid);
        TextView txtloadin = view.findViewById(R.id.loadtext);

        txtloadin.setVisibility(VISIBLE);

        items_wallpaper s = (items_wallpaper) this.getItem(i);

        String imageLink = s.getImageURL();


        RequestOptions reqopt = new RequestOptions().error(R.drawable.ic_holder).diskCacheStrategy(DiskCacheStrategy.ALL);


            Glide.with(activity).asBitmap().load(imageLink).apply(reqopt)
                .addListener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        propro.setVisibility(GONE);
                        txtloadin.setText("----");
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        propro.setVisibility(GONE);
                        txtloadin.setVisibility(GONE);
                        return false;
                    }
                })
                .into(spacecraftImageView);



        spacecraftImageView.setOnClickListener(view1 -> {
            Intent intent = new Intent(activity, wallpaperPreview.class);



            SharedPreferences sharedPref = activity.getSharedPreferences(ConstantFile.shared_pref_file_name, MODE_PRIVATE);
            SharedPreferences.Editor meditor = sharedPref.edit();
            meditor.putString("w_id", s.getId());
            meditor.putString("w_name", s.getName());
            meditor.putString("w_image", imageLink);
            meditor.putString("w_buttonview", "yes");
            meditor.apply();


                //activity.startActivity(new Intent(activity, wallpaperPreview.class));
                //display_interstitial(activity);



        });

        return view;
    }


}