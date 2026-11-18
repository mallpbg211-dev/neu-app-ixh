# MASTER PROMPT --- IX-H HUB 1.0

## Full UI Redesign, Database Reset, dan Persiapan Aplikasi Siap Digunakan

**Sekolah:** SMPN 1 Bojongsari (Spensari)\
**Kelas:** IX-H\
**Platform:** Android Native --- Kotlin, Jetpack Compose, Room/SQLite,
Retrofit, API VPS\
**Target perangkat:** Android kelas menengah ke bawah, termasuk RAM
sekitar 2 GB.

## 1. Peran dan tujuan

Bertindak sebagai senior Android engineer, UI/UX designer, database
architect, dan QA engineer. Kerjakan project yang sudah ada berdasarkan
source code aktual, bukan membuat project baru atau sekadar mockup.

Tujuan: 1. Redesign seluruh layar IX-H HUB 1.0. 2. Terapkan identitas
visual **Hybrid Premium Neubrutalism** secara konsisten. 3. Reset
database sepenuhnya agar aplikasi dimulai dari data kosong, lalu
bangun/rapikan skema sesuai kebutuhan fitur nyata. 4. Sediakan alur
pembuatan **Admin pertama melalui pengaturan awal**. 5. Pertahankan dan
matangkan fungsi aplikasi agar mendekati siap digunakan. 6. Uji build,
navigasi, database, autentikasi, hak akses, dan integrasi backend secara
faktual.

Jangan menyatakan aplikasi siap produksi jika integrasi atau pengujian
nyata belum dilakukan.

## 2. Aturan sebelum mengubah kode

-   Audit seluruh project: arsitektur, modul, dependensi, navigasi,
    database, API, autentikasi, role, dan fitur.
-   Jadikan source code project yang tersedia sebagai basis pengerjaan.
-   Pertahankan MVVM, Repository, Room, Retrofit, dan Flow jika memang
    digunakan dan masih layak.
-   Jangan menghapus fitur lama hanya karena desainnya berubah.
-   Jangan mengarang endpoint, kredensial, respons API, atau fungsi
    backend.
-   Jangan menaruh password, token, API secret, atau kredensial VPS di
    source code.
-   **Backup database lama sebelum reset**, catat lokasi dan cara
    restore.
-   Reset hanya database target yang telah diidentifikasi. Jangan
    menghapus database VPS/produksi, konfigurasi server, file project,
    atau data eksternal tanpa audit, backup, dan persetujuan eksplisit.
-   Jika perubahan berisiko menghilangkan data eksternal atau mengubah
    kontrak backend, berhenti dan minta persetujuan.

## 3. Identitas visual final

Gunakan satu gaya: **Hybrid Premium Neubrutalism**. Jangan mencampurkan
gaya yang tidak sesuai.

### Palet warna

  Token               Hex
  ------------------- -----------
  Primary Yellow      `#FFC928`
  Secondary Yellow    `#FFD95A`
  Light Yellow        `#FFF0B8`
  Background Cream    `#FDF8EC`
  Surface             `#FFFFFF`
  Secondary Surface   `#F2ECE0`
  Charcoal            `#1C1B1F`
  Muted Border        `#E6DEC8`
  Success             `#35A66B`
  Warning             `#F2A93B`
  Error               `#E34B4B`
  Information         `#4A91D9`

### Tipografi, bentuk, dan komponen

-   Gunakan Poppins secara konsisten jika font tersedia.
-   Spacing berbasis kelipatan 4dp; padding layar sekitar 16dp.
-   Radius card sekitar 16dp; list sekitar 12dp.
-   Border 1.5--2dp; shadow terukur, tidak berlebihan.
-   Target sentuh minimal 48dp.
-   Bangun komponen reusable: app bar, bottom navigation, tombol, text
    field, card, list item, chip, badge, dialog, bottom sheet, form
    validation, empty/loading/error/success state.

### Background dan dekorasi

-   Pertahankan gradasi kuning ke cream pada area hero dan layar
    relevan.
-   Pertahankan halftone dots dan organic wave sebagai identitas visual.
-   Jangan meratakan seluruh background menjadi putih polos.
-   Gunakan dekorasi secukupnya agar konten jelas dan performa tetap
    ringan.

### Icon system

Gunakan **Bold Rounded Dual-Tone**: - Outline charcoal. - Isian kuning
sekitar 30--40% dari area visual ikon. - Area lain cream atau warna
permukaan. - Stroke konsisten, geometri membulat, mudah dikenali. -
Jangan gunakan emoji sebagai ikon UI. - Hindari lingkaran kuning besar
dan shadow lebar di belakang ikon. - Tetap terbaca pada ukuran 18--24dp.

## 4. Blob Mascot

Gunakan aset Blob yang telah disetujui jika benar-benar tersedia. Jangan
mengganti identitasnya sepihak.

Karakteristik: tubuh pear kuning-oranye glossy, dua daun hijau, kacamata
silver-white di dahi, pipi merah muda, tangan 3D membulat, outline
charcoal, highlight dan shading.

Ekspresi: `IDLE`, `SMILE`, `SAD`, `BLINK`, `FOCUS`, `SLEEP`,
`PASSWORD_SHOW`, `PASSWORD_HIDE`, `THINKING`, `EXCITED`.

-   Password tersembunyi: mata Blob terbuka.
-   Password terlihat: mata tertutup/tertutupi sesuai aset yang
    disetujui.
-   Animasi harus state-driven, bukan loop dekoratif permanen.
-   Jeda animasi saat layar tidak aktif/background.
-   Optimalkan untuk RAM 2 GB.
-   Jika aset tidak ditemukan, laporkan; jangan mengaku aset sudah
    terpasang.

## 5. Redesign seluruh layar

Audit daftar route aktual, lalu redesign seluruh layar yang memang ada.
Cakupan minimum:

### Autentikasi dan onboarding

-   Login, validasi username/password, show/hide password.
-   Loading, error, success state.
-   Respons Blob.
-   First-run Admin setup.
-   Tidak ada akun demo, password default, atau kredensial hardcoded.

### Beranda dan navigasi

-   Dashboard, ringkasan kelas, navigasi, profil, notifikasi,
    onboarding/tour bila tersedia.
-   Pertahankan tab utama yang sudah ada: **Beranda, Absensi, Kas,
    Jadwal, Tugas**, jika sesuai struktur project.
-   Jangan menghilangkan route atau akses fitur lain.

### Absensi

-   Ringkasan, daftar/riwayat, status, form/tindakan sesuai aturan
    aplikasi, empty state.

### Kas dan tagihan

-   Ringkasan, pemasukan/pengeluaran, riwayat, tagihan/pembayaran jika
    tersedia, form dan validasi, empty state.

### Jadwal dan tugas

-   Jadwal, daftar tugas, detail, status, tenggat, empty state.

### Pengumuman, notifikasi, aktivitas

-   Daftar/detail pengumuman, notifikasi, riwayat aktivitas jika
    tersedia, empty state.

### Admin dan Pengurus

-   Dashboard berdasarkan role.
-   Pengelolaan siswa/pengguna, jadwal, tugas, pengumuman, kas, absensi
    sesuai kewenangan.
-   Pengaturan kelas, konfigurasi, log aktivitas jika tersedia.
-   Konfirmasi tindakan penting/destructive.

### Bot dan integrasi

-   Status bot dan template jika tersedia.
-   Jangan mengklaim WhatsApp bot/VPS aktif jika belum terhubung dan
    diuji.
-   Fitur belum terhubung harus ditandai jujur, bukan disimulasikan
    seolah aktif.

Semua layar harus konsisten dalam warna, tipografi, spacing, ikon,
komponen, hierarki informasi, dan interaksi.

## 6. Reset database dan data kosong

**Keputusan pengguna: database direset sepenuhnya.**

Prosedur wajib: 1. Identifikasi database lokal Room/SQLite dan
penyimpanan terkait yang termasuk project. 2. Backup database lama
sebelum reset; dokumentasikan lokasi dan langkah restore. 3. Audit
relasi, foreign key, indeks, migration, DAO, Repository, dan pemanggil
database. 4. Tetapkan skema akhir sesuai kebutuhan fitur aktual. 5.
Kosongkan seluruh data operasional lama pada database target. 6. Bangun
ulang database dengan versioning/migration yang benar. 7. Jangan seed
data siswa, transaksi, absensi, jadwal, tugas, pengumuman, notifikasi,
atau data demo. 8. Jangan membuat dummy data untuk mempercantik
dashboard. 9. Jangan menghapus konfigurasi koneksi backend yang masih
dibutuhkan. 10. **Reset database lokal tidak berarti reset database
VPS/produksi.** Jangan menghapus data server tanpa persetujuan khusus.

Semua halaman tetap harus tampak selesai saat kosong. Contoh: - Absensi:
"Belum ada data absensi." - Kas: "Belum ada transaksi kas." - Jadwal:
"Belum ada jadwal yang ditambahkan." - Tugas: "Belum ada tugas." -
Pengumuman: "Belum ada pengumuman." - Notifikasi: "Belum ada
notifikasi."

Tampilkan nilai ringkasan yang benar-benar berasal dari data kosong
(misalnya 0), bukan angka atau aktivitas palsu. Tombol tambah hanya
muncul untuk role yang berwenang.

## 7. Admin pertama

**Keputusan pengguna: Admin pertama dibuat sendiri melalui pengaturan
awal.**

Alur: 1. Aplikasi memeriksa apakah Admin awal sudah dibuat. 2. Jika
belum, tampilkan halaman setup Admin pertama. 3. Pengguna menentukan
username dan password sendiri. 4. Validasi input dan kekuatan password.
5. Jika autentikasi menggunakan backend, password harus di-hash dengan
aman di sisi server; jangan simpan plaintext. 6. Jika tahap awal
benar-benar offline, gunakan penyimpanan kredensial aman dan
dokumentasikan keterbatasannya. 7. Setelah berhasil, tandai setup
selesai dan cegah pembuatan Admin pertama berulang melalui alur publik.
8. Jangan sediakan kredensial contoh/default/hardcoded. 9. Pastikan
pengguna tanpa otorisasi tidak dapat membuka halaman Admin. 10. Jika
endpoint setup Admin belum tersedia di VPS, jangan mengarang endpoint.
Audit API dan laporkan kebutuhan backend secara spesifik.

## 8. Autentikasi, role, dan keamanan

Role yang digunakan: **Admin, Pengurus, Siswa**.

-   Terapkan role-based access control di UI dan backend.
-   Validasi izin pada setiap operasi penting; menyembunyikan tombol
    saja tidak cukup.
-   Tangani token/session, logout, dan session expired dengan aman.
-   Validasi input dan tangani error tanpa membocorkan data sensitif.
-   Jangan menulis password/token/secret ke log.
-   Konfirmasi tindakan penghapusan atau perubahan penting.
-   Verifikasi implementasi izin aktual, jangan hanya mengandalkan label
    role.

## 9. Database dan kebutuhan fitur

Audit skema yang sudah ada sebelum menambah tabel. Entitas yang pernah
teridentifikasi sebagai titik awal audit: - User - Cash transaction -
Schedule - Assignment - Announcement - Notification - Activity log - Bot
template - Bot dispatch log

Daftar tersebut bukan perintah untuk menyalin skema tanpa pemeriksaan.

-   Gunakan tabel yang benar-benar diperlukan.
-   Tambahkan tabel hanya jika kebutuhan data dan alur bisnis jelas.
-   Hindari duplikasi entitas/kolom.
-   Tetapkan primary key, foreign key, indeks, constraint, dan timestamp
    secara tepat.
-   Cegah orphan records.
-   Jaga konsistensi Room DAO, Repository, ViewModel, API DTO, dan UI.
-   Dokumentasikan perubahan skema dan alasannya.
-   Jangan menghapus data server karena database lokal dikosongkan.

## 10. Backend, VPS, dan sinkronisasi

Audit Retrofit/API VPS: - Base URL, environment, endpoint aktual, DTO
request/response. - Authentication/authorization, error response,
timeout/retry. - Offline/online behavior, sinkronisasi, konflik data
bila berlaku. - Status server dan bot harus mencerminkan kondisi
sebenarnya.

Jangan membuat endpoint palsu. Jika endpoint belum tersedia, buat daftar
kebutuhan backend dan tandai fitur belum terintegrasi. Jangan menyimpan
secret VPS di source code aplikasi.

## 11. Performa

Target termasuk Android 10 dan RAM sekitar 2 GB: - Gunakan
LazyColumn/LazyRow untuk daftar panjang dan stable keys. - Hindari
animasi loop permanen, blur, efek grafis berat, dan shadow berlapis. -
Hindari gambar resolusi besar yang tidak perlu. - Kelola state Compose
secara efisien. - Pastikan scrolling dan interaksi tetap responsif. -
Perhatikan kontras, ukuran teks, dan target sentuh minimal 48dp.

## 12. Tahapan implementasi

### Tahap 0 --- Audit dan backup

Audit project, daftar layar/route/entity/DAO/API/role, identifikasi
risiko, backup database, lalu laporkan rencana reset.

### Tahap 1 --- Design system

Finalisasi theme tokens, Poppins, warna, spacing, shape, border,
elevation, ikon, komponen reusable, dan preview.

### Tahap 2 --- Blob

Verifikasi sepuluh aset ekspresi, integrasikan state-driven, uji
tampilan dan performa.

### Tahap 3 --- Login dan first-run Admin

Redesign login, integrasikan autentikasi aktual, buat setup Admin
pertama, uji validasi dan keamanan.

### Tahap 4 --- Redesign seluruh layar

Beranda/navigasi, absensi, kas/tagihan, jadwal/tugas,
profil/notifikasi/pengumuman, Admin/Pengurus/Bot, termasuk detail,
dialog, form, dan empty state.

### Tahap 5 --- Database dan integrasi

Terapkan skema final, reset database target setelah backup, pastikan
data kosong, integrasikan Room/Repository/ViewModel/API/UI, uji CRUD dan
role.

### Tahap 6 --- QA

Build debug, unit test, UI/instrumentation test bila tersedia, navigasi
seluruh route, first-run Admin, login/logout, role, empty state, CRUD,
offline/online, sinkronisasi, dan performa perangkat sasaran bila
memungkinkan.

## 13. Kriteria penerimaan

Pekerjaan selesai jika: - Seluruh layar mengikuti sistem visual final. -
Tidak ada layar penting tertinggal tanpa alasan. - Navigasi dan akses
fitur tetap berfungsi. - Tidak ada data dummy. - Database target kosong
setelah reset, kecuali data sistem minimum yang memang diperlukan. -
Backup dan prosedur restore tersedia. - First-run Admin aman dan dapat
digunakan. - Tidak ada kredensial bawaan/hardcoded. - Role
Admin/Pengurus/Siswa diuji. - Semua form memiliki validasi dan
feedback. - Empty state tersedia untuk daftar utama. - Tidak ada
endpoint/fungsi backend fiktif. - Hasil build dan test dilaporkan
berdasarkan hasil aktual.

## 14. Laporan akhir wajib

Laporkan: 1. Ringkasan perubahan UI dan daftar layar yang diperbarui. 2.
File yang dibuat, diubah, dan dihapus. 3. Perubahan database: tabel,
relasi, indeks, versi, migration. 4. Status backup/reset dan cara
restore. 5. Cara menjalankan setup Admin pertama. 6. Fitur yang
benar-benar berfungsi. 7. Fitur yang baru selesai secara UI tetapi belum
terintegrasi. 8. Kebutuhan endpoint, konfigurasi VPS, kredensial, atau
layanan eksternal. 9. Hasil build/test dan error yang ditemukan. 10.
Screenshot layar utama dan layar penting. 11. Masalah yang belum selesai
dan langkah berikutnya.

Kelompokkan status sebagai: - **Selesai dan sudah diuji** - **Selesai
secara UI tetapi belum terintegrasi** - **Belum selesai** -
**Membutuhkan konfigurasi/tindakan pengguna**

Jangan menyembunyikan kegagalan. Jangan mengklaim uji perangkat nyata
jika hanya compile atau emulator.

## 15. Referensi desain

Gunakan tiga file referensi statis jika tersedia: -
`referensi_desain_system.png` - `referensi_desain_blob.png` -
`referensi_login.png`

Jangan mengasumsikan ada GIF/video. Jika gambar referensi tidak
ditemukan, gunakan keputusan tertulis dalam dokumen ini dan laporkan
bahwa file tidak tersedia. Jangan mengarang isi gambar.

## Instruksi penutup

Mulai dari audit project aktual. Jangan langsung menulis ulang semua
file sebelum memahami implementasi yang ada. Kerjakan menyeluruh tetapi
terkontrol. Prioritaskan konsistensi visual, fungsi nyata, keamanan, dan
kestabilan pada perangkat rendah.

**Target akhir:** IX-H HUB 1.0 dengan UI terpadu, database baru dalam
keadaan kosong, setup Admin pertama yang aman, dan fitur yang telah
diverifikasi sejauh lingkungan project memungkinkan.
