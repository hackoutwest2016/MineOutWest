package com.mineoutwest.block;

import com.google.common.base.Predicate;
import com.mineoutwest.Stages;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * Created by ludde on 2016-08-09.
 */
public class Actions {

    static Artist spawnArtist(World world, double x, double y, double z, int facing) {
        BlockPos worldSpawnPoint = new BlockPos(x, y, z);
        Artist villager = new Artist(world);
        villager.setLocationAndAngles(worldSpawnPoint.getX(), worldSpawnPoint.getY(), worldSpawnPoint.getZ(), 30, 30);
        villager.setCustomNameTag("M83");
        villager.setAlwaysRenderNameTag(true);
        world.spawnEntityInWorld(villager);

        villager.getArtistAI().setAllowedRect(x-2, y-2, z-2,
                x+2, y+2, z+2);

        villager.getArtistAI().setFacePitch(facing);

        return villager;
    }

    static void spawnArtistsOnLine(World world, double x1, double z1, double x2, double z2, double y, int n, int facing) {
        for(int i = 0; i < n; i++) {
            double x = x1 + (x2 - x1) * (i*2 + 1) / (2*n);
            double z = z1 + (z2 - z1) * (i*2 + 1) / (2*n);
            spawnArtist(world, x, y, z, facing);
        }
    }

    public static void spawnArtistsAzalea(World world) {
        spawnArtist(world, 126, 68, 112, 0);
        spawnArtist(world, 122, 68, 112, 0);
        spawnArtist(world, 118, 68, 112, 0);
        spawnArtist(world, 114, 68, 112, 0);
    }

    public static double interpolate(double a, double b, double f) {
        return a + (b - a) * f;
    }

    public static Vec3d interpolate(BlockPos a, BlockPos b, double f) {
        return new Vec3d(interpolate(a.getX(), b.getX(), f),
                            interpolate(a.getY(), b.getY(), f),
                            interpolate(a.getZ(), b.getZ(), f));
    }

    public static void spawnArtistStage(World world, Stages.Stage stage) {
        Vec3d p1 = interpolate(stage.stage.FL, stage.stage.BL, 0.2);
        Vec3d p2 = interpolate(stage.stage.FR, stage.stage.BR, 0.2);
        spawnArtistsOnLine(world, p1.xCoord, p1.zCoord, p2.xCoord, p2.zCoord, p1.yCoord, 4, stage.FACING);
    }

    public static int killAllArtists(World world) {
        int nKills = 0;
        for (Entity entity : world.getEntities(Artist.class, new Predicate<Artist>() {
            @Override
            public boolean apply(@Nullable Artist input) {
                return true;
            }
        })) {
            entity.onKillCommand();
            nKills++;
        }
        return nKills;
    }


    static Guest spawnGuest(World world, int x, int y, int z) {
        BlockPos worldSpawnPoint = new BlockPos(x, y, z);
        Guest guest = new Guest(world);
        guest.setLocationAndAngles(worldSpawnPoint.getX(), worldSpawnPoint.getY(), worldSpawnPoint.getZ(), 30, 30);
        world.spawnEntityInWorld(guest);

        guest.getGuestAI().setAllowedRect(x-10, y-10, z-10,
                x+10, y+10, z+10);

        return guest;
    }

    public static void spawnGuests(World world) {
        int count = 0;
        for (int i = 1; i < 100; i++) {
            for (int j = 1; j < 100; j++) {
                Guest guest = spawnGuest(world, 126, 68, 112);
                count++;
                guest.setCustomNameTag("Hipster" + count);
            }
        }
    }

}
