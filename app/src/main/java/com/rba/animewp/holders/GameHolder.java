package com.rba.animewp.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.rba.animewp.R;

public class GameHolder extends RecyclerView.ViewHolder {
    public  ImageView gameimage;
    ProgressBar progBar;
    public TextView  gamename;
    public RelativeLayout game;

    public GameHolder(@NonNull View itemView) {
        super(itemView);
        gameimage = itemView.findViewById(R.id.imgFood);
        gamename = itemView.findViewById(R.id.loadtext);
        game = itemView.findViewById(R.id.game);
        progBar= itemView.findViewById(R.id.progressBar_wall_grid);
        progBar.setVisibility(View.GONE);
    }
}
