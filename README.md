# SpeedoMeter 🚀 - The Ultimate Android Speedometer Library

**SpeedoMeter** is a high-performance, modern **Android Speedometer View** and **Jetpack Compose Gauge** library. It is designed for developers who want to integrate beautiful, customizable, and animated gauges into their Android applications with ease.

Built with **Kotlin** and **Jetpack Compose**, this library provides 16+ pre-defined **Speedometer styles**, ranging from classic analog dashboards to futuristic neon designs. It also offers full backward compatibility for **Traditional Android XML Layouts**.

[![JitPack](https://jitpack.io/v/sankalp125/SpeedoMeter.svg)](https://jitpack.io/#sankalp125/SpeedoMeter)
[![Android SDK](https://img.shields.io/badge/SDK-24%2B-brightgreen.svg)](https://android-arsenal.com/api?level=24)
[![Language](https://img.shields.io/badge/Language-Kotlin-orange.svg)](https://kotlinlang.org/)

---

## ✨ Why Choose This Speedometer Library?

If you are looking for a **Custom Speedometer for Android** or a **Circular Gauge Component**, this library is the perfect choice. 

- ✅ **16+ Premium Designs**: Neon, Wave, Sporty, Retro, and more.
- ✅ **Jetpack Compose Native**: Fully optimized for **Compose UI**.
- ✅ **XML Interoperability**: Easy to use in **Android XML Layouts** via `SpeedometerView`.
- ✅ **Smooth Animations**: Hardware-accelerated needle and progress transitions.
- ✅ **Lightweight & Fast**: No heavy dependencies, optimized for performance.
- ✅ **Highly Customizable**: Modify colors, ranges (min/max), stroke width, and themes.

---

## 📸 Preview Designs (Android UI Components)

| Style | Description | Style | Description |
|-------|-------------|-------|-------------|
| **Neon Glow** | Futuristic glowing neon effect for modern apps. | **Sporty Gauge** | Bold segments designed for racing & automotive UI. |
| **Wave Fill** | Unique liquid-fill effect for battery or speed tracking. | **Retro Analog** | Classic dashboard feel with a precision needle. |
| **Circular Ring** | Clean, minimalist full-circle progress tracker. | **Stepped Blocks** | Discrete segments for a rugged digital look. |

---

## 📦 Installation (JitPack)

Integrating this **Android UI Library** into your project is simple.

### 1. Add JitPack Repository
In your root `settings.gradle` file:
```kotlin
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```

### 2. Add Dependency
In your app-level `build.gradle.kts`:
```kotlin
dependencies {
    implementation("com.github.sankalp125:SpeedoMeter:v1.0.2")
}
```

---

## 🚀 How to Use (Code Examples)

### 🧩 Using in Jetpack Compose
The `Speedometer` composable is the core of this **Compose UI Library**.

```kotlin
import com.hyperdash.speedometer.Speedometer
import com.hyperdash.speedometer.SpeedometerStyle

@Composable
fun MyDashboard() {
    Speedometer(
        targetSpeed = 80f,
        minSpeed = 0f,
        maxSpeed = 160f,
        style = SpeedometerStyle.NEON, // Select your preferred gauge style
        modifier = Modifier.size(300.dp)
    )
}
```

### 🖼️ Using in Traditional XML Layouts
This library acts as a perfect **Android Custom View** for XML-based projects.

```xml
<com.hyperdash.speedometer.SpeedometerView
    android:id="@+id/speedometer"
    android:layout_width="match_parent"
    android:layout_height="300dp"
    app:targetSpeed="70"
    app:speedometerStyle="sporty" /> <!-- Easy XML attributes -->
```

---

## 🎨 Available Gauge Styles

This library includes a variety of **Android Gauge Widgets**:

- `SEMI_CIRCULAR`: Standard semi-circle dashboard.
- `NEON`: Vibrant neon glowing arc.
- `WAVE`: Animated water/wave fill effect.
- `SPORTY`: Bold racing-style segments.
- `RETRO`: Precision analog needle gauge.
- `GLOW`: Soft ambient background glow.
- `DOTTED_CIRCULAR`: Modern dots-based progress ring.
- `QUARTER_CIRCULAR`: Space-saving corner gauge.
- `LINEAR_VERTICAL/HORIZONTAL`: Sleek bar-style speedometers.

---

## 🛠️ Configuration & Customization
You can easily customize the **Dashboard Theme** to match your app's branding:
- Change **Zone Colors** (Green to Red segments).
- Adjust **Needle Thickness** and Color.
- Customize **Background Track** and Glow intensity.

---

## 🏷️ Keywords
Android Speedometer Library, Jetpack Compose Gauge, Android Custom View, Kotlin Speedometer, Circular Progress Bar, Android Dashboard UI, Gauge Component Android, Animated Speedometer Kotlin, Android UI Widgets, SpeedometerView.

---

## 📄 License
Copyright 2026 Sankalp Tiwari. Licensed under the Apache License (Version 2.0).
