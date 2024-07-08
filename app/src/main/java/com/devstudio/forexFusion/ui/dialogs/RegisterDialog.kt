package com.devstudio.forexFusion.ui.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Error
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.ErrorOutline
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.*
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
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
import com.onesignal.OneSignal


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterDialog(
    viewModel: MainViewModel,
    navController: NavController,
) {

    var registerButtonEnabled by remember { mutableStateOf(true) }
    val focusManager = LocalFocusManager.current
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
        onDismissRequest = { viewModel.showRegisterDialog(false) },
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
                "Register",
                fontSize = 28.sp,
                color = headingColor,
                fontWeight = FontWeight.ExtraBold
            )
            Spacer(modifier = Modifier.height(16.dp))

            var name by remember { mutableStateOf("") }
            var email by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }
            var confirmPassword by remember { mutableStateOf("") }

            var passwordVisible by remember { mutableStateOf(false) }
            var confirmPasswordVisible by remember { mutableStateOf(false) }

            val passwordIcon =
                if (passwordVisible) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility
            val confirmPasswordIcon =
                if (confirmPasswordVisible) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility

            OutlinedTextField(
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
            Spacer(modifier = Modifier.height(8.dp))

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
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirm Password", color = textColor) },
                visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                trailingIcon = {
                    IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                        Icon(
                            confirmPasswordIcon,
                            contentDescription = "Confirm Password Toggle",
                            tint = textColor
                        )
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
            Spacer(modifier = Modifier.height(16.dp))

            CustomButton(
                text = "Register",
                withProgress = true,
                enabled = registerButtonEnabled,
                textColor = MaterialTheme.colorScheme.onBackground,
                backgroundColor = MaterialTheme.colorScheme.background,
                borderColor = MaterialTheme.colorScheme.onBackground,
                onClick = {

                    focusManager.clearFocus() // hiding the keyboard
                    registerButtonEnabled = false // disabling the button when clicked

                    /** REGISTER USER **/
                    if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                        if (password == confirmPassword) {
                            /*viewModel.registerUser(email = email, password = password)
                            if (viewModel.authState.value == Result.Success(true)) {
                                viewModel.setUpSnackBar(
                                    true,
                                    MessageBar(
                                        "Registered Successfully",
                                        Icons.Rounded.CheckCircleOutline,
                                        blueLight
                                    )
                                )
                                navController.navigate(HomeScreen) {
                                    popUpTo(WelcomeScreen) {
                                        inclusive = true
                                    }
                                }
                            } else {
                                viewModel.setUpSnackBar(
                                    true,
                                    MessageBar(
                                        "Registration Failed !!",
                                        Icons.Rounded.ErrorOutline,
                                        redLight
                                    )
                                )
                            }*/

                            FirebaseAuth.getInstance()
                                .createUserWithEmailAndPassword(email, password)
                                .addOnSuccessListener {
                                    viewModel.setUpSnackBar(
                                        true,
                                        MessageBar(
                                            "Registered Successfully",
                                            Icons.Rounded.CheckCircle,
                                            greenLight
                                        )
                                    )
                                    FirebaseAuth.getInstance().currentUser?.uid?.let {
                                        OneSignal.login(it)
                                    }
                                    navController.navigate(HomeScreen) {
                                        popUpTo(WelcomeScreen) {
                                            inclusive = true
                                        }
                                    }

                                    viewModel.showRegisterDialog(false)
                                }
                                .addOnFailureListener {
                                    viewModel.setUpSnackBar(
                                        true,
                                        MessageBar(
                                            it.message.toString(),
                                            Icons.Outlined.Error,
                                            redLight
                                        )
                                    )
                                }
                        } else {
                            viewModel.setUpSnackBar(
                                true,
                                MessageBar(
                                    "Password and Confirm Password do not match",
                                    Icons.Rounded.ErrorOutline,
                                    redLight
                                )
                            )
                        }
                    } else {
                        viewModel.setUpSnackBar(
                            true,
                            MessageBar(
                                "Please fill all the fields",
                                Icons.Rounded.ErrorOutline,
                                redLight
                            )
                        )
                    }

                }
            )

            Spacer(modifier = Modifier.height(0.dp))

            TextButton(
                onClick = {
                    viewModel.showRegisterDialog(false)
                }
            ) {
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

    /* Surface(
         shape = RoundedCornerShape(16.dp),
         color = backgroundColor,
         contentColor = textColor,
         modifier = modifier
             .padding(16.dp)
             .fillMaxWidth()
             .wrapContentHeight()
 //            .border(2.dp, textColor, RoundedCornerShape(16.dp))
     ) {
         Column(
             modifier = Modifier.padding(16.dp),
             horizontalAlignment = Alignment.CenterHorizontally
         ) {
             Text(
                 "Register",
                 fontSize = 28.sp,
                 color = headingColor,
                 fontWeight = FontWeight.ExtraBold
             )
             Spacer(modifier = Modifier.height(16.dp))

             var name by remember { mutableStateOf("") }
             var email by remember { mutableStateOf("") }
             var password by remember { mutableStateOf("") }
             var confirmPassword by remember { mutableStateOf("") }

             var passwordVisible by remember { mutableStateOf(true) }
             var confirmPasswordVisible by remember { mutableStateOf(true) }

             val passwordIcon =
                 if (passwordVisible) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility
             val confirmPasswordIcon =
                 if (confirmPasswordVisible) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility

             OutlinedTextField(
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
             Spacer(modifier = Modifier.height(8.dp))

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
             Spacer(modifier = Modifier.height(8.dp))

             OutlinedTextField(
                 value = confirmPassword,
                 onValueChange = { confirmPassword = it },
                 label = { Text("Confirm Password", color = textColor) },
                 visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                 modifier = Modifier.fillMaxWidth(),
                 keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                 singleLine = true,
                 trailingIcon = {
                     IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                         Icon(
                             confirmPasswordIcon,
                             contentDescription = "Confirm Password Toggle",
                             tint = textColor
                         )
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
             Spacer(modifier = Modifier.height(16.dp))

             Button(
                 onClick = { *//*todo*//* },
//                colors = ButtonDefaults.buttonColors(backgroundColor = textColor, contentColor = backgroundColor),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Register")
            }
        }
    }*/
}