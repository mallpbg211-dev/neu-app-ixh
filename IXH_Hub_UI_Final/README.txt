IX-H Hub 2.0 - Final Storyboard
Gaya acuan: zip 2 (Blob 3D) - Android, Material 3, Poppins, Kuning #FFC928, Ink #1C1B1F, Krem #FFF9E8
Tanggal acuan: Rabu, 4 November 2026

00 Design system (REVISI: warna status Hadir hijau / Izin kuning / Sakit biru / Alpha merah; komponen baru: toggle, kalender status warna, bottom sheet, donut persentase, gelembung WhatsApp, chip status nomor, progress bar, checkbox)
01 Dashboard per role (REVISI: banner pengumuman tersemat, lonceng + lencana, menu cepat Siswa/Pengurus/Admin, badge "3 nomor bermasalah", state kosong, Kelola Pengguna)
02 Absensi (zip 2)
03 Kas (REVISI: tab Tagihan, ringkasan tagihan aktif + tombol ke storyboard 19, transaksi "Dari tagihan", tambah transaksi, riwayat & ekspor, state tagihan kosong)
04 Jadwal, 05 Tugas (zip 2)
06 Panel Bot (REVISI: kartu Tagihan Kas, tujuan Pesan pribadi per siswa + penerima valid/dilewati, badge "Tagihan" di riwayat dengan status terkirim/sebagian gagal/gagal, ringkasan pesan pribadi hari ini, peringatan bot terputus, info jeda acak)
07 Profil & Akun (REVISI: digambar ulang bergaya zip 2, blob di layar berhasil logout)
08 Manajemen Pengguna, 09 State layar
10-13 Splash, Login, Autentikasi, Alur autentikasi
14-15 Animasi three.js
16 Pengumuman, 17 Notifikasi, 18 Kehadiranku Siswa
19 Tagihan Kas per Siswa

Folder tambahan_dari_zip1: referensi langkah tambahan (tambah tugas, notifikasi tugas, ekspor kas, absensi berhasil, hasil kirim WhatsApp). Perlu disesuaikan ke gaya zip 2.

Status: 24 Pengajuan Izin/Sakit dan 27 Pergantian Semester dibatalkan; 25 Onboarding sudah dibuat.

--- Batch 1 (perbaikan konsistensi) ---
01 Dashboard: persona Siswa = Citra Lestari (belum bayar, tagihan Rp6.000); layar kosong = Bima Sakti (sudah lunas, tidak ada tagihan aktif).
06 Panel Bot: pratinjau pesan WhatsApp sekarang untuk Citra Lestari (Bima sudah lunas).
19 Tagihan per Siswa: tanggal bayar Bima disamakan dengan Kas (4 Nov).
01, 03, 06, 07: footer digeser agar tidak menabrak segitiga pojok kanan bawah.

Hak akses (disepakati):
- Pengurus: buka Panel Bot untuk kirim laporan & tagihan, lihat status pengiriman, tandai lunas. Tidak bisa kelola pengguna, pengaturan bot, template, restart server.
- Admin: semua fitur, termasuk Pengaturan Bot, Template Pesan, Kelola Pengguna.
- Siswa: hanya melihat data miliknya.

Penomoran file baru: 20 Pengaturan Bot & WA, 21 Pengaturan Kelas, 22 Template Pesan, 23 Alur STOP/MULAI, 24 Pengajuan Izin/Sakit, 25 Onboarding, 26 Log Aktivitas, 27 Pergantian Semester, 28 Diagram Alur Tagihan & Bot, 29-30 Animasi three.js.

--- Batch 1b (layar baru, dibuat dari HTML; sumber di folder sumber_html) ---
20 Pengaturan Bot & Koneksi WhatsApp (7 layar + Aturan Bisnis): status & nomor bot, batas kirim 60/jam + jeda 8-20 detik, hubungkan ulang lewat QR ATAU kode pairing 8 karakter (tautkan ke nomor telepon bot), log kegagalan (+ kosong), Dashboard Admin bot terputus, Status Pengiriman terputus. Khusus Admin.
22 Template Pesan Bot (6 layar + Aturan Bisnis): daftar template, editor variabel {nama}/{nominal}/{jatuh_tempo}, 3 nada pengingat, gagal simpan, atur ulang bawaan, pesan tes terkirim. "Balas STOP" terkunci.
21 Pengaturan Kelas (5 layar + Aturan Bisnis): nama kelas, tahun ajaran/semester, kas default Rp6.000, jatuh tempo Jumat, pengingat 20.00 WIB, batas kehadiran 90%, konfirmasi simpan, tampilan read-only Pengurus. Khusus Admin; sumber default untuk layar 06, 18, 19, 22.

Aturan ikon: tidak memakai emoji. Semua ikon di layar baru (20-22 dan seterusnya) memakai SVG garis (stroke 2, ujung bulat) dari sumber_html/icons.py.

--- Koreksi aturan mata maskot (login) ---
Aturan yang benar: password TERSEMBUNYI -> mata maskot TERBUKA; password TERLIHAT (tombol mata ditekan) -> mata maskot MENUTUP.
Sudah dikoreksi di: 11_login_ekspresi_blob (layar 5 & 6 + subjudul), 12_autentikasi (layar 2 Password Baru), 13_alur_autentikasi (kotak Ganti password wajib), 14_animasi_threejs_1 (baris Login: frame 3 & 4 + kartu aturan).
Layar baru (mis. 25 Onboarding/ganti password wajib) harus mengikuti aturan ini.

25 Onboarding & Login Pertama (7 layar + Aturan Bisnis): ganti password wajib (tersembunyi = mata blob terbuka; terlihat = mata menutup), persetujuan nomor WhatsApp (+ dialog konfirmasi lewati -> status Berhenti), 3 layar pengenalan. Contoh siswa: Citra Lestari. 24 Pengajuan Izin/Sakit dibatalkan (tidak diperlukan).

26 Log Aktivitas (6 layar + Aturan Bisnis, khusus Admin): daftar log (lunas, absensi, role, hapus data), filter pengguna/tanggal/jenis, detail entri sebelum/sesudah (permanen), hasil kosong, gagal memuat/offline, akses ditolak untuk Pengurus. Data selaras: Hana/Bima/Gita lunas 4 Nov, Aisyah jadi Pengurus 3 Nov.

--- Update 00_design_system ---
Isi lama (bagian 1-9) dipertahankan. Ditambah di bawahnya: 10 Matriks Hak Akses (Admin/Pengurus/Siswa, satu sumber untuk semua storyboard), 11 Aturan Ikon (SVG garis, tanpa emoji), 12 Ekspresi Maskot di Login (password tersembunyi = mata terbuka, terlihat = mata menutup), 13 Komponen Tambahan (alert, tab, stepper, kode pairing, input terkunci, chip jenis log, dialog konfirmasi).
Sumber bagian baru: sumber_html/pds.py dan 00_tambahan.html.

--- 23 Alur STOP & MULAI (7 layar + Aturan Bisnis; sumber: sumber_html/p23.py) ---
Contoh siswa: Rangga Putra. 1 Balas STOP di WhatsApp (bot membalas satu konfirmasi), 2 Notifikasi ke Admin & Pengurus, 3 Detail Pengguna status Berhenti (Aktifkan Kembali terkunci; Salin Pesan Tagihan sebagai fallback), 4 Dilewati saat Kirim Tagihan (28 dikirim + 1 berhenti + 3 bermasalah = 32), 5 Balas MULAI di WhatsApp, 6 Aktifkan lewat aplikasi (Profil siswa, dialog konfirmasi), 7 Valid lagi + riwayat status nomor.
Aturan: STOP/BERHENTI dan MULAI/AKTIF tidak peka huruf; hanya siswa/orang tua yang bisa mengaktifkan; tagihan tetap tercatat; STOP/MULAI masuk notifikasi dan Log Aktivitas.
Catatan: layar 21 (Pengaturan Kelas) dan 00 (Design System) masih menyebut Pergantian Semester (27); perlu dihapus karena fitur ini dibatalkan.

--- REVISI 25 Onboarding (9 layar + Aturan Bisnis) ---
Persetujuan pesan WhatsApp sekarang WAJIB (tombol "Tidak, lewati dulu" dan dialog konfirmasi lewati dihapus; menggantikan keterangan 25 di atas).
Jabat tangan dengan bot memakai kalimat biasa, bukan "HALO": "tagihan kas saya", "laporan kas saya", "tagihan kas aku", "laporan kas aku" (kata kas + saya/aku, tidak peka huruf; kalimat bebas boleh).
Alur: 1-2 ganti password, 3 persetujuan wajib, 4 hubungkan (pilih kalimat, buka WhatsApp, menunggu pesan, Hubungi Admin), 5 balasan bot berisi tagihan milik pengirim, 6 nomor terhubung (status Valid otomatis), 7-9 pengenalan.
Pesan harus dari nomor terdaftar; bot hanya membalas data milik pengirim. Kalimat yang sama dipakai kapan saja untuk cek tagihan.
Dampak ke storyboard lain (belum direvisi): 08/10 status "Belum dicek" untuk siswa baru kini terisi otomatis setelah jabat tangan; 19 Panel Bot/Tagihan bisa menambah info perintah "tagihan kas saya".

--- 29-30 Animasi three.js Fitur Baru (format sama dengan 14-15; sumber: sumber_html/p29.py) ---
29 (1/2): Pengumuman, Notifikasi, Kehadiranku, Tagihan Kas.
30 (2/2): Manajemen Pengguna, Pengaturan Bot (QR / kode pairing), Jabat Tangan Bot (onboarding, kalimat "tagihan kas saya"), Alur STOP & MULAI.
Tiap fitur 4 frame + stempel waktu + gizmo XYZ; hanya satu canvas three.js per layar. Aturan mata blob login tidak berubah.
Status akhir: 28 Diagram Alur Tagihan & Bot belum dibuat; 24 dan 27 dibatalkan.

--- 31 Ganti Tema (6 layar + Aturan Bisnis; sumber: sumber_html/p31.py) ---
Dua tema: Neubrutalism (bawaan) dan Liquid Glass. Akses: Pengaturan > Tampilan (di atas pilihan Mode terang/gelap yang sudah ada di 07).
1 Tampilan (Neubrutalism aktif), 2 Pilih & Pratinjau Liquid Glass, 3 Beranda Admin Liquid Glass, 4 Absensi Liquid Glass, 5 Perbandingan Komponen (penanda kaca/datar), 6 Perangkat Ringan (kaca solid, toggle dikunci).
Prinsip: Liquid Glass HARUS ENTENG. Isi layar datar dan putih tanpa blur; kaca hanya di bilah navigasi bawah dan pesan singkat. Maks 1 blur permanen + 1 sementara per layar; animasi hanya geser/pudar; tanpa blur di daftar yang di-scroll.
Wajib responsif sampai HP RAM 2 GB atau kurang: perangkat ringan (RAM <= 2 GB, hemat daya, kurangi transparansi) memakai kaca solid tanpa blur dan animasi; animasi three.js diganti gambar diam.
Keputusan yang diasumsikan (perlu dikonfirmasi): tema tersimpan di perangkat dan berlaku semua role; mode gelap belum digambar; layar login/splash versi Liquid Glass belum ada.
Catatan: layar 04 memakai warna status revisi Design System (Hadir hijau, Izin kuning, Sakit biru, Alpha merah); layar 02 masih warna lama.

--- 32 Pengenalan Aplikasi / Tur Fitur (10 layar + Aturan Bisnis; sumber: sumber_html/p32.py) ---
Tur coachmark singkat setelah onboarding (25): lapisan gelap datar, elemen disorot cincin kuning, tooltip berisi judul + fungsi + siapa yang boleh memakai, tombol Kembali/Lanjut/Lewati, indikator langkah.
1 Tawaran Tur (Mulai Tur / Nanti Saja), 2 Tur 1/6 Kehadiran, 3 Tur 3/6 Tombol Tagihan, 4 Tur 5/6 Bilah Bawah, 5 Tur 6/6 Tombol Akun & Bantuan, 6 Tur Selesai, 7 Tur Siswa 2/5 Tagihanku, 8 Pusat Bantuan (Panduan Fitur + Ulangi Tur), 9 Penjelasan Fitur (contoh Tagihan Kas: fungsi, cara pakai, arti tombol), 10 Arti Status lewat ikon info (contoh Absensi).
Aturan: tur muncul sekali dan bisa diulang dari Profil > Bantuan; langkah sesuai role (Admin 6, Pengurus 5, Siswa 5) dan hanya fitur yang boleh dipakai role itu; status tur tersimpan per akun; ringan (tanpa blur, tanpa animasi di perangkat RAM 2 GB atau kurang).
Dasar: zip IXH_Hub_UI_Final(5) (sudah memuat 31 Ganti Tema). Catatan: layar 21 dan 00 masih menyebut Pergantian Semester (27, dibatalkan); belum diubah.

--- 32A Langkah Tur per Role (7 layar + 3 tabel + Pedoman + Aturan Bisnis; sumber: sumber_html/p32a.py dan tourkit.py) ---
Tabel sumber tunggal isi tooltip: elemen yang disorot, judul, isi, posisi (atas/bawah) untuk Admin (6 langkah), Pengurus (6), Siswa (5).
Layar langkah yang belum tergambar di 32: Admin 2/6 Saldo Kas, Admin 4/6 Lonceng, Pengurus 2/6 Ambil Absensi, Siswa 1/5 Kehadiranku, Siswa 3/5 Lonceng, Siswa 4/5 Bilah Bawah, Siswa 5/5 Akun & Bantuan.
Pedoman tooltip: judul maks. 3 kata, isi maks. 2 kalimat (~120 karakter); tooltip di bawah elemen (di atas bila elemen di bagian bawah layar); tombol Kembali/Lanjut/Selesai/Lewati; langkah tanpa elemen dilewati otomatis; teks dari satu berkas string per role.
Koreksi pada 32: aturan jumlah langkah Pengurus diubah dari 5 menjadi 6 (Kehadiran, Ambil Absensi, Tagihan, Lonceng, Bilah bawah, Akun) agar sama dengan 32A.
Berikutnya (rencana): 32B petunjuk pertama kali per layar + daftar ikon info, 32C Pusat Bantuan lengkap, 32D kondisi khusus dan aksesibilitas.

--- 32B Petunjuk Pertama Kali per Layar + Daftar Ikon Info (10 layar + 2 tabel + Aturan Bisnis; sumber: sumber_html/p32b.py, memakai lib.py, tourkit.py, icons.py) ---
Tabel sumber tunggal isi petunjuk (8 petunjuk): Absensi 1/2 Ubah Status, 2/2 Simpan Absensi; Kas 1/2 Tab Tagihan, 2/2 Tambah Transaksi; Jadwal 1/2 Pilih Hari, 2/2 Detail Pelajaran; Tugas 1/2 Warna Urgensi, 2/2 Tombol Tambah. Format tooltip sama dengan 32/32A (lapisan gelap datar, cincin kuning, Kembali/Lanjut/Selesai/Lewati).
Daftar layar berikon info (6): Daftar Absensi (02), Kelola Tagihan (19), Kalender Akademik (04), Daftar Tugas (05), Kehadiranku (18), Panel Bot (06). Digambar: bottom sheet Arti Status Tagihan (Lunas/Belum/Tunggakan) dan Arti Warna Tugas (merah/kuning/biru/hijau); Arti Status Absensi sudah ada di 32 layar 10.
Aturan: muncul sekali per layar setelah tur 32 selesai/dilewati; Absensi 2 & Kas 2 (Admin & Pengurus), Jadwal 2 (semua role), Tugas 2 (Admin & Pengurus) atau 1 (Siswa, tanpa tombol tambah); Siswa tidak dapat petunjuk Absensi/Kas (sudah di tur Siswa 32A); status tersimpan per akun per layar, ikut diulang oleh Ulangi Tur; ikon info selalu ada; ringan (tanpa blur, tanpa animasi di RAM 2 GB atau kurang).
Asumsi (perlu dikonfirmasi): Ulangi Tur juga mengulang petunjuk 32B; Siswa tidak punya petunjuk Absensi/Kas.
Berikutnya: 32C Pusat Bantuan lengkap, 32D kondisi khusus dan aksesibilitas.

--- 32C Pusat Bantuan Lengkap (12 layar + tabel isi per role + Aturan Bisnis; sumber: sumber_html/p32c.py, memakai lib.py, tourkit.py, icons.py) ---
Tabel isi Pusat Bantuan per role (mengikuti Matriks Hak Akses 00): Admin 9 panduan, Pengurus 6, Siswa 6; label "Lihat saja" untuk Absensi, Kas, Pengumuman bagi Siswa.
Layar: 1-3 Bantuan Admin / Pengurus / Siswa (pencarian, Ulangi Tur, Panduan Fitur; kartu Hubungi Admin untuk Pengurus dan Siswa), 4 Pencarian ada hasil ("tagih", huruf cocok disorot, dikelompokkan Fitur dan Tombol & istilah), 5 Hasil kosong ("ajukan izin", saran kata + Hubungi Admin), 6-10 Penjelasan fitur (Absensi, Kas, Jadwal & Tugas, Tagihanku, Bot WhatsApp: fungsi, cara pakai, tombol di layar ini), 11 Hubungi Admin (pilih topik, pratinjau pesan, Buka WhatsApp / Salin Pesan), 12 WhatsApp dengan pesan terisi ke Ahmad Ramadhan (belum terkirim).
Aturan: pencarian mulai 2 huruf, tidak peka huruf, maks 8 hasil, sesuai role; fitur dibatalkan tidak muncul; Hubungi Admin hanya Pengurus dan Siswa, pesan tidak dikirim otomatis, Salin Pesan sebagai cadangan; Bantuan dan pencarian bisa offline.
Asumsi (perlu dikonfirmasi): Bantuan/pencarian offline; Hubungi Admin lewat WhatsApp pribadi ke nomor Admin (bukan lewat bot); topik pesan: Tidak bisa login, Tagihan salah, Absensi salah, Lainnya.
KOREKSI yang perlu dibuat di 32B: Matriks Hak Akses menyatakan Siswa boleh MELIHAT Absensi dan Kas, jadi aturan 32B "Siswa tidak melihat petunjuk Absensi dan Kas" kurang tepat. Belum diubah.
Berikutnya: 32D kondisi khusus dan aksesibilitas.

--- 32D Kondisi Khusus & Aksesibilitas (8 layar + 2 tabel + Aturan Bisnis + Daftar Uji; sumber: sumber_html/p32d.py + glass_extra.css, memakai lib.py, tourkit.py, icons.py) ---
Layar: 1 Lewati Tur (dialog konfirmasi), 2 Tur Terhenti (Lanjutkan dari langkah 3 / Mulai dari Awal / Nanti Saja), 3 Offline Saat Tur (snackbar, tur tetap jalan), 4 Ukuran Font 200% (tooltip membesar, tombol tetap terlihat), 5 TalkBack (kotak fokus, urutan 1-4, teks yang dibacakan), 6 Target Sentuh 48 dp (area sentuh putus-putus, jarak 8 dp), 7 Sorotan & Chip Aksesibel (cincin kuning + garis ink, chip langkah teks ink), 8 Tur di Tema Liquid Glass (tooltip solid putih tanpa blur, cincin kuning + garis putih, bilah navigasi kaca tetap).
Tabel: Label & Urutan TalkBack (8 komponen); Kontras Warna Tur (rasio dihitung, WCAG teks 4,5:1 dan bukan teks 3:1).
Temuan kontras (dihitung): chip langkah "n dari N" #B88A00 di #FFEBA8 = 2,7:1 GAGAL; chip peran biru #2F6FDB di #DCE6FA = 3,8:1 GAGAL; cincin kuning di atas lapisan gelap = 2,2:1 GAGAL (non-teks). Perbaikan: teks chip langkah ink (14,4:1), chip biru #1F4FA8 (6,1:1), cincin ditambah garis ink 2 px (5,0:1) atau garis putih di Liquid Glass (3,4:1). Layar 32 dan 32A BELUM diubah; perbaikan digambar di 32D layar 7.
Catatan di luar tur: chip status Design System Hadir hijau #2E9B5A di #DDF1E5 = 3,0:1 dan Sakit biru juga di bawah 4,5:1 untuk teks kecil; usulan teks #1F6B3C (5,5:1) dan #1F4FA8 (6,1:1). Belum diubah.
Aturan: Lewati lewat tombol atau Kembali Android di langkah 1; tur terhenti menyimpan posisi; offline tetap jalan; font besar menggulir; target sentuh 48 dp; Liquid Glass tooltip tanpa blur; hapus animasi dan RAM 2 GB atau kurang tanpa animasi; mode gelap belum digambar.
Asumsi (perlu dikonfirmasi): tawaran Lanjutkan tampil saat aplikasi dibuka lagi dan Nanti Saja menutupnya tanpa muncul lagi otomatis; urutan fokus TalkBack: teks tooltip, Kembali, Lanjut, Lewati.
Seri 32 (32, 32A-32D) lengkap. Belum diperbaiki: aturan 32B tentang Siswa tidak dapat petunjuk Absensi/Kas (lihat catatan 32C).
