package com.guitarproacademy.app.audio

import android.annotation.SuppressLint
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.math.abs
import kotlin.math.roundToInt

/**
 * Real-time pitch detector using the guitar's microphone.
 * Uses a simplified autocorrelation (YIN-inspired) algorithm — no external
 * DSP library required, runs entirely on-device.
 */
class PitchDetector {

    private val sampleRate = 44100
    private var audioRecord: AudioRecord? = null
    private var recordingThread: Thread? = null
    @Volatile private var isRunning = false

    private val _detectedFrequency = MutableStateFlow(0f)
    val detectedFrequency: StateFlow<Float> = _detectedFrequency

    @SuppressLint("MissingPermission")
    fun start() {
        if (isRunning) return
        val minBufferSize = AudioRecord.getMinBufferSize(
            sampleRate,
            AudioFormat.CHANNEL_IN_MONO,
            AudioFormat.ENCODING_PCM_16BIT
        )
        val bufferSize = maxOf(minBufferSize, 4096)

        audioRecord = AudioRecord(
            MediaRecorder.AudioSource.MIC,
            sampleRate,
            AudioFormat.CHANNEL_IN_MONO,
            AudioFormat.ENCODING_PCM_16BIT,
            bufferSize
        )

        val record = audioRecord ?: return
        if (record.state != AudioRecord.STATE_INITIALIZED) return

        isRunning = true
        record.startRecording()

        recordingThread = Thread {
            val buffer = ShortArray(bufferSize)
            while (isRunning) {
                val read = record.read(buffer, 0, bufferSize)
                if (read > 0) {
                    val freq = detectPitch(buffer, read, sampleRate)
                    if (freq > 0f) _detectedFrequency.value = freq
                }
            }
        }.also { it.start() }
    }

    fun stop() {
        isRunning = false
        recordingThread?.join(200)
        recordingThread = null
        audioRecord?.let {
            try {
                it.stop()
            } catch (_: Exception) {
            }
            it.release()
        }
        audioRecord = null
        _detectedFrequency.value = 0f
    }

    /**
     * Simplified YIN pitch detection: finds the lag with minimal
     * difference-function value within the guitar's usable range (~70-1000Hz).
     */
    private fun detectPitch(buffer: ShortArray, length: Int, sampleRate: Int): Float {
        val minFreq = 70.0
        val maxFreq = 1000.0
        val maxLag = (sampleRate / minFreq).toInt().coerceAtMost(length - 1)
        val minLag = (sampleRate / maxFreq).toInt().coerceAtLeast(2)

        // Convert to normalized doubles
        val samples = DoubleArray(length) { buffer[it] / 32768.0 }

        // Difference function
        val diff = DoubleArray(maxLag + 1)
        for (lag in minLag..maxLag) {
            var sum = 0.0
            var i = 0
            while (i < length - lag) {
                val delta = samples[i] - samples[i + lag]
                sum += delta * delta
                i++
            }
            diff[lag] = sum
        }

        // Cumulative mean normalized difference
        val cmnd = DoubleArray(maxLag + 1)
        cmnd[0] = 1.0
        var runningSum = 0.0
        for (lag in 1..maxLag) {
            runningSum += diff[lag]
            cmnd[lag] = if (runningSum == 0.0) 1.0 else diff[lag] * lag / runningSum
        }

        // Find first lag below threshold
        val threshold = 0.15
        var bestLag = -1
        for (lag in minLag..maxLag) {
            if (cmnd[lag] < threshold) {
                bestLag = lag
                // walk to local minimum
                while (bestLag + 1 <= maxLag && cmnd[bestLag + 1] < cmnd[bestLag]) bestLag++
                break
            }
        }
        if (bestLag == -1) return -1f

        return (sampleRate / bestLag.toDouble()).toFloat()
    }
}

data class GuitarString(val name: String, val frequency: Float)

val standardTuning = listOf(
    GuitarString("E2", 82.41f),
    GuitarString("A2", 110.00f),
    GuitarString("D3", 146.83f),
    GuitarString("G3", 196.00f),
    GuitarString("B3", 246.94f),
    GuitarString("E4", 329.63f)
)

/** Finds the closest standard-tuning string to a detected frequency. */
fun closestString(frequency: Float): Pair<GuitarString, Float> {
    val closest = standardTuning.minByOrNull { abs(it.frequency - frequency) } ?: standardTuning[0]
    // cents deviation from the target frequency
    val cents = 1200 * (Math.log(frequency / closest.frequency.toDouble()) / Math.log(2.0))
    return closest to cents.roundToInt().toFloat()
}
