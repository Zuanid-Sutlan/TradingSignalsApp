package com.devstudio.forexFusion.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CustomSwitch(
    isChecked: Boolean,
    width: Dp,
    height: Dp,
    cornerRadius: Dp,
    borderColor: Color,
    checkedTrackColor: Color,
    uncheckedTrackColor: Color,
    checkedIcon: Color, // Icons.Filled,
    uncheckedIcon: Color, // Icons.Filled,
    onCheckedChange: (Boolean) -> Unit
) {

    val painter = if (isChecked) checkedIcon else uncheckedIcon

    Box(
        modifier = Modifier
            .size(width = width, height = height)
            .border(
                border = BorderStroke(1.dp, borderColor),
                shape = RoundedCornerShape(cornerRadius)
            )
            .background(
                color = if (isChecked) uncheckedTrackColor else checkedTrackColor,
                shape = RoundedCornerShape(25.dp)
            )
            .clip(RoundedCornerShape(25.dp))
            .clickable {
                val changed = !isChecked
                onCheckedChange(changed)
            },

        ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = if (isChecked) Arrangement.Start else Arrangement.End
        ) {

            Box(modifier = Modifier
                .size(16.dp)
                .clip(RoundedCornerShape(25.dp))
                .background(
                    painter
                ))

            /*Icon(
                painter = painterResource(id = painter),
                contentDescription = null,
                tint = Color.Unspecified
            )*/
        }
    }
}
