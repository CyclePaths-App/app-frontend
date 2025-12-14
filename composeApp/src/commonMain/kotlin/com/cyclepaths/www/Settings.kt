package com.cyclepaths.www

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import cyclepaths.composeapp.generated.resources.Res
import cyclepaths.composeapp.generated.resources.josefin_sans_bold
import cyclepaths.composeapp.generated.resources.josefin_sans_italic
import cyclepaths.composeapp.generated.resources.josefin_sans_regular
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun Settings(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2E377C)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(35.dp),
            contentAlignment = Alignment.TopStart
        ) {
            TextButton(
                onClick = { navController.popBackStack() }
            ) {
                Text(
                    "< Back",
                    color = Color(0xFFA8A4FF),
                    fontWeight = FontWeight.Light
                )
            }
        }

        val josefinSansFamily = FontFamily(
            Font(Res.font.josefin_sans_regular, FontWeight.Normal),
            Font(Res.font.josefin_sans_bold, FontWeight.Bold),
            Font(Res.font.josefin_sans_italic, FontWeight.Normal, FontStyle.Italic)
        )

        val textStyle = TextStyle(
            fontFamily = josefinSansFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 40.sp,
        )

        // Layer 1 (Bottom): The Stroke/Outline
        Text(
            text = "Settings",
            style = textStyle.copy(
                color = Color(0xFFA8A4FF),
            ),
            modifier = Modifier.padding(bottom = 40.dp)
        )

        val modSpace = Modifier.padding(bottom = 20.dp)
        var user by remember { mutableStateOf("") }

        Column(
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                "Full Name",
                color = Color.Black,
                fontFamily = josefinSansFamily,
            )

            OutlinedTextField(
                value = user,
                onValueChange = { user = it },
                placeholder = { Text("John Doe") },
                shape = RoundedCornerShape(5.dp),
                modifier = modSpace,

                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Black,

                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                )
            )

            Text(
                "Email",
                color = Color.Black,
                fontFamily = josefinSansFamily
            )

            var email by remember { mutableStateOf("") }
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text("Email") },
                shape = RoundedCornerShape(5.dp),
                modifier = modSpace,

                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Black,

                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                )
            )

            Text(
                "Password",
                color = Color.Black,
                fontFamily = josefinSansFamily
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
        }

        Button(
            onClick = { /* Coming back to this */ },
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier.padding(15.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFF3958C8),
            ),
            border = BorderStroke(width = 1.dp, Color.Red)
        ) {
            Text("Delete Account", color = Color.White, fontFamily = josefinSansFamily)
        }
    }
}