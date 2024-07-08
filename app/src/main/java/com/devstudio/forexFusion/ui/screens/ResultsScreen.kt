package com.devstudio.forexFusion.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devstudio.forexFusion.MainViewModel
import com.devstudio.forexFusion.ui.component.ResultsItemView
import com.devstudio.forexFusion.ui.component.TradingWarningMessage

@Composable
fun ResultsScreen(viewModel: MainViewModel) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TradingWarningMessage(text = "Al-Hamdulillah !!")

        LazyVerticalGrid(modifier = Modifier.padding(4.dp), columns = GridCells.Fixed(2)) {
            items(viewModel.results.value.reversed()) { item ->
                ResultsItemView(results = item)
            }
        }

    }


}