package com.example.aadi_app

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import kotlin.math.PI
import kotlin.math.sin

class TunePlayer {
    private var playing = false

    fun playSaReGaMa() {
        if (playing) return

        Thread {
            playing = true
            val sampleRate = 44100
            val noteMs = 520
            val gapMs = 45
            val notes = doubleArrayOf(
                261.63,
                293.66,
                329.63,
                349.23,
                392.00,
                440.00,
                493.88,
                523.25
            )

            val samples = buildSamples(notes, sampleRate, noteMs, gapMs)
            val audioTrack = AudioTrack.Builder()
                .setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build()
                )
                .setAudioFormat(
                    AudioFormat.Builder()
                        .setSampleRate(sampleRate)
                        .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                        .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                        .build()
                )
                .setBufferSizeInBytes(samples.size * 2)
                .setTransferMode(AudioTrack.MODE_STATIC)
                .build()

            audioTrack.write(samples, 0, samples.size)
            audioTrack.setVolume(0.28f)
            audioTrack.play()
            Thread.sleep((notes.size * (noteMs + gapMs) + 250).toLong())
            audioTrack.release()
            playing = false
        }.start()
    }

    private fun buildSamples(
        notes: DoubleArray,
        sampleRate: Int,
        noteMs: Int,
        gapMs: Int
    ): ShortArray {
        val noteSamples = sampleRate * noteMs / 1000
        val gapSamples = sampleRate * gapMs / 1000
        val result = ShortArray(notes.size * (noteSamples + gapSamples))
        var index = 0

        for (frequency in notes) {
            for (i in 0 until noteSamples) {
                val progress = i.toDouble() / noteSamples
                val envelope = when {
                    progress < 0.12 -> progress / 0.12
                    progress > 0.86 -> (1.0 - progress) / 0.14
                    else -> 1.0
                }.coerceIn(0.0, 1.0)
                val wave = sin(2.0 * PI * frequency * i / sampleRate)
                result[index++] = (wave * envelope * Short.MAX_VALUE * 0.34).toInt().toShort()
            }

            repeat(gapSamples) {
                result[index++] = 0
            }
        }

        return result
    }
}
