package com.cyclepaths.www

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import cyclepaths.composeapp.generated.resources.Res
import cyclepaths.composeapp.generated.resources.josefin_sans_bold
import cyclepaths.composeapp.generated.resources.josefin_sans_italic
import cyclepaths.composeapp.generated.resources.josefin_sans_regular
import cyclepaths.composeapp.generated.resources.welcomeBKG
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun TripSelection(navController: NavController) {
    MaterialTheme {
        Box {
            Image(
                painter = painterResource(Res.drawable.welcomeBKG),
                contentDescription = "Welcome Background",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxSize()
            )

            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                val josefinSansFamily = FontFamily(
                    Font(Res.font.josefin_sans_regular, FontWeight.Normal),
                    Font(Res.font.josefin_sans_bold, FontWeight.Bold),
                    Font(Res.font.josefin_sans_italic, FontWeight.Normal, FontStyle.Italic)
                )

                val mainTextStyle = TextStyle(
                    fontFamily = josefinSansFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 36.sp,
                )
                val buttonTextStyle = TextStyle(
                    fontFamily = josefinSansFamily,
                    fontWeight = FontWeight.Thin,
                    fontSize = 36.sp,
                    color = Color.White
                )

                val user = "User" // TODO: Temp value.


                Box {
                    val welcomeText = "Welcome $user!\nHow are you commuting today?"
                    // Layer 1 (Bottom): The Stroke/Outline
                    Text(
                        text = welcomeText,
                        style = mainTextStyle.copy(
                            color = Color.Black,
                            drawStyle = Stroke(5F)
                        )
                    )

                    // Layer 2 (Top): The Solid Fill
                    Text(
                        text = welcomeText,
                        style = mainTextStyle.copy(
                            color = Color.White
                        )
                    )
                }
                Button(
                    onClick = {
                        // TODO
                    },
                    shape = RoundedCornerShape(2.dp),
                    modifier = Modifier.padding(15.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFADFF2F)
                    ),
                ) {
                    Text(
                        "Cycling", style = buttonTextStyle.copy(
                            color = Color.White,
                            //drawStyle = Stroke(5F)
                        )
                    )
                }
                Button(
                    onClick = {
                        // TODO
                    },
                    shape = RoundedCornerShape(2.dp),
                    modifier = Modifier.padding(15.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF006400)
                    ),
                ) {
                    Text(
                        "Walking",
                        style = buttonTextStyle.copy(
                            color = Color.White,
                            //drawStyle = Stroke(5F)
                        ),
                    )
                }

            }
        }
    }
}