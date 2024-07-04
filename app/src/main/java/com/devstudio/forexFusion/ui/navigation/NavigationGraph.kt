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
            viewModel.setAppBarTitle("")
            SplashScreen(navController = navController, viewModel = viewModel)
        }

        composable<WelcomeScreen> {
            viewModel.setAppBarTitle("")
            WelcomeScreen(navController = navController, viewModel = viewModel)
        }

        composable<HomeScreen> {
            viewModel.setAppBarTitle("Forex Fusion")
            HomeScreen(viewModel = viewModel, navController = navController)
        }

        composable<CryptoScreen> {
            viewModel.setAppBarTitle("Crypto Signals")
            CryptoSignalScreen(viewModel = viewModel)
        }

        composable<ForexScreen> {
            viewModel.setAppBarTitle("Forex Signals")
            ForexSignalScreen(viewModel = viewModel)
        }

        composable<EventsScreen>{
            viewModel.setAppBarTitle("Events")
            EventSignalScreen(viewModel = viewModel)
        }

        composable<ResultScreen>{
            viewModel.setAppBarTitle("Results")
            ResultsScreen(viewModel = viewModel)
        }

    }

}