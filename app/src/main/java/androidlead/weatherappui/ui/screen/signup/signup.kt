package androidlead.weatherappui.ui.screen.signup

import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidlead.weatherappui.R
import androidlead.weatherappui.ui.screen.auth.AuthViewModel
import androidlead.weatherappui.ui.theme.cuLightBlue
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.delay
@Preview(showBackground = true)
@Composable
fun SignUp(
    onSignUpSuccess: () -> Unit = {}
) {
    val vm: AuthViewModel = hiltViewModel()
    val ui = vm.ui.collectAsState().value

    val username = remember { mutableStateOf("") }
    val emailInput = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }

    val isPasswordVisible = remember { mutableStateOf(false) }
    val isConfirmPasswordVisible = remember { mutableStateOf(false) }

    val usernameError = remember { mutableStateOf("") }
    val passwordError = remember { mutableStateOf("") }
    val confirmPasswordError = remember { mutableStateOf("") }
    val emailError = remember { mutableStateOf("") }

    LaunchedEffect(emailInput.value) {
        delay(500)
        emailError.value =
            if (emailInput.value.isNotEmpty() && !Patterns.EMAIL_ADDRESS.matcher(emailInput.value).matches())
                "Example: example@gmail.com"
            else ""
    }

    fun validate(): Boolean {
        var ok = true

        usernameError.value = when {
            username.value.isBlank() -> { ok = false; "Username is required" }
            username.value.length > 25 -> { ok = false; "Max character limit is 25" }
            else -> ""
        }

        emailError.value = when {
            emailInput.value.isBlank() -> { ok = false; "Email is required" }
            !Patterns.EMAIL_ADDRESS.matcher(emailInput.value).matches() -> { ok = false; "Invalid email" }
            else -> ""
        }

        passwordError.value = when {
            password.value.isBlank() -> { ok = false; "Password is required" }
            password.value.length < 8 -> { ok = false; "Password must be between 8–25 characters" }
            password.value.length > 25 -> { ok = false; "Password must be between 8–25 characters" }
            else -> ""
        }

        confirmPasswordError.value = when {
            confirmPassword.value.isBlank() -> { ok = false; "Confirm your password" }
            confirmPassword.value != password.value -> { ok = false; "Passwords do not match" }
            else -> ""
        }

        return ok
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.night),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Box(Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 24.dp),
                contentAlignment = Alignment.Center
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 450.dp, max = 650.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color.White.copy(alpha = 0.12f))
                        .imePadding()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    item {
                        Text(
                            text = "CREATE YOUR\nACCOUNT",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            color = Color.Yellow,
                        )
                    }

                    item {
                        CustomOutlinedField(
                            value = username.value,
                            onValueChange = {
                                if (it.length <= 25) {
                                    username.value = it
                                    usernameError.value = if (it.isBlank()) "Username is required" else ""
                                }
                            },
                            label = "Username",
                            iconRes = R.drawable.username,
                            isError = usernameError.value.isNotEmpty(),
                            supportingText = usernameError.value,
                            enabled = !ui.loading
                        )
                    }

                    item {
                        CustomOutlinedField(
                            value = emailInput.value,
                            onValueChange = { emailInput.value = it },
                            label = "Email Address",
                            iconRes = R.drawable.email,
                            isError = emailError.value.isNotEmpty(),
                            supportingText = emailError.value,
                            enabled = !ui.loading
                        )
                    }

                    item {
                        CustomOutlinedField(
                            value = password.value,
                            onValueChange = {
                                if (it.length <= 25) {
                                    password.value = it
                                    passwordError.value = when {
                                        it.isBlank() -> "Password is required"
                                        it.length < 8 -> "Password must be between 8–25 characters"
                                        else -> ""
                                    }
                                }
                            },
                            label = "Password",
                            icon = Icons.Default.Lock,
                            isPassword = true,
                            visible = isPasswordVisible,
                            isError = passwordError.value.isNotEmpty(),
                            supportingText = passwordError.value,
                            enabled = !ui.loading
                        )
                    }

                    item {
                        CustomOutlinedField(
                            value = confirmPassword.value,
                            onValueChange = {
                                if (it.length <= 25) {
                                    confirmPassword.value = it
                                    confirmPasswordError.value =
                                        if (it != password.value) "Passwords do not match" else ""
                                }
                            },
                            label = "Confirm Password",
                            icon = Icons.Default.Lock,
                            isPassword = true,
                            visible = isConfirmPasswordVisible,
                            isError = confirmPasswordError.value.isNotEmpty(),
                            supportingText = confirmPasswordError.value,
                            enabled = !ui.loading
                        )
                    }

                    item {
                        Button(
                            onClick = {
                                if (!validate()) return@Button
                                vm.signUp(
                                    username = username.value.trim(),
                                    email = emailInput.value.trim(),
                                    password = password.value
                                ) {
                                    onSignUpSuccess()
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = cuLightBlue),
                            enabled = !ui.loading
                        ) {
                            if (ui.loading) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(22.dp),
                                    strokeWidth = 2.dp,
                                    color = Color.White
                                )
                            } else {
                                Text(
                                    text = "Sign Up",
                                    color = Color.White,
                                    fontSize = 18.sp,
                                    letterSpacing = 1.4.sp
                                )
                            }
                        }
                    }

                    item {
                        ui.error?.let {
                            Text(
                                text = it,
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CustomOutlinedField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    iconRes: Int? = null,
    icon: ImageVector? = null,
    isPassword: Boolean = false,
    visible: MutableState<Boolean>? = null,
    isError: Boolean = false,
    supportingText: String = "",
    enabled: Boolean = true
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        isError = isError,
        enabled = enabled,
        label = { Text(label, color = Color.White) },
        leadingIcon = {
            when {
                iconRes != null -> Icon(
                    painter = painterResource(id = iconRes),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(22.dp)
                )
                icon != null -> Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(22.dp)
                )
            }
        },
        trailingIcon = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (isError) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_error_outline_24),
                        contentDescription = "Error",
                        tint = Color.Red,
                        modifier = Modifier.size(20.dp)
                    )
                }
                if (isPassword && visible != null) {
                    IconButton(onClick = { visible.value = !visible.value }) {
                        Icon(
                            painter = painterResource(
                                id = if (visible.value) R.drawable.hide else R.drawable.unhide
                            ),
                            contentDescription = if (visible.value) "Hide password" else "Show password",
                            tint = Color.Cyan,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
            }
        },
        visualTransformation = if (isPassword && visible?.value == false)
            PasswordVisualTransformation() else VisualTransformation.None,
        supportingText = {
            if (isError) {
                Text(
                    text = supportingText,
                    color = Color.Red,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor    = Color.LightGray,
            focusedBorderColor      = cuLightBlue,
            errorBorderColor        = Color.Red,
            cursorColor             = Color.White,
            focusedTextColor        = Color.White,
            unfocusedTextColor      = Color.White,
            focusedLabelColor       = Color.Yellow,
            unfocusedLabelColor     = Color.White,
            errorLabelColor         = Color.Red,
            errorTrailingIconColor  = Color.Red,
            focusedContainerColor   = Color.Transparent,
            unfocusedContainerColor = Color.Transparent
        ),
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        singleLine = true
    )
}
