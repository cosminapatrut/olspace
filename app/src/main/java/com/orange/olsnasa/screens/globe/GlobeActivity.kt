package com.orange.olsnasa.screens.globe

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.orange.olsnasa.R
import com.orange.olsnasa.screens.satellitedetail.SateliteDetailActivity.Companion.ARG_SATELLITE

class GlobeActivity : AppCompatActivity() {

    companion object {
        fun intent(context: Context, satelliteAbove: String? = null) =
            Intent(context, GlobeActivity::class.java).apply {
                putExtra(ARG_SATELLITE, satelliteAbove)
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_globe)

        val satelliteAboveExtras = intent?.extras?.get(ARG_SATELLITE) as? String
        val globe = PlacemarksPickingFragment.intent(satelliteAboveExtras)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.globe_container, globe)
            .commit()
    }
}
