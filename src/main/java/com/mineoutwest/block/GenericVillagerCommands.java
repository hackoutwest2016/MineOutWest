package com.mineoutwest.block;

import com.mineoutwest.Stages;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class GenericVillagerCommands implements ICommand {

    private List aliases;

    public GenericVillagerCommands() {
        this.aliases = new ArrayList();
        this.aliases.add("villager");
    }

    @Override
    public String getCommandName() {
        return "villager";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return "villager [spawn|kill] x y z name profession";
    }

    @Override
    public List getCommandAliases() {
        return this.aliases;
    }

    @Override
    public void execute(MinecraftServer minecraftServer, ICommandSender iCommandSender, String[] strings) throws CommandException {
        if (strings.length < 1) {
            iCommandSender.addChatMessage(new TextComponentString("Invalid arguments"));
        }

        World world = iCommandSender.getEntityWorld();
        String lower = strings[0].toLowerCase();

        if (lower.equals("villagers")) {
            for(int i = 0; i < 100; i++) {
                BlockPos pos = Stages.ALL_STAGES[i % Stages.ALL_STAGES.length].crowd.getMidPoint();
                BlockPos pos2 = Stages.ALL_STAGES[(i>>2) % Stages.ALL_STAGES.length].crowd.getMidPoint();

                Actions.spawnVillager(world, (pos.getX() + pos2.getX()) / 2, (pos.getY() + pos2.getY())/2, (pos.getZ() + pos2.getZ())/2);
            }
            return;
        }

        if (strings.length == 6) {
          int x = Integer.parseInt(strings[1]);
          int y = Integer.parseInt(strings[2]);
          int z = Integer.parseInt(strings[3]);
          String name = strings[4];
          int professionId = Integer.parseInt(strings[5]);

          if (lower.equals("spawn")) {
            iCommandSender.addChatMessage(new TextComponentString("spawning "+ name +" at (" + x + "," + y + "," + z + ")"));
            Actions.spawnGenericStandingVillager(world, x, y, z, name, professionId);
          } else if (lower.equals("kill")) {
            int kills = Actions.killGenericStandingVillager(world, x, y, z, name, professionId);
            if (kills > 0) {
              iCommandSender.addChatMessage(new TextComponentString("killed "+ name +" at (" + x + "," + y + "," + z + ")"));
              iCommandSender.addChatMessage(new TextComponentString("killed " + kills + " villager"));
            } else {
              iCommandSender.addChatMessage(new TextComponentString("Nothing killed."));
            }

        } else {
          iCommandSender.addChatMessage(new TextComponentString("Need x,y,z,name,profession"));
        }

     }
    }

    @Override
    public boolean checkPermission(MinecraftServer minecraftServer, ICommandSender iCommandSender) {
        return true;
    }

    @Override
    public List<String> getTabCompletionOptions(MinecraftServer minecraftServer, ICommandSender iCommandSender, String[] strings, @Nullable BlockPos blockPos) {
        return null;
    }


    @Override
    public boolean isUsernameIndex(String[] astring, int i) {
        return false;
    }

    @Override
    public int compareTo(ICommand o) {
        return 0;
    }
}

