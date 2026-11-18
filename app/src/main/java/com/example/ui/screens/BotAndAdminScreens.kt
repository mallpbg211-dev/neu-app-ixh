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
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.PowerSettingsNew
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material.icons.outlined.RestartAlt
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material.icons.outlined.UploadFile
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
import com.example.ui.components.IxhEmptyStateCard
import com.example.ui.components.IxhNeubrutalToggle
import com.example.ui.components.IxhPrimaryButton
import com.example.ui.components.IxhSecondaryButton
import com.example.ui.components.IxhSegmentedTabs
import com.example.ui.components.IxhStatusChip
import com.example.ui.components.IxhSubScreenTopBar
import com.example.ui.components.IxhWaBubblePreview
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(IxhCream)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        IxhSubScreenTopBar(
            title = "Panel Bot WhatsApp",
            onBack = onBack,
            rightContent = {
                IxhStatusChip(
                    text = if (botState.isConnected) "Simulasi Putus" else "Simulasi Hubung",
                    tone = ChipTone.NEUTRAL,
                    modifier = Modifier.clickable(onClick = onToggleConnectionSimulated)
                )
            }
        )

        if (!botState.isConnected) {
            IxhCard(
                backgroundColor = IxhRedBg,
                borderColor = IxhRed,
                hasHardShadow = true,
                contentPadding = PaddingValues(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IxhBlobMascot(expression = BlobExpression.SAD_ERROR, size = 56.dp)
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        IxhStatusChip(text = "BELUM TERHUBUNG", tone = ChipTone.RED)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Bot WhatsApp Terputus",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = IxhInk
                        )
                        Text(
                            text = "Sesi ${botState.sessionName} • ${botState.activeSince}. Semua pengiriman otomatis dijeda.",
                            style = MaterialTheme.typography.bodySmall,
                            color = IxhRed
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                if (isAdmin) {
                    IxhPrimaryButton(
                        text = "Hubungkan Ulang (QR / Kode Pairing)",
                        onClick = { onNavigate(AppScreenRoute.BOT_RECONNECT_QR_PAIRING) }
                    )
                } else {
                    IxhAlertBanner(
                        text = "Hanya Admin Kelas IX-H yang dapat memindai ulang QR / Kode Pairing sesi Bot.",
                        tone = ChipTone.YELLOW
                    )
                }
            }
        } else {
            IxhCard(
                backgroundColor = IxhYellow,
                hasHardShadow = true,
                contentPadding = PaddingValues(14.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(CircleShape)
                                .background(IxhGreen)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "TERHUBUNG • ${botState.sessionName}",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = IxhInk
                        )
                    }
                    IxhStatusChip(text = "Aktif sejak ${botState.activeSince}", tone = ChipTone.GREEN)
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = botState.botPhoneNumber,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = IxhInk
                )
                Text(
                    text = "Jeda acak ${botState.randomDelayRange} • Maks ${botState.maxMessagesPerHour} pesan/jam • $validWaCount nomor valid",
                    style = MaterialTheme.typography.bodySmall,
                    color = IxhInk
                )
            }
        }

        if (isAdmin) {
            IxhCard(backgroundColor = IxhWhite) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Server Bot (Pterodactyl VPS)",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = IxhInk
                        )
                        Text(
                            text = "Status container: ${botState.pterodactylStatus}",
                            style = MaterialTheme.typography.bodySmall,
                            color = IxhTextSecondary
                        )
                    }
                    IxhStatusChip(
                        text = botState.pterodactylStatus,
                        tone = if (botState.pterodactylStatus == "Berjalan") ChipTone.GREEN else ChipTone.RED
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    IxhSecondaryButton(
                        text = "Restart Server",
                        icon = Icons.Outlined.RestartAlt,
                        onClick = { onPterodactylControl("restart") },
                        modifier = Modifier.weight(1f)
                    )
                    IxhSecondaryButton(
                        text = if (botState.pterodactylStatus == "Berhenti") "Start Server" else "Stop Server",
                        icon = Icons.Outlined.PowerSettingsNew,
                        isDanger = botState.pterodactylStatus != "Berhenti",
                        onClick = {
                            onPterodactylControl(
                                if (botState.pterodactylStatus == "Berhenti") "start" else "stop"
                            )
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

        // Quick Actions Grid
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            IxhPrimaryButton(
                text = "Kirim Laporan Kelas",
                icon = Icons.Outlined.Send,
                onClick = { onNavigate(AppScreenRoute.BOT_SELECT_REPORT) },
                modifier = Modifier.weight(1f)
            )
            IxhSecondaryButton(
                text = "Template Pesan (6)",
                onClick = { onNavigate(AppScreenRoute.BOT_TEMPLATES_LIST) },
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            IxhSecondaryButton(
                text = "Pengaturan Bot",
                onClick = { onNavigate(AppScreenRoute.BOT_SETTINGS_ADMIN) },
                modifier = Modifier.weight(1f)
            )
            IxhSecondaryButton(
                text = "Alur STOP/MULAI",
                onClick = { onNavigate(AppScreenRoute.BOT_STOP_MULAI_SIMULATOR) },
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Riwayat Pengiriman Terakhir",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = IxhInk
            )
            Text(
                text = "Lihat Semua →",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = IxhTextSecondary,
                modifier = Modifier.clickable { onNavigate(AppScreenRoute.BOT_HISTORY) }
            )
        }

        if (dispatchLogs.isEmpty()) {
            IxhEmptyStateCard(
                title = "Belum ada riwayat pengiriman bot.",
                subtitle = "Riwayat pengiriman laporan dan pengingat WhatsApp akan tercatat di sini setelah bot terhubung.",
                mascotState = BlobMascotState.IDLE
            )
        } else {
            dispatchLogs.take(4).forEach { log ->
                val tone = when {
                    log.failedCount > 0 && log.sentCount == 0 -> ChipTone.RED
                    log.failedCount > 0 -> ChipTone.YELLOW
                    else -> ChipTone.GREEN
                }
                IxhCard(
                    backgroundColor = IxhWhite,
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
                                fontWeight = FontWeight.Bold,
                                color = IxhInk
                            )
                            Text(
                                text = log.dateTimeLabel,
                                style = MaterialTheme.typography.bodySmall,
                                color = IxhTextSecondary
                            )
                        }
                        IxhStatusChip(text = log.statusLabel, tone = tone)
                    }
                }
            }
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
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        IxhSubScreenTopBar(title = "Pilih Jenis Laporan", onBack = onBack)

        reportTypes.forEachIndexed { idx, (title, sub, _) ->
            val selected = idx == selectedIdx
            IxhCard(
                backgroundColor = if (selected) IxhYellowLight else IxhWhite,
                borderColor = if (selected) IxhInk else null,
                hasHardShadow = selected,
                onClick = { selectedIdx = idx }
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = IxhInk
                        )
                        Text(
                            text = sub,
                            style = MaterialTheme.typography.bodySmall,
                            color = IxhTextSecondary
                        )
                    }
                    IxhStatusChip(
                        text = if (selected) "Dipilih ✓" else "Pilih",
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
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        IxhSubScreenTopBar(title = "Atur & Kirim Pesan", onBack = onBack)

        Text(
            text = "TUJUAN PENERIMA",
            style = MaterialTheme.typography.labelSmall,
            color = IxhTextSecondary
        )
        IxhSegmentedTabs(
            tabs = listOf("Nomor Valid ($validCount)", "Belum Lunas ($unpaidCount)", "Grup IX-H"),
            selectedIndex = recipientOption,
            onTabSelected = { recipientOption = it }
        )

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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        IxhSubScreenTopBar(title = "Riwayat Pengiriman Bot", onBack = onBack)

        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            cats.forEachIndexed { idx, label ->
                val sel = idx == filterIdx
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(if (sel) IxhInk else IxhSurfaceMuted)
                        .clickable { filterIdx = idx }
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                ) {
                    Text(
                        text = label,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (sel) IxhWhite else IxhTextSecondary
                    )
                }
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
                val tone = when {
                    log.failedCount > 0 && log.sentCount == 0 -> ChipTone.RED
                    log.failedCount > 0 -> ChipTone.YELLOW
                    else -> ChipTone.GREEN
                }
                IxhCard(
                    backgroundColor = IxhWhite,
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
                                fontWeight = FontWeight.Bold,
                                color = IxhInk
                            )
                            Text(
                                text = log.dateTimeLabel,
                                style = MaterialTheme.typography.bodySmall,
                                color = IxhTextSecondary
                            )
                            if (log.failureReason.isNotBlank()) {
                                Text(
                                    text = "Catatan: ${log.failureReason}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = IxhRed
                                )
                            }
                        }
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
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        IxhSubScreenTopBar(title = "Pengaturan Bot (Admin)", onBack = onBack)

        IxhCard(
            backgroundColor = IxhYellowLight,
            borderColor = IxhInk
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Sesi ${botState.sessionName}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = IxhInk
                    )
                    Text(
                        text = "${botState.botPhoneNumber} • ${botState.activeSince}",
                        style = MaterialTheme.typography.bodySmall,
                        color = IxhTextSecondary
                    )
                }
                IxhStatusChip(
                    text = if (botState.isConnected) "Terhubung" else "Terputus",
                    tone = if (botState.isConnected) ChipTone.GREEN else ChipTone.RED
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            IxhPrimaryButton(
                text = "Tautkan Ulang QR / Kode Pairing",
                onClick = onNavigateReconnect
            )
        }

        IxhCard(backgroundColor = IxhWhite) {
            Text(
                text = "KEAMANAN & ANTI-BLOKIR WHATSAPP",
                style = MaterialTheme.typography.labelSmall,
                color = IxhTextSecondary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Batas Pesan per Jam",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
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
                            .size(30.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(IxhSurfaceMuted)
                            .clickable { onUpdateMaxPerHour(-10) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text("-", fontWeight = FontWeight.ExtraBold, color = IxhInk)
                    }
                    Text(
                        text = "${botState.maxMessagesPerHour}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = IxhInk
                    )
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(IxhYellow)
                            .border(1.dp, IxhInk, RoundedCornerShape(8.dp))
                            .clickable { onUpdateMaxPerHour(10) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text("+", fontWeight = FontWeight.ExtraBold, color = IxhInk)
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Jeda Acak Antar Pesan", style = MaterialTheme.typography.bodySmall, color = IxhTextSecondary)
                Text("8–20 detik (Terkunci Aman)", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold, color = IxhGreenText)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Jeda otomatis saat koneksi terputus",
                    style = MaterialTheme.typography.bodySmall,
                    color = IxhInk
                )
                IxhNeubrutalToggle(checked = autoPause, onCheckedChange = { autoPause = it })
            }
        }

        IxhCard(
            backgroundColor = IxhWhite,
            onClick = onNavigateProblematicNumbers
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Status Nomor WA Siswa",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
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
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        IxhSubScreenTopBar(title = "Tautkan Perangkat WA", onBack = onBack)

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
                contentPadding = PaddingValues(16.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "KODE PAIRING WHATSAPP",
                        style = MaterialTheme.typography.labelSmall,
                        color = IxhTextSecondary
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        botState.pairingCode.forEachIndexed { idx, ch ->
                            if (idx == 4) {
                                Text(
                                    text = "-",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = IxhInk,
                                    modifier = Modifier.padding(horizontal = 2.dp, vertical = 4.dp)
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .size(width = 30.dp, height = 38.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(IxhWhite)
                                    .border(1.5.dp, IxhInk, RoundedCornerShape(8.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = ch.toString(),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = IxhInk
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Buka WhatsApp → Perangkat Tertaut → Tautkan dengan nomor telepon saja.",
                        style = MaterialTheme.typography.bodySmall,
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
                contentPadding = PaddingValues(16.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Canvas(
                        modifier = Modifier
                            .size(160.dp)
                            .border(2.dp, IxhInk, RoundedCornerShape(12.dp))
                            .padding(12.dp)
                    ) {
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
                                        size = Size(cell * 0.85f, cell * 0.85f)
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Pindai QR ini dari WhatsApp nomor Bot (${botState.botPhoneNumber})",
                        style = MaterialTheme.typography.bodySmall,
                        color = IxhTextSecondary,
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
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        IxhSubScreenTopBar(title = "Log Kegagalan Bot", onBack = onBack)

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
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = log.title,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = IxhInk
                        )
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
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        IxhSubScreenTopBar(title = "Template Pesan WA", onBack = onBack)

        IxhAlertBanner(
            text = "Variabel resmi: {nama}, {nominal}, {jatuh_tempo}. Baris 'Balas STOP' dikunci otomatis demi kepatuhan anti-spam.",
            tone = ChipTone.BLUE
        )

        templates.forEach { tpl ->
            IxhCard(
                backgroundColor = IxhWhite,
                onClick = { onOpenEditor(tpl) }
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = tpl.name,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = IxhInk
                        )
                        Text(
                            text = tpl.subtitle,
                            style = MaterialTheme.typography.bodySmall,
                            color = IxhTextSecondary
                        )
                    }
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
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        IxhSubScreenTopBar(title = "Edit Template WA", onBack = onBack)

        Text(
            text = item.name,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.ExtraBold,
            color = IxhInk
        )

        if (item.id == "pengingat_kas") {
            Text(
                text = "NADA PENGINGAT (RAMAH / BIASA / TEGAS)",
                style = MaterialTheme.typography.labelSmall,
                color = IxhTextSecondary
            )
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

        Text(
            text = "SISIPKAN VARIABEL CEPAT",
            style = MaterialTheme.typography.labelSmall,
            color = IxhTextSecondary
        )
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            listOf("{nama}", "{nominal}", "{jatuh_tempo}").forEach { token ->
                IxhStatusChip(
                    text = "+ $token",
                    tone = ChipTone.OUTLINE_YELLOW,
                    modifier = Modifier.clickable {
                        bodyText = "$bodyText $token"
                    }
                )
            }
        }

        OutlinedTextField(
            value = bodyText,
            onValueChange = {
                bodyText = it
                errorMessage = null
            },
            label = { Text("Isi Template Pesan") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 4,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = IxhWhite,
                unfocusedContainerColor = IxhWhite
            )
        )

        if (errorMessage != null) {
            IxhAlertBanner(text = errorMessage!!, tone = ChipTone.RED)
        }

        Text(
            text = "PRATINJAU PESAN WHATSAPP",
            style = MaterialTheme.typography.labelSmall,
            color = IxhTextSecondary
        )
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
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        IxhSubScreenTopBar(title = "Alur STOP & MULAI WA", onBack = onBack)

        IxhAlertBanner(
            text = "Siswa dapat membalas STOP kapan saja di WhatsApp untuk menghentikan pesan otomatis, atau MULAI untuk mengaktifkan kembali.",
            tone = ChipTone.BLUE
        )

        Text(
            text = "1. SAAT SISWA MEMBALAS 'STOP'",
            style = MaterialTheme.typography.labelSmall,
            color = IxhTextSecondary
        )
        IxhWaBubblePreview(
            senderTitle = "IX-H Hub",
            messageBody = "Halo *${sampleStudent?.fullName ?: "Siswa IX-H"}*, tagihan Kas Mingguan sebesar Rp6.000 telah diterbitkan.\nBalas STOP untuk berhenti.",
            timeLabel = "19.38",
            outgoingReply = "STOP"
        )
        IxhCard(backgroundColor = IxhGreenBg, borderColor = IxhGreen) {
            Text(
                text = "Balasan Otomatis Bot:",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = IxhGreenText
            )
            Text(
                text = "[OK] Nomor kamu telah dihentikan dari pengiriman pesan otomatis IX-H Hub. Ketik *MULAI* kapan saja jika ingin menerima pengingat lagi.",
                style = MaterialTheme.typography.bodySmall,
                color = IxhInk
            )
        }

        Text(
            text = "2. KENDALI LANGSUNG DI APLIKASI",
            style = MaterialTheme.typography.labelSmall,
            color = IxhTextSecondary
        )
        if (sampleStudent == null) {
            IxhEmptyStateCard(
                title = "Belum ada akun siswa terdaftar.",
                subtitle = "Tambahkan akun siswa terlebih dahulu untuk mengatur status langganan STOP/MULAI WhatsApp.",
                mascotState = BlobMascotState.IDLE
            )
        } else {
            IxhCard(backgroundColor = IxhWhite) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = sampleStudent.fullName,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
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
                Spacer(modifier = Modifier.height(10.dp))
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
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
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

        if (problematicCount > 0) {
            IxhAlertBanner(
                title = "$problematicCount nomor WA perlu diperiksa",
                text = "Ketuk untuk memeriksa nomor yang belum dicek, tidak terdaftar, atau berhenti (STOP).",
                tone = ChipTone.RED,
                onClick = onNavigateProblematicNumbers
            )
        }

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Cari nama siswa atau username...") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = IxhWhite,
                unfocusedContainerColor = IxhWhite
            )
        )

        IxhSegmentedTabs(
            tabs = listOf("Semua (${users.size})", "Pengurus ($pengurusCount)", "Siswa ($siswaCount)", "Bermasalah ($problematicCount)"),
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
                    onClick = { onOpenUserDetail(user) },
                    contentPadding = PaddingValues(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(CircleShape)
                                .background(if (user.role == UserRole.ADMIN.name) IxhYellow else IxhYellowLight)
                                .border(1.5.dp, IxhInk, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "%02d".format(user.absenNo),
                                fontSize = 11.sp,
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
                                    fontWeight = FontWeight.Bold,
                                    color = IxhInk
                                )
                                IxhStatusChip(
                                    text = if (user.subRoleTitle.isNotBlank()) "${user.role} • ${user.subRoleTitle}" else user.role,
                                    tone = if (user.role == UserRole.SISWA.name) ChipTone.NEUTRAL else ChipTone.OUTLINE_YELLOW
                                )
                            }
                            Text(
                                text = "@${user.username} • ${user.phoneWa}",
                                style = MaterialTheme.typography.bodySmall,
                                color = IxhTextSecondary
                            )
                        }
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
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
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
                backgroundColor = IxhYellowLight,
                borderColor = IxhInk,
                hasHardShadow = true
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = user.fullName,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.ExtraBold,
                            color = IxhInk
                        )
                        Text(
                            text = "@${user.username} • Absen No. ${"%02d".format(user.absenNo)}",
                            style = MaterialTheme.typography.bodySmall,
                            color = IxhTextSecondary
                        )
                    }
                    IxhStatusChip(text = user.role, tone = ChipTone.OUTLINE_YELLOW)
                }
            }

            IxhCard(backgroundColor = IxhWhite) {
                Text("INFORMASI KONTAK & WHATSAPP", style = MaterialTheme.typography.labelSmall, color = IxhTextSecondary)
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Nomor WhatsApp", style = MaterialTheme.typography.bodySmall, color = IxhTextSecondary)
                    Text(user.phoneWa, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold, color = IxhInk)
                }
                Spacer(modifier = Modifier.height(4.dp))
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
                    borderColor = IxhGreen
                ) {
                    Text(
                        text = "PASSWORD SEMENTARA BERHASIL DIBUAT",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = IxhGreenText
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = generatedTempPass!!,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = IxhInk
                    )
                    Text(
                        text = "Pengguna wajib mengganti password saat login pertama kali.",
                        style = MaterialTheme.typography.bodySmall,
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
                            borderColor = if (user.role == rName) IxhInk else IxhBorder,
                            onClick = {
                                onUpdateRole(user, rName, sub)
                                showRoleDialog = false
                            }
                        ) {
                            Text(rName, fontWeight = FontWeight.Bold, color = IxhInk)
                            Text(desc, style = MaterialTheme.typography.bodySmall, color = IxhTextSecondary)
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showRoleDialog = false }) {
                    Text("Tutup", color = IxhInk, fontWeight = FontWeight.Bold)
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
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        IxhSubScreenTopBar(title = "Tambah Pengguna Baru", onBack = onBack)

        OutlinedTextField(
            value = fullName,
            onValueChange = {
                fullName = it
                if (username.isBlank()) {
                    username = it.lowercase().trim().replace(" ", ".")
                }
            },
            label = { Text("Nama Lengkap Siswa") },
            modifier = Modifier.fillMaxWidth().testTag("user_form_name_input"),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(focusedContainerColor = IxhWhite, unfocusedContainerColor = IxhWhite)
        )

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username Login") },
            modifier = Modifier.fillMaxWidth().testTag("user_form_username_input"),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(focusedContainerColor = IxhWhite, unfocusedContainerColor = IxhWhite)
        )

        Text("PILIH ROLE", style = MaterialTheme.typography.labelSmall, color = IxhTextSecondary)
        IxhSegmentedTabs(
            tabs = roles,
            selectedIndex = roleIndex,
            onTabSelected = { roleIndex = it }
        )

        OutlinedTextField(
            value = phoneWa,
            onValueChange = { phoneWa = it },
            label = { Text("Nomor WhatsApp (Otomatis diformat +62)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(focusedContainerColor = IxhWhite, unfocusedContainerColor = IxhWhite)
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
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        IxhSubScreenTopBar(title = "Impor Massal (CSV/Excel)", onBack = onBack)

        IxhCard(backgroundColor = IxhWhite) {
            Text(
                text = "LANGKAH 1: UNDUH FORMAT TEMPLATE",
                style = MaterialTheme.typography.labelSmall,
                color = IxhTextSecondary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Gunakan format kolom: No_Absen, Nama_Lengkap, Username, Nomor_WA, Role",
                style = MaterialTheme.typography.bodySmall,
                color = IxhInk
            )
            Spacer(modifier = Modifier.height(8.dp))
            IxhSecondaryButton(
                text = "Unduh Template_Siswa_IXH.csv",
                icon = Icons.Outlined.Download,
                onClick = { onImportCompleted("Template_Siswa_IXH.csv berhasil diunduh") }
            )
        }

        IxhCard(
            backgroundColor = IxhYellowLight,
            borderColor = IxhInk,
            onClick = { onImportCompleted("Silakan pilih berkas CSV dari penyimpanan perangkat") }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 14.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Outlined.UploadFile,
                    contentDescription = null,
                    tint = IxhInk,
                    modifier = Modifier.size(36.dp)
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Pilih File CSV dari Perangkat",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = IxhInk
                )
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
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        IxhSubScreenTopBar(title = "Nomor WA Bermasalah", onBack = onBack)

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
                val (reason, tone) = when (u.waStatus) {
                    WaNumberStatus.TIDAK_DI_WA.name -> "Nomor tidak aktif / tidak terdaftar di WhatsApp" to ChipTone.RED
                    WaNumberStatus.BERHENTI.name -> "Siswa membalas STOP (${u.waStopTimestamp.ifBlank { "Baru saja" }})" to ChipTone.NEUTRAL
                    else -> "Nomor baru — belum pernah dites kirim" to ChipTone.YELLOW
                }
                IxhCard(
                    backgroundColor = IxhWhite,
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
                                fontWeight = FontWeight.Bold,
                                color = IxhInk
                            )
                            Text(
                                text = u.phoneWa,
                                style = MaterialTheme.typography.bodySmall,
                                color = IxhInk
                            )
                            Text(
                                text = reason,
                                style = MaterialTheme.typography.labelSmall,
                                color = IxhTextSecondary
                            )
                        }
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
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        IxhSubScreenTopBar(
            title = "Pengaturan Kelas",
            onBack = onBack,
            rightContent = {
                if (isAdmin) {
                    IxhStatusChip(
                        text = if (viewAsPengurusDemo) "Mode Admin" else "Lihat Mode Pengurus",
                        tone = ChipTone.OUTLINE_YELLOW,
                        modifier = Modifier.clickable { viewAsPengurusDemo = !viewAsPengurusDemo }
                    )
                }
            }
        )

        if (!canEdit) {
            IxhAlertBanner(
                title = "Hanya Baca (Dikelola Admin)",
                text = "Pengurus dapat melihat aturan kelas, namun perubahan nominal kas & batas kehadiran dikunci untuk Admin.",
                tone = ChipTone.YELLOW,
                icon = Icons.Outlined.Lock
            )
        }

        IxhCard(backgroundColor = IxhWhite) {
            Text("IDENTITAS KELAS & TAHUN AJARAN", style = MaterialTheme.typography.labelSmall, color = IxhTextSecondary)
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = className,
                onValueChange = { if (canEdit) className = it },
                enabled = canEdit,
                label = { Text("Nama Kelas") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = schoolYear,
                onValueChange = { if (canEdit) schoolYear = it },
                enabled = canEdit,
                label = { Text("Tahun Ajaran & Semester") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
        }

        IxhCard(backgroundColor = IxhWhite) {
            Text("ATURAN KAS MINGGUAN BAWAAN", style = MaterialTheme.typography.labelSmall, color = IxhTextSecondary)
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = weeklyKas,
                onValueChange = { if (canEdit) weeklyKas = it.filter { c -> c.isDigit() } },
                enabled = canEdit,
                label = { Text("Nominal Kas Bawaan (Rp)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text("Hari Jatuh Tempo Bawaan", style = MaterialTheme.typography.labelSmall, color = IxhTextSecondary)
            Spacer(modifier = Modifier.height(4.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                days.forEach { d ->
                    val sel = selectedDueDay == d
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (sel) IxhYellow else IxhSurfaceMuted)
                            .border(1.dp, if (sel) IxhInk else IxhBorder, RoundedCornerShape(8.dp))
                            .clickable(enabled = canEdit) { selectedDueDay = d }
                            .padding(vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(d, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = IxhInk)
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Pengingat WA H-1 Jatuh Tempo", style = MaterialTheme.typography.bodySmall, color = IxhInk)
                IxhNeubrutalToggle(
                    checked = autoReminder,
                    onCheckedChange = { autoReminder = it },
                    enabled = canEdit
                )
            }
        }

        IxhCard(backgroundColor = IxhWhite) {
            Text("BATAS AMAN KEHADIRAN SISWA", style = MaterialTheme.typography.labelSmall, color = IxhTextSecondary)
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Target Minimal Kehadiran", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = IxhInk)
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
            .background(IxhCream)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        IxhSubScreenTopBar(
            title = "Audit Log Aktivitas",
            onBack = onBack,
            rightContent = {
                if (isAdmin) {
                    IxhStatusChip(
                        text = if (showLockedPengurusDemo) "Mode Admin" else "Cek Kunci Pengurus",
                        tone = ChipTone.NEUTRAL,
                        modifier = Modifier.clickable { showLockedPengurusDemo = !showLockedPengurusDemo }
                    )
                }
            }
        )

        if (showLockedPengurusDemo) {
            IxhCard(
                backgroundColor = IxhWhite,
                contentPadding = PaddingValues(20.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    IxhCircleIcon(
                        icon = Icons.Outlined.Lock,
                        bgColor = IxhRedBg,
                        iconTint = IxhRed,
                        size = 48.dp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Halaman Khusus Admin",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.ExtraBold,
                        color = IxhInk
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Audit Log mencatat seluruh operasi penting (CREATE, UPDATE, DELETE, BACKUP, RESTORE, ROLE_CHANGE) secara permanen dan hanya dapat diakses oleh Admin.",
                        style = MaterialTheme.typography.bodySmall,
                        color = IxhTextSecondary,
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            IxhAlertBanner(
                title = "Jejak Audit Permanen (Tanpa Tombol Edit/Hapus)",
                text = "Log aktivitas tidak dapat diubah atau dihapus oleh siapa pun (termasuk Admin) dan tidak pernah menyimpan password maupun token rahasia.",
                tone = ChipTone.YELLOW,
                icon = Icons.Outlined.Lock
            )

            // Search Input for Audit Log
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Cari pelaku, aksi, target, atau jenis operasi...") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Search,
                        contentDescription = "Cari Log",
                        tint = IxhInk
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("audit_log_search_input"),
                singleLine = true,
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = IxhWhite,
                    unfocusedContainerColor = IxhWhite
                )
            )

            // Role Filter Tabs
            IxhSegmentedTabs(
                tabs = roles,
                selectedIndex = filterRole,
                onTabSelected = { filterRole = it }
            )

            // Category Filter Pills
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                cats.forEachIndexed { idx, label ->
                    val sel = filterCat == idx
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(if (sel) IxhInk else IxhSurfaceMuted)
                            .clickable { filterCat = idx }
                            .padding(horizontal = 10.dp, vertical = 5.dp)
                    ) {
                        Text(
                            text = label,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (sel) IxhWhite else IxhTextSecondary
                        )
                    }
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
                    val tone = when (log.category) {
                        "Lunas", "Sistem", "Backup" -> ChipTone.GREEN
                        "Hapus", "Berhenti" -> ChipTone.RED
                        "Role" -> ChipTone.YELLOW
                        else -> ChipTone.BLUE
                    }
                    val statusTone = if (log.statusLabel == "GAGAL") ChipTone.RED else ChipTone.GREEN
                    IxhCard(
                        backgroundColor = IxhWhite,
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
                                color = IxhTextSecondary
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "${log.actorName} (${log.actorRole}) — ${log.actionTitle}",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
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
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
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
                backgroundColor = IxhYellowLight,
                borderColor = IxhInk
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    IxhStatusChip(text = log.category, tone = ChipTone.OUTLINE_YELLOW)
                    IxhStatusChip(text = log.operationType, tone = ChipTone.BLUE)
                    IxhStatusChip(
                        text = log.statusLabel,
                        tone = if (log.statusLabel == "GAGAL") ChipTone.RED else ChipTone.GREEN
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = log.actionTitle,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = IxhInk
                )
                Text(
                    text = "${log.dayHeader} • ${log.timeWib}",
                    style = MaterialTheme.typography.bodySmall,
                    color = IxhTextSecondary
                )
            }

            IxhCard(backgroundColor = IxhWhite) {
                Text("PERBANDINGAN SEBELUM & SESUDAH", style = MaterialTheme.typography.labelSmall, color = IxhTextSecondary)
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(10.dp))
                            .background(IxhRedBg)
                            .padding(10.dp)
                    ) {
                        Text("SEBELUM", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = IxhRed)
                        Text(log.beforeValue, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold, color = IxhInk)
                    }
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(10.dp))
                            .background(IxhGreenBg)
                            .padding(10.dp)
                    ) {
                        Text("SESUDAH", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = IxhGreenText)
                        Text(log.afterValue, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold, color = IxhInk)
                    }
                }
            }

            IxhCard(backgroundColor = IxhWhite) {
                Text("INFORMASI PELAKU & PERANGKAT", style = MaterialTheme.typography.labelSmall, color = IxhTextSecondary)
                Spacer(modifier = Modifier.height(6.dp))
                Text("Pelaku: ${log.actorName} (${log.actorRole})", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold, color = IxhInk)
                Text("Target: ${log.targetUser}", style = MaterialTheme.typography.bodySmall, color = IxhInk)
                Text("Alasan: ${log.reason}", style = MaterialTheme.typography.bodySmall, color = IxhTextSecondary)
                Text("Perangkat: ${log.deviceName}", style = MaterialTheme.typography.labelSmall, color = IxhTextSecondary)
            }
        }
    }
}
