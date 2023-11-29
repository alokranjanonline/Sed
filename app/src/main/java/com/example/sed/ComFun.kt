package com.example.sed

import android.content.Context
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds



fun show_banner_ads(mAdView:AdView,context:Context ) {
    MobileAds.initialize(context) {}
    val adRequest = AdRequest.Builder().build()
    mAdView.loadAd(adRequest)
}
