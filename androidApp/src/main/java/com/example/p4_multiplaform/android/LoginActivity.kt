package com.example.p4_multiplaform.android

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class LoginActivity : AppCompatActivity() {

    private fun login(player_1: String, player_2: String) {
        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra("player_1", player_1)
        intent.putExtra("player_2", player_2)
        startActivity(intent)
        // save player names in shared preferences
        val sharedPref = getSharedPreferences("player_names", 0)
        val editor = sharedPref.edit()
        editor.putString("player_1", player_1)
        editor.putString("player_2", player_2)
        editor.apply()
    }

    private fun checkInputBeforeButtonClick(username: String, password: String) {
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
        } else {
            login(username, password)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val player1 = findViewById<android.widget.EditText>(R.id.player_1_name)
        val player2 = findViewById<android.widget.EditText>(R.id.player_2_name)
        val loginButton = findViewById<android.widget.Button>(R.id.startButton)

        loginButton.setOnClickListener {
            checkInputBeforeButtonClick(player1.text.toString(), player2.text.toString())
        }


    }



}