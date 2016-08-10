package com.mineoutwest;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;

import com.mineoutwest.Stages;
import com.mineoutwest.speaker.Sounds;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class WowCommand implements ICommand {

    private List aliases;

    public WowCommand() {
        this.aliases = new ArrayList();
        this.aliases.add("wow");
    }

    @Override
    public String getCommandName() {
        return "wow";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return "wow";
    }

    @Override
    public List getCommandAliases() {
        return this.aliases;
    }

    @Override
    public void execute(MinecraftServer minecraftServer, ICommandSender iCommandSender, String[] strings) throws CommandException {
        World world = iCommandSender.getEntityWorld();
        world.setWorldTime(0);
        iCommandSender.addChatMessage(new TextComponentString("WOW opened!"));
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

