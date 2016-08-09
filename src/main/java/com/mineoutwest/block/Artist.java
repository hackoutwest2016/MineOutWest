package com.mineoutwest.block;

import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.world.World;

public class Artist extends EntityVillager {
    public Artist(World world) {
        super(world);


    }

    @Override
    protected void initEntityAI() {
        mArtistAI = new ArtistAI(this, 0.6D);
        this.tasks.addTask(1, mArtistAI);
    }

    public ArtistAI getArtistAI() { return mArtistAI; }

    ArtistAI mArtistAI;
};