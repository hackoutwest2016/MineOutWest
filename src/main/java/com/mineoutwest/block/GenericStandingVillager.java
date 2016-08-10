package com.mineoutwest.block;

import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GenericStandingVillager extends EntityVillager {

    public GenericStandingVillager(World world) {
        super(world);
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(9,
            new EntityAIWatchClosest2(this,
                EntityPlayer.class,
                3.0F,
                1.0F));
    }

};