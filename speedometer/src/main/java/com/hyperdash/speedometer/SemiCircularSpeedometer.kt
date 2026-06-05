package com.hyperdash.speedometer

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.*

@OptIn(ExperimentalTextApi::class)
@Composable
fun SemiCircularSpeedometer(
    currentValue: Float,
    minRange: Float = 0f,
    maxRange: Float = 100f,
    theme: DashboardTheme,
    modifier: Modifier = Modifier
) {
    val textMeasurer = rememberTextMeasurer()

    // Gauge specifications
    val gaugeStrokeWidth = 80f // changes
    val range = maxRange - minRange
    val safeRange = if (range <= 0f) 1f else range

    Canvas(
        modifier = modifier.fillMaxSize()
    ) {
        val width = size.width
        val height = size.height

        // Define speedometer gauge center. Pivot is at the bottom to maximize gauge size.
        val centerX = width / 2f
        val centerY = height - 40f 

        // Semicircular radius - leave space for labels
        val gaugeRadius = min(width / 2.8f, height - 120f)

        val baseArcRect = Size(gaugeRadius * 2, gaugeRadius * 2)
        val baseArcOffset = Offset(centerX - gaugeRadius, centerY - gaugeRadius)

        // 1. Draw Static Segmented Color Arc Track (5 Zones: 20% each)
        val zones = listOf(
            theme.zone1Color,
            theme.zone2Color,
            theme.zone3Color,
            theme.zone4Color,
            theme.zone5Color
        )

        zones.forEachIndexed { index, color ->
            drawArc(
                color = color,
                startAngle = 180f + (index * 36f),
                sweepAngle = 36f,
                useCenter = false,
                topLeft = baseArcOffset,
                size = baseArcRect,
                style = Stroke(width = gaugeStrokeWidth, cap = StrokeCap.Butt)
            )
        }

        // 2. Draw Numeric Markings (0, 20, 40, 60, 80, 100)
        val markings = listOf(0, 20, 40, 60, 80, 100)
        markings.forEachIndexed { index, value ->
            // Angle corresponds to the boundaries of the 5 zones (180, 216, 252, 288, 324, 360)
            val angleDeg = 180f + (index * 36f)
            val angleRad = Math.toRadians(angleDeg.toDouble())
            
            // Positioning markings just outside the arc
            val labelRadius = gaugeRadius + gaugeStrokeWidth / 2 + 50f
            val labelX = centerX + (cos(angleRad) * labelRadius).toFloat()
            val labelY = centerY + (sin(angleRad) * labelRadius).toFloat()

            val textLayoutResult = textMeasurer.measure(
                text = AnnotatedString(value.toString()),
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif
                )
            )

            drawText(
                textLayoutResult = textLayoutResult,
                topLeft = Offset(
                    x = labelX - textLayoutResult.size.width / 2f,
                    y = labelY - textLayoutResult.size.height / 2f
                )
            )
        }

        // 3. Draw Needle Pointer with Sharp Arrow Head
        val clampedValue = currentValue.coerceIn(minRange, maxRange)
        val valueRatio = (clampedValue - minRange) / safeRange
        val needleLength = gaugeRadius - 80f
        val angleSweep = valueRatio * 180f
        val currentRad = Math.toRadians((180f + angleSweep).toDouble())

        val tipX = centerX + (cos(currentRad) * needleLength).toFloat()
        val tipY = centerY + (sin(currentRad) * needleLength).toFloat()

        // Arrow Head specifications
        val arrowSize = 60f
        val arrowAngle = Math.toRadians(30.0)

        // Calculate the base of the arrow head to end the line there, ensuring sharp corners
        val arrowBaseHeight = (arrowSize * cos(arrowAngle)).toFloat()
        val lineEndX = centerX + (cos(currentRad) * (needleLength - arrowBaseHeight)).toFloat()
        val lineEndY = centerY + (sin(currentRad) * (needleLength - arrowBaseHeight)).toFloat()

        // Draw Needle Line (Flat end at arrow base for sharpness)
        drawLine(
            color = theme.needleColor,
            start = Offset(centerX, centerY),
            end = Offset(lineEndX, lineEndY),
            strokeWidth = 20f,
            cap = StrokeCap.Butt
        )

        // Pivot Circle (Drawn on top of line start to keep the pivot round)
        drawCircle(
            color = theme.needleColor,
            radius = 18f,
            center = Offset(centerX, centerY)
        )

        // Arrow Head (Sharp triangle)
        val path = Path().apply {
            moveTo(tipX, tipY)
            lineTo(
                (tipX - arrowSize * cos(currentRad - arrowAngle)).toFloat(),
                (tipY - arrowSize * sin(currentRad - arrowAngle)).toFloat()
            )
            lineTo(
                (tipX - arrowSize * cos(currentRad + arrowAngle)).toFloat(),
                (tipY - arrowSize * sin(currentRad + arrowAngle)).toFloat()
            )
            close()
        }
        drawPath(path, color = theme.needleColor)
    }
}
