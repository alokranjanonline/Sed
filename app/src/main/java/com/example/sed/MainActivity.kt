package com.example.sed

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.ComponentActivity
import java.util.Timer
import kotlin.concurrent.timerTask

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mAdView = findViewById(R.id.adView)
        mAdView.setVisibility(View.INVISIBLE)
        val buttonCSActivity: Button = findViewById(R.id.homestart)
        buttonCSActivity.setOnClickListener {
            val intent = Intent(this, CSActivity::class.java)
            startActivity(intent)
        }
        Timer().schedule(timerTask {
            val intent = Intent(this@MainActivity, CSActivity::class.java)
            startActivity(intent)
        }, 2000)

    }
}
