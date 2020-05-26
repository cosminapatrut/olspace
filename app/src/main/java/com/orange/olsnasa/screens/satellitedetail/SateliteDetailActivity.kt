package com.orange.olsnasa.screens.satellitedetail

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.google.gson.Gson
import com.orange.domain.model.SatelliteAbove
import com.orange.domain.model.SatellitePassesResponse
import com.orange.olsnasa.R
import com.orange.olsnasa.extensions.loadCropped
import com.orange.olsnasa.notifications.passes.SatellitePassNotificationReceiver
import com.orange.olsnasa.notifications.passes.SatellitePassNotificationReceiver.Companion.SATELLITE_PASS_DATA
import com.orange.olsnasa.screens.globe.GlobeActivity
import kotlinx.android.synthetic.main.activity_satelite_detail.*
import kotlinx.android.synthetic.main.content_scrolling.*
import kotlinx.android.synthetic.main.dialog_follow_me.view.*
import org.koin.android.viewmodel.ext.android.viewModel


class SateliteDetailActivity : AppCompatActivity() {

    companion object {
        const val ARG_SATELLITE = "SateliteDetailActivity:ARG_SATELLITE"
        fun intent(context: Context, satelliteAbove: SatelliteAbove) =
            Intent(context, SateliteDetailActivity::class.java).apply {
                putExtra(ARG_SATELLITE, Gson().toJson(satelliteAbove, SatelliteAbove::class.java))
            }
    }

    private val satelliteDetailViewModel by viewModel<SatelliteDetailViewModel>()
    private var satellite: SatelliteAbove? = null
    private var reminderMillis: Long = 0L

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_satelite_detail)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        btnFollowMe.setOnClickListener {
            showCustomViewDialog()
        }

        btnReminderState.apply {
            isEnabled = false
            backgroundTintList = ColorStateList.valueOf(Color.GRAY)
        }
        btnReminderState.setOnClickListener { checked ->
            Toast.makeText(
                applicationContext,
                "Reminders disabled for ${satellite?.satname}",
                Toast.LENGTH_SHORT
            ).show()
        }

        ivSatelite.loadCropped("https://images-assets.nasa.gov/image/s91-50773/s91-50773~thumb.jpg")
        satelliteDetailViewModel.apply {
            data.observe(this@SateliteDetailActivity,
                Observer { sateliteDetail ->
                    if (sateliteDetail != null) {

                        val img: String = try {
                            sateliteDetail.links.first { item ->
                                item.href.isNotBlank()
                            }.href
                        }catch (e: Exception){
                            "https://images-assets.nasa.gov/image/s91-50773/s91-50773~thumb.jpg"
                        }

                        ivSatelite.loadCropped(img)
                        tvSateliteDescription.text = sateliteDetail.data.first().description
                    }
                })

            passesData.observe(this@SateliteDetailActivity,
                Observer { satellitePassesResponse ->
                    satellitePassesResponse?.let {
                        if (!it.passes.isNullOrEmpty()) {
                            it.passes.forEach { satellitePass ->
                                val delayInMillis =
                                    satellitePass.startUTC * 1000L - System.currentTimeMillis() - reminderMillis
                                scheduleNotification(applicationContext, it, delayInMillis)
                            }
                            btnReminderState.apply {
                                isEnabled = true
                                backgroundTintList = null
                            }
                            Toast.makeText(
                                this@SateliteDetailActivity,
                                "Reminder set",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            btnReminderState.apply {
                                isEnabled = false
                                backgroundTintList = ColorStateList.valueOf(Color.GRAY)
                            }
                            Toast.makeText(
                                applicationContext,
                                "Satellite ${it.info.satName} will not pass above your location in the next X days :(",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                })

        }

        satellite =
            Gson().fromJson(intent.getStringExtra(ARG_SATELLITE), SatelliteAbove::class.java)

        val fab = findViewById<View>(R.id.fab) as ImageView
        fab.setOnClickListener {
            satellite?.let {
                startActivity(GlobeActivity.intent(this,  Gson().toJson(it, SatelliteAbove::class.java)))
            }
        }
        satellite?.let {
            supportActionBar?.title = it.satname
            tvSatelliteId.text = "ID: ${it.satid}"
            tvLatitude.text = "${it.satlat}°"
            tvLongitude.text = "${it.satlng}°"
            satelliteDetailViewModel.searchSatelliteImage(it.satname)
        }
    }

    private fun getSatellitePasses(satelliteAbove: SatelliteAbove, period: Int) {
        satelliteDetailViewModel.getSatellitePasses(satelliteAbove, period)
    }

    private fun showCustomViewDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_follow_me, null)
        val builder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("Follow me")
        val alertDialog = builder.show()
        dialogView.btnFollow.setOnClickListener {
            alertDialog.dismiss()
            val period = dialogView.etPeriod.text.toString()
            val reminder = dialogView.etReminder.text.toString()
            if (period.isNotBlank() && reminder.isNotBlank()) {
                reminderMillis = reminder.toLong() * 60 * 1000
                satellite?.let {
                    getSatellitePasses(it, period.toInt())
                }
            } else {
                Toast.makeText(
                    applicationContext,
                    "Please fill all the fields!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun scheduleNotification(
        context: Context,
        info: SatellitePassesResponse?,
        delayInMillis: Long
    ) {
        val notificationIntent = Intent(context, SatellitePassNotificationReceiver::class.java)
        notificationIntent.putExtra(SATELLITE_PASS_DATA, info)
        val notificationPendingIntent = PendingIntent.getBroadcast(
            context,
            101,
            notificationIntent,
            PendingIntent.FLAG_CANCEL_CURRENT
        )

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.set(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            delayInMillis,
            notificationPendingIntent
        )
    }

}
