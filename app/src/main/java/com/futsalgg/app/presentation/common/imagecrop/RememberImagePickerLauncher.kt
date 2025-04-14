package com.futsalgg.app.presentation.common.imagecrop

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.core.content.ContextCompat
import androidx.navigation.NavController

@Composable
fun rememberImagePickerLauncher(
    navController: NavController,
    viewModelType: String,
    context: Context
):() -> Unit {
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            uri?.let {
                navController.navigate(
                    "cropImage?uri=${Uri.encode(it.toString())}&viewModelType=$viewModelType"
                )
            }
        }
    )

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                imagePickerLauncher.launch("image/*")
            } else {
                Toast.makeText(context, "앨범 접근 권한이 필요합니다.", Toast.LENGTH_SHORT).show()
            }
        }
    )

    val launchGalleryWithPermission = remember {
        {
            val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                Manifest.permission.READ_MEDIA_IMAGES
            } else {
                Manifest.permission.READ_EXTERNAL_STORAGE
            }

            if (ContextCompat.checkSelfPermission(
                    context,
                    permission
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                imagePickerLauncher.launch("image/*")
            } else {
                permissionLauncher.launch(permission)
            }
        }
    }

    return launchGalleryWithPermission
}