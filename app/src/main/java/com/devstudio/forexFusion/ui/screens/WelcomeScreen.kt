package com.devstudio.forexFusion.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircleOutline
import androidx.compose.material.icons.rounded.ErrorOutline
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.devstudio.forexFusion.MainViewModel
import com.devstudio.forexFusion.R
import com.devstudio.forexFusion.data.model.MessageBar
import com.devstudio.forexFusion.ui.component.CustomButton
import com.devstudio.forexFusion.ui.component.GoogleButton
import com.devstudio.forexFusion.ui.dialogs.LoginDialog
import com.devstudio.forexFusion.ui.dialogs.RegisterDialog
import com.devstudio.forexFusion.ui.navigation.HomeScreen
import com.devstudio.forexFusion.ui.navigation.WelcomeScreen
import com.devstudio.forexFusion.ui.theme.app_font
import com.devstudio.forexFusion.ui.theme.blueLight
import com.devstudio.forexFusion.ui.theme.greenLight
import com.devstudio.forexFusion.ui.theme.primaryGradient
import com.devstudio.forexFusion.ui.theme.redLight
import com.devstudio.forexFusion.ui.utils.Prefs
import com.devstudio.forexFusion.ui.utils.Utils
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException

@Composable
fun WelcomeScreen(viewModel: MainViewModel, navController: NavHostController) {

    val context = LocalContext.current

    val googleSignInLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)!!
            viewModel.firebaseAuthWithGoogle(account.idToken!!) { success, message ->
                viewModel.setUpSnackBar(
                    true,
                    MessageBar(
                        message,
                        if (success) Icons.Rounded.CheckCircleOutline else Icons.Rounded.ErrorOutline,
                        if (success) greenLight else redLight
                    )
                )

                navController.navigate(HomeScreen) {
                    popUpTo(WelcomeScreen) {
                        inclusive = true
                    }
                }
            }
        } catch (e: ApiException) {
            viewModel.setUpSnackBar(
                true,
                MessageBar(
                    e.message.toString(),
                    Icons.Rounded.ErrorOutline,
                    redLight
                )
            )
            Toast.makeText(context, "Google sign in failed: ${e.message}", Toast.LENGTH_LONG).show()
            Log.i("TAG", "WelcomeScreen: ${e.message}")
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(primaryGradient)
    ) {


        // designing the background
        Image(
            modifier = Modifier
                .align(Alignment.TopStart)
                //                .size(width = 220.dp, height = 160.dp)
                .rotate(180f),
            painter = painterResource(id = R.drawable.top_right_welcome),
            contentDescription = null
        )

        /*Canvas(modifier = Modifier
            .size(160.dp)
            .align(Alignment.TopStart)) {
            drawCircle(color = Color.White, radius = 1f)
        }*/

        Image(
            modifier = Modifier.align(Alignment.BottomEnd),
            painter = painterResource(id = R.drawable.top_right_welcome),
            contentDescription = null
        )


        // welcome text
        /*Text(
            modifier = Modifier.offset(30.dp, 50.dp),
            text = "Welcome",
            color = blueLight,
            fontSize = MaterialTheme.typography.headlineLarge.fontSize,
            fontWeight = FontWeight.Bold,
            fontFamily = app_font
        )*/

        // buttons for signup and login and contact
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .align(Alignment.Center),
//            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // logo
            Image(
                modifier = Modifier
                    .size(220.dp)
                    .wrapContentSize(),
                painter = painterResource(id = R.drawable.ic_app_logo),
                contentDescription = null
            )

//            Spacer(modifier = Modifier.height(16.dp))

            Text(
                modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                text = "Welcome in Fusion Army",
                fontFamily = app_font,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomButton(
                text = "REGISTER",
                textColor = MaterialTheme.colorScheme.onBackground, //if (Prefs.isDarkTheme) Color.White else blueLight,
                backgroundColor = MaterialTheme.colorScheme.background, //if (Prefs.isDarkTheme) MaterialTheme.colorScheme.background else Color.White,
                onClick = {
                    viewModel.showRegisterDialog(true)
                }
            )

            CustomButton(
                text = "Log In",
                textColor = MaterialTheme.colorScheme.onBackground,//if (Prefs.isDarkTheme) Color.White else blueLight,
                backgroundColor = if (Prefs.isDarkTheme) MaterialTheme.colorScheme.background else Color.White,
                onClick = {
                    viewModel.showLoginDialog(true)
                }
            )

            Text(
                modifier = Modifier.padding(bottom = 6.dp),
                text = "OR",
                color = Color.White,
                fontSize = MaterialTheme.typography.labelSmall.fontSize,
                fontWeight = FontWeight.Bold,
                fontFamily = app_font
            )

            GoogleButton(
                text = "Continue with Google",
                loadingText = "Signing in...",
                progressIndicatorColor = blueLight,
                textColor = MaterialTheme.colorScheme.onBackground,
                backgroundColor = if (Prefs.isDarkTheme) MaterialTheme.colorScheme.background else Color.White,
            ) {
                val signInIntent = viewModel.getGoogleSignInIntent()
                googleSignInLauncher.launch(signInIntent)
            }

            Spacer(modifier = Modifier.height(18.dp))

            TextButton(
                onClick = {
                    Utils.openWhatsAppDeveloper(context)
                }
            ) {
                Text(
                    text = "Contact Us",
                    color = Color.White,
                    fontSize = MaterialTheme.typography.labelLarge.fontSize,
                    fontWeight = FontWeight.Bold,
                    fontFamily = app_font
                )
            }

        }

        if (viewModel.showLoginDialog.value) {
            LoginDialog(viewModel = viewModel, navController = navController)
        }

        if (viewModel.showRegisterDialog.value) {
            RegisterDialog(
                viewModel = viewModel,
                navController = navController
            )
        }

    }

}

