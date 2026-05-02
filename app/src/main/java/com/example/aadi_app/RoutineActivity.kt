package com.example.aadi_app

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class RoutineActivity : Activity() {
    private lateinit var speech: SpeechHelper
    private lateinit var routineText: TextView
    private lateinit var questionText: TextView
    private lateinit var starsText: TextView
    private lateinit var optionOne: Button
    private lateinit var optionTwo: Button
    private val handler = Handler(Looper.getMainLooper())

    private val routine = listOf(
        RoutineStep("Wake up", "🌞"),
        RoutineStep("Washroom", "🚽"),
        RoutineStep("Brush", "🪥"),
        RoutineStep("Breakfast", "🍽️"),
        RoutineStep("Bathe", "🛁"),
        RoutineStep("School", "🏫"),
        RoutineStep("Return from school", "🎒"),
        RoutineStep("Lunch", "🍛"),
        RoutineStep("Rest", "😌"),
        RoutineStep("The Hope", "🌈"),
        RoutineStep("Tea time", "☕"),
        RoutineStep("Play time", "⚽"),
        RoutineStep("Homework", "✏️"),
        RoutineStep("Cycle", "🚲"),
        RoutineStep("Dinner", "🍲"),
        RoutineStep("Sleep", "🌙")
    )

    private var currentIndex = 0
    private lateinit var correctStep: RoutineStep

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_routine)

        speech = SpeechHelper(this)
        routineText = findViewById(R.id.txtRoutine)
        questionText = findViewById(R.id.txtRoutineQuestion)
        starsText = findViewById(R.id.txtRoutineStars)
        optionOne = findViewById(R.id.btnRoutineOptionOne)
        optionTwo = findViewById(R.id.btnRoutineOptionTwo)

        findViewById<Button>(R.id.btnSpeakRoutine).setOnClickListener {
            it.playTap()
            speakQuestion()
        }

        showQuestion()
    }

    private fun showQuestion() {
        currentIndex = (0 until routine.lastIndex).random()
        val currentStep = routine[currentIndex]
        correctStep = routine[currentIndex + 1]
        val wrongStep = routine
            .filter { it.name != correctStep.name && it.name != currentStep.name }
            .random()
        val options = listOf(correctStep, wrongStep).shuffled()

        starsText.text = "⭐ ${RewardStore.getStars(this)}"
        routineText.text = "${currentStep.emoji}\n${currentStep.name}"
        questionText.text = "What comes next?"

        optionOne.text = "${options[0].emoji} ${options[0].name}"
        optionTwo.text = "${options[1].emoji} ${options[1].name}"

        optionOne.setOnClickListener {
            it.playTap()
            checkAnswer(options[0])
        }
        optionTwo.setOnClickListener {
            it.playTap()
            checkAnswer(options[1])
        }

        speakQuestion()
    }

    private fun checkAnswer(selected: RoutineStep) {
        if (selected.name == correctStep.name) {
            val stars = RewardStore.addStar(this)
            Toast.makeText(this, "Good Job Aadi!", Toast.LENGTH_SHORT).show()
            speech.speak("Good Job Aadi. You earned one star. Now you have $stars stars.")
            showNextQuestionSoon()
        } else {
            Toast.makeText(this, "Try again next time", Toast.LENGTH_SHORT).show()
            speech.speak("Oh, this was ${correctStep.name}. Try again next time.")
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

    private fun speakQuestion() {
        val currentStep = routine[currentIndex]
        speech.speak("${currentStep.name}. What comes next?")
    }

    override fun onDestroy() {
        handler.removeCallbacksAndMessages(null)
        speech.shutdown()
        super.onDestroy()
    }
}

data class RoutineStep(
    val name: String,
    val emoji: String
)
