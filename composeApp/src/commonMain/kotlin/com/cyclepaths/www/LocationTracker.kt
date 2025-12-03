package com.cyclepaths.www


expect class LocationTracker() {
    var trip: ArrayList<BackendAPI.Location>
        private set
    var isTracking: Boolean
        private set

    fun startTracking()
    fun stopTracking()
}


