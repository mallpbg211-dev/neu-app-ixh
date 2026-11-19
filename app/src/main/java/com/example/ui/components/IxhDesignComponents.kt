package com.example.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material.icons.outlined.Assignment
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.R
import com.example.ui.theme.IxhAlertYellowBg
import com.example.ui.theme.IxhAmber
import com.example.ui.theme.IxhAmberBg
import com.example.ui.theme.IxhAmberText
import com.example.ui.theme.IxhBlue
import com.example.ui.theme.IxhBlueBg
import com.example.ui.theme.IxhBlueText
import com.example.ui.theme.IxhBorder
import com.example.ui.theme.IxhCream
import com.example.ui.theme.IxhGlassBorder
import com.example.ui.theme.IxhGreen
import com.example.ui.theme.IxhGreenBg
import com.example.ui.theme.IxhGreenText
import com.example.ui.theme.IxhInk
import com.example.ui.theme.IxhRed
import com.example.ui.theme.IxhRedBg
import com.example.ui.theme.IxhSurfaceMuted
import com.example.ui.theme.IxhTextSecondary
import com.example.ui.theme.IxhWaBubbleSent
import com.example.ui.theme.IxhWaChatBg
import com.example.ui.theme.IxhWaHeader
import com.example.ui.theme.IxhWhite
import com.example.ui.theme.IxhYellow
import com.example.ui.theme.IxhYellowLight
import com.example.ui.theme.LocalIxhDesign
import com.example.ui.theme.VisualThemeStyle
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Pemetaan 10 ekspresi final maskot Blob ke aset PNG 3D-Glossy di res/drawable.
 * Interaksi Password:
 * - PASSWORD_HIDE: Password tersembunyi -> mata Blob terbuka (blob_password_hide.png).
 * - PASSWORD_SHOW: Password terlihat -> Blob menutup mata (blob_password_show.png).
 */
enum class BlobExpression(@DrawableRes val drawableRes: Int, val contentDesc: String) {
    IDLE(R.drawable.blob_idle, "Maskot Blob Idle"),
    SMILE(R.drawable.blob_smile, "Maskot Blob Tersenyum"),
    SAD(R.drawable.blob_sad, "Maskot Blob Sedih"),
    BLINK(R.drawable.blob_blink, "Maskot Blob Berkedip"),
    FOCUS(R.drawable.blob_focus, "Maskot Blob Fokus"),
    SLEEP(R.drawable.blob_sleep, "Maskot Blob Tidur"),
    PASSWORD_SHOW(R.drawable.blob_password_show, "Maskot Blob Menutup Mata Saat Password Terlihat"),
    PASSWORD_HIDE(R.drawable.blob_password_hide, "Maskot Blob Membuka Mata Saat Password Tersembunyi"),
    THINKING(R.drawable.blob_thinking, "Maskot Blob Berpikir"),
    EXCITED(R.drawable.blob_excited, "Maskot Blob Gembira");

    companion object {
        // Alias kompatibilitas untuk layar lama sebelum pembaruan tahap berikutnya
        val IDLE_OPEN: BlobExpression get() = IDLE
        val LOOK_INPUT: BlobExpression get() = FOCUS
        val SLEEP_CLOSED: BlobExpression get() = PASSWORD_SHOW
        val SMILE_HAPPY: BlobExpression get() = SMILE
        val SAD_ERROR: BlobExpression get() = SAD
    }
}

/**
 * Single-pass 2D Directional Neubrutalism offset shadow modifier.
 * Optimized for 2 GB RAM devices by drawing directly on the Canvas without extra Box nodes.
 */
fun Modifier.neubrutalShadow(
    enabled: Boolean = true,
    cornerRadius: Dp,
    offsetY: Dp = 4.dp,
    offsetX: Dp = 2.8.dp,
    shadowColor: Color = IxhInk
): Modifier = if (!enabled) {
    this
} else {
    this.drawBehind {
        val radiusPx = cornerRadius.toPx()
        val dx = offsetX.toPx()
        val dy = offsetY.toPx()
        drawRoundRect(
            color = shadowColor,
            topLeft = Offset(dx, dy),
            size = Size(size.width, size.height),
            cornerRadius = CornerRadius(radiusPx, radiusPx)
        )
    }
}

/**
 * Komponen Maskot Blob 3D-Glossy dengan animasi berbasis state (bukan infinite loop permanen),
 * ramah RAM 2 GB (transformasi via RenderNode graphicsLayer tanpa re-layout), dan otomatis
 * menghentikan seluruh animasi saat layar/aplikasi berada di background.
 */
@Composable
fun IxhBlobMascot(
    expression: BlobExpression = BlobExpression.IDLE,
    size: Dp = 76.dp,
    animateBounce: Boolean = false,
    animationTriggerKey: Int = 0,
    modifier: Modifier = Modifier
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    var isLifecycleResumed by remember {
        mutableStateOf(
            lifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)
        )
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            isLifecycleResumed = when (event) {
                Lifecycle.Event.ON_RESUME -> true
                Lifecycle.Event.ON_PAUSE,
                Lifecycle.Event.ON_STOP,
                Lifecycle.Event.ON_DESTROY -> false
                else -> isLifecycleResumed
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    var displayedExpression by remember(expression) { mutableStateOf(expression) }

    val offsetX = remember { Animatable(0f) }
    val offsetY = remember { Animatable(0f) }
    val scaleX = remember { Animatable(1f) }
    val scaleY = remember { Animatable(1f) }
    val rotationZ = remember { Animatable(0f) }

    val density = LocalDensity.current
    val dpToPx = remember(density) { with(density) { 1.dp.toPx() } }

    LaunchedEffect(expression, animateBounce, animationTriggerKey, isLifecycleResumed) {
        displayedExpression = expression

        if (!isLifecycleResumed) {
            offsetX.snapTo(0f)
            offsetY.snapTo(0f)
            scaleX.snapTo(1f)
            scaleY.snapTo(1f)
            rotationZ.snapTo(0f)
            return@LaunchedEffect
        }

        coroutineScope {
            launch { offsetX.animateTo(0f, tween(140)) }
            launch { offsetY.animateTo(0f, tween(140)) }
            launch { scaleX.animateTo(1f, tween(140)) }
            launch { scaleY.animateTo(1f, tween(140)) }
            launch { rotationZ.animateTo(0f, tween(140)) }
        }

        when (expression) {
            BlobExpression.IDLE -> {
                repeat(3) { cycle ->
                    coroutineScope {
                        launch {
                            offsetY.animateTo(
                                targetValue = -3.5f * dpToPx,
                                animationSpec = tween(1050, easing = FastOutSlowInEasing)
                            )
                        }
                        launch {
                            scaleY.animateTo(
                                targetValue = 1.028f,
                                animationSpec = tween(1050, easing = FastOutSlowInEasing)
                            )
                        }
                        launch {
                            scaleX.animateTo(
                                targetValue = 0.985f,
                                animationSpec = tween(1050, easing = FastOutSlowInEasing)
                            )
                        }
                    }
                    if (cycle == 1) {
                        displayedExpression = BlobExpression.BLINK
                        scaleY.animateTo(0.97f, tween(65))
                        delay(75)
                        displayedExpression = BlobExpression.IDLE
                        scaleY.animateTo(1.015f, tween(70))
                    }
                    coroutineScope {
                        launch {
                            offsetY.animateTo(
                                targetValue = 0f,
                                animationSpec = tween(1100, easing = FastOutSlowInEasing)
                            )
                        }
                        launch {
                            scaleY.animateTo(
                                targetValue = 1f,
                                animationSpec = tween(1100, easing = FastOutSlowInEasing)
                            )
                        }
                        launch {
                            scaleX.animateTo(
                                targetValue = 1f,
                                animationSpec = tween(1100, easing = FastOutSlowInEasing)
                            )
                        }
                    }
                }
            }

            BlobExpression.BLINK -> {
                coroutineScope {
                    launch { scaleY.animateTo(0.955f, tween(75, easing = FastOutSlowInEasing)) }
                    launch { scaleX.animateTo(1.02f, tween(75, easing = FastOutSlowInEasing)) }
                }
                delay(70)
                coroutineScope {
                    launch { scaleY.animateTo(1f, spring(dampingRatio = Spring.DampingRatioMediumBouncy)) }
                    launch { scaleX.animateTo(1f, spring(dampingRatio = Spring.DampingRatioMediumBouncy)) }
                }
            }

            BlobExpression.SMILE -> {
                val hops = if (animateBounce) 2 else 1
                repeat(hops) {
                    coroutineScope {
                        launch { scaleX.animateTo(1.045f, tween(95)) }
                        launch { scaleY.animateTo(0.955f, tween(95)) }
                    }
                    coroutineScope {
                        launch {
                            offsetY.animateTo(
                                targetValue = -7.5f * dpToPx,
                                animationSpec = tween(190, easing = LinearOutSlowInEasing)
                            )
                        }
                        launch { scaleX.animateTo(0.975f, tween(190)) }
                        launch { scaleY.animateTo(1.04f, tween(190)) }
                        launch { rotationZ.animateTo(2.6f, tween(190)) }
                    }
                    coroutineScope {
                        launch {
                            offsetY.animateTo(
                                targetValue = 0f,
                                animationSpec = spring(
                                    dampingRatio = Spring.DampingRatioMediumBouncy,
                                    stiffness = Spring.StiffnessMediumLow
                                )
                            )
                        }
                        launch {
                            scaleX.animateTo(
                                targetValue = 1f,
                                animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
                            )
                        }
                        launch {
                            scaleY.animateTo(
                                targetValue = 1f,
                                animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
                            )
                        }
                        launch {
                            rotationZ.animateTo(0f, tween(240, easing = FastOutSlowInEasing))
                        }
                    }
                    if (hops > 1) delay(160)
                }
            }

            BlobExpression.SAD -> {
                coroutineScope {
                    launch {
                        offsetY.animateTo(
                            targetValue = 4.5f * dpToPx,
                            animationSpec = tween(420, easing = FastOutSlowInEasing)
                        )
                    }
                    launch {
                        scaleY.animateTo(
                            targetValue = 0.945f,
                            animationSpec = tween(420, easing = FastOutSlowInEasing)
                        )
                    }
                    launch {
                        scaleX.animateTo(
                            targetValue = 1.035f,
                            animationSpec = tween(420, easing = FastOutSlowInEasing)
                        )
                    }
                    launch {
                        rotationZ.animateTo(
                            targetValue = -4.2f,
                            animationSpec = tween(360, easing = FastOutSlowInEasing)
                        )
                    }
                }
                rotationZ.animateTo(-2.5f, tween(340, easing = FastOutSlowInEasing))
            }

            BlobExpression.FOCUS -> {
                coroutineScope {
                    launch { offsetY.animateTo(1.8f * dpToPx, tween(220)) }
                    launch { scaleX.animateTo(1.018f, tween(220)) }
                    launch { scaleY.animateTo(0.988f, tween(220)) }
                }
                offsetX.animateTo(-2.4f * dpToPx, tween(280, easing = FastOutSlowInEasing))
                offsetX.animateTo(2.4f * dpToPx, tween(360, easing = FastOutSlowInEasing))
                offsetX.animateTo(0f, tween(260, easing = FastOutSlowInEasing))
            }

            BlobExpression.SLEEP -> {
                rotationZ.animateTo(-2.0f, tween(400, easing = FastOutSlowInEasing))
                repeat(3) {
                    coroutineScope {
                        launch {
                            scaleY.animateTo(
                                targetValue = 1.025f,
                                animationSpec = tween(1500, easing = FastOutSlowInEasing)
                            )
                        }
                        launch {
                            scaleX.animateTo(
                                targetValue = 0.984f,
                                animationSpec = tween(1500, easing = FastOutSlowInEasing)
                            )
                        }
                        launch {
                            offsetY.animateTo(
                                targetValue = -1.2f * dpToPx,
                                animationSpec = tween(1500, easing = FastOutSlowInEasing)
                            )
                        }
                    }
                    coroutineScope {
                        launch {
                            scaleY.animateTo(
                                targetValue = 0.99f,
                                animationSpec = tween(1550, easing = FastOutSlowInEasing)
                            )
                        }
                        launch {
                            scaleX.animateTo(
                                targetValue = 1.01f,
                                animationSpec = tween(1550, easing = FastOutSlowInEasing)
                            )
                        }
                        launch {
                            offsetY.animateTo(
                                targetValue = 1.2f * dpToPx,
                                animationSpec = tween(1550, easing = FastOutSlowInEasing)
                            )
                        }
                    }
                }
                coroutineScope {
                    launch { scaleY.animateTo(1f, tween(500)) }
                    launch { scaleX.animateTo(1f, tween(500)) }
                    launch { offsetY.animateTo(0f, tween(500)) }
                }
            }

            BlobExpression.PASSWORD_SHOW -> {
                coroutineScope {
                    launch { scaleY.animateTo(0.955f, tween(110, easing = FastOutSlowInEasing)) }
                    launch { scaleX.animateTo(1.025f, tween(110, easing = FastOutSlowInEasing)) }
                    launch { offsetY.animateTo(2.0f * dpToPx, tween(110, easing = FastOutSlowInEasing)) }
                }
                coroutineScope {
                    launch {
                        scaleY.animateTo(
                            targetValue = 1f,
                            animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
                        )
                    }
                    launch {
                        scaleX.animateTo(
                            targetValue = 1f,
                            animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
                        )
                    }
                    launch {
                        offsetY.animateTo(
                            targetValue = 0f,
                            animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
                        )
                    }
                }
            }

            BlobExpression.PASSWORD_HIDE -> {
                coroutineScope {
                    launch { scaleY.animateTo(1.032f, tween(115, easing = LinearOutSlowInEasing)) }
                    launch { scaleX.animateTo(0.98f, tween(115, easing = LinearOutSlowInEasing)) }
                    launch { offsetY.animateTo(-2.0f * dpToPx, tween(115, easing = LinearOutSlowInEasing)) }
                }
                coroutineScope {
                    launch {
                        scaleY.animateTo(
                            targetValue = 1f,
                            animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
                        )
                    }
                    launch {
                        scaleX.animateTo(
                            targetValue = 1f,
                            animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
                        )
                    }
                    launch {
                        offsetY.animateTo(
                            targetValue = 0f,
                            animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
                        )
                    }
                }
            }

            BlobExpression.THINKING -> {
                coroutineScope {
                    launch { rotationZ.animateTo(-3.8f, tween(300, easing = FastOutSlowInEasing)) }
                    launch { offsetY.animateTo(-2.2f * dpToPx, tween(300, easing = FastOutSlowInEasing)) }
                }
                rotationZ.animateTo(3.6f, tween(380, easing = FastOutSlowInEasing))
                coroutineScope {
                    launch { rotationZ.animateTo(-1.8f, tween(320, easing = FastOutSlowInEasing)) }
                    launch { offsetY.animateTo(0f, tween(320, easing = FastOutSlowInEasing)) }
                }
            }

            BlobExpression.EXCITED -> {
                repeat(2) { idx ->
                    val jumpHeight = if (idx == 0) -11.5f * dpToPx else -7.0f * dpToPx
                    val tilt = if (idx == 0) -3.8f else 3.5f
                    coroutineScope {
                        launch { scaleX.animateTo(1.06f, tween(80)) }
                        launch { scaleY.animateTo(0.935f, tween(80)) }
                    }
                    coroutineScope {
                        launch {
                            offsetY.animateTo(
                                targetValue = jumpHeight,
                                animationSpec = tween(175, easing = LinearOutSlowInEasing)
                            )
                        }
                        launch { scaleX.animateTo(0.955f, tween(175)) }
                        launch { scaleY.animateTo(1.065f, tween(175)) }
                        launch { rotationZ.animateTo(tilt, tween(175)) }
                    }
                    coroutineScope {
                        launch {
                            offsetY.animateTo(
                                targetValue = 0f,
                                animationSpec = spring(
                                    dampingRatio = Spring.DampingRatioLowBouncy,
                                    stiffness = Spring.StiffnessMedium
                                )
                            )
                        }
                        launch {
                            scaleX.animateTo(
                                targetValue = 1f,
                                animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
                            )
                        }
                        launch {
                            scaleY.animateTo(
                                targetValue = 1f,
                                animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
                            )
                        }
                        launch {
                            rotationZ.animateTo(0f, tween(210, easing = FastOutSlowInEasing))
                        }
                    }
                }
            }
        }
    }

    Box(
        modifier = modifier
            .size(size)
            .drawBehind {
                val liftFactor = (-offsetY.value / (12f * dpToPx)).coerceIn(-0.35f, 1f)
                val shadowScale = 1f - (liftFactor * 0.22f)
                val shadowAlpha = (0.22f * (1f - liftFactor * 0.45f)).coerceIn(0.08f, 0.30f)
                val shadowW = this.size.width * 0.58f * shadowScale
                val shadowH = this.size.height * 0.095f * shadowScale
                drawOval(
                    color = IxhInk.copy(alpha = shadowAlpha),
                    topLeft = Offset(
                        x = (this.size.width - shadowW) / 2f,
                        y = this.size.height * 0.865f
                    ),
                    size = Size(shadowW, shadowH)
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Crossfade(
            targetState = displayedExpression,
            animationSpec = tween(durationMillis = 150, easing = FastOutSlowInEasing),
            label = "blob_expression_crossfade"
        ) { targetExp ->
            Image(
                painter = painterResource(id = targetExp.drawableRes),
                contentDescription = targetExp.contentDesc,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        translationX = offsetX.value
                        translationY = offsetY.value
                        this.scaleX = scaleX.value
                        this.scaleY = scaleY.value
                        this.rotationZ = rotationZ.value
                        transformOrigin = TransformOrigin(0.5f, 0.85f)
                    }
            )
        }
    }
}

/**
 * Sculpted Asymmetric Neubrutalist Card with Organic Curve Pocket & Bold Charcoal Outline.
 */
@Composable
fun IxhCard(
    modifier: Modifier = Modifier,
    backgroundColor: Color = IxhWhite,
    borderColor: Color? = null,
    hasHardShadow: Boolean = false,
    leftStripeColor: Color? = null,
    onClick: (() -> Unit)? = null,
    contentPadding: PaddingValues = PaddingValues(14.dp),
    content: @Composable ColumnScope.() -> Unit
) {
    val design = LocalIxhDesign.current
    val isGlass = design.style == VisualThemeStyle.LIQUID_GLASS
    // Asymmetric sculpted corners give every card an intentional organic-neubrutalist silhouette
    val shape = if (hasHardShadow) {
        RoundedCornerShape(topStart = 22.dp, topEnd = 14.dp, bottomStart = 14.dp, bottomEnd = 24.dp)
    } else {
        RoundedCornerShape(topStart = 18.dp, topEnd = 12.dp, bottomStart = 12.dp, bottomEnd = 20.dp)
    }
    val resolvedBorderColor = borderColor ?: if (isGlass) IxhGlassBorder else IxhInk
    val borderWidth = when {
        isGlass -> 1.2.dp
        hasHardShadow -> 2.4.dp
        else -> 1.9.dp
    }
    val shadowOffset = if (hasHardShadow) 4.dp else 2.2.dp
    val shadowOffsetX = if (hasHardShadow) 3.dp else 1.6.dp

    val clickMod = if (onClick != null) {
        Modifier.clickable(onClick = onClick)
    } else {
        Modifier
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .neubrutalShadow(
                enabled = !isGlass,
                cornerRadius = 18.dp,
                offsetY = shadowOffset,
                offsetX = shadowOffsetX,
                shadowColor = IxhInk
            )
            .clip(shape)
            .background(backgroundColor, shape)
            .drawBehind {
                // Subtle organic wave accent in the bottom-right corner of yellow/featured cards
                if (backgroundColor == IxhYellow || backgroundColor == IxhYellowLight) {
                    val w = size.width
                    val h = size.height
                    val wave = androidx.compose.ui.graphics.Path().apply {
                        moveTo(w * 0.58f, h)
                        cubicTo(
                            w * 0.70f, h * 0.74f,
                            w * 0.86f, h * 0.62f,
                            w, h * 0.44f
                        )
                        lineTo(w, h)
                        close()
                    }
                    drawPath(
                        path = wave,
                        color = if (backgroundColor == IxhYellow) {
                            Color(0xFFFFE066).copy(alpha = 0.65f)
                        } else {
                            IxhYellow.copy(alpha = 0.28f)
                        }
                    )
                }
            }
            .border(borderWidth, resolvedBorderColor, shape)
            .then(clickMod)
    ) {
        if (leftStripeColor != null) {
            Box(
                modifier = Modifier
                    .width(8.dp)
                    .height(82.dp)
                    .background(leftStripeColor)
                    .drawBehind {
                        drawLine(
                            color = IxhInk,
                            start = Offset(size.width, 0f),
                            end = Offset(size.width, size.height),
                            strokeWidth = 2.dp.toPx()
                        )
                    }
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(contentPadding),
            content = content
        )
    }
}

@Composable
fun IxhPrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    enabled: Boolean = true,
    testTag: String? = null
) {
    val design = LocalIxhDesign.current
    val isGlass = design.style == VisualThemeStyle.LIQUID_GLASS
    val shape = RoundedCornerShape(topStart = 20.dp, topEnd = 13.dp, bottomStart = 13.dp, bottomEnd = 22.dp)
    val bgColor = if (enabled) IxhYellow else IxhSurfaceMuted
    val textColor = if (enabled) IxhInk else IxhTextSecondary

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .let { if (testTag != null) it.testTag(testTag) else it }
            .neubrutalShadow(
                enabled = !isGlass && enabled,
                cornerRadius = 18.dp,
                offsetY = 4.dp,
                offsetX = 3.dp
            )
            .clip(shape)
            .background(bgColor, shape)
            .drawBehind {
                if (enabled) {
                    val w = size.width
                    val h = size.height
                    val highlightWave = androidx.compose.ui.graphics.Path().apply {
                        moveTo(0f, 0f)
                        lineTo(w * 0.42f, 0f)
                        cubicTo(w * 0.32f, h * 0.45f, w * 0.14f, h * 0.65f, 0f, h * 0.82f)
                        close()
                    }
                    drawPath(highlightWave, color = Color(0xFFFFDE6A).copy(alpha = 0.55f))
                }
            }
            .border(
                width = if (isGlass) 0.dp else 2.4.dp,
                color = if (isGlass) Color.Transparent else IxhInk,
                shape = shape
            )
            .clickable(enabled = enabled, onClick = onClick)
            .padding(horizontal = 18.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = textColor,
                modifier = Modifier.size(19.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            color = textColor,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 13.sp
        )
    }
}

@Composable
fun IxhSecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    isDanger: Boolean = false,
    testTag: String? = null
) {
    val design = LocalIxhDesign.current
    val isGlass = design.style == VisualThemeStyle.LIQUID_GLASS
    val shape = RoundedCornerShape(topStart = 18.dp, topEnd = 12.dp, bottomStart = 12.dp, bottomEnd = 20.dp)
    val strokeColor = when {
        isDanger -> IxhRed
        isGlass -> IxhGlassBorder
        else -> IxhInk
    }
    val bgColor = if (isDanger) IxhRedBg else IxhWhite
    val contentColor = if (isDanger) IxhRed else IxhInk

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .let { if (testTag != null) it.testTag(testTag) else it }
            .neubrutalShadow(
                enabled = !isGlass,
                cornerRadius = 16.dp,
                offsetY = 3.dp,
                offsetX = 2.2.dp,
                shadowColor = strokeColor
            )
            .clip(shape)
            .background(bgColor, shape)
            .border(2.2.dp, strokeColor, shape)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = contentColor,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            color = contentColor,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 12.5.sp
        )
    }
}

enum class ChipTone {
    GREEN, YELLOW, BLUE, RED, NEUTRAL, OUTLINE_YELLOW
}

@Composable
fun IxhStatusChip(
    text: String,
    tone: ChipTone = ChipTone.NEUTRAL,
    modifier: Modifier = Modifier
) {
    val (bg, fg, borderCol) = when (tone) {
        ChipTone.GREEN -> Triple(IxhGreenBg, IxhGreenText, IxhInk)
        ChipTone.YELLOW -> Triple(IxhYellow, IxhInk, IxhInk)
        ChipTone.BLUE -> Triple(IxhBlueBg, IxhBlueText, IxhInk)
        ChipTone.RED -> Triple(IxhRedBg, IxhRed, IxhInk)
        ChipTone.NEUTRAL -> Triple(IxhSurfaceMuted, IxhInk, IxhInk)
        ChipTone.OUTLINE_YELLOW -> Triple(IxhYellow, IxhInk, IxhInk)
    }
    val shape = RoundedCornerShape(topStart = 12.dp, topEnd = 8.dp, bottomStart = 8.dp, bottomEnd = 14.dp)
    Box(
        modifier = modifier
            .clip(shape)
            .background(bg, shape)
            .border(1.6.dp, borderCol, shape)
            .padding(horizontal = 10.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 10.5.sp,
            fontWeight = FontWeight.ExtraBold,
            color = fg
        )
    }
}

@Composable
fun IxhCircleIcon(
    icon: ImageVector,
    modifier: Modifier = Modifier,
    bgColor: Color = IxhYellowLight,
    iconTint: Color = IxhInk,
    hasBorder: Boolean = true,
    size: Dp = 38.dp
) {
    val podShape = RoundedCornerShape(topStart = size * 0.42f, topEnd = size * 0.28f, bottomStart = size * 0.28f, bottomEnd = size * 0.46f)
    Box(
        modifier = modifier
            .size(size)
            .neubrutalShadow(
                enabled = hasBorder,
                cornerRadius = size * 0.35f,
                offsetY = 2.2.dp,
                offsetX = 1.8.dp
            )
            .clip(podShape)
            .background(bgColor, podShape)
            .border(if (hasBorder) 2.dp else 1.5.dp, IxhInk, podShape),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier.size(size * 0.52f)
        )
    }
}

@Composable
fun IxhAlertBanner(
    text: String,
    tone: ChipTone = ChipTone.BLUE,
    title: String? = null,
    icon: ImageVector = Icons.Outlined.Info,
    onClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val (bg, accentCol) = when (tone) {
        ChipTone.RED -> IxhRedBg to IxhRed
        ChipTone.YELLOW -> IxhYellowLight to IxhYellow
        ChipTone.GREEN -> IxhGreenBg to IxhGreen
        else -> IxhBlueBg to IxhBlue
    }
    val shape = RoundedCornerShape(topStart = 20.dp, topEnd = 12.dp, bottomStart = 12.dp, bottomEnd = 22.dp)
    Row(
        modifier = modifier
            .fillMaxWidth()
            .neubrutalShadow(
                enabled = true,
                cornerRadius = 16.dp,
                offsetY = 3.dp,
                offsetX = 2.2.dp,
                shadowColor = IxhInk
            )
            .clip(shape)
            .background(bg, shape)
            .drawBehind {
                val w = size.width
                val h = size.height
                val leftWave = androidx.compose.ui.graphics.Path().apply {
                    moveTo(0f, 0f)
                    lineTo(w * 0.16f, 0f)
                    cubicTo(w * 0.12f, h * 0.45f, w * 0.20f, h * 0.75f, w * 0.08f, h)
                    lineTo(0f, h)
                    close()
                }
                drawPath(leftWave, color = accentCol.copy(alpha = 0.28f))
            }
            .border(2.2.dp, IxhInk, shape)
            .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier)
            .padding(horizontal = 13.dp, vertical = 11.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(34.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(if (tone == ChipTone.YELLOW) IxhYellow else IxhWhite)
                .border(1.8.dp, IxhInk, RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (tone == ChipTone.RED) IxhRed else IxhInk,
                modifier = Modifier.size(18.dp)
            )
        }
        Spacer(modifier = Modifier.width(11.dp))
        Column(modifier = Modifier.weight(1f)) {
            if (title != null) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelLarge,
                    color = if (tone == ChipTone.RED) IxhRed else IxhInk,
                    fontWeight = FontWeight.ExtraBold
                )
                Spacer(modifier = Modifier.height(1.dp))
            }
            Text(
                text = text,
                style = MaterialTheme.typography.bodySmall,
                color = IxhInk,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun IxhNeubrutalToggle(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    val design = LocalIxhDesign.current
    val isGlass = design.style == VisualThemeStyle.LIQUID_GLASS
    val shape = RoundedCornerShape(13.dp)
    val bgColor = if (checked) IxhYellow else IxhSurfaceMuted
    Box(
        modifier = modifier
            .width(48.dp)
            .height(26.dp)
            .neubrutalShadow(enabled = !isGlass, cornerRadius = 13.dp, offsetY = 2.dp, offsetX = 1.5.dp)
            .clip(shape)
            .background(bgColor, shape)
            .border(2.2.dp, IxhInk, shape)
            .clickable(enabled = enabled) { onCheckedChange(!checked) }
            .padding(horizontal = 3.5.dp),
        contentAlignment = if (checked) Alignment.CenterEnd else Alignment.CenterStart
    ) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .clip(CircleShape)
                .background(if (checked) IxhInk else IxhWhite, CircleShape)
                .border(1.5.dp, IxhInk, CircleShape)
        )
    }
}

@Composable
fun IxhSegmentedTabs(
    tabs: List<String>,
    selectedIndex: Int,
    onTabSelected: (Int) -> Unit,
    badgeMap: Map<Int, Int> = emptyMap(),
    modifier: Modifier = Modifier
) {
    val dockShape = RoundedCornerShape(topStart = 18.dp, topEnd = 12.dp, bottomStart = 12.dp, bottomEnd = 20.dp)
    Row(
        modifier = modifier
            .fillMaxWidth()
            .neubrutalShadow(enabled = true, cornerRadius = 16.dp, offsetY = 3.dp, offsetX = 2.2.dp)
            .clip(dockShape)
            .background(IxhWhite, dockShape)
            .border(2.2.dp, IxhInk, dockShape)
            .padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        tabs.forEachIndexed { idx, label ->
            val isSelected = idx == selectedIndex
            val itemShape = RoundedCornerShape(topStart = 13.dp, topEnd = 9.dp, bottomStart = 9.dp, bottomEnd = 14.dp)
            Row(
                modifier = Modifier
                    .weight(1f)
                    .clip(itemShape)
                    .background(if (isSelected) IxhYellow else Color.Transparent)
                    .then(
                        if (isSelected) Modifier.border(2.dp, IxhInk, itemShape)
                        else Modifier
                    )
                    .clickable { onTabSelected(idx) }
                    .padding(vertical = 8.5.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Bold,
                    color = IxhInk
                )
                val badgeCount = badgeMap[idx] ?: 0
                if (badgeCount > 0) {
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(if (isSelected) IxhInk else IxhRed)
                            .border(1.2.dp, IxhInk, CircleShape)
                            .padding(horizontal = 6.dp, vertical = 1.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = badgeCount.toString(),
                            color = if (isSelected) IxhYellow else Color.White,
                            fontSize = 9.5.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun IxhWaBubblePreview(
    senderTitle: String = "IX-H Hub",
    messageBody: String,
    timeLabel: String = "08.10",
    outgoingReply: String? = null,
    modifier: Modifier = Modifier
) {
    val outerShape = RoundedCornerShape(topStart = 18.dp, topEnd = 12.dp, bottomStart = 12.dp, bottomEnd = 20.dp)
    Column(
        modifier = modifier
            .fillMaxWidth()
            .neubrutalShadow(enabled = true, cornerRadius = 16.dp, offsetY = 3.dp, offsetX = 2.dp)
            .clip(outerShape)
            .background(IxhWaChatBg, outerShape)
            .border(2.dp, IxhInk, outerShape)
            .padding(11.dp)
    ) {
        val bubbleShape = RoundedCornerShape(topStart = 4.dp, topEnd = 14.dp, bottomEnd = 14.dp, bottomStart = 14.dp)
        Column(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .clip(bubbleShape)
                .background(IxhWhite, bubbleShape)
                .border(1.8.dp, IxhInk, bubbleShape)
                .padding(10.dp)
        ) {
            Text(
                text = senderTitle,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.ExtraBold,
                color = IxhWaHeader
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = messageBody,
                style = MaterialTheme.typography.bodySmall,
                color = IxhInk,
                fontWeight = FontWeight.Medium,
                lineHeight = 16.sp
            )
            Text(
                text = timeLabel,
                style = MaterialTheme.typography.labelSmall,
                color = IxhTextSecondary,
                modifier = Modifier.align(Alignment.End)
            )
        }
        if (outgoingReply != null) {
            Spacer(modifier = Modifier.height(8.dp))
            val replyShape = RoundedCornerShape(topStart = 14.dp, topEnd = 4.dp, bottomEnd = 14.dp, bottomStart = 14.dp)
            Column(
                modifier = Modifier
                    .align(Alignment.End)
                    .clip(replyShape)
                    .background(IxhWaBubbleSent, replyShape)
                    .border(1.8.dp, IxhInk, replyShape)
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Text(
                    text = outgoingReply,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    color = IxhInk
                )
                Text(
                    text = "$timeLabel ✓✓",
                    style = MaterialTheme.typography.labelSmall,
                    color = IxhTextSecondary,
                    modifier = Modifier.align(Alignment.End)
                )
            }
        }
    }
}

@Composable
fun IxhDonutChart(
    centerValueText: String,
    centerSubText: String,
    segments: List<Pair<Float, Color>>,
    size: Dp = 104.dp,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.size(size), contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.matchParentSize().padding(8.dp)) {
            val total = segments.sumOf { it.first.toDouble() }.toFloat().coerceAtLeast(1f)
            var startAngle = -90f
            val outerBorderStroke = Stroke(width = 16.dp.toPx(), cap = StrokeCap.Butt)
            val fillStroke = Stroke(width = 12.dp.toPx(), cap = StrokeCap.Butt)
            // Base charcoal ring casing for bold Neubrutalist gauge look
            drawArc(
                color = IxhInk,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                style = outerBorderStroke
            )
            segments.forEach { (value, color) ->
                val sweep = (value / total) * 360f
                if (sweep > 0f) {
                    drawArc(
                        color = color,
                        startAngle = startAngle,
                        sweepAngle = (sweep - 3f).coerceAtLeast(1f),
                        useCenter = false,
                        style = fillStroke
                    )
                    startAngle += sweep
                }
            }
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = centerValueText,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.ExtraBold,
                color = IxhInk
            )
            Text(
                text = centerSubText.uppercase(),
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.ExtraBold,
                color = IxhInk,
                fontSize = 9.sp
            )
        }
    }
}

fun formatRupiah(amount: Int): String {
    val isNeg = amount < 0
    val absVal = kotlin.math.abs(amount)
    val formatted = absVal.toString().reversed().chunked(3).joinToString(".").reversed()
    return if (isNeg) "-Rp$formatted" else "Rp$formatted"
}

typealias BlobMascotState = BlobExpression

@Composable
fun IxhEmptyStateCard(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
    expression: BlobExpression = BlobExpression.IDLE,
    mascotState: BlobExpression = expression,
    actionText: String? = null,
    onActionClick: (() -> Unit)? = null,
    actionTestTag: String? = null,
    testTag: String? = null
) {
    val emptyShape = RoundedCornerShape(topStart = 26.dp, topEnd = 16.dp, bottomStart = 16.dp, bottomEnd = 30.dp)
    Box(
        modifier = modifier
            .fillMaxWidth()
            .let { if (testTag != null) it.testTag(testTag) else it }
            .neubrutalShadow(enabled = true, cornerRadius = 22.dp, offsetY = 4.5.dp, offsetX = 3.dp)
            .clip(emptyShape)
            .background(IxhWhite, emptyShape)
            .drawBehind {
                val w = size.width
                val h = size.height
                // Organic flowing yellow wave across top & bottom-right of empty state
                val topWave = androidx.compose.ui.graphics.Path().apply {
                    moveTo(0f, 0f)
                    lineTo(w * 0.45f, 0f)
                    cubicTo(w * 0.34f, h * 0.18f, w * 0.15f, h * 0.24f, 0f, h * 0.36f)
                    close()
                }
                drawPath(topWave, color = IxhYellowLight.copy(alpha = 0.85f))

                val bottomWave = androidx.compose.ui.graphics.Path().apply {
                    moveTo(w * 0.52f, h)
                    cubicTo(w * 0.68f, h * 0.82f, w * 0.85f, h * 0.76f, w, h * 0.62f)
                    lineTo(w, h)
                    close()
                }
                drawPath(bottomWave, color = IxhYellow.copy(alpha = 0.32f))
            }
            .border(2.4.dp, IxhInk, emptyShape)
            .padding(horizontal = 18.dp, vertical = 18.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(86.dp)
                    .neubrutalShadow(enabled = true, cornerRadius = 24.dp, offsetY = 3.dp, offsetX = 2.dp)
                    .clip(RoundedCornerShape(topStart = 26.dp, topEnd = 18.dp, bottomStart = 18.dp, bottomEnd = 28.dp))
                    .background(IxhYellowLight)
                    .border(2.2.dp, IxhInk, RoundedCornerShape(topStart = 26.dp, topEnd = 18.dp, bottomStart = 18.dp, bottomEnd = 28.dp)),
                contentAlignment = Alignment.Center
            ) {
                IxhBlobMascot(
                    expression = mascotState,
                    size = 74.dp
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.ExtraBold,
                color = IxhInk,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = IxhTextSecondary,
                fontWeight = FontWeight.Medium,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
            if (actionText != null && onActionClick != null) {
                Spacer(modifier = Modifier.height(14.dp))
                IxhPrimaryButton(
                    text = actionText,
                    onClick = onActionClick,
                    testTag = actionTestTag ?: testTag?.let { "${it}_action" }
                )
            }
        }
    }
}

@Composable
fun IxhTopBrandHeader(
    roleLabel: String,
    unreadNotificationCount: Int,
    onNotificationsClick: () -> Unit,
    onProfileClick: () -> Unit,
    onRoleBadgeClick: () -> Unit,
    modifier: Modifier = Modifier,
    onSearchClick: (() -> Unit)? = null
) {
    val design = LocalIxhDesign.current
    val isGlass = design.style == VisualThemeStyle.LIQUID_GLASS
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IxhBlobMascotSlot(
                slotStyle = IxhBlobSlotStyle.APP_BADGE_3D,
                size = 40.dp
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "IX-H HUB",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.ExtraBold,
                        color = IxhInk
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Box(
                        modifier = Modifier
                            .neubrutalShadow(enabled = !isGlass, cornerRadius = 7.dp, offsetY = 2.dp, offsetX = 1.5.dp)
                            .clip(RoundedCornerShape(7.dp))
                            .background(IxhYellow)
                            .border(1.8.dp, IxhInk, RoundedCornerShape(7.dp))
                            .padding(horizontal = 6.dp, vertical = 1.dp)
                    ) {
                        Text(
                            text = "1.0",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = IxhInk
                        )
                    }
                }
                Text(
                    text = "Kelas IX-H • SMPN 1 Bojongsari",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = IxhTextSecondary
                )
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(7.dp)
        ) {
            val rolePillShape = RoundedCornerShape(topStart = 14.dp, topEnd = 9.dp, bottomStart = 9.dp, bottomEnd = 15.dp)
            Box(
                modifier = Modifier
                    .neubrutalShadow(enabled = !isGlass, cornerRadius = 12.dp, offsetY = 2.5.dp, offsetX = 1.8.dp)
                    .clip(rolePillShape)
                    .background(IxhYellow, rolePillShape)
                    .border(2.dp, IxhInk, rolePillShape)
                    .clickable(onClick = onRoleBadgeClick)
                    .padding(horizontal = 10.dp, vertical = 5.dp)
                    .testTag("role_badge_button")
            ) {
                Text(
                    text = roleLabel,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = IxhInk
                )
            }

            if (onSearchClick != null) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clickable(onClick = onSearchClick)
                        .testTag("header_search_button"),
                    contentAlignment = Alignment.Center
                ) {
                    val btnShape = RoundedCornerShape(topStart = 14.dp, topEnd = 10.dp, bottomStart = 10.dp, bottomEnd = 16.dp)
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .neubrutalShadow(enabled = !isGlass, cornerRadius = 12.dp, offsetY = 2.5.dp, offsetX = 1.8.dp)
                            .clip(btnShape)
                            .background(IxhWhite, btnShape)
                            .border(2.dp, IxhInk, btnShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Search,
                            contentDescription = "Cari",
                            tint = IxhInk,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }

            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clickable(onClick = onNotificationsClick)
                    .testTag("header_notifications_button"),
                contentAlignment = Alignment.Center
            ) {
                val notifShape = RoundedCornerShape(topStart = 14.dp, topEnd = 10.dp, bottomStart = 10.dp, bottomEnd = 16.dp)
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .neubrutalShadow(enabled = !isGlass, cornerRadius = 12.dp, offsetY = 2.5.dp, offsetX = 1.8.dp)
                        .clip(notifShape)
                        .background(IxhWhite, notifShape)
                        .border(2.dp, IxhInk, notifShape),
                    contentAlignment = Alignment.Center
                ) {
                    IxhDualToneSystemIcon(
                        iconType = IxhSystemIconType.NOTIFIKASI,
                        size = 19.dp
                    )
                }
                if (unreadNotificationCount > 0) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .clip(CircleShape)
                            .background(IxhRed)
                            .border(1.5.dp, IxhInk, CircleShape)
                            .padding(horizontal = 5.dp, vertical = 1.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = unreadNotificationCount.toString(),
                            color = Color.White,
                            fontSize = 8.5.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
            }

            val profShape = RoundedCornerShape(topStart = 14.dp, topEnd = 10.dp, bottomStart = 10.dp, bottomEnd = 16.dp)
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .neubrutalShadow(enabled = !isGlass, cornerRadius = 12.dp, offsetY = 2.5.dp, offsetX = 1.8.dp)
                    .clip(profShape)
                    .background(IxhYellow, profShape)
                    .border(2.dp, IxhInk, profShape)
                    .clickable(onClick = onProfileClick)
                    .testTag("header_profile_button"),
                contentAlignment = Alignment.Center
            ) {
                IxhDualToneSystemIcon(
                    iconType = IxhSystemIconType.PROFIL,
                    size = 19.dp
                )
            }
        }
    }
}

/**
 * Signature Sculpted Organic Greeting Card for IX-H HUB 1.0
 * Features multi-layered flowing S-curves, Charcoal contour lines, halftone matrix,
 * high-contrast Charcoal ribbon badges, 3D Glossy Blob Mascot pod, and Global Search dock.
 */
@Composable
fun IxhOrganicGreetingCard(
    greetingTitle: String,
    userName: String,
    subRoleLabel: String,
    classSubTitle: String,
    syncStatusLabel: String,
    pendingSyncCount: Int,
    onSearchBarClick: () -> Unit,
    onSyncStatusClick: () -> Unit,
    modifier: Modifier = Modifier,
    blobExpression: BlobExpression = BlobExpression.SMILE
) {
    val cardShape = RoundedCornerShape(topStart = 30.dp, topEnd = 16.dp, bottomStart = 16.dp, bottomEnd = 36.dp)
    Box(
        modifier = modifier
            .fillMaxWidth()
            .neubrutalShadow(
                enabled = true,
                cornerRadius = 26.dp,
                offsetY = 5.dp,
                offsetX = 3.5.dp,
                shadowColor = IxhInk
            )
            .clip(cardShape)
            .background(IxhYellow, cardShape)
            .border(2.6.dp, IxhInk, cardShape)
            .drawBehind {
                val w = size.width
                val h = size.height

                // 1. Sculpted Organic Golden-Cream Wave sweeping across top-right
                val upperWave = androidx.compose.ui.graphics.Path().apply {
                    moveTo(w * 0.44f, 0f)
                    cubicTo(
                        w * 0.62f, h * 0.16f,
                        w * 0.72f, h * 0.48f,
                        w, h * 0.38f
                    )
                    lineTo(w, 0f)
                    close()
                }
                drawPath(
                    path = upperWave,
                    color = Color(0xFFFFE47A).copy(alpha = 0.85f)
                )
                val upperWaveContour = androidx.compose.ui.graphics.Path().apply {
                    moveTo(w * 0.44f, 0f)
                    cubicTo(
                        w * 0.62f, h * 0.16f,
                        w * 0.72f, h * 0.48f,
                        w, h * 0.38f
                    )
                }
                drawPath(
                    path = upperWaveContour,
                    color = IxhInk.copy(alpha = 0.18f),
                    style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round)
                )

                // 2. Flowing Lower S-Wave Pocket with Charcoal Rim Accent
                val lowerWave = androidx.compose.ui.graphics.Path().apply {
                    moveTo(0f, h * 0.68f)
                    cubicTo(
                        w * 0.32f, h * 0.54f,
                        w * 0.58f, h * 0.88f,
                        w, h * 0.60f
                    )
                    lineTo(w, h)
                    lineTo(0f, h)
                    close()
                }
                drawPath(
                    path = lowerWave,
                    color = Color(0xFFFFB703).copy(alpha = 0.34f)
                )
                val lowerWaveContour = androidx.compose.ui.graphics.Path().apply {
                    moveTo(0f, h * 0.68f)
                    cubicTo(
                        w * 0.32f, h * 0.54f,
                        w * 0.58f, h * 0.88f,
                        w, h * 0.60f
                    )
                }
                drawPath(
                    path = lowerWaveContour,
                    color = IxhInk.copy(alpha = 0.22f),
                    style = Stroke(width = 1.8.dp.toPx(), cap = StrokeCap.Round)
                )

                // 3. Bold Halftone Dots in Top-Right & Bottom-Left Corners
                val dotColor = IxhInk.copy(alpha = 0.13f)
                val step = 12.dp.toPx()
                for (row in 0..3) {
                    for (col in 0..5) {
                        val cx = w - (col * step) - 14.dp.toPx()
                        val cy = (row * step) + 14.dp.toPx()
                        val r = (2.4f - row * 0.35f).coerceAtLeast(1.1f).dp.toPx()
                        drawCircle(color = dotColor, radius = r, center = Offset(cx, cy))
                    }
                }
            }
            .padding(horizontal = 16.dp, vertical = 15.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        val roleBadgeShape = RoundedCornerShape(topStart = 12.dp, topEnd = 8.dp, bottomStart = 8.dp, bottomEnd = 14.dp)
                        Row(
                            modifier = Modifier
                                .clip(roleBadgeShape)
                                .background(IxhInk)
                                .padding(horizontal = 10.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .clip(CircleShape)
                                    .background(IxhYellow)
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = subRoleLabel.uppercase(),
                                style = MaterialTheme.typography.labelSmall,
                                color = IxhYellow,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 9.5.sp
                            )
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(999.dp))
                                .background(IxhWhite)
                                .border(1.8.dp, IxhInk, RoundedCornerShape(999.dp))
                                .clickable(onClick = onSyncStatusClick)
                                .padding(horizontal = 9.dp, vertical = 3.dp)
                                .testTag("greeting_sync_pill")
                        ) {
                            Text(
                                text = if (pendingSyncCount > 0) "Lokal • $pendingSyncCount Antrean" else syncStatusLabel,
                                style = MaterialTheme.typography.labelSmall,
                                color = IxhInk,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 9.5.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(9.dp))
                    Text(
                        text = greetingTitle,
                        style = MaterialTheme.typography.labelLarge,
                        color = IxhInk,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = userName,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = IxhInk,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = classSubTitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = IxhInk,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))
                val mascotPodShape = RoundedCornerShape(topStart = 26.dp, topEnd = 16.dp, bottomStart = 16.dp, bottomEnd = 28.dp)
                Box(
                    modifier = Modifier
                        .size(82.dp)
                        .neubrutalShadow(enabled = true, cornerRadius = 22.dp, offsetY = 3.5.dp, offsetX = 2.5.dp)
                        .clip(mascotPodShape)
                        .background(IxhCream, mascotPodShape)
                        .border(2.4.dp, IxhInk, mascotPodShape),
                    contentAlignment = Alignment.Center
                ) {
                    IxhBlobMascot(
                        expression = blobExpression,
                        size = 72.dp
                    )
                }
            }

            Spacer(modifier = Modifier.height(13.dp))
            // Integrated High-Contrast Global Search Bar Trigger inside the Organic Greeting Card
            val searchDockShape = RoundedCornerShape(topStart = 16.dp, topEnd = 12.dp, bottomStart = 12.dp, bottomEnd = 18.dp)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp)
                    .neubrutalShadow(enabled = true, cornerRadius = 15.dp, offsetY = 3.dp, offsetX = 2.dp)
                    .clip(searchDockShape)
                    .background(IxhWhite, searchDockShape)
                    .border(2.2.dp, IxhInk, searchDockShape)
                    .clickable(onClick = onSearchBarClick)
                    .padding(horizontal = 12.dp)
                    .testTag("dashboard_global_search_bar"),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Search,
                        contentDescription = "Cari",
                        tint = IxhInk,
                        modifier = Modifier.size(19.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Cari siswa, tugas, jadwal, kas, pengumuman...",
                        style = MaterialTheme.typography.bodySmall,
                        color = IxhTextSecondary,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(9.dp))
                        .background(IxhInk)
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "CARI",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.ExtraBold,
                        color = IxhYellow,
                        fontSize = 9.5.sp,
                        maxLines = 1
                    )
                }
            }
        }
    }
}

/**
 * Purpose-Built Sculpted Organic Module Banner for Domain Screens
 * (Absensi, Kas, Jadwal, Tugas, Pengumuman, Pencarian, Backup, Audit Log, Bot).
 */
@Composable
fun IxhOrganicModuleBanner(
    eyebrowTag: String,
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
    iconType: IxhSystemIconType? = null,
    containerColor: Color = IxhYellow,
    accentWaveColor: Color = Color(0xFFFFE47A),
    rightBadgeContent: (@Composable () -> Unit)? = null,
    trailingContent: (@Composable () -> Unit)? = rightBadgeContent
) {
    val bannerShape = RoundedCornerShape(topStart = 26.dp, topEnd = 14.dp, bottomStart = 14.dp, bottomEnd = 30.dp)
    Box(
        modifier = modifier
            .fillMaxWidth()
            .neubrutalShadow(enabled = true, cornerRadius = 22.dp, offsetY = 4.dp, offsetX = 3.dp)
            .clip(bannerShape)
            .background(containerColor, bannerShape)
            .drawBehind {
                val w = size.width
                val h = size.height
                val wave = androidx.compose.ui.graphics.Path().apply {
                    moveTo(w * 0.42f, 0f)
                    cubicTo(
                        w * 0.58f, h * 0.35f,
                        w * 0.76f, h * 0.25f,
                        w, h * 0.68f
                    )
                    lineTo(w, 0f)
                    close()
                }
                drawPath(wave, color = accentWaveColor.copy(alpha = 0.78f))

                val contour = androidx.compose.ui.graphics.Path().apply {
                    moveTo(w * 0.42f, 0f)
                    cubicTo(
                        w * 0.58f, h * 0.35f,
                        w * 0.76f, h * 0.25f,
                        w, h * 0.68f
                    )
                }
                drawPath(
                    path = contour,
                    color = IxhInk.copy(alpha = 0.18f),
                    style = Stroke(width = 1.8.dp.toPx(), cap = StrokeCap.Round)
                )
            }
            .border(2.4.dp, IxhInk, bannerShape)
            .padding(horizontal = 15.dp, vertical = 13.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    if (iconType != null) {
                        Box(
                            modifier = Modifier
                                .size(26.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(IxhWhite)
                                .border(1.8.dp, IxhInk, RoundedCornerShape(8.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            IxhDualToneSystemIcon(iconType = iconType, size = 16.dp)
                        }
                    }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 6.dp, bottomStart = 6.dp, bottomEnd = 10.dp))
                            .background(IxhInk)
                            .padding(horizontal = 9.dp, vertical = 2.5.dp)
                    ) {
                        Text(
                            text = eyebrowTag.uppercase(),
                            fontSize = 9.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = IxhYellow
                        )
                    }
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = IxhInk
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    color = IxhInk.copy(alpha = 0.86f)
                )
            }
            if (trailingContent != null) {
                Spacer(modifier = Modifier.width(10.dp))
                trailingContent()
            }
        }
    }
}

/**
 * Sculpted Section Title with Charcoal-Bordered Yellow Organic Pill Marker
 */
@Composable
fun IxhSculptedSectionHeader(
    title: String,
    actionLabel: String? = null,
    onActionClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .width(8.dp)
                    .height(20.dp)
                    .clip(RoundedCornerShape(topStart = 6.dp, bottomEnd = 6.dp))
                    .background(IxhYellow)
                    .border(1.6.dp, IxhInk, RoundedCornerShape(topStart = 6.dp, bottomEnd = 6.dp))
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.ExtraBold,
                color = IxhInk
            )
        }
        if (actionLabel != null && onActionClick != null) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(IxhWhite)
                    .border(1.6.dp, IxhInk, RoundedCornerShape(10.dp))
                    .clickable(onClick = onActionClick)
                    .padding(horizontal = 9.dp, vertical = 3.dp)
            ) {
                Text(
                    text = actionLabel,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = IxhInk
                )
            }
        }
    }
}

@Composable
fun IxhSubScreenTopBar(
    title: String,
    onBack: () -> Unit,
    rightContent: (@Composable RowScope.() -> Unit)? = null
) {
    val backShape = RoundedCornerShape(topStart = 14.dp, topEnd = 10.dp, bottomStart = 10.dp, bottomEnd = 16.dp)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(38.dp)
                .neubrutalShadow(enabled = true, cornerRadius = 12.dp, offsetY = 2.5.dp, offsetX = 1.8.dp)
                .clip(backShape)
                .background(IxhYellowLight, backShape)
                .border(2.2.dp, IxhInk, backShape)
                .clickable(onClick = onBack)
                .testTag("subscreen_back_button"),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                contentDescription = "Kembali",
                tint = IxhInk,
                modifier = Modifier.size(19.dp)
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.ExtraBold,
            color = IxhInk,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )
        if (rightContent != null) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                content = rightContent
            )
        }
    }
}

enum class MainNavTab(
    val label: String,
    val icon: ImageVector,
    val systemIcon: IxhSystemIconType
) {
    BERANDA("Beranda", Icons.Outlined.Home, IxhSystemIconType.BERANDA),
    ABSENSI("Absensi", Icons.Outlined.CheckCircle, IxhSystemIconType.ABSENSI),
    KAS("Kas", Icons.Outlined.AccountBalanceWallet, IxhSystemIconType.KAS),
    JADWAL("Jadwal", Icons.Outlined.CalendarMonth, IxhSystemIconType.JADWAL),
    TUGAS("Tugas", Icons.Outlined.Assignment, IxhSystemIconType.TUGAS)
}

@Composable
fun IxhBottomNavigationBar(
    currentTab: MainNavTab,
    onTabSelected: (MainNavTab) -> Unit,
    modifier: Modifier = Modifier
) {
    val dockShape = RoundedCornerShape(topStart = 24.dp, topEnd = 16.dp, bottomStart = 16.dp, bottomEnd = 26.dp)
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(IxhCream)
            .windowInsetsPadding(WindowInsets.navigationBars)
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .neubrutalShadow(enabled = true, cornerRadius = 22.dp, offsetY = 4.dp, offsetX = 2.8.dp)
                .clip(dockShape)
                .background(IxhWhite, dockShape)
                .drawBehind {
                    val w = size.width
                    val h = size.height
                    val dockWave = androidx.compose.ui.graphics.Path().apply {
                        moveTo(0f, h * 0.65f)
                        cubicTo(w * 0.30f, h * 0.40f, w * 0.70f, h * 0.90f, w, h * 0.55f)
                        lineTo(w, h)
                        lineTo(0f, h)
                        close()
                    }
                    drawPath(dockWave, color = IxhYellowLight.copy(alpha = 0.45f))
                }
                .border(2.4.dp, IxhInk, dockShape)
                .padding(horizontal = 6.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            MainNavTab.entries.forEach { tab ->
                val selected = tab == currentTab
                val pillShape = RoundedCornerShape(topStart = 16.dp, topEnd = 10.dp, bottomStart = 10.dp, bottomEnd = 18.dp)
                Column(
                    modifier = Modifier
                        .then(
                            if (selected) {
                                Modifier.neubrutalShadow(
                                    enabled = true,
                                    cornerRadius = 14.dp,
                                    offsetY = 2.dp,
                                    offsetX = 1.5.dp
                                )
                            } else Modifier
                        )
                        .clip(pillShape)
                        .background(if (selected) IxhYellow else Color.Transparent)
                        .then(
                            if (selected) Modifier.border(2.dp, IxhInk, pillShape)
                            else Modifier
                        )
                        .clickable { onTabSelected(tab) }
                        .padding(horizontal = 12.dp, vertical = 5.dp)
                        .testTag("nav_${tab.name.lowercase()}"),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    IxhDualToneSystemIcon(
                        iconType = tab.systemIcon,
                        size = 22.dp
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = tab.label,
                        fontSize = 10.sp,
                        fontWeight = if (selected) FontWeight.ExtraBold else FontWeight.Bold,
                        color = IxhInk
                    )
                }
            }
        }
    }
}
