package com.cyclepaths.www

import cyclepaths.composeapp.generated.resources.Res
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.utils.EmptyContent.contentType
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

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
