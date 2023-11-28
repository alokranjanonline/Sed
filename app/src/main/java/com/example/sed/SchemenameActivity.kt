package com.example.sed

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.widget.TextView

class SchemenameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schemename)
        var receiver_msg: TextView = findViewById(R.id.txtSchemename)
        val intet: Intent
        val str = intent.getStringExtra("message_key")
        receiver_msg.text=str
    }
}