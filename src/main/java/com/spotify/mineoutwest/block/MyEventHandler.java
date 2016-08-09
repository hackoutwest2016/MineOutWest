package com.spotify.mineoutwest.block;


import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerSetSpawnEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class MyEventHandler {

  private final SoundEvent sound;
  private boolean started = false;
  private int counter = 0;

  public MyEventHandler(SoundEvent sound) {
    this.sound = sound;
  }

  @SubscribeEvent
  public void pickupItem(EntityItemPickupEvent event) {
    System.out.println("Item picked up!");
  }

  @SubscribeEvent
  public void foo(PlayerSetSpawnEvent event) {
    System.out.println("!!!!!! " + event);
    //event.getEntityPlayer().playSound(sound, 1.0f, 1.0f);
    event.getEntity().playSound(sound, 1, 1);
  }

  @SubscribeEvent
  public void world(WorldEvent event) {
    System.out.println("!!!!!! world " + event);

    counter++;
    if (counter % 100 == 0) {
      //PositionedSoundRecord mySoundRes = PositionedSoundRecord.getMusicRecord(sound);
      //Minecraft.getMinecraft().getSoundHandler().playSound(mySoundRes);
      event.getWorld().playSound(null, -173, 72, 241, sound, SoundCategory.MASTER, 1, 1);

      //started = true;
      System.out.println("!!!!!! Started sound");
    }
  }
}
