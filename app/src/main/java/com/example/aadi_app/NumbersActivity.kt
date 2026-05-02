package com.example.aadi_app

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class NumbersActivity : Activity() {
    private lateinit var speech: SpeechHelper
    private lateinit var questionText: TextView
    private lateinit var starsText: TextView
    private lateinit var optionGreater: Button
    private lateinit var optionEqual: Button
    private lateinit var optionLess: Button
    private val handler = Handler(Looper.getMainLooper())

    private var leftNumber = 1
    private var rightNumber = 1
    private var correctSymbol = "="

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_numbers)

        speech = SpeechHelper(this)
        questionText = findViewById(R.id.txtNumberQuestion)
        starsText = findViewById(R.id.txtNumberStars)
        optionGreater = findViewById(R.id.btnGreater)
        optionEqual = findViewById(R.id.btnEqual)
        optionLess = findViewById(R.id.btnLess)

        optionGreater.setOnClickListener {
            it.playTap()
            checkAnswer(">")
        }
        optionEqual.setOnClickListener {
            it.playTap()
            checkAnswer("=")
        }
        optionLess.setOnClickListener {
            it.playTap()
            checkAnswer("<")
        }
        findViewById<Button>(R.id.btnSpeakNumbers).setOnClickListener {
            it.playTap()
            speakQuestion()
        }

        showQuestion()
    }

    private fun showQuestion() {
        leftNumber = (1..100).random()
        rightNumber = if ((1..5).random() == 1) leftNumber else (1..100).random()
        correctSymbol = when {
            leftNumber > rightNumber -> ">"
            leftNumber < rightNumber -> "<"
            else -> "="
        }

        starsText.text = "⭐ ${RewardStore.getStars(this)}"
        questionText.text = "$leftNumber   ?   $rightNumber"
        speakQuestion()
    }

    private fun checkAnswer(selectedSymbol: String) {
        val correctWord = symbolToWords(correctSymbol)
        questionText.text = "$leftNumber   $selectedSymbol   $rightNumber"

        if (selectedSymbol == correctSymbol) {
            val stars = RewardStore.addStar(this)
            Toast.makeText(this, "Good Job Aadi!", Toast.LENGTH_SHORT).show()
            speech.speak("Good Job Aadi. $leftNumber is $correctWord $rightNumber. You have $stars stars.")
            showNextQuestionSoon()
        } else {
            speech.speak("Oh, this was $correctWord. Try again next time.")
            Toast.makeText(this, "Try again next time", Toast.LENGTH_SHORT).show()
            showNextQuestionSoon()
        }
    }

    private fun showNextQuestionSoon() {
        setOptionsEnabled(false)
        handler.postDelayed({
            setOptionsEnabled(true)
            showQuestion()
        }, 6200)
    }

    private fun setOptionsEnabled(enabled: Boolean) {
        optionGreater.isEnabled = enabled
        optionEqual.isEnabled = enabled
        optionLess.isEnabled = enabled
    }

    private fun speakQuestion() {
        speech.speak("Which sign goes between $leftNumber and $rightNumber? Greater than, less than, or equal to?")
    }

    private fun symbolToWords(symbol: String): String {
        return when (symbol) {
            ">" -> "greater than"
            "<" -> "less than"
            else -> "equal to"
        }
    }

    override fun onDestroy() {
        handler.removeCallbacksAndMessages(null)
        speech.shutdown()
        super.onDestroy()
    }
}
