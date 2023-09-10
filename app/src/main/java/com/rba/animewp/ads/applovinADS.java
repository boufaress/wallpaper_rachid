package com.rba.animewp.ads;

import android.app.Activity;
import android.view.ViewGroup;
import android.widget.FrameLayout;


import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdListener;
import com.applovin.mediation.MaxAdViewAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxAdView;
import com.applovin.mediation.ads.MaxInterstitialAd;
import com.applovin.mediation.ads.MaxRewardedAd;
import com.applovin.mediation.nativeAds.MaxNativeAdListener;
import com.applovin.mediation.nativeAds.MaxNativeAdLoader;
import com.applovin.mediation.nativeAds.MaxNativeAdView;
import com.applovin.sdk.AppLovinSdk;
import com.applovin.sdk.AppLovinSdkConfiguration;
import com.rba.animewp.mData.settings;

public  class applovinADS {
    public static MaxAdView adView;
    public static MaxInterstitialAd interstitialAd;
    public static MaxNativeAdLoader nativeAdLoader;
    public static MaxAd             nativeAd;
    public static MaxRewardedAd rewardedAd;
    public static int           retryAttempt;
    public static void ApplovIninit (Activity activity){

        AppLovinSdk.getInstance( activity ).setMediationProvider( "max" );
        interstitialadload(activity);
        AppLovinSdk.initializeSdk( activity, new AppLovinSdk.SdkInitializationListener() {
            @Override
            public void onSdkInitialized(final AppLovinSdkConfiguration configuration)
            {
                // AppLovin SDK is initialized, start loading ads
                interstitialadload(activity);
        }
        } );
}
    public static void bannerapplovin (Activity activity,ViewGroup viewGroup){
        ApplovIninit(activity);
            String ApplovinBanner = settings.Bannerapplovine;
        adView = new MaxAdView( ApplovinBanner, activity );


        ViewGroup rootView = viewGroup;

        rootView.addView( adView );

        // Load the ad
        adView.loadAd();



        adView.setListener(new MaxAdViewAdListener() {
            @Override
            public void onAdExpanded(MaxAd maxAd) {

            }

            @Override
            public void onAdCollapsed(MaxAd maxAd) {

            }

            @Override
            public void onAdLoaded(MaxAd maxAd) {

            }

            @Override
            public void onAdDisplayed(MaxAd maxAd) {

            }

            @Override
            public void onAdHidden(MaxAd maxAd) {

            }

            @Override
            public void onAdClicked(MaxAd maxAd) {

            }

            @Override
            public void onAdLoadFailed(String s, MaxError maxError) {

            }

            @Override
            public void onAdDisplayFailed(MaxAd maxAd, MaxError maxError) {

            }
        });
        adView.loadAd();
    }

    public static void interstitialadload(Activity activity) {
        String interapplovin = settings.Interapplovine;

        interstitialAd = new MaxInterstitialAd(interapplovin, activity );
        MaxAdListener adListener= new MaxAdListener() {
            @Override
            public void onAdLoaded(MaxAd ad) {


            }

            @Override
            public void onAdDisplayed(MaxAd ad) {
                interstitialadload(activity);

            }

            @Override
            public void onAdHidden(MaxAd ad) {

            }

            @Override
            public void onAdClicked(MaxAd ad) {

            }

            @Override
            public void onAdLoadFailed(String adUnitId, MaxError error) {

            }

            @Override
            public void onAdDisplayFailed(MaxAd ad, MaxError error) {

            }
        };
        interstitialAd.setListener(adListener);
        interstitialAd.loadAd();


    }
    public static void showapplovine (Activity activity){

        if (interstitialAd.isReady()){
            interstitialAd.showAd();
        }
        else {
            interstitialadload(activity);
        }
    }


    public static void createNativeAd(Activity activity,FrameLayout frameLayout)
    {
        FrameLayout nativeAdContainer = frameLayout;
        String Nativeapplovin = settings.Nativeapplovine;
        nativeAdLoader = new MaxNativeAdLoader( Nativeapplovin, activity );
        nativeAdLoader.setNativeAdListener( new MaxNativeAdListener()
        {
            @Override
            public void onNativeAdLoaded(final MaxNativeAdView nativeAdView, final MaxAd ad)
            {
                // Clean up any pre-existing native ad to prevent memory leaks.
                if ( nativeAd != null )
                {
                    nativeAdLoader.destroy( nativeAd );
                }

                // Save ad for cleanup.
                nativeAd = ad;

                // Add ad view to view.
                nativeAdContainer.removeAllViews();
                nativeAdContainer.addView( nativeAdView );
            }

            @Override
            public void onNativeAdLoadFailed(final String adUnitId, final MaxError error)
            {
                // We recommend retrying with exponentially higher delays up to a maximum delay
            }

            @Override
            public void onNativeAdClicked(final MaxAd ad)
            {
                // Optional click callback
            }
        } );

        nativeAdLoader.loadAd();
    }

    public static void createNativeAdlarg(Activity activity,FrameLayout frameLayout)
    {
        FrameLayout nativeAdContainer = frameLayout;
        String Nativeapplovin = settings.Nativeapplovine_large;
        nativeAdLoader = new MaxNativeAdLoader( Nativeapplovin, activity );
        nativeAdLoader.setNativeAdListener( new MaxNativeAdListener()
        {
            @Override
            public void onNativeAdLoaded(final MaxNativeAdView nativeAdView, final MaxAd ad)
            {
                // Clean up any pre-existing native ad to prevent memory leaks.
                if ( nativeAd != null )
                {
                    nativeAdLoader.destroy( nativeAd );
                }

                // Save ad for cleanup.
                nativeAd = ad;

                // Add ad view to view.
                nativeAdContainer.removeAllViews();
                nativeAdContainer.addView( nativeAdView );
            }

            @Override
            public void onNativeAdLoadFailed(final String adUnitId, final MaxError error)
            {
                // We recommend retrying with exponentially higher delays up to a maximum delay
            }

            @Override
            public void onNativeAdClicked(final MaxAd ad)
            {
                // Optional click callback
            }
        } );

        nativeAdLoader.loadAd();
    }


}



