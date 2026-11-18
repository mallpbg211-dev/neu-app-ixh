from tourkit import *
import lib
lib.CSS+='''
.tt{width:100%;border-collapse:collapse;font-size:10.5px}
.tt th{text-align:left;font-size:9.5px;color:#6B6458;font-weight:600;padding:0 6px 5px;border-bottom:1.5px solid #1C1B1F}
.tt td{padding:6px;border-bottom:1.2px dashed #EADFC2;vertical-align:top;line-height:1.4}
.tt tr:last-child td{border:0}.tt b{font-weight:700}
.pos{display:inline-block;border-radius:10px;padding:0 7px;font-size:9px;font-weight:700;background:#EFE8D6;color:#3d392f;white-space:nowrap}
.sr{display:flex;gap:4px}.sr i{width:22px;height:22px;border-radius:50%;border:1.2px solid #EADFC2;font-style:normal;font-size:9px;font-weight:700;color:#6B6458;display:flex;align-items:center;justify-content:center}
.dayc{flex:1;text-align:center;font-size:10.5px;padding:7px 0;border-radius:10px;background:#fff;border:1.2px solid #EADFC2}
.dayc.on{background:#FFC928;border:2px solid #1C1B1F;font-weight:700}
.les{display:flex;gap:10px;align-items:center;background:#fff;border:1.5px solid #EADFC2;border-radius:14px;padding:8px 10px;margin-bottom:6px;border-left:4px solid #FFC928}
.fab{position:absolute;right:14px;bottom:66px;width:44px;height:44px;border-radius:50%;background:#FFC928;border:2px solid #1C1B1F;box-shadow:0 3px 0 #1C1B1F;display:flex;align-items:center;justify-content:center;z-index:5}
.fix{position:absolute;left:12px;right:12px}
'''
SC={'H':'#2E9B5A','I':'#D9A400','S':'#2F6FDB','A':'#D94452'}
def srow(i,n,st,ring=False):
    c=''.join(f'<i style="background:{SC[k]};border-color:{SC[k]};color:#fff">{k}</i>' if k==st else f'<i>{k}</i>' for k in 'HISA')
    return f'<div class="card" style="padding:6px 10px;margin-bottom:5px"><div class="row sp"><div class="row"><span class="t2" style="width:12px">{i}</span><div class="t1">{n}</div></div><div class="sr" style="{SP+"background:#fff;border-radius:14px;padding:2px;" if ring else ""}">{c}</div></div></div>'
# ---------- Absensi ----------
def absensi(ring=None):
    rows=[('Ahmad Ramadhan','H'),('Aisyah Putri','H'),('Bima Sakti','I'),('Citra Lestari','S'),('Daffa Prakarsa','A'),('Eka Wijaya','H'),('Fajar Nugroho','H'),('Gita Nuraini','H')]
    body=bar('Daftar Absensi','[[cal]]')+'<div class="card" style="background:#FFEBA8;border:1.5px solid #1C1B1F;padding:7px 10px"><div class="row sp"><div class="row t1">[[users]] 32 siswa • default Hadir</div><span class="chip cy" style="border:1.2px solid #1C1B1F">Rab, 4 Nov</span></div></div>'
    body+=''.join(srow(i+1,n,s,ring=='row' and i==0) for i,(n,s) in enumerate(rows))
    body+=f'<div class="fix" style="bottom:14px"><div class="t3" style="margin-bottom:2px">Ketuk huruf untuk ubah</div><div class="btn pri" style="margin:0;{SP if ring=="save" else ""}">[[check]] Simpan Absensi (32)</div></div>'
    return body
# ---------- Kas ----------
def kas(ring=None):
    tab=f'<div class="tabs"><div class="on">Transaksi</div><div style="{SP+"background:#fff;border:2px solid #1C1B1F;" if ring=="tab" else ""}">Tagihan <span class="chip cr">8</span></div></div>'
    tr=lambda n,tg,w,a,up:f'<div class="card" style="padding:7px 10px;margin-bottom:5px"><div class="row sp"><div class="row"><div class="ic" style="width:28px;height:28px;background:{"#DDF1E5" if up else "#FADCDF"};color:{"#2E9B5A" if up else "#D94452"}">{"+" if up else "−"}</div><div><div class="t1">{n} {tg}</div><div class="t3">{w}</div></div></div><span style="font-weight:700;font-size:11px;color:{"#2E9B5A" if up else "#D94452"}">{a}</span></div></div>'
    b=(hdr('ADMIN')+'<div style="font-weight:800;font-size:16px;margin:0 2px 8px">Kas</div>'
      +'<div class="card" style="background:#FFC928;border:2.5px solid #1C1B1F"><div class="row sp"><div><div class="t2" style="color:#1C1B1F">Saldo Kas Terkonfirmasi</div><div style="font-weight:800;font-size:20px">Rp750.000</div><div class="t3" style="color:#1C1B1F">Per Rabu, 4 November 2026</div></div><span style="font-size:26px">[[wallet]]</span></div></div>'
      +tab
      +'<div class="row" style="gap:8px;margin-bottom:8px"><div class="card" style="flex:1;margin:0;padding:7px 10px;background:#DDF1E5;border-color:#2E9B5A"><div class="t3">Pemasukan Nov</div><div style="font-weight:700;font-size:12px;color:#2E9B5A">Rp144.000</div></div><div class="card" style="flex:1;margin:0;padding:7px 10px;background:#FADCDF;border-color:#D94452"><div class="t3">Pengeluaran Nov</div><div style="font-weight:700;font-size:12px;color:#D94452">Rp40.000</div></div></div>'
      +'<div class="row sp" style="margin-bottom:5px"><div class="t1">Transaksi Terbaru</div><span class="t3">Lihat Semua</span></div>'
      +tr('Hana Safitri','<span class="chip cb">Dari tagihan</span>','Rab, 4 Nov • 10.05','+Rp6.000',True)
      +tr('Bima Sakti','<span class="chip cb">Dari tagihan</span>','Rab, 4 Nov • 09.12','+Rp6.000',True)
      +tr('Pembelian Spidol','','Sel, 3 Nov • 14.10','−Rp25.000',False)
      +f'<div class="fix" style="bottom:62px"><div class="btn pri" style="margin:0;{SP if ring=="add" else ""}">[[plus]] Tambah Transaksi</div></div>')
    return b
# ---------- Jadwal ----------
def jadwal(ring=None):
    days=''.join(f'<div class="dayc {"on" if d=="Rab" else ""}">{d}</div>' for d in ['Sen','Sel','Rab','Kam','Jum','Sab'])
    ls=lambda t,n,g,c='#FFC928',bg='#fff',r=False:f'<div class="les" style="border-left-color:{c};background:{bg};{SP if r else ""}"><div style="width:38px"><div class="t1">{t[0]}</div><div class="t3">{t[1]}</div></div><div><div class="t1">{n}</div><div class="t3">{g}</div></div></div>'
    return (hdr('ADMIN')+'<div style="font-weight:800;font-size:16px;margin:0 2px 8px">Jadwal</div>'
      +f'<div class="row" style="gap:5px;margin-bottom:8px;{SP+"border-radius:14px;padding:4px;background:#FFF9E8;" if ring=="day" else ""}">{days}</div>'
      +'<div class="t2" style="margin-bottom:6px">Rabu, 4 November 2026</div>'
      +ls(('07.00','07.45'),'Matematika','Bu Sari Dewi • R. 12',r=ring=='les')
      +ls(('07.45','08.30'),'Bahasa Indonesia','Pak Budi Santoso • R. 12')
      +ls(('08.30','09.15'),'IPA','Bu Rini Handayani • R. 12')
      +ls(('09.15','09.30'),'Istirahat','','#2E9B5A','#DDF1E5')
      +ls(('09.30','10.15'),'PPKN','Pak Andi Pratama • R. 12')
      +'<div class="al ab" style="margin-top:2px"><span>[[info]]</span><span>Jadwal bisa dilihat saat offline</span></div>')
# ---------- Tugas ----------
def tugas(ring=None,info=False):
    st=lambda n,l,c:f'<div class="card" style="flex:1;margin:0;padding:7px 0;text-align:center"><div style="font-weight:800;font-size:15px;color:{c}">{n}</div><div class="t3">{l}</div></div>'
    def tk(t,m,d,chip,cc,col,r=False):
        return f'<div class="card" style="border-left:4px solid {col};padding:8px 10px;margin-bottom:6px;{SP if r else ""}"><div class="row sp"><div class="t1">{t}</div><span class="chip {cc}">{chip}</span></div><div class="t3">{m}</div><div class="t3" style="color:#1C1B1F;font-weight:600">{d}</div></div>'
    ib=f'<span style="{SP if info else ""}border-radius:50%;width:26px;height:26px;display:flex;align-items:center;justify-content:center;background:#fff;border:1.5px solid #EADFC2">[[info]]</span>'
    return (hdr('ADMIN')+f'<div class="row sp" style="margin:0 2px 8px"><div style="font-weight:800;font-size:16px">Tugas</div>{ib}</div>'
      +'<div class="row" style="gap:8px;margin-bottom:8px">'+st(4,'Total','#1C1B1F')+st(1,'Selesai','#2E9B5A')+st(3,'Mendatang','#D9A400')+'</div>'
      +'<div class="row" style="gap:8px;margin-bottom:8px"><div class="inp" style="flex:1;padding:6px 10px;font-size:10.5px">Semua Mapel</div><div class="inp" style="flex:1;padding:6px 10px;font-size:10.5px">Semua Status</div></div>'
      +tk('PR Matematika','Matematika • Bu Sari Dewi','Deadline Kam, 5 Nov','1 hari lagi','cr',R,ring=='card')
      +tk('Laporan Praktikum','IPA • Bu Rini Handayani','Deadline Sab, 7 Nov','3 hari lagi','cy','#D9A400')
      +tk('Ringkasan Materi','PPKN • Pak Andi Pratama','Deadline Sen, 9 Nov','5 hari lagi','cb',B)
      +tk('Tugas Esai','B. Indonesia • Pak Budi Santoso','Deadline Sel, 3 Nov','Selesai','cg',G)
      +f'<div class="fab" style="{"z-index:15;box-shadow:0 0 0 3px #FFC928,0 0 0 7px rgba(255,201,40,.4);" if ring=="fab" else ""}">[[plus]]</div>')
# ---------- Kelola Tagihan (info) ----------
def kelola(ring=False):
    ib=f'<span style="{SP}border-radius:50%;width:26px;height:26px;display:flex;align-items:center;justify-content:center;background:#fff">[[info]]</span>'
    rw=lambda n,c,t,a:f'<div class="card" style="padding:7px 10px;margin-bottom:5px"><div class="row sp"><div class="row"><div class="ic" style="width:26px;height:26px;font-size:9px;font-weight:700;background:#FFC928;border:1.5px solid #1C1B1F">{a}</div><div class="t1">{n}</div></div><span class="chip {c}">{t}</span></div></div>'
    return (bar('Kelola Tagihan',ib)
      +'<div class="card" style="background:#FFC928;border:2.5px solid #1C1B1F"><div class="row sp"><div class="t1">Kas Minggu ke-1 November</div><span class="chip" style="background:#1C1B1F;color:#fff">Aktif</span></div><div class="t3" style="color:#1C1B1F">Rp6.000/siswa • Jatuh tempo Jum, 6 Nov</div></div>'
      +'<div class="row" style="gap:8px;margin-bottom:8px"><div class="card" style="flex:1;margin:0;padding:7px 0;text-align:center"><div style="font-weight:800;font-size:15px;color:#2E9B5A">24</div><div class="t3">Lunas</div></div><div class="card" style="flex:1;margin:0;padding:7px 0;text-align:center"><div style="font-weight:800;font-size:15px;color:#D9A400">6</div><div class="t3">Belum</div></div><div class="card" style="flex:1;margin:0;padding:7px 0;text-align:center"><div style="font-weight:800;font-size:15px;color:#D94452">2</div><div class="t3">Tunggakan</div></div></div>'
      +rw('Hana Safitri','cg','Lunas','HS')+rw('Bima Sakti','cg','Lunas','BS')+rw('Citra Lestari','cy','Belum','CL')+rw('Daffa Prakarsa','cr','Tunggakan','DP'))
# ---------- teks tooltip (satu sumber untuk tabel dan layar) ----------
T={
 'abs1':('Ubah status','Ketuk huruf H, I, S, atau A di tiap baris untuk mengubah status. Semua siswa awalnya Hadir.'),
 'abs2':('Simpan absensi','Perubahan baru tersimpan setelah kamu ketuk Simpan dan konfirmasi. Absensi hari ini masih bisa diubah.'),
 'kas1':('Tab Tagihan','Lihat siapa yang sudah bayar. Angka merah adalah jumlah siswa yang belum bayar dan menunggak.'),
 'kas2':('Tambah transaksi','Catat pemasukan atau pengeluaran kas kelas. Pengeluaran wajib disertai foto bukti.'),
 'jad1':('Pilih hari','Ketuk nama hari untuk melihat pelajaran hari itu. Jadwal tetap bisa dibuka tanpa internet.'),
 'jad2':('Detail pelajaran','Ketuk pelajaran untuk melihat guru dan ruangan, lalu atur pengingat malam harinya.'),
 'tug1':('Warna urgensi','Garis warna menunjukkan sisa waktu: merah 1 hari atau kurang, kuning 2 sampai 3 hari, biru 4 hari atau lebih.'),
 'tug2':('Tombol tambah','Buat tugas baru lengkap dengan deadline dan pengingat. Tombol ini hanya ada untuk Admin dan Pengurus.'),
}
H=[('Absensi 1/2','Baris status siswa (huruf H I S A)','abs1','Bawah','Admin & Pengurus'),('Absensi 2/2','Tombol "Simpan Absensi"','abs2','Atas','Admin & Pengurus'),
   ('Kas 1/2','Tab "Tagihan" (badge merah)','kas1','Bawah','Admin & Pengurus'),('Kas 2/2','Tombol "Tambah Transaksi"','kas2','Atas','Admin & Pengurus'),
   ('Jadwal 1/2','Baris pilihan hari (Sen sampai Sab)','jad1','Bawah','Semua role'),('Jadwal 2/2','Kartu pelajaran pertama','jad2','Bawah','Semua role'),
   ('Tugas 1/2','Kartu tugas pertama (garis warna)','tug1','Bawah','Semua role'),('Tugas 2/2','Tombol tambah (+) mengambang','tug2','Atas','Admin & Pengurus')]
def t1():
    body=''.join(f'<tr><td><span class="num">{i+1}</span></td><td><b>{l}</b></td><td>{el}</td><td><b>{T[k][0]}</b></td><td>{T[k][1]}</td><td>{who}</td><td><span class="pos">{pos}</span></td></tr>' for i,(l,el,k,pos,who) in enumerate(H))
    return f'<div style="grid-column:span 2;padding:0 20px 0 0"><div class="rules"><h3 style="display:flex;gap:8px;align-items:center">Petunjuk Pertama Kali per Layar <span class="chip cy">8 petunjuk</span></h3><table class="tt"><tr><th></th><th>Layar</th><th>Elemen yang disorot</th><th>Judul tooltip</th><th>Isi tooltip</th><th>Peran</th><th>Tooltip</th></tr>{body}</table></div></div>'
I=[('Daftar Absensi','02','Bilah atas, kanan','Arti status Hadir, Izin, Sakit, Alpha (contoh di 32 layar 10)','Admin & Pengurus'),
   ('Kelola Tagihan','19','Bilah atas, kanan','Arti status Lunas, Belum, Tunggakan dan kapan sebuah tagihan menunggak','Admin & Pengurus'),
   ('Kalender Akademik','04','Bilah atas, kanan','Arti titik warna: Hari ini (kuning), Tugas (biru), Ujian (merah)','Semua role'),
   ('Daftar Tugas','05','Di samping judul "Tugas"','Arti warna urgensi: merah, kuning, biru, hijau','Semua role'),
   ('Kehadiranku','18','Bilah atas, kanan','Arti persentase kehadiran dan batas minimal kelas (90%)','Siswa'),
   ('Panel Bot','06','Di samping kartu batas kirim','Batas kirim 60 pesan per jam dan jeda acak 8-20 detik antar pesan','Admin & Pengurus')]
def t2():
    body=''.join(f'<tr><td><span class="num">{i+1}</span></td><td><b>{a}</b></td><td>{s}</td><td>{p}</td><td>{d}</td><td>{w}</td></tr>' for i,(a,s,p,d,w) in enumerate(I))
    return f'<div style="grid-column:span 2;padding:0 20px 0 0"><div class="rules"><h3 style="display:flex;gap:8px;align-items:center">Daftar Layar dengan Ikon Info <span class="chip cb">6 layar</span></h3><table class="tt"><tr><th></th><th>Layar</th><th>Storyboard</th><th>Posisi ikon</th><th>Isi penjelasan singkat</th><th>Peran</th></tr>{body}</table></div></div>'
def ph(label,body,nav,k,total,step,**kw):
    t=T[k]; return phone(label,body,nav=nav,over=dim+tip(step,total,t[0],t[1],**kw))
p1=ph('Absensi 1/2: Ubah Status',absensi('row'),None,'abs1',2,1,top=186,ax=268,first=True,chip='Admin & Pengurus')
p2=ph('Absensi 2/2: Simpan',absensi('save'),None,'abs2',2,2,bottom=84,arrow='dn',ax=150,last=True)
p3=ph('Kas 1/2: Tab Tagihan',kas('tab'),'Kas','kas1',2,1,top=262,ax=230,first=True,chip='Admin & Pengurus')
p4=ph('Kas 2/2: Tambah Transaksi',kas('add'),'Kas','kas2',2,2,bottom=122,arrow='dn',ax=150,last=True)
p5=ph('Jadwal 1/2: Pilih Hari',jadwal('day'),'Jadwal','jad1',2,1,top=170,ax=150,first=True,chip='Semua role')
p6=ph('Jadwal 2/2: Detail Pelajaran',jadwal('les'),'Jadwal','jad2',2,2,top=270,ax=60,last=True)
p7=ph('Tugas 1/2: Warna Urgensi',tugas('card'),'Tugas','tug1',2,1,top=360,ax=60,first=True,chip='Semua role')
p8=ph('Tugas 2/2: Tombol Tambah',tugas('fab'),'Tugas','tug2',2,2,bottom=124,arrow='dn',ax=287,last=True)
sh=lambda c,t,d:f'<div class="lrow"><span class="chip {c}" style="width:62px;text-align:center">{t}</span><div class="t2" style="color:#1C1B1F;line-height:1.4">{d}</div></div>'
def sheet(title,sub,rows):
    return dim+'<div class="sheet"><div style="width:40px;height:4px;border-radius:2px;background:#EADFC2;margin:0 auto 8px"></div><div style="font-weight:800;font-size:14px">'+title+'</div><div class="t3" style="margin-bottom:4px">'+sub+'</div>'+''.join(sh(*r) for r in rows)+'<div class="btn pri" style="margin-top:8px">Mengerti</div></div>'
p9=phone('Ikon Info: Status Tagihan',kelola(),over=sheet('Arti Status Tagihan','Berlaku untuk tagihan kas per siswa',[('cg','Lunas','Sudah dibayar dan dikonfirmasi pengurus'),('cy','Belum','Belum bayar, tetapi belum lewat jatuh tempo'),('cr','Tunggakan','Belum bayar dan sudah lewat jatuh tempo (Jumat)')]))
p10=phone('Ikon Info: Warna Tugas',tugas(info=True),'Tugas',over=sheet('Arti Warna Tugas','Warna garis di kiri kartu tugas',[('cr','Merah','Deadline 1 hari lagi atau kurang'),('cy','Kuning','Deadline 2 sampai 3 hari lagi'),('cb','Biru','Deadline 4 hari lagi atau lebih'),('cg','Hijau','Tugas sudah selesai')]))
Rr=rules(['Petunjuk muncul <b>sekali</b> saat sebuah layar pertama kali dibuka, dan hanya setelah tur 32 selesai atau dilewati. Selama tur belum selesai, petunjuk ini tidak tampil.','Bentuknya sama dengan tooltip tur: lapisan gelap datar, cincin kuning, judul maksimal 3 kata, isi maksimal 2 kalimat, tombol Kembali, Lanjut, Selesai, dan Lewati.','Jumlah petunjuk: <b>Absensi 2</b> dan <b>Kas 2</b> (Admin & Pengurus), <b>Jadwal 2</b> (semua role), <b>Tugas 2</b> untuk Admin & Pengurus dan <b>1</b> untuk Siswa (tanpa tombol tambah).','Siswa tidak melihat petunjuk Absensi dan Kas; Kehadiranku dan Tagihanku sudah dijelaskan di tur Siswa (32A).','Status "sudah dilihat" tersimpan <b>per akun dan per layar</b>. Ulangi Tur di Bantuan juga mengulang petunjuk ini.','Ikon <b>info</b> tetap ada di layar kapan pun dan tidak sekali saja. Ketuk untuk membuka bottom sheet; tombol <b>Mengerti</b> menutupnya.','Teks petunjuk dan ikon info berasal dari <b>satu berkas string per role</b>, sama seperti tur.','Ringan: tanpa blur; perangkat RAM 2 GB atau kurang berpindah petunjuk tanpa animasi.'],span=2)
html=page('Pengenalan Aplikasi: Petunjuk Pertama Kali','Petunjuk sekali tampil di Absensi, Kas, Jadwal, dan Tugas • daftar layar yang punya ikon info',[t1(),t2(),p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,Rr])
render(html,'/home/claude/w32b/32b_petunjuk_pertama_kali.jpg','32b_petunjuk_pertama_kali')
