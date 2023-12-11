package com.example.sed

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.sed.MainActivity.Companion.adError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import org.json.JSONObject
import org.w3c.dom.Text


lateinit var  mAdView : AdView
private var mInterstitialAd: InterstitialAd? = null
var adRequest = AdRequest.Builder().build()
var number: Int = 0
fun loadInterestitialAd(context: Context, /*holder:CustomAdapter.ViewHolder,*/ schemeId:Int,schemeName:String,stateId:Int){
    InterstitialAd.load(context,"ca-app-pub-3940256099942544/1033173712", adRequest, object : InterstitialAdLoadCallback() {
        override fun onAdFailedToLoad(adError: LoadAdError) {
            mInterstitialAd = null
            //holder.textView.text="Not Loaded"
        }

        override fun onAdLoaded(interstitialAd: InterstitialAd) {
            mInterstitialAd = interstitialAd
            //holder.textView.text="Ad Loaded"
        }
    })
}
fun showInterestitialAd(context: Context,/*holder:CustomAdapter.ViewHolder,*/ activity: Activity,schemeId:Int,schemeName:String,stateId:Int){
    mInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
        override fun onAdClicked() {
            // Called when a click is recorded for an ad.
            //holder.textView.text="Ad was clicked."
            Log.d(ContentValues.TAG, "Ad was clicked.")
        }

        override fun onAdDismissedFullScreenContent() {
            // Called when ad is dismissed.
            Log.d(ContentValues.TAG, "Ad dismissed fullscreen content.")
            //holder.textView.text="Ad dismissed fullscreen content."
            mInterstitialAd = null
            val intent = Intent(context, activity::class.java)
            intent.putExtra("message_key", "Hello Alok")
            intent.putExtra("schemeId", schemeId)
            intent.putExtra("schemeName", schemeName)
            intent.putExtra("stateId", stateId)
            context.startActivity(intent)
        }

        /*override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
            // Called when ad fails to show.
            textActivity.text="Ad failed to show fullscreen content."
            mInterstitialAd = null
        }*/

        override fun onAdImpression() {
            // Called when an impression is recorded for an ad.
            Log.d(ContentValues.TAG, "Ad recorded an impression.")
            //holder.textView.text="Ad recorded an impression."
        }

        override fun onAdShowedFullScreenContent() {
            // Called when ad is shown.
            //holder.textView.text="Ad showed fullscreen content."
            Log.d(ContentValues.TAG, "Ad showed fullscreen content.")
        }
    }
    if (mInterstitialAd != null) {
        mInterstitialAd?.show( context as Activity)

    } else {
        //holder.textView.text="The interstitial ad wasn't ready yet."
        val intent = Intent(context, activity::class.java)
        intent.putExtra("schemeId", schemeId)
        intent.putExtra("schemeName", schemeName)
        context.startActivity(intent)
    }
}
fun show_banner_ads(mAdView:AdView,context:Context ) {
    MobileAds.initialize(context) {}
    val adRequest = AdRequest.Builder().build()
    mAdView.loadAd(adRequest)
}
fun checkForInternet(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    // if the android version is equal to M
    // or greater we need to use the
    // NetworkCapabilities to check what type of
    // network has the internet connection
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

        // Returns a Network object corresponding to
        // the currently active default data network.
        val network = connectivityManager.activeNetwork ?: return false

        // Representation of the capabilities of an active network.
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

        return when {
            // Indicates this network uses a Wi-Fi transport,
            // or WiFi has network connectivity
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

            // Indicates this network uses a Cellular transport. or
            // Cellular has network connectivity
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

            // else return false
            else -> false
        }
    } else {
        // if the android version is below M
        @Suppress("DEPRECATION") val networkInfo =
            connectivityManager.activeNetworkInfo ?: return false
        @Suppress("DEPRECATION")
        return networkInfo.isConnected
    }
}

fun fetchSettingVariables(context: Context){
    val queue = Volley.newRequestQueue(context)
    var schemeSettings:ArrayList<String> = ArrayList()
    val url = "http://springtown.in/test/fetch_settings.php"
    val stringRequest = StringRequest( Request.Method.GET, url,
        { response ->
            val jsonObject= JSONObject(response)
            if(jsonObject.get("response").equals("sucess")){
                val jsonArray=jsonObject.getJSONArray("data")
                for(i in 0.. jsonArray.length()-1){
                    val jo=jsonArray.getJSONObject(i)
                    val settingId=jo.get("setting_id")
                    val settingName=jo.get("setting_name")
                    val settingKey=jo.get("setting_key")
                    val settingValue=jo.get("setting_value")
                    //val user=jo.jsonArray(setting_id,setting_name,setting_key,setting_value)
                    //val user=abc(settingId, settingName,settingKey,settingValue)
                    //schemeSettings.add(user)
                    //schemeSettings.add(settingId as Int, settingName as String,settingKey as String,settingValue as String)
                }
                //adapter.notifyDataSetChanged()
            }else{
                Toast.makeText(context, "Ad setting could not fetch...", Toast.LENGTH_SHORT).show()
            }
        },
        { adError="Ad setting could not fetch." })
    queue.add(stringRequest)
}