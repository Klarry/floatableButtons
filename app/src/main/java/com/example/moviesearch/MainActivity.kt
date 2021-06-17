package com.example.moviesearch

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.moviesearch.movable_button.FloaterButton

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val micButton = findViewById<FloaterButton>(R.id.mic_button)
        val remoteButton = findViewById<FloaterButton>(R.id.remote_button)

        micButton.setOnLongClickListener {
            Log.d("MainActivity", "Long Click action")
            false
        }
    }
}