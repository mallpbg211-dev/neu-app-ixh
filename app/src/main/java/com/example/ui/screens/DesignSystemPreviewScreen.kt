package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
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
import androidx.compose.material.icons.automirrored.outlined.Login
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.BlobExpression
import com.example.ui.components.IxhAnnouncementHighlightCard
import com.example.ui.components.IxhAttendanceMetricCard
import com.example.ui.components.IxhBlobMascotSlot
import com.example.ui.components.IxhBlobSlotStyle
import com.example.ui.components.IxhBottomSheet
import com.example.ui.components.IxhButtonVariant
import com.example.ui.components.IxhChipStyle
import com.example.ui.components.IxhDialog
import com.example.ui.components.IxhDsButton
import com.example.ui.components.IxhDsChip
import com.example.ui.components.IxhEmptyState
import com.example.ui.components.IxhErrorState
import com.example.ui.components.IxhFeedbackType
import com.example.ui.components.IxhIconButton
import com.example.ui.components.IxhKasMetricCard
import com.example.ui.components.IxhLoadingIndicator
import com.example.ui.components.IxhNeubrutalCheckbox
import com.example.ui.components.IxhPasswordField
import com.example.ui.components.IxhPillToggle
import com.example.ui.components.IxhSectionPillHeader
import com.example.ui.components.IxhStateFeedbackBanner
import com.example.ui.components.IxhStatusIndicator
import com.example.ui.components.IxhSystemIconTile
import com.example.ui.components.IxhSystemIconType
import com.example.ui.components.IxhTextField
import com.example.ui.components.ixhBrandGradientBackground
import com.example.ui.components.ixhTactileShadow
import com.example.ui.theme.IxhBackgroundCream
import com.example.ui.theme.IxhCharcoal
import com.example.ui.theme.IxhElevationLevel
import com.example.ui.theme.IxhError
import com.example.ui.theme.IxhInfo
import com.example.ui.theme.IxhLightYellow
import com.example.ui.theme.IxhMutedBorder
import com.example.ui.theme.IxhPrimaryYellow
import com.example.ui.theme.IxhSecondaryYellow
import com.example.ui.theme.IxhSuccess
import com.example.ui.theme.IxhSurfacePrimary
import com.example.ui.theme.IxhSurfaceSecondary
import com.example.ui.theme.IxhTextSecondary
import com.example.ui.theme.IxhWarning
import com.example.ui.theme.LocalIxhDesign
import com.example.ui.theme.PoppinsFontFamily

private data class ColorSwatchSpec(
    val name: String,
    val hex: String,
    val color: Color,
    val hasDarkBorder: Boolean = false
)

private data class TypographyRowSpec(
    val styleName: String,
    val weightLabel: String,
    val sizeLabel: String,
    val lineHeightLabel: String,
    val textStyle: TextStyle
)

/**
 * Halaman Preview Design System IX-H HUB 1.0 (TAHAP 1 — Master Prompt V2 §3 & §8).
 * Menguji seluruh Color Tokens, Typography Poppins, Spacing, Radius, Border/Shadow,
 * Icon System, serta Reusable UI Components sesuai referensi_desain_system.png.
 */
@Composable
fun DesignSystemPreviewScreen(
    onClosePreview: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val tokens = LocalIxhDesign.current
    var demoUsername by remember { mutableStateOf("") }
    var demoPassword by remember { mutableStateOf("") }
    var demoPasswordVisible by remember { mutableStateOf(false) }
    var toggleOnState by remember { mutableStateOf(true) }
    var toggleOffState by remember { mutableStateOf(false) }
    var checkboxSelected by remember { mutableStateOf(true) }
    var checkboxUnselected by remember { mutableStateOf(false) }
    var showDemoDialog by remember { mutableStateOf(false) }
    var showDemoBottomSheet by remember { mutableStateOf(false) }
    var activeToastBanner by remember { mutableStateOf<String?>(null) }

    val mainSwatches = remember {
        listOf(
            ColorSwatchSpec("Primary", "#FFC928", IxhPrimaryYellow),
            ColorSwatchSpec("Secondary", "#FFD95A", IxhSecondaryYellow),
            ColorSwatchSpec("Cream", "#FDF8EC", IxhBackgroundCream, hasDarkBorder = true),
            ColorSwatchSpec("Surface", "#FFFFFF", IxhSurfacePrimary, hasDarkBorder = true),
            ColorSwatchSpec("Charcoal", "#1C1B1F", IxhCharcoal),
            ColorSwatchSpec("Success", "#35A66B", IxhSuccess),
            ColorSwatchSpec("Warning", "#F2A93B", IxhWarning),
            ColorSwatchSpec("Error", "#E34B4B", IxhError),
            ColorSwatchSpec("Light Yellow", "#FFF0B8", IxhLightYellow, hasDarkBorder = true),
            ColorSwatchSpec("Surface Sec", "#F2ECE0", IxhSurfaceSecondary, hasDarkBorder = true),
            ColorSwatchSpec("Muted Border", "#E6DEC8", IxhMutedBorder, hasDarkBorder = true),
            ColorSwatchSpec("Information", "#4A91D9", IxhInfo)
        )
    }

    if (showDemoDialog) {
        IxhDialog(
            title = "Dialog IX-H HUB 1.0",
            message = "Contoh komponen Dialog dengan Hybrid Premium Neubrutalism, border Charcoal 2dp, dan tombol utama.",
            confirmText = "Siap",
            dismissText = "Tutup",
            onConfirm = { showDemoDialog = false },
            onDismiss = { showDemoDialog = false }
        )
    }

    if (showDemoBottomSheet) {
        IxhBottomSheet(
            title = "Bottom Sheet IX-H HUB 1.0",
            onDismiss = { showDemoBottomSheet = false }
        ) {
            Text(
                text = "Komponen Bottom Sheet menggunakan latar Background Cream (#FDF8EC) dan radius atas Extra Large (24dp).",
                style = MaterialTheme.typography.bodyLarge,
                color = IxhCharcoal
            )
            Spacer(modifier = Modifier.height(14.dp))
            IxhDsButton(
                text = "Tutup Bottom Sheet",
                onClick = { showDemoBottomSheet = false },
                variant = IxhButtonVariant.PRIMARY,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .ixhBrandGradientBackground(isHeroCard = false)
            .padding(horizontal = tokens.spacing.space16)
            .testTag("design_system_preview_list"),
        verticalArrangement = Arrangement.spacedBy(tokens.spacing.space16)
    ) {
        item(key = "header_brand") {
            Spacer(modifier = Modifier.height(tokens.spacing.space12))
            DesignSystemHeroHeader(onClosePreview = onClosePreview)
        }

        if (activeToastBanner != null) {
            item(key = "interactive_feedback_toast") {
                IxhStateFeedbackBanner(
                    message = activeToastBanner!!,
                    type = IxhFeedbackType.SUCCESS
                )
            }
        }

        // 1. PALET WARNA
        item(key = "section_palet_warna") {
            DesignSystemSectionContainer(title = "Palet Warna") {
                Text(
                    text = "Warna utama dan turunannya untuk seluruh komponen aplikasi.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = IxhTextSecondary
                )
                Spacer(modifier = Modifier.height(tokens.spacing.space12))
                mainSwatches.chunked(4).forEach { rowItems ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = tokens.spacing.space12),
                        horizontalArrangement = Arrangement.spacedBy(tokens.spacing.space12)
                    ) {
                        rowItems.forEach { swatch ->
                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                val swatchShape = RoundedCornerShape(tokens.radius.medium)
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(58.dp)
                                        .ixhTactileShadow(
                                            level = IxhElevationLevel.LOW,
                                            cornerRadius = tokens.radius.medium,
                                            shadowColor = IxhCharcoal.copy(alpha = 0.14f)
                                        )
                                        .clip(swatchShape)
                                        .background(swatch.color, swatchShape)
                                        .border(
                                            width = if (swatch.hasDarkBorder) 1.5.dp else 1.dp,
                                            color = if (swatch.hasDarkBorder) IxhMutedBorder else IxhCharcoal.copy(alpha = 0.25f),
                                            shape = swatchShape
                                        )
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = swatch.name,
                                    style = MaterialTheme.typography.labelLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = IxhCharcoal,
                                    textAlign = TextAlign.Center,
                                    maxLines = 1
                                )
                                Text(
                                    text = swatch.hex,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = IxhTextSecondary,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }
        }

        // 2. TIPOGRAFI — POPPINS & SKALA TIPOGRAFI
        item(key = "section_tipografi_poppins") {
            DesignSystemSectionContainer(title = "Tipografi – Poppins") {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(tokens.spacing.space20)
                ) {
                    Text(
                        text = "Aa",
                        fontFamily = PoppinsFontFamily,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 62.sp,
                        lineHeight = 68.sp,
                        color = IxhCharcoal
                    )
                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        Text(
                            text = "Poppins ExtraBold",
                            fontFamily = PoppinsFontFamily,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 15.sp,
                            color = IxhCharcoal
                        )
                        Text(
                            text = "Poppins Bold",
                            fontFamily = PoppinsFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = IxhCharcoal
                        )
                        Text(
                            text = "Poppins SemiBold",
                            fontFamily = PoppinsFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 15.sp,
                            color = IxhCharcoal
                        )
                        Text(
                            text = "Poppins Medium",
                            fontFamily = PoppinsFontFamily,
                            fontWeight = FontWeight.Medium,
                            fontSize = 15.sp,
                            color = IxhCharcoal
                        )
                        Text(
                            text = "Poppins Regular",
                            fontFamily = PoppinsFontFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = 15.sp,
                            color = IxhCharcoal
                        )
                    }
                }
            }
        }

        item(key = "section_skala_tipografi") {
            val typoSpecs = listOf(
                TypographyRowSpec("Display L", "ExtraBold", "32", "40", MaterialTheme.typography.displayLarge),
                TypographyRowSpec("Display M", "ExtraBold", "28", "36", MaterialTheme.typography.displayMedium),
                TypographyRowSpec("Headline L", "Bold", "24", "32", MaterialTheme.typography.headlineLarge),
                TypographyRowSpec("Headline M", "Bold", "20", "28", MaterialTheme.typography.headlineMedium),
                TypographyRowSpec("Title L", "SemiBold", "18", "26", MaterialTheme.typography.titleLarge),
                TypographyRowSpec("Title M", "SemiBold", "16", "24", MaterialTheme.typography.titleMedium),
                TypographyRowSpec("Body L", "Medium", "14", "22", MaterialTheme.typography.bodyLarge),
                TypographyRowSpec("Body M", "Regular", "13", "20", MaterialTheme.typography.bodyMedium),
                TypographyRowSpec("Body S", "Regular", "12", "18", MaterialTheme.typography.bodySmall),
                TypographyRowSpec("Label", "SemiBold", "12", "18", MaterialTheme.typography.labelLarge)
            )
            DesignSystemSectionContainer(title = "Skala Tipografi") {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Style", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1.3f))
                    Text("Weight", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1.1f))
                    Text("Size", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, modifier = Modifier.weight(0.6f))
                    Text("Line Height", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold, textAlign = TextAlign.End, modifier = Modifier.weight(0.9f))
                }
                HorizontalDivider(color = IxhMutedBorder, thickness = 1.dp)
                typoSpecs.forEach { row ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 7.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = row.styleName,
                            fontFamily = PoppinsFontFamily,
                            fontWeight = row.textStyle.fontWeight ?: FontWeight.Normal,
                            fontSize = 13.5.sp,
                            color = IxhCharcoal,
                            modifier = Modifier.weight(1.3f)
                        )
                        Text(
                            text = row.weightLabel,
                            style = MaterialTheme.typography.bodyMedium,
                            color = IxhCharcoal,
                            modifier = Modifier.weight(1.1f)
                        )
                        Text(
                            text = row.sizeLabel,
                            style = MaterialTheme.typography.bodyMedium,
                            color = IxhCharcoal,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.weight(0.6f)
                        )
                        Text(
                            text = row.lineHeightLabel,
                            style = MaterialTheme.typography.bodyMedium,
                            color = IxhCharcoal,
                            textAlign = TextAlign.End,
                            modifier = Modifier.weight(0.9f)
                        )
                    }
                    HorizontalDivider(color = IxhMutedBorder.copy(alpha = 0.55f), thickness = 0.8.dp)
                }
            }
        }

        // 3. KOMPONEN UTAMA
        item(key = "section_komponen_utama") {
            DesignSystemSectionContainer(title = "Komponen Utama") {
                // Sub-label: Button
                SubCategoryPillLabel("Button")
                Spacer(modifier = Modifier.height(tokens.spacing.space8))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(tokens.spacing.space8),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IxhDsButton(
                        text = "Primary",
                        leadingIcon = Icons.AutoMirrored.Outlined.Login,
                        onClick = { activeToastBanner = "Tombol Primary ditekan!" },
                        variant = IxhButtonVariant.PRIMARY,
                        modifier = Modifier.weight(1.15f),
                        testTag = "ds_btn_primary"
                    )
                    IxhDsButton(
                        text = "Secondary",
                        onClick = { activeToastBanner = "Tombol Secondary ditekan!" },
                        variant = IxhButtonVariant.SECONDARY,
                        modifier = Modifier.weight(1.05f),
                        testTag = "ds_btn_secondary"
                    )
                    IxhDsButton(
                        text = "Ghost",
                        onClick = { activeToastBanner = "Tombol Ghost ditekan!" },
                        variant = IxhButtonVariant.GHOST,
                        modifier = Modifier.weight(0.85f),
                        testTag = "ds_btn_ghost"
                    )
                }
                Spacer(modifier = Modifier.height(tokens.spacing.space8))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(tokens.spacing.space8),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IxhDsButton(
                        text = "Outlined",
                        onClick = { showDemoDialog = true },
                        variant = IxhButtonVariant.OUTLINED,
                        modifier = Modifier.weight(1f)
                    )
                    IxhDsButton(
                        text = "Bottom Sheet",
                        onClick = { showDemoBottomSheet = true },
                        variant = IxhButtonVariant.SECONDARY,
                        modifier = Modifier.weight(1f)
                    )
                    IxhIconButton(
                        icon = Icons.Outlined.Notifications,
                        contentDescription = "Contoh Icon Button",
                        onClick = { activeToastBanner = "Icon Button (48dp) aktif!" }
                    )
                }

                Spacer(modifier = Modifier.height(tokens.spacing.space16))

                // Sub-label: Text Field
                SubCategoryPillLabel("Text Field")
                Spacer(modifier = Modifier.height(tokens.spacing.space8))
                IxhTextField(
                    value = demoUsername,
                    onValueChange = { demoUsername = it },
                    placeholder = "Username",
                    isValid = demoUsername.length >= 3,
                    testTag = "ds_input_username"
                )
                Spacer(modifier = Modifier.height(tokens.spacing.space8))
                IxhPasswordField(
                    value = demoPassword,
                    onValueChange = { demoPassword = it },
                    passwordVisible = demoPasswordVisible,
                    onTogglePasswordVisible = { demoPasswordVisible = !demoPasswordVisible },
                    placeholder = "Password",
                    testTag = "ds_input_password",
                    toggleTestTag = "ds_toggle_password"
                )

                Spacer(modifier = Modifier.height(tokens.spacing.space16))

                // Sub-label: Toggles & Checkbox
                SubCategoryPillLabel("Toggles & Checkbox")
                Spacer(modifier = Modifier.height(tokens.spacing.space8))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IxhPillToggle(
                        checked = toggleOnState,
                        onCheckedChange = { toggleOnState = it },
                        testTag = "ds_toggle_on"
                    )
                    IxhPillToggle(
                        checked = toggleOffState,
                        onCheckedChange = { toggleOffState = it },
                        testTag = "ds_toggle_off"
                    )
                    IxhNeubrutalCheckbox(
                        checked = checkboxSelected,
                        onCheckedChange = { checkboxSelected = it },
                        label = "Terpilih",
                        testTag = "ds_checkbox_checked"
                    )
                    IxhNeubrutalCheckbox(
                        checked = checkboxUnselected,
                        onCheckedChange = { checkboxUnselected = it },
                        label = "Tidak",
                        testTag = "ds_checkbox_unchecked"
                    )
                }

                Spacer(modifier = Modifier.height(tokens.spacing.space16))

                // Sub-label: Chip / Badge
                SubCategoryPillLabel("Chip / Badge")
                Spacer(modifier = Modifier.height(tokens.spacing.space8))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(tokens.spacing.space8),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IxhDsChip(text = "IX-H", style = IxhChipStyle.BRAND_YELLOW)
                    IxhDsChip(text = "Aktif", style = IxhChipStyle.SUCCESS)
                    IxhDsChip(text = "1 hari lagi", style = IxhChipStyle.ERROR_SOFT)
                    IxhDsChip(text = "Terbaca", style = IxhChipStyle.INFO_SOFT)
                }
                Spacer(modifier = Modifier.height(tokens.spacing.space8))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(tokens.spacing.space16)
                ) {
                    IxhStatusIndicator(label = "Bot Online", color = IxhSuccess)
                    IxhStatusIndicator(label = "Sinkronisasi", color = IxhWarning)
                    IxhStatusIndicator(label = "Terputus", color = IxhError)
                }

                Spacer(modifier = Modifier.height(tokens.spacing.space16))

                // Sub-label: Card / Container
                SubCategoryPillLabel("Card / Container")
                Spacer(modifier = Modifier.height(tokens.spacing.space8))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(tokens.spacing.space12)
                ) {
                    IxhAttendanceMetricCard(modifier = Modifier.weight(1f))
                    IxhKasMetricCard(modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.height(tokens.spacing.space12))
                IxhAnnouncementHighlightCard(
                    onClick = { activeToastBanner = "Kartu Pengumuman diklik!" }
                )
            }
        }

        // 4. ICON SYSTEM
        item(key = "section_icon_system") {
            DesignSystemSectionContainer(title = "Icon System") {
                val allIcons = IxhSystemIconType.entries
                allIcons.chunked(5).forEach { rowIcons ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        rowIcons.forEach { item ->
                            IxhSystemIconTile(
                                iconType = item,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
        }

        // 5. RADIUS, SPACING & ELEVATION / SHADOW
        item(key = "section_radius_spacing_elevation") {
            DesignSystemSectionContainer(title = "Radius") {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadiusSpecimenItem("Small", "8 dp", tokens.radius.small)
                    RadiusSpecimenItem("Medium", "12 dp", tokens.radius.medium)
                    RadiusSpecimenItem("Large", "16 dp", tokens.radius.large)
                }
                Spacer(modifier = Modifier.height(tokens.spacing.space12))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadiusSpecimenItem("Extra Large", "24 dp", tokens.radius.extraLarge)
                    RadiusSpecimenItem("Full", "Pill", tokens.radius.full, isWidePill = true)
                }
            }
        }

        item(key = "section_spacing_system") {
            DesignSystemSectionContainer(title = "Spacing (8 dp Base)") {
                val spacingList = listOf(
                    4.dp to "4",
                    8.dp to "8",
                    12.dp to "12",
                    16.dp to "16",
                    20.dp to "20",
                    24.dp to "24",
                    32.dp to "32"
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    spacingList.forEach { (dpVal, label) ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.weight(1f)
                        ) {
                            Box(
                                modifier = Modifier
                                    .width(24.dp)
                                    .height(dpVal * 1.4f + 8.dp)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(
                                        Brush.verticalGradient(
                                            listOf(IxhSecondaryYellow, IxhPrimaryYellow)
                                        )
                                    )
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = label,
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Bold,
                                color = IxhCharcoal
                            )
                            Text(
                                text = "dp",
                                style = MaterialTheme.typography.labelSmall,
                                color = IxhTextSecondary
                            )
                        }
                    }
                }
            }
        }

        item(key = "section_elevation_shadow") {
            DesignSystemSectionContainer(title = "Elevation / Shadow") {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    ElevationSpecimenItem("Low", "(2 dp)", IxhElevationLevel.LOW)
                    ElevationSpecimenItem("Medium", "(4 dp)", IxhElevationLevel.MEDIUM)
                    ElevationSpecimenItem("High", "(8 dp)", IxhElevationLevel.HIGH)
                }
            }
        }

        // 6. STATE & FEEDBACK
        item(key = "section_state_feedback") {
            DesignSystemSectionContainer(title = "State & Feedback") {
                Column(verticalArrangement = Arrangement.spacedBy(tokens.spacing.space8)) {
                    IxhStateFeedbackBanner(
                        message = "Berhasil! Data telah disimpan.",
                        type = IxhFeedbackType.SUCCESS
                    )
                    IxhStateFeedbackBanner(
                        message = "Informasi untuk pengguna.",
                        type = IxhFeedbackType.INFO
                    )
                    IxhStateFeedbackBanner(
                        message = "Perhatian! Periksa kembali.",
                        type = IxhFeedbackType.WARNING
                    )
                    IxhStateFeedbackBanner(
                        message = "Gagal! Coba lagi nanti.",
                        type = IxhFeedbackType.ERROR
                    )
                }
                Spacer(modifier = Modifier.height(tokens.spacing.space12))
                SubCategoryPillLabel("Loading, Empty & Error States")
                Spacer(modifier = Modifier.height(tokens.spacing.space8))
                IxhLoadingIndicator(
                    label = "Memproses Login...",
                    progress = 0.72f
                )
                Spacer(modifier = Modifier.height(tokens.spacing.space8))
                IxhEmptyState(
                    title = "Belum Ada Riwayat",
                    description = "Data transaksi atau jadwal akan tampil di sini."
                )
                Spacer(modifier = Modifier.height(tokens.spacing.space8))
                IxhErrorState(
                    title = "Koneksi Terputus",
                    description = "Tidak dapat terhubung ke server kelas.",
                    onRetry = { activeToastBanner = "Mencoba menghubungkan kembali..." }
                )
            }
        }

        item(key = "bottom_spacer") {
            Spacer(modifier = Modifier.height(tokens.spacing.space32))
        }
    }
}

@Composable
private fun DesignSystemHeroHeader(onClosePreview: (() -> Unit)?) {
    val tokens = LocalIxhDesign.current
    val shape = RoundedCornerShape(tokens.radius.extraLarge)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .ixhTactileShadow(
                level = IxhElevationLevel.LOW,
                cornerRadius = tokens.radius.extraLarge,
                shadowColor = IxhCharcoal.copy(alpha = 0.22f)
            )
            .clip(shape)
            .ixhBrandGradientBackground(isHeroCard = true)
            .border(tokens.border.thick, IxhCharcoal, shape)
            .padding(tokens.spacing.space16)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Canonical 3D-Glossy Blob App Badge from referensi_desain_system.png
                IxhBlobMascotSlot(
                    slotStyle = IxhBlobSlotStyle.APP_BADGE_3D,
                    targetExpression = BlobExpression.SMILE,
                    size = 64.dp
                )
                Spacer(modifier = Modifier.width(tokens.spacing.space12))
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "IX-H HUB",
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.ExtraBold,
                            color = IxhCharcoal
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(IxhPrimaryYellow)
                                .border(1.5.dp, IxhCharcoal, RoundedCornerShape(10.dp))
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "1.0",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.ExtraBold,
                                color = IxhCharcoal
                            )
                        }
                    }
                    Text(
                        text = "Design System",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = IxhCharcoal
                    )
                }
            }

            if (onClosePreview != null) {
                IxhIconButton(
                    icon = Icons.Outlined.Close,
                    contentDescription = "Tutup Preview Design System",
                    onClick = onClosePreview,
                    containerColor = IxhBackgroundCream,
                    size = 42.dp,
                    testTag = "close_design_system_preview"
                )
            }
        }

        Spacer(modifier = Modifier.height(tokens.spacing.space12))

        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(
                    Brush.horizontalGradient(
                        listOf(IxhPrimaryYellow.copy(alpha = 0.72f), IxhSecondaryYellow)
                    )
                )
                .border(1.5.dp, IxhCharcoal, CircleShape)
                .padding(horizontal = 14.dp, vertical = 5.dp)
        ) {
            Text(
                text = "Hybrid Premium Neubrutalism",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = IxhCharcoal
            )
        }

        Spacer(modifier = Modifier.height(tokens.spacing.space12))

        Text(
            text = "SMPN 1 Bojongsari (Spensari) • Kelas IX-H",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = IxhCharcoal
        )
        Spacer(modifier = Modifier.height(6.dp))
        Box(
            modifier = Modifier
                .width(48.dp)
                .height(4.dp)
                .clip(CircleShape)
                .background(IxhPrimaryYellow)
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "Panduan desain visual dan komponen UI untuk menjaga konsistensi, identitas, dan kualitas pengalaman pengguna IX-H HUB 1.0.",
            style = MaterialTheme.typography.bodyMedium,
            color = IxhTextSecondary
        )

        Spacer(modifier = Modifier.height(tokens.spacing.space12))

        // Dedicated Stage 2 3D-Glossy Mascot Slot Showcase inside Hero Header
        val slotShape = RoundedCornerShape(tokens.radius.large)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(slotShape)
                .background(
                    Brush.horizontalGradient(
                        listOf(
                            IxhLightYellow.copy(alpha = 0.65f),
                            IxhBackgroundCream.copy(alpha = 0.92f)
                        )
                    ),
                    slotShape
                )
                .border(1.5.dp, IxhCharcoal, slotShape)
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IxhBlobMascotSlot(
                slotStyle = IxhBlobSlotStyle.HERO_SHOWCASE_3D,
                targetExpression = BlobExpression.EXCITED,
                size = 64.dp
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(IxhPrimaryYellow)
                            .border(1.dp, IxhCharcoal, CircleShape)
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "Slot Maskot Tahap 2",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.ExtraBold,
                            color = IxhCharcoal
                        )
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "3D Glossy Ref",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = IxhTextSecondary
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Acuan bentuk & identitas kanonik (goggles & tunas daun). Siap menerima 10 ekspresi 3D Glossy pada Tahap 2.",
                    style = MaterialTheme.typography.bodySmall,
                    color = IxhCharcoal
                )
            }
        }
    }
}

@Composable
private fun DesignSystemSectionContainer(
    title: String,
    content: @Composable ColumnScope.() -> Unit
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
            .border(tokens.border.regular, IxhCharcoal.copy(alpha = 0.65f), cardShape)
            .padding(tokens.spacing.space16)
    ) {
        IxhSectionPillHeader(title = title)
        Spacer(modifier = Modifier.height(tokens.spacing.space12))
        content()
    }
}

@Composable
private fun SubCategoryPillLabel(text: String) {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(IxhLightYellow)
            .padding(horizontal = 12.dp, vertical = 3.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
            color = IxhCharcoal
        )
    }
}

@Composable
private fun RadiusSpecimenItem(
    title: String,
    subtitle: String,
    radius: Dp,
    isWidePill: Boolean = false
) {
    val shape = RoundedCornerShape(radius)
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .width(if (isWidePill) 72.dp else 54.dp)
                .height(50.dp)
                .clip(shape)
                .background(IxhBackgroundCream, shape)
                .border(2.dp, IxhCharcoal, shape)
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
            color = IxhCharcoal
        )
        Text(
            text = subtitle,
            style = MaterialTheme.typography.labelMedium,
            color = IxhCharcoal
        )
    }
}

@Composable
private fun ElevationSpecimenItem(
    title: String,
    subtitle: String,
    level: IxhElevationLevel
) {
    val shape = RoundedCornerShape(14.dp)
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .ixhTactileShadow(
                    level = level,
                    cornerRadius = 14.dp,
                    softShadow = level == IxhElevationLevel.HIGH
                )
                .clip(shape)
                .background(IxhSurfacePrimary, shape)
                .border(1.5.dp, IxhMutedBorder, shape)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
            color = IxhCharcoal
        )
        Text(
            text = subtitle,
            style = MaterialTheme.typography.labelMedium,
            color = IxhCharcoal
        )
    }
}
