package com.example.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.PowerSettingsNew
import androidx.compose.material.icons.outlined.QrCode2
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material.icons.outlined.RestartAlt
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.StopCircle
import androidx.compose.material.icons.outlined.UploadFile
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.ActivityLogEntity
import com.example.data.local.BillPaymentStatus
import com.example.data.local.BotDispatchLogEntity
import com.example.data.local.BotTemplateEntity
import com.example.data.local.UserEntity
import com.example.data.local.UserRole
import com.example.data.local.WaNumberStatus
import com.example.ui.components.BlobExpression
import com.example.ui.components.BlobMascotState
import com.example.ui.components.ChipTone
import com.example.ui.components.IxhAlertBanner
import com.example.ui.components.IxhBlobMascot
import com.example.ui.components.IxhCard
import com.example.ui.components.IxhCircleIcon
import com.example.ui.components.IxhDualToneSystemIcon
import com.example.ui.components.IxhEmptyStateCard
import com.example.ui.components.IxhNeubrutalToggle
import com.example.ui.components.IxhOrganicModuleBanner
import com.example.ui.components.IxhOrganicStatTile
import com.example.ui.components.IxhOrganicWaveDivider
import com.example.ui.components.IxhPrimaryButton
import com.example.ui.components.IxhSculptedFilterChip
import com.example.ui.components.IxhSculptedSectionHeader
import com.example.ui.components.IxhSecondaryButton
import com.example.ui.components.IxhSegmentedTabs
import com.example.ui.components.IxhStatusChip
import com.example.ui.components.IxhStyledTextField
import com.example.ui.components.IxhSubScreenTopBar
import com.example.ui.components.IxhSystemIconType
import com.example.ui.components.IxhWaBubblePreview
import com.example.ui.components.ixhOrganicScreenBackdrop
import com.example.ui.components.neubrutalShadow
import com.example.ui.theme.IxhBlueBg
import com.example.ui.theme.IxhBorder
import com.example.ui.theme.IxhCream
import com.example.ui.theme.IxhGreen
import com.example.ui.theme.IxhGreenBg
import com.example.ui.theme.IxhGreenText
import com.example.ui.theme.IxhInk
import com.example.ui.theme.IxhRed
import com.example.ui.theme.IxhRedBg
import com.example.ui.theme.IxhSurfaceMuted
import com.example.ui.theme.IxhTextSecondary
import com.example.ui.theme.IxhWhite
import com.example.ui.theme.IxhYellow
import com.example.ui.theme.IxhYellowLight
import com.example.ui.viewmodel.AppScreenRoute
import com.example.ui.viewmodel.BotConnectionState
import com.example.ui.viewmodel.ClassSettingsState

// =========================================================================
// STORYBOARD 06 & 20: PANEL BOT WHATSAPP, SERVER PTERODACTYL & PENGATURAN BOT
// =========================================================================

@Composable
fun BotPanelScreen(
    activeUser: UserEntity,
    botState: BotConnectionState,
    dispatchLogs: List<BotDispatchLogEntity>,
    validWaCount: Int = 0,
    onToggleConnectionSimulated: () -> Unit,
    onPterodactylControl: (String) -> Unit,
    onNavigate: (AppScreenRoute) -> Unit,
    onBack: () -> Unit
) {
    val isAdmin = activeUser.role == UserRole.ADMIN.name
    val heroShape = RoundedCornerShape(topStart = 28.dp, topEnd = 14.dp, bottomStart = 14.dp, bottomEnd = 32.dp)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .ixhOrganicScreenBackdrop()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        IxhSubScreenTopBar(
            title = "Panel Bot WhatsApp",
            onBack = onBack,
            rightContent = {
                IxhStatusChip(
                    text = if (botState.isConnected) "Simulasi Putus" else "Simulasi Hubung",
                    tone = if (botState.isConnected) ChipTone.NEUTRAL else ChipTone.GREEN,
                    modifier = Modifier.clickable(onClick = onToggleConnectionSimulated)
                )
            }
        )

        // Hero Telemetry Control Deck
        if (!botState.isConnected) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .neubrutalShadow(enabled = true, cornerRadius = 22.dp, offsetY = 4.8.dp, offsetX = 3.5.dp)
                    .clip(heroShape)
                    .background(IxhRedBg, heroShape)
                    .drawBehind {
                        val w = size.width
                        val h = size.height
                        val wave = Path().apply {
                            moveTo(w * 0.45f, 0f)
                            cubicTo(w * 0.65f, h * 0.30f, w * 0.80f, h * 0.22f, w, h * 0.56f)
                            lineTo(w, 0f)
                            close()
                        }
                        drawPath(wave, color = IxhRed.copy(alpha = 0.14f))
                    }
                    .border(2.6.dp, IxhInk, heroShape)
                    .padding(16.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(68.dp)
                                .neubrutalShadow(enabled = true, cornerRadius = 18.dp, offsetY = 2.5.dp, offsetX = 2.dp)
                                .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 12.dp, bottomStart = 12.dp, bottomEnd = 22.dp))
                                .background(IxhWhite)
                                .border(2.2.dp, IxhInk, RoundedCornerShape(topStart = 20.dp, topEnd = 12.dp, bottomStart = 12.dp, bottomEnd = 22.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            IxhBlobMascot(expression = BlobExpression.SAD_ERROR, size = 56.dp)
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            IxhStatusChip(text = "OFFLINE • TERPUTUS", tone = ChipTone.RED)
                            Spacer(modifier = Modifier.height(5.dp))
                            Text(
                                text = "Bot WhatsApp Terputus",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.ExtraBold,
                                color = IxhInk
                            )
                            Text(
                                text = "Sesi ${botState.sessionName} • ${botState.activeSince}. Pengiriman otomatis dijeda.",
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.SemiBold,
                                color = IxhRed
                            )
                        }
                    }
                    if (isAdmin) {
                        IxhPrimaryButton(
                            text = "Hubungkan Ulang (QR / Kode Pairing)",
                            icon = Icons.Outlined.QrCode2,
                            onClick = { onNavigate(AppScreenRoute.BOT_RECONNECT_QR_PAIRING) }
                        )
                    } else {
                        IxhAlertBanner(
                            text = "Hanya Admin Kelas IX-H yang dapat memindai ulang QR / Kode Pairing sesi Bot.",
                            tone = ChipTone.YELLOW
                        )
                    }
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .neubrutalShadow(enabled = true, cornerRadius = 24.dp, offsetY = 5.dp, offsetX = 3.5.dp)
                    .clip(heroShape)
                    .background(IxhYellow, heroShape)
                    .drawBehind {
                        val w = size.width
                        val h = size.height
                        val topWave = Path().apply {
                            moveTo(w * 0.42f, 0f)
                            cubicTo(w * 0.58f, h * 0.25f, w * 0.78f, h * 0.18f, w, h * 0.50f)
                            lineTo(w, 0f)
                            close()
                        }
                        drawPath(topWave, color = Color(0xFFFFE47A).copy(alpha = 0.85f))
                        val bottomWave = Path().apply {
                            moveTo(0f, h * 0.74f)
                            cubicTo(w * 0.30f, h * 0.58f, w * 0.65f, h * 0.90f, w, h * 0.66f)
                            lineTo(w, h)
                            lineTo(0f, h)
                            close()
                        }
                        drawPath(bottomWave, color = Color(0xFFFFB703).copy(alpha = 0.28f))
                    }
                    .border(2.6.dp, IxhInk, heroShape)
                    .padding(16.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(IxhInk)
                                .padding(horizontal = 10.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(7.dp)
                                    .clip(CircleShape)
                                    .background(IxhGreen)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "ONLINE • ${botState.sessionName.uppercase()}",
                                fontSize = 9.5.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = IxhYellow
                            )
                        }
                        IxhStatusChip(text = "Aktif ${botState.activeSince}", tone = ChipTone.GREEN)
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = botState.botPhoneNumber,
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.ExtraBold,
                                color = IxhInk
                            )
                            Text(
                                text = "Gateway WangLinMD aktif siap kirim laporan & tagihan",
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.SemiBold,
                                color = IxhInk.copy(alpha = 0.85f)
                            )
                        }
                        Box(
                            modifier = Modifier
                                .size(54.dp)
                                .neubrutalShadow(enabled = true, cornerRadius = 16.dp, offsetY = 2.5.dp, offsetX = 1.8.dp)
                                .clip(RoundedCornerShape(topStart = 18.dp, topEnd = 10.dp, bottomStart = 10.dp, bottomEnd = 20.dp))
                                .background(IxhCream)
                                .border(2.2.dp, IxhInk, RoundedCornerShape(topStart = 18.dp, topEnd = 10.dp, bottomStart = 10.dp, bottomEnd = 20.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            IxhDualToneSystemIcon(iconType = IxhSystemIconType.BOT, size = 28.dp)
                        }
                    }

                    // Embedded 3-metric telemetry dock
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        BotTelemetryMiniBox(
                            label = "Nomor Valid",
                            value = "$validWaCount",
                            modifier = Modifier.weight(1f)
                        )
                        BotTelemetryMiniBox(
                            label = "Jeda Acak",
                            value = botState.randomDelayRange,
                            modifier = Modifier.weight(1f)
                        )
                        BotTelemetryMiniBox(
                            label = "Batas/Jam",
                            value = "${botState.maxMessagesPerHour} psn",
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }

        // Pterodactyl VPS Server Deck
        if (isAdmin) {
            val isRunning = botState.pterodactylStatus == "Berjalan"
            IxhCard(
                backgroundColor = IxhWhite,
                hasHardShadow = true,
                contentPadding = PaddingValues(14.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                        IxhCircleIcon(
                            icon = Icons.Outlined.PowerSettingsNew,
                            bgColor = if (isRunning) IxhGreenBg else IxhRedBg,
                            iconTint = if (isRunning) IxhGreenText else IxhRed,
                            size = 40.dp
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Server VPS (Pterodactyl)",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.ExtraBold,
                                color = IxhInk
                            )
                            Text(
                                text = "Node: ${botState.sessionName} • Container ${botState.pterodactylStatus}",
                                style = MaterialTheme.typography.bodySmall,
                                color = IxhTextSecondary
                            )
                        }
                    }
                    IxhStatusChip(
                        text = botState.pterodactylStatus,
                        tone = if (isRunning) ChipTone.GREEN else ChipTone.RED
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    IxhSecondaryButton(
                        text = "Restart Server",
                        icon = Icons.Outlined.RestartAlt,
                        onClick = { onPterodactylControl("restart") },
                        modifier = Modifier.weight(1f)
                    )
                    IxhSecondaryButton(
                        text = if (!isRunning) "Start Server" else "Stop Server",
                        icon = Icons.Outlined.PowerSettingsNew,
                        isDanger = isRunning,
                        onClick = {
                            onPterodactylControl(if (!isRunning) "start" else "stop")
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        } else {
            IxhAlertBanner(
                text = "Mode Pengurus: Anda dapat mengirim laporan & pengingat WA. Kontrol server Pterodactyl & QR Pairing dikelola Admin.",
                tone = ChipTone.BLUE
            )
        }

        IxhOrganicWaveDivider()
        IxhSculptedSectionHeader(title = "Kendali & Aksi Cepat Bot")

        // 2x2 Sculpted Action Bento
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                BotActionBentoTile(
                    title = "Kirim Laporan",
                    subtitle = "Tagihan, Kas & Absensi",
                    icon = Icons.Outlined.Send,
                    accentBg = IxhYellow,
                    onClick = { onNavigate(AppScreenRoute.BOT_SELECT_REPORT) },
                    modifier = Modifier.weight(1f)
                )
                BotActionBentoTile(
                    title = "Template (6)",
                    subtitle = "Ubah isi & nada WA",
                    icon = Icons.Outlined.Description,
                    accentBg = IxhYellowLight,
                    onClick = { onNavigate(AppScreenRoute.BOT_TEMPLATES_LIST) },
                    modifier = Modifier.weight(1f)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                BotActionBentoTile(
                    title = "Pengaturan Bot",
                    subtitle = "Anti-spam & tautkan QR",
                    icon = Icons.Outlined.Settings,
                    accentBg = IxhBlueBg,
                    onClick = { onNavigate(AppScreenRoute.BOT_SETTINGS_ADMIN) },
                    modifier = Modifier.weight(1f)
                )
                BotActionBentoTile(
                    title = "Alur STOP/MULAI",
                    subtitle = "Simulasi opt-out siswa",
                    icon = Icons.Outlined.StopCircle,
                    accentBg = IxhGreenBg,
                    onClick = { onNavigate(AppScreenRoute.BOT_STOP_MULAI_SIMULATOR) },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        IxhSculptedSectionHeader(
            title = "Riwayat Pengiriman Terakhir",
            actionLabel = "Lihat Semua →",
            onActionClick = { onNavigate(AppScreenRoute.BOT_HISTORY) }
        )

        if (dispatchLogs.isEmpty()) {
            IxhEmptyStateCard(
                title = "Belum ada riwayat pengiriman bot.",
                subtitle = "Riwayat pengiriman laporan dan pengingat WhatsApp akan tercatat di sini setelah bot terhubung.",
                mascotState = BlobMascotState.IDLE
            )
        } else {
            dispatchLogs.take(4).forEach { log ->
                val (tone, stripeCol) = when {
                    log.failedCount > 0 && log.sentCount == 0 -> ChipTone.RED to IxhRed
                    log.failedCount > 0 -> ChipTone.YELLOW to IxhYellow
                    else -> ChipTone.GREEN to IxhGreen
                }
                IxhCard(
                    backgroundColor = IxhWhite,
                    leftStripeColor = stripeCol,
                    onClick = {
                        if (log.failedCount > 0) {
                            onNavigate(AppScreenRoute.BOT_FAILURE_LOGS)
                        } else {
                            onNavigate(AppScreenRoute.BOT_HISTORY)
                        }
                    }
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = log.title,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.ExtraBold,
                                color = IxhInk
                            )
                            Text(
                                text = log.dateTimeLabel,
                                style = MaterialTheme.typography.bodySmall,
                                color = IxhTextSecondary
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        IxhStatusChip(text = log.statusLabel, tone = tone)
                    }
                }
            }
        }
    }
}

@Composable
private fun BotTelemetryMiniBox(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(topStart = 14.dp, topEnd = 8.dp, bottomStart = 8.dp, bottomEnd = 16.dp)
    Column(
        modifier = modifier
            .clip(shape)
            .background(IxhWhite, shape)
            .border(2.dp, IxhInk, shape)
            .padding(horizontal = 8.dp, vertical = 7.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            fontSize = 12.5.sp,
            fontWeight = FontWeight.ExtraBold,
            color = IxhInk,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = label.uppercase(),
            fontSize = 8.5.sp,
            fontWeight = FontWeight.ExtraBold,
            color = IxhTextSecondary,
            maxLines = 1
        )
    }
}

@Composable
private fun BotActionBentoTile(
    title: String,
    subtitle: String,
    icon: ImageVector,
    accentBg: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val tileShape = RoundedCornerShape(topStart = 20.dp, topEnd = 12.dp, bottomStart = 12.dp, bottomEnd = 22.dp)
    Column(
        modifier = modifier
            .neubrutalShadow(enabled = true, cornerRadius = 16.dp, offsetY = 3.5.dp, offsetX = 2.5.dp)
            .clip(tileShape)
            .background(IxhWhite, tileShape)
            .drawBehind {
                val w = size.width
                val h = size.height
                val wave = Path().apply {
                    moveTo(w * 0.48f, 0f)
                    cubicTo(w * 0.65f, h * 0.25f, w * 0.80f, h * 0.20f, w, h * 0.45f)
                    lineTo(w, 0f)
                    close()
                }
                drawPath(wave, color = accentBg.copy(alpha = 0.72f))
            }
            .border(2.2.dp, IxhInk, tileShape)
            .clickable(onClick = onClick)
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        IxhCircleIcon(
            icon = icon,
            bgColor = accentBg,
            size = 36.dp
        )
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.ExtraBold,
                color = IxhInk,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.labelSmall,
                color = IxhTextSecondary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun BotSelectReportScreen(
    onSelectReportToConfigure: (String) -> Unit,
    onBack: () -> Unit
) {
    var selectedIdx by remember { mutableIntStateOf(0) }
    val reportTypes = listOf(
        Triple("Tagihan Kas Mingguan", "Kirim rincian kas mingguan ke nomor WhatsApp siswa yang valid", "Tagihan"),
        Triple("Rekap Absensi Harian", "Kirim ringkasan kehadiran Hadir / Izin / Sakit / Alpha hari ini", "Absensi"),
        Triple("Ringkasan Saldo Kas", "Kirim saldo kas terkonfirmasi, total pemasukan, dan pengeluaran", "Kas"),
        Triple("Pengingat Jadwal Besok", "Kirim daftar mata pelajaran dan catatan perlengkapan esok hari", "Jadwal"),
        Triple("Daftar Tugas Mendatang", "Kirim pengingat tugas aktif yang mendekati tenggat waktu", "Tugas")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .ixhOrganicScreenBackdrop()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        IxhSubScreenTopBar(title = "Pilih Jenis Laporan", onBack = onBack)

        IxhOrganicModuleBanner(
            eyebrowTag = "Langkah 1 dari 2",
            title = "Kirim Laporan Kelas",
            subtitle = "Pilih jenis rekap otomatis yang akan diformat ke pesan WhatsApp.",
            iconType = IxhSystemIconType.BOT
        )

        reportTypes.forEachIndexed { idx, (title, sub, cat) ->
            val selected = idx == selectedIdx
            IxhCard(
                backgroundColor = if (selected) IxhYellowLight else IxhWhite,
                borderColor = IxhInk,
                hasHardShadow = selected,
                leftStripeColor = if (selected) IxhYellow else IxhSurfaceMuted,
                onClick = { selectedIdx = idx }
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(if (selected) IxhYellow else IxhSurfaceMuted)
                                .border(2.dp, IxhInk, RoundedCornerShape(10.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "${idx + 1}",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = IxhInk
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.ExtraBold,
                                color = IxhInk
                            )
                            Text(
                                text = sub,
                                style = MaterialTheme.typography.bodySmall,
                                color = IxhTextSecondary
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    IxhStatusChip(
                        text = if (selected) "Dipilih ✓" else cat,
                        tone = if (selected) ChipTone.OUTLINE_YELLOW else ChipTone.NEUTRAL
                    )
                }
            }
        }

        IxhPrimaryButton(
            text = "Lanjut Atur Pengiriman →",
            onClick = { onSelectReportToConfigure(reportTypes[selectedIdx].first) }
        )
    }
}

@Composable
fun BotConfigureSendScreen(
    users: List<UserEntity> = emptyList(),
    onDispatchNow: (String, String, Int) -> Unit,
    onBack: () -> Unit
) {
    var recipientOption by remember { mutableIntStateOf(0) }
    val validCount = users.count { it.waStatus == WaNumberStatus.VALID.name }
    val unpaidCount = users.count { it.billStatus != BillPaymentStatus.LUNAS.name }
    val targetCounts = listOf(validCount, unpaidCount, 1)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .ixhOrganicScreenBackdrop()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        IxhSubScreenTopBar(title = "Atur & Kirim Pesan", onBack = onBack)

        IxhOrganicModuleBanner(
            eyebrowTag = "Langkah 2 dari 2",
            title = "Pratinjau & Siapkan Antrean",
            subtitle = "Validasi target penerima sebelum pesan masuk antrean jeda acak.",
            iconType = IxhSystemIconType.BOT,
            trailingContent = {
                IxhOrganicStatTile(
                    label = "Target",
                    value = "${targetCounts[recipientOption]}",
                    accentColor = IxhGreenBg
                )
            }
        )

        IxhSculptedSectionHeader(title = "Tujuan Penerima")
        IxhSegmentedTabs(
            tabs = listOf("Valid ($validCount)", "Belum Lunas ($unpaidCount)", "Grup IX-H"),
            selectedIndex = recipientOption,
            onTabSelected = { recipientOption = it }
        )

        IxhSculptedSectionHeader(title = "Pratinjau Pesan WhatsApp")
        IxhWaBubblePreview(
            senderTitle = "IX-H Hub • Laporan Kelas",
            messageBody = "Halo *Siswa IX-H*,\nBerikut rekap harian Kelas IX-H hari ini:\n• Kehadiran & Kas tercatat di aplikasi\n• Periksa jadwal dan tugas besok di IX-H Hub\n\nBalas STOP untuk berhenti menerima pesan.",
            timeLabel = "16.10"
        )

        IxhAlertBanner(
            text = "Pengiriman menggunakan jeda acak 8–20 detik antar nomor agar aman dari pemblokiran WhatsApp.",
            tone = ChipTone.YELLOW
        )

        IxhPrimaryButton(
            text = "Kirim Sekarang (${targetCounts[recipientOption]} Penerima)",
            icon = Icons.Outlined.Send,
            onClick = {
                onDispatchNow(
                    "Rekap Harian & Tagihan Kelas IX-H",
                    "Laporan",
                    targetCounts[recipientOption]
                )
            }
        )
    }
}

@Composable
fun BotHistoryScreen(
    dispatchLogs: List<BotDispatchLogEntity>,
    onOpenFailureLogs: () -> Unit,
    onBack: () -> Unit
) {
    var filterIdx by remember { mutableIntStateOf(0) }
    val cats = listOf("Semua", "Tagihan", "Absensi", "Kas", "Jadwal")

    val filtered = dispatchLogs.filter {
        if (filterIdx == 0) true else it.category.equals(cats[filterIdx], ignoreCase = true)
    }
    val totalSent = dispatchLogs.sumOf { it.sentCount }
    val totalFailed = dispatchLogs.sumOf { it.failedCount }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .ixhOrganicScreenBackdrop()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        IxhSubScreenTopBar(title = "Riwayat Pengiriman Bot", onBack = onBack)

        IxhOrganicModuleBanner(
            eyebrowTag = "Log Broadcast",
            title = "Riwayat Pengiriman",
            subtitle = "Pantau laporan pesan terkirim dan gagal secara langsung.",
            iconType = IxhSystemIconType.BOT
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            IxhOrganicStatTile(
                label = "Total Batch",
                value = "${dispatchLogs.size}",
                accentColor = IxhYellowLight,
                modifier = Modifier.weight(1f)
            )
            IxhOrganicStatTile(
                label = "Terkirim",
                value = "$totalSent",
                accentColor = IxhGreenBg,
                valueColor = IxhGreenText,
                modifier = Modifier.weight(1f)
            )
            IxhOrganicStatTile(
                label = "Gagal",
                value = "$totalFailed",
                accentColor = IxhRedBg,
                valueColor = IxhRed,
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            cats.forEachIndexed { idx, label ->
                IxhSculptedFilterChip(
                    label = label,
                    selected = idx == filterIdx,
                    onClick = { filterIdx = idx }
                )
            }
        }

        if (filtered.isEmpty()) {
            IxhEmptyStateCard(
                title = "Belum ada riwayat pengiriman.",
                subtitle = "Semua pengiriman pesan otomatis bot WhatsApp akan dicatat di halaman ini.",
                mascotState = BlobMascotState.IDLE
            )
        } else {
            filtered.forEach { log ->
                val (tone, stripe) = when {
                    log.failedCount > 0 && log.sentCount == 0 -> ChipTone.RED to IxhRed
                    log.failedCount > 0 -> ChipTone.YELLOW to IxhYellow
                    else -> ChipTone.GREEN to IxhGreen
                }
                IxhCard(
                    backgroundColor = IxhWhite,
                    leftStripeColor = stripe,
                    onClick = { if (log.failedCount > 0) onOpenFailureLogs() }
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = log.title,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.ExtraBold,
                                color = IxhInk
                            )
                            Text(
                                text = log.dateTimeLabel,
                                style = MaterialTheme.typography.bodySmall,
                                color = IxhTextSecondary
                            )
                            if (log.failureReason.isNotBlank()) {
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "Catatan: ${log.failureReason}",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = IxhRed
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        IxhStatusChip(text = log.statusLabel, tone = tone)
                    }
                }
            }
        }
    }
}

@Composable
fun BotSettingsAdminScreen(
    botState: BotConnectionState,
    users: List<UserEntity> = emptyList(),
    onUpdateMaxPerHour: (Int) -> Unit,
    onNavigateReconnect: () -> Unit,
    onNavigateFailureLogs: () -> Unit,
    onNavigateProblematicNumbers: () -> Unit,
    onBack: () -> Unit
) {
    var autoPause by remember { mutableStateOf(botState.autoPauseOnDisconnect) }
    val validCount = users.count { it.waStatus == WaNumberStatus.VALID.name }
    val problemCount = users.count { it.waStatus != WaNumberStatus.VALID.name }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .ixhOrganicScreenBackdrop()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        IxhSubScreenTopBar(title = "Pengaturan Bot (Admin)", onBack = onBack)

        IxhCard(
            backgroundColor = IxhYellow,
            borderColor = IxhInk,
            hasHardShadow = true,
            contentPadding = PaddingValues(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Sesi ${botState.sessionName}",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.ExtraBold,
                        color = IxhInk
                    )
                    Text(
                        text = "${botState.botPhoneNumber} • ${botState.activeSince}",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold,
                        color = IxhInk.copy(alpha = 0.85f)
                    )
                }
                IxhStatusChip(
                    text = if (botState.isConnected) "Terhubung" else "Terputus",
                    tone = if (botState.isConnected) ChipTone.GREEN else ChipTone.RED
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            IxhSecondaryButton(
                text = "Tautkan Ulang QR / Kode Pairing",
                icon = Icons.Outlined.QrCode2,
                onClick = onNavigateReconnect
            )
        }

        IxhSculptedSectionHeader(title = "Keamanan & Anti-Blokir WhatsApp")

        IxhCard(
            backgroundColor = IxhWhite,
            hasHardShadow = true
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Batas Pesan per Jam",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.ExtraBold,
                        color = IxhInk
                    )
                    Text(
                        text = "Disarankan maks. 60 pesan/jam",
                        style = MaterialTheme.typography.bodySmall,
                        color = IxhTextSecondary
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .neubrutalShadow(enabled = true, cornerRadius = 10.dp, offsetY = 2.dp, offsetX = 1.5.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(IxhSurfaceMuted)
                            .border(2.dp, IxhInk, RoundedCornerShape(10.dp))
                            .clickable { onUpdateMaxPerHour(-10) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text("-", fontSize = 16.sp, fontWeight = FontWeight.ExtraBold, color = IxhInk)
                    }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(IxhYellowLight)
                            .border(2.dp, IxhInk, RoundedCornerShape(10.dp))
                            .padding(horizontal = 12.dp, vertical = 5.dp)
                    ) {
                        Text(
                            text = "${botState.maxMessagesPerHour}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = IxhInk
                        )
                    }
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .neubrutalShadow(enabled = true, cornerRadius = 10.dp, offsetY = 2.dp, offsetX = 1.5.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(IxhYellow)
                            .border(2.dp, IxhInk, RoundedCornerShape(10.dp))
                            .clickable { onUpdateMaxPerHour(10) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text("+", fontSize = 16.sp, fontWeight = FontWeight.ExtraBold, color = IxhInk)
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            // Visual rate limit bar
            val ratio = (botState.maxMessagesPerHour / 100f).coerceIn(0.1f, 1f)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp)
                    .clip(RoundedCornerShape(999.dp))
                    .background(IxhSurfaceMuted)
                    .border(1.8.dp, IxhInk, RoundedCornerShape(999.dp))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(ratio)
                        .height(12.dp)
                        .background(if (botState.maxMessagesPerHour <= 60) IxhGreen else IxhYellow)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Jeda Acak Antar Pesan", style = MaterialTheme.typography.bodySmall, color = IxhTextSecondary)
                Text("8–20 detik (Terkunci Aman)", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.ExtraBold, color = IxhGreenText)
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Jeda otomatis saat koneksi terputus",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.SemiBold,
                    color = IxhInk,
                    modifier = Modifier.weight(1f)
                )
                IxhNeubrutalToggle(checked = autoPause, onCheckedChange = { autoPause = it })
            }
        }

        IxhCard(
            backgroundColor = IxhWhite,
            leftStripeColor = if (problemCount > 0) IxhRed else IxhGreen,
            onClick = onNavigateProblematicNumbers
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Status Nomor WA Siswa",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.ExtraBold,
                        color = IxhInk
                    )
                    Text(
                        text = "$validCount Valid • $problemCount Perlu Diperiksa",
                        style = MaterialTheme.typography.bodySmall,
                        color = IxhTextSecondary
                    )
                }
                IxhStatusChip(text = "Kelola →", tone = ChipTone.YELLOW)
            }
        }

        IxhSecondaryButton(
            text = "Lihat Log Kegagalan & Kirim Ulang",
            onClick = onNavigateFailureLogs
        )
    }
}

@Composable
fun BotReconnectQrPairingScreen(
    botState: BotConnectionState,
    onGenerateNewCode: () -> Unit,
    onReconnectSuccess: () -> Unit,
    onBack: () -> Unit
) {
    var modeTab by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .ixhOrganicScreenBackdrop()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        IxhSubScreenTopBar(title = "Tautkan Perangkat WA", onBack = onBack)

        IxhOrganicModuleBanner(
            eyebrowTag = "Sinkronisasi Perangkat",
            title = "Tautkan Sesi ${botState.sessionName}",
            subtitle = "Pilih pairing 8 digit atau pindai kode QR lewat WhatsApp resmi.",
            iconType = IxhSystemIconType.BOT
        )

        IxhSegmentedTabs(
            tabs = listOf("Kode Pairing (8 Digit)", "Pindai Kode QR"),
            selectedIndex = modeTab,
            onTabSelected = { modeTab = it }
        )

        if (modeTab == 0) {
            IxhCard(
                backgroundColor = IxhYellowLight,
                borderColor = IxhInk,
                hasHardShadow = true,
                contentPadding = PaddingValues(18.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "KODE PAIRING WHATSAPP",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.ExtraBold,
                        color = IxhInk
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        botState.pairingCode.forEachIndexed { idx, ch ->
                            if (idx == 4) {
                                Text(
                                    text = "-",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = IxhInk,
                                    modifier = Modifier.padding(horizontal = 2.dp, vertical = 6.dp)
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .size(width = 30.dp, height = 42.dp)
                                    .neubrutalShadow(enabled = true, cornerRadius = 8.dp, offsetY = 2.2.dp, offsetX = 1.6.dp)
                                    .clip(RoundedCornerShape(9.dp))
                                    .background(IxhWhite)
                                    .border(2.2.dp, IxhInk, RoundedCornerShape(9.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = ch.toString(),
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = IxhInk
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Buka WhatsApp → Perangkat Tertaut → Tautkan dengan nomor telepon saja.",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.SemiBold,
                        color = IxhInk,
                        textAlign = TextAlign.Center
                    )
                }
            }
            IxhSecondaryButton(
                text = "Buat Kode Pairing Baru",
                icon = Icons.Outlined.Refresh,
                onClick = onGenerateNewCode
            )
        } else {
            IxhCard(
                backgroundColor = IxhWhite,
                hasHardShadow = true,
                contentPadding = PaddingValues(18.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .neubrutalShadow(enabled = true, cornerRadius = 16.dp, offsetY = 3.5.dp, offsetX = 2.5.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(IxhCream)
                            .border(2.6.dp, IxhInk, RoundedCornerShape(16.dp))
                            .padding(14.dp)
                    ) {
                        Canvas(modifier = Modifier.size(156.dp)) {
                            val grid = 9
                            val cell = size.width / grid
                            for (r in 0 until grid) {
                                for (c in 0 until grid) {
                                    val isCorner = (r < 3 && c < 3) || (r < 3 && c >= grid - 3) || (r >= grid - 3 && c < 3)
                                    val fill = isCorner || ((r * 7 + c * 3) % 2 == 0)
                                    if (fill) {
                                        drawRect(
                                            color = IxhInk,
                                            topLeft = Offset(c * cell, r * cell),
                                            size = Size(cell * 0.86f, cell * 0.86f)
                                        )
                                    }
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Pindai QR ini dari WhatsApp nomor Bot (${botState.botPhoneNumber})",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold,
                        color = IxhInk,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        IxhPrimaryButton(
            text = "✓ Konfirmasi Sesi Terhubung",
            onClick = onReconnectSuccess
        )
    }
}

@Composable
fun BotFailureLogsScreen(
    dispatchLogs: List<BotDispatchLogEntity>,
    onRetryAllFailed: () -> Unit,
    onNavigateProblematicNumbers: () -> Unit,
    onBack: () -> Unit
) {
    val failedLogs = dispatchLogs.filter { it.failedCount > 0 }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .ixhOrganicScreenBackdrop()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        IxhSubScreenTopBar(title = "Log Kegagalan Bot", onBack = onBack)

        IxhOrganicModuleBanner(
            eyebrowTag = "Diagnostik Kirim",
            title = "Pantau Kegagalan Bot",
            subtitle = "Periksa nomor tidak valid atau timeout untuk kirim ulang.",
            iconType = IxhSystemIconType.BOT,
            containerColor = if (failedLogs.isEmpty()) IxhGreenBg else IxhRedBg,
            accentWaveColor = if (failedLogs.isEmpty()) IxhGreen else IxhRed
        )

        if (failedLogs.isEmpty()) {
            IxhEmptyStateCard(
                title = "Tidak ada kegagalan pengiriman.",
                subtitle = "Semua pengiriman laporan dan pengingat WhatsApp dalam kondisi normal.",
                mascotState = BlobMascotState.SMILE
            )
        } else {
            IxhAlertBanner(
                title = "${failedLogs.size} pengiriman membutuhkan perhatian",
                text = "Periksa alasan kegagalan dan kirim ulang batch yang tertunda.",
                tone = ChipTone.RED
            )

            failedLogs.forEach { log ->
                IxhCard(
                    backgroundColor = IxhWhite,
                    leftStripeColor = IxhRed
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = log.title,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.ExtraBold,
                            color = IxhInk,
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        IxhStatusChip(text = "${log.failedCount} gagal", tone = ChipTone.RED)
                    }
                    Text(
                        text = log.dateTimeLabel,
                        style = MaterialTheme.typography.bodySmall,
                        color = IxhTextSecondary
                    )
                    if (log.failureReason.isNotBlank()) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Penyebab: ${log.failureReason}",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = IxhRed
                        )
                    }
                }
            }

            IxhPrimaryButton(
                text = "Kirim Ulang Semua Pesan Gagal",
                onClick = onRetryAllFailed
            )
        }

        IxhSecondaryButton(
            text = "Periksa Nomor WA Bermasalah",
            onClick = onNavigateProblematicNumbers
        )
    }
}

// =========================================================================
// STORYBOARD 22: TEMPLATE PESAN WHATSAPP (6 TEMPLATES + VALIDATOR)
// =========================================================================

@Composable
fun BotTemplatesListScreen(
    templates: List<BotTemplateEntity>,
    onOpenEditor: (BotTemplateEntity) -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .ixhOrganicScreenBackdrop()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        IxhSubScreenTopBar(title = "Template Pesan WA", onBack = onBack)

        IxhOrganicModuleBanner(
            eyebrowTag = "6 Template Aktif",
            title = "Template Pesan Bot",
            subtitle = "Sesuaikan format pesan otomatis dengan placeholder dinamis.",
            iconType = IxhSystemIconType.BOT
        )

        IxhAlertBanner(
            text = "Variabel resmi: {nama}, {nominal}, {jatuh_tempo}. Baris 'Balas STOP' dikunci otomatis demi kepatuhan anti-spam.",
            tone = ChipTone.BLUE
        )

        templates.forEachIndexed { idx, tpl ->
            IxhCard(
                backgroundColor = IxhWhite,
                leftStripeColor = if (tpl.isModified) IxhYellow else IxhGreen,
                onClick = { onOpenEditor(tpl) }
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(IxhYellowLight)
                                .border(1.8.dp, IxhInk, RoundedCornerShape(10.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "${idx + 1}",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = IxhInk
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = tpl.name,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.ExtraBold,
                                color = IxhInk
                            )
                            Text(
                                text = tpl.subtitle,
                                style = MaterialTheme.typography.bodySmall,
                                color = IxhTextSecondary
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    IxhStatusChip(
                        text = if (tpl.isModified) "Diubah" else "Bawaan",
                        tone = if (tpl.isModified) ChipTone.YELLOW else ChipTone.GREEN
                    )
                }
            }
        }
    }
}

@Composable
fun BotTemplateEditorScreen(
    template: BotTemplateEntity?,
    onSaveTemplate: (BotTemplateEntity, String) -> String?,
    onResetToDefault: (String) -> Unit,
    onSendTestMessage: (String) -> Unit,
    onBack: () -> Unit
) {
    val item = template ?: BotTemplateEntity(
        id = "tagihan_baru",
        name = "Tagihan Kas Baru",
        subtitle = "Saat periode tagihan dibuat • 3 variabel",
        bodyText = "Halo {nama}, tagihan kas sebesar {nominal} jatuh tempo pada {jatuh_tempo}.\nBalas STOP untuk berhenti menerima pesan."
    )
    var bodyText by remember(item) { mutableStateOf(item.bodyText) }
    var toneIdx by remember { mutableIntStateOf(1) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val renderedPreview = bodyText
        .replace("{nama}", "Nama Siswa")
        .replace("{nominal}", "Rp6.000")
        .replace("{jatuh_tempo}", "Jumat")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .ixhOrganicScreenBackdrop()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        IxhSubScreenTopBar(title = "Edit Template WA", onBack = onBack)

        IxhOrganicModuleBanner(
            eyebrowTag = "Editor Pesan",
            title = item.name,
            subtitle = item.subtitle,
            iconType = IxhSystemIconType.BOT
        )

        if (item.id == "pengingat_kas") {
            IxhSculptedSectionHeader(title = "Nada Pengingat")
            IxhSegmentedTabs(
                tabs = listOf("Ramah", "Biasa", "Tegas"),
                selectedIndex = toneIdx,
                onTabSelected = { idx ->
                    toneIdx = idx
                    bodyText = when (idx) {
                        0 -> "Halo {nama}, yuk jangan lupa kas kelas sebesar {nominal} jatuh tempo {jatuh_tempo}.\nBalas STOP untuk berhenti menerima pesan."
                        1 -> "Halo {nama}, pengingat kas sebesar {nominal} jatuh tempo pada {jatuh_tempo}.\nBalas STOP untuk berhenti menerima pesan."
                        else -> "PERINGATAN: Halo {nama}, tagihan kas {nominal} jatuh tempo {jatuh_tempo}. Mohon segera dilunasi ke Bendahara.\nBalas STOP untuk berhenti menerima pesan."
                    }
                }
            )
        }

        IxhSculptedSectionHeader(title = "Sisipkan Variabel Cepat")
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("{nama}", "{nominal}", "{jatuh_tempo}").forEach { token ->
                IxhSculptedFilterChip(
                    label = "+ $token",
                    selected = false,
                    onClick = { bodyText = "$bodyText $token" }
                )
            }
        }

        IxhStyledTextField(
            value = bodyText,
            onValueChange = {
                bodyText = it
                errorMessage = null
            },
            label = "Isi Template Pesan",
            singleLine = false,
            minLines = 4
        )

        if (errorMessage != null) {
            IxhAlertBanner(text = errorMessage!!, tone = ChipTone.RED)
        }

        IxhSculptedSectionHeader(title = "Pratinjau Pesan WhatsApp")
        IxhWaBubblePreview(
            senderTitle = "IX-H Hub • ${item.name}",
            messageBody = renderedPreview,
            timeLabel = "08.15"
        )

        IxhPrimaryButton(
            text = "Simpan Template",
            onClick = {
                val err = onSaveTemplate(item, bodyText)
                errorMessage = err
            }
        )

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            IxhSecondaryButton(
                text = "Kirim Tes ke WA Saya",
                onClick = { onSendTestMessage("Pesan tes '${item.name}' dikirim ke nomor Admin") },
                modifier = Modifier.weight(1f)
            )
            IxhSecondaryButton(
                text = "Reset Bawaan",
                onClick = { onResetToDefault(item.id) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

// =========================================================================
// STORYBOARD 23: ALUR BALAS STOP / MULAI DI WHATSAPP
// =========================================================================

@Composable
fun BotStopMulaiSimulatorScreen(
    users: List<UserEntity>,
    onToggleStudentStopMulai: (UserEntity, Boolean) -> Unit,
    onBack: () -> Unit
) {
    val sampleStudent = users.firstOrNull()
    val isCurrentlyStopped = sampleStudent?.waStatus == WaNumberStatus.BERHENTI.name

    Column(
        modifier = Modifier
            .fillMaxSize()
            .ixhOrganicScreenBackdrop()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        IxhSubScreenTopBar(title = "Alur STOP & MULAI WA", onBack = onBack)

        IxhOrganicModuleBanner(
            eyebrowTag = "Kepatuhan Opt-Out",
            title = "Kendali STOP & MULAI",
            subtitle = "Simulasikan dan pantau status persetujuan WhatsApp siswa.",
            iconType = IxhSystemIconType.BOT
        )

        IxhAlertBanner(
            text = "Siswa dapat membalas STOP kapan saja di WhatsApp untuk menghentikan pesan otomatis, atau MULAI untuk mengaktifkan kembali.",
            tone = ChipTone.BLUE
        )

        IxhSculptedSectionHeader(title = "1. Saat Siswa Membalas 'STOP'")
        IxhWaBubblePreview(
            senderTitle = "IX-H Hub",
            messageBody = "Halo *${sampleStudent?.fullName ?: "Siswa IX-H"}*, tagihan Kas Mingguan sebesar Rp6.000 telah diterbitkan.\nBalas STOP untuk berhenti.",
            timeLabel = "19.38",
            outgoingReply = "STOP"
        )
        IxhCard(
            backgroundColor = IxhGreenBg,
            borderColor = IxhInk,
            leftStripeColor = IxhGreen
        ) {
            Text(
                text = "BALASAN OTOMATIS BOT",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.ExtraBold,
                color = IxhGreenText
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = "[OK] Nomor kamu telah dihentikan dari pengiriman pesan otomatis IX-H Hub. Ketik *MULAI* kapan saja jika ingin menerima pengingat lagi.",
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Medium,
                color = IxhInk
            )
        }

        IxhOrganicWaveDivider()
        IxhSculptedSectionHeader(title = "2. Kendali Langsung di Aplikasi")

        if (sampleStudent == null) {
            IxhEmptyStateCard(
                title = "Belum ada akun siswa terdaftar.",
                subtitle = "Tambahkan akun siswa terlebih dahulu untuk mengatur status langganan STOP/MULAI WhatsApp.",
                mascotState = BlobMascotState.IDLE
            )
        } else {
            IxhCard(
                backgroundColor = IxhWhite,
                hasHardShadow = true
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = sampleStudent.fullName,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.ExtraBold,
                            color = IxhInk
                        )
                        Text(
                            text = "${sampleStudent.phoneWa} • Status: ${sampleStudent.waStatus}",
                            style = MaterialTheme.typography.bodySmall,
                            color = IxhTextSecondary
                        )
                    }
                    IxhStatusChip(
                        text = if (isCurrentlyStopped) "Berhenti" else "Valid",
                        tone = if (isCurrentlyStopped) ChipTone.RED else ChipTone.GREEN
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                IxhPrimaryButton(
                    text = if (isCurrentlyStopped) "Simulasikan Ketik MULAI (Aktifkan Lagi)" else "Simulasikan Ketik STOP (Hentikan)",
                    onClick = { onToggleStudentStopMulai(sampleStudent, isCurrentlyStopped) }
                )
            }
        }
    }
}

// =========================================================================
// STORYBOARD 08: MANAJEMEN PENGGUNA & NOMOR WA BERMASALAH
// =========================================================================

@Composable
fun UserManagementListScreen(
    users: List<UserEntity>,
    onOpenUserDetail: (UserEntity) -> Unit,
    onNavigateAddUser: () -> Unit,
    onNavigateImportMassal: () -> Unit,
    onNavigateProblematicNumbers: () -> Unit,
    onBack: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var filterTab by remember { mutableIntStateOf(0) }

    val problematicCount = users.count { it.waStatus != WaNumberStatus.VALID.name }
    val pengurusCount = users.count { it.role == UserRole.ADMIN.name || it.role == UserRole.PENGURUS.name }
    val siswaCount = users.count { it.role == UserRole.SISWA.name }

    val filtered = users.filter { u ->
        val matchesSearch = searchQuery.isBlank() ||
            u.fullName.contains(searchQuery, ignoreCase = true) ||
            u.username.contains(searchQuery, ignoreCase = true)
        val matchesTab = when (filterTab) {
            1 -> u.role == UserRole.ADMIN.name || u.role == UserRole.PENGURUS.name
            2 -> u.role == UserRole.SISWA.name
            3 -> u.waStatus != WaNumberStatus.VALID.name
            else -> true
        }
        matchesSearch && matchesTab
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .ixhOrganicScreenBackdrop()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        IxhSubScreenTopBar(
            title = "Manajemen Pengguna",
            onBack = onBack,
            rightContent = {
                IxhStatusChip(
                    text = "Impor CSV",
                    tone = ChipTone.OUTLINE_YELLOW,
                    modifier = Modifier.clickable(onClick = onNavigateImportMassal)
                )
            }
        )

        IxhOrganicModuleBanner(
            eyebrowTag = "Direktori Kelas IX-H",
            title = "Kelola Anggota & Role",
            subtitle = "Atur akun siswa, pengurus kelas, dan validasi nomor WhatsApp.",
            iconType = IxhSystemIconType.PROFIL
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            IxhOrganicStatTile(
                label = "Total Akun",
                value = "${users.size}",
                accentColor = IxhYellowLight,
                modifier = Modifier.weight(1f)
            )
            IxhOrganicStatTile(
                label = "Pengurus",
                value = "$pengurusCount",
                accentColor = IxhBlueBg,
                modifier = Modifier.weight(1f)
            )
            IxhOrganicStatTile(
                label = "WA Cek",
                value = "$problematicCount",
                accentColor = if (problematicCount > 0) IxhRedBg else IxhGreenBg,
                valueColor = if (problematicCount > 0) IxhRed else IxhGreenText,
                modifier = Modifier.weight(1f)
            )
        }

        if (problematicCount > 0) {
            IxhAlertBanner(
                title = "$problematicCount nomor WA perlu diperiksa",
                text = "Ketuk untuk memeriksa nomor yang belum dicek, tidak terdaftar, atau berhenti (STOP).",
                tone = ChipTone.RED,
                onClick = onNavigateProblematicNumbers
            )
        }

        IxhStyledTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = "Cari nama siswa atau username...",
            leadingIcon = Icons.Outlined.Search
        )

        IxhSegmentedTabs(
            tabs = listOf("Semua (${users.size})", "Pengurus ($pengurusCount)", "Siswa ($siswaCount)", "Cek ($problematicCount)"),
            selectedIndex = filterTab,
            onTabSelected = { filterTab = it }
        )

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            IxhPrimaryButton(
                text = "+ Tambah Pengguna",
                onClick = onNavigateAddUser,
                modifier = Modifier.weight(1f),
                testTag = "user_mgmt_add_button"
            )
            IxhSecondaryButton(
                text = "Impor Massal (CSV)",
                onClick = onNavigateImportMassal,
                modifier = Modifier.weight(1f)
            )
        }

        if (filtered.isEmpty()) {
            IxhEmptyStateCard(
                title = "Belum ada pengguna pada kategori ini.",
                subtitle = "Tambahkan akun siswa atau pengurus menggunakan tombol '+ Tambah Pengguna' di atas.",
                mascotState = BlobMascotState.IDLE
            )
        } else {
            filtered.forEach { user ->
                val (waLabel, waTone) = when (user.waStatus) {
                    WaNumberStatus.VALID.name -> "WA Valid" to ChipTone.GREEN
                    WaNumberStatus.TIDAK_DI_WA.name -> "Tidak di WA" to ChipTone.RED
                    WaNumberStatus.BERHENTI.name -> "Berhenti" to ChipTone.NEUTRAL
                    else -> "Belum dicek" to ChipTone.YELLOW
                }
                IxhCard(
                    backgroundColor = IxhWhite,
                    leftStripeColor = if (user.role == UserRole.SISWA.name) IxhYellowLight else IxhYellow,
                    onClick = { onOpenUserDetail(user) },
                    contentPadding = PaddingValues(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(38.dp)
                                .neubrutalShadow(enabled = true, cornerRadius = 12.dp, offsetY = 2.dp, offsetX = 1.5.dp)
                                .clip(RoundedCornerShape(topStart = 14.dp, topEnd = 9.dp, bottomStart = 9.dp, bottomEnd = 15.dp))
                                .background(if (user.role == UserRole.ADMIN.name) IxhYellow else IxhYellowLight)
                                .border(2.dp, IxhInk, RoundedCornerShape(topStart = 14.dp, topEnd = 9.dp, bottomStart = 9.dp, bottomEnd = 15.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "%02d".format(user.absenNo),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = IxhInk
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(
                                    text = user.fullName,
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = IxhInk,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.weight(1f, fill = false)
                                )
                                IxhStatusChip(
                                    text = if (user.subRoleTitle.isNotBlank()) user.subRoleTitle else user.role,
                                    tone = if (user.role == UserRole.SISWA.name) ChipTone.NEUTRAL else ChipTone.OUTLINE_YELLOW
                                )
                            }
                            Text(
                                text = "@${user.username} • ${user.phoneWa}",
                                style = MaterialTheme.typography.bodySmall,
                                color = IxhTextSecondary,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                        IxhStatusChip(text = waLabel, tone = waTone)
                    }
                }
            }
        }
    }
}

@Composable
fun UserDetailAdminScreen(
    user: UserEntity?,
    actorName: String,
    onUpdateRole: (UserEntity, String, String) -> Unit,
    onResetPassword: (UserEntity) -> String,
    onToggleWaPreference: (UserEntity, Boolean) -> Unit,
    onBack: () -> Unit
) {
    var showRoleDialog by remember { mutableStateOf(false) }
    var generatedTempPass by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .ixhOrganicScreenBackdrop()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        IxhSubScreenTopBar(title = "Detail Pengguna", onBack = onBack)

        if (user == null) {
            IxhEmptyStateCard(
                title = "Pengguna tidak ditemukan.",
                subtitle = "Pilih salah satu pengguna dari daftar Manajemen Pengguna untuk mengelola role dan nomor WhatsApp.",
                mascotState = BlobMascotState.IDLE
            )
        } else {
            IxhCard(
                backgroundColor = IxhYellow,
                borderColor = IxhInk,
                hasHardShadow = true,
                contentPadding = PaddingValues(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .neubrutalShadow(enabled = true, cornerRadius = 14.dp, offsetY = 2.5.dp, offsetX = 1.8.dp)
                                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 10.dp, bottomStart = 10.dp, bottomEnd = 18.dp))
                                .background(IxhCream)
                                .border(2.2.dp, IxhInk, RoundedCornerShape(topStart = 16.dp, topEnd = 10.dp, bottomStart = 10.dp, bottomEnd = 18.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "%02d".format(user.absenNo),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = IxhInk
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = user.fullName,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.ExtraBold,
                                color = IxhInk
                            )
                            Text(
                                text = "@${user.username} • Absen ${"%02d".format(user.absenNo)}",
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Bold,
                                color = IxhInk.copy(alpha = 0.8f)
                            )
                        }
                    }
                    IxhStatusChip(text = user.role, tone = ChipTone.NEUTRAL)
                }
            }

            IxhCard(backgroundColor = IxhWhite) {
                Text(
                    text = "INFORMASI KONTAK & WHATSAPP",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.ExtraBold,
                    color = IxhInk
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Nomor WhatsApp", style = MaterialTheme.typography.bodySmall, color = IxhTextSecondary)
                    Text(user.phoneWa, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.ExtraBold, color = IxhInk)
                }
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Status Pengiriman Bot", style = MaterialTheme.typography.bodySmall, color = IxhTextSecondary)
                    IxhStatusChip(
                        text = user.waStatus,
                        tone = if (user.waStatus == WaNumberStatus.VALID.name) ChipTone.GREEN else ChipTone.RED
                    )
                }
            }

            if (generatedTempPass != null) {
                IxhCard(
                    backgroundColor = IxhGreenBg,
                    borderColor = IxhInk,
                    hasHardShadow = true,
                    leftStripeColor = IxhGreen
                ) {
                    Text(
                        text = "PASSWORD SEMENTARA BERHASIL DIBUAT",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.ExtraBold,
                        color = IxhGreenText
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = generatedTempPass!!,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = IxhInk
                    )
                    Text(
                        text = "Pengguna wajib mengganti password saat login pertama kali.",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.SemiBold,
                        color = IxhInk
                    )
                }
            }

            IxhPrimaryButton(
                text = "Ubah Role Pengguna (${user.role})",
                onClick = { showRoleDialog = true }
            )

            IxhSecondaryButton(
                text = "Reset Password Sementara",
                onClick = { generatedTempPass = onResetPassword(user) }
            )

            IxhSecondaryButton(
                text = if (user.waStatus == WaNumberStatus.BERHENTI.name) "Aktifkan Lagi Pesan WA (MULAI)" else "Hentikan Pesan WA (STOP)",
                isDanger = user.waStatus != WaNumberStatus.BERHENTI.name,
                onClick = {
                    onToggleWaPreference(user, user.waStatus == WaNumberStatus.BERHENTI.name)
                }
            )
        }
    }

    if (showRoleDialog && user != null) {
        AlertDialog(
            onDismissRequest = { showRoleDialog = false },
            containerColor = IxhWhite,
            title = {
                Text(
                    text = "Ubah Role ${user.fullName}",
                    fontWeight = FontWeight.ExtraBold,
                    color = IxhInk
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf(
                        Triple(UserRole.SISWA.name, "", "Akses baca, lihat absensi & tagihan pribadi"),
                        Triple(UserRole.PENGURUS.name, "Sekretaris / Bendahara", "Kelola absensi, kas, jadwal, tugas, pengumuman & kirim laporan bot"),
                        Triple(UserRole.ADMIN.name, "Ketua Kelas / Admin", "Akses penuh termasuk server Pterodactyl, backup, log aktivitas & manajemen akun")
                    ).forEach { (rName, sub, desc) ->
                        IxhCard(
                            backgroundColor = if (user.role == rName) IxhYellowLight else IxhSurfaceMuted,
                            borderColor = IxhInk,
                            hasHardShadow = user.role == rName,
                            onClick = {
                                onUpdateRole(user, rName, sub)
                                showRoleDialog = false
                            }
                        ) {
                            Text(rName, fontWeight = FontWeight.ExtraBold, color = IxhInk)
                            Text(desc, style = MaterialTheme.typography.bodySmall, color = IxhTextSecondary)
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showRoleDialog = false }) {
                    Text("Tutup", color = IxhInk, fontWeight = FontWeight.ExtraBold)
                }
            }
        )
    }
}

@Composable
fun UserFormAdminScreen(
    existingUser: UserEntity?,
    onSaveUser: (UserEntity?, String, String, String, String, Boolean) -> Unit,
    onBack: () -> Unit
) {
    var fullName by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var roleIndex by remember { mutableIntStateOf(0) }
    val roles = listOf(UserRole.SISWA.name, UserRole.PENGURUS.name, UserRole.ADMIN.name)
    var phoneWa by remember { mutableStateOf("081234567890") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .ixhOrganicScreenBackdrop()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        IxhSubScreenTopBar(title = "Tambah Pengguna Baru", onBack = onBack)

        IxhOrganicModuleBanner(
            eyebrowTag = "Akun Kelas IX-H",
            title = "Registrasi Pengguna",
            subtitle = "Password sementara dibuat otomatis untuk onboarding awal.",
            iconType = IxhSystemIconType.PROFIL
        )

        IxhStyledTextField(
            value = fullName,
            onValueChange = {
                fullName = it
                if (username.isBlank()) {
                    username = it.lowercase().trim().replace(" ", ".")
                }
            },
            label = "Nama Lengkap Siswa",
            placeholder = "Contoh: Andi Pratama",
            testTag = "user_form_name_input"
        )

        IxhStyledTextField(
            value = username,
            onValueChange = { username = it },
            label = "Username Login",
            placeholder = "Contoh: andi.pratama",
            testTag = "user_form_username_input"
        )

        IxhSculptedSectionHeader(title = "Pilih Role Akun")
        IxhSegmentedTabs(
            tabs = roles,
            selectedIndex = roleIndex,
            onTabSelected = { roleIndex = it }
        )

        IxhStyledTextField(
            value = phoneWa,
            onValueChange = { phoneWa = it },
            label = "Nomor WhatsApp (Otomatis +62)",
            placeholder = "081234567890"
        )

        IxhPrimaryButton(
            text = "Simpan & Kirim Pesan Tes WA",
            onClick = {
                val finalName = fullName.ifBlank { "Siswa Baru IX-H" }
                val finalUser = username.ifBlank { "siswa.baru" }
                onSaveUser(null, finalName, finalUser, roles[roleIndex], phoneWa, true)
            },
            testTag = "user_form_save_button"
        )

        IxhSecondaryButton(
            text = "Simpan Tanpa Tes WA",
            onClick = {
                val finalName = fullName.ifBlank { "Siswa Baru IX-H" }
                val finalUser = username.ifBlank { "siswa.baru" }
                onSaveUser(null, finalName, finalUser, roles[roleIndex], phoneWa, false)
            }
        )
    }
}

@Composable
fun UserImportMassalScreen(
    onImportCompleted: (String) -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .ixhOrganicScreenBackdrop()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        IxhSubScreenTopBar(title = "Impor Massal (CSV/Excel)", onBack = onBack)

        IxhOrganicModuleBanner(
            eyebrowTag = "Impor Data Massal",
            title = "Impor Daftar Siswa",
            subtitle = "Tambahkan seluruh daftar kelas IX-H sekali klik dengan format CSV.",
            iconType = IxhSystemIconType.PROFIL
        )

        IxhCard(backgroundColor = IxhWhite, hasHardShadow = true) {
            Text(
                text = "LANGKAH 1: UNDUH FORMAT TEMPLATE",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.ExtraBold,
                color = IxhInk
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Gunakan format kolom: No_Absen, Nama_Lengkap, Username, Nomor_WA, Role",
                style = MaterialTheme.typography.bodySmall,
                color = IxhInk
            )
            Spacer(modifier = Modifier.height(10.dp))
            IxhSecondaryButton(
                text = "Unduh Template_Siswa_IXH.csv",
                icon = Icons.Outlined.Download,
                onClick = { onImportCompleted("Template_Siswa_IXH.csv berhasil diunduh") }
            )
        }

        IxhCard(
            backgroundColor = IxhYellowLight,
            borderColor = IxhInk,
            hasHardShadow = true,
            onClick = { onImportCompleted("Silakan pilih berkas CSV dari penyimpanan perangkat") }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 18.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IxhCircleIcon(
                    icon = Icons.Outlined.UploadFile,
                    bgColor = IxhYellow,
                    size = 54.dp
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Pilih File CSV dari Perangkat",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = IxhInk
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Validasi format nomor WhatsApp (+62) dan username unik sebelum impor",
                    style = MaterialTheme.typography.bodySmall,
                    color = IxhTextSecondary,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun UserProblematicNumbersScreen(
    users: List<UserEntity>,
    onRevalidateAll: () -> Unit,
    onOpenUserDetail: (UserEntity) -> Unit,
    onBack: () -> Unit
) {
    val problematic = users.filter { it.waStatus != WaNumberStatus.VALID.name }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .ixhOrganicScreenBackdrop()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        IxhSubScreenTopBar(title = "Nomor WA Bermasalah", onBack = onBack)

        IxhOrganicModuleBanner(
            eyebrowTag = "Validasi Kontak",
            title = "Pemeriksaan Nomor WA",
            subtitle = "Nomor non-aktif atau STOP dilewati otomatis demi keamanan bot.",
            iconType = IxhSystemIconType.BOT,
            containerColor = if (problematic.isEmpty()) IxhGreenBg else IxhYellowLight,
            accentWaveColor = if (problematic.isEmpty()) IxhGreen else IxhYellow
        )

        IxhAlertBanner(
            text = "Nomor di bawah ini otomatis dilewati saat pengiriman massal agar akun Bot WhatsApp tidak terkena blokir.",
            tone = ChipTone.YELLOW
        )

        if (problematic.isEmpty()) {
            IxhEmptyStateCard(
                title = "Semua nomor WhatsApp valid.",
                subtitle = "Tidak ada nomor siswa yang bermasalah atau berstatus STOP saat ini.",
                mascotState = BlobMascotState.SMILE
            )
        } else {
            problematic.forEach { u ->
                val (reason, tone, stripe) = when (u.waStatus) {
                    WaNumberStatus.TIDAK_DI_WA.name -> Triple("Nomor tidak aktif / tidak terdaftar di WhatsApp", ChipTone.RED, IxhRed)
                    WaNumberStatus.BERHENTI.name -> Triple("Siswa membalas STOP (${u.waStopTimestamp.ifBlank { "Baru saja" }})", ChipTone.NEUTRAL, IxhInk)
                    else -> Triple("Nomor baru — belum pernah dites kirim", ChipTone.YELLOW, IxhYellow)
                }
                IxhCard(
                    backgroundColor = IxhWhite,
                    leftStripeColor = stripe,
                    onClick = { onOpenUserDetail(u) }
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "${u.absenNo}. ${u.fullName}",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.ExtraBold,
                                color = IxhInk
                            )
                            Text(
                                text = u.phoneWa,
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Bold,
                                color = IxhInk
                            )
                            Text(
                                text = reason,
                                style = MaterialTheme.typography.labelSmall,
                                color = IxhTextSecondary
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        IxhStatusChip(text = u.waStatus, tone = tone)
                    }
                }
            }

            IxhPrimaryButton(
                text = "Periksa & Validasi Ulang Semua Nomor",
                onClick = onRevalidateAll
            )
        }
    }
}

// =========================================================================
// STORYBOARD 21: PENGATURAN KELAS (ADMIN EDITABLE vs PENGURUS READ-ONLY)
// =========================================================================

@Composable
fun ClassSettingsScreen(
    activeUser: UserEntity,
    settings: ClassSettingsState,
    onSaveSettings: (ClassSettingsState) -> Unit,
    onBack: () -> Unit
) {
    val isAdmin = activeUser.role == UserRole.ADMIN.name
    var viewAsPengurusDemo by remember { mutableStateOf(!isAdmin) }
    val canEdit = isAdmin && !viewAsPengurusDemo

    var className by remember(settings) { mutableStateOf(settings.className) }
    var schoolYear by remember(settings) { mutableStateOf(settings.schoolYear) }
    var weeklyKas by remember(settings) { mutableStateOf(settings.defaultWeeklyKas.toString()) }
    var selectedDueDay by remember(settings) { mutableStateOf(settings.defaultDueDay) }
    var autoReminder by remember(settings) { mutableStateOf(settings.autoReminderEnabled) }
    var safeThreshold by remember(settings) { mutableIntStateOf(settings.attendanceSafeThreshold) }

    val days = listOf("Sen", "Sel", "Rab", "Kam", "Jum", "Sab")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .ixhOrganicScreenBackdrop()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        IxhSubScreenTopBar(
            title = "Pengaturan Kelas",
            onBack = onBack,
            rightContent = {
                if (isAdmin) {
                    IxhStatusChip(
                        text = if (viewAsPengurusDemo) "Mode Admin" else "Mode Pengurus",
                        tone = ChipTone.OUTLINE_YELLOW,
                        modifier = Modifier.clickable { viewAsPengurusDemo = !viewAsPengurusDemo }
                    )
                }
            }
        )

        IxhOrganicModuleBanner(
            eyebrowTag = "Konfigurasi Kelas",
            title = settings.className,
            subtitle = "Tahun Ajaran ${settings.schoolYear} • Target Hadir $safeThreshold%",
            iconType = IxhSystemIconType.BERANDA
        )

        if (!canEdit) {
            IxhAlertBanner(
                title = "Hanya Baca (Dikelola Admin)",
                text = "Pengurus dapat melihat aturan kelas, namun perubahan nominal kas & batas kehadiran dikunci untuk Admin.",
                tone = ChipTone.YELLOW,
                icon = Icons.Outlined.Lock
            )
        }

        IxhCard(backgroundColor = IxhWhite, hasHardShadow = true) {
            Text(
                text = "IDENTITAS KELAS & TAHUN AJARAN",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.ExtraBold,
                color = IxhInk
            )
            Spacer(modifier = Modifier.height(10.dp))
            IxhStyledTextField(
                value = className,
                onValueChange = { if (canEdit) className = it },
                label = "Nama Kelas"
            )
            Spacer(modifier = Modifier.height(10.dp))
            IxhStyledTextField(
                value = schoolYear,
                onValueChange = { if (canEdit) schoolYear = it },
                label = "Tahun Ajaran & Semester"
            )
        }

        IxhCard(backgroundColor = IxhWhite, hasHardShadow = true) {
            Text(
                text = "ATURAN KAS MINGGUAN BAWAAN",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.ExtraBold,
                color = IxhInk
            )
            Spacer(modifier = Modifier.height(10.dp))
            IxhStyledTextField(
                value = weeklyKas,
                onValueChange = { if (canEdit) weeklyKas = it.filter { c -> c.isDigit() } },
                label = "Nominal Kas Bawaan (Rp)"
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "HARI JATUH TEMPO BAWAAN",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.ExtraBold,
                color = IxhInk
            )
            Spacer(modifier = Modifier.height(6.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                days.forEach { d ->
                    val sel = selectedDueDay == d
                    IxhSculptedFilterChip(
                        label = d,
                        selected = sel,
                        onClick = { if (canEdit) selectedDueDay = d },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Pengingat WA H-1 Jatuh Tempo",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    color = IxhInk
                )
                IxhNeubrutalToggle(
                    checked = autoReminder,
                    onCheckedChange = { autoReminder = it },
                    enabled = canEdit
                )
            }
        }

        IxhCard(backgroundColor = IxhYellowLight, borderColor = IxhInk, hasHardShadow = true) {
            Text(
                text = "BATAS AMAN KEHADIRAN SISWA",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.ExtraBold,
                color = IxhInk
            )
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Target Minimal Kehadiran", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.ExtraBold, color = IxhInk)
                    Text("Di bawah batas ini muncul peringatan di profil siswa", style = MaterialTheme.typography.bodySmall, color = IxhTextSecondary)
                }
                IxhStatusChip(text = "$safeThreshold%", tone = ChipTone.GREEN)
            }
        }

        if (canEdit) {
            IxhPrimaryButton(
                text = "Simpan Pengaturan Kelas",
                onClick = {
                    onSaveSettings(
                        settings.copy(
                            className = className,
                            schoolYear = schoolYear,
                            defaultWeeklyKas = weeklyKas.toIntOrNull() ?: 6000,
                            defaultDueDay = selectedDueDay,
                            autoReminderEnabled = autoReminder,
                            attendanceSafeThreshold = safeThreshold
                        )
                    )
                }
            )
        }
    }
}

// =========================================================================
// §22.2 AUDIT LOG AKTIVITAS PERMANEN (IMMUTABLE ADMIN AUDIT LOG)
// =========================================================================

@Composable
fun ActivityLogsScreen(
    activeUser: UserEntity,
    logs: List<ActivityLogEntity>,
    onOpenLogDetail: (ActivityLogEntity) -> Unit,
    onExportToast: (String) -> Unit,
    onBack: () -> Unit
) {
    val isAdmin = activeUser.role == UserRole.ADMIN.name
    var showLockedPengurusDemo by remember { mutableStateOf(!isAdmin) }
    var searchQuery by remember { mutableStateOf("") }
    var filterCat by remember { mutableIntStateOf(0) }
    var filterRole by remember { mutableIntStateOf(0) }
    val cats = listOf("Semua", "Sistem", "Backup", "Lunas", "Kas", "Absensi", "Jadwal", "Tugas", "Pengumuman", "Role", "Hapus", "Berhenti")
    val roles = listOf("Semua Role", "ADMIN", "PENGURUS", "SISWA")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .ixhOrganicScreenBackdrop()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        IxhSubScreenTopBar(
            title = "Audit Log Aktivitas",
            onBack = onBack,
            rightContent = {
                if (isAdmin) {
                    IxhStatusChip(
                        text = if (showLockedPengurusDemo) "Mode Admin" else "Cek Kunci Pengurus",
                        tone = ChipTone.OUTLINE_YELLOW,
                        modifier = Modifier.clickable { showLockedPengurusDemo = !showLockedPengurusDemo }
                    )
                }
            }
        )

        if (showLockedPengurusDemo) {
            IxhCard(
                backgroundColor = IxhWhite,
                hasHardShadow = true,
                contentPadding = PaddingValues(22.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    IxhCircleIcon(
                        icon = Icons.Outlined.Lock,
                        bgColor = IxhRedBg,
                        iconTint = IxhRed,
                        size = 54.dp
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Halaman Khusus Admin",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.ExtraBold,
                        color = IxhInk
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Audit Log mencatat seluruh operasi penting (CREATE, UPDATE, DELETE, BACKUP, RESTORE, ROLE_CHANGE) secara permanen dan hanya dapat diakses oleh Admin.",
                        style = MaterialTheme.typography.bodySmall,
                        color = IxhTextSecondary,
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            IxhOrganicModuleBanner(
                eyebrowTag = "Jejak Audit Permanen",
                title = "Audit Log Kelas IX-H",
                subtitle = "${logs.size} entri tercatat • Tidak dapat diubah atau dihapus.",
                iconType = IxhSystemIconType.PROFIL
            )

            IxhAlertBanner(
                title = "Jejak Audit Permanen (Tanpa Tombol Edit/Hapus)",
                text = "Log aktivitas tidak dapat diubah atau dihapus oleh siapa pun (termasuk Admin) dan tidak pernah menyimpan password maupun token rahasia.",
                tone = ChipTone.YELLOW,
                icon = Icons.Outlined.Lock
            )

            IxhStyledTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = "Cari pelaku, aksi, target, atau jenis operasi...",
                leadingIcon = Icons.Outlined.Search,
                testTag = "audit_log_search_input"
            )

            IxhSegmentedTabs(
                tabs = roles,
                selectedIndex = filterRole,
                onTabSelected = { filterRole = it }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                cats.forEachIndexed { idx, label ->
                    IxhSculptedFilterChip(
                        label = label,
                        selected = filterCat == idx,
                        onClick = { filterCat = idx }
                    )
                }
            }

            val filtered = logs.filter { log ->
                val matchesCat = filterCat == 0 || log.category.equals(cats[filterCat], ignoreCase = true)
                val matchesRole = filterRole == 0 || log.actorRole.equals(roles[filterRole], ignoreCase = true)
                val q = searchQuery.trim()
                val matchesQuery = q.isBlank() ||
                    log.actorName.contains(q, ignoreCase = true) ||
                    log.actionTitle.contains(q, ignoreCase = true) ||
                    log.detailText.contains(q, ignoreCase = true) ||
                    log.targetUser.contains(q, ignoreCase = true) ||
                    log.operationType.contains(q, ignoreCase = true)
                matchesCat && matchesRole && matchesQuery
            }

            if (filtered.isEmpty()) {
                IxhEmptyStateCard(
                    title = "Belum ada entri audit log.",
                    subtitle = "Setiap aksi penting (pembuatan akun, perubahan kas, absensi, backup/restore, dan perubahan role) otomatis tercatat di sini.",
                    mascotState = BlobMascotState.IDLE,
                    testTag = "audit_logs_empty_state"
                )
            } else {
                filtered.forEach { log ->
                    val (tone, stripe) = when (log.category) {
                        "Lunas", "Sistem", "Backup" -> ChipTone.GREEN to IxhGreen
                        "Hapus", "Berhenti" -> ChipTone.RED to IxhRed
                        "Role" -> ChipTone.YELLOW to IxhYellow
                        else -> ChipTone.BLUE to Color(0xFF1D4ED8)
                    }
                    val statusTone = if (log.statusLabel == "GAGAL") ChipTone.RED else ChipTone.GREEN
                    IxhCard(
                        backgroundColor = IxhWhite,
                        leftStripeColor = stripe,
                        onClick = { onOpenLogDetail(log) }
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                IxhStatusChip(text = log.category, tone = tone)
                                IxhStatusChip(text = log.operationType, tone = ChipTone.OUTLINE_YELLOW)
                                IxhStatusChip(text = log.statusLabel, tone = statusTone)
                            }
                            Text(
                                text = log.timeWib,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = IxhTextSecondary
                            )
                        }
                        Spacer(modifier = Modifier.height(7.dp))
                        Text(
                            text = "${log.actorName} (${log.actorRole}) — ${log.actionTitle}",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.ExtraBold,
                            color = IxhInk
                        )
                        Text(
                            text = log.detailText,
                            style = MaterialTheme.typography.bodySmall,
                            color = IxhTextSecondary
                        )
                    }
                }
            }

            IxhSecondaryButton(
                text = "Ekspor Audit Log (CSV)",
                icon = Icons.Outlined.Download,
                onClick = { onExportToast("Audit_Log_IXH.csv (${filtered.size} baris) berhasil diekspor") }
            )
        }
    }
}

@Composable
fun ActivityLogDetailScreen(
    log: ActivityLogEntity?,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .ixhOrganicScreenBackdrop()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        IxhSubScreenTopBar(title = "Detail Log Aktivitas", onBack = onBack)

        if (log == null) {
            IxhEmptyStateCard(
                title = "Entri log tidak ditemukan.",
                subtitle = "Pilih salah satu entri pada daftar Audit Log untuk memeriksa perbandingan nilai sebelum dan sesudah.",
                mascotState = BlobMascotState.IDLE
            )
        } else {
            IxhCard(
                backgroundColor = IxhYellow,
                borderColor = IxhInk,
                hasHardShadow = true,
                contentPadding = PaddingValues(16.dp)
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    IxhStatusChip(text = log.category, tone = ChipTone.NEUTRAL)
                    IxhStatusChip(text = log.operationType, tone = ChipTone.BLUE)
                    IxhStatusChip(
                        text = log.statusLabel,
                        tone = if (log.statusLabel == "GAGAL") ChipTone.RED else ChipTone.GREEN
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = log.actionTitle,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = IxhInk
                )
                Text(
                    text = "${log.dayHeader} • ${log.timeWib}",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    color = IxhInk.copy(alpha = 0.8f)
                )
            }

            IxhSculptedSectionHeader(title = "Perbandingan Nilai (Diff)")
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                val diffShape = RoundedCornerShape(topStart = 18.dp, topEnd = 10.dp, bottomStart = 10.dp, bottomEnd = 20.dp)
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .neubrutalShadow(enabled = true, cornerRadius = 14.dp, offsetY = 3.dp, offsetX = 2.dp)
                        .clip(diffShape)
                        .background(IxhRedBg, diffShape)
                        .border(2.2.dp, IxhInk, diffShape)
                        .padding(12.dp)
                ) {
                    Text("SEBELUM", fontSize = 9.5.sp, fontWeight = FontWeight.ExtraBold, color = IxhRed)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(log.beforeValue, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold, color = IxhInk)
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .neubrutalShadow(enabled = true, cornerRadius = 14.dp, offsetY = 3.dp, offsetX = 2.dp)
                        .clip(diffShape)
                        .background(IxhGreenBg, diffShape)
                        .border(2.2.dp, IxhInk, diffShape)
                        .padding(12.dp)
                ) {
                    Text("SESUDAH", fontSize = 9.5.sp, fontWeight = FontWeight.ExtraBold, color = IxhGreenText)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(log.afterValue, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold, color = IxhInk)
                }
            }

            IxhCard(backgroundColor = IxhWhite, hasHardShadow = true) {
                Text(
                    text = "INFORMASI PELAKU & PERANGKAT",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.ExtraBold,
                    color = IxhInk
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text("Pelaku: ${log.actorName} (${log.actorRole})", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold, color = IxhInk)
                Text("Target: ${log.targetUser}", style = MaterialTheme.typography.bodySmall, color = IxhInk)
                Text("Alasan: ${log.reason}", style = MaterialTheme.typography.bodySmall, color = IxhTextSecondary)
                Text("Perangkat: ${log.deviceName}", style = MaterialTheme.typography.labelSmall, color = IxhTextSecondary)
            }
        }
    }
}
