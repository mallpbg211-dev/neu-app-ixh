package com.example.ui.theme

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// ============================================================================
// IX-H HUB 1.0 — HYBRID PREMIUM NEUBRUTALISM DESIGN TOKENS
// Sumber Kebenaran: MASTER_PROMPT_V2_IXH_HUB_1.0_REFERENSI_FINAL.md (§3.3–3.5)
// & referensi_desain_system.png
// ============================================================================

enum class VisualThemeStyle {
    NEUBRUTALISM,
    LIQUID_GLASS
}

/**
 * Spacing System (Basis 4dp / 8dp — Master Prompt V2 §3.3 & referensi_desain_system.png)
 */
@Immutable
data class IxhSpacingTokens(
    val space4: Dp = 4.dp,   // Mikro
    val space8: Dp = 8.dp,   // Kecil
    val space12: Dp = 12.dp, // Jarak komponen
    val space16: Dp = 16.dp, // Padding standar
    val space20: Dp = 20.dp, // Antarkelompok
    val space24: Dp = 24.dp, // Antarseksi
    val space32: Dp = 32.dp  // Jarak besar
)

/**
 * Radius System (Master Prompt V2 §3.4 & referensi_desain_system.png)
 */
@Immutable
data class IxhRadiusTokens(
    val small: Dp = 8.dp,       // Small: 8dp
    val medium: Dp = 12.dp,     // Medium: 12dp
    val large: Dp = 16.dp,      // Large: 16dp
    val extraLarge: Dp = 24.dp, // Extra Large: 24dp
    val full: Dp = 999.dp       // Full: Pill
) {
    val smallShape: RoundedCornerShape get() = RoundedCornerShape(small)
    val mediumShape: RoundedCornerShape get() = RoundedCornerShape(medium)
    val largeShape: RoundedCornerShape get() = RoundedCornerShape(large)
    val extraLargeShape: RoundedCornerShape get() = RoundedCornerShape(extraLarge)
    val fullPillShape: Shape get() = CircleShape
}

/**
 * Border & Outline System (Master Prompt V2 §3.5 & referensi_desain_system.png)
 */
@Immutable
data class IxhBorderTokens(
    val thin: Dp = 1.dp,
    val regular: Dp = 1.5.dp,
    val thick: Dp = 2.dp,
    val primaryOutline: Color = IxhCharcoal,
    val mutedOutline: Color = IxhMutedBorder
)

/**
 * Elevation / Tactile Shadow System (Master Prompt V2 §3.5 & referensi_desain_system.png)
 * - Low: 2 dp
 * - Medium: 4 dp
 * - High: 8 dp
 */
enum class IxhElevationLevel(val offsetDp: Dp, val label: String) {
    NONE(0.dp, "None (0 dp)"),
    LOW(2.dp, "Low (2 dp)"),
    MEDIUM(4.dp, "Medium (4 dp)"),
    HIGH(8.dp, "High (8 dp)")
}

@Immutable
data class IxhElevationTokens(
    val low: Dp = 2.dp,
    val medium: Dp = 4.dp,
    val high: Dp = 8.dp
)

@Immutable
data class IxhDesignTokens(
    val style: VisualThemeStyle = VisualThemeStyle.NEUBRUTALISM,
    val darkMode: Boolean = false,
    val lowRamSolidMode: Boolean = true,
    val spacing: IxhSpacingTokens = IxhSpacingTokens(),
    val radius: IxhRadiusTokens = IxhRadiusTokens(),
    val border: IxhBorderTokens = IxhBorderTokens(),
    val elevation: IxhElevationTokens = IxhElevationTokens(),
    // Token spesifik komponen & kompatibilitas mundur
    val cardCornerRadius: Dp = 16.dp,
    val buttonCornerRadius: Dp = 999.dp,
    val inputCornerRadius: Dp = 999.dp,
    val primaryStrokeWidth: Dp = 2.dp,
    val secondaryStrokeWidth: Dp = 1.5.dp,
    val shadowOffsetY: Dp = 3.dp
)

val LocalIxhDesign = staticCompositionLocalOf { IxhDesignTokens() }

private val LightColorScheme = lightColorScheme(
    primary = IxhPrimaryYellow,
    onPrimary = IxhCharcoal,
    primaryContainer = IxhLightYellow,
    onPrimaryContainer = IxhCharcoal,
    secondary = IxhSecondaryYellow,
    onSecondary = IxhCharcoal,
    secondaryContainer = IxhInfoBg,
    onSecondaryContainer = IxhCharcoal,
    tertiary = IxhSuccess,
    onTertiary = Color.White,
    tertiaryContainer = IxhSuccessBg,
    onTertiaryContainer = IxhCharcoal,
    background = IxhBackgroundCream,
    onBackground = IxhCharcoal,
    surface = IxhSurfacePrimary,
    onSurface = IxhCharcoal,
    surfaceVariant = IxhSurfaceSecondary,
    onSurfaceVariant = IxhTextSecondary,
    error = IxhError,
    onError = Color.White,
    errorContainer = IxhErrorBg,
    onErrorContainer = IxhCharcoal,
    outline = IxhCharcoal,
    outlineVariant = IxhMutedBorder
)

private val DarkColorScheme = darkColorScheme(
    primary = IxhPrimaryYellow,
    onPrimary = IxhCharcoal,
    primaryContainer = Color(0xFF4B3B00),
    onPrimaryContainer = IxhLightYellow,
    secondary = IxhSecondaryYellow,
    onSecondary = IxhCharcoal,
    background = Color(0xFF18171A),
    onBackground = Color(0xFFF7F2E4),
    surface = Color(0xFF242227),
    onSurface = Color(0xFFF7F2E4),
    surfaceVariant = Color(0xFF333037),
    onSurfaceVariant = Color(0xFFC8C0B0),
    error = IxhError,
    outline = IxhPrimaryYellow,
    outlineVariant = Color(0xFF464239)
)

val IxhShapes = Shapes(
    extraSmall = RoundedCornerShape(8.dp),
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(24.dp),
    extraLarge = CircleShape
)

@Composable
fun MyApplicationTheme(
    visualStyle: VisualThemeStyle = VisualThemeStyle.NEUBRUTALISM,
    darkTheme: Boolean = false,
    lowRamSolidMode: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val tokens = remember(visualStyle, darkTheme, lowRamSolidMode) {
        IxhDesignTokens(
            style = visualStyle,
            darkMode = darkTheme,
            lowRamSolidMode = lowRamSolidMode,
            cardCornerRadius = if (visualStyle == VisualThemeStyle.LIQUID_GLASS) 18.dp else 16.dp,
            buttonCornerRadius = if (visualStyle == VisualThemeStyle.LIQUID_GLASS) 16.dp else 12.dp
        )
    }

    CompositionLocalProvider(LocalIxhDesign provides tokens) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            shapes = IxhShapes,
            content = content
        )
    }
}
