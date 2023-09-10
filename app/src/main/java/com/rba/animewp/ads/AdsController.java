package com.rba.animewp.ads;

import static com.unity3d.services.core.properties.ClientProperties.getApplicationContext;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxInterstitialAd;
import com.bumptech.glide.Glide;

import com.ironsource.mediationsdk.IronSource;
import com.ironsource.mediationsdk.logger.IronSourceError;
import com.ironsource.mediationsdk.sdk.InterstitialListener;
import com.rba.animewp.R;
import com.rba.animewp.mData.settings;
import com.unity3d.ads.IUnityAdsLoadListener;
import com.unity3d.ads.IUnityAdsShowListener;
import com.unity3d.ads.UnityAds;

public class AdsController {
    public static boolean unity_test = false;
    public static String unity_banner = "Banner_Android";
    public static String unity_interstitial = "Interstitial_Android";
    public static String unity_Rewarded = "Rewarded_Android";
    public static String unity_game_id = "5302519";

    public static void initialiseads (Activity activity){
        if(settings.ADSremot == 1  ){
            ironesource.initialize_ironSource(activity);
        }
        else{
            applovinADS.ApplovIninit(activity);
            UnityAds.initialize(getApplicationContext(), unity_game_id, unity_test);

        }


    }

    public interface Adfinished{
        void onADfinished();

    }


    public static void BannerAdsCreateur (Activity activity, ViewGroup viewGroup, ImageView imageView){
            if (settings.cpabanner == 1)
            {
                imageView.setVisibility(View.VISIBLE);
                Glide.with(activity).load(settings.ImageBannerImg)
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.mipmap.ic_launcher)
                        .into(imageView);

                viewGroup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri uri = Uri.parse(settings.ImageBannerURL); // missing 'http://' will cause crashed
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        activity.startActivity(intent);
                    }
                });
            }
else {

                applovinADS.bannerapplovin(activity,viewGroup);

            }

}



    public static void Showinter (Activity activity,Adfinished adfinished){

        Loadingads.StartDialoge(activity);

        if(settings.ADSremot == 1  ){


            IronSource.loadInterstitial();

            IronSource.setInterstitialListener(new InterstitialListener() {

                @Override
                public void onInterstitialAdReady() {
                    IronSource.showInterstitial();



                }


                @Override
                public void onInterstitialAdLoadFailed(IronSourceError error) {
                    Loadingads.closeDialoge(activity);
                    adfinished.onADfinished();

                }


                @Override
                public void onInterstitialAdOpened() {
                }

                /*
                 * Invoked when the ad is closed and the user is about to return to the application.
                 */
                @Override
                public void onInterstitialAdClosed() {

                    adfinished.onADfinished();

                    Loadingads.closeDialoge(activity);

                }


                @Override
                public void onInterstitialAdShowFailed(IronSourceError error) {

                    adfinished.onADfinished();
                    Loadingads.closeDialoge(activity);

                }

                /*
                 * Invoked when the end user clicked on the interstitial ad, for supported networks only.
                 */
                @Override
                public void onInterstitialAdClicked() {
                }


                @Override
                public void onInterstitialAdShowSucceeded() {
                }
            });



        }
        else if(settings.ADSremot == 3){


            IUnityAdsLoadListener adsLoadListener = new IUnityAdsLoadListener() {
                @Override
                public void onUnityAdsAdLoaded(String placementId) {
                    UnityAds.show(activity, unity_interstitial, new IUnityAdsShowListener() {
                        @Override
                        public void onUnityAdsShowFailure(String placementId, UnityAds.UnityAdsShowError error, String message) {
                            adfinished.onADfinished();
                            Loadingads.closeDialoge(activity);
                        }

                        @Override
                        public void onUnityAdsShowStart(String placementId) {

                        }

                        @Override
                        public void onUnityAdsShowClick(String placementId) {

                        }

                        @Override
                        public void onUnityAdsShowComplete(String placementId, UnityAds.UnityAdsShowCompletionState state) {
                            adfinished.onADfinished();
                            Loadingads.closeDialoge(activity);
                        }
                    } );
                }

                @Override
                public void onUnityAdsFailedToLoad(String placementId, UnityAds.UnityAdsLoadError error, String message) {
                    adfinished.onADfinished();
                }
            };


            UnityAds.load(unity_interstitial, adsLoadListener);

        }
        else {
            MaxInterstitialAd interstitialAd = new MaxInterstitialAd( settings.Interapplovine, activity );
            interstitialAd.setListener(new MaxAdListener() {
                @Override
                public void onAdLoaded(MaxAd ad) {
                    interstitialAd.showAd();

                }

                @Override
                public void onAdDisplayed(MaxAd ad) {

                }

                @Override
                public void onAdHidden(MaxAd ad) {
                    adfinished.onADfinished();
                    Loadingads.closeDialoge(activity);
                }

                @Override
                public void onAdClicked(MaxAd ad) {

                }

                @Override
                public void onAdLoadFailed(String adUnitId, MaxError error) {
                    adfinished.onADfinished();
                    Loadingads.closeDialoge(activity);
                }

                @Override
                public void onAdDisplayFailed(MaxAd ad, MaxError error) {
                    adfinished.onADfinished();
                    Loadingads.closeDialoge(activity);
                }
            });

            // Load the first ad
            interstitialAd.loadAd();
        }


}
    public static void adsinter (Activity activity,Adfinished adfinished){



        if(settings.ADSremot == 1  ){


            IronSource.loadInterstitial();

            IronSource.setInterstitialListener(new InterstitialListener() {

                @Override
                public void onInterstitialAdReady() {
                    IronSource.showInterstitial();



                }


                @Override
                public void onInterstitialAdLoadFailed(IronSourceError error) {

                    adfinished.onADfinished();
                    Loadingads.closeDialoge(activity);
                }


                @Override
                public void onInterstitialAdOpened() {
                }

                /*
                 * Invoked when the ad is closed and the user is about to return to the application.
                 */
                @Override
                public void onInterstitialAdClosed() {

                    adfinished.onADfinished();

                    Loadingads.closeDialoge(activity);

                }


                @Override
                public void onInterstitialAdShowFailed(IronSourceError error) {

                    adfinished.onADfinished();

                    Loadingads.closeDialoge(activity);
                }

                /*
                 * Invoked when the end user clicked on the interstitial ad, for supported networks only.
                 */
                @Override
                public void onInterstitialAdClicked() {
                }


                @Override
                public void onInterstitialAdShowSucceeded() {
                }
            });



        }
        else {


            IUnityAdsLoadListener adsLoadListener = new IUnityAdsLoadListener() {
                @Override
                public void onUnityAdsAdLoaded(String placementId) {
                    UnityAds.show(activity, unity_interstitial, new IUnityAdsShowListener() {
                        @Override
                        public void onUnityAdsShowFailure(String placementId, UnityAds.UnityAdsShowError error, String message) {
                            adfinished.onADfinished();
                        }

                        @Override
                        public void onUnityAdsShowStart(String placementId) {

                        }

                        @Override
                        public void onUnityAdsShowClick(String placementId) {

                        }

                        @Override
                        public void onUnityAdsShowComplete(String placementId, UnityAds.UnityAdsShowCompletionState state) {
                            adfinished.onADfinished();

                        }
                    } );
                }

                @Override
                public void onUnityAdsFailedToLoad(String placementId, UnityAds.UnityAdsLoadError error, String message) {
                    adfinished.onADfinished();
                }
            };


            UnityAds.load(unity_interstitial, adsLoadListener);

        }



    }
    public static void Showinter1 (Activity activity){
        if(settings.ADSremot == 1  ){
            ironesource.initialize_ironSource(activity);
            ironesource.lowdinter(activity);

        }
        else{

            applovinADS.showapplovine(activity);

        }

    }



}