package com.example.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.stopwatch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    var secs = 0
    var isRunning = false
    val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Thread to be run in the background
        val runnable = object : Runnable {
            override fun run() {
                binding.time.text = getTime(secs)

                handler.postDelayed(this, 1000)

                secs++
            }
        }

        binding.startStopButton.setOnClickListener {
            if(isRunning) {
                handler.removeCallbacks(runnable) // Stops the thread(runnable)
                binding.startStopButton.text = "Start"
                isRunning = false
            } else {
                handler.post(runnable) // Starts the thread(runnable)
                binding.startStopButton.text = "Stop"
                isRunning = true
            }
        }

        binding.resetButton.setOnClickListener {
            handler.removeCallbacks(runnable)
            secs = 0
            isRunning = false
            binding.startStopButton.text = "Start"
            binding.time.text = getTime(secs)
        }
    }

    // Conversion of seconds into h:m:s format
    fun getTime(sec: Int = 0): String {
        val h = sec/3600
        val m = sec%3600 / 60
        val s = sec%60
        return "%1$02d:%2$02d:%3$02d".format(h, m, s)
    }

}