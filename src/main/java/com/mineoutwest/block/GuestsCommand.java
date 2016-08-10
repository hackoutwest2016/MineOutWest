package com.mineoutwest.block;

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

/**
 * Created by Simon on 2016-08-09.
 */
public class GuestsCommand implements ICommand {

    private List aliases;

    public GuestsCommand() {
        this.aliases = new ArrayList();
        this.aliases.add("guests");
        this.aliases.add("gu");
    }

    @Override
    public String getCommandName() {
        return "guests";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return "guests [spawn|kill]";
    }

    @Override
    public List getCommandAliases() {
        return this.aliases;
    }

    @Override
    public void execute(MinecraftServer minecraftServer, ICommandSender iCommandSender, String[] strings) throws CommandException {
        if (strings.length != 1) {
            iCommandSender.addChatMessage(new TextComponentString("Invalid arguments"));
            return;
        }

        World world = iCommandSender.getEntityWorld();
        String lower = strings[0].toLowerCase();
        if (lower.equals("spawn")) {
            Actions.spawnGuests(world);
        } else if (lower.equals("kill")) {
            int nKills = Actions.killAllGuests(world);
            if (nKills > 0) {
                iCommandSender.addChatMessage(new TextComponentString(nKills + " guests killed."));
            } else {
                iCommandSender.addChatMessage(new TextComponentString("No guests found."));
            };
        } else {
            iCommandSender.addChatMessage(new TextComponentString("Invalid arguments"));
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

