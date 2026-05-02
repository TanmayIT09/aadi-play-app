package com.example.aadi_app

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class RoutineActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_routine)

        findViewById<Button>(R.id.btnDone).setOnClickListener {
            val prefs = getSharedPreferences("aadi_prefs", MODE_PRIVATE)
            val currentStars = prefs.getInt("stars", 0)

            prefs.edit()
                .putInt("stars", currentStars + 1)
                .apply()

            Toast.makeText(this, "Great Job!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
