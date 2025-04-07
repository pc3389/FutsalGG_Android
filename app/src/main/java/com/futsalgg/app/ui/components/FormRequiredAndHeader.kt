package com.futsalgg.app.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.futsalgg.app.R
import com.futsalgg.app.ui.theme.FutsalggColor
import com.futsalgg.app.ui.theme.FutsalggTypography

@Composable
fun FormRequiredAndHeader(
    headerText: String
) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.required),
            style = FutsalggTypography.bold_17_200,
            color = FutsalggColor.mint500,
            modifier = Modifier
                .padding(top = 16.dp)
                .align(Alignment.TopEnd)
        )
        TextWithStar(
            text = headerText,
            modifier = Modifier.padding(top = 40.dp)
        )
    }
}