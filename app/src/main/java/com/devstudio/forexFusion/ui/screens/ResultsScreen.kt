package com.devstudio.forexFusion.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.devstudio.forexFusion.MainViewModel
import com.devstudio.forexFusion.data.model.ResultSignal
import com.devstudio.forexFusion.ui.component.CircularProgressMeter
import com.devstudio.forexFusion.ui.component.ResultsItemView
import com.devstudio.forexFusion.ui.component.TradingWarningMessage
import com.devstudio.forexFusion.ui.theme.greenDark
import com.devstudio.forexFusion.ui.theme.greenLight
import com.devstudio.forexFusion.ui.theme.redDark
import com.devstudio.forexFusion.ui.theme.redLight
import kotlinx.coroutines.delay

@Composable
fun ResultsScreen(viewModel: MainViewModel) {

    val winTradesList = remember { mutableListOf<ResultSignal>() }

    val winRate = winTradesList.size * 100 / viewModel.results.value.size
    var progress by remember { mutableIntStateOf(0) }

    val color = when (progress) {
        in 0..25 -> {
            if (progress == 0) MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f) else redLight
        }

        in 26..50 -> {
            redDark
        }

        in 51..75 -> {
            greenDark
        }

        in 76..100 -> {
            greenLight
        }

        else -> greenLight
    }

    val animateColor = animateColorAsState(
        targetValue = color,
        animationSpec = tween(durationMillis = 2000),
        label = ""
    )

    LaunchedEffect(winRate) {
        progress = 0
        for (i in 0..winRate){
            progress = i
        }
    }

    LaunchedEffect(viewModel.results.value.size) {
        winTradesList.clear()
        for (item in viewModel.results.value){
            if (item.profit.contains("+") || item.profit.contains("Win")){
                winTradesList.add(item)
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TradingWarningMessage(text = "Al-Hamdulillah !! Win Rate : $winRate%")




        LazyVerticalGrid(modifier = Modifier.padding(4.dp), columns = GridCells.Fixed(2)) {


            item(span = { GridItemSpan(maxCurrentLineSpan) }) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
//                .fillMaxHeight(0.3f)
                        .wrapContentHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressMeter(
//                canvasSize = 150.dp,
                        indicatorValue = if (viewModel.results.value.isEmpty()) 0 else progress,
                        maxIndicatorValue = 100,
                        foregroundIndicatorColor = animateColor.value,
//                backgroundIndicatorStrokeWidth = 10f,
//                foregroundIndicatorStrokeWidth = 12f,
//                bigTextFontSize = MaterialTheme.typography.labelLarge.fontSize,
//                smallTextFontSize = MaterialTheme.typography.headlineSmall.fontSize,
                        bigTextColor = MaterialTheme.colorScheme.onBackground,
                        smallTextColor = animateColor.value,
                        smallTextFontSize = MaterialTheme.typography.bodyLarge.fontSize
                    )
                }
            }

            items(viewModel.results.value.reversed()) { item ->
                /*if (item.profit.contains("+") || item.profit.contains("Win")){
                    winTradesList.add(item)
                }*/
                ResultsItemView(results = item)
            }
        }

    }


}