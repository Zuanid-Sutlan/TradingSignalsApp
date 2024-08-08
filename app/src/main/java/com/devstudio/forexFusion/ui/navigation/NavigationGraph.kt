package com.devstudio.forexFusion.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.devstudio.forexFusion.MainViewModel
import com.devstudio.forexFusion.ui.screens.CryptoSignalScreen
import com.devstudio.forexFusion.ui.screens.EventSignalScreen
import com.devstudio.forexFusion.ui.screens.ForexSignalScreen
import com.devstudio.forexFusion.ui.screens.HomeScreen
import com.devstudio.forexFusion.ui.screens.ResultsScreen
import com.devstudio.forexFusion.ui.screens.SplashScreen
import com.devstudio.forexFusion.ui.screens.WelcomeScreen
import kotlinx.serialization.Serializable


@Serializable
object SplashScreen

@Serializable
object WelcomeScreen

@Serializable
object HomeScreen

@Serializable
object CryptoScreen

@Serializable
object ForexScreen

@Serializable
object EventsScreen

@Serializable
object ResultScreen


@Composable
fun NavigationGraph(
    navController: NavHostController,
    viewModel: MainViewModel
) {



    NavHost(navController, startDestination = SplashScreen) {

        composable<SplashScreen> {
            viewModel.apply {
                setAppBarTitle("")
                drawerGestureState(false)
            }
            SplashScreen(navController = navController, viewModel = viewModel)
        }

        composable<WelcomeScreen> {
            viewModel.apply {
                setAppBarTitle("")
                drawerGestureState(false)
            }
            WelcomeScreen(navController = navController, viewModel = viewModel)
        }

        composable<HomeScreen> {
            viewModel.apply {
                setAppBarTitle("Forex Fusion")
                drawerGestureState(true)
            }
            HomeScreen(viewModel = viewModel, navController = navController)
        }

        composable<CryptoScreen> {
            viewModel.apply {
                setAppBarTitle("Crypto Signals")
                drawerGestureState(false)
            }
            CryptoSignalScreen(viewModel = viewModel)
        }

        composable<ForexScreen> {
            viewModel.apply {
                setAppBarTitle("Forex Signals")
                drawerGestureState(false)
            }
            ForexSignalScreen(viewModel = viewModel)
        }

        composable<EventsScreen>{
            viewModel.apply {
                setAppBarTitle("Events")
                drawerGestureState(false)
            }
            EventSignalScreen(viewModel = viewModel)
        }

        composable<ResultScreen>{
            viewModel.apply {
                setAppBarTitle("Results")
                drawerGestureState(false)
            }
            ResultsScreen(viewModel = viewModel)
        }

    }

}