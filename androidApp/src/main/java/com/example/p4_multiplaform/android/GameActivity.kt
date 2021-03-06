package com.example.p4_multiplaform.android

import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

@Suppress("DEPRECATION")
class GameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        // Get the screen size
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        val width = displayMetrics.widthPixels

        // get the player names
        val player1 = intent.getStringExtra("namePlayer1").toString()
        val player2 = intent.getStringExtra("namePlayer2").toString()

        // Get the board
        val linearLayoutBoard = findViewById<LinearLayout>(R.id.board)

        // Get the current player TextView
        val currentPlayer = findViewById<TextView>(R.id.player_name)

        // Get the message TextView
        val message = findViewById<TextView>(R.id.message)

        // Get the current color LinearLayout
        val currentColor = findViewById<LinearLayout>(R.id.currentPlayerCircleColor)

        val board = Multiplayer(
            this,
            linearLayoutBoard,
            width,
            player1,
            player2,
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

