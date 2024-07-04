package com.devstudio.forexFusion

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import com.devstudio.forexFusion.ui.screens.DrawerScreen
import com.devstudio.forexFusion.ui.component.CustomBottomBar
import com.devstudio.forexFusion.ui.component.CustomTopAppBar
import com.devstudio.forexFusion.ui.navigation.NavigationGraph
import com.devstudio.forexFusion.ui.theme.TradingSignalsAppTheme
import com.devstudio.forexFusion.ui.theme.app_font
import com.devstudio.forexFusion.ui.theme.darkCardBackground
import com.devstudio.forexFusion.ui.theme.lightCardBackground
import com.onesignal.OneSignal
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        /*installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.splashScreenIsLoading.value
            }
        }*/

        setContent {

            val context = LocalContext.current
            var hasNotificationPermission by remember { mutableStateOf(false) }

            val requestPermissionLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                hasNotificationPermission = isGranted
            }

            LaunchedEffect(Unit) {
                hasNotificationPermission = ContextCompat.checkSelfPermission(
                    context, android.Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            }

            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            val scope = rememberCoroutineScope()
            val navController = rememberNavController()

            BackHandler {
                when {
                    drawerState.isOpen -> {
                        scope.launch {
                            drawerState.close()
                        }
                    }
                    else -> {
                        finish()
                    }
                }
            }







            TradingSignalsAppTheme(darkTheme = viewModel.theme.value) {

                ModalNavigationDrawer(
                    modifier = Modifier,
                    drawerContent = {
                        DrawerScreen(
                            viewModel = viewModel,
                            navController = navController,
                            drawerState = drawerState
                        )
                    },
                    drawerState = drawerState,
                    content = {

                        Scaffold(
                            modifier = Modifier
                                .fillMaxSize()
                                .statusBarsPadding()
                                .navigationBarsPadding(),
                            topBar = {
                                CustomTopAppBar(
                                    viewModel = viewModel,
                                    drawerState = drawerState,
                                    navController = navController
                                )
                            },
                            bottomBar = {
                                CustomBottomBar(
                                    viewModel = viewModel,
                                    navController = navController
                                )
                            }
                        ) { paddingValues ->


                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(paddingValues)
                            ) {


                                NavigationGraph(
                                    navController = navController,
                                    viewModel = viewModel
                                )

                                if (!hasNotificationPermission){
                                    requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                                }

                                if (viewModel.snackBarVisible.value) {
                                    SnackBar(
                                        modifier = Modifier.align(Alignment.TopCenter),
                                        viewModel = viewModel
                                    )
                                }

                            }

                        }
                    }
                )

            }
        }

       /* val workRequest = PeriodicWorkRequestBuilder<FirebaseWorker>(1, TimeUnit.MINUTES).build()
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "FirebaseWorker",
            androidx.work.ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )*/
    }

    @Deprecated("This method has been deprecated in favor of using the Activity Result API\n      which brings increased type safety via an {@link ActivityResultContract} and the prebuilt\n      contracts for common intents available in\n      {@link androidx.activity.result.contract.ActivityResultContracts}, provides hooks for\n      testing, and allow receiving results in separate, testable classes independent from your\n      activity. Use\n      {@link #registerForActivityResult(ActivityResultContract, ActivityResultCallback)} passing\n      in a {@link RequestMultiplePermissions} object for the {@link ActivityResultContract} and\n      handling the result in the {@link ActivityResultCallback#onActivityResult(Object) callback}.")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 101) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, initialize OneSignal again if needed
                OneSignal.initWithContext(this, AppClass.ONESIGNAL_APP_ID)
            } else {
                // Permission denied, handle accordingly
                Log.e("OneSignal", "Notification permission denied.")

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 101)
                    }
                }

            }
        }
    }

}

@Composable
fun SnackBar(modifier: Modifier, viewModel: MainViewModel) {
    viewModel.snackBarMessage.value?.let {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .height(60.dp)
                .padding(start = 8.dp, end = 8.dp, top = 8.dp),
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (viewModel.theme.value) darkCardBackground else lightCardBackground
            )
        ) {

            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {

                VerticalDivider(
                    modifier = Modifier
                        .fillMaxHeight(),
                    thickness = 4.dp,
                    color = it.color
                )

                Icon(
                    modifier = Modifier.padding(start = 4.dp, end = 8.dp),
                    imageVector = it.icon,
                    contentDescription = null,
                    tint = it.color
                )

                Text(
                    modifier = Modifier,
                    text = it.text,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = MaterialTheme.typography.labelSmall.fontSize,
                    fontWeight = FontWeight.Bold,
                    fontFamily = app_font
                )
            }
        }
    }
    LaunchedEffect(viewModel.snackBarVisible.value) {
        delay(6000L)
        viewModel.snackBarDisable()
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TradingSignalsAppTheme {

    }
}