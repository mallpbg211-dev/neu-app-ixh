from lib import *
import lib, icons
P=icons.P
P['home']=['M15 21v-8a1 1 0 0 0-1-1h-4a1 1 0 0 0-1 1v8','M3 10a2 2 0 0 1 .709-1.528l7-5.999a2 2 0 0 1 2.582 0l7 5.999A2 2 0 0 1 21 10v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z']
P['clip']=['<rect x="8" y="2" width="8" height="4" rx="1"/>','M16 4h2a2 2 0 0 1 2 2v14a2 2 0 0 1-2 2H6a2 2 0 0 1-2-2V6a2 2 0 0 1 2-2h2','M12 11h4','M12 16h4']
P['play']=['<polygon points="6 3 20 12 6 21 6 3"/>']
P['help']=['<circle cx="12" cy="12" r="10"/>','M9.09 9a3 3 0 0 1 5.83 1c0 2-3 3-3 3','M12 17h.01']
P['target']=['<circle cx="12" cy="12" r="10"/>','<circle cx="12" cy="12" r="6"/>','<circle cx="12" cy="12" r="2"/>']
lib.CSS+='''
.tip{position:absolute;left:14px;right:14px;background:#fff;border:2px solid #1C1B1F;border-radius:16px;padding:11px 12px 10px;z-index:20;box-shadow:0 4px 0 #1C1B1F}
.tip .ar2{position:absolute;width:14px;height:14px;background:#fff;border:2px solid #1C1B1F;transform:rotate(45deg)}
.tip .ar2.up{top:-9px;border-right:0;border-bottom:0}.tip .ar2.dn{bottom:-9px;border-left:0;border-top:0}
.tip h4{font-size:13px;margin:6px 0 3px}.tip p{font-size:10.5px;line-height:1.45;color:#3d392f}
.dots{display:flex;gap:4px;align-items:center}.dots i{width:6px;height:6px;border-radius:3px;background:#EADFC2;display:block}.dots i.on{width:16px;background:#FFC928;border:1px solid #1C1B1F;height:7px}
.tb{display:flex;align-items:center;justify-content:center;height:30px;padding:0 14px;border-radius:10px;font-size:11px;font-weight:700}
.tb.p{background:#FFC928;border:2px solid #1C1B1F;box-shadow:0 2px 0 #1C1B1F}.tb.s{background:#fff;border:1.2px solid #1C1B1F}
.tile{background:#fff;border:1.5px solid #EADFC2;border-radius:14px;height:82px;display:flex;flex-direction:column;align-items:center;justify-content:center;gap:6px;font-size:10px;font-weight:600;text-align:center}
.tile .ic{width:32px;height:32px}
.tg3{display:grid;grid-template-columns:repeat(3,1fr);gap:8px}
.lrow{display:flex;gap:10px;align-items:center;padding:9px 2px;border-bottom:1.2px dashed #EADFC2}.lrow:last-child{border:0}
.num{width:20px;height:20px;border-radius:50%;background:#FFC928;border:1.5px solid #1C1B1F;font-size:10px;font-weight:800;display:flex;align-items:center;justify-content:center;flex:none}
'''
SP='position:relative;z-index:15;box-shadow:0 0 0 3px #FFC928,0 0 0 7px rgba(255,201,40,.4);'
def hdr(role='ADMIN',ring=None):
    bell=f'<div style="position:relative;width:30px;height:30px;border-radius:50%;background:#fff;border:1.5px solid #EADFC2;display:flex;align-items:center;justify-content:center">[[bell]]<span style="position:absolute;right:-4px;top:-5px;background:#D94452;color:#fff;border-radius:8px;font-size:8px;font-weight:700;padding:0 4px;border:1.5px solid #fff">4</span></div>'
    av=f'<div style="width:30px;height:30px;border-radius:50%;background:#FFC928;border:2px solid #1C1B1F;display:flex;align-items:center;justify-content:center;{SP if ring=="av" else ""}">[[user]]</div>'
    return f'<div class="row sp" style="padding:4px 2px 10px"><div class="row"><div style="width:30px;height:30px;border-radius:9px;background:#FFC928;border:2px solid #1C1B1F;font-size:9px;font-weight:800;display:flex;align-items:center;justify-content:center">IXH</div><div><div class="t1" style="font-size:12.5px">IX-H Hub</div><div class="t3">Kelas IX-H • SMP Spensari</div></div></div><div class="row" style="gap:6px"><span class="chip cy" style="border:1.5px solid #1C1B1F">{role}</span>{bell}{av}</div></div>'
def tile(ic,t,ring=False): return f'<div class="tile" style="{SP if ring else ""}"><div class="ic">[[{ic}]]</div>{t}</div>'
def dash(role='admin',ring=None):
    if role=='admin':
        hero=('Halo, Ahmad!','Kelola seluruh data IX-H');s1=('Absensi Hari Ini','28 / 32','87,5%');s2=('Kas Saat Ini','Rp750.000','Terkonfirmasi')
        tl=[('check','Ambil Absensi'),('wallet','Catat Kas'),('mail','Tagihan'),('bot','Panel Bot'),('users','Kelola Pengguna'),('mega','Pengumuman')]
    else:
        hero=('Halo, Citra!','Cek jadwal dan tugas hari ini');s1=('Kehadiranku','27 / 29','93%');s2=('Tagihan Kas','Rp6.000','Belum bayar')
        tl=[('chart','Kehadiranku'),('wallet','Tagihanku'),('mega','Pengumuman'),('cal','Jadwal'),('clip','Tugas Saya'),('user','Profil')]
    st=lambda s,r:f'<div class="card" style="flex:1;margin:0;padding:9px 11px;{SP if r else ""}"><div class="t3">{s[0]}</div><div style="font-weight:800;font-size:15px">{s[1]}</div><div class="t3" style="color:#2E9B5A;font-weight:600">{s[2]}</div></div>'
    tiles=''.join(tile(i,t,ring==('tile',k)) for k,(i,t) in enumerate(tl))
    return (hdr('ADMIN' if role=='admin' else 'SISWA',ring)
      +f'<div class="card" style="background:#FFC928;border:2.5px solid #1C1B1F;display:flex;justify-content:space-between;align-items:center"><div><div style="font-weight:800;font-size:15px">{hero[0]}</div><div class="t2" style="color:#1C1B1F">{hero[1]}</div></div>{blob("smile",46)}</div>'
      +f'<div class="row" style="gap:8px;margin-bottom:8px;align-items:stretch">{st(s1,ring=="stat")}{st(s2,False)}</div>'
      +'<div class="al ab" style="margin-bottom:8px"><span>[[mega]]</span><span><b>TERSEMAT</b> Rapat kelas IX-H besok pagi</span></div>'
      +f'<div class="t1" style="margin:2px 0 6px">Aksi Cepat</div><div class="tg3">{tiles}</div>')
def navb(ring=False):
    items=['Beranda','Absensi','Kas','Jadwal','Tugas']
    return f'<div class="nav" style="z-index:15;{"box-shadow:0 0 0 3px #FFC928 inset;" if ring else ""}">'+''.join(f'<div>{"<b>"+i+"</b>" if i=="Beranda" else i}</div>' for i in items)+'</div>'
def tip(step,total,title,desc,top=None,bottom=None,arrow='up',ax=40,chip='',first=False,last=False,skip=True):
    pos=f'top:{top}px;' if top is not None else f'bottom:{bottom}px;'
    dots=''.join(f'<i class="{"on" if i+1==step else ""}"></i>' for i in range(total))
    ch=f'<span class="chip cb" style="margin-left:6px">{chip}</span>' if chip else ''
    back='' if first else '<div class="tb s">Kembali</div>'
    return (f'<div class="tip" style="{pos}"><span class="ar2 {"up" if arrow=="up" else "dn"}" style="left:{ax}px"></span>'
      f'<div class="row sp"><div><span class="chip cy">{step} dari {total}</span>{ch}</div>'+('<span class="t2" style="font-weight:600">Lewati</span>' if skip else '')+'</div>'
      f'<h4>{title}</h4><p>{desc}</p><div class="row sp" style="margin-top:9px"><div class="dots">{dots}</div><div class="row" style="gap:6px">{back}<div class="tb p">{"Selesai" if last else "Lanjut"}</div></div></div></div>')
dim='<div class="dim"></div>'
# 1 tawaran
s1=phone('1. Tawaran Tur',dash('admin'),over=dim+'<div class="dlg">'+blob('smile',92)+'<div style="font-weight:800;font-size:14.5px;margin-top:4px">Mau kenalan dengan IX-H HUB?</div><div class="t2" style="margin:5px 0 12px;line-height:1.5">Tur singkat 1 menit: kamu akan melihat fungsi tombol dan fitur utama. Bisa diulang kapan saja lewat Bantuan.</div><div class="btn pri" style="margin-top:0">[[play]] Mulai Tur</div><div class="btn sec">Nanti Saja</div></div>')
s2=phone('2. Tur 1/6: Kehadiran',dash('admin','stat'),over=dim+tip(1,6,'Kehadiran hari ini','Menampilkan jumlah siswa yang hadir dan persentasenya. Ketuk kartu untuk melihat daftar lengkap.',top=232,ax=60,first=True))
s3=phone('3. Tur 3/6: Tombol Tagihan',dash('admin',('tile',2)),over=dim+tip(3,6,'Tombol Tagihan','Membuat dan mengirim tagihan kas ke WhatsApp tiap siswa, lalu memantau siapa yang sudah bayar.',top=392,ax=250,chip='Admin & Pengurus'))
s4=phone('4. Tur 5/6: Bilah Bawah',dash('admin'),nav=None,over=dim+navb(True)+tip(5,6,'Bilah navigasi','Pindah cepat antar fitur: Beranda, Absensi, Kas, Jadwal, dan Tugas. Tab yang aktif berwarna kuning.',bottom=70,arrow='dn',ax=150))
s5=phone('5. Tur 6/6: Akun & Bantuan',dash('admin','av'),over=dim+tip(6,6,'Tombol akun','Buka profil, pengaturan, dan Bantuan. Di sini kamu juga bisa mengulang tur ini kapan saja.',top=84,ax=262,last=True))
s6=phone('6. Tur Selesai',f'<div style="text-align:center;padding-top:30px">{blob("smile",120)}<div style="font-weight:800;font-size:18px;margin-top:6px">Kamu siap!</div><div class="t2" style="margin:3px 0 14px">Beberapa hal yang bisa kamu coba sekarang</div></div><div class="card"><div class="lrow"><span style="color:#2E9B5A">[[checkc]]</span><div class="t1">Ambil absensi dari Aksi Cepat</div></div><div class="lrow"><span style="color:#2E9B5A">[[checkc]]</span><div class="t1">Kirim tagihan kas ke WhatsApp siswa</div></div><div class="lrow"><span style="color:#2E9B5A">[[checkc]]</span><div class="t1">Cari penjelasan fitur di Bantuan</div></div></div><div class="btn pri" style="margin-top:14px">Mulai Pakai</div><div class="btn sec">[[play]] Ulangi Tur</div>')
s7=phone('7. Tur Siswa 2/5: Tagihanku',dash('siswa',('tile',1)),over=dim+tip(2,5,'Tagihanku','Lihat tagihan kas milikmu, jatuh tempo, dan kirim bukti pembayaran supaya ditandai lunas.',top=392,ax=140,chip='Siswa'))
def hrow(ic,t,d,chip=''): return f'<div class="card" style="padding:8px 10px;margin-bottom:6px"><div class="row sp"><div class="row"><div class="ic" style="width:32px;height:32px">[[{ic}]]</div><div><div class="t1">{t}</div><div class="t3">{d}</div></div></div>{chip or "<span style=\'font-size:15px\'>[[chev]]</span>"}</div></div>'
s8=phone('8. Pusat Bantuan',bar('Bantuan')+'<div class="inp" style="color:#6B6458;display:flex;gap:8px;align-items:center;margin-bottom:8px">[[search]] Cari fitur atau tombol</div><div class="btn pri" style="margin:0 0 10px">[[play]] Ulangi Tur Aplikasi</div><div class="lbl" style="margin-top:0">Panduan Fitur</div>'
  +hrow('check','Absensi','Catat dan lihat kehadiran siswa')+hrow('wallet','Kas','Pemasukan, pengeluaran, dan laporan')+hrow('mail','Tagihan Kas','Tagih kas lewat WhatsApp','<span class="chip cb">Admin</span>')+hrow('cal','Jadwal & Tugas','Pelajaran, PR, dan pengingat')+hrow('mega','Pengumuman','Kabar resmi dari Admin dan Pengurus')+hrow('bot','Bot WhatsApp','Pesan otomatis ke siswa','<span class="chip cb">Admin</span>')
  +'<div class="t3" style="text-align:center;margin-top:6px">Hanya fitur sesuai role kamu yang ditampilkan</div>')
def step(n,t): return f'<div class="lrow"><span class="num">{n}</span><div class="t2" style="color:#1C1B1F">{t}</div></div>'
def btnrow(ic,t,d): return f'<div class="lrow"><div class="ic" style="width:30px;height:30px">[[{ic}]]</div><div><div class="t1">{t}</div><div class="t3">{d}</div></div></div>'
s9=phone('9. Penjelasan Fitur',bar('Tagihan Kas')+'<div class="row" style="margin-bottom:6px"><div class="ic" style="width:38px;height:38px;background:#FFC928;border:2px solid #1C1B1F">[[mail]]</div><div><div style="font-weight:800;font-size:14px">Tagihan Kas</div><span class="chip cb">Admin & Pengurus</span></div></div>'
  +'<div class="lbl">Fungsinya</div><div class="card" style="font-size:10.5px;line-height:1.5;padding:8px 11px">Membuat tagihan kas per periode dan mengirimnya ke WhatsApp tiap siswa, lalu memantau siapa yang sudah bayar.</div>'
  +'<div class="lbl">Cara pakai</div><div class="card" style="padding:2px 11px">'+step(1,'Buat tagihan: nominal dan jatuh tempo')+step(2,'Cek pratinjau pesan, lalu kirim')+step(3,'Tandai lunas setelah bukti masuk')+'</div>'
  +'<div class="lbl">Tombol di layar ini</div><div class="card" style="padding:2px 11px">'+btnrow('plus','Buat Tagihan','Membuat tagihan periode baru')+btnrow('send','Kirim Pengingat','Hanya ke yang belum bayar, maks. 1x per hari')+btnrow('copy','Salin Pesan','Kirim manual jika pesan bot gagal')+'</div>'
  +'<div class="btn pri" style="margin-top:6px">Buka Tagihan Kas</div>')
def arow(n,i,st,cc): return f'<div class="card" style="padding:7px 10px;margin-bottom:5px"><div class="row sp"><div class="row"><div class="ic" style="width:26px;height:26px;font-size:9px;font-weight:700;background:#FFC928;border:1.5px solid #1C1B1F">{i}</div><div class="t1">{n}</div></div><span class="chip {cc}">{st}</span></div></div>'
sh=lambda c,t,d:f'<div class="lrow"><span class="chip {c}" style="width:50px;text-align:center">{t}</span><div class="t2" style="color:#1C1B1F;line-height:1.4">{d}</div></div>'
s10=phone('10. Arti Status (Ikon Info)',bar('Absensi','<span style="'+SP+'border-radius:50%;width:26px;height:26px;display:flex;align-items:center;justify-content:center;background:#fff">[[info]]</span>')+arow('Ahmad Ramadhan','AR','Hadir','cg')+arow('Aisyah Putri','AP','Hadir','cg')+arow('Bima Sakti','BS','Izin','cy')+arow('Citra Lestari','CL','Sakit','cb')+arow('Daffa Prakarsa','DP','Alpha','cr'),
  over=dim+'<div class="sheet"><div style="width:40px;height:4px;border-radius:2px;background:#EADFC2;margin:0 auto 8px"></div><div style="font-weight:800;font-size:14px">Arti Status Kehadiran</div><div class="t3" style="margin-bottom:4px">Ketuk ikon info di layar mana pun untuk penjelasan singkat</div>'+sh('cg','Hadir','Siswa masuk dan tercatat hadir')+sh('cy','Izin','Tidak hadir, ada keterangan dari orang tua')+sh('cb','Sakit','Tidak hadir karena sakit')+sh('cr','Alpha','Tidak hadir tanpa keterangan')+'<div class="btn pri" style="margin-top:8px">Mengerti</div></div>')
R=rules(['Tur muncul <b>sekali</b> setelah onboarding (25) selesai. Bisa dilewati, dan diulang lewat <b>Profil > Bantuan > Ulangi Tur</b> atau tombol akun.','Langkah menyesuaikan role: <b>Admin 6</b> (Kehadiran, Kas, Tagihan, Lonceng, Bilah bawah, Akun), <b>Pengurus 6</b> (Kehadiran, Ambil Absensi, Tagihan, Lonceng, Bilah bawah, Akun), <b>Siswa 5</b> (Kehadiranku, Tagihanku, Lonceng, Bilah bawah, Akun).','Tiap tooltip menjelaskan <b>satu elemen</b>: judul, fungsi, dan siapa yang boleh memakainya. Ada tombol Kembali, Lanjut, Lewati, dan indikator langkah.','Elemen yang disorot diberi cincin kuning; layar lainnya digelapkan dan tidak bisa diketuk selama tur.','Tur hanya menyorot fitur yang <b>boleh dipakai role itu</b> (mengikuti Matriks Hak Akses di 00).','Pusat Bantuan memuat penjelasan tiap fitur: fungsi, siapa yang bisa, cara pakai, dan arti tombol. Isinya menyesuaikan role.','Ikon <b>info</b> di layar menampilkan penjelasan singkat istilah dan tombol (contoh: arti status absensi).','Status tur tersimpan <b>per akun</b>; login di perangkat lain tidak mengulang tur.','Ringan: sorotan memakai lapisan gelap datar tanpa blur; perangkat RAM 2 GB atau kurang berpindah langkah tanpa animasi.'],span=2)
html=page('Pengenalan Aplikasi (Tur Fitur)','Tur singkat setelah login pertama • sorotan tombol dan fitur • Pusat Bantuan • penjelasan istilah dan tombol',[s1,s2,s3,s4,s5,s6,s7,s8,s9,s10,R])
render(html,'/home/claude/w32/32_pengenalan_aplikasi.jpg','32_pengenalan_aplikasi')
