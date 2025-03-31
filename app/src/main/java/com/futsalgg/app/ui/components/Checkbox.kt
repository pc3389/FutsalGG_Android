package com.futsalgg.app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.futsalgg.app.R

/**
 * 커스텀 체크박스
 *
 * @param checked 체크 상태
 * @param onCheckedChange onClick, 체크를 반대로 바꿔준다
 * @param modifier modifier
 */
@Composable
fun Checkbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    // 체크 상태에 따라 보여줄 이미지 선택
    val imageRes = if (checked) {
        R.drawable.ic_checkbox_true_24  // 체크된 상태 이미지
    } else {
        R.drawable.ic_checkbox_false_24  // 체크되지 않은 상태 이미지
    }

    Image(
        painter = painterResource(id = imageRes),
        contentDescription = if (checked) "체크됨" else "체크되지 않음",
        modifier = modifier.clickable { onCheckedChange(!checked) }
    )
}