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
    // Display L — ExtraBold | Size: 32sp | Line Height: 38sp
    displayLarge = TextStyle(
        fontFamily = PoppinsFontFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 32.sp,
        lineHeight = 38.sp,
        letterSpacing = (-0.6).sp,
        color = IxhCharcoal
    ),
    // Display M — ExtraBold | Size: 28sp | Line Height: 34sp
    displayMedium = TextStyle(
        fontFamily = PoppinsFontFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 28.sp,
        lineHeight = 34.sp,
        letterSpacing = (-0.5).sp,
        color = IxhCharcoal
    ),
    // Display S — ExtraBold | Size: 25sp | Line Height: 32sp
    displaySmall = TextStyle(
        fontFamily = PoppinsFontFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 25.sp,
        lineHeight = 32.sp,
        letterSpacing = (-0.4).sp,
        color = IxhCharcoal
    ),
    // Headline L — ExtraBold | Size: 23sp | Line Height: 30sp
    headlineLarge = TextStyle(
        fontFamily = PoppinsFontFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 23.sp,
        lineHeight = 30.sp,
        letterSpacing = (-0.4).sp,
        color = IxhCharcoal
    ),
    // Headline M — ExtraBold | Size: 20sp | Line Height: 26sp
    headlineMedium = TextStyle(
        fontFamily = PoppinsFontFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 20.sp,
        lineHeight = 26.sp,
        letterSpacing = (-0.3).sp,
        color = IxhCharcoal
    ),
    // Headline S — ExtraBold | Size: 18sp | Line Height: 24sp
    headlineSmall = TextStyle(
        fontFamily = PoppinsFontFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        letterSpacing = (-0.25).sp,
        color = IxhCharcoal
    ),
    // Title L — ExtraBold | Size: 18sp | Line Height: 24sp
    titleLarge = TextStyle(
        fontFamily = PoppinsFontFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        letterSpacing = (-0.2).sp,
        color = IxhCharcoal
    ),
    // Title M — Bold | Size: 16sp | Line Height: 22sp
    titleMedium = TextStyle(
        fontFamily = PoppinsFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 22.sp,
        letterSpacing = (-0.15).sp,
        color = IxhCharcoal
    ),
    // Title S — Bold | Size: 14sp | Line Height: 20sp
    titleSmall = TextStyle(
        fontFamily = PoppinsFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = (-0.1).sp,
        color = IxhCharcoal
    ),
    // Body L — SemiBold | Size: 14sp | Line Height: 21sp
    bodyLarge = TextStyle(
        fontFamily = PoppinsFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 21.sp,
        color = IxhCharcoal
    ),
    // Body M — Medium | Size: 13sp | Line Height: 19sp
    bodyMedium = TextStyle(
        fontFamily = PoppinsFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 13.sp,
        lineHeight = 19.sp,
        color = IxhCharcoal
    ),
    // Body S — Medium | Size: 12sp | Line Height: 17sp
    bodySmall = TextStyle(
        fontFamily = PoppinsFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 17.sp,
        color = IxhTextSecondary
    ),
    // Label L — ExtraBold | Size: 12sp | Line Height: 17sp
    labelLarge = TextStyle(
        fontFamily = PoppinsFontFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 12.sp,
        lineHeight = 17.sp,
        letterSpacing = 0.1.sp,
        color = IxhCharcoal
    ),
    // Label M — Bold | Size: 11sp | Line Height: 15sp
    labelMedium = TextStyle(
        fontFamily = PoppinsFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 11.sp,
        lineHeight = 15.sp,
        letterSpacing = 0.1.sp,
        color = IxhCharcoal
    ),
    // Label S — Bold | Size: 10sp | Line Height: 14sp
    labelSmall = TextStyle(
        fontFamily = PoppinsFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 10.sp,
        lineHeight = 14.sp,
        letterSpacing = 0.15.sp,
        color = IxhTextSecondary
    )
)
