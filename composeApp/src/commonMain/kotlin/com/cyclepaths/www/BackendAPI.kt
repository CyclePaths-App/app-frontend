package com.cyclepaths.www

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.json.Json
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlin.collections.mapOf


class BackendAPI(baseUrl: String) {

    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    private val baseUrl = baseUrl

    suspend fun getTrips(userId: Int): List<Trip> {
        return client.get("$baseUrl/trips/userid/$userId").body()
    }

    @Serializable
    private data class CreateTripReq(
        val user_id: Int,
        val trip: List<Location>,
        val trip_type: TripType
    )

    @Serializable
    data class Location(val latitude: Double, val longitude: Double, val time: String)


    @Serializable
    private data class CreateTripRes(val id: Int)

    suspend fun createTrip(
        userId: Int,
        trip: List<Location>,
        tripType: TripType
    ): Result<Int> {
        val res = try {
            client.post("$baseUrl/trips/") {
                contentType(ContentType.Application.Json)
                setBody(CreateTripReq(userId, trip, tripType))
            }
        } catch (e: UnresolvedAddressException) {
            return Result.failure(Exception("Could not connect to WiFi."));
        } catch (e: SerializationException) {
            return Result.failure(Exception("Serialization Error: " + e.message));
        } catch (e: Exception) {
            return Result.failure(Exception("Unknown Clientside Error: " + e.message))
        }

        return when (res.status.value) {
            in 200..299 -> Result.success(res.body<CreateTripRes>().id)
            400 -> Result.failure(Exception("Bad Request."))
            409 -> Result.failure(Exception("Unauthorized."))
            500 -> Result.failure(Exception("Internal Server Error."))
            else -> Result.failure(Exception("Unknown Error: ${res.status.value}"))
        }
    }

    suspend fun getPoints(tripId: Int): List<Point> {
        return client.get("$baseUrl/trips/$tripId/points").body()
    }

    /**
     * This is for a full trip that includes distance.
     */
    @Serializable
    private data class CreateTripFull(
        val tripID: Int,
        val userID: Int,
        val distance: Int,
        val tripType: TripType
    )

//    suspend fun createUserTrip(
//        tripID: Int,
//        userID: Int,
//        distance: Int,
//        tripType: TripType
//    ): Result<Int> {
//        val res = try {
//            client.post("$baseUrl/users/") {
//                contentType(ContentType.Application.Json)
//                setBody(CreateUser(username, firstName, lastName, email, password))
//            }
//        } catch (e: Exception) {
//            return Result.failure(Exception("Client error: ${e.message}"))
//        }
//
//        return when (res.status.value) {
//            in 200..299 -> Result.success(res.body<CreateUserRes>().id)
//            400 -> Result.failure(Exception("Bad Request"))
//            409 -> Result.failure(Exception("User already exists"))
//            500 -> Result.failure(Exception("Internal Server Error"))
//            else -> Result.failure(Exception("Unknown Error"))
//        }
//    }

    suspend fun getTotalDistanceMeters(userId: Int): Int {
        val trips = getTrips(userId)
        return trips.sumOf { it.distance }
    }

    suspend fun getTotalDistanceMiles(userId: Int): Double {
        return getTotalDistanceMeters(userId) / 1609.34
    }

    @Serializable
    private data class CreateUser(
        val username: String,
        val first_name: String,
        val last_name: String,
        val email: String,
        val password: String
    )

    @Serializable
    private data class CreateUserRes(val id: Int)

    suspend fun createUser(
        username: String,
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ): Result<Int> {
        val res = try {
            client.post("$baseUrl/users/") {
                contentType(ContentType.Application.Json)
                setBody(CreateUser(username, firstName, lastName, email, password))
            }
        } catch (e: Exception) {
            return Result.failure(Exception("Client error: ${e.message}"))
        }

        return when (res.status.value) {
            in 200..299 -> Result.success(res.body<CreateUserRes>().id)
            400 -> Result.failure(Exception("Bad Request"))
            409 -> Result.failure(Exception("User already exists"))
            500 -> Result.failure(Exception("Internal Server Error"))
            else -> Result.failure(Exception("Unknown Error"))
        }
    }

    suspend fun getUser(username: String): User {
        return client.get("$baseUrl/users/$username").body()
    }

    @Serializable
    private data class UserLogin(
        val username: String,
        val password: String
    )

    suspend fun sendLogin(username: String, password: String): Result<User> {
        val res = try {
            client.post("$baseUrl/users/login") {
                contentType(ContentType.Application.Json)
                setBody(UserLogin(username, password))
            }
        } catch (e: Exception) {
            return Result.failure(Exception("Client error: ${e.message}"))
        }

        return when (res.status.value) {
            in 200..299 -> Result.success(res.body<User>())
            400 -> Result.failure(Exception("Bad Request"))
            409 -> Result.failure(Exception("User already exists"))
            500 -> Result.failure(Exception("Internal Server Error"))
            else -> Result.failure(Exception("Unknown Error"))
        }
    }

    suspend fun deleteUser(user: User) {
        client.delete("$baseUrl/users/${user.id}")
    }

    suspend fun getTotalDistanceMetersWalk(userId: Int): Int {
        val trips = getTrips(userId)
        var sum = 0

        for (trip in trips) {
            if (trip.trip_type == TripType.walk) {
                sum += trip.distance
            }
        }
        return sum;
    }

    suspend fun getTotalDistanceMilesWalk(userId: Int): Double {
        return getTotalDistanceMetersWalk(userId) * 0.0006213712
    }
}
