package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Assignment
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.outlined.Login
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Campaign
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Inbox
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.SmartToy
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material.icons.outlined.WarningAmber
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.R
import com.example.ui.theme.IxhBackgroundCream
import com.example.ui.theme.IxhCharcoal
import kotlin.math.hypot
import com.example.ui.theme.IxhElevationLevel
import com.example.ui.theme.IxhError
import com.example.ui.theme.IxhErrorBg
import com.example.ui.theme.IxhErrorText
import com.example.ui.theme.IxhInfo
import com.example.ui.theme.IxhInfoBg
import com.example.ui.theme.IxhInfoText
import com.example.ui.theme.IxhLightYellow
import com.example.ui.theme.IxhMutedBorder
import com.example.ui.theme.IxhPrimaryYellow
import com.example.ui.theme.IxhSecondaryYellow
import com.example.ui.theme.IxhSuccess
import com.example.ui.theme.IxhSuccessBg
import com.example.ui.theme.IxhSuccessText
import com.example.ui.theme.IxhSurfacePrimary
import com.example.ui.theme.IxhSurfaceSecondary
import com.example.ui.theme.IxhTextSecondary
import com.example.ui.theme.IxhWarning
import com.example.ui.theme.IxhWarningBg
import com.example.ui.theme.IxhWarningText
import com.example.ui.theme.LocalIxhDesign

// ============================================================================
// 1. TACTILE SHADOW & DASHED BORDER MODIFIERS (2 GB RAM Optimized, Single-Pass)
// ============================================================================

/**
 * Tactile Shadow Modifier sesuai referensi_desain_system.png ("Elevation / Shadow"):
 * - Low (2 dp): shadow tactile tajam untuk chip/input/kartu ringan
 * - Medium (4 dp): shadow tactile standar untuk tombol & kartu utama
 * - High (8 dp): shadow berlapis lembut untuk modal/floating card
 */
fun Modifier.ixhTactileShadow(
    level: IxhElevationLevel = IxhElevationLevel.LOW,
    cornerRadius: Dp = 16.dp,
    shadowColor: Color = IxhCharcoal,
    softShadow: Boolean = false
): Modifier = if (level == IxhElevationLevel.NONE) {
    this
} else {
    this.drawBehind {
        val rPx = cornerRadius.toPx()
        val dy = level.offsetDp.toPx()
        val dx = (dy * 0.52f).coerceAtLeast(1.6.dp.toPx())
        if (softShadow || level == IxhElevationLevel.HIGH) {
            drawRoundRect(
                color = shadowColor.copy(alpha = 0.22f),
                topLeft = Offset(dx * 0.85f, dy * 1.15f),
                size = Size(size.width, size.height),
                cornerRadius = CornerRadius(rPx, rPx)
            )
            drawRoundRect(
                color = IxhCharcoal,
                topLeft = Offset(dx * 0.55f, dy * 0.72f),
                size = Size(size.width, size.height),
                cornerRadius = CornerRadius(rPx, rPx)
            )
        } else {
            drawRoundRect(
                color = shadowColor,
                topLeft = Offset(dx, dy),
                size = Size(size.width, size.height),
                cornerRadius = CornerRadius(rPx, rPx)
            )
        }
    }
}

/**
 * Background Gradient + Organic Yellow Wave + Elegant Halftone Dots sesuai referensi_desain_system.png.
 * Menggabungkan gradasi halus Kuning (#FFF0B8 / #FFD95A) ke Cream (#FDF8EC),
 * organic yellow wave yang menyatu lembut dengan gradasi, serta halftone dots
 * dengan ukuran dan transparansi proporsional (hemat RAM 2 GB, single-pass drawBehind).
 */
fun Modifier.ixhBrandGradientBackground(
    isHeroCard: Boolean = false
): Modifier = this.drawBehind {
    val w = size.width
    val h = size.height

    // 1. Base smooth gradient: Yellow-tinted Cream -> Warm Cream (#FDF8EC)
    val baseGradient = if (isHeroCard) {
        Brush.linearGradient(
            colors = listOf(
                IxhLightYellow.copy(alpha = 0.72f),
                Color(0xFFFFF8DF),
                IxhBackgroundCream
            ),
            start = Offset(0f, 0f),
            end = Offset(w * 0.95f, h)
        )
    } else {
        Brush.verticalGradient(
            colorStops = arrayOf(
                0.0f to Color(0xFFFFF3C4),
                0.28f to IxhBackgroundCream,
                0.76f to IxhBackgroundCream,
                1.0f to Color(0xFFFFF4CC)
            )
        )
    }
    drawRect(brush = baseGradient)

    // 2. Soft radial golden glow in top-left so the organic wave blends seamlessly into the cream
    drawCircle(
        brush = Brush.radialGradient(
            colors = listOf(
                IxhSecondaryYellow.copy(alpha = if (isHeroCard) 0.45f else 0.38f),
                IxhLightYellow.copy(alpha = 0.18f),
                Color.Transparent
            ),
            center = Offset(w * 0.08f, h * 0.04f),
            radius = w * (if (isHeroCard) 0.55f else 0.65f)
        ),
        center = Offset(w * 0.08f, h * 0.04f),
        radius = w * (if (isHeroCard) 0.55f else 0.65f)
    )

    // 3. Bold Organic Yellow Wave (Top-Left) with crisp Charcoal contour accent
    val topWavePath = Path().apply {
        moveTo(0f, 0f)
        lineTo(w * (if (isHeroCard) 0.56f else 0.52f), 0f)
        cubicTo(
            w * 0.42f, h * (if (isHeroCard) 0.28f else 0.08f),
            w * 0.18f, h * (if (isHeroCard) 0.36f else 0.11f),
            0f, h * (if (isHeroCard) 0.58f else 0.16f)
        )
        close()
    }
    drawPath(
        path = topWavePath,
        brush = Brush.linearGradient(
            colors = listOf(
                IxhPrimaryYellow.copy(alpha = if (isHeroCard) 0.72f else 0.78f),
                IxhSecondaryYellow.copy(alpha = if (isHeroCard) 0.44f else 0.48f),
                IxhLightYellow.copy(alpha = 0.14f)
            ),
            start = Offset(0f, 0f),
            end = Offset(w * 0.48f, h * (if (isHeroCard) 0.54f else 0.15f))
        )
    )
    val topWaveEdge = Path().apply {
        moveTo(w * (if (isHeroCard) 0.56f else 0.52f), 0f)
        cubicTo(
            w * 0.42f, h * (if (isHeroCard) 0.28f else 0.08f),
            w * 0.18f, h * (if (isHeroCard) 0.36f else 0.11f),
            0f, h * (if (isHeroCard) 0.58f else 0.16f)
        )
    }
    drawPath(
        path = topWaveEdge,
        color = IxhCharcoal.copy(alpha = if (isHeroCard) 0.18f else 0.14f),
        style = Stroke(width = 1.8.dp.toPx(), cap = StrokeCap.Round)
    )

    // 4. Secondary Organic Yellow Wave (Bottom-Right & Bottom-Left) for dynamic diagonal flow
    val bottomWavePath = Path().apply {
        moveTo(0f, h * (if (isHeroCard) 0.70f else 0.86f))
        cubicTo(
            w * 0.22f, h * (if (isHeroCard) 0.78f else 0.90f),
            w * 0.36f, h * (if (isHeroCard) 0.92f else 0.96f),
            w * (if (isHeroCard) 0.52f else 0.58f), h
        )
        lineTo(0f, h)
        close()
    }
    drawPath(
        path = bottomWavePath,
        brush = Brush.linearGradient(
            colors = listOf(
                IxhPrimaryYellow.copy(alpha = if (isHeroCard) 0.48f else 0.58f),
                IxhSecondaryYellow.copy(alpha = 0.25f),
                Color.Transparent
            ),
            start = Offset(0f, h),
            end = Offset(w * 0.50f, h * (if (isHeroCard) 0.70f else 0.86f))
        )
    )

    // 5. Elegant Halftone Dots (Top-Right & Bottom-Left) with smooth size & alpha attenuation
    val spacingPx = (if (isHeroCard) 11.dp else 13.dp).toPx()
    val maxRadiusPx = (if (isHeroCard) 2.2.dp else 2.6.dp).toPx()
    val cols = if (isHeroCard) 6 else 7
    val rows = if (isHeroCard) 5 else 6

    // Top-right halftone cluster fading smoothly inward
    val anchorTopRightX = w - 12.dp.toPx()
    val anchorTopRightY = 12.dp.toPx()
    val maxDistTR = hypot(cols * spacingPx, rows * spacingPx).coerceAtLeast(1f)
    for (r in 0 until rows) {
        for (c in 0 until cols) {
            // Stagger every other row slightly for authentic halftone matrix feel
            val staggerX = if (r % 2 == 1) spacingPx * 0.35f else 0f
            val cx = anchorTopRightX - c * spacingPx - staggerX
            val cy = anchorTopRightY + r * spacingPx
            val dist = hypot(anchorTopRightX - cx, cy - anchorTopRightY)
            val norm = (1f - (dist / maxDistTR)).coerceIn(0f, 1f)
            if (norm > 0.12f) {
                val dotRadius = (maxRadiusPx * (0.35f + 0.65f * norm))
                val dotAlpha = (if (isHeroCard) 0.28f else 0.34f) * norm
                drawCircle(
                    color = IxhPrimaryYellow.copy(alpha = dotAlpha),
                    radius = dotRadius,
                    center = Offset(cx, cy)
                )
            }
        }
    }

    // Subtle bottom-left halftone accents
    val anchorBottomLeftX = 14.dp.toPx()
    val anchorBottomLeftY = h - 14.dp.toPx()
    for (r in 0 until (rows - 1)) {
        for (c in 0 until (cols - 1)) {
            val cx = anchorBottomLeftX + c * spacingPx
            val cy = anchorBottomLeftY - r * spacingPx
            val dist = hypot(cx - anchorBottomLeftX, anchorBottomLeftY - cy)
            val norm = (1f - (dist / maxDistTR)).coerceIn(0f, 1f)
            if (norm > 0.18f) {
                val dotRadius = (maxRadiusPx * (0.32f + 0.58f * norm))
                val dotAlpha = (if (isHeroCard) 0.22f else 0.26f) * norm
                drawCircle(
                    color = IxhPrimaryYellow.copy(alpha = dotAlpha),
                    radius = dotRadius,
                    center = Offset(cx, cy)
                )
            }
        }
    }
}

/**
 * Dashed Border Modifier untuk varian tombol "Ghost" pada referensi_desain_system.png.
 */
fun Modifier.ixhDashedBorder(
    strokeWidth: Dp = 1.5.dp,
    color: Color = IxhCharcoal,
    cornerRadius: Dp = 999.dp
): Modifier = this.drawBehind {
    val strokePx = strokeWidth.toPx()
    val halfStroke = strokePx / 2f
    val rPx = cornerRadius.toPx()
    drawRoundRect(
        color = color,
        topLeft = Offset(halfStroke, halfStroke),
        size = Size(size.width - strokePx, size.height - strokePx),
        cornerRadius = CornerRadius(rPx, rPx),
        style = Stroke(
            width = strokePx,
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(12f, 9f), 0f)
        )
    )
}

// ============================================================================
// 2. BUTTON SYSTEM (Primary, Secondary, Ghost, Outlined, Icon Button)
// ============================================================================

enum class IxhButtonVariant {
    PRIMARY,
    SECONDARY,
    GHOST,
    OUTLINED
}

@Composable
fun IxhDsButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: IxhButtonVariant = IxhButtonVariant.PRIMARY,
    leadingIcon: ImageVector? = null,
    dualToneIcon: IxhSystemIconType? = null,
    trailingIcon: ImageVector? = null,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    isPill: Boolean = true,
    testTag: String? = null
) {
    val tokens = LocalIxhDesign.current
    val radiusDp = if (isPill) tokens.radius.full else tokens.radius.medium
    val shape = RoundedCornerShape(radiusDp)

    val bgColor = when {
        !enabled -> IxhSurfaceSecondary
        variant == IxhButtonVariant.PRIMARY -> IxhPrimaryYellow
        variant == IxhButtonVariant.SECONDARY -> IxhSurfacePrimary
        variant == IxhButtonVariant.GHOST -> IxhBackgroundCream
        variant == IxhButtonVariant.OUTLINED -> IxhSurfacePrimary
        else -> IxhPrimaryYellow
    }

    val contentColor = if (enabled) IxhCharcoal else IxhTextSecondary
    val hasTactileShadow = enabled && (variant == IxhButtonVariant.PRIMARY || variant == IxhButtonVariant.SECONDARY)

    val borderMod = when {
        !enabled -> Modifier.border(tokens.border.regular, IxhMutedBorder, shape)
        variant == IxhButtonVariant.PRIMARY -> Modifier.border(tokens.border.thick, IxhCharcoal, shape)
        variant == IxhButtonVariant.SECONDARY -> Modifier.border(tokens.border.regular, IxhCharcoal, shape)
        variant == IxhButtonVariant.OUTLINED -> Modifier.border(tokens.border.regular, IxhCharcoal, shape)
        variant == IxhButtonVariant.GHOST -> Modifier.ixhDashedBorder(tokens.border.regular, IxhCharcoal, radiusDp)
        else -> Modifier
    }

    val horizontalPad = if (dualToneIcon != null || leadingIcon != null) 14.dp else 18.dp

    Row(
        modifier = modifier
            .heightIn(min = 48.dp)
            .let { if (testTag != null) it.testTag(testTag) else it }
            .ixhTactileShadow(
                level = if (hasTactileShadow) IxhElevationLevel.LOW else IxhElevationLevel.NONE,
                cornerRadius = radiusDp
            )
            .clip(shape)
            .background(bgColor, shape)
            .then(borderMod)
            .clickable(enabled = enabled && !isLoading, onClick = onClick)
            .padding(horizontal = horizontalPad, vertical = 10.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color = contentColor,
                strokeWidth = 2.dp,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
        } else if (dualToneIcon != null) {
            IxhDualToneSystemIcon(
                iconType = dualToneIcon,
                size = 20.dp
            )
            Spacer(modifier = Modifier.width(8.dp))
        } else if (leadingIcon != null) {
            Icon(
                imageVector = leadingIcon,
                contentDescription = null,
                tint = contentColor,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
        }

        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = contentColor,
            maxLines = 1
        )

        if (trailingIcon != null && !isLoading) {
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = trailingIcon,
                contentDescription = null,
                tint = contentColor,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

@Composable
fun IxhIconButton(
    icon: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    dualToneIcon: IxhSystemIconType? = null,
    containerColor: Color = IxhPrimaryYellow,
    contentColor: Color = IxhCharcoal,
    size: Dp = 48.dp,
    testTag: String? = null
) {
    val tokens = LocalIxhDesign.current
    val shape = RoundedCornerShape(tokens.radius.medium)
    Box(
        modifier = modifier
            .size(size)
            .let { if (testTag != null) it.testTag(testTag) else it }
            .ixhTactileShadow(level = IxhElevationLevel.LOW, cornerRadius = tokens.radius.medium)
            .clip(shape)
            .background(containerColor, shape)
            .border(tokens.border.regular, IxhCharcoal, shape)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        if (dualToneIcon != null) {
            IxhDualToneSystemIcon(
                iconType = dualToneIcon,
                size = 22.dp
            )
        } else {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = contentColor,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

// ============================================================================
// 3. TEXT FIELD & PASSWORD FIELD (Pill Rounded Input sesuai referensi_desain_system.png)
// ============================================================================

@Composable
fun IxhTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    leadingIcon: ImageVector = Icons.Outlined.Person,
    isValid: Boolean = false,
    errorMessage: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    testTag: String? = null
) {
    val tokens = LocalIxhDesign.current
    val shape = RoundedCornerShape(tokens.radius.full)
    val borderColor = when {
        errorMessage != null -> IxhError
        else -> IxhCharcoal
    }

    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 48.dp)
                .ixhTactileShadow(
                    level = IxhElevationLevel.LOW,
                    cornerRadius = tokens.radius.full,
                    shadowColor = IxhCharcoal.copy(alpha = 0.16f)
                )
                .clip(shape)
                .background(IxhSurfacePrimary, shape)
                .border(tokens.border.regular, borderColor, shape)
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = leadingIcon,
                contentDescription = null,
                tint = IxhCharcoal,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {
                if (value.isEmpty()) {
                    Text(
                        text = placeholder,
                        style = MaterialTheme.typography.bodyLarge,
                        color = IxhTextSecondary.copy(alpha = 0.75f)
                    )
                }
                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    singleLine = true,
                    textStyle = MaterialTheme.typography.bodyLarge.copy(color = IxhCharcoal),
                    cursorBrush = SolidColor(IxhCharcoal),
                    keyboardOptions = keyboardOptions,
                    modifier = Modifier
                        .fillMaxWidth()
                        .let { if (testTag != null) it.testTag(testTag) else it }
                )
            }
            if (isValid && errorMessage == null) {
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .size(22.dp)
                        .clip(CircleShape)
                        .background(IxhSuccess)
                        .border(1.2.dp, IxhCharcoal, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Check,
                        contentDescription = "Input Valid",
                        tint = Color.White,
                        modifier = Modifier.size(13.dp)
                    )
                }
            }
        }
        if (errorMessage != null) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = errorMessage,
                style = MaterialTheme.typography.labelMedium,
                color = IxhError,
                modifier = Modifier.padding(start = 12.dp)
            )
        }
    }
}

@Composable
fun IxhPasswordField(
    value: String,
    onValueChange: (String) -> Unit,
    passwordVisible: Boolean,
    onTogglePasswordVisible: () -> Unit,
    placeholder: String = "Password",
    modifier: Modifier = Modifier,
    errorMessage: String? = null,
    testTag: String? = null,
    toggleTestTag: String? = null
) {
    val tokens = LocalIxhDesign.current
    val shape = RoundedCornerShape(tokens.radius.full)
    val borderColor = if (errorMessage != null) IxhError else IxhCharcoal

    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 48.dp)
                .ixhTactileShadow(
                    level = IxhElevationLevel.LOW,
                    cornerRadius = tokens.radius.full,
                    shadowColor = IxhCharcoal.copy(alpha = 0.16f)
                )
                .clip(shape)
                .background(IxhSurfacePrimary, shape)
                .border(tokens.border.regular, borderColor, shape)
                .padding(start = 16.dp, end = 6.dp, top = 4.dp, bottom = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.Lock,
                contentDescription = null,
                tint = IxhCharcoal,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {
                if (value.isEmpty()) {
                    Text(
                        text = placeholder,
                        style = MaterialTheme.typography.bodyLarge,
                        color = IxhTextSecondary.copy(alpha = 0.75f)
                    )
                }
                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    singleLine = true,
                    textStyle = MaterialTheme.typography.bodyLarge.copy(color = IxhCharcoal),
                    cursorBrush = SolidColor(IxhCharcoal),
                    visualTransformation = if (passwordVisible) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .let { if (testTag != null) it.testTag(testTag) else it }
                )
            }
            IconButton(
                onClick = onTogglePasswordVisible,
                modifier = Modifier
                    .size(40.dp)
                    .let { if (toggleTestTag != null) it.testTag(toggleTestTag) else it }
            ) {
                Icon(
                    imageVector = if (passwordVisible) {
                        Icons.Outlined.Visibility
                    } else {
                        Icons.Outlined.VisibilityOff
                    },
                    contentDescription = if (passwordVisible) {
                        "Sembunyikan password"
                    } else {
                        "Tampilkan password"
                    },
                    tint = IxhCharcoal,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
        if (errorMessage != null) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = errorMessage,
                style = MaterialTheme.typography.labelMedium,
                color = IxhError,
                modifier = Modifier.padding(start = 12.dp)
            )
        }
    }
}

// ============================================================================
// 4. TOGGLES & CHECKBOX (Sesuai referensi_desain_system.png)
// ============================================================================

@Composable
fun IxhPillToggle(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    testTag: String? = null
) {
    val shape = CircleShape
    val trackColor = if (checked) IxhPrimaryYellow else IxhMutedBorder
    val borderColor = if (checked) IxhCharcoal else IxhTextSecondary.copy(alpha = 0.35f)
    val thumbColor = if (checked) IxhCharcoal else IxhSurfacePrimary

    Box(
        modifier = modifier
            .width(54.dp)
            .height(30.dp)
            .let { if (testTag != null) it.testTag(testTag) else it }
            .clip(shape)
            .background(trackColor, shape)
            .border(if (checked) 2.dp else 1.5.dp, borderColor, shape)
            .clickable(enabled = enabled) { onCheckedChange(!checked) }
            .padding(horizontal = 4.dp),
        contentAlignment = if (checked) Alignment.CenterEnd else Alignment.CenterStart
    ) {
        Box(
            modifier = Modifier
                .size(20.dp)
                .clip(CircleShape)
                .background(thumbColor, CircleShape)
        )
    }
}

@Composable
fun IxhNeubrutalCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    testTag: String? = null
) {
    val boxShape = RoundedCornerShape(6.dp)
    Row(
        modifier = modifier
            .heightIn(min = 36.dp)
            .let { if (testTag != null) it.testTag(testTag) else it }
            .clickable(enabled = enabled) { onCheckedChange(!checked) }
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(22.dp)
                .clip(boxShape)
                .background(if (checked) IxhPrimaryYellow else IxhSurfacePrimary, boxShape)
                .border(2.dp, IxhCharcoal, boxShape),
            contentAlignment = Alignment.Center
        ) {
            if (checked) {
                Icon(
                    imageVector = Icons.Outlined.Check,
                    contentDescription = null,
                    tint = IxhCharcoal,
                    modifier = Modifier.size(15.dp)
                )
            }
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold,
            color = IxhCharcoal
        )
    }
}

// ============================================================================
// 5. CHIP / BADGE & STATUS INDICATOR (Sesuai referensi_desain_system.png)
// ============================================================================

enum class IxhChipStyle {
    BRAND_YELLOW, // "IX-H" (#FFC928 + border #1C1B1F)
    SUCCESS,      // "Aktif" (#DDF5E8 + border #35A66B)
    ERROR_SOFT,   // "1 hari lagi" (#FDE2E4 + border #E34B4B)
    INFO_SOFT,    // "Terbaca" (#DCEBFA + border #4A91D9)
    WARNING_SOFT; // Warning badge (#FFF0C7 + border #F2A93B)

    companion object {
        val WARNING: IxhChipStyle get() = WARNING_SOFT
    }
}

@Composable
fun IxhDsChip(
    text: String,
    style: IxhChipStyle = IxhChipStyle.BRAND_YELLOW,
    modifier: Modifier = Modifier
) {
    val (bg, fg, borderCol) = when (style) {
        IxhChipStyle.BRAND_YELLOW -> Triple(IxhPrimaryYellow, IxhCharcoal, IxhCharcoal)
        IxhChipStyle.SUCCESS -> Triple(IxhSuccessBg, IxhSuccessText, IxhSuccess)
        IxhChipStyle.ERROR_SOFT -> Triple(IxhErrorBg, IxhErrorText, IxhError.copy(alpha = 0.55f))
        IxhChipStyle.INFO_SOFT -> Triple(IxhInfoBg, IxhInfoText, IxhInfo.copy(alpha = 0.65f))
        IxhChipStyle.WARNING_SOFT -> Triple(IxhWarningBg, IxhWarningText, IxhWarning)
    }

    val shape = CircleShape
    Box(
        modifier = modifier
            .clip(shape)
            .background(bg, shape)
            .border(1.5.dp, borderCol, shape)
            .padding(horizontal = 12.dp, vertical = 5.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
            color = fg
        )
    }
}

@Composable
fun IxhStatusIndicator(
    label: String,
    color: Color = IxhSuccess,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .clip(CircleShape)
                .background(color, CircleShape)
                .border(1.2.dp, IxhCharcoal, CircleShape)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = IxhCharcoal
        )
    }
}

// ============================================================================
// 6. CARD / CONTAINER VARIANTS (Sesuai referensi_desain_system.png)
// ============================================================================

@Composable
fun IxhAttendanceMetricCard(
    title: String = "Kehadiran",
    valueText: String = "28 / 30",
    percentageText: String = "93%",
    progress: Float = 0.93f,
    modifier: Modifier = Modifier
) {
    val tokens = LocalIxhDesign.current
    val shape = RoundedCornerShape(tokens.radius.large)
    Column(
        modifier = modifier
            .ixhTactileShadow(
                level = IxhElevationLevel.LOW,
                cornerRadius = tokens.radius.large,
                shadowColor = IxhCharcoal.copy(alpha = 0.12f)
            )
            .clip(shape)
            .background(IxhSurfacePrimary, shape)
            .border(tokens.border.regular, IxhMutedBorder, shape)
            .padding(14.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelLarge,
            color = IxhCharcoal
        )
        Spacer(modifier = Modifier.height(6.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = valueText,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.ExtraBold,
                color = IxhCharcoal
            )
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(IxhSuccessBg)
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
                Text(
                    text = percentageText,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = IxhSuccessText
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(CircleShape)
                .background(IxhSurfaceSecondary)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(progress.coerceIn(0f, 1f))
                    .height(8.dp)
                    .clip(CircleShape)
                    .background(IxhSuccess)
            )
        }
    }
}

@Composable
fun IxhKasMetricCard(
    title: String = "Kas Kelas",
    amountText: String = "Rp680.000",
    statusText: String = "Terkonfirmasi",
    modifier: Modifier = Modifier
) {
    val tokens = LocalIxhDesign.current
    val shape = RoundedCornerShape(tokens.radius.large)
    Column(
        modifier = modifier
            .ixhTactileShadow(
                level = IxhElevationLevel.LOW,
                cornerRadius = tokens.radius.large,
                shadowColor = IxhCharcoal.copy(alpha = 0.14f)
            )
            .clip(shape)
            .background(IxhSurfacePrimary, shape)
            .border(tokens.border.regular, IxhCharcoal.copy(alpha = 0.45f), shape)
            .padding(14.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge,
                color = IxhCharcoal
            )
            // Dual-tone Coin Stack Icon matching referensi_desain_system.png
            Canvas(modifier = Modifier.size(22.dp)) {
                val w = size.width
                val h = size.height
                // Bottom coin
                drawOval(
                    color = IxhPrimaryYellow,
                    topLeft = Offset(w * 0.1f, h * 0.52f),
                    size = Size(w * 0.8f, h * 0.36f)
                )
                drawOval(
                    color = IxhCharcoal,
                    topLeft = Offset(w * 0.1f, h * 0.52f),
                    size = Size(w * 0.8f, h * 0.36f),
                    style = Stroke(width = 1.6.dp.toPx())
                )
                // Middle coin
                drawOval(
                    color = IxhSecondaryYellow,
                    topLeft = Offset(w * 0.1f, h * 0.32f),
                    size = Size(w * 0.8f, h * 0.36f)
                )
                drawOval(
                    color = IxhCharcoal,
                    topLeft = Offset(w * 0.1f, h * 0.32f),
                    size = Size(w * 0.8f, h * 0.36f),
                    style = Stroke(width = 1.6.dp.toPx())
                )
                // Top coin
                drawOval(
                    color = IxhPrimaryYellow,
                    topLeft = Offset(w * 0.1f, h * 0.12f),
                    size = Size(w * 0.8f, h * 0.36f)
                )
                drawOval(
                    color = IxhCharcoal,
                    topLeft = Offset(w * 0.1f, h * 0.12f),
                    size = Size(w * 0.8f, h * 0.36f),
                    style = Stroke(width = 1.6.dp.toPx())
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = amountText,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.ExtraBold,
            color = IxhCharcoal
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = statusText,
            style = MaterialTheme.typography.bodySmall,
            color = IxhTextSecondary
        )
    }
}

@Composable
fun IxhAnnouncementHighlightCard(
    title: String = "Pengumuman",
    subtitle: String = "Rapat kelas minggu depan!",
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val tokens = LocalIxhDesign.current
    val shape = RoundedCornerShape(tokens.radius.large)
    val bgBrush = Brush.horizontalGradient(
        colors = listOf(IxhSurfacePrimary, IxhInfoBg.copy(alpha = 0.85f))
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .ixhTactileShadow(
                level = IxhElevationLevel.LOW,
                cornerRadius = tokens.radius.large
            )
            .clip(shape)
            .background(bgBrush, shape)
            .border(tokens.border.regular, IxhCharcoal, shape)
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(IxhBackgroundCream)
                .border(1.5.dp, IxhCharcoal, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            IxhDualToneSystemIcon(
                iconType = IxhSystemIconType.PENGUMUMAN,
                size = 24.dp
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = IxhCharcoal
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = IxhCharcoal
            )
        }
        Icon(
            imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
            contentDescription = "Lihat Detail",
            tint = IxhCharcoal,
            modifier = Modifier.size(22.dp)
        )
    }
}

// ============================================================================
// 7. ICON SYSTEM (10 Dual-Tone Vector Icons sesuai referensi_desain_system.png)
//    - Bold Rounded Dual-Tone: Charcoal (#1C1B1F) outline + 30–40% Primary Yellow (#FFC928)
//    - Tanpa lingkaran kuning besar atau shadow lebar di belakang ikon
//    - Proporsional pada ukuran normal (34.dp) maupun kecil (18.dp–24.dp)
// ============================================================================

enum class IxhSystemIconType(val label: String, val vectorIcon: ImageVector) {
    BERANDA("Beranda", Icons.Outlined.Home),
    ABSENSI("Absensi", Icons.Outlined.CheckCircle),
    KAS("Kas", Icons.Outlined.AccountBalanceWallet),
    JADWAL("Jadwal", Icons.Outlined.CalendarMonth),
    TUGAS("Tugas", Icons.AutoMirrored.Outlined.Assignment),
    PENGGUNA("Pengguna", Icons.Outlined.Group),
    PANEL_BOT("Panel Bot", Icons.Outlined.SmartToy),
    PENGUMUMAN("Pengumuman", Icons.Outlined.Campaign),
    PENGATURAN("Pengaturan", Icons.Outlined.Settings),
    INFO("Info", Icons.Outlined.Info);

    companion object {
        val NOTIFIKASI: IxhSystemIconType get() = PENGUMUMAN
        val PROFIL: IxhSystemIconType get() = PENGGUNA
        val TAGIHAN: IxhSystemIconType get() = KAS
        val BOT: IxhSystemIconType get() = PANEL_BOT
        val ADMIN: IxhSystemIconType get() = PENGATURAN
    }
}

@Composable
fun IxhSystemIconTile(
    iconType: IxhSystemIconType,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    Column(
        modifier = modifier
            .let { if (onClick != null) it.clickable(onClick = onClick) else it }
            .padding(vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IxhDualToneSystemIcon(
            iconType = iconType,
            size = 34.dp
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = iconType.label,
            style = MaterialTheme.typography.labelSmall,
            fontSize = 10.sp,
            letterSpacing = (-0.15).sp,
            fontWeight = FontWeight.SemiBold,
            color = IxhCharcoal,
            textAlign = TextAlign.Center,
            maxLines = 1,
            softWrap = false,
            overflow = TextOverflow.Visible
        )
    }
}

/**
 * Unified Bold Rounded Dual-Tone Icon System (10 Ikon Navigasi & Fitur Utama IX-H HUB 1.0).
 * Seluruh ikon dirancang dalam satu keluarga desain yang sama:
 * - Ketebalan garis seragam & proporsional terhadap ukuran (2.55.dp @ 34.dp, skala otomatis untuk 18–24.dp)
 * - Proporsi optik seragam (optical bounding box 72% di dalam kanvas)
 * - Gaya Bold Rounded Dual-Tone: kombinasi outline Charcoal (#1C1B1F) dan isian kuning
 *   Primary Yellow (#FFC928) yang jelas & dominan (~30–40% area visual di dalam siluet ikon)
 * - Tanpa lingkaran kuning besar atau shadow di luar/belakang ikon
 */
@Composable
fun IxhDualToneSystemIcon(
    iconType: IxhSystemIconType,
    modifier: Modifier = Modifier,
    size: Dp = 34.dp
) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        val s = (size.value / 34f).coerceIn(0.62f, 1.30f)
        val strokePx = (2.95f * s).dp.toPx().coerceAtLeast(1.95.dp.toPx())
        val boldStroke = Stroke(
            width = strokePx,
            cap = StrokeCap.Round,
            join = StrokeJoin.Round
        )

        when (iconType) {
            IxhSystemIconType.BERANDA -> {
                val rDoor = CornerRadius((3f * s).dp.toPx(), (3f * s).dp.toPx())
                // Closed unified house silhouette (roof + eaves + walls)
                val houseOutline = Path().apply {
                    moveTo(w * 0.50f, h * 0.15f)
                    lineTo(w * 0.86f, h * 0.45f)
                    lineTo(w * 0.77f, h * 0.45f)
                    lineTo(w * 0.77f, h * 0.83f)
                    lineTo(w * 0.23f, h * 0.83f)
                    lineTo(w * 0.23f, h * 0.45f)
                    lineTo(w * 0.14f, h * 0.45f)
                    close()
                }
                // 1. Base fill: Primary Yellow (#FFC928) across the house interior
                drawPath(path = houseOutline, color = IxhPrimaryYellow)
                // 2. Top-left roof highlight in Cream (#FDF8EC) so yellow occupies ~40% of the visual area
                val roofCreamHighlight = Path().apply {
                    moveTo(w * 0.50f, h * 0.17f)
                    lineTo(w * 0.78f, h * 0.41f)
                    lineTo(w * 0.25f, h * 0.46f)
                    lineTo(w * 0.17f, h * 0.45f)
                    close()
                }
                drawPath(path = roofCreamHighlight, color = IxhBackgroundCream)
                // 3. Cream door cutout in the center
                drawRoundRect(
                    color = IxhBackgroundCream,
                    topLeft = Offset(w * 0.39f, h * 0.53f),
                    size = Size(w * 0.22f, h * 0.30f),
                    cornerRadius = rDoor
                )
                // 4. Bold rounded Charcoal outlines
                drawPath(path = houseOutline, color = IxhCharcoal, style = boldStroke)
                drawRoundRect(
                    color = IxhCharcoal,
                    topLeft = Offset(w * 0.39f, h * 0.53f),
                    size = Size(w * 0.22f, h * 0.30f),
                    cornerRadius = rDoor,
                    style = boldStroke
                )
            }

            IxhSystemIconType.ABSENSI -> {
                val center = Offset(w * 0.50f, h * 0.50f)
                val radius = w * 0.35f
                // 1. Primary Yellow base across the full circle
                drawCircle(color = IxhPrimaryYellow, radius = radius, center = center)
                // 2. Upper-left Cream dual-tone cut (~58% Cream, leaving a bold ~42% Yellow lower-right body)
                drawArc(
                    color = IxhBackgroundCream,
                    startAngle = 145f,
                    sweepAngle = 195f,
                    useCenter = true,
                    topLeft = Offset(center.x - radius, center.y - radius),
                    size = Size(radius * 2f, radius * 2f)
                )
                // 3. Bold outer circle outline
                drawCircle(
                    color = IxhCharcoal,
                    radius = radius,
                    center = center,
                    style = boldStroke
                )
                // 4. Bold rounded checkmark
                val checkPath = Path().apply {
                    moveTo(w * 0.34f, h * 0.51f)
                    lineTo(w * 0.45f, h * 0.62f)
                    lineTo(w * 0.67f, h * 0.39f)
                }
                drawPath(path = checkPath, color = IxhCharcoal, style = boldStroke)
            }

            IxhSystemIconType.KAS -> {
                val rWallet = CornerRadius((5.5f * s).dp.toPx(), (5.5f * s).dp.toPx())
                val rInner = CornerRadius((5f * s).dp.toPx(), (5f * s).dp.toPx())
                val rClasp = CornerRadius((4.5f * s).dp.toPx(), (4.5f * s).dp.toPx())
                // 1. Cream upper wallet base
                drawRoundRect(
                    color = IxhBackgroundCream,
                    topLeft = Offset(w * 0.15f, h * 0.22f),
                    size = Size(w * 0.66f, h * 0.56f),
                    cornerRadius = rWallet
                )
                // 2. Dominant lower half yellow fill (~40% of wallet body)
                drawRoundRect(
                    color = IxhPrimaryYellow,
                    topLeft = Offset(w * 0.16f, h * 0.44f),
                    size = Size(w * 0.64f, h * 0.33f),
                    cornerRadius = rInner
                )
                // 3. Bold wallet body outline
                drawRoundRect(
                    color = IxhCharcoal,
                    topLeft = Offset(w * 0.15f, h * 0.22f),
                    size = Size(w * 0.66f, h * 0.56f),
                    cornerRadius = rWallet,
                    style = boldStroke
                )
                // 4. Clasp capsule filled with yellow + bold Charcoal outline
                drawRoundRect(
                    color = IxhPrimaryYellow,
                    topLeft = Offset(w * 0.56f, h * 0.38f),
                    size = Size(w * 0.30f, h * 0.24f),
                    cornerRadius = rClasp
                )
                drawRoundRect(
                    color = IxhCharcoal,
                    topLeft = Offset(w * 0.56f, h * 0.38f),
                    size = Size(w * 0.30f, h * 0.24f),
                    cornerRadius = rClasp,
                    style = boldStroke
                )
                // 5. Bold clasp dot
                drawCircle(
                    color = IxhCharcoal,
                    radius = (2.1f * s).dp.toPx(),
                    center = Offset(w * 0.68f, h * 0.50f)
                )
            }

            IxhSystemIconType.JADWAL -> {
                val rCal = CornerRadius((5.5f * s).dp.toPx(), (5.5f * s).dp.toPx())
                val rBand = CornerRadius((4.5f * s).dp.toPx(), (4.5f * s).dp.toPx())
                // 1. Cream calendar body
                drawRoundRect(
                    color = IxhBackgroundCream,
                    topLeft = Offset(w * 0.16f, h * 0.22f),
                    size = Size(w * 0.68f, h * 0.61f),
                    cornerRadius = rCal
                )
                // 2. Dominant yellow top header band + lower-right active calendar block (~38% total area)
                drawRoundRect(
                    color = IxhPrimaryYellow,
                    topLeft = Offset(w * 0.17f, h * 0.23f),
                    size = Size(w * 0.66f, h * 0.19f),
                    cornerRadius = rBand
                )
                drawRoundRect(
                    color = IxhPrimaryYellow,
                    topLeft = Offset(w * 0.46f, h * 0.61f),
                    size = Size(w * 0.36f, h * 0.21f),
                    cornerRadius = rBand
                )
                // 3. Calendar outer frame
                drawRoundRect(
                    color = IxhCharcoal,
                    topLeft = Offset(w * 0.16f, h * 0.22f),
                    size = Size(w * 0.68f, h * 0.61f),
                    cornerRadius = rCal,
                    style = boldStroke
                )
                // 4. Header divider line
                drawLine(
                    color = IxhCharcoal,
                    start = Offset(w * 0.16f, h * 0.42f),
                    end = Offset(w * 0.84f, h * 0.42f),
                    strokeWidth = strokePx
                )
                // 5. 2 bold rounded binding rings at top
                drawLine(
                    color = IxhCharcoal,
                    start = Offset(w * 0.34f, h * 0.14f),
                    end = Offset(w * 0.34f, h * 0.28f),
                    strokeWidth = strokePx,
                    cap = StrokeCap.Round
                )
                drawLine(
                    color = IxhCharcoal,
                    start = Offset(w * 0.66f, h * 0.14f),
                    end = Offset(w * 0.66f, h * 0.28f),
                    strokeWidth = strokePx,
                    cap = StrokeCap.Round
                )
                // 6. 6 bold rounded date grid dots
                val dotR = (1.95f * s).dp.toPx()
                for (row in 0..1) {
                    for (col in 0..2) {
                        drawCircle(
                            color = IxhCharcoal,
                            radius = dotR,
                            center = Offset(
                                x = w * (0.33f + col * 0.17f),
                                y = h * (0.55f + row * 0.14f)
                            )
                        )
                    }
                }
            }

            IxhSystemIconType.TUGAS -> {
                val rDoc = CornerRadius((5.5f * s).dp.toPx(), (5.5f * s).dp.toPx())
                val rBand = CornerRadius((4.5f * s).dp.toPx(), (4.5f * s).dp.toPx())
                // 1. Cream document body
                drawRoundRect(
                    color = IxhBackgroundCream,
                    topLeft = Offset(w * 0.19f, h * 0.17f),
                    size = Size(w * 0.62f, h * 0.66f),
                    cornerRadius = rDoc
                )
                // 2. Dominant yellow top header & lower-right dual-tone band (~38% of document area)
                drawRoundRect(
                    color = IxhPrimaryYellow,
                    topLeft = Offset(w * 0.20f, h * 0.18f),
                    size = Size(w * 0.60f, h * 0.18f),
                    cornerRadius = rBand
                )
                drawRoundRect(
                    color = IxhPrimaryYellow,
                    topLeft = Offset(w * 0.20f, h * 0.64f),
                    size = Size(w * 0.60f, h * 0.18f),
                    cornerRadius = rBand
                )
                // 3. Bold outer document frame
                drawRoundRect(
                    color = IxhCharcoal,
                    topLeft = Offset(w * 0.19f, h * 0.17f),
                    size = Size(w * 0.62f, h * 0.66f),
                    cornerRadius = rDoc,
                    style = boldStroke
                )
                // 4. 3 bold rounded horizontal task lines
                for (i in 0..2) {
                    val y = h * (0.43f + i * 0.13f)
                    drawLine(
                        color = IxhCharcoal,
                        start = Offset(w * 0.33f, y),
                        end = Offset(w * 0.67f, y),
                        strokeWidth = strokePx,
                        cap = StrokeCap.Round
                    )
                }
            }

            IxhSystemIconType.PENGGUNA -> {
                // 1. Secondary user (right) shoulder capsule + head filled 100% with Primary Yellow
                val secondaryShoulders = Path().apply {
                    moveTo(w * 0.54f, h * 0.80f)
                    lineTo(w * 0.86f, h * 0.80f)
                    cubicTo(
                        w * 0.86f, h * 0.58f,
                        w * 0.54f, h * 0.58f,
                        w * 0.54f, h * 0.80f
                    )
                    close()
                }
                drawPath(path = secondaryShoulders, color = IxhPrimaryYellow)
                drawPath(path = secondaryShoulders, color = IxhCharcoal, style = boldStroke)

                drawCircle(
                    color = IxhPrimaryYellow,
                    radius = w * 0.115f,
                    center = Offset(w * 0.67f, h * 0.36f)
                )
                drawCircle(
                    color = IxhCharcoal,
                    radius = w * 0.115f,
                    center = Offset(w * 0.67f, h * 0.36f),
                    style = boldStroke
                )

                // 2. Primary user (left/front) shoulder capsule with dominant yellow lower body (~40% total)
                val primaryShoulders = Path().apply {
                    moveTo(w * 0.14f, h * 0.80f)
                    lineTo(w * 0.62f, h * 0.80f)
                    cubicTo(
                        w * 0.62f, h * 0.54f,
                        w * 0.14f, h * 0.54f,
                        w * 0.14f, h * 0.80f
                    )
                    close()
                }
                drawPath(path = primaryShoulders, color = IxhPrimaryYellow)
                // Upper-left Cream highlight inside primary shoulder capsule
                drawRoundRect(
                    color = IxhBackgroundCream,
                    topLeft = Offset(w * 0.19f, h * 0.60f),
                    size = Size(w * 0.32f, h * 0.08f),
                    cornerRadius = CornerRadius((4f * s).dp.toPx(), (4f * s).dp.toPx())
                )
                drawPath(path = primaryShoulders, color = IxhCharcoal, style = boldStroke)

                // Primary user head with lower-right yellow crescent + Cream top-left
                val pHeadCenter = Offset(w * 0.38f, h * 0.33f)
                val pHeadR = w * 0.135f
                drawCircle(color = IxhPrimaryYellow, radius = pHeadR, center = pHeadCenter)
                drawArc(
                    color = IxhBackgroundCream,
                    startAngle = 140f,
                    sweepAngle = 200f,
                    useCenter = true,
                    topLeft = Offset(pHeadCenter.x - pHeadR, pHeadCenter.y - pHeadR),
                    size = Size(pHeadR * 2f, pHeadR * 2f)
                )
                drawCircle(
                    color = IxhCharcoal,
                    radius = pHeadR,
                    center = pHeadCenter,
                    style = boldStroke
                )
            }

            IxhSystemIconType.PANEL_BOT -> {
                val rBot = CornerRadius((7f * s).dp.toPx(), (7f * s).dp.toPx())
                val rBrow = CornerRadius((5f * s).dp.toPx(), (5f * s).dp.toPx())
                // 1. Dominant Primary Yellow fill across bot head (~40% visual area)
                drawRoundRect(
                    color = IxhPrimaryYellow,
                    topLeft = Offset(w * 0.19f, h * 0.31f),
                    size = Size(w * 0.62f, h * 0.49f),
                    cornerRadius = rBot
                )
                // 2. Top-left Cream brow highlight band for crisp dual-tone balance
                drawRoundRect(
                    color = IxhBackgroundCream,
                    topLeft = Offset(w * 0.21f, h * 0.33f),
                    size = Size(w * 0.58f, h * 0.13f),
                    cornerRadius = rBrow
                )
                // 3. Bold bot head outline
                drawRoundRect(
                    color = IxhCharcoal,
                    topLeft = Offset(w * 0.19f, h * 0.31f),
                    size = Size(w * 0.62f, h * 0.49f),
                    cornerRadius = rBot,
                    style = boldStroke
                )
                // 4. Top antenna stem & yellow bulb
                drawLine(
                    color = IxhCharcoal,
                    start = Offset(w * 0.50f, h * 0.20f),
                    end = Offset(w * 0.50f, h * 0.31f),
                    strokeWidth = strokePx,
                    cap = StrokeCap.Round
                )
                drawCircle(
                    color = IxhPrimaryYellow,
                    radius = (3.2f * s).dp.toPx(),
                    center = Offset(w * 0.50f, h * 0.17f)
                )
                drawCircle(
                    color = IxhCharcoal,
                    radius = (3.2f * s).dp.toPx(),
                    center = Offset(w * 0.50f, h * 0.17f),
                    style = Stroke(width = (2.1f * s).dp.toPx())
                )
                // 5. Side ear tabs
                drawLine(
                    color = IxhCharcoal,
                    start = Offset(w * 0.12f, h * 0.55f),
                    end = Offset(w * 0.19f, h * 0.55f),
                    strokeWidth = strokePx,
                    cap = StrokeCap.Round
                )
                drawLine(
                    color = IxhCharcoal,
                    start = Offset(w * 0.81f, h * 0.55f),
                    end = Offset(w * 0.88f, h * 0.55f),
                    strokeWidth = strokePx,
                    cap = StrokeCap.Round
                )
                // 6. Bot eyes & rounded smile
                drawCircle(
                    color = IxhCharcoal,
                    radius = (2.1f * s).dp.toPx(),
                    center = Offset(w * 0.38f, h * 0.53f)
                )
                drawCircle(
                    color = IxhCharcoal,
                    radius = (2.1f * s).dp.toPx(),
                    center = Offset(w * 0.62f, h * 0.53f)
                )
                val smilePath = Path().apply {
                    moveTo(w * 0.42f, h * 0.64f)
                    cubicTo(
                        w * 0.46f, h * 0.70f,
                        w * 0.54f, h * 0.70f,
                        w * 0.58f, h * 0.64f
                    )
                }
                drawPath(path = smilePath, color = IxhCharcoal, style = boldStroke)
            }

            IxhSystemIconType.PENGUMUMAN -> {
                val rCap = CornerRadius((3f * s).dp.toPx(), (3f * s).dp.toPx())
                // 1. Bottom handle filled with Primary Yellow + bold Charcoal outline
                val handlePath = Path().apply {
                    moveTo(w * 0.27f, h * 0.56f)
                    lineTo(w * 0.31f, h * 0.79f)
                    lineTo(w * 0.43f, h * 0.79f)
                    lineTo(w * 0.40f, h * 0.59f)
                    close()
                }
                drawPath(path = handlePath, color = IxhPrimaryYellow)
                drawPath(path = handlePath, color = IxhCharcoal, style = boldStroke)

                // 2. Megaphone cone body with dominant Primary Yellow fill (~40% area) + upper Cream highlight
                val conePath = Path().apply {
                    moveTo(w * 0.26f, h * 0.39f)
                    lineTo(w * 0.66f, h * 0.21f)
                    lineTo(w * 0.66f, h * 0.73f)
                    lineTo(w * 0.26f, h * 0.57f)
                    close()
                }
                drawPath(path = conePath, color = IxhPrimaryYellow)
                val upperCreamStrip = Path().apply {
                    moveTo(w * 0.27f, h * 0.40f)
                    lineTo(w * 0.64f, h * 0.24f)
                    lineTo(w * 0.64f, h * 0.36f)
                    lineTo(w * 0.27f, h * 0.45f)
                    close()
                }
                drawPath(path = upperCreamStrip, color = IxhBackgroundCream)
                drawPath(path = conePath, color = IxhCharcoal, style = boldStroke)

                // 3. Left mouth cap filled with Primary Yellow
                drawRoundRect(
                    color = IxhPrimaryYellow,
                    topLeft = Offset(w * 0.15f, h * 0.39f),
                    size = Size(w * 0.12f, h * 0.18f),
                    cornerRadius = rCap
                )
                drawRoundRect(
                    color = IxhCharcoal,
                    topLeft = Offset(w * 0.15f, h * 0.39f),
                    size = Size(w * 0.12f, h * 0.18f),
                    cornerRadius = rCap,
                    style = boldStroke
                )

                // 4. Bold sound wave rays on the right
                drawLine(
                    color = IxhCharcoal,
                    start = Offset(w * 0.75f, h * 0.36f),
                    end = Offset(w * 0.85f, h * 0.30f),
                    strokeWidth = strokePx,
                    cap = StrokeCap.Round
                )
                drawLine(
                    color = IxhCharcoal,
                    start = Offset(w * 0.76f, h * 0.47f),
                    end = Offset(w * 0.86f, h * 0.47f),
                    strokeWidth = strokePx,
                    cap = StrokeCap.Round
                )
                drawLine(
                    color = IxhCharcoal,
                    start = Offset(w * 0.75f, h * 0.58f),
                    end = Offset(w * 0.85f, h * 0.64f),
                    strokeWidth = strokePx,
                    cap = StrokeCap.Round
                )
            }

            IxhSystemIconType.PENGATURAN -> {
                val cx = w * 0.50f
                val cy = h * 0.50f
                val teeth = 8
                val outerR = w * 0.37f
                val innerR = w * 0.27f
                // Continuous closed 8-tooth gear polygon path (bold rounded outline)
                val gearPath = Path().apply {
                    val stepDeg = 360.0 / (teeth * 2)
                    for (i in 0 until teeth * 2) {
                        val isTooth = (i % 2 == 0)
                        val r = if (isTooth) outerR else innerR
                        val a1 = Math.toRadians(i * stepDeg - (if (isTooth) 6.5 else 10.0))
                        val a2 = Math.toRadians(i * stepDeg + (if (isTooth) 6.5 else 10.0))
                        val p1x = cx + (r * kotlin.math.cos(a1)).toFloat()
                        val p1y = cy + (r * kotlin.math.sin(a1)).toFloat()
                        val p2x = cx + (r * kotlin.math.cos(a2)).toFloat()
                        val p2y = cy + (r * kotlin.math.sin(a2)).toFloat()
                        if (i == 0) moveTo(p1x, p1y) else lineTo(p1x, p1y)
                        lineTo(p2x, p2y)
                    }
                    close()
                }
                // Dominant Primary Yellow fill across the entire gear body (~40% visual area) + bold Charcoal outline
                drawPath(path = gearPath, color = IxhPrimaryYellow)
                drawPath(path = gearPath, color = IxhCharcoal, style = boldStroke)

                // Center hole with Cream fill + bold Charcoal outline
                drawCircle(
                    color = IxhBackgroundCream,
                    radius = w * 0.125f,
                    center = Offset(cx, cy)
                )
                drawCircle(
                    color = IxhCharcoal,
                    radius = w * 0.125f,
                    center = Offset(cx, cy),
                    style = boldStroke
                )
            }

            IxhSystemIconType.INFO -> {
                val center = Offset(w * 0.50f, h * 0.50f)
                val radius = w * 0.35f
                // 1. Primary Yellow base across the full circle
                drawCircle(color = IxhPrimaryYellow, radius = radius, center = center)
                // 2. Upper-left Cream dual-tone cut (~58% Cream, leaving a bold ~42% Yellow lower-right body)
                drawArc(
                    color = IxhBackgroundCream,
                    startAngle = 145f,
                    sweepAngle = 195f,
                    useCenter = true,
                    topLeft = Offset(center.x - radius, center.y - radius),
                    size = Size(radius * 2f, radius * 2f)
                )
                // 3. Bold outer circle outline
                drawCircle(
                    color = IxhCharcoal,
                    radius = radius,
                    center = center,
                    style = boldStroke
                )
                // 4. Bold "i" dot + stem
                drawCircle(
                    color = IxhCharcoal,
                    radius = (2.2f * s).dp.toPx(),
                    center = Offset(w * 0.50f, h * 0.34f)
                )
                drawLine(
                    color = IxhCharcoal,
                    start = Offset(w * 0.50f, h * 0.46f),
                    end = Offset(w * 0.50f, h * 0.67f),
                    strokeWidth = strokePx,
                    cap = StrokeCap.Round
                )
            }
        }
    }
}

// ============================================================================
// 7B. BLOB MASCOT SLOT (Tahap 2 3D Glossy Asset Integrated)
// ============================================================================

enum class IxhBlobSlotStyle {
    APP_BADGE_3D,
    HERO_SHOWCASE_3D,
    COMPACT_3D
}

/**
 * Komponen Wadah Maskot Blob 3D Glossy (Tahap 2 Terintegrasi).
 * - Untuk COMPACT_3D, merender langsung IxhBlobMascot 3D Glossy dengan ekspresi & animasi state.
 * - Untuk APP_BADGE_3D & HERO_SHOWCASE_3D, merender aset 3D Glossy kanonik beresolusi tinggi.
 */
@Composable
fun IxhBlobMascotSlot(
    modifier: Modifier = Modifier,
    slotStyle: IxhBlobSlotStyle = IxhBlobSlotStyle.COMPACT_3D,
    targetExpression: BlobExpression = BlobExpression.IDLE,
    size: Dp = 56.dp,
    showStageBadge: Boolean = false
) {
    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        when (slotStyle) {
            IxhBlobSlotStyle.APP_BADGE_3D -> {
                Image(
                    painter = painterResource(id = R.drawable.blob_ref_badge_3d),
                    contentDescription = "Blob Mascot 3D App Badge",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.fillMaxSize()
                )
            }
            IxhBlobSlotStyle.HERO_SHOWCASE_3D -> {
                Image(
                    painter = painterResource(id = R.drawable.blob_ref_hero_3d),
                    contentDescription = "Blob Mascot 3D Hero Showcase",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.fillMaxSize()
                )
            }
            IxhBlobSlotStyle.COMPACT_3D -> {
                IxhBlobMascot(
                    expression = targetExpression,
                    size = size
                )
            }
        }

        if (showStageBadge) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .clip(CircleShape)
                    .background(IxhPrimaryYellow)
                    .border(1.dp, IxhCharcoal, CircleShape)
                    .padding(horizontal = 5.dp, vertical = 1.dp)
            ) {
                Text(
                    text = "3D",
                    style = MaterialTheme.typography.labelSmall,
                    fontSize = 8.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = IxhCharcoal
                )
            }
        }
    }
}

// ============================================================================
// 8. STATE & FEEDBACK BANNERS (Sesuai referensi_desain_system.png)
// ============================================================================

enum class IxhFeedbackType {
    SUCCESS,
    INFO,
    WARNING,
    ERROR
}

@Composable
fun IxhStateFeedbackBanner(
    message: String,
    type: IxhFeedbackType,
    modifier: Modifier = Modifier
) {
    val tokens = LocalIxhDesign.current
    val shape = RoundedCornerShape(tokens.radius.medium)

    val (bgColor, badgeFill, iconVec) = when (type) {
        IxhFeedbackType.SUCCESS -> Triple(IxhSuccessBg, IxhSuccess.copy(alpha = 0.25f), Icons.Outlined.Check)
        IxhFeedbackType.INFO -> Triple(IxhInfoBg, IxhInfo.copy(alpha = 0.25f), Icons.Outlined.Info)
        IxhFeedbackType.WARNING -> Triple(IxhWarningBg, IxhPrimaryYellow, Icons.Outlined.WarningAmber)
        IxhFeedbackType.ERROR -> Triple(IxhErrorBg, IxhError.copy(alpha = 0.28f), Icons.Outlined.Close)
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .background(bgColor, shape)
            .padding(horizontal = 14.dp, vertical = 11.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(badgeFill, CircleShape)
                .border(1.5.dp, IxhCharcoal, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = iconVec,
                contentDescription = null,
                tint = IxhCharcoal,
                modifier = Modifier.size(15.dp)
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = IxhCharcoal
        )
    }
}

// ============================================================================
// 9. DIALOG, BOTTOM SHEET, LOADING, EMPTY STATE, ERROR STATE & SNACKBAR (§3.7)
// ============================================================================

@Composable
fun IxhDialog(
    title: String,
    message: String,
    confirmText: String = "Mengerti",
    dismissText: String? = "Batal",
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    val tokens = LocalIxhDesign.current
    val shape = RoundedCornerShape(tokens.radius.extraLarge)
    Dialog(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .ixhTactileShadow(level = IxhElevationLevel.HIGH, cornerRadius = tokens.radius.extraLarge)
                .clip(shape)
                .background(IxhBackgroundCream, shape)
                .border(tokens.border.thick, IxhCharcoal, shape)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IxhBlobMascotSlot(
                slotStyle = IxhBlobSlotStyle.COMPACT_3D,
                targetExpression = BlobExpression.SMILE,
                size = 72.dp
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
                color = IxhCharcoal
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = IxhTextSecondary
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                if (dismissText != null) {
                    IxhDsButton(
                        text = dismissText,
                        onClick = onDismiss,
                        variant = IxhButtonVariant.SECONDARY,
                        modifier = Modifier.weight(1f)
                    )
                }
                IxhDsButton(
                    text = confirmText,
                    onClick = onConfirm,
                    variant = IxhButtonVariant.PRIMARY,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IxhBottomSheet(
    title: String,
    onDismiss: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = IxhBackgroundCream,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 12.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium,
                color = IxhCharcoal
            )
            Spacer(modifier = Modifier.height(12.dp))
            content()
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun IxhLoadingIndicator(
    label: String = "Memproses data kelas IX-H...",
    progress: Float = 0.68f,
    modifier: Modifier = Modifier
) {
    val tokens = LocalIxhDesign.current
    val shape = RoundedCornerShape(tokens.radius.large)
    Row(
        modifier = modifier
            .fillMaxWidth()
            .ixhTactileShadow(level = IxhElevationLevel.LOW, cornerRadius = tokens.radius.large, softShadow = true)
            .clip(shape)
            .background(IxhSurfacePrimary, shape)
            .border(tokens.border.regular, IxhMutedBorder, shape)
            .padding(horizontal = 14.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
    ) {
        IxhBlobMascotSlot(
            slotStyle = IxhBlobSlotStyle.COMPACT_3D,
            targetExpression = BlobExpression.THINKING,
            size = 50.dp,
            showStageBadge = false
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label,
                style = MaterialTheme.typography.titleMedium,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = IxhCharcoal
            )
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
                    .clip(CircleShape)
                    .background(IxhSurfaceSecondary)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(progress.coerceIn(0f, 1f))
                        .height(10.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.horizontalGradient(
                                listOf(IxhSecondaryYellow, IxhPrimaryYellow)
                            )
                        )
                )
            }
        }
    }
}

@Composable
fun IxhEmptyState(
    title: String = "Belum Ada Data",
    description: String = "Data yang Anda cari belum tersedia untuk saat ini.",
    actionLabel: String? = null,
    onActionClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val tokens = LocalIxhDesign.current
    val shape = RoundedCornerShape(tokens.radius.large)
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .background(IxhSurfacePrimary, shape)
            .border(tokens.border.regular, IxhMutedBorder, shape)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(IxhLightYellow)
                .border(1.5.dp, IxhCharcoal, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.Inbox,
                contentDescription = null,
                tint = IxhCharcoal,
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = IxhCharcoal,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            color = IxhTextSecondary,
            textAlign = TextAlign.Center
        )
        if (actionLabel != null && onActionClick != null) {
            Spacer(modifier = Modifier.height(14.dp))
            IxhDsButton(
                text = actionLabel,
                onClick = onActionClick,
                variant = IxhButtonVariant.PRIMARY
            )
        }
    }
}

@Composable
fun IxhErrorState(
    title: String = "Gagal Memuat Data",
    description: String = "Periksa koneksi jaringan Anda lalu coba kembali.",
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    val tokens = LocalIxhDesign.current
    val shape = RoundedCornerShape(tokens.radius.large)
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .background(IxhErrorBg.copy(alpha = 0.55f), shape)
            .border(tokens.border.regular, IxhError, shape)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(IxhSurfacePrimary)
                .border(1.5.dp, IxhError, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.ErrorOutline,
                contentDescription = null,
                tint = IxhError,
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = IxhCharcoal,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            color = IxhTextSecondary,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(14.dp))
        IxhDsButton(
            text = "Coba Lagi",
            leadingIcon = Icons.Outlined.Refresh,
            onClick = onRetry,
            variant = IxhButtonVariant.SECONDARY
        )
    }
}

@Composable
fun IxhSectionPillHeader(
    title: String,
    modifier: Modifier = Modifier
) {
    val shape = CircleShape
    Box(
        modifier = modifier
            .ixhTactileShadow(level = IxhElevationLevel.LOW, cornerRadius = 999.dp)
            .clip(shape)
            .background(IxhPrimaryYellow, shape)
            .border(1.5.dp, IxhCharcoal, shape)
            .padding(horizontal = 16.dp, vertical = 6.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = IxhCharcoal
        )
    }
}
