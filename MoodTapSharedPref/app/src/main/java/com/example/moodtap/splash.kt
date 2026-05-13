package com.example.moodtap

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.VideoView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val videoView = findViewById<VideoView>(R.id.videoView)

        // path to video in the raw folder
        val videoPath = "android.resource://" + packageName + "/" + R.raw.splashvid
        val uri = Uri.parse(videoPath)
        videoView.setVideoURI(uri)

        // starts the video
        videoView.start()

        // detects when video finishes
        videoView.setOnCompletionListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            // closes the splash activity so user can't go back to it
            finish()
        }
    }
}