package com.example.data.repository

import com.example.data.local.ActivityLogEntity
import com.example.data.local.AnnouncementEntity
import com.example.data.local.AssignmentEntity
import com.example.data.local.AttendanceStatus
import com.example.data.local.BillPaymentStatus
import com.example.data.local.BotDispatchLogEntity
import com.example.data.local.BotTemplateEntity
import com.example.data.local.CashTransactionEntity
import com.example.data.local.NotificationEntity
import com.example.data.local.ScheduleEntity
import com.example.data.local.UserEntity
import com.example.data.local.UserRole
import com.example.data.local.WaNumberStatus

object IxHubSeedData {

    fun initialUsers(): List<UserEntity> {
        val explicitUsers = listOf(
            UserEntity(
                id = 1,
                absenNo = 1,
                fullName = "Ahmad Ramadhan",
                username = "ahmad.ramadhan",
                email = "ahmad.ramadhan@spensari.sch.id",
                gender = "Laki-laki",
                role = UserRole.ADMIN.name,
                subRoleTitle = "Ketua Kelas & Admin",
                phoneWa = "+62 812-3456-7890",
                waStatus = WaNumberStatus.VALID.name,
                attendanceToday = AttendanceStatus.HADIR.name,
                presentDaysCount = 29,
                totalEffectiveDays = 29,
                billStatus = BillPaymentStatus.LUNAS.name,
                billAmount = 6000,
                paidDate = "4 Nov",
                paidMethod = "Tunai",
                deliveryStatus = "Terkirim",
                lastLogin = "Hari ini, 07:55"
            ),
            UserEntity(
                id = 2,
                absenNo = 2,
                fullName = "Aisyah Putri",
                username = "aisyah.putri",
                email = "aisyah.putri@spensari.sch.id",
                gender = "Perempuan",
                role = UserRole.PENGURUS.name,
                subRoleTitle = "Bendahara",
                phoneWa = "+62 813-2211-4567",
                waStatus = WaNumberStatus.VALID.name,
                attendanceToday = AttendanceStatus.HADIR.name,
                presentDaysCount = 29,
                totalEffectiveDays = 29,
                billStatus = BillPaymentStatus.LUNAS.name,
                billAmount = 6000,
                paidDate = "4 Nov",
                paidMethod = "Tunai",
                deliveryStatus = "Terkirim",
                lastLogin = "Hari ini, 07:10"
            ),
            UserEntity(
                id = 3,
                absenNo = 3,
                fullName = "Bima Sakti",
                username = "bima.s",
                email = "bima.sakti@spensari.sch.id",
                gender = "Laki-laki",
                role = UserRole.SISWA.name,
                subRoleTitle = "Siswa",
                phoneWa = "+62 857-9012-3344",
                waStatus = WaNumberStatus.VALID.name,
                attendanceToday = AttendanceStatus.IZIN.name,
                presentDaysCount = 29,
                totalEffectiveDays = 29,
                billStatus = BillPaymentStatus.LUNAS.name,
                billAmount = 6000,
                paidDate = "4 Nov • 09.12",
                paidMethod = "Transfer",
                deliveryStatus = "Terkirim",
                lastLogin = "Hari ini, 06:42"
            ),
            UserEntity(
                id = 4,
                absenNo = 4,
                fullName = "Citra Lestari",
                username = "citra.l",
                email = "citra.lestari@spensari.sch.id",
                gender = "Perempuan",
                role = UserRole.SISWA.name,
                subRoleTitle = "Siswa",
                phoneWa = "+62 821-7788-9900",
                waStatus = WaNumberStatus.VALID.name,
                attendanceToday = AttendanceStatus.SAKIT.name,
                presentDaysCount = 27,
                totalEffectiveDays = 29,
                billStatus = BillPaymentStatus.BELUM.name,
                billAmount = 6000,
                paidDate = "",
                paidMethod = "",
                deliveryStatus = "Gagal",
                lastLogin = "Hari ini, 08:14"
            ),
            UserEntity(
                id = 5,
                absenNo = 5,
                fullName = "Daffa Prakarsa",
                username = "daffa.p",
                email = "daffa.prakarsa@spensari.sch.id",
                gender = "Laki-laki",
                role = UserRole.SISWA.name,
                subRoleTitle = "Siswa",
                phoneWa = "0813-5566-7745",
                waStatus = WaNumberStatus.BERHENTI.name,
                attendanceToday = AttendanceStatus.HADIR.name,
                presentDaysCount = 28,
                totalEffectiveDays = 29,
                billStatus = BillPaymentStatus.BELUM.name,
                billAmount = 6000,
                paidDate = "",
                paidMethod = "",
                deliveryStatus = "Menunggu",
                waStopTimestamp = "Sel, 3 Nov 15.52"
            ),
            UserEntity(
                id = 6,
                absenNo = 6,
                fullName = "Eka Wijaya",
                username = "eka.w",
                email = "eka.wijaya@spensari.sch.id",
                gender = "Laki-laki",
                role = UserRole.SISWA.name,
                subRoleTitle = "Siswa",
                phoneWa = "0857-4412-1190",
                waStatus = WaNumberStatus.BELUM_DICEK.name,
                attendanceToday = AttendanceStatus.HADIR.name,
                presentDaysCount = 26,
                totalEffectiveDays = 29,
                billStatus = BillPaymentStatus.TUNGGAKAN.name,
                billAmount = 12000,
                paidDate = "2 minggu",
                paidMethod = "",
                deliveryStatus = "Dilewati"
            ),
            UserEntity(
                id = 7,
                absenNo = 7,
                fullName = "Fajar Nugroho",
                username = "fajar.n",
                email = "fajar.nugroho@spensari.sch.id",
                gender = "Laki-laki",
                role = UserRole.SISWA.name,
                subRoleTitle = "Siswa",
                phoneWa = "+62 812-9988-7766",
                waStatus = WaNumberStatus.VALID.name,
                attendanceToday = AttendanceStatus.ALPHA.name,
                presentDaysCount = 25,
                totalEffectiveDays = 29,
                billStatus = BillPaymentStatus.LUNAS.name,
                billAmount = 6000,
                paidDate = "4 Nov",
                paidMethod = "Tunai",
                deliveryStatus = "Gagal"
            ),
            UserEntity(
                id = 8,
                absenNo = 8,
                fullName = "Gita Nuraini",
                username = "gita.n",
                email = "gita.nuraini@spensari.sch.id",
                gender = "Perempuan",
                role = UserRole.SISWA.name,
                subRoleTitle = "Siswa",
                phoneWa = "0812-3301-3302",
                waStatus = WaNumberStatus.TIDAK_DI_WA.name,
                attendanceToday = AttendanceStatus.HADIR.name,
                presentDaysCount = 28,
                totalEffectiveDays = 29,
                billStatus = BillPaymentStatus.LUNAS.name,
                billAmount = 6000,
                paidDate = "4 Nov • 08.40",
                paidMethod = "QRIS",
                deliveryStatus = "Dilewati"
            ),
            UserEntity(
                id = 9,
                absenNo = 9,
                fullName = "Hana Safitri",
                username = "hana.s",
                email = "hana.safitri@spensari.sch.id",
                gender = "Perempuan",
                role = UserRole.SISWA.name,
                subRoleTitle = "Sekretaris 1",
                phoneWa = "+62 852-6677-8899",
                waStatus = WaNumberStatus.VALID.name,
                attendanceToday = AttendanceStatus.HADIR.name,
                presentDaysCount = 29,
                totalEffectiveDays = 29,
                billStatus = BillPaymentStatus.LUNAS.name,
                billAmount = 6000,
                paidDate = "4 Nov • 10.05",
                paidMethod = "Tunai",
                deliveryStatus = "Gagal"
            ),
            UserEntity(
                id = 10,
                absenNo = 10,
                fullName = "Irfan Hakim",
                username = "irfan.h",
                email = "irfan.hakim@spensari.sch.id",
                gender = "Laki-laki",
                role = UserRole.SISWA.name,
                subRoleTitle = "Wakil Ketua",
                phoneWa = "+62 819-1122-3344",
                waStatus = WaNumberStatus.VALID.name,
                attendanceToday = AttendanceStatus.HADIR.name,
                presentDaysCount = 28,
                totalEffectiveDays = 29,
                billStatus = BillPaymentStatus.LUNAS.name,
                billAmount = 6000,
                paidDate = "4 Nov",
                paidMethod = "Tunai",
                deliveryStatus = "Terkirim"
            ),
            UserEntity(
                id = 11,
                absenNo = 11,
                fullName = "Rangga Putra",
                username = "rangga.p",
                email = "rangga.putra@spensari.sch.id",
                gender = "Laki-laki",
                role = UserRole.SISWA.name,
                subRoleTitle = "Seksi Olahraga",
                phoneWa = "0815-2233-4455",
                waStatus = WaNumberStatus.VALID.name,
                attendanceToday = AttendanceStatus.HADIR.name,
                presentDaysCount = 28,
                totalEffectiveDays = 29,
                billStatus = BillPaymentStatus.LUNAS.name,
                billAmount = 6000,
                paidDate = "4 Nov",
                paidMethod = "Transfer",
                deliveryStatus = "Terkirim",
                waStopTimestamp = "Aktif kembali Rab, 4 Nov 07.12"
            )
        )

        val remainingNames = listOf(
            "Jihan Aulia" to "Perempuan",
            "Kevin Pratama" to "Laki-laki",
            "Laila Rahmawati" to "Perempuan",
            "Muhammad Rizky" to "Laki-laki",
            "Nada Savitri" to "Perempuan",
            "Oki Setiawan" to "Laki-laki",
            "Putri Maharani" to "Perempuan",
            "Qori Amelia" to "Perempuan",
            "Rafi Aditya" to "Laki-laki",
            "Salsabila Azzahra" to "Perempuan",
            "Taufik Hidayat" to "Laki-laki",
            "Ulfa Khairunnisa" to "Perempuan",
            "Vino Bastian" to "Laki-laki",
            "Wulan Sari" to "Perempuan",
            "Yoga Kurniawan" to "Laki-laki",
            "Zahra Nabila" to "Perempuan",
            "Arif Budiman" to "Laki-laki",
            "Bella Shofie" to "Perempuan",
            "Cahyo Nugroho" to "Laki-laki",
            "Dewi Sartika" to "Perempuan",
            "Ikhsan Maulana" to "Laki-laki"
        )

        // Exact math alignment with storyboards:
        // Total = 32 students
        // Attendance 4 Nov: 28 Hadir, 2 Izin (Bima #3 + Nada #16), 1 Sakit (Citra #4), 1 Alpha (Fajar #7)
        // WA Status: 29 Valid + 3 Bermasalah (Daffa #5 Berhenti, Eka #6 Belum dicek, Gita #8 Tidak di WA)
        // Bill Status: 24 Lunas + 6 Belum (Citra #4, Daffa #5, + 4 students) + 2 Tunggakan (Eka #6 + Ikhsan #32)
        val generated = remainingNames.mapIndexed { idx, (name, gender) ->
            val absen = 12 + idx
            val uname = name.lowercase().replace(" ", ".")
            val att = if (name == "Nada Savitri") AttendanceStatus.IZIN.name else AttendanceStatus.HADIR.name
            val billSt = when (absen) {
                12, 13, 14, 15 -> BillPaymentStatus.BELUM.name // + Citra(4) & Daffa(5) = 6 Belum
                32 -> BillPaymentStatus.TUNGGAKAN.name // + Eka(6) = 2 Tunggakan
                else -> BillPaymentStatus.LUNAS.name // 24 Lunas total
            }
            val deliv = if (absen in listOf(12, 13)) "Menunggu" else "Terkirim"
            UserEntity(
                id = absen,
                absenNo = absen,
                fullName = name,
                username = uname,
                email = "$uname@spensari.sch.id",
                gender = gender,
                role = UserRole.SISWA.name,
                subRoleTitle = "Siswa",
                phoneWa = "+62 812-5500-${1000 + absen}",
                waStatus = WaNumberStatus.VALID.name,
                attendanceToday = att,
                presentDaysCount = 28,
                totalEffectiveDays = 29,
                billStatus = billSt,
                billAmount = if (billSt == BillPaymentStatus.TUNGGAKAN.name) 12000 else 6000,
                paidDate = if (billSt == BillPaymentStatus.LUNAS.name) "4 Nov" else "",
                paidMethod = if (billSt == BillPaymentStatus.LUNAS.name) "Tunai" else "",
                deliveryStatus = deliv
            )
        }

        return explicitUsers + generated
    }

    fun initialTransactions(): List<CashTransactionEntity> = listOf(
        CashTransactionEntity(
            id = 1,
            title = "Hana Safitri",
            amount = 6000,
            isIncome = true,
            isFromBill = true,
            dateTimeLabel = "Rab, 4 Nov • 10.05",
            studentName = "Hana Safitri",
            paymentMethod = "Tunai",
            confirmedBy = "Aisyah Putri",
            proofNote = "Lunas tunai ke bendahara"
        ),
        CashTransactionEntity(
            id = 2,
            title = "Bima Sakti",
            amount = 6000,
            isIncome = true,
            isFromBill = true,
            dateTimeLabel = "Rab, 4 Nov • 09.12",
            studentName = "Bima Sakti",
            paymentMethod = "Transfer",
            confirmedBy = "Aisyah Putri",
            proofNote = "BUKTI TRANSFER Rp6.000 • 4 Nov 2026 Berhasil"
        ),
        CashTransactionEntity(
            id = 3,
            title = "Gita Nuraini",
            amount = 6000,
            isIncome = true,
            isFromBill = true,
            dateTimeLabel = "Rab, 4 Nov • 08.40",
            studentName = "Gita Nuraini",
            paymentMethod = "QRIS",
            confirmedBy = "Aisyah Putri",
            proofNote = "QRIS Terkonfirmasi"
        ),
        CashTransactionEntity(
            id = 4,
            title = "Pembelian Spidol",
            amount = 25000,
            isIncome = false,
            isFromBill = false,
            dateTimeLabel = "Sel, 3 Nov • 14.10",
            paymentMethod = "Tunai",
            confirmedBy = "Aisyah Putri",
            proofNote = "Nota fotokopi & ATK koperasi sekolah"
        ),
        CashTransactionEntity(
            id = 5,
            title = "Pembelian Kertas",
            amount = 15000,
            isIncome = false,
            isFromBill = false,
            dateTimeLabel = "Sen, 2 Nov • 15.20",
            paymentMethod = "Tunai",
            confirmedBy = "Aisyah Putri",
            proofNote = "Struk kertas HVS A4 untuk kelas"
        )
    )

    fun initialSchedules(): List<ScheduleEntity> = listOf(
        // Rabu (Storyboard 04 Active Day)
        ScheduleEntity(1, "Rab", "07.00", "07.45", "Matematika", "Bu Sari Dewi", "R. 12", "Bawa buku catatan dan kalkulator."),
        ScheduleEntity(2, "Rab", "07.45", "08.30", "Bahasa Indonesia", "Pak Budi Santoso", "R. 12", "Bawa buku paket halaman 80."),
        ScheduleEntity(3, "Rab", "08.30", "09.15", "IPA", "Bu Rini Handayani", "R. 12", "Persiapan laporan praktikum fotosintesis."),
        ScheduleEntity(4, "Rab", "09.15", "09.30", "Istirahat", "", "", isBreak = true),
        ScheduleEntity(5, "Rab", "09.30", "10.15", "PPKN", "Pak Andi Pratama", "R. 12", "Diskusi kelompok bab 3."),
        ScheduleEntity(6, "Rab", "10.15", "11.00", "Seni Budaya", "Bu Mega Lestari", "R. 12", "Bawa buku gambar A3 & pensil 2B."),

        // Senin
        ScheduleEntity(7, "Sen", "07.00", "07.45", "Upacara Bendera", "Wali Kelas IX-H", "Lapangan", "Wajib atribut lengkap & topi Spensari."),
        ScheduleEntity(8, "Sen", "07.45", "09.15", "Matematika", "Bu Sari Dewi", "R. 12", "Bab Persamaan Linear."),
        ScheduleEntity(9, "Sen", "09.15", "09.30", "Istirahat", "", "", isBreak = true),
        ScheduleEntity(10, "Sen", "09.30", "11.00", "Bahasa Inggris", "Ms. Ratna Sari", "R. 12", "Bring English dictionary."),

        // Selasa
        ScheduleEntity(11, "Sel", "07.00", "08.30", "IPA Terpadu", "Bu Rini Handayani", "Lab IPA", "Praktikum biologi."),
        ScheduleEntity(12, "Sel", "08.30", "09.15", "IPS", "Pak Hendra Wijaya", "R. 12", "Peta Asia Tenggara."),
        ScheduleEntity(13, "Sel", "09.15", "09.30", "Istirahat", "", "", isBreak = true),
        ScheduleEntity(14, "Sel", "09.30", "11.00", "Bahasa Indonesia", "Pak Budi Santoso", "R. 12", "Pengumpulan tugas esai."),

        // Kamis
        ScheduleEntity(15, "Kam", "07.00", "08.30", "Matematika", "Bu Sari Dewi", "R. 12", "Pengumpulan PR Matematika Hal. 45-47."),
        ScheduleEntity(16, "Kam", "08.30", "09.15", "Bahasa Indonesia", "Pak Budi Santoso", "R. 12", "Membaca cepat."),
        ScheduleEntity(17, "Kam", "09.15", "09.30", "Istirahat", "", "", isBreak = true),
        ScheduleEntity(18, "Kam", "09.30", "11.00", "Informatika", "Pak Dimas Saputra", "Lab Komputer", "Praktik algoritma dasar."),

        // Jumat
        ScheduleEntity(19, "Jum", "07.00", "07.45", "Jumat Bersih & Rohani", "Wali Kelas IX-H", "R. 12", "Jatuh tempo iuran kas mingguan Rp6.000."),
        ScheduleEntity(20, "Jum", "07.45", "09.15", "PJOK / Olahraga", "Pak Rio Pratama", "Lapangan", "Seragam olahraga Spensari."),
        ScheduleEntity(21, "Jum", "09.15", "09.30", "Istirahat", "", "", isBreak = true),
        ScheduleEntity(22, "Jum", "09.30", "10.45", "PAI & Budi Pekerti", "Ust. Fauzi Rahman", "R. 12", "Bawa Al-Quran / buku catatan."),

        // Sabtu (6 Hari Sekolah Sesuai Storyboard 04)
        ScheduleEntity(23, "Sab", "07.00", "08.30", "IPA", "Bu Rini Handayani", "Lab IPA", "Deadline Laporan Praktikum IPA."),
        ScheduleEntity(24, "Sab", "08.30", "09.15", "Prakarya", "Bu Mega Lestari", "R. 12", "Kerajinan bahan daur ulang."),
        ScheduleEntity(25, "Sab", "09.15", "09.30", "Istirahat", "", "", isBreak = true),
        ScheduleEntity(26, "Sab", "09.30", "11.00", "Pramuka & Ekstrakurikuler", "Pembina Pramuka", "Aula Spensari", "Seragam Pramuka lengkap.")
    )

    fun initialAssignments(): List<AssignmentEntity> = listOf(
        AssignmentEntity(
            id = 1,
            title = "PR Matematika",
            subject = "Matematika",
            teacher = "Bu Sari Dewi",
            deadlineLabel = "Kamis, 5 Nov 2026",
            daysRemaining = 1,
            isCompleted = false,
            description = "Buat rangkuman materi persamaan linear satu variabel dan kerjakan 5 soal latihan. Halaman 45–47",
            attachmentName = "Materi_Persamaan.pdf",
            attachmentSize = "245 KB",
            reminderEnabled = true
        ),
        AssignmentEntity(
            id = 2,
            title = "Laporan Praktikum",
            subject = "IPA",
            teacher = "Bu Rini Handayani",
            deadlineLabel = "Sabtu, 7 Nov 2026",
            daysRemaining = 3,
            isCompleted = false,
            description = "Laporan hasil praktikum fotosintesis dilengkapi dengan kesimpulan.",
            attachmentName = "Format_Laporan_IPA.pdf",
            attachmentSize = "180 KB",
            reminderEnabled = true
        ),
        AssignmentEntity(
            id = 3,
            title = "Ringkasan Materi",
            subject = "PPKN",
            teacher = "Pak Andi Pratama",
            deadlineLabel = "Senin, 9 Nov 2026",
            daysRemaining = 5,
            isCompleted = false,
            description = "Ringkasan Bab 3 Kedaulatan Negara Republik Indonesia ditulis di buku catatan.",
            reminderEnabled = false
        ),
        AssignmentEntity(
            id = 4,
            title = "Tugas Esai",
            subject = "B. Indonesia",
            teacher = "Pak Budi Santoso",
            deadlineLabel = "Selasa, 3 Nov 2026",
            daysRemaining = 0,
            isCompleted = true,
            description = "Esai teks persuasif bertema kebersihan lingkungan sekolah minimal 3 paragraf.",
            reminderEnabled = false
        )
    )

    fun initialAnnouncements(): List<AnnouncementEntity> = listOf(
        AnnouncementEntity(
            id = 1,
            title = "Rapat kelas IX-H besok pagi!",
            content = "Assalamualaikum. Besok Kamis, 6 November pukul 06.45 kita rapat persiapan class meeting di kelas IX-H.\n\nBawa catatan ide lomba dan hadir tepat waktu ya.",
            authorName = "Ahmad Ramadhan",
            authorRole = "Admin",
            timeLabel = "Hari ini, 07:10",
            category = "Penting",
            isPinned = true,
            attachmentName = "Agenda_Rapat.pdf",
            attachmentSize = "124 KB",
            readCount = 24
        ),
        AnnouncementEntity(
            id = 2,
            title = "Jadwal piket berubah minggu ini",
            content = "Piket Rabu ditukar dengan Kamis karena persiapan ruang rapat kelas. Cek menu Jadwal untuk detail.",
            authorName = "Aisyah Putri",
            authorRole = "Pengurus",
            timeLabel = "Kemarin",
            category = "Info",
            isPinned = false,
            readCount = 29
        ),
        AnnouncementEntity(
            id = 3,
            title = "Iuran kas dikumpulkan hari Jumat",
            content = "Kas mingguan Rp6.000 per siswa. Serahkan ke bendahara (Aisyah Putri) atau transfer lalu kirim bukti via WhatsApp bot.",
            authorName = "Ahmad Ramadhan",
            authorRole = "Admin",
            timeLabel = "5 Nov",
            category = "Info",
            isPinned = false,
            readCount = 31
        ),
        AnnouncementEntity(
            id = 4,
            title = "Pendaftaran lomba kebersihan kelas",
            content = "Daftarkan nama kelompok dekorasi sudut baca paling lambat Senin depan.",
            authorName = "Aisyah Putri",
            authorRole = "Pengurus",
            timeLabel = "3 Nov",
            category = "Kegiatan",
            isPinned = false,
            readCount = 27
        )
    )

    fun initialNotifications(): List<NotificationEntity> = listOf(
        NotificationEntity(
            id = 1,
            title = "Tugas PR Matematika besok",
            subtitle = "Deadline Kamis, 6 Nov pukul 07.00",
            timeLabel = "20.00",
            dayGroup = "HARI INI",
            category = "Tugas",
            isUnread = true,
            targetRoute = "tugas"
        ),
        NotificationEntity(
            id = 2,
            title = "Jadwal Rabu diperbarui",
            subtitle = "Jam ke-3 IPA diganti Bahasa Inggris",
            timeLabel = "10.15",
            dayGroup = "HARI INI",
            category = "Jadwal",
            isUnread = true,
            targetRoute = "jadwal"
        ),
        NotificationEntity(
            id = 3,
            title = "Pengumuman baru",
            subtitle = "Rapat kelas IX-H besok pagi",
            timeLabel = "07.10",
            dayGroup = "HARI INI",
            category = "Pengumuman",
            isUnread = true,
            targetRoute = "pengumuman"
        ),
        NotificationEntity(
            id = 4,
            title = "Kas Rp6.000 tercatat",
            subtitle = "Pemasukan kas harian oleh Admin",
            timeLabel = "Selasa 16.02",
            dayGroup = "KEMARIN",
            category = "Kas",
            isUnread = false,
            targetRoute = "kas"
        ),
        NotificationEntity(
            id = 5,
            title = "Absensi hari ini tersimpan",
            subtitle = "28 hadir • 2 izin • 1 sakit • 1 alpha",
            timeLabel = "Selasa 07.05",
            dayGroup = "KEMARIN",
            category = "Absensi",
            isUnread = false,
            targetRoute = "absensi"
        )
    )

    fun initialActivityLogs(): List<ActivityLogEntity> = listOf(
        ActivityLogEntity(
            id = 1,
            category = "Lunas",
            actorName = "Aisyah Putri",
            actorRole = "Pengurus (Bendahara)",
            actionTitle = "Menandai lunas: Hana Safitri",
            detailText = "Kas Minggu ke-1 Nov • Rp6.000 • Tunai",
            targetUser = "Hana Safitri",
            beforeValue = "Belum bayar",
            afterValue = "Lunas (Tunai)",
            reason = "Setoran tunai langsung di kelas",
            dayHeader = "RABU, 4 NOVEMBER 2026",
            timeWib = "10.05"
        ),
        ActivityLogEntity(
            id = 2,
            category = "Lunas",
            actorName = "Aisyah Putri",
            actorRole = "Pengurus (Bendahara)",
            actionTitle = "Menandai lunas: Bima Sakti",
            detailText = "Kas Minggu ke-1 Nov • Rp6.000 • Transfer",
            targetUser = "Bima Sakti",
            beforeValue = "Belum bayar",
            afterValue = "Lunas (Transfer)",
            reason = "Bukti transfer terverifikasi via WhatsApp",
            dayHeader = "RABU, 4 NOVEMBER 2026",
            timeWib = "09.12"
        ),
        ActivityLogEntity(
            id = 3,
            category = "Lunas",
            actorName = "Aisyah Putri",
            actorRole = "Pengurus (Bendahara)",
            actionTitle = "Menandai lunas: Gita Nuraini",
            detailText = "Kas Minggu ke-1 Nov • Rp6.000 • QRIS",
            targetUser = "Gita Nuraini",
            beforeValue = "Belum bayar",
            afterValue = "Lunas (QRIS)",
            reason = "Bukti pembayaran QRIS terkonfirmasi",
            dayHeader = "RABU, 4 NOVEMBER 2026",
            timeWib = "08.40"
        ),
        ActivityLogEntity(
            id = 4,
            category = "Absensi",
            actorName = "Ahmad Ramadhan",
            actorRole = "Admin",
            actionTitle = "Menyimpan absensi IX-H",
            detailText = "Hadir 28 • Izin 2 • Sakit 1 • Alpha 1",
            targetUser = "Kelas IX-H (32 Siswa)",
            beforeValue = "Belum tercatat",
            afterValue = "28 H / 2 I / 1 S / 1 A",
            reason = "Absensi pagi jam pertama",
            dayHeader = "RABU, 4 NOVEMBER 2026",
            timeWib = "07.58"
        ),
        ActivityLogEntity(
            id = 5,
            category = "Role",
            actorName = "Ahmad Ramadhan",
            actorRole = "Admin",
            actionTitle = "Mengubah role: Aisyah Putri",
            detailText = "Siswa → Pengurus (Bendahara)",
            targetUser = "Aisyah Putri",
            beforeValue = "Siswa",
            afterValue = "Pengurus (Bendahara)",
            reason = "Ditunjuk sebagai bendahara kelas IX-H",
            dayHeader = "SELASA, 3 NOVEMBER 2026",
            timeWib = "16.20"
        )
    )

    fun initialBotTemplates(): List<BotTemplateEntity> = listOf(
        BotTemplateEntity(
            id = "tagihan",
            name = "Tagihan",
            subtitle = "Pesan tagihan kas per siswa",
            bodyText = "Halo {nama}, ini tagihan kas IX-H:\nKas Minggu ke-1 November\nNominal: {nominal}\nJatuh tempo: {jatuh_tempo}\nBayar ke bendahara, lalu balas LUNAS + foto bukti.\nBalas STOP untuk berhenti menerima pesan.",
            isModified = true,
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

    fun initialBotDispatchLogs(): List<BotDispatchLogEntity> = listOf(
        BotDispatchLogEntity(
            id = 1,
            title = "Kas Minggu ke-1 Nov",
            category = "Tagihan",
            dateTimeLabel = "4 Nov • 08.10 • 26/29 terkirim",
            sentCount = 26,
            totalTarget = 29,
            failedCount = 3,
            statusLabel = "Sebagian gagal",
            failureReason = "3 gagal: koneksi terputus"
        ),
        BotDispatchLogEntity(
            id = 2,
            title = "Pengingat Kas Okt",
            category = "Tagihan",
            dateTimeLabel = "3 Nov • 19.00 • 0/8 terkirim",
            sentCount = 0,
            totalTarget = 8,
            failedCount = 8,
            statusLabel = "Gagal",
            failureReason = "Bot terputus saat pengiriman"
        ),
        BotDispatchLogEntity(
            id = 3,
            title = "Kas Minggu ke-4 Okt",
            category = "Tagihan",
            dateTimeLabel = "28 Okt • 08.05 • 29/29 terkirim",
            sentCount = 29,
            totalTarget = 29,
            failedCount = 0,
            statusLabel = "Terkirim"
        ),
        BotDispatchLogEntity(
            id = 4,
            title = "Kas Minggu ke-3 Okt",
            category = "Tagihan",
            dateTimeLabel = "21 Okt • 08.00 • 29/29 terkirim",
            sentCount = 29,
            totalTarget = 29,
            failedCount = 0,
            statusLabel = "Terkirim"
        )
    )
}
