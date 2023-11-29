package com.example.sed


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class SchemeDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scheme_details)
        val schemeDetails = findViewById<TextView>(R.id.schemedetails)
        val intet: Intent
        val str = intent.getStringExtra("schemeId")
        schemeDetails.text=str
    }
}