package com.example.ui.screens

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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.AddAPhoto
import androidx.compose.material.icons.outlined.ArrowDownward
import androidx.compose.material.icons.outlined.ArrowUpward
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material.icons.outlined.NotificationsActive
import androidx.compose.material.icons.outlined.ReceiptLong
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material.icons.outlined.WarningAmber
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.BillPaymentStatus
import com.example.data.local.CashTransactionEntity
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
import com.example.ui.components.IxhPrimaryButton
import com.example.ui.components.IxhSecondaryButton
import com.example.ui.components.IxhSegmentedTabs
import com.example.ui.components.IxhStatusChip
import com.example.ui.components.IxhSubScreenTopBar
import com.example.ui.components.IxhTopBrandHeader
import com.example.ui.components.IxhWaBubblePreview
import com.example.ui.theme.IxhAmber
import com.example.ui.theme.IxhAmberBg
import com.example.ui.theme.IxhBlue
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
import java.text.NumberFormat
import java.util.Locale

private fun formatIdr(amount: Int): String {
    val nf = NumberFormat.getNumberInstance(Locale("id", "ID"))
    return "Rp${nf.format(amount)}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CashMainTabScreen(
    activeUser: UserEntity,
    users: List<UserEntity>,
    transactions: List<CashTransactionEntity>,
    unreadNotificationCount: Int,
    selectedTabIndex: Int,
    onSelectTabIndex: (Int) -> Unit,
    onNavigate: (AppScreenRoute) -> Unit,
    onOpenTransactionDetail: (CashTransactionEntity) -> Unit,
    onOpenBillVerifyDetail: (UserEntity) -> Unit,
    onOpenRoleSwitcher: () -> Unit
) {
    val isSiswa = activeUser.role == UserRole.SISWA.name
    var showEmptyBillPreview by remember { mutableStateOf(false) }
    var showStatusInfoSheet by remember { mutableStateOf(false) }
    var billFilterIndex by remember { mutableIntStateOf(0) } // 0=Semua, 1=Belum, 2=Lunas, 3=Tunggakan

    val paidCount = users.count { it.billStatus == BillPaymentStatus.LUNAS.name }
    val unpaidCount = users.count { it.billStatus == BillPaymentStatus.BELUM.name }
    val arrearsCount = users.count { it.billStatus == BillPaymentStatus.TUNGGAKAN.name }
    val totalStudents = users.size
    val totalIncome = transactions.filter { it.isIncome }.sumOf { it.amount }
    val totalExpense = transactions.filter { !it.isIncome }.sumOf { it.amount }
    val netBalance = totalIncome - totalExpense
    val incomeCount = transactions.count { it.isIncome }
    val expenseCount = transactions.count { !it.isIncome }

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
                onRoleBadgeClick = onOpenRoleSwitcher
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Kas Kelas IX-H",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = IxhInk
                )
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    IxhStatusChip(
                        text = if (isSiswa) "Tagihan Saya" else "Riwayat & Ekspor",
                        tone = ChipTone.OUTLINE_YELLOW,
                        modifier = Modifier.clickable {
                            if (isSiswa) {
                                onNavigate(AppScreenRoute.STUDENT_MY_BILLS)
                            } else {
                                onNavigate(AppScreenRoute.CASH_HISTORY_EXPORT)
                            }
                        }
                    )
                }
            }

            IxhSegmentedTabs(
                tabs = listOf("Transaksi", "Tagihan"),
                selectedIndex = selectedTabIndex,
                onTabSelected = onSelectTabIndex,
                badgeMap = mapOf(1 to (unpaidCount + arrearsCount))
            )

            if (selectedTabIndex == 0) {
                IxhCard(
                    backgroundColor = IxhYellow,
                    hasHardShadow = true,
                    contentPadding = PaddingValues(14.dp)
                ) {
                    Text(
                        text = "SALDO KAS TERKONFIRMASI",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = IxhInk
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = formatIdr(netBalance),
                        fontSize = 26.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = IxhInk
                    )
                    Text(
                        text = "Dihitung langsung dari transaksi masuk & keluar tercatat",
                        style = MaterialTheme.typography.labelSmall,
                        color = IxhInk.copy(alpha = 0.8f)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(10.dp))
                                .background(IxhWhite)
                                .border(1.5.dp, IxhInk, RoundedCornerShape(10.dp))
                                .padding(8.dp)
                        ) {
                            Text(
                                text = "Total Pemasukan",
                                style = MaterialTheme.typography.labelSmall,
                                color = IxhTextSecondary
                            )
                            Text(
                                text = formatIdr(totalIncome),
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.ExtraBold,
                                color = IxhGreen
                            )
                            Text(
                                text = "$incomeCount transaksi masuk",
                                fontSize = 9.5.sp,
                                color = IxhTextSecondary
                            )
                        }
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(10.dp))
                                .background(IxhWhite)
                                .border(1.5.dp, IxhInk, RoundedCornerShape(10.dp))
                                .padding(8.dp)
                        ) {
                            Text(
                                text = "Total Pengeluaran",
                                style = MaterialTheme.typography.labelSmall,
                                color = IxhTextSecondary
                            )
                            Text(
                                text = formatIdr(totalExpense),
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.ExtraBold,
                                color = IxhRed
                            )
                            Text(
                                text = "$expenseCount transaksi keluar",
                                fontSize = 9.5.sp,
                                color = IxhTextSecondary
                            )
                        }
                    }
                }

                // Active Bill quick summary card
                IxhCard(
                    backgroundColor = IxhWhite,
                    onClick = { onSelectTabIndex(1) }
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Tagihan Aktif • Kas Minggu ke-1 Nov",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = IxhInk
                            )
                            Text(
                                text = "$paidCount/$totalStudents lunas • $unpaidCount belum • $arrearsCount tunggakan",
                                style = MaterialTheme.typography.bodySmall,
                                color = IxhTextSecondary
                            )
                        }
                        Text(
                            text = "Kelola →",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = IxhInk
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Transaksi Terbaru",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = IxhInk
                    )
                    Text(
                        text = "Semua →",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = IxhTextSecondary,
                        modifier = Modifier.clickable {
                            onNavigate(AppScreenRoute.CASH_HISTORY_EXPORT)
                        }
                    )
                }

                if (transactions.isEmpty()) {
                    IxhEmptyStateCard(
                        title = "Belum ada transaksi kas.",
                        subtitle = "Pemasukan dan pengeluaran kas kelas IX-H yang dicatat Bendahara atau Admin akan tampil di sini.",
                        mascotState = BlobMascotState.IDLE,
                        actionText = if (!isSiswa) "+ Catat Transaksi" else null,
                        onActionClick = if (!isSiswa) ({ onNavigate(AppScreenRoute.CASH_ADD_TRANSACTION) }) else null,
                        testTag = "kas_empty_transactions"
                    )
                } else {
                    transactions.forEach { tx ->
                        CashTransactionRowCard(
                            tx = tx,
                            onClick = { onOpenTransactionDetail(tx) }
                        )
                    }
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable { showStatusInfoSheet = true }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = "Info Status Tagihan",
                            tint = IxhInk,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Arti Status Tagihan",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = IxhInk
                        )
                    }
                    if (users.isNotEmpty()) {
                        Text(
                            text = if (showEmptyBillPreview) "Lihat Periode Aktif" else "Pratinjau Kosong",
                            style = MaterialTheme.typography.labelSmall,
                            color = IxhTextSecondary,
                            modifier = Modifier.clickable { showEmptyBillPreview = !showEmptyBillPreview }
                        )
                    }
                }

                if (showEmptyBillPreview || users.isEmpty()) {
                    IxhEmptyStateCard(
                        title = "Belum ada tagihan kas.",
                        subtitle = "Buat periode tagihan kas baru atau tambahkan data siswa agar status tagihan tercatat per siswa.",
                        mascotState = BlobMascotState.IDLE,
                        actionText = if (!isSiswa) "+ Buat Tagihan Pertama" else null,
                        onActionClick = if (!isSiswa) ({ onNavigate(AppScreenRoute.BILLS_CREATE) }) else null,
                        testTag = "kas_empty_bills"
                    )
                } else {
                    val paidRatio = if (totalStudents > 0) paidCount.toFloat() / totalStudents.toFloat() else 0f
                    val paidPct = if (totalStudents > 0) (paidCount * 100) / totalStudents else 0
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
                            Text(
                                text = "PERIODE AKTIF",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = IxhInk
                            )
                            IxhStatusChip(
                                text = "Kas Mingguan",
                                tone = ChipTone.YELLOW
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Tagihan Kas Kelas IX-H",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.ExtraBold,
                            color = IxhInk
                        )
                        Text(
                            text = "Rp6.000 / siswa • Target ${formatIdr(totalStudents * 6000)}",
                            style = MaterialTheme.typography.bodySmall,
                            color = IxhTextSecondary
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        LinearProgressIndicator(
                            progress = { paidRatio },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(RoundedCornerShape(6.dp)),
                            color = IxhGreen,
                            trackColor = IxhWhite
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Terkumpul ${formatIdr(paidCount * 6000)}",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = IxhInk
                            )
                            Text(
                                text = "$paidPct% ($paidCount/$totalStudents)",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = IxhGreenText
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            IxhStatusChip(text = "Lunas $paidCount", tone = ChipTone.GREEN)
                            IxhStatusChip(text = "Belum $unpaidCount", tone = ChipTone.YELLOW)
                            IxhStatusChip(text = "Tunggakan $arrearsCount", tone = ChipTone.RED)
                        }

                        if (!isSiswa) {
                            Spacer(modifier = Modifier.height(10.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                IxhPrimaryButton(
                                    text = "Ingatkan (${unpaidCount + arrearsCount})",
                                    onClick = { onNavigate(AppScreenRoute.BILLS_REMINDER_SEND) },
                                    modifier = Modifier.weight(1f),
                                    testTag = "remind_unpaid_button"
                                )
                                IxhSecondaryButton(
                                    text = "+ Tagihan",
                                    onClick = { onNavigate(AppScreenRoute.BILLS_CREATE) },
                                    modifier = Modifier.weight(1f),
                                    testTag = "create_bill_button"
                                )
                            }
                        } else {
                            Spacer(modifier = Modifier.height(10.dp))
                            IxhPrimaryButton(
                                text = "Lihat Kartu Tagihan Kas Saya",
                                onClick = { onNavigate(AppScreenRoute.STUDENT_MY_BILLS) }
                            )
                        }
                    }

                    // Filter chips for students list
                    val filterLabels = listOf(
                        "Semua ($totalStudents)",
                        "Belum ($unpaidCount)",
                        "Lunas ($paidCount)",
                        "Tunggakan ($arrearsCount)"
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        filterLabels.forEachIndexed { idx, label ->
                            val selected = billFilterIndex == idx
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(if (selected) IxhInk else IxhSurfaceMuted)
                                    .clickable { billFilterIndex = idx }
                                    .padding(horizontal = 10.dp, vertical = 5.dp)
                            ) {
                                Text(
                                    text = label,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (selected) IxhWhite else IxhTextSecondary
                                )
                            }
                        }
                    }

                    val filteredStudents = users.filter { u ->
                        when (billFilterIndex) {
                            1 -> u.billStatus == BillPaymentStatus.BELUM.name
                            2 -> u.billStatus == BillPaymentStatus.LUNAS.name
                            3 -> u.billStatus == BillPaymentStatus.TUNGGAKAN.name
                            else -> true
                        }
                    }.sortedWith(
                        compareBy<UserEntity> {
                            when (it.billStatus) {
                                BillPaymentStatus.TUNGGAKAN.name -> 0
                                BillPaymentStatus.BELUM.name -> 1
                                else -> 2
                            }
                        }.thenBy { it.absenNo }
                    )

                    filteredStudents.forEach { student ->
                        StudentBillRowItem(
                            student = student,
                            canVerify = !isSiswa,
                            onClick = {
                                if (!isSiswa) {
                                    onOpenBillVerifyDetail(student)
                                } else if (student.username == activeUser.username) {
                                    onNavigate(AppScreenRoute.STUDENT_MY_BILLS)
                                }
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(80.dp))
        }

        // FAB + Catat Transaksi / Buat Tagihan for Admin/Pengurus
        if (!isSiswa) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 18.dp, bottom = 18.dp)
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(IxhYellow)
                    .border(2.dp, IxhInk, CircleShape)
                    .clickable {
                        if (selectedTabIndex == 0) {
                            onNavigate(AppScreenRoute.CASH_ADD_TRANSACTION)
                        } else {
                            onNavigate(AppScreenRoute.BILLS_CREATE)
                        }
                    }
                    .testTag("kas_fab_add"),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Add,
                    contentDescription = "Tambah Transaksi atau Tagihan",
                    tint = IxhInk,
                    modifier = Modifier.size(26.dp)
                )
            }
        }
    }

    // Bottom Sheet: Arti Status Tagihan (Storyboard 32B)
    if (showStatusInfoSheet) {
        ModalBottomSheet(
            onDismissRequest = { showStatusInfoSheet = false },
            containerColor = IxhWhite
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Arti Status Tagihan",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = IxhInk
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IxhStatusChip(text = "Lunas", tone = ChipTone.GREEN)
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Sudah dibayar & dikonfirmasi bendahara. Otomatis masuk ke Saldo Kas.",
                        style = MaterialTheme.typography.bodySmall,
                        color = IxhInk
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IxhStatusChip(text = "Belum", tone = ChipTone.YELLOW)
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Tagihan periode ini masih berjalan (sebelum lewat jatuh tempo).",
                        style = MaterialTheme.typography.bodySmall,
                        color = IxhInk
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IxhStatusChip(text = "Tunggakan", tone = ChipTone.RED)
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Melewati batas jatuh tempo atau ada sisa tagihan minggu sebelumnya.",
                        style = MaterialTheme.typography.bodySmall,
                        color = IxhInk
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                IxhPrimaryButton(
                    text = "Mengerti",
                    onClick = { showStatusInfoSheet = false }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun CashTransactionRowCard(
    tx: CashTransactionEntity,
    onClick: () -> Unit
) {
    IxhCard(
        backgroundColor = IxhWhite,
        onClick = onClick,
        contentPadding = PaddingValues(11.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IxhCircleIcon(
                icon = if (tx.isIncome) Icons.Outlined.ArrowDownward else Icons.Outlined.ArrowUpward,
                bgColor = if (tx.isIncome) IxhGreenBg else IxhRedBg,
                iconTint = if (tx.isIncome) IxhGreen else IxhRed,
                size = 34.dp
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = tx.title,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = IxhInk
                    )
                    if (tx.isFromBill) {
                        IxhStatusChip(text = "Dari tagihan", tone = ChipTone.BLUE)
                    }
                }
                Text(
                    text = tx.dateTimeLabel,
                    style = MaterialTheme.typography.bodySmall,
                    color = IxhTextSecondary
                )
            }
            Text(
                text = (if (tx.isIncome) "+" else "-") + formatIdr(tx.amount),
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.ExtraBold,
                color = if (tx.isIncome) IxhGreen else IxhRed
            )
        }
    }
}

@Composable
private fun StudentBillRowItem(
    student: UserEntity,
    canVerify: Boolean,
    onClick: () -> Unit
) {
    val (chipText, chipTone) = when (student.billStatus) {
        BillPaymentStatus.LUNAS.name -> "Lunas" to ChipTone.GREEN
        BillPaymentStatus.TUNGGAKAN.name -> "Tunggakan" to ChipTone.RED
        else -> "Belum" to ChipTone.YELLOW
    }
    IxhCard(
        backgroundColor = IxhWhite,
        onClick = onClick,
        contentPadding = PaddingValues(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape)
                    .background(IxhYellowLight),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "%02d".format(student.absenNo),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = IxhInk
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = student.fullName,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = IxhInk
                )
                val subtitle = when (student.billStatus) {
                    BillPaymentStatus.LUNAS.name -> "${formatIdr(student.billAmount)} • Dibayar ${student.paidDate.ifEmpty { "3 Nov" }} (${student.paidMethod.ifEmpty { "Tunai" }})"
                    BillPaymentStatus.TUNGGAKAN.name -> "${formatIdr(student.billAmount)} (2 minggu) • WA Terkirim"
                    else -> "${formatIdr(student.billAmount)} • WA ${student.deliveryStatus}"
                }
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = if (student.billStatus == BillPaymentStatus.TUNGGAKAN.name) IxhRed else IxhTextSecondary
                )
            }
            IxhStatusChip(text = chipText, tone = chipTone)
            if (canVerify) {
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                    contentDescription = null,
                    tint = IxhTextSecondary,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

@Composable
fun AddCashTransactionScreen(
    actorName: String,
    onSave: (title: String, amount: Int, isIncome: Boolean, dateLabel: String, proofNote: String) -> Unit,
    onBack: () -> Unit
) {
    var typeIndex by remember { mutableIntStateOf(0) }
    var amountText by remember { mutableStateOf("") }
    var titleText by remember { mutableStateOf("") }
    var dateText by remember { mutableStateOf("Hari ini") }
    var hasProofAttached by remember { mutableStateOf(true) }
    var errorMsg by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        IxhSubScreenTopBar(title = "Catat Transaksi", onBack = onBack)

        IxhAlertBanner(
            text = "Pembayaran kas siswa dicatat otomatis dari tab Tagihan.",
            tone = ChipTone.BLUE
        )

        Text(
            text = "JENIS TRANSAKSI",
            style = MaterialTheme.typography.labelSmall,
            color = IxhTextSecondary
        )
        IxhSegmentedTabs(
            tabs = listOf("Pemasukan Lain", "Pengeluaran"),
            selectedIndex = typeIndex,
            onTabSelected = { typeIndex = it }
        )

        OutlinedTextField(
            value = amountText,
            onValueChange = { amountText = it.filter { ch -> ch.isDigit() } },
            label = { Text("Nominal (Rp)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = IxhWhite,
                unfocusedContainerColor = IxhWhite
            )
        )

        OutlinedTextField(
            value = titleText,
            onValueChange = { titleText = it },
            label = { Text("Keterangan") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = IxhWhite,
                unfocusedContainerColor = IxhWhite
            )
        )

        OutlinedTextField(
            value = dateText,
            onValueChange = { dateText = it },
            label = { Text("Tanggal") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = IxhWhite,
                unfocusedContainerColor = IxhWhite
            )
        )

        Text(
            text = if (typeIndex == 1) "BUKTI NOTA (WAJIB UNTUK PENGELUARAN)" else "BUKTI TRANSAKSI (OPSIONAL)",
            style = MaterialTheme.typography.labelSmall,
            color = IxhTextSecondary
        )
        IxhCard(
            backgroundColor = if (hasProofAttached) IxhGreenBg else IxhSurfaceMuted,
            borderColor = if (hasProofAttached) IxhGreen else IxhBorder,
            onClick = { hasProofAttached = !hasProofAttached }
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.AddAPhoto,
                    contentDescription = null,
                    tint = if (hasProofAttached) IxhGreenText else IxhTextSecondary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (hasProofAttached) "Nota_Spidol_04Nov.jpg terlampir (Ketuk untuk lepas)" else "+ Unggah Foto Nota / Bukti",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = if (hasProofAttached) IxhGreenText else IxhTextSecondary
                )
            }
        }

        if (errorMsg != null) {
            IxhAlertBanner(text = errorMsg!!, tone = ChipTone.RED)
        }

        Spacer(modifier = Modifier.height(6.dp))
        IxhPrimaryButton(
            text = "Simpan Transaksi",
            onClick = {
                val amt = amountText.toIntOrNull() ?: 0
                if (amt <= 0 || titleText.isBlank()) {
                    errorMsg = "Nominal dan keterangan wajib diisi."
                } else if (typeIndex == 1 && !hasProofAttached) {
                    errorMsg = "Foto bukti nota wajib dilampirkan untuk transaksi Pengeluaran."
                } else {
                    onSave(
                        titleText.trim(),
                        amt,
                        typeIndex == 0,
                        "$dateText • Baru saja",
                        if (hasProofAttached) "Bukti nota terlampir" else "Dicatat manual"
                    )
                }
            },
            testTag = "save_transaction_button"
        )
    }
}

@Composable
fun CashTransactionDetailScreen(
    tx: CashTransactionEntity?,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        IxhSubScreenTopBar(title = "Detail Transaksi", onBack = onBack)

        if (tx == null) {
            IxhEmptyStateCard(
                title = "Transaksi tidak ditemukan.",
                subtitle = "Pilih salah satu transaksi kas dari daftar untuk melihat rinciannya.",
                actionText = "Kembali",
                onActionClick = onBack
            )
            return@Column
        }

        val item = tx
        IxhCard(
            backgroundColor = if (item.isIncome) IxhGreenBg else IxhRedBg,
            borderColor = if (item.isIncome) IxhGreen else IxhRed,
            contentPadding = PaddingValues(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    IxhStatusChip(
                        text = if (item.isIncome) "Pemasukan" else "Pengeluaran",
                        tone = if (item.isIncome) ChipTone.GREEN else ChipTone.RED
                    )
                    if (item.isFromBill) {
                        IxhStatusChip(text = "Dari tagihan", tone = ChipTone.BLUE)
                    }
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = (if (item.isIncome) "+" else "-") + formatIdr(item.amount),
                    fontSize = 26.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = if (item.isIncome) IxhGreenText else IxhRed
                )
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = IxhInk
                )
            }
        }

        IxhCard(backgroundColor = IxhWhite) {
            DetailKeyValRow("Periode", item.periodName)
            DetailKeyValRow("Siswa / Pihak", item.studentName.ifEmpty { "Kas Kelas IX-H" })
            DetailKeyValRow("Tanggal", item.dateTimeLabel)
            DetailKeyValRow("Metode", item.paymentMethod)
            DetailKeyValRow("Dikonfirmasi oleh", item.confirmedBy.ifEmpty { "Bendahara / Admin" })
        }

        IxhCard(backgroundColor = IxhWhite) {
            Text(
                text = "CATATAN & BUKTI TRANSAKSI",
                style = MaterialTheme.typography.labelSmall,
                color = IxhTextSecondary
            )
            Spacer(modifier = Modifier.height(6.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(82.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(IxhSurfaceMuted),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Outlined.ReceiptLong,
                        contentDescription = null,
                        tint = IxhInk
                    )
                    Text(
                        text = item.proofNote.ifEmpty { "Catatan transaksi tersimpan di buku kas" },
                        style = MaterialTheme.typography.labelSmall,
                        color = IxhTextSecondary
                    )
                }
            }
        }
    }
}

@Composable
private fun DetailKeyValRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.bodySmall, color = IxhTextSecondary)
        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Bold,
            color = IxhInk
        )
    }
}

@Composable
fun CashHistoryExportScreen(
    transactions: List<CashTransactionEntity>,
    onOpenTransactionDetail: (CashTransactionEntity) -> Unit,
    onExportToast: (String) -> Unit,
    onBack: () -> Unit
) {
    var filterIdx by remember { mutableIntStateOf(0) } // 0=Semua, 1=Masuk, 2=Keluar, 3=Dari tagihan

    val filtered = transactions.filter {
        when (filterIdx) {
            1 -> it.isIncome
            2 -> !it.isIncome
            3 -> it.isFromBill
            else -> true
        }
    }

    val totalIncome = transactions.filter { it.isIncome }.sumOf { it.amount }
    val totalExpense = transactions.filter { !it.isIncome }.sumOf { it.amount }
    val netBalance = totalIncome - totalExpense

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        IxhSubScreenTopBar(title = "Riwayat & Ekspor Kas", onBack = onBack)

        IxhCard(backgroundColor = IxhWhite) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Rekapitulasi Kas",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = IxhInk
                )
                IxhStatusChip(
                    text = "Saldo ${formatIdr(netBalance)}",
                    tone = if (netBalance >= 0) ChipTone.GREEN else ChipTone.RED
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Masuk: ${formatIdr(totalIncome)}",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    color = IxhGreen
                )
                Text(
                    text = "Keluar: ${formatIdr(totalExpense)}",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    color = IxhRed
                )
            }
        }

        val filters = listOf("Semua", "Masuk", "Keluar", "Dari tagihan")
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            filters.forEachIndexed { idx, label ->
                val sel = filterIdx == idx
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
                title = "Belum ada transaksi kas.",
                subtitle = "Transaksi kas yang sudah dicatat akan muncul di daftar riwayat ini."
            )
        } else {
            filtered.forEach { tx ->
                CashTransactionRowCard(tx = tx, onClick = { onOpenTransactionDetail(tx) })
            }
        }

        Spacer(modifier = Modifier.height(6.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            IxhSecondaryButton(
                text = "Ekspor PDF",
                icon = Icons.Outlined.Download,
                onClick = { onExportToast("Laporan Kas Kelas IX-H (PDF) berhasil disiapkan") },
                modifier = Modifier.weight(1f)
            )
            IxhPrimaryButton(
                text = "Ekspor Excel",
                icon = Icons.Outlined.Download,
                onClick = { onExportToast("Laporan Kas Kelas IX-H (XLSX) berhasil disiapkan") },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun CreateBillScreen(
    defaultAmount: Int = 6000,
    onContinueToPreview: () -> Unit,
    onSaveDraftOnly: () -> Unit,
    onBack: () -> Unit
) {
    var billName by remember { mutableStateOf("Kas Mingguan Kelas IX-H") }
    var amountText by remember { mutableStateOf(defaultAmount.toString()) }
    var dueDateText by remember { mutableStateOf("Jumat Minggu Ini") }
    var targetOption by remember { mutableIntStateOf(0) }

    val amountInt = amountText.toIntOrNull() ?: defaultAmount

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        IxhSubScreenTopBar(title = "Buat Tagihan Kas", onBack = onBack)

        OutlinedTextField(
            value = billName,
            onValueChange = { billName = it },
            label = { Text("Nama Periode Tagihan") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = IxhWhite,
                unfocusedContainerColor = IxhWhite
            )
        )

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                value = amountText,
                onValueChange = { amountText = it.filter { c -> c.isDigit() } },
                label = { Text("Nominal / Siswa (Rp)") },
                modifier = Modifier.weight(1f),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = IxhWhite,
                    unfocusedContainerColor = IxhWhite
                )
            )
            OutlinedTextField(
                value = dueDateText,
                onValueChange = { dueDateText = it },
                label = { Text("Jatuh Tempo") },
                modifier = Modifier.weight(1f),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = IxhWhite,
                    unfocusedContainerColor = IxhWhite
                )
            )
        }

        Text(
            text = "TARGET SISWA",
            style = MaterialTheme.typography.labelSmall,
            color = IxhTextSecondary
        )
        IxhSegmentedTabs(
            tabs = listOf("Semua Anggota Aktif", "Pilih Tertentu"),
            selectedIndex = targetOption,
            onTabSelected = { targetOption = it }
        )

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
                        text = "Nominal Iuran per Siswa",
                        style = MaterialTheme.typography.labelSmall,
                        color = IxhTextSecondary
                    )
                    Text(
                        text = "Otomatis diterapkan ke anggota terdaftar",
                        style = MaterialTheme.typography.bodySmall,
                        color = IxhInk
                    )
                }
                Text(
                    text = formatIdr(amountInt),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.ExtraBold,
                    color = IxhInk
                )
            }
        }

        IxhPrimaryButton(
            text = "Lanjut ke Pratinjau WhatsApp →",
            onClick = onContinueToPreview
        )
        IxhSecondaryButton(
            text = "Simpan Tanpa Kirim WA",
            onClick = onSaveDraftOnly
        )
    }
}

@Composable
fun BillPreviewScheduleScreen(
    onSendAndCreate: () -> Unit,
    onNavigateProblematicNumbers: () -> Unit,
    onBack: () -> Unit
) {
    var scheduleMode by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        IxhSubScreenTopBar(title = "Pratinjau & Jadwal WA", onBack = onBack)

        Text(
            text = "PRATINJAU PESAN WHATSAPP PERSONAL",
            style = MaterialTheme.typography.labelSmall,
            color = IxhTextSecondary
        )

        IxhWaBubblePreview(
            senderTitle = "IX-H Hub • Tagihan Kas",
            messageBody = "Halo *{nama}*,\nTagihan *Kas Mingguan* sebesar *Rp6.000* jatuh tempo minggu ini.\n\nBayar ke Bendahara atau balas pesan ini dengan foto bukti transfer.\n\nBalas STOP untuk berhenti menerima pesan.",
            timeLabel = "08.00"
        )

        IxhCard(backgroundColor = IxhWhite) {
            Text(
                text = "VALIDASI NOMOR PENERIMA",
                style = MaterialTheme.typography.labelSmall,
                color = IxhTextSecondary
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Pesan hanya dikirim ke anggota dengan nomor WhatsApp berstatus Valid dan tidak memilih STOP.",
                style = MaterialTheme.typography.bodySmall,
                color = IxhInk
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Cek & Perbaiki Nomor Bermasalah →",
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold,
                color = IxhBlue,
                modifier = Modifier.clickable(onClick = onNavigateProblematicNumbers)
            )
        }

        Text(
            text = "WAKTU PENGIRIMAN",
            style = MaterialTheme.typography.labelSmall,
            color = IxhTextSecondary
        )
        IxhSegmentedTabs(
            tabs = listOf("Kirim Sekarang", "Jadwalkan Otomatis"),
            selectedIndex = scheduleMode,
            onTabSelected = { scheduleMode = it }
        )

        IxhAlertBanner(
            text = "Anti-Blokir: Dikirim bertahap dengan jeda acak 8–20 detik antar siswa saat VPS Bot terhubung.",
            tone = ChipTone.YELLOW
        )

        IxhPrimaryButton(
            text = "Buat Tagihan & Catat Antrean WA",
            onClick = onSendAndCreate
        )
    }
}

@Composable
fun BillDeliveryStatusScreen(
    users: List<UserEntity>,
    onRetryFailed: () -> Unit,
    onNavigateProblematicNumbers: () -> Unit,
    onBack: () -> Unit
) {
    val validCount = users.count { it.waStatus == WaNumberStatus.VALID.name }
    val skippedCount = users.count { it.waStatus != WaNumberStatus.VALID.name }
    val failedCount = users.count { it.deliveryStatus == "Gagal" }
    val sentCount = (validCount - failedCount).coerceAtLeast(0)
    val progress = if (users.isNotEmpty()) sentCount.toFloat() / users.size.toFloat() else 0f

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        IxhSubScreenTopBar(title = "Status Pengiriman WA", onBack = onBack)

        IxhCard(
            backgroundColor = IxhYellowLight,
            borderColor = IxhInk
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Rekap Pengiriman Tagihan IX-H",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = IxhInk
                )
                IxhStatusChip(
                    text = "${users.size} Anggota",
                    tone = ChipTone.OUTLINE_YELLOW
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(6.dp)),
                color = IxhGreen,
                trackColor = IxhWhite
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                IxhStatusChip(text = "Siap/Terkirim $sentCount", tone = ChipTone.GREEN)
                IxhStatusChip(text = "Gagal $failedCount", tone = ChipTone.RED)
                IxhStatusChip(text = "Belum Valid $skippedCount", tone = ChipTone.NEUTRAL)
            }
        }

        if (users.isEmpty()) {
            IxhEmptyStateCard(
                title = "Belum ada penerima.",
                subtitle = "Tambahkan anggota kelas terlebih dahulu agar status pengiriman tercatat."
            )
        } else {
            users.forEach { u ->
                val (badge, tone) = when {
                    u.waStatus != WaNumberStatus.VALID.name -> "Belum Valid (${u.waStatus})" to ChipTone.NEUTRAL
                    u.deliveryStatus == "Gagal" -> "Gagal" to ChipTone.RED
                    else -> u.deliveryStatus.ifBlank { "Siap Dikirim" } to ChipTone.GREEN
                }
                IxhCard(
                    backgroundColor = IxhWhite,
                    contentPadding = PaddingValues(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "${u.absenNo}. ${u.fullName}",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = IxhInk
                            )
                            Text(
                                text = u.phoneWa.ifBlank { "Nomor WA belum diisi" },
                                style = MaterialTheme.typography.bodySmall,
                                color = IxhTextSecondary
                            )
                        }
                        IxhStatusChip(text = badge, tone = tone)
                    }
                }
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            IxhPrimaryButton(
                text = "Kirim Ulang yang Gagal",
                onClick = onRetryFailed,
                modifier = Modifier.weight(1f)
            )
            IxhSecondaryButton(
                text = "Perbaiki Nomor",
                onClick = onNavigateProblematicNumbers,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun BillVerifyDetailScreen(
    student: UserEntity?,
    actorName: String,
    actorRole: String,
    onMarkPaid: (UserEntity, String, String) -> Unit,
    onSendSingleReminder: (UserEntity) -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        IxhSubScreenTopBar(title = "Verifikasi Pembayaran", onBack = onBack)

        if (student == null) {
            IxhEmptyStateCard(
                title = "Siswa belum dipilih.",
                subtitle = "Pilih salah satu anggota dari daftar tagihan untuk melakukan verifikasi.",
                actionText = "Kembali",
                onActionClick = onBack
            )
            return@Column
        }

        val target = student
        var selectedMethodIndex by remember { mutableIntStateOf(0) } // 0=Tunai, 1=Transfer, 2=QRIS
        val methods = listOf("Tunai", "Transfer", "QRIS")
        var noteText by remember { mutableStateOf("Dibayar langsung ke Bendahara di kelas") }
        val isAlreadyPaid = target.billStatus == BillPaymentStatus.LUNAS.name
        val nominal = if (target.billAmount > 0) target.billAmount else 6000

        IxhCard(
            backgroundColor = if (isAlreadyPaid) IxhGreenBg else IxhYellowLight,
            borderColor = IxhInk
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = target.fullName,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.ExtraBold,
                        color = IxhInk
                    )
                    Text(
                        text = "No. Absen ${"%02d".format(target.absenNo)} • ${target.phoneWa.ifBlank { "Tanpa WA" }}",
                        style = MaterialTheme.typography.bodySmall,
                        color = IxhTextSecondary
                    )
                }
                IxhStatusChip(
                    text = if (isAlreadyPaid) "Lunas" else target.billStatus,
                    tone = if (isAlreadyPaid) ChipTone.GREEN else if (target.billStatus == BillPaymentStatus.TUNGGAKAN.name) ChipTone.RED else ChipTone.YELLOW
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Iuran Kas Kelas IX-H",
                    style = MaterialTheme.typography.bodySmall,
                    color = IxhInk
                )
                Text(
                    text = formatIdr(nominal),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = IxhInk
                )
            }
        }

        IxhCard(backgroundColor = IxhWhite) {
            Text(
                text = "PRATINJAU KONFIRMASI WHATSAPP",
                style = MaterialTheme.typography.labelSmall,
                color = IxhTextSecondary
            )
            Spacer(modifier = Modifier.height(6.dp))
            IxhWaBubblePreview(
                senderTitle = target.fullName,
                messageBody = "Halo Bendahara, saya ingin mengonfirmasi pembayaran kas kelas sebesar ${formatIdr(nominal)}.",
                timeLabel = "Hari ini",
                outgoingReply = "Terima kasih ${target.fullName.substringBefore(" ")}, status kas Anda diverifikasi oleh $actorName."
            )
        }

        if (!isAlreadyPaid) {
            Text(
                text = "METODE PEMBAYARAN",
                style = MaterialTheme.typography.labelSmall,
                color = IxhTextSecondary
            )
            IxhSegmentedTabs(
                tabs = methods,
                selectedIndex = selectedMethodIndex,
                onTabSelected = { selectedMethodIndex = it }
            )

            OutlinedTextField(
                value = noteText,
                onValueChange = { noteText = it },
                label = { Text("Catatan Konfirmasi (Opsional)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = IxhWhite,
                    unfocusedContainerColor = IxhWhite
                )
            )

            IxhPrimaryButton(
                text = "✓ Tandai Lunas (${formatIdr(nominal)})",
                onClick = {
                    onMarkPaid(target, methods[selectedMethodIndex], noteText)
                },
                testTag = "mark_bill_paid_button"
            )

            IxhSecondaryButton(
                text = "Kirim Pengingat WA ke ${target.fullName.substringBefore(" ")}",
                onClick = { onSendSingleReminder(target) }
            )
        } else {
            IxhAlertBanner(
                text = "Tagihan ini sudah LUNAS pada ${target.paidDate.ifEmpty { "Hari ini" }} melalui ${target.paidMethod.ifEmpty { "Tunai" }} dan tercatat di Kas Kelas.",
                tone = ChipTone.GREEN
            )
        }
    }
}

@Composable
fun BillReminderSendScreen(
    unpaidStudents: List<UserEntity>,
    onSendReminders: (Int, String) -> Unit,
    onBack: () -> Unit
) {
    var toneIndex by remember { mutableIntStateOf(1) } // 0=Ramah, 1=Biasa, 2=Tegas
    val tones = listOf("Ramah", "Biasa", "Tegas")
    val targetCount = unpaidStudents.size
    val belumCount = unpaidStudents.count { it.billStatus == BillPaymentStatus.BELUM.name }
    val tunggakanCount = unpaidStudents.count { it.billStatus == BillPaymentStatus.TUNGGAKAN.name }

    val previewMessage = when (toneIndex) {
        0 -> "Halo *{nama}*, sekadar mengingatkan kas kelas sebesar *Rp6.000* jatuh tempo minggu ini. Yuk dibayar ke Bendahara ya, terima kasih!\nBalas STOP untuk berhenti."
        1 -> "Halo *{nama}*, pengingat tagihan kas kelas sebesar *Rp6.000* jatuh tempo minggu ini. Mohon segera dibayarkan ke Bendahara.\nBalas STOP untuk berhenti."
        else -> "PERINGATAN TAGIHAN: Halo *{nama}*, tagihan kas kelas sebesar *Rp6.000* sudah mendekati/melewati batas jatuh tempo. Segera lunasi ke Bendahara hari ini.\nBalas STOP untuk berhenti."
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        IxhSubScreenTopBar(title = "Kirim Pengingat WA", onBack = onBack)

        IxhCard(backgroundColor = IxhWhite) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "$targetCount Siswa Belum Lunas / Tunggakan",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = IxhInk
                    )
                    Text(
                        text = "$belumCount Belum bayar • $tunggakanCount Tunggakan",
                        style = MaterialTheme.typography.bodySmall,
                        color = IxhTextSecondary
                    )
                }
                IxhStatusChip(text = "$targetCount penerima", tone = ChipTone.YELLOW)
            }
        }

        Text(
            text = "PILIH NADA PESAN",
            style = MaterialTheme.typography.labelSmall,
            color = IxhTextSecondary
        )
        IxhSegmentedTabs(
            tabs = tones,
            selectedIndex = toneIndex,
            onTabSelected = { toneIndex = it }
        )

        IxhWaBubblePreview(
            senderTitle = "IX-H Hub • Pengingat (${tones[toneIndex]})",
            messageBody = previewMessage,
            timeLabel = "16.00"
        )

        IxhAlertBanner(
            text = "Jeda acak 8–20 detik antar pesan aktif otomatis agar nomor bot aman.",
            tone = ChipTone.BLUE
        )

        IxhPrimaryButton(
            text = "Kirim Pengingat ke $targetCount Siswa",
            onClick = { onSendReminders(targetCount, tones[toneIndex]) },
            enabled = targetCount > 0
        )
    }
}

@Composable
fun StudentMyBillsScreen(
    activeUser: UserEntity,
    onSwitchStudentDemo: (String) -> Unit,
    onSendProofToast: (String) -> Unit,
    onBack: () -> Unit
) {
    val isPaid = activeUser.billStatus == BillPaymentStatus.LUNAS.name
    val billAmt = activeUser.billAmount

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        IxhSubScreenTopBar(
            title = "Tagihan Kas Saya",
            onBack = onBack
        )

        if (!isPaid && billAmt > 0) {
            IxhCard(
                backgroundColor = IxhYellow,
                hasHardShadow = true,
                contentPadding = PaddingValues(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IxhStatusChip(text = "BELUM DIBAYAR", tone = ChipTone.RED)
                    Text(
                        text = "Status: ${activeUser.billStatus}",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = IxhInk
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = formatIdr(billAmt),
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = IxhInk
                )
                Text(
                    text = "Iuran Kas Kelas IX-H • ${activeUser.fullName}",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = IxhInk
                )
                Spacer(modifier = Modifier.height(10.dp))
                IxhCard(backgroundColor = IxhWhite) {
                    Text(
                        text = "CARA PEMBAYARAN",
                        style = MaterialTheme.typography.labelSmall,
                        color = IxhTextSecondary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "1. Tunai langsung ke Bendahara Kelas IX-H.\n2. Transfer / QRIS lalu kirim foto bukti lewat WhatsApp.",
                        style = MaterialTheme.typography.bodySmall,
                        color = IxhInk
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                IxhPrimaryButton(
                    text = "Kirim Bukti Bayar via WhatsApp",
                    onClick = {
                        onSendProofToast("Menyiapkan format konfirmasi kas ${activeUser.fullName}...")
                    }
                )
            }
        } else {
            IxhCard(
                backgroundColor = IxhGreenBg,
                borderColor = IxhGreen,
                hasHardShadow = true,
                contentPadding = PaddingValues(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IxhBlobMascot(expression = BlobExpression.SMILE_HAPPY, size = 56.dp)
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        IxhStatusChip(
                            text = if (isPaid) "LUNAS ✓" else "TIDAK ADA TAGIHAN",
                            tone = ChipTone.GREEN
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Halo, ${activeUser.fullName.substringBefore(" ")}!",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.ExtraBold,
                            color = IxhInk
                        )
                        Text(
                            text = if (isPaid) {
                                "Kas kelas sebesar ${formatIdr(billAmt)} sudah dikonfirmasi LUNAS (${activeUser.paidMethod.ifBlank { "Tunai" }})."
                            } else {
                                "Saat ini belum ada tagihan kas aktif untuk akunmu."
                            },
                            style = MaterialTheme.typography.bodySmall,
                            color = IxhGreenText
                        )
                    }
                }
            }
        }
    }
}
