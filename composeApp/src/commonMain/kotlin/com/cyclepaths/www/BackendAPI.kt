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


class BackendAPI(baseUrl: String) {

    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    private val baseUrl = baseUrl

    suspend fun getUser(userId: Int): User {
        return client.get("$baseUrl/users/$userId").body()
    }

    suspend fun getTrips(userId: Int): List<Trip> {
        return client.get("$baseUrl/users/$userId/trips").body()
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
            else -> Result.failure(Exception("Unknown Error."))
        }
    }

    suspend fun getPoints(tripId: Int): List<Point> {
        return client.get("$baseUrl/trips/$tripId/points").body()
    }

    suspend fun getTotalDistanceMeters(userId: Int): Int {
        val trips = getTrips(userId)
        return trips.sumOf { it.distance }
    }

    suspend fun getTotalDistanceMiles(userId: Int): Double {
        return getTotalDistanceMeters(userId) / 1609.34
    }

    suspend fun createUser(user: User): User {
        return client.post("$baseUrl/users") {
            contentType(ContentType.Application.Json)
            setBody(user)
        }.body()
    }

    suspend fun sendLogin(username: String, password: String): User? {
        return try {
            val response = client.post("$baseUrl/login") {
                contentType(ContentType.Application.Json)
                setBody(mapOf("username" to username, "password" to password))
            }
            response.body<User>()
        } catch (e: Exception) {
            println("Login failed: ${e.message}")
            null
        }
    }

    suspend fun deleteUser(user: User) {
        client.delete("$baseUrl/users/${user.id}")
    }
}
