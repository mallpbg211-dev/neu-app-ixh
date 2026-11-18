package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
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
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.NotificationsActive
import androidx.compose.material.icons.outlined.PictureAsPdf
import androidx.compose.material.icons.outlined.WifiOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.AssignmentEntity
import com.example.data.local.ScheduleEntity
import com.example.data.local.UserEntity
import com.example.data.local.UserRole
import com.example.ui.components.BlobMascotState
import com.example.ui.components.ChipTone
import com.example.ui.components.IxhAlertBanner
import com.example.ui.components.IxhCard
import com.example.ui.components.IxhCircleIcon
import com.example.ui.components.IxhEmptyStateCard
import com.example.ui.components.IxhNeubrutalToggle
import com.example.ui.components.IxhPrimaryButton
import com.example.ui.components.IxhSecondaryButton
import com.example.ui.components.IxhSegmentedTabs
import com.example.ui.components.IxhStatusChip
import com.example.ui.components.IxhSubScreenTopBar
import com.example.ui.components.IxhTopBrandHeader
import com.example.ui.theme.IxhAmber
import com.example.ui.theme.IxhAmberBg
import com.example.ui.theme.IxhBlue
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

// =========================================================================
// REDESIGN V2: JADWAL PELAJARAN (VERTICAL TIME-RAIL) & KALENDER AKADEMIK
// =========================================================================

@Composable
fun ScheduleMainTabScreen(
    activeUser: UserEntity,
    schedules: List<ScheduleEntity>,
    selectedDayCode: String,
    unreadNotificationCount: Int,
    onSelectDay: (String) -> Unit,
    onOpenScheduleDetail: (ScheduleEntity) -> Unit,
    onNavigate: (AppScreenRoute) -> Unit,
    onOpenRoleSwitcher: () -> Unit
) {
    val days = listOf(
        "Sen" to "Senin",
        "Sel" to "Selasa",
        "Rab" to "Rabu",
        "Kam" to "Kamis",
        "Jum" to "Jumat",
        "Sab" to "Sabtu"
    )
    var showOfflinePreview by remember { mutableStateOf(false) }
    val daySchedules = schedules.filter { it.dayCode == selectedDayCode }
    val selectedDayFullName = days.firstOrNull { it.first == selectedDayCode }?.second ?: selectedDayCode

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(IxhCream)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        IxhTopBrandHeader(
            roleLabel = activeUser.role,
            unreadNotificationCount = unreadNotificationCount,
            onNotificationsClick = { onNavigate(AppScreenRoute.NOTIFICATIONS_CENTER) },
            onProfileClick = { onNavigate(AppScreenRoute.PROFILE_MAIN) },
            onRoleBadgeClick = onOpenRoleSwitcher,
            onSearchClick = { onNavigate(AppScreenRoute.GLOBAL_SEARCH) }
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Jadwal Pelajaran",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = IxhInk
                )
                Text(
                    text = "Hari $selectedDayFullName • ${daySchedules.count { !it.isBreak }} Mata Pelajaran",
                    style = MaterialTheme.typography.bodySmall,
                    color = IxhTextSecondary
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                IxhStatusChip(
                    text = "Kalender",
                    tone = ChipTone.OUTLINE_YELLOW,
                    modifier = Modifier.clickable {
                        onNavigate(AppScreenRoute.ACADEMIC_CALENDAR)
                    }
                )
                IxhStatusChip(
                    text = if (showOfflinePreview) "Online" else "Offline UI",
                    tone = ChipTone.NEUTRAL,
                    modifier = Modifier.clickable { showOfflinePreview = !showOfflinePreview }
                )
            }
        }

        if (showOfflinePreview) {
            IxhAlertBanner(
                title = "Mode Offline-First Aktif — Jadwal Lokal Room",
                text = "Jadwal pelajaran tetap dapat diakses tanpa koneksi internet karena tersimpan di database lokal perangkat.",
                tone = ChipTone.YELLOW,
                icon = Icons.Outlined.WifiOff
            )
        }

        // 6-Day Selector Pill Bar (Senin - Sabtu) with active lesson badge count
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            days.forEach { (code, _) ->
                val isSelected = code == selectedDayCode
                val lessonCountForDay = schedules.count { it.dayCode == code && !it.isBreak }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(14.dp))
                        .background(if (isSelected) IxhYellow else IxhWhite)
                        .border(
                            width = if (isSelected) 2.dp else 1.5.dp,
                            color = if (isSelected) IxhInk else IxhBorder,
                            shape = RoundedCornerShape(14.dp)
                        )
                        .clickable { onSelectDay(code) }
                        .padding(vertical = 8.dp)
                        .testTag("schedule_day_$code"),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = code,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = IxhInk
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "$lessonCountForDay mapel",
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isSelected) IxhInk else IxhTextSecondary
                    )
                }
            }
        }

        // Night Reminder Banner Card
        IxhCard(
            backgroundColor = IxhYellowLight,
            borderColor = IxhInk,
            onClick = { onNavigate(AppScreenRoute.SCHEDULE_NIGHT_REMINDER) }
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                    IxhCircleIcon(
                        icon = Icons.Outlined.NotificationsActive,
                        bgColor = IxhYellow,
                        hasBorder = true,
                        size = 34.dp
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "Pengingat Malam Hari • 20.00 WIB",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = IxhInk
                        )
                        Text(
                            text = "Siapkan buku & perlengkapan esok hari",
                            style = MaterialTheme.typography.bodySmall,
                            color = IxhTextSecondary
                        )
                    }
                }
                Text(
                    text = "Atur →",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = IxhInk
                )
            }
        }

        val isSiswa = activeUser.role == UserRole.SISWA.name
        if (daySchedules.isEmpty()) {
            IxhEmptyStateCard(
                title = "Belum ada jadwal yang ditambahkan.",
                subtitle = "Jadwal pelajaran hari $selectedDayFullName belum diisi. ${if (!isSiswa) "Ketuk tombol di bawah untuk menambahkan jadwal pelajaran." else "Pengurus kelas belum menambahkan jadwal hari ini."}",
                mascotState = BlobMascotState.IDLE,
                actionText = if (!isSiswa) "+ Tambah Jadwal" else null,
                onActionClick = if (!isSiswa) ({ onNavigate(AppScreenRoute.SCHEDULE_FORM) }) else null,
                testTag = "schedule_empty_state"
            )
        } else {
            // Dedicated Vertical Time-Rail Layout for Schedule Screen
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                daySchedules.forEachIndexed { idx, item ->
                    val isOngoing = idx == 0 && !item.isBreak
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min),
                        verticalAlignment = Alignment.Top
                    ) {
                        // Left Time & Rail Column
                        Column(
                            modifier = Modifier
                                .width(64.dp)
                                .fillMaxHeight()
                                .padding(top = 4.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = item.startTime,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = IxhInk
                            )
                            Text(
                                text = item.endTime,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = IxhTextSecondary
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Box(
                                modifier = Modifier
                                    .size(12.dp)
                                    .clip(CircleShape)
                                    .background(
                                        when {
                                            item.isBreak -> IxhSurfaceMuted
                                            isOngoing -> IxhYellow
                                            else -> IxhBlue
                                        }
                                    )
                                    .border(2.dp, IxhInk, CircleShape)
                            )
                            if (idx < daySchedules.lastIndex) {
                                Box(
                                    modifier = Modifier
                                        .width(2.dp)
                                        .weight(1f)
                                        .background(IxhInk.copy(alpha = 0.25f))
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        // Right Schedule Content Card
                        Box(modifier = Modifier.weight(1f)) {
                            if (item.isBreak) {
                                IxhCard(
                                    backgroundColor = IxhSurfaceMuted,
                                    contentPadding = PaddingValues(vertical = 10.dp, horizontal = 12.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "ISTIRAHAT • ${item.subject}",
                                            style = MaterialTheme.typography.labelMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = IxhTextSecondary
                                        )
                                        IxhStatusChip(text = "${item.startTime}–${item.endTime}", tone = ChipTone.NEUTRAL)
                                    }
                                }
                            } else {
                                IxhCard(
                                    backgroundColor = if (isOngoing) IxhYellowLight else IxhWhite,
                                    borderColor = if (isOngoing) IxhInk else null,
                                    hasHardShadow = isOngoing,
                                    leftStripeColor = if (isOngoing) IxhYellow else IxhBlue,
                                    onClick = { onOpenScheduleDetail(item) }
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        IxhStatusChip(
                                            text = if (isOngoing) "Jam Pertama" else item.room,
                                            tone = if (isOngoing) ChipTone.OUTLINE_YELLOW else ChipTone.NEUTRAL
                                        )
                                        Text(
                                            text = "Detail →",
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = IxhInk
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = item.subject,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = IxhInk
                                    )
                                    Text(
                                        text = "${item.teacher} • ${item.room}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = IxhTextSecondary
                                    )
                                    if (item.notes.isNotBlank()) {
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = "Catatan: ${item.notes}",
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.SemiBold,
                                            color = IxhInk
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (!isSiswa) {
                IxhSecondaryButton(
                    text = "+ Tambah Jadwal Pelajaran",
                    onClick = { onNavigate(AppScreenRoute.SCHEDULE_FORM) },
                    testTag = "schedule_add_button"
                )
            }
        }

        Spacer(modifier = Modifier.height(80.dp))
    }
}

@Composable
fun ScheduleFormScreen(
    defaultDayCode: String,
    onSave: (dayCode: String, startTime: String, endTime: String, subject: String, teacher: String, room: String, notes: String, isBreak: Boolean) -> Unit,
    onBack: () -> Unit
) {
    val days = listOf("Sen", "Sel", "Rab", "Kam", "Jum", "Sab")
    var selectedDay by remember { mutableStateOf(defaultDayCode.ifBlank { "Sen" }) }
    var startTime by remember { mutableStateOf("07.00") }
    var endTime by remember { mutableStateOf("08.20") }
    var subject by remember { mutableStateOf("") }
    var teacher by remember { mutableStateOf("") }
    var room by remember { mutableStateOf("Ruang IX-H") }
    var notes by remember { mutableStateOf("") }
    var isBreak by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        IxhSubScreenTopBar(title = "Tambah Jadwal Pelajaran", onBack = onBack)

        Text("PILIH HARI SEKOLAH", style = MaterialTheme.typography.labelSmall, color = IxhTextSecondary)
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            days.forEach { d ->
                val sel = selectedDay == d
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (sel) IxhYellow else IxhWhite)
                        .border(1.5.dp, if (sel) IxhInk else IxhBorder, RoundedCornerShape(10.dp))
                        .clickable { selectedDay = d }
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(d, fontWeight = FontWeight.ExtraBold, fontSize = 11.sp, color = IxhInk)
                }
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = startTime,
                onValueChange = { startTime = it },
                label = { Text("Jam Mulai") },
                modifier = Modifier.weight(1f),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(focusedContainerColor = IxhWhite, unfocusedContainerColor = IxhWhite)
            )
            OutlinedTextField(
                value = endTime,
                onValueChange = { endTime = it },
                label = { Text("Jam Selesai") },
                modifier = Modifier.weight(1f),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(focusedContainerColor = IxhWhite, unfocusedContainerColor = IxhWhite)
            )
        }

        OutlinedTextField(
            value = subject,
            onValueChange = { subject = it },
            label = { Text("Mata Pelajaran") },
            placeholder = { Text("Contoh: Matematika") },
            modifier = Modifier.fillMaxWidth().testTag("schedule_subject_input"),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(focusedContainerColor = IxhWhite, unfocusedContainerColor = IxhWhite)
        )

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = teacher,
                onValueChange = { teacher = it },
                label = { Text("Nama Guru") },
                modifier = Modifier.weight(1f),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(focusedContainerColor = IxhWhite, unfocusedContainerColor = IxhWhite)
            )
            OutlinedTextField(
                value = room,
                onValueChange = { room = it },
                label = { Text("Ruangan") },
                modifier = Modifier.weight(1f),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(focusedContainerColor = IxhWhite, unfocusedContainerColor = IxhWhite)
            )
        }

        OutlinedTextField(
            value = notes,
            onValueChange = { notes = it },
            label = { Text("Catatan Perlengkapan (Opsional)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(focusedContainerColor = IxhWhite, unfocusedContainerColor = IxhWhite)
        )

        IxhCard(backgroundColor = IxhWhite) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Tandai sebagai Jam Istirahat", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = IxhInk)
                IxhNeubrutalToggle(checked = isBreak, onCheckedChange = { isBreak = it })
            }
        }

        IxhPrimaryButton(
            text = "Simpan Jadwal",
            onClick = {
                val finalSubject = subject.ifBlank { if (isBreak) "Istirahat" else "Mata Pelajaran" }
                onSave(selectedDay, startTime, endTime, finalSubject, teacher.ifBlank { "Guru Mapel" }, room.ifBlank { "Ruang IX-H" }, notes, isBreak)
            },
            testTag = "save_schedule_button"
        )
    }
}

@Composable
fun ScheduleDetailScreen(
    schedule: ScheduleEntity?,
    onOpenNightReminder: () -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        IxhSubScreenTopBar(title = "Detail Pelajaran", onBack = onBack)

        if (schedule == null) {
            IxhEmptyStateCard(
                title = "Jadwal tidak ditemukan.",
                subtitle = "Pilih jadwal pelajaran dari daftar untuk melihat rincian guru, ruangan, dan catatan perlengkapan.",
                mascotState = BlobMascotState.IDLE
            )
        } else {
            var reminderOn by remember(schedule.id) { mutableStateOf(schedule.reminderEnabled) }

            IxhCard(
                backgroundColor = IxhYellow,
                hasHardShadow = true,
                contentPadding = PaddingValues(16.dp)
            ) {
                IxhStatusChip(text = "Hari ${schedule.dayCode} • ${schedule.startTime}–${schedule.endTime}", tone = ChipTone.NEUTRAL)
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = schedule.subject,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = IxhInk
                )
                Text(
                    text = "${schedule.teacher} • ${schedule.room}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = IxhInk
                )
            }

            IxhCard(backgroundColor = IxhWhite) {
                Text(
                    text = "CATATAN & PERLENGKAPAN",
                    style = MaterialTheme.typography.labelSmall,
                    color = IxhTextSecondary
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = schedule.notes.ifBlank { "Siapkan buku paket dan buku catatan pelajaran ${schedule.subject}." },
                    style = MaterialTheme.typography.bodyMedium,
                    color = IxhInk
                )
            }

            IxhCard(backgroundColor = IxhWhite) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Ingatkan Malam Sebelumnya",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = IxhInk
                        )
                        Text(
                            text = "Notifikasi pukul ${schedule.reminderTime}",
                            style = MaterialTheme.typography.bodySmall,
                            color = IxhTextSecondary
                        )
                    }
                    IxhNeubrutalToggle(
                        checked = reminderOn,
                        onCheckedChange = { reminderOn = it }
                    )
                }
            }

            IxhSecondaryButton(
                text = "Atur Jam Pengingat Malam (${schedule.reminderTime})",
                onClick = onOpenNightReminder
            )
        }
    }
}

@Composable
fun ScheduleNightReminderScreen(
    defaultTimeWib: String,
    onSave: (Boolean, String) -> Unit,
    onBack: () -> Unit
) {
    var enabled by remember { mutableStateOf(true) }
    var selectedTime by remember { mutableStateOf(defaultTimeWib) }
    val timeOptions = listOf("19.00 WIB", "19.30 WIB", "20.00 WIB", "20.30 WIB")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        IxhSubScreenTopBar(title = "Pengingat Persiapan", onBack = onBack)

        IxhCard(backgroundColor = IxhWhite) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Aktifkan Pengingat Malam",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = IxhInk
                    )
                    Text(
                        text = "Kirim pengingat jadwal esok hari setiap malam (Minggu–Jumat)",
                        style = MaterialTheme.typography.bodySmall,
                        color = IxhTextSecondary
                    )
                }
                IxhNeubrutalToggle(checked = enabled, onCheckedChange = { enabled = it })
            }
        }

        Text(
            text = "PILIH JAM PENGINGAT",
            style = MaterialTheme.typography.labelSmall,
            color = IxhTextSecondary
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            timeOptions.forEach { t ->
                val sel = selectedTime == t
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (sel) IxhYellow else IxhWhite)
                        .border(1.5.dp, if (sel) IxhInk else IxhBorder, RoundedCornerShape(10.dp))
                        .clickable { selectedTime = t }
                        .padding(vertical = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = t.removeSuffix(" WIB"),
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = IxhInk
                    )
                }
            }
        }

        Text(
            text = "CONTOH NOTIFIKASI MALAM INI",
            style = MaterialTheme.typography.labelSmall,
            color = IxhTextSecondary
        )
        IxhCard(
            backgroundColor = IxhYellowLight,
            borderColor = IxhInk
        ) {
            Text(
                text = "IX-H Hub • $selectedTime",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = IxhInk
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Pengingat otomatis: Periksa daftar mata pelajaran dan perlengkapan kelas IX-H untuk esok hari.",
                style = MaterialTheme.typography.bodySmall,
                color = IxhInk
            )
        }

        IxhPrimaryButton(
            text = "Simpan Pengingat",
            onClick = { onSave(enabled, selectedTime) }
        )
    }
}

@Composable
fun AcademicCalendarScreen(
    schedules: List<ScheduleEntity> = emptyList(),
    assignments: List<AssignmentEntity> = emptyList(),
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        IxhSubScreenTopBar(title = "Kalender Akademik", onBack = onBack)

        IxhCard(backgroundColor = IxhWhite) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Kalender Kelas IX-H",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = IxhInk
                )
                IxhStatusChip(text = "${schedules.size} Jadwal • ${assignments.size} Tugas", tone = ChipTone.YELLOW)
            }
            Spacer(modifier = Modifier.height(10.dp))
            val dayHeaders = listOf("Sen", "Sel", "Rab", "Kam", "Jum", "Sab", "Min")
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                dayHeaders.forEach { d ->
                    Text(
                        text = d,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (d == "Min") IxhRed else IxhTextSecondary
                    )
                }
            }
            Spacer(modifier = Modifier.height(6.dp))
            val rows = listOf(
                listOf(1, 2, 3, 4, 5, 6, 7),
                listOf(8, 9, 10, 11, 12, 13, 14),
                listOf(15, 16, 17, 18, 19, 20, 21),
                listOf(22, 23, 24, 25, 26, 27, 28)
            )
            rows.forEach { week ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 3.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    week.forEach { dayNum ->
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(if (dayNum == 1) IxhYellowLight else Color.Transparent)
                                .then(
                                    if (dayNum == 1) Modifier.border(1.5.dp, IxhInk, CircleShape)
                                    else Modifier
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = dayNum.toString(),
                                fontSize = 11.sp,
                                fontWeight = if (dayNum == 1) FontWeight.ExtraBold else FontWeight.Medium,
                                color = IxhInk
                            )
                        }
                    }
                }
            }
        }

        Text(
            text = "Agenda & Tenggat Tugas Aktif",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = IxhInk
        )

        if (assignments.isEmpty() && schedules.isEmpty()) {
            IxhEmptyStateCard(
                title = "Belum ada kegiatan atau tenggat tugas.",
                subtitle = "Tambahkan jadwal pelajaran atau tugas sekolah agar otomatis tampil di kalender akademik kelas IX-H.",
                mascotState = BlobMascotState.IDLE
            )
        } else {
            assignments.forEach { task ->
                IxhCard(backgroundColor = IxhWhite) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "${task.subject} — ${task.title}",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = IxhInk
                            )
                            Text(
                                text = "Tenggat: ${task.deadlineLabel}",
                                style = MaterialTheme.typography.bodySmall,
                                color = IxhTextSecondary
                            )
                        }
                        IxhStatusChip(
                            text = if (task.isCompleted) "Selesai" else "${task.daysRemaining}h lagi",
                            tone = if (task.isCompleted) ChipTone.GREEN else ChipTone.YELLOW
                        )
                    }
                }
            }
        }
    }
}

// =========================================================================
// REDESIGN V2: TUGAS SEKOLAH + PROGRESS TRACKER + INFO SHEET ARTI WARNA
// =========================================================================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksMainTabScreen(
    activeUser: UserEntity,
    assignments: List<AssignmentEntity>,
    unreadNotificationCount: Int,
    onOpenAssignmentDetail: (AssignmentEntity) -> Unit,
    onToggleComplete: (AssignmentEntity) -> Unit,
    onNavigate: (AppScreenRoute) -> Unit,
    onOpenRoleSwitcher: () -> Unit
) {
    val isSiswa = activeUser.role == UserRole.SISWA.name
    var filterTab by remember { mutableIntStateOf(0) } // 0=Semua, 1=Belum Selesai, 2=Selesai
    var showColorInfoSheet by remember { mutableStateOf(false) }

    val totalTasks = assignments.size
    val completedTasks = assignments.count { it.isCompleted }
    val upcomingTasks = (totalTasks - completedTasks).coerceAtLeast(0)
    val urgentTasks = assignments.count { !it.isCompleted && it.daysRemaining <= 1 }
    val progressPct = if (totalTasks > 0) (completedTasks * 100) / totalTasks else 0

    val filtered = assignments.filter {
        when (filterTab) {
            1 -> !it.isCompleted
            2 -> it.isCompleted
            else -> true
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(IxhCream)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            IxhTopBrandHeader(
                roleLabel = activeUser.role,
                unreadNotificationCount = unreadNotificationCount,
                onNotificationsClick = { onNavigate(AppScreenRoute.NOTIFICATIONS_CENTER) },
                onProfileClick = { onNavigate(AppScreenRoute.PROFILE_MAIN) },
                onRoleBadgeClick = onOpenRoleSwitcher,
                onSearchClick = { onNavigate(AppScreenRoute.GLOBAL_SEARCH) }
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Tugas Kelas IX-H",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = IxhInk
                    )
                    Text(
                        text = "Progres penyelesaian $progressPct% • $urgentTasks mendesak",
                        style = MaterialTheme.typography.bodySmall,
                        color = IxhTextSecondary
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(14.dp))
                        .background(IxhWhite)
                        .border(1.5.dp, IxhInk, RoundedCornerShape(14.dp))
                        .clickable { showColorInfoSheet = true }
                        .padding(horizontal = 8.dp, vertical = 5.dp)
                        .testTag("task_color_info_button")
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Info,
                        contentDescription = "Arti Warna Tugas",
                        tint = IxhInk,
                        modifier = Modifier.size(15.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Arti Warna",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = IxhInk
                    )
                }
            }

            // Summary KPI Row + Completion Bar
            IxhCard(
                backgroundColor = IxhWhite,
                hasHardShadow = true,
                contentPadding = PaddingValues(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TaskKpiMiniCard("Total", totalTasks.toString(), IxhYellowLight, Modifier.weight(1f))
                    TaskKpiMiniCard("Selesai", completedTasks.toString(), IxhGreenBg, Modifier.weight(1f))
                    TaskKpiMiniCard("Mendatang", upcomingTasks.toString(), IxhAmberBg, Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                        .clip(RoundedCornerShape(999.dp))
                        .background(IxhSurfaceMuted)
                        .border(1.dp, IxhInk, RoundedCornerShape(999.dp))
                ) {
                    if (completedTasks > 0 && totalTasks > 0) {
                        Box(
                            modifier = Modifier
                                .weight(completedTasks.toFloat())
                                .height(10.dp)
                                .background(IxhGreen)
                        )
                    }
                    if (upcomingTasks > 0 && totalTasks > 0) {
                        Box(
                            modifier = Modifier
                                .weight(upcomingTasks.toFloat())
                                .height(10.dp)
                                .background(IxhYellow)
                        )
                    }
                }
            }

            IxhSegmentedTabs(
                tabs = listOf("Semua ($totalTasks)", "Belum ($upcomingTasks)", "Selesai ($completedTasks)"),
                selectedIndex = filterTab,
                onTabSelected = { filterTab = it }
            )

            if (filtered.isEmpty()) {
                IxhEmptyStateCard(
                    title = "Belum ada tugas.",
                    subtitle = "Belum ada tugas kelas IX-H pada kategori ini. ${if (!isSiswa) "Ketuk tombol + untuk menambahkan tugas baru." else "Semua tugas telah selesai atau belum ditambahkan."}",
                    mascotState = BlobMascotState.SMILE,
                    actionText = if (!isSiswa) "+ Tambah Tugas" else null,
                    onActionClick = if (!isSiswa) ({ onNavigate(AppScreenRoute.ASSIGNMENT_FORM) }) else null,
                    testTag = "tasks_empty_state"
                )
            } else {
                filtered.forEach { task ->
                    val stripeColor = when {
                        task.isCompleted -> IxhGreen
                        task.daysRemaining <= 1 -> IxhRed
                        task.daysRemaining in 2..3 -> IxhAmber
                        else -> IxhBlue
                    }
                    val (badgeLabel, badgeTone) = when {
                        task.isCompleted -> "Selesai ✓" to ChipTone.GREEN
                        task.daysRemaining <= 1 -> "Mendesak (≤1 hari)" to ChipTone.RED
                        task.daysRemaining in 2..3 -> "${task.daysRemaining} hari lagi" to ChipTone.YELLOW
                        else -> "${task.daysRemaining} hari lagi" to ChipTone.BLUE
                    }

                    IxhCard(
                        backgroundColor = IxhWhite,
                        leftStripeColor = stripeColor,
                        onClick = { onOpenAssignmentDetail(task) }
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${task.subject} • ${task.teacher}",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = IxhTextSecondary
                            )
                            IxhStatusChip(text = badgeLabel, tone = badgeTone)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = task.title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = IxhInk,
                            textDecoration = if (task.isCompleted) TextDecoration.LineThrough else TextDecoration.None
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Tenggat: ${task.deadlineLabel}",
                                style = MaterialTheme.typography.bodySmall,
                                color = IxhTextSecondary
                            )
                            Text(
                                text = if (task.isCompleted) "Buka Kembali" else "✓ Tandai Selesai",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = if (task.isCompleted) IxhTextSecondary else IxhGreenText,
                                modifier = Modifier.clickable { onToggleComplete(task) }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(80.dp))
        }

        if (!isSiswa) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 18.dp, bottom = 18.dp)
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(IxhYellow)
                    .border(2.dp, IxhInk, CircleShape)
                    .clickable { onNavigate(AppScreenRoute.ASSIGNMENT_FORM) }
                    .testTag("tasks_fab_add"),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Add,
                    contentDescription = "Tambah Tugas",
                    tint = IxhInk,
                    modifier = Modifier.size(26.dp)
                )
            }
        }
    }

    if (showColorInfoSheet) {
        ModalBottomSheet(
            onDismissRequest = { showColorInfoSheet = false },
            containerColor = IxhWhite
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Arti Warna Garis Tugas",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = IxhInk
                )
                ColorLegendRow(IxhRed, "Merah (Mendesak)", "Tenggat ≤ 1 hari (besok atau hari ini)")
                ColorLegendRow(IxhAmber, "Kuning (Dekat)", "Tenggat 2–3 hari lagi")
                ColorLegendRow(IxhBlue, "Biru (Aman)", "Tenggat ≥ 4 hari lagi")
                ColorLegendRow(IxhGreen, "Hijau (Selesai)", "Tugas sudah ditandai selesai")
                Spacer(modifier = Modifier.height(8.dp))
                IxhPrimaryButton(text = "Mengerti", onClick = { showColorInfoSheet = false })
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun TaskKpiMiniCard(label: String, value: String, bg: Color, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(bg)
            .border(1.5.dp, IxhInk, RoundedCornerShape(12.dp))
            .padding(vertical = 8.dp, horizontal = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.ExtraBold,
            color = IxhInk
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = IxhTextSecondary
        )
    }
}

@Composable
private fun ColorLegendRow(color: Color, title: String, desc: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .width(8.dp)
                .height(32.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(color)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = IxhInk
            )
            Text(
                text = desc,
                style = MaterialTheme.typography.bodySmall,
                color = IxhTextSecondary
            )
        }
    }
}

@Composable
fun TaskDetailScreen(
    assignment: AssignmentEntity?,
    onToggleComplete: (AssignmentEntity) -> Unit,
    onDownloadToast: (String) -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        IxhSubScreenTopBar(title = "Detail Tugas", onBack = onBack)

        if (assignment == null) {
            IxhEmptyStateCard(
                title = "Tugas tidak ditemukan.",
                subtitle = "Pilih salah satu tugas pada daftar untuk melihat deskripsi lengkap dan status pengumpulan.",
                mascotState = BlobMascotState.IDLE
            )
        } else {
            IxhCard(
                backgroundColor = if (assignment.isCompleted) IxhGreenBg else IxhYellowLight,
                borderColor = IxhInk,
                hasHardShadow = true
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IxhStatusChip(text = assignment.subject, tone = ChipTone.OUTLINE_YELLOW)
                    IxhStatusChip(
                        text = if (assignment.isCompleted) "Selesai ✓" else "Tenggat ${assignment.daysRemaining} hari lagi",
                        tone = if (assignment.isCompleted) ChipTone.GREEN else if (assignment.daysRemaining <= 1) ChipTone.RED else ChipTone.YELLOW
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = assignment.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = IxhInk
                )
                Text(
                    text = "Guru: ${assignment.teacher} • Batas: ${assignment.deadlineLabel}",
                    style = MaterialTheme.typography.bodySmall,
                    color = IxhTextSecondary
                )
            }

            IxhCard(backgroundColor = IxhWhite) {
                Text(
                    text = "DESKRIPSI & INSTRUKSI TUGAS",
                    style = MaterialTheme.typography.labelSmall,
                    color = IxhTextSecondary
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = assignment.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = IxhInk
                )
            }

            if (assignment.attachmentName.isNotBlank()) {
                IxhCard(
                    backgroundColor = IxhWhite,
                    onClick = { onDownloadToast("Mengunduh ${assignment.attachmentName} (${assignment.attachmentSize})...") }
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IxhCircleIcon(
                                icon = Icons.Outlined.PictureAsPdf,
                                bgColor = IxhRedBg,
                                iconTint = IxhRed,
                                size = 36.dp
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = assignment.attachmentName,
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = IxhInk
                                )
                                Text(
                                    text = "Lampiran PDF • ${assignment.attachmentSize}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = IxhTextSecondary
                                )
                            }
                        }
                        Icon(
                            imageVector = Icons.Outlined.Download,
                            contentDescription = "Unduh PDF",
                            tint = IxhInk
                        )
                    }
                }
            }

            IxhPrimaryButton(
                text = if (assignment.isCompleted) "Buka Kembali Status Tugas" else "✓ Tandai Selesai",
                onClick = { onToggleComplete(assignment) }
            )
        }
    }
}

@Composable
fun TaskFormScreen(
    onSave: (title: String, subject: String, teacher: String, deadlineLabel: String, daysRemaining: Int, description: String, reminderEnabled: Boolean) -> Unit,
    onBack: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var subject by remember { mutableStateOf("") }
    var teacher by remember { mutableStateOf("") }
    var deadlineLabel by remember { mutableStateOf("Besok • 23:59 WIB") }
    var daysRemaining by remember { mutableIntStateOf(2) }
    var description by remember { mutableStateOf("") }
    var reminderOn by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        IxhSubScreenTopBar(title = "Tambah Tugas Baru", onBack = onBack)

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Judul Tugas") },
            placeholder = { Text("Contoh: Latihan Bab 4 — Fungsi Kuadrat") },
            modifier = Modifier.fillMaxWidth().testTag("task_title_input"),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = IxhWhite,
                unfocusedContainerColor = IxhWhite
            )
        )

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = subject,
                onValueChange = { subject = it },
                label = { Text("Mata Pelajaran") },
                placeholder = { Text("Matematika") },
                modifier = Modifier.weight(1f),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = IxhWhite,
                    unfocusedContainerColor = IxhWhite
                )
            )
            OutlinedTextField(
                value = teacher,
                onValueChange = { teacher = it },
                label = { Text("Guru") },
                placeholder = { Text("Nama Guru") },
                modifier = Modifier.weight(1f),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = IxhWhite,
                    unfocusedContainerColor = IxhWhite
                )
            )
        }

        OutlinedTextField(
            value = deadlineLabel,
            onValueChange = { deadlineLabel = it },
            label = { Text("Tenggat Waktu") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = IxhWhite,
                unfocusedContainerColor = IxhWhite
            )
        )

        Text(
            text = "TINGKAT MENDESAK (GARIS WARNA)",
            style = MaterialTheme.typography.labelSmall,
            color = IxhTextSecondary
        )
        IxhSegmentedTabs(
            tabs = listOf("≤1 Hari (Merah)", "2-3 Hari (Kuning)", "≥4 Hari (Biru)"),
            selectedIndex = when {
                daysRemaining <= 1 -> 0
                daysRemaining in 2..3 -> 1
                else -> 2
            },
            onTabSelected = { idx ->
                daysRemaining = when (idx) {
                    0 -> 1
                    1 -> 2
                    else -> 5
                }
            }
        )

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Deskripsi & Instruksi") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = IxhWhite,
                unfocusedContainerColor = IxhWhite
            )
        )

        IxhCard(backgroundColor = IxhWhite) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Kirim Pengingat H-1",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = IxhInk
                    )
                    Text(
                        text = "Ingatkan semua siswa sehari sebelum tenggat",
                        style = MaterialTheme.typography.bodySmall,
                        color = IxhTextSecondary
                    )
                }
                IxhNeubrutalToggle(checked = reminderOn, onCheckedChange = { reminderOn = it })
            }
        }

        IxhPrimaryButton(
            text = "Simpan Tugas",
            onClick = {
                val finalSubj = subject.ifBlank { "Mata Pelajaran" }
                val finalTeacher = teacher.ifBlank { "Guru Mapel" }
                val finalTitle = title.ifBlank { "Tugas $finalSubj — Latihan Mandiri" }
                val finalDesc = description.ifBlank { "Kerjakan tepat waktu dan kumpulkan sesuai instruksi guru." }
                onSave(finalTitle, finalSubj, finalTeacher, deadlineLabel, daysRemaining, finalDesc, reminderOn)
            },
            testTag = "save_task_button"
        )
    }
}
