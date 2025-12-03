package com.cyclepaths.www

import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.ServiceCompat
import dev.jordond.compass.Priority
import dev.jordond.compass.geolocation.LocationRequest
import dev.jordond.compass.geolocation.MobileGeolocator
import dev.jordond.compass.geolocation.TrackingStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class LocationTrackingService : Service() {

    private val binder = LocalBinder()
    private val scope = CoroutineScope(Dispatchers.IO)

    private val tracker = MobileGeolocator()
    var trip = ArrayList<BackendAPI.Location>()
        private set

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        stop()
        return super.onUnbind(intent)
    }

    inner class LocalBinder : Binder() {
        fun getService(): LocationTrackingService = this@LocationTrackingService
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            Actions.START.toString() -> start()
            Actions.STOP.toString() -> stop()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start() {
        val notification = NotificationCompat.Builder(this, "commute_tracking")
            .setSmallIcon(R.drawable.ic_launcher_foreground) // TODO: if we make a logo, add it here.
            .setContentTitle("Tracking your commute...")
            .setContentText("")
            .build()

        ServiceCompat.startForeground(
            this,
            1,
            notification,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                ServiceInfo.FOREGROUND_SERVICE_TYPE_LOCATION
            } else {
                0
            }
        )

        trip.clear()    // Clean up before tracking again.

        tracker
            .track(LocationRequest(Priority.HighAccuracy, 3000))
            .onEach { status ->
                println("LocationTracker: Status = $status")
                when (status) { // When a status changes, the collection sends that here
                    is TrackingStatus.Error -> {
                        val error = status.cause
                        println(error)
                    }

                    TrackingStatus.Idle -> {}   // Don't need to do anything if it is idle.
                    TrackingStatus.Tracking -> {}   // Waiting for an update, no need to do anything.
                    is TrackingStatus.Update -> {
                        // Unwrap and rewrap location information.
                        val latitude = status.location.coordinates.latitude
                        val longitude = status.location.coordinates.longitude
                        val time = Clock.System.now().toLocalDateTime(TimeZone.UTC)
                            .toString() // WHOMST designed this API?

                        val currentLocation = BackendAPI.Location(latitude, longitude, time)

                        // Add to list.
                        trip.add(currentLocation)
                    }
                }
            }.launchIn(scope)
    }

    private fun stop() {
        tracker.stopTracking()
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }

    enum class Actions {
        START, STOP, KILL
    }
}