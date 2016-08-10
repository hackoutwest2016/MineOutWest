package com.mineoutwest.block;

import com.mineoutwest.Stages;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.world.World;

public class Guest extends EntityVillager {
    public Guest(World world) {
        super(world);
    }

    @Override
    protected void initEntityAI() {
        mGuestAIGoToStage = new GuestAIGoToStage(this, Stages.LINNE);
        this.tasks.addTask(1, mGuestAIGoToStage);
    }

    public GuestAIGoToStage getGuestAI() { return mGuestAIGoToStage; }

    GuestAIGoToStage mGuestAIGoToStage;
};