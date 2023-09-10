package com.rba.animewp;


import androidx.multidex.MultiDexApplication;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.rba.animewp.mData.constant;
import com.rba.animewp.mData.items_guide;
import com.rba.animewp.mData.settings;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AppHelper extends MultiDexApplication {
    RequestQueue queue;
    public static String url_game;

private String name,gamename;
private String image,gameimage;
private String AppUrl,gameurl;


    @Override
    public void onCreate() {
        super.onCreate();

        Getdata ();
    }

    private void  Getdata () {
        queue = Volley.newRequestQueue(this);
        JsonObjectRequest request= new JsonObjectRequest(Request.Method.GET, settings.file_Link, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject data = response.getJSONObject("data");
                    constant.customtext = data.getString("customtext");
                    constant.customtext1 = data.getString("customtext1");

                    constant.customtext_color = data.getString("customtext_color");
                    constant.customtext1_color = data.getString("customtext1_color");
                    constant.AppUrl = data.getString("AppUrl");




                    JSONObject ads = response.getJSONObject("Json_Data");
                    settings.ironsourceid = ads.getString("ironsource");
                    settings.Bannerapplovine = ads.getString("Bannerapplovine");
                    settings.Interapplovine = ads.getString("Interapplovine");
                    settings.Nativeapplovine = ads.getString("Nativeapplovine");
                    settings.cpabanner = ads.getInt("cpabanner");
                    settings.ImageBannerImg = ads.getString("ImageBannerImg");
                    settings.ImageBannerURL = ads.getString("ImageBannerURL");
                    settings.ADSremot = ads.getInt("ADSremot");
                    settings.Nativeapplovine_large = ads.getString("Nativeapplovine_large");
                    constant.nombre_nativeCategory = ads.getInt("nombre_nativeCategory");
                    constant.nombre_nativedetail = ads.getInt("nombre_nativedetail");

                    JSONArray GuideContent = response.getJSONArray("GuideContent");
                    for (int i=0;i < GuideContent.length();i++){

                        JSONObject appf = GuideContent.getJSONObject(i) ;
                        items_guide items_guide1 = new items_guide();
                        name = appf.getString("title");
                        items_guide1.setName(name);
                        items_guide1.setImageurl(appf.getString("imageurl"));
                        items_guide1.setTextcolor(appf.getString("Textcolor"));
                        settings.arrayList.add(items_guide1);
                    }






                    settings.statue = 1;
                }
                catch (JSONException e){
                    e.printStackTrace();
                    settings.statue = 2;
            }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                settings.statue =2;
            }
        });
        request.setShouldCache(false);
        queue.add(request);
    }



}
