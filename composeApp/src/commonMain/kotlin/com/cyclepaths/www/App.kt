package com.cyclepaths.www

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.navigation.compose.*
import cyclepaths.composeapp.generated.resources.Res
import cyclepaths.composeapp.generated.resources.josefin_sans_bold
import cyclepaths.composeapp.generated.resources.josefin_sans_italic
import cyclepaths.composeapp.generated.resources.josefin_sans_regular
import cyclepaths.composeapp.generated.resources.loginBKG
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
fun App(modifier: Modifier = Modifier) {
    // Initialize the navigation controller only once
    val navController = rememberNavController()

    MaterialTheme {
        NavHost(navController, startDestination = "login", builder = {
            composable("login") {
                Login(navController)
            }

            composable("signup") {
                Signup(navController)
            }
        })
    }
}

@Preview
@Composable
fun AppPreview() {
    App()
}

@Composable
fun Login(navController: NavController) {
    MaterialTheme {
        Box {
            Image(
                painter = painterResource(Res.drawable.loginBKG),
                contentDescription = "login background",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxSize()
                    .blur(
                        radius = 4.dp,
                        edgeTreatment = BlurredEdgeTreatment.Unbounded
                    )
            )

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                val josefinSansFamily = FontFamily(
                    Font(Res.font.josefin_sans_regular, FontWeight.Normal),
                    Font(Res.font.josefin_sans_bold, FontWeight.Bold),
                    Font(Res.font.josefin_sans_italic, FontWeight.Normal, FontStyle.Italic)
                )

                Box {
                    val textStyle = TextStyle(
                        fontFamily = josefinSansFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 40.sp,
                    )

                    // Layer 1 (Bottom): The Stroke/Outline
                    Text(
                        text = "CyclePaths",
                        style = textStyle.copy(
                            color = Color.Black,
                            drawStyle = Stroke(5F)
                        )
                    )

                    // Layer 2 (Top): The Solid Fill
                    Text(
                        text = "CyclePaths",
                        style = textStyle.copy(
                            color = Color(0xFFFF850B)
                        )
                    )
                }

                val modSpace = Modifier.padding(10.dp)
                var user by remember { mutableStateOf("") }

                OutlinedTextField(
                    value = user,
                    onValueChange = { user = it },
                    placeholder = { Text("Username") },
                    shape = RoundedCornerShape(5.dp),
                    modifier = modSpace,

                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = Color.Black,

                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                    )
                )

                var password by remember { mutableStateOf("") }
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = { Text("Password") },
                    shape = RoundedCornerShape(5.dp),
                    modifier = modSpace,

                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = Color.Black,

                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                    )
                )

                Button(
                    onClick = { /* Coming back to this */ },
                    shape = RoundedCornerShape(5.dp),
                    modifier = Modifier.padding(15.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF3958C8)
                    ),
                ) {
                    Text("Sign In", color = Color.White, fontFamily = josefinSansFamily)
                }

                Box {
                    val textStyle = TextStyle(
                        fontFamily = josefinSansFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                    )

                    Text(
                        text = "Don't have an account?",
                        style = textStyle.copy(
                            color = Color.White,
                            drawStyle = Stroke(5F)
                        )
                    )

                    Text(
                        "Don't have an account?",
                        style = textStyle.copy(
                            color = Color(0xFFFF1128F8)
                        ),
                        modifier = Modifier
                            .clickable{navController.navigate("signup")}
                    )
                }
            }
        }
    }
}
