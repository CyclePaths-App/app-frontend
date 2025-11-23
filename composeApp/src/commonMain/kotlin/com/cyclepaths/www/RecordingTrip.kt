package com.cyclepaths.www

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.cyclepaths.www.components.OutlinedText
import cyclepaths.composeapp.generated.resources.Res
import cyclepaths.composeapp.generated.resources.josefin_sans_bold
import cyclepaths.composeapp.generated.resources.josefin_sans_italic
import cyclepaths.composeapp.generated.resources.josefin_sans_regular
import dev.jordond.compass.Priority
import dev.jordond.compass.geolocation.LocationRequest
import dev.jordond.compass.geolocation.MobileGeolocator
import dev.jordond.compass.geolocation.TrackingStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun RecordingTripDemo() {
    RecordingTrip(rememberNavController(), TripType.bike)
}

@Composable
fun RecordingButton(onClick: () -> Unit, message: String, enabled: Boolean) {
    Button(
        onClick = onClick,
        border = BorderStroke(1.dp, Color.Black),
        shape = RoundedCornerShape(100),
        modifier = Modifier.padding(15.dp).size(300.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFFF3958C8)
        ),
        enabled = enabled
    ) {
        Text(
            message,
            color = Color.White,
            style = TextStyle(
                fontFamily = FontFamily(
                    Font(Res.font.josefin_sans_regular, FontWeight.Normal),
                    Font(Res.font.josefin_sans_bold, FontWeight.Bold),
                    Font(Res.font.josefin_sans_italic, FontWeight.Normal, FontStyle.Italic)
                ),
                fontSize = 35.sp,
            )
        )
    }
}

@Composable
fun RecordingTrip(navController: NavController, tripType: TripType) {
    val josefinSansFamily = FontFamily(
        Font(Res.font.josefin_sans_regular, FontWeight.Normal),
        Font(Res.font.josefin_sans_bold, FontWeight.Bold),
        Font(Res.font.josefin_sans_italic, FontWeight.Normal, FontStyle.Italic)
    )

    val api = remember { BackendAPI(BACKEND_URL) }

    var errorMessage by remember { mutableStateOf<String?>(null) }
    var trip by remember { mutableStateOf(ArrayList<BackendAPI.Location>()) }

    val scope = rememberCoroutineScope()

    val tracker = remember { MobileGeolocator() }
    val trackingStatus by tracker.trackingStatus.collectAsState(initial = null)

    // Launch effect runs in the background
    LaunchedEffect(Unit) {
        tracker.trackingStatus.collect { status ->
            when (status) { // When a status changes, the collection sends that here
                is TrackingStatus.Error -> {
                    val error = status.cause
                    errorMessage = "TRACKING ERROR: $error"
                }

                TrackingStatus.Idle -> {}   // Don't need to do anything if it is idle.
                TrackingStatus.Tracking -> {}   // Waiting for an update, no need to do anything.
                is TrackingStatus.Update -> {
                    // Unwrap and rewrap location information.
                    val latitude = status.location.coordinates.latitude
                    val longitude = status.location.coordinates.longitude
                    val time = Clock.System.now().toLocalDateTime(TimeZone.UTC)
                        .toString()// Whomst designed this API?

                    val currentLocation = BackendAPI.Location(latitude, longitude, time)

                    // Add to list.
                    trip.add(currentLocation)
                }
            }
        }
    }

    MaterialTheme {
        Box(
            modifier = Modifier.background(Color(0xFF2E377C))
                .fillMaxSize()
                .padding(35.dp)
        )
        {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                val message = when (tripType) {
                    TripType.bike -> "Recording cycling trip."
                    TripType.walk -> "Recording walking trip."
                }
                OutlinedText(
                    message = message,
                    outlineColor = Color.Black,
                    fillColor = Color(0xFFFF850B),
                    style = TextStyle(
                        fontFamily = josefinSansFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 40.sp
                    )
                )

                if (trackingStatus == TrackingStatus.Idle) {
                    RecordingButton(
                        onClick = {
                            scope.launch(Dispatchers.IO) {
                                tracker.startTracking(
                                    LocationRequest(Priority.HighAccuracy, 1000)
                                )
                            }
                        }, "Start Tracking",
                        true
                    )
                } else {
                    RecordingButton(
                        onClick = {
                            scope.launch {
                                tracker.stopTracking()// Stop tracking to prevent overlapping thread nonsense.

                                api.createTrip(userId = 1, trip, tripType).onSuccess {
                                    navController.navigate(
                                        when (tripType) {
                                            TripType.bike -> "cyclestats"
                                            TripType.walk -> "walkstats"
                                        }
                                    )
                                }.onFailure {
                                    errorMessage = it.message
                                }
                            }
                        },
                        "End trip",
                        trip.isNotEmpty() // Needs entries in the trip to avoid Bad Request.
                    )
                }
                errorMessage?.let {
                    Text(
                        it,
                        color = Color.Red,
                        style = TextStyle(
                            fontFamily = josefinSansFamily,
                            fontSize = 30.sp,
                        )
                    )
                }
            }
        }
    }
}