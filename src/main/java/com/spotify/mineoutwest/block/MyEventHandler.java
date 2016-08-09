package com.spotify.mineoutwest.block;


import java.util.List;

import com.spotify.mineoutwest.Sounds;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MyEventHandler {

  @SubscribeEvent
  public void world(WorldEvent event) {
    long worldTime = event.getWorld().getWorldTime();

    // check for valid event time (NPE hell otherwise)
    if (worldTime > 0) {
      List<Sounds.ScheduledSound> sounds = Sounds.getScheduledSound(worldTime);
      for (Sounds.ScheduledSound sound : sounds) {
        event.getWorld().playSound(null, sound.getX(), sound.getY(), sound.getZ(), sound.getEvent(), SoundCategory.MASTER, 2, 1);
        System.out.println("Started sound " + sound + " at " + worldTime);
      }
    }
  }
}
