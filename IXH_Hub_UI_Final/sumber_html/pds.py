from lib import *
from icons import convert, P, svg
import numpy as np
from PIL import Image
# ---------- konten baru ----------
def S(t): return f'<div style="display:inline-block;background:#FFC928;border:2px solid #1C1B1F;border-radius:10px;padding:2px 12px;font-weight:700;font-size:15px;margin-bottom:12px">{t}</div>'
CARD='background:#fff;border:1.5px solid #EADFC2;border-radius:16px;padding:16px 20px'
OK=f'<span style="display:inline-flex;width:20px;height:20px;border-radius:50%;background:{G};color:#fff;align-items:center;justify-content:center;font-size:12px">[[check]]</span>'
VW=f'<span style="display:inline-flex;width:20px;height:20px;border-radius:50%;background:#DCE6FA;color:{B};align-items:center;justify-content:center;font-size:12px">[[eye]]</span>'
NO='<span style="display:inline-flex;width:20px;height:20px;border-radius:50%;background:#EFE8D6;color:#6B6458;align-items:center;justify-content:center;font-size:12px">[[minus]]</span>'
rows=[('Beranda, absensi, jadwal, tugas: lihat data kelas',OK,OK,'own'),
('Absensi: ambil dan ubah',OK,OK,VW),
('Kas: catat transaksi, lihat riwayat dan ekspor',OK,OK,VW),
('Tagihan: buat, kirim pengingat, tandai lunas',OK,OK,NO),
('Tagihanku dan Kehadiranku (data sendiri)',OK,OK,OK),
('Pengumuman dan tugas: tambah/ubah',OK,OK,'tandai'),
('Panel Bot: kirim laporan/tagihan, status pengiriman',OK,OK,NO),
('Pengaturan Bot: QR/pairing, batas kirim, log gagal',OK,NO,NO),
('Template Pesan Bot dan restart server Pterodactyl',OK,NO,NO),
('Kelola Pengguna: role, reset password, cek nomor',OK,NO,NO),
('Pengaturan Kelas (kas, jatuh tempo, pengingat, batas)',OK,VW,VW),
('Log Aktivitas dan pergantian semester',OK,NO,NO),
('Profil sendiri dan persetujuan nomor WhatsApp',OK,OK,OK)]
def cell(c):
    if c=='own': return '<span class="chip cb">Data sendiri</span>'
    if c=='tandai': return '<span class="chip cg">Tandai selesai</span>'
    return c
tbl='<table style="width:100%;border-collapse:collapse;font-size:12.5px"><tr style="color:#6B6458;font-size:11.5px"><th style="text-align:left;padding:4px 0">Fitur</th><th style="width:96px">Admin</th><th style="width:96px">Pengurus</th><th style="width:130px">Siswa</th></tr>'+''.join(f'<tr style="border-top:1px solid #F1E8CF"><td style="padding:7px 0">{r[0]}</td><td style="text-align:center">{cell(r[1])}</td><td style="text-align:center">{cell(r[2])}</td><td style="text-align:center">{cell(r[3])}</td></tr>' for r in rows)+'</table><div style="display:flex;gap:18px;margin-top:10px;font-size:11px;color:#6B6458;align-items:center">'+OK+' Akses penuh '+VW+' Lihat saja '+NO+' Tidak ada akses</div>'
catatan=lambda items:'<ul style="margin:0 0 0 18px;font-size:12.5px;line-height:1.6">'+''.join(f'<li style="margin-bottom:5px">{i}</li>' for i in items)+'</ul>'
sec10=f'<div>{S("10. Matriks Hak Akses (satu sumber, semua storyboard mengacu ke sini)")}<div style="display:grid;grid-template-columns:1020px 1fr;gap:20px"><div style="{CARD}">{tbl}</div><div style="{CARD}"><b style="font-size:14px">Aturan hak akses</b><div style="height:8px"></div>'+catatan(['<b>Admin</b>: semua fitur, termasuk bot, template, pengguna, dan log.','<b>Pengurus</b> (bendahara): kirim tagihan dan laporan lewat Panel Bot, tandai lunas, catat kas dan absensi. <b>Tidak</b> bisa kelola pengguna, pengaturan bot, template, atau restart server.','<b>Siswa</b>: hanya melihat data miliknya; boleh menandai tugas selesai.','Hanya Admin yang mengubah role dan mereset password.','Setiap perubahan status lunas, absensi, role, dan hapus data tercatat di Log Aktivitas (26).','Layar yang tidak diizinkan menampilkan status terkunci atau “Khusus Admin”, bukan menghilang.'])+'</div></div></div>'
ico=['check','alert','lock','wifi','qr','copy','refresh','send','wallet','mail','bot','users','mega','school','cal','alarm','chart','smile','bell','book','phone','shield','trash','filter','search','download','history','eye','eyeoff','info','arrow','chev']
icons=''.join(f'<div style="width:54px;text-align:center"><div style="font-size:24px">[[{n}]]</div><div style="font-size:8.5px;color:#6B6458;margin-top:2px">{n}</div></div>' for n in ico)
mas=''.join(f'<div style="text-align:center;width:118px">{blob(k,76)}<div style="font-weight:700;font-size:11.5px">{t}</div><div style="font-size:9.5px;color:#6B6458;margin-top:1px">{d}</div></div>' for k,t,d in [('idle','Mata terbuka','Password tersembunyi'),('sleep','Mata menutup','Password terlihat'),('smile','Senyum','Berhasil, lunas'),('sad','Sedih','Gagal, bot terputus')])
sec11=f'<div style="display:grid;grid-template-columns:1fr 1fr;gap:20px"><div>{S("11. Aturan Ikon")}<div style="{CARD}"><div style="display:flex;flex-wrap:wrap;gap:6px 4px;margin-bottom:10px">{icons}</div>'+catatan(['<b>Tanpa emoji.</b> Semua ikon SVG garis: stroke 2, ujung bulat, viewBox 24.','Ukuran 16 / 20 / 24dp; warna mengikuti teks atau status (hijau, kuning, biru, merah).','Ikon dalam lingkaran kuning muda 34dp untuk daftar dan kartu.'])+f'</div></div><div>{S("12. Ekspresi Maskot di Login")}<div style="{CARD}"><div style="display:flex;justify-content:space-between;margin-bottom:8px">{mas}</div>'+catatan(['Password tersembunyi: mata <b>terbuka</b>. Tombol mata ditekan (terlihat): mata <b>menutup</b>.','Gagal atau bot terputus: sedih; berhasil atau lunas: senyum; state kosong: mata tertutup.','Berlaku di login, ganti password wajib, dan animasi three.js.'])+'</div></div></div>'
def c(t,b): return f'<div style="{CARD};padding:14px 16px"><div style="font-weight:700;font-size:13px;margin-bottom:8px">{t}</div>{b}</div>'
al=lambda cl,i,t:f'<div class="al {cl}" style="margin-bottom:5px;padding:6px 9px"><span>[[{i}]]</span><span>{t}</span></div>'
boxes=''.join(f'<div style="width:24px;height:32px;border:2px solid #1C1B1F;border-radius:8px;background:#fff;display:flex;align-items:center;justify-content:center;font-weight:700;font-size:14px">{c_}</div>'+('<b style="align-self:center;color:#6B6458">–</b>' if i==3 else '') for i,c_ in enumerate('WLAK9QH2'))
sec13=f'<div>{S("13. Komponen Tambahan")}<div style="display:grid;grid-template-columns:repeat(4,1fr);gap:20px">'+c('Peringatan (alert)',al('ab','info','Info: data diambil dari fitur lain')+al('ay','alert','Peringatan: bot hampir mencapai batas')+al('ar','wifioff','Bahaya: bot WhatsApp terputus')+al('ag','check','Berhasil: pesan tes terkirim'))+c('Tab, stepper, kode pairing','<div class="tabs"><div class="on">Pindai QR</div><div>Kode Pairing</div></div><div style="display:flex;align-items:center;gap:10px;margin:8px 0"><div class="ic" style="width:26px;height:26px">[[minus]]</div><b style="font-size:16px">60</b><div class="ic" style="width:26px;height:26px;background:#FFC928">[[plus]]</div><span class="t3">pesan/jam</span></div><div style="display:flex;gap:3px">'+boxes+'</div>')+c('Input dan chip jenis log','<div class="inp" style="background:#F6EFDB;margin-bottom:6px">Terkunci (hanya lihat) &nbsp; [[lock]]</div><div class="inp" style="border:1.5px solid #D94452;margin-bottom:8px">Variabel tidak dikenal</div><span class="chip cg">Lunas</span> <span class="chip cb">Absensi</span> <span class="chip cy">Role</span> <span class="chip cr">Hapus</span> <span class="chip cx">Berhenti</span>')+c('Dialog konfirmasi','<div style="border:2px solid #1C1B1F;border-radius:16px;padding:12px;text-align:center;box-shadow:0 4px 0 #1C1B1F"><div style="font-weight:700;font-size:12.5px">Simpan perubahan?</div><div class="t3" style="margin:3px 0 8px">Berlaku untuk pengingat berikutnya.</div><div style="display:flex;gap:8px"><div class="btn sec" style="flex:1;margin:0;height:34px">Batal</div><div class="btn pri" style="flex:1;margin:0;height:34px">Simpan</div></div></div>')+'</div></div>'
html=f'<html><head><meta charset="utf-8"><style>{CSS}body{{background:#FFF9E8}}</style></head><body style="padding:20px 49px 0 49px;display:flex;flex-direction:column;gap:26px">{sec10}{sec11}{sec13}</body></html>'
html=convert(html)
open('/home/claude/work/gen/00_tambahan.html','w').write(html)
with sync_playwright() as p:
    b=p.chromium.launch(); pg=b.new_page(viewport={'width':1615,'height':800},device_scale_factor=2); pg.set_content(html); pg.wait_for_timeout(400)
    pg.screenshot(path='/home/claude/work/00_tambahan.png',full_page=True); b.close()
new=Image.open('/home/claude/work/00_tambahan.png').convert('RGB'); print(new.size)
# ---------- gabung ----------
orig=Image.open('/home/claude/ixh/IXH_Hub_UI_Final/00_design_system.jpg').convert('RGB'); a=np.array(orig).astype(int); H,W=a.shape[:2]
ys,xs=np.mgrid[0:H,0:W]; v=H-ys
tri=(ys>=3700)&(xs>=2929+v-3)
yel=(np.abs(a-np.array([255,201,40])).sum(-1)<60); ink=(np.abs(a-np.array([28,27,31])).sum(-1)<80)
import cv2
m=(tri&(yel|ink)).astype('uint8')
md=cv2.dilate(m,np.ones((7,7),np.uint8)).astype(bool)&tri
white=(np.abs(a-255).sum(-1)<40); border=(np.abs(a-np.array([234,223,194])).sum(-1)<40); krem=(np.abs(a-np.array([255,249,232])).sum(-1)<14)
md&=~(white|border|krem); a[md|(m.astype(bool))]=[255,249,232]
cut=3925; top=Image.fromarray(a.astype('uint8')).crop((0,0,W,cut))
total=cut+new.height+360
out=Image.new('RGB',(W,total),(255,249,232)); out.paste(top,(0,0)); out.paste(new,(0,cut))
# footer + corner
from PIL import ImageDraw, ImageFont
h=total; reg=np.array(out.crop((2000,h-330,W,h))).astype(float); yy,xx=np.mgrid[h-330:h,2000:W]; vv=h-yy
d1=(xx-(2929+vv))/1.414; d2=(xx-(3088+vv))/1.414
ay=np.clip(d1+.5,0,1)[...,None]; ak=np.clip(d2+.5,0,1)[...,None]
o=np.array([255,249,232.])*(1-ay)+np.array([255,201,40.])*ay; o=o*(1-ak)+np.array([28,27,31.])*ak
out.paste(Image.fromarray(o.clip(0,255).astype('uint8')),(2000,h-330))
F="Android • Material 3 • Poppins • Kuning #FFC928 • Ink #1C1B1F • Krem #FFF9E8"
sz=26
for s_ in range(10,60):
    if ImageFont.truetype('/usr/share/fonts/truetype/google-fonts/Poppins-Regular.ttf',s_).getlength(F)>990: sz=s_-1; break
ImageDraw.Draw(out).text((2940,h-58),F,font=ImageFont.truetype('/usr/share/fonts/truetype/google-fonts/Poppins-Regular.ttf',sz),fill=(107,100,88),anchor='rs')
out.save('/home/claude/work/00_new.jpg',quality=93); print(out.size)
out.resize((1600,int(out.height*1600/out.width))).crop((0,int(out.height*1600/out.width)-1300,1600,int(out.height*1600/out.width))).save('/home/claude/prev/p00.png')
