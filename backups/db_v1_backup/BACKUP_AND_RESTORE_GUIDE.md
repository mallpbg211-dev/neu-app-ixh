# IX-H HUB 1.0 — Database v1 Backup & Restore Guide

## 1. Informasi Backup Database Lama (v1)

Sesuai spesifikasi `MASTER_PROMPT_IXH_HUB_1.0_FULL_UI_REDESIGN_DATABASE_RESET.md` (§5 & §6 — Backup & Reset Database Total), seluruh struktur skema database lokal Room versi 1 (`ixh_hub_spensari.db`) beserta arsip seed data awal telah dicadangkan sebelum dilakukan reset total ke Database v2 (`ixh_hub_spensari_v2_clean.db`).

### Lokasi File Backup
- `/backups/db_v1_backup/Entities_v1_backup.kt` — Definisi 9 tabel Room v1 (`users`, `cash_transactions`, `schedules`, `assignments`, `announcements`, `notifications`, `activity_logs`, `bot_templates`, `bot_dispatch_logs`).
- `/backups/db_v1_backup/IxHubDao_v1_backup.kt` — Query DAO v1.
- `/backups/db_v1_backup/IxHubDatabase_v1_backup.kt` — Konfigurasi RoomDatabase v1 (`ixh_hub_spensari.db`).
- `/backups/db_v1_backup/IxHubSeedData_v1_backup.kt` — Arsip lengkap data seed v1 (32 siswa kelas IX-H, 5 transaksi kas, 26 jadwal pelajaran Senin–Sabtu, 4 tugas, 4 pengumuman, 5 notifikasi, 5 log aktivitas, 6 template bot, 4 log pengiriman bot).

---

## 2. Perbedaan Skema Database v1 vs Database v2 (Clean Production Ready)

1. **Nama File Database**:
   - **v1**: `ixh_hub_spensari.db` (terisi 32 data siswa dummy & transaksi sampel saat startup).
   - **v2**: `ixh_hub_spensari_v2_clean.db` (100% kosong dari seluruh data operasional saat aplikasi pertama kali dijalankan).
2. **Keamanan Password (`passwordHash`)**:
   - **v1**: Tidak menyimpan hash password (mengandalkan demo switch role).
   - **v2**: Menambahkan kolom `passwordHash` (SHA-256 salted dengan username) dan `createdAt` pada tabel `users`, serta indeks unik pada `username`.
3. **Indeks & Constraint**:
   - **v2**: Menambahkan `@Index(value = ["username"], unique = true)` pada `users`, `@Index(value = ["dayCode"])` pada `schedules`, dan `@Index(value = ["isPinned"])` pada `announcements`.
4. **Inisialisasi Data Operasional**:
   - **v2**: Seluruh tabel operasional (`users`, `cash_transactions`, `schedules`, `assignments`, `announcements`, `notifications`, `activity_logs`, `bot_dispatch_logs`) dimulai dengan **0 baris**. Akun Admin pertama wajib dibuat sendiri oleh pengguna melalui layar **Setup Admin Pertama (`FIRST_RUN_ADMIN_SETUP`)**.

---

## 3. Cara Restore Database Lama (Jika Diperlukan)

Apabila di kemudian hari pengembang ingin memulihkan skema atau data arsip v1:
1. Salin kembali `/backups/db_v1_backup/Entities_v1_backup.kt` ke `/app/src/main/java/com/example/data/local/Entities.kt`.
2. Salin kembali `/backups/db_v1_backup/IxHubDao_v1_backup.kt` ke `/app/src/main/java/com/example/data/local/IxHubDao.kt`.
3. Salin kembali `/backups/db_v1_backup/IxHubSeedData_v1_backup.kt` ke `/app/src/main/java/com/example/data/repository/IxHubSeedData.kt`.
4. Pada `IxHubDatabase.kt`, arahkan kembali nama database ke `"ixh_hub_spensari.db"` atau panggil `IxHubSeedData` secara eksplisit melalui fungsi migrasi/restore.
