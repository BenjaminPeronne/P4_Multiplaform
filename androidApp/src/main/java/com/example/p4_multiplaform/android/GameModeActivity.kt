package com.example.p4_multiplaform.android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class GameModeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_mode)

        // Get the two buttons from the layout
        val btnSolo = findViewById<android.widget.Button>(R.id.btn_play_alone)
        val btnMulti = findViewById<android.widget.Button>(R.id.btn_play_multi)

        // Set the onClickListener for the solo button
        btnSolo.setOnClickListener {
            // Create an intent to start the GameActivity
            val intent = Intent(this, SingleplayerActivity::class.java)
            intent.putExtra("modeSolo", "solo")

            // Start the GameActivity with the solo mode
            startActivity(intent)

            // Close the GameModeActivity
            finish()
        }

        // Set the onClickListener for the multi button
        btnMulti.setOnClickListener {
            // Create an intent to start the GameActivity
            val intent = Intent(this, LoginActivity::class.java)
            intent.putExtra("modeMulti", "multi")

            // Start the GameActivity with the multi mode
            startActivity(intent)

            // Close the GameModeActivity
            finish()
        }
    }
}