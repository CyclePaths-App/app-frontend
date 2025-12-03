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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import cyclepaths.composeapp.generated.resources.Res
import cyclepaths.composeapp.generated.resources.josefin_sans_bold
import cyclepaths.composeapp.generated.resources.josefin_sans_italic
import cyclepaths.composeapp.generated.resources.josefin_sans_regular
import dev.jordond.compass.geolocation.MobileGeolocator
import dev.jordond.compass.geolocation.TrackingStatus
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun RecordingTripDemo() {
    RecordingTrip(rememberNavController(), TripType.bike)
}

@Composable
fun RecordingTrip(navController: NavController, tripType: TripType) {
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
                val josefinSansFamily = FontFamily(
                    Font(Res.font.josefin_sans_regular, FontWeight.Normal),
                    Font(Res.font.josefin_sans_bold, FontWeight.Bold),
                    Font(Res.font.josefin_sans_italic, FontWeight.Normal, FontStyle.Italic)
                )
                val api = remember { BackendAPI(BACKEND_URL) }
                var errorMessge by remember { mutableStateOf<String?>(null) }
                var locationList by remember { mutableStateOf(ArrayList<BackendAPI.Location>()) }
                val scope = rememberCoroutineScope()

                Box {
                    val textStyle = TextStyle(
                        fontFamily = josefinSansFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 40.sp,
                    )

                    val message = when (tripType) {
                        TripType.bike -> "Recording cycling trip."
                        TripType.walk -> "Recording walking trip."
                    }

                    // Layer 1 (Bottom): The Stroke/Outline
                    Text(
                        text = message,
                        style = textStyle.copy(
                            color = Color.Black,
                            drawStyle = Stroke(5F)
                        )
                    )

                    // Layer 2 (Top): The Solid Fill
                    Text(
                        text = message,
                        style = textStyle.copy(
                            color = Color(0xFFFF850B)
                        )
                    )
                }

                val modSpace = Modifier.padding(10.dp)

                Button(
                    onClick = {
                        var response: Result<Int>
                        scope.launch {
//                            val standardList =
//                                listOf(
//                                    BackendAPI.Location(
//                                        latitude = 42.686261,
//                                        longitude = -73.828025,
//                                        time = LocalDateTime(2025, 9, 20, 18, 5, 0).toInstant(
//                                            TimeZone.UTC
//                                        ).toString()
//                                    ),
//                                    BackendAPI.Location(
//                                        latitude = 42.686945,
//                                        longitude = -73.827349,
//                                        time = LocalDateTime(2025, 9, 20, 18, 5, 15).toInstant(
//                                            TimeZone.UTC
//                                        ).toString()
//                                    ),
//                                    BackendAPI.Location(
//                                        latitude = 42.687378,
//                                        longitude = -73.826919,
//                                        time = LocalDateTime(2025, 9, 20, 18, 5, 30).toInstant(
//                                            TimeZone.UTC
//                                        ).toString()
//                                    )
//                                )
                            response = api.createTrip(userId = 1, locationList, tripType)
                            response.onSuccess {
                                navController.navigate(
                                    when (tripType) {
                                        TripType.bike -> "cyclestats"
                                        TripType.walk -> "walkstats"
                                    }
                                )
                            }.onFailure {
                                // Just temporary.
                                navController.navigate(
                                    when (tripType) {
                                        TripType.bike -> "cyclestats"
                                        TripType.walk -> "walkstats"
                                    }
                                )
                                //errorMessge = it.message
                            }
                        }
                    },
                    border = BorderStroke(1.dp, Color.Black),
                    shape = RoundedCornerShape(100),
                    modifier = Modifier.padding(15.dp).size(300.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF3958C8)
                    ),
                ) {
                    Text(
                        "End Trip",
                        color = Color.White,
                        style = TextStyle(
                            fontFamily = josefinSansFamily,
                            fontSize = 35.sp,
                        )
                    )
                }

                errorMessge?.let {
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