package com.futsalgg.app.presentation.auth.termsandcondition

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.futsalgg.app.R
import com.futsalgg.app.ui.components.Checkbox
import com.futsalgg.app.ui.components.SingleButton
import com.futsalgg.app.ui.theme.FutsalggColor
import com.futsalgg.app.ui.theme.FutsalggDimensions
import com.futsalgg.app.ui.theme.FutsalggTypography

@Composable
fun TermsAndConditionScreen() {
    var allAgree by remember { mutableStateOf(false) }
    var termsAgree by remember { mutableStateOf(false) }
    var privacyAgree by remember { mutableStateOf(false) }

    // TODO 화면이 아직 finalized 되지 않았음. Finalized 되면 다시 찾아올것.

    // 버튼 활성화: 필수 항목 두 개 모두 체크되어야 함.
    val isSubmitEnabled = termsAgree && privacyAgree

    Content(
        isSubmitEnabled = isSubmitEnabled,
        allAgree = allAgree,
        allAgreeClick = {
            allAgree = it
            termsAgree = it
            privacyAgree = it
        },
        termsAgree = termsAgree,
        termsClick = {
            termsAgree = it
            if (privacyAgree && termsAgree) allAgree = true else allAgree = false
        },
        privacyAgree = privacyAgree,
        privacyClick = {
            privacyAgree = it
            if (privacyAgree && termsAgree) allAgree = true else allAgree = false
        },
    )
}

@Composable
fun Content(
    isSubmitEnabled: Boolean,
    allAgree: Boolean,
    allAgreeClick: (Boolean) -> Unit,
    termsAgree: Boolean,
    termsClick: (Boolean) -> Unit,
    privacyAgree: Boolean,
    privacyClick: (Boolean) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(FutsalggColor.white)
            .padding(top = 40.dp)
            .padding(horizontal = FutsalggDimensions.horizontalPadding)
    ) {
        // 위 텍스트 컬럼
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
        ) {
            // 첫 텍스트
            Text(
                text = stringResource(R.string.terms_and_condition_title),
                style = FutsalggTypography.bold_24_400
            )

            Spacer(Modifier.height(16.dp))

            // 두번째 텍스트
            Text(
                text = stringResource(R.string.terms_and_condition_contents),
                style = FutsalggTypography.regular_17_200
            )
        }

        // 아래 컬럼
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            // 전체 동의
            Row(
                modifier = Modifier
                    .clickable { allAgreeClick(!allAgree) }
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = allAgree,
                    onCheckedChange = { allAgreeClick(it) },
                    modifier = Modifier.padding(16.dp)
                )
                Text(
                    text = stringResource(R.string.agree_all),
                    style = FutsalggTypography.bold_17_200
                )
            }

            HorizontalDivider(thickness = 1.dp, color = FutsalggColor.mono200)

            Spacer(Modifier.height(16.dp))

            // 이용약관 동의
            CheckBoxAndTextWithBorder(
                text = R.string.agree_terms,
                check = termsAgree,
                checkClick = termsClick,
                {
                    // TODO 이용약관 클릭
                }
            )

            Spacer(Modifier.height(8.dp))

            // 개인정보 동의
            CheckBoxAndTextWithBorder(
                text = R.string.agree_privacy,
                check = privacyAgree,
                checkClick = privacyClick,
                {
                    // TODO 개인정보 클릭
                }
            )

            Spacer(Modifier.height(54.dp))

            SingleButton(
                text = "Text",
                onClick = {},
                modifier = Modifier.padding(bottom = 16.dp),
                containerColor = if (isSubmitEnabled) FutsalggColor.mono900 else FutsalggColor.mono100,
                contentColor = if (isSubmitEnabled) FutsalggColor.white else FutsalggColor.mono500
            )
        }
    }
}

@Composable
fun CheckBoxAndTextWithBorder(
    @StringRes text: Int,
    check: Boolean,
    checkClick: (Boolean) -> Unit,
    textClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .clickable { checkClick(!check) }
            .border(
                width = 1.dp,
                color = FutsalggColor.mono900,
                shape = RoundedCornerShape(8.dp)
            )
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = check,
            onCheckedChange = { checkClick(it) },
            modifier = Modifier.padding(16.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { textClick() },
            contentAlignment = Alignment.CenterStart
        ){
            Text(
                text = stringResource(text),
                style = FutsalggTypography.regular_17_200
            )
            Image(
                painter = painterResource(R.drawable.ic_arrow_forward_16),
                contentDescription = "",
                modifier = Modifier.padding(16.dp)
                    .align(Alignment.CenterEnd)
            )
        }
    }
}

@Preview
@Composable
fun PreviewTermsAndCondition() {
    Content(
        isSubmitEnabled = false,
        allAgree = true,
        allAgreeClick = {},
        termsAgree = false,
        termsClick = { },
        privacyAgree = true,
        privacyClick = {}
    )
}