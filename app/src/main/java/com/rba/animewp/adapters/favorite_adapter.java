package com.rba.animewp.adapters;

import static android.content.Context.MODE_PRIVATE;


import android.app.Activity;
import android.content.Context;
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
import com.rba.animewp.mData.items_favorit;
import com.rba.animewp.wallpaperPreview;


import java.util.ArrayList;

public class favorite_adapter extends BaseAdapter {

    // wallpaper favorites adapter

    private final Activity activity;
    private final int layout;
    private final ArrayList<items_favorit> arrayList;

    public favorite_adapter(Activity activity, int layout, ArrayList<items_favorit> foodsList) {
        this.activity = activity;
        this.layout = layout;
        this.arrayList = foodsList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        View row = view;
        ViewHolder holder = new ViewHolder();

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.txtName = row.findViewById(R.id.txtName);
            holder.txtPrice = row.findViewById(R.id.txtPrice);
            holder.imageView = row.findViewById(R.id.imgFood);
            holder.progBar = row.findViewById(R.id.progressBar_wall_grid);
            row.setTag(holder);

        } else {
            holder = (ViewHolder) row.getTag();
        }

        items_favorit food = arrayList.get(position);

        String imaglink_full = food.getPrice();

        holder.txtName.setOnClickListener(view1 -> {

            SharedPreferences sharedPref = activity.getSharedPreferences(ConstantFile.shared_pref_file_name, MODE_PRIVATE);
            SharedPreferences.Editor meditor = sharedPref.edit();
            meditor.putString("w_name", food.getName());
            meditor.putString("w_image", imaglink_full);
            meditor.putString("w_buttonview", "no");
            meditor.apply();

            activity.startActivity(new Intent(activity, wallpaperPreview.class));

        });


        holder.txtName.setText("Preview");
        holder.txtPrice.setText(imaglink_full);

        RequestOptions requestOptions = new RequestOptions().error(R.drawable.ic_holder).diskCacheStrategy(DiskCacheStrategy.ALL);

        ViewHolder finalHolder = holder;



            Glide.with(activity).asBitmap().load(imaglink_full).apply(requestOptions)
                .addListener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        finalHolder.txtName.setText("--");
                        finalHolder.progBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        finalHolder.progBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(holder.imageView);



        return row;
    }


    private static class ViewHolder {
        ImageView imageView;
        TextView txtName, txtPrice;
        ProgressBar progBar;
    }
}
