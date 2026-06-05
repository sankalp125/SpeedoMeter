package com.example.ui.speedometer

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.math.round

class DashboardViewModel : ViewModel() {

    // Center state representation (selected value 0.0 to 100.0)
    private val _targetSpeed = MutableStateFlow(0f)
    val targetSpeed: StateFlow<Float> = _targetSpeed.asStateFlow()

    // Default initializer
    init {
        _targetSpeed.value = 60f
    }

    // Direct interface update from the user slider controller
    fun setTargetSpeed(value: Float) {
        _targetSpeed.value = round(value).coerceIn(0f, 100f)
    }
}
