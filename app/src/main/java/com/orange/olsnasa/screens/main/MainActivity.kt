package com.orange.olsnasa.screens.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.orange.olsnasa.R
import com.orange.olsnasa.extensions.load
import com.orange.olsnasa.screens.ar.ARActivity
import com.orange.olsnasa.screens.globe.GlobeActivity
import com.orange.olsnasa.screens.satellites.SatellitesActivity
import com.orange.olsnasa.screens.scores.ScoresActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ivActivityBackground.load(R.drawable.backroung_universe2)
        ivActivityHeader.load(R.drawable.earth_top)

        cvSatellitesCard.setOnClickListener {
            startActivity(SatellitesActivity.intent(this))
        }

        cvEarthCard.setOnClickListener {
            startActivity(GlobeActivity.intent(this))
        }

        cvScoresCard.setOnClickListener {
            startActivity(ScoresActivity.intent(this))
        }

        cvARCard.setOnClickListener {
            startActivity(ARActivity.intent(this))
        }
    }
}
