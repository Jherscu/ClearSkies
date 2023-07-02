package com.jHerscu.clearskies.utils

sealed class UiEvent { // TODO(jherscu): move into individual vm's
    data class ShowSnackbar(val text: TextWrapper) : UiEvent()
}
