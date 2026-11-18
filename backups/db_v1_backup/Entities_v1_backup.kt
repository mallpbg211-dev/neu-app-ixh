package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

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

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: Int,
    val absenNo: Int,
    val fullName: String,
    val username: String,
    val email: String,
    val gender: String, // "Perempuan" or "Laki-laki"
    val role: String, // UserRole.name
    val subRoleTitle: String = "", // e.g., "Bendahara"
    val phoneWa: String,
    val waStatus: String, // WaNumberStatus.name
    val attendanceToday: String, // AttendanceStatus.name
    val presentDaysCount: Int = 28,
    val totalEffectiveDays: Int = 29,
    val billStatus: String, // BillPaymentStatus.name
    val billAmount: Int = 6000,
    val paidDate: String = "",
    val paidMethod: String = "", // Tunai, Transfer, QRIS
    val deliveryStatus: String = "Terkirim", // Terkirim, Menunggu, Gagal, Dilewati
    val isActiveAccount: Boolean = true,
    val mustChangePassword: Boolean = false,
    val tempPassword: String = "",
    val lastLogin: String = "Hari ini, 06:42",
    val waStopTimestamp: String = ""
)

@Entity(tableName = "cash_transactions")
data class CashTransactionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val amount: Int,
    val isIncome: Boolean,
    val isFromBill: Boolean,
    val dateTimeLabel: String,
    val studentName: String = "",
    val paymentMethod: String = "Tunai", // Tunai, Transfer, QRIS
    val confirmedBy: String = "Aisyah Putri",
    val proofNote: String = "",
    val periodName: String = "Kas Minggu ke-1 November"
)

@Entity(tableName = "schedules")
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
    val reminderTime: String = "20.00 WIB"
)

@Entity(tableName = "assignments")
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
    val reminderEnabled: Boolean = true
)

@Entity(tableName = "announcements")
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
    val readCount: Int = 24
)

@Entity(tableName = "notifications")
data class NotificationEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val subtitle: String,
    val timeLabel: String,
    val dayGroup: String, // HARI INI, KEMARIN
    val category: String, // Tugas, Jadwal, Pengumuman, Kas, Absensi, Bot
    val isUnread: Boolean = true,
    val targetRoute: String = "tugas" // tugas, jadwal, pengumuman, kas, absensi, bot
)

@Entity(tableName = "activity_logs")
data class ActivityLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val category: String, // Lunas, Absensi, Role, Hapus, Berhenti
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
    val deviceName: String = "Redmi 9A • Android"
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
    val failureReason: String = ""
)
