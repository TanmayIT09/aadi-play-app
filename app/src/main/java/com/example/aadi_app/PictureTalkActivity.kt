package com.example.aadi_app

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class PictureTalkActivity : Activity() {
    private lateinit var speech: SpeechHelper
    private lateinit var sceneView: SceneDrawingView
    private lateinit var titleText: TextView
    private lateinit var starsText: TextView
    private lateinit var optionButtons: List<Button>

    private val scenes = listOf(
        PictureScene(
            id = "park",
            title = "Park",
            prompt = "What is happening in the park?",
            correctOptions = listOf("Kids are playing", "Swing is moving", "There is a slide"),
            wrongOptions = listOf("Car is stopping", "Teacher is writing")
        ),
        PictureScene(
            id = "school",
            title = "School",
            prompt = "What is happening at school?",
            correctOptions = listOf("Kids are going to school", "School bus is there", "Teacher is near board"),
            wrongOptions = listOf("Ball is on table", "Traffic light is red")
        ),
        PictureScene(
            id = "mall",
            title = "Mall",
            prompt = "What is happening in the mall?",
            correctOptions = listOf("People are shopping", "Shop is open", "Bag is in hand"),
            wrongOptions = listOf("Kids are on swing", "Stumps are standing")
        ),
        PictureScene(
            id = "road",
            title = "Road",
            prompt = "What is happening on the road?",
            correctOptions = listOf("Car is moving", "Traffic light is there", "People are crossing"),
            wrongOptions = listOf("Teacher is writing", "Table tennis is playing")
        ),
        PictureScene(
            id = "cricket",
            title = "Cricket",
            prompt = "What is happening in cricket?",
            correctOptions = listOf("Boy is batting", "Ball is coming", "Stumps are standing"),
            wrongOptions = listOf("People are shopping", "Swing is moving")
        ),
        PictureScene(
            id = "table_tennis",
            title = "Table Tennis",
            prompt = "What is happening in table tennis?",
            correctOptions = listOf("Two players are playing", "Ball is on table", "Racket is in hand"),
            wrongOptions = listOf("School bus is there", "Car is moving")
        ),
        PictureScene(
            id = "courtesy_thank_you",
            title = "Kind Words",
            prompt = "Someone says thank you. What should you say?",
            correctOptions = listOf("Welcome"),
            wrongOptions = listOf("Sorry", "Give me ball", "Good night", "I am angry")
        ),
        PictureScene(
            id = "courtesy_sorry",
            title = "Kind Words",
            prompt = "You did something wrong. What should you say?",
            correctOptions = listOf("Sorry"),
            wrongOptions = listOf("Welcome", "Good morning", "I want cycle", "I am sleepy")
        ),
        PictureScene(
            id = "courtesy_please",
            title = "Asking Nicely",
            prompt = "You need something. What should you say?",
            correctOptions = listOf("Please give me water"),
            wrongOptions = listOf("Go away", "I am sad", "Welcome", "Good night")
        ),
        PictureScene(
            id = "courtesy_help",
            title = "Asking Help",
            prompt = "You need help. What should you say?",
            correctOptions = listOf("Please help me"),
            wrongOptions = listOf("Thank you", "I am angry", "Good night", "Car is moving")
        ),
        PictureScene(
            id = "courtesy_greeting",
            title = "Greeting",
            prompt = "You meet your teacher in the morning. What should you say?",
            correctOptions = listOf("Good morning"),
            wrongOptions = listOf("Sorry", "I want ball", "Good night", "Look again")
        ),
        PictureScene(
            id = "courtesy_namaste",
            title = "Greeting",
            prompt = "When you meet someone, what should you say?",
            correctOptions = listOf("Namaste"),
            wrongOptions = listOf("Sorry", "I want water", "Good night", "Look again")
        )
    )

    private lateinit var currentScene: PictureScene
    private val selectedCorrectOptions = mutableSetOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_picture_talk)

        speech = SpeechHelper(this)
        sceneView = findViewById(R.id.sceneDrawingView)
        titleText = findViewById(R.id.txtPictureTitle)
        starsText = findViewById(R.id.txtPictureStars)
        optionButtons = listOf(
            findViewById(R.id.btnPictureOptionOne),
            findViewById(R.id.btnPictureOptionTwo),
            findViewById(R.id.btnPictureOptionThree),
            findViewById(R.id.btnPictureOptionFour),
            findViewById(R.id.btnPictureOptionFive)
        )

        findViewById<Button>(R.id.btnSpeakPicture).setOnClickListener {
            it.playTap()
            speech.speak(currentScene.prompt)
        }

        findViewById<Button>(R.id.btnNextPicture).setOnClickListener {
            it.playTap()
            showNewScene()
        }

        showNewScene()
    }

    private fun showNewScene() {
        currentScene = scenes.random()
        selectedCorrectOptions.clear()

        sceneView.setScene(currentScene.id)
        starsText.text = "⭐ ${RewardStore.getStars(this)}"
        titleText.text = currentScene.title

        val options = (currentScene.correctOptions + currentScene.wrongOptions).shuffled()
        optionButtons.forEachIndexed { index, button ->
            val option = options[index]
            button.text = option
            button.isEnabled = true
            button.setOnClickListener {
                it.playTap()
                checkOption(button, option)
            }
        }

        speech.speak(currentScene.prompt)
    }

    private fun checkOption(button: Button, option: String) {
        if (currentScene.correctOptions.contains(option)) {
            if (selectedCorrectOptions.add(option)) {
                val stars = RewardStore.addStar(this)
                starsText.text = "⭐ $stars"
                button.text = "✓ $option"
                button.isEnabled = false
                Toast.makeText(this, "Good Job Aadi!", Toast.LENGTH_SHORT).show()
                if (currentScene.isCourtesyScene()) {
                    speech.speak("Good Job Aadi. You can say, $option.")
                } else {
                    speech.speak("Good Job Aadi. $option.")
                }
            }
        } else {
            Toast.makeText(this, "Look again", Toast.LENGTH_SHORT).show()
            if (currentScene.isCourtesyScene()) {
                speech.speak("Try again. You can say, ${currentScene.correctOptions.first()}.")
            } else {
                speech.speak("Look again. I do not see $option in this picture.")
            }
        }
    }

    override fun onDestroy() {
        speech.shutdown()
        super.onDestroy()
    }
}

data class PictureScene(
    val id: String,
    val title: String,
    val prompt: String,
    val correctOptions: List<String>,
    val wrongOptions: List<String>
) {
    fun isCourtesyScene(): Boolean = id.startsWith("courtesy_")
}
