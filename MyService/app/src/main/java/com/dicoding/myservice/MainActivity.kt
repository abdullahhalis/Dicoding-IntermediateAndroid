package com.dicoding.myservice

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dicoding.myservice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val serviceIntent = Intent(this, MyBackgroundService::class.java)
        binding.apply {
            btnStartBackgroundService.setOnClickListener {
                startService(serviceIntent)
            }
            btnStopBackgroundService.setOnClickListener {
                stopService(serviceIntent)
            }
        }

        val foregroundServiceIntent = Intent(this, MyForegroundService::class.java)
        binding.apply {
            btnStartForegroundService.setOnClickListener{
                if (Build.VERSION.SDK_INT >= 26) {
                    startForegroundService(foregroundServiceIntent)
                } else {
                    startService(foregroundServiceIntent)
                }
            }
            btnStopForegroundService.setOnClickListener{
                stopService(foregroundServiceIntent)
            }
        }
    }
}