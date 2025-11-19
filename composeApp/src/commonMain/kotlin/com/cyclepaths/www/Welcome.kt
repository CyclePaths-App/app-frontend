package com.cyclepaths.www

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import cyclepaths.composeapp.generated.resources.Res
import cyclepaths.composeapp.generated.resources.WelcomeBackground
import cyclepaths.composeapp.generated.resources.cycleoption
import cyclepaths.composeapp.generated.resources.gear
import cyclepaths.composeapp.generated.resources.josefin_sans_bold
import cyclepaths.composeapp.generated.resources.josefin_sans_italic
import cyclepaths.composeapp.generated.resources.josefin_sans_regular
import cyclepaths.composeapp.generated.resources.walkoption
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource

@Composable
fun Welcome(navController: NavController) {
    MaterialTheme {
        Box {
            Image(
                painter = painterResource(Res.drawable.WelcomeBackground),
                contentDescription = "welcome page background",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(40.dp),
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
                    .padding(top = 85.dp)
                    .padding(horizontal = 30.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                val josefinSansFamily = FontFamily(
                    Font(Res.font.josefin_sans_regular, FontWeight.Normal),
                    Font(Res.font.josefin_sans_bold, FontWeight.Bold),
                    Font(Res.font.josefin_sans_italic, FontWeight.Normal, FontStyle.Italic)
                )

                Box() {
                    val textStyle = TextStyle(
                        fontFamily = josefinSansFamily,
                        fontStyle = FontStyle.Italic,
                        fontSize = 40.sp,
                    )

                    // Layer 1 (Bottom): The Stroke/Outline
                    Text(
                        text = "Welcome User! How are you commuting today?",
                        style = textStyle.copy(
                            color = Color.Black,
                            drawStyle = Stroke(5F)
                        ),
                        textAlign = TextAlign.Left
                    )

                    // Layer 2 (Top): The Solid Fill
                    Text(
                        text = "Welcome User! How are you commuting today?",
                        style = textStyle.copy(
                            color = Color.White
                        ),
                        textAlign = TextAlign.Left
                    )
                }

                Spacer(modifier = Modifier.height(50.dp))

                Button(
                    onClick = { navController.navigate("bikeTrip") },
                    border = BorderStroke(1.dp, Color.White),
                    shape = RoundedCornerShape(5.dp),
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier
                        .padding(vertical = 15.dp)
                        .size(width = 500.dp, height = 70.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Image(
                            painter = painterResource(Res.drawable.cycleoption),
                            contentDescription = null,
                            modifier = Modifier.matchParentSize(),
                            contentScale = ContentScale.Crop
                        )

                        Text(
                            "Cycling",
                            color = Color.White,
                            fontFamily = josefinSansFamily,
                            fontSize = 30.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                Button(
                    onClick = { navController.navigate("walkTrip") },
                    border = BorderStroke(1.dp, Color.White),
                    shape = RoundedCornerShape(5.dp),
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier
                        .padding(15.dp)
                        .size(width = 500.dp, height = 70.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Image(
                            painter = painterResource(Res.drawable.walkoption),
                            contentDescription = null,
                            modifier = Modifier.matchParentSize(),
                            contentScale = ContentScale.Crop,
                        )

                        Text(
                            "Walking",
                            color = Color.White,
                            fontFamily = josefinSansFamily,
                            fontSize = 30.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}