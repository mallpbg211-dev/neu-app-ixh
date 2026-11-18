package com.example.data.local

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.security.MessageDigest

object PasswordSecurity {
    private const val SALT_PREFIX = "ixh_hub_v2_salt"

    fun hashPassword(username: String, rawPassword: String): String {
        val normalizedUser = username.trim().lowercase()
        val input = "$SALT_PREFIX:$normalizedUser:$rawPassword"
        val bytes = MessageDigest.getInstance("SHA-256").digest(input.toByteArray(Charsets.UTF_8))
        return bytes.joinToString("") { "%02x".format(it) }
    }

    fun hashPassword(rawPassword: String): String = hashPassword("", rawPassword)

    fun sha256Hex(payload: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(payload.toByteArray(Charsets.UTF_8))
        return bytes.joinToString("") { "%02x".format(it) }
    }

    fun verifyPassword(username: String, rawPassword: String, storedHash: String): Boolean {
        if (storedHash.isBlank() || rawPassword.isBlank()) return false
        return hashPassword(username, rawPassword) == storedHash ||
            hashPassword("", rawPassword) == storedHash
    }

    fun verifyPassword(rawPassword: String, storedHash: String): Boolean {
        if (storedHash.isBlank() || rawPassword.isBlank()) return false
        return hashPassword("", rawPassword) == storedHash
    }

    fun isStrongEnough(rawPassword: String): Boolean {
        if (rawPassword.length < 8) return false
        val hasLetter = rawPassword.any { it.isLetter() }
        val hasDigit = rawPassword.any { it.isDigit() }
        return hasLetter && hasDigit
    }
}

enum class UserRole(val label: String) {
    ADMIN("ADMIN"),
    PENGURUS("PENGURUS"),
    SISWA("SISWA")
}

enum class WaNumberStatus(val label: String) {
    VALID("Valid"),
    BELUM_DICEK("Belum dicek"),
    TIDAK_DI_WA("Tidak di WA"),
    BERHENTI("Berhenti")
}

enum class AttendanceStatus(val code: String, val label: String) {
    HADIR("H", "Hadir"),
    IZIN("I", "Izin"),
    SAKIT("S", "Sakit"),
    ALPHA("A", "Alpha")
}

enum class BillPaymentStatus(val label: String) {
    LUNAS("Lunas"),
    BELUM("Belum"),
    TUNGGAKAN("Tunggakan")
}

@Entity(
    tableName = "users",
    indices = [
        Index(value = ["username"], unique = true),
        Index(value = ["role"])
    ]
)
data class UserEntity(
    @PrimaryKey val id: Int = 0,
    val absenNo: Int = 1,
    val fullName: String,
    val username: String,
    val email: String = "",
    val gender: String = "Laki-laki", // "Perempuan" or "Laki-laki"
    val role: String = UserRole.SISWA.name, // UserRole.name
    val subRoleTitle: String = "", // e.g., "Ketua Kelas & Admin", "Bendahara", "Siswa"
    val phoneWa: String = "",
    val waStatus: String = WaNumberStatus.BELUM_DICEK.name,
    val attendanceToday: String = "", // Empty until attendance is recorded
    val presentDaysCount: Int = 0,
    val totalEffectiveDays: Int = 0,
    val billStatus: String = BillPaymentStatus.BELUM.name,
    val billAmount: Int = 0,
    val paidDate: String = "",
    val paidMethod: String = "", // Tunai, Transfer, QRIS
    val deliveryStatus: String = "Belum dikirim",
    val isActiveAccount: Boolean = true,
    val mustChangePassword: Boolean = false,
    val tempPassword: String = "",
    val passwordHash: String = "",
    val lastLogin: String = "Belum pernah login",
    val waStopTimestamp: String = "",
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(
    tableName = "cash_transactions",
    indices = [Index(value = ["isIncome"])]
)
data class CashTransactionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val amount: Int,
    val isIncome: Boolean,
    val isFromBill: Boolean,
    val dateTimeLabel: String,
    val studentName: String = "",
    val paymentMethod: String = "Tunai", // Tunai, Transfer, QRIS
    val confirmedBy: String = "",
    val proofNote: String = "",
    val periodName: String = "Kas Kelas IX-H",
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(
    tableName = "schedules",
    indices = [Index(value = ["dayCode"])]
)
data class ScheduleEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val dayCode: String, // Sen, Sel, Rab, Kam, Jum, Sab
    val startTime: String,
    val endTime: String,
    val subject: String,
    val teacher: String,
    val room: String,
    val notes: String = "",
    val isBreak: Boolean = false,
    val reminderEnabled: Boolean = true,
    val reminderTime: String = "20.00 WIB",
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(
    tableName = "assignments",
    indices = [Index(value = ["isCompleted"])]
)
data class AssignmentEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val subject: String,
    val teacher: String,
    val deadlineLabel: String,
    val daysRemaining: Int, // <=1 red, 2..3 yellow, >=4 blue
    val isCompleted: Boolean,
    val description: String,
    val attachmentName: String = "",
    val attachmentSize: String = "",
    val reminderEnabled: Boolean = true,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(
    tableName = "announcements",
    indices = [Index(value = ["isPinned"])]
)
data class AnnouncementEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val content: String,
    val authorName: String,
    val authorRole: String,
    val timeLabel: String,
    val category: String, // Penting, Kegiatan, Info
    val isPinned: Boolean = false,
    val isDraft: Boolean = false,
    val attachmentName: String = "",
    val attachmentSize: String = "",
    val readCount: Int = 0,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(
    tableName = "notifications",
    indices = [
        Index(value = ["isUnread"]),
        Index(value = ["eventKey"])
    ]
)
data class NotificationEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val subtitle: String,
    val timeLabel: String,
    val dayGroup: String, // HARI INI, KEMARIN
    val category: String, // Tugas, Jadwal, Pengumuman, Kas, Absensi, Bot
    val isUnread: Boolean = true,
    val targetRoute: String = "tugas", // tugas, jadwal, pengumuman, kas, absensi, bot
    val eventKey: String = "", // Deduplication key so retry/sync never duplicates notifications
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(
    tableName = "activity_logs",
    indices = [
        Index(value = ["category"]),
        Index(value = ["actorRole"])
    ]
)
data class ActivityLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val category: String, // Lunas, Absensi, Role, Hapus, Berhenti, Sistem, Backup, Login
    val operationType: String = "UPDATE", // CREATE, UPDATE, DELETE, LOGIN, LOGOUT, BACKUP, RESTORE, ROLE_CHANGE
    val statusLabel: String = "BERHASIL", // BERHASIL, GAGAL
    val actorName: String,
    val actorRole: String,
    val actionTitle: String,
    val detailText: String,
    val targetUser: String,
    val beforeValue: String,
    val afterValue: String,
    val reason: String,
    val dayHeader: String,
    val timeWib: String,
    val deviceName: String = "Android • IX-H HUB 1.0",
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "bot_templates")
data class BotTemplateEntity(
    @PrimaryKey val id: String,
    val name: String,
    val subtitle: String,
    val bodyText: String,
    val isModified: Boolean = false,
    val tone: String = "Biasa" // Ramah, Biasa, Tegas
)

@Entity(tableName = "bot_dispatch_logs")
data class BotDispatchLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val category: String, // Tagihan, Absensi, Kas, Jadwal, Tugas
    val dateTimeLabel: String,
    val sentCount: Int,
    val totalTarget: Int,
    val failedCount: Int,
    val statusLabel: String, // Terkirim, Sebagian gagal, Gagal, Berhasil
    val failureReason: String = "",
    val createdAt: Long = System.currentTimeMillis()
)

/**
 * Offline-First Persistent Synchronization Queue (Master Prompt Final V2 §22.5)
 * Tracks local mutations with idempotency keys (eventId) and explicit conflict resolution policy.
 */
@Entity(
    tableName = "sync_queue",
    indices = [
        Index(value = ["eventId"], unique = true),
        Index(value = ["status"])
    ]
)
data class SyncQueueEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val eventId: String,
    val entityType: String, // Absensi, Kas, Jadwal, Tugas, Pengumuman, Pengguna
    val operation: String, // CREATE, UPDATE, DELETE
    val summary: String,
    val status: String = "PENDING", // PENDING, SYNCED, FAILED
    val conflictRule: String = "LAST_WRITE_WINS", // APPEND_LEDGER, SERVER_AUTHORITATIVE_MERGE, LAST_WRITE_WINS
    val actorName: String = "",
    val lastError: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
