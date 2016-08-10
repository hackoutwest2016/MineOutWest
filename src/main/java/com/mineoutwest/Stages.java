package com.mineoutwest;

import com.google.common.collect.Lists;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;

import java.util.ArrayList;
import java.util.List;

public class Stages {

    //public static final BlockPos LINNE = new BlockPos(75, 66, 335);
    //public static final BlockPos AZALEA = new BlockPos(122, 66, 118);
    //public static final BlockPos FLAMINGO = new BlockPos(140, 65, 212);
    //public static final BlockPos DUNGEN = new BlockPos(161, 65, 43);

    public static class Area {
        public BlockPos FL;
        public BlockPos FR;
        public BlockPos BL;
        public BlockPos BR;

        public Area( BlockPos front_left,
                     BlockPos front_right,
                     BlockPos back_left,
                     BlockPos back_right) {
            FL = front_left;
            FR = front_right;
            BL = back_left;
            BR = back_right;
        }

        public BlockPos getMidPoint() {
            BlockPos mid = new BlockPos( (FL.getX() + FR.getX() + BL.getX() + BR.getX()) / 4,
                                         (FL.getY() + FR.getY() + BL.getY() + BR.getY()) / 4,
                                         (FL.getZ() + FR.getZ() + BL.getZ() + BR.getZ()) / 4);
            return mid;
        }

        public boolean isPosInArea(BlockPos pos) {
            Vec3i p1 = new Vec3i(FL.getX(), 0.0, FL.getZ());
            Vec3i p2 = new Vec3i(FR.getX(), 0.0, FR.getZ());
            Vec3i p3 = new Vec3i(BR.getX(), 0.0, BR.getZ());
            Vec3i p4 = new Vec3i(BL.getX(), 0.0, BL.getZ());

            Vec3i L1 = new Vec3i( p2.getX() - p1.getX(), 0.0, p2.getZ() - p1.getZ());
            Vec3i L2 = new Vec3i( p3.getX() - p2.getX(), 0.0, p3.getZ() - p2.getZ());
            Vec3i L3 = new Vec3i( p4.getX() - p3.getX(), 0.0, p4.getZ() - p3.getZ());
            Vec3i L4 = new Vec3i( p1.getX() - p4.getX(), 0.0, p1.getZ() - p4.getZ());

            Vec3i P = new Vec3i(pos.getX(), 0.0, pos.getZ());
            Vec3i P1 = new Vec3i( P.getX() - p1.getX(), 0.0, P.getZ() - p1.getZ());
            Vec3i P2 = new Vec3i( P.getX() - p2.getX(), 0.0, P.getZ() - p2.getZ());
            Vec3i P3 = new Vec3i( P.getX() - p3.getX(), 0.0, P.getZ() - p3.getZ());
            Vec3i P4 = new Vec3i( P.getX() - p4.getX(), 0.0, P.getZ() - p4.getZ());

            Vec3i C1 = P1.crossProduct(L1);
            Vec3i C2 = P2.crossProduct(L2);
            Vec3i C3 = P3.crossProduct(L3);
            Vec3i C4 = P4.crossProduct(L4);

            return (C1.getY() >= 0.0 && C2.getY() >= 0.0 && C3.getY() >= 0.0 && C4.getY() >= 0.0);
        }
    }


    public static class Stage {
        public String NAME;

        public Area stage;
        public Area crowd;
        public int FACING;
        public double STAGEMOVEMENT;

        public long spawnTime;
        public boolean isSpawned;
        public String artistName;

        public Stage(String name,
            BlockPos front_left,
            BlockPos front_right,
            BlockPos back_left,
            BlockPos back_right,
            BlockPos crowd_fl,
            BlockPos crowd_fr,
            BlockPos crowd_bl,
            BlockPos crowd_br,
            int facing, double stagemovement) {
            NAME = name;
            stage = new Area(front_left, front_right, back_left, back_right);
            crowd = new Area(crowd_fl, crowd_fr, crowd_bl, crowd_br);
            FACING = facing;
            STAGEMOVEMENT = stagemovement;
        }

      public BlockPos getCenter() {
        return stage.getMidPoint();
      }
    }

    public static final BlockPos AZALEASTAGECR = new BlockPos(122, 66, 118);

    public static final BlockPos AZALEASTAGEFL = new BlockPos(135, 68, 117);
    public static final BlockPos AZALEASTAGEFR = new BlockPos(110, 68, 117);
    public static final BlockPos AZALEASTAGEBL = new BlockPos(135, 68, 106);
    public static final BlockPos AZALEASTAGEBR = new BlockPos(110, 68, 106);

    public static final BlockPos AZALEACROWDFL = new BlockPos(110, 65, 118);
    public static final BlockPos AZALEACROWDFR = new BlockPos(135, 65, 118);
    public static final BlockPos AZALEACROWDBL = new BlockPos(110, 65, 138);
    public static final BlockPos AZALEACROWDBR = new BlockPos(135, 65, 138);
    public static final Stage AZALEA = new Stage("AZALEA",
        AZALEASTAGEFL,
        AZALEASTAGEFR,
        AZALEASTAGEBL,
        AZALEASTAGEBR,
        AZALEACROWDFL,
        AZALEACROWDFR,
        AZALEACROWDBL,
        AZALEACROWDBR,
            0, 2);

  public static final BlockPos FLAMINGOSTAGECR = new BlockPos(140, 65, 212);
  public static final BlockPos FLAMINGOSTAGEFL = new BlockPos(132, 68, 219);
  public static final BlockPos FLAMINGOSTAGEFR = new BlockPos(153, 68, 205);
  public static final BlockPos FLAMINGOSTAGEBL = new BlockPos(132, 68, 231);
  public static final BlockPos FLAMINGOSTAGEBR = new BlockPos(157, 68, 213);
  public static final BlockPos FLAMINGOCROWDFL = new BlockPos(149, 65, 201);
  public static final BlockPos FLAMINGOCROWDFR = new BlockPos(126, 65, 218);
  public static final BlockPos FLAMINGOCROWDBL = new BlockPos(135, 65, 179);
  public static final BlockPos FLAMINGOCROWDBR = new BlockPos(111, 65, 194);
  public static final Stage FLAMINGO = new Stage("FLAMINGO",
      FLAMINGOSTAGEFL,
      FLAMINGOSTAGEFR,
      FLAMINGOSTAGEBL,
      FLAMINGOSTAGEBR,
      FLAMINGOCROWDFL,
      FLAMINGOCROWDFR,
      FLAMINGOCROWDBL,
      FLAMINGOCROWDBR,
          130, 2);

  public static final BlockPos LINNESTAGECR = new BlockPos(75, 66, 335);
  public static final BlockPos LINNESTAGEFL = new BlockPos(74, 67, 352);
  public static final BlockPos LINNESTAGEFR = new BlockPos(81, 67, 348);
  public static final BlockPos LINNESTAGEBL = new BlockPos(74, 67, 355);
  public static final BlockPos LINNESTAGEBR = new BlockPos(82, 67, 352);
  public static final BlockPos LINNECROWDFL = new BlockPos(81, 65, 346);
  public static final BlockPos LINNECROWDFR = new BlockPos(69, 65, 351);
  public static final BlockPos LINNECROWDBL = new BlockPos(90, 65, 312);
  public static final BlockPos LINNECROWDBR = new BlockPos(43, 65, 310);
  public static final Stage LINNE = new Stage("LINNE",
      LINNESTAGEFL,
      LINNESTAGEFR,
      LINNESTAGEBL,
      LINNESTAGEBR,
      LINNECROWDFL,
      LINNECROWDFR,
      LINNECROWDBL,
      LINNECROWDBR,
          160, 1.2);

  public static final BlockPos DUNGENSTAGECR = new BlockPos(161, 65, 43);
  public static final BlockPos DUNGENSTAGEFL = new BlockPos(164, 67, 31);
  public static final BlockPos DUNGENSTAGEFR = new BlockPos(162, 67, 34);
  public static final BlockPos DUNGENSTAGEBL = new BlockPos(164, 67, 31);
  public static final BlockPos DUNGENSTAGEBR = new BlockPos(162, 67, 34);
  public static final BlockPos DUNGENCROWDFL = new BlockPos(161, 65, 35);
  public static final BlockPos DUNGENCROWDFR = new BlockPos(166, 65, 30);
  public static final BlockPos DUNGENCROWDBL = new BlockPos(166, 65, 42);
  public static final BlockPos DUNGENCROWDBR = new BlockPos(171, 65, 36);
  public static final Stage DUNGEN = new Stage("DUNGEN",
      DUNGENSTAGEFL,
      DUNGENSTAGEFR,
      DUNGENSTAGEBL,
      DUNGENSTAGEBR,
      DUNGENCROWDFL,
      DUNGENCROWDFR,
      DUNGENCROWDBL,
      DUNGENCROWDBR,
          -44, 0.5);


    public static Stage [] ALL_STAGES = new Stage[] {AZALEA, FLAMINGO, DUNGEN, LINNE};
}
