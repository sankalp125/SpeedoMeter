package com.hyperdash.speedometer

import android.content.Context
import android.util.AttributeSet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.AbstractComposeView

/**
 * XML-compatible View for the Speedometer component.
 * Can be used in XML layouts using the <com.hyperdash.speedometer.SpeedometerView /> tag.
 */
class SpeedometerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AbstractComposeView(context, attrs, defStyleAttr) {

    var targetSpeed by mutableStateOf(0f)
    var minSpeed by mutableStateOf(0f)
    var maxSpeed by mutableStateOf(100f)
    var theme by mutableStateOf(DashboardTheme.MinimalLight)
    var style by mutableStateOf(SpeedometerStyle.SEMI_CIRCULAR)

    init {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.SpeedometerView)
            try {
                targetSpeed = typedArray.getFloat(R.styleable.SpeedometerView_targetSpeed, 0f)
                minSpeed = typedArray.getFloat(R.styleable.SpeedometerView_minSpeed, 0f)
                maxSpeed = typedArray.getFloat(R.styleable.SpeedometerView_maxSpeed, 100f)
                val styleIndex = typedArray.getInt(R.styleable.SpeedometerView_speedometerStyle, 0)
                style = SpeedometerStyle.entries[styleIndex]
            } finally {
                typedArray.recycle()
            }
        }
    }

    @Composable
    override fun Content() {
        Speedometer(
            targetSpeed = targetSpeed,
            minSpeed = minSpeed,
            maxSpeed = maxSpeed,
            theme = theme,
            style = style
        )
    }

    // Helper methods for non-Compose callers
    fun setSpeed(speed: Float) {
        targetSpeed = speed
    }

    fun setRange(min: Float, max: Float) {
        minSpeed = min
        maxSpeed = max
    }
}
