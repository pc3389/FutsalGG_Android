package com.example.futsalgg_android.ui.login

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.futsalgg_android.R

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel(),
    onClick: () -> Unit,
    context: Context
) {
    Contents(onClick = {
        onClick()
    })
}

@Composable
fun Contents(onClick: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.login_background),
            contentDescription = "",
            contentScale = ContentScale.FillHeight,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 80.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Image(
                painter = painterResource(id = R.drawable.temp_login_logo),
                contentDescription = "",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 68.dp)
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Image(
                painter = painterResource(id = R.drawable.temp_login_button),
                contentDescription = "",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onClick() }
            )
        }
    }
}

@Preview
@Composable
fun ContentPreview() {
    Contents() {}
}