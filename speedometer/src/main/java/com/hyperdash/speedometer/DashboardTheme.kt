package com.hyperdash.speedometer

import androidx.compose.ui.graphics.Color

enum class DashboardThemeType {
    LIGHT_MINIMAL
}

data class DashboardTheme(
    val name: String,
    val type: DashboardThemeType,
    val backgroundColor: Color,
    val cardBackgroundColor: Color,
    val zone1Color: Color, // 0-20%
    val zone2Color: Color, // 20-40%
    val zone3Color: Color, // 40-60%
    val zone4Color: Color, // 60-80%
    val zone5Color: Color, // 80-100%
    val needleColor: Color,
    val textPrimaryColor: Color,
    val textSecondaryColor: Color,
    val ambientGlowColor: Color,
    val trackBgColor: Color
) {
    companion object {
        val MinimalLight = DashboardTheme(
            name = "Minimal Light",
            type = DashboardThemeType.LIGHT_MINIMAL,
            backgroundColor = Color(0xFFF8FAFC),
            cardBackgroundColor = Color(0xFFFFFFFF),
            zone1Color = Color(0xFFE5524A), // Red
            zone2Color = Color(0xFFF1A54C), // Orange
            zone3Color = Color(0xFFFFD217), // Yellow
            zone4Color = Color(0xFF5AC2E0), // Light Blue
            zone5Color = Color(0xFF299F44), // Green
            needleColor = Color(0xFF1F2937),
            textPrimaryColor = Color(0xFF0F172A),
            textSecondaryColor = Color(0xFF64748B),
            ambientGlowColor = Color(0x10EF4444),
            trackBgColor = Color(0xFFE2E8F0)
        )
    }
}
