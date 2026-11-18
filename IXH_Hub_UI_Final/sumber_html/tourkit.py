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
