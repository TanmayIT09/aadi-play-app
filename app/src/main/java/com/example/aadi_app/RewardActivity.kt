package com.example.aadi_app

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class RewardActivity : Activity() {
    private lateinit var speech: SpeechHelper
    private var currentStars = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reward)
        speech = SpeechHelper(this)

        findViewById<Button>(R.id.btnCountStars).setOnClickListener {
            it.playTap()
            speakStars()
        }

        findViewById<Button>(R.id.btnResetStars).setOnClickListener {
            it.playTap()
            RewardStore.resetStars(this)
            speech.speak("Stars reset.")
            refreshStars()
        }
    }

    override fun onResume() {
        super.onResume()
        refreshStars()
    }

    private fun refreshStars() {
        currentStars = RewardStore.getStars(this)

        val starsText = formatStarGroups(currentStars)
        val numberText = formatNumberGroups(currentStars)

        findViewById<TextView>(R.id.txtStars).text = "Stars: $starsText"
        findViewById<TextView>(R.id.txtStarCount).text = "Total: $currentStars"
        findViewById<TextView>(R.id.txtNumbers).text = numberText
        findViewById<TextView>(R.id.txtRewardTime).visibility =
            if (currentStars >= 5) View.VISIBLE else View.GONE
    }

    private fun speakStars() {
        if (currentStars == 0) {
            speech.speak("No stars yet. Play a game to earn one star.")
            return
        }

        val countWords = buildCountWords(currentStars)
        val reward = if (currentStars >= 5) "Reward time." else ""
        speech.speak("$countWords. You have $currentStars stars. $reward")
    }

    private fun formatStarGroups(stars: Int): String {
        if (stars == 0) return "No stars yet"
        if (stars < 10) return "⭐".repeat(stars)

        val groupsOfTen = stars / 10
        val extraStars = stars % 10
        val text = StringBuilder()

        repeat(groupsOfTen) {
            if (text.isNotEmpty()) text.append("\n")
            text.append("10 ⭐")
        }

        if (extraStars > 0) {
            text.append("\n+ ")
                .append(extraStars)
                .append(" more ")
                .append("⭐".repeat(extraStars))
        }

        return text.toString()
    }

    private fun formatNumberGroups(stars: Int): String {
        if (stars == 0) return "Play a game"
        if (stars < 10) return (1..stars).joinToString("  ")

        val groupsOfTen = stars / 10
        val extraStars = stars % 10
        val parts = MutableList(groupsOfTen) { "10" }

        if (extraStars > 0) {
            parts.add("$extraStars more")
        }

        return parts.joinToString(" + ") + " = $stars"
    }

    private fun buildCountWords(stars: Int): String {
        if (stars <= 10) return (1..stars).joinToString(", ")

        val groupsOfTen = stars / 10
        val extraStars = stars % 10
        val groupsText = MutableList(groupsOfTen) { "10" }

        if (extraStars > 0) {
            groupsText.add("$extraStars more")
        }

        return groupsText.joinToString(", plus ")
    }

    override fun onDestroy() {
        speech.shutdown()
        super.onDestroy()
    }
}
