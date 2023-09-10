package com.rba.animewp.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rba.animewp.R;

public class categoryHolder extends RecyclerView.ViewHolder {
    public  ImageView appimage;
    public TextView  appname;

    public RelativeLayout app;

    public categoryHolder(@NonNull View itemView) {
        super(itemView);

        appname = itemView.findViewById(R.id.textView1);
        app = itemView.findViewById(R.id.app);

        appimage = itemView.findViewById(R.id.background1);
    }
}
