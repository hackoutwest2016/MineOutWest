package com.spotify.mineoutwest;

import java.util.List;

import com.google.common.collect.Lists;
import net.minecraft.util.SoundEvent;

public class Sounds {

  public static List<ScheduledSound> SCHEDULE;

  /**
   * Run before anything else. Read your config, create blocks, items, etc, and register them with the GameRegistry
   */
  public static void preInit() {
    System.out.println("Sound pre init");

    EventConfigParser eventConfigParser = new EventConfigParser();

    SCHEDULE = eventConfigParser.loadFromConfig();;
  }

  public static class ScheduledSound {
    private final SoundEvent event;
    private final long time;
    private long lastStarted = -1;
    private final int x;
    private final int y;
    private final int z;

    public ScheduledSound(SoundEvent event, long time, int x, int y, int z) {
      this.event = event;
      this.time = time;
      this.x = x;
      this.y = y;
      this.z = z;
    }

    public SoundEvent getEvent() {
      return event;
    }

    public long getTime() {
      return time;
    }

    public int getX() {
      return x;
    }

    public int getY() {
      return y;
    }

    public int getZ() {
      return z;
    }

    public long getLastStarted() {
      return lastStarted;
    }

    public void setLastStarted(long lastStarted) {
      this.lastStarted = lastStarted;
    }

    @Override
    public String toString() {
      return "ScheduledSound{" +
              "event=" + event.getRegistryName() +
              ", lastStarted=" + lastStarted +
              ", time=" + time +
              ", x=" + x +
              ", y=" + y +
              ", z=" + z +
              '}';
    }
  }

  public static List<ScheduledSound> getScheduledSound(long worldTime) {
    long normTime = worldTime % 72000; // 3 days

    List<ScheduledSound> toPlay = Lists.newArrayList();
    if (SCHEDULE != null) {
      for (Sounds.ScheduledSound sound : Sounds.SCHEDULE) {
        long timeToStart = normTime - sound.getTime();
        if (timeToStart > 0 && timeToStart < 10*20 && (sound.getLastStarted() == -1 || worldTime - sound.getLastStarted() > 30*20)) {

          toPlay.add(sound);
          sound.setLastStarted(worldTime);
        }
      }
    }
    return toPlay;
  }
}
