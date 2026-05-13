package com.example.moodtap

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.PopupWindow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.app.DatePickerDialog
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    //elements of my app that will be used later in the code
    private lateinit var dateEntry: EditText
    private lateinit var nameEntry: EditText
    private lateinit var moodDescription: EditText
    private lateinit var neutralMoodBtn: ImageButton
    private lateinit var happyMoodBtn: ImageButton
    private lateinit var sadMoodBtn: ImageButton
    private lateinit var angryMoodBtn: ImageButton
    private lateinit var saveButton: Button
    private lateinit var finishButton: Button
    private lateinit var stickerMenuBtn: ImageButton

    //variable for the mood that will be selected
    private var selectedMood: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //element ids
        dateEntry = findViewById(R.id.dateEntry)
        nameEntry = findViewById(R.id.nameEntry)
        moodDescription = findViewById(R.id.moodDescription)
        neutralMoodBtn = findViewById(R.id.neutralMoodBtn)
        happyMoodBtn = findViewById(R.id.happyMoodBtn)
        sadMoodBtn = findViewById(R.id.sadMoodBtn)
        angryMoodBtn = findViewById(R.id.angryMoodBtn)
        saveButton = findViewById(R.id.saveBtn)
        finishButton = findViewById(R.id.finishBtn)
        stickerMenuBtn = findViewById(R.id.stickerMenuBtn)

        //updates the mood the user chooses
        neutralMoodBtn.setOnClickListener { updateMood("Neutral", neutralMoodBtn) }
        happyMoodBtn.setOnClickListener { updateMood("Happy", happyMoodBtn) }
        sadMoodBtn.setOnClickListener { updateMood("Sad", sadMoodBtn) }
        angryMoodBtn.setOnClickListener { updateMood("Angry", angryMoodBtn) }

        //grabs the info the user put in/ mood they chose and saves user input into shared preferences
        saveButton.setOnClickListener {
            saveJournalEntry()
        }

        //takes you to the moodEntry activity where the user's input is displayed
        finishButton.setOnClickListener {
            val intent = Intent(this, moodEntry ::class.java)
            startActivity(intent)
        }


        // takes you to the sticker page/activity
        stickerMenuBtn.setOnClickListener {
            val intent = Intent(this, stickers::class.java)
            startActivity(intent)
        }

        //information pop up
        val infoButton : ImageButton = findViewById(R.id.informationBtn)
        infoButton.setOnClickListener {
            val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val popupView = inflater.inflate(R.layout.activity_info_popup, null)
            val instructWindow = PopupWindow(popupView, 1000, 1300, true)
            instructWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0)

            //the close button on the instructions screen will close the popup
            val closeButton : Button = popupView.findViewById(R.id.closeInfoBtn)
            closeButton.setOnClickListener {
                instructWindow.dismiss()
            }
        }


        // date entry box
        dateEntry = findViewById(R.id.dateEntry)

        // allows users to pick the date rather than type it
        dateEntry.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            //creates the calender pop up
            val datePickerDialog = DatePickerDialog(
                //grabs the user's input once they pick a date and press ok
                this, { _, selectedYear, selectedMonth, selectedDay ->
                    // this formats the date as DD/MM/YYYY
                    //+1 because the computer counts from 0
                    val formattedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                    //automatically types/updates the entry box with the proper formatted date
                    dateEntry.setText(formattedDate)
                },
                year, month, day
            )
            //shows the calendar on screen
            datePickerDialog.show()
        }
    }

    //if the user picks a mood, it will change the selected image/emoji to the highlighted version and reset the rest back to their neutral forms
    private fun updateMood(mood: String, selectedBtn: ImageButton) {
        neutralMoodBtn.setImageResource(R.drawable._neutral1)
        happyMoodBtn.setImageResource(R.drawable.happy1)
        sadMoodBtn.setImageResource(R.drawable.sad1)
        angryMoodBtn.setImageResource(R.drawable.angry1)

        when(mood) {
            "Neutral" -> neutralMoodBtn.setImageResource(R.drawable._neutral2)
            "Happy" -> happyMoodBtn.setImageResource(R.drawable.happy2)
            "Sad" -> sadMoodBtn.setImageResource(R.drawable.sad2)
            "Angry" -> angryMoodBtn.setImageResource(R.drawable.angry2)
        }
        //saves the mood that was picked
        selectedMood = mood
    }

    //this saves the user's info/input into the local phone storage shared preferences
    private fun saveJournalEntry() {
        //grabs the user's input for date name and mood description and converts them from editText objects to strings
        val date = dateEntry.text.toString()
        val name = nameEntry.text.toString()
        val description = moodDescription.text.toString()

        //access the shared preferences
        val sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        //specific keys and values to find them later
        editor.putString("date", date)
        editor.putString("name", name)
        editor.putString("mood_type", selectedMood)
        editor.putString("description", description)

        // applies the changes
        editor.apply()

        //toast message after wards to inform the user
        Toast.makeText(this, "Mood saved!", Toast.LENGTH_SHORT).show()
    }
}