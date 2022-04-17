package com.example.p4_multiplaform.android

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Get the players names
        val player1 = findViewById<android.widget.EditText>(R.id.player_1_name)
        val player2 = findViewById<android.widget.EditText>(R.id.player_2_name)

        //  Get the start Button
        val btn = findViewById<android.widget.Button>(R.id.startButton)

        // Set the button on click listener
        btn.setOnClickListener {
            // Get the players names
            val namePlayer1 = player1.text.toString()
            val namePlayer2 = player2.text.toString()

            if(namePlayer1.isEmpty() || namePlayer2.isEmpty()){
                // Create a toast message
                val toast = android.widget.Toast.makeText(this, "Please enter your nicknames", android.widget.Toast.LENGTH_SHORT)
                // Show the toast message
                toast.show()

            }else{

                // Create the intent
                val intent = Intent(this, GameActivity::class.java)

                // Put the players names in the intent
                intent.putExtra("namePlayer1", namePlayer1)
                intent.putExtra("namePlayer2", namePlayer2)

                // Start the activity
                startActivity(intent)

                // Finish this activity
                finish()
            }
        }
    }
}