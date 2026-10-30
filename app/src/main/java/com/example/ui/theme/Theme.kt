package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = MedicalTealDark,
    onPrimary = MedicalTealOnDark,
    primaryContainer = MedicalTealDarkContainer,
    onPrimaryContainer = MedicalTealContainer,
    secondary = SurgicalGreenDark,
    onSecondary = SurgicalGreenOnDark,
    secondaryContainer = SurgicalGreenDarkContainer,
    onSecondaryContainer = SurgicalGreenContainer,
    tertiary = EmergencyRedDark,
    onTertiary = EmergencyRedOnDark,
    tertiaryContainer = EmergencyRedDarkContainer,
    onTertiaryContainer = EmergencyRedContainer,
    background = MedicalDarkBackground,
    surface = MedicalDarkSurface,
    surfaceVariant = MedicalDarkSurfaceVariant,
    onSurface = MedicalDarkOnSurface
)

private val LightColorScheme = lightColorScheme(
    primary = MedicalTealPrimary,
    onPrimary = MedicalTealOnPrimary,
    primaryContainer = MedicalTealContainer,
    onPrimaryContainer = MedicalTealOnContainer,
    secondary = SurgicalGreenSecondary,
    onSecondary = MedicalTealOnPrimary,
    secondaryContainer = SurgicalGreenContainer,
    onSecondaryContainer = SurgicalGreenOnContainer,
    tertiary = EmergencyRedTertiary,
    onTertiary = MedicalTealOnPrimary,
    tertiaryContainer = EmergencyRedContainer,
    onTertiaryContainer = EmergencyRedOnContainer,
    background = MedicalLightBackground,
    surface = MedicalLightSurface,
    surfaceVariant = MedicalLightSurfaceVariant,
    onSurface = MedicalLightOnSurface,
    outline = MedicalLightOutline
)

@Composable
fun RehmanHospitalTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Keep consistent medical hospital branding
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

// Backwards compatibility alias if referenced anywhere
@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) = RehmanHospitalTheme(darkTheme, dynamicColor, content)
