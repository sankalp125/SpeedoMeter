package com.hyperdash.speedometer

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.*

/**
 * A full circular speedometer design.
 */
@OptIn(ExperimentalTextApi::class)
@Composable
fun CircularSpeedometer(
    currentValue: Float,
    minRange: Float = 0f,
    maxRange: Float = 100f,
    theme: DashboardTheme,
    modifier: Modifier = Modifier
) {
    val textMeasurer = rememberTextMeasurer()
    val range = maxRange - minRange
    val safeRange = if (range <= 0f) 1f else range

    Canvas(modifier = modifier.fillMaxSize()) {
        val centerX = size.width / 2f
        val centerY = size.height / 2f
        val radius = min(size.width, size.height) / 2.5f
        val strokeWidth = 40f

        val arcRect = Size(radius * 2, radius * 2)
        val arcOffset = Offset(centerX - radius, centerY - radius)

        // Track
        drawCircle(
            color = theme.trackBgColor,
            radius = radius,
            center = Offset(centerX, centerY),
            style = Stroke(width = strokeWidth)
        )

        // Progress Arc (Full circle mapping)
        val sweepAngle = ((currentValue - minRange) / safeRange) * 360f
        drawArc(
            brush = Brush.sweepGradient(
                0f to theme.zone1Color,
                0.2f to theme.zone2Color,
                0.5f to theme.zone3Color,
                0.8f to theme.zone4Color,
                1f to theme.zone5Color,
                center = Offset(centerX, centerY)
            ),
            startAngle = -90f,
            sweepAngle = sweepAngle,
            useCenter = false,
            topLeft = arcOffset,
            size = arcRect,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
        )

        // Center Text
        val textLayoutResult = textMeasurer.measure(
            text = AnnotatedString(currentValue.toInt().toString()),
            style = TextStyle(
                color = theme.textPrimaryColor,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold
            )
        )
        drawText(
            textLayoutResult = textLayoutResult,
            topLeft = Offset(
                centerX - textLayoutResult.size.width / 2,
                centerY - textLayoutResult.size.height / 2
            )
        )
    }
}

/**
 * A modern, minimalist arc speedometer with a gradient track.
 */
@Composable
fun ModernArcSpeedometer(
    currentValue: Float,
    minRange: Float = 0f,
    maxRange: Float = 100f,
    theme: DashboardTheme,
    modifier: Modifier = Modifier
) {
    val range = maxRange - minRange
    val safeRange = if (range <= 0f) 1f else range

    Canvas(modifier = modifier.fillMaxSize()) {
        val centerX = size.width / 2f
        val centerY = size.height * 0.7f
        val radius = size.width * 0.4f
        val strokeWidth = 20f

        val arcRect = Size(radius * 2, radius * 2)
        val arcOffset = Offset(centerX - radius, centerY - radius)

        // Background Arc
        drawArc(
            color = theme.trackBgColor.copy(alpha = 0.3f),
            startAngle = 180f,
            sweepAngle = 180f,
            useCenter = false,
            topLeft = arcOffset,
            size = arcRect,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
        )

        // Active Arc
        val sweepAngle = ((currentValue - minRange) / safeRange) * 180f
        drawArc(
            brush = Brush.horizontalGradient(
                colors = listOf(theme.zone1Color, theme.zone5Color)
            ),
            startAngle = 180f,
            sweepAngle = sweepAngle,
            useCenter = false,
            topLeft = arcOffset,
            size = arcRect,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
        )

        // Indicator Dot
        val angleRad = Math.toRadians((180f + sweepAngle).toDouble())
        val dotX = centerX + (cos(angleRad) * radius).toFloat()
        val dotY = centerY + (sin(angleRad) * radius).toFloat()
        drawCircle(
            color = theme.needleColor,
            radius = 12f,
            center = Offset(dotX, dotY)
        )
        drawCircle(
            color = Color.White,
            radius = 6f,
            center = Offset(dotX, dotY)
        )
    }
}

/**
 * A sporty speedometer with thick zones and a long needle.
 */
@Composable
fun SportySpeedometer(
    currentValue: Float,
    minRange: Float = 0f,
    maxRange: Float = 100f,
    theme: DashboardTheme,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val centerX = size.width / 2f
        val centerY = size.height * 0.8f
        val radius = size.width * 0.45f
        val strokeWidth = 60f

        val arcOffset = Offset(centerX - radius, centerY - radius)
        val arcSize = Size(radius * 2, radius * 2)

        // Zones
        val zones = listOf(theme.zone1Color, theme.zone2Color, theme.zone3Color, theme.zone4Color, theme.zone5Color)
        zones.forEachIndexed { index, color ->
            drawArc(
                color = color,
                startAngle = 180f + (index * 36f),
                sweepAngle = 34f, // Small gap for "sporty" look
                useCenter = false,
                topLeft = arcOffset,
                size = arcSize,
                style = Stroke(width = strokeWidth)
            )
        }

        // Needle
        val ratio = (currentValue - minRange) / (maxRange - minRange).let { if (it == 0f) 1f else it }
        val angleRad = Math.toRadians((180f + ratio * 180f).toDouble())
        val needleLength = radius + 20f
        
        drawLine(
            color = theme.needleColor,
            start = Offset(centerX, centerY),
            end = Offset(
                centerX + (cos(angleRad) * needleLength).toFloat(),
                centerY + (sin(angleRad) * needleLength).toFloat()
            ),
            strokeWidth = 8f,
            cap = StrokeCap.Round
        )
        
        drawCircle(theme.needleColor, radius = 15f, center = Offset(centerX, centerY))
    }
}

/**
 * A vertical linear speedometer.
 */
@Composable
fun LinearVerticalSpeedometer(
    currentValue: Float,
    minRange: Float = 0f,
    maxRange: Float = 100f,
    theme: DashboardTheme,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.fillMaxSize().padding(horizontal = 40.dp)) {
        val width = 40f
        val height = size.height * 0.8f
        val top = (size.height - height) / 2
        val left = (size.width - width) / 2

        // Background
        drawRoundRect(
            color = theme.trackBgColor,
            topLeft = Offset(left, top),
            size = Size(width, height),
            cornerRadius = CornerRadius(20f, 20f)
        )

        // Progress
        val ratio = (currentValue - minRange) / (maxRange - minRange).let { if (it == 0f) 1f else it }
        val progressHeight = height * ratio
        drawRoundRect(
            brush = Brush.verticalGradient(
                colors = listOf(theme.zone5Color, theme.zone1Color),
                startY = top + height - progressHeight,
                endY = top + height
            ),
            topLeft = Offset(left, top + height - progressHeight),
            size = Size(width, progressHeight),
            cornerRadius = CornerRadius(20f, 20f)
        )
    }
}

/**
 * A horizontal linear speedometer.
 */
@Composable
fun LinearHorizontalSpeedometer(
    currentValue: Float,
    minRange: Float = 0f,
    maxRange: Float = 100f,
    theme: DashboardTheme,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.fillMaxSize().padding(vertical = 40.dp)) {
        val height = 30f
        val width = size.width * 0.8f
        val left = (size.width - width) / 2
        val top = (size.height - height) / 2

        drawRoundRect(
            color = theme.trackBgColor,
            topLeft = Offset(left, top),
            size = Size(width, height),
            cornerRadius = CornerRadius(15f, 15f)
        )

        val ratio = (currentValue - minRange) / (maxRange - minRange).let { if (it == 0f) 1f else it }
        val progressWidth = width * ratio
        drawRoundRect(
            brush = Brush.horizontalGradient(
                colors = listOf(theme.zone1Color, theme.zone5Color)
            ),
            topLeft = Offset(left, top),
            size = Size(progressWidth, height),
            cornerRadius = CornerRadius(15f, 15f)
        )
    }
}

/**
 * A dashed arc speedometer.
 */
@Composable
fun DashedArcSpeedometer(
    currentValue: Float,
    minRange: Float = 0f,
    maxRange: Float = 100f,
    theme: DashboardTheme,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val centerX = size.width / 2f
        val centerY = size.height * 0.7f
        val radius = size.width * 0.4f
        val arcSize = Size(radius * 2, radius * 2)
        val arcOffset = Offset(centerX - radius, centerY - radius)

        val dashCount = 30
        val dashAngle = 180f / dashCount
        
        val currentDash = ((currentValue - minRange) / (maxRange - minRange).let { if (it == 0f) 1f else it } * dashCount).toInt()

        for (i in 0 until dashCount) {
            val color = if (i <= currentDash) {
                when {
                    i < dashCount * 0.2 -> theme.zone1Color
                    i < dashCount * 0.4 -> theme.zone2Color
                    i < dashCount * 0.6 -> theme.zone3Color
                    i < dashCount * 0.8 -> theme.zone4Color
                    else -> theme.zone5Color
                }
            } else {
                theme.trackBgColor
            }

            drawArc(
                color = color,
                startAngle = 180f + (i * dashAngle) + 1f,
                sweepAngle = dashAngle - 2f,
                useCenter = false,
                topLeft = arcOffset,
                size = arcSize,
                style = Stroke(width = 30f)
            )
        }
    }
}

/**
 * A quarter circular speedometer (Bottom Right).
 */
@Composable
fun QuarterCircularSpeedometer(
    currentValue: Float,
    minRange: Float = 0f,
    maxRange: Float = 100f,
    theme: DashboardTheme,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val radius = min(size.width, size.height) * 0.8f
        val arcSize = Size(radius * 2, radius * 2)
        val arcOffset = Offset(size.width - radius, size.height - radius)

        drawArc(
            color = theme.trackBgColor,
            startAngle = 180f,
            sweepAngle = 90f,
            useCenter = false,
            topLeft = arcOffset,
            size = arcSize,
            style = Stroke(width = 40f)
        )

        val sweepAngle = ((currentValue - minRange) / (maxRange - minRange).let { if (it == 0f) 1f else it }) * 90f
        drawArc(
            color = theme.zone3Color,
            startAngle = 180f,
            sweepAngle = sweepAngle,
            useCenter = false,
            topLeft = arcOffset,
            size = arcSize,
            style = Stroke(width = 40f, cap = StrokeCap.Round)
        )
    }
}

/**
 * A dot-based circular speedometer.
 */
@Composable
fun DottedCircularSpeedometer(
    currentValue: Float,
    minRange: Float = 0f,
    maxRange: Float = 100f,
    theme: DashboardTheme,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val centerX = size.width / 2f
        val centerY = size.height / 2f
        val radius = min(size.width, size.height) / 3f
        
        val dotCount = 40
        val ratio = (currentValue - minRange) / (maxRange - minRange).let { if (it == 0f) 1f else it }
        val activeDots = (ratio * dotCount).toInt()

        for (i in 0 until dotCount) {
            val angleRad = Math.toRadians((i * (360.0 / dotCount) - 90.0))
            val x = centerX + (cos(angleRad) * radius).toFloat()
            val y = centerY + (sin(angleRad) * radius).toFloat()
            
            val color = if (i <= activeDots) theme.zone5Color else theme.trackBgColor.copy(alpha = 0.5f)
            val dotRadius = if (i <= activeDots) 10f else 6f
            
            drawCircle(color = color, radius = dotRadius, center = Offset(x, y))
        }
    }
}

/**
 * Speedometer with a glowing progress arc.
 */
@Composable
fun GlowSpeedometer(
    currentValue: Float,
    minRange: Float = 0f,
    maxRange: Float = 100f,
    theme: DashboardTheme,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val centerX = size.width / 2f
        val centerY = size.height / 2f
        val radius = size.width * 0.35f
        val arcSize = Size(radius * 2, radius * 2)
        val arcOffset = Offset(centerX - radius, centerY - radius)

        // Shadow/Glow effect (Simplified for Canvas)
        drawArc(
            color = theme.zone4Color.copy(alpha = 0.2f),
            startAngle = 135f,
            sweepAngle = 270f,
            useCenter = false,
            topLeft = arcOffset,
            size = arcSize,
            style = Stroke(width = 50f, cap = StrokeCap.Round)
        )

        val sweepAngle = ((currentValue - minRange) / (maxRange - minRange).let { if (it == 0f) 1f else it }) * 270f
        drawArc(
            brush = Brush.sweepGradient(
                colors = listOf(theme.zone1Color, theme.zone5Color),
                center = Offset(centerX, centerY)
            ),
            startAngle = 135f,
            sweepAngle = sweepAngle,
            useCenter = false,
            topLeft = arcOffset,
            size = arcSize,
            style = Stroke(width = 30f, cap = StrokeCap.Round)
        )
    }
}

/**
 * A minimalist speedometer with just a large number and a small arc.
 */
@OptIn(ExperimentalTextApi::class)
@Composable
fun MinimalistSpeedometer(
    currentValue: Float,
    minRange: Float = 0f,
    maxRange: Float = 100f,
    theme: DashboardTheme,
    modifier: Modifier = Modifier
) {
    val textMeasurer = rememberTextMeasurer()
    Canvas(modifier = modifier.fillMaxSize()) {
        val centerX = size.width / 2f
        val centerY = size.height / 2f
        
        val textLayoutResult = textMeasurer.measure(
            text = AnnotatedString(currentValue.toInt().toString()),
            style = TextStyle(
                color = theme.textPrimaryColor,
                fontSize = 80.sp,
                fontWeight = FontWeight.ExtraBold
            )
        )
        
        drawText(
            textLayoutResult = textLayoutResult,
            topLeft = Offset(centerX - textLayoutResult.size.width / 2, centerY - textLayoutResult.size.height / 2)
        )

        val radius = 120f
        val ratio = (currentValue - minRange) / (maxRange - minRange).let { if (it == 0f) 1f else it }
        drawArc(
            color = theme.zone5Color,
            startAngle = -90f,
            sweepAngle = ratio * 360f,
            useCenter = false,
            topLeft = Offset(centerX - radius, centerY - radius),
            size = Size(radius * 2, radius * 2),
            style = Stroke(width = 10f, cap = StrokeCap.Round)
        )
    }
}

/**
 * A speedometer with discrete steps/blocks.
 */
@Composable
fun SteppedSpeedometer(
    currentValue: Float,
    minRange: Float = 0f,
    maxRange: Float = 100f,
    theme: DashboardTheme,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val centerX = size.width / 2f
        val centerY = size.height * 0.7f
        val radius = size.width * 0.4f
        val blockCount = 15
        val ratio = (currentValue - minRange) / (maxRange - minRange).let { if (it == 0f) 1f else it }
        val activeBlocks = (ratio * blockCount).toInt()

        for (i in 0 until blockCount) {
            val angleDeg = 180f + (i * (180f / blockCount))
            val angleRad = Math.toRadians(angleDeg.toDouble())
            
            val color = if (i < activeBlocks) {
                when {
                    i < blockCount * 0.3 -> theme.zone2Color
                    i < blockCount * 0.7 -> theme.zone4Color
                    else -> theme.zone5Color
                }
            } else theme.trackBgColor.copy(alpha = 0.3f)

            val x1 = centerX + (cos(angleRad) * (radius - 20f)).toFloat()
            val y1 = centerY + (sin(angleRad) * (radius - 20f)).toFloat()
            val x2 = centerX + (cos(angleRad) * (radius + 20f)).toFloat()
            val y2 = centerY + (sin(angleRad) * (radius + 20f)).toFloat()

            drawLine(
                color = color,
                start = Offset(x1, y1),
                end = Offset(x2, y2),
                strokeWidth = 15f,
                cap = StrokeCap.Round
            )
        }
    }
}

/**
 * A wave-filled circular speedometer.
 */
@Composable
fun WaveSpeedometer(
    currentValue: Float,
    minRange: Float = 0f,
    maxRange: Float = 100f,
    theme: DashboardTheme,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val centerX = size.width / 2f
        val centerY = size.height / 2f
        val radius = min(size.width, size.height) / 3f
        
        // Background Circle
        drawCircle(
            color = theme.trackBgColor.copy(alpha = 0.2f),
            radius = radius,
            center = Offset(centerX, centerY)
        )

        // Wave Fill (Clipped Circle)
        val ratio = (currentValue - minRange) / (maxRange - minRange).let { if (it == 0f) 1f else it }
        val fillHeight = radius * 2 * ratio
        
        val path = Path().apply {
            addOval(Rect(centerX - radius, centerY - radius, centerX + radius, centerY + radius))
        }

        clipPath(path) {
            drawRect(
                brush = Brush.verticalGradient(
                    colors = listOf(theme.zone5Color, theme.zone3Color),
                    startY = centerY + radius - fillHeight,
                    endY = centerY + radius
                ),
                topLeft = Offset(centerX - radius, centerY + radius - fillHeight),
                size = Size(radius * 2, fillHeight)
            )
        }
        
        // Outline
        drawCircle(
            color = theme.textPrimaryColor.copy(alpha = 0.5f),
            radius = radius,
            center = Offset(centerX, centerY),
            style = Stroke(width = 4f)
        )
    }
}

/**
 * A classic analog speedometer with a ring of numbers.
 */
@OptIn(ExperimentalTextApi::class)
@Composable
fun RetroAnalogSpeedometer(
    currentValue: Float,
    minRange: Float = 0f,
    maxRange: Float = 100f,
    theme: DashboardTheme,
    modifier: Modifier = Modifier
) {
    val textMeasurer = rememberTextMeasurer()
    Canvas(modifier = modifier.fillMaxSize()) {
        val centerX = size.width / 2f
        val centerY = size.height / 2f
        val radius = min(size.width, size.height) / 2.8f

        // Outer Ring
        drawCircle(
            color = Color.DarkGray,
            radius = radius + 10f,
            center = Offset(centerX, centerY),
            style = Stroke(width = 4f)
        )

        // Numbers
        val markings = listOf(0, 20, 40, 60, 80, 100)
        markings.forEach { value ->
            val angleDeg = 135f + (value / 100f * 270f)
            val angleRad = Math.toRadians(angleDeg.toDouble())
            val textRadius = radius - 40f
            
            val x = centerX + (cos(angleRad) * textRadius).toFloat()
            val y = centerY + (sin(angleRad) * textRadius).toFloat()

            val textLayoutResult = textMeasurer.measure(
                text = AnnotatedString(value.toString()),
                style = TextStyle(color = Color.Black, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            )
            drawText(
                textLayoutResult = textLayoutResult,
                topLeft = Offset(x - textLayoutResult.size.width / 2, y - textLayoutResult.size.height / 2)
            )
        }

        // Needle
        val ratio = (currentValue - minRange) / (maxRange - minRange).let { if (it == 0f) 1f else it }
        val angleDeg = 135f + (ratio * 270f)
        val angleRad = Math.toRadians(angleDeg.toDouble())
        
        drawLine(
            color = Color.Red,
            start = Offset(centerX, centerY),
            end = Offset(
                centerX + (cos(angleRad) * (radius - 10f)).toFloat(),
                centerY + (sin(angleRad) * (radius - 10f)).toFloat()
            ),
            strokeWidth = 6f,
            cap = StrokeCap.Round
        )
        
        drawCircle(Color.Black, radius = 12f, center = Offset(centerX, centerY))
    }
}

/**
 * A double arc speedometer.
 */
@Composable
fun DoubleArcSpeedometer(
    currentValue: Float,
    minRange: Float = 0f,
    maxRange: Float = 100f,
    theme: DashboardTheme,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val centerX = size.width / 2f
        val centerY = size.height / 2f
        val radius1 = size.width * 0.3f
        val radius2 = size.width * 0.35f
        
        val ratio = (currentValue - minRange) / (maxRange - minRange).let { if (it == 0f) 1f else it }
        
        // Inner Arc
        drawArc(
            color = theme.zone1Color,
            startAngle = 135f,
            sweepAngle = ratio * 270f,
            useCenter = false,
            topLeft = Offset(centerX - radius1, centerY - radius1),
            size = Size(radius1 * 2, radius1 * 2),
            style = Stroke(width = 20f, cap = StrokeCap.Round)
        )

        // Outer Arc (Delayed or secondary effect)
        drawArc(
            color = theme.zone5Color.copy(alpha = 0.6f),
            startAngle = 135f,
            sweepAngle = min(ratio * 1.2f, 1f) * 270f,
            useCenter = false,
            topLeft = Offset(centerX - radius2, centerY - radius2),
            size = Size(radius2 * 2, radius2 * 2),
            style = Stroke(width = 10f, cap = StrokeCap.Round)
        )
    }
}

/**
 * A speedometer with a neon glow effect.
 */
@Composable
fun NeonSpeedometer(
    currentValue: Float,
    minRange: Float = 0f,
    maxRange: Float = 100f,
    theme: DashboardTheme,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val centerX = size.width / 2f
        val centerY = size.height / 2f
        val radius = size.width * 0.38f
        
        val ratio = (currentValue - minRange) / (maxRange - minRange).let { if (it == 0f) 1f else it }
        val sweepAngle = ratio * 270f

        // Neon Glow (Wide, blurry stroke)
        drawArc(
            color = theme.zone4Color.copy(alpha = 0.3f),
            startAngle = 135f,
            sweepAngle = sweepAngle,
            useCenter = false,
            topLeft = Offset(centerX - radius, centerY - radius),
            size = Size(radius * 2, radius * 2),
            style = Stroke(width = 40f, cap = StrokeCap.Round)
        )

        // Main Neon Line
        drawArc(
            color = theme.zone4Color,
            startAngle = 135f,
            sweepAngle = sweepAngle,
            useCenter = false,
            topLeft = Offset(centerX - radius, centerY - radius),
            size = Size(radius * 2, radius * 2),
            style = Stroke(width = 8f, cap = StrokeCap.Round)
        )
    }
}
