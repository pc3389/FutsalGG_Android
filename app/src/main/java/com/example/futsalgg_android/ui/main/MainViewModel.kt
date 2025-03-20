package com.example.futsalgg_android.ui.main

import androidx.lifecycle.ViewModel
import com.example.futsalgg_android.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    // TODO Main View Model
}