package com.spotify.mineoutwest.block;


import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerSetSpawnEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ArtistSpawner {

    private boolean started = false;
    private int counter = 0;
    private BlockPos worldSpawnPoint;

    public ArtistSpawner() {
    }

    @SubscribeEvent
    public void pickupItem(EntityItemPickupEvent event) {
        System.out.println("Item picked up!");
    }

    @SubscribeEvent
    public void foo(WorldEvent.Load event) {
        World world = event.getWorld();
        if (!world.isRemote) {
            worldSpawnPoint = world.getSpawnPoint();
        } else {
            EntityVillager villager = new EntityVillager(world);
            villager.setLocationAndAngles(worldSpawnPoint.getX() + 2, worldSpawnPoint.getY(), worldSpawnPoint.getZ(), 30, 30);
            System.out.println("Villager's AI is disbaled: " + villager.isAIDisabled());
            world.spawnEntityInWorld(villager);
            System.out.println("Villager spawned at " + (worldSpawnPoint.getX() - 2) + ", " + worldSpawnPoint.getY() + ", " + worldSpawnPoint.getZ());
            System.out.println("Villager's AI is disbaled: " + villager.isAIDisabled());
        }
    }

}
