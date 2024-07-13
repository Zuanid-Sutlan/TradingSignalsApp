package com.devstudio.forexFusion.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.devstudio.forexFusion.MainViewModel
import com.devstudio.forexFusion.ui.component.EventSignalItemView
import com.devstudio.forexFusion.ui.component.TradingWarningMessage

@Composable
fun EventSignalScreen(viewModel: MainViewModel) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TradingWarningMessage()

        LazyColumn(
            modifier = Modifier.weight(1f)
        ){
            items(viewModel.eventSignals.value.reversed()){
                EventSignalItemView(item = it)
            }
        }
    }

}