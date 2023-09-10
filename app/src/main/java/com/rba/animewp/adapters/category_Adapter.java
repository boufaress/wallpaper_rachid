package com.rba.animewp.adapters;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.rba.animewp.R;
import com.rba.animewp.ads.Loadingdata;
import com.rba.animewp.holders.categoryHolder;
import com.rba.animewp.mData.ConstantFile;
import com.rba.animewp.mData.items_guide;
import com.rba.animewp.mData.items_wallpaper;
import com.rba.animewp.mData.settings;
import com.rba.animewp.wallpaperdetail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class category_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public Context context;
    RequestQueue queue;
    public category_Adapter(Context context, List<items_guide> arrayList){
        this.context = context;

    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View ItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview,parent,false);



        return new categoryHolder(ItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {





        items_guide items_guide  =  settings.arrayList.get(position);
        categoryHolder moreAppHolder = (categoryHolder) holder;


        moreAppHolder.appname.setText(items_guide.getName());
        moreAppHolder.appname.setTextColor(Color.parseColor(items_guide.getTextcolor()));



        Glide.with(context).load(items_guide.getImageurl())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(moreAppHolder.appimage);




        moreAppHolder.app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                Getdata(position);
                //Toast.makeText(context, "colect data", Toast.LENGTH_SHORT).show();
                Loadingdata.StartDialoge((Activity) context);

                            Handler hndler = new Handler();
                            hndler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (settings.statue1==1){
                                    Intent intent = new Intent(context, wallpaperdetail.class);
                                    intent.putExtra("position", position);
                                    context.startActivity(intent);
                                        Loadingdata.closeDialoge((Activity) context);
                                    }
                                    else {
                                        hndler.postDelayed(this,100);
                                    }
                                }
                            },200);












                SharedPreferences sharedPref = context.getSharedPreferences(ConstantFile.shared_pref_file_name, MODE_PRIVATE);
                SharedPreferences.Editor meditor = sharedPref.edit();
                meditor.putString("g_title", items_guide.getName());
                meditor.apply();




                //  display_interstitial(activity);
            }
        });




    }

    @Override
    public int getItemCount() {
        return settings.arrayList.size() ;
    }


    private void  Getdata (int position) {
        queue = Volley.newRequestQueue(context);
        JsonObjectRequest request= new JsonObjectRequest(Request.Method.GET, settings.file_Link, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {


                    String name= "Collection"+String.valueOf(position);

                    JSONArray Collection1 = response.getJSONArray(name);
                    for (int i=0;i < Collection1.length();i++){

                        JSONObject Collec = Collection1.getJSONObject(i) ;

                        items_wallpaper items_wallpaper1 = new items_wallpaper();
                        items_wallpaper1.setId(Collec.getString("id"));
                        items_wallpaper1.setImageURL(Collec.getString("image"));
                        items_wallpaper1.setName(Collec.getString("name"));
                        settings.arrayL.add(items_wallpaper1);

                    }

                  //  Toast.makeText(context, name, Toast.LENGTH_SHORT).show();

                    settings.statue1 = 1;
                }
                catch (JSONException e){
                    e.printStackTrace();
                    settings.statue1 = 2;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                settings.statue1 = 2;
            }
        });
        request.setShouldCache(false);
        queue.add(request);
    }

}
