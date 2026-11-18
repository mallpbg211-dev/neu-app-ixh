import math,re,icons
from lib import blob as _blob
from playwright.sync_api import sync_playwright
INK='#1C1B1F';Y='#FFC928';G='#2E9B5A';R='#D94452';B='#2F6FDB';GR='#6B6458';CR='#FFF9E8'
icons.P['key']=['m15.5 7.5 2.3 2.3a1 1 0 0 0 1.4 0l2.1-2.1a1 1 0 0 0 0-1.4L19 4','m21 2-9.6 9.6','<circle cx="7.5" cy="15.5" r="5.5"/>']
icons.P['flame']=['M8.5 14.5A2.5 2.5 0 0 0 11 12c0-1.38-.5-2-1-3-1.072-2.143-.224-4.054 2-6 .5 2.5 2 4.9 4 6.5 2 1.6 3 3.5 3 5.5a7 7 0 1 1-14 0c0-1.153.433-2.294 1-3a2.5 2.5 0 0 0 2.5 2.5z']
icons.P['pin']=['M12 17v5','M9 10.76a2 2 0 0 1-1.11 1.79l-1.78.9A2 2 0 0 0 5 15.24V16a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1v-.76a2 2 0 0 0-1.11-1.79l-1.78-.9A2 2 0 0 1 15 10.76V7a1 1 0 0 1 1-1 2 2 0 0 0 0-4H8a2 2 0 0 0 0 4 1 1 0 0 1 1 1z']
P=icons.P
def ic(n,x,y,s,col=INK,fill='none',sw=2):
    inner=''.join(p if p.startswith('<') else f'<path d="{p}"/>' for p in P[n])
    return f'<g transform="translate({x},{y}) scale({s/24})" fill="{fill}" stroke="{col}" stroke-width="{sw}" stroke-linecap="round" stroke-linejoin="round">{inner}</g>'
def T(x,y,s,size=11,w=700,fill=INK,a='middle',ls=0): return f'<text x="{x}" y="{y}" font-family="Poppins" font-size="{size}" font-weight="{w}" fill="{fill}" text-anchor="{a}" letter-spacing="{ls}">{s}</text>'
def card(x,y,w,h,fill='#fff',st=INK,sw=2,r=8,op=1,rot=None):
    t=f' transform="rotate({rot[0]} {rot[1]} {rot[2]})"' if rot else ''
    return f'<rect x="{x}" y="{y}" width="{w}" height="{h}" rx="{r}" fill="{fill}" stroke="{st}" stroke-width="{sw}" opacity="{op}"{t}/>'
def arr(x1,y1,x2,y2,c=GR,w=1.6):
    a=math.atan2(y2-y1,x2-x1);h=5
    p=f'{x2},{y2} {x2-h*math.cos(a-.5):.1f},{y2-h*math.sin(a-.5):.1f} {x2-h*math.cos(a+.5):.1f},{y2-h*math.sin(a+.5):.1f}'
    return f'<line x1="{x1}" y1="{y1}" x2="{x2-2*math.cos(a):.1f}" y2="{y2-2*math.sin(a):.1f}" stroke="{c}" stroke-width="{w}" stroke-linecap="round"/><polygon points="{p}" fill="{c}"/>'
def dp(d,c=GR,w=2): return f'<path d="{d}" fill="none" stroke="{c}" stroke-width="{w}" stroke-dasharray="0.1 5" stroke-linecap="round"/>'
def blob(x,y,s,k='idle'):
    b=_blob(k,s); b=re.sub(r'<svg width="\d+" height="\d+"',f'<svg x="{x}" y="{y}" width="{s}" height="{s}"',b,1); return b
def circ(x,y,r,fill='#fff',st=INK,sw=2,op=1): return f'<circle cx="{x}" cy="{y}" r="{r}" fill="{fill}" stroke="{st}" stroke-width="{sw}" opacity="{op}"/>'
def bars(x,y,w,n=2,gap=7,c='#D8D0B8'): return ''.join(f'<rect x="{x}" y="{y+i*gap}" width="{w-i*14}" height="3.5" rx="1.75" fill="{c}"/>' for i in range(n))
def bub(x,y,w,h,t,fill='#D9FDD3',size=9.5): return card(x,y,w,h,fill,INK,1.6,9)+T(x+w/2,y+h/2+size*0.36,t,size,700)
def sp(x,y,r=4,c=Y): return f'<path d="M{x} {y-r}v{2*r}M{x-r} {y}h{2*r}" stroke="{c}" stroke-width="2" stroke-linecap="round"/>'
GROUND='<ellipse cx="119" cy="160" rx="88" ry="11" fill="#EFE5CC"/>'
GIZ='<line x1="14" y1="178" x2="31" y2="178" stroke="#D94452" stroke-width="2"/><line x1="14" y1="178" x2="14" y2="161" stroke="#2E9B5A" stroke-width="2"/><line x1="14" y1="178" x2="6" y2="186" stroke="#2F6FDB" stroke-width="2"/>'
def frame(t,i,body,cap):
    return f'<div class="fr"><div class="f"><svg viewBox="0 0 238 193" width="238" height="193">{GROUND}{body}{GIZ}</svg><span class="tm">{t}</span><span class="ix">{i}</span></div><div class="cp">{cap}</div></div>'
def row(title,bul,frs):
    li=''.join(f'<li>{b}</li>' for b in bul)
    return f'<div class="rw"><div class="lc"><span class="pl">{title}</span><ul>{li}</ul><div class="ft2">Hanya satu canvas three.js per layar</div></div>'+'<div class="ar"></div>'.join(frs)+'</div>'
# ---------- 29 ----------
qr_pat=[ '1110111','1010101','1110101','0001000','1101110','0101010','1110111']
def qr(x,y,c=13,show=49,op=1):
    out='';k=0
    for r,line in enumerate(qr_pat):
        for cc,v in enumerate(line):
            fin=(r<3 and cc<3) or (r<3 and cc>3) or (r>3 and cc<3)
            if v=='1' or fin:
                o=op if k<show else .18
                out+=f'<rect x="{x+cc*c}" y="{y+r*c}" width="{c-2}" height="{c-2}" rx="2" fill="{INK}" opacity="{o}"/>'
            k+=1
    return out
# Pengumuman
a1=frame('0,0 s',1,ic('mega',84,52,70,fill=Y,sw=1.6)+arr(78,62,64,48)+arr(160,62,174,48)+arr(78,118,64,132)+arr(160,118,174,132),'Megafon muncul dengan efek pop')
a2=frame('0,4 s',2,ic('mega',24,64,54,fill=Y,sw=1.6)+''.join(f'<path d="M{92+i*16} {82-i*8} q{10+i*4} {11+i*8} 0 {22+i*16}" fill="none" stroke="{INK}" stroke-width="2" stroke-linecap="round" opacity="{1-i*.25}"/>' for i in range(3))+card(166,34,46,24,'#fff',INK,1.8,6,rot=(-10,189,46))+bars(172,42,34,2,6)+card(172,118,46,24,'#fff',INK,1.8,6,rot=(8,195,130))+bars(178,126,34,2,6),'Suara melebar, kartu berterbangan')
a3=frame('0,9 s',3,arr(119,10,119,34)+card(30,48,178,44,Y,INK,2.5,10)+ic('pin',40,58,22,fill='#fff',sw=1.8)+T(72,75,'Rapat kelas IX-H',12,700,INK,'start')+card(30,108,84,30,'#fff',INK,1.5,8,.55)+card(124,108,84,30,'#fff',INK,1.5,8,.55)+dp('M70 100 q-6 -6 -12 0',GR,1.6)+dp('M170 100 q6 -6 12 0',GR,1.6),'Banner jatuh ke dashboard')
a4=frame('1,3 s',4,card(30,30,178,34,Y,INK,2.5,10)+ic('pin',40,37,20,fill='#fff',sw=1.8)+T(70,52,'Rapat kelas IX-H',11,700,INK,'start')+blob(122,74,88,'smile')+arr(108,96,94,72)+card(30,100,54,20,'#fff',INK,1.6,10)+T(57,114,'Tersemat',9,700),'Blob menunjuk banner')
r1=row('Pengumuman',['Megafon pop + getar kecil','Gelombang suara melebar','Banner jatuh + bounce','Blob menunjuk banner'],[a1,a2,a3,a4])
# Notifikasi
n1=frame('0,0 s',1,ic('bell',79,40,80,fill=Y,sw=1.5)+dp('M60 70 q-14 22 0 44',GR)+dp('M178 70 q14 22 0 44',GR)+circ(156,58,13,R,INK,2)+T(156,63,'3',13,800,'#fff')+arr(176,40,186,30),'Lonceng bergoyang + lencana')
n2=frame('0,4 s',2,arr(214,8,214,34)+card(22,22,180,48,'#fff',INK,2.5,12)+circ(46,46,14,Y,INK,2)+ic('mega',38,38,16)+bars(68,36,110,3,8,'#CFC6AE')+card(22,92,180,26,'#fff','#D8D0B8',1.5,8,.5)+card(22,126,180,20,'#fff','#D8D0B8',1.5,8,.35),'Banner masuk dari atas layar')
def itc(x,y,op=1): return card(x,y,170,32,'#fff',INK,2,9,op)+circ(x+15,y+16,5,R,INK,1.4)+bars(x+28,y+9,100,2,8,'#CFC6AE')
n3=frame('0,8 s',3,itc(24,24)+itc(34,66,.85)+itc(44,108,.6)+arr(228,40,210,40)+arr(228,82,214,82)+arr(228,124,214,124),'Item daftar masuk berurutan')
n4=frame('1,2 s',4,card(24,26,170,36,'#fff',INK,2,10)+circ(40,44,3,R,INK,1.2)+arr(48,44,62,44,GR,1.4)+circ(74,44,9,G,INK,1.6)+ic('check',68,38,12,'#fff',sw=3)+T(132,48,'sudah dibaca',10,600,INK,'middle')+blob(120,72,86,'sleep')+T(70,120,'0 baru',13,800,G,'middle')+T(190,92,'z',12,700,GR)+T(200,80,'Z',14,700,GR),'Titik merah hilang, blob tenang')
r2=row('Notifikasi',['Lonceng berayun ±15°','Lencana angka muncul (pop)','Banner turun dari atas, 4 detik','Titik merah mengecil saat dibaca'],[n1,n2,n3,n4])
# Kehadiranku
C=2*math.pi*42
def donut(p,txt,sub=''):
    return circ(119,84,42,'none','#EFE5CC',12)+f'<circle cx="119" cy="84" r="42" fill="none" stroke="{G}" stroke-width="12" stroke-linecap="round" stroke-dasharray="{C*p:.1f} {C}" transform="rotate(-90 119 84)"/>'+T(119,90,txt,19,800)+(T(119,104,sub,9,500,GR) if sub else '')
k1=frame('0,0 s',1,donut(.02,'0%')+dp('M62 84 a57 57 0 0 1 57 -57',GR,1.6)+arr(100,26,112,26)+T(119,150,'mulai terisi',9,500,GR),'Donut mulai terisi dari 0%')
k2=frame('0,6 s',2,donut(.94,'94,0%','Hadir')+blob(172,110,52,'idle')+arr(150,52,164,40),'Donut penuh sampai 94,0%')
cols={'H':'#CBEED6','I':'#FFD98A','S':'#BCD4FF'}
g='HHHHH HHIHH HSSHH HHHHH'.split()
cells=''
for r_,line in enumerate(g):
    for c_,ch in enumerate(line):
        o=1 if (r_*5+c_)<14 else .25
        cells+=f'<rect x="{50+c_*28}" y="{34+r_*26}" width="24" height="21" rx="6" fill="{cols[ch]}" stroke="{INK if ch!="H" else G}" stroke-width="1.6" opacity="{o}"/>'
k3=frame('1,0 s',3,T(119,25,'Oktober 2026',10,700)+cells+arr(212,60,212,44)+arr(24,110,24,94),'Sel kalender muncul berurutan')
k4=frame('1,5 s',4,ic('flame',26,40,64,fill=Y,sw=1.6)+T(58,126,'12 hari',13,800)+blob(124,58,98,'smile')+sp(190,44,5)+sp(112,48,4)+sp(204,96,4,B),'Rentetan 12 hari, blob bangga')
r3=row('Kehadiranku',['Donut tumbuh 0 ke 94%','Kalender: sel berwarna pop','Rentetan hadir: api membesar','Blob bangga saat ≥ 90%'],[k1,k2,k3,k4])
# Tagihan
t1=frame('0,0 s',1,card(24,78,78,50,'#fff',INK,2.5,8,rot=(-8,63,103))+T(63,110,'Rp6.000',13,800)+dp('M104 70 q40 -50 76 0',GR)+arr(176,64,184,72)+card(170,70,40,64,'#fff',INK,2.5,8)+circ(190,126,2,INK,INK,1),'Tagihan terbang ke ponsel siswa')
t2=frame('0,6 s',2,card(78,16,82,130,'#fff',INK,3,12)+bub(86,38,64,32,'Tagihan kas','#fff',8.5)+bars(94,78,38,2,6)+bub(100,92,50,22,'Rp6.000','#D9FDD3',8.5)+ic('check-check',132,118,14,G,sw=3)+arr(56,50,70,56)+arr(182,50,168,56),'Bubble WA muncul, centang ganda')
t3=frame('1,1 s',3,card(26,50,96,72,'#fff',INK,2.5,8)+'<path d="M36 112 l22 -26 l16 16 l12 -12 l22 22z" fill="#E9E2D0"/><circle cx="52" cy="70" r="6" fill="#FFD98A"/>'+arr(158,28,158,56)+circ(158,92,34,'none',G,1.6,.5)+circ(158,92,26,'#fff',G,3)+T(158,96,'LUNAS',11,800,G)+dp('M122 140 q36 14 70 0',GR,1.6),'Bukti masuk, stempel LUNAS')
t4=frame('1,6 s',4,T(119,28,'Rp144.000 / Rp192.000',11,700)+card(30,38,178,16,'#fff',INK,2.5,8)+'<rect x="32" y="40" width="130" height="12" rx="6" fill="#2E9B5A"/>'+arr(166,70,166,60,G)+T(190,72,'75%',10,800,G)+blob(22,86,74,'smile')+'<ellipse cx="170" cy="138" rx="22" ry="7" fill="#FFC928" stroke="#1C1B1F" stroke-width="2"/><ellipse cx="170" cy="130" rx="22" ry="7" fill="#FFC928" stroke="#1C1B1F" stroke-width="2"/><ellipse cx="170" cy="122" rx="22" ry="7" fill="#FFC928" stroke="#1C1B1F" stroke-width="2"/>'+arr(170,82,170,108)+ '<ellipse cx="170" cy="78" rx="11" ry="4" fill="#FFC928" stroke="#1C1B1F" stroke-width="2"/>','Progres kas naik, koin masuk')
r4=row('Tagihan Kas',['Kartu terbang ke chat (parabola)','Bubble WhatsApp + centang ganda','Stempel LUNAS menekan bukti','Progres bar naik, koin masuk'],[t1,t2,t3,t4])
# ---------- 30 ----------
def ucard(x,y,fill='#fff',op=1,w=160): return card(x,y,w,28,fill,INK,2,9,op)+circ(x+16,y+14,8,Y,INK,1.6,op)+bars(x+32,y+8,w-50,2,8,'#CFC6AE')
m1=frame('0,0 s',1,ucard(46,34)+ucard(46,70)+ucard(46,106)+arr(18,48,40,48)+arr(18,84,40,84)+arr(18,120,40,120),'Kartu pengguna berbaris')
m2=frame('0,5 s',2,card(54,64,64,34,'#CBEED6',G,2.5,17)+T(86,86,'Siswa',11,700,'#1E7A45')+card(124,64,64,34,'#BCD4FF',B,2.5,17)+T(156,86,'Pengurus',10,700,'#1F55B0')+dp('M70 50 q50 -34 100 0',GR)+arr(164,44,172,52)+T(119,128,'putar 180°',10,600,GR),'Lencana role berputar 180°')
m3=frame('1,0 s',3,ic('lock',46,40,64,fill=Y,sw=1.6)+ic('key',126,50,52,fill='#fff',sw=1.8)+dp('M130 40 a30 30 0 0 1 36 6',GR,1.8)+arr(164,40,170,48)+card(60,128,118,20,INK,INK,1,10)+T(119,142,'Kuning-731',11,800,Y),'Kunci berputar, password baru')
m4=frame('1,4 s',4,ucard(24,34,'#fff',.4,130)+card(24,74,52,20,'#E6E2D6',GR,1.4,10)+T(50,88,'Nonaktif',9,700,GR)+blob(120,64,100,'sad'),'Akun nonaktif meredup, blob sedih')
r5=row('Manajemen Pengguna',['Kartu berbaris, stagger 80 ms','Lencana role: flip 180°','Kunci berputar saat reset','Nonaktif: meredup, blob sedih'],[m1,m2,m3,m4])
b1=frame('0,0 s',1,qr(70,32,14,10)+dp('M62 140 q57 12 114 0',GR,1.6),'Kode QR tersusun dari piksel')
b2=frame('0,6 s',2,qr(70,32,14,49)+'<line x1="64" y1="82" x2="174" y2="82" stroke="#2E9B5A" stroke-width="3"/><rect x="64" y="26" width="110" height="110" rx="6" fill="none" stroke="#2F6FDB" stroke-width="2.5" stroke-dasharray="22 14"/>'+arr(190,70,190,50)+arr(190,96,190,118),'Garis pemindai menyapu QR')
code='WL4K9QH2'
b3=frame('1,1 s',3,T(119,52,'Kode pairing 8 karakter',10,600,GR)+''.join(card(17+i*26,66,22,32,'#fff' if i<5 else '#F3ECD6',INK,2,6)+(T(28+i*26,88,code[i],14,800) if i<5 else '') for i in range(8))+'<rect x="146" y="72" width="2" height="20" fill="#2F6FDB"/>'+T(119,126,'ditautkan ke nomor bot',9,500,GR),'Kode pairing diketik satu per satu')
b4=frame('1,6 s',4,blob(70,44,100,'idle')+'<rect x="94" y="80" width="52" height="20" rx="9" fill="#1C1B1F"/><circle cx="108" cy="90" r="4.5" fill="#2E9B5A"/><circle cx="132" cy="90" r="4.5" fill="#2E9B5A"/><line x1="120" y1="46" x2="120" y2="34" stroke="#1C1B1F" stroke-width="2.5"/><circle cx="120" cy="30" r="5" fill="#2E9B5A" stroke="#1C1B1F" stroke-width="2"/>'+ic('wifi',176,36,26,G,sw=2.4)+circ(60,48,12,G,INK,2)+ic('check',54,42,12,'#fff',sw=3.2),'Terhubung: visor hijau menyala')
r6=row('Pengaturan Bot',['QR tersusun dari piksel','Garis pemindai naik-turun','Kode pairing diketik per karakter','Terhubung: visor hijau'],[b1,b2,b3,b4])
o1=frame('0,0 s',1,blob(66,38,100,'smile')+'<circle cx="176" cy="74" r="9" fill="#FFC928" stroke="#1C1B1F" stroke-width="2.5"/><path d="M150 96 q14 -10 20 -16" stroke="#1C1B1F" stroke-width="2.5" fill="none" stroke-linecap="round"/>'+dp('M186 56 q10 6 8 18',GR,1.8)+dp('M192 48 q14 10 12 26',GR,1.8)+bub(26,38,34,20,'Hai!','#fff',10),'Blob menyapa sambil melambai')
def chp(x,y,t,on=False): return card(x,y,98,30,'#FFF3C4' if on else '#fff',INK if on else '#CFC6AE',2.6 if on else 1.5,10)+T(x+49,y+19,t,8.2,700 if on else 500)
o2=frame('0,5 s',2,chp(16,40,'Tagihan kas saya',True)+chp(124,40,'Laporan kas saya')+chp(16,82,'Tagihan kas aku')+chp(124,82,'Laporan kas aku')+circ(65,55,40,'none',Y,1.6,.8)+arr(30,32,22,24)+arr(104,32,112,24),'Kalimat dipilih, chip pop')
o3=frame('1,0 s',3,ic('bot',166,66,42,fill='#EFE8D6',sw=1.8)+bub(14,94,100,24,'Tagihan kas saya','#D9FDD3',8.5)+dp('M60 90 q50 -64 100 -6',GR,1.8)+arr(152,78,160,86)+bub(84,26,84,22,'Tagihan kas saya','#D9FDD3',8),'Pesan terbang ke bot')
o4=frame('1,5 s',4,card(22,22,150,64,'#fff',INK,2.5,10)+T(36,42,'IX-H Hub',9,700,GR,'start')+T(36,58,'Nomor terhubung.',10.5,700,INK,'start')+card(36,66,40,14,'#DDF1E5',G,1.2,7)+T(56,76,'Valid',8,800,G)+ic('check-check',128,66,16,G,sw=3)+blob(132,92,84,'smile'),'Balasan bot, status Valid')
r7=row('Jabat Tangan Bot',['Blob melambai (±20°)','Chip kalimat terpilih pop','Gelembung terbang ke bot','Balasan bot: status Valid'],[o1,o2,o3,o4])
s1=frame('0,0 s',1,ic('bot',168,64,44,fill='#EFE8D6',sw=1.8)+bub(22,84,60,26,'STOP','#D9FDD3',12)+dp('M52 78 q60 -62 110 -2',GR,1.8)+arr(152,70,160,78)+arr(30,66,46,74),'Balasan STOP terbang ke bot')
s2=frame('0,5 s',2,ic('bot',78,50,84,fill='#D8D0B8',sw=1.8)+bub(86,16,60,22,'Baik.','#fff',10)+T(168,60,'z',13,700,GR)+T(178,48,'Z',16,700,GR),'Bot membalas satu pesan lalu diam')
s3=frame('1,0 s',3,ic('bell',79,34,80,fill=Y,sw=1.5)+dp('M60 62 q-14 22 0 44',GR)+dp('M178 62 q14 22 0 44',GR)+circ(158,52,6,R,INK,2)+card(78,128,82,20,'#FFEBA8','#D9A400',1.6,10)+T(119,142,'Berhenti',10,800,'#B88A00'),'Lonceng admin bergoyang')
s4=frame('1,5 s',4,blob(62,46,106,'smile')+bub(18,22,58,22,'MULAI','#D9FDD3',11)+sp(186,50,5)+sp(200,84,4,B)+sp(34,100,4)+circ(190,126,13,G,INK,2)+ic('check',184,120,12,'#fff',sw=3.2),'MULAI: blob terbangun, aktif lagi')
r8=row('Alur STOP & MULAI',['Pesan STOP terbang ke bot','Bot membalas satu pesan, diam','Lonceng admin + chip Berhenti','MULAI: blob terbangun'],[s1,s2,s3,s4])
CSS='''*{box-sizing:border-box;margin:0;padding:0}body{width:1440px;height:1180px;background:#FFF9E8;font-family:Poppins,sans-serif;color:#1C1B1F;position:relative;overflow:hidden}
.brand{position:absolute;left:96px;top:24px;background:#FFC928;border:3px solid #1C1B1F;border-radius:16px;padding:4px 20px;font-weight:800;font-size:31px}
.ttl{position:absolute;left:366px;top:30px;font-weight:700;font-size:28px}.sub{position:absolute;left:96px;top:96px;color:#6B6458;font-size:16px}
.rw{position:absolute;left:42px;display:flex;align-items:flex-start}
.lc{width:303px;height:193px;background:#FFF9E8;border:2.5px solid #EADFC8;border-radius:16px;padding:12px 14px;margin-right:16px;position:relative}
.pl{display:inline-block;background:#FFC928;border:2.5px solid #1C1B1F;border-radius:20px;padding:0 14px;font-weight:800;font-size:20px;margin-bottom:6px}
.lc ul{list-style:none}.lc li{font-size:13.5px;margin:6px 0;display:flex;gap:8px;align-items:center}.lc li:before{content:"";width:10px;height:10px;border-radius:50%;background:#FFC928;border:1.5px solid #1C1B1F;flex:none}
.ft2{position:absolute;left:14px;bottom:8px;font-size:11px;color:#6B6458}
.fr{width:238px;display:flex;flex-direction:column;align-items:center}.f{position:relative;width:238px;height:193px;background:#FFF9E8;border:3px solid #1C1B1F;border-radius:14px;overflow:hidden;box-shadow:0 3px 0 #EADFC8}
.f svg{display:block}.tm{position:absolute;left:8px;top:8px;background:#1C1B1F;color:#FFC928;border-radius:10px;padding:0 8px;font-size:10.5px;font-weight:700;line-height:17px}
.ix{position:absolute;right:8px;top:8px;width:19px;height:19px;border-radius:50%;background:#FFC928;border:2px solid #1C1B1F;font-size:10.5px;font-weight:700;text-align:center;line-height:15px}
.cp{font-size:13.5px;margin-top:10px;text-align:center;width:250px;line-height:1.25;font-weight:500}
.ar{width:23px;flex:none;height:0;align-self:flex-start;margin-top:93px}.ar:after{content:"";display:block;margin-left:5px;border-left:12px solid #1C1B1F;border-top:8px solid transparent;border-bottom:8px solid transparent;margin-top:-8px}
.fo{position:absolute;left:0;right:0;top:1146px;text-align:center;font-size:13px;color:#6B6458}'''
TRI='<svg style="position:absolute;left:0;top:0" width="260" height="260"><polygon points="0,0 190,0 0,190" fill="#FFC928"/><polygon points="0,0 86,0 0,86" fill="#1C1B1F"/></svg><svg style="position:absolute;right:0;bottom:0" width="260" height="260"><polygon points="260,70 260,260 70,260" fill="#FFC928"/><polygon points="260,170 260,260 170,260" fill="#1C1B1F"/></svg>'
def page(title,rows,out):
    body=''.join(r.replace('class="rw"',f'class="rw" style="top:{152+i*245}px"') for i,r in enumerate(rows))
    html=f'<html><head><meta charset="utf-8"><style>{CSS}</style></head><body>{TRI}<div class="brand">IX-H Hub 2.0</div><div class="ttl">{title}</div><div class="sub">Storyboard animasi per fitur baru • maskot BLOB orisinal • selaras 14 dan 15</div>{body}<div class="fo">Sumbu kecil kiri-bawah tiap frame = gizmo XYZ three.js (merah X, hijau Y, biru Z) • Poppins • Kuning #FFC928</div></body></html>'
    open(out.replace('.jpg','.html'),'w').write(html)
    with sync_playwright() as p:
        b=p.chromium.launch();pg=b.new_page(viewport={'width':1440,'height':1180},device_scale_factor=2);pg.set_content(html);pg.wait_for_timeout(400);pg.screenshot(path=out,type='jpeg',quality=93);b.close()
page('Animasi three.js: Fitur Baru (1/2)',[r1,r2,r3,r4],'/home/claude/w29/29_animasi_threejs_fitur_baru_1.jpg')
page('Animasi three.js: Fitur Baru (2/2)',[r5,r6,r7,r8],'/home/claude/w29/30_animasi_threejs_fitur_baru_2.jpg')
