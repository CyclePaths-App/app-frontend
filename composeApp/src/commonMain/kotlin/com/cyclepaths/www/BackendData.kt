package com.cyclepaths.www

import kotlinx.serialization.Serializable

/**
 * Contains the user data.
 */
@Serializable
data class User(
    val id: Int,
    val username: String,
    val first_name: String,
    val last_name: String,
    val email: String,
    val password: String
)

/**
 * Contains the trip data.
 */
@Serializable
data class Trip(
    val id: Int,
    val userId: Int,
    val distance: Int,
    val tripType: TripType
)

/**
 * Enum for identifying the trip type.
 */
@Serializable
enum class TripType {
    walk,
    bike
}

/**
 * Contains data for trip points.
 */
@Serializable
data class Point(
    val tripId: Int,
    val longitude: Double,
    val latitude: Double,
    val time: String,
    val speedMps: Double
)