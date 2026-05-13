package com.example.moodtap

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class stickers : AppCompatActivity() {
    // all the stickers and their variables
    private lateinit var bowBtn: ImageButton
    private lateinit var appleBtn: ImageButton
    private lateinit var heartBtn: ImageButton
    private lateinit var starBtn: ImageButton
    private lateinit var exclamationBtn: ImageButton
    private lateinit var swirlyBtn: ImageButton
    private lateinit var saveButton: Button
    private lateinit var closeBtn: ImageButton

    // string for keeping check of which sticker was picked by the user
    private var selectedSticker: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stickers)

        //element's IDS
        bowBtn = findViewById(R.id.bow)
        appleBtn = findViewById(R.id.apple)
        heartBtn = findViewById(R.id.heart)
        starBtn = findViewById(R.id.star)
        exclamationBtn = findViewById(R.id.exclamationMark)
        swirlyBtn = findViewById(R.id.swirly)
        saveButton = findViewById(R.id.saveStickerBtn)
        closeBtn = findViewById(R.id.exitStickerBtn)

        // when a user picks a sticker, it updates the selectedSticker
        bowBtn.setOnClickListener { updateSticker("Bow") }
        appleBtn.setOnClickListener { updateSticker("Apple") }
        heartBtn.setOnClickListener { updateSticker("Heart") }
        starBtn.setOnClickListener { updateSticker("Star") }
        exclamationBtn.setOnClickListener { updateSticker("Exclamation") }
        swirlyBtn.setOnClickListener { updateSticker("Swirly") }

        // closes the activity when a user presses save
        closeBtn.setOnClickListener {
            finish()
        }

        // same save button logic used for the shared preferences
        saveButton.setOnClickListener {
            val sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()

            editor.putString("selected_sticker", selectedSticker)
            editor.apply()

            Toast.makeText(this, "Sticker added!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    //helper function which just temporarily updates the selectedSticker with the word of the sticker the user chose
    private fun updateSticker(stickerName: String) {
        selectedSticker = stickerName
    }
}