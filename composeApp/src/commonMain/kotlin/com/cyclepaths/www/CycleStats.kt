package com.cyclepaths.www

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType.Companion.Uri
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import cyclepaths.composeapp.generated.resources.Res
import cyclepaths.composeapp.generated.resources.WelcomeBackground
import cyclepaths.composeapp.generated.resources.badge
import cyclepaths.composeapp.generated.resources.cycleoption
import cyclepaths.composeapp.generated.resources.gear
import cyclepaths.composeapp.generated.resources.josefin_sans_bold
import cyclepaths.composeapp.generated.resources.josefin_sans_italic
import cyclepaths.composeapp.generated.resources.josefin_sans_regular
import cyclepaths.composeapp.generated.resources.mapoption
import cyclepaths.composeapp.generated.resources.viewheatmapoption
import cyclepaths.composeapp.generated.resources.walkoption
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.text.intl.Locale
import kotlinx.serialization.StringFormat
import kotlin.math.round


@Composable
fun CycleStats(navController: NavController) {
    MaterialTheme {
        Box(
            modifier = Modifier
                .background(Color(0xFF2E377C))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(35.dp),
                contentAlignment = Alignment.TopStart
            ) {
                TextButton(
                    onClick = { navController.navigate("welcome") }
                ) {
                    Text(
                        "< Back",
                        color = Color(0xFFA8A4FF),
                        fontWeight = FontWeight.Light
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(35.dp),
                contentAlignment = Alignment.TopEnd
            ) {
                Image(
                    painter = painterResource(Res.drawable.gear),
                    contentDescription = "Gear icon",
                    modifier = Modifier.size(30.dp)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 85.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                val josefinSansFamily = FontFamily(
                    Font(Res.font.josefin_sans_regular, FontWeight.Normal),
                    Font(Res.font.josefin_sans_bold, FontWeight.Bold),
                    Font(Res.font.josefin_sans_italic, FontWeight.Normal, FontStyle.Italic)
                )
                Text(
                    buildAnnotatedString {
                        append("You saved\n")

                        withStyle(
                            SpanStyle(
                                color = Color(0xFF9958F9),
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append("${CO2SavedCombustion()} kg-eq CO2 compared to combustion vehicles\n")
                            append("${CO2SavedElectric()} kg-eq CO2 compared to electric vehicles\n")
                        }

                        append("\nYou cycled\n")

                        withStyle(
                            SpanStyle(
                                color = Color(0xFF9958F9),
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append("${CycleMiles()} miles\n")
                        }
                    },
                    color = Color(0xFFA8A4FF),
                    textAlign = TextAlign.Center,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                    lineHeight = 38.sp
                )
                Spacer(modifier = Modifier.height(45.dp))

                val uriHandler = LocalUriHandler.current

                Button(
                    onClick = {
                        uriHandler.openUri("http://10.0.2.2:5173/")
                    },
                    border = BorderStroke(1.dp, Color.White),
                    shape = RoundedCornerShape(5.dp),
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier
                        .padding(15.dp)
                        .size(width = 500.dp, height = 70.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Image(
                            painter = painterResource(Res.drawable.viewheatmapoption),
                            contentDescription = null,
                            modifier = Modifier.matchParentSize(),
                            contentScale = ContentScale.FillBounds,
                        )

                        Text(
                            "View Cycle Heat Map",
                            color = Color.Black,
                            fontFamily = josefinSansFamily,
                            fontSize = 30.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                //WeeklyCycleBadges()
            }
        }
    }
}



/**
 * Fetch the total miles cycled by the user.
 */
@Composable
fun CycleMiles(): Double? {
    val scope = rememberCoroutineScope()
    var totalMiles by remember { mutableStateOf<Double?>(0.0) }
    val api = remember { BackendAPI(BACKEND_URL) }
    val userId = SessionManager.currentUser?.id

    LaunchedEffect(userId) {
        scope.launch {
            try {
                totalMiles = userId?.let { api.getTotalDistanceMilesCycle(it) }
            } catch (e: Exception) {
                println("Error fetching total distance: ${e.message}")
            }
        }
    }
    return totalMiles
}

/**
 * Calculate the CO2 saved by the user for combustion vehicles.
 */
@Composable
fun CO2SavedCombustion(): Double? {
    /*
    electric vehicle = 0.1286 kg CO2-eq/km for battery electric vehicles.
    combustion vehicle = 0.2032 kg CO2-eq/km for internal combustion engines. Convert miles to km or this to CO2-eq/mile
    bike_conversion= 0.0296-0.0818 kg CO2-eq/km for biking (we can take the average or median and use it, need to convert for miles too)
    CO2_saved = (miles * vehicle_conversion) – (miles * bike_conversion)
     */
    val miles = CycleMiles() ?: return null
    val combustionVehicle = 0.1262626263 // in miles
    val bike = 0.0183925873 // in miles
    val CO2Saved = (miles * combustionVehicle) - (miles * bike)

    return (round(CO2Saved * 100.0) / 100.0)
}

/**
 * Calculate the CO2 saved by the user for electric vehicles.
 */
@Composable
fun CO2SavedElectric(): Double? {
    /*
    electric vehicle = 0.1286 kg CO2-eq/km for battery electric vehicles.
    combustion vehicle = 0.2032 kg CO2-eq/km for internal combustion engines. Convert miles to km or this to CO2-eq/mile
    bike_conversion= 0.0296-0.0818 kg CO2-eq/km for biking (we can take the average or median and use it, need to convert for miles too)
    CO2_saved = (miles * vehicle_conversion) – (miles * bike_conversion)
     */
    val miles = CycleMiles() ?: return null
    val electricVehicle = 0.0799083353 // in miles
    val bike = 0.0183925873 // in miles
    val CO2Saved = (miles * electricVehicle) - (miles * bike)

    return (round(CO2Saved * 100.0) / 100.0)
}