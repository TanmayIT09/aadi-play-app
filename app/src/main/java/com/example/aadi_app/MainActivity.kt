package com.example.aadi_app

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button

class MainActivity : Activity() {
    private lateinit var speech: SpeechHelper
    private val tunePlayer = TunePlayer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        speech = SpeechHelper(this)

        findViewById<Button>(R.id.btnRoutine).setOnClickListener {
            it.playTap()
            speech.speak("My routine. First and then.")
            startActivity(Intent(this, RoutineActivity::class.java))
        }

        findViewById<Button>(R.id.btnEmotion).setOnClickListener {
            it.playTap()
            speech.speak("How I feel. Choose one feeling.")
            startActivity(Intent(this, EmotionActivity::class.java))
        }

        findViewById<Button>(R.id.btnReward).setOnClickListener {
            it.playTap()
            speech.speak("My stars. Let's count.")
            startActivity(Intent(this, RewardActivity::class.java))
        }

        findViewById<Button>(R.id.btnNumbers).setOnClickListener {
            it.playTap()
            speech.speak("Number game. Greater than, less than, or equal.")
            startActivity(Intent(this, NumbersActivity::class.java))
        }

        findViewById<Button>(R.id.btnPictureTalk).setOnClickListener {
            it.playTap()
            speech.speak("Picture talk. What is happening?")
            startActivity(Intent(this, PictureTalkActivity::class.java))
        }

        findViewById<Button>(R.id.btnSpeakHome).setOnClickListener {
            it.playTap()
            speech.speak("Welcome Aadi. This is your space to explore. All the best. Choose routine, feelings, numbers, picture talk, or stars.")
        }

        findViewById<Button>(R.id.btnTune).setOnClickListener {
            it.playTap()
            tunePlayer.playSaReGaMa()
        }
    }

    override fun onDestroy() {
        speech.shutdown()
        super.onDestroy()
    }
}
