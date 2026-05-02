package com.example.aadi_app

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class EmotionActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emotion)

        findViewById<Button>(R.id.btnHappy).setOnClickListener {
            showMessage("That's great!")
        }

        findViewById<Button>(R.id.btnSad).setOnClickListener {
            showMessage("It's okay")
        }

        findViewById<Button>(R.id.btnAngry).setOnClickListener {
            showMessage("Take deep breath")
        }

        findViewById<Button>(R.id.btnSleepy).setOnClickListener {
            showMessage("Time to rest")
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
