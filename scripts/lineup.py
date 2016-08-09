import csv
import os
import re
import sys
import json

def is_safe(s):
    return s.isalnum() and all(ord(c) < 128 for c in s)

def safestr(s):
    return "".join(x if is_safe(x) else '_' for x in s)

def main(ogg_folder, lineup):
    with open(lineup, 'rb') as csvfile:
        r = csv.reader(csvfile, delimiter=';')
        rexp = re.compile(r"(\d\d)\.(\d\d)")
        events = {}
        for ix, row in enumerate(r):
            if ix == 0:
                continue
            artist, day, ts, stage = row
            days = {"torsdag": 0, "fredag": 1, "l\xf6rdag": 2}
            day_ix = days[day]
            m = rexp.match(ts)
            event = (int(m.group(1), 10), int(m.group(2), 10), artist, safestr(stage))
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
                scaled_tick = int(13000 * float(tick - first_tick) / float(last_tick - first_tick))
                artist_safe = safestr(ev[2])
                folder = os.path.join(ogg_folder, artist_safe)
                try:
                    files = [(artist_safe + "_" + f[0:-4], os.path.join(artist_safe, f)) for f in os.listdir(folder) if os.path.isfile(os.path.join(folder, f))]
                except OSError:
                    print "No files for", artist_safe
                    continue
                for ix, f in enumerate(files[0:2]):
                    a_id, a_f = f
                    tick_events.append((24000 * d + scaled_tick + 600 * ix, a_id, a_f, ev[3]))
        audio = {}
        events = []
        for event in sorted(tick_events):
            tick, a_id, a_f, stage = event
            name = "mineoutwest:%s"%(a_f)
            audio[a_id] = {"category": "record", "sounds": [{"name": name, "stream": True}]}
            events.append({"tick": tick, "id": a_id, "stage": stage})
        with open("sounds.json", "wb") as f:
            json.dump(audio, f, ensure_ascii=True)
        with open("events.json", "wb") as f:
            json.dump(events, f, ensure_ascii=True)


if __name__=="__main__":
    main(sys.argv[1], sys.argv[2])
