package com.mineoutwest.block;

import com.mineoutwest.Stages;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class ArtistAI extends EntityAIBase
{
    private final EntityCreature entity;
    private double xPosition;
    private double yPosition;
    private double zPosition;
    private final double speed;
    private int executionChance;
    private boolean mustUpdate;

    private double mMinX = -1, mMinY, mMinZ;
    private double mMaxX, mMaxY, mMaxZ;

    private Stages.Stage stage;

    private int mAlwaysFacePitch, mAlwaysFaceYaw;

    public ArtistAI(EntityCreature creatureIn, double speedIn)
    {
        this(creatureIn, speedIn, 120);
    }

    public ArtistAI(EntityCreature creatureIn, double speedIn, int chance)
    {
        this.entity = creatureIn;
        this.speed = speedIn;
        this.executionChance = 10;//chance;
        this.setMutexBits(1);
    }

    public void setStage(Stages.Stage stage) {
        this.stage = stage;
    }

    public void setAllowedRect(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        mMinX = minX;
        mMinY = minY;
        mMinZ = minZ;
        mMaxX = maxX;
        mMaxY = maxY;
        mMaxZ = maxZ;
    }

    public void setFacePitch(int pitch) {
        mAlwaysFacePitch = pitch;
    }

    public double clamp(double v, double min, double max) {
        if (v < min) v = min;
        else if (v > max) v = max;
        return v;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (!this.mustUpdate)
        {
            if (this.entity.getAge() >= 100)
            {
                return false;
            }

            if (this.entity.getRNG().nextInt(this.executionChance) != 0)
            {
                return false;
            }
        }

        if (stage != null) {
            BlockPos current = this.entity.getPosition();
            if (current.getY() < stage.stage.getMidPoint().getY() - 1) {
                BlockPos mid = stage.stage.getMidPoint();
                this.entity.setPosition(mid.getX(), mid.getY(), mid.getZ());
            }
        }

        Vec3d vec3d = RandomPositionGenerator.findRandomTarget(this.entity, 10, 7);

        if (vec3d == null)
        {
            return false;
        }
        else
        {
            if (mMinX >= 0) {
                this.xPosition = clamp(vec3d.xCoord, mMinX, mMaxX);
                this.yPosition = clamp(vec3d.yCoord, mMinY, mMaxY);
                this.zPosition = clamp(vec3d.zCoord, mMinZ, mMaxZ);
            } else {
                this.xPosition = vec3d.xCoord;
                this.yPosition = vec3d.yCoord;
                this.zPosition = vec3d.zCoord;
            }
            this.mustUpdate = false;
            return true;
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        if (!this.entity.getNavigator().noPath())
            return true;
        BlockPos pos = entity.getPosition();
        entity.setLocationAndAngles(pos.getX(), pos.getY(), pos.getZ(), mAlwaysFacePitch, 0);
        return false;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.entity.getNavigator().tryMoveToXYZ(this.xPosition, this.yPosition, this.zPosition, this.speed);
    }

    /**
     * Makes task to bypass chance
     */
    public void makeUpdate()
    {
        this.mustUpdate = true;
    }

    /**
     * Changes task random possibility for execution
     */
    public void setExecutionChance(int newchance)
    {
        this.executionChance = newchance;
    }
}