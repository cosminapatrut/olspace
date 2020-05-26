package com.orange.olsnasa.screens.satellites

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.orange.domain.model.SatelliteAbove
import com.orange.olsnasa.R
import com.orange.olsnasa.screens.satellitedetail.SateliteDetailActivity
import kotlinx.android.synthetic.main.activity_satellites.*
import org.koin.android.viewmodel.ext.android.viewModel

class SatellitesActivity: AppCompatActivity() {

    companion object {
        fun intent(context: Context) = Intent(context, SatellitesActivity::class.java)
    }

    private val satellitesViewModel by viewModel<SatellitesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_satellites)

        swipeRefresh.setOnRefreshListener {
            satellitesViewModel.getSatellites(44.429535f, 26.100865f, 1 * 60 * 1000L)
        }

        satellitesViewModel.apply {
            data.observe(this@SatellitesActivity,
            Observer {
                tvSatellitesCount.text = if (!it.isNullOrEmpty()) {
                    "There are ${it.size} satellites above you right now"
                } else {
                    "Oops, I could not find any satellite `;9"
                }
                val adapter = it?.let { it1 -> SatellitesAdapter(it1).apply {
                    onCardClicked.plusAssign { openSatelliteDetails(it) }
                }
                }
                val layoutManager =  LinearLayoutManager(this@SatellitesActivity)
                rvSatellitesAbove.layoutManager = layoutManager
                rvSatellitesAbove.adapter = adapter
            })

            isLoading.observe(this@SatellitesActivity,
                Observer {
                    it?.let {
                        loading.visibility = if (it) View.VISIBLE else View.GONE
                        swipeRefresh.isRefreshing = false
                    }
                })

            satellitesViewModel.getSatellites(44.429535f, 26.100865f, 1 * 60 * 1000L)
        }

    }

    private fun openSatelliteDetails(satelliteAbove: SatelliteAbove) {
        startActivity(SateliteDetailActivity.intent(this, satelliteAbove))
    }
}
