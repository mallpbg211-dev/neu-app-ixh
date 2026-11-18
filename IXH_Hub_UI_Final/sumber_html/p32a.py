from tourkit import *
import lib
lib.CSS+='''
.tt{width:100%;border-collapse:collapse;font-size:10.5px}
.tt th{text-align:left;font-size:9.5px;color:#6B6458;font-weight:600;padding:0 6px 5px;border-bottom:1.5px solid #1C1B1F}
.tt td{padding:6px;border-bottom:1.2px dashed #EADFC2;vertical-align:top;line-height:1.4}
.tt tr:last-child td{border:0}.tt td:first-child{width:26px}.tt b{font-weight:700}
.pos{display:inline-block;border-radius:10px;padding:0 7px;font-size:9px;font-weight:700;background:#EFE8D6;color:#3d392f;white-space:nowrap}
'''
def hdr2(role,cc,ring=None):
    bell=f'<div style="position:relative;width:30px;height:30px;border-radius:50%;background:#fff;border:1.5px solid #EADFC2;display:flex;align-items:center;justify-content:center;{SP if ring=="bell" else ""}">[[bell]]<span style="position:absolute;right:-4px;top:-5px;background:#D94452;color:#fff;border-radius:8px;font-size:8px;font-weight:700;padding:0 4px;border:1.5px solid #fff">4</span></div>'
    av=f'<div style="width:30px;height:30px;border-radius:50%;background:#FFC928;border:2px solid #1C1B1F;display:flex;align-items:center;justify-content:center;{SP if ring=="av" else ""}">[[user]]</div>'
    return f'<div class="row sp" style="padding:4px 2px 10px"><div class="row"><div style="width:30px;height:30px;border-radius:9px;background:#FFC928;border:2px solid #1C1B1F;font-size:9px;font-weight:800;display:flex;align-items:center;justify-content:center">IXH</div><div><div class="t1" style="font-size:12.5px">IX-H Hub</div><div class="t3">Kelas IX-H • SMP Spensari</div></div></div><div class="row" style="gap:6px"><span class="chip {cc}" style="border:1.5px solid #1C1B1F">{role}</span>{bell}{av}</div></div>'
ROLES={
 'admin':dict(chip=('ADMIN','cy'),hero=('Halo, Ahmad!','Kelola seluruh data IX-H'),s1=('Absensi Hari Ini','28 / 32','87,5%'),s2=('Kas Saat Ini','Rp750.000','Terkonfirmasi'),tl=[('check','Ambil Absensi'),('wallet','Catat Kas'),('mail','Tagihan'),('bot','Panel Bot'),('users','Kelola Pengguna'),('mega','Pengumuman')]),
 'pengurus':dict(chip=('PENGURUS','cb'),hero=('Halo, Aisyah!','Semangat mengelola kelas IX-H'),s1=('Absensi Hari Ini','28 / 32','87,5%'),s2=('Kas Saat Ini','Rp750.000','Terkonfirmasi'),tl=[('check','Ambil Absensi'),('wallet','Catat Kas'),('mail','Tagihan'),('bot','Panel Bot'),('mega','Pengumuman'),('cal','Jadwal')]),
 'siswa':dict(chip=('SISWA','cg'),hero=('Halo, Citra!','Cek jadwal dan tugas hari ini'),s1=('Kehadiranku','27 / 29','93%'),s2=('Tagihan Kas','Rp6.000','Belum bayar'),tl=[('chart','Kehadiranku'),('wallet','Tagihanku'),('mega','Pengumuman'),('cal','Jadwal'),('clip','Tugas Saya'),('user','Profil')]),
}
def dash3(role,ring=None):
    r=ROLES[role]
    st=lambda s,on:f'<div class="card" style="flex:1;margin:0;padding:9px 11px;{SP if on else ""}"><div class="t3">{s[0]}</div><div style="font-weight:800;font-size:15px">{s[1]}</div><div class="t3" style="color:#2E9B5A;font-weight:600">{s[2]}</div></div>'
    tiles=''.join(tile(i,t,ring==('tile',k)) for k,(i,t) in enumerate(r['tl']))
    return (hdr2(r['chip'][0],r['chip'][1],ring)
      +f'<div class="card" style="background:#FFC928;border:2.5px solid #1C1B1F;display:flex;justify-content:space-between;align-items:center"><div><div style="font-weight:800;font-size:15px">{r["hero"][0]}</div><div class="t2" style="color:#1C1B1F">{r["hero"][1]}</div></div>{blob("smile",46)}</div>'
      +f'<div class="row" style="gap:8px;margin-bottom:8px;align-items:stretch">{st(r["s1"],ring=="s1")}{st(r["s2"],ring=="s2")}</div>'
      +'<div class="al ab" style="margin-bottom:8px"><span>[[mega]]</span><span><b>TERSEMAT</b> Rapat kelas IX-H besok pagi</span></div>'
      +f'<div class="t1" style="margin:2px 0 6px">Aksi Cepat</div><div class="tg3">{tiles}</div>')
# ---- teks tooltip (satu sumber untuk tabel dan layar) ----
T={
 'kehadiran':('Kehadiran hari ini','Menampilkan jumlah siswa yang hadir dan persentasenya. Ketuk kartu untuk melihat daftar lengkap.'),
 'kas':('Saldo kas','Menampilkan saldo kas kelas yang sudah terkonfirmasi. Ketuk untuk melihat transaksi dan laporan.'),
 'tagihan':('Tombol Tagihan','Membuat dan mengirim tagihan kas ke WhatsApp tiap siswa, lalu memantau siapa yang sudah bayar.'),
 'tagihan_p':('Tombol Tagihan','Kirim tagihan dan pengingat kas ke WhatsApp siswa, lalu tandai lunas setelah bukti bayar masuk.'),
 'absensi':('Ambil absensi','Catat kehadiran siswa hari ini: hadir, izin, sakit, atau alpha. Perubahan disimpan setelah konfirmasi.'),
 'bell_a':('Notifikasi','Semua kabar kelas ada di sini. Titik merah berarti ada yang belum dibaca.'),
 'bell_p':('Notifikasi','Kabar kelas dan bukti bayar dari siswa masuk di sini. Titik merah berarti belum dibaca.'),
 'bell_s':('Notifikasi','Pengingat tugas, tagihan, dan pengumuman ada di sini. Titik merah berarti belum dibaca.'),
 'nav':('Bilah navigasi','Pindah cepat antar fitur: Beranda, Absensi, Kas, Jadwal, dan Tugas. Tab aktif berwarna kuning.'),
 'akun_a':('Tombol akun','Buka profil, pengaturan, dan Bantuan. Di sini kamu juga bisa mengulang tur ini kapan saja.'),
 'akun_p':('Tombol akun','Buka profil dan Bantuan. Kelola Pengguna dan pengaturan bot hanya ada untuk Admin.'),
 'akun_s':('Tombol akun','Buka profil, atur pesan WhatsApp, dan Bantuan. Kamu bisa mengulang tur ini kapan saja.'),
 'hadirku':('Kehadiranmu','Menampilkan jumlah hari hadir dan persentase kehadiranmu. Ketuk untuk melihat kalender dan riwayat.'),
 'tagihanku':('Tagihanku','Lihat tagihan kas milikmu, jatuh tempo, dan kirim bukti pembayaran supaya ditandai lunas.'),
}
ADM=[('Kartu "Absensi Hari Ini"','kehadiran','Bawah'),('Kartu "Kas Saat Ini"','kas','Bawah'),('Tombol "Tagihan" (Aksi Cepat)','tagihan','Bawah'),('Ikon lonceng (header)','bell_a','Bawah'),('Bilah navigasi bawah','nav','Atas'),('Tombol akun (header kanan)','akun_a','Bawah')]
PNG=[('Kartu "Absensi Hari Ini"','kehadiran','Bawah'),('Tombol "Ambil Absensi"','absensi','Bawah'),('Tombol "Tagihan" (Aksi Cepat)','tagihan_p','Bawah'),('Ikon lonceng (header)','bell_p','Bawah'),('Bilah navigasi bawah','nav','Atas'),('Tombol akun (header kanan)','akun_p','Bawah')]
SIS=[('Kartu "Kehadiranku"','hadirku','Bawah'),('Tombol "Tagihanku" (Aksi Cepat)','tagihanku','Bawah'),('Ikon lonceng (header)','bell_s','Bawah'),('Bilah navigasi bawah','nav','Atas'),('Tombol akun (header kanan)','akun_s','Bawah')]
def tbl(title,chipcls,chip,rows,span=2):
    body=''.join(f'<tr><td><span class="num">{i+1}</span></td><td>{el}</td><td><b>{T[k][0]}</b></td><td>{T[k][1]}</td><td><span class="pos">{pos}</span></td></tr>' for i,(el,k,pos) in enumerate(rows))
    return f'<div style="grid-column:span {span};padding:0 20px 0 0"><div class="rules"><h3 style="display:flex;gap:8px;align-items:center">{title} <span class="chip {chipcls}">{chip}</span></h3><table class="tt"><tr><th></th><th>Elemen yang disorot</th><th>Judul tooltip</th><th>Isi tooltip</th><th>Tooltip</th></tr>{body}</table></div></div>'
t_adm=tbl('Tur Admin','cy','6 langkah',ADM); t_png=tbl('Tur Pengurus','cb','6 langkah',PNG); t_sis=tbl('Tur Siswa','cg','5 langkah',SIS)
ped=rules(['<b>Judul</b> maksimal 3 kata; <b>isi</b> maksimal 2 kalimat (sekitar 120 karakter) dan memakai bahasa sehari-hari.','Urutan: kartu ringkasan, tombol aksi, notifikasi, navigasi, lalu tombol akun sebagai penutup.','Tooltip tampil <b>di bawah</b> elemen; bila elemen ada di bagian bawah layar (bilah navigasi), tooltip tampil <b>di atas</b>. Panah selalu menunjuk elemen.','Tombol: <b>Kembali</b> (tidak ada di langkah 1), <b>Lanjut</b>, <b>Selesai</b> (langkah terakhir), dan <b>Lewati</b>. Indikator titik menunjukkan posisi langkah.','Jika elemen tidak tersedia (role tidak punya fiturnya), langkah dilewati otomatis dan jumlah langkah menyesuaikan.','Semua teks berasal dari <b>satu berkas string per role</b> sehingga mudah diubah tanpa mengubah layar.'],span=2,title='Pedoman Penulisan & Posisi Tooltip')
def ph3(label,role,ring,t,step,total,**kw):
    k=T[t]; return phone(label,dash3(role,ring),nav=None,over=dim+(navb(True) if ring=='nav' else '')+tip(step,total,k[0],k[1],**kw))
a=ph3('Admin 2/6: Saldo Kas','admin','s2','kas',2,6,top=232,ax=250)
b=ph3('Admin 4/6: Lonceng','admin','bell','bell_a',4,6,top=84,ax=228)
c=ph3('Pengurus 2/6: Ambil Absensi','pengurus',('tile',0),'absensi',2,6,top=392,ax=56,chip='Admin & Pengurus')
d=ph3('Siswa 1/5: Kehadiranku','siswa','s1','hadirku',1,5,top=232,ax=60,first=True,chip='Siswa')
e=ph3('Siswa 3/5: Lonceng','siswa','bell','bell_s',3,5,top=84,ax=228)
f=ph3('Siswa 4/5: Bilah Bawah','siswa','nav','nav',4,5,bottom=70,arrow='dn',ax=150)
g=ph3('Siswa 5/5: Akun & Bantuan','siswa','av','akun_s',5,5,top=84,ax=262,last=True)
R=rules(['Jumlah langkah: <b>Admin 6</b>, <b>Pengurus 6</b>, <b>Siswa 5</b>. Kartu Saldo Kas hanya ada di tur Admin; tombol Ambil Absensi hanya di tur Admin dan Pengurus.','Teks di tabel adalah <b>sumber tunggal</b> isi tooltip untuk implementasi.','Lanjut di langkah terakhir = <b>Selesai</b>; status tur tersimpan per akun.','Layar lain (Absensi, Kas, Jadwal, Tugas) punya petunjuk pertama kali sendiri (32B).','Kondisi khusus (Lewati, tur terhenti, TalkBack, tema Liquid Glass) ada di 32D.'],span=1)
html=page('Pengenalan Aplikasi: Langkah Tur per Role','Tabel elemen, judul, dan isi tooltip untuk Admin, Pengurus, dan Siswa • layar langkah yang belum tergambar di 32',[t_adm,t_png,t_sis,ped,a,b,c,d,e,f,g,R])
render(html,'/home/claude/w32a/32a_langkah_tur_per_role.jpg','32a_langkah_tur_per_role')
