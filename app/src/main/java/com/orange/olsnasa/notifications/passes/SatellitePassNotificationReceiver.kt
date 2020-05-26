package com.orange.olsnasa.notifications.passes

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import com.orange.domain.model.SatellitePassesResponse
import com.orange.olsnasa.R
import com.orange.olsnasa.screens.main.MainActivity


class SatellitePassNotificationReceiver : BroadcastReceiver() {
    companion object {
        private const val NOTIFICATION_ID = 101
        const val SATELLITE_PASS_DATA = "SATELLITE_PASS_DATA:DATA"
        const val notificationChannel = "SatellitePassNotification:pass:channel"
        const val channelID = "com.orange.olsnasa:pass"
    }
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let { ctx ->
            intent?.let {
                val info = it.getParcelableExtra<SatellitePassesResponse>(SATELLITE_PASS_DATA)
                showNotification(ctx, info)
            }
        }
    }

    private fun showNotification(context: Context, info: SatellitePassesResponse?) {

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        val builder = NotificationCompat.Builder(context, channelID)
            .setSmallIcon(R.drawable.ic_reminder)
            .setContentTitle("Alert")
            .setContentText("Satellite ${info?.info?.satName} will pass above you")
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        createNotificationChannel(context)

        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            notify(NOTIFICATION_ID, builder.build())
        }
    }

    private fun createNotificationChannel(context: Context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = notificationChannel
            val descriptionText = "olsnasa"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
