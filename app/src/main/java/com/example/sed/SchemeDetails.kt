package com.example.sed


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.android.gms.ads.AdView

class SchemeDetails : AppCompatActivity() {
    private var number: Int = 0
    private lateinit var  mAdView : AdView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scheme_details)
        mAdView = findViewById(R.id.adView)
        val actionbar=supportActionBar
        val schemeDetails = findViewById<TextView>(R.id.schemedetails)
        val intet: Intent
        val str = intent.getStringExtra("schemeId")
        schemeDetails.text=str
        actionbar!!.title=intent.getStringExtra("schemeName")
        show_banner_ads(mAdView,this)
    }
}