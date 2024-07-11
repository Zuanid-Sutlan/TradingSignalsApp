package com.devstudio.forexFusion.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AttachMoney
import androidx.compose.material.icons.outlined.CurrencyBitcoin
import androidx.compose.material.icons.outlined.EventAvailable
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.devstudio.forexFusion.MainViewModel
import com.devstudio.forexFusion.ui.navigation.CryptoScreen
import com.devstudio.forexFusion.ui.navigation.EventsScreen
import com.devstudio.forexFusion.ui.navigation.ForexScreen
import com.devstudio.forexFusion.ui.navigation.HomeScreen
import com.devstudio.forexFusion.ui.theme.app_font
import com.devstudio.forexFusion.ui.theme.blueDark


data class BottomBarItem(
    val label: String,
    val icon: ImageVector,
    val selectedTitle: String,
)

val bottomBarItems = listOf(
    BottomBarItem("Home", Icons.Outlined.Home, "Forex Fusion"),
    BottomBarItem("Crypto", Icons.Outlined.CurrencyBitcoin, "Crypto Signals"),
    BottomBarItem("Forex", Icons.Outlined.AttachMoney, "Forex Signals"),
    BottomBarItem("Events", Icons.Outlined.EventAvailable, "Events")
)

@Composable
fun CustomBottomBar(viewModel: MainViewModel, navController: NavHostController) {

    if (viewModel.appBarTitle.value != "") {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(blueDark, RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                .clipToBounds()
                .graphicsLayer {
                    shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                    clip = true
                },
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            bottomBarItems.forEach {
                val selected = viewModel.appBarTitle.value == it.selectedTitle
                Column(
                    modifier = Modifier
                        .padding(top = 14.dp, bottom = 14.dp)
                        .weight(1f)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            when (it.label) {
                                "Home" -> navController.navigate(HomeScreen)
                                "Crypto" -> navController.navigate(CryptoScreen)
                                "Forex" -> navController.navigate(ForexScreen)
                                "Events" -> navController.navigate(EventsScreen)
                                else -> navController.navigate(HomeScreen)
                            }
                        },
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(imageVector = it.icon, contentDescription = it.label, tint = Color.White)
                    if (selected) {
                        Text(
                            text = it.label,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White,
                            fontFamily = app_font
                        )
                    }
                }
            }
        }
    }
}


