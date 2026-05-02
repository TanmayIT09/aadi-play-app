package com.example.aadi_app

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class EmotionActivity : Activity() {
    private lateinit var speech: SpeechHelper
    private lateinit var emojiText: TextView
    private lateinit var sentenceText: TextView
    private lateinit var starsText: TextView
    private lateinit var optionOne: Button
    private lateinit var optionTwo: Button
    private val handler = Handler(Looper.getMainLooper())

    private val emotions = listOf(
        Emotion("Happy", "happy", "😊"),
        Emotion("Sad", "sad", "😢"),
        Emotion("Crying", "crying", "😭"),
        Emotion("Angry", "angry", "😡"),
        Emotion("Sleepy", "sleepy", "😴"),
        Emotion("Shocked", "shocked", "😲"),
        Emotion("Surprised", "surprised", "😮"),
        Emotion("Wink", "winking", "😉"),
        Emotion("Love", "love", "😍"),
        Emotion("Scared", "scared", "😨")
    )

    private lateinit var correctEmotion: Emotion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emotion)

        speech = SpeechHelper(this)
        emojiText = findViewById(R.id.txtSelectedEmotion)
        sentenceText = findViewById(R.id.txtFeelingSentence)
        starsText = findViewById(R.id.txtEmotionStars)
        optionOne = findViewById(R.id.btnEmotionOptionOne)
        optionTwo = findViewById(R.id.btnEmotionOptionTwo)

        findViewById<Button>(R.id.btnSpeakFeeling).setOnClickListener {
            it.playTap()
            speech.speak("How does this face feel?")
        }

        showQuestion()
    }

    private fun showQuestion() {
        correctEmotion = emotions.random()
        val wrongEmotion = emotions.filter { it.name != correctEmotion.name }.random()
        val options = listOf(correctEmotion, wrongEmotion).shuffled()

        starsText.text = "⭐ ${RewardStore.getStars(this)}"
        emojiText.text = correctEmotion.emoji
        sentenceText.text = "How does this face feel?"

        optionOne.text = options[0].name
        optionTwo.text = options[1].name

        optionOne.setOnClickListener {
            it.playTap()
            checkAnswer(options[0])
        }
        optionTwo.setOnClickListener {
            it.playTap()
            checkAnswer(options[1])
        }

        speech.speak("How does this face feel?")
    }

    private fun checkAnswer(selected: Emotion) {
        if (selected.name == correctEmotion.name) {
            val stars = RewardStore.addStar(this)
            Toast.makeText(this, "Good Job Aadi!", Toast.LENGTH_SHORT).show()
            sentenceText.text = "I feel ${correctEmotion.spokenWord}"
            speech.speak("Good Job Aadi. I feel ${correctEmotion.spokenWord}. You have $stars stars.")
            showNextQuestionSoon()
        } else {
            Toast.makeText(this, "Try again next time", Toast.LENGTH_SHORT).show()
            speech.speak("Oh, this was ${correctEmotion.name} emotion. Try again next time.")
            showNextQuestionSoon()
        }
    }

    private fun showNextQuestionSoon() {
        optionOne.isEnabled = false
        optionTwo.isEnabled = false
        handler.postDelayed({
            optionOne.isEnabled = true
            optionTwo.isEnabled = true
            showQuestion()
        }, 2400)
    }

    override fun onDestroy() {
        handler.removeCallbacksAndMessages(null)
        speech.shutdown()
        super.onDestroy()
    }
}

data class Emotion(
    val name: String,
    val spokenWord: String,
    val emoji: String
)
