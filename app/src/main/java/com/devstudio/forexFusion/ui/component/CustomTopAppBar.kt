package com.devstudio.forexFusion.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.devstudio.forexFusion.MainViewModel
import com.devstudio.forexFusion.ui.theme.app_font
import com.devstudio.forexFusion.ui.theme.blueLight
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(
    viewModel: MainViewModel,
    drawerState: DrawerState,
    navController: NavHostController
) {
    val scope = rememberCoroutineScope()

    // it is only in case of splashScreen and WelcomeScreen
    if (viewModel.appBarTitle.value != ""){
        TopAppBar(
            modifier = Modifier
                .background(
                    Color.Transparent,
                    if (viewModel.appBarTitle.value == "Forex Fusion") RoundedCornerShape(0.dp) else RoundedCornerShape(
                        bottomStart = 20.dp,
                        bottomEnd = 20.dp
                    )
                )
                .graphicsLayer {
                    shape =
                        if (viewModel.appBarTitle.value == "Forex Fusion") RoundedCornerShape(0.dp) else RoundedCornerShape(
                            bottomStart = 20.dp,
                            bottomEnd = 20.dp
                        )
                    clip = true
                }
            /*.background(
            Brush.linearGradient(
                colors = listOf(
                    blueDark,
                    blueLight,
                    blueLight
                )
            )
        )*/,
            title = {
                Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Center) {
                    Text(
                        text = viewModel.appBarTitle.value,
                        color = Color.White,
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        fontFamily = app_font
                    )
                }
            },
            navigationIcon = {

                if (viewModel.appBarTitle.value == "Forex Fusion") {
                    IconButton(
                        onClick = {
                            scope.launch {
                                drawerState.open()
                            }
                        }
                    ) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = null)
                    }
                } else {
                    IconButton(
                        onClick = { navController.navigateUp() }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBackIosNew,
                            contentDescription = "go back"
                        )
                    }
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = blueLight,
                titleContentColor = Color.White,
                navigationIconContentColor = Color.White
            )
        )
    }
}