package com.example.sed

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class SchemeDetailsWebview : AppCompatActivity() {
    private lateinit var  mAdView : AdView
    private var number: Int = 0
    companion object {
        var globalUrl = "http://www.sportainmentdesign.com/"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scheme_details_webview)
        val myWebView: WebView = findViewById(R.id.myWebView)
        mAdView = findViewById(R.id.adView)
        val button1: Button =findViewById(R.id.button1)
        val alertMsg: TextView =findViewById(R.id.internetalert)
        if (checkForInternet(this)) {
            val progressBar: ProgressBar = findViewById(R.id.progress_bar)
            myWebView.settings.javaScriptEnabled = true
            myWebView.loadUrl(globalUrl)
            myWebView.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                    view.loadUrl(url)
                    globalUrl= myWebView.url.toString()
                    return true
                }

                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view,url,favicon)
                    //progress_bar.visibility = View.VISIBLE
                }

                override fun onPageFinished(view: WebView, url: String) {
                    super.onPageFinished(view, url)
                    //progress_bar.visibility = View.GONE
                }
                override fun onReceivedError(view: WebView, int: Int, description:String, failingUrl:String){
                    Toast.makeText(applicationContext, "No internet connection", Toast.LENGTH_LONG).show()
                    //myWebView.loadUrl("file:///android_asset/lost.html")
                }
            }
            myWebView.webChromeClient = object : WebChromeClient(){
                override fun onProgressChanged(view: WebView, newProgress:Int){
                    progressBar.visibility = View.VISIBLE
                    progressBar.progress = newProgress
                    if(newProgress==100){
                        progressBar.visibility = View.GONE
                    }
                    super.onProgressChanged(view, newProgress)
                }
            }

            //Swipe to refresh
            val swipeRefreshLayout: SwipeRefreshLayout = findViewById(R.id.swipe)
            swipeRefreshLayout.setOnRefreshListener {
                alertMsg.text = number++.toString()
                myWebView.loadUrl(globalUrl)
                swipeRefreshLayout.isRefreshing = false
            }



            show_banner_ads(mAdView,this)
        }


        onBackPressedDispatcher.addCallback(this /* lifecycle owner */, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                /*if (backPressedTime + 3000 > System.currentTimeMillis()) {
                    super.onBackPressed()
                    finish()
                } else {
                    Toast.makeText(this, "Press back again to leave the app.", Toast.LENGTH_LONG).show()
                }*/
                // Back is pressed... Finishing the activity
                if (myWebView.canGoBack()) {
                    myWebView.goBack()

                }else {
                    finish()
                }
            }
        })
    }


}