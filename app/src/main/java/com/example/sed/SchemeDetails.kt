package com.example.sed


import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import com.google.android.gms.ads.AdView


class SchemeDetails : AppCompatActivity() {
    private var number: Int = 0
    private lateinit var  mAdView : AdView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scheme_details)
        mAdView = findViewById(R.id.adView)
        val actionbar=supportActionBar
        val str = intent.getStringExtra("schemeId")
        actionbar!!.title=intent.getStringExtra("schemeName")
        actionbar.setDisplayHomeAsUpEnabled(true)
        val schemeDetails = findViewById<TextView>(R.id.schemedetails)
        schemeDetails.text="Hello Alok"


        show_banner_ads(mAdView,this)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                NavUtils.navigateUpFromSameTask(this)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}