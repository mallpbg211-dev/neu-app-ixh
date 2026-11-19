package com.example.ui.theme

import androidx.compose.ui.graphics.Color

// ============================================================================
// IX-H HUB 1.0 — HYBRID PREMIUM NEUBRUTALISM COLOR TOKENS (Master Prompt V2 §3.1)
// Sumber Kebenaran: referensi_desain_system.png
// ============================================================================

// Core Brand & Surface Tokens
val IxhPrimaryYellow = Color(0xFFFFC928)    // Primary Yellow (#FFC928)
val IxhSecondaryYellow = Color(0xFFFFD95A)  // Secondary Yellow (#FFD95A)
val IxhLightYellow = Color(0xFFFFF0B8)      // Light Yellow (#FFF0B8)
val IxhBackgroundCream = Color(0xFFFDF8EC)  // Background Cream (#FDF8EC)
val IxhSurfacePrimary = Color(0xFFFFFFFF)   // Surface Primary (#FFFFFF)
val IxhSurfaceSecondary = Color(0xFFF2ECE0) // Surface Secondary (#F2ECE0)
val IxhCharcoal = Color(0xFF1C1B1F)         // Charcoal text & outline (#1C1B1F)
val IxhMutedBorder = Color(0xFFE6DEC8)      // Muted Border (#E6DEC8)

// Semantic Status Colors (Master Prompt V2 §3.1)
val IxhSuccess = Color(0xFF35A66B)          // Success (#35A66B)
val IxhWarning = Color(0xFFF2A93B)          // Warning (#F2A93B)
val IxhError = Color(0xFFE34B4B)            // Error (#E34B4B)
val IxhInfo = Color(0xFF4A91D9)             // Information (#4A91D9)

// Documented Supporting Semantic Surface/Text Tokens
// Digunakan khusus untuk latar lembut pada Chip/Badge, Banner State & Feedback, serta teks sekunder kontras tinggi
val IxhTextSecondary = Color(0xFF3A362E)    // Teks sekunder kontras tinggi & tegas di atas Cream/White
val IxhShadowSubtle = Color(0xFF1C1B1F)     // Bayangan taktil tegas berbasis Charcoal

val IxhSuccessBg = Color(0xFFD6F5E5)        // Latar chip/banner Success (Aktif / Berhasil)
val IxhSuccessText = Color(0xFF125933)      // Teks kontras tinggi pada chip Success

val IxhWarningBg = Color(0xFFFFECB3)        // Latar chip/banner Warning (Perhatian)
val IxhWarningText = Color(0xFF6E4200)      // Teks kontras tinggi pada chip Warning

val IxhErrorBg = Color(0xFFFADadd)          // Latar chip/banner Error ("1 hari lagi" / Gagal)
val IxhErrorText = Color(0xFF9E1922)        // Teks kontras tinggi pada chip Error

val IxhInfoBg = Color(0xFFD4E8FC)           // Latar chip/banner Info ("Terbaca" / Pengumuman)
val IxhInfoText = Color(0xFF144985)         // Teks kontras tinggi pada chip Info

// ============================================================================
// Backward-Compatibility Aliases (Agar seluruh halaman lama tetap terkompilasi)
// ============================================================================
val IxhYellow = IxhPrimaryYellow
val IxhYellowLight = IxhLightYellow
val IxhYellowSoft = Color(0xFFFFF6CC)
val IxhInk = IxhCharcoal
val IxhCream = IxhBackgroundCream
val IxhWhite = IxhSurfacePrimary
val IxhBorder = IxhMutedBorder
val IxhGlassBorder = Color(0xFFEDE4CE)
val IxhSurfaceMuted = IxhSurfaceSecondary
val IxhLockedBg = Color(0xFFF5EFE2)

val IxhGreen = IxhSuccess
val IxhGreenText = IxhSuccessText
val IxhGreenBg = IxhSuccessBg

val IxhAmber = IxhWarning
val IxhAmberText = IxhWarningText
val IxhAmberBg = IxhWarningBg
val IxhAlertYellowBg = IxhWarningBg

val IxhBlue = IxhInfo
val IxhBlueText = IxhInfoText
val IxhBlueBg = IxhInfoBg

val IxhRed = IxhError
val IxhRedBg = IxhErrorBg

// WhatsApp Preview & Mascot Accent Palette
val IxhWaHeader = Color(0xFF0B6B5B)
val IxhWaChatBg = Color(0xFFEAE0CA)
val IxhWaBubbleSent = Color(0xFFD9FDD3)
val IxhBlobCheek = Color(0xFFF4A6B0)
