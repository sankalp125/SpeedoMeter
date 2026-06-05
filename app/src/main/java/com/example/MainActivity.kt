package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.hyperdash.speedometer.Speedometer
import com.example.ui.speedometer.DashboardViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        val viewModel: DashboardViewModel = viewModel()
        val targetSpeed by viewModel.targetSpeed.collectAsState()

        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
          Column(
            modifier = Modifier
              .padding(innerPadding)
              .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            // Speedometer component from SDK
            Speedometer(
              targetSpeed = targetSpeed,
              minSpeed = 0f,
              maxSpeed = 100f,
              modifier = Modifier.weight(1f)
            )

            // Slider in the APP to control the speed
            Column(
              modifier = Modifier.padding(24.dp),
              horizontalAlignment = Alignment.CenterHorizontally
            ) {
              Text(text = "Control Speed from App")
              Slider(
                value = targetSpeed,
                onValueChange = { viewModel.setTargetSpeed(it) },
                valueRange = 0f..100f
              )
            }
          }
        }
      }
    }
  }
}
