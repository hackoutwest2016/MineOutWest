package com.mineoutwest;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.BlockPos;

/**
 * Created by Simon on 2016-08-09.
 */
public class Stages {

    public static final BlockPos LINNE = new BlockPos(75, 66, 335);
    public static final BlockPos AZALEA = new BlockPos(122, 66, 118);
    public static final BlockPos FLAMINGO = new BlockPos(140, 65, 212);
    public static final BlockPos DUNGEN = new BlockPos(161, 65, 43);

    public static class Stage {
        private BlockPos FL;
        private BlockPos FR;
        private BlockPos BL;
        private BlockPos BR;

        public Stage( BlockPos front_left, BlockPos front_right, BlockPos back_left, BlockPos back_right) {
            FL = front_left;
            FR = front_right;
            BL = back_left;
            BR = back_right;
        }
    }

    private static final BlockPos S1PFL = new BlockPos(99.065, 65, 119.145);
    private static final BlockPos S1PFR = new BlockPos(137.844, 65, 118.312);
    private static final BlockPos S1PBL = new BlockPos(84.564, 65, 145.904);
    private static final BlockPos S1PBR = new BlockPos(144.389, 65, 154.429);
    public static final Stage Stage1Pitch = new Stage(S1PFL, S1PFR, S1PBL, S1PBR);

    private static final BlockPos S1FL = new BlockPos(136.7, 68, 117.8);
    private static final BlockPos S1FR = new BlockPos(99.3, 68, 117.4);
    private static final BlockPos S1BL = new BlockPos(136.7, 68, 106.3);
    private static final BlockPos S1BR = new BlockPos(99.3, 68, 106.3);
    public static final Stage Stage1 = new Stage(S1FL, S1FR, S1BL, S1BR);
}
