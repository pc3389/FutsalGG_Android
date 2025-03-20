package com.example.futsalgg_android.ui.login

import androidx.lifecycle.ViewModel
import com.example.futsalgg_android.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository
) : ViewModel() {

    // TODO Login View Model
}