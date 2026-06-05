# SpeedoMeter 🚀

A highly customizable, elegant, and modern Speedometer library for Android. Built with **Jetpack Compose**, it offers 16+ unique designs and is fully compatible with **Traditional XML Layouts**.

[![JitPack](https://jitpack.io/v/sankalp125/SpeedoMeter.svg)](https://jitpack.io/#sankalp125/SpeedoMeter)
[![Android SDK](https://img.shields.io/badge/SDK-24%2B-brightgreen.svg)](https://android-arsenal.com/api?level=24)

---

## ✨ Features

- 🎨 **16+ Unique Styles**: From classic analog to modern neon and wave designs.
- 📱 **Dual Support**: Works seamlessly with Jetpack Compose and XML Views.
- 🌈 **Fully Customizable**: Change colors, ranges, and themes easily.
- ⚡ **Smooth Animations**: High-performance needle and progress transitions.
- 🛠️ **Easy Integration**: Get started in just a few minutes.

---

## 📸 Preview Styles

| Style | Preview | Style | Preview |
|-------|---------|-------|---------|
| **Semi Circular** | ![Semi](https://via.placeholder.com/150?text=Semi+Circular) | **Circular** | ![Circular](https://via.placeholder.com/150?text=Circular) |
| **Neon** | ![Neon](https://via.placeholder.com/150?text=Neon) | **Sporty** | ![Sporty](https://via.placeholder.com/150?text=Sporty) |
| **Wave** | ![Wave](https://via.placeholder.com/150?text=Wave) | **Glow** | ![Glow](https://via.placeholder.com/150?text=Glow) |
| **Retro** | ![Retro](https://via.placeholder.com/150?text=Retro) | **Stepped** | ![Stepped](https://via.placeholder.com/150?text=Stepped) |

> *Note: Add your actual design screenshots in the `images/` folder and update these links.*

---

## 📦 Installation

### 1. Add JitPack to your `settings.gradle`
```kotlin
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```

### 2. Add the dependency to your app `build.gradle.kts`
```kotlin
dependencies {
    implementation("com.github.sankalp125:SpeedoMeter:v1.0.2")
}
```

---

## 🚀 Usage

### 1. Jetpack Compose
Using the speedometer in Compose is as simple as calling a function:

```kotlin
import com.hyperdash.speedometer.Speedometer
import com.hyperdash.speedometer.SpeedometerStyle
import com.hyperdash.speedometer.DashboardTheme

Speedometer(
    targetSpeed = 75f,
    minSpeed = 0f,
    maxSpeed = 100f,
    style = SpeedometerStyle.NEON, // Choose from 16 styles
    theme = DashboardTheme.MinimalLight,
    modifier = Modifier.size(300.dp)
)
```

### 2. Traditional XML Layout
For non-compose projects, use `SpeedometerView` in your XML files:

```xml
<com.hyperdash.speedometer.SpeedometerView
    android:id="@+id/speedometer"
    android:layout_width="300dp"
    android:layout_height="300dp"
    app:targetSpeed="65"
    app:minSpeed="0"
    app:maxSpeed="100"
    app:speedometerStyle="neon" /> <!-- Select style via XML -->
```

**Control via Code (Kotlin/Java):**
```kotlin
val speedometer = findViewById<SpeedometerView>(R.id.speedometer)
speedometer.targetSpeed = 85f
speedometer.style = SpeedometerStyle.WAVE
```

---

## 🎨 Available Styles

Explore all 16 built-in designs using the `SpeedometerStyle` enum:

- `SEMI_CIRCULAR`: The classic dashboard look.
- `CIRCULAR`: Full ring progress.
- `NEON`: High-contrast glowing neon effect.
- `WAVE`: Unique liquid-fill wave effect.
- `SPORTY`: Thick segments with a racing vibe.
- `RETRO`: Classic analog style with a red needle.
- `GLOW`: Soft ambient glow around the arc.
- `STEPPED`: Progress shown in discrete blocks.
- `MODERN_ARC`: Minimalist gradient arc.
- `DASHED_ARC`: Stylized dashed segments.
- `QUARTER_CIRCULAR`: Corner-aligned gauge.
- `DOTTED_CIRCULAR`: Modern dotted progress ring.
- `MINIMALIST`: Just the numbers and a slim arc.
- `LINEAR_VERTICAL`: Vertical bar gauge.
- `LINEAR_HORIZONTAL`: Horizontal bar gauge.
- `DOUBLE_ARC`: Multi-layered progress arcs.

---

## 🛠️ Customization

### Themes
You can define custom colors using the `DashboardTheme` data class:

```kotlin
val MyCustomTheme = DashboardTheme(
    name = "Dark Mode",
    zone1Color = Color.Red,
    zone5Color = Color.Green,
    needleColor = Color.White,
    // ... define other colors
)
```

---

## 🤝 Contribution
Contributions are welcome! If you have a new design idea or found a bug, feel free to open an Issue or a Pull Request.

---

## 📄 License
```
Copyright 2026 Sankalp Tiwari

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0
```
