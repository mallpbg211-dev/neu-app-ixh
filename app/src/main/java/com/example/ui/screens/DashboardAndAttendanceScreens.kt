package com.example.ui.screens

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
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.WarningAmber
import androidx.compose.material.icons.outlined.WifiOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.AnnouncementEntity
import com.example.data.local.AssignmentEntity
import com.example.data.local.AttendanceStatus
import com.example.data.local.BillPaymentStatus
import com.example.data.local.CashTransactionEntity
import com.example.data.local.ScheduleEntity
import com.example.data.local.UserEntity
import com.example.data.local.UserRole
import com.example.ui.components.BlobExpression
import com.example.ui.components.ChipTone
import com.example.ui.components.IxhAlertBanner
import com.example.ui.components.IxhCard
import com.example.ui.components.IxhCircleIcon
import com.example.ui.components.IxhDonutChart
import com.example.ui.components.IxhDualToneSystemIcon
import com.example.ui.components.IxhEmptyStateCard
import com.example.ui.components.IxhOrganicGreetingCard
import com.example.ui.components.IxhOrganicModuleBanner
import com.example.ui.components.IxhPrimaryButton
import com.example.ui.components.IxhSculptedSectionHeader
import com.example.ui.components.IxhSecondaryButton
import com.example.ui.components.IxhStatusChip
import com.example.ui.components.IxhSubScreenTopBar
import com.example.ui.components.IxhSystemIconType
import com.example.ui.components.IxhTopBrandHeader
import com.example.ui.components.MainNavTab
import com.example.ui.components.formatRupiah
import com.example.ui.components.neubrutalShadow
import com.example.ui.theme.IxhAmber
import com.example.ui.theme.IxhAmberBg
import com.example.ui.theme.IxhBlue
import com.example.ui.theme.IxhBlueBg
import com.example.ui.theme.IxhBorder
import com.example.ui.theme.IxhCream
import com.example.ui.theme.IxhGreen
import com.example.ui.theme.IxhGreenBg
import com.example.ui.theme.IxhInk
import com.example.ui.theme.IxhRed
import com.example.ui.theme.IxhRedBg
import com.example.ui.theme.IxhSurfaceMuted
import com.example.ui.theme.IxhTextSecondary
import com.example.ui.theme.IxhWhite
import com.example.ui.theme.IxhYellow
import com.example.ui.theme.IxhYellowLight
import com.example.ui.theme.IxhYellowSoft
import com.example.ui.viewmodel.AppScreenRoute
import com.example.ui.viewmodel.BotConnectionState

data class QuickActionTileData(
    val systemIcon: IxhSystemIconType,
    val label: String,
    val badgeText: String = "",
    val testTag: String? = null,
    val onClick: () -> Unit
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    activeUser: UserEntity?,
    allUsers: List<UserEntity>,
    transactions: List<CashTransactionEntity> = emptyList(),
    pinnedAnnouncement: AnnouncementEntity?,
    closestAssignment: AssignmentEntity?,
    todaySchedules: List<ScheduleEntity>,
    unreadNotificationCount: Int,
    botState: BotConnectionState,
    onNavigateRoute: (AppScreenRoute) -> Unit,
    onSelectMainTab: (MainNavTab) -> Unit,
    onSwitchPersona: (String) -> Unit,
    onOpenKasTab: (Int) -> Unit,
    pendingSyncCount: Int = 0
) {
    var showPersonaSheet by remember { mutableStateOf(false) }
    val role = activeUser?.role ?: UserRole.ADMIN.name
    val isStudent = role == UserRole.SISWA.name
    val canManageClass = role == UserRole.ADMIN.name || role == UserRole.PENGURUS.name

    // 100% Real computed metrics from Room database (zero fake coercions)
    val presentCount = allUsers.count { it.attendanceToday == AttendanceStatus.HADIR.name }
    val totalStudents = allUsers.size
    val attendanceProgress = if (totalStudents > 0) presentCount.toFloat() / totalStudents.toFloat() else 0f
    val attendancePctLabel = if (totalStudents > 0) {
        String.format("%.0f%%", (presentCount * 100f) / totalStudents)
    } else {
        "0%"
    }
    val netCashBalance = transactions.sumOf { if (it.isIncome) it.amount else -it.amount }
    val unpaidCount = allUsers.count { it.billStatus != BillPaymentStatus.LUNAS.name && it.billAmount > 0 }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(IxhCream)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 14.dp, vertical = 6.dp)
            .testTag("dashboard_scroll_container")
    ) {
        IxhTopBrandHeader(
            roleLabel = role,
            unreadNotificationCount = unreadNotificationCount,
            onNotificationsClick = { onNavigateRoute(AppScreenRoute.NOTIFICATIONS_CENTER) },
            onProfileClick = { onNavigateRoute(AppScreenRoute.PROFILE_MAIN) },
            onRoleBadgeClick = { showPersonaSheet = true },
            onSearchClick = { onNavigateRoute(AppScreenRoute.GLOBAL_SEARCH) }
        )

        val displayFullName = activeUser?.fullName?.ifBlank { "Admin Kelas IX-H" } ?: "Admin Kelas IX-H"
        val subRoleLabel = activeUser?.subRoleTitle?.ifBlank { role } ?: role
        val subtitleGreeting = when (role) {
            UserRole.ADMIN.name -> "IX-H • SMPN 1 Bojongsari • Kendali Penuh Kelas"
            UserRole.PENGURUS.name -> "IX-H • SMPN 1 Bojongsari • Pengurus Kelas"
            else -> "IX-H • SMPN 1 Bojongsari • Portal Siswa"
        }

        // Signature Organic Greeting Card with Asymmetric Curves, 3D Blob Mascot, and Global Search Bar
        IxhOrganicGreetingCard(
            greetingTitle = "Selamat Datang di IX-H HUB 1.0",
            userName = displayFullName,
            subRoleLabel = subRoleLabel,
            classSubTitle = subtitleGreeting,
            syncStatusLabel = if (botState.isConnected) "VPS Terhubung" else "Mode Lokal Aktif",
            pendingSyncCount = pendingSyncCount,
            onSearchBarClick = { onNavigateRoute(AppScreenRoute.GLOBAL_SEARCH) },
            onSyncStatusClick = { onNavigateRoute(AppScreenRoute.SETTINGS_VPS_SYNC) },
            blobExpression = BlobExpression.SMILE
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Honest VPS / Bot Connection Status Banner for Admin
        if (!botState.isConnected && role == UserRole.ADMIN.name) {
            IxhAlertBanner(
                title = "Bot WhatsApp / VPS Belum Terhubung",
                text = botState.lastVpsSyncStatus,
                tone = ChipTone.YELLOW,
                icon = Icons.Outlined.WifiOff,
                onClick = { onNavigateRoute(AppScreenRoute.SETTINGS_VPS_SYNC) }
            )
            Spacer(modifier = Modifier.height(10.dp))
        }

        // Bento Summary Grid (Real Data from Room SQLite)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            if (!isStudent) {
                IxhCard(
                    modifier = Modifier
                        .weight(1f)
                        .testTag("dashboard_attendance_summary_card"),
                    hasHardShadow = true,
                    contentPadding = PaddingValues(0.dp),
                    onClick = { onSelectMainTab(MainNavTab.ABSENSI) }
                ) {
                    // Sculpted Top Header Band
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(IxhYellowLight)
                            .padding(horizontal = 12.dp, vertical = 7.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "KEHADIRAN HARI INI",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = IxhInk
                        )
                        IxhDualToneSystemIcon(iconType = IxhSystemIconType.ABSENSI, size = 20.dp)
                    }
                    Box(modifier = Modifier.fillMaxWidth().height(1.8.dp).background(IxhInk))
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 10.dp)
                    ) {
                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(
                                text = "$presentCount / $totalStudents",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.ExtraBold,
                                color = IxhInk
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (presentCount > 0) IxhGreenBg else IxhSurfaceMuted)
                                    .border(1.4.dp, IxhInk, RoundedCornerShape(8.dp))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = attendancePctLabel,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = IxhInk,
                                    fontWeight = FontWeight.ExtraBold
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        LinearProgressIndicator(
                            progress = { attendanceProgress },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(9.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .border(1.5.dp, IxhInk, RoundedCornerShape(6.dp)),
                            color = IxhGreen,
                            trackColor = IxhSurfaceMuted
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = if (totalStudents == 0) "Belum ada anggota" else "Ketuk untuk kelola absensi →",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = IxhTextSecondary
                        )
                    }
                }

                IxhCard(
                    modifier = Modifier
                        .weight(1f)
                        .testTag("dashboard_cash_summary_card"),
                    backgroundColor = IxhYellowSoft,
                    hasHardShadow = true,
                    contentPadding = PaddingValues(0.dp),
                    onClick = {
                        onOpenKasTab(0)
                        onSelectMainTab(MainNavTab.KAS)
                    }
                ) {
                    // Bold Charcoal Header Band with Yellow Text
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(IxhInk)
                            .padding(horizontal = 12.dp, vertical = 7.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "SALDO KAS IX-H",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = IxhYellow
                        )
                        IxhDualToneSystemIcon(iconType = IxhSystemIconType.KAS, size = 20.dp)
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 10.dp)
                    ) {
                        Text(
                            text = formatRupiah(netCashBalance),
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.ExtraBold,
                            color = IxhInk,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        IxhStatusChip(
                            text = if (transactions.isEmpty()) "0 Transaksi" else "${transactions.size} Transaksi",
                            tone = if (transactions.isEmpty()) ChipTone.NEUTRAL else ChipTone.GREEN
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = if (unpaidCount > 0) "$unpaidCount siswa belum lunas" else "Buku kas kelas aktif →",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = if (unpaidCount > 0) IxhAmber else IxhInk
                        )
                    }
                }
            } else {
                val myPresent = activeUser?.presentDaysCount ?: 0
                val myTotal = activeUser?.totalEffectiveDays ?: 0
                val myPct = if (myTotal > 0) ((myPresent * 100f) / myTotal).toInt() else 0
                val isPaid = activeUser?.billStatus == BillPaymentStatus.LUNAS.name
                val billAmt = activeUser?.billAmount ?: 0

                IxhCard(
                    modifier = Modifier.weight(1f),
                    hasHardShadow = true,
                    onClick = { onNavigateRoute(AppScreenRoute.STUDENT_MY_ATTENDANCE) }
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("KEHADIRANKU", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.ExtraBold)
                        IxhDualToneSystemIcon(iconType = IxhSystemIconType.ABSENSI, size = 18.dp)
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "$myPresent / $myTotal hari",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    LinearProgressIndicator(
                        progress = { if (myTotal > 0) myPresent.toFloat() / myTotal.toFloat() else 0f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp)),
                        color = IxhGreen,
                        trackColor = IxhSurfaceMuted
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Rasio hadir: $myPct%", style = MaterialTheme.typography.labelSmall, color = IxhGreen)
                }

                IxhCard(
                    modifier = Modifier.weight(1f),
                    hasHardShadow = true,
                    onClick = { onNavigateRoute(AppScreenRoute.STUDENT_MY_BILLS) }
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("TAGIHANKU", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.ExtraBold)
                        IxhDualToneSystemIcon(iconType = IxhSystemIconType.TAGIHAN, size = 18.dp)
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = when {
                            isPaid -> "Lunas"
                            billAmt > 0 -> formatRupiah(billAmt)
                            else -> "Rp0"
                        },
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.ExtraBold,
                        color = if (isPaid || billAmt == 0) IxhGreen else IxhInk
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = if (isPaid || billAmt == 0) "Tidak ada tagihan aktif" else "Menunggu pembayaran",
                        style = MaterialTheme.typography.labelSmall,
                        color = if (isPaid || billAmt == 0) IxhTextSecondary else IxhAmber
                    )
                }
            }
        }

        // Admin Governance Strip: Backup & Restore + Audit Log + Offline Sync Queue
        if (role == UserRole.ADMIN.name) {
            Spacer(modifier = Modifier.height(10.dp))
            val govShape = RoundedCornerShape(topStart = 18.dp, topEnd = 10.dp, bottomStart = 10.dp, bottomEnd = 18.dp)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .neubrutalShadow(cornerRadius = 16.dp, offsetX = 2.4.dp, offsetY = 3.4.dp)
                        .clip(govShape)
                        .background(IxhWhite)
                        .border(2.2.dp, IxhInk, govShape)
                        .clickable { onNavigateRoute(AppScreenRoute.BACKUP_RESTORE) }
                        .padding(horizontal = 10.dp, vertical = 10.dp)
                        .testTag("dashboard_backup_restore_button"),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(RoundedCornerShape(9.dp))
                            .background(IxhYellow)
                            .border(1.6.dp, IxhInk, RoundedCornerShape(9.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        IxhDualToneSystemIcon(iconType = IxhSystemIconType.PENGATURAN, size = 18.dp)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "Backup & Restore",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = IxhInk
                        )
                        Text(
                            text = "Cadangkan DB v3",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = IxhTextSecondary,
                            fontSize = 9.5.sp
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .weight(1f)
                        .neubrutalShadow(cornerRadius = 16.dp, offsetX = 2.4.dp, offsetY = 3.4.dp)
                        .clip(govShape)
                        .background(IxhWhite)
                        .border(2.2.dp, IxhInk, govShape)
                        .clickable { onNavigateRoute(AppScreenRoute.ACTIVITY_LOGS) }
                        .padding(horizontal = 10.dp, vertical = 10.dp)
                        .testTag("dashboard_audit_log_button"),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(RoundedCornerShape(9.dp))
                            .background(IxhYellow)
                            .border(1.6.dp, IxhInk, RoundedCornerShape(9.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        IxhDualToneSystemIcon(iconType = IxhSystemIconType.ADMIN, size = 18.dp)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "Audit Log",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = IxhInk
                        )
                        Text(
                            text = "Riwayat Permanen",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = IxhTextSecondary,
                            fontSize = 9.5.sp
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Pinned Announcement or Empty State
        if (pinnedAnnouncement != null) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(IxhBlueBg)
                    .border(1.8.dp, IxhInk, RoundedCornerShape(16.dp))
                    .clickable { onNavigateRoute(AppScreenRoute.ANNOUNCEMENTS_LIST) }
                    .padding(horizontal = 14.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IxhDualToneSystemIcon(
                    iconType = IxhSystemIconType.PENGUMUMAN,
                    size = 24.dp
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "TERSEMAT • PENGUMUMAN KELAS IX-H",
                        fontSize = 9.5.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = IxhBlue
                    )
                    Text(
                        text = pinnedAnnouncement.title,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = IxhInk
                    )
                    Text(
                        text = "Oleh ${pinnedAnnouncement.authorName} • ${pinnedAnnouncement.timeLabel}",
                        style = MaterialTheme.typography.labelSmall,
                        color = IxhTextSecondary
                    )
                }
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                    contentDescription = null,
                    tint = IxhInk
                )
            }
        } else {
            IxhEmptyStateCard(
                title = "Belum ada pengumuman.",
                subtitle = "Pengumuman kelas IX-H yang diterbitkan Admin atau Pengurus akan muncul di sini.",
                expression = BlobExpression.IDLE,
                actionText = if (canManageClass) "Buat Pengumuman Pertama" else null,
                onActionClick = if (canManageClass) {
                    { onNavigateRoute(AppScreenRoute.ANNOUNCEMENT_FORM) }
                } else null,
                actionTestTag = "dashboard_create_announcement_button"
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Tugas Terdekat & Jadwal Hari Ini Preview
        IxhSculptedSectionHeader(
            title = "Agenda & Tugas Terdekat",
            actionLabel = "Semua Tugas",
            onActionClick = { onSelectMainTab(MainNavTab.TUGAS) }
        )
        Spacer(modifier = Modifier.height(6.dp))
        if (closestAssignment != null) {
            IxhCard(
                backgroundColor = IxhYellowLight,
                borderColor = IxhInk,
                hasHardShadow = true,
                onClick = { onSelectMainTab(MainNavTab.TUGAS) }
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
                        IxhDualToneSystemIcon(
                            iconType = IxhSystemIconType.TUGAS,
                            size = 24.dp
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(closestAssignment.subject, style = MaterialTheme.typography.labelSmall)
                            Text(
                                text = closestAssignment.title,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    IxhStatusChip(
                        text = "${closestAssignment.daysRemaining} hari lagi",
                        tone = if (closestAssignment.daysRemaining <= 1) ChipTone.RED else ChipTone.YELLOW
                    )
                }
            }
        } else {
            IxhCard(
                onClick = { onSelectMainTab(MainNavTab.TUGAS) },
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IxhDualToneSystemIcon(iconType = IxhSystemIconType.TUGAS, size = 20.dp)
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Belum ada tugas aktif tercatat.",
                            style = MaterialTheme.typography.bodySmall,
                            color = IxhTextSecondary
                        )
                    }
                    if (canManageClass) {
                        IxhStatusChip(text = "+ Tugas", tone = ChipTone.OUTLINE_YELLOW)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        val firstSchedule = todaySchedules.firstOrNull { !it.isBreak }
        IxhCard(
            onClick = { onSelectMainTab(MainNavTab.JADWAL) },
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 10.dp)
        ) {
            if (firstSchedule != null) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IxhDualToneSystemIcon(iconType = IxhSystemIconType.JADWAL, size = 20.dp)
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "${firstSchedule.startTime}–${firstSchedule.endTime}",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = firstSchedule.subject,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    IxhStatusChip(text = firstSchedule.room, tone = ChipTone.BLUE)
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IxhDualToneSystemIcon(iconType = IxhSystemIconType.JADWAL, size = 20.dp)
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Belum ada jadwal yang ditambahkan.",
                            style = MaterialTheme.typography.bodySmall,
                            color = IxhTextSecondary
                        )
                    }
                    if (canManageClass) {
                        IxhStatusChip(text = "+ Jadwal", tone = ChipTone.OUTLINE_YELLOW)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Quick Actions Grid (3x2 Role-Specific with Bold Rounded Dual-Tone Icons)
        IxhSculptedSectionHeader(
            title = if (isStudent) "Menu Cepat Siswa" else "Aksi Cepat Kelas IX-H"
        )
        Spacer(modifier = Modifier.height(8.dp))

        val tiles = when (role) {
            UserRole.ADMIN.name -> listOf(
                QuickActionTileData(IxhSystemIconType.ABSENSI, "Ambil Absensi", testTag = "quick_take_attendance") {
                    onNavigateRoute(AppScreenRoute.ATTENDANCE_TAKE)
                },
                QuickActionTileData(IxhSystemIconType.KAS, "Catat Kas", testTag = "quick_add_cash") {
                    onNavigateRoute(AppScreenRoute.CASH_ADD_TRANSACTION)
                },
                QuickActionTileData(
                    IxhSystemIconType.TAGIHAN,
                    "Tagihan",
                    badgeText = if (unpaidCount > 0) "$unpaidCount belum" else "",
                    testTag = "quick_bills_manage"
                ) {
                    onNavigateRoute(AppScreenRoute.BILLS_MANAGE)
                },
                QuickActionTileData(IxhSystemIconType.BOT, "Panel Bot", testTag = "quick_bot_panel") {
                    onNavigateRoute(AppScreenRoute.BOT_PANEL)
                },
                QuickActionTileData(
                    IxhSystemIconType.ADMIN,
                    "Kelola Pengguna",
                    badgeText = "${allUsers.size} akun",
                    testTag = "quick_user_management"
                ) {
                    onNavigateRoute(AppScreenRoute.USER_MANAGEMENT_LIST)
                },
                QuickActionTileData(IxhSystemIconType.PENGUMUMAN, "Pengumuman", testTag = "quick_announcements") {
                    onNavigateRoute(AppScreenRoute.ANNOUNCEMENTS_LIST)
                }
            )

            UserRole.PENGURUS.name -> listOf(
                QuickActionTileData(IxhSystemIconType.ABSENSI, "Ambil Absensi") {
                    onNavigateRoute(AppScreenRoute.ATTENDANCE_TAKE)
                },
                QuickActionTileData(IxhSystemIconType.KAS, "Catat Kas") {
                    onNavigateRoute(AppScreenRoute.CASH_ADD_TRANSACTION)
                },
                QuickActionTileData(IxhSystemIconType.TUGAS, "Tambah Tugas") {
                    onNavigateRoute(AppScreenRoute.ASSIGNMENT_FORM)
                },
                QuickActionTileData(
                    IxhSystemIconType.TAGIHAN,
                    "Tagihan",
                    badgeText = if (unpaidCount > 0) "$unpaidCount belum" else ""
                ) {
                    onNavigateRoute(AppScreenRoute.BILLS_MANAGE)
                },
                QuickActionTileData(IxhSystemIconType.BOT, "Panel Bot") {
                    onNavigateRoute(AppScreenRoute.BOT_PANEL)
                },
                QuickActionTileData(IxhSystemIconType.PENGUMUMAN, "Pengumuman") {
                    onNavigateRoute(AppScreenRoute.ANNOUNCEMENTS_LIST)
                }
            )

            else -> listOf(
                QuickActionTileData(IxhSystemIconType.ABSENSI, "Kehadiranku") {
                    onNavigateRoute(AppScreenRoute.STUDENT_MY_ATTENDANCE)
                },
                QuickActionTileData(IxhSystemIconType.TAGIHAN, "Tagihanku") {
                    onNavigateRoute(AppScreenRoute.STUDENT_MY_BILLS)
                },
                QuickActionTileData(IxhSystemIconType.PENGUMUMAN, "Pengumuman") {
                    onNavigateRoute(AppScreenRoute.ANNOUNCEMENTS_LIST)
                },
                QuickActionTileData(IxhSystemIconType.JADWAL, "Jadwal") {
                    onSelectMainTab(MainNavTab.JADWAL)
                },
                QuickActionTileData(IxhSystemIconType.TUGAS, "Tugas Saya") {
                    onSelectMainTab(MainNavTab.TUGAS)
                },
                QuickActionTileData(IxhSystemIconType.PROFIL, "Profil") {
                    onNavigateRoute(AppScreenRoute.PROFILE_MAIN)
                }
            )
        }

        tiles.chunked(3).forEach { rowItems ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                rowItems.forEach { item ->
                    val podShape = RoundedCornerShape(topStart = 14.dp, topEnd = 8.dp, bottomStart = 8.dp, bottomEnd = 14.dp)
                    IxhCard(
                        modifier = Modifier
                            .weight(1f)
                            .let { if (item.testTag != null) it.testTag(item.testTag) else it },
                        hasHardShadow = true,
                        onClick = item.onClick,
                        contentPadding = PaddingValues(vertical = 12.dp, horizontal = 4.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .neubrutalShadow(cornerRadius = 11.dp, offsetX = 1.8.dp, offsetY = 2.4.dp)
                                    .clip(podShape)
                                    .background(IxhYellow)
                                    .border(2.dp, IxhInk, podShape),
                                contentAlignment = Alignment.Center
                            ) {
                                IxhDualToneSystemIcon(
                                    iconType = item.systemIcon,
                                    size = 22.dp
                                )
                            }
                            Spacer(modifier = Modifier.height(7.dp))
                            Text(
                                text = item.label,
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.ExtraBold,
                                color = IxhInk,
                                textAlign = TextAlign.Center,
                                maxLines = 1
                            )
                            if (item.badgeText.isNotBlank()) {
                                Spacer(modifier = Modifier.height(4.dp))
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(IxhInk)
                                        .border(1.2.dp, IxhInk, RoundedCornerShape(8.dp))
                                        .padding(horizontal = 7.dp, vertical = 1.5.dp)
                                ) {
                                    Text(
                                        text = item.badgeText,
                                        fontSize = 8.5.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = IxhYellow
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }

    if (showPersonaSheet) {
        ModalBottomSheet(
            onDismissRequest = { showPersonaSheet = false },
            containerColor = IxhWhite
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp, vertical = 10.dp)
            ) {
                Text(
                    text = "Akun Terdaftar di Database",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "Daftar akun nyata yang tersimpan di Room Database IX-H HUB 1.0",
                    style = MaterialTheme.typography.bodySmall,
                    color = IxhTextSecondary
                )
                Spacer(modifier = Modifier.height(12.dp))
                if (allUsers.isEmpty()) {
                    Text(
                        text = "Belum ada akun terdaftar.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                } else {
                    allUsers.forEach { u ->
                        val isCurrent = activeUser?.username == u.username
                        IxhCard(
                            modifier = Modifier.padding(bottom = 8.dp),
                            backgroundColor = if (isCurrent) IxhYellowSoft else IxhWhite,
                            hasHardShadow = isCurrent,
                            onClick = {
                                onSwitchPersona(u.username)
                                showPersonaSheet = false
                            }
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(u.fullName, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                                    Text("@${u.username} • ${u.subRoleTitle.ifBlank { u.role }}", style = MaterialTheme.typography.bodySmall)
                                }
                                IxhStatusChip(text = u.role, tone = ChipTone.OUTLINE_YELLOW)
                            }
                        }
                    }
                }
                if (role == UserRole.ADMIN.name) {
                    Spacer(modifier = Modifier.height(8.dp))
                    IxhPrimaryButton(
                        text = "Tambah Anggota / Pengurus Baru",
                        onClick = {
                            showPersonaSheet = false
                            onNavigateRoute(AppScreenRoute.USER_FORM_ADMIN)
                        }
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttendanceMainTabScreen(
    activeUser: UserEntity?,
    allUsers: List<UserEntity>,
    unreadNotificationCount: Int,
    onNavigateRoute: (AppScreenRoute) -> Unit,
    onSwitchPersona: (String) -> Unit
) {
    var showInfoSheet by remember { mutableStateOf(false) }
    var selectedStatusFilter by remember { mutableStateOf("SEMUA") }
    val role = activeUser?.role ?: UserRole.ADMIN.name
    val canEdit = role == UserRole.ADMIN.name || role == UserRole.PENGURUS.name

    val hCount = allUsers.count { it.attendanceToday == AttendanceStatus.HADIR.name }
    val iCount = allUsers.count { it.attendanceToday == AttendanceStatus.IZIN.name }
    val sCount = allUsers.count { it.attendanceToday == AttendanceStatus.SAKIT.name }
    val aCount = allUsers.count { it.attendanceToday == AttendanceStatus.ALPHA.name }
    val recordedTotal = hCount + iCount + sCount + aCount
    val total = allUsers.size
    val pct = if (total > 0 && recordedTotal > 0) {
        String.format("%.1f%%", (hCount * 100f) / total).replace('.', ',')
    } else {
        "0%"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(IxhCream)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 14.dp, vertical = 6.dp)
            .testTag("attendance_main_scroll")
    ) {
        IxhTopBrandHeader(
            roleLabel = role,
            unreadNotificationCount = unreadNotificationCount,
            onNotificationsClick = { onNavigateRoute(AppScreenRoute.NOTIFICATIONS_CENTER) },
            onProfileClick = { onNavigateRoute(AppScreenRoute.PROFILE_MAIN) },
            onRoleBadgeClick = {},
            onSearchClick = { onNavigateRoute(AppScreenRoute.GLOBAL_SEARCH) }
        )

        IxhOrganicModuleBanner(
            eyebrowTag = "MATRIKS KEHADIRAN HARIAN",
            title = "Absensi Kelas IX-H",
            subtitle = "Matriks kehadiran harian & pemantauan siswa ($recordedTotal/$total tercatat)",
            iconType = IxhSystemIconType.ABSENSI,
            trailingContent = {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .neubrutalShadow(cornerRadius = 999.dp, offsetX = 1.8.dp, offsetY = 2.4.dp)
                        .clip(CircleShape)
                        .background(IxhWhite)
                        .border(2.2.dp, IxhInk, CircleShape)
                        .clickable { showInfoSheet = true },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Outlined.Info,
                        contentDescription = "Arti Status Kehadiran",
                        tint = IxhInk,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Purpose-Built Attendance Matrix Card (Donut + 4-Segment Distribution Bar)
        IxhCard(hasHardShadow = true) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IxhDonutChart(
                    centerValueText = pct,
                    centerSubText = "Hadir",
                    segments = if (recordedTotal > 0) {
                        listOf(
                            hCount.toFloat() to IxhGreen,
                            iCount.toFloat() to IxhAmber,
                            sCount.toFloat() to IxhBlue,
                            aCount.toFloat() to IxhRed
                        )
                    } else {
                        listOf(1f to IxhSurfaceMuted)
                    }
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    listOf(
                        Triple("Hadir (H)", hCount, IxhGreen),
                        Triple("Izin (I)", iCount, IxhAmber),
                        Triple("Sakit (S)", sCount, IxhBlue),
                        Triple("Alpha (A)", aCount, IxhRed)
                    ).forEach { (label, count, color) ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 3.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(10.dp)
                                        .clip(CircleShape)
                                        .background(color)
                                        .border(1.dp, IxhInk, CircleShape)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(label, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Medium)
                            }
                            Text(
                                text = count.toString(),
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }
                    }
                }
            }

            if (recordedTotal > 0) {
                Spacer(modifier = Modifier.height(10.dp))
                // Multi-segment proportion bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                        .clip(RoundedCornerShape(999.dp))
                        .background(IxhSurfaceMuted)
                        .border(1.2.dp, IxhInk, RoundedCornerShape(999.dp))
                ) {
                    if (hCount > 0) Box(modifier = Modifier.weight(hCount.toFloat()).fillMaxHeight().background(IxhGreen))
                    if (iCount > 0) Box(modifier = Modifier.weight(iCount.toFloat()).fillMaxHeight().background(IxhAmber))
                    if (sCount > 0) Box(modifier = Modifier.weight(sCount.toFloat()).fillMaxHeight().background(IxhBlue))
                    if (aCount > 0) Box(modifier = Modifier.weight(aCount.toFloat()).fillMaxHeight().background(IxhRed))
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        if (recordedTotal == 0) {
            IxhEmptyStateCard(
                title = "Belum ada data absensi.",
                subtitle = if (allUsers.isEmpty()) {
                    "Daftar siswa masih kosong. Tambahkan anggota kelas terlebih dahulu atau ambil absensi."
                } else {
                    "Absensi untuk ${allUsers.size} anggota kelas IX-H belum dicatat hari ini."
                },
                expression = BlobExpression.IDLE,
                actionText = if (canEdit) "Ambil Absensi Sekarang" else null,
                onActionClick = if (canEdit) {
                    { onNavigateRoute(AppScreenRoute.ATTENDANCE_TAKE) }
                } else null,
                actionTestTag = "empty_take_attendance_button"
            )
        } else {
            // Filter Pills for Live Roster Inspection
            val filterOptions = listOf(
                "SEMUA" to "Semua ($total)",
                AttendanceStatus.HADIR.name to "Hadir ($hCount)",
                AttendanceStatus.IZIN.name to "Izin ($iCount)",
                AttendanceStatus.SAKIT.name to "Sakit ($sCount)",
                AttendanceStatus.ALPHA.name to "Alpha ($aCount)"
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                filterOptions.forEach { (key, label) ->
                    val isSel = selectedStatusFilter == key
                    val pillShape = RoundedCornerShape(topStart = 16.dp, topEnd = 10.dp, bottomStart = 10.dp, bottomEnd = 16.dp)
                    Box(
                        modifier = Modifier
                            .then(
                                if (isSel) Modifier.neubrutalShadow(cornerRadius = 14.dp, offsetX = 1.8.dp, offsetY = 2.6.dp)
                                else Modifier
                            )
                            .clip(pillShape)
                            .background(if (isSel) IxhYellow else IxhWhite)
                            .border(if (isSel) 2.2.dp else 1.8.dp, IxhInk, pillShape)
                            .clickable { selectedStatusFilter = key }
                            .padding(horizontal = 13.dp, vertical = 7.dp)
                    ) {
                        Text(
                            text = label,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = IxhInk
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            val filteredUsers = if (selectedStatusFilter == "SEMUA") {
                allUsers
            } else {
                allUsers.filter { it.attendanceToday == selectedStatusFilter }
            }

            IxhCard {
                Text(
                    text = "Daftar Status Kehadiran (${filteredUsers.size} Siswa)",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.ExtraBold
                )
                Spacer(modifier = Modifier.height(6.dp))
                if (filteredUsers.isEmpty()) {
                    Text(
                        text = "Tidak ada siswa pada kategori filter ini.",
                        style = MaterialTheme.typography.bodySmall,
                        color = IxhTextSecondary
                    )
                } else {
                    filteredUsers.forEach { u ->
                        val (tone, lbl) = when (u.attendanceToday) {
                            AttendanceStatus.HADIR.name -> ChipTone.GREEN to "Hadir (H)"
                            AttendanceStatus.SAKIT.name -> ChipTone.BLUE to "Sakit (S)"
                            AttendanceStatus.ALPHA.name -> ChipTone.RED to "Alpha (A)"
                            AttendanceStatus.IZIN.name -> ChipTone.YELLOW to "Izin (I)"
                            else -> ChipTone.NEUTRAL to "Belum Dicatat"
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "#${u.absenNo}",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = IxhTextSecondary,
                                    modifier = Modifier.width(28.dp)
                                )
                                Text(
                                    text = u.fullName,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            IxhStatusChip(text = lbl, tone = tone)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        if (canEdit) {
            IxhPrimaryButton(
                text = "Ambil / Ubah Absensi",
                icon = Icons.Outlined.CheckCircle,
                onClick = { onNavigateRoute(AppScreenRoute.ATTENDANCE_TAKE) },
                testTag = "take_attendance_button"
            )
            Spacer(modifier = Modifier.height(8.dp))
        } else {
            IxhPrimaryButton(
                text = "Lihat Kehadiranku Pribadi",
                icon = Icons.Outlined.BarChart,
                onClick = { onNavigateRoute(AppScreenRoute.STUDENT_MY_ATTENDANCE) }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        IxhSecondaryButton(
            text = "Riwayat & Ekspor",
            icon = Icons.Outlined.History,
            onClick = { onNavigateRoute(AppScreenRoute.ATTENDANCE_HISTORY) }
        )

        Spacer(modifier = Modifier.height(16.dp))
    }

    if (showInfoSheet) {
        ModalBottomSheet(
            onDismissRequest = { showInfoSheet = false },
            containerColor = IxhWhite
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp, vertical = 10.dp)
            ) {
                Text("Arti Status Kehadiran", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(12.dp))
                listOf(
                    Triple("Hadir", ChipTone.GREEN, "Siswa masuk dan tercatat hadir"),
                    Triple("Izin", ChipTone.YELLOW, "Tidak hadir, ada keterangan dari orang tua"),
                    Triple("Sakit", ChipTone.BLUE, "Tidak hadir karena sakit"),
                    Triple("Alpha", ChipTone.RED, "Tidak hadir tanpa keterangan")
                ).forEach { (st, tone, desc) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IxhStatusChip(text = st, tone = tone, modifier = Modifier.width(64.dp))
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(desc, style = MaterialTheme.typography.bodyMedium)
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                IxhPrimaryButton(text = "Mengerti", onClick = { showInfoSheet = false })
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TakeAttendanceScreen(
    users: List<UserEntity>,
    onBack: () -> Unit,
    onSaveAttendance: (List<UserEntity>) -> Unit
) {
    val draftMap = remember(users) {
        mutableStateMapOf<Int, String>().apply {
            users.forEach {
                put(
                    it.id,
                    it.attendanceToday.ifBlank { AttendanceStatus.HADIR.name }
                )
            }
        }
    }
    var showConfirmSheet by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(IxhCream)
            .padding(horizontal = 14.dp, vertical = 8.dp)
    ) {
        IxhSubScreenTopBar(title = "Daftar Absensi", onBack = onBack)

        if (users.isEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            IxhEmptyStateCard(
                title = "Belum ada data siswa.",
                subtitle = "Tambahkan anggota kelas terlebih dahulu melalui menu Kelola Pengguna.",
                expression = BlobExpression.IDLE,
                actionText = "Kembali",
                onActionClick = onBack
            )
            return@Column
        }

        IxhCard(
            backgroundColor = IxhYellowLight,
            borderColor = IxhInk,
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${users.size} anggota • default Hadir",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                IxhStatusChip(text = "Kelas IX-H", tone = ChipTone.OUTLINE_YELLOW)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            users.forEach { student ->
                val currentStatus = draftMap[student.id] ?: AttendanceStatus.HADIR.name
                IxhCard(contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = student.absenNo.toString(),
                                style = MaterialTheme.typography.labelMedium,
                                color = IxhTextSecondary,
                                modifier = Modifier.width(24.dp)
                            )
                            Text(
                                text = student.fullName,
                                style = MaterialTheme.typography.titleSmall
                            )
                        }

                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            AttendanceStatus.entries.forEach { st ->
                                val isSel = currentStatus == st.name
                                val activeColor = when (st) {
                                    AttendanceStatus.HADIR -> IxhGreen
                                    AttendanceStatus.IZIN -> IxhAmber
                                    AttendanceStatus.SAKIT -> IxhBlue
                                    AttendanceStatus.ALPHA -> IxhRed
                                }
                                Box(
                                    modifier = Modifier
                                        .size(30.dp)
                                        .clip(CircleShape)
                                        .background(if (isSel) activeColor else IxhWhite)
                                        .border(
                                            1.5.dp,
                                            if (isSel) IxhInk else IxhBorder,
                                            CircleShape
                                        )
                                        .clickable { draftMap[student.id] = st.name },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = st.code,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isSel) Color.White else IxhTextSecondary
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))
        Text("Ketuk huruf H / I / S / A untuk mengubah status kehadiran", style = MaterialTheme.typography.labelSmall)
        Spacer(modifier = Modifier.height(4.dp))
        IxhPrimaryButton(
            text = "Simpan Absensi (${users.size})",
            icon = Icons.Outlined.CheckCircle,
            onClick = { showConfirmSheet = true },
            testTag = "save_attendance_batch_button"
        )
    }

    if (showConfirmSheet) {
        val h = draftMap.values.count { it == AttendanceStatus.HADIR.name }
        val i = draftMap.values.count { it == AttendanceStatus.IZIN.name }
        val s = draftMap.values.count { it == AttendanceStatus.SAKIT.name }
        val a = draftMap.values.count { it == AttendanceStatus.ALPHA.name }

        ModalBottomSheet(
            onDismissRequest = { showConfirmSheet = false },
            containerColor = IxhWhite
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IxhCircleIcon(
                    icon = Icons.Outlined.WarningAmber,
                    bgColor = IxhYellow,
                    size = 48.dp
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text("Simpan absensi?", style = MaterialTheme.typography.headlineMedium)
                Text("Rekap kehadiran hari ini akan diperbarui", style = MaterialTheme.typography.bodySmall)
                Spacer(modifier = Modifier.height(14.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf(
                        Triple(h, "Hadir", IxhGreenBg to IxhGreen),
                        Triple(i, "Izin", IxhAmberBg to IxhAmber),
                        Triple(s, "Sakit", IxhBlueBg to IxhBlue),
                        Triple(a, "Alpha", IxhRedBg to IxhRed)
                    ).forEach { (cnt, lbl, colors) ->
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(12.dp))
                                .background(colors.first)
                                .padding(vertical = 10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = cnt.toString(),
                                style = MaterialTheme.typography.titleLarge,
                                color = colors.second,
                                fontWeight = FontWeight.ExtraBold
                            )
                            Text(lbl, style = MaterialTheme.typography.labelSmall, color = colors.second)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    IxhSecondaryButton(
                        text = "Batal",
                        onClick = { showConfirmSheet = false },
                        modifier = Modifier.weight(1f)
                    )
                    IxhPrimaryButton(
                        text = "Simpan",
                        onClick = {
                            showConfirmSheet = false
                            val updatedList = users.map { u ->
                                u.copy(attendanceToday = draftMap[u.id] ?: AttendanceStatus.HADIR.name)
                            }
                            onSaveAttendance(updatedList)
                        },
                        modifier = Modifier.weight(1f),
                        testTag = "confirm_save_attendance_button"
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun AttendanceHistoryExportScreen(
    users: List<UserEntity> = emptyList(),
    onBack: () -> Unit,
    onExportClicked: (String) -> Unit
) {
    val recorded = users.filter { it.attendanceToday.isNotBlank() }
    val hCount = recorded.count { it.attendanceToday == AttendanceStatus.HADIR.name }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(IxhCream)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 14.dp, vertical = 8.dp)
    ) {
        IxhSubScreenTopBar(title = "Riwayat Absensi", onBack = onBack)

        if (recorded.isEmpty()) {
            Spacer(modifier = Modifier.height(12.dp))
            IxhEmptyStateCard(
                title = "Belum ada data absensi.",
                subtitle = "Riwayat absensi kelas IX-H akan muncul setelah absensi harian disimpan.",
                expression = BlobExpression.IDLE
            )
        } else {
            val pct = String.format("%.1f%%", (hCount * 100f) / recorded.size).replace('.', ',')
            IxhCard(modifier = Modifier.padding(bottom = 8.dp), hasHardShadow = true) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IxhDualToneSystemIcon(iconType = IxhSystemIconType.ABSENSI, size = 24.dp)
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text("Rekap Absensi Hari Ini", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                            Text("$hCount / ${recorded.size} hadir", style = MaterialTheme.typography.bodySmall)
                        }
                    }
                    IxhStatusChip(text = pct, tone = ChipTone.GREEN)
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            IxhSecondaryButton(
                text = "Ekspor PDF",
                icon = Icons.Outlined.Download,
                onClick = { onExportClicked("PDF Absensi") },
                modifier = Modifier.weight(1f)
            )
            IxhSecondaryButton(
                text = "Ekspor Excel",
                icon = Icons.Outlined.Download,
                onClick = { onExportClicked("Excel Absensi (.xlsx)") },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun StudentMyAttendanceScreen(
    student: UserEntity?,
    onBack: () -> Unit,
    onExportPdf: () -> Unit
) {
    val present = student?.presentDaysCount ?: 0
    val total = student?.totalEffectiveDays ?: 0
    val pctText = if (total > 0) {
        String.format("%.1f%%", (present * 100f) / total).replace('.', ',')
    } else {
        "0%"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(IxhCream)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 14.dp, vertical = 8.dp)
    ) {
        IxhSubScreenTopBar(
            title = "Kehadiranku",
            onBack = onBack,
            rightContent = {
                IxhStatusChip(text = "Semester Ganjil", tone = ChipTone.BLUE)
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (total == 0) {
            IxhEmptyStateCard(
                title = "Belum ada data absensi.",
                subtitle = "Rekap kehadiran pribadimu akan tampil setelah Pengurus atau Admin mencatat absensi kelas.",
                expression = BlobExpression.IDLE
            )
        } else {
            IxhCard(hasHardShadow = true) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    IxhDonutChart(
                        centerValueText = pctText,
                        centerSubText = "kehadiran",
                        segments = listOf(
                            present.toFloat().coerceAtLeast(1f) to IxhGreen,
                            (total - present).toFloat().coerceAtLeast(0f) to IxhAmber
                        ),
                        size = 128.dp
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "$present dari $total hari efektif hadir",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            IxhPrimaryButton(
                text = "Unduh Rekap (PDF)",
                icon = Icons.Outlined.Download,
                onClick = onExportPdf
            )
        }
    }
}
