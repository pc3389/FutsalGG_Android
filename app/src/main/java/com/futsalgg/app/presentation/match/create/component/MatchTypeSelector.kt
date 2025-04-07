package com.futsalgg.app.presentation.match.create.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.futsalgg.app.domain.team.model.MatchType
import com.futsalgg.app.ui.theme.FutsalggTypography

@Composable
fun MatchTypeSelector(
    selectedType: MatchType,
    onTypeSelected: (MatchType) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selectedType == MatchType.INTER_TEAM,
            onClick = { onTypeSelected(MatchType.INTER_TEAM) }
        )
        Text(
            text = "팀전",
            style = FutsalggTypography.regular_17_200
        )
        
        Spacer(modifier = Modifier.width(16.dp))
        
        RadioButton(
            selected = selectedType == MatchType.INTRA_SQUAD,
            onClick = { onTypeSelected(MatchType.INTRA_SQUAD) }
        )
        Text(
            text = "팀내전",
            style = FutsalggTypography.regular_17_200
        )
    }
} 