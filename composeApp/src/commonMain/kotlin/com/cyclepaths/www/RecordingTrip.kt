package com.cyclepaths.www

import androidx.collection.mutableLongSetOf
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
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
import dev.jordond.compass.geolocation.TrackingStatus
import kotlinx.coroutines.launch
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
    val scope = rememberCoroutineScope()
    var tracker by remember { mutableStateOf(LocationTracker()) }
    var isTracking by rememberSaveable { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }



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

                if (!isTracking) {
                    RecordingButton(
                        onClick = {
                            tracker.startTracking()
                            isTracking = true
                        },
                        "Start Tracking",
                        true
                    )
                } else {
                    RecordingButton(
                        onClick = {
                            scope.launch {

                                tracker.stopTracking()// Stop tracking to prevent overlapping thread nonsense.
                                isTracking = false

                                api.createTrip(userId = 1, tracker.trip, tripType).onSuccess {
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
                        true //tracker.trip.isNotEmpty()
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