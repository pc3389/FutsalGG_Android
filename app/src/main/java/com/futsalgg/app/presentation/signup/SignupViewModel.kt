package com.futsalgg.app.presentation.signup

import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class SignupViewModel @Inject constructor() : ViewModel() {
    var croppedProfileImage by mutableStateOf<Bitmap?>(null)
        private set

    fun setCroppedImage(bitmap: Bitmap) {
        croppedProfileImage = bitmap
    }
}