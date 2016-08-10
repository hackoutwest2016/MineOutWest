package com.mineoutwest.speaker;

import java.util.List;

import com.google.common.collect.Lists;
import com.mineoutwest.EventConfigParser;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;

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
    private final BlockPos pos;
    public final String stage;
    public final String artist;

    public ScheduledSound(SoundEvent event, long time, BlockPos pos, String stage, String artist) {
      this.event = event;
      this.time = time;
      this.pos = pos;
      this.stage = stage;
      this.artist = artist;
    }

    public SoundEvent getEvent() {
      return event;
    }

    public long getTime() {
      return time;
    }

    public BlockPos getPos() {
      return pos;
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
              ", pos=" + pos +
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
