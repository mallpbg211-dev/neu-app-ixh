package com.example

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.data.local.AnnouncementEntity
import com.example.data.local.AssignmentEntity
import com.example.data.local.AttendanceStatus
import com.example.data.local.BillPaymentStatus
import com.example.data.local.IxHubDatabase
import com.example.data.local.PasswordSecurity
import com.example.data.local.ScheduleEntity
import com.example.data.local.UserEntity
import com.example.data.local.UserRole
import com.example.data.repository.IxHubRepository
import com.example.ui.screens.ActivityLogsScreen
import com.example.ui.screens.AnnouncementsListScreen
import com.example.ui.screens.AttendanceMainTabScreen
import com.example.ui.screens.BackupRestoreScreen
import com.example.ui.screens.BotPanelScreen
import com.example.ui.screens.CashMainTabScreen
import com.example.ui.screens.DashboardScreen
import com.example.ui.screens.FirstRunAdminSetupScreen
import com.example.ui.screens.GlobalSearchScreen
import com.example.ui.screens.LoginScreen
import com.example.ui.screens.NotificationsCenterScreen
import com.example.ui.screens.ScheduleMainTabScreen
import com.example.ui.screens.TasksMainTabScreen
import com.example.ui.theme.IxhBackgroundCream
import com.example.ui.theme.IxhCharcoal
import com.example.ui.theme.IxhError
import com.example.ui.theme.IxhInfo
import com.example.ui.theme.IxhLightYellow
import com.example.ui.theme.IxhMutedBorder
import com.example.ui.theme.IxhPrimaryYellow
import com.example.ui.theme.IxhSecondaryYellow
import com.example.ui.theme.IxhSuccess
import com.example.ui.theme.IxhSurfacePrimary
import com.example.ui.theme.IxhSurfaceSecondary
import com.example.ui.theme.IxhWarning
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.BackupUiState
import com.example.ui.viewmodel.BotConnectionState
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode
import java.io.File
import java.io.FileOutputStream

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(sdk = [35], qualifiers = "w412dp-h915dp-xxhdpi")
class ExampleRobolectricTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var db: IxHubDatabase
    private lateinit var repository: IxHubRepository

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, IxHubDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        repository = IxHubRepository(db.dao())
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun verifyDesignSystemColorTokens() {
        assertEquals(0xFFFFC928.toInt(), IxhPrimaryYellow.value.shr(32).toInt())
        assertEquals(0xFFFFD95A.toInt(), IxhSecondaryYellow.value.shr(32).toInt())
        assertEquals(0xFFFFF0B8.toInt(), IxhLightYellow.value.shr(32).toInt())
        assertEquals(0xFFFDF8EC.toInt(), IxhBackgroundCream.value.shr(32).toInt())
        assertEquals(0xFFFFFFFF.toInt(), IxhSurfacePrimary.value.shr(32).toInt())
        assertEquals(0xFFF2ECE0.toInt(), IxhSurfaceSecondary.value.shr(32).toInt())
        assertEquals(0xFF1C1B1F.toInt(), IxhCharcoal.value.shr(32).toInt())
        assertEquals(0xFFE6DEC8.toInt(), IxhMutedBorder.value.shr(32).toInt())
        assertEquals(0xFF35A66B.toInt(), IxhSuccess.value.shr(32).toInt())
        assertEquals(0xFFF2A93B.toInt(), IxhWarning.value.shr(32).toInt())
        assertEquals(0xFFE34B4B.toInt(), IxhError.value.shr(32).toInt())
        assertEquals(0xFF4A91D9.toInt(), IxhInfo.value.shr(32).toInt())
    }

    @Test
    fun verifyEmptyOperationalDatabaseAndFirstRunAdminSetupFlow() = runBlocking {
        // 1. Verify ensureSeeded() inserts zero operational data
        repository.ensureSeeded()
        assertEquals(0, repository.users.first().size)
        assertEquals(0, repository.transactions.first().size)
        assertEquals(0, repository.schedules.first().size)
        assertEquals(0, repository.assignments.first().size)
        assertEquals(0, repository.announcements.first().size)
        assertEquals(0, repository.notifications.first().size)
        assertEquals(0, repository.activityLogs.first().size)
        assertEquals(0, repository.botDispatchLogs.first().size)
        assertEquals(0, repository.syncQueue.first().size)
        assertFalse(repository.hasAdminAccount())

        // 2. Render FirstRunAdminSetupScreen and capture screenshot
        var rootView: View? = null
        var createdAdmin: UserEntity? = null
        composeTestRule.setContent {
            rootView = LocalView.current.rootView
            MyApplicationTheme {
                FirstRunAdminSetupScreen(
                    onCreateFirstAdmin = { fullName, uname, phoneWa, pass, subRoleTitle, onResult ->
                        runBlocking {
                            val res = repository.createFirstAdmin(fullName, uname, phoneWa, pass, subRoleTitle)
                            createdAdmin = res.getOrNull()
                            onResult(res)
                        }
                    },
                    onSetupCompleted = { createdAdmin = it }
                )
            }
        }
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText("SETUP AWAL • DATABASE KOSONG").assertIsDisplayed()
        composeTestRule.onNodeWithText("Buat Admin Pertama").assertIsDisplayed()
        saveRootViewScreenshot(rootView, "final_01_first_admin_setup.png")

        // 3. Fill valid Admin account details & submit
        composeTestRule.onNodeWithTag("first_admin_fullname_input").performTextInput("Ahmad Ramadhan")
        composeTestRule.onNodeWithTag("first_admin_username_input").performTextInput("ahmad.admin")
        composeTestRule.onNodeWithTag("first_admin_phone_input").performTextInput("081210012026")
        composeTestRule.onNodeWithTag("first_admin_password_input").performTextInput("Spensari2026")
        composeTestRule.onNodeWithTag("first_admin_confirm_password_input").performTextInput("Spensari2026")
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag("first_admin_submit_button").performClick()
        composeTestRule.waitForIdle()

        assertNotNull(createdAdmin)
        assertEquals(UserRole.ADMIN.name, createdAdmin?.role)
        assertTrue(repository.hasAdminAccount())
        assertTrue(PasswordSecurity.verifyPassword("ahmad.admin", "Spensari2026", createdAdmin!!.passwordHash))

        // 4. Verify second call to createFirstAdmin is rejected (locked after first Admin exists)
        val duplicateAttempt = repository.createFirstAdmin(
            "Admin Kedua",
            "admin.dua",
            "081299998888",
            "Spensari2026"
        )
        assertTrue(duplicateAttempt.isFailure)
    }

    @Test
    fun verifyCleanLoginScreenAndSha256Authentication() = runBlocking {
        repository.ensureSeeded()
        repository.createFirstAdmin(
            fullName = "Ahmad Ramadhan",
            username = "ahmad.admin",
            phoneWa = "081210012026",
            rawPassword = "Spensari2026"
        )
        val registered = repository.users.first()

        var rootView: View? = null
        var loggedInUsername: String? = null
        composeTestRule.setContent {
            rootView = LocalView.current.rootView
            MyApplicationTheme {
                LoginScreen(
                    onLoginSuccess = { loggedInUsername = it },
                    onForgotPassword = {},
                    onLockedAccount = {},
                    onStartOnboarding = {},
                    registeredUsers = registered
                )
            }
        }
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText("IX-H HUB").assertIsDisplayed()
        composeTestRule.onNodeWithText("Selamat Datang!").assertIsDisplayed()
        saveRootViewScreenshot(rootView, "final_02_login_clean.png")

        // Verify password show/hide interaction
        composeTestRule.onNodeWithTag("login_username_input").performTextInput("ahmad.admin")
        composeTestRule.onNodeWithTag("login_password_input").performTextInput("Spensari2026")
        composeTestRule.onNodeWithTag("toggle_password_visibility").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("toggle_password_visibility").performClick()
        composeTestRule.waitForIdle()

        // Submit login with SHA-256 verification
        composeTestRule.onNodeWithTag("login_submit_button").performClick()
        composeTestRule.waitForIdle()
        assertEquals("ahmad.admin", loggedInUsername)
    }

    @Test
    fun verifyEmptyStatesAcrossAllMainModules() = runBlocking {
        repository.ensureSeeded()
        val adminUser = repository.createFirstAdmin(
            fullName = "Ahmad Ramadhan",
            username = "ahmad.admin",
            phoneWa = "081210012026",
            rawPassword = "Spensari2026"
        ).getOrThrow()

        var currentModule by mutableStateOf("BERANDA")
        var rootView: View? = null

        composeTestRule.setContent {
            rootView = LocalView.current.rootView
            MyApplicationTheme {
                when (currentModule) {
                    "BERANDA" -> DashboardScreen(
                        activeUser = adminUser,
                        allUsers = emptyList(),
                        transactions = emptyList(),
                        pinnedAnnouncement = null,
                        closestAssignment = null,
                        todaySchedules = emptyList(),
                        unreadNotificationCount = 0,
                        botState = BotConnectionState(),
                        pendingSyncCount = 0,
                        onNavigateRoute = {},
                        onSelectMainTab = {},
                        onSwitchPersona = {},
                        onOpenKasTab = {}
                    )
                    "ABSENSI" -> AttendanceMainTabScreen(
                        activeUser = adminUser,
                        allUsers = emptyList(),
                        unreadNotificationCount = 0,
                        onNavigateRoute = {},
                        onSwitchPersona = {}
                    )
                    "KAS" -> CashMainTabScreen(
                        activeUser = adminUser,
                        users = emptyList(),
                        transactions = emptyList(),
                        unreadNotificationCount = 0,
                        selectedTabIndex = 0,
                        onSelectTabIndex = {},
                        onNavigate = {},
                        onOpenTransactionDetail = {},
                        onOpenBillVerifyDetail = {},
                        onOpenRoleSwitcher = {}
                    )
                    "JADWAL" -> ScheduleMainTabScreen(
                        activeUser = adminUser,
                        schedules = emptyList(),
                        selectedDayCode = "Sen",
                        unreadNotificationCount = 0,
                        onSelectDay = {},
                        onOpenScheduleDetail = {},
                        onNavigate = {},
                        onOpenRoleSwitcher = {}
                    )
                    "TUGAS" -> TasksMainTabScreen(
                        activeUser = adminUser,
                        assignments = emptyList(),
                        unreadNotificationCount = 0,
                        onOpenAssignmentDetail = {},
                        onToggleComplete = {},
                        onNavigate = {},
                        onOpenRoleSwitcher = {}
                    )
                    "PENGUMUMAN" -> AnnouncementsListScreen(
                        activeUser = adminUser,
                        announcements = emptyList(),
                        onOpenDetail = {},
                        onNavigateCreate = {},
                        onBack = {}
                    )
                    "NOTIFIKASI" -> NotificationsCenterScreen(
                        notifications = emptyList(),
                        onMarkAllRead = {},
                        onDeleteRead = {},
                        onNotificationClick = {},
                        onDeleteSingle = {},
                        onOpenSettings = {},
                        onBack = {}
                    )
                }
            }
        }
        composeTestRule.waitForIdle()

        // 1. Beranda Empty State
        composeTestRule.onNodeWithText("0 / 0").assertIsDisplayed()
        composeTestRule.onAllNodesWithText("Rp0").onFirst().assertIsDisplayed()
        composeTestRule.onNodeWithText("Belum ada pengumuman.").assertIsDisplayed()
        saveRootViewScreenshot(rootView, "final_03_dashboard_empty.png")

        // 2. Absensi Empty State
        currentModule = "ABSENSI"
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Belum ada data absensi.").assertIsDisplayed()
        saveRootViewScreenshot(rootView, "final_04_absensi_empty.png")

        // 3. Kas Empty State
        currentModule = "KAS"
        composeTestRule.waitForIdle()
        composeTestRule.onAllNodesWithText("Rp0").onFirst().assertIsDisplayed()
        composeTestRule.onNodeWithText("Belum ada transaksi kas.").assertIsDisplayed()
        saveRootViewScreenshot(rootView, "final_05_kas_empty.png")

        // 4. Jadwal Empty State
        currentModule = "JADWAL"
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Belum ada jadwal yang ditambahkan.").assertIsDisplayed()
        saveRootViewScreenshot(rootView, "final_06_jadwal_empty.png")

        // 5. Tugas Empty State
        currentModule = "TUGAS"
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Belum ada tugas.").assertIsDisplayed()
        saveRootViewScreenshot(rootView, "final_07_tugas_empty.png")

        // 6. Pengumuman Empty State
        currentModule = "PENGUMUMAN"
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Belum ada pengumuman.").assertIsDisplayed()
        saveRootViewScreenshot(rootView, "final_08_pengumuman_empty.png")

        // 7. Notifikasi Empty State
        currentModule = "NOTIFIKASI"
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Belum ada notifikasi.").assertIsDisplayed()
        saveRootViewScreenshot(rootView, "final_09_notifikasi_empty.png")
    }

    @Test
    fun verifyRealCrudOperationsRbacAndBotPanelHonestStatus() = runBlocking {
        repository.ensureSeeded()
        val admin = repository.createFirstAdmin(
            fullName = "Ahmad Ramadhan",
            username = "ahmad.admin",
            phoneWa = "081210012026",
            rawPassword = "Spensari2026"
        ).getOrThrow()

        // Create Siswa user via real repository CRUD
        repository.saveOrUpdateUser(
            UserEntity(
                id = 2,
                absenNo = 2,
                fullName = "Citra Lestari",
                username = "citra.l",
                passwordHash = PasswordSecurity.hashPassword("citra.l", "Siswa2026"),
                role = UserRole.SISWA.name,
                phoneWa = "+62 857-2341-0988",
                attendanceToday = AttendanceStatus.HADIR.name,
                billStatus = BillPaymentStatus.BELUM.name,
                billAmount = 6000
            )
        )

        // Add real Cash Transaction, Schedule, Assignment, Announcement
        repository.addManualCashTransaction(
            title = "Iuran Kas Awal Kelas IX-H",
            amount = 120000,
            isIncome = true,
            dateLabel = "Hari ini • 08.00 WIB",
            proofNote = "Setoran kas awal",
            actorName = admin.fullName
        )
        repository.saveSchedule(
            ScheduleEntity(
                dayCode = "Rab",
                startTime = "07.00",
                endTime = "08.20",
                subject = "Matematika",
                teacher = "Pak Hendra",
                room = "Ruang IX-H",
                notes = "Bawa jangka dan busur"
            )
        )
        repository.saveAssignment(
            AssignmentEntity(
                title = "Latihan Bab 4 — Bangun Ruang",
                subject = "Matematika",
                teacher = "Pak Hendra",
                deadlineLabel = "Jumat • 23:59",
                daysRemaining = 2,
                isCompleted = false,
                description = "Halaman 45 nomor 1-5"
            )
        )
        repository.saveAnnouncement(
            AnnouncementEntity(
                title = "Rapat Persiapan Kelas IX-H",
                content = "Seluruh siswa berkumpul di kelas pukul 14.00 WIB.",
                authorName = admin.fullName,
                authorRole = admin.role,
                timeLabel = "Baru saja",
                category = "Penting",
                isPinned = true,
                isDraft = false
            ),
            clearOtherPins = emptyList()
        )

        val allUsers = repository.users.first()
        val allTransactions = repository.transactions.first()
        val allSchedules = repository.schedules.first()
        val allAssignments = repository.assignments.first()
        val allAnnouncements = repository.announcements.first()
        val unreadNotifCount = repository.notifications.first().size
        val pendingSync = repository.syncQueue.first().count { it.status == "PENDING" }

        assertEquals(2, allUsers.size)
        assertEquals(1, allTransactions.size)
        assertEquals(1, allSchedules.size)
        assertEquals(1, allAssignments.size)
        assertEquals(1, allAnnouncements.size)
        assertTrue(pendingSync >= 5)

        var activeScreen by mutableStateOf("DASHBOARD")
        var rootView: View? = null

        composeTestRule.setContent {
            rootView = LocalView.current.rootView
            MyApplicationTheme {
                when (activeScreen) {
                    "DASHBOARD" -> DashboardScreen(
                        activeUser = admin,
                        allUsers = allUsers,
                        transactions = allTransactions,
                        pinnedAnnouncement = allAnnouncements.first(),
                        closestAssignment = allAssignments.first(),
                        todaySchedules = allSchedules,
                        unreadNotificationCount = unreadNotifCount,
                        botState = BotConnectionState(),
                        pendingSyncCount = pendingSync,
                        onNavigateRoute = {},
                        onSelectMainTab = {},
                        onSwitchPersona = {},
                        onOpenKasTab = {}
                    )
                    "BOT_PANEL" -> BotPanelScreen(
                        activeUser = admin,
                        botState = BotConnectionState(),
                        dispatchLogs = emptyList(),
                        validWaCount = allUsers.size,
                        onToggleConnectionSimulated = {},
                        onPterodactylControl = {},
                        onNavigate = {},
                        onBack = {}
                    )
                    "JADWAL_RAIL" -> ScheduleMainTabScreen(
                        activeUser = admin,
                        schedules = allSchedules,
                        selectedDayCode = "Rab",
                        unreadNotificationCount = unreadNotifCount,
                        onSelectDay = {},
                        onOpenScheduleDetail = {},
                        onNavigate = {},
                        onOpenRoleSwitcher = {}
                    )
                }
            }
        }
        composeTestRule.waitForIdle()

        // Verify live populated Dashboard metrics
        composeTestRule.onNodeWithText("Rp120.000").assertIsDisplayed()
        composeTestRule.onNodeWithText("Rapat Persiapan Kelas IX-H").assertIsDisplayed()
        composeTestRule.onNodeWithText("Latihan Bab 4 — Bangun Ruang").assertIsDisplayed()
        saveRootViewScreenshot(rootView, "final_10_dashboard_populated.png")

        // Switch to BotPanelScreen and verify honest disconnected status
        activeScreen = "BOT_PANEL"
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Bot WhatsApp Terputus").assertIsDisplayed()
        composeTestRule.onNodeWithText("Belum ada riwayat pengiriman bot.").assertIsDisplayed()
        saveRootViewScreenshot(rootView, "final_11_bot_panel_honest_status.png")

        // Switch to Schedule Vertical Time-Rail populated screen
        activeScreen = "JADWAL_RAIL"
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Matematika").assertIsDisplayed()
        saveRootViewScreenshot(rootView, "final_15_jadwal_populated.png")
    }

    @Test
    fun verifyBackupRestoreGlobalSearchAuditLogAndOfflineSyncQueue() = runBlocking {
        repository.ensureSeeded()
        val admin = repository.createFirstAdmin(
            fullName = "Ahmad Ramadhan",
            username = "ahmad.admin",
            phoneWa = "081210012026",
            rawPassword = "Spensari2026"
        ).getOrThrow()

        val siswa1 = UserEntity(
            id = 2,
            absenNo = 2,
            fullName = "Citra Lestari",
            username = "citra.l",
            passwordHash = PasswordSecurity.hashPassword("citra.l", "Siswa2026"),
            role = UserRole.SISWA.name,
            phoneWa = "+62 857-2341-0988",
            attendanceToday = AttendanceStatus.HADIR.name,
            billStatus = BillPaymentStatus.BELUM.name,
            billAmount = 6000
        )
        val siswa2 = UserEntity(
            id = 3,
            absenNo = 3,
            fullName = "Rangga Putra",
            username = "rangga.p",
            passwordHash = PasswordSecurity.hashPassword("rangga.p", "Siswa2026"),
            role = UserRole.SISWA.name,
            phoneWa = "+62 813-9999-1111",
            attendanceToday = AttendanceStatus.ALPHA.name,
            billStatus = BillPaymentStatus.BELUM.name,
            billAmount = 12000
        )
        repository.saveOrUpdateUser(siswa1, admin.fullName, admin.role)
        repository.saveOrUpdateUser(siswa2, admin.fullName, admin.role)

        repository.saveAnnouncement(
            AnnouncementEntity(
                title = "Pengumuman Resmi Ujian Praktik",
                content = "Ujian praktik IPA dilaksanakan hari Kamis.",
                authorName = admin.fullName,
                authorRole = admin.role,
                timeLabel = "Baru saja",
                category = "Penting",
                isPinned = true,
                isDraft = false
            ),
            clearOtherPins = emptyList()
        )
        repository.saveAnnouncement(
            AnnouncementEntity(
                title = "Draf Rahasia Pengurus",
                content = "Draf internal belum dipublikasikan.",
                authorName = admin.fullName,
                authorRole = admin.role,
                timeLabel = "Baru saja",
                category = "Info",
                isPinned = false,
                isDraft = true
            ),
            clearOtherPins = emptyList()
        )

        val users = repository.users.first()
        val txs = repository.transactions.first()
        val scheds = repository.schedules.first()
        val tasks = repository.assignments.first()
        val anns = repository.announcements.first()

        // 1. Test Global Search RBAC (§22.3)
        // As SISWA (citra.l), searching "Rangga" must NOT expose Rangga's private phone number or bill status
        val siswaSearchRangga = repository.performGlobalSearch(
            query = "Rangga",
            activeRole = siswa1.role,
            activeUsername = siswa1.username,
            usersList = users,
            assignmentsList = tasks,
            schedulesList = scheds,
            transactionsList = txs,
            announcementsList = anns
        )
        assertTrue(siswaSearchRangga.none { it.subtitle.contains("+62 813-9999-1111") })
        assertTrue(siswaSearchRangga.none { it.category == "Absensi" && it.title.contains("Rangga") })

        // As SISWA, searching "Draf Rahasia" must return 0 results
        val siswaSearchDraft = repository.performGlobalSearch(
            query = "Draf Rahasia",
            activeRole = siswa1.role,
            activeUsername = siswa1.username,
            usersList = users,
            assignmentsList = tasks,
            schedulesList = scheds,
            transactionsList = txs,
            announcementsList = anns
        )
        assertEquals(0, siswaSearchDraft.size)

        // As ADMIN, searching "Draf Rahasia" returns the draft
        val adminSearchDraft = repository.performGlobalSearch(
            query = "Draf Rahasia",
            activeRole = admin.role,
            activeUsername = admin.username,
            usersList = users,
            assignmentsList = tasks,
            schedulesList = scheds,
            transactionsList = txs,
            announcementsList = anns
        )
        assertEquals(1, adminSearchDraft.size)

        // 2. Test Backup & Restore with SHA-256 Checksum (§22.1)
        val backupPkg = repository.exportDatabaseBackupJson(admin.fullName, admin.role).getOrThrow()
        val backupJson = backupPkg.jsonContent
        assertTrue(backupJson.contains("\"schemaVersion\": 3"))
        assertTrue(backupJson.contains("checksum"))

        // Tamper with backup JSON and verify restore rejects it and logs GAGAL
        val tamperedJson = backupJson.replace("Citra Lestari", "Citra Tampered")
        val tamperedResult = repository.restoreDatabaseBackupJson(tamperedJson, admin.fullName, admin.role)
        assertTrue(tamperedResult.isFailure)

        // Valid restore succeeds
        val validRestoreResult = repository.restoreDatabaseBackupJson(backupJson, admin.fullName, admin.role)
        assertTrue(validRestoreResult.isSuccess)
        assertEquals(3, repository.users.first().size)

        // 3. Verify Audit Log entries (§22.2) & Notification Deduplication (§22.4)
        repository.emitNotificationIfAbsent(
            eventKey = "TEST_DEDUP_KEY_1",
            category = "Kas",
            title = "Pengingat Kas Unik",
            subtitle = "Tes deduplikasi notifikasi",
            targetRoute = "kas"
        )
        repository.emitNotificationIfAbsent(
            eventKey = "TEST_DEDUP_KEY_1",
            category = "Kas",
            title = "Pengingat Kas Unik",
            subtitle = "Tes deduplikasi notifikasi",
            targetRoute = "kas"
        )
        val dedupNotifs = repository.notifications.first().filter { it.eventKey == "TEST_DEDUP_KEY_1" }
        assertEquals(1, dedupNotifs.size)

        val allAuditLogs = repository.activityLogs.first()
        assertTrue(allAuditLogs.any { it.operationType == "BACKUP" && it.statusLabel == "BERHASIL" })
        assertTrue(allAuditLogs.any { it.operationType == "RESTORE" && it.statusLabel == "GAGAL" })
        assertTrue(allAuditLogs.any { it.operationType == "RESTORE" && it.statusLabel == "BERHASIL" })

        // 4. Render & capture screenshots for GlobalSearchScreen, BackupRestoreScreen, ActivityLogsScreen
        val allSearchItems = repository.performGlobalSearch(
            query = "a",
            activeRole = admin.role,
            activeUsername = admin.username,
            usersList = users,
            assignmentsList = tasks,
            schedulesList = scheds,
            transactionsList = txs,
            announcementsList = anns
        )

        var screenMode by mutableStateOf("SEARCH")
        var rootView: View? = null

        composeTestRule.setContent {
            rootView = LocalView.current.rootView
            MyApplicationTheme {
                when (screenMode) {
                    "SEARCH" -> GlobalSearchScreen(
                        activeUser = admin,
                        query = "a",
                        selectedCategory = "Semua",
                        results = allSearchItems,
                        onQueryChange = {},
                        onCategoryChange = {},
                        onSelectResult = {},
                        onBack = {}
                    )
                    "BACKUP" -> BackupRestoreScreen(
                        activeUser = admin,
                        backupState = BackupUiState(
                            lastBackupTime = "Baru saja (WIB)",
                            lastBackupChecksum = backupPkg.checksum,
                            lastBackupTotalRecords = backupPkg.totalRecords,
                            latestBackupJson = backupJson,
                            localFilePath = "files/backups/ixh_hub_latest_backup.json",
                            statusMessage = "Backup berhasil dibuat (${backupPkg.totalRecords} entitas • SHA-256 terverifikasi)."
                        ),
                        onCreateBackupNow = {},
                        onRestoreFromBackupJson = {},
                        onBack = {}
                    )
                    "AUDIT" -> ActivityLogsScreen(
                        activeUser = admin,
                        logs = allAuditLogs,
                        onOpenLogDetail = {},
                        onExportToast = {},
                        onBack = {}
                    )
                }
            }
        }
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText("Pencarian Global IX-H").assertIsDisplayed()
        saveRootViewScreenshot(rootView, "final_12_global_search.png")

        screenMode = "BACKUP"
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Backup & Restore Database").assertIsDisplayed()
        saveRootViewScreenshot(rootView, "final_13_backup_restore.png")

        screenMode = "AUDIT"
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Audit Log Aktivitas").assertIsDisplayed()
        saveRootViewScreenshot(rootView, "final_14_audit_logs.png")
    }

    private fun saveRootViewScreenshot(rootView: View?, fileName: String) {
        composeTestRule.runOnIdle {
            val v = rootView ?: return@runOnIdle
            val w = v.width.coerceAtLeast(1080)
            val h = v.height.coerceAtLeast(2200)
            val bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            v.draw(canvas)

            val outDir = File("/app/applet/.aistudio/artifacts/brain/ce7448c2-f2b8-4313-af9c-f5bba84c793f")
            if (outDir.exists()) {
                FileOutputStream(File(outDir, fileName)).use { fos ->
                    bitmap.compress(Bitmap.CompressFormat.PNG, 95, fos)
                }
            }
        }
    }
}
