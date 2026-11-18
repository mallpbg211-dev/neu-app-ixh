# MASTER PROMPT V2 --- IX-H HUB 1.0

## Hybrid Premium Neubrutalism · Design System, Blob Mascot, Login

**Identitas aplikasi** - Nama: IX-H HUB 1.0 - Sekolah: SMPN 1 Bojongsari
(Spensari) - Kelas: IX-H - Platform: Android - Teknologi utama: Kotlin,
Jetpack Compose, Material 3 - Target perangkat: Android kelas rendah,
termasuk perangkat dengan RAM 2 GB

Dokumen ini adalah pedoman utama implementasi fondasi visual IX-H HUB
1.0. Gunakan dokumen ini bersama gambar referensi final yang
dilampirkan. Jangan mencampur referensi desain lama yang bertentangan
dengan keputusan di dokumen ini.

------------------------------------------------------------------------

# 1. ARAH VISUAL UTAMA

Gunakan satu identitas visual:

**HYBRID PREMIUM NEUBRUTALISM**

Perpaduan tampilan modern dan premium dengan karakter neubrutalism yang
ekspresif.

Karakter visual: - Cream hangat sebagai latar utama. - Kuning cerah
sebagai warna identitas dan aksen utama. - Charcoal sebagai teks dan
outline. - Komponen rounded dengan outline tegas. - Shadow tactile yang
terukur. - Detail dekoratif organik dan pola halftone digunakan secara
selektif. - Blob glossy 3D menjadi maskot utama. - Typography Poppins
dengan hierarki yang jelas.

Hasil akhir harus terlihat konsisten, rapi, modern, menyenangkan, dan
mudah digunakan. Hindari tampilan generik, terlalu ramai, terlalu banyak
kartu, dekorasi tanpa fungsi, warna neon, gradient berlebihan, dan
shadow tebal pada semua elemen.

## 1.1 Referensi yang menjadi sumber kebenaran

Lampirkan **hanya tiga gambar referensi** berikut ke Google AI Studio. Gunakan nama file persis seperti ini:

1. `referensi_desain_system.png` — acuan utama warna, typography, komponen, radius, spacing, border, shadow, dan ikon.
2. `referensi_desain_blob.png` — acuan bentuk, proporsi, material glossy, ekspresi, serta background gradient kuning ke cream.
3. `referensi_login.png` — acuan komposisi halaman login, posisi Blob, form, tombol, dan interaksi.

Jangan meminta atau mengandalkan GIF/video referensi. GIF animasi tidak dilampirkan dan bukan bagian dari paket referensi.

Jika ada perbedaan antarreferensi, prioritaskan:
- `referensi_desain_system.png` untuk aturan visual dan komponen.
- `referensi_desain_blob.png` untuk karakter Blob.
- `referensi_login.png` untuk layout dan pengalaman Login.

Jangan menyalin gambar referensi sebagai satu gambar latar aplikasi. Bangun ulang UI menggunakan komponen Android native.

------------------------------------------------------------------------

# 2. BATASAN PROYEK

Sebelum mengubah kode:

1.  Audit struktur project yang sedang terbuka.
2.  Identifikasi arsitektur, dependency, theme, komponen, autentikasi,
    aset, navigasi, database, API, dan ViewModel yang sudah ada.
3.  Pertahankan arsitektur serta fungsi yang masih berjalan.
4.  Jangan membuat project Android baru.
5.  Jangan mengganti database, API, repository, ViewModel, atau sistem
    autentikasi tanpa alasan teknis yang kuat.
6.  Jangan menghapus fitur lama.
7.  Jangan mengubah halaman Beranda, Absensi, Kas, Jadwal, Tugas, Admin,
    Pengurus, dan Siswa pada tahap ini.
8.  Jangan mengubah aturan hak akses tiga role.
9.  Jangan menyalin data contoh dari gambar referensi ke data aplikasi.
10. Jangan membuat tombol atau kontrol yang hanya dekoratif.
11. Jangan mengklaim pengujian berhasil jika belum dilakukan.

Jika ada konflik dengan implementasi lama, jelaskan risiko dan file
terkait sebelum melakukan perubahan yang berisiko.

------------------------------------------------------------------------

# 3. DESIGN SYSTEM

Implementasikan design system terpusat dan reusable. Semua halaman yang
dikerjakan harus menggunakan token yang sama.

## 3.1 Color tokens

Palet utama:

  Token               Hex
  ------------------- -----------
  Primary Yellow      `#FFC928`
  Secondary Yellow    `#FFD95A`
  Light Yellow        `#FFF0B8`
  Background Cream    `#FDF8EC`
  Surface Primary     `#FFFFFF`
  Surface Secondary   `#F2ECE0`
  Charcoal            `#1C1B1F`
  Muted Border        `#E6DEC8`
  Success             `#35A66B`
  Warning             `#F2A93B`
  Error               `#E34B4B`
  Information         `#4A91D9`

Aturan penggunaan: - Cream menjadi latar dominan. - Kuning menjadi aksen
utama, highlight, dan warna CTA. - Charcoal untuk teks utama, ikon, dan
outline. - Putih untuk surface dan area input. - Warna semantik hanya
digunakan untuk status yang sesuai. - Gradient kuning ke cream digunakan
pada area hero, background Blob, dan Login sesuai referensi; jangan
menerapkan gradient pada semua komponen. - Jangan membuat warna baru
secara acak. Jika membutuhkan warna tambahan, dokumentasikan token dan
alasannya.

## 3.2 Typography

Gunakan Poppins sebagai font utama jika aset font tersedia di project.
Jika belum tersedia, periksa resource yang ada sebelum menambahkan aset.

Buat skala: - Display Large / Medium - Headline Large / Medium - Title
Large / Medium - Body Large / Medium / Small - Label Large / Medium /
Small

Gunakan bobot ExtraBold/Bold untuk judul, SemiBold untuk label dan
tombol, serta Medium/Regular untuk isi. Jaga keterbacaan, line height,
dan dukungan font scaling Android.

## 3.3 Spacing

Gunakan basis 4dp: - 4dp: mikro - 8dp: kecil - 12dp: jarak komponen -
16dp: padding standar - 20dp: antarkelompok - 24dp: antarseksi - 32dp:
jarak besar

## 3.4 Radius

-   Small: 8dp
-   Medium: 12dp
-   Large: 16dp
-   Extra Large: 24dp
-   Full: pill

## 3.5 Border dan shadow

-   Border utama sekitar 1.5--2dp.
-   Gunakan charcoal untuk outline utama.
-   Outline tegas diprioritaskan pada CTA, input, dan komponen penting.
-   Shadow tactile memiliki offset yang konsisten dan blur terbatas.
-   Jangan memberi shadow berat pada semua komponen.
-   Hindari lapisan shadow berlebihan.

## 3.6 Icon system

Gunakan ikon vektor yang konsisten. Jangan menggunakan emoji sebagai
ikon UI. Jaga ketebalan stroke, ukuran, alignment, dan makna ikon.

## 3.7 Reusable components

Siapkan komponen: - Primary Button - Secondary Button - Outlined
Button - Icon Button - Text Field - Password Field - Checkbox - Toggle -
Badge / Chip - Status Indicator - Card / Container - Dialog - Bottom
Sheet - Loading Indicator - Empty State - Error State - Snackbar / Toast

Semua komponen harus mengambil warna, radius, border, typography, dan
shadow dari design tokens.

------------------------------------------------------------------------

# 4. BLOB MASCOT 3D GLOSSY

Blob adalah identitas karakter IX-H HUB 1.0. Gunakan dua referensi Blob
final yang dilampirkan.

## 4.1 Bentuk dan identitas

Pertahankan karakteristik: - Tubuh kuning organik, membulat, dan sedikit
asimetris. - Tunas hijau kecil di bagian atas. - Goggles putih
keperakan. - Outline charcoal tebal. - Pipi pink. - Mata dan mulut
ekspresif. - Highlight putih. - Proporsi tubuh yang konsisten.

Gunakan bentuk hero dari referensi Blob kedua dan gaya ekspresi wajah
dari referensi Blob pertama. Jangan membuat siluet, proporsi, aksesori,
atau identitas Blob berubah antar-ekspresi.

Jika aset Blob asli dari project masih tersedia dan sudah disetujui
sebagai bentuk kanonik, gunakan sebagai referensi bentuk utama. Jangan
mengganti karakter lama secara diam-diam. Jika bentuk referensi baru
berbeda dari aset lama, tampilkan perbandingan dan minta persetujuan
sebelum mengganti aset kanonik.

## 4.2 Material 3D Glossy

Target material: glossy vinyl / polished toy / soft clay.

Gunakan: - Gradasi volume kuning ke oranye hangat. - Multi-layer
specular highlight. - Glossy rim light. - Ambient occlusion. - Soft
contact shadow. - Pipi airbrushed. - Pantulan cahaya mengikuti
lengkungan tubuh.

Blob harus terlihat memiliki volume 3D, bukan gambar flat yang hanya
diberi drop shadow.

## 4.3 Background Blob

Background gradient kuning ke cream **wajib dipertahankan** pada halaman
preview dan komposisi hero yang menggunakannya.

Gunakan: - Gradient kuning ke cream. - Bentuk organik kuning di sudut
atau tepi. - Halftone dots secara selektif. - Ruang kosong yang cukup
agar karakter tetap menjadi fokus.

Jangan menghilangkan gradient atau menggantinya dengan latar putih
polos. Untuk penggunaan Blob sebagai ikon kecil atau aset transparan di
dalam komponen, background boleh tidak disertakan jika konteks UI memang
membutuhkannya.

## 4.4 Sepuluh ekspresi

Buat sepuluh ekspresi: 1. IDLE --- wajah normal, mata terbuka. 2. SMILE
--- tersenyum bahagia. 3. SAD --- sedih, mata berkaca-kaca. 4. BLINK ---
satu kedipan natural. 5. FOCUS --- fokus memperhatikan atau menggunakan
perangkat. 6. SLEEP --- mata tertutup dan ekspresi tidur. 7.
PASSWORD_SHOW --- mata tertutup atau ditutup tangan. 8. PASSWORD_HIDE
--- mata terbuka. 9. THINKING --- ekspresi berpikir. 10. EXCITED ---
sangat antusias.

Semua aset harus: - Memiliki siluet dan skala karakter yang sama. -
Memiliki arah pencahayaan dan material konsisten. - Memiliki posisi
karakter konsisten. - Tidak terpotong. - Memiliki transparansi jika
dipakai sebagai aset karakter. - Tidak memiliki teks, watermark, atau
dekorasi yang menempel pada karakter kecuali memang bagian dari
ekspresi. - Memiliki resolusi memadai untuk penggunaan Android.

## 4.5 Animasi Blob

Gunakan animasi berbasis state dengan Jetpack Compose Animation: - IDLE:
floating dan breathing lembut. - BLINK: kedipan natural. - SMILE: bounce
kecil. - SAD: gerakan menunduk. - FOCUS: gerakan mikro yang halus. -
SLEEP: breathing lambat. - PASSWORD_SHOW: mata tertutup. -
PASSWORD_HIDE: mata terbuka. - THINKING: gerakan kecil. - EXCITED: dua
kali bounce.

Gunakan Animatable, animateFloatAsState, graphicsLayer, dan Crossfade
sesuai kebutuhan. Hindari infinite animation permanen. Jeda animasi saat
aplikasi berada di background. Hormati reduce motion jika tersedia.
Pastikan animasi tidak menyebabkan recomposition atau penggunaan CPU/GPU
berlebihan.

------------------------------------------------------------------------

# 5. LOGIN UI

Bangun ulang halaman Login menggunakan Design System dan Blob final.

## 5.1 Komposisi

Gunakan layout vertikal yang mengacu pada referensi Login: 1. Header
brand: logo, IX-H HUB 1.0, SMPN 1 Bojongsari (Spensari), Kelas IX-H. 2.
Area hero Blob yang cukup besar dan menjadi focal point. 3. Sapaan
singkat, misalnya "Selamat Datang!". 4. Subteks: "Masuk untuk
melanjutkan aktivitas kelas IX-H." 5. Input Username. 6. Input Password
dengan tombol show/hide. 7. Tombol utama "Masuk". 8. Tautan atau
tindakan "Butuh bantuan login?" jika mekanismenya tersedia.

Jangan menambahkan elemen yang tidak diperlukan. Jaga proporsi antara
Blob dan form agar form tetap mudah digunakan.

## 5.2 Background dan komposisi visual

-   Gunakan gradient kuning ke cream seperti referensi.
-   Gunakan aksen organik dan halftone secara selektif.
-   Pertahankan outline charcoal, komponen rounded, dan shadow tactile.
-   Hindari ornamen yang mengganggu area input.
-   Pastikan kontras teks dan kontrol memadai.

## 5.3 Interaksi form

Username: - Menggunakan username, bukan email. - Validasi input. -
Keyboard yang sesuai.

Password: - Tersembunyi secara default. - Tombol show/hide berfungsi. -
Saat tersembunyi: Blob PASSWORD_HIDE dengan mata terbuka. - Saat
terlihat: Blob PASSWORD_SHOW dengan mata tertutup/tertutup tangan. -
Perubahan ekspresi menggunakan transisi lembut.

Saat pengguna mengetik, Blob boleh beralih ke FOCUS secara terkontrol.
Jangan membuat ekspresi berubah-ubah secara mengganggu.

Saat tombol Masuk ditekan: - Validasi form. - Tampilkan loading. - Cegah
pengiriman berulang. - Tampilkan pesan error jika autentikasi gagal. -
Tampilkan feedback berhasil jika autentikasi benar-benar berhasil. -
Jangan pernah menulis password ke log.

## 5.4 Autentikasi dan role

Pertahankan mekanisme autentikasi project yang sudah tersedia. Jangan
membuat login palsu atau hardcoded success.

Role aplikasi: - Admin - Pengurus - Siswa

Setelah autentikasi berhasil, arahkan ke tujuan yang sesuai dengan role
dan aturan akses lama. Jangan mengubah sistem hak akses.

## 5.5 Responsive layout

Prioritaskan ukuran layar ponsel normal dengan rasio sekitar 9:16,
tetapi layout harus adaptif terhadap variasi ukuran layar.

Ketentuan: - Tidak terpotong pada layar kecil. - Form dapat di-scroll
ketika keyboard muncul. - Tidak tertutup system bars atau navigation
bar. - Touch target minimal 48dp. - Mendukung font scaling. - Jaga agar
tombol Masuk tetap mudah dijangkau. - Jangan mengunci UI ke satu ukuran
piksel.

------------------------------------------------------------------------

# 6. ANIMASI LOGIN

Tidak ada GIF atau video referensi yang dilampirkan. Rancang animasi Login berdasarkan tiga gambar referensi, Design System, dan spesifikasi interaksi tertulis dalam dokumen ini.

Urutan animasi yang diinginkan:

1. Background gradient kuning–cream tampil dengan transisi lembut.
2. Header IX-H HUB muncul.
3. Blob masuk dari bawah dengan gerakan naik dan bounce ringan.
4. Blob berkedip atau tersenyum.
5. Welcome text muncul.
6. Field Username dan Password muncul berurutan dengan transisi halus.
7. Saat pengguna mengetik, Blob bereaksi dengan ekspresi FOCUS.
8. Saat visibilitas password berubah, ekspresi Blob ikut berubah.
9. Saat tombol Masuk ditekan, tombol masuk ke loading state dan Blob menunjukkan ekspresi THINKING.
10. Setelah hasil autentikasi diterima, tampilkan success atau error state yang sesuai.

Penting:
- Jangan menampilkan success state sebelum autentikasi benar-benar berhasil.
- Gunakan animasi singkat, ringan, dan tidak menghambat pengguna.
- Hormati pengaturan reduce motion jika tersedia.
- Targetkan komposisi ponsel vertikal sekitar 9:16, tetapi buat layout adaptif terhadap variasi ukuran layar.

------------------------------------------------------------------------

# 7. IMPLEMENTASI TEKNIS

Teknologi: - Kotlin - Jetpack Compose - Material 3 - Arsitektur yang
sudah ada di project

Ketentuan: 1. Gunakan reusable composables. 2. Pisahkan theme, design
tokens, components, mascot, dan authentication UI. 3. Gunakan state
management yang sesuai. 4. Hindari duplikasi kode. 5. Hindari hardcoded
color, radius, dan spacing di halaman. 6. Optimalkan ukuran aset gambar.
7. Gunakan LazyColumn untuk daftar panjang jika diperlukan. 8. Hindari
blur dan shader berat. 9. Jangan menambah dependency jika tidak
diperlukan. 10. Optimalkan untuk perangkat Android RAM 2 GB.

------------------------------------------------------------------------

# 8. URUTAN PELAKSANAAN

Kerjakan bertahap. Jangan mengerjakan semua tahap sekaligus tanpa
pemeriksaan.

## TAHAP 1 --- DESIGN SYSTEM

-   Audit theme dan komponen lama.
-   Implementasikan color tokens.
-   Implementasikan typography Poppins.
-   Implementasikan spacing, radius, border, dan shadow.
-   Buat icon guideline.
-   Buat reusable components.
-   Buat halaman preview Design System.

Setelah selesai, tampilkan screenshot preview, daftar file yang berubah,
dan hasil build. Tunggu persetujuan.

## TAHAP 2 --- BLOB MASCOT

-   Audit aset Blob yang ada.
-   Tetapkan bentuk kanonik setelah membandingkan dengan referensi.
-   Buat sepuluh ekspresi.
-   Terapkan material 3D Glossy.
-   Pertahankan background gradient pada preview/hero.
-   Implementasikan animasi berbasis state.
-   Buat halaman preview Blob.

Tampilkan seluruh ekspresi dan demonstrasi animasi. Tunggu persetujuan.

## TAHAP 3 --- LOGIN

-   Buat ulang Login UI.
-   Integrasikan Design System.
-   Integrasikan Blob final.
-   Implementasikan form dan validasi.
-   Implementasikan show/hide password.
-   Implementasikan animasi login.
-   Integrasikan autentikasi project.
-   Uji role Admin, Pengurus, dan Siswa.

Tampilkan screenshot pada rasio ponsel 9:16 dan demonstrasi interaksi.
Tunggu persetujuan.

## TAHAP 4 --- QUALITY CONTROL

Uji: - Build dan compile. - Visual inspection. - Responsivitas. -
Keyboard dan scrolling. - Validasi form. - Show/hide password. -
Perubahan ekspresi Blob. - Animasi foreground/background. - Autentikasi
dan role. - Performa pada perangkat kelas rendah.

------------------------------------------------------------------------

# 9. STANDAR HASIL DAN PELAPORAN

Setiap tahap harus melaporkan: - File yang dibuat. - File yang diubah. -
Penjelasan implementasi. - Screenshot hasil. - Hasil pengujian yang
benar-benar dilakukan. - Kendala yang masih ada.

Jangan menyatakan build, animasi, autentikasi, atau pengujian berhasil
jika hanya berdasarkan asumsi. Bedakan compile check, build APK, dan
pengujian pada perangkat nyata.

Jangan mengubah halaman lain di luar ruang lingkup.

**MULAI DARI TAHAP 1 --- DESIGN SYSTEM.**

Setelah Tahap 1 selesai, berhenti dan tunggu persetujuan pengguna
sebelum melanjutkan ke Tahap 2.
