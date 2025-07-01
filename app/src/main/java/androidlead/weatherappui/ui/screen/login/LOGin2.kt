package androidlead.weatherappui.ui.screen.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import androidlead.weatherappui.R
import androidlead.weatherappui.ui.theme.cuLightBlue
import androidlead.weatherappui.ui.theme.cuOrange

@Preview
@Composable
fun LOGin2(
    onLoginClick: () -> Unit = {},
    onSignUpClick: () -> Unit = {}) {
    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val showPassword = remember { mutableStateOf(false) }
    val errorMessage = remember { mutableStateOf<String?>(null) }

    LaunchedEffect(username.value) {
        delay(1500)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.night),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Box(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .graphicsLayer(alpha = 0.5f)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.eagle),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .blur(5555.dp)
                )
            }

            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .size(width = 300.dp, height = 430.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color.White.copy(alpha = 0.2f))
                ) {
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .background(Color.White.copy(alpha = 0.1f))
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.6f), shape = RoundedCornerShape(20.dp))
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Have an Account?",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        OutlinedTextField(
                            value = username.value,
                            onValueChange = {
                                if (it.length <= 25) {
                                    username.value = it
                                    errorMessage.value = null
                                } else {
                                    errorMessage.value = "Max character limit is 25"
                                }   
                            },
                            label = { Text("Username", color = Color.White) },
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Color.LightGray,
                                focusedBorderColor = cuLightBlue,
                                cursorColor = Color.White,
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedLabelColor = Color.Red,
                                unfocusedLabelColor = Color.White,
                                unfocusedContainerColor = Color.Transparent,
                                focusedContainerColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent,
                                errorBorderColor = Color.Red,
                                errorTextColor = Color.Red,
                                errorLabelColor   = Color.Red,
                                errorTrailingIconColor = Color.Red
                            ),
                            trailingIcon = {
                                if (!errorMessage.value.isNullOrBlank()) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.baseline_error_outline_24),
                                        contentDescription = "Error",
                                        tint = Color.Red,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            singleLine = true,
                            isError = !errorMessage.value.isNullOrBlank(),
                            supportingText = {
                                if (!errorMessage.value.isNullOrBlank()) {
                                    Text(errorMessage.value!!, color = Color.Red)
                                }
                            }
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedTextField(
                            value = password.value,
                            onValueChange = { password.value = it },
                            label = { Text("Password", color = Color.White) },
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Color.LightGray,
                                focusedBorderColor = cuLightBlue,
                                cursorColor = Color.White,
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedLabelColor = Color.Yellow,
                                unfocusedLabelColor = Color.White,
                                unfocusedContainerColor = Color.Transparent,
                                focusedContainerColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent,
                                errorBorderColor = Color.Red,
                                errorTextColor = Color.Red,
                                errorLabelColor = Color.Red,
                                errorTrailingIconColor = Color.Red
                            ),
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            trailingIcon = {
                                IconButton(
                                    onClick = { showPassword.value = !showPassword.value },
                                    modifier = Modifier.size(32.dp).padding(end = 10.dp)
                                ) {
                                    Icon(
                                        tint = Color.Cyan,
                                        painter = painterResource(id = if (showPassword.value) R.drawable.hide else R.drawable.unhide),
                                        contentDescription = null
                                    )
                                }
                            },
                            visualTransformation = if (showPassword.value) VisualTransformation.None else PasswordVisualTransformation()
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            TextButton(onClick = { }) {
                                Text(text = "Need Help?", fontSize = 12.sp, color = Color(0xFFFFF085))
                            }
                            Button(
                                onClick = onLoginClick,
                                colors = ButtonDefaults.buttonColors(containerColor = cuOrange),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(text = "Login", color = Color.White, fontWeight = FontWeight.Bold)
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "Don't have an account yet? :)",
                            fontSize = 14.sp,
                            color = Color.White
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Button(
                            onClick = onSignUpClick,
                            colors = ButtonDefaults.buttonColors(containerColor = cuOrange),
                            shape = RoundedCornerShape(12.dp),
                            modifier =
                                Modifier.fillMaxWidth().padding(horizontal = 40.dp, vertical = 4.dp)

                        ) {
                            Text(
                                text = "Sign Up",
                                color = Color.White,
                                fontSize = 18.sp,
                                letterSpacing = 1.4.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
