package com.cyclepaths.www

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.IBinder

actual class LocationTracker {
    actual var trip: ArrayList<BackendAPI.Location> = ArrayList()
        private set
    actual var isTracking: Boolean = false
        private set

    private var locationService: LocationTrackingService? = null

    private var connection = object : ServiceConnection {
        override fun onServiceConnected(
            name: ComponentName?,
            service: IBinder?
        ) {
            val binder = service as LocationTrackingService.LocalBinder
            locationService = binder.getService()
            trip = locationService!!.trip
            isTracking = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isTracking = false
        }

    }

    actual fun startTracking() {
        val context = AppContext.get().applicationContext

        // Request background tracking permission:
        // ActivityCompat.requestPermissions(, arrayOf(Manifest.permission.FOREGROUND_SERVICE_LOCATION), 0)

        // Initialize foreground service.
        val startIntent = Intent(context, LocationTrackingService::class.java).apply {
            action = LocationTrackingService.Actions.START.toString()
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(startIntent)
        }
        context.bindService(startIntent, connection, 0)
    }

    actual fun stopTracking() {
        AppContext.get().applicationContext.unbindService(connection)
    }

}