package com.rba.animewp.adapters;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.rba.animewp.R;
import com.rba.animewp.ads.AdsController;
import com.rba.animewp.holders.GameHolder;
import com.rba.animewp.mData.ConstantFile;
import com.rba.animewp.mData.items_wallpaper;
import com.rba.animewp.mData.settings;
import com.rba.animewp.page_1;
import com.rba.animewp.wallpaperCategory;
import com.rba.animewp.wallpaperPreview;

import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public Context context;
        int conteur =3;


    public GameAdapter(Context context, List<items_wallpaper> arrayL){
        this.context = context;

    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View ItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.wallpaperfavitem,parent,false);



        return new GameHolder(ItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        items_wallpaper s =  settings.arrayL.get(position);



        GameHolder gameHolder = (GameHolder) holder;





        Glide.with(context).load(s.getImageURL())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(gameHolder.gameimage);


        gameHolder.game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(conteur==3){

                        AdsController.Showinter((Activity) context, new AdsController.Adfinished() {
                            @Override
                            public void onADfinished() {
                                Intent intent = new Intent(context,wallpaperPreview.class);
                                intent.putExtra("position",position);
                                context.startActivity(intent);
                                SharedPreferences sharedPref = context.getSharedPreferences(ConstantFile.shared_pref_file_name, MODE_PRIVATE);
                                SharedPreferences.Editor meditor = sharedPref.edit();
                                //meditor.putString("w_id", s.getId());
                                //meditor.putString("w_name", s.getName());
                                // meditor.putString("w_image", s.getImageURL());
                                //meditor.putString("w_buttonview", "yes");
                                meditor.apply();
                                conteur=0;
                            }
                        });


                    }else {
                        Intent intent = new Intent(context,wallpaperPreview.class);
                        intent.putExtra("position",position);
                        context.startActivity(intent);
                        SharedPreferences sharedPref = context.getSharedPreferences(ConstantFile.shared_pref_file_name, MODE_PRIVATE);
                        SharedPreferences.Editor meditor = sharedPref.edit();
                        //meditor.putString("w_id", s.getId());
                        //meditor.putString("w_name", s.getName());
                        // meditor.putString("w_image", s.getImageURL());
                        //meditor.putString("w_buttonview", "yes");
                        meditor.apply();
                        conteur=conteur+1;
                    }



            }
        });

    }

    @Override
    public int getItemCount() {
        return settings.arrayL.size() ;
    }
}
