package com.devstudio.forexFusion.ui.component

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.WarningAmber
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.devstudio.forexFusion.MainViewModel
import com.devstudio.forexFusion.data.model.BrokerLink
import com.devstudio.forexFusion.data.model.MessageBar
import com.devstudio.forexFusion.ui.theme.app_font
import com.devstudio.forexFusion.ui.theme.darkCardBackground
import com.devstudio.forexFusion.ui.theme.lightCardBackground
import com.devstudio.forexFusion.ui.utils.Prefs

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BrokerLinkItemView(
    item: BrokerLink,
    viewModel: MainViewModel
) {

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