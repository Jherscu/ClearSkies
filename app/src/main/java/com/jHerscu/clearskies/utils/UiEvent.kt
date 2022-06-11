package com.jHerscu.clearskies.utils

sealed class UiEvent {
    data class ShowSnackbar(val text: TextWrapper) : UiEvent()
}