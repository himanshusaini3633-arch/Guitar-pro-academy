package com.guitarproacademy.app.audio

import android.media.AudioManager
import android.media.ToneGenerator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

/**
 * Real metronome engine. Uses ToneGenerator for click sounds (no audio
 * asset files needed) and a self-correcting timing loop so drift doesn't
 * accumulate over long practice sessions.
 */
class MetronomeEngine(private val scope: CoroutineScope) {

    private var job: Job? = null
    private val toneGenerator = ToneGenerator(AudioManager.STREAM_MUSIC, 80)

    var onBeat: ((beatIndexInBar: Int) -> Unit)? = null

    fun start(bpm: Int, beatsPerBar: Int, accentFirstBeat: Boolean) {
        stop()
        val intervalMs = 60_000L / bpm.coerceIn(20, 300)
        var beatIndex = 0
        var nextTickTime = System.currentTimeMillis()

        job = scope.launch {
            while (isActive) {
                val isAccent = accentFirstBeat && beatIndex == 0
                toneGenerator.startTone(
                    if (isAccent) ToneGenerator.TONE_CDMA_PIP else ToneGenerator.TONE_PROP_BEEP,
                    60
                )
                onBeat?.invoke(beatIndex)
                beatIndex = (beatIndex + 1) % beatsPerBar

                nextTickTime += intervalMs
                val delayMs = (nextTickTime - System.currentTimeMillis()).coerceAtLeast(0)
                delay(delayMs)
            }
        }
    }

    fun stop() {
        job?.cancel()
        job = null
    }

    fun release() {
        stop()
        toneGenerator.release()
    }
}
