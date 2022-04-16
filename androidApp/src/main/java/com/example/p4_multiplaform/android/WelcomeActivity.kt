package com.example.p4_multiplaform.android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.util.*

fun switchPageAfterDelay(activity: AppCompatActivity, delay: Long) {
    Timer().schedule(object : TimerTask() {
        override fun run() {
            // Start the next activity after the delay
            activity.startActivity(Intent(activity, GameModeActivity::class.java))

            // Finish the current activity
            activity.finish()
        }
    }, delay)
}

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        // Call the function to switch to the next activity after a delay
        switchPageAfterDelay(this, 4000)
    }
}
