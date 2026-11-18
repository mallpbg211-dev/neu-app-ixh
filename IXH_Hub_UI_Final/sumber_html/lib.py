from playwright.sync_api import sync_playwright
from icons import convert
G='#2E9B5A';Y='#D9A400';B='#2F6FDB';R='#D94452'
CSS='''
*{box-sizing:border-box;margin:0;padding:0}
body{width:1615px;background:#FFF9E8;font-family:Poppins,sans-serif;color:#1C1B1F;position:relative}
.tri{position:absolute;left:0;top:0}
.top{padding:28px 0 0 22px;display:flex;align-items:center;gap:16px}
.brand{background:#FFC928;border:2px solid #1C1B1F;border-radius:14px;padding:6px 18px;font-weight:700;font-size:25px}
.title{font-weight:700;font-size:23px}
.sub{padding:8px 0 0 22px;color:#6B6458;font-size:13px}
.grid{display:grid;grid-template-columns:repeat(4,390px);gap:46px 0;padding:46px 0 0 22px}
.col{position:relative;display:flex;justify-content:center}
.ph{width:350px;height:700px;border:3px solid #1C1B1F;border-radius:34px;background:#FFF9E8;box-shadow:0 6px 0 #EADFC2;position:relative;overflow:hidden;padding:0 12px}
.lab{position:absolute;top:-13px;left:50%;transform:translateX(-50%);background:#FFC928;border:2px solid #1C1B1F;border-radius:20px;padding:1px 14px;font-weight:700;font-size:11.5px;white-space:nowrap;z-index:30;line-height:19px}
.sb{display:flex;justify-content:space-between;font-size:10.5px;font-weight:600;padding:10px 6px 4px}
.bar{display:flex;align-items:center;gap:8px;font-weight:700;font-size:14.5px;padding:8px 2px 10px}
.card{background:#fff;border:1.5px solid #EADFC2;border-radius:14px;padding:10px 12px;margin-bottom:8px}
.row{display:flex;align-items:center;gap:10px}.sp{justify-content:space-between}
.ic{width:34px;height:34px;border-radius:50%;background:#FFEBA8;display:flex;align-items:center;justify-content:center;flex:none;font-size:15px}
.t1{font-weight:600;font-size:12.5px}.t2{font-size:10.5px;color:#6B6458}.t3{font-size:9.5px;color:#6B6458}
.btn{display:flex;align-items:center;justify-content:center;gap:6px;height:42px;border-radius:12px;font-weight:700;font-size:12.5px;margin-top:8px}
.pri{background:#FFC928;border:2px solid #1C1B1F;box-shadow:0 3px 0 #1C1B1F}
.sec{background:#fff;border:1.2px solid #1C1B1F}.dan{background:#fff;border:1.5px solid #D94452;color:#D94452}
.chip{display:inline-block;border-radius:20px;padding:1px 9px;font-size:9.5px;font-weight:700;white-space:nowrap}
.cg{background:#DDF1E5;color:#2E9B5A}.cy{background:#FFEBA8;color:#B88A00}.cb{background:#DCE6FA;color:#2F6FDB}.cr{background:#FADCDF;color:#D94452}.cx{background:#EFE8D6;color:#6B6458}
.al{border-radius:12px;padding:9px 11px;font-size:10.5px;margin-bottom:8px;display:flex;gap:8px;align-items:flex-start;border:1.2px solid}
.ar{background:#FADCDF;border-color:#D94452;color:#1C1B1F}.ab{background:#DCE6FA;border-color:#2F6FDB}.ay{background:#FFF0B8;border-color:#D9A400}.ag{background:#DDF1E5;border-color:#2E9B5A}
.tog{width:40px;height:22px;border-radius:12px;background:#FFC928;border:2px solid #1C1B1F;position:relative;flex:none}
.tog:after{content:"";position:absolute;right:2px;top:2px;width:14px;height:14px;border-radius:50%;background:#1C1B1F}
.tog.off{background:#EFE8D6}.tog.off:after{right:auto;left:2px;background:#6B6458}
.nav{position:absolute;left:0;right:0;bottom:0;height:54px;background:#FFF9E8;border-top:1px solid #EADFC2;display:flex;justify-content:space-around;align-items:center;font-size:8.5px;color:#6B6458}
.nav b{color:#1C1B1F}
.dim{position:absolute;inset:0;background:rgba(28,27,31,.5);z-index:10}
.sheet{position:absolute;left:0;right:0;bottom:0;background:#fff;border-radius:22px 22px 0 0;padding:14px 16px 16px;z-index:20;border-top:2px solid #1C1B1F}
.dlg{position:absolute;left:14px;right:14px;top:50%;transform:translateY(-50%);background:#fff;border:2px solid #1C1B1F;border-radius:20px;padding:16px;z-index:20;text-align:center;box-shadow:0 5px 0 #1C1B1F}
.wa{background:#EADFC8;border-radius:12px;padding:8px}
.bub{background:#fff;border-radius:3px 12px 12px 12px;padding:8px 10px;font-size:10px;line-height:1.5}
.rules{background:#fff;border:2.5px solid #1C1B1F;border-radius:16px;padding:16px 18px;box-shadow:0 4px 0 #EADFC2}
.rules h3{font-size:15px;margin-bottom:8px}.rules li{font-size:11.5px;margin:0 0 6px 18px;line-height:1.45}.rules li::marker{color:#FFC928}
.foot{text-align:right;padding:28px 150px 36px 0;color:#6B6458;font-size:12.5px}
.tabs{display:flex;background:#EFE8D6;border-radius:12px;padding:3px;margin-bottom:8px}.tabs div{flex:1;text-align:center;font-size:11px;padding:6px 0;border-radius:10px;color:#6B6458}.tabs .on{background:#FFC928;border:1.5px solid #1C1B1F;color:#1C1B1F;font-weight:700}
.inp{border:1.2px solid #EADFC2;background:#fff;border-radius:12px;padding:9px 12px;font-size:11.5px}
.lbl{font-size:10px;color:#6B6458;margin:6px 0 3px}
'''
def blob(kind='idle',s=70):
    eyes={'idle':'<circle cx="38" cy="50" r="3.2"/><circle cx="62" cy="50" r="3.2"/>',
    'smile':'<path d="M33 51q5-6 10 0M57 51q5-6 10 0" fill="none" stroke="#1C1B1F" stroke-width="3" stroke-linecap="round"/>',
    'sleep':'<path d="M33 50q5 5 10 0M57 50q5 5 10 0" fill="none" stroke="#1C1B1F" stroke-width="3" stroke-linecap="round"/>',
    'sad':'<circle cx="38" cy="50" r="3.2"/><circle cx="62" cy="50" r="3.2"/><path d="M29 44l12-4M71 44l-12-4" stroke="#1C1B1F" stroke-width="2.5" stroke-linecap="round"/>'}[kind]
    mouth={'idle':'<path d="M45 60q5 4 10 0" fill="none" stroke="#1C1B1F" stroke-width="2.5" stroke-linecap="round"/>','smile':'<path d="M42 59q8 10 16 0z" fill="#D94452" stroke="#1C1B1F" stroke-width="2.5" stroke-linejoin="round"/>','sleep':'','sad':'<path d="M44 65q6-6 12 0" fill="none" stroke="#1C1B1F" stroke-width="2.5" stroke-linecap="round"/>'}[kind]
    return f'<svg width="{s}" height="{s}" viewBox="0 0 100 100"><ellipse cx="50" cy="90" rx="26" ry="4" fill="#EADFC2"/><path d="M50 20q-2-10 6-14" fill="none" stroke="#1C1B1F" stroke-width="3" stroke-linecap="round"/><path d="M50 18C28 18 14 36 14 58c0 20 14 32 36 32s36-12 36-32c0-22-14-40-36-40z" fill="#FFC928" stroke="#1C1B1F" stroke-width="3.5"/><ellipse cx="34" cy="36" rx="7" ry="4" fill="#fff" opacity=".6" transform="rotate(-30 34 36)"/><circle cx="29" cy="58" r="5" fill="#F4A6B0" opacity=".8"/><circle cx="71" cy="58" r="5" fill="#F4A6B0" opacity=".8"/><g fill="#1C1B1F">{eyes}</g>{mouth}</svg>'
def phone(label,body,nav=None,over=''):
    n=''
    if nav is not None:
        items=['Beranda','Absensi','Kas','Jadwal','Tugas']
        n='<div class="nav">'+''.join(f'<div>{"<b>"+i+"</b>" if i==nav else i}</div>' for i in items)+'</div>'
    return f'<div class="col"><div class="lab">{label}</div><div class="ph"><div class="sb"><span>9:41</span><span>▂▄▆ ▭</span></div>{body}{n}{over}</div></div>'
def bar(t,right=''): return f'<div class="bar"><span style="font-size:17px">←</span><span style="flex:1">{t}</span>{right}</div>'
def rules(items,span=2,title='Aturan Bisnis'):
    return f'<div style="grid-column:span {span};padding:0 20px 0 0"><div class="rules"><h3>{title}</h3><ul>'+''.join(f'<li>{i}</li>' for i in items)+'</ul></div></div>'
def page(title,sub,cells):
    tri='<svg class="tri" width="110" height="110"><polygon points="0,0 72,0 0,72" fill="#FFC928"/><polygon points="0,0 34,0 0,34" fill="#1C1B1F"/></svg>'
    tri2='<svg style="position:absolute;right:0;bottom:0" width="110" height="110"><polygon points="110,0 110,110 0,110" fill="#FFC928"/><polygon points="110,44 110,110 44,110" fill="#1C1B1F"/></svg>'
    foot='Android • Material 3 • Poppins • Kuning #FFC928 • Ink #1C1B1F • Krem #FFF9E8'
    return f'<html><head><meta charset="utf-8"><style>{CSS}</style></head><body>{tri}{tri2}<div class="top"><div class="brand">IX-H Hub 2.0</div><div class="title">{title}</div></div><div class="sub">{sub}</div><div class="grid">{"".join(cells)}</div><div class="foot">{foot}</div></body></html>'
def render(html,out,name):
    html=convert(html)
    open(f'/home/claude/work/gen/{name}.html','w').write(html)
    with sync_playwright() as p:
        b=p.chromium.launch(); pg=b.new_page(viewport={'width':1615,'height':800},device_scale_factor=2)
        pg.set_content(html); pg.wait_for_timeout(400)
        pg.screenshot(path=out,full_page=True,type='jpeg',quality=93); b.close()
