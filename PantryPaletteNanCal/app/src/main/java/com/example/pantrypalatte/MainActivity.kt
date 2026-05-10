package com.example.pantrypalatte

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
        // the ingredients have booleans which are currently turned off. It's like a memory system.
        private var hasCream = false
        private var hasMilk = false
        private var hasEggs = false
        private var hasStrawberries = false
        private var hasChocolate = false
        private var hasBakingPowder = false
        private var hasFlour = false
        private var hasSugar = false

        // initializes the media player
        private lateinit var clickSound: android.media.MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            // initializing the sound for ingredient clicks
            clickSound = android.media.MediaPlayer.create(this, R.raw.buttonclick)

            //instructions button logic
            // pressing on the 'instructions' button will inflate a pop up instructions activity in the center of the screen
            val instructButton : Button = findViewById(R.id.instructionsBtn)
            instructButton.setOnClickListener {
                val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
                val popupView = inflater.inflate(R.layout.instructions, null)
                val instructWindow = PopupWindow(popupView, 1000, 1600, true)
                instructWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0)

                //the close button on the instructions screen will dismiss/close the popup
                val closeButton : Button = popupView.findViewById(R.id.closeBtn)
                closeButton.setOnClickListener {
                    instructWindow.dismiss()
                }
            }
        }


        // once a button/ingredient is clicked, the boolean will turn true meaning it's added to the bowl
        fun ingredientClick(view: View) {
            // Play click sound feedback
            clickSound.seekTo(0)
            clickSound.start()

            when (view.id) {
                R.id.creamBtn -> hasCream = true
                R.id.milkBtn -> hasMilk = true
                R.id.eggsBtn -> hasEggs = true
                R.id.strawberryBtn -> hasStrawberries = true
                R.id.chocolateBtn -> hasChocolate = true
                R.id.bakingBtn -> hasBakingPowder = true
                R.id.flourBtn -> hasFlour = true
                R.id.sugarBtn -> hasSugar = true
            }
            // this confirms to the user they clicked something
            Toast.makeText(this, "Ingredient was added to bowl!", Toast.LENGTH_SHORT).show()
        }

    // 'get results' button logic
    //if the users has turned all the required booleans for a specific dish on, then it will display the name and image of the dish
    //dishes are put from most ingredients to least so the computer won't get confused and override certain dishes
    fun onGetResultsClick(view: View) {
        val displayLabel = findViewById<TextView>(R.id.resultText)
        val displayImage = findViewById<ImageView>(R.id.resultImage)

        // this variable tracks if a valid recipe was found
        var recipeFound = true

        // Check recipes from "most ingredients" to "least ingredients"
        if (hasBakingPowder && hasFlour && hasCream && hasEggs && hasStrawberries && hasSugar && hasMilk) {
            displayLabel.text = "Strawberry Shortcake!"
            displayImage.setImageResource(R.drawable.strawberryshortcake)
        }
        else if (hasFlour && hasSugar && hasMilk && hasEggs && hasBakingPowder && hasChocolate) {
            displayLabel.text = "Chocolate Cake!"
            displayImage.setImageResource(R.drawable.chocolatecake)
        }
        else if (hasFlour && hasSugar && hasMilk && hasEggs && hasBakingPowder) {
            displayLabel.text = "Bread!"
            displayImage.setImageResource(R.drawable.bread)
        }
        else if (hasFlour && hasSugar && hasMilk && hasEggs) {
            displayLabel.text = "Pancakes!"
            displayImage.setImageResource(R.drawable.pancake)
        }
        else if (hasCream && hasMilk && hasChocolate) {
            displayLabel.text = "Hot Chocolate!"
            displayImage.setImageResource(R.drawable.hotchocolate)
        }
        else if (hasChocolate && hasStrawberries) {
            displayLabel.text = "Chocolate Dipped Strawberries!"
            displayImage.setImageResource(R.drawable.chocolatestrawberries)
        }
        else {
            displayLabel.text = "Oops..Try again!"
            displayImage.setImageResource(R.drawable.wrongfood)
            recipeFound = false
        }

        // if user makes a dish then a 'good result' sound effect plays, else if not then a 'bad result' plays
        if (recipeFound) {
            val successSound = android.media.MediaPlayer.create(this, R.raw.goodresult)
            successSound.start()
            successSound.setOnCompletionListener { it.release() }
        } else {
            val failSound = android.media.MediaPlayer.create(this, R.raw.badresult)
            failSound.start()
            failSound.setOnCompletionListener { it.release() }
        }

        //resets the 'bowl' once a dish is made by pressing get results
        resetBowl()
    }

    //turns off all the booleans/ resets everything
    private fun resetBowl() {
        hasCream = false; hasMilk = false; hasEggs = false
        hasStrawberries = false; hasChocolate = false
        hasBakingPowder = false; hasFlour = false; hasSugar = false
    }

    //because sounds take up phone memory, it's released when the app is closed (got this tip online)
    override fun onDestroy() {
        super.onDestroy()
        if (::clickSound.isInitialized) {
            clickSound.release()
        }
    }
}