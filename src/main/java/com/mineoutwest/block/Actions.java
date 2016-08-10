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

    static Artist spawnArtist(World world, double x, double y, double z, double movement, int facing) {
        BlockPos worldSpawnPoint = new BlockPos(x, y, z);
        Artist villager = new Artist(world);
        villager.setLocationAndAngles(worldSpawnPoint.getX(), worldSpawnPoint.getY(), worldSpawnPoint.getZ(), 30, 30);
        villager.setCustomNameTag("M83");
        villager.setAlwaysRenderNameTag(true);
        world.spawnEntityInWorld(villager);

        villager.getArtistAI().setAllowedRect(x-movement, y-movement, z-movement,
                x+movement, y+movement, z+movement);

        villager.getArtistAI().setFacePitch(facing);

        return villager;
    }

    static void spawnArtistsOnLine(World world, double x1, double z1, double x2, double z2, double y, double movement, int n, int facing) {
        for(int i = 0; i < n; i++) {
            double x = x1 + (x2 - x1) * (i*2 + 1) / (2*n);
            double z = z1 + (z2 - z1) * (i*2 + 1) / (2*n);
            spawnArtist(world, x, y, z, movement, facing);
        }
    }


    public static double interpolate(double a, double b, double f) {
        return a + (b - a) * f;
    }

    public static Vec3d interpolate(BlockPos a, BlockPos b, double f) {
        return new Vec3d(interpolate(a.getX(), b.getX(), f),
                            interpolate(a.getY(), b.getY(), f),
                            interpolate(a.getZ(), b.getZ(), f));
    }

    public static Vec3d interpolate(Vec3d a, Vec3d b, double f) {
        return new Vec3d(interpolate(a.xCoord, b.xCoord, f),
                interpolate(a.yCoord, b.yCoord, f),
                interpolate(a.zCoord, b.zCoord, f));
    }

    public static void spawnArtistStage(World world, Stages.Stage stage) {
        Vec3d p1 = interpolate(stage.stage.FL, stage.stage.BL, 0.4);
        Vec3d p2 = interpolate(stage.stage.FR, stage.stage.BR, 0.4);

        Vec3d s1 = interpolate(p1, p2, 0.2);
        Vec3d s2 = interpolate(p1, p2, 0.8);

        int num = stage == Stages.DUNGEN ? 2 : 4;
        spawnArtistsOnLine(world, s1.xCoord, s1.zCoord, s2.xCoord, s2.zCoord, s1.yCoord, stage.STAGEMOVEMENT, num, stage.FACING);
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
