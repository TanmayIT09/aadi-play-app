package com.example.aadi_app

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class RewardActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reward)
    }

    override fun onResume() {
        super.onResume()

        val stars = getSharedPreferences("aadi_prefs", MODE_PRIVATE)
            .getInt("stars", 0)

        val starsText = if (stars > 0) "⭐".repeat(stars) else "No stars yet"

        findViewById<TextView>(R.id.txtStars).text = "Stars: $starsText"
        findViewById<TextView>(R.id.txtStarCount).text = "Total: $stars"
        findViewById<TextView>(R.id.txtRewardTime).visibility =
            if (stars >= 5) View.VISIBLE else View.GONE
    }
}
