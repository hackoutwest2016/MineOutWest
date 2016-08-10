package com.mineoutwest.block;

import com.mineoutwest.Stages;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class GuestAIGoToStage extends EntityAIBase
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

    public GuestAIGoToStage(EntityCreature creatureIn, double speedIn)
    {
        this(creatureIn, speedIn, 120);
    }

    public GuestAIGoToStage(EntityCreature creatureIn, double speedIn, int chance)
    {
        this.entity = creatureIn;
        this.speed = speedIn;
        this.executionChance = 50; //chance - higher is more seldom
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

    public Vec3d getNextTarget() {
        BlockPos temp = new BlockPos(stage.crowd.getMidPoint());
        Vec3d temp2 = new Vec3d(temp.getX(), temp.getY(), temp.getZ());
        return temp2;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (!this.mustUpdate)
        {
            if (this.entity.getRNG().nextInt(this.executionChance) != 0)
            {
                return false;
            }
        }

        if (stage == null) {
            return false;
        }

        Vec3d vec3d = RandomPositionGenerator.findRandomTarget(this.entity, 3, 3);
        if (vec3d == null) {
            return false;
        }

        BlockPos target = new BlockPos(vec3d.xCoord, vec3d.yCoord, vec3d.zCoord);
        while (!stage.crowd.isPosInArea(target)) {
            vec3d = RandomPositionGenerator.findRandomTarget(this.entity, 3, 3);
            if (vec3d == null) {
                return false;
            }
            target = new BlockPos(vec3d.xCoord, vec3d.yCoord, vec3d.zCoord);
        }
        this.xPosition = vec3d.xCoord;
        this.yPosition = vec3d.yCoord;
        this.zPosition = vec3d.zCoord;
        this.mustUpdate = false;
        return true;
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



/* Guest AI overview:
- Has a list of concerts that it wants to go to at certain times.
- In between those times, there's a certain chance they'll change scene or go to bar or toilet.
- At a concert - try to move forwards a bit.
- At a concert - face the stage.
 *




public class GuestAIGoToStage extends EntityAIBase
{
    private final EntityCreature entity;
    private BlockPos target;
    private int executionChance;
    private boolean mustUpdate;
    private boolean hasVisitedStage;

    private int mMinX = -1, mMinY, mMinZ;
    private int mMaxX, mMaxY, mMaxZ;
    
    private Stages.Stage stage;

    private int mAlwaysFacePitch = -1, mAlwaysFaceYaw;

    public GuestAIGoToStage(EntityCreature creatureIn)
    {
        this.entity = creatureIn;
        this.setMutexBits(1);
        mustUpdate = true;
        hasVisitedStage = false;
    }

    public void setStage(Stages.Stage stage) {
        this.stage = stage;
        target = new BlockPos(stage.crowd.getMidPoint());
    }

    public void setAllowedRect(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        mMinX = minX;
        mMinY = minY;
        mMinZ = minZ;
        mMaxX = maxX;
        mMaxY = maxY;
        mMaxZ = maxZ;
    }

    public double clamp(double v, double min, double max) {
        if (v < min) v = min;
        else if (v > max) v = max;
        return v;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     *
    public boolean shouldExecute()
    {
        if (!hasVisitedStage) {
            return true;
        }


        if (!this.mustUpdate)
        {
            if (this.entity.getRNG().nextInt(this.executionChance) != 0)
            {
                return false;
            }
        }


        Vec3d vec3d = RandomPositionGenerator.findRandomTarget(this.entity, 10, 7);

        if (vec3d == null)
        {
            return false;
        }
        else
        {
//            if (mMinX >= 0) {
 //               this.target. = clamp(vec3d.xCoord, mMinX, mMaxX);
  //              this.yPosition = clamp(vec3d.yCoord, mMinY, mMaxY);
   ////             this.zPosition = clamp(vec3d.zCoord, mMinZ, mMaxZ);
       //     } else {
         //       this.xPosition = vec3d.xCoord;
          //      this.yPosition = vec3d.yCoord;
            //    this.zPosition = vec3d.zCoord;
            //}
            this.mustUpdate = false;
            return true;
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     *
    public boolean continueExecuting()
    {
        if (!this.entity.getNavigator().noPath())
            return true;
        BlockPos pos = entity.getPosition();
        entity.setLocationAndAngles(pos.getX(), pos.getY(), pos.getZ(), -180, 0);
        if (stage != null) {
            if (stage.crowd.isPosInArea(entity.getPosition())) {
                hasVisitedStage = true;
            }
        }
        return false;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     *
    public void startExecuting()
    {
        if (target != null) {

            this.entity.getNavigator().tryMoveToXYZ(294, 73, 26, 0.3D); // target.getX(), target.getY(), target.getZ(), 0.3D);
        }
    }

    /**
     * Makes task to bypass chance
     *
    public void makeUpdate()
    {
        this.mustUpdate = true;
    }

    /**
     * Changes task random possibility for execution
     *
    public void setExecutionChance(int newchance)
    {
        this.executionChance = newchance;
    }
}
*/