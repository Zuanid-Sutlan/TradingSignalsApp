package com.devstudio.forexFusion.ui.screens

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.WifiOff
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.devstudio.forexFusion.MainViewModel
import com.devstudio.forexFusion.R
import com.devstudio.forexFusion.data.model.MessageBar
import com.devstudio.forexFusion.ui.navigation.HomeScreen
import com.devstudio.forexFusion.ui.navigation.SplashScreen
import com.devstudio.forexFusion.ui.navigation.WelcomeScreen
import com.devstudio.forexFusion.ui.theme.app_font
import com.devstudio.forexFusion.ui.theme.primaryGradient
import com.devstudio.forexFusion.ui.theme.redLight
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: MainViewModel,/*changeScreen: (Boolean) -> Unit*/
) {

    val internetConnection by viewModel.isConnected.observeAsState(initial = false)
    val scope = rememberCoroutineScope()
    val alpha = remember { Animatable(0f) }

    val internetUnavailableState = remember { mutableStateOf(true) }
    val internetAvailableState = remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        alpha.animateTo(1f, tween(1000))
        delay(2000)

    }

    LaunchedEffect(internetConnection) {

        delay(2000)

        if (internetConnection) {
            if (internetAvailableState.value) {
                if (FirebaseAuth.getInstance().currentUser == null) {
                    navController.navigate(WelcomeScreen) {
                        popUpTo(SplashScreen) {
                            inclusive = true
                        }
                    }
                } else {
                    navController.navigate(HomeScreen) {
                        popUpTo(SplashScreen) {
                            inclusive = true
                        }
                    }
                }
                internetAvailableState.value = false
            }

        } else {
            if (internetUnavailableState.value) {
                viewModel.setUpSnackBar(
                    state = true,
                    message = MessageBar(
                        text = "No Internet Connection",
                        icon = Icons.Rounded.WifiOff,
                        color = redLight
                    )
                )
                internetUnavailableState.value = false
            }
        }

    }



    Log.i("SplashScreen: ", "state = $internetConnection")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(primaryGradient)
            .navigationBarsPadding()
            .statusBarsPadding()
            .alpha(alpha.value)
    ) {


        Image(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(bottom = 30.dp),
            painter = painterResource(id = R.drawable.ic_app),
            contentDescription = null
        )
        /*Text(
            text = "Forex Fusion",
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = MaterialTheme.typography.headlineMedium.fontSize,
            fontWeight = FontWeight.Bold,
            fontFamily = app_font
        )*/

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 10.dp)
                .navigationBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Powered By",
                color = Color.White,
                fontSize = MaterialTheme.typography.labelSmall.fontSize,
                fontWeight = FontWeight.Light,
                fontFamily = app_font
            )
            Text(
                text = "DevStudio",
                color = Color.White,
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                fontWeight = FontWeight.Bold,
                fontFamily = app_font
            )
        }
    }
}