package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.local.BillPaymentStatus
import com.example.data.local.UserEntity
import com.example.data.local.UserRole
import com.example.data.local.WaNumberStatus
import com.example.ui.components.IxhBottomNavigationBar
import com.example.ui.components.MainNavTab
import com.example.ui.components.neubrutalShadow
import com.example.ui.screens.AcademicCalendarScreen
import com.example.ui.screens.ActivityLogDetailScreen
import com.example.ui.screens.ActivityLogsScreen
import com.example.ui.screens.AddCashTransactionScreen
import com.example.ui.screens.AnnouncementDetailScreen
import com.example.ui.screens.AnnouncementFormScreen
import com.example.ui.screens.AnnouncementsListScreen
import com.example.ui.screens.AttendanceHistoryExportScreen
import com.example.ui.screens.AttendanceMainTabScreen
import com.example.ui.screens.BackupRestoreScreen
import com.example.ui.screens.BillDeliveryStatusScreen
import com.example.ui.screens.BillPreviewScheduleScreen
import com.example.ui.screens.BillReminderSendScreen
import com.example.ui.screens.BillVerifyDetailScreen
import com.example.ui.screens.BotConfigureSendScreen
import com.example.ui.screens.BotFailureLogsScreen
import com.example.ui.screens.BotHistoryScreen
import com.example.ui.screens.BotPanelScreen
import com.example.ui.screens.BotReconnectQrPairingScreen
import com.example.ui.screens.BotSelectReportScreen
import com.example.ui.screens.BotSettingsAdminScreen
import com.example.ui.screens.BotStopMulaiSimulatorScreen
import com.example.ui.screens.BotTemplateEditorScreen
import com.example.ui.screens.BotTemplatesListScreen
import com.example.ui.screens.CashHistoryExportScreen
import com.example.ui.screens.CashMainTabScreen
import com.example.ui.screens.CashTransactionDetailScreen
import com.example.ui.screens.ClassSettingsScreen
import com.example.ui.screens.CoachmarkTourOverlay
import com.example.ui.screens.CreateBillScreen
import com.example.ui.screens.DashboardScreen
import com.example.ui.screens.FirstRunAdminSetupScreen
import com.example.ui.screens.ForgotPasswordScreen
import com.example.ui.screens.GlobalSearchScreen
import com.example.ui.screens.HelpArticleDetailScreen
import com.example.ui.screens.HelpCenterScreen
import com.example.ui.screens.LockedAccountScreen
import com.example.ui.screens.LoginScreen
import com.example.ui.screens.LogoutSuccessScreen
import com.example.ui.screens.NotificationSettingsScreen
import com.example.ui.screens.NotificationsCenterScreen
import com.example.ui.screens.OnboardingFirstLoginScreen
import com.example.ui.screens.ProfileEditScreen
import com.example.ui.screens.ProfileMainScreen
import com.example.ui.screens.ScheduleDetailScreen
import com.example.ui.screens.ScheduleFormScreen
import com.example.ui.screens.ScheduleMainTabScreen
import com.example.ui.screens.ScheduleNightReminderScreen
import com.example.ui.screens.SettingsAppearanceScreen
import com.example.ui.screens.SettingsSecurityScreen
import com.example.ui.screens.SettingsVpsSyncScreen
import com.example.ui.screens.SplashScreen
import com.example.ui.screens.StudentMyAttendanceScreen
import com.example.ui.screens.StudentMyBillsScreen
import com.example.ui.screens.TakeAttendanceScreen
import com.example.ui.screens.TaskDetailScreen
import com.example.ui.screens.TaskFormScreen
import com.example.ui.screens.TasksMainTabScreen
import com.example.ui.screens.UserDetailAdminScreen
import com.example.ui.screens.UserFormAdminScreen
import com.example.ui.screens.UserImportMassalScreen
import com.example.ui.screens.UserManagementListScreen
import com.example.ui.screens.UserProblematicNumbersScreen
import com.example.ui.theme.IxhCream
import com.example.ui.theme.IxhInk
import com.example.ui.theme.IxhYellow
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.AppScreenRoute
import com.example.ui.viewmodel.IxHubViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val vm: IxHubViewModel = viewModel()
            val visualTheme by vm.visualTheme.collectAsState()
            val darkTheme by vm.darkTheme.collectAsState()
            val lowRamSolidMode by vm.lowRamSolidMode.collectAsState()

            MyApplicationTheme(
                darkTheme = darkTheme,
                visualStyle = visualTheme,
                lowRamSolidMode = lowRamSolidMode
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    IxHubRootApp(vm = vm)
                }
            }
        }
    }
}

@Composable
fun IxHubRootApp(vm: IxHubViewModel) {
    val currentRoute by vm.currentRoute.collectAsState()
    val currentTab by vm.currentTab.collectAsState()
    val activeUsername by vm.activeUsername.collectAsState()
    val activeSessionUser by vm.activeSessionUser.collectAsState()
    val hasAdminAccount by vm.hasAdminAccount.collectAsState()
    val users by vm.users.collectAsState()
    val transactions by vm.transactions.collectAsState()
    val schedules by vm.schedules.collectAsState()
    val assignments by vm.assignments.collectAsState()
    val announcements by vm.announcements.collectAsState()
    val notifications by vm.notifications.collectAsState()
    val activityLogs by vm.activityLogs.collectAsState()
    val botTemplates by vm.botTemplates.collectAsState()
    val botDispatchLogs by vm.botDispatchLogs.collectAsState()
    val syncQueue by vm.syncQueue.collectAsState()

    val selectedScheduleDay by vm.selectedScheduleDay.collectAsState()
    val selectedScheduleItem by vm.selectedScheduleItem.collectAsState()
    val selectedAssignment by vm.selectedAssignment.collectAsState()
    val selectedAnnouncement by vm.selectedAnnouncement.collectAsState()
    val selectedTransaction by vm.selectedTransaction.collectAsState()
    val selectedUserForDetail by vm.selectedUserForDetail.collectAsState()
    val selectedActivityLog by vm.selectedActivityLog.collectAsState()
    val selectedBotTemplate by vm.selectedBotTemplate.collectAsState()
    val selectedHelpTopicId by vm.selectedHelpTopicId.collectAsState()

    val classSettings by vm.classSettings.collectAsState()
    val botState by vm.botState.collectAsState()
    val backupState by vm.backupState.collectAsState()
    val globalSearchQuery by vm.globalSearchQuery.collectAsState()
    val globalSearchCategory by vm.globalSearchCategory.collectAsState()
    val tourState by vm.tourState.collectAsState()
    val toastMessage by vm.toastMessage.collectAsState()
    val kasTabIndex by vm.kasTabIndex.collectAsState()
    val visualTheme by vm.visualTheme.collectAsState()
    val darkTheme by vm.darkTheme.collectAsState()
    val lowRamSolidMode by vm.lowRamSolidMode.collectAsState()

    val activeUser = users.firstOrNull { it.username.equals(activeUsername, ignoreCase = true) }
        ?: activeSessionUser
        ?: users.firstOrNull()
        ?: UserEntity(
            id = 0,
            absenNo = 1,
            fullName = "Pengguna IX-H",
            username = "",
            email = "",
            gender = "Laki-laki",
            phoneWa = "",
            role = UserRole.SISWA.name
        )

    val unreadCount = notifications.count { it.isUnread }
    val pendingSyncCount = syncQueue.count { it.status == "PENDING" }

    BackHandler(
        enabled = currentRoute != AppScreenRoute.MAIN_SHELL || currentTab != MainNavTab.BERANDA
    ) {
        vm.navigateBack()
    }

    val cyclePersonaQuick: () -> Unit = {
        if (users.size > 1) {
            val currentIdx = users.indexOfFirst { it.username == activeUser.username }
            val nextUser = users[(currentIdx + 1) % users.size]
            vm.switchPersona(nextUser.username)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = IxhCream,
        contentWindowInsets = WindowInsets.safeDrawing,
        bottomBar = {
            if (currentRoute == AppScreenRoute.MAIN_SHELL) {
                IxhBottomNavigationBar(
                    currentTab = currentTab,
                    onTabSelected = { vm.selectMainTab(it) }
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (currentRoute) {
                AppScreenRoute.SPLASH -> {
                    SplashScreen(
                        onFinished = { vm.refreshStartupRoute() }
                    )
                }

                AppScreenRoute.FIRST_RUN_ADMIN_SETUP -> {
                    FirstRunAdminSetupScreen(
                        onCreateFirstAdmin = { fullName, uname, phoneWa, pass, subRoleTitle, onResult ->
                            vm.createFirstAdminAccount(
                                fullName = fullName,
                                username = uname,
                                phoneWa = phoneWa,
                                rawPassword = pass,
                                subRoleTitle = subRoleTitle,
                                onResult = onResult
                            )
                        },
                        onSetupCompleted = {
                            vm.navigateRoot(AppScreenRoute.MAIN_SHELL)
                        }
                    )
                }

                AppScreenRoute.LOGIN -> {
                    LoginScreen(
                        onLoginSuccess = { uname ->
                            vm.switchPersona(uname)
                            vm.navigateRoot(AppScreenRoute.MAIN_SHELL)
                        },
                        onForgotPassword = { vm.navigateTo(AppScreenRoute.FORGOT_PASSWORD) },
                        onLockedAccount = { vm.navigateTo(AppScreenRoute.LOCKED_ACCOUNT) },
                        onStartOnboarding = { uname ->
                            vm.switchPersona(uname)
                            vm.navigateTo(AppScreenRoute.ONBOARDING_FLOW)
                        },
                        onNavigateFirstRunSetup = if (!hasAdminAccount) {
                            { vm.navigateRoot(AppScreenRoute.FIRST_RUN_ADMIN_SETUP) }
                        } else null,
                        registeredUsers = users
                    )
                }

                AppScreenRoute.FORGOT_PASSWORD -> {
                    ForgotPasswordScreen(
                        onBackToLogin = { vm.navigateBack() },
                        onRequestSent = { uname ->
                            vm.showToast("Permintaan reset @$uname dikirim ke Admin Kelas IX-H")
                            vm.navigateBack()
                        }
                    )
                }

                AppScreenRoute.LOCKED_ACCOUNT -> {
                    LockedAccountScreen(
                        onBackToLogin = { vm.navigateBack() },
                        onContactAdmin = {
                            vm.showToast("Silakan hubungi Admin Kelas IX-H untuk membuka kunci akun.")
                        }
                    )
                }

                AppScreenRoute.ONBOARDING_FLOW -> {
                    OnboardingFirstLoginScreen(
                        username = activeUsername,
                        onFinishOnboarding = {
                            vm.navigateRoot(AppScreenRoute.MAIN_SHELL)
                            vm.offerTour()
                        }
                    )
                }

                AppScreenRoute.MAIN_SHELL -> {
                    when (currentTab) {
                        MainNavTab.BERANDA -> {
                            DashboardScreen(
                                activeUser = activeUser,
                                allUsers = users,
                                transactions = transactions,
                                pinnedAnnouncement = announcements.firstOrNull { it.isPinned && !it.isDraft }
                                    ?: announcements.firstOrNull(),
                                closestAssignment = assignments.filter { !it.isCompleted }
                                    .minByOrNull { it.daysRemaining },
                                todaySchedules = schedules.filter { it.dayCode == selectedScheduleDay && !it.isBreak },
                                unreadNotificationCount = unreadCount,
                                botState = botState,
                                pendingSyncCount = pendingSyncCount,
                                onNavigateRoute = { vm.navigateTo(it) },
                                onSelectMainTab = { vm.selectMainTab(it) },
                                onSwitchPersona = { vm.switchPersona(it) },
                                onOpenKasTab = { subIdx ->
                                    vm.setKasTabIndex(subIdx)
                                    vm.selectMainTab(MainNavTab.KAS)
                                }
                            )
                        }

                        MainNavTab.ABSENSI -> {
                            AttendanceMainTabScreen(
                                activeUser = activeUser,
                                allUsers = users,
                                unreadNotificationCount = unreadCount,
                                onNavigateRoute = { vm.navigateTo(it) },
                                onSwitchPersona = { vm.switchPersona(it) }
                            )
                        }

                        MainNavTab.KAS -> {
                            CashMainTabScreen(
                                activeUser = activeUser,
                                users = users,
                                transactions = transactions,
                                unreadNotificationCount = unreadCount,
                                selectedTabIndex = kasTabIndex,
                                onSelectTabIndex = { vm.setKasTabIndex(it) },
                                onNavigate = { vm.navigateTo(it) },
                                onOpenTransactionDetail = { vm.openTransactionDetail(it) },
                                onOpenBillVerifyDetail = { vm.openBillVerifyDetail(it) },
                                onOpenRoleSwitcher = cyclePersonaQuick
                            )
                        }

                        MainNavTab.JADWAL -> {
                            ScheduleMainTabScreen(
                                activeUser = activeUser,
                                schedules = schedules,
                                selectedDayCode = selectedScheduleDay,
                                unreadNotificationCount = unreadCount,
                                onSelectDay = { vm.selectScheduleDay(it) },
                                onOpenScheduleDetail = { vm.openScheduleDetail(it) },
                                onNavigate = { vm.navigateTo(it) },
                                onOpenRoleSwitcher = cyclePersonaQuick
                            )
                        }

                        MainNavTab.TUGAS -> {
                            TasksMainTabScreen(
                                activeUser = activeUser,
                                assignments = assignments,
                                unreadNotificationCount = unreadCount,
                                onOpenAssignmentDetail = { vm.openAssignmentDetail(it) },
                                onToggleComplete = { vm.toggleAssignmentComplete(it) },
                                onNavigate = { vm.navigateTo(it) },
                                onOpenRoleSwitcher = cyclePersonaQuick
                            )
                        }
                    }
                }

                // §22.3 Global Search Route
                AppScreenRoute.GLOBAL_SEARCH -> {
                    val searchResults = vm.getFilteredGlobalSearchResults()
                    GlobalSearchScreen(
                        activeUser = activeUser,
                        query = globalSearchQuery,
                        selectedCategory = globalSearchCategory,
                        results = searchResults,
                        onQueryChange = { vm.updateGlobalSearchQuery(it) },
                        onCategoryChange = { vm.updateGlobalSearchCategory(it) },
                        onSelectResult = { vm.openGlobalSearchItem(it) },
                        onBack = { vm.navigateBack() }
                    )
                }

                // §22.1 Backup & Restore Route
                AppScreenRoute.BACKUP_RESTORE -> {
                    BackupRestoreScreen(
                        activeUser = activeUser,
                        backupState = backupState,
                        onCreateBackupNow = {
                            vm.createDatabaseBackupNow()
                        },
                        onRestoreFromBackupJson = { jsonText ->
                            vm.restoreDatabaseFromBackupJson(jsonText)
                        },
                        onBack = { vm.navigateBack() }
                    )
                }

                // Subscreens
                AppScreenRoute.ATTENDANCE_TAKE -> {
                    TakeAttendanceScreen(
                        users = users,
                        onBack = { vm.navigateBack() },
                        onSaveAttendance = { updatedList ->
                            vm.saveAttendanceBatch(updatedList, activeUser.fullName, activeUser.role)
                        }
                    )
                }

                AppScreenRoute.ATTENDANCE_HISTORY -> {
                    AttendanceHistoryExportScreen(
                        users = users,
                        onBack = { vm.navigateBack() },
                        onExportClicked = { fmt ->
                            vm.showToast("$fmt berhasil diunduh ke penyimpanan perangkat")
                        }
                    )
                }

                AppScreenRoute.STUDENT_MY_ATTENDANCE -> {
                    StudentMyAttendanceScreen(
                        student = activeUser,
                        onBack = { vm.navigateBack() },
                        onExportPdf = {
                            vm.showToast("Rekap Kehadiran ${activeUser.fullName} (PDF) berhasil diunduh")
                        }
                    )
                }

                AppScreenRoute.CASH_ADD_TRANSACTION -> {
                    AddCashTransactionScreen(
                        actorName = activeUser.fullName,
                        onSave = { title, amt, isIncome, dateLbl, proofNote ->
                            vm.addCashTransaction(title, amt, isIncome, dateLbl, proofNote, activeUser.fullName)
                        },
                        onBack = { vm.navigateBack() }
                    )
                }

                AppScreenRoute.CASH_TRANSACTION_DETAIL -> {
                    CashTransactionDetailScreen(
                        tx = selectedTransaction,
                        onBack = { vm.navigateBack() }
                    )
                }

                AppScreenRoute.CASH_HISTORY_EXPORT -> {
                    CashHistoryExportScreen(
                        transactions = transactions,
                        onOpenTransactionDetail = { vm.openTransactionDetail(it) },
                        onExportToast = { vm.showToast(it) },
                        onBack = { vm.navigateBack() }
                    )
                }

                AppScreenRoute.BILLS_MANAGE, AppScreenRoute.BILLS_CREATE -> {
                    CreateBillScreen(
                        defaultAmount = classSettings.defaultWeeklyKas,
                        onContinueToPreview = { vm.navigateTo(AppScreenRoute.BILLS_PREVIEW_SCHEDULE) },
                        onSaveDraftOnly = {
                            vm.showToast("Periode tagihan baru berhasil disimpan tanpa kirim WA")
                            vm.navigateBack()
                        },
                        onBack = { vm.navigateBack() }
                    )
                }

                AppScreenRoute.BILLS_PREVIEW_SCHEDULE -> {
                    BillPreviewScheduleScreen(
                        onSendAndCreate = {
                            vm.dispatchBotMessage("Kas Mingguan Kelas IX-H", "Tagihan", users.size)
                        },
                        onNavigateProblematicNumbers = {
                            vm.navigateTo(AppScreenRoute.USER_PROBLEMATIC_NUMBERS)
                        },
                        onBack = { vm.navigateBack() }
                    )
                }

                AppScreenRoute.BILLS_DELIVERY_STATUS -> {
                    BillDeliveryStatusScreen(
                        users = users,
                        onRetryFailed = { vm.retryFailedDispatches() },
                        onNavigateProblematicNumbers = {
                            vm.navigateTo(AppScreenRoute.USER_PROBLEMATIC_NUMBERS)
                        },
                        onBack = { vm.navigateBack() }
                    )
                }

                AppScreenRoute.BILLS_VERIFY_DETAIL -> {
                    BillVerifyDetailScreen(
                        student = selectedUserForDetail,
                        actorName = activeUser.fullName,
                        actorRole = activeUser.role,
                        onMarkPaid = { student, method, note ->
                            vm.markBillPaid(student, method, note, activeUser.fullName, activeUser.role)
                        },
                        onSendSingleReminder = { student ->
                            vm.showToast("Pengingat WA terkirim ke ${student.fullName} (${student.phoneWa})")
                        },
                        onBack = { vm.navigateBack() }
                    )
                }

                AppScreenRoute.BILLS_REMINDER_SEND -> {
                    val unpaid = users.filter { it.billStatus != BillPaymentStatus.LUNAS.name }
                    BillReminderSendScreen(
                        unpaidStudents = unpaid,
                        onSendReminders = { count, tone ->
                            vm.dispatchBotMessage("Pengingat Kas ($tone)", "Tagihan", count)
                        },
                        onBack = { vm.navigateBack() }
                    )
                }

                AppScreenRoute.STUDENT_MY_BILLS -> {
                    StudentMyBillsScreen(
                        activeUser = activeUser,
                        onSwitchStudentDemo = { vm.switchPersona(it) },
                        onSendProofToast = { vm.showToast(it) },
                        onBack = { vm.navigateBack() }
                    )
                }

                AppScreenRoute.SCHEDULE_DETAIL -> {
                    ScheduleDetailScreen(
                        schedule = selectedScheduleItem,
                        onOpenNightReminder = { vm.navigateTo(AppScreenRoute.SCHEDULE_NIGHT_REMINDER) },
                        onBack = { vm.navigateBack() }
                    )
                }

                AppScreenRoute.SCHEDULE_FORM -> {
                    ScheduleFormScreen(
                        defaultDayCode = selectedScheduleDay,
                        onSave = { day, start, end, subj, teacher, room, notes, isBreak ->
                            vm.createOrUpdateSchedule(day, start, end, subj, teacher, room, notes, isBreak)
                        },
                        onBack = { vm.navigateBack() }
                    )
                }

                AppScreenRoute.SCHEDULE_NIGHT_REMINDER -> {
                    ScheduleNightReminderScreen(
                        defaultTimeWib = classSettings.defaultReminderTime,
                        onSave = { enabled, timeWib -> vm.saveNightReminder(enabled, timeWib) },
                        onBack = { vm.navigateBack() }
                    )
                }

                AppScreenRoute.ACADEMIC_CALENDAR -> {
                    AcademicCalendarScreen(
                        schedules = schedules,
                        assignments = assignments,
                        onBack = { vm.navigateBack() }
                    )
                }

                AppScreenRoute.ASSIGNMENT_DETAIL -> {
                    TaskDetailScreen(
                        assignment = selectedAssignment,
                        onToggleComplete = { vm.toggleAssignmentComplete(it) },
                        onDownloadToast = { vm.showToast(it) },
                        onBack = { vm.navigateBack() }
                    )
                }

                AppScreenRoute.ASSIGNMENT_FORM -> {
                    TaskFormScreen(
                        onSave = { title, subject, teacher, deadline, days, desc, reminder ->
                            vm.createOrUpdateAssignment(title, subject, teacher, deadline, days, desc, reminder)
                        },
                        onBack = { vm.navigateBack() }
                    )
                }

                AppScreenRoute.ANNOUNCEMENTS_LIST -> {
                    AnnouncementsListScreen(
                        activeUser = activeUser,
                        announcements = announcements,
                        onOpenDetail = { vm.openAnnouncementDetail(it) },
                        onNavigateCreate = { vm.navigateTo(AppScreenRoute.ANNOUNCEMENT_FORM) },
                        onBack = { vm.navigateBack() }
                    )
                }

                AppScreenRoute.ANNOUNCEMENT_DETAIL -> {
                    AnnouncementDetailScreen(
                        activeUser = activeUser,
                        announcement = selectedAnnouncement,
                        onTogglePin = { vm.togglePinAnnouncement(it) },
                        onDelete = { vm.deleteAnnouncement(it, activeUser.fullName) },
                        onDownloadToast = { vm.showToast(it) },
                        onBack = { vm.navigateBack() }
                    )
                }

                AppScreenRoute.ANNOUNCEMENT_FORM -> {
                    AnnouncementFormScreen(
                        authorName = activeUser.fullName,
                        authorRole = activeUser.role,
                        onSave = { title, content, cat, pinned, draft ->
                            vm.createAnnouncement(title, content, cat, pinned, draft, activeUser.fullName, activeUser.role)
                        },
                        onBack = { vm.navigateBack() }
                    )
                }

                AppScreenRoute.NOTIFICATIONS_CENTER -> {
                    NotificationsCenterScreen(
                        notifications = notifications,
                        onMarkAllRead = { vm.markAllNotificationsRead() },
                        onDeleteRead = { vm.deleteReadNotifications() },
                        onNotificationClick = { notif ->
                            vm.markNotificationRead(notif)
                            when (notif.targetRoute) {
                                "tugas" -> vm.selectMainTab(MainNavTab.TUGAS)
                                "jadwal" -> vm.selectMainTab(MainNavTab.JADWAL)
                                "kas" -> vm.selectMainTab(MainNavTab.KAS)
                                "absensi" -> vm.selectMainTab(MainNavTab.ABSENSI)
                                "pengumuman" -> vm.navigateTo(AppScreenRoute.ANNOUNCEMENTS_LIST)
                                "bot" -> vm.navigateTo(AppScreenRoute.BOT_PANEL)
                                else -> vm.selectMainTab(MainNavTab.BERANDA)
                            }
                        },
                        onDeleteSingle = { vm.deleteNotification(it) },
                        onOpenSettings = { vm.navigateTo(AppScreenRoute.NOTIFICATION_SETTINGS) },
                        onBack = { vm.navigateBack() }
                    )
                }

                AppScreenRoute.NOTIFICATION_SETTINGS -> {
                    NotificationSettingsScreen(
                        onSaveToast = { vm.showToast(it) },
                        onBack = { vm.navigateBack() }
                    )
                }

                AppScreenRoute.BOT_PANEL -> {
                    BotPanelScreen(
                        activeUser = activeUser,
                        botState = botState,
                        dispatchLogs = botDispatchLogs,
                        validWaCount = users.count { it.waStatus == WaNumberStatus.VALID.name },
                        onToggleConnectionSimulated = { vm.toggleBotConnectionSimulated() },
                        onPterodactylControl = { vm.controlPterodactylServer(it) },
                        onNavigate = { vm.navigateTo(it) },
                        onBack = { vm.navigateBack() }
                    )
                }

                AppScreenRoute.BOT_SELECT_REPORT -> {
                    BotSelectReportScreen(
                        onSelectReportToConfigure = { vm.navigateTo(AppScreenRoute.BOT_CONFIGURE_SEND) },
                        onBack = { vm.navigateBack() }
                    )
                }

                AppScreenRoute.BOT_CONFIGURE_SEND -> {
                    BotConfigureSendScreen(
                        users = users,
                        onDispatchNow = { title, cat, count ->
                            vm.dispatchBotMessage(title, cat, count)
                        },
                        onBack = { vm.navigateBack() }
                    )
                }

                AppScreenRoute.BOT_HISTORY -> {
                    BotHistoryScreen(
                        dispatchLogs = botDispatchLogs,
                        onOpenFailureLogs = { vm.navigateTo(AppScreenRoute.BOT_FAILURE_LOGS) },
                        onBack = { vm.navigateBack() }
                    )
                }

                AppScreenRoute.BOT_SETTINGS_ADMIN -> {
                    BotSettingsAdminScreen(
                        botState = botState,
                        users = users,
                        onUpdateMaxPerHour = { vm.updateBotMaxPerHour(it) },
                        onNavigateReconnect = { vm.navigateTo(AppScreenRoute.BOT_RECONNECT_QR_PAIRING) },
                        onNavigateFailureLogs = { vm.navigateTo(AppScreenRoute.BOT_FAILURE_LOGS) },
                        onNavigateProblematicNumbers = { vm.navigateTo(AppScreenRoute.USER_PROBLEMATIC_NUMBERS) },
                        onBack = { vm.navigateBack() }
                    )
                }

                AppScreenRoute.BOT_RECONNECT_QR_PAIRING -> {
                    BotReconnectQrPairingScreen(
                        botState = botState,
                        onGenerateNewCode = { vm.generateNewPairingCode() },
                        onReconnectSuccess = {
                            if (!botState.isConnected) {
                                vm.toggleBotConnectionSimulated()
                            } else {
                                vm.showToast("Sesi WangLinMD aktif & terhubung")
                            }
                            vm.navigateBack()
                        },
                        onBack = { vm.navigateBack() }
                    )
                }

                AppScreenRoute.BOT_FAILURE_LOGS -> {
                    BotFailureLogsScreen(
                        dispatchLogs = botDispatchLogs,
                        onRetryAllFailed = { vm.retryFailedDispatches() },
                        onNavigateProblematicNumbers = { vm.navigateTo(AppScreenRoute.USER_PROBLEMATIC_NUMBERS) },
                        onBack = { vm.navigateBack() }
                    )
                }

                AppScreenRoute.BOT_TEMPLATES_LIST -> {
                    BotTemplatesListScreen(
                        templates = botTemplates,
                        onOpenEditor = { vm.openBotTemplateEditor(it) },
                        onBack = { vm.navigateBack() }
                    )
                }

                AppScreenRoute.BOT_TEMPLATE_EDITOR -> {
                    BotTemplateEditorScreen(
                        template = selectedBotTemplate,
                        onSaveTemplate = { tpl, body -> vm.saveBotTemplate(tpl, body) },
                        onResetToDefault = { vm.resetBotTemplate(it) },
                        onSendTestMessage = { vm.showToast(it) },
                        onBack = { vm.navigateBack() }
                    )
                }

                AppScreenRoute.BOT_STOP_MULAI_SIMULATOR -> {
                    BotStopMulaiSimulatorScreen(
                        users = users,
                        onToggleStudentStopMulai = { u, enable -> vm.toggleStudentWaStopMulai(u, enable) },
                        onBack = { vm.navigateBack() }
                    )
                }

                AppScreenRoute.USER_MANAGEMENT_LIST -> {
                    UserManagementListScreen(
                        users = users,
                        onOpenUserDetail = { vm.openUserDetail(it) },
                        onNavigateAddUser = { vm.navigateTo(AppScreenRoute.USER_FORM_ADMIN) },
                        onNavigateImportMassal = { vm.navigateTo(AppScreenRoute.USER_IMPORT_MASSAL) },
                        onNavigateProblematicNumbers = { vm.navigateTo(AppScreenRoute.USER_PROBLEMATIC_NUMBERS) },
                        onBack = { vm.navigateBack() }
                    )
                }

                AppScreenRoute.USER_DETAIL_ADMIN -> {
                    UserDetailAdminScreen(
                        user = selectedUserForDetail,
                        actorName = activeUser.fullName,
                        onUpdateRole = { u, newRole, sub ->
                            vm.updateUserRole(u, newRole, sub, activeUser.fullName)
                        },
                        onResetPassword = { u -> vm.resetUserPasswordAdmin(u) },
                        onToggleWaPreference = { u, enable -> vm.toggleStudentWaStopMulai(u, enable) },
                        onBack = { vm.navigateBack() }
                    )
                }

                AppScreenRoute.USER_FORM_ADMIN -> {
                    UserFormAdminScreen(
                        existingUser = null,
                        onSaveUser = { existing, name, uname, role, phone, testSend ->
                            vm.saveNewOrEditedUser(existing, name, uname, role, phone, testSend)
                        },
                        onBack = { vm.navigateBack() }
                    )
                }

                AppScreenRoute.USER_IMPORT_MASSAL -> {
                    UserImportMassalScreen(
                        onImportCompleted = { vm.showToast(it) },
                        onBack = { vm.navigateBack() }
                    )
                }

                AppScreenRoute.USER_PROBLEMATIC_NUMBERS -> {
                    UserProblematicNumbersScreen(
                        users = users,
                        onRevalidateAll = { vm.revalidateAllProblematicNumbers() },
                        onOpenUserDetail = { vm.openUserDetail(it) },
                        onBack = { vm.navigateBack() }
                    )
                }

                AppScreenRoute.CLASS_SETTINGS -> {
                    ClassSettingsScreen(
                        activeUser = activeUser,
                        settings = classSettings,
                        onSaveSettings = { vm.saveClassSettings(it) },
                        onBack = { vm.navigateBack() }
                    )
                }

                AppScreenRoute.ACTIVITY_LOGS -> {
                    ActivityLogsScreen(
                        activeUser = activeUser,
                        logs = activityLogs,
                        onOpenLogDetail = { vm.openActivityLogDetail(it) },
                        onExportToast = { vm.showToast(it) },
                        onBack = { vm.navigateBack() }
                    )
                }

                AppScreenRoute.ACTIVITY_LOG_DETAIL -> {
                    ActivityLogDetailScreen(
                        log = selectedActivityLog,
                        onBack = { vm.navigateBack() }
                    )
                }

                AppScreenRoute.PROFILE_MAIN, AppScreenRoute.SETTINGS_MAIN -> {
                    ProfileMainScreen(
                        activeUser = activeUser,
                        onSwitchPersona = { vm.switchPersona(it) },
                        onNavigate = { vm.navigateTo(it) },
                        onStartTourAgain = { vm.startTour() },
                        onBack = { vm.navigateBack() }
                    )
                }

                AppScreenRoute.PROFILE_EDIT -> {
                    ProfileEditScreen(
                        activeUser = activeUser,
                        onSaveProfile = { newName, newPhone ->
                            vm.saveNewOrEditedUser(
                                activeUser,
                                newName,
                                activeUser.username,
                                activeUser.role,
                                newPhone,
                                true
                            )
                        },
                        onBack = { vm.navigateBack() }
                    )
                }

                AppScreenRoute.SETTINGS_APPEARANCE -> {
                    SettingsAppearanceScreen(
                        currentStyle = visualTheme,
                        darkTheme = darkTheme,
                        lowRamSolidMode = lowRamSolidMode,
                        onApplyTheme = { style, dark, lowRam ->
                            vm.applyVisualTheme(style, dark, lowRam)
                        },
                        onBack = { vm.navigateBack() }
                    )
                }

                AppScreenRoute.SETTINGS_SECURITY -> {
                    SettingsSecurityScreen(
                        onSavePasswordToast = { vm.showToast(it) },
                        onBack = { vm.navigateBack() }
                    )
                }

                AppScreenRoute.SETTINGS_VPS_SYNC -> {
                    SettingsVpsSyncScreen(
                        botState = botState,
                        syncQueue = syncQueue,
                        onSaveVpsConfig = { url, key -> vm.updateVpsConfig(url, key) },
                        onSyncNow = { vm.testAndSyncVpsNow() },
                        onClearSyncedQueue = { vm.clearSyncedQueueItems() },
                        onBack = { vm.navigateBack() }
                    )
                }

                AppScreenRoute.HELP_CENTER -> {
                    HelpCenterScreen(
                        activeUser = activeUser,
                        onOpenArticle = { vm.openHelpArticle(it) },
                        onStartTourAgain = { vm.startTour() },
                        onBack = { vm.navigateBack() }
                    )
                }

                AppScreenRoute.HELP_ARTICLE_DETAIL -> {
                    HelpArticleDetailScreen(
                        topicId = selectedHelpTopicId,
                        onHelpfulToast = { vm.showToast(it) },
                        onBack = { vm.navigateBack() }
                    )
                }

                AppScreenRoute.LOGOUT_SUCCESS -> {
                    LogoutSuccessScreen(
                        onLoginAgain = { vm.navigateRoot(AppScreenRoute.LOGIN) }
                    )
                }
            }

            // Coachmark Tour Overlay
            CoachmarkTourOverlay(
                tourState = tourState,
                activeUser = activeUser,
                onStartTour = { vm.startTour() },
                onNextStep = { total -> vm.nextTourStep(total) },
                onPrevStep = { vm.prevTourStep() },
                onDismissTour = { vm.skipOrDismissTour() },
                onSwitchTabForStep = { vm.selectMainTab(it) }
            )

            // Floating Neubrutalism Toast Banner
            AnimatedVisibility(
                visible = toastMessage != null,
                enter = fadeIn(),
                exit = fadeOut(),
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                val toastShape = RoundedCornerShape(topStart = 18.dp, topEnd = 10.dp, bottomStart = 10.dp, bottomEnd = 18.dp)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .neubrutalShadow(cornerRadius = 14.dp, offsetX = 2.6.dp, offsetY = 3.5.dp)
                        .clip(toastShape)
                        .background(IxhYellow)
                        .border(2.4.dp, IxhInk, toastShape)
                        .clickable { vm.dismissToast() }
                        .padding(horizontal = 14.dp, vertical = 10.dp)
                ) {
                    Text(
                        text = toastMessage ?: "",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.ExtraBold,
                        color = IxhInk
                    )
                }
            }
        }
    }
}
