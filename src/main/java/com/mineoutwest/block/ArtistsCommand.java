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

/**
 * Created by Simon on 2016-08-09.
 */
public class ArtistsCommand implements ICommand {

    private List aliases;

    public ArtistsCommand() {
        this.aliases = new ArrayList();
        this.aliases.add("artists");
        this.aliases.add("art");
    }

    @Override
    public String getCommandName() {
        return "artists";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return "artists [spawn|kill]";
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
        if (lower.equals("spa")) {
            Actions.spawnArtistStage(world, Stages.AZALEA);
        } else if (lower.equals("spf")) {
            Actions.spawnArtistStage(world, Stages.FLAMINGO);
        } else if (lower.equals("spl")) {
            Actions.spawnArtistStage(world, Stages.LINNE);
        } else if (lower.equals("spd")) {
            Actions.spawnArtistStage(world, Stages.DUNGEN);
        } else if (lower.equals("kill")) {
            int nKills = Actions.killAllArtists(world);
            if (nKills > 0) {
                iCommandSender.addChatMessage(new TextComponentString(nKills + " artists killed."));
            } else {
                iCommandSender.addChatMessage(new TextComponentString("No artists found."));
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

