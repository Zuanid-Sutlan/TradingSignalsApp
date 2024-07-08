package com.devstudio.forexFusion.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.outlined.Feedback
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material.icons.outlined.Whatsapp
import androidx.compose.material.icons.rounded.CheckCircleOutline
import androidx.compose.material.icons.rounded.ErrorOutline
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.devstudio.forexFusion.MainViewModel
import com.devstudio.forexFusion.R
import com.devstudio.forexFusion.data.model.MessageBar
import com.devstudio.forexFusion.data.model.enums.Result
import com.devstudio.forexFusion.ui.component.CustomSwitch
import com.devstudio.forexFusion.ui.navigation.HomeScreen
import com.devstudio.forexFusion.ui.navigation.WelcomeScreen
import com.devstudio.forexFusion.ui.theme.app_font
import com.devstudio.forexFusion.ui.theme.blueLight
import com.devstudio.forexFusion.ui.theme.greenLight
import com.devstudio.forexFusion.ui.theme.redLight
import com.devstudio.forexFusion.ui.utils.Prefs
import com.devstudio.forexFusion.ui.utils.Utils
import kotlinx.coroutines.launch

@Composable
fun DrawerScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    navController: NavController,
    drawerState: DrawerState
) {

    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxHeight()
            .fillMaxWidth(0.8f)
            .background(
                MaterialTheme.colorScheme.background,
                RoundedCornerShape(10.dp)
            )
            .graphicsLayer {
                shape = RoundedCornerShape(10.dp)
                clip = true
            }
    ) {

        Image(
            modifier = modifier
                .padding(top = 58.dp)
                .size(140.dp)
                .align(Alignment.CenterHorizontally)
                .graphicsLayer {
                    shape = CircleShape
                    clip = true
                }
                .border(1.dp, color = blueLight, shape = CircleShape)
                .background(
                    if (Prefs.isDarkTheme) MaterialTheme.colorScheme.background else blueLight,
                    CircleShape
                ),
            painter = painterResource(id = R.drawable.ic_app),
            contentDescription = ""
        )

        Text(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            text = "Forex Fusion",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = if (viewModel.theme.value) Color.White else blueLight,
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            fontFamily = app_font
        )

        Spacer(modifier = modifier.height(20.dp))

        ThemeSwitchItem(itemText = "Light Theme", viewModel = viewModel)

        Spacer(modifier = modifier.height(10.dp))

//        NotificationSwitchItem(itemText = "Notifications")

        DrawerItemWithIcon(
            itemText = "Feedback",
            onClick = { Utils.sendFeedback(context) },
            icon = Icons.Outlined.Feedback
        )

        DrawerItemWithIcon(
            itemText = "Rate Us",
            onClick = { Utils.openAppInPlayStore(context) },
            icon = Icons.Outlined.ThumbUp
        )

        DrawerItemWithIcon(
            itemText = "Contact Us",
            onClick = { Utils.openWhatsApp(context) },
            icon = Icons.Outlined.Whatsapp
        )

        DrawerItemWithIcon(
            itemText = "Logout",
            onClick = {
                viewModel.signOut()
                if (viewModel.authState.value == Result.Success(true)) {
                    viewModel.setUpSnackBar(
                        true,
                        MessageBar("Success", Icons.Rounded.CheckCircleOutline, greenLight)
                    )
                    navController.navigate(WelcomeScreen) {
                        popUpTo(HomeScreen) {
                            inclusive = true
                        }
                    }
                } else {
                    viewModel.setUpSnackBar(
                        true,
                        MessageBar("Failed", Icons.Rounded.ErrorOutline, redLight)
                    )
                }

                scope.launch{ drawerState.close() }
            },
            icon = Icons.AutoMirrored.Outlined.Logout
        )


    }

}

@Composable
fun ThemeSwitchItem(
    modifier: Modifier = Modifier,
    itemText: String,
    viewModel: MainViewModel,
) {

    // for theme boolean state
    var themeChecked by remember { mutableStateOf(Prefs.isDarkTheme) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
            .background(Color.Transparent, RoundedCornerShape(10.dp))
            .graphicsLayer {
                shape = RoundedCornerShape(10.dp)
                clip = true
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = modifier,
            text = itemText,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = MaterialTheme.typography.titleLarge.fontSize,//20.sp,
            fontFamily = app_font
        )

        CustomSwitch(
            isChecked = themeChecked,
            width = 38.dp,
            height = 20.dp,
            cornerRadius = 25.dp,
            borderColor = if (Prefs.isDarkTheme) Color.White else blueLight,
            checkedTrackColor = Color.Transparent,
            uncheckedTrackColor = Color.Transparent,
            checkedIcon = if (Prefs.isDarkTheme) Color.White else blueLight,
            uncheckedIcon = if (Prefs.isDarkTheme) Color.White else blueLight
        ) {
            themeChecked = it
            Prefs.isDarkTheme = themeChecked
            viewModel.setTheme(themeChecked)
        }

    }
}

@Composable
fun NotificationSwitchItem(
    modifier: Modifier = Modifier,
    itemText: String,
) {

    // for notification boolean state
    var notificationChecked by remember { mutableStateOf(Prefs.notification) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
            .background(Color.Transparent, RoundedCornerShape(10.dp))
            .graphicsLayer {
                shape = RoundedCornerShape(10.dp)
                clip = true
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = modifier,
            text = itemText,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = MaterialTheme.typography.titleLarge.fontSize, //20.sp,
            fontFamily = app_font
        )

        CustomSwitch(
            isChecked = notificationChecked,
            width = 38.dp,
            height = 20.dp,
            cornerRadius = 25.dp,
            borderColor = if (Prefs.isDarkTheme) Color.White else blueLight,
            checkedTrackColor = Color.Transparent,
            uncheckedTrackColor = Color.Transparent,
            checkedIcon = if (Prefs.isDarkTheme) Color.White else blueLight,
            uncheckedIcon = if (Prefs.isDarkTheme) Color.White else blueLight
        ) {
            notificationChecked = it
            Prefs.notification = notificationChecked
//            MainActivity.viewModel.setTheme(themeChecked) todo
        }

    }
}


@Composable
fun DrawerItemWithIcon(
    modifier: Modifier = Modifier,
    itemText: String,
    onClick: () -> Unit,
    icon: ImageVector
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 6.dp, end = 6.dp)
            .background(Color.Transparent, RoundedCornerShape(10.dp))
            .graphicsLayer {
                shape = RoundedCornerShape(10.dp)
                clip = true
            }
            .clickable { onClick() },
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp, top = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = modifier,
                text = itemText,
                textAlign = TextAlign.Center,
//            fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                fontFamily = app_font
            )

            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (Prefs.isDarkTheme) Color.White else blueLight
            )
        }
    }

}