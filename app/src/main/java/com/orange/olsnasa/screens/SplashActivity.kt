package com.orange.olsnasa.screens

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.orange.olsnasa.R
import com.orange.olsnasa.extensions.load
import com.orange.olsnasa.extensions.loadCropped
import com.orange.olsnasa.screens.main.MainActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        icSplash.load(R.drawable.ic_splash)

        Handler().postDelayed({
            startActivity(
                Intent(
                    this@SplashActivity,
                    MainActivity::class.java
                )
            )
        }, 3000)
    }
}