package com.futsalgg.app.presentation.main

import androidx.lifecycle.ViewModel
import com.futsalgg.app.domain.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    // TODO Main View Model
}