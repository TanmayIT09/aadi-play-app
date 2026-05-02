package com.example.aadi_app

import android.content.Context
import android.speech.tts.TextToSpeech
import java.util.Locale

class SpeechHelper(context: Context) : TextToSpeech.OnInitListener {
    private var ready = false
    private var pendingText: String? = null
    private val tts = TextToSpeech(context.applicationContext, this)

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts.language = Locale.US
            tts.setSpeechRate(0.72f)
            tts.setPitch(1.1f)
            ready = true
            pendingText?.let {
                speak(it)
                pendingText = null
            }
        }
    }

    fun speak(text: String) {
        if (ready) {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "aadi_speech")
        } else {
            pendingText = text
        }
    }

    fun shutdown() {
        tts.stop()
        tts.shutdown()
    }
}
