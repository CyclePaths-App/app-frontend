package com.cyclepaths.www

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

fun hasLocationPermissions(context: Context): Boolean {
    val hasCoarseLocation = ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
    val hasFineLocation =
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

    return hasCoarseLocation && hasFineLocation
}

fun hasForegroundLocationPermission(context: Context): Boolean {
    val hasForegroundPermission = ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.FOREGROUND_SERVICE
    ) == PackageManager.PERMISSION_GRANTED
    val hasForegroundLocationPermission = ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.FOREGROUND_SERVICE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    return hasForegroundPermission && hasForegroundLocationPermission
}

fun requestLocationPermissions(activity: ActivityCompat) {

}