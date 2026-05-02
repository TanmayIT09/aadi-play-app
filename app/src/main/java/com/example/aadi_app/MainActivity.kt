package com.example.aadi_app

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btnRoutine).setOnClickListener {
            startActivity(Intent(this, RoutineActivity::class.java))
        }

        findViewById<Button>(R.id.btnEmotion).setOnClickListener {
            startActivity(Intent(this, EmotionActivity::class.java))
        }

        findViewById<Button>(R.id.btnReward).setOnClickListener {
            startActivity(Intent(this, RewardActivity::class.java))
        }
    }
}
