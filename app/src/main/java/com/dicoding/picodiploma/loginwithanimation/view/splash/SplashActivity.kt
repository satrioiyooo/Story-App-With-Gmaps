package com.dicoding.picodiploma.loginwithanimation.view.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserPreference
import com.dicoding.picodiploma.loginwithanimation.data.pref.dataStore
import com.dicoding.picodiploma.loginwithanimation.view.main.MainActivity
import com.dicoding.picodiploma.loginwithanimation.view.welcome.WelcomeActivity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Handler(Looper.getMainLooper()).postDelayed({
            goToMainActivity()
        }, 1L)
    }

    private fun goToMainActivity() {
        runBlocking {
            val userPreference = UserPreference.getInstance(this@SplashActivity.dataStore)
            val userModel = userPreference.getSession().first()

            val intent = if (userModel.isLogin) {
                Intent(this@SplashActivity, MainActivity::class.java)
            } else {
                Intent(this@SplashActivity, WelcomeActivity::class.java)
            }
            startActivity(intent)
            finish()
        }
    }

}