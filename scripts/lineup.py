import json
import optparse
import os
import re
import requests
import subprocess
import sys

def is_safe(s):
    return s.isalnum() and all(ord(c) < 128 for c in s)

def safestr(s):
    return "".join(x if is_safe(x) else '_' for x in s).lower()

def is_safe(s):
    return s.isalnum() and all(ord(c) < 128 for c in s)

def safestr(s):
    return "".join(x if is_safe(x) else '_' for x in s).lower()

def downloadPreview(url, path):
    r = requests.get(url, stream=True)
    if r.status_code == 200:
        with open(path, 'wb') as f:
            for chunk in r:
                f.write(chunk)

def getInfo(track_id):
    url = 'https://api.spotify.com/v1/tracks/%s'%(track_id,)
    print url
    r = requests.get(url)
    j = r.json()
    return (j['name'], j['preview_url'])

def convertToOgg(mp3, ogg):
    subprocess.call(['ffmpeg', '-i', mp3, '-y', '-codec:a', 'libvorbis', ogg])

def download(download_folder, lineup):
    tracks = []
    for act in lineup:
        for t in act['tracks']:
            tracks.append((act['artist'], t))
    rexp = re.compile(r"https://open.spotify.com/track/(.*)")
    for artist, track in tracks:
        m = rexp.match(track)
        if m:
            track_name, url = getInfo(m.group(1))
            if not url:
                print "No preview url for", artist, track_name, track
                continue
            artist_safe = safestr(artist)
            track_safe = safestr(track_name)
            try:
                os.makedirs(os.path.join(download_folder, artist_safe))
            except OSError:
                pass
            track_path_no_ext = os.path.join(download_folder, artist_safe, track_safe)
            downloadPreview(url, track_path_no_ext + ".mp3")
            convertToOgg(track_path_no_ext + ".mp3", track_path_no_ext + ".ogg")
            os.remove(track_path_no_ext + ".mp3")

def makeEvents(ogg_folder, lineup):
    events = {}
    for act in lineup:
        rexp = re.compile(r"(\d\d)\.(\d\d)")
        days = {unicode("torsdag", "latin1"): 0, unicode("fredag", "latin1"): 1, unicode("l\xf6rdag", "latin1"): 2}
        day_ix = days[act['day']]
        m = rexp.match(act['time'])
        event = (int(m.group(1), 10), int(m.group(2), 10), act['artist'], act['stage'])
        if day_ix in events:
            events[day_ix].append(event)
        else:
            events[day_ix] = [event]
    tick_events = []
    for d, day_events in sorted(events.items()):
        sorted_day_events = sorted(day_events)
        first = sorted_day_events[0]
        last = sorted_day_events[-1]
        first_tick = first[0] * 1000  + first[1] * 1000 / 60
        last_tick = last[0] * 1000 + last[1] * 1000 / 60
        for ev in sorted_day_events:
            tick = ev[0] * 1000 + ev[1] * 1000 / 60
            scaled_tick = int(24000 * float(tick - first_tick) / float(last_tick - first_tick))
            artist_safe = safestr(ev[2])
            folder = os.path.join(ogg_folder, artist_safe)
            try:
                files = [(artist_safe + "_" + f[0:-4], os.path.join(artist_safe, f)) for f in os.listdir(folder) if os.path.isfile(os.path.join(folder, f))]
            except OSError:
                print "No files for", artist_safe
                continue
            # Max two files
            for ix, f in enumerate(files[0:2]):
                a_id, a_f = f
                tick_events.append((24000 * d + scaled_tick + 600 * ix, ev[2], a_id, a_f, ev[3]))
        audio = {}
        events = []
        used_files = []
        for event in sorted(tick_events):
            tick, artist, a_id, a_f, stage = event
            name = "mineoutwest:%s"%(a_f[0:-4])
            audio[a_id] = {"category": "record", "sounds": [{"name": name, "stream": True}]}
            events.append({"tick": tick, "id": a_id, "stage": stage, "artist": artist})
        with open("sounds.json", "wb") as f:
            json.dump(audio, f, sort_keys=True, indent=2, separators=(',', ': '))
        with open("events.json", "wb") as f:
            json.dump(events, f, sort_keys=True, indent=2, separators=(',', ': '))

if __name__=="__main__":
    parser = optparse.OptionParser(usage='usage: read the code')
    parser.add_option('-l', '--lineup', action='store', type="string")
    parser.add_option('-s', '--sounds', action='store', type="string")
    parser.add_option('-d', '--download', action='store_true', default=False)
    options, remainder = parser.parse_args()
    if not options.lineup:
        parser.error("--lineup is required")
    if not options.sounds:
        parser.error("--sounds is required")
    with open(options.lineup, "rb") as f:
        lineup = json.load(f)
        if options.download:
            download(options.sounds, lineup)
        makeEvents(options.sounds, lineup)
