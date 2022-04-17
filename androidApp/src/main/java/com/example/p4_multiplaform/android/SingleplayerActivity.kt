package com.example.p4_multiplaform.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.LinearLayout
import android.widget.TextView

@Suppress("DEPRECATION")
class SingleplayerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_singleplayer)

        // Get the screen size
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        val width = displayMetrics.widthPixels

        // Get the board
        val linearLayoutBoard = findViewById<LinearLayout>(R.id.board)

        // Get the current player TextView
        val currentPlayer = findViewById<TextView>(R.id.player_name)

        // Get the message TextView
        val message = findViewById<TextView>(R.id.message)

        // Get the current color LinearLayout
        val currentColor = findViewById<LinearLayout>(R.id.currentPlayerCircleColor)

        val board = Singleplayer(
            this,
            linearLayoutBoard,
            width,
            currentPlayer,
            currentColor,
            message
        )

        // Get the restart_game button
        val reset = findViewById<TextView>(R.id.restart_game)

        // Set the onClickListener for the `restart` button
        reset.setOnClickListener {
            board.reset()
        }
    }
}