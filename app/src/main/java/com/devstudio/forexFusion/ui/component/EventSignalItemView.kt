package com.devstudio.forexFusion.ui.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.devstudio.forexFusion.data.model.EventSignal
import com.devstudio.forexFusion.ui.theme.app_font
import com.devstudio.forexFusion.ui.theme.greenLight
import com.devstudio.forexFusion.ui.theme.redLight
import java.util.Locale

@Composable
fun EventSignalItemView(item: EventSignal, padding: Dp = 16.dp) {

    var expandedState by remember { mutableStateOf(false) }

    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 180f else 0f, label = ""
    )

    val accuracyColor = animateColorAsState(
        targetValue = if (item.accuracy.toInt() > 40) greenLight else redLight,
        label = "",
        animationSpec = tween(
            durationMillis = 1000,
            easing = LinearOutSlowInEasing
        )
    )


    Card(
        modifier = Modifier
            .fillMaxWidth()
//            .height(116.dp)
            .wrapContentHeight()
            .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp)
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            ),
        shape = RoundedCornerShape(10.dp),
        onClick = {
            expandedState = !expandedState
        }
    ) {
        Column(
            modifier = Modifier
                .padding(padding)
        ) {

            // pair name, icon and trade date
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ImageLoadFromUrl(
                        modifier = Modifier,
                        /*.border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.onBackground,
                            shape = RoundedCornerShape(10.dp)
                        )*/
                        url = item.imageUrl,
                    )

                    Text(
                        modifier = Modifier.padding(start = 16.dp),
                        text = item.pairName.uppercase(Locale.ROOT),
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        fontFamily = app_font
                    )
                }

                // date
                Text(
                    modifier = Modifier
//                            .align(Alignment.TopEnd)
                        .padding(4.dp),
                    text = item.date,
                    color = redLight,
                    fontSize = MaterialTheme.typography.labelSmall.fontSize,
                    fontFamily = app_font
                )
            }


            // entry time and trade direction up / down
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    modifier = Modifier,
                    text = "Entry Time",
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    fontFamily = app_font
                )

                Text(
                    modifier = Modifier,
                    text = item.entryTime,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    fontFamily = app_font
                )

                Text(
                    modifier = Modifier,
                    text = item.direction,
                    color = if (item.direction == "Up" || item.direction == "UP" || item.direction == "up") greenLight else redLight,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    fontFamily = app_font
                )

                // the arrow icon for switch the state expandable and un-expandable state
                Icon(
                    modifier = Modifier
                        .rotate(rotationState)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() },
                            onClick = { expandedState = !expandedState }),
                    imageVector = Icons.Outlined.KeyboardArrowDown,
                    contentDescription = "Drop-Down Arrow"
                )
            }
        }

        if (expandedState) {
            // expand view and data
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, end = 12.dp)
                    .wrapContentHeight()
                    .padding(top = 0.dp, bottom = 16.dp)
            ) {

                // capital and platform
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Capital   ${item.capital}%",
                        color = redLight,
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        fontFamily = app_font
                    )

                    Text(
                        text = item.platform,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        fontFamily = app_font
                    )

                }

                // accuracy and time frame
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 28.dp, end = 28.dp, top = 8.dp, bottom = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Accuracy",
                        color = accuracyColor.value,
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        fontFamily = app_font
                    )

                    Text(
                        text = "${item.accuracy}%",
                        color = accuracyColor.value,
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        fontFamily = app_font
                    )
                }

                // time frame or take profit for different scenario
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 28.dp, end = 28.dp, top = 0.dp, bottom = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = if (item.takeProfit.isNotEmpty()) "Take Profit" else "Time Frame",
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        fontFamily = app_font
                    )

                    Text(
                        text = if (item.takeProfit.isNotEmpty()) item.takeProfit else "${item.timeFrame} min",
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        fontFamily = app_font
                    )
                }

                // mtg reversal in 2 min

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 28.dp, end = 28.dp, top = 0.dp, bottom = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = if (item.takeProfit.isEmpty()) if (item.mtgTime.isEmpty()) "-- --" else "MTG Reversal " else "Stop Loss",
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        fontFamily = app_font
                    )

                    Text(
                        text = if (item.takeProfit.isEmpty()) if (item.mtgTime.isEmpty()) "-- --" else "${item.timeFrame} min" else "",
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        fontFamily = app_font
                    )

                    Text(
                        text = if (item.takeProfit.isEmpty()) if (item.mtgTime.isEmpty()) "-- --" else item.mtgNeed else item.stopLoss,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        fontFamily = app_font
                    )
                }

            }
        }
    }
}