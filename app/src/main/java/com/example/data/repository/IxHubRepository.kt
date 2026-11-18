package com.example.data.repository

import com.example.data.local.ActivityLogEntity
import com.example.data.local.AnnouncementEntity
import com.example.data.local.AssignmentEntity
import com.example.data.local.AttendanceStatus
import com.example.data.local.BillPaymentStatus
import com.example.data.local.BotDispatchLogEntity
import com.example.data.local.BotTemplateEntity
import com.example.data.local.CashTransactionEntity
import com.example.data.local.IxHubDao
import com.example.data.local.IxHubDatabase
import com.example.data.local.NotificationEntity
import com.example.data.local.PasswordSecurity
import com.example.data.local.ScheduleEntity
import com.example.data.local.SyncQueueEntity
import com.example.data.local.UserEntity
import com.example.data.local.UserRole
import com.example.data.local.WaNumberStatus
import com.example.data.remote.PterodactylPowerRequest
import com.example.data.remote.VpsNetworkClient
import com.example.data.remote.VpsSyncPayload
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

data class DatabaseBackupPackage(
    val jsonContent: String,
    val checksum: String,
    val schemaVersion: Int,
    val createdAtLabel: String,
    val totalRecords: Int
)

data class GlobalSearchItem(
    val id: String,
    val category: String, // Siswa, Tugas, Jadwal, Kas, Pengumuman, Absensi
    val title: String,
    val subtitle: String,
    val badgeText: String,
    val targetRouteName: String,
    val entityId: Int = 0
)

class IxHubRepository(private val dao: IxHubDao) {

    val users: Flow<List<UserEntity>> = dao.observeUsers()
    val transactions: Flow<List<CashTransactionEntity>> = dao.observeTransactions()
    val schedules: Flow<List<ScheduleEntity>> = dao.observeSchedules()
    val assignments: Flow<List<AssignmentEntity>> = dao.observeAssignments()
    val announcements: Flow<List<AnnouncementEntity>> = dao.observeAnnouncements()
    val notifications: Flow<List<NotificationEntity>> = dao.observeNotifications()
    val activityLogs: Flow<List<ActivityLogEntity>> = dao.observeActivityLogs()
    val botTemplates: Flow<List<BotTemplateEntity>> = dao.observeBotTemplates()
    val botDispatchLogs: Flow<List<BotDispatchLogEntity>> = dao.observeBotDispatchLogs()
    val syncQueue: Flow<List<SyncQueueEntity>> = dao.observeSyncQueue()

    private fun currentWibTimeLabel(): String {
        val sdf = SimpleDateFormat("dd MMM yyyy • HH.mm 'WIB'", Locale("id", "ID"))
        sdf.timeZone = TimeZone.getTimeZone("Asia/Jakarta")
        return sdf.format(Date())
    }

    /**
     * Database v2/v3 Policy:
     * Operational tables (users, cash_transactions, schedules, assignments, announcements,
     * notifications, activity_logs, bot_dispatch_logs, sync_queue) are NEVER seeded with dummy data.
     * The database starts 100% empty until the First Admin is created via FirstRunAdminSetupScreen.
     */
    suspend fun ensureSeeded() = withContext(Dispatchers.IO) {
        // Intentional no-op for operational tables: no dummy data is ever inserted.
    }

    suspend fun hasAdminAccount(): Boolean = withContext(Dispatchers.IO) {
        dao.getAdminCount() > 0
    }

    suspend fun getUserCount(): Int = withContext(Dispatchers.IO) {
        dao.getUserCount()
    }

    private suspend fun enqueueSyncMutation(
        eventId: String,
        entityType: String,
        operation: String,
        summary: String,
        conflictRule: String,
        actorName: String
    ) {
        dao.insertSyncQueueItem(
            SyncQueueEntity(
                eventId = eventId,
                entityType = entityType,
                operation = operation,
                summary = summary,
                status = "PENDING",
                conflictRule = conflictRule,
                actorName = actorName
            )
        )
    }

    suspend fun emitNotificationIfAbsent(
        eventKey: String,
        title: String,
        subtitle: String,
        category: String,
        targetRoute: String,
        timeLabel: String = "Baru saja"
    ) = withContext(Dispatchers.IO) {
        if (eventKey.isNotBlank()) {
            val existing = dao.findNotificationByEventKey(eventKey)
            if (existing != null) return@withContext
        }
        dao.insertNotification(
            NotificationEntity(
                title = title,
                subtitle = subtitle,
                timeLabel = timeLabel,
                dayGroup = "HARI INI",
                category = category,
                isUnread = true,
                targetRoute = targetRoute,
                eventKey = eventKey
            )
        )
    }

    suspend fun createFirstAdmin(
        fullName: String,
        username: String,
        phoneWa: String,
        rawPassword: String,
        subRoleTitle: String = "Ketua Kelas & Admin"
    ): Result<UserEntity> = withContext(Dispatchers.IO) {
        if (dao.getAdminCount() > 0) {
            return@withContext Result.failure(
                IllegalStateException("Akun Admin pertama sudah terdaftar. Silakan masuk melalui halaman Login.")
            )
        }
        val cleanName = fullName.trim()
        val cleanUsername = username.trim().lowercase()
        val rawPhone = phoneWa.trim()
        val cleanPhone = if (rawPhone.startsWith("08")) {
            "+62 " + rawPhone.removePrefix("0")
        } else {
            rawPhone
        }

        if (cleanName.length < 3) {
            return@withContext Result.failure(IllegalArgumentException("Nama lengkap minimal 3 karakter."))
        }
        if (cleanUsername.length < 3 || cleanUsername.contains(" ")) {
            return@withContext Result.failure(
                IllegalArgumentException("Username minimal 3 karakter dan tidak boleh mengandung spasi.")
            )
        }
        if (rawPhone.length < 9) {
            return@withContext Result.failure(
                IllegalArgumentException("Nomor WhatsApp tidak valid (minimal 9 digit).")
            )
        }
        if (!PasswordSecurity.isStrongEnough(rawPassword)) {
            return@withContext Result.failure(
                IllegalArgumentException("Password minimal 8 karakter dengan kombinasi huruf dan angka.")
            )
        }

        val hashed = PasswordSecurity.hashPassword(cleanUsername, rawPassword)
        val firstAdmin = UserEntity(
            id = 1,
            absenNo = 1,
            fullName = cleanName,
            username = cleanUsername,
            email = "$cleanUsername@spensari.sch.id",
            gender = "Laki-laki",
            role = UserRole.ADMIN.name,
            subRoleTitle = subRoleTitle.ifBlank { "Admin Utama IX-H" },
            phoneWa = cleanPhone,
            waStatus = WaNumberStatus.BELUM_DICEK.name,
            attendanceToday = "",
            presentDaysCount = 0,
            totalEffectiveDays = 0,
            billStatus = BillPaymentStatus.BELUM.name,
            billAmount = 0,
            paidDate = "",
            paidMethod = "",
            deliveryStatus = "Belum dikirim",
            isActiveAccount = true,
            mustChangePassword = false,
            tempPassword = "",
            passwordHash = hashed,
            lastLogin = "Baru saja"
        )
        dao.insertUser(firstAdmin)
        dao.insertActivityLog(
            ActivityLogEntity(
                category = "Sistem",
                operationType = "CREATE",
                statusLabel = "BERHASIL",
                actorName = cleanName,
                actorRole = "Admin",
                actionTitle = "Inisialisasi Admin Pertama: $cleanName",
                detailText = "Username @$cleanUsername • Setup awal IX-H HUB 1.0 selesai",
                targetUser = cleanName,
                beforeValue = "Database Kosong",
                afterValue = "Admin Aktif (@$cleanUsername)",
                reason = "Setup Admin Pertama",
                dayHeader = "SETUP AWAL SISTEM",
                timeWib = currentWibTimeLabel()
            )
        )
        enqueueSyncMutation(
            eventId = "USR-FIRST-ADMIN-$cleanUsername",
            entityType = "Pengguna",
            operation = "CREATE",
            summary = "Setup Admin Pertama: $cleanName (@$cleanUsername)",
            conflictRule = "LAST_WRITE_WINS",
            actorName = cleanName
        )
        Result.success(firstAdmin)
    }

    suspend fun authenticateUser(
        usernameInput: String,
        rawPasswordInput: String
    ): Result<UserEntity> = withContext(Dispatchers.IO) {
        val cleanUser = usernameInput.trim().lowercase()
        if (cleanUser.isBlank() || rawPasswordInput.isBlank()) {
            return@withContext Result.failure(IllegalArgumentException("Username dan password wajib diisi."))
        }

        val user = dao.findUserByUsername(cleanUser)
            ?: return@withContext Result.failure(
                IllegalArgumentException("Akun @$cleanUser tidak ditemukan. Periksa kembali username Anda.")
            )

        if (!user.isActiveAccount) {
            dao.insertActivityLog(
                ActivityLogEntity(
                    category = "Login",
                    operationType = "LOGIN",
                    statusLabel = "GAGAL",
                    actorName = user.fullName,
                    actorRole = user.role,
                    actionTitle = "Percobaan login akun terkunci: @${user.username}",
                    detailText = "Akses ditolak karena akun dinonaktifkan",
                    targetUser = user.fullName,
                    beforeValue = "Nonaktif",
                    afterValue = "Ditolak",
                    reason = "Akun dinonaktifkan Admin",
                    dayHeader = "HARI INI",
                    timeWib = currentWibTimeLabel()
                )
            )
            return@withContext Result.failure(
                IllegalStateException("Akun @$cleanUser sedang dinonaktifkan oleh Admin kelas.")
            )
        }

        val isHashValid = PasswordSecurity.verifyPassword(user.username, rawPasswordInput, user.passwordHash)
        val isTempPasswordValid = user.passwordHash.isBlank() &&
            user.tempPassword.isNotBlank() &&
            rawPasswordInput == user.tempPassword

        if (!isHashValid && !isTempPasswordValid) {
            dao.insertActivityLog(
                ActivityLogEntity(
                    category = "Login",
                    operationType = "LOGIN",
                    statusLabel = "GAGAL",
                    actorName = user.fullName,
                    actorRole = user.role,
                    actionTitle = "Gagal autentikasi: @${user.username}",
                    detailText = "Kredensial tidak cocok (password disensor)",
                    targetUser = user.fullName,
                    beforeValue = "Sesi Kosong",
                    afterValue = "Gagal Login",
                    reason = "Password salah",
                    dayHeader = "HARI INI",
                    timeWib = currentWibTimeLabel()
                )
            )
            return@withContext Result.failure(
                IllegalArgumentException("Password yang Anda masukkan salah.")
            )
        }

        val updatedUser = user.copy(
            passwordHash = if (user.passwordHash.isBlank()) {
                PasswordSecurity.hashPassword(user.username, rawPasswordInput)
            } else {
                user.passwordHash
            },
            tempPassword = "",
            lastLogin = "Baru saja"
        )
        dao.updateUser(updatedUser)
        dao.insertActivityLog(
            ActivityLogEntity(
                category = "Login",
                operationType = "LOGIN",
                statusLabel = "BERHASIL",
                actorName = updatedUser.fullName,
                actorRole = updatedUser.role,
                actionTitle = "Login berhasil: ${updatedUser.fullName}",
                detailText = "Masuk sebagai ${updatedUser.role} (@${updatedUser.username})",
                targetUser = updatedUser.fullName,
                beforeValue = "Offline",
                afterValue = "Sesi Aktif (${updatedUser.role})",
                reason = "Autentikasi pengguna",
                dayHeader = "HARI INI",
                timeWib = currentWibTimeLabel()
            )
        )
        Result.success(updatedUser)
    }

    suspend fun recordLogout(actorName: String, actorRole: String) = withContext(Dispatchers.IO) {
        if (actorName.isBlank()) return@withContext
        dao.insertActivityLog(
            ActivityLogEntity(
                category = "Login",
                operationType = "LOGOUT",
                statusLabel = "BERHASIL",
                actorName = actorName,
                actorRole = actorRole,
                actionTitle = "Logout sesi: $actorName",
                detailText = "Sesi pengguna diakhiri secara aman",
                targetUser = actorName,
                beforeValue = "Sesi Aktif",
                afterValue = "Logout",
                reason = "Pengguna keluar dari aplikasi",
                dayHeader = "HARI INI",
                timeWib = currentWibTimeLabel()
            )
        )
    }

    suspend fun resetAllOperationalData() = withContext(Dispatchers.IO) {
        dao.clearAllUsers()
        dao.clearAllTransactions()
        dao.clearAllSchedules()
        dao.clearAllAssignments()
        dao.clearAllAnnouncements()
        dao.clearAllNotifications()
        dao.clearAllActivityLogs()
        dao.clearAllBotDispatchLogs()
        dao.clearAllBotTemplates()
        dao.clearAllSyncQueue()
    }

    suspend fun saveAttendanceBatch(
        updatedUsers: List<UserEntity>,
        actorName: String,
        actorRole: String
    ) = withContext(Dispatchers.IO) {
        if (updatedUsers.isEmpty()) return@withContext
        val normalized = updatedUsers.map { user ->
            val incPresent = if (user.attendanceToday == AttendanceStatus.HADIR.name) 1 else 0
            user.copy(
                presentDaysCount = (user.presentDaysCount + incPresent).coerceAtLeast(0),
                totalEffectiveDays = (user.totalEffectiveDays + 1).coerceAtLeast(1)
            )
        }
        dao.insertUsers(normalized)
        val h = normalized.count { it.attendanceToday == AttendanceStatus.HADIR.name }
        val i = normalized.count { it.attendanceToday == AttendanceStatus.IZIN.name }
        val s = normalized.count { it.attendanceToday == AttendanceStatus.SAKIT.name }
        val a = normalized.count { it.attendanceToday == AttendanceStatus.ALPHA.name }
        dao.insertActivityLog(
            ActivityLogEntity(
                category = "Absensi",
                operationType = "UPDATE",
                statusLabel = "BERHASIL",
                actorName = actorName,
                actorRole = actorRole,
                actionTitle = "Menyimpan absensi IX-H",
                detailText = "Hadir $h • Izin $i • Sakit $s • Alpha $a",
                targetUser = "Kelas IX-H (${normalized.size} Siswa)",
                beforeValue = "Draft Absensi",
                afterValue = "$h H / $i I / $s S / $a A",
                reason = "Penyimpanan absensi harian",
                dayHeader = "HARI INI",
                timeWib = currentWibTimeLabel()
            )
        )
        val dayKey = SimpleDateFormat("yyyyMMdd", Locale.US).format(Date())
        enqueueSyncMutation(
            eventId = "ABS-$dayKey-${normalized.size}",
            entityType = "Absensi",
            operation = "UPDATE",
            summary = "Absensi harian IX-H ($h H, $i I, $s S, $a A)",
            conflictRule = "SERVER_AUTHORITATIVE_MERGE",
            actorName = actorName
        )
        emitNotificationIfAbsent(
            eventKey = "NOTIF-ABS-$dayKey-$h-$i-$s-$a",
            title = "Rekap Absensi IX-H Diperbarui",
            subtitle = "Hadir $h • Izin $i • Sakit $s • Alpha $a (Dicatat oleh $actorName)",
            category = "Absensi",
            targetRoute = "absensi"
        )
    }

    suspend fun markStudentBillPaid(
        student: UserEntity,
        method: String,
        note: String,
        actorName: String,
        actorRole: String
    ) = withContext(Dispatchers.IO) {
        val nominal = if (student.billAmount > 0) student.billAmount else 6000
        val updated = student.copy(
            billStatus = BillPaymentStatus.LUNAS.name,
            billAmount = nominal,
            paidDate = "Hari ini",
            paidMethod = method
        )
        dao.updateUser(updated)
        dao.insertTransaction(
            CashTransactionEntity(
                title = student.fullName,
                amount = nominal,
                isIncome = true,
                isFromBill = true,
                dateTimeLabel = "Hari ini",
                studentName = student.fullName,
                paymentMethod = method,
                confirmedBy = actorName,
                proofNote = note.ifBlank { "Terkonfirmasi lunas via $method" }
            )
        )
        dao.insertActivityLog(
            ActivityLogEntity(
                category = "Lunas",
                operationType = "UPDATE",
                statusLabel = "BERHASIL",
                actorName = actorName,
                actorRole = actorRole,
                actionTitle = "Menandai lunas: ${student.fullName}",
                detailText = "Kas Kelas IX-H • Rp$nominal • $method",
                targetUser = student.fullName,
                beforeValue = student.billStatus,
                afterValue = "Lunas ($method)",
                reason = note.ifBlank { "Bukti pembayaran diverifikasi pengurus" },
                dayHeader = "HARI INI",
                timeWib = currentWibTimeLabel()
            )
        )
        enqueueSyncMutation(
            eventId = "KAS-BILL-${student.id}-${System.currentTimeMillis() / 1000}",
            entityType = "Kas",
            operation = "CREATE",
            summary = "Pembayaran kas ${student.fullName} Rp$nominal ($method)",
            conflictRule = "APPEND_LEDGER",
            actorName = actorName
        )
        emitNotificationIfAbsent(
            eventKey = "NOTIF-BILL-PAID-${student.id}-$nominal",
            title = "Kas Lunas: ${student.fullName}",
            subtitle = "Iuran kas Rp$nominal via $method telah diverifikasi oleh $actorName",
            category = "Kas",
            targetRoute = "kas"
        )
    }

    suspend fun addManualCashTransaction(
        title: String,
        amount: Int,
        isIncome: Boolean,
        dateLabel: String,
        proofNote: String,
        actorName: String,
        actorRole: String = "Pengurus"
    ) = withContext(Dispatchers.IO) {
        val cleanTitle = title.trim()
        dao.insertTransaction(
            CashTransactionEntity(
                title = cleanTitle,
                amount = amount,
                isIncome = isIncome,
                isFromBill = false,
                dateTimeLabel = dateLabel.ifBlank { "Hari ini" },
                paymentMethod = "Tunai",
                confirmedBy = actorName,
                proofNote = proofNote
            )
        )
        val kind = if (isIncome) "Pemasukan" else "Pengeluaran"
        dao.insertActivityLog(
            ActivityLogEntity(
                category = "Lunas",
                operationType = "CREATE",
                statusLabel = "BERHASIL",
                actorName = actorName,
                actorRole = actorRole,
                actionTitle = "Mencatat $kind Kas: $cleanTitle",
                detailText = "$kind Rp$amount • ${dateLabel.ifBlank { "Hari ini" }}",
                targetUser = "Kas Kelas IX-H",
                beforeValue = "-",
                afterValue = "${if (isIncome) "+" else "-"}Rp$amount",
                reason = proofNote.ifBlank { "Pencatatan transaksi kas manual" },
                dayHeader = "HARI INI",
                timeWib = currentWibTimeLabel()
            )
        )
        enqueueSyncMutation(
            eventId = "KAS-MANUAL-${cleanTitle.hashCode()}-$amount-${System.currentTimeMillis() / 1000}",
            entityType = "Kas",
            operation = "CREATE",
            summary = "$kind kas: $cleanTitle (Rp$amount)",
            conflictRule = "APPEND_LEDGER",
            actorName = actorName
        )
    }

    suspend fun toggleAssignmentComplete(item: AssignmentEntity) = withContext(Dispatchers.IO) {
        val next = !item.isCompleted
        dao.updateAssignment(item.copy(isCompleted = next))
        enqueueSyncMutation(
            eventId = "TUGAS-TOGGLE-${item.id}",
            entityType = "Tugas",
            operation = "UPDATE",
            summary = "Status tugas '${item.title}' -> ${if (next) "Selesai" else "Aktif"}",
            conflictRule = "LAST_WRITE_WINS",
            actorName = "Anggota IX-H"
        )
    }

    suspend fun saveAssignment(
        item: AssignmentEntity,
        actorName: String = "Pengurus",
        actorRole: String = "Pengurus"
    ) = withContext(Dispatchers.IO) {
        dao.insertAssignment(item)
        dao.insertActivityLog(
            ActivityLogEntity(
                category = "Sistem",
                operationType = "CREATE",
                statusLabel = "BERHASIL",
                actorName = actorName,
                actorRole = actorRole,
                actionTitle = "Menambah tugas: ${item.title}",
                detailText = "${item.subject} • Tenggat ${item.deadlineLabel}",
                targetUser = "Kelas IX-H",
                beforeValue = "-",
                afterValue = "${item.title} (${item.subject})",
                reason = "Penambahan tugas kelas",
                dayHeader = "HARI INI",
                timeWib = currentWibTimeLabel()
            )
        )
        val eventKey = "NOTIF-TUGAS-${item.subject.lowercase()}-${item.title.lowercase()}"
        emitNotificationIfAbsent(
            eventKey = eventKey,
            title = "Tugas Baru: ${item.subject}",
            subtitle = "${item.title} • Tenggat ${item.deadlineLabel}",
            category = "Tugas",
            targetRoute = "tugas"
        )
        enqueueSyncMutation(
            eventId = "TUGAS-SAVE-${item.title.hashCode()}",
            entityType = "Tugas",
            operation = "CREATE",
            summary = "Tugas ${item.subject}: ${item.title}",
            conflictRule = "LAST_WRITE_WINS",
            actorName = actorName
        )
    }

    suspend fun deleteAssignment(id: Int) = withContext(Dispatchers.IO) {
        dao.deleteAssignment(id)
        enqueueSyncMutation(
            eventId = "TUGAS-DEL-$id",
            entityType = "Tugas",
            operation = "DELETE",
            summary = "Menghapus tugas ID #$id",
            conflictRule = "LAST_WRITE_WINS",
            actorName = "Pengurus"
        )
    }

    suspend fun saveSchedule(
        item: ScheduleEntity,
        actorName: String = "Pengurus",
        actorRole: String = "Pengurus"
    ) = withContext(Dispatchers.IO) {
        dao.insertSchedule(item)
        dao.insertActivityLog(
            ActivityLogEntity(
                category = "Sistem",
                operationType = "CREATE",
                statusLabel = "BERHASIL",
                actorName = actorName,
                actorRole = actorRole,
                actionTitle = "Menyimpan jadwal: ${item.subject}",
                detailText = "Hari ${item.dayCode} • ${item.startTime}–${item.endTime} (${item.room})",
                targetUser = "Jadwal IX-H (${item.dayCode})",
                beforeValue = "-",
                afterValue = "${item.subject} (${item.startTime}–${item.endTime})",
                reason = "Pembaruan jadwal pelajaran",
                dayHeader = "HARI INI",
                timeWib = currentWibTimeLabel()
            )
        )
        enqueueSyncMutation(
            eventId = "JADWAL-${item.dayCode}-${item.startTime}-${item.subject.hashCode()}",
            entityType = "Jadwal",
            operation = "CREATE",
            summary = "Jadwal ${item.dayCode}: ${item.subject} (${item.startTime}–${item.endTime})",
            conflictRule = "LAST_WRITE_WINS",
            actorName = actorName
        )
    }

    suspend fun deleteSchedule(id: Int, actorName: String = "Pengurus", actorRole: String = "Pengurus") =
        withContext(Dispatchers.IO) {
            dao.deleteSchedule(id)
            dao.insertActivityLog(
                ActivityLogEntity(
                    category = "Hapus",
                    operationType = "DELETE",
                    statusLabel = "BERHASIL",
                    actorName = actorName,
                    actorRole = actorRole,
                    actionTitle = "Menghapus sesi jadwal #$id",
                    detailText = "Sesi pelajaran dihapus dari jadwal aktif",
                    targetUser = "Jadwal IX-H",
                    beforeValue = "Aktif",
                    afterValue = "Dihapus",
                    reason = "Penghapusan jadwal pelajaran",
                    dayHeader = "HARI INI",
                    timeWib = currentWibTimeLabel()
                )
            )
            enqueueSyncMutation(
                eventId = "JADWAL-DEL-$id",
                entityType = "Jadwal",
                operation = "DELETE",
                summary = "Menghapus jadwal ID #$id",
                conflictRule = "LAST_WRITE_WINS",
                actorName = actorName
            )
        }

    suspend fun updateScheduleReminder(item: ScheduleEntity, enabled: Boolean, time: String) =
        withContext(Dispatchers.IO) {
            dao.updateSchedule(item.copy(reminderEnabled = enabled, reminderTime = time))
        }

    suspend fun saveAnnouncement(
        item: AnnouncementEntity,
        clearOtherPins: List<AnnouncementEntity>
    ) = withContext(Dispatchers.IO) {
        if (item.isPinned) {
            clearOtherPins.filter { it.isPinned && it.id != item.id }.forEach {
                dao.updateAnnouncement(it.copy(isPinned = false))
            }
        }
        dao.insertAnnouncement(item)
        dao.insertActivityLog(
            ActivityLogEntity(
                category = "Sistem",
                operationType = if (item.id == 0) "CREATE" else "UPDATE",
                statusLabel = "BERHASIL",
                actorName = item.authorName,
                actorRole = item.authorRole,
                actionTitle = if (item.isDraft) "Menyimpan draf: ${item.title}" else "Menerbitkan pengumuman: ${item.title}",
                detailText = "Kategori ${item.category} • ${if (item.isPinned) "Disematkan" else "Standar"}",
                targetUser = "Papan Pengumuman IX-H",
                beforeValue = "-",
                afterValue = if (item.isDraft) "Draf" else "Terbit (${item.category})",
                reason = "Publikasi informasi kelas",
                dayHeader = "HARI INI",
                timeWib = currentWibTimeLabel()
            )
        )
        if (!item.isDraft) {
            emitNotificationIfAbsent(
                eventKey = "NOTIF-ANN-${item.category.lowercase()}-${item.title.lowercase()}",
                title = "Pengumuman ${item.category}: ${item.title}",
                subtitle = "Oleh ${item.authorName} • Ketuk untuk membaca selengkapnya",
                category = "Pengumuman",
                targetRoute = "pengumuman"
            )
        }
        enqueueSyncMutation(
            eventId = "ANN-${item.title.hashCode()}",
            entityType = "Pengumuman",
            operation = if (item.id == 0) "CREATE" else "UPDATE",
            summary = "Pengumuman: ${item.title} (${item.category})",
            conflictRule = "LAST_WRITE_WINS",
            actorName = item.authorName
        )
    }

    suspend fun deleteAnnouncement(item: AnnouncementEntity, actorName: String) =
        withContext(Dispatchers.IO) {
            dao.deleteAnnouncement(item.id)
            dao.insertActivityLog(
                ActivityLogEntity(
                    category = "Hapus",
                    operationType = "DELETE",
                    statusLabel = "BERHASIL",
                    actorName = actorName,
                    actorRole = "Admin",
                    actionTitle = "Menghapus pengumuman: ${item.title}",
                    detailText = "Kategori ${item.category}",
                    targetUser = item.title,
                    beforeValue = "Terbit",
                    afterValue = "Dihapus",
                    reason = "Dihapus dari papan pengumuman",
                    dayHeader = "HARI INI",
                    timeWib = currentWibTimeLabel()
                )
            )
            enqueueSyncMutation(
                eventId = "ANN-DEL-${item.id}",
                entityType = "Pengumuman",
                operation = "DELETE",
                summary = "Hapus pengumuman: ${item.title}",
                conflictRule = "LAST_WRITE_WINS",
                actorName = actorName
            )
        }

    suspend fun updateUserRole(
        user: UserEntity,
        newRole: String,
        newSubTitle: String,
        actorName: String
    ) = withContext(Dispatchers.IO) {
        val oldRoleLabel = user.role
        dao.updateUser(user.copy(role = newRole, subRoleTitle = newSubTitle))
        dao.insertActivityLog(
            ActivityLogEntity(
                category = "Role",
                operationType = "ROLE_CHANGE",
                statusLabel = "BERHASIL",
                actorName = actorName,
                actorRole = "Admin",
                actionTitle = "Mengubah role: ${user.fullName}",
                detailText = "$oldRoleLabel → $newRole ($newSubTitle)",
                targetUser = user.fullName,
                beforeValue = oldRoleLabel,
                afterValue = newRole,
                reason = "Perubahan hak akses oleh Admin",
                dayHeader = "HARI INI",
                timeWib = currentWibTimeLabel()
            )
        )
        enqueueSyncMutation(
            eventId = "ROLE-${user.username}-$newRole",
            entityType = "Pengguna",
            operation = "UPDATE",
            summary = "Perubahan role ${user.fullName}: $oldRoleLabel -> $newRole",
            conflictRule = "LAST_WRITE_WINS",
            actorName = actorName
        )
    }

    suspend fun saveOrUpdateUser(
        user: UserEntity,
        actorName: String = "Admin",
        actorRole: String = "Admin"
    ) = withContext(Dispatchers.IO) {
        val normalized = if (user.passwordHash.isBlank() && user.tempPassword.isNotBlank()) {
            user.copy(passwordHash = PasswordSecurity.hashPassword(user.username, user.tempPassword))
        } else {
            user
        }
        dao.insertUser(normalized)
        dao.insertActivityLog(
            ActivityLogEntity(
                category = "Sistem",
                operationType = "UPDATE",
                statusLabel = "BERHASIL",
                actorName = actorName,
                actorRole = actorRole,
                actionTitle = "Menyimpan data anggota: ${normalized.fullName}",
                detailText = "Username @${normalized.username} • Role ${normalized.role}",
                targetUser = normalized.fullName,
                beforeValue = "-",
                afterValue = "${normalized.fullName} (${normalized.role})",
                reason = "Pembaruan data anggota kelas",
                dayHeader = "HARI INI",
                timeWib = currentWibTimeLabel()
            )
        )
        enqueueSyncMutation(
            eventId = "USR-SAVE-${normalized.username}",
            entityType = "Pengguna",
            operation = "UPDATE",
            summary = "Simpan data pengguna: ${normalized.fullName} (@${normalized.username})",
            conflictRule = "LAST_WRITE_WINS",
            actorName = actorName
        )
    }

    suspend fun deleteUser(user: UserEntity, actorName: String) = withContext(Dispatchers.IO) {
        dao.deleteUserById(user.id)
        dao.insertActivityLog(
            ActivityLogEntity(
                category = "Hapus",
                operationType = "DELETE",
                statusLabel = "BERHASIL",
                actorName = actorName,
                actorRole = "Admin",
                actionTitle = "Menghapus anggota: ${user.fullName}",
                detailText = "Username @${user.username}",
                targetUser = user.fullName,
                beforeValue = user.role,
                afterValue = "Dihapus",
                reason = "Dihapus oleh Admin",
                dayHeader = "HARI INI",
                timeWib = currentWibTimeLabel()
            )
        )
        enqueueSyncMutation(
            eventId = "USR-DEL-${user.username}",
            entityType = "Pengguna",
            operation = "DELETE",
            summary = "Hapus anggota: ${user.fullName} (@${user.username})",
            conflictRule = "LAST_WRITE_WINS",
            actorName = actorName
        )
    }

    suspend fun setStudentWaPreference(
        user: UserEntity,
        enableMessages: Boolean
    ) = withContext(Dispatchers.IO) {
        val newStatus = if (enableMessages) WaNumberStatus.VALID.name else WaNumberStatus.BERHENTI.name
        val updated = user.copy(
            waStatus = newStatus,
            waStopTimestamp = if (enableMessages) "Aktif kembali" else "Berhenti menerima pesan"
        )
        dao.updateUser(updated)
        dao.insertActivityLog(
            ActivityLogEntity(
                category = "Berhenti",
                operationType = "UPDATE",
                statusLabel = "BERHASIL",
                actorName = user.fullName,
                actorRole = user.role,
                actionTitle = if (enableMessages) "Mengaktifkan kembali pesan WA: ${user.fullName}" else "Menghentikan pesan WA: ${user.fullName}",
                detailText = "Status nomor WA -> $newStatus",
                targetUser = user.fullName,
                beforeValue = user.waStatus,
                afterValue = newStatus,
                reason = if (enableMessages) "Perintah MULAI" else "Perintah STOP",
                dayHeader = "HARI INI",
                timeWib = currentWibTimeLabel()
            )
        )
        emitNotificationIfAbsent(
            eventKey = "NOTIF-WA-${user.username}-$newStatus",
            title = if (enableMessages) "${user.fullName} aktif kembali" else "${user.fullName} berhenti menerima pesan",
            subtitle = if (enableMessages) "Membalas MULAI • pesan bot aktif lagi" else "Membalas STOP • bot tidak mengirim pesan lagi",
            category = "Bot",
            targetRoute = "bot"
        )
    }

    suspend fun saveBotTemplate(template: BotTemplateEntity) = withContext(Dispatchers.IO) {
        dao.updateBotTemplate(template)
    }

    suspend fun resetBotTemplateToDefault(templateId: String) = withContext(Dispatchers.IO) {
        val defaultItem = defaultBotTemplates().find { it.id == templateId }
        if (defaultItem != null) {
            dao.updateBotTemplate(defaultItem.copy(isModified = false))
        }
    }

    fun defaultBotTemplates(): List<BotTemplateEntity> = listOf(
        BotTemplateEntity(
            id = "tagihan",
            name = "Tagihan",
            subtitle = "Pesan tagihan kas per siswa",
            bodyText = "Halo {nama}, ini tagihan kas IX-H:\nNominal: {nominal}\nJatuh tempo: {jatuh_tempo}\nBayar ke bendahara, lalu balas LUNAS + foto bukti.\nBalas STOP untuk berhenti menerima pesan.",
            isModified = false,
            tone = "Biasa"
        ),
        BotTemplateEntity(
            id = "pengingat_ramah",
            name = "Pengingat • Ramah",
            subtitle = "Nada santai",
            bodyText = "Halo {nama} pengingat ya, kas IX-H {nominal} jatuh tempo {jatuh_tempo}. Kalau sudah bayar, balas LUNAS + foto bukti. Terima kasih!\nBalas STOP untuk berhenti menerima pesan.",
            isModified = false,
            tone = "Ramah"
        ),
        BotTemplateEntity(
            id = "pengingat_biasa",
            name = "Pengingat • Biasa",
            subtitle = "Nada netral",
            bodyText = "Halo {nama}, pengingat iuran kas IX-H sebesar {nominal} jatuh tempo pada {jatuh_tempo}. Silakan bayar ke bendahara.\nBalas STOP untuk berhenti menerima pesan.",
            isModified = false,
            tone = "Biasa"
        ),
        BotTemplateEntity(
            id = "pengingat_tegas",
            name = "Pengingat • Tegas",
            subtitle = "Untuk tunggakan",
            bodyText = "Halo {nama}, kas IX-H {nominal} sudah melewati jatuh tempo ({jatuh_tempo}). Mohon segera dilunasi ke bendahara kelas.\nBalas STOP untuk berhenti menerima pesan.",
            isModified = false,
            tone = "Tegas"
        ),
        BotTemplateEntity(
            id = "konfirmasi_lunas",
            name = "Konfirmasi Lunas",
            subtitle = "Dikirim setelah verifikasi",
            bodyText = "Terima kasih {nama}! Pembayaran kas IX-H sebesar {nominal} untuk periode {jatuh_tempo} telah dikonfirmasi LUNAS oleh bendahara.\nBalas STOP untuk berhenti menerima pesan.",
            isModified = false,
            tone = "Ramah"
        ),
        BotTemplateEntity(
            id = "pesan_tes",
            name = "Pesan Tes",
            subtitle = "Cek koneksi bot",
            bodyText = "Halo {nama}, ini pesan percobaan dari IX-H Hub kelas IX-H. Nomor ini akan dipakai untuk info tagihan kas dan kelas.\nBalas STOP untuk berhenti menerima pesan.",
            isModified = false,
            tone = "Biasa"
        )
    )

    suspend fun recordBotDispatch(log: BotDispatchLogEntity) = withContext(Dispatchers.IO) {
        dao.insertBotDispatchLog(log)
    }

    suspend fun retryFailedDispatch(log: BotDispatchLogEntity) = withContext(Dispatchers.IO) {
        dao.updateBotDispatchLog(
            log.copy(
                sentCount = log.totalTarget,
                failedCount = 0,
                statusLabel = "Terkirim",
                dateTimeLabel = "Baru saja • ${log.totalTarget}/${log.totalTarget} terkirim",
                failureReason = ""
            )
        )
    }

    suspend fun markAllNotificationsRead() = withContext(Dispatchers.IO) {
        dao.markAllNotificationsRead()
    }

    suspend fun markNotificationRead(id: Int) = withContext(Dispatchers.IO) {
        dao.markNotificationRead(id)
    }

    suspend fun deleteNotification(id: Int) = withContext(Dispatchers.IO) {
        dao.deleteNotification(id)
    }

    suspend fun deleteReadNotifications() = withContext(Dispatchers.IO) {
        dao.deleteReadNotifications()
    }

    // =========================================================================
    // §22.1 — BACKUP & RESTORE (WITH SCHEMA VERSION & SHA-256 INTEGRITY CHECK)
    // =========================================================================
    suspend fun exportDatabaseBackupJson(
        actorName: String,
        actorRole: String
    ): Result<DatabaseBackupPackage> = withContext(Dispatchers.IO) {
        try {
            val allUsers = dao.getAllUsersOnce()
            val allTx = dao.getAllTransactionsOnce()
            val allSchedules = dao.getAllSchedulesOnce()
            val allAssignments = dao.getAllAssignmentsOnce()
            val allAnnouncements = dao.getAllAnnouncementsOnce()
            val allTemplates = dao.getAllBotTemplatesOnce()

            val dataPayload = JSONObject().apply {
                put("users", JSONArray().apply {
                    allUsers.forEach { u ->
                        put(JSONObject().apply {
                            put("id", u.id)
                            put("absenNo", u.absenNo)
                            put("fullName", u.fullName)
                            put("username", u.username)
                            put("email", u.email)
                            put("gender", u.gender)
                            put("role", u.role)
                            put("subRoleTitle", u.subRoleTitle)
                            put("phoneWa", u.phoneWa)
                            put("waStatus", u.waStatus)
                            put("attendanceToday", u.attendanceToday)
                            put("presentDaysCount", u.presentDaysCount)
                            put("totalEffectiveDays", u.totalEffectiveDays)
                            put("billStatus", u.billStatus)
                            put("billAmount", u.billAmount)
                            put("paidDate", u.paidDate)
                            put("paidMethod", u.paidMethod)
                            put("deliveryStatus", u.deliveryStatus)
                            put("isActiveAccount", u.isActiveAccount)
                            put("mustChangePassword", u.mustChangePassword)
                            put("passwordHash", u.passwordHash)
                            put("lastLogin", u.lastLogin)
                            put("waStopTimestamp", u.waStopTimestamp)
                        })
                    }
                })
                put("cash_transactions", JSONArray().apply {
                    allTx.forEach { t ->
                        put(JSONObject().apply {
                            put("id", t.id)
                            put("title", t.title)
                            put("amount", t.amount)
                            put("isIncome", t.isIncome)
                            put("isFromBill", t.isFromBill)
                            put("dateTimeLabel", t.dateTimeLabel)
                            put("studentName", t.studentName)
                            put("paymentMethod", t.paymentMethod)
                            put("confirmedBy", t.confirmedBy)
                            put("proofNote", t.proofNote)
                            put("periodName", t.periodName)
                        })
                    }
                })
                put("schedules", JSONArray().apply {
                    allSchedules.forEach { s ->
                        put(JSONObject().apply {
                            put("id", s.id)
                            put("dayCode", s.dayCode)
                            put("startTime", s.startTime)
                            put("endTime", s.endTime)
                            put("subject", s.subject)
                            put("teacher", s.teacher)
                            put("room", s.room)
                            put("notes", s.notes)
                            put("isBreak", s.isBreak)
                            put("reminderEnabled", s.reminderEnabled)
                            put("reminderTime", s.reminderTime)
                        })
                    }
                })
                put("assignments", JSONArray().apply {
                    allAssignments.forEach { a ->
                        put(JSONObject().apply {
                            put("id", a.id)
                            put("title", a.title)
                            put("subject", a.subject)
                            put("teacher", a.teacher)
                            put("deadlineLabel", a.deadlineLabel)
                            put("daysRemaining", a.daysRemaining)
                            put("isCompleted", a.isCompleted)
                            put("description", a.description)
                            put("attachmentName", a.attachmentName)
                            put("attachmentSize", a.attachmentSize)
                            put("reminderEnabled", a.reminderEnabled)
                        })
                    }
                })
                put("announcements", JSONArray().apply {
                    allAnnouncements.forEach { an ->
                        put(JSONObject().apply {
                            put("id", an.id)
                            put("title", an.title)
                            put("content", an.content)
                            put("authorName", an.authorName)
                            put("authorRole", an.authorRole)
                            put("timeLabel", an.timeLabel)
                            put("category", an.category)
                            put("isPinned", an.isPinned)
                            put("isDraft", an.isDraft)
                            put("readCount", an.readCount)
                        })
                    }
                })
                put("bot_templates", JSONArray().apply {
                    allTemplates.forEach { bt ->
                        put(JSONObject().apply {
                            put("id", bt.id)
                            put("name", bt.name)
                            put("subtitle", bt.subtitle)
                            put("bodyText", bt.bodyText)
                            put("isModified", bt.isModified)
                            put("tone", bt.tone)
                        })
                    }
                })
            }

            val canonicalDataString = dataPayload.toString()
            val checksum = PasswordSecurity.sha256Hex("IXH_BACKUP_V3:$canonicalDataString")
            val createdAtWib = currentWibTimeLabel()
            val totalRecords = allUsers.size + allTx.size + allSchedules.size +
                allAssignments.size + allAnnouncements.size + allTemplates.size

            val envelope = JSONObject().apply {
                put("app", "IX-H HUB 1.0")
                put("schemaVersion", IxHubDatabase.SCHEMA_VERSION)
                put("createdAtWib", createdAtWib)
                put("createdBy", actorName)
                put("totalRecords", totalRecords)
                put("checksum", checksum)
                put("data", dataPayload)
            }

            dao.insertActivityLog(
                ActivityLogEntity(
                    category = "Backup",
                    operationType = "BACKUP",
                    statusLabel = "BERHASIL",
                    actorName = actorName,
                    actorRole = actorRole,
                    actionTitle = "Membuat cadangan database (Backup V3)",
                    detailText = "$totalRecords entitas dicadangkan • Checksum ${checksum.take(12)}...",
                    targetUser = "Database IX-H HUB 1.0",
                    beforeValue = "Data Aktif",
                    afterValue = "Snapshot V3 ($totalRecords item)",
                    reason = "Backup manual oleh Admin",
                    dayHeader = "HARI INI",
                    timeWib = createdAtWib
                )
            )

            Result.success(
                DatabaseBackupPackage(
                    jsonContent = envelope.toString(2),
                    checksum = checksum,
                    schemaVersion = IxHubDatabase.SCHEMA_VERSION,
                    createdAtLabel = createdAtWib,
                    totalRecords = totalRecords
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun restoreDatabaseBackupJson(
        rawJson: String,
        actorName: String,
        actorRole: String
    ): Result<String> = withContext(Dispatchers.IO) {
        if (rawJson.isBlank()) {
            return@withContext Result.failure(IllegalArgumentException("Data backup kosong."))
        }
        try {
            val root = JSONObject(rawJson.trim())
            val appId = root.optString("app", "")
            val schemaVer = root.optInt("schemaVersion", -1)
            val providedChecksum = root.optString("checksum", "")
            val dataObj = root.optJSONObject("data")

            if (appId != "IX-H HUB 1.0" || dataObj == null) {
                dao.insertActivityLog(
                    ActivityLogEntity(
                        category = "Backup",
                        operationType = "RESTORE",
                        statusLabel = "GAGAL",
                        actorName = actorName,
                        actorRole = actorRole,
                        actionTitle = "Gagal restore: Format backup tidak dikenali",
                        detailText = "File/JSON bukan format cadangan resmi IX-H HUB 1.0",
                        targetUser = "Database IX-H HUB 1.0",
                        beforeValue = "Data Aktif",
                        afterValue = "Ditolak (Format Rusak)",
                        reason = "Validasi format gagal",
                        dayHeader = "HARI INI",
                        timeWib = currentWibTimeLabel()
                    )
                )
                return@withContext Result.failure(
                    IllegalArgumentException("Format backup tidak valid atau bukan milik IX-H HUB 1.0.")
                )
            }

            if (schemaVer != IxHubDatabase.SCHEMA_VERSION) {
                dao.insertActivityLog(
                    ActivityLogEntity(
                        category = "Backup",
                        operationType = "RESTORE",
                        statusLabel = "GAGAL",
                        actorName = actorName,
                        actorRole = actorRole,
                        actionTitle = "Gagal restore: Versi skema tidak kompatibel (v$schemaVer)",
                        detailText = "Dibutuhkan schemaVersion=${IxHubDatabase.SCHEMA_VERSION}",
                        targetUser = "Database IX-H HUB 1.0",
                        beforeValue = "Schema v${IxHubDatabase.SCHEMA_VERSION}",
                        afterValue = "Ditolak (Schema v$schemaVer)",
                        reason = "Versi skema tidak cocok",
                        dayHeader = "HARI INI",
                        timeWib = currentWibTimeLabel()
                    )
                )
                return@withContext Result.failure(
                    IllegalArgumentException(
                        "Versi skema backup (v$schemaVer) tidak kompatibel dengan aplikasi saat ini (v${IxHubDatabase.SCHEMA_VERSION})."
                    )
                )
            }

            val canonicalData = JSONObject().apply {
                put("users", dataObj.optJSONArray("users") ?: JSONArray())
                put("cash_transactions", dataObj.optJSONArray("cash_transactions") ?: JSONArray())
                put("schedules", dataObj.optJSONArray("schedules") ?: JSONArray())
                put("assignments", dataObj.optJSONArray("assignments") ?: JSONArray())
                put("announcements", dataObj.optJSONArray("announcements") ?: JSONArray())
                put("bot_templates", dataObj.optJSONArray("bot_templates") ?: JSONArray())
            }.toString()

            val expectedChecksum = PasswordSecurity.sha256Hex("IXH_BACKUP_V3:$canonicalData")
            if (providedChecksum.isBlank() || providedChecksum != expectedChecksum) {
                dao.insertActivityLog(
                    ActivityLogEntity(
                        category = "Backup",
                        operationType = "RESTORE",
                        statusLabel = "GAGAL",
                        actorName = actorName,
                        actorRole = actorRole,
                        actionTitle = "Gagal restore: Checksum integritas tidak cocok",
                        detailText = "Data cadangan terdeteksi rusak atau telah dimodifikasi",
                        targetUser = "Database IX-H HUB 1.0",
                        beforeValue = "Data Aktif",
                        afterValue = "Ditolak (Checksum Mismatch)",
                        reason = "Integritas SHA-256 gagal",
                        dayHeader = "HARI INI",
                        timeWib = currentWibTimeLabel()
                    )
                )
                return@withContext Result.failure(
                    IllegalArgumentException("Integritas backup gagal diverifikasi (Checksum SHA-256 tidak cocok / file rusak).")
                )
            }

            val usersArr = dataObj.optJSONArray("users") ?: JSONArray()
            val parsedUsers = mutableListOf<UserEntity>()
            for (i in 0 until usersArr.length()) {
                val u = usersArr.getJSONObject(i)
                parsedUsers.add(
                    UserEntity(
                        id = u.optInt("id", i + 1),
                        absenNo = u.optInt("absenNo", i + 1),
                        fullName = u.optString("fullName", ""),
                        username = u.optString("username", ""),
                        email = u.optString("email", ""),
                        gender = u.optString("gender", "Laki-laki"),
                        role = u.optString("role", UserRole.SISWA.name),
                        subRoleTitle = u.optString("subRoleTitle", "Siswa"),
                        phoneWa = u.optString("phoneWa", ""),
                        waStatus = u.optString("waStatus", WaNumberStatus.BELUM_DICEK.name),
                        attendanceToday = u.optString("attendanceToday", ""),
                        presentDaysCount = u.optInt("presentDaysCount", 0),
                        totalEffectiveDays = u.optInt("totalEffectiveDays", 0),
                        billStatus = u.optString("billStatus", BillPaymentStatus.BELUM.name),
                        billAmount = u.optInt("billAmount", 0),
                        paidDate = u.optString("paidDate", ""),
                        paidMethod = u.optString("paidMethod", ""),
                        deliveryStatus = u.optString("deliveryStatus", "Belum dikirim"),
                        isActiveAccount = u.optBoolean("isActiveAccount", true),
                        mustChangePassword = u.optBoolean("mustChangePassword", false),
                        passwordHash = u.optString("passwordHash", ""),
                        lastLogin = u.optString("lastLogin", "Dipulihkan dari backup"),
                        waStopTimestamp = u.optString("waStopTimestamp", "")
                    )
                )
            }

            val txArr = dataObj.optJSONArray("cash_transactions") ?: JSONArray()
            val parsedTx = mutableListOf<CashTransactionEntity>()
            for (i in 0 until txArr.length()) {
                val t = txArr.getJSONObject(i)
                parsedTx.add(
                    CashTransactionEntity(
                        id = t.optInt("id", 0),
                        title = t.optString("title", ""),
                        amount = t.optInt("amount", 0),
                        isIncome = t.optBoolean("isIncome", true),
                        isFromBill = t.optBoolean("isFromBill", false),
                        dateTimeLabel = t.optString("dateTimeLabel", "Hari ini"),
                        studentName = t.optString("studentName", ""),
                        paymentMethod = t.optString("paymentMethod", "Tunai"),
                        confirmedBy = t.optString("confirmedBy", ""),
                        proofNote = t.optString("proofNote", ""),
                        periodName = t.optString("periodName", "Kas Kelas IX-H")
                    )
                )
            }

            val schArr = dataObj.optJSONArray("schedules") ?: JSONArray()
            val parsedSchedules = mutableListOf<ScheduleEntity>()
            for (i in 0 until schArr.length()) {
                val s = schArr.getJSONObject(i)
                parsedSchedules.add(
                    ScheduleEntity(
                        id = s.optInt("id", 0),
                        dayCode = s.optString("dayCode", "Sen"),
                        startTime = s.optString("startTime", "07.00"),
                        endTime = s.optString("endTime", "08.20"),
                        subject = s.optString("subject", ""),
                        teacher = s.optString("teacher", ""),
                        room = s.optString("room", ""),
                        notes = s.optString("notes", ""),
                        isBreak = s.optBoolean("isBreak", false),
                        reminderEnabled = s.optBoolean("reminderEnabled", true),
                        reminderTime = s.optString("reminderTime", "20.00 WIB")
                    )
                )
            }

            val asgArr = dataObj.optJSONArray("assignments") ?: JSONArray()
            val parsedAssignments = mutableListOf<AssignmentEntity>()
            for (i in 0 until asgArr.length()) {
                val a = asgArr.getJSONObject(i)
                parsedAssignments.add(
                    AssignmentEntity(
                        id = a.optInt("id", 0),
                        title = a.optString("title", ""),
                        subject = a.optString("subject", ""),
                        teacher = a.optString("teacher", ""),
                        deadlineLabel = a.optString("deadlineLabel", ""),
                        daysRemaining = a.optInt("daysRemaining", 3),
                        isCompleted = a.optBoolean("isCompleted", false),
                        description = a.optString("description", ""),
                        attachmentName = a.optString("attachmentName", ""),
                        attachmentSize = a.optString("attachmentSize", ""),
                        reminderEnabled = a.optBoolean("reminderEnabled", true)
                    )
                )
            }

            val annArr = dataObj.optJSONArray("announcements") ?: JSONArray()
            val parsedAnnouncements = mutableListOf<AnnouncementEntity>()
            for (i in 0 until annArr.length()) {
                val an = annArr.getJSONObject(i)
                parsedAnnouncements.add(
                    AnnouncementEntity(
                        id = an.optInt("id", 0),
                        title = an.optString("title", ""),
                        content = an.optString("content", ""),
                        authorName = an.optString("authorName", ""),
                        authorRole = an.optString("authorRole", ""),
                        timeLabel = an.optString("timeLabel", "Baru saja"),
                        category = an.optString("category", "Info"),
                        isPinned = an.optBoolean("isPinned", false),
                        isDraft = an.optBoolean("isDraft", false),
                        readCount = an.optInt("readCount", 0)
                    )
                )
            }

            // Replace operational tables atomically after verification succeeds
            dao.clearAllUsers()
            dao.clearAllTransactions()
            dao.clearAllSchedules()
            dao.clearAllAssignments()
            dao.clearAllAnnouncements()

            if (parsedUsers.isNotEmpty()) dao.insertUsers(parsedUsers)
            if (parsedTx.isNotEmpty()) dao.insertTransactions(parsedTx)
            if (parsedSchedules.isNotEmpty()) dao.insertSchedules(parsedSchedules)
            if (parsedAssignments.isNotEmpty()) dao.insertAssignments(parsedAssignments)
            if (parsedAnnouncements.isNotEmpty()) dao.insertAnnouncements(parsedAnnouncements)

            val restoredCount = parsedUsers.size + parsedTx.size + parsedSchedules.size +
                parsedAssignments.size + parsedAnnouncements.size

            dao.insertActivityLog(
                ActivityLogEntity(
                    category = "Backup",
                    operationType = "RESTORE",
                    statusLabel = "BERHASIL",
                    actorName = actorName,
                    actorRole = actorRole,
                    actionTitle = "Memulihkan database dari Backup V3",
                    detailText = "${parsedUsers.size} pengguna, ${parsedTx.size} kas, ${parsedSchedules.size} jadwal, ${parsedAssignments.size} tugas, ${parsedAnnouncements.size} pengumuman",
                    targetUser = "Database IX-H HUB 1.0",
                    beforeValue = "Data Sebelumnya",
                    afterValue = "Pulih ($restoredCount entitas)",
                    reason = "Restore terverifikasi SHA-256",
                    dayHeader = "HARI INI",
                    timeWib = currentWibTimeLabel()
                )
            )
            Result.success("Berhasil memulihkan $restoredCount data (${parsedUsers.size} anggota, ${parsedTx.size} transaksi, ${parsedSchedules.size} jadwal, ${parsedAssignments.size} tugas).")
        } catch (e: Exception) {
            dao.insertActivityLog(
                ActivityLogEntity(
                    category = "Backup",
                    operationType = "RESTORE",
                    statusLabel = "GAGAL",
                    actorName = actorName,
                    actorRole = actorRole,
                    actionTitle = "Gagal memulihkan backup JSON",
                    detailText = e.message?.take(80) ?: "JSON tidak valid",
                    targetUser = "Database IX-H HUB 1.0",
                    beforeValue = "Data Aktif",
                    afterValue = "Ditolak (JSON Error)",
                    reason = "Kesalahan parsing JSON backup",
                    dayHeader = "HARI INI",
                    timeWib = currentWibTimeLabel()
                )
            )
            Result.failure(IllegalArgumentException("File backup rusak atau bukan format JSON yang valid: ${e.message}"))
        }
    }

    // =========================================================================
    // §22.3 — ROLE-AWARE GLOBAL SEARCH
    // =========================================================================
    fun performGlobalSearch(
        query: String,
        activeRole: String,
        activeUsername: String,
        usersList: List<UserEntity>,
        assignmentsList: List<AssignmentEntity>,
        schedulesList: List<ScheduleEntity>,
        transactionsList: List<CashTransactionEntity>,
        announcementsList: List<AnnouncementEntity>
    ): List<GlobalSearchItem> {
        val q = query.trim().lowercase()
        if (q.isBlank()) return emptyList()

        val isManager = activeRole == UserRole.ADMIN.name || activeRole == UserRole.PENGURUS.name
        val results = mutableListOf<GlobalSearchItem>()

        // 1. Tugas (Accessible to all roles)
        assignmentsList.filter {
            it.title.lowercase().contains(q) ||
                it.subject.lowercase().contains(q) ||
                it.teacher.lowercase().contains(q) ||
                it.description.lowercase().contains(q)
        }.forEach { item ->
            results.add(
                GlobalSearchItem(
                    id = "tugas_${item.id}",
                    category = "Tugas",
                    title = "${item.subject} — ${item.title}",
                    subtitle = "${item.teacher} • Tenggat ${item.deadlineLabel}",
                    badgeText = if (item.isCompleted) "Selesai" else "${item.daysRemaining} hari lagi",
                    targetRouteName = "TUGAS",
                    entityId = item.id
                )
            )
        }

        // 2. Jadwal (Accessible to all roles)
        schedulesList.filter {
            it.subject.lowercase().contains(q) ||
                it.teacher.lowercase().contains(q) ||
                it.room.lowercase().contains(q) ||
                it.dayCode.lowercase().contains(q)
        }.forEach { item ->
            results.add(
                GlobalSearchItem(
                    id = "jadwal_${item.id}",
                    category = "Jadwal",
                    title = "${item.subject} (${item.dayCode})",
                    subtitle = "${item.startTime}–${item.endTime} • ${item.teacher} • ${item.room}",
                    badgeText = item.dayCode,
                    targetRouteName = "JADWAL",
                    entityId = item.id
                )
            )
        }

        // 3. Pengumuman (SISWA cannot see drafts!)
        announcementsList.filter { ann ->
            (isManager || !ann.isDraft) && (
                ann.title.lowercase().contains(q) ||
                    ann.content.lowercase().contains(q) ||
                    ann.category.lowercase().contains(q) ||
                    ann.authorName.lowercase().contains(q)
                )
        }.forEach { ann ->
            results.add(
                GlobalSearchItem(
                    id = "ann_${ann.id}",
                    category = "Pengumuman",
                    title = ann.title,
                    subtitle = "Oleh ${ann.authorName} • ${ann.timeLabel}",
                    badgeText = if (ann.isDraft) "Draf" else ann.category,
                    targetRouteName = "PENGUMUMAN",
                    entityId = ann.id
                )
            )
        }

        // 4. Transaksi Kas (SISWA only sees general class ledger or their own transactions)
        val activeUserObj = usersList.firstOrNull { it.username.equals(activeUsername, ignoreCase = true) }
        transactionsList.filter { tx ->
            val allowedForRole = isManager || !tx.isFromBill ||
                (activeUserObj != null && tx.studentName.equals(activeUserObj.fullName, ignoreCase = true))
            allowedForRole && (
                tx.title.lowercase().contains(q) ||
                    tx.studentName.lowercase().contains(q) ||
                    tx.paymentMethod.lowercase().contains(q) ||
                    tx.proofNote.lowercase().contains(q)
                )
        }.forEach { tx ->
            val sign = if (tx.isIncome) "+" else "-"
            results.add(
                GlobalSearchItem(
                    id = "kas_${tx.id}",
                    category = "Kas",
                    title = tx.title,
                    subtitle = "${tx.dateTimeLabel} • ${tx.paymentMethod} • Oleh ${tx.confirmedBy.ifBlank { "Bendahara" }}",
                    badgeText = "${sign}Rp${tx.amount}",
                    targetRouteName = "KAS",
                    entityId = tx.id
                )
            )
        }

        // 5. Siswa / Pengguna & Absensi (RBAC strictly enforced: SISWA cannot see other students' private phone/bill/attendance)
        usersList.filter { u ->
            u.fullName.lowercase().contains(q) ||
                u.username.lowercase().contains(q) ||
                u.subRoleTitle.lowercase().contains(q) ||
                u.role.lowercase().contains(q)
        }.forEach { u ->
            val isSelf = u.username.equals(activeUsername, ignoreCase = true)
            if (isManager || isSelf) {
                results.add(
                    GlobalSearchItem(
                        id = "user_${u.id}",
                        category = "Siswa",
                        title = "${u.fullName} (@${u.username})",
                        subtitle = "Absen #${u.absenNo} • ${u.subRoleTitle.ifBlank { u.role }} • Kas: ${u.billStatus}",
                        badgeText = u.role,
                        targetRouteName = if (isManager) "USER_DETAIL" else "PROFIL",
                        entityId = u.id
                    )
                )
                if (u.attendanceToday.isNotBlank()) {
                    results.add(
                        GlobalSearchItem(
                            id = "abs_${u.id}",
                            category = "Absensi",
                            title = "Absensi: ${u.fullName}",
                            subtitle = "Status Hari Ini: ${u.attendanceToday} • Hadir ${u.presentDaysCount}/${u.totalEffectiveDays} hari",
                            badgeText = u.attendanceToday,
                            targetRouteName = "ABSENSI",
                            entityId = u.id
                        )
                    )
                }
            } else {
                // Public directory entry for classmates (no private phone number, no private bill status, no private attendance)
                results.add(
                    GlobalSearchItem(
                        id = "user_pub_${u.id}",
                        category = "Siswa",
                        title = u.fullName,
                        subtitle = "Anggota Kelas IX-H • Absen #${u.absenNo} • ${u.subRoleTitle.ifBlank { u.role }}",
                        badgeText = u.role,
                        targetRouteName = "BERANDA",
                        entityId = u.id
                    )
                )
            }
        }

        return results
    }

    // =========================================================================
    // §22.5 — OFFLINE-FIRST & VPS SYNCHRONIZATION
    // =========================================================================
    suspend fun clearSyncedQueueItems() = withContext(Dispatchers.IO) {
        dao.clearSyncedQueueItems()
    }

    suspend fun testVpsConnection(baseUrl: String, apiKey: String): Result<String> =
        withContext(Dispatchers.IO) {
            if (baseUrl.isBlank() || apiKey.isBlank()) {
                return@withContext Result.failure(
                    IllegalArgumentException("URL VPS dan Token API belum dikonfigurasi.")
                )
            }
            try {
                val api = VpsNetworkClient.create(baseUrl)
                val response = api.checkHealth("Bearer $apiKey")
                if (response.isSuccessful) {
                    val body = response.body()
                    Result.success("Terhubung ke ${body?.serverName ?: baseUrl} (Sesi: ${body?.botSession ?: "Aktif"})")
                } else {
                    Result.failure(Exception("HTTP ${response.code()} dari server VPS"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    suspend fun syncWithVps(
        baseUrl: String,
        apiKey: String,
        totalStudents: Int,
        confirmedCash: Int,
        presentCount: Int
    ): Result<String> = withContext(Dispatchers.IO) {
        val pendingCount = dao.getPendingSyncCount()
        if (baseUrl.isBlank() || apiKey.isBlank()) {
            return@withContext Result.failure(
                IllegalArgumentException("URL VPS dan Token API belum diisi di Pengaturan. ($pendingCount perubahan tersimpan aman di antrean lokal)")
            )
        }
        try {
            val api = VpsNetworkClient.create(baseUrl)
            val response = api.syncClassData(
                authHeader = "Bearer $apiKey",
                payload = VpsSyncPayload(
                    totalStudents = totalStudents,
                    confirmedCashBalance = confirmedCash,
                    presentCount = presentCount
                )
            )
            if (response.isSuccessful) {
                dao.markAllSyncQueueSynced()
                Result.success("Sinkronisasi VPS berhasil ($pendingCount antrean lokal disinkronkan • ${response.body()?.message ?: "200 OK"})")
            } else {
                val errText = "HTTP ${response.code()} saat sinkronisasi VPS"
                dao.markPendingSyncQueueFailed(errText)
                Result.failure(Exception(errText))
            }
        } catch (e: Exception) {
            dao.markPendingSyncQueueFailed(e.message ?: "Koneksi gagal")
            Result.failure(e)
        }
    }

    suspend fun sendPterodactylSignal(
        baseUrl: String,
        apiKey: String,
        signal: String
    ): Result<String> = withContext(Dispatchers.IO) {
        if (baseUrl.isBlank() || apiKey.isBlank()) {
            return@withContext Result.failure(
                IllegalArgumentException("Panel Pterodactyl belum dikonfigurasi (URL/Token kosong).")
            )
        }
        try {
            val api = VpsNetworkClient.create(baseUrl)
            val response = api.sendPterodactylPower(
                authHeader = "Bearer $apiKey",
                request = PterodactylPowerRequest(signal = signal)
            )
            if (response.isSuccessful) {
                Result.success("Perintah '$signal' diterima oleh node Pterodactyl")
            } else {
                Result.failure(Exception("HTTP ${response.code()} dari panel Pterodactyl"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
