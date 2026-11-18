from lib import *
import lib, icons

# ---------- ikon baru (SVG garis, stroke 2, ujung bulat) ----------
icons.P['palette']=['<circle cx="13.5" cy="6.5" r=".6"/>','<circle cx="17.5" cy="10.5" r=".6"/>','<circle cx="6.5" cy="12.5" r=".6"/>','<circle cx="8.5" cy="7.5" r=".6"/>','M12 22a1 1 0 0 1 0-20 10 9 0 0 1 10 9 5 5 0 0 1-5 5h-2.25a1.75 1.75 0 0 0-1.4 2.8l.3.4a1.75 1.75 0 0 1-1.4 2.8z']
icons.P['layers']=['M12.83 2.18a2 2 0 0 0-1.66 0L2.6 6.08a1 1 0 0 0 0 1.83l8.58 3.91a2 2 0 0 0 1.66 0l8.58-3.9a1 1 0 0 0 0-1.83z','M2 12a1 1 0 0 0 .58.91l8.6 3.91a2 2 0 0 0 1.65 0l8.58-3.9A1 1 0 0 0 22 12','M2 17a1 1 0 0 0 .58.91l8.6 3.91a2 2 0 0 0 1.65 0l8.58-3.9A1 1 0 0 0 22 17']
icons.P['sun']=['<circle cx="12" cy="12" r="4"/>','M12 2v2','M12 20v2','m4.93 4.93 1.41 1.41','m17.66 17.66 1.41 1.41','M2 12h2','M20 12h2','m6.34 17.66-1.41 1.41','m19.07 4.93-1.41 1.41']
icons.P['moon']=['M12 3a6 6 0 0 0 9 9 9 9 0 1 1-9-9Z']
icons.P['home']=['M15 21v-8a1 1 0 0 0-1-1h-4a1 1 0 0 0-1 1v8','M3 10a2 2 0 0 1 .709-1.528l7-5.999a2 2 0 0 1 2.582 0l7 5.999A2 2 0 0 1 21 10v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z']
icons.P['clip']=['<rect x="8" y="2" width="8" height="4" rx="1"/>','M16 4h2a2 2 0 0 1 2 2v14a2 2 0 0 1-2 2H6a2 2 0 0 1-2-2V6a2 2 0 0 1 2-2h2','M12 11h4','M12 16h4','M8 11h.01','M8 16h.01']
icons.P['batt']=['<rect x="2" y="7" width="16" height="10" rx="2"/>','M22 11v2','M6 11v2']
icons.P['down']=['m6 9 6 6 6-6']

# ---------- CSS tambahan: komponen yang sama, kulit berbeda ----------
EXTRA='''
.lg{width:30px;height:30px;border-radius:10px;background:#FFC928;border:2px solid #1C1B1F;font-weight:800;font-size:8.5px;display:flex;align-items:center;justify-content:center}
.rl{background:#FFC928;border:1.5px solid #1C1B1F;border-radius:12px;padding:1px 9px;font-size:8.5px;font-weight:700}
.circ{width:30px;height:30px;border-radius:50%;background:#FFF9E8;border:1.5px solid #1C1B1F;display:flex;align-items:center;justify-content:center;position:relative;font-size:13px;flex:none}
.circ.y{background:#FFC928;border-width:2px}
.bd{position:absolute;top:-4px;right:-4px;background:#D94452;color:#fff;border-radius:8px;font-size:7.5px;padding:0 4px;font-style:normal;font-weight:700;line-height:12px}
.hello{background:#FFC928;border:2px solid #1C1B1F;border-radius:16px;box-shadow:0 3px 0 #1C1B1F;padding:10px 12px;display:flex;justify-content:space-between;align-items:center;margin-bottom:8px}
.tile{background:#fff;border:1.5px solid #EADFC2;border-radius:14px;padding:5px 3px 8px;text-align:center;font-size:9.5px;font-weight:500;line-height:1.25}
.tile .ic{margin:0 auto 2px;width:26px;height:26px;font-size:12px}
.cmpt .card{margin-bottom:6px}.cmpt .al{margin-bottom:6px}.cmpt .hello{margin-bottom:6px;padding:8px 12px}
.mb{display:inline-block;background:#D94452;color:#fff;border-radius:8px;font-size:6.8px;padding:0 5px;font-weight:700}
.pb{height:6px;background:#EFE8D6;border-radius:4px;overflow:hidden}.pb i{display:block;height:100%;background:#2E9B5A;border-radius:4px}
.card.tint{background:#FFEBA8;border-color:#EADFC2}
.card.sel{border:2px solid #1C1B1F;box-shadow:0 3px 0 #1C1B1F}
.rad{width:20px;height:20px;border-radius:50%;border:2px solid #1C1B1F;background:#fff;display:flex;align-items:center;justify-content:center;flex:none;font-size:11px}
.rad.on{background:#FFC928}
.wb{width:30px;height:44px;border-radius:8px}.wb.g{background:#2E9B5A}.wb.t{background:#FFC928;border:2px solid #1C1B1F}.wb.e{background:#F1EAD6}
.nav2{position:absolute;left:0;right:0;bottom:0;height:60px;background:#FFF9E8;border-top:1px solid #EADFC2;display:flex;justify-content:space-around;align-items:center;z-index:5}
.nv{display:flex;flex-direction:column;align-items:center;gap:1px;font-size:8.5px;color:#6B6458;padding:4px 10px;border-radius:18px;border:2px solid transparent}
.nv svg{width:16px;height:16px}
.nv.on{background:#FFC928;border-color:#1C1B1F;color:#1C1B1F;font-weight:700}
.toast{position:absolute;left:12px;right:12px;top:34px;z-index:12;background:#1C1B1F;color:#fff;border-radius:14px;padding:10px 12px;display:flex;align-items:center;gap:8px;font-size:10.5px}
.pv{border-radius:16px;padding:10px}
.cmpg{display:grid;grid-template-columns:1fr 1fr;gap:8px}
.cmpg .p{border-radius:14px;padding:8px;min-height:50px;display:flex;align-items:center;justify-content:center}
.pvn{background:#FFF9E8;border:1.5px solid #EADFC2}
.mini{width:58px;height:78px;border-radius:12px;padding:6px;flex:none;position:relative}

/* ===== Liquid Glass ENTENG: isi datar, kaca hanya di lapisan melayang (navigasi, pesan) ===== */
.ph.glass,.pvg{background:#FFF9E8}
.glass .card,.glass .tile{background:#fff;border:1px solid #F1E8CC;border-radius:18px;box-shadow:0 1px 2px rgba(120,90,0,.08)}
.glass .card.tint{background:#FFF0B8}
.glass .card.sel{border:2px solid #FFC928;box-shadow:0 0 0 3px rgba(255,201,40,.28)}
.glass .ic{background:#FFEBA8;border:0}
.glass .lg,.glass .rl,.glass .circ.y{background:#FFC928;border:0}
.glass .circ{background:#fff;border:1px solid #F1E8CC}
.glass .hello{background:#FFC928;border:0;border-radius:24px;box-shadow:inset 0 1px 0 rgba(255,255,255,.55)}
.glass .pri{background:#FFC928;border:0;border-radius:16px;box-shadow:inset 0 1px 0 rgba(255,255,255,.7),0 2px 4px rgba(200,140,0,.25)}
.glass .sec{background:#fff;border:1px solid #F1E8CC;border-radius:16px}
.glass .inp{background:#fff;border:1px solid #F1E8CC;border-radius:16px}
.glass .tabs{background:#F4ECD6;border-radius:16px}
.glass .tabs .on{background:#fff;border:0;border-radius:13px;box-shadow:0 1px 3px rgba(0,0,0,.14)}
.glass .chip{border:0}
.glass .al{border-width:1px;border-radius:16px}
.glass .tog{background:#FFC928;border:0}
.glass .tog:after{background:#fff;box-shadow:0 1px 3px rgba(0,0,0,.25)}
.glass .tog.off{background:#E4DCC6;border:0}
.glass .tog.off:after{background:#fff}
.glass .rad{border:1.5px solid #D9CFB2;background:#fff}
.glass .rad.on{background:#FFC928;border-color:#FFC928}
.glass .pb{background:#F1EAD6}
.glass .wb.t{border:0;background:#FFC928}
.glass .wb.e{background:#F1EAD6}
.glass .t3,.glass .t2{color:#5A5448}
/* satu-satunya blur permanen: bilah navigasi melayang; blur kecil, statis */
.glass .nav2{left:10px;right:10px;bottom:10px;height:58px;border-radius:29px;background:rgba(255,255,255,.62);border:1px solid rgba(255,255,255,.95);box-shadow:0 4px 14px rgba(120,90,0,.16),inset 0 1px 0 #fff;-webkit-backdrop-filter:blur(12px) saturate(1.4);backdrop-filter:blur(12px) saturate(1.4)}
.glass .nv{border:1px solid transparent;color:#4F493D}
.glass .nv.on{background:linear-gradient(180deg,rgba(255,226,110,.95),rgba(255,196,30,.9));border-color:#fff;box-shadow:inset 0 1px 0 #fff;color:#1C1B1F}
/* blur sementara: pesan singkat */
.glass .toast{background:rgba(28,27,31,.72);border:1px solid rgba(255,255,255,.3);border-radius:22px;-webkit-backdrop-filter:blur(12px);backdrop-filter:blur(12px)}
/* perangkat ringan: tanpa blur sama sekali */
.glass.solid *{-webkit-backdrop-filter:none!important;backdrop-filter:none!important}
.glass.solid .nav2{background:#fff;border:1px solid #F1E8CC;box-shadow:0 2px 6px rgba(120,90,0,.12)}
.glass.solid .nv.on{background:#FFC928;border-color:#FFC928;box-shadow:none}
.glass.solid .toast{background:#1C1B1F;border:0}
'''
lib.CSS+=EXTRA

# ---------- komponen ----------
def blobg(kind='idle',s=70):
    eyes={'idle':'<circle cx="38" cy="50" r="3.2"/><circle cx="62" cy="50" r="3.2"/>','smile':'<path d="M33 51q5-6 10 0M57 51q5-6 10 0" fill="none" stroke="#1C1B1F" stroke-width="3" stroke-linecap="round"/>'}[kind]
    mouth={'idle':'<path d="M45 60q5 4 10 0" fill="none" stroke="#1C1B1F" stroke-width="2.5" stroke-linecap="round"/>','smile':'<path d="M42 59q8 10 16 0z" fill="#D94452" stroke="#1C1B1F" stroke-width="2.5" stroke-linejoin="round"/>'}[kind]
    gid=f'g{s}{kind}'
    return (f'<svg width="{s}" height="{s}" viewBox="0 0 100 100"><defs><radialGradient id="{gid}" cx="35%" cy="28%" r="80%"><stop offset="0" stop-color="#FFF0A8"/><stop offset=".55" stop-color="#FFCB2E"/><stop offset="1" stop-color="#FFAE00"/></radialGradient></defs>'
            '<ellipse cx="50" cy="91" rx="26" ry="4" fill="rgba(150,100,0,.22)"/><path d="M50 20q-2-10 6-14" fill="none" stroke="#1C1B1F" stroke-width="3" stroke-linecap="round"/>'
            f'<path d="M50 18C28 18 14 36 14 58c0 20 14 32 36 32s36-12 36-32c0-22-14-40-36-40z" fill="url(#{gid})" stroke="rgba(255,255,255,.95)" stroke-width="2.5"/>'
            '<ellipse cx="34" cy="36" rx="9" ry="5" fill="#fff" opacity=".75" transform="rotate(-30 34 36)"/><circle cx="29" cy="58" r="5" fill="#F4A6B0" opacity=".7"/><circle cx="71" cy="58" r="5" fill="#F4A6B0" opacity=".7"/>'
            f'<g fill="#1C1B1F">{eyes}</g>{mouth}</svg>')

def ph(label,body,nav=None,over='',glass=False,solid=False):
    n=''
    if nav:
        its=[('home','Beranda'),('checkc','Absensi'),('wallet','Kas'),('caldays','Jadwal'),('clip','Tugas')]
        n='<div class="nav2">'+''.join(f'<div class="nv{" on" if t==nav else ""}">[[{i}]]<span>{t}</span></div>' for i,t in its)+'</div>'
    cls='ph'+(' glass' if glass else '')+(' solid' if solid else '')
    return f'<div class="col"><div class="lab">{label}</div><div class="{cls}"><div class="sb"><span>9:41</span><span>▂▄▆ ▭</span></div>{body}{n}{over}</div></div>'

def hdr(role='ADMIN',n=4):
    return (f'<div class="row sp" style="padding:0 0 6px"><div class="row" style="gap:8px"><div class="lg">IXH</div><div><div class="t1" style="font-size:12.5px">IX-H Hub</div><div class="t3" style="font-size:9px">Kelas IX-H • SMP Spensari</div></div></div>'
            f'<div class="row" style="gap:6px"><span class="rl">{role}</span><div class="circ">[[bell]]<i class="bd">{n}</i></div><div class="circ y">[[user]]</div></div></div>')

def item(ic,t,d,right='[[chev]]'):
    return f'<div class="card"><div class="row sp"><div class="row"><div class="ic">[[{ic}]]</div><div><div class="t1">{t}</div><div class="t3">{d}</div></div></div><span style="color:#6B6458">{right}</span></div></div>'

# ---------- Beranda Admin (isi sama persis dengan layar 01) ----------
def tile(ic,t,badge=''):
    return f'<div class="tile"><div class="ic">[[{ic}]]</div>{t}'+(f'<div><span class="mb">{badge}</span></div>' if badge else '')+'</div>'
def beranda(g):
    bl=blobg('smile',36) if g else blob('smile',36)
    return ('<div class="cmpt">'+hdr()+f'<div class="hello"><div><div style="font-weight:700;font-size:14px">Halo, Ahmad!</div><div class="t3" style="color:#1C1B1F;font-size:10px">Kelola seluruh data IX-H</div></div>{bl}</div>'
    +'<div class="card row sp" style="padding:8px 12px"><div class="row" style="gap:8px"><span>[[cal]]</span><b style="font-size:11px">Rabu, 4 November 2026</b></div><span class="chip cy">IX-H [[down]]</span></div>'
    +'<div class="row" style="gap:8px;align-items:stretch"><div class="card" style="flex:1;margin:0 0 6px;padding:7px 10px"><div class="t3">Absensi Hari Ini</div><div class="row" style="gap:6px;margin:2px 0 5px"><b style="font-size:16px">28 / 32</b><span style="font-size:9px;color:#2E9B5A;font-weight:700">87,5%</span></div><div class="pb"><i style="width:87.5%"></i></div></div>'
    +'<div class="card" style="flex:1;margin:0 0 6px;padding:7px 10px"><div class="t3">Kas Saat Ini</div><b style="font-size:16px;display:block;margin:2px 0">Rp750.000</b><div style="font-size:9px;color:#2E9B5A">Terkonfirmasi</div></div></div>'
    +'<div class="al ab" style="padding:8px 11px"><span>[[mega]]</span><span><b style="font-size:8.5px">TERSEMAT • Pengumuman</b><br><b style="font-size:11px">Rapat kelas IX-H besok pagi!</b></span><span style="margin-left:auto;align-self:center">[[chev]]</span></div>'
    +'<div class="card tint row sp" style="padding:8px 12px"><div class="row" style="gap:8px"><div class="ic" style="width:28px;height:28px">[[clip]]</div><div><div class="t3">Tugas Terdekat</div><b style="font-size:11px">PR Matematika • Hal. 45–47</b></div></div><span class="chip cy">1 hari lagi</span></div>'
    +'<div class="row sp" style="margin:2px 2px 5px"><b style="font-size:12px">Jadwal Hari Ini</b><span class="t3">Lihat Semua</span></div>'
    +'<div class="card row sp" style="padding:7px 12px"><span style="font-size:10.5px"><b>07.00–07.45</b>&nbsp;&nbsp;Matematika</span><span class="t3">R. 12</span></div>'
    +'<b style="font-size:12px;margin:0 2px;display:block">Aksi Cepat</b><div style="display:grid;grid-template-columns:repeat(3,1fr);gap:6px;margin-top:5px">'
    +tile('checkc','Ambil Absensi')+tile('wallet','Catat Kas')+tile('mail','Tagihan')+tile('bot','Panel Bot')+tile('users','Kelola Pengguna','3 nomor bermasalah')+tile('mega','Pengumuman')+'</div>'+'<div class="card" style="margin-top:36px;padding:8px 12px"><div class="row sp"><div><div class="t3">Absensi Terakhir</div><b style="font-size:11px">Selasa, 3 Nov 2026 • 31 / 32 siswa</b></div><span class="chip cg">96,9%</span></div></div></div>')

# ---------- Absensi (isi sama dengan layar 02, warna status sesuai Design System) ----------
def donut():
    C=226.2;segs=[(28,'#2E9B5A'),(2,'#D9A400'),(1,'#2F6FDB'),(1,'#D94452')];off=0;out=''
    for v,c in segs:
        l=C*v/32
        out+=f'<circle cx="45" cy="45" r="36" fill="none" stroke="{c}" stroke-width="11" stroke-dasharray="{l-1.2:.1f} {C-l+1.2:.1f}" stroke-dashoffset="{-off:.1f}" transform="rotate(-90 45 45)"/>'
        off+=l
    return f'<svg width="90" height="90" viewBox="0 0 90 90">{out}<text x="45" y="47" text-anchor="middle" font-family="Poppins" font-weight="700" font-size="15" fill="#1C1B1F">87,5%</text><text x="45" y="59" text-anchor="middle" font-family="Poppins" font-size="8" fill="#6B6458">Hadir</text></svg>'
def absensi():
    leg=''.join(f'<div class="row sp" style="gap:8px;margin:3px 0"><span class="row" style="gap:6px;font-size:10.5px"><span style="color:{c}">●</span>{n}</span><b style="font-size:11px">{v}</b></div>' for n,v,c in [('Hadir',28,'#2E9B5A'),('Izin',2,'#D9A400'),('Sakit',1,'#2F6FDB'),('Alpha',1,'#D94452')])
    wb=''.join(f'<div style="text-align:center"><div class="wb {k}"></div><div class="t3" style="margin-top:3px">{d}</div></div>' for k,d in [('g','Sen'),('g','Sel'),('t','Rab'),('e','Kam'),('e','Jum')])
    return (hdr()+'<b style="font-size:16px;display:block;margin:0 2px 6px">Absensi</b>'
    +'<div class="card row sp" style="padding:8px 12px"><div class="row" style="gap:8px"><span>[[cal]]</span><span style="font-size:11px;font-weight:600">Rabu, 4 November 2026</span></div><span>[[down]]</span></div>'
    +f'<div class="card row" style="gap:12px;padding:8px 14px">{donut()}<div style="flex:1">{leg}</div></div>'
    +'<div class="card"><b style="font-size:11.5px">Perlu Perhatian</b><div class="row" style="gap:8px;margin-top:7px"><span class="chip cb">Sakit</span><span style="font-size:11px">Citra Lestari</span></div><div class="row" style="gap:8px;margin-top:6px"><span class="chip cr">Alpha</span><span style="font-size:11px">Fajar Nugroho</span></div></div>'
    +f'<div class="card"><b style="font-size:11.5px">Kehadiran Minggu Ini</b><div class="row sp" style="margin-top:8px;padding:0 6px">{wb}</div></div>'
    +'<div class="btn pri" style="margin-top:4px">[[checkc]] Ambil / Ubah Absensi</div><div class="btn sec" style="height:38px">[[history]] Riwayat &amp; Ekspor</div>'+'<div class="card" style="margin-top:34px;padding:8px 12px"><div class="row sp"><div class="row" style="gap:8px"><div class="ic" style="width:28px;height:28px">[[caldays]]</div><div><b style="font-size:11px">Selasa, 3 Nov 2026</b><div class="t3">31 / 32 siswa</div></div></div><span class="chip cg">96,9%</span></div></div>')

# ---------- Tampilan ----------
def thumb(k):
    if k=='neo':
        return ('<div class="mini" style="background:#FFF9E8;border:2px solid #1C1B1F"><div style="height:13px;background:#FFC928;border:1.5px solid #1C1B1F;border-radius:5px;box-shadow:0 2px 0 #1C1B1F"></div>'
                '<div style="height:13px;margin-top:6px;background:#fff;border:1px solid #EADFC2;border-radius:5px"></div><div style="height:13px;margin-top:4px;background:#fff;border:1px solid #EADFC2;border-radius:5px"></div>'
                '<div style="position:absolute;left:8px;right:8px;bottom:6px;height:9px;background:#FFC928;border:1.5px solid #1C1B1F;border-radius:6px"></div></div>')
    return ('<div class="mini" style="background:#FFF9E8;border:1px solid #F1E8CC"><div style="height:13px;background:#FFC928;border-radius:8px"></div>'
            '<div style="height:13px;margin-top:6px;background:#fff;border:1px solid #F1E8CC;border-radius:8px"></div><div style="height:13px;margin-top:4px;background:#fff;border:1px solid #F1E8CC;border-radius:8px"></div>'
            '<div style="position:absolute;left:10px;width:20px;bottom:8px;height:12px;background:#FFC928;border-radius:4px"></div>'
            '<div style="position:absolute;left:6px;right:6px;bottom:5px;height:10px;background:rgba(255,255,255,.7);border:1px solid #fff;border-radius:6px;box-shadow:0 2px 6px rgba(120,90,0,.2)"></div></div>')

def opt(k,name,desc,sel):
    r=f'<div class="rad{" on" if sel else ""}">'+('[[check]]' if sel else '')+'</div>'
    return f'<div class="card{" sel" if sel else ""}" style="padding:9px 12px"><div class="row" style="gap:12px">{thumb(k)}<div style="flex:1"><div class="t1" style="font-size:13px">{name}</div><div class="t3" style="font-size:10px;margin-top:2px">{desc}</div></div>{r}</div></div>'
def pnav(g):
    its=[('home','Beranda'),('checkc','Absensi'),('wallet','Kas')]
    inner=''.join(f'<div class="nv{" on" if i=="checkc" else ""}" style="padding:2px 8px;font-size:7px">[[{i}]]<span>{t}</span></div>' for i,t in its)
    if g:
        return ('<div style="position:relative;margin-top:6px;height:34px"><div style="position:absolute;left:16px;top:3px;width:56px;height:26px;background:#FFC928;border-radius:10px"></div><div style="position:absolute;right:16px;top:6px;width:50px;height:26px;background:#9CC9FF;border-radius:10px"></div>'
                '<div class="nav2" style="position:absolute;left:0;right:0;top:0;bottom:auto;height:34px;border-radius:17px">'+inner+'</div></div>')
    return '<div class="nav2" style="position:static;height:34px;border-radius:12px;margin-top:6px">'+inner+'</div>'
def prev(k):
    g=k=='glass'
    bl=blobg('smile',32) if g else blob('smile',32)
    body=(f'<div class="hello" style="padding:7px 10px;margin-bottom:6px"><div><div style="font-weight:700;font-size:11px">Halo, Ahmad!</div><div class="t3" style="color:#1C1B1F">Kelola seluruh data IX-H</div></div>{bl}</div>'
          '<div class="row" style="gap:6px"><div class="card" style="flex:1;margin:0;padding:6px 8px"><div class="t3">Absensi Hari Ini</div><b style="font-size:12px">28 / 32</b></div><div class="card" style="flex:1;margin:0;padding:6px 8px"><div class="t3">Kas Saat Ini</div><b style="font-size:12px">Rp750.000</b></div></div>'+pnav(g))
    return f'<div class="pv {"glass pvg" if g else "pvn"}">{body}</div>'
def tampilan(sel,btn,note,k_prev):
    return (bar('Tampilan')+'<div class="t3" style="font-weight:700;margin:0 2px 5px">TEMA</div>'
    +opt('neo','Neubrutalism','Tepi hitam tegas, bayangan solid, kuning pekat. Tema bawaan.',sel=='neo')
    +opt('glass','Liquid Glass','Isi datar dan ringan; kaca hanya di navigasi dan pesan. Sudut membulat.',sel=='glass')
    +'<div class="t3" style="font-weight:700;margin:6px 2px 5px">MODE</div><div class="tabs"><div class="on">[[sun]] Terang</div><div>[[moon]] Gelap</div></div>'
    +('<div class="t3" style="font-weight:700;margin:4px 2px 5px">PRATINJAU</div>'+prev(k_prev) if k_prev else '')+note
    +f'<div style="position:absolute;left:12px;right:12px;bottom:16px">{btn}</div>')
OFF='<div class="btn off" style="background:#EFE8D6;border:1.2px solid #EADFC8;color:#6B6458;margin:0">Tema ini sedang dipakai</div>'
BTN=lambda t:f'<div class="btn pri" style="margin:0">[[check]] {t}</div>'

s1=ph('1. Pengaturan ▸ Tampilan',tampilan('neo',OFF,'<div class="al ab" style="margin-top:8px"><span>[[info]]</span><span>Tema hanya mengubah tampilan. Isi, menu, dan hak aksesmu tidak berubah.</span></div>','neo'))
s2=ph('2. Pilih & Pratinjau',tampilan('glass',BTN('Terapkan Liquid Glass'),'<div class="al ab" style="margin-top:8px"><span>[[info]]</span><span>Berlaku langsung tanpa restart dan tersimpan di perangkat ini.</span></div>','glass'))
toast='<div class="toast"><span style="width:20px;height:20px;border-radius:50%;background:#2E9B5A;display:flex;align-items:center;justify-content:center;flex:none">[[check]]</span><span style="flex:1"><b>Tema Liquid Glass diterapkan</b></span><b style="color:#FFC928">Urungkan</b></div>'
s3=ph('3. Beranda • Liquid Glass',beranda(True),nav='Beranda',over=toast,glass=True)
s4=ph('4. Absensi • Liquid Glass',absensi(),nav='Absensi',glass=True)

# ---------- perbandingan komponen ----------
BG1='<div style="position:absolute;left:10px;top:8px;width:46px;height:30px;background:#FFC928;border-radius:10px"></div><div style="position:absolute;right:12px;bottom:8px;width:46px;height:30px;background:#9CC9FF;border-radius:10px"></div>'
def cmp_row(name,neo,gls,glass=False,bg=''):
    tag='<span class="chip cy" style="margin-left:6px">kaca</span>' if glass else '<span class="chip cx" style="margin-left:6px">datar</span>'
    return f'<div class="t3" style="font-weight:700;margin:6px 2px 3px">{name}{tag}</div><div class="cmpg"><div class="p pvn">{neo}</div><div class="p glass pvg" style="position:relative;overflow:hidden">{bg}{gls}</div></div>'
def mnav(g):
    its=[('home','Beranda'),('checkc','Absensi'),('wallet','Kas')]
    inner=''.join(f'<div class="nv{" on" if t=="Absensi" else ""}" style="padding:3px 6px;font-size:7.5px">[[{i}]]<span>{t}</span></div>' for i,t in its)
    if g: return '<div class="nav2" style="position:relative;z-index:1;height:auto;width:100%;left:auto;right:auto;bottom:auto;padding:3px 4px;border-radius:22px">'+inner+'</div>'
    return '<div class="nav2" style="position:static;height:auto;background:transparent;border:0;width:100%;gap:0">'+inner+'</div>'
btn_='<div class="btn pri" style="margin:0;width:100%;height:34px;font-size:10.5px">Simpan</div>'
crd='<div class="card" style="margin:0;width:100%;padding:6px 8px"><div class="row" style="gap:6px"><div class="ic" style="width:24px;height:24px;font-size:11px">[[wallet]]</div><div><div class="t1" style="font-size:10px">Kas</div><div class="t3" style="font-size:8.5px">Rp750.000</div></div></div></div>'
chp='<span class="chip cg">Hadir</span>&nbsp;<span class="chip cy">Izin</span>&nbsp;<span class="chip cb">Sakit</span>'
tst_n='<div style="background:#1C1B1F;color:#fff;border-radius:14px;padding:6px 10px;font-size:9px;width:100%;display:flex;gap:6px;align-items:center">[[check]] <b>Tema diterapkan</b></div>'
tst_g='<div class="toast" style="position:relative;left:auto;right:auto;top:auto;bottom:auto;z-index:1;width:100%;padding:6px 10px;font-size:9px;border-radius:14px">[[check]] <b>Tema diterapkan</b></div>'
rows=(cmp_row('Tombol utama',btn_,btn_)+cmp_row('Kartu & ikon',crd,crd)+cmp_row('Chip status',chp,chp)+cmp_row('Toggle','<div class="tog"></div>','<div class="tog"></div>')
 +cmp_row('Navigasi bawah',mnav(0),mnav(1),True,BG1)+cmp_row('Pesan singkat',tst_n,tst_g,True,BG1))
s5=ph('5. Perbandingan Komponen',bar('Perbandingan Komponen')
 +'<div class="cmpg" style="margin-bottom:2px"><div style="text-align:center"><span class="rl">Neubrutalism</span></div><div style="text-align:center"><span class="rl" style="background:#FFC928;border:0">Liquid Glass</span></div></div>'
 +rows+'<div class="al ay" style="margin-top:10px"><span>[[info]]</span><span>Kaca hanya di <b>navigasi</b> dan <b>pesan singkat</b>. Selebihnya datar dan putih seperti referensi.</span></div>')

# ---------- perangkat ringan ----------
NOTE6=('<div class="al ay" style="margin-top:6px"><span>[[batt]]</span><span><b>Perangkat ringan terdeteksi.</b> RAM 2 GB atau kurang, atau hemat daya aktif. Navigasi dan pesan tampil solid tanpa blur dan animasi.</span></div>'
 '<div class="card" style="padding:10px 12px"><div class="row sp"><div class="row" style="gap:8px"><div class="ic" style="width:28px;height:28px">[[layers]]</div><div><div class="t1" style="font-size:11.5px">Efek kaca (blur)</div><div class="t3" style="font-size:9px">[[lock]] Dikunci agar tetap lancar</div></div></div><div class="tog off"></div></div>'
 '<div class="row sp" style="margin-top:10px"><div class="row" style="gap:8px"><div class="ic" style="width:28px;height:28px">[[zap]]</div><div><div class="t1" style="font-size:11.5px">Animasi</div><div class="t3" style="font-size:9px">Mati otomatis di perangkat ini</div></div></div><div class="tog off"></div></div></div>'
 '<div class="al ab"><span>[[info]]</span><span>Tampilan tetap Liquid Glass. Hanya blur dan animasi yang dimatikan.</span></div>')
s6=ph('6. Perangkat Ringan • Kaca Solid',tampilan('glass',BTN('Terapkan Liquid Glass'),NOTE6,None),glass=True,solid=True)

R_=rules(['Ada <b>dua tema</b>: <b>Neubrutalism</b> (bawaan) dan <b>Liquid Glass</b>. Semua role (Admin, Pengurus, Siswa) boleh mengganti tema miliknya.',
 'Akses: Beranda ▸ ikon akun ▸ Pengaturan ▸ <b>Tampilan</b>. Pilihan <b>Mode</b> terang/gelap tetap di halaman yang sama dan terpisah dari tema.',
 'Tema hanya mengubah <b>kulit visual</b>. Tata letak, isi, hak akses, dan navigasi bawah 5 item <b>tidak berubah</b>. Tombol <b>Terapkan</b> berlaku langsung tanpa restart, ada tombol <b>Urungkan</b>, dan pilihan tersimpan di perangkat (splash dan login ikut tema terakhir).',
 '<b>Liquid Glass harus enteng.</b> Isi layar (kartu, daftar, banner, tombol) <b>datar dan putih tanpa blur</b>. Kaca hanya untuk lapisan melayang: <b>bilah navigasi bawah</b> dan <b>pesan singkat</b>.',
 '<b>Anggaran kaca</b>: maksimal 1 blur permanen (navigasi) + 1 sementara (pesan) per layar, radius blur kecil dan statis. Animasi hanya geser dan pudar; tidak ada blur di daftar yang di-scroll.',
 '<b>Wajib responsif</b> sampai <b>RAM 2 GB atau kurang</b>. Perangkat ringan (RAM ≤ 2 GB, hemat daya, atau sistem meminta kurangi transparansi) memakai versi <b>solid tanpa blur dan animasi</b>; toggle efek kaca dikunci.',
 'Neubrutalism tanpa blur sama sekali, jadi paling ringan. Animasi three.js diganti gambar diam di perangkat ringan.',
 'Warna status tetap <b>Hadir hijau, Izin kuning, Sakit biru, Alpha merah</b> dan teks tetap Ink <b>#1C1B1F</b> di kedua tema.',
 'Bisa diganti saat <b>offline</b>. Aturan mata maskot login tetap: password tersembunyi = mata terbuka, terlihat = mata menutup.'])
render(page('Ganti Tema','Neubrutalism dan Liquid Glass • tata letak sama, hanya kulit visual yang berbeda • Pengaturan ▸ Tampilan',[s1,s2,s3,s4,s5,s6,R_]),'/home/claude/work/31_ganti_tema.jpg','31_ganti_tema')
