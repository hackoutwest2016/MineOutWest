package com.spotify.mineoutwest.block;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Created by ludde on 2016-08-09.
 */
public class Actions {

    static Artist spawnArtist(World world, int x, int y, int z) {
        BlockPos worldSpawnPoint = new BlockPos(x, y, z);
        Artist villager = new Artist(world);
        villager.setLocationAndAngles(worldSpawnPoint.getX(), worldSpawnPoint.getY(), worldSpawnPoint.getZ(), 30, 30);
        world.spawnEntityInWorld(villager);

        villager.getArtistAI().setAllowedRect(x-2, y-2, z-2,
                x+2, y+2, z+2);

        return villager;
    }

    public static void spawnArtists(World world) {
        spawnArtist(world, 126, 68, 112);
        spawnArtist(world, 122, 68, 112);
        spawnArtist(world, 118, 68, 112);
        spawnArtist(world, 114, 68, 112);
    }
}
