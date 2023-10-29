package com.dicoding.mystory.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import com.dicoding.mystory.R
import com.dicoding.mystory.ui.main.MainActivity
import com.dicoding.mystory.ui.welcome.WelcomeActivity
import com.dicoding.mystory.utils.AuthViewModelFactory

class SplashActivity : AppCompatActivity() {
    private val splashViewModel: SplashViewModel by viewModels { AuthViewModelFactory.getInstance(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        supportActionBar?.hide()

        val delay = 3000L
        Handler(Looper.getMainLooper()).postDelayed({
            splashViewModel.getSession().observe(this){ user ->
                if(user.isLogin){
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    startActivity(Intent(this, WelcomeActivity::class.java))
                    finish()
                }
            }
        }, delay)
    }
}