package com.cyclepaths.www.api

import com.cyclepaths.www.BACKEND_URL

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.util.network.UnresolvedAddressException

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException

import util.NetworkError
import util.Result

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

class Trips(private val client: HttpClient) {
    private val baseUrl = BACKEND_URL + "trips/"

    suspend fun createTrip(
        userId: Int,
        trip: List<Location>,
        tripType: Trip.TripType
    ): Result<Int, NetworkError> {
        val res = try {
            client.post(baseUrl) {
                contentType(ContentType.Application.Json)
                setBody(CreateTripReq(userId, trip, tripType))
            }
        } catch (e: UnresolvedAddressException) {
            return Result.Error(NetworkError.NOT_CONNECTED)
        } catch (e: SerializationException) {
            print(e.message)
            return Result.Error(NetworkError.SERIALIZATION_ERROR)
        }

        return when (res.status.value) {
            in 200..299 -> Result.Ok(res.body<CreateTripRes>().id)
            400 -> Result.Error(NetworkError.BAD_REQUEST)
            409 -> Result.Error(NetworkError.UNAUTHORIZED)
            500 -> Result.Error(NetworkError.INTERNAL_ERROR)
            else -> Result.Error(NetworkError.UNKNOWN)
        }
    }

    @Serializable
    private data class CreateTripReq(
        val user_id: Int,
        val trip: List<Location>,
        val trip_type: Trip.TripType
    )

    @Serializable
    private data class CreateTripRes(val id: Int)

    suspend fun getTrip(id: Int): Result<Trip, NetworkError> {
        val res = try {
            client.get(baseUrl + "$id")
        } catch (e: UnresolvedAddressException) {
            return Result.Error(NetworkError.NOT_CONNECTED)
        } catch (e: SerializationException) {
            return Result.Error(NetworkError.SERIALIZATION_ERROR)
        }

        return when (res.status.value) {
            in 200..299 -> Result.Ok(res.body<Trip>())
            400 -> Result.Error(NetworkError.BAD_REQUEST)
            401 -> Result.Error(NetworkError.UNAUTHORIZED)
            404 -> Result.Error(NetworkError.NOT_FOUND)
            500 -> Result.Error(NetworkError.INTERNAL_ERROR)
            else -> Result.Error(NetworkError.UNKNOWN)
        }
    }

    suspend fun getTripByUserId(userId: Int): Result<List<Trip>, NetworkError> {
        val res = try {
            client.get(baseUrl + "userId/$userId")
        } catch (e: UnresolvedAddressException) {
            return Result.Error(NetworkError.NOT_CONNECTED)
        } catch (e: SerializationException) {
            return Result.Error(NetworkError.SERIALIZATION_ERROR)
        }

        return when (res.status.value) {
            in 200..299 -> Result.Ok(res.body<List<Trip>>())
            400 -> Result.Error(NetworkError.BAD_REQUEST)
            401 -> Result.Error(NetworkError.UNAUTHORIZED)
            404 -> Result.Error(NetworkError.NOT_FOUND)
            500 -> Result.Error(NetworkError.INTERNAL_ERROR)
            else -> Result.Error(NetworkError.UNKNOWN)
        }
    }

    suspend fun updateTrip(
        id: Int,
        userId: Int,
        distance: Int?,
        tripType: Trip.TripType?
    ): Result<Boolean, NetworkError> {
        val res = try {
            client.put(baseUrl) {
                contentType(ContentType.Application.Json)
                setBody(UpdateTripReq(id, userId, distance, tripType))
            }
        } catch (e: UnresolvedAddressException) {
            return Result.Error(NetworkError.NOT_CONNECTED)
        } catch (e: SerializationException) {
            return Result.Error(NetworkError.SERIALIZATION_ERROR)
        }

        return when (res.status.value) {
            in 200..299 -> Result.Ok(true)
            400 -> Result.Error(NetworkError.BAD_REQUEST)
            404 -> Result.Error(NetworkError.NOT_FOUND)
            500 -> Result.Error(NetworkError.INTERNAL_ERROR)
            else -> Result.Error(NetworkError.UNKNOWN)
        }
    }

    @Serializable
    private data class UpdateTripReq(
        val id: Int,
        val user_id: Int,
        val distance: Int?,
        val trip_type: Trip.TripType?
    )

    suspend fun deleteTrip(
        id: Int,
    ): Result<Boolean, NetworkError> {
        val res = try {
            client.delete(baseUrl) {
                contentType(ContentType.Application.Json)
                setBody(DeleteTripReq(id))
            }
        } catch (e: UnresolvedAddressException) {
            return Result.Error(NetworkError.NOT_CONNECTED)
        } catch (e: SerializationException) {
            return Result.Error(NetworkError.SERIALIZATION_ERROR)
        }

        return when (res.status.value) {
            in 200..299 -> Result.Ok(true)
            400 -> Result.Error(NetworkError.BAD_REQUEST)
            404 -> Result.Error(NetworkError.NOT_FOUND)
            500 -> Result.Error(NetworkError.INTERNAL_ERROR)
            else -> Result.Error(NetworkError.UNKNOWN)
        }
    }

    @Serializable
    private data class DeleteTripReq(val id: Int);
}

@Serializable
data class Trip(val id: Int, val user_id: Int, val distance: Int, val trip_type: TripType) {
    @Serializable(with = TripTypeSerializer::class)
    enum class TripType {
        WALK,
        BIKE
    }

    private object TripTypeSerializer : KSerializer<TripType> {
        override val descriptor: SerialDescriptor =
            PrimitiveSerialDescriptor("com.cyclepaths.www.api", PrimitiveKind.STRING)

        override fun serialize(
            encoder: Encoder,
            value: TripType
        ) {
            val string = when (value) {
                TripType.WALK -> "walk"
                TripType.BIKE -> "bike"
            }
            encoder.encodeString(string)
        }

        override fun deserialize(decoder: Decoder): TripType {
            val string = decoder.decodeString()
            return when (string) {
                "walk" -> TripType.WALK
                "bike" -> TripType.BIKE
                else -> throw SerializationException("Unexpected enum value: $string")
            }
        }

    }
}

@Serializable
data class Location(val latitude: Double, val longitude: Double, val time: String)