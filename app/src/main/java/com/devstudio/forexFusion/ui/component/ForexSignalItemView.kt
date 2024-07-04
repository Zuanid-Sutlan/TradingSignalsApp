package com.devstudio.forexFusion.ui.component

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.devstudio.forexFusion.data.model.ForexSignal
import com.devstudio.forexFusion.ui.theme.blueDark
import com.devstudio.forexFusion.ui.theme.greenLight
import com.devstudio.forexFusion.ui.theme.redLight
import java.util.Locale

@Composable
fun ForexSignalItemView(
    item: ForexSignal,
    padding: PaddingValues = PaddingValues(8.dp),
) {

    var expandedState by remember { mutableStateOf(false) }

    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 180f else 0f, label = ""
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp)
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            ),
        onClick = {
            expandedState = !expandedState
        },
        shape = RoundedCornerShape(10.dp),
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
                        url = item.imageUrl,
                    )

                    Text(
                        modifier = Modifier.padding(start = 16.dp),
                        text = item.pairName.uppercase(Locale.ROOT),
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
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
                )

                Text(
                    modifier = Modifier,
                    text = item.entryPrice,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                )

                Text(
                    modifier = Modifier,
                    text = item.type,
                    color = if (item.type == "Long" || item.type == "long" || item.type == "LONG"
                        || item.type == "Long " || item.type == "long " || item.type == "LONG "
                    )
                        greenLight else redLight,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
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
                    .padding(start = 16.dp, end = 16.dp)
                    .wrapContentHeight()
                    .padding(top = 0.dp, bottom = 16.dp)
            ) {

                // Take Profit 1
                TakeProfitItemView(
                    target = "Take Profit 1",
                    price = item.takeProfit1,
                    pips = item.tp1Pips,
                    status = item.takeProfit1Status,
                )

                // Take Profit 2
                if (item.takeProfit2.isNotEmpty()) {
                    TakeProfitItemView(
                        target = "Take Profit 2",
                        price = item.takeProfit2,
                        pips = item.tp2Pips,
                        status = item.takeProfit2Status,
                    )
                }

                // Take Profit 3
                if (item.takeProfit3.isNotEmpty()) {
                    TakeProfitItemView(
                        target = "Take Profit 3",
                        price = item.takeProfit3,
                        pips = item.tp3Pips,
                        status = item.takeProfit3Status,
                    )
                }

                // stop lose
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = "Stop lose    ${item.stopLose}",
                    color = redLight,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                )

                Spacer(modifier = Modifier.height(4.dp))

            }

        }
    }
}

@Composable
private fun TakeProfitItemView(
    target: String,
    price: String,
    pips: String,
    status: Int
) {
    val color = if (status == 1) greenLight else MaterialTheme.colorScheme.onBackground

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
    ) {
        Row(
            modifier = Modifier
//                .padding(2.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = target,
                color = color,
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
            )
            Text(
                text = price,
                color = color,
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
            )
            Text(
                text = "$pips pips",
                color = color,
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
            )
        }

        Spacer(modifier = Modifier.height(4.dp))
    }


}