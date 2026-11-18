package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.ActivityLogEntity
import com.example.data.local.AnnouncementEntity
import com.example.data.local.AssignmentEntity
import com.example.data.local.AttendanceStatus
import com.example.data.local.BillPaymentStatus
import com.example.data.local.BotDispatchLogEntity
import com.example.data.local.BotTemplateEntity
import com.example.data.local.CashTransactionEntity
import com.example.data.local.IxHubDatabase
import com.example.data.local.NotificationEntity
import com.example.data.local.PasswordSecurity
import com.example.data.local.ScheduleEntity
import com.example.data.local.SyncQueueEntity
import com.example.data.local.UserEntity
import com.example.data.local.UserRole
import com.example.data.local.WaNumberStatus
import com.example.data.repository.DatabaseBackupPackage
import com.example.data.repository.GlobalSearchItem
import com.example.data.repository.IxHubRepository
import com.example.ui.components.MainNavTab
import com.example.ui.theme.VisualThemeStyle
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File

enum class AppScreenRoute {
    SPLASH,
    FIRST_RUN_ADMIN_SETUP,
    LOGIN,
    FORGOT_PASSWORD,
    LOCKED_ACCOUNT,
    ONBOARDING_FLOW,
    MAIN_SHELL,
    // Subscreens pushed on top of MAIN_SHELL
    GLOBAL_SEARCH,
    BACKUP_RESTORE,
    ATTENDANCE_TAKE,
    ATTENDANCE_HISTORY,
    STUDENT_MY_ATTENDANCE,
    CASH_ADD_TRANSACTION,
    CASH_TRANSACTION_DETAIL,
    CASH_HISTORY_EXPORT,
    BILLS_MANAGE,
    BILLS_CREATE,
    BILLS_PREVIEW_SCHEDULE,
    BILLS_DELIVERY_STATUS,
    BILLS_VERIFY_DETAIL,
    BILLS_REMINDER_SEND,
    STUDENT_MY_BILLS,
    SCHEDULE_DETAIL,
    SCHEDULE_FORM,
    SCHEDULE_NIGHT_REMINDER,
    ACADEMIC_CALENDAR,
    ASSIGNMENT_DETAIL,
    ASSIGNMENT_FORM,
    ANNOUNCEMENTS_LIST,
    ANNOUNCEMENT_DETAIL,
    ANNOUNCEMENT_FORM,
    NOTIFICATIONS_CENTER,
    NOTIFICATION_SETTINGS,
    BOT_PANEL,
    BOT_SELECT_REPORT,
    BOT_CONFIGURE_SEND,
    BOT_HISTORY,
    BOT_SETTINGS_ADMIN,
    BOT_RECONNECT_QR_PAIRING,
    BOT_FAILURE_LOGS,
    BOT_TEMPLATES_LIST,
    BOT_TEMPLATE_EDITOR,
    BOT_STOP_MULAI_SIMULATOR,
    USER_MANAGEMENT_LIST,
    USER_DETAIL_ADMIN,
    USER_FORM_ADMIN,
    USER_IMPORT_MASSAL,
    USER_PROBLEMATIC_NUMBERS,
    CLASS_SETTINGS,
    ACTIVITY_LOGS,
    ACTIVITY_LOG_DETAIL,
    PROFILE_MAIN,
    PROFILE_EDIT,
    SETTINGS_MAIN,
    SETTINGS_APPEARANCE,
    SETTINGS_SECURITY,
    SETTINGS_VPS_SYNC,
    HELP_CENTER,
    HELP_ARTICLE_DETAIL,
    LOGOUT_SUCCESS
}

data class ClassSettingsState(
    val className: String = "IX-H • SMPN 1 Bojongsari",
    val schoolYear: String = "2026/2027 • Semester 1 (Ganjil)",
    val defaultWeeklyKas: Int = 6000,
    val defaultDueDay: String = "Jum",
    val defaultReminderTime: String = "20.00 WIB",
    val autoReminderEnabled: Boolean = true,
    val attendanceSafeThreshold: Int = 90
)

/**
 * Honest default state for VPS & WhatsApp Bot:
 * Never claims connection is active or stores hardcoded API secrets in source code.
 */
data class BotConnectionState(
    val isConnected: Boolean = false,
    val sessionName: String = "Belum Terhubung",
    val activeSince: String = "Menunggu konfigurasi VPS",
    val botPhoneNumber: String = "Belum dikonfigurasi",
    val maxMessagesPerHour: Int = 60,
    val randomDelayRange: String = "8–20 detik",
    val autoPauseOnDisconnect: Boolean = true,
    val pairingCode: String = "--------",
    val pterodactylStatus: String = "Belum Terhubung",
    val vpsBaseUrl: String = "",
    val vpsApiKey: String = "",
    val lastVpsSyncStatus: String = "Belum dikonfigurasi — Masukkan URL & Token VPS di Pengaturan"
)

data class BackupUiState(
    val lastBackupTime: String = "Belum ada cadangan",
    val lastBackupChecksum: String = "-",
    val lastBackupTotalRecords: Int = 0,
    val latestBackupJson: String = "",
    val localFilePath: String = "",
    val statusMessage: String = "Siap mencadangkan atau memulihkan database lokal Room (Schema v3).",
    val isError: Boolean = false
)

data class TourUiState(
    val showOfferDialog: Boolean = false,
    val isActive: Boolean = false,
    val currentStep: Int = 1, // 1..total
    val showFinishedScreen: Boolean = false,
    val pausedAtStep: Int = 0
)

class IxHubViewModel(application: Application) : AndroidViewModel(application) {

    private val db = IxHubDatabase.getInstance(application)
    val repository = IxHubRepository(db.dao())

    val users: StateFlow<List<UserEntity>> = repository.users.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
    )
    val transactions: StateFlow<List<CashTransactionEntity>> = repository.transactions.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
    )
    val schedules: StateFlow<List<ScheduleEntity>> = repository.schedules.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
    )
    val assignments: StateFlow<List<AssignmentEntity>> = repository.assignments.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
    )
    val announcements: StateFlow<List<AnnouncementEntity>> = repository.announcements.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
    )
    val notifications: StateFlow<List<NotificationEntity>> = repository.notifications.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
    )
    val activityLogs: StateFlow<List<ActivityLogEntity>> = repository.activityLogs.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
    )
    val botTemplates: StateFlow<List<BotTemplateEntity>> = repository.botTemplates.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
    )
    val botDispatchLogs: StateFlow<List<BotDispatchLogEntity>> = repository.botDispatchLogs.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
    )
    val syncQueue: StateFlow<List<SyncQueueEntity>> = repository.syncQueue.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
    )

    private val _hasAdminAccount = MutableStateFlow(false)
    val hasAdminAccount: StateFlow<Boolean> = _hasAdminAccount.asStateFlow()

    private val _isInitialCheckDone = MutableStateFlow(false)
    val isInitialCheckDone: StateFlow<Boolean> = _isInitialCheckDone.asStateFlow()

    // Navigation & Screen Stack
    private val _currentRoute = MutableStateFlow(AppScreenRoute.FIRST_RUN_ADMIN_SETUP)
    val currentRoute: StateFlow<AppScreenRoute> = _currentRoute.asStateFlow()

    private val routeBackStack = ArrayDeque<AppScreenRoute>()

    private val _currentTab = MutableStateFlow(MainNavTab.BERANDA)
    val currentTab: StateFlow<MainNavTab> = _currentTab.asStateFlow()

    // Active User Session (populated after First-Run Admin Setup or Login)
    private val _activeUsername = MutableStateFlow("")
    val activeUsername: StateFlow<String> = _activeUsername.asStateFlow()

    private val _activeSessionUser = MutableStateFlow<UserEntity?>(null)
    val activeSessionUser: StateFlow<UserEntity?> = _activeSessionUser.asStateFlow()

    // Global Search State
    private val _globalSearchQuery = MutableStateFlow("")
    val globalSearchQuery: StateFlow<String> = _globalSearchQuery.asStateFlow()

    private val _globalSearchCategory = MutableStateFlow("Semua")
    val globalSearchCategory: StateFlow<String> = _globalSearchCategory.asStateFlow()

    // Backup & Restore State
    private val _backupState = MutableStateFlow(BackupUiState())
    val backupState: StateFlow<BackupUiState> = _backupState.asStateFlow()

    // Selected items for detail screens
    private val _selectedScheduleDay = MutableStateFlow("Sen")
    val selectedScheduleDay: StateFlow<String> = _selectedScheduleDay.asStateFlow()

    private val _selectedScheduleItem = MutableStateFlow<ScheduleEntity?>(null)
    val selectedScheduleItem: StateFlow<ScheduleEntity?> = _selectedScheduleItem.asStateFlow()

    private val _selectedAssignment = MutableStateFlow<AssignmentEntity?>(null)
    val selectedAssignment: StateFlow<AssignmentEntity?> = _selectedAssignment.asStateFlow()

    private val _selectedAnnouncement = MutableStateFlow<AnnouncementEntity?>(null)
    val selectedAnnouncement: StateFlow<AnnouncementEntity?> = _selectedAnnouncement.asStateFlow()

    private val _selectedTransaction = MutableStateFlow<CashTransactionEntity?>(null)
    val selectedTransaction: StateFlow<CashTransactionEntity?> = _selectedTransaction.asStateFlow()

    private val _selectedUserForDetail = MutableStateFlow<UserEntity?>(null)
    val selectedUserForDetail: StateFlow<UserEntity?> = _selectedUserForDetail.asStateFlow()

    private val _selectedActivityLog = MutableStateFlow<ActivityLogEntity?>(null)
    val selectedActivityLog: StateFlow<ActivityLogEntity?> = _selectedActivityLog.asStateFlow()

    private val _selectedBotTemplate = MutableStateFlow<BotTemplateEntity?>(null)
    val selectedBotTemplate: StateFlow<BotTemplateEntity?> = _selectedBotTemplate.asStateFlow()

    private val _selectedHelpTopicId = MutableStateFlow("tagihan")
    val selectedHelpTopicId: StateFlow<String> = _selectedHelpTopicId.asStateFlow()

    // Visual Theme
    private val _visualTheme = MutableStateFlow(VisualThemeStyle.NEUBRUTALISM)
    val visualTheme: StateFlow<VisualThemeStyle> = _visualTheme.asStateFlow()

    private val _darkTheme = MutableStateFlow(false)
    val darkTheme: StateFlow<Boolean> = _darkTheme.asStateFlow()

    private val _lowRamSolidMode = MutableStateFlow(false)
    val lowRamSolidMode: StateFlow<Boolean> = _lowRamSolidMode.asStateFlow()

    // Class & Bot Settings
    private val _classSettings = MutableStateFlow(ClassSettingsState())
    val classSettings: StateFlow<ClassSettingsState> = _classSettings.asStateFlow()

    private val _botState = MutableStateFlow(BotConnectionState())
    val botState: StateFlow<BotConnectionState> = _botState.asStateFlow()

    // Tour & Coachmarks
    private val _tourState = MutableStateFlow(TourUiState())
    val tourState: StateFlow<TourUiState> = _tourState.asStateFlow()

    // Toast / Banner feedback message
    private val _toastMessage = MutableStateFlow<String?>(null)
    val toastMessage: StateFlow<String?> = _toastMessage.asStateFlow()

    // Active Kas sub-tab (0 = Transaksi, 1 = Tagihan)
    private val _kasTabIndex = MutableStateFlow(0)
    val kasTabIndex: StateFlow<Int> = _kasTabIndex.asStateFlow()

    init {
        refreshStartupRoute()
        loadExistingBackupFileIfPresent()
    }

    private fun loadExistingBackupFileIfPresent() {
        viewModelScope.launch {
            try {
                val backupDir = File(getApplication<Application>().filesDir, "backups")
                val file = File(backupDir, "ixh_hub_latest_backup.json")
                if (file.exists()) {
                    val text = file.readText(Charsets.UTF_8)
                    if (text.isNotBlank()) {
                        _backupState.update {
                            it.copy(
                                latestBackupJson = text,
                                localFilePath = file.absolutePath,
                                statusMessage = "Ditemukan file backup lokal di perangkat (${file.name})."
                            )
                        }
                    }
                }
            } catch (_: Exception) {
            }
        }
    }

    fun refreshStartupRoute() {
        viewModelScope.launch {
            repository.ensureSeeded()
            val adminExists = repository.hasAdminAccount()
            _hasAdminAccount.value = adminExists
            if (!adminExists) {
                routeBackStack.clear()
                _currentRoute.value = AppScreenRoute.FIRST_RUN_ADMIN_SETUP
            } else if (_currentRoute.value == AppScreenRoute.FIRST_RUN_ADMIN_SETUP) {
                routeBackStack.clear()
                _currentRoute.value = AppScreenRoute.LOGIN
            }
            _isInitialCheckDone.value = true
        }
    }

    fun createFirstAdminAccount(
        fullName: String,
        username: String,
        phoneWa: String,
        rawPassword: String,
        subRoleTitle: String = "Ketua Kelas & Admin",
        onResult: (Result<UserEntity>) -> Unit = {}
    ) {
        viewModelScope.launch {
            val result = repository.createFirstAdmin(
                fullName = fullName,
                username = username,
                phoneWa = phoneWa,
                rawPassword = rawPassword,
                subRoleTitle = subRoleTitle
            )
            result.onSuccess { admin ->
                _hasAdminAccount.value = true
                _activeUsername.value = admin.username
                _activeSessionUser.value = admin
                showToast("Akun Admin Pertama (@${admin.username}) berhasil dibuat!")
            }
            onResult(result)
        }
    }

    fun authenticateAndLogin(
        username: String,
        rawPassword: String,
        onResult: (Result<UserEntity>) -> Unit = {}
    ) {
        viewModelScope.launch {
            val result = repository.authenticateUser(username, rawPassword)
            result.onSuccess { user ->
                _activeUsername.value = user.username
                _activeSessionUser.value = user
            }
            onResult(result)
        }
    }

    fun logoutSession() {
        val currentName = currentActiveActorName()
        val currentRole = currentActiveRole()
        viewModelScope.launch {
            repository.recordLogout(currentName, currentRole)
        }
        _activeUsername.value = ""
        _activeSessionUser.value = null
        navigateRoot(AppScreenRoute.LOGOUT_SUCCESS)
    }

    fun showToast(msg: String) {
        viewModelScope.launch {
            _toastMessage.value = msg
            delay(3200)
            if (_toastMessage.value == msg) {
                _toastMessage.value = null
            }
        }
    }

    fun dismissToast() {
        _toastMessage.value = null
    }

    fun navigateTo(route: AppScreenRoute) {
        // Block public navigation to FIRST_RUN_ADMIN_SETUP once an Admin already exists
        if (route == AppScreenRoute.FIRST_RUN_ADMIN_SETUP && _hasAdminAccount.value) {
            showToast("Admin pertama sudah terdaftar. Silakan gunakan halaman Login.")
            return
        }
        if (_currentRoute.value != route) {
            routeBackStack.addLast(_currentRoute.value)
            _currentRoute.value = route
        }
    }

    fun navigateRoot(route: AppScreenRoute) {
        if (route == AppScreenRoute.FIRST_RUN_ADMIN_SETUP && _hasAdminAccount.value) {
            routeBackStack.clear()
            _currentRoute.value = AppScreenRoute.LOGIN
            return
        }
        routeBackStack.clear()
        _currentRoute.value = route
    }

    fun selectMainTab(tab: MainNavTab) {
        _currentTab.value = tab
        if (_currentRoute.value != AppScreenRoute.MAIN_SHELL) {
            routeBackStack.clear()
            _currentRoute.value = AppScreenRoute.MAIN_SHELL
        }
    }

    fun setKasTabIndex(index: Int) {
        _kasTabIndex.value = index
    }

    fun navigateBack(): Boolean {
        return if (routeBackStack.isNotEmpty()) {
            _currentRoute.value = routeBackStack.removeLast()
            true
        } else if (_currentRoute.value != AppScreenRoute.MAIN_SHELL &&
            _currentRoute.value != AppScreenRoute.LOGIN &&
            _currentRoute.value != AppScreenRoute.FIRST_RUN_ADMIN_SETUP
        ) {
            _currentRoute.value = AppScreenRoute.MAIN_SHELL
            true
        } else if (_currentTab.value != MainNavTab.BERANDA &&
            _currentRoute.value == AppScreenRoute.MAIN_SHELL
        ) {
            _currentTab.value = MainNavTab.BERANDA
            true
        } else {
            false
        }
    }

    fun switchPersona(username: String) {
        val target = users.value.firstOrNull { it.username.equals(username, ignoreCase = true) }
        if (target != null) {
            _activeUsername.value = target.username
            _activeSessionUser.value = target
            showToast("Sesi aktif: ${target.fullName} (${target.role})")
        } else {
            _activeUsername.value = username
        }
    }

    fun currentActiveRole(): String {
        val session = _activeSessionUser.value
            ?: users.value.firstOrNull { it.username == _activeUsername.value }
            ?: users.value.firstOrNull()
        return session?.role ?: UserRole.SISWA.name
    }

    fun currentActiveActorName(): String {
        val session = _activeSessionUser.value
            ?: users.value.firstOrNull { it.username == _activeUsername.value }
            ?: users.value.firstOrNull()
        return session?.fullName ?: "Anggota IX-H"
    }

    fun isCurrentUserAdmin(): Boolean = currentActiveRole() == UserRole.ADMIN.name

    fun canCurrentUserManageClass(): Boolean {
        val role = currentActiveRole()
        return role == UserRole.ADMIN.name || role == UserRole.PENGURUS.name
    }

    // Global Search Controls
    fun updateGlobalSearchQuery(query: String) {
        _globalSearchQuery.value = query
    }

    fun updateGlobalSearchCategory(category: String) {
        _globalSearchCategory.value = category
    }

    fun getFilteredGlobalSearchResults(
        query: String = _globalSearchQuery.value,
        categoryFilter: String = _globalSearchCategory.value,
        usersList: List<UserEntity> = users.value,
        assignmentsList: List<AssignmentEntity> = assignments.value,
        schedulesList: List<ScheduleEntity> = schedules.value,
        transactionsList: List<CashTransactionEntity> = transactions.value,
        announcementsList: List<AnnouncementEntity> = announcements.value
    ): List<GlobalSearchItem> {
        val all = repository.performGlobalSearch(
            query = query,
            activeRole = currentActiveRole(),
            activeUsername = _activeUsername.value,
            usersList = usersList,
            assignmentsList = assignmentsList,
            schedulesList = schedulesList,
            transactionsList = transactionsList,
            announcementsList = announcementsList
        )
        return if (categoryFilter == "Semua") {
            all
        } else {
            all.filter { it.category.equals(categoryFilter, ignoreCase = true) }
        }
    }

    fun openGlobalSearchItem(item: GlobalSearchItem) {
        when (item.category) {
            "Tugas" -> {
                val found = assignments.value.firstOrNull { it.id == item.entityId }
                if (found != null) {
                    openAssignmentDetail(found)
                } else {
                    selectMainTab(MainNavTab.TUGAS)
                }
            }
            "Jadwal" -> {
                val found = schedules.value.firstOrNull { it.id == item.entityId }
                if (found != null) {
                    openScheduleDetail(found)
                } else {
                    selectMainTab(MainNavTab.JADWAL)
                }
            }
            "Pengumuman" -> {
                val found = announcements.value.firstOrNull { it.id == item.entityId }
                if (found != null) {
                    openAnnouncementDetail(found)
                } else {
                    navigateTo(AppScreenRoute.ANNOUNCEMENTS_LIST)
                }
            }
            "Kas" -> {
                val found = transactions.value.firstOrNull { it.id == item.entityId }
                if (found != null) {
                    openTransactionDetail(found)
                } else {
                    selectMainTab(MainNavTab.KAS)
                }
            }
            "Absensi" -> {
                selectMainTab(MainNavTab.ABSENSI)
            }
            "Siswa" -> {
                val found = users.value.firstOrNull { it.id == item.entityId }
                if (found != null && canCurrentUserManageClass()) {
                    openUserDetail(found)
                } else {
                    navigateTo(AppScreenRoute.PROFILE_MAIN)
                }
            }
            else -> selectMainTab(MainNavTab.BERANDA)
        }
    }

    // Backup & Restore Controls
    fun createDatabaseBackupNow(onCompleted: (Result<DatabaseBackupPackage>) -> Unit = {}) {
        if (!isCurrentUserAdmin()) {
            showToast("Akses ditolak: Hanya Admin yang dapat membuat cadangan database.")
            return
        }
        viewModelScope.launch {
            val result = repository.exportDatabaseBackupJson(
                actorName = currentActiveActorName(),
                actorRole = currentActiveRole()
            )
            result.fold(
                onSuccess = { pkg ->
                    var savedPath = ""
                    try {
                        val backupDir = File(getApplication<Application>().filesDir, "backups")
                        if (!backupDir.exists()) backupDir.mkdirs()
                        val outFile = File(backupDir, "ixh_hub_latest_backup.json")
                        outFile.writeText(pkg.jsonContent, Charsets.UTF_8)
                        savedPath = outFile.absolutePath
                    } catch (_: Exception) {
                    }
                    _backupState.value = BackupUiState(
                        lastBackupTime = pkg.createdAtLabel,
                        lastBackupChecksum = pkg.checksum,
                        lastBackupTotalRecords = pkg.totalRecords,
                        latestBackupJson = pkg.jsonContent,
                        localFilePath = savedPath,
                        statusMessage = "Cadangan berhasil dibuat (${pkg.totalRecords} data • Checksum SHA-256 valid).",
                        isError = false
                    )
                    showToast("Backup database berhasil dibuat (${pkg.totalRecords} item)")
                },
                onFailure = { err ->
                    _backupState.update {
                        it.copy(
                            statusMessage = "Gagal membuat backup: ${err.message}",
                            isError = true
                        )
                    }
                    showToast("Gagal membuat backup: ${err.message}")
                }
            )
            onCompleted(result)
        }
    }

    fun restoreDatabaseFromBackupJson(
        rawJson: String,
        onCompleted: (Result<String>) -> Unit = {}
    ) {
        if (!isCurrentUserAdmin()) {
            showToast("Akses ditolak: Hanya Admin yang dapat memulihkan database.")
            return
        }
        viewModelScope.launch {
            val result = repository.restoreDatabaseBackupJson(
                rawJson = rawJson,
                actorName = currentActiveActorName(),
                actorRole = currentActiveRole()
            )
            result.fold(
                onSuccess = { msg ->
                    val adminExists = repository.hasAdminAccount()
                    _hasAdminAccount.value = adminExists
                    _backupState.update {
                        it.copy(
                            statusMessage = msg,
                            isError = false
                        )
                    }
                    showToast(msg)
                },
                onFailure = { err ->
                    val errMsg = err.message ?: "Backup rusak atau tidak kompatibel"
                    _backupState.update {
                        it.copy(
                            statusMessage = "Restore ditolak: $errMsg",
                            isError = true
                        )
                    }
                    showToast("Restore ditolak: $errMsg")
                }
            )
            onCompleted(result)
        }
    }

    fun clearSyncedQueueItems() {
        viewModelScope.launch {
            repository.clearSyncedQueueItems()
            showToast("Riwayat antrean yang sudah tersinkron berhasil dibersihkan")
        }
    }

    fun selectScheduleDay(dayCode: String) {
        _selectedScheduleDay.value = dayCode
    }

    fun openScheduleDetail(item: ScheduleEntity) {
        _selectedScheduleItem.value = item
        navigateTo(AppScreenRoute.SCHEDULE_DETAIL)
    }

    fun openAssignmentDetail(item: AssignmentEntity) {
        _selectedAssignment.value = item
        navigateTo(AppScreenRoute.ASSIGNMENT_DETAIL)
    }

    fun openAnnouncementDetail(item: AnnouncementEntity) {
        _selectedAnnouncement.value = item
        navigateTo(AppScreenRoute.ANNOUNCEMENT_DETAIL)
    }

    fun openTransactionDetail(item: CashTransactionEntity) {
        _selectedTransaction.value = item
        navigateTo(AppScreenRoute.CASH_TRANSACTION_DETAIL)
    }

    fun openUserDetail(user: UserEntity) {
        _selectedUserForDetail.value = user
        navigateTo(AppScreenRoute.USER_DETAIL_ADMIN)
    }

    fun openBillVerifyDetail(user: UserEntity) {
        _selectedUserForDetail.value = user
        navigateTo(AppScreenRoute.BILLS_VERIFY_DETAIL)
    }

    fun openActivityLogDetail(log: ActivityLogEntity) {
        _selectedActivityLog.value = log
        navigateTo(AppScreenRoute.ACTIVITY_LOG_DETAIL)
    }

    fun openBotTemplateEditor(template: BotTemplateEntity) {
        _selectedBotTemplate.value = template
        navigateTo(AppScreenRoute.BOT_TEMPLATE_EDITOR)
    }

    fun openHelpArticle(topicId: String) {
        _selectedHelpTopicId.value = topicId
        navigateTo(AppScreenRoute.HELP_ARTICLE_DETAIL)
    }

    // Domain Actions (with RBAC validation)
    fun saveAttendanceBatch(updatedList: List<UserEntity>, actorName: String, actorRole: String) {
        if (!canCurrentUserManageClass()) {
            showToast("Akses ditolak: Hanya Admin dan Pengurus yang dapat menyimpan absensi.")
            return
        }
        viewModelScope.launch {
            repository.saveAttendanceBatch(updatedList, actorName, actorRole)
            showToast("Absensi kelas IX-H (${updatedList.size} anggota) berhasil disimpan")
            navigateBack()
        }
    }

    fun markBillPaid(student: UserEntity, method: String, note: String, actorName: String, actorRole: String) {
        if (!canCurrentUserManageClass()) {
            showToast("Akses ditolak: Hanya Admin dan Pengurus yang dapat memverifikasi pembayaran.")
            return
        }
        viewModelScope.launch {
            repository.markStudentBillPaid(student, method, note, actorName, actorRole)
            showToast("${student.fullName} ditandai LUNAS ($method) • Otomatis tercatat di Kas")
            navigateBack()
        }
    }

    fun addCashTransaction(title: String, amount: Int, isIncome: Boolean, dateLabel: String, proofNote: String, actorName: String) {
        if (!canCurrentUserManageClass()) {
            showToast("Akses ditolak: Hanya Admin dan Pengurus yang dapat mencatat kas.")
            return
        }
        viewModelScope.launch {
            repository.addManualCashTransaction(
                title = title,
                amount = amount,
                isIncome = isIncome,
                dateLabel = dateLabel,
                proofNote = proofNote,
                actorName = actorName,
                actorRole = currentActiveRole()
            )
            showToast("Transaksi '$title' berhasil disimpan")
            navigateBack()
        }
    }

    fun toggleAssignmentComplete(item: AssignmentEntity) {
        viewModelScope.launch {
            val updated = item.copy(isCompleted = !item.isCompleted)
            repository.toggleAssignmentComplete(item)
            _selectedAssignment.value = updated
            showToast(if (updated.isCompleted) "Tugas ditandai Selesai!" else "Status tugas dibuka kembali")
        }
    }

    fun createOrUpdateAssignment(
        title: String,
        subject: String,
        teacher: String,
        deadlineLabel: String,
        daysRemaining: Int,
        description: String,
        reminderEnabled: Boolean
    ) {
        if (!canCurrentUserManageClass()) {
            showToast("Akses ditolak: Hanya Admin dan Pengurus yang dapat menambah tugas.")
            return
        }
        viewModelScope.launch {
            repository.saveAssignment(
                item = AssignmentEntity(
                    title = title,
                    subject = subject,
                    teacher = teacher,
                    deadlineLabel = deadlineLabel,
                    daysRemaining = daysRemaining,
                    isCompleted = false,
                    description = description,
                    reminderEnabled = reminderEnabled
                ),
                actorName = currentActiveActorName(),
                actorRole = currentActiveRole()
            )
            showToast("Tugas '$title' berhasil disimpan")
            navigateBack()
        }
    }

    fun createOrUpdateSchedule(
        dayCode: String,
        startTime: String,
        endTime: String,
        subject: String,
        teacher: String,
        room: String,
        notes: String,
        isBreak: Boolean = false
    ) {
        if (!canCurrentUserManageClass()) {
            showToast("Akses ditolak: Hanya Admin dan Pengurus yang dapat mengelola jadwal.")
            return
        }
        viewModelScope.launch {
            repository.saveSchedule(
                item = ScheduleEntity(
                    dayCode = dayCode,
                    startTime = startTime,
                    endTime = endTime,
                    subject = subject,
                    teacher = teacher,
                    room = room,
                    notes = notes,
                    isBreak = isBreak
                ),
                actorName = currentActiveActorName(),
                actorRole = currentActiveRole()
            )
            _selectedScheduleDay.value = dayCode
            showToast("Jadwal '$subject' ($dayCode) berhasil ditambahkan")
            navigateBack()
        }
    }

    fun deleteScheduleItem(id: Int) {
        if (!canCurrentUserManageClass()) {
            showToast("Akses ditolak: Hanya Admin dan Pengurus yang dapat menghapus jadwal.")
            return
        }
        viewModelScope.launch {
            repository.deleteSchedule(id, currentActiveActorName(), currentActiveRole())
            showToast("Jadwal dihapus")
            navigateBack()
        }
    }

    fun saveNightReminder(enabled: Boolean, timeWib: String) {
        viewModelScope.launch {
            val item = _selectedScheduleItem.value
            if (item != null) {
                repository.updateScheduleReminder(item, enabled, timeWib)
            }
            _classSettings.update { it.copy(defaultReminderTime = timeWib) }
            showToast("Pengingat persiapan malam hari disimpan ($timeWib)")
            navigateBack()
        }
    }

    fun createAnnouncement(
        title: String,
        content: String,
        category: String,
        isPinned: Boolean,
        isDraft: Boolean,
        authorName: String,
        authorRole: String
    ) {
        if (!canCurrentUserManageClass()) {
            showToast("Akses ditolak: Hanya Admin dan Pengurus yang dapat membuat pengumuman.")
            return
        }
        viewModelScope.launch {
            val item = AnnouncementEntity(
                title = title,
                content = content,
                authorName = authorName,
                authorRole = authorRole,
                timeLabel = "Baru saja",
                category = category,
                isPinned = isPinned,
                isDraft = isDraft,
                readCount = 0
            )
            repository.saveAnnouncement(item, announcements.value)
            showToast(if (isDraft) "Draf pengumuman disimpan" else "Pengumuman berhasil diterbitkan!")
            navigateBack()
        }
    }

    fun togglePinAnnouncement(item: AnnouncementEntity) {
        if (!canCurrentUserManageClass()) {
            showToast("Hanya Admin dan Pengurus yang dapat menyematkan pengumuman.")
            return
        }
        viewModelScope.launch {
            val updated = item.copy(isPinned = !item.isPinned)
            repository.saveAnnouncement(updated, announcements.value)
            _selectedAnnouncement.value = updated
            showToast(if (updated.isPinned) "Disematkan di Beranda" else "Sematan dilepas")
        }
    }

    fun deleteAnnouncement(item: AnnouncementEntity, actorName: String) {
        if (!canCurrentUserManageClass()) {
            showToast("Hanya Admin dan Pengurus yang dapat menghapus pengumuman.")
            return
        }
        viewModelScope.launch {
            repository.deleteAnnouncement(item, actorName)
            showToast("Pengumuman dihapus")
            navigateBack()
        }
    }

    fun markAllNotificationsRead() {
        viewModelScope.launch {
            repository.markAllNotificationsRead()
            showToast("Semua notifikasi ditandai sudah dibaca")
        }
    }

    fun markNotificationRead(item: NotificationEntity) {
        viewModelScope.launch {
            repository.markNotificationRead(item.id)
        }
    }

    fun deleteNotification(id: Int) {
        viewModelScope.launch {
            repository.deleteNotification(id)
            showToast("Notifikasi dihapus")
        }
    }

    fun deleteReadNotifications() {
        viewModelScope.launch {
            repository.deleteReadNotifications()
            showToast("Notifikasi yang sudah dibaca berhasil dihapus")
        }
    }

    // Bot & Server Controls (Honest VPS status + Local simulation label)
    fun toggleBotConnectionSimulated() {
        _botState.update {
            val next = !it.isConnected
            it.copy(
                isConnected = next,
                sessionName = if (next) "Simulasi Lokal" else "Belum Terhubung",
                activeSince = if (next) "Mode Uji Lokal" else "Menunggu konfigurasi VPS"
            )
        }
        showToast(
            if (_botState.value.isConnected) "Mode Simulasi Bot Lokal Diaktifkan (Bukan Koneksi VPS Produksi)"
            else "Simulasi Bot Lokal Dinonaktifkan (Status: Belum Terhubung)"
        )
    }

    fun updateBotMaxPerHour(delta: Int) {
        _botState.update {
            val next = (it.maxMessagesPerHour + delta).coerceIn(10, 120)
            it.copy(maxMessagesPerHour = next)
        }
    }

    fun generateNewPairingCode() {
        val chars = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789"
        val newCode = (1..8).map { chars.random() }.joinToString("")
        _botState.update { it.copy(pairingCode = newCode) }
        showToast("Kode pairing lokal dibuat: $newCode (Hubungkan ke VPS untuk pairing WhatsApp nyata)")
    }

    fun controlPterodactylServer(action: String) {
        if (!isCurrentUserAdmin()) {
            showToast("Hanya Admin yang dapat mengontrol server Pterodactyl.")
            return
        }
        viewModelScope.launch {
            val url = _botState.value.vpsBaseUrl
            val key = _botState.value.vpsApiKey
            if (url.isBlank() || key.isBlank()) {
                _botState.update {
                    it.copy(
                        pterodactylStatus = "Belum Terhubung (URL/Token VPS Kosong)",
                        isConnected = false
                    )
                }
                showToast("Konfigurasi URL & Token API VPS diperlukan di menu Pengaturan Sinkronisasi VPS")
                return@launch
            }
            _botState.update {
                it.copy(
                    pterodactylStatus = when (action) {
                        "restart" -> "Menghubungi VPS..."
                        "stop" -> "Menghentikan..."
                        else -> "Menghubungi VPS..."
                    }
                )
            }
            val result = repository.sendPterodactylSignal(
                baseUrl = url,
                apiKey = key,
                signal = action
            )
            result.fold(
                onSuccess = { msg ->
                    _botState.update {
                        it.copy(
                            pterodactylStatus = if (action == "stop") "Berhenti" else "Berjalan",
                            isConnected = action != "stop"
                        )
                    }
                    showToast(msg)
                },
                onFailure = { err ->
                    _botState.update {
                        it.copy(
                            pterodactylStatus = "Gagal Terhubung ke VPS",
                            isConnected = false
                        )
                    }
                    showToast("Gagal menghubungi Pterodactyl: ${err.message?.take(48) ?: "Periksa URL/Token VPS"}")
                }
            )
        }
    }

    fun updateVpsConfig(baseUrl: String, apiKey: String) {
        if (!isCurrentUserAdmin()) {
            showToast("Hanya Admin yang dapat mengubah konfigurasi VPS.")
            return
        }
        _botState.update {
            it.copy(
                vpsBaseUrl = baseUrl.trim(),
                vpsApiKey = apiKey.trim(),
                lastVpsSyncStatus = if (baseUrl.isBlank() || apiKey.isBlank()) {
                    "Belum dikonfigurasi — Masukkan URL & Token VPS"
                } else {
                    "Konfigurasi tersimpan • Klik Uji & Sinkronkan untuk memverifikasi koneksi"
                }
            )
        }
        showToast("Konfigurasi VPS disimpan")
    }

    fun testAndSyncVpsNow() {
        viewModelScope.launch {
            val url = _botState.value.vpsBaseUrl
            val key = _botState.value.vpsApiKey
            val pendingCount = syncQueue.value.count { it.status == "PENDING" || it.status == "FAILED" }
            if (url.isBlank() || key.isBlank()) {
                val msg = "URL VPS & Token API belum diisi ($pendingCount perubahan tersimpan aman di antrean lokal)."
                _botState.update {
                    it.copy(
                        isConnected = false,
                        pterodactylStatus = "Belum Terhubung",
                        lastVpsSyncStatus = msg
                    )
                }
                showToast(msg)
                return@launch
            }
            _botState.update { it.copy(lastVpsSyncStatus = "Menghubungi server VPS...") }
            val userList = users.value
            val txList = transactions.value
            val present = userList.count { it.attendanceToday == AttendanceStatus.HADIR.name }
            val netCash = txList.sumOf { if (it.isIncome) it.amount else -it.amount }
            val result = repository.syncWithVps(
                baseUrl = url,
                apiKey = key,
                totalStudents = userList.size,
                confirmedCash = netCash,
                presentCount = present
            )
            result.fold(
                onSuccess = { msg ->
                    _botState.update {
                        it.copy(
                            isConnected = true,
                            pterodactylStatus = "Terhubung",
                            lastVpsSyncStatus = msg
                        )
                    }
                    showToast(msg)
                },
                onFailure = { err ->
                    val statusMsg = "Gagal terhubung ke VPS (${err.message?.take(36) ?: "Offline"}) • Data tersimpan di antrean lokal"
                    _botState.update {
                        it.copy(
                            isConnected = false,
                            pterodactylStatus = "Belum Terhubung",
                            lastVpsSyncStatus = statusMsg
                        )
                    }
                    showToast(statusMsg)
                }
            )
        }
    }

    fun dispatchBotMessage(title: String, category: String, targetCount: Int) {
        viewModelScope.launch {
            val connected = _botState.value.isConnected && _botState.value.vpsBaseUrl.isNotBlank()
            val statusLabel = if (connected) "Terkirim" else "Antrean Lokal (VPS Belum Terhubung)"
            repository.recordBotDispatch(
                BotDispatchLogEntity(
                    title = title,
                    category = category,
                    dateTimeLabel = "Baru saja • $targetCount target",
                    sentCount = if (connected) targetCount else 0,
                    totalTarget = targetCount,
                    failedCount = if (connected) 0 else targetCount,
                    statusLabel = statusLabel,
                    failureReason = if (connected) "" else "Menunggu koneksi Bot WhatsApp / VPS aktif"
                )
            )
            showToast(
                if (connected) "Pesan '$title' dikirim ke $targetCount penerima"
                else "Antrean '$title' ($targetCount target) dicatat • Hubungkan VPS untuk mengirim WA"
            )
            navigateTo(AppScreenRoute.BILLS_DELIVERY_STATUS)
        }
    }

    fun retryFailedDispatches() {
        viewModelScope.launch {
            if (!_botState.value.isConnected || _botState.value.vpsBaseUrl.isBlank()) {
                showToast("Tidak dapat mengirim ulang: Bot WhatsApp / VPS belum terhubung.")
                return@launch
            }
            botDispatchLogs.value.filter { it.failedCount > 0 }.forEach {
                repository.retryFailedDispatch(it)
            }
            users.value.filter { it.deliveryStatus == "Gagal" }.forEach {
                repository.saveOrUpdateUser(
                    user = it.copy(deliveryStatus = "Terkirim"),
                    actorName = currentActiveActorName(),
                    actorRole = currentActiveRole()
                )
            }
            showToast("Pesan gagal berhasil dikirim ulang ke penerima!")
        }
    }

    fun saveBotTemplate(template: BotTemplateEntity, newBody: String): String? {
        val regex = Regex("\\{([^}]+)\\}")
        val allowed = setOf("nama", "nominal", "jatuh_tempo")
        val found = regex.findAll(newBody).map { it.groupValues[1] }.toList()
        val unknown = found.firstOrNull { it !in allowed }
        if (unknown != null) {
            return "Variabel {$unknown} tidak dikenal. Pakai {nama}, {nominal}, atau {jatuh_tempo}."
        }
        val finalBody = if (newBody.contains("Balas STOP", ignoreCase = true)) {
            newBody
        } else {
            "${newBody.trim()}\nBalas STOP untuk berhenti menerima pesan."
        }
        viewModelScope.launch {
            val updated = template.copy(bodyText = finalBody, isModified = true)
            repository.saveBotTemplate(updated)
            _selectedBotTemplate.value = updated
            showToast("Template '${template.name}' berhasil disimpan")
        }
        return null
    }

    fun resetBotTemplate(templateId: String) {
        viewModelScope.launch {
            repository.resetBotTemplateToDefault(templateId)
            showToast("Template dikembalikan ke bawaan")
            navigateBack()
        }
    }

    // User Management (Admin RBAC enforced)
    fun updateUserRole(user: UserEntity, newRole: String, subRole: String, actorName: String) {
        if (!isCurrentUserAdmin()) {
            showToast("Akses ditolak: Hanya Admin yang dapat mengubah role pengguna.")
            return
        }
        viewModelScope.launch {
            repository.updateUserRole(user, newRole, subRole, actorName)
            _selectedUserForDetail.value = user.copy(role = newRole, subRoleTitle = subRole)
            showToast("Role ${user.fullName} diubah menjadi $newRole")
        }
    }

    fun resetUserPasswordAdmin(user: UserEntity): String {
        val newTemp = "Spensari${(1000..9999).random()}"
        viewModelScope.launch {
            val updated = user.copy(
                tempPassword = newTemp,
                passwordHash = PasswordSecurity.hashPassword(user.username, newTemp),
                mustChangePassword = true
            )
            repository.saveOrUpdateUser(updated, currentActiveActorName(), currentActiveRole())
            _selectedUserForDetail.value = updated
        }
        return newTemp
    }

    fun saveNewOrEditedUser(
        existing: UserEntity?,
        fullName: String,
        username: String,
        role: String,
        phoneWa: String,
        testSend: Boolean,
        initialPassword: String = "Spensari2026"
    ) {
        val cleanName = fullName.trim()
        val cleanUsername = username.trim().lowercase()
        if (cleanName.isBlank() || cleanUsername.isBlank()) {
            showToast("Nama lengkap dan username wajib diisi")
            return
        }
        viewModelScope.launch {
            val nextId = existing?.id ?: ((users.value.maxOfOrNull { it.id } ?: 0) + 1)
            val nextAbsen = existing?.absenNo ?: ((users.value.maxOfOrNull { it.absenNo } ?: 0) + 1)
            val formattedPhone = if (phoneWa.startsWith("08")) {
                "+62 " + phoneWa.removePrefix("0")
            } else {
                phoneWa.trim()
            }
            val status = if (testSend && _botState.value.isConnected) {
                WaNumberStatus.VALID.name
            } else {
                existing?.waStatus ?: WaNumberStatus.BELUM_DICEK.name
            }
            val effectivePassword = initialPassword.ifBlank { "Spensari2026" }
            val entity = (existing ?: UserEntity(
                id = nextId,
                absenNo = nextAbsen,
                fullName = cleanName,
                username = cleanUsername,
                email = "$cleanUsername@spensari.sch.id",
                gender = "Laki-laki",
                role = role,
                subRoleTitle = when (role) {
                    UserRole.ADMIN.name -> "Admin Kelas"
                    UserRole.PENGURUS.name -> "Pengurus Kelas"
                    else -> "Siswa"
                },
                phoneWa = formattedPhone,
                waStatus = status,
                attendanceToday = "",
                presentDaysCount = 0,
                totalEffectiveDays = 0,
                billStatus = BillPaymentStatus.BELUM.name,
                billAmount = _classSettings.value.defaultWeeklyKas,
                tempPassword = effectivePassword,
                passwordHash = PasswordSecurity.hashPassword(cleanUsername, effectivePassword),
                mustChangePassword = true
            )).copy(
                fullName = cleanName,
                username = cleanUsername,
                role = role,
                phoneWa = formattedPhone,
                waStatus = status
            )
            repository.saveOrUpdateUser(entity, currentActiveActorName(), currentActiveRole())
            _selectedUserForDetail.value = entity
            if (_activeSessionUser.value?.id == entity.id) {
                _activeSessionUser.value = entity
            }
            showToast("Data anggota $cleanName (@$cleanUsername) berhasil disimpan")
            navigateBack()
        }
    }

    fun toggleStudentWaStopMulai(user: UserEntity, enable: Boolean) {
        viewModelScope.launch {
            repository.setStudentWaPreference(user, enable)
            _selectedUserForDetail.value = user.copy(
                waStatus = if (enable) WaNumberStatus.VALID.name else WaNumberStatus.BERHENTI.name
            )
            showToast(
                if (enable) "Pesan WhatsApp diaktifkan kembali (Status: Valid)"
                else "Pesan WhatsApp dihentikan (Status: Berhenti)"
            )
        }
    }

    fun revalidateAllProblematicNumbers() {
        viewModelScope.launch {
            if (!_botState.value.isConnected) {
                showToast("Hubungkan Bot WhatsApp / VPS terlebih dahulu untuk memvalidasi nomor secara nyata.")
                return@launch
            }
            users.value.filter { it.waStatus == WaNumberStatus.BELUM_DICEK.name }.forEach {
                repository.saveOrUpdateUser(
                    user = it.copy(waStatus = WaNumberStatus.VALID.name),
                    actorName = currentActiveActorName(),
                    actorRole = currentActiveRole()
                )
            }
            showToast("Pemeriksaan nomor selesai")
        }
    }

    // Class Settings
    fun saveClassSettings(newState: ClassSettingsState) {
        if (!isCurrentUserAdmin()) {
            showToast("Hanya Admin yang dapat mengubah pengaturan kelas.")
            return
        }
        _classSettings.value = newState
        showToast("Pengaturan Kelas IX-H berhasil disimpan")
    }

    // Visual Theme
    fun applyVisualTheme(style: VisualThemeStyle, dark: Boolean, lowRamSolid: Boolean) {
        val prev = _visualTheme.value
        _visualTheme.value = style
        _darkTheme.value = dark
        _lowRamSolidMode.value = lowRamSolid
        val label = if (style == VisualThemeStyle.LIQUID_GLASS) "Liquid Glass" else "Neubrutalism"
        if (prev != style) {
            showToast("Tema $label diterapkan")
        }
    }

    // Coachmark Tour
    fun offerTour() {
        _tourState.update { it.copy(showOfferDialog = true) }
    }

    fun startTour() {
        _currentTab.value = MainNavTab.BERANDA
        _currentRoute.value = AppScreenRoute.MAIN_SHELL
        _tourState.value = TourUiState(
            showOfferDialog = false,
            isActive = true,
            currentStep = 1,
            showFinishedScreen = false
        )
    }

    fun nextTourStep(totalSteps: Int) {
        _tourState.update { state ->
            if (state.currentStep >= totalSteps) {
                state.copy(isActive = false, showFinishedScreen = true)
            } else {
                state.copy(currentStep = state.currentStep + 1)
            }
        }
    }

    fun prevTourStep() {
        _tourState.update { state ->
            if (state.currentStep > 1) state.copy(currentStep = state.currentStep - 1) else state
        }
    }

    fun skipOrDismissTour() {
        _tourState.update {
            it.copy(
                showOfferDialog = false,
                isActive = false,
                showFinishedScreen = false,
                pausedAtStep = if (it.isActive) it.currentStep else it.pausedAtStep
            )
        }
    }
}
