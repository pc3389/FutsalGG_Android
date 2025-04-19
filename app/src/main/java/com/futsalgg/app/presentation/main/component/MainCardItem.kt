package com.futsalgg.app.presentation.main.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.futsalgg.app.ui.theme.FutsalggColor
import com.futsalgg.app.ui.theme.FutsalggTypography

@Composable
fun MainCardItem(
    text: String,
    image: ImageVector,
    color: Color,
    color2: Color = FutsalggColor.white,
    textColor: Color = FutsalggColor.mono900,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(380.dp)
            .clickable {
                onClick()
            },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(

        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 2.dp
        ),
        border = BorderStroke(
            1.dp,
            FutsalggColor.mono50
        )
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            color2,
                            color
                        )
                    )
                )
        ) {
            Spacer(modifier = Modifier.weight(1f))

            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = text,
                style = FutsalggTypography.bold_17_200,
                color = textColor
            )

            Spacer(Modifier.height(24.dp))

            Image(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                imageVector = image,
                contentDescription = ""
            )

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}