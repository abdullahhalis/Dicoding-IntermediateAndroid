package com.dicoding.myservice

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import com.dicoding.myservice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var boundStatus = false
    private lateinit var boundService: MyBoundService

    private val connection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName) {
            boundStatus = false
        }

        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val myBinder = service as MyBoundService.MyBinder
            boundService = myBinder.getService
            boundStatus = true
            getNumberFromService()
        }
    }

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
        val boundServiceIntent = Intent(this, MyBoundService::class.java)
        binding.apply {
            btnStartBoundService.setOnClickListener{
                bindService(boundServiceIntent, connection, BIND_AUTO_CREATE)
            }
            btnStopBoundService.setOnClickListener{
                unbindService(connection)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        if(boundStatus) {
            unbindService(connection)
            boundStatus = false
        }
    }

    private fun getNumberFromService() {
        boundService.numberLiveData.observe(this) { number ->
            binding.tvBoundServiceNumber.text = number.toString()
        }
    }
}