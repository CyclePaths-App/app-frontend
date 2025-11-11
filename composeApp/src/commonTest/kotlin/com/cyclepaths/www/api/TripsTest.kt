package com.cyclepaths.www.api

import io.ktor.client.HttpClient
import kotlinx.coroutines.runBlocking
import util.onError
import util.onSuccess
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.serialization.json.Json

class TripsTest {

    val client: HttpClient = HttpClient() {
        install(ContentNegotiation) {
            json(
                json = Json {
                    ignoreUnknownKeys = true // Ignores extra items sent along with the request.
                }
            )
        }
    }
    val trip: Trips = Trips(client)
    val standardList =
        listOf<Location>(
            Location(
                latitude = 42.686261,
                longitude = -73.828025,
                time = LocalDateTime(2025, 9, 20, 18, 5, 0).toInstant(TimeZone.UTC).toString()
            ),
            Location(
                latitude = 42.686945,
                longitude = -73.827349,
                time = LocalDateTime(2025, 9, 20, 18, 5, 15).toInstant(TimeZone.UTC).toString()
            ),
            Location(
                latitude = 42.687378,
                longitude = -73.826919,
                time = LocalDateTime(2025, 9, 20, 18, 5, 30).toInstant(TimeZone.UTC).toString()
            )
        )

    @Test
    fun shouldCreateTrip() {
        runBlocking {
            val result = trip.createTrip(1, standardList, Trip.TripType.BIKE);

            result.onSuccess {
                assertEquals(3, it)
            }.onError {
                assertTrue(false, "Expected Ok, got error: $it")
            }
        }
    }

    @Test
    fun shouldGetTrip() {
        runBlocking {
            val result = trip.getTrip(2)

            result.onSuccess {
                assertEquals(2, it.id)
                assertEquals(2, it.user_id)
                assertEquals(420, it.distance)
                assertEquals(Trip.TripType.BIKE, it.trip_type)
            }.onError {
                assertTrue(false, "Expected Ok, got error: $it")
            }
        }
    }

    @Test
    fun shouldGetTrips() {
        runBlocking {
            val result = trip.getTripByUserId(1)

            result.onSuccess {
                assertEquals(2, it.size)
            }.onError {
                assertTrue(false, "Expected Ok, got error: $it")
            }
        }
    }

    @Test
    fun shouldUpdateTrip() {
        runBlocking {
            val result = trip.updateTrip(3, 1, 155, Trip.TripType.WALK)

            result.onSuccess {
                assertTrue(it)
            }.onError {
                assertTrue(false, "Expected Ok, got error: $it")
            }
        }
    }

    @Test
    fun shouldDeleteTrip() {
        runBlocking {
            val result = trip.deleteTrip(3)

            result.onSuccess {
                assertTrue(it)
            }.onError {
                assertTrue(false, "Expected Ok, got error: $it")
            }
        }
    }
}