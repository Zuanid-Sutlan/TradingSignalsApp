package com.devstudio.forexFusion.ui.component

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.devstudio.forexFusion.data.model.CryptoSignal
import com.devstudio.forexFusion.ui.theme.app_font
import com.devstudio.forexFusion.ui.theme.blueLight
import com.devstudio.forexFusion.ui.theme.green
import com.devstudio.forexFusion.ui.theme.redBright
import com.devstudio.forexFusion.ui.theme.redLight
import java.util.Locale

@Composable
fun CryptoSignalItemView(
    item: CryptoSignal,
    padding: Dp = 16.dp
) {

    var expandedState by remember { mutableStateOf(false) }

    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 180f else 0f, label = ""
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
        /*Column(
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
                        */
        /*.border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.onBackground,
                            shape = RoundedCornerShape(10.dp)
                        )*/
        /*
                        url = item.imageUrl,
                    )

                    Text(
                        modifier = Modifier.padding(start = 16.dp),
                        text = item.pairName.uppercase(),
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


            // entry price and trade type long / short
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    modifier = Modifier,
                    text = "Entry Price",
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    fontFamily = app_font
                )

                Text(
                    modifier = Modifier,
                    text = item.entryPrice,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    fontFamily = app_font
                )

                Text(
                    modifier = Modifier,
                    text = item.type,
                    color = if (item.type == "Long" || item.type == "long" || item.type == "LONG") greenLight else redLight,
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
        }*/

        Column(
            modifier = Modifier
                .padding(start = padding, end = padding, top = padding)
        ) {

            // pair name, icon and trade date
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row {
                    ImageLoadFromUrl(
                        modifier = Modifier,
                        /*.border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.onBackground,
                            shape = RoundedCornerShape(10.dp)
                        )*/
                        url = item.imageUrl,
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .align(Alignment.CenterVertically)
                            .padding(start = 16.dp),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            modifier = Modifier.padding(start = 0.dp),
                            text = item.pairName.uppercase(Locale.ROOT),
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.Bold,
                            fontFamily = app_font,
                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        )
                        // date
                        Text(
                            modifier = Modifier
//                            .align(Alignment.TopEnd)
                                .padding(0.dp),
                            text = item.date,
                            fontFamily = app_font,
                            color = if (isSystemInDarkTheme()) redBright else Color.Red,
                            fontSize = MaterialTheme.typography.labelSmall.fontSize,
                        )
                    }
                }


                // the arrow icon for switch the state expandable and un-expandable state
                Icon(
                    modifier = Modifier
                        .rotate(rotationState)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() },
                            onClick = { expandedState = !expandedState }
                        ),
                    imageVector = Icons.Outlined.KeyboardArrowDown,
                    contentDescription = "Drop-Down Arrow"
                )

            }


            // entry price and trade type long / short
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, bottom = 6.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier,
                        text = "Entry: ${item.entryPrice}",
                        color = MaterialTheme.colorScheme.onBackground,
                        fontFamily = app_font,
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    )
                    Text(
                        modifier = Modifier,
                        text = item.type,
                        fontFamily = app_font,
                        color = if (item.type == "Long" || item.type == "long" || item.type == "LONG") if (isSystemInDarkTheme()) Color.Green else green else Color.Red,
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    )
                }



//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.SpaceBetween,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Text(
//                        modifier = Modifier,
//                        text = "Current: ",
//                        color = MaterialTheme.colorScheme.onBackground,
//                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
//                    )
//                    Text(
//                        modifier = Modifier,
//                        text = item.entryPrice,
//                        color = MaterialTheme.colorScheme.onBackground,
//                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
//                    )
////                    Text(
////                        modifier = Modifier,
////                        text = "${
////                            Utils.calculatePercentageDifference(
////                                value1 = if(item.direction == "Long") item.entryPrice else item.entryPrice.toDouble(),
////                                value2 = if(item.direction == "Long") item.entryPrice.toDouble() else item.price,
////                                leverage = item.leverage
////                            )
////                        }%",
////                        color = currentPercentageColor,
////                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
////                    )
//                }


            }
        }

        if (expandedState) {
            // expand view and data
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp)
                    .wrapContentHeight()
                    .padding(top = 0.dp, bottom = 16.dp)
            ) {

                // leverage and capital
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Capital ${item.capital}%",
                        color = redLight,
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        fontFamily = app_font
                    )

                    Text(
                        text = "leverage ${item.leverage}x",
                        color = redLight,
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        fontFamily = app_font
                    )

                }

                Spacer(modifier = Modifier.height(6.dp))

                // Take Profit 1
                TakeProfitItemView(
                    target = "Take Profit 1",
                    price = item.takeProfit1,
                    percent = item.percentProfit1,
                    status = item.takeProfit1Status
                )

                Spacer(modifier = Modifier.height(6.dp))

                // Take Profit 2
                if (item.takeProfit2.isNotEmpty()){
                    TakeProfitItemView(
                        target = "Take Profit 2",
                        price = item.takeProfit2,
                        percent = item.percentProfit2,
                        status = item.takeProfit2Status
                    )

                    Spacer(modifier = Modifier.height(6.dp))
                }

                // Take Profit 3
                if (item.takeProfit3.isNotEmpty()) {
                    TakeProfitItemView(
                        target = "Take Profit 3",
                        price = item.takeProfit3,
                        percent = item.percentProfit3,
                        status = item.takeProfit3Status
                    )

                    Spacer(modifier = Modifier.height(6.dp))
                }

                // stop lose
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = "Stop loss   ${item.stopLose}",
                    color = redLight,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    fontFamily = app_font
                )

            }
            /*Text(
                text = description,
                fontSize = descriptionFontSize,
                fontWeight = descriptionFontWeight,
                maxLines = descriptionMaxLines,
                overflow = TextOverflow.Ellipsis
            )*/


        }
    }
}

@Composable
private fun TakeProfitItemView(target: String, price: String, percent: String, status: Int) {
    val color =
        if (status == 1) blueLight else MaterialTheme.colorScheme.background
    val colorText = if (status == 1) Color.White else MaterialTheme.colorScheme.onBackground

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = color,
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = target,
                color = colorText,
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
            )
            Text(
                text = price,
                color = colorText,
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
            )
            Text(
                text = percent,
                color = colorText,
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
            )
            if (status == 1) {
                Icon(
                    imageVector = Icons.Rounded.CheckCircle,
                    contentDescription = "profit done",
                    tint = colorText
                )
            }
        }
    }

}
