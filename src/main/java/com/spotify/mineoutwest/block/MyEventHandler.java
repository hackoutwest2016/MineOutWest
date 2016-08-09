package com.spotify.mineoutwest.block;


import java.util.List;

import com.spotify.mineoutwest.Sounds;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class MyEventHandler {

  private World world;

  @SubscribeEvent
  public void tick(TickEvent.ServerTickEvent event) {
    if (world != null) {
      long worldTime = world.getWorldTime();

      // check for valid event time (NPE hell otherwise)
      if (worldTime > 0) {
        List<Sounds.ScheduledSound> sounds = Sounds.getScheduledSound(worldTime);
        for (Sounds.ScheduledSound sound : sounds) {
          world.playSound(null, sound.getX(), sound.getY(), sound.getZ(), sound.getEvent(), SoundCategory.MASTER, 2, 1);
          System.out.println("Started sound " + sound + " at " + worldTime);
        }
      }
    }
  }

    @SubscribeEvent
  public void world(WorldEvent event) {
    this.world = event.getWorld();
  }
}
