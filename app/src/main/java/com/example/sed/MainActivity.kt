package com.example.sed

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.sed.ui.theme.SedTheme
import java.util.Timer
import java.util.logging.Handler
import kotlin.concurrent.timerTask

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /*var timer:Timer
        timer.schedule(2000) {
            val intent = Intent(this, CSActivity::class.java)
            startActivity(intent)
        }*/
        Timer().schedule(timerTask {
            val intent = Intent(this@MainActivity, CSActivity::class.java)
            startActivity(intent)
        }, 2000)
    }
}
