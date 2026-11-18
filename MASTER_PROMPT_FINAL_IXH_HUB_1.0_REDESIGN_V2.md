# MASTER PROMPT FINAL --- IX-H HUB 1.0

## Full UI/UX Redesign, Creative Design Autonomy, Organic Shape Language, Database Reset & Readiness

**Produk:** IX-H HUB 1.0\
**Sekolah:** SMPN 1 Bojongsari (Spensari) · **Kelas:** IX-H\
**Platform:** Android Native · Kotlin · Jetpack Compose · Room/SQLite ·
Retrofit/API VPS (verifikasi aktual)\
**Target:** Android 10, perangkat RAM sekitar 2 GB · Bahasa UI:
Indonesia

## 1. Misi

Bertindak sebagai Lead Product Designer, Senior UI/UX Designer, Android
Engineer, Database Architect, Security Engineer, dan QA Engineer.
Kerjakan project yang sudah ada, bukan membuat project baru atau sekadar
mockup.

Lakukan redesign menyeluruh seluruh pengalaman visual dan interaksi
aplikasi. Tujuan: 1. Seluruh layar memiliki desain matang, profesional,
konsisten, dan sesuai fungsi. 2. Arah visual tetap **Hybrid Premium
Neubrutalism**, tetapi tidak terkunci pada Design System lama. 3.
Kembangkan organic curves, greeting card, wave, dan shape language khas
referensi. 4. Utamakan UX, keterbacaan, aksesibilitas, dan logika
penggunaan daripada dekorasi. 5. Reset database aplikasi target setelah
backup; siapkan skema sesuai kebutuhan aktual. 6. Admin pertama dibuat
sendiri melalui First Admin Setup yang aman. 7. Matangkan fungsi,
integrasi, keamanan, dan performa agar mendekati siap digunakan. 8.
Laporkan hasil berdasarkan implementasi dan pengujian nyata.

Jangan menyatakan production-ready jika masih ada integrasi atau
pengujian penting yang belum selesai.

## 2. Keputusan final pengguna

Keputusan berikut sudah final dan jangan ditanyakan kembali: - Database
aplikasi target direset sepenuhnya setelah backup. - Semua data
operasional dimulai kosong; tidak boleh ada dummy data. - Admin pertama
dibuat sendiri melalui halaman pengaturan awal. - Username dan password
Admin ditentukan pengguna; tidak ada kredensial default/hardcoded. -
Role: Admin, Pengurus, Siswa. - Identitas visual: Hybrid Premium
Neubrutalism. - AI bebas mengevaluasi dan memperbaiki keputusan desain
lama jika tidak logis atau menghasilkan desain buruk. - Reset database
lokal **tidak** berarti reset database VPS/produksi. Database server
tidak boleh dihapus tanpa audit, backup, dan persetujuan khusus.

## 3. Design autonomy --- jangan terpaku pada Design System lama

Design System dan keputusan lama adalah referensi awal, bukan aturan
mutlak. Berpikirlah kritis seperti desainer profesional.

Evaluasi proporsi warna, tipografi, hierarki, bentuk card, tombol,
input, navigasi, radius, lengkungan, border, shadow, komposisi, dan
kesesuaian komponen dengan fungsi.

AI boleh mengubah, mengembangkan, atau mengganti warna, ukuran, radius,
bentuk, tipografi, shadow, layout, dan komponen lama jika ada solusi
lebih baik. Namun: - Arah Hybrid Premium Neubrutalism tetap menjadi
landasan. - Jangan mengganti identitas Blob secara sepihak. - Jangan
menghilangkan organic curves dan greeting card referensi tanpa alasan UX
kuat. - Jangan mengorbankan fungsi, keterbacaan, atau aksesibilitas demi
estetika.

**Kualitas desain akhir lebih penting daripada mempertahankan keputusan
lama yang ternyata kurang tepat.**

## 4. Identitas visual dan shape language

Gunakan Hybrid Premium Neubrutalism: playful tetapi dewasa, ekspresif
tetapi terstruktur, berkarakter tetapi mudah digunakan.

### Organic curves --- elemen identitas penting

Jangan menyederhanakan semua komponen menjadi rounded rectangle standar.
Kembangkan: - Greeting card dengan lengkungan organik seperti referensi
sebelumnya. - Kurva asimetris yang terkontrol. - Organic wave pada
background. - Perpaduan bentuk geometris membulat dan bentuk organik. -
Overlap antar elemen jika harmonis. - Kurva pada hero card atau panel
yang sesuai.

Pelajari karakter kurva, proporsi, arah aliran, dan relasinya dengan
elemen lain. Jangan sekadar memberi radius besar. Greeting card
referensi adalah acuan utama shape language.

Jangan memaksakan bentuk organik pada semua komponen. Tabel, daftar
transaksi, form, dan informasi padat boleh memakai bentuk sederhana demi
keterbacaan.

### Neubrutalism character

-   Outline charcoal tegas dan konsisten.
-   Kontras warna terukur.
-   Shadow tactile yang tidak berlebihan.
-   Border, depth, dan sudut dirancang dengan sengaja.
-   Hindari tampilan template generik.

### Palet awal (boleh dievaluasi)

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

Atur ulang proporsi/shade jika dibutuhkan demi kontras dan keseimbangan.
Jangan gunakan kuning berlebihan.

### Tipografi, background, ikon

-   Poppins adalah referensi awal; evaluasi ukuran, weight, line-height,
    dan hierarki. Alternatif boleh dipertimbangkan jika lebih baik dan
    konsisten.
-   Pertahankan gradient kuning-cream, halftone dots, dan organic wave
    pada area yang tepat. Jangan membuat seluruh background putih polos
    atau terlalu ramai.
-   Bold Rounded Dual-Tone adalah referensi ikon: outline charcoal,
    aksen kuning, geometri membulat, stroke konsisten. Tidak boleh
    menggunakan emoji sebagai ikon UI. Perbaiki jika ikon lama tidak
    konsisten atau sulit dikenali.

## 5. Blob Mascot

Gunakan aset Blob yang disetujui jika tersedia. Jangan mengganti
identitasnya sepihak.

Karakter: tubuh pear-shaped kuning-oranye glossy, dua daun hijau,
kacamata silver-white di dahi, pipi merah muda, tangan 3D membulat,
outline charcoal, highlight dan shading.

Ekspresi: `IDLE`, `SMILE`, `SAD`, `BLINK`, `FOCUS`, `SLEEP`,
`PASSWORD_SHOW`, `PASSWORD_HIDE`, `THINKING`, `EXCITED`.

-   Password tersembunyi: mata Blob terbuka.
-   Password terlihat: mata tertutup/tertutupi sesuai konsep aset.
-   Respons Blob mengikuti state UI.
-   Hindari animasi dekoratif permanen; jeda saat layar tidak
    aktif/background.
-   Optimalkan untuk RAM 2 GB.
-   Jika aset tidak ditemukan atau tidak sesuai, laporkan dengan jujur.
-   Blob harus menyatu dengan komposisi hero, bukan sekadar gambar
    tempelan.

## 6. UX: setiap halaman punya kebutuhan sendiri

Jangan memakai layout sama untuk semua halaman. Buat satu keluarga
visual yang konsisten, tetapi komposisi dan prioritas informasi sesuai
fungsi.

-   **Beranda:** ekspresif, greeting card organik, Blob, ringkasan,
    akses fitur.
-   **Absensi:** status kehadiran, ringkasan, riwayat, tindakan jelas.
-   **Kas:** nominal, pemasukan, pengeluaran, transaksi.
-   **Jadwal:** urutan waktu, hari, agenda.
-   **Tugas:** tenggat, prioritas, status, detail.
-   **Pengumuman:** keterbacaan isi dan informasi penting.
-   **Profil:** identitas akun, role, pengaturan, keamanan.
-   **Admin:** pengelolaan data dan tindakan secara efisien.

Setiap layar harus menjawab: tujuan pengguna, informasi terpenting,
tindakan utama, konten yang perlu terlihat pertama, serta tampilan
ketika kosong/loading/error/success. Jangan membuat UI yang hanya bagus
di screenshot tetapi sulit digunakan.

## 7. Redesign seluruh layar

Audit route aktual terlebih dahulu. Cakupan minimal: - Autentikasi:
Login, First Admin Setup, validasi, show/hide password,
loading/error/success, session expired, logout, onboarding jika ada. -
Beranda dan navigasi: dashboard, ringkasan kelas, bottom navigation,
profil/avatar, notifikasi, akses sesuai role. Pertahankan tab Beranda,
Absensi, Kas, Jadwal, Tugas jika memang ada; jangan hilangkan route
lain. - Absensi: ringkasan, daftar/riwayat, status, form sesuai aturan
sistem, empty state. - Kas/tagihan: ringkasan, pemasukan/pengeluaran,
transaksi, pembayaran jika tersedia, form, validasi, empty state. -
Jadwal/tugas: jadwal, daftar/detail tugas, tenggat, status, prioritas,
empty state. - Pengumuman/notifikasi/aktivitas: daftar, detail, riwayat,
empty state. - Admin/Pengurus: dashboard per role, pengelolaan
pengguna/siswa, absensi, kas, jadwal, tugas, pengumuman, pengaturan
kelas, konfigurasi, log jika tersedia. - Bot/integrasi: status bot dan
template jika tersedia. Jangan mengklaim bot/VPS aktif jika belum diuji.

Semua halaman, detail, dialog, bottom sheet, form, loading, error,
success, dan empty state ikut didesain ulang. Jangan hanya memperbarui
dashboard.

## 8. Responsive design dan perilaku perangkat

Rancang untuk berbagai ukuran smartphone, terutama layar kecil. - Tidak
ada elemen terpotong/keluar layar. - Perhatikan status bar, navigation
bar, safe drawing insets, dan keyboard/IME. - Form tetap bisa di-scroll
ketika keyboard terbuka; tombol submit tidak tertutup. - Field fokus
terlihat jelas. - Layout tidak bergantung pada satu ukuran emulator. -
Perhatikan penggunaan satu tangan dan target sentuh minimal 48dp. - Uji
ukuran layar kecil/besar jika tersedia. - Jangan menilai desain hanya
dari screenshot; periksa interaksi nyata.

## 9. Bahasa dan konteks Indonesia

-   Gunakan Bahasa Indonesia natural, konsisten, ringkas, mudah dipahami
    siswa dan pengurus.
-   Format tanggal Indonesia.
-   Gunakan Rupiah (IDR) untuk fitur kas.
-   Nomor WhatsApp divalidasi dan dinormalisasi sesuai format yang
    diterima sistem (misalnya 08 atau +62).
-   Gunakan zona waktu sesuai konfigurasi sekolah/backend; jangan
    hardcode tanpa verifikasi.
-   Pesan validasi harus menjelaskan cara memperbaiki input.
-   Hindari istilah teknis di UI siswa jika ada istilah sederhana.

## 10. Database reset sepenuhnya

**Database aplikasi target harus direset sepenuhnya setelah backup.**

1.  Audit Room/SQLite dan penyimpanan terkait.
2.  Identifikasi database lokal, testing, dan eksternal secara terpisah.
3.  Backup database lama; catat lokasi dan langkah restore.
4.  Audit entity, relasi, foreign key, indeks, DAO, Repository,
    migration, dan pemanggil.
5.  Tetapkan skema akhir sesuai kebutuhan aktual.
6.  Kosongkan seluruh data operasional pada database target.
7.  Bangun ulang database dengan versioning/migration yang benar.
8.  Jangan seed siswa, transaksi, absensi, jadwal, tugas, pengumuman,
    notifikasi, atau data demo.
9.  Jangan hapus konfigurasi koneksi backend.
10. Jangan menghapus database VPS/produksi tanpa audit, backup, dan
    persetujuan eksplisit.

### Empty state

Setiap daftar utama memiliki empty state profesional, misalnya: - "Belum
ada data absensi." - "Belum ada transaksi kas." - "Belum ada jadwal yang
ditambahkan." - "Belum ada tugas." - "Belum ada pengumuman." - "Belum
ada notifikasi."

Angka ringkasan harus berasal dari data aktual (misalnya 0), bukan angka
palsu. Tombol tambah hanya untuk role yang berwenang.

## 11. First Admin Setup

1.  Periksa apakah Admin awal sudah dibuat.
2.  Jika belum, tampilkan halaman First Admin Setup.
3.  Minta data yang benar-benar diperlukan, misalnya nama lengkap,
    username, password, konfirmasi password, dan nomor WhatsApp hanya
    jika ada tujuan fitur yang jelas.
4.  Validasi semua field dan tampilkan indikator password yang jelas.
5.  Jika memakai backend, password di-hash aman di sisi server; jangan
    simpan plaintext.
6.  Jika benar-benar offline, gunakan penyimpanan kredensial aman dan
    dokumentasikan batasannya.
7.  Setelah berhasil, tandai setup selesai dan cegah pembuatan Admin
    pertama berulang melalui alur publik.
8.  Tidak boleh ada kredensial default/hardcoded.
9.  Pengguna tanpa otorisasi tidak boleh membuka fitur Admin.
10. Jika endpoint setup belum tersedia di VPS, jangan mengarang
    endpoint; laporkan kebutuhan backend spesifik.

## 12. Database architecture

Audit skema lama sebelum menambah tabel. Entitas yang pernah
teridentifikasi sebagai titik awal audit: User, Cash transaction,
Schedule, Assignment, Announcement, Notification, Activity log, Bot
template, Bot dispatch log. Ini bukan perintah menyalin skema tanpa
pemeriksaan.

-   Gunakan tabel yang benar-benar diperlukan.
-   Tambah tabel hanya jika kebutuhan dan alur bisnis jelas.
-   Hindari duplikasi.
-   Tetapkan primary key, foreign key, indeks, constraint, timestamp,
    dan aturan penghapusan.
-   Cegah orphan records.
-   Jaga konsistensi Room DAO, Repository, ViewModel, DTO, API, dan UI.
-   Dokumentasikan perubahan skema.
-   Jangan hapus data server karena database lokal dikosongkan.

## 13. Backend, VPS, offline, dan sinkronisasi

Audit Retrofit/API aktual: Base URL/environment, endpoint, DTO,
autentikasi/otorisasi, error, timeout/retry, offline/online,
sinkronisasi, konflik data, status server/bot.

Jangan membuat endpoint/respons palsu. Jika backend belum mendukung
fitur, tandai belum terintegrasi dan buat daftar kebutuhan. Jika
offline-first: - Tentukan sumber kebenaran data. - Dokumentasikan
strategi sinkronisasi. - Tangani konflik dan perubahan bersamaan. -
Tampilkan status sinkronisasi bila relevan. - Hindari kehilangan data
saat koneksi terputus.

Jangan simpan secret VPS/API key privat/kredensial server di source code
aplikasi.

## 14. Keamanan, privasi, dan role

Role: Admin, Pengurus, Siswa. - Terapkan otorisasi di backend dan UI;
menyembunyikan tombol saja tidak cukup. - Tangani token/session, logout,
dan session expired dengan aman. - Validasi input dan jangan tulis
password/token/secret ke log. - Lindungi data pribadi siswa. - Minta
izin Android hanya ketika fitur membutuhkannya dan jelaskan tujuannya. -
Jangan meminta akses kontak/lokasi/kamera/file/notifikasi tanpa
kebutuhan. - Konfirmasi tindakan destruktif. - Sediakan pemulihan akun
jika didukung. - Jangan mengklaim keamanan yang belum diuji.

## 15. Performa dan aksesibilitas

Target Android 10 dan RAM sekitar 2 GB: - LazyColumn/LazyRow untuk
daftar panjang dengan stable keys. - Hindari animasi loop permanen,
blur, efek berat, shadow berlapis. - Optimalkan gambar dan loading. -
Kelola state Compose dan recomposition secara efisien. -
Scrolling/interaksi responsif. - Kontras memadai, teks nyaman, dukung
font scaling. - Target sentuh minimal 48dp. - Jangan menyampaikan status
hanya dengan warna; sertakan label/ikon/teks.

## 16. Aturan implementasi

Sebelum coding: - Audit arsitektur, screen, route, entity, DAO,
repository, ViewModel, API, role, aset. - Catat fitur yang benar-benar
berfungsi dan yang masih simulasi. - Buat rencana berdasarkan
dependensi.

Saat coding: - Pertahankan arsitektur sehat. - Pisahkan design tokens,
reusable components, screens, dan business logic. - Jangan menaruh
business logic di composable. - Jangan menduplikasi komponen. - Jangan
mengubah kontrak API tanpa alasan dan dokumentasi. - Jangan menghapus
fitur lama tanpa audit. - Jangan menyebut fitur selesai hanya karena UI
dibuat. - Lakukan perubahan terkontrol dan mudah ditinjau.

## 17. Tahapan kerja

**Tahap 0 --- Audit dan backup:** audit project, daftar
layar/route/entity/API/role, periksa integrasi, identifikasi risiko,
backup database, laporkan rencana reset.

**Tahap 1 --- Design exploration:** evaluasi desain lama; eksplorasi
arah visual dan shape language; buat preview layar representatif
termasuk greeting card organik; pilih solusi terbaik dari sisi estetika
dan UX.

**Tahap 2 --- Design system hasil evaluasi:** buat token final yang
benar-benar dibutuhkan; jangan pertahankan token lama jika tidak tepat;
bangun komponen reusable dan dokumentasikan keputusan.

**Tahap 3 --- Blob dan aset:** verifikasi aset, integrasikan ekspresi
berbasis state, uji performa.

**Tahap 4 --- Login dan First Admin Setup:** redesign dan integrasikan
autentikasi aktual serta pembuatan Admin pertama.

**Tahap 5 --- Redesign semua layar:** Beranda, Absensi, Kas, Jadwal,
Tugas, Profil, Notifikasi, Pengumuman, Admin, Pengurus, Bot, detail,
dialog, form, loading, error, success, empty state.

**Tahap 6 --- Database reset dan integrasi:** setelah backup dan audit,
reset database target, terapkan skema final, pastikan data kosong,
integrasikan Room/Repository/ViewModel/API/UI, uji CRUD dan role.

**Tahap 7 --- QA dan readiness:** build, test, navigasi, responsivitas,
keamanan, performa, integrasi backend, release readiness.

Tahapan boleh dikerjakan dalam satu rangkaian, tetapi perubahan harus
terstruktur. Jangan berhenti hanya setelah Design System atau Login.

## 18. QA dan kriteria penerimaan

Periksa: - Semua route/layar diperbarui. - Tiap halaman sesuai kebutuhan
UX, bukan copy-paste layout. - Organic curves/greeting card diterapkan
tepat dan konsisten. - Tidak ada elemen terpotong di layar kecil. -
Keyboard tidak menutupi field/tombol. - Semua tombol/form berfungsi. -
Loading, empty, error, success, offline ditangani. - Tidak ada dummy
data. - Database target kosong setelah reset. - Backup dan restore
tersedia. - First Admin Setup aman. - Role Admin/Pengurus/Siswa diuji. -
CRUD diuji jika didukung. - Tidak ada endpoint palsu. - Build/test
dilaporkan berdasarkan hasil aktual.

Uji build debug, unit test, UI/instrumentation test jika tersedia,
seluruh navigasi, first-run Admin, login/logout/session, permission,
empty state, CRUD, offline/online, sinkronisasi, keyboard/IME, safe
insets, layar kecil, dan performa.

## 19. Referensi desain

Gunakan jika tersedia: - `referensi_desain_system.png` -
`referensi_desain_blob.png` - `referensi_login.png` - Referensi kartu
sapaan organik yang pernah diberikan.

Jangan mengasumsikan GIF/video tersedia. Jika referensi tidak ditemukan,
laporkan dan jangan mengarang isi gambar. Gunakan arahan tertulis ini.

## 20. Laporan akhir

Laporkan secara faktual: 1. Arah desain baru dan alasan keputusan. 2.
Semua layar yang didesain ulang. 3. Screenshot tiap kelompok layar. 4.
File dibuat/diubah/dihapus. 5. Design tokens dan komponen final. 6.
Perubahan database, relasi, indeks, versi, migration. 7. Status
backup/reset dan cara restore. 8. Cara menjalankan First Admin Setup. 9.
Fitur benar-benar berfungsi dan sudah diuji. 10. Fitur UI-only/belum
terintegrasi. 11. Endpoint, konfigurasi VPS, kredensial, layanan
eksternal yang dibutuhkan. 12. Hasil build/test dan error. 13. Masalah
tersisa. 14. Langkah konkret menuju penggunaan di sekolah.

Kelompokkan status: - **Selesai dan sudah diuji** - **Selesai secara UI
tetapi belum terintegrasi** - **Belum selesai** - **Membutuhkan
konfigurasi/tindakan pengguna**

Jangan mengklaim pengujian perangkat nyata jika hanya compile/emulator.
Jangan sembunyikan error.

## 21. Instruksi penutup

Mulai dari audit project aktual dan backup database. Jangan langsung
menulis ulang semua file sebelum memahami sistem.

Gunakan kreativitas dan penilaian profesional. Jangan terpaku pada
Design System lama jika kurang logis atau menghasilkan desain buruk.
Tetap pertahankan arah Hybrid Premium Neubrutalism dan kembangkan
organic curves serta greeting card sebagai bagian penting identitas
visual.

Prioritaskan kualitas UX, visual matang, konsistensi, keamanan, fungsi
nyata, aksesibilitas, dan performa.

**Target akhir:** IX-H HUB 1.0 dengan desain baru terpadu, database
aplikasi kosong setelah reset aman, First Admin Setup aman, serta fitur
yang telah diimplementasikan dan diverifikasi sejauh lingkungan project
memungkinkan.


---

# 22. Prioritas produk final — balanced

**Prioritas pengguna: BALANCED.**

Kualitas visual, UX, fungsi nyata, stabilitas, keamanan, dan performa memiliki bobot yang seimbang. Jangan mengorbankan fungsi demi screenshot yang cantik; jangan pula membuat UI sekadar teknis dan membingungkan.

Urutan keputusan:
1. Fungsi dan alur kerja harus benar.
2. Informasi harus jelas dan mudah digunakan.
3. Desain harus menarik, konsisten, dan memiliki karakter.
4. Performa dan keamanan harus sesuai kemampuan perangkat dan kebutuhan data sekolah.

Fitur tambahan yang diprioritaskan:
- Backup dan restore.
- Audit log aktivitas.
- Global search.
- Notification center.
- Offline-first dan sinkronisasi online/offline.

Fitur tersebut harus diaudit terhadap arsitektur yang sudah ada sebelum membuat tabel, endpoint, atau komponen baru.

## 22.1 Backup dan restore

Rancang mekanisme backup dan pemulihan yang sesuai dengan jenis penyimpanan yang benar-benar digunakan.

Ketentuan:
- Bedakan backup database lokal Room/SQLite dengan backup database VPS/server.
- Jangan menganggap backup lokal otomatis mencadangkan data server.
- Tentukan cakupan backup, lokasi penyimpanan, format, enkripsi, retensi, dan siapa yang berhak menjalankan restore.
- Jangan menyimpan backup yang berisi data pribadi siswa di lokasi publik atau tanpa perlindungan.
- Jika backup diekspor ke perangkat, jelaskan izin Android yang dibutuhkan dan gunakan mekanisme penyimpanan yang aman.
- Sediakan konfirmasi sebelum restore karena restore dapat menimpa data yang lebih baru.
- Verifikasi integritas file dan kompatibilitas versi skema sebelum restore.
- Buat prosedur pemulihan dan uji restore, bukan hanya uji pembuatan file backup.
- Catat waktu backup terakhir dan status hasilnya.
- Jangan membuat klaim backup otomatis jika scheduler, storage, atau backend belum tersedia.
- Jangan pernah menghapus backup lama sebelum backup baru terbukti valid.
- Untuk server, gunakan mekanisme backup server yang sah dan terotorisasi; jangan melakukan reset atau restore produksi tanpa persetujuan eksplisit.

## 22.2 Audit log aktivitas

Audit log harus membantu Admin mengetahui perubahan penting dan siapa yang melakukannya.

Catat seperlunya:
- Pelaku dan role.
- Jenis tindakan: create, update, delete, login/logout, atau tindakan administratif penting.
- Jenis objek/data yang berubah.
- Waktu kejadian dengan zona waktu yang konsisten.
- Hasil tindakan: berhasil/gagal jika relevan.
- Ringkasan perubahan yang tidak membocorkan data sensitif.

Ketentuan:
- Jangan mencatat password, token, secret, atau data pribadi berlebihan.
- Batasi akses audit log untuk role yang berwenang.
- Jangan menyediakan fitur edit/hapus log melalui UI biasa.
- Jika log berada di perangkat lokal, jelaskan keterbatasannya: log lokal dapat hilang ketika perangkat/database direset.
- Untuk jejak audit lintas pengguna/perangkat, pencatatan otoritatif perlu dilakukan di backend/server.
- Hindari duplikasi log akibat retry atau sinkronisasi; gunakan ID peristiwa yang stabil bila diperlukan.
- Tampilkan filter berdasarkan tanggal, pelaku, kategori, dan jenis tindakan jika data mendukung.

## 22.3 Global search

Sediakan pencarian terpadu hanya untuk kategori data yang memang tersedia dan boleh diakses oleh pengguna.

Cakupan yang dievaluasi:
- Siswa/pengguna.
- Tugas.
- Jadwal.
- Transaksi kas.
- Pengumuman.
- Absensi, jika pencarian tersebut relevan dan tidak membuka data yang tidak berhak dilihat.

Ketentuan:
- Hasil pencarian wajib mengikuti role dan permission yang sama dengan halaman sumber.
- Jangan pernah menampilkan data privat hanya karena ditemukan melalui search.
- Sediakan empty result, loading, error, dan clear query state.
- Gunakan debounce dan pencarian lokal/server sesuai arsitektur aktual.
- Untuk offline mode, tampilkan hasil dari data lokal yang tersedia dan jelaskan jika hasil mungkin belum terbaru.
- Jangan menambahkan search index atau tabel baru jika DAO/query yang ada sudah cukup.

## 22.4 Notification center

Satukan notifikasi yang benar-benar berasal dari peristiwa aplikasi.

Ketentuan:
- Bedakan notifikasi dalam aplikasi dengan push notification Android.
- Notification center harus memiliki status dibaca/belum dibaca, waktu, kategori, dan tujuan navigasi jika tersedia.
- Hormati role dan izin akses.
- Jangan membuat notifikasi contoh atau notifikasi palsu.
- Hindari notifikasi duplikat saat sinkronisasi atau retry.
- Tangani izin Android 13+ untuk notifikasi jika aplikasi mendukung versi tersebut; jangan meminta izin tanpa konteks.
- Jika push service/Firebase/backend belum tersedia, implementasikan hanya notifikasi lokal/in-app yang didukung dan laporkan keterbatasannya.
- Sediakan empty, loading, error, dan offline state.
- Jangan mengirim informasi sensitif melalui notifikasi pada lock screen.

## 22.5 Offline-first dan sinkronisasi

Pertahankan offline-first jika memang menjadi arsitektur project. Jangan mengubahnya menjadi online-only hanya untuk menyederhanakan implementasi.

Tetapkan dengan jelas:
- Data apa yang dapat dibaca saat offline.
- Operasi apa yang boleh dilakukan saat offline.
- Data mana yang menjadi sumber kebenaran.
- Bagaimana operasi lokal masuk ke antrean sinkronisasi.
- Bagaimana retry, timeout, dan koneksi pulih ditangani.
- Bagaimana konflik perubahan dari beberapa pengguna diselesaikan.
- Bagaimana pengguna mengetahui status pending, synced, atau failed.

Ketentuan teknis:
- Gunakan antrean operasi yang tahan terhadap aplikasi ditutup atau perangkat restart.
- Gunakan strategi idempotensi agar retry tidak membuat transaksi atau aktivitas ganda.
- Jangan menganggap request berhasil hanya karena disimpan secara lokal.
- Tampilkan status sinkronisasi secara jujur.
- Jangan menimpa perubahan yang lebih baru tanpa aturan konflik yang jelas.
- Tentukan kebijakan konflik per jenis data; misalnya transaksi kas dan absensi memerlukan perlakuan berbeda dari pengumuman.
- Tangani timestamp dan zona waktu secara konsisten.
- Hindari sinkronisasi agresif yang menguras baterai atau kuota.
- Jika backend belum mendukung sinkronisasi, dokumentasikan endpoint dan perubahan server yang dibutuhkan. Jangan mengarang endpoint.

## 22.6 Integrasi fitur dengan UI

Desain visual untuk kelima fitur tambahan harus mengikuti identitas visual hasil redesign, tetapi tidak boleh memaksakan dekorasi.

- Backup/restore: tampilkan status backup, tanggal terakhir, ukuran/cakupan bila tersedia, dan aksi restore dengan konfirmasi.
- Audit log: gunakan daftar yang mudah dipindai, filter, detail peristiwa, dan label yang jelas.
- Search: tampilkan kolom pencarian yang mudah dijangkau, hasil terkelompok bila berguna, dan empty result yang informatif.
- Notification center: bedakan status unread/read secara jelas tanpa mengandalkan warna saja.
- Offline/sync: tampilkan status koneksi dan antrean sinkronisasi secara ringkas, tidak mengganggu konten utama.

Jangan menambahkan semua fitur ini sebagai tab bottom navigation. Tempatkan sesuai hierarki informasi dan frekuensi penggunaan.

---

# 23. Prioritas implementasi dan acceptance criteria tambahan

Urutan implementasi yang disarankan:
1. Audit arsitektur dan data flow offline-first yang sudah ada.
2. Tetapkan model permission dan keamanan.
3. Rancang kontrak backup/restore dan audit log.
4. Rancang sinkronisasi serta aturan konflik.
5. Implementasikan global search sesuai hak akses.
6. Implementasikan notification center.
7. Integrasikan UI seluruh fitur ke design system final.
8. Uji skenario normal, offline, retry, konflik, restore, dan akses role.

Sebelum dinyatakan selesai, uji skenario berikut:
- Backup dibuat, diverifikasi, lalu berhasil direstore ke database uji.
- Restore ditolak jika file rusak atau versi skema tidak kompatibel.
- Audit log merekam tindakan penting tanpa menyimpan secret.
- Pengguna tidak dapat melihat hasil search di luar hak aksesnya.
- Notification center tidak menggandakan notifikasi setelah retry/sinkronisasi.
- Perubahan offline tetap tersimpan setelah aplikasi ditutup dan dibuka kembali.
- Operasi yang tertunda tersinkron saat koneksi pulih.
- Retry tidak membuat transaksi, absensi, atau log ganda.
- Konflik data mengikuti aturan yang didokumentasikan.
- Status pending/failed/synced terlihat jelas bagi pengguna.
- Seluruh fitur tetap nyaman digunakan pada perangkat RAM rendah.

---

# 24. Instruksi implementasi tambahan untuk AI

Prioritas produk final adalah **balanced** dengan lima fitur tambahan: backup/restore, audit log, global search, notification center, dan offline-first/sinkronisasi.

Jangan langsung menambahkan seluruh fitur tanpa audit. Periksa implementasi yang sudah ada, gunakan kembali komponen dan tabel yang masih tepat, lalu tambahkan hanya bagian yang benar-benar dibutuhkan.

Jangan menganggap fitur siap hanya karena halaman UI sudah tersedia. Untuk setiap fitur, laporkan:
- UI selesai atau belum.
- Data layer selesai atau belum.
- Backend/API selesai atau belum.
- Keamanan dan permission diuji atau belum.
- Skenario offline diuji atau belum.
- Build/test yang benar-benar dijalankan.

Jika ada kebutuhan backend, layanan push, storage backup, atau konfigurasi server yang belum tersedia, jelaskan dengan rinci dan jangan menggantinya dengan simulasi yang seolah-olah nyata.


---

# 25. Ketentuan tegas tentang storyboard dan Design System lama

**Storyboard dan Design System sebelumnya BUKAN template, batasan desain, atau cetak biru layout yang wajib ditiru.**

Gunakan referensi lama hanya untuk memahami tujuan dan alur fitur, identitas visual, karakter IX-H HUB, bentuk khas yang ingin dipertahankan atau dikembangkan, serta kebutuhan fungsional yang telah disepakati.

Jangan menyalin layout lama secara langsung. Jangan mempertahankan susunan layar hanya karena pernah dibuat atau disetujui sebelumnya. Lakukan eksplorasi dan perancangan ulang secara mandiri dari perspektif Lead Product Designer dan UI/UX Designer profesional.

Jika desain baru yang lebih logis, menarik, konsisten, mudah digunakan, dan aksesibel mengharuskan perubahan besar terhadap storyboard atau Design System lama, lakukan perubahan tersebut.

AI memiliki kewenangan untuk menata ulang struktur dan komposisi setiap layar, mengembangkan atau mengganti komponen visual lama, mengevaluasi palet warna, tipografi, ikon, radius, bentuk, border, shadow, serta merancang ulang sistem layout dan komponen responsif.

Batas yang tetap berlaku:
- Hybrid Premium Neubrutalism tetap menjadi arah identitas visual utama, tetapi bukan resep visual kaku.
- Organic curves, greeting card, dan organic wave adalah inspirasi karakter visual yang perlu dipertahankan atau dikembangkan secara tepat, bukan dipaksakan pada semua komponen.
- Identitas Blob yang telah disetujui tetap dipertahankan kecuali ada alasan kuat dan perubahan tersebut dilaporkan untuk ditinjau.
- Semua fungsi, data, role, keamanan, dan integrasi yang valid harus tetap terjaga.
- Jangan mengubah kontrak API, menghapus data eksternal, atau mengubah perilaku bisnis penting tanpa audit dan persetujuan.

**Prioritaskan kualitas hasil akhir, bukan kesetiaan terhadap desain lama.** Targetnya adalah IX-H HUB 1.0 mendapatkan wajah baru dengan identitas visual yang lebih matang, UX yang logis, dan implementasi yang benar-benar berfungsi.
