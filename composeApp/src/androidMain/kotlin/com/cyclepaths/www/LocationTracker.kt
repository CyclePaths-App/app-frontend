package com.cyclepaths.www

import android.Manifest
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Build
import android.os.IBinder
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

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
        val context = AppContext.getContext().applicationContext

        val hasLocationPermissions = hasLocationPermissions(context)
        val hasForegroundPermissions = hasForegroundLocationPermission(context)

        if (!hasLocationPermissions) {
            ActivityCompat.requestPermissions(
                AppContext.getActivity(),
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.FOREGROUND_SERVICE,
                    Manifest.permission.FOREGROUND_SERVICE_LOCATION
                ),
                0
            )
            // Thread.sleep(3500) // Location requests are non-blocking. Give the user time to accept or the app crashes. Is this the best way of doing it? Absolutely not. Do I have time to do it the "right" way? Also no.
        }

        // Initialize foreground service.
        val startIntent = Intent(context, LocationTrackingService::class.java).apply {
            action = LocationTrackingService.Actions.START.toString()
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ContextCompat.startForegroundService(context, startIntent)
        }
        context.bindService(startIntent, connection, 0)
    }

    actual fun stopTracking() {
        AppContext.getContext().applicationContext.unbindService(connection)
    }

}