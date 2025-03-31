package com.futsalgg.app.presentation.signup

import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.futsalgg.app.domain.model.Gender
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class SignupViewModel @Inject constructor() : ViewModel() {

    private val _nickname = MutableStateFlow("")
    val nickname: StateFlow<String> = _nickname.asStateFlow()

    val isNicknameValid: StateFlow<Boolean> = nickname.map { it.length >= 2 }
        .stateIn(viewModelScope, SharingStarted.Eagerly, false)

    private val _birthday = MutableStateFlow("")
    val birthday: StateFlow<String> = _birthday.asStateFlow()

    private val _showCalendarSheet = MutableStateFlow(false)
    val showCalendarSheet: StateFlow<Boolean> = _showCalendarSheet.asStateFlow()

    private val _gender = MutableStateFlow(Gender.MALE)
    val gender: StateFlow<Gender> = _gender.asStateFlow()

    private val _croppedProfileImage = MutableStateFlow<Bitmap?>(null)
    val croppedProfileImage: StateFlow<Bitmap?> = _croppedProfileImage.asStateFlow()

    private val _notificationChecked = MutableStateFlow(false)
    val notificationChecked: StateFlow<Boolean> = _notificationChecked.asStateFlow()

    internal fun onNicknameChange(newValue: String) {
        _nickname.value = newValue
    }

    internal fun onBirthdayChange(value: String) {
        _birthday.value = value
    }

    internal fun onBirthdaySelect(date: LocalDate) {
        _birthday.value = date.format(DateTimeFormatter.ofPattern("yyyyMMdd"))
    }

    internal fun onCalendarClick() {
        _showCalendarSheet.value = true
    }

    internal fun onDismissCalendar() {
        _showCalendarSheet.value = false
    }

    internal fun onGenderChange(gender: Gender) {
        _gender.value = gender
    }

    internal fun setCroppedImage(bitmap: Bitmap) {
        _croppedProfileImage.value = bitmap
    }

    internal fun toggleNotification() {
        _notificationChecked.value = !_notificationChecked.value
    }
}