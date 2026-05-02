package com.example.aadi_app

import android.content.Context

object RewardStore {
    private const val PREFS = "aadi_prefs"
    private const val STARS = "stars"

    fun getStars(context: Context): Int {
        return context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .getInt(STARS, 0)
    }

    fun addStar(context: Context): Int {
        val newStars = getStars(context) + 1
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit()
            .putInt(STARS, newStars)
            .apply()
        return newStars
    }

    fun resetStars(context: Context) {
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit()
            .putInt(STARS, 0)
            .apply()
    }
}
