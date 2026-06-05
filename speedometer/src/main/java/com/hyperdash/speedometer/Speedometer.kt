package com.hyperdash.speedometer

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

/**
 * A highly elegant semicircular speedometer component.
 *
 * @param targetSpeed The current speed value to display.
 * @param minSpeed The minimum value of the speedometer range.
 * @param maxSpeed The maximum value of the speedometer range.
 * @param theme The color theme to apply to the speedometer.
 * @param style The design style of the speedometer.
 * @param modifier Modifier to apply to the component.
 */
@Composable
fun Speedometer(
    targetSpeed: Float,
    minSpeed: Float = 0f,
    maxSpeed: Float = 100f,
    theme: DashboardTheme = DashboardTheme.MinimalLight,
    style: SpeedometerStyle = SpeedometerStyle.SEMI_CIRCULAR,
    modifier: Modifier = Modifier
) {
    // State to trigger initial animation
    var startValue by remember { mutableStateOf(minSpeed) }

    LaunchedEffect(targetSpeed) {
        // Small delay to ensure the UI is rendered and visible before animation starts
        kotlinx.coroutines.delay(300)
        startValue = targetSpeed
    }

    // Smooth transition for the gauge needle
    val currentSpeedAnimated by animateFloatAsState(
        targetValue = startValue,
        animationSpec = tween(
            durationMillis = 1000,
            easing = FastOutSlowInEasing
        ),
        label = "NeedleAnimator"
    )

    val currentIntSpeed = currentSpeedAnimated.roundToInt()

    // Color theme matching for the active numeric reading matching the active zone
    val range = maxSpeed - minSpeed
    val ratio = if (range != 0f) (currentSpeedAnimated - minSpeed) / range else 0f
    
    val activeZoneColor by animateColorAsState(
        targetValue = when {
            ratio <= 0.2f -> theme.zone1Color
            ratio <= 0.4f -> theme.zone2Color
            ratio <= 0.6f -> theme.zone3Color
            ratio <= 0.8f -> theme.zone4Color
            else -> theme.zone5Color
        },
        label = "CurrentZoneColor"
    )

    val performanceText = when {
        ratio <= 0.2f -> "Very Poor"
        ratio <= 0.4f -> "Poor"
        ratio <= 0.6f -> "Average"
        ratio <= 0.8f -> "Good"
        else -> "Excellent"
    }

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .background(theme.cardBackgroundColor)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Speedometer Gauge Dial based on style
        Box(modifier = Modifier.weight(1f).fillMaxWidth()) {
            when (style) {
                SpeedometerStyle.SEMI_CIRCULAR -> SemiCircularSpeedometer(currentSpeedAnimated, minSpeed, maxSpeed, theme)
                SpeedometerStyle.CIRCULAR -> CircularSpeedometer(currentSpeedAnimated, minSpeed, maxSpeed, theme)
                SpeedometerStyle.MODERN_ARC -> ModernArcSpeedometer(currentSpeedAnimated, minSpeed, maxSpeed, theme)
                SpeedometerStyle.SPORTY -> SportySpeedometer(currentSpeedAnimated, minSpeed, maxSpeed, theme)
                SpeedometerStyle.LINEAR_VERTICAL -> LinearVerticalSpeedometer(currentSpeedAnimated, minSpeed, maxSpeed, theme)
                SpeedometerStyle.LINEAR_HORIZONTAL -> LinearHorizontalSpeedometer(currentSpeedAnimated, minSpeed, maxSpeed, theme)
                SpeedometerStyle.DASHED_ARC -> DashedArcSpeedometer(currentSpeedAnimated, minSpeed, maxSpeed, theme)
                SpeedometerStyle.QUARTER_CIRCULAR -> QuarterCircularSpeedometer(currentSpeedAnimated, minSpeed, maxSpeed, theme)
                SpeedometerStyle.DOTTED_CIRCULAR -> DottedCircularSpeedometer(currentSpeedAnimated, minSpeed, maxSpeed, theme)
                SpeedometerStyle.GLOW -> GlowSpeedometer(currentSpeedAnimated, minSpeed, maxSpeed, theme)
                SpeedometerStyle.MINIMALIST -> MinimalistSpeedometer(currentSpeedAnimated, minSpeed, maxSpeed, theme)
                SpeedometerStyle.STEPPED -> SteppedSpeedometer(currentSpeedAnimated, minSpeed, maxSpeed, theme)
                SpeedometerStyle.WAVE -> WaveSpeedometer(currentSpeedAnimated, minSpeed, maxSpeed, theme)
                SpeedometerStyle.RETRO -> RetroAnalogSpeedometer(currentSpeedAnimated, minSpeed, maxSpeed, theme)
                SpeedometerStyle.DOUBLE_ARC -> DoubleArcSpeedometer(currentSpeedAnimated, minSpeed, maxSpeed, theme)
                SpeedometerStyle.NEON -> NeonSpeedometer(currentSpeedAnimated, minSpeed, maxSpeed, theme)
            }
        }

        // Numerical representation and performance label
        Column(
            modifier = Modifier.padding(top = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "$currentIntSpeed%",
                color = theme.textPrimaryColor,
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif
            )
//            Text(
//                text = performanceText,
//                color = activeZoneColor,
//                fontSize = 24.sp,
//                fontWeight = FontWeight.Bold,
//                fontFamily = FontFamily.SansSerif
//            )
        }
    }
}
