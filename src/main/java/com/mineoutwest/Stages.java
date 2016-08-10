package com.mineoutwest;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.BlockPos;

/**
 * Created by Simon on 2016-08-09.
 */
public class Stages {

    //public static final BlockPos LINNE = new BlockPos(75, 66, 335);
    //public static final BlockPos AZALEA = new BlockPos(122, 66, 118);
    //public static final BlockPos FLAMINGO = new BlockPos(140, 65, 212);
    //public static final BlockPos DUNGEN = new BlockPos(161, 65, 43);

    public static class Stage {
        private String NAME;
        private BlockPos CR;
        private BlockPos FL;
        private BlockPos FR;
        private BlockPos BL;
        private BlockPos BR;

        private BlockPos CROWD_FL;
        private BlockPos CROWD_FR;
        private BlockPos CROWD_BL;
        private BlockPos CROWD_BR;

        public Stage(String name,
            BlockPos center,
            BlockPos front_left,
            BlockPos front_right,
            BlockPos back_left,
            BlockPos back_right,
            BlockPos crowd_fl,
            BlockPos crowd_fr,
            BlockPos crowd_bl,
            BlockPos crowd_br) {
            NAME = name;
            CR = center;
            FL = front_left;
            FR = front_right;
            BL = back_left;
            BR = back_right;
            CROWD_FL = crowd_fl;
            CROWD_FR = crowd_fr;
            CROWD_BL = crowd_bl;
            CROWD_BR = crowd_br;
        }
      public BlockPos getCenter() {
        return this.CR;
      }
    }

    public static final BlockPos AZALEASTAGECR = new BlockPos(122, 66, 118);
    private static final BlockPos AZALEASTAGEFL = new BlockPos(135, 68, 106);
    private static final BlockPos AZALEASTAGEFR = new BlockPos(110, 68, 106);
    private static final BlockPos AZALEASTAGEBL = new BlockPos(135, 68, 106);
    private static final BlockPos AZALEASTAGEBR = new BlockPos(110, 68, 106);
    private static final BlockPos AZALEACROWDFL = new BlockPos(110, 65, 118);
    private static final BlockPos AZALEACROWDFR = new BlockPos(135, 65, 118);
    private static final BlockPos AZALEACROWDBL = new BlockPos(110, 65, 138);
    private static final BlockPos AZALEACROWDBR = new BlockPos(135, 65, 138);
    public static final Stage AZALEA = new Stage("AZALEA",
        AZALEASTAGECR,
        AZALEASTAGEFL,
        AZALEASTAGEFR,
        AZALEASTAGEBL,
        AZALEASTAGEBR,
        AZALEACROWDFL,
        AZALEACROWDFR,
        AZALEACROWDBL,
        AZALEACROWDBR);

  private static final BlockPos FLAMINGOSTAGECR = new BlockPos(140, 65, 212);
  // todo update coorinates
  private static final BlockPos FLAMINGOSTAGEFL = new BlockPos(132, 68, 219);
  private static final BlockPos FLAMINGOSTAGEFR = new BlockPos(153, 68, 205);
  private static final BlockPos FLAMINGOSTAGEBL = new BlockPos(132, 68, 231);
  private static final BlockPos FLAMINGOSTAGEBR = new BlockPos(157, 68, 213);
  private static final BlockPos FLAMINGOCROWDFL = new BlockPos(149, 65, 201);
  private static final BlockPos FLAMINGOCROWDFR = new BlockPos(126, 65, 218);
  private static final BlockPos FLAMINGOCROWDBL = new BlockPos(135, 65, 179);
  private static final BlockPos FLAMINGOCROWDBR = new BlockPos(111, 65, 194);
  public static final Stage FLAMINGO = new Stage("FLAMINGO",
      FLAMINGOSTAGECR,
      FLAMINGOSTAGEFL,
      FLAMINGOSTAGEFR,
      FLAMINGOSTAGEBL,
      FLAMINGOSTAGEBR,
      FLAMINGOCROWDFL,
      FLAMINGOCROWDFR,
      FLAMINGOCROWDBL,
      FLAMINGOCROWDBR);

  private static final BlockPos LINNESTAGECR = new BlockPos(75, 66, 335);
  // todo update coorinates
  private static final BlockPos LINNESTAGEFL = new BlockPos(99.065, 65, 119.145);
  private static final BlockPos LINNESTAGEFR = new BlockPos(137.844, 65, 118.312);
  private static final BlockPos LINNESTAGEBL = new BlockPos(84.564, 65, 145.904);
  private static final BlockPos LINNESTAGEBR = new BlockPos(144.389, 65, 154.429);
  private static final BlockPos LINNECROWDFL = new BlockPos(99.065, 65, 119.145);
  private static final BlockPos LINNECROWDFR = new BlockPos(137.844, 65, 118.312);
  private static final BlockPos LINNECROWDBL = new BlockPos(84.564, 65, 145.904);
  private static final BlockPos LINNECROWDBR = new BlockPos(144.389, 65, 154.429);
  public static final Stage LINNE = new Stage("LINNE",
      LINNESTAGECR,
      LINNESTAGEFL,
      LINNESTAGEFR,
      LINNESTAGEBL,
      LINNESTAGEBR,
      LINNECROWDFL,
      LINNECROWDFR,
      LINNECROWDBL,
      LINNECROWDBR);

  private static final BlockPos DUNGENSTAGECR = new BlockPos(161, 65, 43);
  // todo update coorinates
  private static final BlockPos DUNGENSTAGEFL = new BlockPos(99.065, 65, 119.145);
  private static final BlockPos DUNGENSTAGEFR = new BlockPos(137.844, 65, 118.312);
  private static final BlockPos DUNGENSTAGEBL = new BlockPos(84.564, 65, 145.904);
  private static final BlockPos DUNGENSTAGEBR = new BlockPos(144.389, 65, 154.429);
  private static final BlockPos DUNGENCROWDFL = new BlockPos(99.065, 65, 119.145);
  private static final BlockPos DUNGENCROWDFR = new BlockPos(137.844, 65, 118.312);
  private static final BlockPos DUNGENCROWDBL = new BlockPos(84.564, 65, 145.904);
  private static final BlockPos DUNGENCROWDBR = new BlockPos(144.389, 65, 154.429);
  public static final Stage DUNGEN = new Stage("DUNGEN",
      DUNGENSTAGECR,
      DUNGENSTAGEFL,
      DUNGENSTAGEFR,
      DUNGENSTAGEBL,
      DUNGENSTAGEBR,
      DUNGENCROWDFL,
      DUNGENCROWDFR,
      DUNGENCROWDBL,
      DUNGENCROWDBR);

    //private static final BlockPos S1PFL = new BlockPos(99.065, 65, 119.145);
//    private static final BlockPos S1PFR = new BlockPos(137.844, 65, 118.312);
//    private static final BlockPos S1PBL = new BlockPos(84.564, 65, 145.904);
//    private static final BlockPos S1PBR = new BlockPos(144.389, 65, 154.429);
//    public static final Stage Stage1Pitch = new Stage(S1PFL, S1PFR, S1PBL, S1PBR);
//
//    private static final BlockPos S1FL = new BlockPos(136.7, 68, 117.8);
//    private static final BlockPos S1FR = new BlockPos(99.3, 68, 117.4);
//    private static final BlockPos S1BL = new BlockPos(136.7, 68, 106.3);
//    private static final BlockPos S1BR = new BlockPos(99.3, 68, 106.3);
//    public static final Stage Stage1 = new Stage(S1FL, S1FR, S1BL, S1BR);
}
