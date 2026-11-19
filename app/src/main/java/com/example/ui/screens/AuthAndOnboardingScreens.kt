package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material.icons.outlined.Badge
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.PhoneAndroid
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material.icons.outlined.SmartToy
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.PasswordSecurity
import com.example.data.local.UserEntity
import com.example.ui.components.BlobExpression
import com.example.ui.components.ChipTone
import com.example.ui.components.IxhAlertBanner
import com.example.ui.components.IxhBlobMascot
import com.example.ui.components.IxhBlobMascotSlot
import com.example.ui.components.IxhBlobSlotStyle
import com.example.ui.components.IxhCard
import com.example.ui.components.IxhChipStyle
import com.example.ui.components.IxhCircleIcon
import com.example.ui.components.IxhDsChip
import com.example.ui.components.IxhFeedbackType
import com.example.ui.components.IxhLoadingIndicator
import com.example.ui.components.IxhNeubrutalCheckbox
import com.example.ui.components.IxhPasswordField
import com.example.ui.components.IxhPrimaryButton
import com.example.ui.components.IxhSecondaryButton
import com.example.ui.components.IxhStateFeedbackBanner
import com.example.ui.components.IxhStatusChip
import com.example.ui.components.IxhSubScreenTopBar
import com.example.ui.components.IxhTextField
import com.example.ui.components.IxhWaBubblePreview
import com.example.ui.components.ixhBrandGradientBackground
import com.example.ui.components.ixhTactileShadow
import com.example.ui.theme.IxhAmber
import com.example.ui.theme.IxhBackgroundCream
import com.example.ui.theme.IxhBorder
import com.example.ui.theme.IxhCharcoal
import com.example.ui.theme.IxhCream
import com.example.ui.theme.IxhElevationLevel
import com.example.ui.theme.IxhGreen
import com.example.ui.theme.IxhInk
import com.example.ui.theme.IxhPrimaryYellow
import com.example.ui.theme.IxhRed
import com.example.ui.theme.IxhSecondaryYellow
import com.example.ui.theme.IxhSurfaceMuted
import com.example.ui.theme.IxhSurfacePrimary
import com.example.ui.theme.IxhTextSecondary
import com.example.ui.theme.IxhWhite
import com.example.ui.theme.IxhYellow
import com.example.ui.theme.IxhYellowLight
import com.example.ui.theme.IxhYellowSoft
import com.example.ui.theme.LocalIxhDesign
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(onFinished: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(1200)
        onFinished()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .ixhBrandGradientBackground(isHeroCard = false)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        IxhBlobMascot(
            expression = BlobExpression.SMILE,
            size = 136.dp,
            animateBounce = true
        )
        Spacer(modifier = Modifier.height(20.dp))
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(18.dp))
                .background(IxhWhite)
                .border(2.5.dp, IxhInk, RoundedCornerShape(18.dp))
                .padding(horizontal = 24.dp, vertical = 14.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "IX-H HUB 1.0",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = IxhInk
                )
                Text(
                    text = "SMPN 1 Bojongsari (Spensari) • Kelas IX-H",
                    style = MaterialTheme.typography.bodySmall,
                    color = IxhTextSecondary
                )
            }
        }
        Spacer(modifier = Modifier.height(28.dp))
        Text(
            text = "Memeriksa sesi & database...",
            style = MaterialTheme.typography.labelMedium,
            color = IxhInk
        )
        Spacer(modifier = Modifier.height(8.dp))
        LinearProgressIndicator(
            modifier = Modifier
                .width(160.dp)
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp)),
            color = IxhInk,
            trackColor = IxhYellowLight
        )
    }
}

/**
 * FIRST-RUN ADMIN SETUP SCREEN (Master Prompt §7 — Alur Pembuatan Admin Pertama).
 *
 * Ditampilkan secara otomatis ketika aplikasi pertama kali dijalankan dan tabel `users`
 * belum memiliki akun dengan role `ADMIN`.
 * Begitu Admin pertama berhasil dibuat, layar ini dikunci dan tidak dapat diakses kembali secara publik.
 */
@Composable
fun FirstRunAdminSetupScreen(
    onCreateFirstAdmin: (
        fullName: String,
        username: String,
        phoneWa: String,
        password: String,
        subRoleTitle: String,
        onResult: (Result<UserEntity>) -> Unit
    ) -> Unit,
    onSetupCompleted: (UserEntity) -> Unit
) {
    val tokens = LocalIxhDesign.current
    val focusManager = LocalFocusManager.current

    var fullName by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var phoneWa by remember { mutableStateOf("") }
    var subRoleTitle by remember { mutableStateOf("Ketua Kelas & Admin") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    var isInputFocused by remember { mutableStateOf(false) }
    var isPasswordFocused by remember { mutableStateOf(false) }
    var hasToggledPassword by remember { mutableStateOf(false) }
    var isSubmitting by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var successMessage by remember { mutableStateOf<String?>(null) }
    var animationTriggerKey by remember { mutableIntStateOf(0) }

    val isUsernameValid = remember(username) {
        val clean = username.trim()
        clean.length >= 3 && !clean.contains(" ") && !clean.contains("@")
    }
    val hasMinLength = password.length >= 8
    val hasLetterAndNumber = password.any { it.isLetter() } && password.any { it.isDigit() }
    val isConfirmMatch = password.isNotBlank() && password == confirmPassword

    val activeBlobExpression = when {
        isSubmitting -> BlobExpression.THINKING
        successMessage != null -> BlobExpression.EXCITED
        errorMessage != null -> BlobExpression.SAD
        passwordVisible -> BlobExpression.PASSWORD_SHOW
        hasToggledPassword && !passwordVisible -> BlobExpression.PASSWORD_HIDE
        isPasswordFocused && !passwordVisible -> BlobExpression.PASSWORD_HIDE
        isInputFocused && (fullName.isNotBlank() || username.isNotBlank()) -> BlobExpression.FOCUS
        else -> BlobExpression.IDLE
    }

    val handleSubmit = {
        if (!isSubmitting) {
            errorMessage = null
            successMessage = null
            val cleanName = fullName.trim()
            val cleanUser = username.trim().lowercase()
            val cleanPhone = phoneWa.trim()

            when {
                cleanName.length < 3 -> {
                    errorMessage = "Nama lengkap Admin minimal 3 karakter."
                    animationTriggerKey++
                }
                cleanUser.length < 3 || cleanUser.contains(" ") || cleanUser.contains("@") -> {
                    errorMessage = "Username minimal 3 karakter tanpa spasi atau simbol '@'."
                    animationTriggerKey++
                }
                cleanPhone.length < 9 -> {
                    errorMessage = "Masukkan nomor WhatsApp yang valid (minimal 9 digit)."
                    animationTriggerKey++
                }
                !hasMinLength || !hasLetterAndNumber -> {
                    errorMessage = "Password wajib minimal 8 karakter dengan kombinasi huruf dan angka."
                    animationTriggerKey++
                }
                !isConfirmMatch -> {
                    errorMessage = "Konfirmasi password tidak cocok."
                    animationTriggerKey++
                }
                else -> {
                    focusManager.clearFocus()
                    isSubmitting = true
                    animationTriggerKey++
                    onCreateFirstAdmin(
                        cleanName,
                        cleanUser,
                        cleanPhone,
                        password,
                        subRoleTitle.trim().ifBlank { "Ketua Kelas & Admin" }
                    ) { result ->
                        isSubmitting = false
                        result.fold(
                            onSuccess = { adminUser ->
                                successMessage = "Akun Admin @${adminUser.username} berhasil dibuat!"
                                animationTriggerKey++
                                onSetupCompleted(adminUser)
                            },
                            onFailure = { err ->
                                errorMessage = err.message ?: "Gagal menyimpan akun Admin pertama."
                                animationTriggerKey++
                            }
                        )
                    }
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .ixhBrandGradientBackground(isHeroCard = false)
            .imePadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 14.dp)
                .testTag("first_run_admin_scroll_container"),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoginBrandHeader()

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 460.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                val cardShape = RoundedCornerShape(
                    topStart = 30.dp,
                    topEnd = 18.dp,
                    bottomStart = 18.dp,
                    bottomEnd = 32.dp
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 120.dp)
                        .ixhTactileShadow(
                            level = IxhElevationLevel.HIGH,
                            cornerRadius = 26.dp,
                            shadowColor = IxhCharcoal.copy(alpha = 0.88f)
                        )
                        .clip(cardShape)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(IxhSurfacePrimary, IxhBackgroundCream)
                            ),
                            cardShape
                        )
                        .drawBehind {
                            val w = size.width
                            val h = size.height
                            val topWave = Path().apply {
                                moveTo(0f, 0f)
                                lineTo(w, 0f)
                                lineTo(w, h * 0.14f)
                                cubicTo(w * 0.72f, h * 0.08f, w * 0.32f, h * 0.18f, 0f, h * 0.11f)
                                close()
                            }
                            drawPath(topWave, color = IxhYellowSoft.copy(alpha = 0.65f))
                            val bottomWave = Path().apply {
                                moveTo(w * 0.52f, h)
                                cubicTo(w * 0.66f, h * 0.90f, w * 0.85f, h * 0.88f, w, h * 0.82f)
                                lineTo(w, h)
                                close()
                            }
                            drawPath(bottomWave, color = IxhPrimaryYellow.copy(alpha = 0.35f))
                        }
                        .border(2.6.dp, IxhCharcoal, cardShape)
                        .padding(start = 20.dp, end = 20.dp, top = 24.dp, bottom = 22.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    IxhDsChip(
                        text = "SETUP AWAL • DATABASE KOSONG",
                        style = IxhChipStyle.WARNING
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Buat Admin Pertama",
                        style = MaterialTheme.typography.headlineMedium,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = IxhCharcoal,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(5.dp))
                    Box(
                        modifier = Modifier
                            .width(132.dp)
                            .height(6.dp)
                            .clip(CircleShape)
                            .background(IxhPrimaryYellow)
                            .border(1.5.dp, IxhCharcoal, CircleShape)
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Belum ada akun di sistem IX-H HUB 1.0.\nBuat akun Admin utama untuk memulai manajemen kelas.",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.SemiBold,
                        color = IxhTextSecondary,
                        textAlign = TextAlign.Center,
                        lineHeight = 18.sp
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    IxhTextField(
                        value = fullName,
                        onValueChange = {
                            fullName = it
                            errorMessage = null
                        },
                        placeholder = "Nama Lengkap Admin",
                        leadingIcon = Icons.Outlined.Badge,
                        isValid = fullName.trim().length >= 3,
                        modifier = Modifier.onFocusChanged { isInputFocused = it.isFocused },
                        testTag = "first_admin_fullname_input"
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    IxhTextField(
                        value = username,
                        onValueChange = {
                            username = it
                            errorMessage = null
                        },
                        placeholder = "Username Admin (contoh: admin.ixh)",
                        leadingIcon = Icons.Outlined.Person,
                        isValid = isUsernameValid,
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.None,
                            autoCorrectEnabled = false,
                            keyboardType = KeyboardType.Ascii,
                            imeAction = ImeAction.Next
                        ),
                        modifier = Modifier.onFocusChanged { isInputFocused = it.isFocused },
                        testTag = "first_admin_username_input"
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    IxhTextField(
                        value = phoneWa,
                        onValueChange = {
                            phoneWa = it
                            errorMessage = null
                        },
                        placeholder = "Nomor WhatsApp (08xx / +62)",
                        leadingIcon = Icons.Outlined.PhoneAndroid,
                        isValid = phoneWa.trim().length >= 9,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Phone,
                            imeAction = ImeAction.Next
                        ),
                        modifier = Modifier.onFocusChanged { isInputFocused = it.isFocused },
                        testTag = "first_admin_phone_input"
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    IxhPasswordField(
                        value = password,
                        onValueChange = {
                            password = it
                            errorMessage = null
                        },
                        passwordVisible = passwordVisible,
                        onTogglePasswordVisible = {
                            passwordVisible = !passwordVisible
                            hasToggledPassword = true
                            animationTriggerKey++
                        },
                        placeholder = "Password Admin (Min. 8 karakter)",
                        modifier = Modifier.onFocusChanged { isPasswordFocused = it.isFocused },
                        testTag = "first_admin_password_input",
                        toggleTestTag = "first_admin_toggle_password"
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    IxhPasswordField(
                        value = confirmPassword,
                        onValueChange = {
                            confirmPassword = it
                            errorMessage = null
                        },
                        passwordVisible = passwordVisible,
                        onTogglePasswordVisible = {
                            passwordVisible = !passwordVisible
                            hasToggledPassword = true
                            animationTriggerKey++
                        },
                        placeholder = "Konfirmasi Password Admin",
                        modifier = Modifier.onFocusChanged { isPasswordFocused = it.isFocused },
                        testTag = "first_admin_confirm_password_input",
                        toggleTestTag = "first_admin_toggle_confirm_password"
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Password checklist pill box
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(IxhYellowSoft.copy(alpha = 0.55f))
                            .border(1.2.dp, IxhCharcoal.copy(alpha = 0.35f), RoundedCornerShape(12.dp))
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        PasswordRuleRow(
                            label = "Minimal 8 karakter",
                            satisfied = hasMinLength
                        )
                        PasswordRuleRow(
                            label = "Kombinasi huruf dan angka",
                            satisfied = hasLetterAndNumber
                        )
                        PasswordRuleRow(
                            label = "Konfirmasi password cocok",
                            satisfied = isConfirmMatch
                        )
                    }

                    AnimatedVisibility(
                        visible = errorMessage != null,
                        enter = fadeIn() + expandVertically(),
                        exit = fadeOut() + shrinkVertically()
                    ) {
                        if (errorMessage != null) {
                            Column {
                                Spacer(modifier = Modifier.height(12.dp))
                                IxhStateFeedbackBanner(
                                    message = errorMessage!!,
                                    type = IxhFeedbackType.ERROR,
                                    modifier = Modifier.testTag("first_admin_error_banner")
                                )
                            }
                        }
                    }

                    AnimatedVisibility(
                        visible = successMessage != null,
                        enter = fadeIn() + expandVertically(),
                        exit = fadeOut() + shrinkVertically()
                    ) {
                        if (successMessage != null) {
                            Column {
                                Spacer(modifier = Modifier.height(12.dp))
                                IxhStateFeedbackBanner(
                                    message = successMessage!!,
                                    type = IxhFeedbackType.SUCCESS,
                                    modifier = Modifier.testTag("first_admin_success_banner")
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    LoginPrimaryMasukButton(
                        text = "Buat Akun Admin Pertama",
                        isLoading = isSubmitting,
                        onClick = handleSubmit,
                        testTag = "first_admin_submit_button"
                    )
                }

                LoginHeroBlobStage(
                    expression = activeBlobExpression,
                    animationTriggerKey = animationTriggerKey,
                    entryOffsetY = 0f,
                    isSleepMode = false,
                    onTapBlob = { animationTriggerKey++ }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun PasswordRuleRow(label: String, satisfied: Boolean) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .clip(CircleShape)
                .background(if (satisfied) IxhGreen else IxhSurfacePrimary)
                .border(1.5.dp, if (satisfied) IxhCharcoal else IxhBorder, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            if (satisfied) {
                Icon(
                    imageVector = Icons.Outlined.Check,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(11.dp)
                )
            }
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = if (satisfied) FontWeight.Bold else FontWeight.Medium,
            color = IxhCharcoal
        )
    }
}

/**
 * TAHAP 3 — LOGIN UI IX-H HUB 1.0 (Master Prompt §8, referensi_login.png).
 *
 * Bersih dari kredensial demo dan akun dummy:
 * - Hanya mengautentikasi pengguna yang benar-benar terdaftar di Room Database (`registeredUsers`)
 *   menggunakan verifikasi hash password SHA-256 (`PasswordSecurity.verifyPassword`).
 * - Seluruh 10 ekspresi 3D-Glossy Blob Mascot terhubung ke interaksi form.
 */
@Composable
fun LoginScreen(
    onLoginSuccess: (String) -> Unit,
    onForgotPassword: () -> Unit,
    onLockedAccount: () -> Unit,
    onStartOnboarding: (String) -> Unit,
    registeredUsers: List<UserEntity> = emptyList(),
    onNavigateFirstRunSetup: (() -> Unit)? = null,
    initialUsername: String = "",
    initialPassword: String = "",
    simulateLoadingDelayMs: Long = 0L
) {
    val focusManager = LocalFocusManager.current
    val coroutineScope = rememberCoroutineScope()

    var username by remember { mutableStateOf(initialUsername) }
    var password by remember { mutableStateOf(initialPassword) }
    var passwordVisible by remember { mutableStateOf(false) }
    var rememberMe by remember { mutableStateOf(true) }

    var isUsernameFocused by remember { mutableStateOf(false) }
    var isPasswordFocused by remember { mutableStateOf(false) }
    var hasTypedUsername by remember { mutableStateOf(false) }
    var hasToggledPassword by remember { mutableStateOf(false) }
    var isIdleSleepPreview by remember { mutableStateOf(false) }

    var isSubmitting by remember { mutableStateOf(false) }
    var loadingProgress by remember { mutableFloatStateOf(0.72f) }
    var failedAttempts by remember { mutableIntStateOf(0) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var successMessage by remember { mutableStateOf<String?>(null) }
    var animationTriggerKey by remember { mutableIntStateOf(0) }

    val headerAlpha = remember { Animatable(1f) }
    val blobEntryOffsetY = remember { Animatable(0f) }
    val cardAlpha = remember { Animatable(1f) }
    var initialGreetingExpression by remember { mutableStateOf<BlobExpression?>(null) }

    LaunchedEffect(Unit) {
        launch {
            blobEntryOffsetY.snapTo(24f)
            blobEntryOffsetY.animateTo(
                targetValue = 0f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessMediumLow
                )
            )
        }
    }

    val normalizedInputUsername = remember(username) {
        username.trim().lowercase()
    }

    val matchedUser = remember(normalizedInputUsername, registeredUsers) {
        if (normalizedInputUsername.length < 3 || normalizedInputUsername.contains("@")) {
            null
        } else {
            registeredUsers.firstOrNull {
                it.username.equals(normalizedInputUsername, ignoreCase = true)
            }
        }
    }

    val isUsernameValid = remember(username, matchedUser) {
        val trimmed = username.trim()
        trimmed.length >= 3 && !trimmed.contains("@") && !trimmed.contains(" ") && matchedUser != null
    }

    val activeBlobExpression = when {
        isIdleSleepPreview -> BlobExpression.SLEEP
        isSubmitting -> BlobExpression.THINKING
        successMessage != null -> BlobExpression.EXCITED
        errorMessage != null -> BlobExpression.SAD
        passwordVisible -> BlobExpression.PASSWORD_SHOW
        hasToggledPassword && !passwordVisible -> BlobExpression.PASSWORD_HIDE
        isPasswordFocused && !passwordVisible -> BlobExpression.PASSWORD_HIDE
        (isUsernameFocused && username.isNotBlank()) || (hasTypedUsername && username.isNotBlank() && password.isEmpty()) ->
            BlobExpression.FOCUS
        initialGreetingExpression != null -> initialGreetingExpression!!
        else -> BlobExpression.IDLE
    }

    val executeLogin: (String, String) -> Unit = { targetUname, targetPass ->
        if (!isSubmitting) {
            errorMessage = null
            successMessage = null
            isIdleSleepPreview = false

            val rawUname = targetUname.trim()
            val rawPass = targetPass

            when {
                rawUname.isBlank() -> {
                    errorMessage = "Username wajib diisi."
                    animationTriggerKey++
                }
                rawUname.contains("@") -> {
                    errorMessage = "Gunakan username akun (bukan email)."
                    animationTriggerKey++
                }
                rawUname.length < 3 -> {
                    errorMessage = "Username minimal 3 karakter."
                    animationTriggerKey++
                }
                rawPass.isBlank() -> {
                    errorMessage = "Password wajib diisi."
                    animationTriggerKey++
                }
                registeredUsers.isEmpty() -> {
                    errorMessage = "Belum ada akun terdaftar. Buat akun Admin pertama terlebih dahulu."
                    animationTriggerKey++
                }
                else -> {
                    val resolvedUname = rawUname.lowercase()
                    val account = registeredUsers.firstOrNull {
                        it.username.equals(resolvedUname, ignoreCase = true)
                    }

                    val isPasswordValid = if (account != null) {
                        when {
                            account.passwordHash.isNotBlank() ->
                                PasswordSecurity.verifyPassword(account.username, rawPass, account.passwordHash)
                            account.tempPassword.isNotBlank() ->
                                rawPass == account.tempPassword
                            else -> rawPass.length >= 8
                        }
                    } else {
                        false
                    }

                    if (account == null || !isPasswordValid) {
                        failedAttempts++
                        val remaining = (5 - failedAttempts).coerceAtLeast(0)
                        if (remaining == 0) {
                            onLockedAccount()
                        } else {
                            errorMessage = if (account == null) {
                                "Username \"$rawUname\" belum terdaftar di IX-H HUB. Sisa percobaan: $remaining"
                            } else {
                                "Username atau password salah. Sisa percobaan: $remaining"
                            }
                            animationTriggerKey++
                        }
                    } else if (!account.isActiveAccount) {
                        errorMessage = "Akun @${account.username} sedang dinonaktifkan oleh Admin."
                        animationTriggerKey++
                    } else {
                        focusManager.clearFocus()
                        if (simulateLoadingDelayMs > 0L) {
                            isSubmitting = true
                            loadingProgress = 0.72f
                            animationTriggerKey++
                            coroutineScope.launch {
                                delay(simulateLoadingDelayMs)
                                isSubmitting = false
                                successMessage = "Berhasil masuk sebagai ${account.fullName} (${account.role})!"
                                animationTriggerKey++
                                if (account.mustChangePassword) {
                                    onStartOnboarding(account.username)
                                } else {
                                    onLoginSuccess(account.username)
                                }
                            }
                        } else {
                            successMessage = "Berhasil masuk sebagai ${account.fullName} (${account.role})!"
                            animationTriggerKey++
                            if (account.mustChangePassword) {
                                onStartOnboarding(account.username)
                            } else {
                                onLoginSuccess(account.username)
                            }
                        }
                    }
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .ixhBrandGradientBackground(isHeroCard = false)
            .imePadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 14.dp)
                .testTag("login_scroll_container"),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 1. BRAND HEADER (Sesuai referensi_login.png)
            LoginBrandHeader(
                modifier = Modifier.graphicsLayer { alpha = headerAlpha.value }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 2. HERO BLOB MASCOT 3D-GLOSSY + MAIN LOGIN CARD STACK
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 460.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 120.dp)
                        .graphicsLayer { alpha = cardAlpha.value },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LoginMainFormCard(
                        username = username,
                        onUsernameChange = {
                            username = it
                            hasTypedUsername = true
                            isIdleSleepPreview = false
                            errorMessage = null
                            successMessage = null
                        },
                        isUsernameValid = isUsernameValid,
                        onUsernameFocusChanged = { focused ->
                            isUsernameFocused = focused
                            if (focused) isIdleSleepPreview = false
                        },
                        password = password,
                        onPasswordChange = {
                            password = it
                            isIdleSleepPreview = false
                            errorMessage = null
                            successMessage = null
                        },
                        passwordVisible = passwordVisible,
                        onTogglePasswordVisible = {
                            passwordVisible = !passwordVisible
                            hasToggledPassword = true
                            isIdleSleepPreview = false
                            animationTriggerKey++
                        },
                        onPasswordFocusChanged = { focused ->
                            isPasswordFocused = focused
                            if (focused) isIdleSleepPreview = false
                        },
                        rememberMe = rememberMe,
                        onRememberMeChange = { rememberMe = it },
                        isSubmitting = isSubmitting,
                        loadingProgress = loadingProgress,
                        errorMessage = errorMessage,
                        successMessage = successMessage,
                        onSubmitLogin = { executeLogin(username, password) },
                        onForgotPassword = onForgotPassword
                    )
                }

                LoginHeroBlobStage(
                    expression = activeBlobExpression,
                    animationTriggerKey = animationTriggerKey,
                    entryOffsetY = blobEntryOffsetY.value,
                    isSleepMode = isIdleSleepPreview,
                    onTapBlob = {
                        initialGreetingExpression = if (initialGreetingExpression == BlobExpression.SMILE) {
                            BlobExpression.BLINK
                        } else {
                            BlobExpression.SMILE
                        }
                        animationTriggerKey++
                    }
                )
            }

            if (registeredUsers.isEmpty() && onNavigateFirstRunSetup != null) {
                Spacer(modifier = Modifier.height(14.dp))
                IxhCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .widthIn(max = 460.dp),
                    backgroundColor = IxhYellowSoft
                ) {
                    Text(
                        text = "Belum Ada Akun Admin",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.ExtraBold,
                        color = IxhCharcoal
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Database aplikasi masih kosong. Buat akun Admin pertama terlebih dahulu untuk mulai menggunakan IX-H HUB 1.0.",
                        style = MaterialTheme.typography.bodySmall,
                        color = IxhTextSecondary
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    IxhPrimaryButton(
                        text = "Buka Setup Admin Pertama",
                        onClick = onNavigateFirstRunSetup,
                        testTag = "login_open_first_admin_setup_button"
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
internal fun LoginBrandHeader(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 4.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IxhBlobMascotSlot(
            slotStyle = IxhBlobSlotStyle.APP_BADGE_3D,
            size = 54.dp
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(horizontalAlignment = Alignment.Start) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "IX-H HUB",
                    style = MaterialTheme.typography.headlineLarge,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = IxhCharcoal
                )
                Spacer(modifier = Modifier.width(6.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(IxhPrimaryYellow)
                        .border(1.8.dp, IxhCharcoal, RoundedCornerShape(10.dp))
                        .padding(horizontal = 8.dp, vertical = 1.dp)
                ) {
                    Text(
                        text = "1.0",
                        style = MaterialTheme.typography.titleLarge,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = IxhCharcoal
                    )
                }
            }
            Text(
                text = "SMPN 1 Bojongsari (Spensari)",
                style = MaterialTheme.typography.bodySmall,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = IxhCharcoal
            )
            Text(
                text = "Kelas IX-H",
                style = MaterialTheme.typography.bodySmall,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = IxhCharcoal
            )
        }
    }
}

@Composable
internal fun LoginHeroBlobStage(
    expression: BlobExpression,
    animationTriggerKey: Int,
    entryOffsetY: Float,
    isSleepMode: Boolean,
    onTapBlob: () -> Unit
) {
    val hasBuiltInLeftAccessory = expression == BlobExpression.SMILE ||
        expression == BlobExpression.BLINK ||
        expression == BlobExpression.SLEEP ||
        expression == BlobExpression.PASSWORD_HIDE ||
        expression == BlobExpression.EXCITED

    val hasBuiltInRightAccessory = expression == BlobExpression.THINKING ||
        expression == BlobExpression.EXCITED

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(156.dp)
            .graphicsLayer { translationY = entryOffsetY },
        contentAlignment = Alignment.TopCenter
    ) {
        Canvas(
            modifier = Modifier
                .size(width = 276.dp, height = 148.dp)
                .align(Alignment.TopCenter)
        ) {
            val w = size.width
            val h = size.height
            val strokePx = 1.85.dp.toPx()
            val strokeStyle = Stroke(
                width = strokePx,
                cap = StrokeCap.Round,
                join = StrokeJoin.Round
            )

            if (!isSleepMode && !hasBuiltInLeftAccessory) {
                val ray1 = Path().apply {
                    moveTo(w * 0.14f, h * 0.34f)
                    lineTo(w * 0.05f, h * 0.24f)
                    lineTo(w * 0.09f, h * 0.18f)
                    lineTo(w * 0.18f, h * 0.29f)
                    close()
                }
                drawPath(ray1, color = IxhPrimaryYellow)
                drawPath(ray1, color = IxhCharcoal, style = strokeStyle)

                val ray2 = Path().apply {
                    moveTo(w * 0.21f, h * 0.24f)
                    lineTo(w * 0.16f, h * 0.08f)
                    lineTo(w * 0.22f, h * 0.05f)
                    lineTo(w * 0.26f, h * 0.21f)
                    close()
                }
                drawPath(ray2, color = IxhPrimaryYellow)
                drawPath(ray2, color = IxhCharcoal, style = strokeStyle)

                val ray3 = Path().apply {
                    moveTo(w * 0.12f, h * 0.45f)
                    lineTo(w * 0.02f, h * 0.43f)
                    lineTo(w * 0.03f, h * 0.37f)
                    lineTo(w * 0.13f, h * 0.40f)
                    close()
                }
                drawPath(ray3, color = IxhPrimaryYellow)
                drawPath(ray3, color = IxhCharcoal, style = strokeStyle)
            }

            if (!hasBuiltInRightAccessory) {
                fun drawFourPointStar(cx: Float, cy: Float, outerR: Float, innerR: Float) {
                    val star = Path().apply {
                        moveTo(cx, cy - outerR)
                        lineTo(cx + innerR, cy - innerR)
                        lineTo(cx + outerR, cy)
                        lineTo(cx + innerR, cy + innerR)
                        lineTo(cx, cy + outerR)
                        lineTo(cx - innerR, cy + innerR)
                        lineTo(cx - outerR, cy)
                        lineTo(cx - innerR, cy - innerR)
                        close()
                    }
                    drawPath(star, color = IxhPrimaryYellow)
                    drawPath(star, color = IxhCharcoal, style = strokeStyle)
                }

                drawFourPointStar(
                    cx = w * 0.89f,
                    cy = h * 0.46f,
                    outerR = 11.dp.toPx(),
                    innerR = 3.6.dp.toPx()
                )
                drawFourPointStar(
                    cx = w * 0.93f,
                    cy = h * 0.65f,
                    outerR = 6.5.dp.toPx(),
                    innerR = 2.2.dp.toPx()
                )
            }
        }

        IxhBlobMascot(
            expression = expression,
            size = 152.dp,
            animateBounce = expression == BlobExpression.SMILE || expression == BlobExpression.EXCITED,
            animationTriggerKey = animationTriggerKey,
            modifier = Modifier
                .clickable(onClick = onTapBlob)
                .testTag("login_hero_blob")
        )
    }
}

@Composable
private fun LoginMainFormCard(
    username: String,
    onUsernameChange: (String) -> Unit,
    isUsernameValid: Boolean,
    onUsernameFocusChanged: (Boolean) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    passwordVisible: Boolean,
    onTogglePasswordVisible: () -> Unit,
    onPasswordFocusChanged: (Boolean) -> Unit,
    rememberMe: Boolean,
    onRememberMeChange: (Boolean) -> Unit,
    isSubmitting: Boolean,
    loadingProgress: Float,
    errorMessage: String?,
    successMessage: String?,
    onSubmitLogin: () -> Unit,
    onForgotPassword: () -> Unit
) {
    val cardShape = RoundedCornerShape(
        topStart = 30.dp,
        topEnd = 18.dp,
        bottomStart = 18.dp,
        bottomEnd = 32.dp
    )

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .ixhTactileShadow(
                    level = IxhElevationLevel.HIGH,
                    cornerRadius = 26.dp,
                    shadowColor = IxhCharcoal.copy(alpha = 0.88f)
                )
                .clip(cardShape)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            IxhSurfacePrimary,
                            IxhBackgroundCream
                        )
                    ),
                    cardShape
                )
                .drawBehind {
                    val w = size.width
                    val h = size.height
                    val topWave = Path().apply {
                        moveTo(0f, 0f)
                        lineTo(w, 0f)
                        lineTo(w, h * 0.16f)
                        cubicTo(w * 0.70f, h * 0.09f, w * 0.30f, h * 0.20f, 0f, h * 0.12f)
                        close()
                    }
                    drawPath(topWave, color = IxhYellowSoft.copy(alpha = 0.70f))
                    val bottomWave = Path().apply {
                        moveTo(w * 0.50f, h)
                        cubicTo(w * 0.65f, h * 0.88f, w * 0.84f, h * 0.86f, w, h * 0.78f)
                        lineTo(w, h)
                        close()
                    }
                    drawPath(bottomWave, color = IxhPrimaryYellow.copy(alpha = 0.38f))
                }
                .border(2.6.dp, IxhCharcoal, cardShape)
                .padding(
                    start = 20.dp,
                    end = 20.dp,
                    top = 24.dp,
                    bottom = 20.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Selamat Datang!",
                style = MaterialTheme.typography.headlineMedium,
                fontSize = 23.sp,
                fontWeight = FontWeight.ExtraBold,
                color = IxhCharcoal,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(5.dp))
            Box(
                modifier = Modifier
                    .width(156.dp)
                    .height(6.dp)
                    .clip(CircleShape)
                    .background(IxhPrimaryYellow)
                    .border(1.5.dp, IxhCharcoal, CircleShape)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Masuk untuk melanjutkan\naktivitas kelas IX-H.",
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 13.5.sp,
                fontWeight = FontWeight.SemiBold,
                color = IxhCharcoal,
                textAlign = TextAlign.Center,
                lineHeight = 19.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            IxhTextField(
                value = username,
                onValueChange = onUsernameChange,
                placeholder = "Username",
                leadingIcon = Icons.Outlined.Person,
                isValid = isUsernameValid,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrectEnabled = false,
                    keyboardType = KeyboardType.Ascii,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.onFocusChanged { onUsernameFocusChanged(it.isFocused) },
                testTag = "login_username_input"
            )

            Spacer(modifier = Modifier.height(12.dp))

            IxhPasswordField(
                value = password,
                onValueChange = onPasswordChange,
                passwordVisible = passwordVisible,
                onTogglePasswordVisible = onTogglePasswordVisible,
                placeholder = "Password",
                modifier = Modifier.onFocusChanged { onPasswordFocusChanged(it.isFocused) },
                testTag = "login_password_input",
                toggleTestTag = "toggle_password_visibility"
            )

            Spacer(modifier = Modifier.height(16.dp))

            LoginPrimaryMasukButton(
                text = "Masuk",
                isLoading = isSubmitting,
                onClick = onSubmitLogin,
                testTag = "login_submit_button"
            )

            AnimatedVisibility(
                visible = isSubmitting,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Column {
                    Spacer(modifier = Modifier.height(14.dp))
                    IxhLoadingIndicator(
                        label = "Memproses Login...",
                        progress = loadingProgress,
                        modifier = Modifier.testTag("login_loading_card")
                    )
                }
            }

            AnimatedVisibility(
                visible = errorMessage != null,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                if (errorMessage != null) {
                    Column {
                        Spacer(modifier = Modifier.height(12.dp))
                        IxhStateFeedbackBanner(
                            message = errorMessage,
                            type = IxhFeedbackType.ERROR,
                            modifier = Modifier.testTag("login_error_banner")
                        )
                    }
                }
            }

            AnimatedVisibility(
                visible = successMessage != null,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                if (successMessage != null) {
                    Column {
                        Spacer(modifier = Modifier.height(12.dp))
                        IxhStateFeedbackBanner(
                            message = successMessage,
                            type = IxhFeedbackType.SUCCESS,
                            modifier = Modifier.testTag("login_success_banner")
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IxhNeubrutalCheckbox(
                    checked = rememberMe,
                    onCheckedChange = onRememberMeChange,
                    label = "Ingat saya",
                    testTag = "login_remember_checkbox"
                )

                Text(
                    text = "|",
                    style = MaterialTheme.typography.titleMedium,
                    color = IxhCharcoal.copy(alpha = 0.45f)
                )

                Box(
                    modifier = Modifier
                        .heightIn(min = 48.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .clickable(onClick = onForgotPassword)
                        .padding(horizontal = 8.dp)
                        .testTag("login_help_button"),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Bantuan Login?",
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = IxhCharcoal
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 36.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            repeat(2) {
                Box(
                    modifier = Modifier
                        .width(10.dp)
                        .height(20.dp)
                        .clip(CircleShape)
                        .background(IxhPrimaryYellow)
                        .border(2.dp, IxhCharcoal, CircleShape)
                )
            }
        }
    }
}

@Composable
internal fun LoginPrimaryMasukButton(
    text: String,
    isLoading: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    testTag: String? = null
) {
    val tokens = LocalIxhDesign.current
    val pillShape = RoundedCornerShape(tokens.radius.full)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(54.dp)
            .let { if (testTag != null) it.testTag(testTag) else it }
            .ixhTactileShadow(
                level = IxhElevationLevel.MEDIUM,
                cornerRadius = tokens.radius.full,
                shadowColor = IxhCharcoal
            )
            .clip(pillShape)
            .background(
                Brush.horizontalGradient(
                    colors = listOf(
                        IxhPrimaryYellow,
                        IxhSecondaryYellow,
                        IxhPrimaryYellow
                    )
                ),
                pillShape
            )
            .border(tokens.border.thick, IxhCharcoal, pillShape)
            .clickable(enabled = !isLoading, onClick = onClick)
            .padding(horizontal = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = if (isLoading) "Memverifikasi..." else text,
            style = MaterialTheme.typography.titleLarge,
            fontSize = 16.sp,
            fontWeight = FontWeight.ExtraBold,
            color = IxhCharcoal
        )

        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .size(36.dp)
                .clip(CircleShape)
                .background(IxhCharcoal, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    color = IxhPrimaryYellow,
                    strokeWidth = 2.dp,
                    modifier = Modifier.size(18.dp)
                )
            } else {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.ArrowForward,
                    contentDescription = text,
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
fun ForgotPasswordScreen(
    onBackToLogin: () -> Unit,
    onRequestSent: (String) -> Unit
) {
    var uname by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(IxhCream)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        IxhSubScreenTopBar(title = "Bantuan & Reset Password", onBack = onBackToLogin)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            IxhBlobMascot(expression = BlobExpression.IDLE_OPEN, size = 104.dp)
        }
        Text(
            text = "Reset dilakukan oleh Admin Kelas",
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = "Masukkan username akun Anda. Permintaan reset password akan diteruskan ke Admin IX-H.",
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            color = IxhTextSecondary,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(14.dp))
        IxhTextField(
            value = uname,
            onValueChange = { uname = it },
            placeholder = "Masukkan username Anda",
            leadingIcon = Icons.Outlined.Person,
            isValid = uname.trim().length >= 3,
            testTag = "forgot_password_username_input"
        )
        Spacer(modifier = Modifier.height(12.dp))
        IxhPrimaryButton(
            text = "Kirim Permintaan ke Admin",
            icon = Icons.AutoMirrored.Outlined.Send,
            enabled = uname.trim().length >= 3,
            onClick = { onRequestSent(uname.trim()) }
        )
        Spacer(modifier = Modifier.height(16.dp))
        IxhCard {
            Text("Alur Reset Password", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            listOf(
                Triple("1", "Ajukan permintaan", "Admin menerima permintaan reset akun"),
                Triple("2", "Admin mereset password", "Melalui menu Kelola Pengguna"),
                Triple("3", "Masuk dengan password baru", "Gunakan password baru yang diberikan Admin")
            ).forEach { (num, t, sub) ->
                Row(
                    modifier = Modifier.padding(vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(IxhYellow)
                            .border(1.5.dp, IxhInk, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(num, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(t, style = MaterialTheme.typography.titleSmall)
                        Text(sub, style = MaterialTheme.typography.bodySmall, color = IxhTextSecondary)
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        IxhSecondaryButton(text = "Kembali ke Login", onClick = onBackToLogin)
    }
}

@Composable
fun LockedAccountScreen(onBackToLogin: () -> Unit, onContactAdmin: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(IxhCream)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(IxhYellowLight)
                .border(1.8.dp, IxhInk, RoundedCornerShape(16.dp))
                .padding(20.dp),
            contentAlignment = Alignment.Center
        ) {
            IxhBlobMascot(expression = BlobExpression.SAD_ERROR, size = 116.dp)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text("Akun Dikunci Sementara", style = MaterialTheme.typography.headlineMedium)
        Text(
            "Terlalu banyak percobaan login yang gagal.",
            style = MaterialTheme.typography.bodySmall,
            color = IxhTextSecondary
        )
        Spacer(modifier = Modifier.height(18.dp))
        Box(
            modifier = Modifier
                .size(110.dp)
                .clip(CircleShape)
                .background(IxhWhite)
                .border(4.dp, IxhRed, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("05:00", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.ExtraBold)
                Text("jeda keamanan", style = MaterialTheme.typography.labelSmall)
            }
        }
        Spacer(modifier = Modifier.height(22.dp))
        IxhPrimaryButton(text = "Hubungi Admin Kelas", onClick = onContactAdmin)
        Spacer(modifier = Modifier.height(10.dp))
        IxhSecondaryButton(text = "Kembali ke Login", onClick = onBackToLogin)
    }
}

@Composable
fun OnboardingFirstLoginScreen(
    username: String,
    onFinishOnboarding: () -> Unit
) {
    var step by remember { mutableIntStateOf(0) }
    var newPass by remember { mutableStateOf("") }
    var confirmPass by remember { mutableStateOf("") }
    var passVisible by remember { mutableStateOf(false) }
    var waConsentChecked by remember { mutableStateOf(true) }
    var selectedPhrase by remember { mutableStateOf("Tagihan kas saya") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(IxhCream)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        when (step) {
            0 -> {
                Text("Buat Password Baru", style = MaterialTheme.typography.headlineMedium)
                Text(
                    "Akun @$username wajib memperbarui password sementara dari Admin.",
                    style = MaterialTheme.typography.bodySmall,
                    color = IxhTextSecondary
                )
                Spacer(modifier = Modifier.height(10.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(IxhYellowLight)
                        .border(1.5.dp, IxhInk, RoundedCornerShape(14.dp))
                        .padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        IxhBlobMascot(
                            expression = if (passVisible) BlobExpression.PASSWORD_SHOW else BlobExpression.PASSWORD_HIDE,
                            size = 108.dp
                        )
                        Text(
                            text = if (passVisible) "Password terlihat: Blob menutup mata" else "Password tersembunyi: mata terbuka",
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text("Password baru", style = MaterialTheme.typography.labelMedium)
                OutlinedTextField(
                    value = newPass,
                    onValueChange = { newPass = it },
                    placeholder = { Text("Minimal 8 karakter (huruf & angka)") },
                    visualTransformation = if (passVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { passVisible = !passVisible }) {
                            Icon(
                                if (passVisible) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                                contentDescription = "Ubah visibilitas password"
                            )
                        }
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = IxhWhite,
                        unfocusedContainerColor = IxhWhite
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text("Konfirmasi password", style = MaterialTheme.typography.labelMedium)
                OutlinedTextField(
                    value = confirmPass,
                    onValueChange = { confirmPass = it },
                    placeholder = { Text("Ulangi password baru") },
                    visualTransformation = if (passVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = IxhWhite,
                        unfocusedContainerColor = IxhWhite
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                val hasLen = newPass.length >= 8
                val hasLetter = newPass.any { it.isLetter() }
                val hasDigit = newPass.any { it.isDigit() }
                val match = newPass.isNotBlank() && newPass == confirmPass

                Spacer(modifier = Modifier.height(10.dp))
                PasswordRuleRow("Minimal 8 karakter", hasLen)
                Spacer(modifier = Modifier.height(4.dp))
                PasswordRuleRow("Kombinasi huruf dan angka", hasLetter && hasDigit)
                Spacer(modifier = Modifier.height(4.dp))
                PasswordRuleRow("Konfirmasi password cocok", match)

                Spacer(modifier = Modifier.height(14.dp))
                IxhPrimaryButton(
                    text = "Simpan & Lanjut",
                    icon = Icons.Outlined.Check,
                    enabled = hasLen && hasLetter && hasDigit && match,
                    onClick = { step = 1 }
                )
            }

            1 -> {
                Text("Pesan Kelas via WhatsApp", style = MaterialTheme.typography.headlineMedium)
                Text(
                    "Persetujuan notifikasi WhatsApp kelas IX-H",
                    style = MaterialTheme.typography.bodySmall,
                    color = IxhTextSecondary
                )
                Spacer(modifier = Modifier.height(10.dp))
                IxhCard {
                    listOf(
                        Icons.AutoMirrored.Outlined.Send to "Bot IX-H Hub hanya mengirim informasi tagihan dan pengingat kelas.",
                        Icons.Outlined.Shield to "Kamu bisa membalas STOP kapan saja untuk menghentikan pesan otomatis.",
                        Icons.Outlined.Lock to "Nomor WhatsApp hanya dapat diakses oleh Admin dan Pengurus kelas."
                    ).forEach { (ic, desc) ->
                        Row(
                            modifier = Modifier.padding(vertical = 5.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            IxhCircleIcon(icon = ic, size = 28.dp)
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(desc, style = MaterialTheme.typography.bodySmall, color = IxhInk)
                        }
                    }
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Checkbox(
                        checked = waConsentChecked,
                        onCheckedChange = { waConsentChecked = it },
                        colors = CheckboxDefaults.colors(checkedColor = IxhYellow, checkmarkColor = IxhInk)
                    )
                    Text(
                        "Saya menyetujui penggunaan nomor WhatsApp untuk informasi kelas IX-H.",
                        style = MaterialTheme.typography.bodySmall,
                        color = IxhInk
                    )
                }
                IxhPrimaryButton(
                    text = "Setuju & Masuk ke Beranda",
                    enabled = waConsentChecked,
                    onClick = onFinishOnboarding
                )
            }
        }
    }
}
