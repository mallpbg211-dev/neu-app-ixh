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
import androidx.compose.material.icons.automirrored.outlined.HelpOutline
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Backup
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.CloudSync
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material.icons.outlined.DoneAll
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.NotificationsActive
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.outlined.PictureAsPdf
import androidx.compose.material.icons.outlined.Restore
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.SmartToy
import androidx.compose.material.icons.outlined.VerifiedUser
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.AnnouncementEntity
import com.example.data.local.NotificationEntity
import com.example.data.local.SyncQueueEntity
import com.example.data.local.UserEntity
import com.example.data.local.UserRole
import com.example.data.repository.GlobalSearchItem
import com.example.ui.components.BlobExpression
import com.example.ui.components.BlobMascotState
import com.example.ui.components.ChipTone
import com.example.ui.components.IxhAlertBanner
import com.example.ui.components.IxhBlobMascot
import com.example.ui.components.IxhBlobMascotSlot
import com.example.ui.components.IxhBlobSlotStyle
import com.example.ui.components.IxhCard
import com.example.ui.components.IxhCircleIcon
import com.example.ui.components.IxhEmptyStateCard
import com.example.ui.components.IxhNeubrutalToggle
import com.example.ui.components.IxhOrganicModuleBanner
import com.example.ui.components.IxhOrganicStatTile
import com.example.ui.components.IxhOrganicWaveDivider
import com.example.ui.components.IxhPrimaryButton
import com.example.ui.components.IxhSculptedFab
import com.example.ui.components.IxhSculptedFilterChip
import com.example.ui.components.IxhSculptedSectionHeader
import com.example.ui.components.IxhSecondaryButton
import com.example.ui.components.IxhSegmentedTabs
import com.example.ui.components.IxhStatusChip
import com.example.ui.components.IxhStyledTextField
import com.example.ui.components.IxhSubScreenTopBar
import com.example.ui.components.IxhSystemIconType
import com.example.ui.components.MainNavTab
import com.example.ui.components.ixhOrganicScreenBackdrop
import com.example.ui.components.neubrutalShadow
import com.example.ui.theme.IxhAmberBg
import com.example.ui.theme.IxhBlue
import com.example.ui.theme.IxhBlueBg
import com.example.ui.theme.IxhBlueText
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
import com.example.ui.theme.VisualThemeStyle
import com.example.ui.viewmodel.AppScreenRoute
import com.example.ui.viewmodel.BackupUiState
import com.example.ui.viewmodel.BotConnectionState
import com.example.ui.viewmodel.TourUiState

// =========================================================================
// §22.3 GLOBAL SEARCH SCREEN (OFFLINE-READY + RBAC ENFORCED)
// =========================================================================

@Composable
fun GlobalSearchScreen(
    activeUser: UserEntity,
    query: String,
    selectedCategory: String,
    results: List<GlobalSearchItem>,
    onQueryChange: (String) -> Unit,
    onCategoryChange: (String) -> Unit,
    onSelectResult: (GlobalSearchItem) -> Unit,
    onBack: () -> Unit
) {
    val categories = listOf("Semua", "Siswa", "Tugas", "Jadwal", "Kas", "Pengumuman", "Absensi")
    val isSiswa = activeUser.role == UserRole.SISWA.name
    val searchBoxShape = RoundedCornerShape(topStart = 18.dp, topEnd = 12.dp, bottomStart = 12.dp, bottomEnd = 20.dp)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .ixhOrganicScreenBackdrop()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        IxhSubScreenTopBar(
            title = "Pencarian Global IX-H",
            onBack = onBack,
            rightContent = {
                IxhStatusChip(
                    text = "Akses ${activeUser.role}",
                    tone = ChipTone.OUTLINE_YELLOW
                )
            }
        )

        IxhOrganicModuleBanner(
            eyebrowTag = "Pencarian Lintas Modul",
            title = "Cari Data Kelas IX-H",
            subtitle = "${results.size} hasil siap telusur dari database lokal Room.",
            iconType = IxhSystemIconType.BERANDA
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .neubrutalShadow(enabled = true, cornerRadius = 15.dp, offsetY = 3.dp, offsetX = 2.2.dp)
                .clip(searchBoxShape)
                .background(IxhWhite, searchBoxShape)
                .border(2.4.dp, IxhInk, searchBoxShape)
        ) {
            OutlinedTextField(
                value = query,
                onValueChange = onQueryChange,
                placeholder = {
                    Text(
                        "Cari siswa, jadwal, tugas, kas, pengumuman...",
                        style = MaterialTheme.typography.bodySmall,
                        color = IxhTextSecondary
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Search,
                        contentDescription = "Cari",
                        tint = IxhInk
                    )
                },
                trailingIcon = {
                    if (query.isNotEmpty()) {
                        Icon(
                            imageVector = Icons.Outlined.Close,
                            contentDescription = "Hapus Pencarian",
                            tint = IxhInk,
                            modifier = Modifier
                                .clickable { onQueryChange("") }
                                .testTag("global_search_clear")
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("global_search_input"),
                singleLine = true,
                shape = searchBoxShape,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    cursorColor = IxhInk
                )
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            categories.forEach { cat ->
                val isSelected = selectedCategory.equals(cat, ignoreCase = true)
                IxhSculptedFilterChip(
                    label = cat,
                    selected = isSelected,
                    onClick = { onCategoryChange(cat) },
                    modifier = Modifier.testTag("global_search_cat_$cat")
                )
            }
        }

        IxhAlertBanner(
            title = if (isSiswa) "Filter Privasi Role Siswa Aktif" else "Pencarian Lintas Modul (Offline-Ready)",
            text = if (isSiswa) {
                "Sebagai Siswa, hasil pencarian hanya menampilkan jadwal, tugas, pengumuman resmi, dan data kas/kehadiran milikmu sendiri."
            } else {
                "Menampilkan hasil pencarian instan dari database lokal Room (${results.size} item cocok)."
            },
            tone = if (isSiswa) ChipTone.BLUE else ChipTone.YELLOW
        )

        if (results.isEmpty()) {
            IxhEmptyStateCard(
                title = if (query.isBlank()) "Belum ada data pada kategori $selectedCategory." else "Hasil untuk \"$query\" tidak ditemukan.",
                subtitle = "Coba kata kunci lain atau pilih kategori 'Semua' untuk menelusuri data kelas IX-H.",
                mascotState = BlobMascotState.IDLE,
                testTag = "global_search_empty_state"
            )
        } else {
            results.forEach { item ->
                val (catTone, stripe) = when (item.category) {
                    "Tugas" -> ChipTone.RED to IxhRed
                    "Jadwal" -> ChipTone.BLUE to IxhBlue
                    "Kas" -> ChipTone.GREEN to IxhGreen
                    "Pengumuman" -> ChipTone.YELLOW to IxhYellow
                    "Absensi" -> ChipTone.OUTLINE_YELLOW to IxhYellow
                    else -> ChipTone.NEUTRAL to IxhInk
                }
                IxhCard(
                    backgroundColor = IxhWhite,
                    leftStripeColor = stripe,
                    onClick = { onSelectResult(item) }
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IxhStatusChip(text = item.category, tone = catTone)
                        IxhStatusChip(text = item.badgeText, tone = ChipTone.NEUTRAL)
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.ExtraBold,
                        color = IxhInk
                    )
                    Text(
                        text = item.subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = IxhTextSecondary
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(36.dp))
    }
}

// =========================================================================
// §22.1 BACKUP & RESTORE DATABASE SCREEN (ADMIN ONLY + SHA-256 CHECKSUM)
// =========================================================================

@Composable
fun BackupRestoreScreen(
    activeUser: UserEntity,
    backupState: BackupUiState,
    onCreateBackupNow: () -> Unit,
    onRestoreFromBackupJson: (String) -> Unit,
    onBack: () -> Unit
) {
    val isAdmin = activeUser.role == UserRole.ADMIN.name
    var jsonInput by remember(backupState.latestBackupJson) {
        mutableStateOf(backupState.latestBackupJson)
    }
    var showConfirmRestoreDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .ixhOrganicScreenBackdrop()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        IxhSubScreenTopBar(
            title = "Backup & Restore Database",
            onBack = onBack,
            rightContent = {
                IxhStatusChip(text = "Schema v3", tone = ChipTone.OUTLINE_YELLOW)
            }
        )

        if (!isAdmin) {
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
                        text = "Khusus Admin Sistem",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.ExtraBold,
                        color = IxhInk
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Fitur pencadangan dan pemulihan database hanya dapat dijalankan oleh Admin.",
                        style = MaterialTheme.typography.bodySmall,
                        color = IxhTextSecondary,
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            IxhOrganicModuleBanner(
                eyebrowTag = "Proteksi SHA-256",
                title = "Brankas Snapshot Data",
                subtitle = "Cadangkan atau pulihkan database lokal dengan validasi integritas.",
                iconType = IxhSystemIconType.KAS,
                trailingContent = {
                    IxhOrganicStatTile(
                        label = "Entitas",
                        value = "${backupState.lastBackupTotalRecords}",
                        accentColor = IxhGreenBg
                    )
                }
            )

            IxhAlertBanner(
                title = "Proteksi Integritas SHA-256 & Versi Schema",
                text = "Setiap paket backup memvalidasi checksum SHA-256 dan versi schema (v3) sebelum memulihkan tabel pengguna, kas, jadwal, tugas, pengumuman, dan pengaturan.",
                tone = ChipTone.YELLOW,
                icon = Icons.Outlined.VerifiedUser
            )

            if (backupState.statusMessage.isNotBlank()) {
                IxhAlertBanner(
                    title = if (backupState.isError) "Operasi Gagal" else "Status Backup & Restore",
                    text = backupState.statusMessage,
                    tone = if (backupState.isError) ChipTone.RED else ChipTone.GREEN
                )
            }

            IxhCard(
                backgroundColor = IxhYellowLight,
                borderColor = IxhInk,
                hasHardShadow = true,
                contentPadding = PaddingValues(15.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                        IxhCircleIcon(
                            icon = Icons.Outlined.Backup,
                            bgColor = IxhYellow,
                            hasBorder = true,
                            size = 40.dp
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Cadangkan Database Lokal",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.ExtraBold,
                                color = IxhInk
                            )
                            Text(
                                text = "Simpan snapshot JSON terverifikasi",
                                style = MaterialTheme.typography.bodySmall,
                                color = IxhTextSecondary
                            )
                        }
                    }
                    IxhStatusChip(
                        text = "${backupState.lastBackupTotalRecords} entitas",
                        tone = ChipTone.GREEN
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(IxhWhite)
                        .border(2.dp, IxhInk, RoundedCornerShape(14.dp))
                        .padding(11.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Waktu Backup Terakhir: ${backupState.lastBackupTime}",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.ExtraBold,
                        color = IxhInk
                    )
                    Text(
                        text = "Lokasi File: ${backupState.localFilePath.ifBlank { "files/backups/ixh_hub_latest_backup.json" }}",
                        style = MaterialTheme.typography.bodySmall,
                        color = IxhTextSecondary
                    )
                    if (backupState.lastBackupChecksum.isNotBlank()) {
                        Text(
                            text = "SHA-256: ${backupState.lastBackupChecksum.take(24)}...",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = IxhGreenText
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
                IxhPrimaryButton(
                    text = "Buat Backup Sekarang",
                    icon = Icons.Outlined.Backup,
                    onClick = onCreateBackupNow,
                    testTag = "create_backup_button"
                )
            }

            IxhOrganicWaveDivider()

            IxhCard(
                backgroundColor = IxhWhite,
                hasHardShadow = true,
                contentPadding = PaddingValues(15.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IxhCircleIcon(
                        icon = Icons.Outlined.Restore,
                        bgColor = IxhBlueBg,
                        iconTint = IxhBlue,
                        hasBorder = true,
                        size = 38.dp
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "Pulihkan Database (Restore)",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = IxhInk
                        )
                        Text(
                            text = "Verifikasi checksum & ganti data operasional dengan paket backup",
                            style = MaterialTheme.typography.bodySmall,
                            color = IxhTextSecondary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
                IxhStyledTextField(
                    value = jsonInput,
                    onValueChange = { jsonInput = it },
                    label = "Paket JSON Backup (Schema v3)",
                    placeholder = "Tempelkan isi file backup JSON atau buat backup di atas terlebih dahulu...",
                    singleLine = false,
                    minLines = 5,
                    testTag = "backup_json_input"
                )

                Spacer(modifier = Modifier.height(12.dp))
                IxhSecondaryButton(
                    text = "Verifikasi & Pulihkan Database",
                    icon = Icons.Outlined.Restore,
                    onClick = { showConfirmRestoreDialog = true },
                    testTag = "restore_backup_button"
                )
            }
        }

        Spacer(modifier = Modifier.height(36.dp))
    }

    if (showConfirmRestoreDialog) {
        AlertDialog(
            onDismissRequest = { showConfirmRestoreDialog = false },
            containerColor = IxhWhite,
            title = {
                Text(
                    text = "Konfirmasi Pemulihan Database?",
                    fontWeight = FontWeight.ExtraBold,
                    color = IxhInk
                )
            },
            text = {
                Text(
                    text = "Data operasional saat ini akan diganti dengan isi paket backup setelah verifikasi SHA-256 dan schemaVersion=3 berhasil. Riwayat operasi ini akan dicatat di Audit Log.",
                    color = IxhInk
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showConfirmRestoreDialog = false
                        onRestoreFromBackupJson(jsonInput)
                    },
                    modifier = Modifier.testTag("confirm_restore_button")
                ) {
                    Text("Ya, Pulihkan", color = IxhRed, fontWeight = FontWeight.ExtraBold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showConfirmRestoreDialog = false }) {
                    Text("Batal", color = IxhInk)
                }
            }
        )
    }
}

// =========================================================================
// STORYBOARD 16: PENGUMUMAN KELAS (MAKS 2 DISEMATKAN, DRAF, PENTING/KEGIATAN/INFO)
// =========================================================================

@Composable
fun AnnouncementsListScreen(
    activeUser: UserEntity,
    announcements: List<AnnouncementEntity>,
    onOpenDetail: (AnnouncementEntity) -> Unit,
    onNavigateCreate: () -> Unit,
    onBack: () -> Unit
) {
    val isSiswa = activeUser.role == UserRole.SISWA.name
    var filterTab by remember { mutableIntStateOf(0) } // 0=Semua, 1=Penting, 2=Kegiatan, 3=Draf

    val visibleList = announcements.filter {
        if (isSiswa && it.isDraft) return@filter false
        when (filterTab) {
            1 -> it.category.equals("Penting", ignoreCase = true) && !it.isDraft
            2 -> it.category.equals("Kegiatan", ignoreCase = true) && !it.isDraft
            3 -> it.isDraft
            else -> !it.isDraft
        }
    }

    Box(modifier = Modifier.fillMaxSize().ixhOrganicScreenBackdrop()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            IxhSubScreenTopBar(title = "Pengumuman Kelas", onBack = onBack)

            IxhOrganicModuleBanner(
                eyebrowTag = "Papan Informasi IX-H",
                title = "Pengumuman Resmi",
                subtitle = "${announcements.count { !it.isDraft }} pengumuman aktif • Maksimal 2 disematkan di Beranda.",
                iconType = IxhSystemIconType.NOTIFIKASI
            )

            val tabs = if (isSiswa) {
                listOf("Semua", "Penting", "Kegiatan")
            } else {
                listOf("Semua", "Penting", "Kegiatan", "Draf")
            }
            IxhSegmentedTabs(
                tabs = tabs,
                selectedIndex = filterTab.coerceAtMost(tabs.lastIndex),
                onTabSelected = { filterTab = it }
            )

            if (visibleList.isEmpty()) {
                IxhEmptyStateCard(
                    title = "Belum ada pengumuman.",
                    subtitle = "Pengumuman resmi kelas IX-H dari Admin atau Pengurus akan muncul di halaman ini.",
                    mascotState = BlobMascotState.IDLE,
                    actionText = if (!isSiswa) "+ Buat Pengumuman" else null,
                    onActionClick = if (!isSiswa) onNavigateCreate else null,
                    testTag = "announcements_empty_state"
                )
            } else {
                visibleList.forEach { item ->
                    val (tone, stripe) = when (item.category) {
                        "Penting" -> ChipTone.RED to IxhRed
                        "Kegiatan" -> ChipTone.YELLOW to IxhYellow
                        else -> ChipTone.BLUE to IxhBlue
                    }
                    IxhCard(
                        backgroundColor = if (item.isPinned) IxhYellowLight else IxhWhite,
                        borderColor = IxhInk,
                        hasHardShadow = item.isPinned,
                        leftStripeColor = stripe,
                        onClick = { onOpenDetail(item) }
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                IxhStatusChip(text = item.category, tone = tone)
                                if (item.isPinned) {
                                    IxhStatusChip(text = "Disematkan", tone = ChipTone.OUTLINE_YELLOW)
                                }
                                if (item.isDraft) {
                                    IxhStatusChip(text = "Draf", tone = ChipTone.NEUTRAL)
                                }
                            }
                            Text(
                                text = item.timeLabel,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = IxhTextSecondary
                            )
                        }
                        Spacer(modifier = Modifier.height(7.dp))
                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = IxhInk
                        )
                        Text(
                            text = item.content,
                            style = MaterialTheme.typography.bodySmall,
                            color = IxhTextSecondary,
                            maxLines = 2
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Oleh ${item.authorName} (${item.authorRole})",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = IxhInk.copy(alpha = 0.72f)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(80.dp))
        }

        if (!isSiswa) {
            IxhSculptedFab(
                icon = Icons.Outlined.Add,
                contentDescription = "Buat Pengumuman",
                onClick = onNavigateCreate,
                testTag = "announcement_fab_add",
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 18.dp, bottom = 18.dp)
            )
        }
    }
}

@Composable
fun AnnouncementDetailScreen(
    activeUser: UserEntity,
    announcement: AnnouncementEntity?,
    onTogglePin: (AnnouncementEntity) -> Unit,
    onDelete: (AnnouncementEntity) -> Unit,
    onDownloadToast: (String) -> Unit,
    onBack: () -> Unit
) {
    val isSiswa = activeUser.role == UserRole.SISWA.name
    var showDeleteConfirm by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .ixhOrganicScreenBackdrop()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        IxhSubScreenTopBar(title = "Detail Pengumuman", onBack = onBack)

        if (announcement == null) {
            IxhEmptyStateCard(
                title = "Pengumuman tidak ditemukan.",
                subtitle = "Pilih salah satu pengumuman pada daftar untuk membaca isi lengkapnya.",
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
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IxhStatusChip(text = announcement.category, tone = ChipTone.RED)
                    if (announcement.isPinned) {
                        IxhStatusChip(text = "Disematkan di Beranda", tone = ChipTone.NEUTRAL)
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = announcement.title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.ExtraBold,
                    color = IxhInk
                )
                Text(
                    text = "Oleh ${announcement.authorName} (${announcement.authorRole}) • ${announcement.timeLabel}",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    color = IxhInk.copy(alpha = 0.8f)
                )
            }

            IxhCard(backgroundColor = IxhWhite, hasHardShadow = true) {
                Text(
                    text = announcement.content,
                    style = MaterialTheme.typography.bodyMedium,
                    color = IxhInk,
                    lineHeight = 22.sp
                )
            }

            if (announcement.attachmentName.isNotBlank()) {
                IxhCard(
                    backgroundColor = IxhWhite,
                    onClick = { onDownloadToast("Mengunduh ${announcement.attachmentName}...") }
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                            IxhCircleIcon(
                                icon = Icons.Outlined.PictureAsPdf,
                                bgColor = IxhRedBg,
                                iconTint = IxhRed
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = announcement.attachmentName,
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = IxhInk
                                )
                                Text(
                                    text = announcement.attachmentSize,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = IxhTextSecondary
                                )
                            }
                        }
                        IxhStatusChip(text = "Unduh", tone = ChipTone.OUTLINE_YELLOW)
                    }
                }
            }

            if (!isSiswa) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    IxhPrimaryButton(
                        text = if (announcement.isPinned) "Lepas Sematan" else "Sematkan (Maks 2)",
                        onClick = { onTogglePin(announcement) },
                        modifier = Modifier.weight(1f)
                    )
                    IxhSecondaryButton(
                        text = "Hapus",
                        isDanger = true,
                        onClick = { showDeleteConfirm = true },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }

    if (showDeleteConfirm && announcement != null) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirm = false },
            containerColor = IxhWhite,
            title = { Text("Hapus Pengumuman?", fontWeight = FontWeight.ExtraBold, color = IxhInk) },
            text = { Text("Pengumuman '${announcement.title}' akan dihapus dan tercatat di Log Aktivitas.", color = IxhInk) },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDeleteConfirm = false
                        onDelete(announcement)
                    }
                ) {
                    Text("Ya, Hapus", color = IxhRed, fontWeight = FontWeight.ExtraBold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteConfirm = false }) {
                    Text("Batal", color = IxhInk)
                }
            }
        )
    }
}

@Composable
fun AnnouncementFormScreen(
    authorName: String,
    authorRole: String,
    onSave: (title: String, content: String, category: String, isPinned: Boolean, isDraft: Boolean) -> Unit,
    onBack: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var catIdx by remember { mutableIntStateOf(0) }
    val cats = listOf("Penting", "Kegiatan", "Info")
    var isPinned by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .ixhOrganicScreenBackdrop()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        IxhSubScreenTopBar(title = "Buat Pengumuman", onBack = onBack)

        IxhOrganicModuleBanner(
            eyebrowTag = "Terbitkan Info",
            title = "Siaran Kelas IX-H",
            subtitle = "Pengumuman aktif otomatis mengirim notifikasi ke seluruh anggota.",
            iconType = IxhSystemIconType.NOTIFIKASI
        )

        IxhSculptedSectionHeader(title = "Kategori Pengumuman")
        IxhSegmentedTabs(
            tabs = cats,
            selectedIndex = catIdx,
            onTabSelected = { catIdx = it }
        )

        IxhStyledTextField(
            value = title,
            onValueChange = { title = it },
            label = "Judul Pengumuman",
            placeholder = "Contoh: Kerja Bakti Jumat Bersih",
            testTag = "announcement_title_input"
        )

        IxhStyledTextField(
            value = content,
            onValueChange = { content = it },
            label = "Isi Pengumuman",
            placeholder = "Tulis detail informasi lengkap untuk siswa...",
            singleLine = false,
            minLines = 4,
            testTag = "announcement_content_input"
        )

        IxhCard(backgroundColor = IxhWhite) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Sematkan di Beranda", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.ExtraBold, color = IxhInk)
                    Text("Maksimal 2 pengumuman disematkan bersamaan", style = MaterialTheme.typography.bodySmall, color = IxhTextSecondary)
                }
                IxhNeubrutalToggle(checked = isPinned, onCheckedChange = { isPinned = it })
            }
        }

        IxhPrimaryButton(
            text = "Terbitkan & Kirim Notifikasi",
            onClick = {
                val finalTitle = title.ifBlank { "Info Kelas IX-H Terbaru" }
                val finalContent = content.ifBlank { "Harap diperhatikan oleh seluruh siswa kelas IX-H." }
                onSave(finalTitle, finalContent, cats[catIdx], isPinned, false)
            },
            testTag = "publish_announcement_button"
        )

        IxhSecondaryButton(
            text = "Simpan Sebagai Draf",
            onClick = {
                val finalTitle = title.ifBlank { "Draf Pengumuman IX-H" }
                val finalContent = content.ifBlank { "Draf belum diterbitkan." }
                onSave(finalTitle, finalContent, cats[catIdx], false, true)
            }
        )
    }
}

// =========================================================================
// §22.4 NOTIFICATION CENTER (DEDUPLICATED EVENT STREAM + MULTI-CUE STATUS)
// =========================================================================

@Composable
fun NotificationsCenterScreen(
    notifications: List<NotificationEntity>,
    onMarkAllRead: () -> Unit,
    onDeleteRead: () -> Unit,
    onNotificationClick: (NotificationEntity) -> Unit,
    onDeleteSingle: (Int) -> Unit,
    onOpenSettings: () -> Unit,
    onBack: () -> Unit
) {
    var filterTab by remember { mutableIntStateOf(0) } // 0=Semua, 1=Belum Dibaca
    var selectedCategory by remember { mutableStateOf("Semua") }
    val categories = listOf("Semua", "Kas", "Tugas", "Jadwal", "Pengumuman", "Bot")
    val unreadCount = notifications.count { it.isUnread }

    val displayed = notifications.filter { item ->
        val matchesRead = if (filterTab == 1) item.isUnread else true
        val matchesCat = selectedCategory == "Semua" || item.category.equals(selectedCategory, ignoreCase = true)
        matchesRead && matchesCat
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
            title = "Pusat Notifikasi",
            onBack = onBack,
            rightContent = {
                IxhStatusChip(
                    text = "Atur",
                    tone = ChipTone.OUTLINE_YELLOW,
                    modifier = Modifier.clickable(onClick = onOpenSettings)
                )
            }
        )

        IxhOrganicModuleBanner(
            eyebrowTag = "Aliran Event IX-H",
            title = if (unreadCount > 0) "$unreadCount Belum Dibaca" else "Semua Sudah Dibaca",
            subtitle = "Pemberitahuan kas, jadwal, tugas, & bot bebas duplikasi.",
            iconType = IxhSystemIconType.NOTIFIKASI,
            trailingContent = {
                IxhOrganicStatTile(
                    label = "Total",
                    value = "${notifications.size}",
                    accentColor = if (unreadCount > 0) IxhRedBg else IxhGreenBg,
                    valueColor = if (unreadCount > 0) IxhRed else IxhGreenText
                )
            }
        )

        IxhSegmentedTabs(
            tabs = listOf("Semua (${notifications.size})", "Belum Dibaca ($unreadCount)"),
            selectedIndex = filterTab,
            onTabSelected = { filterTab = it }
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            categories.forEach { cat ->
                IxhSculptedFilterChip(
                    label = cat,
                    selected = selectedCategory == cat,
                    onClick = { selectedCategory = cat }
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            IxhSecondaryButton(
                text = "Tandai Dibaca",
                icon = Icons.Outlined.DoneAll,
                onClick = onMarkAllRead,
                modifier = Modifier.weight(1f),
                testTag = "mark_all_notifications_read"
            )
            IxhSecondaryButton(
                text = "Hapus Dibaca",
                icon = Icons.Outlined.DeleteOutline,
                onClick = onDeleteRead,
                modifier = Modifier.weight(1f)
            )
        }

        IxhOrganicWaveDivider()

        if (displayed.isEmpty()) {
            IxhEmptyStateCard(
                title = "Belum ada notifikasi.",
                subtitle = "Semua pemberitahuan kas, jadwal, tugas, dan pengumuman kelas akan muncul secara otomatis tanpa duplikasi.",
                mascotState = BlobMascotState.SMILE,
                testTag = "notifications_empty_state"
            )
        } else {
            displayed.forEach { item ->
                val catStripe = when (item.category.lowercase()) {
                    "kas" -> IxhGreen
                    "tugas" -> IxhRed
                    "jadwal" -> IxhBlue
                    "pengumuman" -> IxhYellow
                    else -> IxhInk
                }
                IxhCard(
                    backgroundColor = if (item.isUnread) IxhYellowLight else IxhWhite,
                    borderColor = IxhInk,
                    hasHardShadow = item.isUnread,
                    leftStripeColor = if (item.isUnread) IxhYellow else catStripe,
                    onClick = { onNotificationClick(item) }
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            IxhStatusChip(
                                text = item.category,
                                tone = if (item.isUnread) ChipTone.OUTLINE_YELLOW else ChipTone.NEUTRAL
                            )
                            IxhStatusChip(
                                text = if (item.isUnread) "BARU" else "DIBACA",
                                tone = if (item.isUnread) ChipTone.RED else ChipTone.GREEN
                            )
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(IxhRedBg)
                                .border(1.4.dp, IxhInk, RoundedCornerShape(8.dp))
                                .clickable { onDeleteSingle(item.id) }
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = "Hapus",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = IxhRed
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(7.dp))
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.ExtraBold,
                        color = IxhInk
                    )
                    Text(
                        text = item.subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = IxhTextSecondary
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = "${item.dayGroup} • ${item.timeLabel}",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = IxhInk.copy(alpha = 0.72f)
                    )
                }
            }
        }
    }
}

@Composable
fun NotificationSettingsScreen(
    onSaveToast: (String) -> Unit,
    onBack: () -> Unit
) {
    var tugasOn by remember { mutableStateOf(true) }
    var jadwalOn by remember { mutableStateOf(true) }
    var kasOn by remember { mutableStateOf(true) }
    var pengumumanOn by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .ixhOrganicScreenBackdrop()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        IxhSubScreenTopBar(title = "Pengaturan Notifikasi", onBack = onBack)

        IxhOrganicModuleBanner(
            eyebrowTag = "Preferensi Pengingat",
            title = "Atur Saluran Notifikasi",
            subtitle = "Pilih kategori peringatan yang aktif di perangkat ini.",
            iconType = IxhSystemIconType.NOTIFIKASI
        )

        IxhCard(backgroundColor = IxhWhite, hasHardShadow = true) {
            NotifToggleRow("Pengingat Tugas (H-1)", "Ingatkan sehari sebelum tenggat waktu", tugasOn) { tugasOn = it }
            NotifToggleRow("Jadwal Pelajaran (20.00 WIB)", "Pengingat buku & perlengkapan esok hari", jadwalOn) { jadwalOn = it }
            NotifToggleRow("Tagihan & Konfirmasi Kas", "Notifikasi saat tagihan terbit atau lunas", kasOn) { kasOn = it }
            NotifToggleRow("Pengumuman Penting Kelas", "Info mendadak dari Ketua Kelas / Wali Kelas", pengumumanOn) { pengumumanOn = it }
        }

        IxhPrimaryButton(
            text = "Simpan Preferensi Notifikasi",
            onClick = {
                onSaveToast("Preferensi notifikasi berhasil disimpan")
                onBack()
            }
        )
    }
}

@Composable
private fun NotifToggleRow(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.ExtraBold, color = IxhInk)
            Text(subtitle, style = MaterialTheme.typography.bodySmall, color = IxhTextSecondary)
        }
        Spacer(modifier = Modifier.width(8.dp))
        IxhNeubrutalToggle(checked = checked, onCheckedChange = onCheckedChange)
    }
}

// =========================================================================
// STORYBOARD 07 & 31: PROFIL & AKUN, PENGATURAN, GANTI TEMA, OFFLINE-FIRST VPS SYNC
// =========================================================================

@Composable
fun ProfileMainScreen(
    activeUser: UserEntity,
    onSwitchPersona: (String) -> Unit,
    onNavigate: (AppScreenRoute) -> Unit,
    onStartTourAgain: () -> Unit,
    onBack: () -> Unit
) {
    val isAdmin = activeUser.role == UserRole.ADMIN.name
    val isPengurus = activeUser.role == UserRole.PENGURUS.name
    var showLogoutDialog by remember { mutableStateOf(false) }
    val idPassShape = RoundedCornerShape(topStart = 28.dp, topEnd = 16.dp, bottomStart = 16.dp, bottomEnd = 34.dp)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .ixhOrganicScreenBackdrop()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        IxhSubScreenTopBar(title = "Profil & Akun", onBack = onBack)

        // Signature Organic Student/Officer ID Pass
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .neubrutalShadow(enabled = true, cornerRadius = 24.dp, offsetY = 5.dp, offsetX = 3.5.dp)
                .clip(idPassShape)
                .background(IxhYellow, idPassShape)
                .drawBehind {
                    val w = size.width
                    val h = size.height
                    val upperWave = Path().apply {
                        moveTo(w * 0.40f, 0f)
                        cubicTo(w * 0.58f, h * 0.24f, w * 0.78f, h * 0.16f, w, h * 0.48f)
                        lineTo(w, 0f)
                        close()
                    }
                    drawPath(upperWave, color = Color(0xFFFFE47A).copy(alpha = 0.85f))

                    val lowerWave = Path().apply {
                        moveTo(0f, h * 0.70f)
                        cubicTo(w * 0.34f, h * 0.56f, w * 0.68f, h * 0.88f, w, h * 0.62f)
                        lineTo(w, h)
                        lineTo(0f, h)
                        close()
                    }
                    drawPath(lowerWave, color = Color(0xFFFFB703).copy(alpha = 0.28f))
                    val contour = Path().apply {
                        moveTo(0f, h * 0.70f)
                        cubicTo(w * 0.34f, h * 0.56f, w * 0.68f, h * 0.88f, w, h * 0.62f)
                    }
                    drawPath(contour, color = IxhInk.copy(alpha = 0.20f), style = Stroke(width = 1.8.dp.toPx(), cap = StrokeCap.Round))
                }
                .border(2.6.dp, IxhInk, idPassShape)
                .padding(16.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .neubrutalShadow(enabled = true, cornerRadius = 18.dp, offsetY = 3.dp, offsetX = 2.2.dp)
                                .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 12.dp, bottomStart = 12.dp, bottomEnd = 22.dp))
                                .background(IxhCream)
                                .border(2.4.dp, IxhInk, RoundedCornerShape(topStart = 20.dp, topEnd = 12.dp, bottomStart = 12.dp, bottomEnd = 22.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = activeUser.fullName.take(2).uppercase(),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = IxhInk
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(IxhInk)
                                    .padding(horizontal = 9.dp, vertical = 3.dp)
                            ) {
                                Text(
                                    text = if (activeUser.subRoleTitle.isNotBlank()) "${activeUser.role} • ${activeUser.subRoleTitle}".uppercase() else activeUser.role.uppercase(),
                                    fontSize = 9.5.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = IxhYellow
                                )
                            }
                            Spacer(modifier = Modifier.height(5.dp))
                            Text(
                                text = activeUser.fullName,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.ExtraBold,
                                color = IxhInk,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = "@${activeUser.username} • Absen ${"%02d".format(activeUser.absenNo)}",
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Bold,
                                color = IxhInk.copy(alpha = 0.85f)
                            )
                        }
                    }
                    IxhBlobMascotSlot(
                        slotStyle = IxhBlobSlotStyle.APP_BADGE_3D,
                        size = 44.dp
                    )
                }

                // Embedded quick metadata + action dock
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(IxhWhite)
                        .border(2.dp, IxhInk, RoundedCornerShape(14.dp))
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("NOMOR WHATSAPP", fontSize = 8.5.sp, fontWeight = FontWeight.ExtraBold, color = IxhTextSecondary)
                        Text(
                            text = activeUser.phoneWa.ifBlank { "Belum diatur" },
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.ExtraBold,
                            color = IxhInk
                        )
                    }
                    IxhStatusChip(
                        text = activeUser.waStatus.ifBlank { "VALID" },
                        tone = ChipTone.GREEN
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    IxhSecondaryButton(
                        text = "Edit Profil & WA",
                        icon = Icons.Outlined.Edit,
                        onClick = { onNavigate(AppScreenRoute.PROFILE_EDIT) },
                        modifier = Modifier.weight(1f)
                    )
                    IxhSecondaryButton(
                        text = "Ganti Tema",
                        icon = Icons.Outlined.Palette,
                        onClick = { onNavigate(AppScreenRoute.SETTINGS_APPEARANCE) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        IxhOrganicWaveDivider()

        IxhSculptedSectionHeader(title = "Pencarian Cepat")
        ProfileMenuRow("Pencarian Global IX-H", "Cari siswa, jadwal, tugas, kas & pengumuman", Icons.Outlined.Search) {
            onNavigate(AppScreenRoute.GLOBAL_SEARCH)
        }

        if (isAdmin || isPengurus) {
            IxhSculptedSectionHeader(title = "Manajemen Kelas & Bot")
            ProfileMenuRow("Panel Bot WhatsApp (WangLinMD)", "Status sesi, kirim laporan & template", Icons.Outlined.SmartToy) {
                onNavigate(AppScreenRoute.BOT_PANEL)
            }
            if (isAdmin) {
                ProfileMenuRow("Manajemen Pengguna Kelas IX-H", "Kelola akun, role & status nomor WhatsApp", Icons.Outlined.Group) {
                    onNavigate(AppScreenRoute.USER_MANAGEMENT_LIST)
                }
                ProfileMenuRow("Backup & Restore Database", "Cadangkan & pulihkan snapshot JSON terverifikasi SHA-256", Icons.Outlined.Backup) {
                    onNavigate(AppScreenRoute.BACKUP_RESTORE)
                }
                ProfileMenuRow("Log Aktivitas Permanen (Audit Log)", "Audit semua perubahan kas, absensi, backup & role", Icons.Outlined.History) {
                    onNavigate(AppScreenRoute.ACTIVITY_LOGS)
                }
            }
            ProfileMenuRow(
                title = "Pengaturan Kelas IX-H",
                subtitle = if (isAdmin) "Ubah kas bawaan Rp6.000 & target hadir 90%" else "Hanya baca (Dikelola Admin)",
                icon = Icons.Outlined.Settings
            ) {
                onNavigate(AppScreenRoute.CLASS_SETTINGS)
            }
        }

        IxhSculptedSectionHeader(title = "Pengaturan & Bantuan")
        ProfileMenuRow("Ganti Tema & Tampilan", "Hybrid Neubrutalism • Liquid Glass (Enteng)", Icons.Outlined.Palette) {
            onNavigate(AppScreenRoute.SETTINGS_APPEARANCE)
        }
        ProfileMenuRow("Offline-First & Sinkronisasi VPS", "Antrean sinkronisasi lokal Room & endpoint REST API", Icons.Outlined.CloudSync) {
            onNavigate(AppScreenRoute.SETTINGS_VPS_SYNC)
        }
        ProfileMenuRow("Keamanan & Ubah Password", "Ganti password akun & sesi perangkat", Icons.Outlined.Security) {
            onNavigate(AppScreenRoute.SETTINGS_SECURITY)
        }
        ProfileMenuRow("Pusat Bantuan (FAQ)", "Cari panduan kas, absensi, atau perintah STOP", Icons.AutoMirrored.Outlined.HelpOutline) {
            onNavigate(AppScreenRoute.HELP_CENTER)
        }
        ProfileMenuRow("Ulangi Tur Panduan Aplikasi", "Tampilkan kembali coachmark langkah demi langkah", Icons.Outlined.Explore) {
            onStartTourAgain()
        }

        IxhSecondaryButton(
            text = "Keluar dari Akun",
            icon = Icons.AutoMirrored.Outlined.Logout,
            isDanger = true,
            onClick = { showLogoutDialog = true }
        )
    }

    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            containerColor = IxhWhite,
            title = { Text("Keluar dari IX-H Hub?", fontWeight = FontWeight.ExtraBold, color = IxhInk) },
            text = { Text("Data kelas tetap aman tersimpan di database lokal. Anda dapat masuk kembali kapan saja.", color = IxhInk) },
            confirmButton = {
                TextButton(
                    onClick = {
                        showLogoutDialog = false
                        onNavigate(AppScreenRoute.LOGOUT_SUCCESS)
                    }
                ) {
                    Text("Ya, Keluar", color = IxhRed, fontWeight = FontWeight.ExtraBold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) {
                    Text("Batal", color = IxhInk)
                }
            }
        )
    }
}

@Composable
private fun ProfileMenuRow(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    IxhCard(
        backgroundColor = IxhWhite,
        onClick = onClick,
        contentPadding = PaddingValues(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IxhCircleIcon(icon = icon, bgColor = IxhYellowLight, size = 38.dp)
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(title, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.ExtraBold, color = IxhInk)
                Text(subtitle, style = MaterialTheme.typography.bodySmall, color = IxhTextSecondary)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(RoundedCornerShape(9.dp))
                    .background(IxhCream)
                    .border(1.6.dp, IxhInk, RoundedCornerShape(9.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                    contentDescription = null,
                    tint = IxhInk,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

@Composable
fun ProfileEditScreen(
    activeUser: UserEntity,
    onSaveProfile: (String, String) -> Unit,
    onBack: () -> Unit
) {
    var fullName by remember(activeUser) { mutableStateOf(activeUser.fullName) }
    var phoneWa by remember(activeUser) { mutableStateOf(activeUser.phoneWa) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .ixhOrganicScreenBackdrop()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        IxhSubScreenTopBar(title = "Edit Profil Saya", onBack = onBack)

        IxhOrganicModuleBanner(
            eyebrowTag = "Identitas Anggota",
            title = "@${activeUser.username}",
            subtitle = "Perbarui nama tampilan & nomor WhatsApp penerima laporan.",
            iconType = IxhSystemIconType.PROFIL
        )

        IxhStyledTextField(
            value = fullName,
            onValueChange = { fullName = it },
            label = "Nama Lengkap",
            placeholder = "Nama sesuai absen kelas"
        )

        IxhStyledTextField(
            value = phoneWa,
            onValueChange = { phoneWa = it },
            label = "Nomor WhatsApp Aktif",
            placeholder = "0812xxxxxxxx"
        )

        IxhAlertBanner(
            text = "Nomor WhatsApp otomatis dinormalisasi ke format +62 untuk keperluan laporan kelas.",
            tone = ChipTone.BLUE
        )

        IxhPrimaryButton(
            text = "Simpan Perubahan Profil",
            onClick = { onSaveProfile(fullName, phoneWa) }
        )
    }
}

@Composable
fun SettingsAppearanceScreen(
    currentStyle: VisualThemeStyle,
    darkTheme: Boolean,
    lowRamSolidMode: Boolean,
    onApplyTheme: (VisualThemeStyle, Boolean, Boolean) -> Unit,
    onBack: () -> Unit
) {
    var selectedStyle by remember(currentStyle) { mutableStateOf(currentStyle) }
    var isDark by remember(darkTheme) { mutableStateOf(darkTheme) }
    var isLowRam by remember(lowRamSolidMode) { mutableStateOf(lowRamSolidMode) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .ixhOrganicScreenBackdrop()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        IxhSubScreenTopBar(title = "Ganti Tema & Tampilan", onBack = onBack)

        IxhOrganicModuleBanner(
            eyebrowTag = "Personalisasi UI",
            title = "Gaya Visual IX-H Hub",
            subtitle = "Pilih gaya tampilan utama dan mode hemat memori.",
            iconType = IxhSystemIconType.BERANDA
        )

        IxhSculptedSectionHeader(title = "Pilih Gaya Desain")

        // Option 1: Hybrid Premium Neubrutalism (Bawaan)
        val isNeu = selectedStyle == VisualThemeStyle.NEUBRUTALISM
        IxhCard(
            backgroundColor = if (isNeu) IxhYellowLight else IxhWhite,
            borderColor = IxhInk,
            hasHardShadow = isNeu,
            leftStripeColor = if (isNeu) IxhYellow else IxhSurfaceMuted,
            onClick = { selectedStyle = VisualThemeStyle.NEUBRUTALISM }
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(
                            text = "Hybrid Neubrutalism",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = IxhInk
                        )
                        IxhStatusChip(text = "Sangat Ringan", tone = ChipTone.GREEN)
                    }
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(
                        text = "Garis tepi tegas hitam, bayangan taktil, dan warna kuning hangat #FFC928.",
                        style = MaterialTheme.typography.bodySmall,
                        color = IxhTextSecondary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Box(modifier = Modifier.size(18.dp).clip(CircleShape).background(IxhYellow).border(1.8.dp, IxhInk, CircleShape))
                        Box(modifier = Modifier.size(18.dp).clip(CircleShape).background(IxhCream).border(1.8.dp, IxhInk, CircleShape))
                        Box(modifier = Modifier.size(18.dp).clip(CircleShape).background(IxhInk).border(1.8.dp, IxhInk, CircleShape))
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
                IxhStatusChip(
                    text = if (isNeu) "Aktif ✓" else "Pilih",
                    tone = if (isNeu) ChipTone.OUTLINE_YELLOW else ChipTone.NEUTRAL
                )
            }
        }

        // Option 2: Liquid Glass (Enteng)
        val isGlass = selectedStyle == VisualThemeStyle.LIQUID_GLASS
        IxhCard(
            backgroundColor = if (isGlass) IxhBlueBg else IxhWhite,
            borderColor = IxhInk,
            hasHardShadow = isGlass,
            leftStripeColor = if (isGlass) IxhBlue else IxhSurfaceMuted,
            onClick = { selectedStyle = VisualThemeStyle.LIQUID_GLASS }
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(
                            text = "Liquid Glass (Enteng)",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.ExtraBold,
                            color = IxhInk
                        )
                        IxhStatusChip(text = "Modern", tone = ChipTone.BLUE)
                    }
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(
                        text = "Kartu kaca semi-transparan, sudut membulat lembut, dan bilah navigasi melayang.",
                        style = MaterialTheme.typography.bodySmall,
                        color = IxhTextSecondary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Box(modifier = Modifier.size(18.dp).clip(CircleShape).background(IxhBlueBg).border(1.8.dp, IxhInk, CircleShape))
                        Box(modifier = Modifier.size(18.dp).clip(CircleShape).background(IxhWhite).border(1.8.dp, IxhInk, CircleShape))
                        Box(modifier = Modifier.size(18.dp).clip(CircleShape).background(IxhYellow).border(1.8.dp, IxhInk, CircleShape))
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
                IxhStatusChip(
                    text = if (isGlass) "Aktif ✓" else "Pilih",
                    tone = if (isGlass) ChipTone.BLUE else ChipTone.NEUTRAL
                )
            }
        }

        IxhCard(backgroundColor = IxhWhite, hasHardShadow = true) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Mode Hemat RAM (HP RAM ≤ 2 GB)",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.ExtraBold,
                        color = IxhInk
                    )
                    Text(
                        text = "Matikan efek transparan & animasi maskot untuk perangkat entry-level.",
                        style = MaterialTheme.typography.bodySmall,
                        color = IxhTextSecondary
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                IxhNeubrutalToggle(checked = isLowRam, onCheckedChange = { isLowRam = it })
            }
        }

        IxhPrimaryButton(
            text = "Terapkan Tema Sekarang",
            onClick = {
                onApplyTheme(selectedStyle, isDark, isLowRam)
                onBack()
            },
            testTag = "apply_theme_button"
        )
    }
}

@Composable
fun SettingsSecurityScreen(
    onSavePasswordToast: (String) -> Unit,
    onBack: () -> Unit
) {
    var oldPass by remember { mutableStateOf("") }
    var newPass by remember { mutableStateOf("") }
    var confirmPass by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .ixhOrganicScreenBackdrop()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        IxhSubScreenTopBar(title = "Keamanan & Password", onBack = onBack)

        IxhOrganicModuleBanner(
            eyebrowTag = "Keamanan Akun",
            title = "Ubah Password Akun",
            subtitle = "Gunakan kombinasi huruf & angka minimal 8 karakter.",
            iconType = IxhSystemIconType.PROFIL
        )

        IxhStyledTextField(
            value = oldPass,
            onValueChange = { oldPass = it },
            label = "Password Saat Ini",
            placeholder = "Masukkan password aktif"
        )
        IxhStyledTextField(
            value = newPass,
            onValueChange = { newPass = it },
            label = "Password Baru (Min. 8 karakter)",
            placeholder = "Minimal 8 karakter huruf & angka"
        )
        IxhStyledTextField(
            value = confirmPass,
            onValueChange = { confirmPass = it },
            label = "Ulangi Password Baru",
            placeholder = "Ketik ulang password baru"
        )

        IxhPrimaryButton(
            text = "Perbarui Password",
            onClick = {
                onSavePasswordToast("Password akun berhasil diperbarui")
                onBack()
            }
        )
    }
}

// =========================================================================
// §22.5 OFFLINE-FIRST & VPS SYNCHRONIZATION SCREEN
// =========================================================================

@Composable
fun SettingsVpsSyncScreen(
    botState: BotConnectionState,
    syncQueue: List<SyncQueueEntity> = emptyList(),
    onSaveVpsConfig: (String, String) -> Unit,
    onSyncNow: () -> Unit,
    onClearSyncedQueue: () -> Unit = {},
    onBack: () -> Unit
) {
    var baseUrl by remember(botState) { mutableStateOf(botState.vpsBaseUrl) }
    var apiKey by remember(botState) { mutableStateOf(botState.vpsApiKey) }
    val pendingCount = syncQueue.count { it.status == "PENDING" }
    val syncedCount = syncQueue.count { it.status == "SYNCED" }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .ixhOrganicScreenBackdrop()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        IxhSubScreenTopBar(
            title = "Offline-First & Sinkronisasi VPS",
            onBack = onBack,
            rightContent = {
                IxhStatusChip(
                    text = if (pendingCount > 0) "$pendingCount Pending" else "Lokal Aman",
                    tone = if (pendingCount > 0) ChipTone.YELLOW else ChipTone.GREEN
                )
            }
        )

        IxhOrganicModuleBanner(
            eyebrowTag = "Sinkronisasi VPS",
            title = "Antrean Data Lokal",
            subtitle = "Semua perubahan tersimpan di Room & siap sinkronisasi idempoten.",
            iconType = IxhSystemIconType.BOT
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            IxhOrganicStatTile(
                label = "Pending",
                value = "$pendingCount",
                accentColor = IxhYellowLight,
                modifier = Modifier.weight(1f)
            )
            IxhOrganicStatTile(
                label = "Tersinkron",
                value = "$syncedCount",
                accentColor = IxhGreenBg,
                valueColor = IxhGreenText,
                modifier = Modifier.weight(1f)
            )
        }

        IxhAlertBanner(
            title = "Arsitektur Offline-First (Room SQLite + Idempotent Queue)",
            text = "Semua perubahan absensi, kas, jadwal, tugas, dan pengumuman langsung tersimpan di Room dan dicatat dalam antrean sinkronisasi lokal dengan idempotency key unik.",
            tone = ChipTone.GREEN
        )

        IxhStyledTextField(
            value = baseUrl,
            onValueChange = { baseUrl = it },
            label = "Endpoint URL Server VPS",
            placeholder = "Contoh: https://api.ixh-spensari.my.id/v1",
            testTag = "vps_url_input"
        )

        IxhStyledTextField(
            value = apiKey,
            onValueChange = { apiKey = it },
            label = "Bearer Token / API Key VPS",
            placeholder = "Masukkan bearer token VPS",
            testTag = "vps_token_input"
        )

        IxhCard(backgroundColor = IxhWhite) {
            Text("STATUS SINKRONISASI TERAKHIR", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.ExtraBold, color = IxhInk)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = botState.lastVpsSyncStatus,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold,
                color = IxhInk
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                IxhStatusChip(text = "Pending: $pendingCount", tone = ChipTone.YELLOW)
                IxhStatusChip(text = "Tersinkron: $syncedCount", tone = ChipTone.GREEN)
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            IxhPrimaryButton(
                text = "Simpan & Sinkronkan ke VPS",
                onClick = {
                    onSaveVpsConfig(baseUrl, apiKey)
                    onSyncNow()
                },
                modifier = Modifier.weight(1f),
                testTag = "sync_vps_now_button"
            )
            if (syncedCount > 0) {
                IxhSecondaryButton(
                    text = "Bersihkan ($syncedCount)",
                    onClick = onClearSyncedQueue,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        IxhCard(backgroundColor = IxhYellowLight, borderColor = IxhInk, hasHardShadow = true) {
            Text(
                text = "KEBIJAKAN RESOLUSI KONFLIK DATA (§22.5)",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.ExtraBold,
                color = IxhInk
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "• Kas & Transaksi: APPEND_LEDGER (Buku besar append-only, tidak pernah ditimpa diam-diam)\n" +
                    "• Absensi & Pengguna: SERVER_AUTHORITATIVE_MERGE (Rekonsiliasi aman dengan jejak audit)\n" +
                    "• Jadwal, Tugas & Pengumuman: LAST_WRITE_WINS (Berdasarkan timestamp pembaruan)",
                style = MaterialTheme.typography.bodySmall,
                color = IxhInk
            )
        }

        IxhSculptedSectionHeader(title = "Antrean Sinkronisasi Lokal (${syncQueue.size})")

        if (syncQueue.isEmpty()) {
            IxhEmptyStateCard(
                title = "Antrean sinkronisasi kosong.",
                subtitle = "Perubahan data lokal baru akan otomatis masuk ke antrean ini hingga disinkronkan ke VPS.",
                mascotState = BlobMascotState.SMILE
            )
        } else {
            syncQueue.take(15).forEach { item ->
                val (tone, stripe) = when (item.status) {
                    "SYNCED" -> ChipTone.GREEN to IxhGreen
                    "FAILED" -> ChipTone.RED to IxhRed
                    else -> ChipTone.YELLOW to IxhYellow
                }
                IxhCard(backgroundColor = IxhWhite, leftStripeColor = stripe) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            IxhStatusChip(text = "${item.entityType} • ${item.operation}", tone = ChipTone.OUTLINE_YELLOW)
                            IxhStatusChip(text = item.status, tone = tone)
                        }
                        Text(
                            text = item.actorName.ifBlank { "Lokal" },
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = IxhTextSecondary
                        )
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = item.summary,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.ExtraBold,
                        color = IxhInk
                    )
                    Text(
                        text = "Aturan Konflik: ${item.conflictRule} • ID: ${item.eventId.takeLast(12)}",
                        style = MaterialTheme.typography.labelSmall,
                        color = IxhTextSecondary
                    )
                }
            }
        }
    }
}

@Composable
fun LogoutSuccessScreen(
    onLoginAgain: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .ixhOrganicScreenBackdrop()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        IxhCard(
            backgroundColor = IxhYellowLight,
            borderColor = IxhInk,
            hasHardShadow = true,
            contentPadding = PaddingValues(24.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(106.dp)
                        .neubrutalShadow(enabled = true, cornerRadius = 24.dp, offsetY = 3.5.dp, offsetX = 2.5.dp)
                        .clip(RoundedCornerShape(topStart = 28.dp, topEnd = 16.dp, bottomStart = 16.dp, bottomEnd = 30.dp))
                        .background(IxhWhite)
                        .border(2.5.dp, IxhInk, RoundedCornerShape(topStart = 28.dp, topEnd = 16.dp, bottomStart = 16.dp, bottomEnd = 30.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    IxhBlobMascot(expression = BlobExpression.SMILE_HAPPY, size = 86.dp)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Berhasil Keluar",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = IxhInk
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Sampai jumpa kembali di IX-H Hub • SMP Spensari!",
                    style = MaterialTheme.typography.bodyMedium,
                    color = IxhTextSecondary,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(20.dp))
                IxhPrimaryButton(
                    text = "Masuk Kembali ke Akun",
                    onClick = onLoginAgain
                )
            }
        }
    }
}

// =========================================================================
// STORYBOARD 32C: PUSAT BANTUAN (LIVE SEARCH >= 2 HURUF + HIGHLIGHT KUNING)
// =========================================================================

data class HelpTopicItem(
    val id: String,
    val title: String,
    val category: String,
    val summary: String,
    val steps: List<String>,
    val forAdminOnly: Boolean = false
)

private val HELP_TOPICS = listOf(
    HelpTopicItem(
        id = "tagihan",
        title = "Cara Membuat & Mengirim Tagihan Kas via WhatsApp",
        category = "Kas & Tagihan",
        summary = "Panduan membuat periode kas Rp6.000 dan mengirim pengingat WA otomatis ke nomor siswa yang valid.",
        steps = listOf(
            "Buka tab Kas di bilah navigasi bawah, lalu pilih sub-tab Tagihan.",
            "Ketuk tombol '+ Tagihan' dan isi nama periode kas mingguan.",
            "Periksa pratinjau pesan WhatsApp personal dan pastikan nomor bermasalah dilewati otomatis.",
            "Ketuk 'Buat Tagihan & Kirim WA'. Bot akan mengirim pesan dengan jeda acak 8–20 detik."
        )
    ),
    HelpTopicItem(
        id = "lunas",
        title = "Cara Konfirmasi Pembayaran Kas Siswa (Tandai Lunas)",
        category = "Kas & Tagihan",
        summary = "Saat siswa ditandai Lunas, saldo Kas Terkonfirmasi otomatis bertambah tanpa input dua kali.",
        steps = listOf(
            "Buka tab Kas → sub-tab Tagihan, lalu ketuk nama siswa yang sudah membayar.",
            "Periksa bukti pesan WhatsApp jika siswa membayar via Transfer atau QRIS.",
            "Pilih metode pembayaran (Tunai, Transfer, atau QRIS) lalu ketuk 'Tandai Lunas'.",
            "Transaksi otomatis tercatat di daftar Transaksi dengan label 'Dari tagihan'."
        )
    ),
    HelpTopicItem(
        id = "stop_mulai",
        title = "Cara Berhenti (STOP) atau Mengaktifkan Lagi (MULAI) Pesan WA",
        category = "Bot WhatsApp",
        summary = "Siswa memiliki kendali penuh atas pesan otomatis WhatsApp dari IX-H Hub.",
        steps = listOf(
            "Untuk berhenti menerima pesan otomatis, cukup balas 'STOP' pada chat WhatsApp bot IX-H Hub.",
            "Status nomor siswa otomatis berubah menjadi 'Berhenti' dan dilewati saat pengiriman massal.",
            "Untuk mengaktifkan kembali, balas 'MULAI' di WhatsApp atau minta Admin mengaktifkan dari menu Detail Pengguna."
        )
    ),
    HelpTopicItem(
        id = "absensi",
        title = "Cara Mencatat Absensi Harian & Mengubah Status Siswa",
        category = "Absensi",
        summary = "Semua siswa terdaftar otomatis berstatus Hadir (H) sebagai bawaan agar pencatatan selesai < 30 detik.",
        steps = listOf(
            "Buka tab Absensi lalu ketuk 'Ambil Absensi Hari Ini'.",
            "Semua siswa otomatis bertanda H (Hadir). Anda cukup mengetuk I (Izin), S (Sakit), atau A (Alpha) pada siswa yang tidak hadir.",
            "Ketuk 'Simpan Absensi'. Setiap koreksi absensi akan tercatat di Log Aktivitas."
        )
    ),
    HelpTopicItem(
        id = "bot_qr",
        title = "Cara Menghubungkan Ulang Sesi Bot WhatsApp (QR / Kode Pairing)",
        category = "Admin & Server",
        summary = "Panduan khusus Admin saat sesi WangLinMD terputus atau container Pterodactyl di-restart.",
        steps = listOf(
            "Buka Beranda atau Profil → pilih 'Panel Bot WhatsApp'.",
            "Ketuk 'Tautkan Ulang QR / Kode Pairing'. Pilih metode Kode Pairing 8 karakter.",
            "Buka WhatsApp di HP Bot → Perangkat Tertaut → masukkan kode pairing tersebut."
        ),
        forAdminOnly = true
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpCenterScreen(
    activeUser: UserEntity,
    onOpenArticle: (String) -> Unit,
    onStartTourAgain: () -> Unit,
    onBack: () -> Unit
) {
    var query by remember { mutableStateOf("") }
    var showContactAdminSheet by remember { mutableStateOf(false) }

    val trimmed = query.trim()
    val isLiveSearch = trimmed.length >= 2
    val results = if (isLiveSearch) {
        HELP_TOPICS.filter {
            it.title.contains(trimmed, ignoreCase = true) ||
                it.summary.contains(trimmed, ignoreCase = true) ||
                it.category.contains(trimmed, ignoreCase = true)
        }
    } else {
        HELP_TOPICS
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .ixhOrganicScreenBackdrop()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        IxhSubScreenTopBar(title = "Pusat Bantuan", onBack = onBack)

        IxhOrganicModuleBanner(
            eyebrowTag = "Panduan Cepat IX-H",
            title = "Pusat Bantuan (FAQ)",
            subtitle = "Ketik kata kunci minimal 2 huruf untuk menyorot topik.",
            iconType = IxhSystemIconType.BERANDA
        )

        IxhStyledTextField(
            value = query,
            onValueChange = { query = it },
            placeholder = "Ketik min. 2 huruf (contoh: tagihan, stop, kas)...",
            leadingIcon = Icons.Outlined.Search,
            testTag = "help_search_input"
        )

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("tagihan", "STOP", "absensi", "QR").forEach { kw ->
                IxhSculptedFilterChip(
                    label = kw,
                    selected = query.equals(kw, ignoreCase = true),
                    onClick = { query = kw }
                )
            }
        }

        if (results.isEmpty()) {
            IxhCard(
                backgroundColor = IxhWhite,
                hasHardShadow = true,
                contentPadding = PaddingValues(20.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    IxhBlobMascot(expression = BlobExpression.SAD_ERROR, size = 64.dp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Topik \"$trimmed\" tidak ditemukan",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = IxhInk
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Coba kata kunci lain seperti 'tagihan', 'absensi', atau hubungi Admin Kelas IX-H.",
                        style = MaterialTheme.typography.bodySmall,
                        color = IxhTextSecondary,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    IxhPrimaryButton(
                        text = "Hubungi Admin Kelas IX-H",
                        onClick = { showContactAdminSheet = true }
                    )
                }
            }
        } else {
            results.forEach { topic ->
                IxhCard(
                    backgroundColor = IxhWhite,
                    leftStripeColor = if (topic.forAdminOnly) IxhInk else IxhYellow,
                    onClick = { onOpenArticle(topic.id) }
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        IxhStatusChip(text = topic.category, tone = ChipTone.YELLOW)
                        if (topic.forAdminOnly) {
                            IxhStatusChip(text = "Khusus Admin", tone = ChipTone.NEUTRAL)
                        }
                    }
                    Spacer(modifier = Modifier.height(7.dp))
                    HighlightedText(
                        fullText = topic.title,
                        highlight = if (isLiveSearch) trimmed else ""
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = topic.summary,
                        style = MaterialTheme.typography.bodySmall,
                        color = IxhTextSecondary
                    )
                }
            }
        }

        IxhSecondaryButton(
            text = "Ulangi Tur Panduan Aplikasi",
            onClick = onStartTourAgain
        )
        IxhSecondaryButton(
            text = "Masih Butuh Bantuan? Hubungi Admin",
            onClick = { showContactAdminSheet = true }
        )
    }

    if (showContactAdminSheet) {
        ModalBottomSheet(
            onDismissRequest = { showContactAdminSheet = false },
            containerColor = IxhWhite
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Hubungi Admin Kelas IX-H",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = IxhInk
                )
                Text(
                    text = "Silakan hubungi Admin Kelas IX-H melalui grup resmi kelas atau Ketua Kelas untuk bantuan akun, reset password, dan verifikasi nomor WhatsApp.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = IxhInk
                )
                IxhPrimaryButton(
                    text = "Tutup",
                    onClick = { showContactAdminSheet = false }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun HighlightedText(fullText: String, highlight: String) {
    if (highlight.isBlank() || !fullText.contains(highlight, ignoreCase = true)) {
        Text(
            text = fullText,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.ExtraBold,
            color = IxhInk
        )
        return
    }
    val annotated = buildAnnotatedString {
        var start = 0
        val lowerFull = fullText.lowercase()
        val lowerQuery = highlight.lowercase()
        while (start < fullText.length) {
            val idx = lowerFull.indexOf(lowerQuery, start)
            if (idx < 0) {
                append(fullText.substring(start))
                break
            }
            if (idx > start) {
                append(fullText.substring(start, idx))
            }
            withStyle(SpanStyle(background = IxhYellow, fontWeight = FontWeight.ExtraBold, color = IxhInk)) {
                append(fullText.substring(idx, idx + highlight.length))
            }
            start = idx + highlight.length
        }
    }
    Text(
        text = annotated,
        style = MaterialTheme.typography.titleSmall,
        fontWeight = FontWeight.ExtraBold,
        color = IxhInk
    )
}

@Composable
fun HelpArticleDetailScreen(
    topicId: String,
    onHelpfulToast: (String) -> Unit,
    onBack: () -> Unit
) {
    val topic = HELP_TOPICS.firstOrNull { it.id == topicId } ?: HELP_TOPICS.first()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .ixhOrganicScreenBackdrop()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        IxhSubScreenTopBar(title = "Artikel Panduan", onBack = onBack)

        IxhCard(
            backgroundColor = IxhYellow,
            borderColor = IxhInk,
            hasHardShadow = true
        ) {
            IxhStatusChip(text = topic.category, tone = ChipTone.NEUTRAL)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = topic.title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.ExtraBold,
                color = IxhInk
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = topic.summary,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.SemiBold,
                color = IxhInk.copy(alpha = 0.85f)
            )
        }

        IxhCard(backgroundColor = IxhWhite, hasHardShadow = true) {
            Text(
                text = "LANGKAH DEMI LANGKAH",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.ExtraBold,
                color = IxhInk
            )
            Spacer(modifier = Modifier.height(10.dp))
            topic.steps.forEachIndexed { idx, stepText ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Box(
                        modifier = Modifier
                            .size(26.dp)
                            .neubrutalShadow(enabled = true, cornerRadius = 8.dp, offsetY = 1.8.dp, offsetX = 1.4.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(IxhYellow)
                            .border(1.8.dp, IxhInk, RoundedCornerShape(8.dp)),
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
                    Text(
                        text = stepText,
                        style = MaterialTheme.typography.bodyMedium,
                        color = IxhInk,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        IxhCard(backgroundColor = IxhWhite) {
            Text(
                text = "Apakah panduan ini membantu?",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.ExtraBold,
                color = IxhInk
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                IxhPrimaryButton(
                    text = "Ya, Membantu",
                    onClick = { onHelpfulToast("Terima kasih atas masukannya!") },
                    modifier = Modifier.weight(1f)
                )
                IxhSecondaryButton(
                    text = "Kurang Jelas",
                    onClick = { onHelpfulToast("Kami akan memperbarui artikel ini.") },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

// =========================================================================
// STORYBOARD 32, 32A & 32D: TUR PANDUAN COACHMARK
// =========================================================================

@Composable
fun CoachmarkTourOverlay(
    tourState: TourUiState,
    activeUser: UserEntity,
    onStartTour: () -> Unit,
    onNextStep: (Int) -> Unit,
    onPrevStep: () -> Unit,
    onDismissTour: () -> Unit,
    onSwitchTabForStep: (MainNavTab) -> Unit
) {
    val isSiswa = activeUser.role == UserRole.SISWA.name
    val steps = if (isSiswa) {
        listOf(
            Triple("1. Ringkasan Kelas & Pribadi", "Di Beranda, kamu bisa langsung melihat kehadiranmu dan status tagihan kas minggu ini.", MainNavTab.BERANDA),
            Triple("2. Riwayat Kehadiranku", "Tab Absensi menampilkan kalender kehadiranmu bulan ini dan target aman ≥90%.", MainNavTab.ABSENSI),
            Triple("3. Kartu Tagihan Kas Saya", "Tab Kas memudahkanmu mengecek rincian kas Rp6.000 dan mengirim bukti bayar via WhatsApp.", MainNavTab.KAS),
            Triple("4. Jadwal 6 Hari & Pengingat Malam", "Cek jadwal Senin–Sabtu dan nyalakan pengingat pukul 20.00 WIB agar tidak lupa buku.", MainNavTab.JADWAL),
            Triple("5. Tugas & Arti Warna", "Garis merah berarti besok dikumpulkan, kuning 2–3 hari lagi, biru masih lama. Tandai selesai kapan saja!", MainNavTab.TUGAS)
        )
    } else {
        listOf(
            Triple("1. Kartu Ringkasan Beranda", "Pantau kehadiran siswa, kas terkonfirmasi, jadwal hari ini & tugas mendesak dalam satu layar.", MainNavTab.BERANDA),
            Triple("2. Ambil Absensi < 30 Detik", "Semua siswa otomatis Hadir (H). Cukup ketuk I, S, atau A untuk siswa yang berhalangan hadir.", MainNavTab.ABSENSI),
            Triple("3. Tagihan Kas & WhatsApp Otomatis", "Saat siswa ditandai Lunas di tab Tagihan, saldo Kas otomatis bertambah tanpa input ganda.", MainNavTab.KAS),
            Triple("4. Jadwal Senin–Sabtu & Kalender", "Kelola jadwal 6 hari sekolah beserta pengingat persiapan malam pukul 20.00 WIB.", MainNavTab.JADWAL),
            Triple("5. Tugas & Lampiran PDF", "Tambahkan tugas kelas dan pantau indikator warna tenggat waktu.", MainNavTab.TUGAS),
            Triple("6. Panel Bot WA, Backup & Audit Log", "Akses cepat di Beranda untuk memantau sesi WangLinMD, Backup/Restore database, dan Audit Log.", MainNavTab.BERANDA)
        )
    }

    if (tourState.showOfferDialog) {
        AlertDialog(
            onDismissRequest = onDismissTour,
            containerColor = IxhWhite,
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IxhBlobMascot(expression = BlobExpression.SMILE_HAPPY, size = 48.dp)
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Kenalan Singkat 30 Detik?",
                        fontWeight = FontWeight.ExtraBold,
                        color = IxhInk
                    )
                }
            },
            text = {
                Text(
                    text = "Ikuti tur singkat (${steps.size} langkah) untuk melihat cara kerja IX-H Hub bagi peran ${activeUser.role}.",
                    color = IxhInk
                )
            },
            confirmButton = {
                TextButton(onClick = onStartTour) {
                    Text("Mulai Tur →", color = IxhInk, fontWeight = FontWeight.ExtraBold)
                }
            },
            dismissButton = {
                TextButton(onClick = onDismissTour) {
                    Text("Lewati", color = IxhTextSecondary)
                }
            }
        )
    }

    if (tourState.isActive) {
        val currentIdx = (tourState.currentStep - 1).coerceIn(0, steps.lastIndex)
        val (stepTitle, stepDesc, _) = steps[currentIdx]

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.62f))
                .clickable(enabled = false) {}
                .padding(20.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            IxhCard(
                backgroundColor = IxhYellowLight,
                borderColor = IxhInk,
                hasHardShadow = true,
                contentPadding = PaddingValues(16.dp),
                modifier = Modifier.padding(bottom = 72.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IxhStatusChip(
                        text = "LANGKAH ${tourState.currentStep} DARI ${steps.size} • ${activeUser.role}",
                        tone = ChipTone.OUTLINE_YELLOW
                    )
                    Text(
                        text = "Lewati Tur ✕",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.ExtraBold,
                        color = IxhTextSecondary,
                        modifier = Modifier.clickable(onClick = onDismissTour)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stepTitle,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = IxhInk
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stepDesc,
                    style = MaterialTheme.typography.bodyMedium,
                    color = IxhInk
                )
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (tourState.currentStep > 1) {
                        IxhSecondaryButton(
                            text = "← Kembali",
                            onClick = {
                                val prevTab = steps[(currentIdx - 1).coerceAtLeast(0)].third
                                onSwitchTabForStep(prevTab)
                                onPrevStep()
                            },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    IxhPrimaryButton(
                        text = if (tourState.currentStep == steps.size) "Selesai Tur ✓" else "Lanjut →",
                        onClick = {
                            if (currentIdx + 1 <= steps.lastIndex) {
                                onSwitchTabForStep(steps[currentIdx + 1].third)
                            }
                            onNextStep(steps.size)
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }

    if (tourState.showFinishedScreen) {
        AlertDialog(
            onDismissRequest = onDismissTour,
            containerColor = IxhWhite,
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IxhBlobMascot(expression = BlobExpression.SMILE_HAPPY, size = 52.dp)
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Kamu Siap Pakai IX-H Hub!",
                        fontWeight = FontWeight.ExtraBold,
                        color = IxhInk
                    )
                }
            },
            text = {
                Text(
                    text = "Tur selesai. Kamu bisa mengulangi tur ini kapan saja melalui menu Profil → Pusat Bantuan.",
                    color = IxhInk
                )
            },
            confirmButton = {
                TextButton(onClick = onDismissTour) {
                    Text("Mulai Gunakan Aplikasi", color = IxhInk, fontWeight = FontWeight.ExtraBold)
                }
            }
        )
    }
}
