package com.orange.olsnasa.screens.ar

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.MediaController
import android.widget.VideoView
import com.orange.olsnasa.R
import kotlinx.android.synthetic.main.activity_ar.*


class ARActivity : AppCompatActivity() {

    companion object {
        fun intent(context: Context) = Intent(context, ARActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ar)

        playLocalVideo()
    }

    fun playLocalVideo() {
        val video = findViewById<View>(R.id.video1) as VideoView
        val mediaController = MediaController(this)
        mediaController.setAnchorView(video)
        video.setMediaController(mediaController)
        video.keepScreenOn = true
        video.setVideoURI(Uri.parse("android.resource://" + packageName + "/" + R.raw.ar))
      //  video.setVideoPath("android.resource://uk.co.SplashActivity/vid/big_buck_bunny.mp4")
        video.start()
        video.requestFocus()
    }
}
