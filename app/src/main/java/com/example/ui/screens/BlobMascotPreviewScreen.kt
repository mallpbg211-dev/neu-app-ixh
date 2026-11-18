package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.components.BlobExpression
import com.example.ui.components.IxhAnnouncementHighlightCard
import com.example.ui.components.IxhBlobMascot
import com.example.ui.components.IxhBlobMascotSlot
import com.example.ui.components.IxhBlobSlotStyle
import com.example.ui.components.IxhBottomNavigationBar
import com.example.ui.components.IxhButtonVariant
import com.example.ui.components.IxhChipStyle
import com.example.ui.components.IxhDsButton
import com.example.ui.components.IxhDsChip
import com.example.ui.components.IxhDualToneSystemIcon
import com.example.ui.components.IxhIconButton
import com.example.ui.components.IxhPasswordField
import com.example.ui.components.IxhSectionPillHeader
import com.example.ui.components.IxhSystemIconType
import com.example.ui.components.IxhTextField
import com.example.ui.components.MainNavTab
import com.example.ui.components.ixhBrandGradientBackground
import com.example.ui.components.ixhTactileShadow
import com.example.ui.theme.IxhBackgroundCream
import com.example.ui.theme.IxhCharcoal
import com.example.ui.theme.IxhElevationLevel
import com.example.ui.theme.IxhLightYellow
import com.example.ui.theme.IxhMutedBorder
import com.example.ui.theme.IxhPrimaryYellow
import com.example.ui.theme.IxhSecondaryYellow
import com.example.ui.theme.IxhSurfacePrimary
import com.example.ui.theme.IxhTextSecondary
import com.example.ui.theme.LocalIxhDesign

data class BlobExpressionSpec(
    val expression: BlobExpression,
    val displayTitle: String,
    val animationNote: String,
    val usageContext: String
)

val CanonicalBlobExpressions: List<BlobExpressionSpec> = listOf(
    BlobExpressionSpec(
        expression = BlobExpression.IDLE,
        displayTitle = "Idle",
        animationNote = "Floating lembut ±3.5dp, breathing halus, & 1x kedipan natural",
        usageContext = "State netral saat halaman baru dibuka"
    ),
    BlobExpressionSpec(
        expression = BlobExpression.SMILE,
        displayTitle = "Smile",
        animationNote = "Bounce ramah sekali (squash-stretch kenyal)",
        usageContext = "Sambutan ramah, validasi benar, atau berhasil"
    ),
    BlobExpressionSpec(
        expression = BlobExpression.SAD,
        displayTitle = "Sad",
        animationNote = "Gerakan menunduk lesu & menghela napas (-4.2°)",
        usageContext = "Login gagal, salah password, atau state error"
    ),
    BlobExpressionSpec(
        expression = BlobExpression.BLINK,
        displayTitle = "Blink",
        animationNote = "Kedipan natural cepat (75ms squash vertikal)",
        usageContext = "Transisi kedipan mata natural di sela Idle"
    ),
    BlobExpressionSpec(
        expression = BlobExpression.FOCUS,
        displayTitle = "Focus",
        animationNote = "Condong fokus & lirikan halus mengikuti baris input",
        usageContext = "Pengguna sedang mengetik Username / Input"
    ),
    BlobExpressionSpec(
        expression = BlobExpression.SLEEP,
        displayTitle = "Sleep",
        animationNote = "Breathing lambat & tenang (3 siklus napas lalu hemat daya)",
        usageContext = "Tidak ada interaksi pengguna (idle lama)"
    ),
    BlobExpressionSpec(
        expression = BlobExpression.PASSWORD_SHOW,
        displayTitle = "Password Show",
        animationNote = "Kedua tangan menutup mata rapat + anggukan kecil",
        usageContext = "Saat toggle 'Tampilkan Password' diaktifkan"
    ),
    BlobExpressionSpec(
        expression = BlobExpression.PASSWORD_HIDE,
        displayTitle = "Password Hide",
        animationNote = "Membuka mata kembali dengan tatapan waspada",
        usageContext = "Saat password disembunyikan kembali (•••)"
    ),
    BlobExpressionSpec(
        expression = BlobExpression.THINKING,
        displayTitle = "Thinking",
        animationNote = "Memiringkan kepala (-3.8° ke +3.6°) saat memproses",
        usageContext = "Loading data, verifikasi akun, atau sinkronisasi"
    ),
    BlobExpressionSpec(
        expression = BlobExpression.EXCITED,
        displayTitle = "Excited",
        animationNote = "Double bounce antusias (2x lompatan kenyal)",
        usageContext = "Login berhasil, kas lunas, atau tugas selesai"
    )
)

/**
 * Halaman Preview TAHAP 2 — BLOB MASCOT 3D GLOSSY (Master Prompt V2 §4 & §8).
 * Menampilkan:
 * 1. Header Pill "Blob Mascot — 10 Ekspresi Utama" & Hero 3D-Glossy Blob sesuai referensi_desain_blob.png.
 * 2. Galeri 10 Ekspresi Utama 3D-Glossy (2 kolom x 5 baris) dengan pill label identik referensi.
 * 3. Laboratorium Animasi State & Interaksi Password Show / Hide.
 * 4. Preview Konsistensi Ikon Bold Rounded Dual-Tone pada Ukuran Kecil (Navigasi Bawah & Tombol Aksi).
 */
@Composable
fun BlobMascotPreviewScreen(
    onOpenDesignSystemPreview: (() -> Unit)? = null,
    onClosePreview: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val tokens = LocalIxhDesign.current
    var selectedExpression by remember { mutableStateOf(BlobExpression.IDLE) }
    var animationReplayCount by remember { mutableIntStateOf(0) }
    var demoUsername by remember { mutableStateOf("ahmad.ramadhan") }
    var demoPassword by remember { mutableStateOf("Spensari9H!") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var previewNavTab by remember { mutableStateOf(MainNavTab.BERANDA) }

    val activeSpec = remember(selectedExpression) {
        CanonicalBlobExpressions.firstOrNull { it.expression == selectedExpression }
            ?: CanonicalBlobExpressions.first()
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .ixhBrandGradientBackground(isHeroCard = false)
            .testTag("blob_mascot_preview_list"),
        contentPadding = PaddingValues(
            horizontal = tokens.spacing.space16,
            vertical = tokens.spacing.space12
        ),
        verticalArrangement = Arrangement.spacedBy(tokens.spacing.space16)
    ) {
        // 1. TOP PILL HEADER CARD (Sesuai referensi_desain_blob.png)
        item(key = "blob_top_header") {
            BlobPreviewTopHeaderCard(
                onOpenDesignSystemPreview = onOpenDesignSystemPreview,
                onClosePreview = onClosePreview
            )
        }

        // 2. HERO 3D-GLOSSY SHOWCASE & STATE ANIMATION CONTROLLER
        item(key = "blob_hero_showcase") {
            BlobHeroInteractiveStageCard(
                activeSpec = activeSpec,
                animationTriggerKey = animationReplayCount,
                onReplayAnimation = { animationReplayCount++ },
                onSelectExpression = { exp ->
                    selectedExpression = exp
                    animationReplayCount++
                }
            )
        }

        // 3. 10 EKSPRESI UTAMA GRID (2x5 sesuai referensi_desain_blob.png)
        item(key = "blob_10_expressions_grid") {
            BlobTenExpressionsPosterSection(
                selectedExpression = selectedExpression,
                animationTriggerKey = animationReplayCount,
                onSelectExpression = { exp ->
                    selectedExpression = exp
                    animationReplayCount++
                }
            )
        }

        // 4. INTERAKSI PASSWORD SHOW / HIDE & INPUT FOCUS LAB
        item(key = "blob_password_interaction_lab") {
            BlobPasswordInteractionCard(
                username = demoUsername,
                onUsernameChange = {
                    demoUsername = it
                    selectedExpression = BlobExpression.FOCUS
                    animationReplayCount++
                },
                password = demoPassword,
                onPasswordChange = {
                    demoPassword = it
                    selectedExpression = if (isPasswordVisible) {
                        BlobExpression.PASSWORD_SHOW
                    } else {
                        BlobExpression.PASSWORD_HIDE
                    }
                },
                passwordVisible = isPasswordVisible,
                onTogglePasswordVisible = {
                    isPasswordVisible = !isPasswordVisible
                    selectedExpression = if (isPasswordVisible) {
                        BlobExpression.PASSWORD_SHOW
                    } else {
                        BlobExpression.PASSWORD_HIDE
                    }
                    animationReplayCount++
                },
                currentExpression = selectedExpression
            )
        }

        // 5. KONSISTENSI ICON SYSTEM BOLD ROUNDED DUAL-TONE PADA UKURAN KECIL
        item(key = "small_icon_consistency_showcase") {
            SmallDualToneIconIntegrationCard(
                currentNavTab = previewNavTab,
                onSelectNavTab = { previewNavTab = it }
            )
        }

        item(key = "blob_preview_bottom_spacer") {
            Spacer(modifier = Modifier.height(tokens.spacing.space24))
        }
    }
}

@Composable
private fun BlobPreviewTopHeaderCard(
    onOpenDesignSystemPreview: (() -> Unit)?,
    onClosePreview: (() -> Unit)?
) {
    val tokens = LocalIxhDesign.current
    val pillShape = RoundedCornerShape(tokens.radius.extraLarge)

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .ixhTactileShadow(
                    level = IxhElevationLevel.LOW,
                    cornerRadius = tokens.radius.extraLarge,
                    shadowColor = IxhCharcoal.copy(alpha = 0.20f)
                )
                .clip(pillShape)
                .background(
                    Brush.horizontalGradient(
                        listOf(IxhSurfacePrimary, IxhBackgroundCream)
                    ),
                    pillShape
                )
                .border(tokens.border.thick, IxhCharcoal, pillShape)
                .padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                IxhBlobMascotSlot(
                    slotStyle = IxhBlobSlotStyle.APP_BADGE_3D,
                    size = 44.dp
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Blob Mascot",
                    style = MaterialTheme.typography.headlineMedium,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = IxhCharcoal,
                    maxLines = 1
                )
            }

            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(IxhPrimaryYellow)
                    .border(1.5.dp, IxhCharcoal, CircleShape)
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "10 Ekspresi Utama",
                    style = MaterialTheme.typography.labelLarge,
                    fontSize = 11.5.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = IxhCharcoal,
                    maxLines = 1
                )
            }
        }

        if (onOpenDesignSystemPreview != null || onClosePreview != null) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IxhDsChip(
                    text = "Tahap 2 • 3D Glossy & Animasi",
                    style = IxhChipStyle.BRAND_YELLOW
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (onOpenDesignSystemPreview != null) {
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(IxhSurfacePrimary)
                                .border(1.5.dp, IxhCharcoal, CircleShape)
                                .clickable(onClick = onOpenDesignSystemPreview)
                                .padding(horizontal = 11.dp, vertical = 5.dp)
                                .testTag("switch_to_ds_stage1")
                        ) {
                            Text(
                                text = "Tahap 1 (DS)",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = IxhCharcoal
                            )
                        }
                    }
                    if (onClosePreview != null) {
                        IxhIconButton(
                            icon = Icons.Outlined.Close,
                            contentDescription = "Tutup Preview Blob",
                            onClick = onClosePreview,
                            containerColor = IxhBackgroundCream,
                            size = 34.dp,
                            testTag = "close_blob_preview"
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun BlobHeroInteractiveStageCard(
    activeSpec: BlobExpressionSpec,
    animationTriggerKey: Int,
    onReplayAnimation: () -> Unit,
    onSelectExpression: (BlobExpression) -> Unit
) {
    val tokens = LocalIxhDesign.current
    val shape = RoundedCornerShape(tokens.radius.extraLarge)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .ixhTactileShadow(
                level = IxhElevationLevel.LOW,
                cornerRadius = tokens.radius.extraLarge,
                shadowColor = IxhCharcoal.copy(alpha = 0.18f)
            )
            .clip(shape)
            .ixhBrandGradientBackground(isHeroCard = true)
            .border(tokens.border.thick, IxhCharcoal, shape)
            .padding(tokens.spacing.space16),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(IxhSurfacePrimary.copy(alpha = 0.90f))
                    .border(1.5.dp, IxhCharcoal, CircleShape)
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "3D-Glossy Canonical Mascot",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = IxhCharcoal
                )
            }
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(IxhPrimaryYellow)
                    .border(1.5.dp, IxhCharcoal, CircleShape)
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Text(
                    text = activeSpec.displayTitle,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = IxhCharcoal
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Main 3D-Glossy Animated Hero Blob
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(148.dp),
            contentAlignment = Alignment.Center
        ) {
            // Soft warm radial glow behind the Hero 3D Blob
            Box(
                modifier = Modifier
                    .size(134.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                IxhSurfacePrimary.copy(alpha = 0.72f),
                                IxhSecondaryYellow.copy(alpha = 0.25f),
                                Color.Transparent
                            )
                        )
                    )
            )
            IxhBlobMascot(
                expression = activeSpec.expression,
                size = 136.dp,
                animateBounce = activeSpec.expression == BlobExpression.SMILE ||
                    activeSpec.expression == BlobExpression.EXCITED,
                animationTriggerKey = animationTriggerKey,
                modifier = Modifier.testTag("hero_blob_mascot")
            )
        }

        Text(
            text = activeSpec.animationNote,
            style = MaterialTheme.typography.titleMedium,
            fontSize = 13.5.sp,
            fontWeight = FontWeight.Bold,
            color = IxhCharcoal,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = activeSpec.usageContext,
            style = MaterialTheme.typography.bodySmall,
            color = IxhTextSecondary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IxhDsButton(
                text = "Putar Animasi (${activeSpec.displayTitle})",
                leadingIcon = Icons.Outlined.PlayArrow,
                onClick = onReplayAnimation,
                variant = IxhButtonVariant.PRIMARY,
                modifier = Modifier.weight(1f),
                testTag = "btn_replay_blob_animation"
            )
            IxhIconButton(
                icon = Icons.Outlined.Refresh,
                dualToneIcon = IxhSystemIconType.INFO,
                contentDescription = "Reset ke Idle",
                containerColor = IxhSurfacePrimary,
                onClick = { onSelectExpression(BlobExpression.IDLE) },
                testTag = "btn_reset_blob_idle"
            )
        }
    }
}

@Composable
private fun BlobTenExpressionsPosterSection(
    selectedExpression: BlobExpression,
    animationTriggerKey: Int,
    onSelectExpression: (BlobExpression) -> Unit
) {
    val tokens = LocalIxhDesign.current
    val outerShape = RoundedCornerShape(tokens.radius.extraLarge)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .ixhTactileShadow(
                level = IxhElevationLevel.LOW,
                cornerRadius = tokens.radius.extraLarge,
                shadowColor = IxhCharcoal.copy(alpha = 0.14f)
            )
            .clip(outerShape)
            .background(IxhBackgroundCream.copy(alpha = 0.94f), outerShape)
            .border(tokens.border.regular, IxhCharcoal.copy(alpha = 0.75f), outerShape)
            .padding(tokens.spacing.space16)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IxhSectionPillHeader(title = "10 Ekspresi 3D-Glossy")
            Text(
                text = "Ketuk kartu untuk animasi",
                style = MaterialTheme.typography.labelMedium,
                color = IxhTextSecondary,
                maxLines = 1
            )
        }

        Spacer(modifier = Modifier.height(tokens.spacing.space12))

        // 2x5 Grid matching referensi_desain_blob.png
        CanonicalBlobExpressions.chunked(2).forEachIndexed { rowIndex, pair ->
            if (rowIndex > 0) {
                Spacer(modifier = Modifier.height(12.dp))
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                pair.forEach { spec ->
                    val isSelected = spec.expression == selectedExpression
                    BlobExpressionPosterTile(
                        spec = spec,
                        isSelected = isSelected,
                        animationTriggerKey = if (isSelected) animationTriggerKey else 0,
                        onClick = { onSelectExpression(spec.expression) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun BlobExpressionPosterTile(
    spec: BlobExpressionSpec,
    isSelected: Boolean,
    animationTriggerKey: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val tokens = LocalIxhDesign.current
    val cardShape = RoundedCornerShape(tokens.radius.large)
    val pillShape = CircleShape

    Column(
        modifier = modifier
            .ixhTactileShadow(
                level = if (isSelected) IxhElevationLevel.MEDIUM else IxhElevationLevel.LOW,
                cornerRadius = tokens.radius.large,
                shadowColor = IxhCharcoal.copy(alpha = if (isSelected) 0.24f else 0.12f)
            )
            .clip(cardShape)
            .background(
                brush = if (isSelected) {
                    Brush.verticalGradient(
                        listOf(IxhLightYellow, IxhSurfacePrimary)
                    )
                } else {
                    Brush.verticalGradient(
                        listOf(IxhSurfacePrimary, IxhBackgroundCream)
                    )
                },
                shape = cardShape
            )
            .border(
                width = if (isSelected) tokens.border.thick else tokens.border.regular,
                color = if (isSelected) IxhCharcoal else IxhCharcoal.copy(alpha = 0.45f),
                shape = cardShape
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 10.dp, vertical = 10.dp)
            .testTag("blob_card_${spec.expression.name.lowercase()}"),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isSelected) {
            IxhBlobMascot(
                expression = spec.expression,
                size = 92.dp,
                animateBounce = spec.expression == BlobExpression.SMILE ||
                    spec.expression == BlobExpression.EXCITED,
                animationTriggerKey = animationTriggerKey
            )
        } else {
            Image(
                painter = painterResource(id = spec.expression.drawableRes),
                contentDescription = spec.expression.contentDesc,
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(92.dp)
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Rounded Pill Label matching referensi_desain_blob.png
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(pillShape)
                .background(
                    if (isSelected) IxhPrimaryYellow else IxhBackgroundCream,
                    pillShape
                )
                .border(1.5.dp, IxhCharcoal, pillShape)
                .padding(horizontal = 10.dp, vertical = 5.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = spec.displayTitle,
                style = MaterialTheme.typography.titleMedium,
                fontSize = 13.sp,
                fontWeight = FontWeight.ExtraBold,
                color = IxhCharcoal,
                maxLines = 1
            )
        }
    }
}

@Composable
private fun BlobPasswordInteractionCard(
    username: String,
    onUsernameChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    passwordVisible: Boolean,
    onTogglePasswordVisible: () -> Unit,
    currentExpression: BlobExpression
) {
    val tokens = LocalIxhDesign.current
    val cardShape = RoundedCornerShape(tokens.radius.extraLarge)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .ixhTactileShadow(
                level = IxhElevationLevel.LOW,
                cornerRadius = tokens.radius.extraLarge,
                shadowColor = IxhCharcoal.copy(alpha = 0.14f)
            )
            .clip(cardShape)
            .background(IxhBackgroundCream, cardShape)
            .border(tokens.border.regular, IxhCharcoal.copy(alpha = 0.75f), cardShape)
            .padding(tokens.spacing.space16)
    ) {
        IxhSectionPillHeader(title = "Simulasi Interaksi Password & Input")
        Spacer(modifier = Modifier.height(tokens.spacing.space12))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(tokens.radius.large))
                .background(IxhSurfacePrimary)
                .border(1.5.dp, IxhCharcoal, RoundedCornerShape(tokens.radius.large))
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IxhBlobMascot(
                expression = if (passwordVisible) {
                    BlobExpression.PASSWORD_SHOW
                } else if (currentExpression == BlobExpression.FOCUS) {
                    BlobExpression.FOCUS
                } else {
                    BlobExpression.PASSWORD_HIDE
                },
                size = 76.dp,
                modifier = Modifier.testTag("password_lab_blob")
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                IxhDsChip(
                    text = if (passwordVisible) {
                        "PASSWORD_SHOW • Mata Tertutup"
                    } else {
                        "PASSWORD_HIDE • Mata Terbuka"
                    },
                    style = if (passwordVisible) IxhChipStyle.WARNING_SOFT else IxhChipStyle.SUCCESS
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = if (passwordVisible) {
                        "Password terlihat: Blob menutup kedua matanya dengan tangan demi privasi."
                    } else {
                        "Password tersembunyi (•••): Mata Blob terbuka normal menjaga form login."
                    },
                    style = MaterialTheme.typography.bodySmall,
                    color = IxhCharcoal
                )
            }
        }

        Spacer(modifier = Modifier.height(tokens.spacing.space12))

        IxhTextField(
            value = username,
            onValueChange = onUsernameChange,
            placeholder = "Username Siswa / Pengurus",
            isValid = username.length >= 3,
            testTag = "blob_lab_username"
        )
        Spacer(modifier = Modifier.height(tokens.spacing.space8))
        IxhPasswordField(
            value = password,
            onValueChange = onPasswordChange,
            passwordVisible = passwordVisible,
            onTogglePasswordVisible = onTogglePasswordVisible,
            placeholder = "Password Akun Kelas",
            testTag = "blob_lab_password",
            toggleTestTag = "blob_lab_password_toggle"
        )
    }
}

@Composable
private fun SmallDualToneIconIntegrationCard(
    currentNavTab: MainNavTab,
    onSelectNavTab: (MainNavTab) -> Unit
) {
    val tokens = LocalIxhDesign.current
    val cardShape = RoundedCornerShape(tokens.radius.extraLarge)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .ixhTactileShadow(
                level = IxhElevationLevel.LOW,
                cornerRadius = tokens.radius.extraLarge,
                shadowColor = IxhCharcoal.copy(alpha = 0.14f)
            )
            .clip(cardShape)
            .background(IxhBackgroundCream, cardShape)
            .border(tokens.border.regular, IxhCharcoal.copy(alpha = 0.75f), cardShape)
            .padding(tokens.spacing.space16)
    ) {
        IxhSectionPillHeader(title = "Penerapan Ikon Dual-Tone (Ukuran Kecil)")
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "Ikon Bold Rounded Dual-Tone (30–40% kuning dominan + Charcoal outline) diterapkan konsisten pada tombol aksi (20dp), kartu (24dp), dan navigasi bawah (21dp).",
            style = MaterialTheme.typography.bodySmall,
            color = IxhTextSecondary
        )

        Spacer(modifier = Modifier.height(tokens.spacing.space12))

        // Action Buttons with small Dual-Tone icons (20dp - 22dp)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IxhDsButton(
                text = "Panel Bot",
                dualToneIcon = IxhSystemIconType.PANEL_BOT,
                onClick = {},
                variant = IxhButtonVariant.PRIMARY,
                modifier = Modifier.weight(1f)
            )
            IxhDsButton(
                text = "Pengaturan",
                dualToneIcon = IxhSystemIconType.PENGATURAN,
                onClick = {},
                variant = IxhButtonVariant.SECONDARY,
                modifier = Modifier.weight(1f)
            )
            IxhIconButton(
                icon = Icons.Outlined.Refresh,
                dualToneIcon = IxhSystemIconType.PENGGUNA,
                contentDescription = "Pengguna",
                containerColor = IxhSurfacePrimary,
                onClick = {}
            )
        }

        Spacer(modifier = Modifier.height(tokens.spacing.space12))

        // Highlight Card with 24dp PENGUMUMAN Dual-Tone Icon
        IxhAnnouncementHighlightCard(
            title = "Pengumuman Kelas IX-H",
            subtitle = "Ikon megaphone dual-tone (24dp) pada kartu pengumuman."
        )

        Spacer(modifier = Modifier.height(tokens.spacing.space12))

        // Live Bottom Navigation Bar (21dp Dual-Tone Icons)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(18.dp))
                .border(1.5.dp, IxhMutedBorder, RoundedCornerShape(18.dp))
        ) {
            IxhBottomNavigationBar(
                currentTab = currentNavTab,
                onTabSelected = onSelectNavTab
            )
        }
    }
}
