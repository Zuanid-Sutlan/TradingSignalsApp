package com.devstudio.forexFusion.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.WarningAmber
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.devstudio.forexFusion.MainViewModel
import com.devstudio.forexFusion.R
import com.devstudio.forexFusion.data.model.CommunityLink
import com.devstudio.forexFusion.data.model.MessageBar
import com.devstudio.forexFusion.ui.theme.app_font
import com.devstudio.forexFusion.ui.theme.blueDark
import com.devstudio.forexFusion.ui.theme.blueLight
import com.devstudio.forexFusion.ui.theme.darkCardBackground
import com.devstudio.forexFusion.ui.theme.lightCardBackground
import com.devstudio.forexFusion.ui.utils.Prefs
import com.devstudio.forexFusion.ui.component.ImageLoadFromUrl
import com.devstudio.forexFusion.ui.component.ResultsItemView
import com.devstudio.forexFusion.ui.navigation.ResultScreen

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    navController: NavHostController
) {

    val activity = LocalContext.current as ComponentActivity

    BackHandler {
        activity.finish()
    }

    Column(
        modifier = modifier
    ) {

        // top view like header of the home page
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.3f)
                .background(
                    Brush.verticalGradient(
                        listOf(
                            blueLight,
                            blueDark
                        )
                    ), RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp)
                )
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 20.dp, bottom = 30.dp)
                    .align(Alignment.CenterStart)
                    .graphicsLayer {
//                        alpha = 0.8f
                    },
                text = "Maximize Your \n Trading Potential",
                color = Color.White,
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                fontFamily = app_font
            )

            val screenWidth = LocalConfiguration.current.screenWidthDp
            val width = screenWidth / 2

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .align(Alignment.CenterEnd)
                    .width(width.dp)
            ) {
                Icon(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(horizontal = 9.dp, vertical = 16.dp),
                    painter = painterResource(id = R.drawable.ic_bitcoin),
                    contentDescription = "bitcoin",
                    tint = Color.Unspecified
                )
                Icon(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
//                        .size(48.dp)
                        .padding(horizontal = 16.dp, vertical = 9.dp),
                    painter = painterResource(id = R.drawable.ic_gold_oil),
                    contentDescription = "gold",
                    tint = Color.Unspecified
                )
                Icon(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(32.dp),
                    painter = painterResource(id = R.drawable.ic_money),
                    contentDescription = "money",
                    tint = Color.Unspecified
                )
                Icon(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(35.dp),
                    painter = painterResource(id = R.drawable.ic_eth),
                    contentDescription = "ethereum",
                    tint = Color.Unspecified
                )
                Icon(
                    modifier = Modifier.align(Alignment.BottomEnd).padding(bottom = 8.dp),
                    painter = painterResource(id = R.drawable.ic_solana),
                    contentDescription = "solaria",
                    tint = Color.Unspecified
                )
            }


        }
        // results row view in the home page

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(top = 18.dp, start = 18.dp, end = 18.dp, bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier
//                            .fillMaxWidth()
                            .padding()
                            .graphicsLayer {
                                alpha = 0.8f
                            },
                        text = "Results",
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold,
                        fontSize = MaterialTheme.typography.labelLarge.fontSize,
                        fontFamily = app_font
                    )

                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .background(Color.Transparent, RoundedCornerShape(4.dp))
                            .clip(RoundedCornerShape(4.dp))
                            .clickable(
//                                    indication = null,
//                                    interactionSource = remember { MutableInteractionSource() },
                                onClick = {
                                    navController.navigate(ResultScreen)
                                }
                            )
                    ) {
                        Text(
                            modifier = Modifier
                                .graphicsLayer {
                                    alpha = 0.8f
                                },
                            text = " View All ",
                            color = blueLight,
                            fontWeight = FontWeight.Bold,
                            fontSize = MaterialTheme.typography.labelLarge.fontSize,
                            fontFamily = app_font
                        )
                    }

                }
            }


            item {
                LazyRow(Modifier.fillMaxWidth()) {
                    items(viewModel.results.value.reversed().take(7)) {
                        ResultsItemView(results = it)
                    }
                }
            }

            // community section
            item {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 0.dp, start = 18.dp, end = 18.dp, bottom = 8.dp)
                        .graphicsLayer {
                            alpha = 0.8f
                        },
                    text = "Community",
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.labelLarge.fontSize,
                    fontFamily = app_font
                )
            }

            items(viewModel.communityLinks.value) {
                CommunityItemUi(item = it, viewModel = viewModel)
            }

            item { Spacer(modifier = Modifier.height(10.dp)) }


        }

    }

}

@Composable
fun CommunityItemUi(item: CommunityLink, viewModel: MainViewModel) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(8.dp)
            .background(
                color = if (Prefs.isDarkTheme) darkCardBackground else lightCardBackground,
                shape = RoundedCornerShape(10.dp)
            )
            .graphicsLayer {
                shape = RoundedCornerShape(10.dp)
                clip = true
            }
            .clickable {
                /*when (item.platform) {
                    "whatsapp" -> {

                    }

                    "facebook" -> {

                    }

                    "x" -> {

                    }

                    "instagram" -> {

                    }

                    "telegram" -> {

                    }

                    "youtube" -> {

                    }

                    else -> {

                    }
                }*/
                if (item.link.isNotEmpty() && item.link.contains("https://")) {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(item.link)
                    context.startActivity(intent)
                } else {
                    viewModel.setUpSnackBar(
                        true,
                        MessageBar(
                            "Invalid Link",
                            Icons.Rounded.WarningAmber,
                            Color.Red
                        )
                    )
                }
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        ImageLoadFromUrl(
            url = item.imageUrl,
            modifier = Modifier
                .size(56.dp)
                .padding(16.dp)
                .graphicsLayer { shape = RoundedCornerShape(10.dp) }
                .clip(RoundedCornerShape(10.dp))
        )

        /*Image(
            modifier = Modifier
                .size(56.dp)
                .padding(16.dp)
                .graphicsLayer {
                    shape = RoundedCornerShape(10.dp)
                    clip = true
                },
            painter = painterResource(id = item.imageUrl),
            contentDescription = item.communityName
        )*/

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp, end = 20.dp),
            text = item.title,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = MaterialTheme.typography.titleMedium.fontSize,
            fontFamily = app_font
        )
    }
}