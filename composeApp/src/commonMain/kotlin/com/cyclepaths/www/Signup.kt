package com.cyclepaths.www

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.cyclepaths.www.components.OutlinedText
import cyclepaths.composeapp.generated.resources.Res
import cyclepaths.composeapp.generated.resources.eye
import cyclepaths.composeapp.generated.resources.josefin_sans_bold
import cyclepaths.composeapp.generated.resources.josefin_sans_italic
import cyclepaths.composeapp.generated.resources.josefin_sans_regular
import cyclepaths.composeapp.generated.resources.visible
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun Signup(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFF850B)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        val josefinSansFamily = FontFamily(
            Font(Res.font.josefin_sans_regular, FontWeight.Normal),
            Font(Res.font.josefin_sans_bold, FontWeight.Bold),
            Font(Res.font.josefin_sans_italic, FontWeight.Normal, FontStyle.Italic)
        )

        val textStyle = TextStyle(
            fontFamily = josefinSansFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 35.sp,
        )

        // Layer 1 (Bottom): The Stroke/Outline
        Text(
            text = "Create Account",
            style = textStyle.copy(
                color = Color.White,
            ),
            modifier = Modifier.padding(bottom = 10.dp)
        )

        val modSpace = Modifier.padding(bottom = 10.dp)
        val api = remember { BackendAPI(BACKEND_URL) }
        val scope = rememberCoroutineScope()
        var username by remember { mutableStateOf("") }
        var firstName by remember { mutableStateOf("") }
        var lastName by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        Column(
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                "Enter first name",
                color = Color.Black,
                fontFamily = josefinSansFamily,
                fontSize = 15.sp
            )

            OutlinedTextField(
                value = firstName,
                onValueChange = { firstName = it },
                placeholder = { Text("First name", fontSize = 10.sp) },
                singleLine = true,
                shape = RoundedCornerShape(5.dp),
                modifier =
                    modSpace
                        .height(55.dp),

                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Black,

                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                )
            )

            Text(
                "Enter last name",
                color = Color.Black,
                fontFamily = josefinSansFamily,
                fontSize = 15.sp
            )

            OutlinedTextField(
                value = lastName,
                onValueChange = { lastName = it },
                placeholder = { Text("Last name", fontSize = 10.sp) },
                shape = RoundedCornerShape(5.dp),
                singleLine = true,
                modifier =
                    modSpace
                        .height(55.dp),

                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Black,

                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                )
            )

            Text(
                "Enter email",
                color = Color.Black,
                fontFamily = josefinSansFamily,
                fontSize = 15.sp
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text("Email", fontSize = 10.sp) },
                singleLine = true,
                shape = RoundedCornerShape(5.dp),
                modifier =
                    modSpace
                        .height(55.dp),

                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Black,

                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                )
            )

            Text(
                "Enter username",
                color = Color.Black,
                fontFamily = josefinSansFamily,
                fontSize = 15.sp
            )

            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                placeholder = { Text("Username", fontSize = 10.sp) },
                shape = RoundedCornerShape(5.dp),
                singleLine = true,
                modifier =
                    modSpace
                        .height(55.dp),

                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Black,

                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                )
            )

            Text(
                "Enter password",
                color = Color.Black,
                fontFamily = josefinSansFamily,
                fontSize = 15.sp
            )

            // Toggle password visibility.
            var passwordVisible by remember { mutableStateOf(false) }
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text("Password", fontSize = 10.sp) },
                singleLine = true,
                shape = RoundedCornerShape(5.dp),
                modifier =
                    modSpace
                        .height(55.dp),

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

            Text(
                "Confirm password",
                color = Color.Black,
                fontFamily = josefinSansFamily,
                fontSize = 15.sp
            )

            var confirmPassword by remember { mutableStateOf("") }

            // Toggle password visibility.
            var confirmPasswordVisible by remember { mutableStateOf(false) }
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                placeholder = { Text("Re-enter password", fontSize = 10.sp) },
                singleLine = true,
                shape = RoundedCornerShape(5.dp),
                modifier =
                    Modifier.padding(bottom = 9.dp)
                        .height(55.dp),

                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Black,

                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                ),
                visualTransformation =
                    if (confirmPasswordVisible)
                        VisualTransformation.None
                    else
                        PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    val icon =
                        if (confirmPasswordVisible)
                            Res.drawable.visible
                        else
                            Res.drawable.eye

                    IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                        Icon(
                            painter = painterResource(icon),
                            contentDescription =
                                if (confirmPasswordVisible)
                                    "Hide password"
                                else "Show password",
                            modifier = Modifier
                                .size(25.dp)
                        )
                    }
                }
            )

            val isNotMatching = remember {
                derivedStateOf {
                    password != confirmPassword
                }
            }

            if (isNotMatching.value) {
                Text(text = "Password is incorrect", fontSize = 10.sp, color = Color.White)
            }
        }

        Button(
            onClick = {
                scope.launch {
                    try {
                        api.createUser(username, firstName, lastName, email, password)
                            .onSuccess { id ->
                                SessionManager.currentUser =
                                    User(id, username, firstName, lastName, email, password)
                                navController.navigate("welcome")
                            }
                        println("Created user: ${username}")
                    } catch (e: Exception) {
                        println("Error creating user: ${e.message}")
                    }
                }
            },
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier.padding(bottom = 9.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFF3958C8),
            ),
        ) {
            Text("Create Account", color = Color.White, fontFamily = josefinSansFamily)
        }

        OutlinedText(
            message = "Have an account already?",
            outlineColor = Color.White,
            fillColor = Color(0xFFFF1128F8),
            fontFamily = josefinSansFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            outlineWeight = 5F,
            modifier = Modifier
                .clickable { navController.navigate("login") }
        )
    }
}