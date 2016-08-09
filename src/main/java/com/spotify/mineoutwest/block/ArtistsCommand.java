package com.spotify.mineoutwest.block;

import com.google.common.base.Predicate;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

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
        if (lower.equals("spawn")) {
            BlockPos worldSpawnPoint = new BlockPos(126, 68, 112);

            Artist artist = new Artist(world);
            artist.setLocationAndAngles(worldSpawnPoint.getX(), worldSpawnPoint.getY(), worldSpawnPoint.getZ(), 30, 30);

            world.spawnEntityInWorld(artist);

            iCommandSender.addChatMessage(new TextComponentString("Artist spawned at " + (worldSpawnPoint.getX() - 2) + ", " + worldSpawnPoint.getY() + ", " + worldSpawnPoint.getZ()));
        } else if (lower.equals("kill")) {
            int nKills = 0;
            for (Entity entity : world.getEntities(Artist.class, new Predicate<Artist>() {
                @Override
                public boolean apply(@Nullable Artist input) {
                    return true;
                }
            })) {
                entity.onKillCommand();
                nKills++;
            }
            if (nKills > 0) {
                iCommandSender.addChatMessage(new TextComponentString(nKills + " artists killed."));
            } else {
                iCommandSender.addChatMessage(new TextComponentString("No artists found."));
            }
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

