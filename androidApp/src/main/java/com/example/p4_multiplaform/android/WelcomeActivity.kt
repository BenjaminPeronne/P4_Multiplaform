package com.example.p4_multiplaform.android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.util.*

fun switchPageAfterDelay(activity: AppCompatActivity, delay: Long) {
    Timer().schedule(object : TimerTask() {
        override fun run() {
            activity.startActivity(Intent(activity, LoginActivity::class.java))
            activity.finish()
        }
    }, delay)
}

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        switchPageAfterDelay(this, 4000)
    }
}
