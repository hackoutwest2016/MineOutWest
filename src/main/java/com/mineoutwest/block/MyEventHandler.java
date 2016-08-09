package com.mineoutwest.block;


import java.util.List;

import com.mineoutwest.Sounds;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class MyEventHandler {

  @SubscribeEvent
  public void world(TickEvent.WorldTickEvent event) {
      long worldTime = event.world.getWorldTime();

      // check for valid event time (NPE hell otherwise)
      if (worldTime > 0) {
        List<Sounds.ScheduledSound> sounds = Sounds.getScheduledSound(worldTime);
        for (Sounds.ScheduledSound sound : sounds) {
          event.world.playSound(null, sound.getX(), sound.getY(), sound.getZ(), sound.getEvent(), SoundCategory.MASTER, 4, 1);
          System.out.println("Started sound " + sound + " at " + worldTime);
        }
      }
  }
}
