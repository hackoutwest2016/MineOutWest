package com.mineoutwest.block;

import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.world.World;

public class Guest extends EntityVillager {
    public Guest(World world) {
        super(world);
    }

    @Override
    protected void initEntityAI() {
        mGuestAI = new GuestAI(this, 0.6D);
        this.tasks.addTask(1, mGuestAI);
    }

    public GuestAI getGuestAI() { return mGuestAI; }

    GuestAI mGuestAI;
};