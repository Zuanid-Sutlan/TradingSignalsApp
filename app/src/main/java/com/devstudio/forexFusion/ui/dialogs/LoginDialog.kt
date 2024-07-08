package com.devstudio.forexFusion.ui.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material.icons.rounded.CheckCircleOutline
import androidx.compose.material.icons.rounded.ErrorOutline
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import com.devstudio.forexFusion.MainViewModel
import com.devstudio.forexFusion.data.model.MessageBar
import com.devstudio.forexFusion.ui.component.CustomButton
import com.devstudio.forexFusion.ui.navigation.HomeScreen
import com.devstudio.forexFusion.ui.navigation.WelcomeScreen
import com.devstudio.forexFusion.ui.theme.app_font
import com.devstudio.forexFusion.ui.theme.blueLight
import com.devstudio.forexFusion.ui.theme.greenLight
import com.devstudio.forexFusion.ui.theme.redLight
import com.devstudio.forexFusion.ui.utils.Prefs
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginDialog(viewModel: MainViewModel, navController: NavHostController) {

    var loginButtonEnabled by remember { mutableStateOf(true) }
    val darkTheme by remember { mutableStateOf(Prefs.isDarkTheme) }
    val backgroundColor =
        MaterialTheme.colorScheme.background //if (darkTheme) Color.Black else Color.White
    val textColor =
        MaterialTheme.colorScheme.onBackground //if (darkTheme) Color.White else blueLight

    val headingColor = if (darkTheme) Color.White else blueLight

    BasicAlertDialog(
        modifier = Modifier
            .fillMaxWidth()
//            .padding(16.dp)
            .wrapContentHeight()
            .background(backgroundColor, RoundedCornerShape(16.dp)),
        onDismissRequest = { viewModel.showLoginDialog(false) },
        properties = DialogProperties(
            usePlatformDefaultWidth = true,
            dismissOnBackPress = true,
            dismissOnClickOutside = false
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Log In",
                fontSize = 28.sp,
                color = headingColor,
                fontWeight = FontWeight.ExtraBold
            )
            Spacer(modifier = Modifier.height(16.dp))

//            var name by remember { mutableStateOf("") }
            var email by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }
//            var confirmPassword by remember { mutableStateOf("") }

            var passwordVisible by remember { mutableStateOf(false) }
//            var confirmPasswordVisible by remember { mutableStateOf(false) }

            val passwordIcon =
                if (passwordVisible) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility
//            val confirmPasswordIcon =
//                if (confirmPasswordVisible) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility

            /*OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name", color = textColor) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = textColor,
                    unfocusedTextColor = textColor,
                    focusedBorderColor = textColor,
                    unfocusedBorderColor = textColor,
                    cursorColor = textColor
                )
            )
            Spacer(modifier = Modifier.height(8.dp))*/

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email", color = textColor) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = textColor,
                    unfocusedTextColor = textColor,
                    focusedBorderColor = textColor,
                    unfocusedBorderColor = textColor,
                    cursorColor = textColor
                )
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password", color = textColor) },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(passwordIcon, contentDescription = "Password Toggle", tint = textColor)
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = textColor,
                    unfocusedTextColor = textColor,
                    focusedBorderColor = textColor,
                    unfocusedBorderColor = textColor,
                    cursorColor = textColor
                )
            )
            Spacer(modifier = Modifier.height(4.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                TextButton(
                    onClick = {
                        if (email.isNotEmpty()){
                            FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                                .addOnSuccessListener {
                                    viewModel.setUpSnackBar(
                                        true,
                                        MessageBar(
                                            "Password Reset Email Sent",
                                            Icons.Rounded.CheckCircleOutline,
                                            greenLight
                                        )
                                    )
                                }
                                .addOnFailureListener {
                                    viewModel.setUpSnackBar(
                                        true,
                                        MessageBar(
                                            it.message.toString(),
                                            Icons.Rounded.ErrorOutline,
                                            redLight
                                        )
                                    )
                                }
                        }else{
                            viewModel.setUpSnackBar(
                                true,
                                MessageBar(
                                    "Enter Email",
                                    Icons.Rounded.ErrorOutline,
                                    redLight
                                )
                            )
                        }
                    }
                ) {
                    Text(
                        text = "Forgot Password?",
                        textAlign = TextAlign.End,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = MaterialTheme.typography.labelMedium.fontSize,
                        fontWeight = FontWeight.Bold,
                        fontFamily = app_font
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            CustomButton(
                text = "Log In",
                withProgress = true,
                enabled = loginButtonEnabled,
                textColor = MaterialTheme.colorScheme.onBackground,
                backgroundColor = MaterialTheme.colorScheme.background,
                borderColor = MaterialTheme.colorScheme.onBackground,
                onClick = {

                    /** LOGIN USER **/
                    loginButtonEnabled = false // disabling the button when clicked

                    if (email.isNotEmpty() && password.isNotEmpty()) {
                        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                            .addOnSuccessListener {
                                viewModel.setUpSnackBar(
                                    true,
                                    MessageBar(
                                        "Login Successful",
                                        Icons.Rounded.CheckCircleOutline,
                                        greenLight
                                    )
                                )
                                navController.navigate(HomeScreen) {
                                    popUpTo(WelcomeScreen) {
                                        inclusive = true
                                    }
                                }
                                viewModel.showLoginDialog(false)
                            }
                            .addOnFailureListener {
                                viewModel.setUpSnackBar(
                                    true,
                                    MessageBar(
                                        it.message.toString(),
                                        Icons.Rounded.ErrorOutline,
                                        redLight
                                    )
                                )
                            }
                    }else{
                        viewModel.setUpSnackBar(
                            true,
                            MessageBar(
                                "Enter Email and Password",
                                Icons.Rounded.ErrorOutline,
                                redLight
                            )
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(0.dp))

            TextButton(onClick = { viewModel.showLoginDialog(false) }) {
                Text(
                    text = "Go Back",
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = MaterialTheme.typography.labelLarge.fontSize,
                    fontWeight = FontWeight.Bold,
                    fontFamily = app_font
                )
            }
        }
    }
}