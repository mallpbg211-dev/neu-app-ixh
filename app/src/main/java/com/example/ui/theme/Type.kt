package com.example.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.R

// ============================================================================
// IX-H HUB 1.0 — POPPINS TYPOGRAPHY SYSTEM (Master Prompt V2 §3.2)
// Sumber Kebenaran: referensi_desain_system.png ("Skala Tipografi")
// Menggunakan aset lokal app/src/main/res/font/poppins.ttf (R.font.poppins)
// ============================================================================

val PoppinsFontFamily = FontFamily(
    Font(resId = R.font.poppins_regular, weight = FontWeight.Normal),
    Font(resId = R.font.poppins_medium, weight = FontWeight.Medium),
    Font(resId = R.font.poppins_semibold, weight = FontWeight.SemiBold),
    Font(resId = R.font.poppins_bold, weight = FontWeight.Bold),
    Font(resId = R.font.poppins_extrabold, weight = FontWeight.ExtraBold)
)

val Typography = Typography(
    // Display L — ExtraBold | Size: 32sp | Line Height: 40sp
    displayLarge = TextStyle(
        fontFamily = PoppinsFontFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = (-0.4).sp,
        color = IxhCharcoal
    ),
    // Display M — ExtraBold | Size: 28sp | Line Height: 36sp
    displayMedium = TextStyle(
        fontFamily = PoppinsFontFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = (-0.3).sp,
        color = IxhCharcoal
    ),
    // Display S (Supporting) — ExtraBold | Size: 26sp | Line Height: 34sp
    displaySmall = TextStyle(
        fontFamily = PoppinsFontFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 26.sp,
        lineHeight = 34.sp,
        color = IxhCharcoal
    ),
    // Headline L — Bold | Size: 24sp | Line Height: 32sp
    headlineLarge = TextStyle(
        fontFamily = PoppinsFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = (-0.2).sp,
        color = IxhCharcoal
    ),
    // Headline M — Bold | Size: 20sp | Line Height: 28sp
    headlineMedium = TextStyle(
        fontFamily = PoppinsFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 28.sp,
        color = IxhCharcoal
    ),
    // Headline S (Supporting) — Bold | Size: 18sp | Line Height: 26sp
    headlineSmall = TextStyle(
        fontFamily = PoppinsFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        lineHeight = 26.sp,
        color = IxhCharcoal
    ),
    // Title L — SemiBold | Size: 18sp | Line Height: 26sp
    titleLarge = TextStyle(
        fontFamily = PoppinsFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = 26.sp,
        color = IxhCharcoal
    ),
    // Title M — SemiBold | Size: 16sp | Line Height: 24sp
    titleMedium = TextStyle(
        fontFamily = PoppinsFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        color = IxhCharcoal
    ),
    // Title S (Supporting) — SemiBold | Size: 14sp | Line Height: 20sp
    titleSmall = TextStyle(
        fontFamily = PoppinsFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        color = IxhCharcoal
    ),
    // Body L — Medium | Size: 14sp | Line Height: 22sp
    bodyLarge = TextStyle(
        fontFamily = PoppinsFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 22.sp,
        color = IxhCharcoal
    ),
    // Body M — Regular | Size: 13sp | Line Height: 20sp
    bodyMedium = TextStyle(
        fontFamily = PoppinsFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp,
        lineHeight = 20.sp,
        color = IxhCharcoal
    ),
    // Body S — Regular | Size: 12sp | Line Height: 18sp
    bodySmall = TextStyle(
        fontFamily = PoppinsFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 18.sp,
        color = IxhTextSecondary
    ),
    // Label — SemiBold | Size: 12sp | Line Height: 18sp
    labelLarge = TextStyle(
        fontFamily = PoppinsFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        lineHeight = 18.sp,
        color = IxhCharcoal
    ),
    // Label M — SemiBold | Size: 11sp | Line Height: 16sp
    labelMedium = TextStyle(
        fontFamily = PoppinsFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        color = IxhCharcoal
    ),
    // Label S — SemiBold | Size: 10sp | Line Height: 14sp
    labelSmall = TextStyle(
        fontFamily = PoppinsFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 10.sp,
        lineHeight = 14.sp,
        color = IxhTextSecondary
    )
)
