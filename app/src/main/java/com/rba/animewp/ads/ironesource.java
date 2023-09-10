package com.rba.animewp.ads;

import android.app.Activity;


import com.ironsource.mediationsdk.IronSource;
import com.ironsource.mediationsdk.logger.IronSourceError;
import com.ironsource.mediationsdk.sdk.InitializationListener;
import com.ironsource.mediationsdk.sdk.InterstitialListener;
import com.rba.animewp.mData.settings;

public class ironesource {



    public static void initialize_ironSource (Activity activity){




        IronSource.init(activity, settings.ironsourceid, IronSource.AD_UNIT.INTERSTITIAL);
        IronSource.init(activity, settings.ironsourceid, IronSource.AD_UNIT.REWARDED_VIDEO);
        //Init Interstitial
        IronSource.init(activity, settings.ironsourceid, IronSource.AD_UNIT.INTERSTITIAL);
        //Init Offerwall
        IronSource.init(activity, settings.ironsourceid, IronSource.AD_UNIT.OFFERWALL);
        //Init Banner
        IronSource.init(activity, settings.ironsourceid, IronSource.AD_UNIT.BANNER);
        IronSource.loadInterstitial();
        IronSource.init(activity, settings.ironsourceid, new InitializationListener() {
            @Override
            public void onInitializationComplete() {

            }
        });


        applovinADS.ApplovIninit(activity);

    }
    public static void interstitial_ironSource (Activity activity) {
        if (IronSource.isInterstitialReady()){
            // Toast.makeText(activity, "ironsource inter show", Toast.LENGTH_SHORT).show();
            IronSource.showInterstitial();
            ironesource.lowdinter(activity);
        }
        else  {
            applovinADS.showapplovine(activity);
            IronSource.loadInterstitial();

        }



    }
    public static void lowdinterAdfeneched (Activity activity) {
        IronSource.loadInterstitial();

        IronSource.setInterstitialListener(new InterstitialListener() {

            @Override
            public void onInterstitialAdReady() {

                IronSource.showInterstitial();


            }


            @Override
            public void onInterstitialAdLoadFailed(IronSourceError error) {



            }


            @Override
            public void onInterstitialAdOpened() {
            }

            /*
             * Invoked when the ad is closed and the user is about to return to the application.
             */
            @Override
            public void onInterstitialAdClosed() {





            }


            @Override
            public void onInterstitialAdShowFailed(IronSourceError error) {




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

    public static void lowdinter (Activity activity) {
        IronSource.loadInterstitial();

        IronSource.setInterstitialListener(new InterstitialListener() {

            @Override
            public void onInterstitialAdReady() {

                IronSource.showInterstitial();


            }


            @Override
            public void onInterstitialAdLoadFailed(IronSourceError error) {



            }


            @Override
            public void onInterstitialAdOpened() {
            }

            /*
             * Invoked when the ad is closed and the user is about to return to the application.
             */
            @Override
            public void onInterstitialAdClosed() {





            }


            @Override
            public void onInterstitialAdShowFailed(IronSourceError error) {




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
















}
