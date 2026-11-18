from tourkit import *
import lib, icons, re
icons.P['down']=['m6 9 6 6 6-6']
icons.P['gauge']=['m12 14 4-4','M3.34 19a10 10 0 1 1 17.32 0']
lib.CSS+=open('glass_extra.css').read()
lib.CSS+='''
.tt{width:100%;border-collapse:collapse;font-size:10.5px}
.tt th{text-align:left;font-size:9.5px;color:#6B6458;font-weight:600;padding:0 6px 5px;border-bottom:1.5px solid #1C1B1F}
.tt td{padding:5px 6px;border-bottom:1.2px dashed #EADFC2;vertical-align:top;line-height:1.4}
.tt tr:last-child td{border:0}.tt b{font-weight:700}.tt td.c,.tt th.c{text-align:center}
.big .tip h4{font-size:19px;margin:8px 0 5px}.big .tip p{font-size:14px;line-height:1.45}.big .tb{height:42px;font-size:14px;padding:0 16px}.big .chip{font-size:13px;padding:2px 11px}.big .t2{font-size:14px}.big .dots i{width:9px;height:9px}.big .dots i.on{width:22px;height:10px}
.gt .tip{border:0;border-radius:22px;box-shadow:0 8px 24px rgba(28,27,31,.3)}
.gt .tip .ar2{border:0}.gt .tip .ar2.up{top:-7px}.gt .tip .ar2.dn{bottom:-7px}
.gt .tb.p{background:#FFC928;border:0;border-radius:14px;box-shadow:inset 0 1px 0 rgba(255,255,255,.7)}
.gt .tb.s{background:#fff;border:1px solid #F1E8CC;border-radius:14px}
.gt .chip{border:0}.gt .dots i.on{border:0;background:#FFC928}
.snack{position:absolute;left:12px;right:12px;bottom:14px;z-index:30;background:#1C1B1F;color:#fff;border-radius:12px;padding:10px 12px;display:flex;align-items:center;gap:8px;font-size:10.5px}
.cap{position:absolute;left:10px;right:10px;bottom:12px;z-index:30;background:#111;color:#fff;border-radius:10px;padding:9px 11px;font-size:10.5px;line-height:1.5;border:1.5px solid #4a4a4a}
.cap small{display:block;color:#9aa0a6;font-size:8.5px;margin-bottom:2px;letter-spacing:.3px}
.hit{display:flex;align-items:center;justify-content:center;min-height:46px;min-width:46px;outline:1.5px dashed #D94452;outline-offset:0;border-radius:6px}
.nb{position:absolute;z-index:40;width:18px;height:18px;border-radius:50%;background:#2E9B5A;color:#fff;font-size:9.5px;font-weight:800;display:flex;align-items:center;justify-content:center;border:1.5px solid #fff}
'''
SP='position:relative;z-index:15;box-shadow:0 0 0 3px #FFC928,0 0 0 7px rgba(255,201,40,.4);'
SP2='position:relative;z-index:15;box-shadow:0 0 0 3px #FFC928,0 0 0 5px #1C1B1F,0 0 0 9px rgba(255,201,40,.45);'
GSP='position:relative;z-index:15;box-shadow:0 0 0 3px #FFC928,0 0 0 5px #fff,0 0 0 9px rgba(255,255,255,.35);'
# ---------------- Beranda Admin (neubrutalism) ----------------
def hdr2(ring=None,sp=SP):
    bell=f'<div style="position:relative;width:30px;height:30px;border-radius:50%;background:#fff;border:1.5px solid #EADFC2;display:flex;align-items:center;justify-content:center">[[bell]]<span style="position:absolute;right:-4px;top:-5px;background:#D94452;color:#fff;border-radius:8px;font-size:8px;font-weight:700;padding:0 4px;border:1.5px solid #fff">4</span></div>'
    av=f'<div style="width:30px;height:30px;border-radius:50%;background:#FFC928;border:2px solid #1C1B1F;display:flex;align-items:center;justify-content:center;{sp if ring=="av" else ""}">[[user]]</div>'
    return f'<div class="row sp" style="padding:4px 2px 10px"><div class="row"><div style="width:30px;height:30px;border-radius:9px;background:#FFC928;border:2px solid #1C1B1F;font-size:9px;font-weight:800;display:flex;align-items:center;justify-content:center">IXH</div><div><div class="t1" style="font-size:12.5px">IX-H Hub</div><div class="t3">Kelas IX-H • SMP Spensari</div></div></div><div class="row" style="gap:6px"><span class="chip cy" style="border:1.5px solid #1C1B1F">ADMIN</span>{bell}{av}</div></div>'
TL=[('check','Ambil Absensi'),('wallet','Catat Kas'),('mail','Tagihan'),('bot','Panel Bot'),('users','Kelola Pengguna'),('mega','Pengumuman')]
def dash(ring=None,sp=SP):
    st=lambda s,on:f'<div class="card" style="flex:1;margin:0;padding:9px 11px;{sp if on else ""}"><div class="t3">{s[0]}</div><div style="font-weight:800;font-size:15px">{s[1]}</div><div class="t3" style="color:#2E9B5A;font-weight:600">{s[2]}</div></div>'
    tiles=''.join(tile(i,t,ring==('tile',k)) .replace(SP,sp) for k,(i,t) in enumerate(TL))
    return (hdr2(ring,sp)+f'<div class="card" style="background:#FFC928;border:2.5px solid #1C1B1F;display:flex;justify-content:space-between;align-items:center"><div><div style="font-weight:800;font-size:15px">Halo, Ahmad!</div><div class="t2" style="color:#1C1B1F">Kelola seluruh data IX-H</div></div>{blob("smile",46)}</div>'
      +f'<div class="row" style="gap:8px;margin-bottom:8px;align-items:stretch">{st(("Absensi Hari Ini","28 / 32","87,5%"),ring=="s1")}{st(("Kas Saat Ini","Rp750.000","Terkonfirmasi"),ring=="s2")}</div>'
      +'<div class="al ab" style="margin-bottom:8px"><span>[[mega]]</span><span><b>TERSEMAT</b> Rapat kelas IX-H besok pagi</span></div>'
      +f'<div class="t1" style="margin:2px 0 6px">Aksi Cepat</div><div class="tg3">{tiles}</div>')
TX={'kas':('Saldo kas','Menampilkan saldo kas kelas yang sudah terkonfirmasi. Ketuk untuk melihat transaksi dan laporan.'),
    'tagihan':('Tombol Tagihan','Membuat dan mengirim tagihan kas ke WhatsApp tiap siswa, lalu memantau siapa yang sudah bayar.'),
    'nav':('Bilah navigasi','Pindah cepat antar fitur: Beranda, Absensi, Kas, Jadwal, dan Tugas. Tab aktif berwarna kuning.')}
def dlg_icon(ic): return f'<div class="ic" style="width:46px;height:46px;margin:0 auto 8px;background:#FFC928;border:2px solid #1C1B1F">[[{ic}]]</div>'
# ---------- 1 Lewati ----------
k=TX['tagihan']
s1=phone('1. Lewati Tur: Konfirmasi',dash(('tile',2)),over=dim+tip(3,6,k[0],k[1],top=392,ax=250,chip='Admin & Pengurus')+'<div class="dim" style="z-index:20"></div><div class="dlg" style="z-index:25">'+dlg_icon('help')+'<div style="font-weight:800;font-size:14.5px">Lewati tur?</div><div class="t2" style="margin:5px 0 12px;line-height:1.5">Kamu bisa mengulangnya kapan saja lewat Profil &gt; Bantuan &gt; Ulangi Tur.</div><div class="btn pri" style="margin-top:0">Lanjutkan Tur</div><div class="btn sec">Lewati Tur</div></div>')
# ---------- 2 Tur terhenti ----------
s2=phone('2. Tur Terhenti: Lanjutkan?',dash(),over=dim+'<div class="dlg">'+blob('smile',84)+'<div style="font-weight:800;font-size:14.5px;margin-top:2px">Lanjutkan tur?</div><div class="t2" style="margin:5px 0 12px;line-height:1.5">Kamu berhenti di <b>langkah 3 dari 6</b> (Tombol Tagihan). Mau lanjut dari sana?</div><div class="btn pri" style="margin-top:0">[[play]] Lanjutkan dari Langkah 3</div><div class="btn sec">Mulai dari Awal</div><div class="t2" style="font-weight:600;margin-top:10px">Nanti Saja</div></div>')
# ---------- 3 Offline ----------
k=TX['kas']
s3=phone('3. Offline Saat Tur',dash('s2'),over=dim+tip(2,6,k[0],k[1],top=232,ax=250)+'<div class="snack">[[wifioff]]<span>Kamu sedang offline. Tur tetap berjalan.</span></div>')
# ---------- 4 Font besar ----------
s4=phone('4. Ukuran Font 200%',dash('s2'),over=dim+'<div class="big">'+tip(2,6,k[0],k[1],top=232,ax=250)+'</div>')
# ---------- 5 TalkBack ----------
tb=tip(2,6,k[0],k[1],top=232,ax=250).replace('class="tip" style="','class="tip" style="outline:3px solid #2E9B5A;outline-offset:3px;',1)
tb=tb[:-6]+'<span class="nb" style="left:-8px;top:34px">1</span><span class="nb" style="right:104px;bottom:-14px">2</span><span class="nb" style="right:-8px;bottom:6px">3</span><span class="nb" style="right:-8px;top:-12px">4</span></div>'
s5=phone('5. TalkBack: Fokus & Urutan Baca',dash('s2'),over=dim+tb+'<div class="cap"><small>TEKS YANG DIBACAKAN (FOKUS 1)</small>Langkah 2 dari 6. Saldo kas. Menampilkan saldo kas kelas yang sudah terkonfirmasi. Ketuk untuk melihat transaksi dan laporan.</div>')
# ---------- 6 Target sentuh ----------
def tip_hit(step,total,title,desc,top):
    dots=''.join(f'<i class="{"on" if i+1==step else ""}"></i>' for i in range(total))
    return (f'<div class="tip" style="top:{top}px"><span class="ar2 up" style="left:250px"></span>'
      f'<div class="row sp"><span class="chip cy">{step} dari {total}</span><div class="hit" style="min-width:60px"><span class="t2" style="font-weight:600">Lewati</span></div></div>'
      f'<h4>{title}</h4><p>{desc}</p><div class="row sp" style="margin-top:6px"><div class="dots">{dots}</div><div class="row" style="gap:8px"><div class="hit" style="padding:0 6px"><div class="tb s">Kembali</div></div><div class="hit" style="padding:0 6px"><div class="tb p">Lanjut</div></div></div></div>'
      '<div class="t3" style="color:#D94452;font-weight:700;margin-top:6px">Area sentuh minimal 48 x 48 dp, jarak antar tombol minimal 8 dp</div></div>')
s6=phone('6. Target Sentuh 48 dp',dash('s2'),over=dim+tip_hit(2,6,k[0],k[1],232))
# ---------- 7 Sorotan aksesibel ----------
tp7=(f'<div class="tip" style="top:232px"><span class="ar2 up" style="left:250px"></span>'
  '<div class="row sp"><div><span class="chip" style="background:#FFEBA8;color:#1C1B1F">2 dari 6</span></div><span class="t2" style="font-weight:600">Lewati</span></div>'
  f'<h4>{k[0]}</h4><p>{k[1]}</p><div class="row sp" style="margin-top:9px"><div class="dots">'+''.join(f'<i class="{"on" if i==1 else ""}"></i>' for i in range(6))+'</div><div class="row" style="gap:6px"><div class="tb s">Kembali</div><div class="tb p">Lanjut</div></div></div></div>')
tp7b=tip(3,6,TX['tagihan'][0],TX['tagihan'][1],top=392,ax=250).replace('<span class="chip cy">3 dari 6</span>','<span class="chip" style="background:#FFEBA8;color:#1C1B1F">3 dari 6</span>').replace('<span class="chip cb" style="margin-left:6px">','<span class="chip" style="margin-left:6px;background:#DCE6FA;color:#1F4FA8">') if False else ''
s7=phone('7. Sorotan & Chip Aksesibel',dash('s2',SP2),over=dim+tp7)
# ---------------- Liquid Glass ----------
def blobg(kind='smile',s=70):
    gid=f'g{s}{kind}'
    return (f'<svg width="{s}" height="{s}" viewBox="0 0 100 100"><defs><radialGradient id="{gid}" cx="35%" cy="28%" r="80%"><stop offset="0" stop-color="#FFF0A8"/><stop offset=".55" stop-color="#FFCB2E"/><stop offset="1" stop-color="#FFAE00"/></radialGradient></defs>'
      '<ellipse cx="50" cy="91" rx="26" ry="4" fill="rgba(150,100,0,.22)"/><path d="M50 20q-2-10 6-14" fill="none" stroke="#1C1B1F" stroke-width="3" stroke-linecap="round"/>'
      f'<path d="M50 18C28 18 14 36 14 58c0 20 14 32 36 32s36-12 36-32c0-22-14-40-36-40z" fill="url(#{gid})" stroke="rgba(255,255,255,.95)" stroke-width="2.5"/>'
      '<ellipse cx="34" cy="36" rx="9" ry="5" fill="#fff" opacity=".75" transform="rotate(-30 34 36)"/><circle cx="29" cy="58" r="5" fill="#F4A6B0" opacity=".7"/><circle cx="71" cy="58" r="5" fill="#F4A6B0" opacity=".7"/>'
      '<g fill="#1C1B1F"><path d="M33 51q5-6 10 0M57 51q5-6 10 0" fill="none" stroke="#1C1B1F" stroke-width="3" stroke-linecap="round"/></g><path d="M42 59q8 10 16 0z" fill="#D94452" stroke="#1C1B1F" stroke-width="2.5" stroke-linejoin="round"/></svg>')
def ghdr():
    return (f'<div class="row sp" style="padding:0 0 6px"><div class="row" style="gap:8px"><div class="lg">IXH</div><div><div class="t1" style="font-size:12.5px">IX-H Hub</div><div class="t3" style="font-size:9px">Kelas IX-H • SMP Spensari</div></div></div>'
            f'<div class="row" style="gap:6px"><span class="rl">ADMIN</span><div class="circ">[[bell]]<i class="bd">4</i></div><div class="circ y">[[user]]</div></div></div>')
def gtile(ic,t,badge=''):
    return f'<div class="tile"><div class="ic">[[{ic}]]</div>{t}'+(f'<div><span class="mb">{badge}</span></div>' if badge else '')+'</div>'
def gberanda():
    return ('<div class="cmpt">'+ghdr()+f'<div class="hello"><div><div style="font-weight:700;font-size:14px">Halo, Ahmad!</div><div class="t3" style="color:#1C1B1F;font-size:10px">Kelola seluruh data IX-H</div></div>{blobg("smile",36)}</div>'
    +'<div class="card row sp" style="padding:8px 12px"><div class="row" style="gap:8px"><span>[[cal]]</span><b style="font-size:11px">Rabu, 4 November 2026</b></div><span class="chip cy">IX-H [[down]]</span></div>'
    +'<div class="row" style="gap:8px;align-items:stretch"><div class="card" style="flex:1;margin:0 0 6px;padding:7px 10px"><div class="t3">Absensi Hari Ini</div><div class="row" style="gap:6px;margin:2px 0 5px"><b style="font-size:16px">28 / 32</b><span style="font-size:9px;color:#2E9B5A;font-weight:700">87,5%</span></div><div class="pb"><i style="width:87.5%"></i></div></div>'
    +'<div class="card" style="flex:1;margin:0 0 6px;padding:7px 10px"><div class="t3">Kas Saat Ini</div><b style="font-size:16px;display:block;margin:2px 0">Rp750.000</b><div style="font-size:9px;color:#2E9B5A">Terkonfirmasi</div></div></div>'
    +'<div class="al ab" style="padding:8px 11px"><span>[[mega]]</span><span><b style="font-size:8.5px">TERSEMAT • Pengumuman</b><br><b style="font-size:11px">Rapat kelas IX-H besok pagi!</b></span><span style="margin-left:auto;align-self:center">[[chev]]</span></div>'
    +'<div class="card tint row sp" style="padding:8px 12px"><div class="row" style="gap:8px"><div class="ic" style="width:28px;height:28px">[[clip]]</div><div><div class="t3">Tugas Terdekat</div><b style="font-size:11px">PR Matematika • Hal. 45–47</b></div></div><span class="chip cy">1 hari lagi</span></div>'
    +'<div class="row sp" style="margin:2px 2px 5px"><b style="font-size:12px">Jadwal Hari Ini</b><span class="t3">Lihat Semua</span></div>'
    +'<div class="card row sp" style="padding:7px 12px"><span style="font-size:10.5px"><b>07.00–07.45</b>&nbsp;&nbsp;Matematika</span><span class="t3">R. 12</span></div>'
    +'<b style="font-size:12px;margin:0 2px;display:block">Aksi Cepat</b><div style="display:grid;grid-template-columns:repeat(3,1fr);gap:6px;margin-top:5px">'
    +gtile('checkc','Ambil Absensi')+gtile('wallet','Catat Kas')+gtile('mail','Tagihan')+gtile('bot','Panel Bot')+gtile('users','Kelola Pengguna','3 nomor bermasalah')+gtile('mega','Pengumuman')+'</div></div>')
def gphone(label,body,over='',ring_nav=False):
    its=[('home','Beranda'),('checkc','Absensi'),('wallet','Kas'),('caldays','Jadwal'),('clip','Tugas')]
    st=GSP if ring_nav else ''
    n=f'<div class="nav2" style="{st}">'+''.join(f'<div class="nv{" on" if t=="Beranda" else ""}">[[{i}]]<span>{t}</span></div>' for i,t in its)+'</div>'
    return f'<div class="col"><div class="lab">{label}</div><div class="ph glass"><div class="sb"><span>9:41</span><span>▂▄▆ ▭</span></div>{body}{n}{over}</div></div>'
kn=TX['nav']
s8=gphone('8. Tur di Tema Liquid Glass',gberanda(),over=dim+'<div class="gt">'+tip(5,6,kn[0],kn[1],bottom=84,arrow='dn',ax=150)+'</div>',ring_nav=True)
# ---------------- tabel ----------------
def cr_table():
    def L(h):
        h=h.lstrip('#');r,g,b=[int(h[i:i+2],16)/255 for i in (0,2,4)]
        f=lambda c:c/12.92 if c<=.03928 else ((c+.055)/1.055)**2.4
        return .2126*f(r)+.7152*f(g)+.0722*f(b)
    def cr(a,b):
        x,y=L(a),L(b);x,y=max(x,y),min(x,y);return (x+.05)/(y+.05)
    dimk='#8E8A84'
    P=lambda v:'<span class="chip cg">Lulus</span>' if v else '<span class="chip cr">Gagal</span>'
    rows=[('Judul tooltip, teks tombol','Ink #1C1B1F / putih','#1C1B1F','#FFFFFF',4.5,'Tetap'),
          ('Isi tooltip','#3D392F / putih','#3D392F','#FFFFFF',4.5,'Tetap'),
          ('"Lewati", teks bantu','Abu #6B6458 / putih','#6B6458','#FFFFFF',4.5,'Tetap'),
          ('Tombol Lanjut / Selesai','Ink / kuning #FFC928','#1C1B1F','#FFC928',4.5,'Tetap'),
          ('Chip langkah "2 dari 6"','#B88A00 / #FFEBA8','#B88A00','#FFEBA8',4.5,'Teks jadi ink #1C1B1F'),
          ('Chip peran "Admin & Pengurus"','#2F6FDB / #DCE6FA','#2F6FDB','#DCE6FA',4.5,'Teks jadi #1F4FA8'),
          ('Cincin sorotan (bukan teks)','Kuning #FFC928 / lapisan gelap','#FFC928',dimk,3,'Tambah garis ink 2 px di luar cincin'),
          ('Cincin + garis ink (usulan)','Ink / lapisan gelap','#1C1B1F',dimk,3,'Dipakai di neubrutalism'),
          ('Cincin + garis putih (usulan)','Putih / lapisan gelap','#FFFFFF',dimk,3,'Dipakai di Liquid Glass'),
          ('Teks abu di Liquid Glass','#5A5448 / putih','#5A5448','#FFFFFF',4.5,'Tetap')]
    body=''.join(f'<tr><td><b>{a}</b></td><td>{b}</td><td class="c"><b>{cr(c,d):.1f} : 1</b></td><td class="c">{P(cr(c,d)>=m)}</td><td>{act}</td></tr>' for a,b,c,d,m,act in rows)
    return f'<div style="grid-column:span 2;padding:0 20px 0 0"><div class="rules"><h3 style="display:flex;gap:8px;align-items:center">Kontras Warna Tur <span class="chip cy">teks 4,5 : 1 • bukan teks 3 : 1</span></h3><table class="tt"><tr><th>Komponen</th><th>Pasangan warna</th><th class="c">Rasio</th><th class="c">Hasil</th><th>Tindakan</th></tr>{body}</table><div class="t3" style="margin-top:6px">Lapisan gelap rgba(28,27,31,.5) di atas krem menghasilkan #8E8A84. Titik indikator tidak diwajibkan karena posisi langkah juga tertulis "2 dari 6".</div></div></div>'
TB=[('Teks tooltip','"Langkah {n} dari {N}. {judul}. {isi}"','1','Dibacakan sebagai satu blok saat tooltip muncul (live region sopan). Fokus awal ada di sini.'),
    ('Tombol Kembali','"Kembali ke langkah {n-1}. Tombol"','2','Tidak ada di langkah 1.'),
    ('Tombol Lanjut','"Lanjut ke langkah {n+1}. Tombol"','3','Langkah terakhir: "Selesai. Tombol. Menutup tur".'),
    ('Tombol Lewati','"Lewati tur. Tombol. Membuka konfirmasi"','4','Dibaca terakhir agar tidak menutup tur tanpa sengaja.'),
    ('Elemen yang disorot','Tidak bisa difokus selama tur','–','Isinya sudah dibacakan di tooltip; layar di belakang disembunyikan dari TalkBack.'),
    ('Indikator titik','Disembunyikan dari TalkBack','–','Posisi sudah dibacakan di "Langkah n dari N".'),
    ('Dialog Lewati / Lanjutkan','Fokus awal ke judul dialog','1','Tombol utama "Lanjutkan Tur" dibaca lebih dulu dari "Lewati Tur".'),
    ('Setelah tur selesai','"Tur selesai"','–','Fokus kembali ke judul layar; pengumuman dibacakan sekali.')]
def tb_table():
    body=''.join(f'<tr><td><b>{a}</b></td><td>{b}</td><td class="c">{c}</td><td>{d}</td></tr>' for a,b,c,d in TB)
    return f'<div style="grid-column:span 2;padding:0 20px 0 0"><div class="rules"><h3 style="display:flex;gap:8px;align-items:center">Label & Urutan TalkBack <span class="chip cb">8 komponen</span></h3><table class="tt"><tr><th>Komponen</th><th>Label / perilaku</th><th class="c">Urutan</th><th>Catatan</th></tr>{body}</table></div></div>'
A=rules(['<b>Lewati:</b> tombol Lewati di tooltip, atau tombol Kembali Android di langkah 1, membuka dialog konfirmasi. <b>Lewati Tur</b> menandai tur selesai (tersimpan per akun). <b>Lanjutkan Tur</b> kembali ke langkah terakhir. Tombol Kembali Android di langkah 2 ke atas sama dengan Kembali.','<b>Tur terhenti</b> (aplikasi ditutup, pindah aplikasi, atau ada telepon masuk): posisi langkah tersimpan. Saat dibuka lagi muncul tawaran <b>Lanjutkan dari langkah n</b>, <b>Mulai dari Awal</b>, atau <b>Nanti Saja</b>. Nanti Saja menutup tawaran; tur tetap bisa diulang dari Bantuan.','<b>Offline:</b> teks tur ada di perangkat, jadi tur tetap berjalan. Snackbar kecil tidak memblokir; status tur disinkronkan saat kembali online.','<b>Font besar</b> (sampai 200%): tooltip menyesuaikan tinggi, isi bisa digulir, tombol tetap terlihat. Bila tidak muat di bawah elemen, tooltip pindah ke atasnya.','<b>Target sentuh</b> minimal 48 x 48 dp dan jarak antar tombol minimal 8 dp; tampilan tombol boleh lebih kecil, area sentuhnya yang diperluas.','<b>Kontras:</b> teks minimal 4,5 : 1 dan elemen non-teks minimal 3 : 1. Tiga pasangan yang gagal (chip langkah, chip peran biru, cincin kuning) diperbaiki seperti di tabel dan digambar di layar 7. Layar tur di 32 dan 32A belum diubah; perbaikannya berlaku saat implementasi.','<b>Liquid Glass:</b> tooltip putih solid tanpa blur (blur tidak bertambah, tetap maksimal 1 permanen + 1 sementara), cincin kuning dengan garis putih. Di perangkat ringan tampilannya sama persis karena tooltip memang tanpa blur.','<b>Kurangi/hapus animasi</b> (setelan Android) dan perangkat RAM 2 GB atau kurang: pindah langkah tanpa animasi. Mode gelap belum digambar (catatan dari 31).'],span=2,title='Aturan Bisnis')
CK=rules(['Aktifkan <b>TalkBack</b>: fokus awal di tooltip, urutan 1 sampai 4 sesuai tabel, tidak ada fokus yang bocor ke layar belakang.','Ukuran font <b>200%</b> dan ukuran tampilan terbesar: judul, isi, dan tombol tidak terpotong.','Jalankan <b>Accessibility Scanner</b> di setiap langkah tur: target sentuh dan kontras.','Setel <b>Hapus animasi</b>: pindah langkah tanpa gerak.','Uji <b>offline</b> (mode pesawat) dari langkah 1 sampai Selesai.','Matikan paksa aplikasi di langkah 3, buka lagi: tawaran Lanjutkan muncul dengan langkah yang benar.','Ulangi tiga uji pertama di tema <b>Liquid Glass</b> dan di perangkat RAM 2 GB atau kurang.','Uji tiap role (Admin, Pengurus, Siswa): jumlah langkah dan teks sesuai 32A.'],span=2,title='Daftar Uji Aksesibilitas')
html=page('Pengenalan Aplikasi: Kondisi Khusus & Aksesibilitas','Lewati • tur terhenti • offline • font besar • TalkBack • target sentuh • kontras • Liquid Glass',[tb_table(),cr_table(),s1,s2,s3,s4,s5,s6,s7,s8,A,CK])
render(html,'/home/claude/w32d/32d_kondisi_khusus_aksesibilitas.jpg','32d_kondisi_khusus_aksesibilitas')
