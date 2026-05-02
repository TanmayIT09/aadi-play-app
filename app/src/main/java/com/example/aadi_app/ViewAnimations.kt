package com.example.aadi_app

import android.view.View

fun View.playTap() {
    animate()
        .scaleX(0.94f)
        .scaleY(0.94f)
        .setDuration(80)
        .withEndAction {
            animate()
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(120)
                .start()
        }
        .start()
}
