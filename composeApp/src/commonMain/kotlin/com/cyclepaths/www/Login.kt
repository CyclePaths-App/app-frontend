package com.cyclepaths.www

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.*
import cyclepaths.composeapp.generated.resources.Res
import cyclepaths.composeapp.generated.resources.eye
import cyclepaths.composeapp.generated.resources.josefin_sans_bold
import cyclepaths.composeapp.generated.resources.josefin_sans_italic
import cyclepaths.composeapp.generated.resources.josefin_sans_regular
import cyclepaths.composeapp.generated.resources.loginBKG
import cyclepaths.composeapp.generated.resources.visible
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
fun App() {
    // Initialize the navigation controller only once
    val navController = rememberNavController()

    MaterialTheme {
        NavHost(navController, startDestination = "login", builder = {
            composable("login") { Login(navController) }
            composable("signup") { Signup(navController) }
            composable("welcome") { Welcome(navController) }
            composable("walkstats") { WalkStats(navController) }
            composable("cyclestats") { CycleStats(navController) }
            composable("map") { Map(navController) }
            composable("bikeTrip") { RecordingTrip(navController, TripType.bike) }
            composable("walkTrip") { RecordingTrip(navController, TripType.walk) }
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
                val api = remember { BackendAPI(BACKEND_URL) }
                val scope = rememberCoroutineScope()
                var user by remember { mutableStateOf("") }
                var password by remember { mutableStateOf("") }

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

                // Toggle password visibility.
                var passwordVisible by remember { mutableStateOf(false) }
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = { Text("Password") },
                    singleLine = true,
                    shape = RoundedCornerShape(5.dp),
                    modifier = modSpace,

                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = Color.Black,

                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                    ),
                    visualTransformation =
                        if (passwordVisible)
                            VisualTransformation.None
                        else
                            PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        val icon =
                            if (passwordVisible)
                                Res.drawable.visible
                            else
                                Res.drawable.eye

                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                painter = painterResource(icon),
                                contentDescription =
                                    if (passwordVisible)
                                        "Hide password"
                                    else "Show password",
                                modifier = Modifier
                                    .size(25.dp)
                            )
                        }
                    }
                )

                Button(
                    onClick = {
                        scope.launch {
                            val loggedInUser = api.sendLogin(user, password)
                            if (loggedInUser != null) {
                                println("Logged in as: ${loggedInUser.username}")
                                SessionManager.currentUserId = loggedInUser.id
                                navController.navigate("welcome")
                            } else {
                                println("Invalid credentials")
                            }
                        }
                        navController.navigate("welcome")
                    },
                    border = BorderStroke(1.dp, Color.Black),
                    shape = RoundedCornerShape(5.dp),
                    modifier = Modifier.padding(15.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF3958C8)
                    ),
                ) {
                    Text("Login", color = Color.White, fontFamily = josefinSansFamily)
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
                            .clickable { navController.navigate("signup") }
                    )
                }
            }
        }
    }
}
