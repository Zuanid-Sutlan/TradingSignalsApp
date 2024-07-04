package com.devstudio.forexFusion.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.devstudio.forexFusion.data.model.ResultSignal
import com.devstudio.forexFusion.ui.theme.app_font
import com.devstudio.forexFusion.ui.theme.greenGradient
import com.devstudio.forexFusion.ui.theme.redGradient

@Composable
fun ResultsItemView(results: ResultSignal) {

    Box(
        modifier = Modifier
            .width(162.dp)
            .height(114.dp)
            .padding(start = 6.dp, end = 6.dp, bottom = 12.dp)
            .background(
                if (results.profit.contains("+") || results.profit.contains("Win")) greenGradient else redGradient,
                RoundedCornerShape(10.dp)
            )
    ) {

        Text(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(26.dp),
            text = results.pairName,
            color = Color.White,
            fontWeight = FontWeight.ExtraBold,
            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
            fontFamily = app_font
        )

        Text(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp),
            text = results.date,
            color = Color.White,
            fontSize = MaterialTheme.typography.labelSmall.fontSize,
            fontFamily = app_font
        )

        Text(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(top = 24.dp),
            text = results.profit,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
            fontFamily = app_font
        )

    }
}