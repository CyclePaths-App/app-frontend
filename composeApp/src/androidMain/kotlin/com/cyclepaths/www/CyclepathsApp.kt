package com.cyclepaths.www

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

class CyclepathsApp : Application() {

    override fun onCreate() {
        super.onCreate()
        // Check if we have to create a notification channel for the foreground location service.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "commute_tracking",
                "Tracking Commute",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        AppContext.setContext(applicationContext)
    }
}