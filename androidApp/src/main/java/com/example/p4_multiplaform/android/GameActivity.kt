package com.example.p4_multiplaform.android

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class GameActivity : AppCompatActivity() {

    // Get the player's name saved in the shared preferences
    private fun getPlayerName(): String {
        val sharedPreferences = getSharedPreferences("player", MODE_PRIVATE)
        return sharedPreferences.getString("name", "") ?: ""
    }

    // Create 7x6 grid of circle buttons and add them to the layout
    @SuppressLint("UseCompatLoadingForDrawables")
    private fun createGrid(layout: LinearLayout) {
        for (i in 0..6) {
            val row = LinearLayout(this)
            row.orientation = LinearLayout.HORIZONTAL

            for (j in 0..5) {
                val button = Button(this)

                // set margin between buttons
                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                params.setMargins(10, 10, 10, 10)
                params.width = 140
                params.height = 140
                button.layoutParams = params
                button.background = getDrawable(R.drawable.circle)
                row.addView(button)
            }
            layout.addView(row)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        // Get the player's name
        val playerName = getPlayerName()

        // Get the text view and set the player's name
        val textView = findViewById<TextView>(R.id.player_name)
        textView.text = playerName


        // Create the grid
        val layout = findViewById<LinearLayout>(R.id.board)
        createGrid(layout)

    }
}