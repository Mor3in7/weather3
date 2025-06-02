package androidlead.weatherappui.ui.screen.signup

import android.util.Patterns
import android.widget.Toast
import androidlead.weatherappui.R
import androidlead.weatherappui.ui.theme.cuLightBlue
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay

@Preview(showBackground = true)
@Composable
fun SignUp(onButtonClicked3: () -> Unit = {}) {
    val username = remember { mutableStateOf("") }
    val emailInput = remember { mutableStateOf("") } // برای debounce
    val password = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }
    val isPasswordVisible = remember { mutableStateOf(false) }
    val isConfirmPasswordVisible = remember { mutableStateOf(false) }
    val context = LocalContext.current
    val usernameError = remember { mutableStateOf("") }
    val passwordError = remember { mutableStateOf("") }
    val confirmPasswordError = remember { mutableStateOf("") }
    val emailError = remember { mutableStateOf("") } // خطای ایمیل

    // DEBOUNCE برای ایمیل
    LaunchedEffect(emailInput.value) {
        delay(1500) // 1.5 ثانیه تأخیر
        if (emailInput.value.isNotEmpty() && !Patterns.EMAIL_ADDRESS.matcher(emailInput.value).matches()) {
            emailError.value = "Example: example@gmail.com"
        } else {
            emailError.value = ""
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Background image
        Image(
            painter = painterResource(id = R.drawable.night),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
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
                            onValueChange = {if (it.length <= 25)
                                username.value = it
                                usernameError.value = if (it.length > 25) "Max character limit reached" else ""

                            },
                            label = "Username",
                            iconRes = R.drawable.username,

                            isError = usernameError.value.isNotEmpty(),
                            supportingText = usernameError.value,

                        )
                    }

                    item {
                        CustomOutlinedField(
                            value = emailInput.value,
                            onValueChange = {
                                emailInput.value = it
                            },
                            label = "Email Address",
                            iconRes = R.drawable.email,
                            isError = emailError.value.isNotEmpty(),
                            supportingText = emailError.value
                        )
                    }

                    item {
                        CustomOutlinedField(
                            value = password.value,
                            onValueChange = {if(it.length<=25)
                                password.value = it
                                passwordError.value = if (it.length > 25) "Max character limit reached" else if (it.length < 8) "Password must  be between 8-25 characters" else ""
                                // تطبیق مجدد با confirmPassword
                            },
                            label = "Password",
                            icon = Icons.Default.Lock,
                            isPassword = true,
                            visible = isPasswordVisible,
                            isError = passwordError.value.isNotEmpty(),
                            supportingText = passwordError.value
                        )
                    }

                    item {
                        CustomOutlinedField(
                            value = confirmPassword.value,
                            onValueChange = {if(it.length<=25)
                                confirmPassword.value = it
                                confirmPasswordError.value = if (it != password.value) "Passwords do not match" else ""
                            },
                            label = "Confirm Password",
                            icon = Icons.Default.Lock,
                            isPassword = true,
                            visible = isConfirmPasswordVisible,
                            isError = confirmPasswordError.value.isNotEmpty(),
                            supportingText = confirmPasswordError.value
                        )
                    }

                    item {
                        Button(
                            onClick = {
                                if (usernameError.value.isNullOrBlank() || password.value.isNullOrBlank() || emailInput.value.isNullOrBlank() || confirmPassword.value.isNullOrBlank()) {
                                    Toast.makeText(
                                        context,
                                        "Plz Fill Your data",
                                        Toast.LENGTH_LONG
                                    ).show()
                                } else {
                                    onButtonClicked3()
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = cuLightBlue)
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
    supportingText: String = ""
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        isError = isError,
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
                // 1) Always show error icon when there's an error
                if (isError) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_error_outline_24),
                        contentDescription = "Error",
                        tint = Color.Red,
                        modifier = Modifier.size(20.dp)
                    )
                }
                // 2) If this is a password field, show the eye toggle
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
