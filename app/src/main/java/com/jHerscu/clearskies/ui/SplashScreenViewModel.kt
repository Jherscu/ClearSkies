package com.jHerscu.clearskies.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val SPLASH_DURATION = 1500L

class SplashScreenViewModel : ViewModel() {
    private val _isVisible = MutableStateFlow(true)
    val isVisible = _isVisible.asStateFlow()

    init {
        viewModelScope.launch {
            delay(SPLASH_DURATION)
            _isVisible.value = false
        }
    }
}
