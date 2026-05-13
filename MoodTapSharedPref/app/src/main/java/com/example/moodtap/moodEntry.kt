package com.example.moodtap

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class moodEntry : AppCompatActivity() {

    // variables
    private lateinit var displayDate: TextView
    private lateinit var displayName: TextView
    private lateinit var displayDescription: TextView
    private lateinit var displayMoodImage: ImageView
    private lateinit var stickerView: ImageView
    private lateinit var goBackButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mood_entry)

        // element IDS
        displayDate = findViewById(R.id.displayDate)
        displayName = findViewById(R.id.displayName)
        displayDescription = findViewById(R.id.displayDescription)
        displayMoodImage = findViewById(R.id.displayMoodImage)
        stickerView = findViewById(R.id.stickerView)
        goBackButton = findViewById(R.id.goBackBtn)

        // calls the retrieve function for displaying the user's input
        retrieveJournalData()

        //returns to main activity while saving the memory
        goBackButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun retrieveJournalData() {
        //pulls the exact same shared preferences from main activity into this activity
        val sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE)

        // pulls the exact keys
        val savedDate = sharedPreferences.getString("date", "")
        val savedName = sharedPreferences.getString("name", "")
        val savedMood = sharedPreferences.getString("mood_type", "")
        val savedDesc = sharedPreferences.getString("description", "")
        val savedSticker = sharedPreferences.getString("selected_sticker", "")

        // display the user's chosen date and name (text)
        displayDate.text = savedDate
        displayName.text = savedName
        displayDescription.text = savedDesc

        // checks which mood was picked then sets the bright version in the mood image view
        when (savedMood) {
            "Neutral" -> displayMoodImage.setImageResource(R.drawable._neutral2)
            "Happy" -> displayMoodImage.setImageResource(R.drawable.happy2)
            "Sad" -> displayMoodImage.setImageResource(R.drawable.sad2)
            "Angry" -> displayMoodImage.setImageResource(R.drawable.angry2)
        }

        //same logic, check which sticker was oicked and update the sticker view
        when (savedSticker) {
            "Bow" -> stickerView.setImageResource(R.drawable.bowsticker)
            "Apple" -> stickerView.setImageResource(R.drawable.stickerapple)
            "Heart" -> stickerView.setImageResource(R.drawable.stickerheart)
            "Star" -> stickerView.setImageResource(R.drawable.stickerstar)
            "Exclamation" -> stickerView.setImageResource(R.drawable.stickerexclamark)
            "Swirly" -> stickerView.setImageResource(R.drawable.stickerswirly)
        }
    }
}